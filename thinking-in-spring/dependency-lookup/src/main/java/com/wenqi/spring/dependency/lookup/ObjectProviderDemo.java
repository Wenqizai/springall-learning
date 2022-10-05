package com.wenqi.spring.dependency.lookup;

import com.wenqi.ioc.overview.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.Iterator;

/**
 * 通过 {@link org.springframework.beans.factory.ObjectProvider} 进行依赖查找
 *
 * @author Wenqi Liang
 * @date 2022/9/25
 */
public class ObjectProviderDemo {
    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 ObjectProviderDemo 注册为 Configuration class (配置类)
        applicationContext.register(ObjectProviderDemo.class);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 依赖查找对象
        lookupByObjectProvider(applicationContext);
        // 安全查找对象
        lookupIfAvailable(applicationContext);
        // 通过 Stream 方式查找对象
        lookupByStreamOps(applicationContext);

        // 显示关闭 Spring 应用上下文
        applicationContext.close();
    }

    private static void lookupByStreamOps(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> objectProvider = applicationContext.getBeanProvider(String.class);
        // 打印所有的 String 类型的 Bean
        Iterable<String> stringIterable = objectProvider;
        for (String string : stringIterable) {
            System.out.println(string);
        }

        // 使用 Stream 方式打印
        objectProvider.stream().forEach(System.out::println);
    }

    private static void lookupIfAvailable(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
        User user = userObjectProvider.getIfAvailable(User::createUser);
        System.out.println("当前 User 对象: " + user);
    }

    private static void lookupByObjectProvider(AnnotationConfigApplicationContext applicationContext) {
       ObjectProvider<String> objectProvider = applicationContext.getBeanProvider(String.class);
        System.out.println(objectProvider.getObject());
    }

    @Bean
    @Primary
    public String helloWorld() { // 方法名就是 Bean 名称 = "helloWorld"
        return "hello, World";
    }

    @Bean
    public String message() {
        return "Message";
    }

}
