package com.wenqi.ioc.overview.dependency.injection;

import com.wenqi.ioc.overview.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:META-INF/dependency-injection-context.xml");
        UserRepository userRepository = beanFactory.getBean("userRepository", UserRepository.class);

        // 依赖注入
        System.out.println(userRepository.getBeanFactory());
        // 说明依赖查找和依赖注入的BeanFactory并不是同源的Bean
//        System.out.println(userRepository.getBeanFactory() == beanFactory); // false

        ObjectFactory<ApplicationContext> userObjectFactory = userRepository.getApplicationContextObjectFactory();
        System.out.println(userObjectFactory.getObject() == beanFactory); // true


        // 依赖查找(错误)
//        System.out.println(beanFactory.getBean(BeanFactory.class));
    }
}
