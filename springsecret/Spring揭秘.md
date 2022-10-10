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



























