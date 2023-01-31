package com.wenqi.springaop.aspectj.pointcut;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author liangwenqi
 * @date 2023/1/31
 */
@Aspect
public class SystemCommonsAspect {
    @Pointcut("execution(void method1())")
    public void commonPointcut1() {
    }

    @Pointcut("execution(void method2())")
    public void commonPointcut2() {
    }
}
