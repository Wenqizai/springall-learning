## About

- 官方文档

  https://docs.spring.io/spring-cloud-openfeign/docs/2.2.10.RELEASE/reference/html/

- 参看blog

  https://www.cnblogs.com/binarylei/p/11576147.html

## 概述

Feign封装HTTP调用流程，面向接口编程，并为很多的HTTP Client做了大量的适配工作。

<img src="Spring-OpenFeign.assets/Http调用流程.png" alt="Http调用流程" style="zoom:50%;" />



> Feign整体设计

- 整体设计

![Feign整体设计](Spring-OpenFeign.assets/Feign整体设计.png)

1. 前两步是生成动态对象：将 Method 方法的注解解析成 MethodMetadata，并最终生成 Feign 动态代理对象。
2. 后几步是调用过程：根据解析的 MethodMetadata 对象，将 Method 方法的参数转换成 Request，最后调用 Client 发送请求。



> REST声明规范

其中 `Feign` 已经适配了 JAX-RS 1/2 和 Feign 自带的注解规范。`Spring Cloud Open Feign` 进一步适配了 Spring Web MVC 的注解规范。

| REST框架       | 使用场景                | 请求映射注解    | 请求参数      |
| -------------- | ----------------------- | --------------- | ------------- |
| JAX-RS         | 客户端声明、 服务端声明 | @Path           | @Param        |
| Feign          | 客户端声明              | @RequestLine    | @Param        |
| Spring Web MVC | 服务端声明              | @ReqeustMapping | @RequestParam |



> Feign的声明使用

```java
// 构建代理对象
GitHub github = Feign.builder()
    .encoder(new GsonDecoder())
    .decoder(new GsonEncoder())
    .errorDecoder(new GitHubErrorDecoder(decoder))
    .logger(new Logger.ErrorLogger())
    .logLevel(Logger.Level.BASIC)
    .requestInterceptor(template -> {
        if (System.getenv().containsKey(GITHUB_TOKEN)) {
            System.out.println("Detected Authorization token from environment variable");
            template.header(
                "Authorization",
                "token " + System.getenv(GITHUB_TOKEN));
        }
    })
    .target(GitHub.class, "https://api.github.com");

// 方法调用
github.contributors("openfeign", "some-unknown-project");
```

## 动态代理对象生成

目的：生成Target.type 的代理对象 proxy，这个代理对象就可以像访问普通方法一样发送 Http 请求，其实和 RPC 的 Stub 模型是一样的。了解 proxy 后，其执行过程其实也就一模了然。



![Feign生成代理对象.png](Spring-OpenFeign.assets/Feign生成代理对象.png)




- 生成动态代理对象的核心方法：

feign.Feign.Builder#target(java.lang.Class<T>, java.lang.String)

feign.Feign.Builder#target(feign.Target<T>)

```java
public <T> T target(Target<T> target) {
  return build().newInstance(target);
}

public <T> T target(Class<T> apiType, String url) {
    return target(new HardCodedTarget<T>(apiType, url));
}
```

### Target

```java
public interface Target<T> {
    // 接口的类型
    Class<T> type();

    // 代理对象的名称，默认为url,负载均衡时有用
    String name();

    // 请求的url地址，eg: https://api/v2
    String url();
}
```

### ReflectiveFeign

feign的默认实现ReflectiveFeign。当然我们可以通过`extend Feign.Builder`来重写`build()`方法，替换Feign的实现形式，如`feign.hystrix.HystrixFeign`。



> 构建ReflectiveFeign

feign.Feign.Builder#build

```java
public Feign build() {
    // client 有三种实现 JdkHttp/ApacheHttp/okHttp，默认是 jdk 的实现
    // RequestInterceptor 请求拦截器
    SynchronousMethodHandler.Factory synchronousMethodHandlerFactory =
        new SynchronousMethodHandler.Factory(client, retryer, requestInterceptors, logger,
                                             logLevel, decode404, closeAfterDecode, propagationPolicy);

    // Contract REST注解解析器，默认为 Contract.Default()，即支持 Feign 的原生注解。
    ParseHandlersByName handlersByName =
        new ParseHandlersByName(contract, options, encoder, decoder, queryMapEncoder,
                                errorDecoder, synchronousMethodHandlerFactory);

    // InvocationHandlerFactory 生成 JDK 动态代理，实际执行是委托给了 MethodHandler。
    return new ReflectiveFeign(handlersByName, invocationHandlerFactory, queryMapEncoder);
}
```



