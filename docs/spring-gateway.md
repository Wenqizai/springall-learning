## 概述

参考文档：

http://c.biancheng.net/springcloud/gateway.html

https://github.com/YunaiV/Blog

> Base

Spring Cloud Gateway 是基于 `Spring 5.0`、`Spring Boot 2.0` 、`project reactor`和`WebFlux`框架实现的，而 WebFlux 框架底层则使用了高性能的 Reactor 模式通信框架 Netty。

-  **project reactor**，遵循 Reactive Streams Specification，使用非阻塞编程模型。

- **webflux**，基于 spring 5.x 和 `reactor-netty` 构建，不依赖于 Servlet 容器，但是可以在支持 Servlet 3.1 Non-Blocking IO API 的容器上运行。

### 工作流程

<img src="spring-gateway.assets/spring-gateway工作流程.png" alt="spring gateway工作流程" style="zoom: 80%;" />

1. 客户端将请求发送到Gateway上；
2. Gateway通过Gateway Handler Mapping找到请求相匹配的路由，将其发送到对应的Gateway Web Handler；
3. Gateway Web Handler通过指定的过滤器链（Filter Chain），将请求转发到实际的服务节点中，执行业务逻辑返回响应结果；
4. 过滤器之间用虚线分开是因为过滤器可能会在转发请求之前（pre）或之后（post）执行业务逻辑；
5. 过滤器（Filter）可以在请求被转发到服务端前，对请求进行拦截和修改，例如：参数校验，权限校验，流量监控，日志输出以及协议转换等；
6. 过滤器可以在响应返回客户端之前，对响应进行拦截和再处理，例如：修改响应内容，修改响应头，日志输出，流量监控等；
7. 响应原路返回给客户端。

> 组件交互

![gateway-组件交互](spring-gateway.assets/gateway-组件交互.png)

### Predicate 断言

Spring Cloud Gateway 通过 ==Predicate 断言来实现 Route 路由的匹配规则==。简单点说，Predicate 是路由转发的判断条件，请求只有==满足了 Predicate 的条件，才会被转发到指定的服务上==进行处理。

![Predicate-断言匹配](spring-gateway.assets/Predicate-断言匹配.png)

> 注意点

- Route 路由与 Predicate 断言的对应关系为“一对多”，一个路由可以包含多个不同断言。
- 一个请求想要转发到指定的路由上，就必须同时匹配路由上的所有断言。
- 当一个请求同时满足多个路由的断言条件时，请求只会被首个成功匹配的路由转发。

### Filter 过滤器

> 按类型划分

- Pre 类型  

  这种过滤器在**请求被转发到微服务之前**可以对请求进行拦截和修改，例如参数校验、权限校验、流量监控、日志输出以及协议转换等操作。

- Post 类型 

  这种过滤器**在微服务对请求做出响应后**可以对响应进行拦截和再处理，例如修改响应内容或响应头、日志输出、流量监控等。

> 按作用范围划分

- GatewayFilter：应用在单个路由或者一组路由上的过滤器。
- GlobalFilter：应用在所有的路由上的过滤器。

## 启动

spring-cloud-gateway-server配置文件spring.factories加载配置类：

```properties
# Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.cloud.gateway.config.GatewayClassPathWarningAutoConfiguration,\
org.springframework.cloud.gateway.config.GatewayAutoConfiguration,\
org.springframework.cloud.gateway.config.GatewayHystrixCircuitBreakerAutoConfiguration,\
org.springframework.cloud.gateway.config.GatewayResilience4JCircuitBreakerAutoConfiguration,\
org.springframework.cloud.gateway.config.GatewayLoadBalancerClientAutoConfiguration,\
org.springframework.cloud.gateway.config.GatewayNoLoadBalancerClientAutoConfiguration,\
org.springframework.cloud.gateway.config.GatewayMetricsAutoConfiguration,\
org.springframework.cloud.gateway.config.GatewayRedisAutoConfiguration,\
org.springframework.cloud.gateway.discovery.GatewayDiscoveryClientAutoConfiguration,\
org.springframework.cloud.gateway.config.SimpleUrlHandlerMappingGlobalCorsAutoConfiguration,\
org.springframework.cloud.gateway.config.GatewayReactiveLoadBalancerClientAutoConfiguration

org.springframework.boot.env.EnvironmentPostProcessor=\
org.springframework.cloud.gateway.config.GatewayEnvironmentPostProcessor

# Failure Analyzers
org.springframework.boot.diagnostics.FailureAnalyzer=\
org.springframework.cloud.gateway.support.MvcFoundOnClasspathFailureAnalyzer
```

