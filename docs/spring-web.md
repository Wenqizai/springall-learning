# Spring Webflux

- blog
  - https://www.cnblogs.com/davidwang456/p/10396168.html

- 核心类
  1. org.springframework.web.reactive.DispatcherHandler：分发器，将请求和响应分配给`HandlerMapping、HandlerAdapter、HandlerResultHandler`来处理。
  2. org.springframework.web.reactive.HandlerMapping：处理请求映射
  3. org.springframework.web.reactive.HandlerAdapter：用来适配handler的接口
  4. org.springframework.web.reactive.HandlerResultHandler：返回handler处理的结果

## 启动

`DispatcherHandler implements ApplicationContextAware`在Spring容器启动时加载`HandlerMapping、HandlerAdapter、HandlerResultHandler`。

```java
// 容器启动时调用
@Override
public void setApplicationContext(ApplicationContext applicationContext) {
   initStrategies(applicationContext);
}


protected void initStrategies(ApplicationContext context) {
    // 加载bean类型时HandlerMapping类，包括父类型
    Map<String, HandlerMapping> mappingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(
        context, HandlerMapping.class, true, false);
    ArrayList<HandlerMapping> mappings = new ArrayList<>(mappingBeans.values());
    AnnotationAwareOrderComparator.sort(mappings);
    this.handlerMappings = Collections.unmodifiableList(mappings);

    // 加载bean类型时HandlerAdapter类，包括父类型
    Map<String, HandlerAdapter> adapterBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(
        context, HandlerAdapter.class, true, false);
    this.handlerAdapters = new ArrayList<>(adapterBeans.values());
    AnnotationAwareOrderComparator.sort(this.handlerAdapters);

    // 加载bean类型时HandlerResultHandler类，包括父类型
    Map<String, HandlerResultHandler> beans = BeanFactoryUtils.beansOfTypeIncludingAncestors(
        context, HandlerResultHandler.class, true, false);
    this.resultHandlers = new ArrayList<>(beans.values());
    AnnotationAwareOrderComparator.sort(this.resultHandlers);
}
```

## handle

```java
@Override
public Mono<Void> handle(ServerWebExchange exchange) {
   if (this.handlerMappings == null) {
      return createNotFoundError();
   }
   return Flux.fromIterable(this.handlerMappings)				 // 获取所有的handlerMappings
         .concatMap(mapping -> mapping.getHandler(exchange))	 // 根据Mapping来获取handler
         .next()											 // 下一个hanlder
         .switchIfEmpty(createNotFoundError())				   // handler是空，error处理
         .flatMap(handler -> invokeHandler(exchange, handler))  // 找到handler，调用handlerAdapter.handle(exchange, handler)，返回handleResult
         .flatMap(result -> handleResult(exchange, result));   // handleResult处理
}
```



