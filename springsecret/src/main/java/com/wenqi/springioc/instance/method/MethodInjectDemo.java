package com.wenqi.springioc.instance.method;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Wenqi Liang
 * @date 2022/11/28
 */
public class MethodInjectDemo {
    public static void main(String[] args) {
        //methodInjectTest1();
        //methodInjectTest2();
        methodInjectTest3();
    }

    private static void methodInjectTest3() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:xml/spring-context.xml");
        MockNewsPersister3 persister = (MockNewsPersister3) context.getBean("mockPersister3");
        // 每次调用改方法都从容器中获取一遍bean
        persister.persistNewes();
        persister.persistNewes();
    }

    private static void methodInjectTest2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:xml/spring-context.xml");
        MockNewsPersister2 persister = (MockNewsPersister2) context.getBean("mockPersister2");
        // 每次调用改方法都从容器中获取一遍bean
        persister.persistNewes();
        persister.persistNewes();
    }

    private static void methodInjectTest1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:xml/spring-context.xml");
        MockNewsPersister persister = (MockNewsPersister) context.getBean("mockPersister");
        // 这两次都是输出同一个bean, 按道理prototype模式应该是两个不同的bean, 为什么呢?
        // persister类里面一直持有同一个FXNewsBean的引用, 所以persister.persistNewes()调用多次都是同一个FXNewsBean
        // 换句话说，后，MockNewsPersister再也没有重新向容器申请新的实例。所以，容器也不会重新为其注入新的FXNewsBean类型的实例。
        // 解决问题的关键在于保证getNewsBean()方法每次从容器中取得新的FXNewsBean实例，而不是每次都返回其持有的单一实例。
        persister.persistNewes();
        persister.persistNewes();
    }
}