### GatewayAutoConfiguration

网关开启配置：`spring.cloud.gateway.enabled`，默认开启。GatewayAutoConfiguration用来加载响应的Bean组件。

```java
@Configuration(proxyBeanMethods = false)
// 默认开启网关
@ConditionalOnProperty(name = "spring.cloud.gateway.enabled", matchIfMissing = true)
@EnableConfigurationProperties
// GatewayAutoConfiguration之后加载
@AutoConfigureBefore({ HttpHandlerAutoConfiguration.class, WebFluxAutoConfiguration.class })
// GatewayAutoConfiguration之前加载
@AutoConfigureAfter({ GatewayLoadBalancerClientAutoConfiguration.class, GatewayClassPathWarningAutoConfiguration.class })
@ConditionalOnClass(DispatcherHandler.class)
public class GatewayAutoConfiguration {
    
    // Netty实现的Client(封装了连接池ConnectionProvider)
    @Bean
    @ConditionalOnMissingBean
    public HttpClient gatewayHttpClient(HttpClientProperties properties, List<HttpClientCustomizer> customizers) {}

    @Bean
    @ConditionalOnEnabledGlobalFilter
    public NettyRoutingFilter routingFilter(HttpClient httpClient, ObjectProvider<List<HttpHeadersFilter>> headersFilters, HttpClientProperties properties) {
        return new NettyRoutingFilter(httpClient, headersFilters, properties);
    }

    @Bean
    @ConditionalOnEnabledGlobalFilter
    public NettyWriteResponseFilter nettyWriteResponseFilter(GatewayProperties properties) {
        return new NettyWriteResponseFilter(properties.getStreamingMediaTypes());
    }

    @Bean
    public ReactorNettyWebSocketClient reactorNettyWebSocketClient(HttpClientProperties properties, HttpClient httpClient) {
        ReactorNettyWebSocketClient webSocketClient = new ReactorNettyWebSocketClient(httpClient);
        if (properties.getWebsocket().getMaxFramePayloadLength() != null) {
            webSocketClient.setMaxFramePayloadLength(properties.getWebsocket().getMaxFramePayloadLength());
        }
        webSocketClient.setHandlePing(properties.getWebsocket().isProxyPing());
        return webSocketClient;
    }
    
    // ======================================= 分割线 ======================================= //
    
    // 全局过滤器GlobalFilter
      @Bean
    @ConditionalOnEnabledGlobalFilter
    public RouteToRequestUrlFilter routeToRequestUrlFilter() {
      return new RouteToRequestUrlFilter();
    }

      @Bean
    @ConditionalOnEnabledGlobalFilter
    public ForwardRoutingFilter forwardRoutingFilter(
        ObjectProvider<DispatcherHandler> dispatcherHandler) {
      return new ForwardRoutingFilter(dispatcherHandler);
    }

    // gateway Filter
    @Bean
    public FilteringWebHandler filteringWebHandler(List<GlobalFilter> globalFilters) {
      return new FilteringWebHandler(globalFilters);
    }

    // 初始化gateway propeties
    @Bean
    public GatewayProperties gatewayProperties() {
      return new GatewayProperties();
    }

    // ======================================= 分割线 ======================================= //

    // 初始化GatewayFilterFactory, 有很多个喔
    @Bean
    @ConditionalOnEnabledFilter
    public PrefixPathGatewayFilterFactory prefixPathGatewayFilterFactory() {
      return new PrefixPathGatewayFilterFactory();
    }

    // Predicate Factory beans
    // 初始化PredicateFactory, 有很多个喔
    @Bean
    @ConditionalOnEnabledPredicate
    public AfterRoutePredicateFactory afterRoutePredicateFactory() {
      return new AfterRoutePredicateFactory();
    }
    
  	// ======================================= 分割线 ======================================= //
  
  	// 初始化RouteDefinitionLocator
  	@Bean
    @ConditionalOnMissingBean
    public PropertiesRouteDefinitionLocator propertiesRouteDefinitionLocator(GatewayProperties properties) {
      return new PropertiesRouteDefinitionLocator(properties);
    }

    @Bean
    @ConditionalOnMissingBean(RouteDefinitionRepository.class)
    public InMemoryRouteDefinitionRepository inMemoryRouteDefinitionRepository() {
      return new InMemoryRouteDefinitionRepository();
    }

    @Bean
    @Primary // 优先注入
    public RouteDefinitionLocator routeDefinitionLocator(List<RouteDefinitionLocator> routeDefinitionLocators) {
      return new CompositeRouteDefinitionLocator(Flux.fromIterable(routeDefinitionLocators));
    }
  
  	// ======================================= 分割线 ======================================= //

  	// 初始化RouteLocator
    @Bean
    public RouteLocator routeDefinitionRouteLocator(GatewayProperties properties,
                                                    List<GatewayFilterFactory> gatewayFilters,
                                                    List<RoutePredicateFactory> predicates,
                                                    RouteDefinitionLocator routeDefinitionLocator,
                                                    ConfigurationService configurationService) {
      return new RouteDefinitionRouteLocator(routeDefinitionLocator, predicates, gatewayFilters, properties, configurationService);
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(name = "cachedCompositeRouteLocator")
    // TODO: property to disable composite?
    public RouteLocator cachedCompositeRouteLocator(List<RouteLocator> routeLocators) {
      return new CachingRouteLocator(new CompositeRouteLocator(Flux.fromIterable(routeLocators)));
    }
  
  	// 初始化RoutePredicateHandlerMapping，用于查找匹配到Route，并进行处理
    @Bean
    public RoutePredicateHandlerMapping routePredicateHandlerMapping(
      FilteringWebHandler webHandler, RouteLocator routeLocator,
      GlobalCorsProperties globalCorsProperties, Environment environment) {
      return new RoutePredicateHandlerMapping(webHandler, routeLocator,
                                              globalCorsProperties, environment);
    }

    // ======================================= 分割线 ======================================= //
    
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass({ HystrixObservableCommand.class, RxReactiveStreams.class })
    protected static class HystrixConfiguration {

        @Bean
        @ConditionalOnEnabledFilter
        public HystrixGatewayFilterFactory hystrixGatewayFilterFactory(
            ObjectProvider<DispatcherHandler> dispatcherHandler) {
            return new HystrixGatewayFilterFactory(dispatcherHandler);
        }

        @Bean
        @ConditionalOnMissingBean(FallbackHeadersGatewayFilterFactory.class)
        @ConditionalOnEnabledFilter
        public FallbackHeadersGatewayFilterFactory fallbackHeadersGatewayFilterFactory() {
            return new FallbackHeadersGatewayFilterFactory();
        }

    }
}
```

