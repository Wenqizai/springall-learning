package com.wenqi.springioc.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author liangwenqi
 * @date 2022/10/26
 */
public class ConstructArgInjectXmlTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/xml/spring-context.xml");
        System.out.println(context.getBean("mockBO"));
        System.out.println(context.getBean("mockBO2"));
        System.out.println(context.getBean("mockBO3"));

        System.out.println(context.getBean("mockBO4"));
        System.out.println(context.getBean("mockBO5"));
    }
}
