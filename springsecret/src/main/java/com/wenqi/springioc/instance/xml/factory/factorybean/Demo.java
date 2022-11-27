package com.wenqi.springioc.instance.xml.factory.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;

/**
 * @author Wenqi Liang
 * @date 2022/11/27
 */
public class Demo {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:xml/spring-factory-context.xml");

        Object nextDayDate = context.getBean("nextDayDate");
        System.out.println(nextDayDate instanceof LocalDate);


        Object factoryBean = context.getBean("&nextDayDate");
        System.out.println(factoryBean instanceof FactoryBean);
        System.out.println(factoryBean instanceof NextDayDateFactoryBean);


        Object factoryValue = ((FactoryBean) factoryBean).getObject();
        System.out.println(factoryValue instanceof LocalDate);
    }
}
