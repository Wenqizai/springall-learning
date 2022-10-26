# IOC

## 概念

### IOC

#### 注入方式

- **构造方法注入**

对象构造完成后，Bean 就可以被使用。此时 final 的作用就显现出来了。

访问 final 属性时，必须等待整个对象构造完成方可访问。

```java
private final IFXNewsListener newsListener;
private final IFXNewsPersister newsPersister; 

public FXNewsProvider(IFXNewsListener newsListner, IFXNewsPersister newsPersister) {
    this.newsListener = newsListner;
    this.newPersistener = newsPersister;
}
```

- **setter 方法注入**

setter 方法不用等待对象构造完成就可使用，相对宽松一些。

```java
private IFXNewsListener newsListener;
private IFXNewsPersister newPersistener;

public IFXNewsListener getNewsListener() {
	return newsListener;
}

public void setNewsListener(IFXNewsListener newsListener) {
	this.newsListener = newsListener;
}

public IFXNewsPersister getNewPersistener() {
	return newPersistener;
}

public void setNewPersistener(IFXNewsPersister newPersistener) {
	this.newPersistener = newPersistener;
}
```

- **接口注入**

接口注入比较死板和烦琐。如果需要注入依赖对象，被注入对象就必须声明和实现另外的接口。

- **三种注入方式的优缺点比较**

|      | 接口注入                                                     | 构造方法注入                                                 | setter 方法注入                                              |
| ---- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 优点 | 无，不提倡使用                                               | 对象构造完成之后方可使用，可避免线程安全问题                 | 1. 方法可以命名，所以setter方法注入在描述性上要比构造方法注入好一些；<br />2. setter方法可以被继承，允许设置默认值。 |
| 缺点 | 强制被注入对象实现不必要的接口，带有侵入性；<br />而构造方法注入和 setter 方法注入则不需要如此。 | 1. 当依赖对象比较多的时候，构造方法的参数列表会比较长；<br />2. 构造方法无法被继承，无法设置默认值；<br />3. 通过反射构造对象的时候，对相同类型的参数的处理会比较困难。 | 对象无法在构造完成后马上进入就绪状态。容易引发线程安全问题   |

综上所述，构造方法注入和setter方法注入因为其侵入性较弱，且易于理解和使用，所以是现在使用最多的注入方式；而接口注入因为侵入性较强，近年来已经不流行了。

#### 依赖管理

Ioc Service Provider不是人类，也就不能像酒吧服务生那样通过大脑来记忆和存储所有的相关信息。所以，它需要寻求其他方式来**记录**诸多对象之间的对应关系（依赖关系）。

Ioc 记录依赖关系的方式：

- **直接编码（硬编码）**

通过编码形式，绑定对象的关系信息，并将对象依赖注入到 Ioc 中。

```java
IoContainer container = ...;
// 似曾相识的 register
container.register(FXNewsProvider.class,new FXNewsProvider());
container.register(IFXNewsListener.class,new DowJonesNewsListener());

// 通过bind方法将“被注入对象”（由IFXNewsListenerCallable接口添加标志）所依赖的对象，
container.bind(IFXNewsListenerCallable.class, container.get(IFXNewsListener.class));

FXNewsProvider newsProvider = (FXNewsProvider)container.get(FXNewsProvider.class);
newProvider.getAndPersistNews();
```

- **配置文件方式（properties、XML）**

1. 编写 XML 文件

```xml
<bean id="newsProvider" class="..FXNewsProvider">
    <property name="newsListener">
        <ref bean="djNewsListener"/>
    </property>
    <property name="newPersistener">
        <ref bean="djNewsPersister"/>
    </property>
</bean>
<bean id="djNewsListener"
      class="..impl.DowJonesNewsListener">
</bean>
<bean id="djNewsPersister"
      class="..impl.DowJonesNewsPersister">
</bean>
```

2. 读取 XML 文件，从容器中获取组装完成的对象。

```java
container.readConfigurationFiles(...);
FXNewsProvider newsProvider = (FXNewsProvider)container.getBean("newsProvider");
newsProvider.getAndPersistNews();
```

- **元数据方式**

这种方式的代表实现是 Google Guice，这是 Bob Lee在 Java 5 的注解和 Generic 的基础上开发的一套 IoC 框架。我们可以直接在类中使用元数据信息来标注各个对象之间的依赖关系，然后由 Guice 框架根据这些注解所提供的信息将这些对象组装后，交给客户端对象使用。

