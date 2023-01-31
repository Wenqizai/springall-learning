package com.wenqi.springaop.aspectj.pointcut;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author liangwenqi
 * @date 2023/1/31
 */
@Aspect
public class OtherAspect {
    @Pointcut("com.wenqi.springaop.aspectj.pointcut.SystemCommonsAspect.commonPointcut1() " +
            "|| com.wenqi.springaop.aspectj.pointcut.SystemCommonsAspect.commonPointcut2()")
    public void compostPointcutDefinition() {
    }
}
