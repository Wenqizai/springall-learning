package com.wenqi.springaop.overview.cglibproxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author liangwenqi
 * @date 2023/1/27
 */
public class RequestCtrlCallback implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (Objects.equals(method.getName(), "request")) {
            System.out.println("do something in proxy");
            return methodProxy.invokeSuper(o, objects);
        }
        return null;
    }
}
