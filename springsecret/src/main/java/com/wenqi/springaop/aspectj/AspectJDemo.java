package com.wenqi.springaop.aspectj;

import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author liangwenqi
 * @date 2023/1/31
 */
public class AspectJDemo {
    public static void main(String[] args) {
        test02();
    }

    /**
     * 自动织入
     */
    private static void test02() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:aop/spring-aop2-context.xml");
        Object proxy = context.getBean("foo");
        ((Foo)proxy).method1();
        ((Foo)proxy).method2();
    }

    /**
     * 手动织入
     */
    private static void test01() {
        AspectJProxyFactory weaver = new AspectJProxyFactory();
        weaver.setProxyTargetClass(true);
        weaver.setTarget(new Foo());
        weaver.addAspect(PerformanceTraceAspect.class);

        Object proxy = weaver.getProxy();
        ((Foo)proxy).method1();
        ((Foo)proxy).method2();
    }
}
