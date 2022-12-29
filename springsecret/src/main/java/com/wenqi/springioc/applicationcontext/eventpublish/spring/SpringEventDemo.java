package com.wenqi.springioc.applicationcontext.eventpublish.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author liangwenqi
 * @date 2022/12/29
 */
public class SpringEventDemo {
    public static void main(String[] args) {
       ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationcontext/spring-context.xml");
        MethodExecutionEventPublisher publisher = (MethodExecutionEventPublisher) applicationContext.getBean("methodExecutionEventPublisher");
        publisher.methodToMonitor();
    }
}
