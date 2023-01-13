package com.wenqi.springioc.applicationcontext.extend.autowire;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author liangwenqi
 * @date 2023/1/13
 */
public class AutowireBindTestDemo {
    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:applicationcontext/spring-autowire-context.xml");
        System.out.println(beanFactory.getBeanProvider(AutowiredAnnotationBeanPostProcessor.class).getIfAvailable());
    }
}
