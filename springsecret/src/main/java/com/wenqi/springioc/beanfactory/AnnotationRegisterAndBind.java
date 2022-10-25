package com.wenqi.springioc.beanfactory;

import com.wenqi.springioc.instance.FXNewsAnnoProvider;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author liangwenqi
 * @date 2022/10/25
 */
public class AnnotationRegisterAndBind {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:beanfactory/spring-context.xml");
        FXNewsAnnoProvider newsAnnoProvider = (FXNewsAnnoProvider) ctx.getBean("FXNewsAnnoProvider");
        newsAnnoProvider.getAndPersistNews();
    }
}