初始化GatewayControllerEndpoint，提供管理网关的HTTP API。

```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(Health.class)
protected static class GatewayActuatorConfiguration {

   @Bean
   @ConditionalOnProperty(name = "spring.cloud.gateway.actuator.verbose.enabled",
         matchIfMissing = true)
   @ConditionalOnAvailableEndpoint
   public GatewayControllerEndpoint gatewayControllerEndpoint(
         List<GlobalFilter> globalFilters,
         List<GatewayFilterFactory> gatewayFilters,
         List<RoutePredicateFactory> routePredicates,
         RouteDefinitionWriter routeDefinitionWriter, RouteLocator routeLocator,
         RouteDefinitionLocator routeDefinitionLocator) {
      return new GatewayControllerEndpoint(globalFilters, gatewayFilters,
            routePredicates, routeDefinitionWriter, routeLocator,
            routeDefinitionLocator);
   }

   @Bean
   @Conditional(OnVerboseDisabledCondition.class)
   @ConditionalOnAvailableEndpoint
   public GatewayLegacyControllerEndpoint gatewayLegacyControllerEndpoint(
         RouteDefinitionLocator routeDefinitionLocator,
         List<GlobalFilter> globalFilters,
         List<GatewayFilterFactory> gatewayFilters,
         List<RoutePredicateFactory> routePredicates,
         RouteDefinitionWriter routeDefinitionWriter, RouteLocator routeLocator) {
      return new GatewayLegacyControllerEndpoint(routeDefinitionLocator,
            globalFilters, gatewayFilters, routePredicates, routeDefinitionWriter,
            routeLocator);
   }

}
```

