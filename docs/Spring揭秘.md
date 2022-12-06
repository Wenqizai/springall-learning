#  IOC

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

<!-- 关于<constructor-arg> -->

有的时候对象具有多个构造方法， 仅仅使用`<constructor-arg ref="djNewsListener"/>`是无法准确找到对应的构造方法（寻找构造方法时，仅会找第一个符合的构造方法来注入）。这时需要引入`<constructor-arg/>`的type属性，用来标注特定的构造方法。

test：`com.wenqi.springioc.xml.ConstructArgInjectXmlTest`

- 指定type属性
- 指定index属性

> setter方法注入

通过setter方法注入依赖对象时，可以使用标签`<property>`。``<property>``提供一个name属性，用来指定注入Bean对应的实例变量名。之后通过`<ref>`或`<value>`来指定具体依赖的对象或值。

其中`<constructor-arg>`和`<property>`可以同时配合使用。

```xml
<bean id="djNewsProvider" class="..FXNewsProvider">
    <property name="newsListener">
        <ref bean="djNewsListener"/>
    </property>
    <property name="newPersistener">
        <ref bean="djNewsPersister"/>
    </property>
</bean>

<!-- 另一种表达形式 -->
<bean id="djNewsProvider" class="..FXNewsProvider">
    <property name="newsListener" ref="djNewsListener"/>
    <property name="newPersistener" ref="djNewsPersister"/>
</bean>
```

> `<property>`和`<constructor-arg>`中可用的配置项

Spring 提供了其他元素供使用：bean、ref、idref、value、null、list、set、map、props。

- value

可以使用type来指定原始类型及其包装类型。

```xml
<constructor-arg value="111111"/>
<property name="attributeName" value="222222"/>
```

- ref

通过ref的local、parent和bean属性来指定引用的对象的beanName是什么，ref没有子元素。

1. local：指定与当前配置再同一个配置文件的对象定义的名称（可以获得XML解析器的id约束验证支持）
2. parent：则只能指定位于当前容器的父容器中定义的对象引用；
3. bean：则基本上通吃，所以，通常情况下，直接使用bean来指定对象引用就可以了。

```xml
<constructor-arg>
    <ref local="djNewsPersister"/>
</constructor-arg>

<constructor-arg>
    <ref parent="djNewsPersister"/>
</constructor-arg>

<constructor-arg>
    <ref bean="djNewsPersister"/>
</constructor-arg>
```

==注意：== BeanFactory可以分层次（通过实现`HierarchicalBeanFactory`接口），容器A在初始化的时候，可以首先加载容器B中的所有对象定义，然后再加载自身的对象定义，这样，容器B就成为了容器A的父容器，容器A可以引用容器B中的所有对象定义：

```java
BeanFactory parentContainer = new XmlBeanFactory(new ClassPathResource("父容器配置文件路径"));
BeanFactory childContainer = new XmlBeanFactory(new ClassPathResource("子容器配置文件路径"), parentContainer);
```

childContainer中定义的对象，如果通过parent指定依赖，则只能引用parentContainer中的对象定义。

- idref

为当前对象注入所依赖的对象的名称，而不是引用，使用标签`<value>`和`<idref>`均可。

使用idref才是最为合适的。因为使用idref，容器在解析配置的时候就可以帮你检查这个beanName到底是否存在，而不用等到运行时才发现这个beanName对应的对象实例不存在。

```xml
<!-- 为属性newsListenerBeanName注入bean djNewsListener 两种形式都可以，使用idref更合理 -->
<property name="newsListenerBeanName">
    <idref bean="djNewsListener"/>
</property>

<property name="newsListenerBeanName">
    <value>djNewsListener</value>
</property>
```

- 内部bean

有时我们所依赖的bean A只会被另一个Bean B引用，或者bean A定义我们不想其他对象通过`<ref>`引用到它，这时候可以使用内嵌形式构建内部bean A来防止其他bean引用。如果其他bean需要引用这个beanA，那么可以考虑将bean A独立定义。

一般来说内部bean是不需要指定id的。