> 生成代理对象

feign.ReflectiveFeign#newInstance

```java
// creates an api binding to the target. As this invokes reflection, care should be taken to cache the result.
// 创建与target的 api 绑定。由于这会调用反射，因此应注意缓存结果
public <T> T newInstance(Target<T> target) {
  Map<String, MethodHandler> nameToHandler = targetToHandlersByName.apply(target);
  Map<Method, MethodHandler> methodToHandler = new LinkedHashMap<Method, MethodHandler>();
  List<DefaultMethodHandler> defaultMethodHandlers = new LinkedList<DefaultMethodHandler>();
  // 1. Contract 将 target.type 接口类上的方法和注解解析成 MethodMetadata，并转换成内部的MethodHandler处理方式
  for (Method method : target.type().getMethods()) {
    if (method.getDeclaringClass() == Object.class) {
      continue;
    } else if (Util.isDefault(method)) {
      DefaultMethodHandler handler = new DefaultMethodHandler(method);
      defaultMethodHandlers.add(handler);
      methodToHandler.put(method, handler);
    } else {
      methodToHandler.put(method, nameToHandler.get(Feign.configKey(target.type(), method)));
    }
  }
  // 2. 生成 target.type 的 jdk 动态代理对象
  InvocationHandler handler = factory.create(target, methodToHandler); // InvocationHandler委托给SynchronousMethodHandler.Factory创建, 传入methodToHandler
  T proxy = (T) Proxy.newProxyInstance(target.type().getClassLoader(),
                                       new Class<?>[] {target.type()}, handler);

  for (DefaultMethodHandler defaultMethodHandler : defaultMethodHandlers) {
    defaultMethodHandler.bindTo(proxy);
  }
  return proxy;
}
```

### MethodHandler 

由动态代理对象生成（`newInstance()`）可以知道，通过`ParseHandlersByName.apply` 可以生成了每个方法的执行器MethodHandler。

```java
Map<String, MethodHandler> nameToHandler = targetToHandlersByName.apply(target);
```



> apply

这个方法由以下几步：

1. Contract 统一将方法解析 MethodMetadata，这样就可以通过实现不同的 Contract 适配各种 REST 声明式规范。
2. buildTemplate 实际上将 Method 方法的参数转换成 Request。
3. 将 metadata 和 buildTemplate 封装成 MethodHandler。

feign.ReflectiveFeign.ParseHandlersByName#apply

```java
public Map<String, MethodHandler> apply(Target key) {
  // 1. contract解析代理类Target中的方法、参数和注解等，并生成MethodMetadata
  List<MethodMetadata> metadata = contract.parseAndValidatateMetadata(key.type());
  Map<String, MethodHandler> result = new LinkedHashMap<String, MethodHandler>();
  for (MethodMetadata md : metadata) {
    // 2. buildTemplate 实际上将请求Method方法的参数转换成 Request
    BuildTemplateByResolvingArgs buildTemplate;
    if (!md.formParams().isEmpty() && md.template().bodyTemplate() == null) {
      // 2.1 表单
      buildTemplate = new BuildFormEncodedTemplateFromArgs(md, encoder, queryMapEncoder);
    } else if (md.bodyIndex() != null) {
      // 2.2 @Body 注解
      buildTemplate = new BuildEncodedTemplateFromArgs(md, encoder, queryMapEncoder);
    } else {
      // 2.3 其余
      buildTemplate = new BuildTemplateByResolvingArgs(md, queryMapEncoder);
    }
    // 3. 将 metadata 和 buildTemplate 封装成 MethodHandler
    result.put(md.configKey(),
               factory.create(key, md, buildTemplate, options, decoder, errorDecoder));
  }
  return result;
}
```

## 调用过程

由上面可知，动态代理对象生成，并绑定了MethodHandler。当代理对象调用方法是就会调用proxy对象的`invoke()`方法，进而完成HTTP调用的过程。

![Feign调用过程](spring-openfeign.assets/Feign调用过程.png)

### FeignInvocationHandler#invoke

动态代理对象的默认实现`feign.ReflectiveFeign.FeignInvocationHandler`。该方法执行的`invoke()`，实际上是委托给`SynchronousMethodHandler#invoke`执行。