### GatewayClassPathWarningAutoConfiguration

GatewayClassPathWarningAutoConfiguration在GatewayAutoConfiguration之前加载。作用主要是：检查依赖，检查项目是否正确导入 `spring-boot-starter-webflux` 依赖，而不是错误导入 `spring-boot-starter-web` 依赖。

```java
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(GatewayAutoConfiguration.class)
@ConditionalOnProperty(name = "spring.cloud.gateway.enabled", matchIfMissing = true)
public class GatewayClassPathWarningAutoConfiguration {

   // 引入了spring-boot-starter-web依赖，报错
   @Configuration(proxyBeanMethods = false)
   @ConditionalOnClass(name = "org.springframework.web.servlet.DispatcherServlet")
   @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
   protected static class SpringMvcFoundOnClasspathConfiguration {
      public SpringMvcFoundOnClasspathConfiguration() {
         throw new MvcFoundOnClasspathException();
      }
   }

   @Configuration(proxyBeanMethods = false)
   @ConditionalOnMissingClass("org.springframework.web.reactive.DispatcherHandler")
   protected static class WebfluxMissingFromClasspathConfiguration {
      public WebfluxMissingFromClasspathConfiguration() {}
   }

}
```

## 组件

### Route

具体路由信息载体。

```java
public class Route implements Ordered {
	// 标识符：区别于其他Route
  private final String id;
	// 路由指向目的地uri，即客户端请求最终被转发的目的地
  private final URI uri;
	// 用于多个Route之间的排序，数值越小排序越靠前，匹配优先级越高
  private final int order;
	// 断言，匹配该 Route 的前置条件，即满足相应的条件才会被路由到目的地 uri。
  private final AsyncPredicate<ServerWebExchange> predicate;
	// 适用路由的过滤器，用于处理切面逻辑，如路由转发前修改请求头等
  private final List<GatewayFilter> gatewayFilters;
	// 元数据
  private final Map<String, Object> metadata;
}
```

#### 构建Route

##### 配置文件

```yaml
spring:
  cloud:
    gateway: # ①
      routes: # ②
      - id: cookie_route # ③
        uri: http://example.org # ④
        predicates: # ⑤
        - Cookie=chocolate, ch.p # ⑥
        filters: # ⑦ 
        - AddRequestHeader=X-Request-Foo, Bar # ⑧
```

1. "spring.cloud.gateway" 为固定前缀；
2. 定义路由信息列表，即可定义多个路由；
3. 声明了一个 id 为 "cookie_route" 的路由；
4. 定义了路由的目的地 uri，即请求转发的目的地；
5. 声明 predicates，即请求满足相应的条件才能匹配成功；
6. 定义了一个 Predicate，当名称为 `chocolate` 的 Cookie  的值匹配`ch.p`时 Predicate 才能够匹配，它由 CookieRoutePredicateFactory 来生产；
7. 声明 filters，即路由转发前后处理的过滤器；
8. 定义了一个 Filter，所有的请求转发至下游服务时会添加请求头 `X-Request-Foo:Bar` ，由AddRequestHeaderGatewayFilterFactory 来生产。