```xml
<bean id="djNewsProvider" class="..FXNewsProvider">
    <constructor-arg index="0">
        <bean class="..impl.DowJonesNewsListener">
        </bean>
    </constructor-arg>
    <constructor-arg index="1">
        <ref bean="djNewsPersister"/>
    </constructor-arg>
</bean>
```

- list

`<list>`对应注入对象类型为java.util.List及其子类或者数组类型的依赖对象，有序注入。

```java
public class MockDemoObject {
    private List param1;
    private String[] param2;
    private Set valueSet;
    private Map mapping;
    // 相应的setter和getter方法
}
```

```xml
<property name="param1">
    <list>
        <value> something</value>
        <ref bean="someBeanName"/>
        <bean class="..."/>
    </list>
</property>
<property name="param2">
    <list>
        <value>stringValue1</value>
        <value>stringValue2</value>
    </list>
</property>
```

- set

`<set>`对应注入Java Collection中类型为java.util.Set或者其子类的依赖对象，无序注入。

```xml
<property name="valueSet">
    <set>
        <value> something</value>
        <ref bean="someBeanName"/>
        <bean class="..."/>
        <list>
            ...
        </list>
    </set>
</property>
```

- map

与列表（list）使用数字下标来标识元素不同，映射（map）可以通过指定的键（key）来获取相应的值。对应注入java.util.Map或者其子类类型的依赖对象。

每一个`<entry>`都需要为其指定一个键和一个值，key属性用于指定通常的简单类型的键，而key-ref则用于指定对象的引用作为键。

```xml
<property name="mapping">
    <map>
        <entry key="strValueKey">
            <value>something</value>
        </entry>
        <entry>
            <key>objectKey</key>
            <ref bean="someObject"/>
        </entry>
        <entry key-ref="listKey">
            <list>
                ...
            </list>
        </entry>
    </map>
</property>

<!-- 简化写法 -->
<property name="valueSet">
    <map>
        <entry key="strValueKey" value="something"/>
        <entry key-ref="" value-ref="someObject"/>
        <entry key-ref="listKey">
            <list>
                ...
            </list>
        </entry>
    </map>
</property>
```

==警告：==往list，set，map注入不同类型的对象虽然很爽，用起来分分钟报`ClassCastException`。

- props

`<props>`是简化后了的<map>，或者说是特殊化的map，该元素对应配置类型为java.util.Properties的对象依赖。因为Properties只能指定String类型的键（key）和值

```java
public class MockDemoObject{
    private Properties emailAddrs;
    // 必要的setter和getter方法
}
```

```xml
<property name="valueSet">
    <props>
        <prop key="author">fujohnwang@gmail.com</prop>
        <prop key="support">support@spring21.cn</prop>
    </props>
</property>
```

- null

为属性注入null值，比如String不指定value的话，默认生成“”。若有属性为null，则可以使用`<null/>`

```java
public class MockDemoObject{
    private String param1;
    private Object param2;
    // 必要的setter和getter方法
}
```

```XML
<property name="param1">
    <null/>
</property>
<property name="param2">
    <null/>
</property>

<!-- 
// 实际上就相当于
public class MockDemoObject{
    private String param1 = null;
    private Object param2 = null;
    // 必要的setter和getter方法
}
-->
```

> depend on

容器在初始化当前bean定义时，会根据这些元素所标记的依赖关系，首先实例化当前bean定义所依赖其他bean定义。如果某些时候，我们没有通过类似`<ref>`的元素明确指定对象A依赖与对象B时，我们可以使用`depend on`属性来指定依赖关系。

假设ClassA需要使用log4j，那么ClassA在bean定义时必须在容器初始化自身实例时，先实例化log4j相关的模块（SystemConfigurationSetup），如下所示：

```java
public class SystemConfigurationSetup {
  static
  {
    DOMConfigurator.configure("配置文件路径");
    // 其他初始化代码
  }
}
```

