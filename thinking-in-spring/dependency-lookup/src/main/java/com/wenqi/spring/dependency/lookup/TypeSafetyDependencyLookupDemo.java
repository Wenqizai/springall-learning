package com.wenqi.spring.dependency.lookup;

import com.wenqi.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Wenqi Liang
 * @date 2022/10/8
 */
public class TypeSafetyDependencyLookupDemo {
    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 ObjectProviderDemo 注册为 Configuration class (配置类)
        applicationContext.register(TypeSafetyDependencyLookupDemo.class);
        // 启动 Spring 应用上下文
        applicationContext.refresh();

        // 1. 单一类型获取 Bean, 不安全的会抛出异常
        // 演示 BeanFactory#getBean 方法的安全性 (不安全)
        displayBeanFactoryGetBean(applicationContext);
        // 演示 ObjectFactory#getBean 方法的安全性 (不安全)
        displayObjectFactoryGetObject(applicationContext);
        // 演示 ObjectFactory#getBean 方法的安全性 (安全)
        displayObjectProviderIfAvailable(applicationContext);

        // 2. 集合类型获取 Bean, 均为安全
        // 演示 ListableBeanFactory#getBeansOfType 方法的安全性
        displayListableBeanFactory(applicationContext);
        // 演示 ObjectProvider stream 操作的安全性
        displayListObjectProviderStreamOps(applicationContext);

        // 显示关闭 Spring 应用上下文
        applicationContext.close();
    }

    private static void displayListObjectProviderStreamOps(AnnotationConfigApplicationContext applicationContext) {
        // objectProvider is objectFactory
        ObjectProvider<User> objectProvider = applicationContext.getBeanProvider(User.class);
        printBeansException("displayListObjectProviderStreamOps", () -> objectProvider.stream().forEach(System.out::println));
    }

    private static void displayListableBeanFactory(ListableBeanFactory beanFactory) {
        printBeansException("displayListableBeanFactory", () -> beanFactory.getBeansOfType(User.class));
    }

    private static void displayObjectProviderIfAvailable(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> objectProvider = applicationContext.getBeanProvider(User.class);
        printBeansException("displayObjectFactoryGetObject", objectProvider::getIfAvailable);
    }

    private static void displayObjectFactoryGetObject(AnnotationConfigApplicationContext applicationContext) {
        // objectProvider is objectFactory
        ObjectProvider<User> objectProvider = applicationContext.getBeanProvider(User.class);
        printBeansException("displayObjectFactoryGetObject", objectProvider::getObject);
    }

    public static void displayBeanFactoryGetBean(BeanFactory beanFactory) {
        printBeansException("displayBeanFactoryGetBean", () -> beanFactory.getBean(User.class));
    }

    private static void printBeansException(String message, Runnable runnable) {
        System.err.println("========================================");
        System.err.println("Source from: " + message);
        try {
            runnable.run();
        } catch (BeansException exception) {
            exception.printStackTrace();
        }
    }
}
