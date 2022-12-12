package com.wenqi.springioc.beanfactory.postprocessor;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * @author liangwenqi
 * @date 2022/12/8
 */
public class PropertyEditorDemo {
    public static void main(String[] args) {
        demo01();
    }

    private static void demo02() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:beanfactory/postprocessor/spring-context.xml");
        DateFoo dateFoo = (DateFoo) applicationContext.getBean("dateFoo");
        System.out.println(dateFoo.getDate());
    }


    private static void demo01() {
        XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("/beanfactory/postprocessor/spring-context.xml"));
        DateFoo dateFoo = (DateFoo) beanFactory.getBean("dateFoo");
        System.out.println(dateFoo.getDate());
    }
}
