package com.wenqi.springaop.weave.proxyfactorybean.introduction;

import com.wenqi.springaop.weave.intf.ITask;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author liangwenqi
 * @date 2023/1/30
 */
public class IntroductionDemo {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:aop/spring-aop-context.xml");
        Object proxy1 = context.getBean("introducedTask2");
        Object proxy2 = context.getBean("introducedTask2");

        // proxy1和proxy2 拥有独立状态, 结果为: 1 2 1
        System.out.println(((ICounter)proxy1).getCounter());
        System.out.println(((ICounter)proxy1).getCounter());
        System.out.println(((ICounter)proxy2).getCounter());

        // proxy同时拥有ITask状态
        ((ITask)proxy1).execute();
        ((ITask)proxy2).execute();
    }
}
