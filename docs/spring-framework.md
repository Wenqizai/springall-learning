## just for interview?

### Spring核心模块

- spring-core：Spring基础API模块，如资源管理，泛型处理
- spring-beans：Spring Bean相关，如依赖查找，依赖注入
- spring-aop：Spring AOP处理，如动态代理，AOP字节码提升
- spring-context：事件驱动、注解驱动、模块驱动等
- spring-expression：Spring表达式语言模块

### IOC

> IOC容器是什么?

IOC控制反转，我们把对象的生命周期都交给IOC来管理。其中，IOC重要的特性就是依赖注入（DI）、依赖查找。

> BeanFactory 与 FactoryBean区别？

BeanFactory是IOC底层的容器；

FactoryBean是创建Bean的一种方式，帮助实现负载的初始化逻辑。

1. 如何使用，ApplicationContext BenFactory选哪一个？

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

> Spring 文档解析

The [`BeanFactory`](https://docs.spring.io/spring-framework/docs/5.2.23.BUILD-SNAPSHOT/javadoc-api/org/springframework/beans/factory/BeanFactory.html) interface provides an advanced configuration mechanism capable of managing any type of object. [`ApplicationContext`](https://docs.spring.io/spring-framework/docs/5.2.23.BUILD-SNAPSHOT/javadoc-api/org/springframework/context/ApplicationContext.html) is a sub-interface of `BeanFactory`. It adds:

- Easier integration with Spring’s AOP features
- Message resource handling (for use in internationalization)
- Event publication
- Application-layer specific contexts such as the `WebApplicationContext` for use in web applications.

In short, the `BeanFactory` provides the configuration framework and basic functionality, and the `ApplicationContext` adds more enterprise-specific functionality. The `ApplicationContext` is a complete superset of the `BeanFactory` and is used exclusively in this chapter in descriptions of Spring’s IoC container.

简单来说：`BeanFactory`和`ApplicationContext`都是IOC容器，`BeanFactory`接口提供容器的基本功能，是一个更加底层的容器。而`ApplicationContext`继承于`BeanFactory`，拥有`BeanFactory`的所有的特性，有提供了一些更多的企业的特性，如AOP，国际化，事件发布，容器上下文等。因此`ApplicationContext`是`Beanfactory`的超集（注意不是超类）。

> ApplicationContext支持的一些特性

- 面向切面（AOP）
- 配置元信息（Configuration Metadata）
- 资源管理（Resources）
- 事件（Event）
- 国际化（i18n）
- 注解（Annotations）
- Environment 抽象（Enviroment Abstraction）

https://docs.spring.io/spring-framework/docs/5.2.23.BUILD-SNAPSHOT/spring-framework-reference/core.html#context-introduction

> 如何使用，ApplicationContext BenFactory选哪一个？

com.wenqi.ioc.overview.container.BeanFactoryAsIocContainerDemo

com.wenqi.ioc.overview.container.AnnotationApplicationContextAsIocContainerDemo

### IOC生命周期

> 容器启动

org.springframework.context.support.AbstractApplicationContext#refresh

- ioc启动都做了哪些准备？
  - IOC配置元信息读取和解析
  - IOC容器生命周期
  - Spring事件发布
  - 国际化

> 容器关闭

org.springframework.context.support.AbstractApplicationContext#close

## Spring Bean

> 路线

定义Spring Bean -> BeanDefinition -> 命名Spring Bean -> Spring Bean的别名 -> 注册Spring Bean -> 实例化Spring Bean -> 初始化Spring Bean -> 延迟初始化Spring Bean -> 销毁Spring Bean -> 垃圾回收Spring Bean 

### BeanDefinition

#### 是什么？

org.springframework.beans.factory.config.BeanDefinition

BeanDefinition用来定义Bean的配置元信息接口，其中包括：

- Bean的类名
- Bean行为配置元素，如作用域、自动绑定的模式、生命周期回调等
- 其他Bean引用，有可称合作者（Collaborators）或者依赖（Dependencies）
- 配置设置，如Bean属性（Properties）

#### 元信息

BeanDefintion包含的元信息：

| 属性（Property）        | 说明                                         |
| ----------------------- | -------------------------------------------- |
| Class                   | Bean全类名，必须是具体类，不能用抽象类或接口 |
| Name                    | Bean的名称或者ID                             |
| Scope                   | Bean的作用域（如：singleton、prototype）     |
| Constructor arguments   | Bean构造器参数（用于依赖注入）               |
| Properties              | Bean的属性设置（用于依赖注入）               |
| Autowiring mode         | Bean的自动绑定模式（如：通过名称byName）     |
| Lazy initalization mode | Bean延迟初始化模式（延迟和非延迟）           |
| Initialization method   | Bean初始化回调方法名称                       |
| Destruction method      | Bean销毁回调方法名称                         |

#### 构建方式

com.wenqi.spring.bean.definition.BeanDefinitionCreationDemo

1. 通过BeanDefinitionBuilder
2. 通过AbstractBeanDefinition以及派生类

#### Spring Bean 命名

> Bean 名称

每个Bean拥有一个或者多个标识符（identifiers），这些标识符在 Bean 所在的容器必须是唯一的。通常一个Bean仅有一个标识符，如果需要额外的，可考虑使用别名（Alias）扩充。

在基于XML的配置元信息重，开发人员可用id或者name属性来规定 Bean 的标识符。通常 Bean 的标识符由字母组成，允许出现特殊字符。如果要想引入 Bean 的别名的话，可在 name属性使用半角逗号（“ , ”）或分号（“ ; ”）来间隔。

Bean的 id 或 name 属性并非必须指定，如果留空的话，容器会为Bean自动生成一个唯一的名称。 Bean 的命名尽管没有限制，官方建议使用驼峰命名方式。

> Bean 名称生成器

Bean 名称生成器 BeanNameGenerator，由Spring Framework 2.0.3 引入，框架内建两种实现：

- DeafaultBeanNameGenerator：默认通用 BeanNameGenerator 实现；
- AnnotationBeanNameGenerator：基于注解扫描的 BeanNameGenerator 实现，起始于 Spring Framework 2.5 

#### Spring Bean 别名

com.wenqi.spring.bean.definition.BeanAliasDemo

有时候，我们会引入第三方的jar包中的bean。如果我们不想沿用第三方原始的bean名称进行依赖查找和依赖注入。我们可以通过起别名的方式，建立别名与原始的bean的映射关系。这样就可以通过别名来进行依赖查找和依赖注入，此时`alias bean == origin bean`。

> Bean 别名（Alias）的价值

1. 复用现有的 BeanDefinition
2. 更具有场景化的命名方法，如：

```xml
<alias name="myApp-dataSource"alias="subsystemA-dataSource"/>
<alias name="myApp-dataSource"alias="subsystemB-dataSource"/>
```

#### Spring Bean 注册

> BeanDefinition 注册

1. XML 配置元信息
   - 《bean name="..."/>
2. Java 注解配置元信息：com.wenqi.spring.bean.definition.AnnotationBeanDefinitionDemo
   - @Bean
   - @Component
   - @Import
3. Java API 配置元信息
   - 命名方式：org.springframework.beans.factory.support.BeanDefinitionRegistry#registerBeanDefinition
   - 非命名方式：org.springframework.beans.factory.support.BeanDefinitionReaderUtils#registerWithGeneratedName
   - 配置类方式：org.springframework.context.annotation.AnnotatedBeanDefinitionReader#register

#### Bean 实例化

Instantiation 的两种方式：

1. 常规方式：com.wenqi.spring.bean.definition.BeanInstantiationDemo
   - 通过构造器（配置元信息：XML、Java 注解和 Java API）
   - 通过静态工厂方法（配置元信息：XML 和 Java API）
   - 通过 Bean 工厂方法（配置元信息：XML和 Java API）
   - 通过 FactoryBean （配置元信息：XML、Java 注解和 Java API）
2. 特殊方式：com.wenqi.spring.bean.definition.SpecialBeanInstantiationDemo
   - 通过 ServiceLoaderFactoryBean （配置元信息：XML、Java 注解 和 Java API）
   - 通过 org.springframework.beans.factory.config.AutowireCapableBeanFactory#createBean(java.lang.Class<?>, int, boolean)
   - 通过 org.springframework.beans.factory.support.BeanDefinitionRegistry#registerBeanDefinition

#### Bean 初始化

com.wenqi.spring.bean.definition.BeanInitializationDemo

> Bean 初始化方式（Initialization）

- @PostConstruct 标注方法
- 实现 InitializingBean 接口的 afterPropertiesSet() 方法
- 自定义初始化方法 initMethod
  - XML 配置：`<bean init-method = "init" ... />`
  - Java 注解：`@Bean(initMethod = "init")`
  - Java API：`AbstractBeanDefinition#setInitMethodName(String)`

**思考：**假设以上三种方式均在同一个 Bean 中定义，那么这些方法的执行顺序是怎样的？

顺序（任何Spring版本）：`@PostConstruct` -> `InitializingBean#afterPropertiesSet()` -> 自定义初始化方法 initMethod

> Bean 延迟初始化 （Lazy Initialization）

- XML 配置 `<bean lazy-init = 'true' ... />`
- Java 注解：`@Lazy(true)`

**思考：**当某个 Bean 定义为延迟初始化，那么 Spring 容器返回的对象与非延迟的对象存在怎样的差异？

1. 添加 `@Lazy`

```txt
Spring 应用上下文已启动 ...
@PostConstruct: UserFactory 初始化中 ... 
InitializingBean#afterPropertiesSet(): UserFactory 初始化中 ... 
自定义初始化方法 initUserFactory(): UserFactory 初始化中 ... 
com.wenqi.spring.bean.factory.DefaultUserFactory@38364841
```

2. 去掉 `@Lazy`

```txt
@PostConstruct: UserFactory 初始化中 ... 
InitializingBean#afterPropertiesSet(): UserFactory 初始化中 ... 
自定义初始化方法 initUserFactory(): UserFactory 初始化中 ... 
Spring 应用上下文已启动 ...
com.wenqi.spring.bean.factory.DefaultUserFactory@641147d0
```

**意味者：**非延迟初始化在 ==Spring 应用上下文启动完成后（`applicationContext.refresh()`）==, 进行初始化。而延迟初始化是==依赖查找时触发==Bean的初始化。



