```java
@Override
public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
  if ("equals".equals(method.getName())) {
    try {
      Object otherHandler =
        args.length > 0 && args[0] != null ? Proxy.getInvocationHandler(args[0]) : null;
      return equals(otherHandler);
    } catch (IllegalArgumentException e) {
      return false;
    }
  } else if ("hashCode".equals(method.getName())) {
    return hashCode();
  } else if ("toString".equals(method.getName())) {
    return toString();
  }
  // 委托给MethodHandler执行invoke方法，每个方法都对应一个MethodHandler。
  // MethodHandler是在生成proxy时存入dispatch，见：InvocationHandler handler = factory.create(target, methodToHandler);
  return dispatch.get(method).invoke(args);
}
```

###  SynchronousMethodHandler#invoke

该方法主要做：发起 http 请求，并根据 retryer 进行重试，具体执行委托给`executeAndDecode()`方法。

```java
@Override
public Object invoke(Object[] argv) throws Throwable {
  // template将argv参数构建成Request
  RequestTemplate template = buildTemplateFromArgs.create(argv);
  Options options = findOptions(argv);
  Retryer retryer = this.retryer.clone();
  while (true) {
    try {
      // 调用client.execute(request, options)
      return executeAndDecode(template, options);
    } catch (RetryableException e) {
      try {
        // 重试机制
        retryer.continueOrPropagate(e);
      } catch (RetryableException th) {
        Throwable cause = th.getCause();
        if (propagationPolicy == UNWRAP && cause != null) {
          throw cause;
        } else {
          throw th;
        }
      }
      if (logLevel != Logger.Level.NONE) {
        logger.logRetry(metadata.configKey(), logLevel);
      }
      continue;
    }
  }
}
```

> executeAndDecode

该方法主要做：一是编码生成Request；二是http请求；三是解码生成Response。

```java
Object executeAndDecode(RequestTemplate template, Options options) throws Throwable {
  // 1. 调用拦截器 RequestInterceptor，并根据 template 生成 Request
  Request request = targetRequest(template);

  if (logLevel != Logger.Level.NONE) {
    logger.logRequest(metadata.configKey(), logLevel, request);
  }

  Response response;
  long start = System.nanoTime();
  try {
    // 2. http 请求
    response = client.execute(request, options);
  } catch (IOException e) {
    if (logLevel != Logger.Level.NONE) {
      logger.logIOException(metadata.configKey(), logLevel, e, elapsedTime(start));
    }
    throw errorExecuting(request, e);
  }
  long elapsedTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);

  boolean shouldClose = true;
  try {
    if (logLevel != Logger.Level.NONE) {
      response =
          logger.logAndRebufferResponse(metadata.configKey(), logLevel, response, elapsedTime);
    }
    // 3. response 解码
    if (Response.class == metadata.returnType()) {
      if (response.body() == null) {
        return response;
      }
      if (response.body().length() == null ||
          response.body().length() > MAX_RESPONSE_BUFFER_SIZE) {
        shouldClose = false;
        return response;
      }
      // Ensure the response body is disconnected
      byte[] bodyData = Util.toByteArray(response.body().asInputStream());
      return response.toBuilder().body(bodyData).build();
    }
    if (response.status() >= 200 && response.status() < 300) {
      if (void.class == metadata.returnType()) {
        return null;
      } else {
        Object result = decode(response);
        shouldClose = closeAfterDecode;
        return result;
      }
    } else if (decode404 && response.status() == 404 && void.class != metadata.returnType()) {
      Object result = decode(response);
      shouldClose = closeAfterDecode;
      return result;
    } else {
      throw errorDecoder.decode(metadata.configKey(), response);
    }
  } catch (IOException e) {
    if (logLevel != Logger.Level.NONE) {
      logger.logIOException(metadata.configKey(), logLevel, e, elapsedTime);
    }
    throw errorReading(request, response, e);
  } finally {
    if (shouldClose) {
      ensureClosed(response.body());
    }
  }
}
```

> targetRequest

```java
Request targetRequest(RequestTemplate template) {
  for (RequestInterceptor interceptor : requestInterceptors) {
    interceptor.apply(template);
  }
  return target.apply(template);
}
```



**Note**

1. 每个类的作用，如：ParseHandlersByName