```java
public class FXNewsProvider {
    private IFXNewsListener newsListener;
    private IFXNewsPersister newPersistener;
    
    // 通过 @Inject，我们指明需要 IoC Service Provider 通过构造方法注入方式
    @Inject
    public FXNewsProvider(IFXNewsListener listener,IFXNewsPersister persister) {
        this.newsListener = listener;
        this.newPersistener = persister;
    }
}

Injector injector = Guice.createInjector(new NewsBindingModule());
FXNewsProvider newsProvider = injector.getInstance(FXNewsProvider.class);
newsProvider.getAndPersistNews();
```

### Spring IOC

Spring 的 IOC 容器是一个提供 IOC 支持的轻量级容器，除了基本的 IOC 支持之外, Spring IOC 还提供了 AOP 框架支持、企业级服务集成等服务。



<img src="Spring揭秘.assets/SpringIOC与IOC的关系.png" alt="SpringIOC与IOC的关系" style="zoom: 80%;" />



Spring IOC 提供两种容器类型：`BeanFactory`和`ApplicationContext`。

- **`BeanFactory`**
  1. 基础类型IOC容器，完整的IOC服务支持;
  2. 无指定时，默认延时初始化策略（lazy-load）;
  3. 也就是说Bean被访问时才对Bean进行初始化和依赖注入操作，容器开始启动较快。对于资源有限，并且功能要求不是很严格的场景，`BeanFactory`是比较合适的IOC容器选择。

- **`ApplicationContext`**
  1. `ApplicationContext`在`BeanFactory`基础上构建，提供完整的**`BeanFactory`**功能之外，还提供其他高级特性，如AOP，国际化，事件发布，容器上下文等；
  1. 默认容器启动时完成Bean的全部初始化和绑定；
  1. `ApplicationContext`比`BeanFactory`要求更多的系统资源，如果系统资源充足，`ApplicationContext`是比较合适的IOC容器选择。

-  `ApplicationContext` 与 `BeanFactory` 的类图

![ApplicationContext与BeanFactory的关系](Spring揭秘.assets/ApplicationContext与BeanFactory的关系.png)

可以看到  `ApplicationContext` 继承于 `BeanFactory`，并且比 `BeanFactory` 多继承了一些接口，所以 `ApplicationContext` 功能丰富于 `BeanFactory`。

#### BeanFactory

- 未拥有BeanFactory之前，获取Bean方式

```java
// 1. 设计FXNewsProvider类用于普遍的新闻处理 (容器，管理Bean和依赖)
public class FXNewsProvider {
    private IFXNewsListener newsListener;
    private IFXNewsPersister newPersistener;

    public FXNewsProvider(IFXNewsListener listener,IFXNewsPersister persister) {
        this.newsListener = listener;
        this.newPersistener = persister;
    }
}

// 2. 设计IFXNewsListener接口抽象各个新闻社不同的新闻获取方式，并给出相应实现类 (Bean的接口和实现类)
public interface IFXNewsListener {}
public class DowJonesNewsListener implements IFXNewsListener {}

// 3. 设计IFXNewsPersister接口抽象不同数据访问方式，并实现相应的实现类 (Bean的接口和实现类)
public interface IFXNewsPersister {}
public class DowJonesNewsPersister implements IFXNewsPersister {}

// 4. 实例化Bean
FXNewsProvider newsProvider = new FXNewsProvider();
newsProvider.getAndPersistNews();
```

- 拥有BeanFactory之后，获取Bean方式

1. 编写XML文件（或注解形式）

```xml
<beans>
    <bean id="djNewsProvider" class="..FXNewsProvider">
        <constructor-arg index="0">
            <ref bean="djNewsListener"/>
        </constructor-arg>
        <constructor-arg index="1">
            <ref bean="djNewsPersister"/>
        </constructor-arg>
    </bean>
</beans>
```

2. 获取Bean方式

```java
// 方式一
BeanFactory container = new XmlBeanFactory(new ClassPathResource("配置文件路径"));
FXNewsProvider newsProvider = (FXNewsProvider)container.getBean("djNewsProvider");
newsProvider.getAndPersistNews();

// 方式二
ApplicationContext container = new ClassPathXmlApplicationContext("配置文件路径");
FXNewsProvider newsProvider = (FXNewsProvider)container.getBean("djNewsProvider");
newsProvider.getAndPersistNews();

// 方式三
ApplicationContext container = new FileSystemXmlApplicationContext("配置文件路径");
FXNewsProvider newsProvider = (FXNewsProvider)container.getBean("djNewsProvider");
newsProvider.getAndPersistNews();
```

