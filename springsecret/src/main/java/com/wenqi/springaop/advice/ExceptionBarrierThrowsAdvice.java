package com.wenqi.springaop.advice;

import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * @author liangwenqi
 * @date 2023/1/27
 */
public class ExceptionBarrierThrowsAdvice implements ThrowsAdvice {

    public void afterThrowing(Throwable t) {
        // 普通异常处理逻辑
    }

    public void afterThrowing(RuntimeException e) {
        // 运行时异常处理逻辑
    }

    public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {
        // 处理引用程序生成的异常
    }
}
