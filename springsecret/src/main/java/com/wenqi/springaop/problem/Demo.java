package com.wenqi.springaop.problem;

import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

/**
 * @author liangwenqi
 * @date 2023/2/2
 */
public class Demo {
    public static void main(String[] args) {
        AspectJProxyFactory weaver = new AspectJProxyFactory(new NestableInvocationBO());
        weaver.setProxyTargetClass(true);
        weaver.addAspect(PerformanceTraceAspect2.class);
        weaver.setExposeProxy(true);
        Object proxy = weaver.getProxy();
        ((NestableInvocationBO)proxy).method2();
        ((NestableInvocationBO)proxy).method1();
    }
}
