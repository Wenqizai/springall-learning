package com.wenqi.springaop.problem;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StopWatch;

/**
 * @author liangwenqi
 * @date 2023/1/31
 */
@Aspect
public class PerformanceTraceAspect2 {

    @Pointcut("execution(public void *.method1())")
    public void method1() {
    }

    @Pointcut("execution(public void *.method2())")
    public void method2() {
    }

    @Pointcut("method1() || method2()")
    public void compositePointcut() {
    }

    @Around("compositePointcut()")
    public Object performanceTrace(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch watch = new StopWatch();
        try {
            watch.start();
            return joinPoint.proceed();
        } finally {
            watch.stop();
            System.out.println("PT in method[" + joinPoint.getSignature().getName() + "] >>>>>>>" + watch.toString());
        }
    }

}
