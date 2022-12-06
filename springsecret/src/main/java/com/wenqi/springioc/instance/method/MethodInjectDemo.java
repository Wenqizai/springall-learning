package com.wenqi.springioc.instance.method;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Wenqi Liang
 * @date 2022/11/28
 */
public class MethodInjectDemo {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:xml/spring-context.xml");
        MockNewsPersister persister = (MockNewsPersister) context.getBean("mockPersister");
        // 这两次都是输出同一个bean, 按道理prototype模式应该是两个不同的bean, 为什么呢?
        persister.persistNewes();
        persister.persistNewes();
    }
}
