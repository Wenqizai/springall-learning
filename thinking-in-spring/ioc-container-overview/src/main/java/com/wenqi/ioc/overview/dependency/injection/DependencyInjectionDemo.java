package com.wenqi.ioc.overview.dependency.injection;

import com.wenqi.ioc.overview.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 依赖注入示例
 *
 * @author Wenqi Liang
 * @date 2022/8/6
 */
public class DependencyInjectionDemo {
    public static void main(String[] args) {
        // 配置XML配置文件
        // 启动Spring应用上下文
        // ApplicationContext extend BeanFactory
//        BeanFactory applicationContext = new ClassPathXmlApplicationContext("classpath:META-INF/dependency-injection-context.xml");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:META-INF/dependency-injection-context.xml");

        // * 依赖来源一 : 自定义 Bean
        UserRepository userRepository = applicationContext.getBean("userRepository", UserRepository.class);

        // * 依赖来源二 : 依赖注入(内建依赖)
        BeanFactory insideFactory = userRepository.getBeanFactory();
        System.out.println(insideFactory);
        // 说明依赖查找和依赖注入的BeanFactory并不是同源的Bean
        System.out.println(userRepository.getBeanFactory() == applicationContext); // false
        // 依赖查找(错误)
//        System.out.println(applicationContext.getBean(BeanFactory.class));

        ObjectFactory<ApplicationContext> userObjectFactory = userRepository.getApplicationContextObjectFactory();
        System.out.println(userObjectFactory.getObject() == applicationContext); // true

        // * 依赖来源三 : 容器内建Bean
        Environment environment = applicationContext.getBean(Environment.class);
        System.out.println("获取 Environment 类型的 Bean" + environment);
    }

    private static void whoIsContainer(UserRepository userRepository, ApplicationContext applicationContext) {
        // 思考点1: 为什么这个表达式不成立
        System.out.println(userRepository.getBeanFactory() == applicationContext);

    }
}