##### 注册与绑定方式



![image-20221024144110698](Spring揭秘.assets/BeanFactory,BeanDefinitionRegistry和DefaultListableBeanFactory的关系.png)



- **BeanFactory**：定义访问Bean的方式

- **BeanDefinitionRegistry**：担当Bean注册管理的角色

- **DefaultListableBeanFactory**：实现了`BeanFactory`和`BeanDefinitionRegistry`，继承他们的所有功能。

`BeanFactory`只是一个接口，我们最终需要一个该接口的实现来进行实际的Bean的管理，因此不同的`BeanFactory`实现类，承担不同的功能。

每一个受管的对象（Bean），在容器中都会有一个BeanDefinition的实例（instance）与之相对应，<u>该BeanDefinition的实例负责保存对象的所有必要信息，包括其对应的对象的class类型、是否是抽象类、构造方法参数以及其他属性等</u>。当客户端向BeanFactory请求相应对象的时候，BeanFactory通过这些信息为客户端返回一个完备可用的对象实例。

###### 1 直接编码方式

`com.wenqi.springioc.beanfactory.HardCodeRegisterAndBind`

###### 2 外部配置文件方式

Spring IOC支持两种配置文件格式：Properties和XML文件格式 (当然可以自定义文件格式，前提是需要)。

Spring IOC也提供了一个统一的配置文件处理方式：根据不同的配置文件格式，获取对应的`BeanDefinitionReader`实现类（如：`PropertiesBeanDefinitionReader`、`XmlBeanDefinitionReader`），由`BeanDefinitionReader`实现类负责将相应的配置文件内容读取并映射到`BeanDefinition`，然后将映射后的`BeanDefinition`注册到`BeanDefinitionRegistry`，之后`BeanDefinitionRegistry`完成Bean的注册和加载。对于`BeanDefinitionRegistry`来说，它只不过负责保管而已。

> Properties配置格式的加载

文件加载类：`org.springframework.beans.factory.support.PropertiesBeanDefinitionReader`

test：`com.wenqi.springioc.beanfactory.PropertiesRegisterAndBind`

> XML配置格式的加载

XML配置格式是Spring支持最完整，功能最强大的表达方式。当然，一方面这得益于XML良好的语意表达能力；另一方面，就是Spring框架从开始就自始至终保持XML配置加载的统一性

文件加载类：`org.springframework.beans.factory.xml.XmlBeanDefinitionReader`

test：`com.wenqi.springioc.beanfactory.XMLRegisterAndBind`

###### 3. 注解方式

编写配置类或配置的xml文件，扫描注解，注册/加载Bean。

test：`com.wenqi.springioc.beanfactory.AnnotationRegisterAndBind`

#### XML

> 声明方式

- 1. Spring 2.0 版本之前基于DTD文档声明

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN" 
	"http://www.springframework.org/dtd/spring-beans.dtd">
<beans>
    
</beans>
```

- 2. Spring 2.0版本之后以继续使用DTD方式的DOCTYPE进行配置文件格式的限定，又引入了基于XML Schema的文档声明。

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:util="http://www.springframework.org/schema/util" 
       xmlns:jee="http://www.springframework.org/schema/jee" 
       xmlns:lang="http://www.springframework.org/schema/lang" 
       xmlns:aop="http://www.springframework.org/schema/aop" 
       xmlns:tx="http://www.springframework.org/schema/tx" 
       xsi:schemaLocation="http://www.springframework.org/schema/beans 
                           http://www.springframework.org/schema/beans/spring-beans-2.0.xsd 
                           http://www.springframework.org/schema/util 
                           http://www.springframework.org/schema/util/spring-util-2.0.xsd 
                           http://www.springframework.org/schema/jee 
                           http://www.springframework.org/schema/jee/spring-jee-2.0.xsd 
                           http://www.springframework.org/schema/lang 
                           http://www.springframework.org/schema/lang/spring-lang-2.0.xsd 
                           http://www.springframework.org/schema/aop 
                           http://www.springframework.org/schema/aop/spring-aop-2.0.xsd
                           http://www.springframework.org/schema/tx
                           http://www.springframework.org/schema/tx/spring-tx-2.0.xsd">
</beans>
```



##### beans

