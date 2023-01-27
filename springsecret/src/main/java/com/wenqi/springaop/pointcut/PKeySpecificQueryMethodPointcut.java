package com.wenqi.springaop.pointcut;

import org.springframework.aop.support.DynamicMethodMatcherPointcut;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author liangwenqi
 * @date 2023/1/27
 */
public class PKeySpecificQueryMethodPointcut extends DynamicMethodMatcherPointcut {
    @Override
    public boolean matches(Method method, Class<?> targetClass, Object... args) {
        if (method.getName().startsWith("get") && targetClass.getPackage().getName().startsWith("*.dao")) {
            if (args.length > 1) {
                return Objects.equals("12345", args[0].toString());
            }
        }
        return false;
    }
}