```xml
<bean id="classAInstance" class="...ClassA" depends-on="configSetup"/>
<bean id="configSetup" class="SystemConfigurationSetup"/>

<!-- 如果ClassA依赖多个实例 -->
<bean id="classAInstance" class="...ClassA" depends-on="configSetup,configSetup2,..."/>
<bean id="configSetup" class="SystemConfigurationSetup"/>
<bean id="configSetup2" class="SystemConfigurationSetup2"/>
```

> 自动绑定 autowire

上述都是手动指定bean的依赖关系进行绑定。下面来介绍一下`<bean>`提供autowire属性，自动绑定某些bean。Spring提供了5中自动绑定模式：

- no：默认模式，不采用任何形式的自动绑定，完全依赖手工配置

```xml
<!-- 以下两种写法是等效的 -->
<bean id="beanName" class="..."/>
<bean id="beanName" class="..." autowire="no"/>
```

- byName：

按照类声明的实例变量的名称，与bean定义的beanName的值进行匹配，符合的bean定义将被自动绑定到当前的实例变量上。

如下：xml定义了bean emphasisAttribute，同时Foo的属性名为emphasisAttribute，同时符合才进行自动绑定。

```java
public class Foo {
  private Bar emphasisAttribute;
  // 相应的setter方法定义
}

public class Bar {
}
```

```xml
<bean id="fooBean" class="...Foo" autowire="byName"></bean>
<bean id="emphasisAttribute" class="...Bar"></bean>
```

- byType

如果指定当前Bean定义的autowire模式为byType，那么容器会根据当前bean定义类型，分析依赖对象类型，然后到容器中查找所有的bean并找到与依赖对象类型相同的bean定义，然后将该bean自动绑定到定义的bean中。

byName和byType的演示是相同的，只不过将`autowire="byName"`换成`autowire="byType"`

- constructor

byName和byType类型的自动绑定模式是针对property的自动绑定，而constructor类型是针对构造方法的类型进行的自动绑定，而不是实例属性的类型，绑定模式是byType类型。

```java
public class Foo {
  private Bar bar;
  public Foo(Bar arg) {
    this.bar = arg;
  }
}

public class Bar {
}
```

```xml
<bean id="foo" class="...Foo" autowire="constructor"></bean>
<bean id="bar" class="...Bar"></bean>
```

- autodetect

这种模式是byType和constructor模式的结合体，如果对象拥有默认无参数的构造方法，容器会优先考虑byType的自动绑定模式。否则，会使用constructor模式。当然，如果通过构造方法注入绑定后还有其他属性没有绑定，容器也会使用byType对剩余的对象属性进行自动绑定。

> 手动指定绑定关系 or 自动绑定关系？

- 优点：

1. 自动绑定可以减少手动配置的繁琐；
2. 自动绑定在添加新的依赖时，无需手动更改配置信息。

- 缺点：

1. 自动绑定不如指定绑定的依赖关系一目了然；
2. 会出现一些不可预知行为（不过启动阶段可以发现，问题不大）。比如使用byType时，新增了一个相同类型的bean定义；使用byName是，新增相同的beanName或者替换了等等；
3. 自动绑定Spring IDE的支持不是很友好。

==注意：== 

1. 手工明确指定的绑定关系总会覆盖自动绑定模式的行为；
2. 自动绑定只应用与**原生类型、String类型和Classes类型之外**的对象类型，对于原生类型、String类型和Classes类型以及这些类型的数组，应用自动绑定类型是无效的。

> dependency-check

`<bean>`的dependency-check属性对其所依赖的对象进行最终检查，该功能主要与自动绑定结合使用，可以保证当自动绑定完成后，最终确认每个对象所依赖的对象是否按照所预期的那样被注入。

- none：默认，不做依赖检查；
- simple：对简单属性类型以及相关的collection进行依赖检查，对象引用类型的依赖除外；
- object：只对对象引用类型依赖进行检查；
- all：simple和object相结合，基本上是所有类型。

> lazy-init

我们直到ApplicationContext容器启动时会对所有的bean进行实例化，如果我们不想某些bean启动时进行实例化，这时`lazy-init`就派上用场了。