`<beans>` 是XML配置文件中最顶层的元素，它下面可以包含0或者1个 `<description>` 和多个 `<bean>` 以及 `<import>` 或者 `<alias>`。

![image-20221025140619156](Spring揭秘.assets/4.3标签beans与下层元素关系.png)

> `<beans>`的属性值

`<beans>`标签属性，可以提取`<bean>`标签中的重合的设置，减少每个<bean>中指定的不必要工作。

1. **default-lazy-init**：默认false，是否对所有的 `<bean>` 进行延迟初始化；
2. **default-autowire：**可以取值为`no`, `byName`, `byType`, `constructor`以及`autodetect`。默认值为`no`，如果使用自动绑定的话，用来指定全体bean使用哪一种默认绑定方式；
3. **default-dependency-check**：可以取值`none`，`objects`，`simple`以及`all`。，默认值为none，即不做依赖检查；
4. **default-init-method：**如果`<bean>`中有相同的初始化方法，那么可以提取到`<beans>`中，统一指定方法；
5. **default-destroy-method：**如果`<bean>`中有相同的对象销毁方法，那么可以提取到`<beans>`中，统一指定方法；

> description/import/alias

通常情况下，这几个元素位于`<beans>`的下级，都不是必须的，仅作了解。

- **description**

**`<description>`**：在配置的文件中指定一些描述性的信息。通常情况下，该元素是省略的。

- **import**

通常情况下，可以根据模块功能或者层次关系，将配置信息分门别类地放到多个配置文件中。在想加载主要配置文件，并将主要配置文件所依赖的配置文件同时加载时，可以在这个主要的配置文件中通过`<import>`元素对其所依赖的配置文件进行引用。

即A.xml的bean引用了B.xml的bean，文件间相互引用，可以使用`<import>`进行引用。

```xml
<import resource="B.xml"/>
```

- **alias**

<bean>起别名。

```xml
<alias name="djNewsListener" alias="/news/djNewsListener"/>
<alias name="djNewsListener" alias="dowJonesNewsListener"/>
```

##### bean

```xml
<bean id="djNewsListener" 
      name="/news/djNewsListener,dowJonesNewsListener"
      class="..impl.DowJonesNewsListener">
</bean>
```

###### bean的属性

- **id**

每个注册到容器里的对象都需要唯一标识来区分开其他的bean，id属性来指定当前注册对象的beanName是什么。

并非任何情况下都需要指定每个`<bean>`的id，有些情况下，id可以省略，比如后面会提到的内部`<bean>`以及不需要根据
beanName明确依赖关系的场合等。

除了id指定标识外，使用name属性来指定`<bean>`的别名（alias）。与id属性相比，name属性的灵活之处在于，name可以使用id不能使用的一些字符，比如/。而且还可以通过逗号、空格或者冒号分割指定多个name。name的作用跟`<alias>`标签作用相同。

- **class**

每个注册到容器的对象都需要通过`<bean>`元素的class属性指定其类型，否则，容器可不知道这个对象到底是何方神圣。在大部分情况下，<u>该属性是必须的</u>。仅在少数情况下不需要指定，如后面将提到的在使用抽象配置模板的情况下

###### 相互依赖的bean

更多时候，Bean之间是相互依赖，共同协作构建的。下面看一下Spring的IoC容器的XML配置中，应该如何表达这种依赖性。

> 构建方法注入

```xml
<bean id="djNewsProvider" class="..FXNewsProvider">
    <constructor-arg>
        <ref bean="djNewsListener"/>
    </constructor-arg>
    <constructor-arg>
        <ref bean="djNewsPersister"/>
    </constructor-arg>
</bean>

<!-- 另一种表达方式 -->
<bean id="djNewsProvider" class="..FXNewsProvider">
    <constructor-arg ref="djNewsListener"/>
    <constructor-arg ref="djNewsPersister"/>
</bean>
```

通过构造方法注入依赖对象时，可以使用标签`<constructor-arg>`。使用`<ref>`来指明容器将要引入的Bean实例。

> 关于`<constructor-arg>`

有的时候对象具有多个构造方法， 仅仅使用`<constructor-arg ref="djNewsListener"/>`是无法准确找到对应的构造方法（寻找构造方法时，仅会找第一个符合的构造方法来注入）。这时需要引入`<constructor-arg/>`的type属性，用来标注特定的构造方法。

test：`com.wenqi.springioc.xml.ConstructArgInjectXmlTest`

- 指定type属性
- 指定index属性





