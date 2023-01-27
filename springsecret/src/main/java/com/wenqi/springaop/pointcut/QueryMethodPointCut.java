package com.wenqi.springaop.pointcut;

import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * @author liangwenqi
 * @date 2023/1/27
 */
public class QueryMethodPointCut extends StaticMethodMatcherPointcut {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return method.getName().startsWith("get") && targetClass.getPackage().getName().startsWith("*.dao");
    }
}
