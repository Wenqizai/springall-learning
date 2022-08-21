package com.wenqi.spring.bean.definition;

import com.wenqi.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 常规的 Bean 实列化方式
 *
 * @author Wenqi Liang
 * @date 2022/8/21
 */
public class BeanInstantiationDemo {
    public static void main(String[] args) {
        // 配置XML配置文件
        // 启动Spring应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:META-INF/bean-instantiation-context.xml");
        User user = beanFactory.getBean("user-by-static-method", User.class);
        System.out.println(user);


        User userByInstanceMethod = beanFactory.getBean("user-by-instance-method", User.class);
        System.out.println(userByInstanceMethod);

        // false, 实例化方式不同, 两者不等
        System.out.println(user == userByInstanceMethod);


        User userByFactoryBean = beanFactory.getBean("user-by-factory-bean", User.class);
        System.out.println(userByFactoryBean);

        // false, 实例化方式不同, 两者不等
        System.out.println(user == userByFactoryBean);

    }

}
