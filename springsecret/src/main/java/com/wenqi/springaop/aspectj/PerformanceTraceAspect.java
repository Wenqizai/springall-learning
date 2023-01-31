package com.wenqi.springaop.aspectj;

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
public class PerformanceTraceAspect {

    @Pointcut("execution(public void *.menthod1()) || execution(public void *.method2())")
    public void pointcutName(){}

    @Around("pointcutName()")
    public Object performanceTrace(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch watch = new StopWatch();
        System.out.println("method start: " + joinPoint.getSignature().getName());
        try {
            watch.start();
            return joinPoint.proceed();
        } finally {
            watch.stop();
            System.out.println("PT in method[" + joinPoint.getSignature().getName() + "] >>>>>>>" + watch.toString());
        }
    }

}
