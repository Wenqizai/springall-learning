package com.wenqi.springioc.applicationcontext.multimodule;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

/**
 * @author liangwenqi
 * @date 2023/1/6
 */
public class LoadMultiFileDemo {
    public static void main(String[] args) {
        useApplicationContext();

    }

    private static void useApplicationContext() {
        String[] locations = new String[]{"/applicationcontext/conf/business-tier.springxml", "/applicationcontext/conf/dao-tier.springxml", "/applicationcontext/conf/view-tier.springxml"};
        ApplicationContext container = new FileSystemXmlApplicationContext(locations);
        ApplicationContext container2 = new ClassPathXmlApplicationContext(locations);
        // 使用通配符
        ApplicationContext container3 = new FileSystemXmlApplicationContext("conf/**/*.springxml");
    }

    private static void useBeanFactory() {
        BeanFactory parentFactory = new XmlBeanFactory(new FileSystemResource("/applicationcontext/conf/dao-tier.springxml"));
        BeanFactory subFactory = new XmlBeanFactory(new FileSystemResource("/applicationcontext/conf/view-tier.springxml"));
        BeanFactory subSubFactory = new XmlBeanFactory(new FileSystemResource("/applicationcontext/conf/business-tier.springxml"));
    }
}
