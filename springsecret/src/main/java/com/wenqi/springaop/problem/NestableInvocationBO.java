package com.wenqi.springaop.problem;

import org.springframework.aop.framework.AopContext;

/**
 * @author liangwenqi
 * @date 2023/2/2
 */
public class NestableInvocationBO {
    public void method1() {
        // aop 不生效
        //method2();

        // aop 生效
        ((NestableInvocationBO)AopContext.currentProxy()).method2();
        System.out.println("method1 executed!");
    }

    public void method2() {
        System.out.println("method2 executed");
    }
}
