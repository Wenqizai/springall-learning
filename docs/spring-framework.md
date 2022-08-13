## just for interview?

### Spring核心模块

- spring-core：Spring基础API模块，如资源管理，泛型处理
- spring-beans：Spring Bean相关，如依赖查找，依赖注入
- spring-aop：Spring AOP处理，如动态代理，AOP字节码提升
- spring-context：事件驱动、注解驱动、模块驱动等
- spring-expression：Spring表达式语言模块

### IOC



## IOC

### 关注点

依赖查找？依赖注入？

### 实现技术

wiki关于IOC容器实现的技术手段：https://en.wikipedia.org/wiki/Inversion_of_control

```markdown
1. Using a service locator pattern 
2. Using dependency injection; for example,
	- Constructor injection
	-	Parameter injection
	- Setter injection
	- Interface injection
	- Method Injection
3. Using a contextualized lookup *上下文查找
4. Using the template method design pattern
5. Using the strategy design pattern
```

- Java SE：Java Beans、Java ServiceLoader SPI、JNDI(Java Naming and Directory interface)
- Java EE: EJB（Enterprise Java Beans）、Servlet
- 开源框架
  - Apache Avalon
  - Picocontainer
  - Google Guice
  - Spring Framework

### 职责

- 依赖处理：依赖查找、依赖注入
- 生命周期管理：容器的生命周期、托管的资源（Java Beans或其他资源）
- 配置：容器、外部化配置、托管的资源（Java Beans或其他资源）

### 依赖

> 依赖查找 vs 依赖注入

| 类型     | 依赖处理 | 实现便利性 | 代码侵入行   | API依赖性      | 可读性 |
| -------- | -------- | ---------- | ------------ | -------------- | ------ |
| 依赖查找 | 主动获取 | 相对繁琐   | 侵入业务逻辑 | 依赖容器 API   | 良好   |
| 依赖注入 | 被动提供 | 相对便利   | 低侵入性     | 不依赖容器 API | 一般   |

> 构造器注入 or Setter注入

构造器注入：程序发布， Bean实例化之后状态不可修改， 不允许循环依赖的产生。

Setting注入：程序发布，Bean实例化之后可以再次Setter，修改Bean状态。

谁好谁坏，见仁见智。

## Spring IOC

### 依赖来源

1. 自定义的Bean
2. 容器内建Bean对象
3. 容器内建依赖

打破我们对于Bean来源的刻板影响，Bean来源不单单只有我们自定义的Bean。

### 依赖查找

1. 根据Bean名称查找：实时查找、延时查找；
2. 根据Bean类型查找：单个Bean对象、集合Bean对象；
3. 根据Bean名称 + 类型查找；
4. 根据Java注解查找：单个Bean对象、集合Bean对象。

demo：com.wenqi.ioc.overview.dependency.lookup.DependencyLookupDemo

### 依赖注入

依赖查找相对繁琐，而且对代码有一定的侵入性，而依赖注入可以补齐这些缺点。

1. 根据Bean名称注入；
2. 根据Bean类型注入：单个Bean对象、集合Bean对象；
3. 注入容器内构建Bean对象；
4. 注入非Bean对象
5. 注入类型：实时注入、延迟注入。

demo：com.wenqi.ioc.overview.dependency.injection.DependencyInjectionDemo

### 配置元信息

通过配置元信息，可以影响容器内的行为。

> 如何配置元信息？

1. 通过Bean定义配置
   - 基于XML文件、基于Properties文件、基于Java注解、基于Java API（专题讨论）
2. IOC容器配置
   - 基于XML文件、基于Java注解、基于Java API（专题讨论）
3. 外部化属性配置
   - 基于Java注解

### BeanFactory | ApplicationContext

