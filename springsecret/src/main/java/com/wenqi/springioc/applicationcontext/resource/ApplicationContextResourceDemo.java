package com.wenqi.springioc.applicationcontext.resource;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;

/**
 * @author liangwenqi
 * @date 2022/12/27
 */
public class ApplicationContextResourceDemo {
    public static void main(String[] args) {
        ResourceLoader resourceLoader = new ClassPathXmlApplicationContext("classpath:applicationcontext/spring-context.xml");
        // 或者
        // ResourceLoader resourceLoader = new FileSystemXmlApplicationContext("classpath:applicationcontext/spring-context.xml");
        Resource fileResource = resourceLoader.getResource("D:/spring21site/README");
        System.out.println(fileResource instanceof ClassPathResource);
        System.out.println(fileResource.exists());
        Resource urlResource2 = resourceLoader.getResource("http://www.spring21.cn");
        System.out.println(urlResource2 instanceof UrlResource);
    }
}