指定了`lazy-init`不一定按照我们的预期生效，如下，当`lazy-init-bean`被`not-lazy-init-bean`所依赖时，`lazy-init-bean`并不会延迟初始化。

```xml
<bean id="lazy-init-bean" class="..." lazy-init="true"/>
<bean id="not-lazy-init-bean" class="...">
  <property name="propName">
    <ref bean="lazy-init-bean"/>
  </property>
</bean>
```

> 继承: parent属性

parent属性与abstract属性结合使用，达到将响应bean定义模板化的目的。

```xml
<bean id="newsProviderTemplate" abstract="true">
  <property name="newPersistener">
    <ref bean="djNewsPersister"/>
  </property>
</bean>

<bean id="superNewsProvider" parent="newsProviderTemplate" class="..FXNewsProvider">
  <property name="newsListener">
    <ref bean="djNewsListener"/>
  </property>
</bean>

<bean id="subNewsProvider" parent="newsProviderTemplate" class="..SpecificFXNewsProvider">
  <property name="newsListener">
    <ref bean="specificNewsListener"/>
  </property>
</bean>
```

newsProviderTemplate的bean定义通过abstract属性声明为true，说明这个bean定义不需要实例化。该bean定义只是一个配置模板，不对应任何对象。`superNewsProvider`和`subNewsProvider`通过parent指向这个模板定义，就拥有了该模板定义的所有属性配置。

> scope

scope：作用域，包含singleton、prototype，其中request、session和global session类型只能再Web应用中使用。

- singleton

默认的scope，在Spring IOC容器中只存在一个实例，所有对该对象的引用将共享这个实例。生命周期是从容器启动，到它第一次被请求而实例化开始，只要容器不销毁或者退出，该类型bean的单一实例就会一直存活。

```xml
<!-- DTD or XSD -->
<bean id="mockObject1" class="...MockBusinessObject"/>
<!-- DTD -->
<bean id="mockObject1" class="...MockBusinessObject" singleton="true"/>
<!-- XSD -->
< bean id="mockObject1" class="...MockBusinessObject" scope="singleton"/>
```

- prototype

容器在接到该类型对象的请求的时候，会每次都重新生成一个新的对象实例给请求方。

prototype类型的对象的实例化以及属性设置等工作都是由容器负责的，但是只要准备完毕，并且对象实例返回给请求方之后，容器就不再拥有当前返回对象的引用，**请求方需要自己负责当前返回对象的后继生命周期的管理工作，包括该对象的销毁**。

```xml
<!-- DTD -->
<bean id="mockObject1" class="...MockBusinessObject" singleton="false"/>
<!-- XSD -->
<bean id="mockObject1" class="...MockBusinessObject" scope="prototype"/>
```

- request

Spring容器，即XmlWebApplicationContext会为每个HTTP请求创建一个全新的Request-Processor对象供当前请求使用，当请求结束后，该对象实例的生命周期即告结束。

```xml
<bean id="requestProcessor" class="...RequestProcessor" scope="request"/>
```

- session

Spring容器会为每个独立的session创建属于它们自己的全新的UserPreferences对象实例。与
request相比，除了拥有session scope的bean的实例具有比request scope的bean可能更长的存活时间，其他方面真是没什么差别。

```xml
<bean id="userPreferences" class="com.foo.UserPreferences" scope="session"/>
```

- global session

global session只有应用在基于portlet的Web应用程序中才有意义，它映射到portlet的global范围的
session。如果在普通的基于servlet的Web应用中使用了这个类型的scope，容器会将其作为普通的session类型的scope对待。

```xml
<bean id="userPreferences" class="com.foo.UserPreferences" scope="globalSession"/>
```

- 自定义scope类型

1. 实现`org.springframework.beans.factory.config.Scope`接口，必须实现get和remove方法；
2. 将自定义实现的`com.wenqi.springioc.instance.xml.scope.ThreadScope`注册到容器中；

```java
Scope threadScope = new ThreadScope();
beanFactory.registerScope("thread", threadScope);
```

3. 使用自定义的scope

```xml
<bean id="beanName" class="..." scope="thread"/>
```

