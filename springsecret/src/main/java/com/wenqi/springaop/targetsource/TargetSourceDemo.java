package com.wenqi.springaop.targetsource;

import org.springframework.aop.framework.Advised;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author liangwenqi
 * @date 2023/1/30
 */
public class TargetSourceDemo {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:aop/spring-aop-context.xml");
        Advised proxy1 = (Advised) context.getBean("targetProxy");
        Advised proxy2 = (Advised) context.getBean("targetProxy");

        System.out.println(proxy1.getTargetSource().getTarget() == proxy2.getTargetSource().getTarget());

        Advised proxy3 = (Advised) context.getBean("targetProxy2");
        Advised proxy4 = (Advised) context.getBean("targetProxy2");
        System.out.println(proxy3.getTargetSource().getTarget() == proxy4.getTargetSource().getTarget());

    }
}