![](https://mmbiz.qpic.cn/mmbiz_png/J1WBDkullftbic7xjLSayfM5hkMRQj4kx8XicB584Rdr5cqCicMHIoVUTiaicIBNLgwU49pSL6JEzsLpeBm1u8d7HEA/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

##### 编程方式

```java
// static imports from GatewayFilters and RoutePredicates
@Bean
public RouteLocator customRouteLocator(RouteLocatorBuilder builder) { // ①
  return builder.routes() // ②
    .route(r -> r.host("**.abc.org").and().path("/image/png") // ③
           .filters(f ->
                    f.addResponseHeader("X-TestHeader", "foobar")) // ④
           .uri("http://httpbin.org:80") // ⑤
          )
    .build();
}
```

1. RouteLocatorBuilder bean 在 spring-cloud-starter-gateway 模块自动装配类中已经声明，可直接使用。RouteLocator 封装了对 Route 获取的定义，可简单理解成工厂模；
2. RouteLocatorBuilder 可以构建多个路由信息；
3. 指定了 Predicates，这里包含两个：
   1. 请求头`Host`需要匹配`**.abc.org`，通过 HostRoutePredicateFactory 产生。
   2. 请求路径需要匹配`/image/png`，通过 PathRoutePredicateFactory 产生。
4. 指定了一个 Filter，下游服务响应后添加响应头`X-TestHeader:foobar`，通过AddResponseHeaderGatewayFilterFactory 产生；
5. 指定路由转发的目的地 uri。

### AsyncPredicate

Predicate 即 Route 中所定义的部分，用于条件匹配。

```java
public interface AsyncPredicate<T> extends Function<T, Publisher<Boolean>> {
	// 与操作，同时满足两个predicate
  default AsyncPredicate<T> and(AsyncPredicate<? super T> other) {
    return new AndAsyncPredicate<>(this, other);
  }
	// 取反操作，对predicate匹配结果取反
  default AsyncPredicate<T> negate() {
    return new NegateAsyncPredicate<>(this);
  }
	// 或操作，即两个predicate满足其中一个即可
  default AsyncPredicate<T> not(AsyncPredicate<? super T> other) {
    return new NegateAsyncPredicate<>(other);
  }
	// 非操作，即满足predicate，但与另外的predicate匹配结果相反
  default AsyncPredicate<T> or(AsyncPredicate<? super T> other) {
    return new OrAsyncPredicate<>(this, other);
  }
}
```

### GatewayFilter

Filter 最终是通过 filter chain 来形成链式调用的，每个 filter 处理完 pre filter 逻辑后，调用方法`chain.filter(exchange)`，将 exchange 委派给  filterChain，filterChain 再委派给下一下 filter。

```java
public interface GatewayFilter extends ShortcutConfigurable {

   String NAME_KEY = "name";
   String VALUE_KEY = "value";

   /**
    * Process the Web request and (optionally) delegate to the next {@code WebFilter}
    * through the given {@link GatewayFilterChain}.
    * @param exchange the current server exchange
    * @param chain provides a way to delegate to the next filter
    * @return {@code Mono<Void>} to indicate when request processing is complete
    */
   Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain);

}
```

### GatewayFilterChain

- GatewayFilterChain接口

```java
public interface GatewayFilterChain {

   /**
    * Delegate to the next {@code WebFilter} in the chain.
    * @param exchange the current server exchange
    * @return {@code Mono<Void>} to indicate when request handling is complete
    */
   Mono<Void> filter(ServerWebExchange exchange);

}
```

- GatewayFilterChain实现方法

```java
private static class DefaultGatewayFilterChain implements GatewayFilterChain {
  private final int index;
  private final List<GatewayFilter> filters;

  DefaultGatewayFilterChain(List<GatewayFilter> filters) {
    this.filters = filters;
    this.index = 0;
  }

  private DefaultGatewayFilterChain(DefaultGatewayFilterChain parent, int index) {
    this.filters = parent.getFilters();
    this.index = index;
  }

  public List<GatewayFilter> getFilters() {
    return filters;
  }

	// filters在容器启动时注入
  // 每次调用filter方法之后index + 1，获取下一个filter进行过滤
  @Override
  public Mono<Void> filter(ServerWebExchange exchange) {
    return Mono.defer(() -> {
      if (this.index < filters.size()) {
        GatewayFilter filter = filters.get(this.index);
        DefaultGatewayFilterChain chain = new DefaultGatewayFilterChain(this,
                                                                        this.index + 1);
        return filter.filter(exchange, chain);
      }
      else {
        return Mono.empty(); // complete
      }
    });
  }
}
```





