除此之外，Spring还专门提供了用于同意注册自定义Scope的实现：`org.springframework.beans.factory.config.CustomScopeConfigurer`

```xml
<bean class="org.springframework.beans.factory.config.CustomScopeConfigurer">
  <property name="scopes">
    <map>
      <entry key="thread" value="com.wenqi.springioc.instance.xml.scope.ThreadScope"/>
    </map>
  </property>
</bean>

<bean id="beanName" class="..." scope="thread">
  <aop:scoped-proxy/>
</bean>
```

##### 工厂方法

很多时候我们需要依赖第三方库，并且需要实例化相关类。此时我们可以通过工厂方法（Factory Method）模式，提供一个工厂类来是实例具体的接口实现类。这时当实现类有变更的话，只需要修改工厂方法类，而使用对象不需要改动，做到接口鱼实现类解耦。

###### 静态工厂

`com.wenqi.springioc.instance.xml.factory.StaticBarInterfaceFactory`

```xml
<bean id="bar" class="com.wenqi.springioc.instance.xml.factory.StaticBarInterfaceFactory" factory-method="getInstance"/>
```

class指定静态方法工厂类，factory-method指定工厂方法名称，然后，容器调用该静态方法工厂类的指定工厂方法（getInstance），并返回方法调用后的结果，即BarInterfaceImpl的实例。

###### 非静态工厂

`com.wenqi.springioc.instance.xml.factory.NoStaticBarInterfaceFactory`

```xml
<bean id="fooNoStatic" class="com.wenqi.springioc.instance.xml.factory.Foo">
  <property name="barInterface" ref="bar"/>
</bean>
<!-- NoStaticBarInterfaceFactory 作为正常的bean注入容器中 -->
<bean id="barFactory" class="com.wenqi.springioc.instance.xml.factory.NoStaticBarInterfaceFactory"/>
<bean id="barNoStatic" factory-bean="barFactory" factory-method="getInstance"/>
```

NonStaticBarInterfaceFactory是作为正常的bean注册到容器的，而barNoStatic的定义则与静态工厂方法的定义有些不同。现在使用factory-bean属性来指定工厂方法所在的工厂类实例，而不是通过class属性来指定工厂方法所在类的类型。

如果barFactory带参数，处理方法与静态工厂处理方法一致。

###### FactoryBean

FactoryBean是Spring容器提供的一种可以扩展容器对象实例化逻辑的接口，请不要将其与容器名称BeanFactory相混淆。FactoryBean，其主语是Bean，定语为Factory，也就是说，它本身与其他注册到容器的对象一样，只是一个Bean而已，只不过，这种类型的Bean本身就是生产对象的工厂（Factory）。

当某些对象的实例化过程过于烦琐，通过XML配置过于复杂，或者，某些第三方库不能直接注册到Spring容器的时候，就可以实现`org.spring.framework.beans.factory.FactoryBean`接口，给出自己的对象实例化逻辑代码。相当于Spring为我们提供了Bean实例化的标准步骤。

```java
public interface FactoryBean {
  // 返回Factory生产的实例
  Object getObject() throws Exception;
  
  // 返回getObject()方法所返回的对象的类型，如果预先无法确定，则返回null
  Class getObjectType();
  
  // 是否要以singleton形式存在于容器中
  boolean isSingleton();
}
```

- 示例

`com.wenqi.springioc.instance.xml.factory.factorybean.NextDayDateFactoryBean`

FactoryBean类型的bean定义，通过正常的id引用，容器返回BeanFactory的是FactoryBean所“生产”的对象类型，而非FactoryBean实现本身。如果一定要取得FactoryBean本身的话，可以通过在bean定义的id之前加前缀&来达到目的。

```txt
// 一些比较常见的FactoryBean实现
JndiObjectFactoryBean
LocalSessionFactoryBean
SqlMapClientFactoryBean
ProxyFactoryBean
TransactionProxyFactoryBean
```

##### 方法注入/替换

方法注入（Method Injection）以及方法替换（Method Replacement）

`com.wenqi.springioc.instance.method.MethodInjectDemo`







