package com.wenqi.spring.bean.definition;

import com.wenqi.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bean 别名示例
 *
 * @author Wenqi Liang
 * @date 2022/8/21
 */
public class BeanAliasDemo {
    public static void main(String[] args) {
        // 配置XML配置文件
        // 启动Spring应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:META-INF/bean-definitions-context.xml");
        // 通过别名 xiaomage-user 获取曾用名 user 的 bean
        User user = beanFactory.getBean("user", User.class);
        User xiaomageUser = beanFactory.getBean("xiaomage-user", User.class);
        // true
        System.out.println(xiaomageUser == user);
    }
}
