package com.wenqi.springaop.overview.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author liangwenqi
 * @date 2023/1/27
 */
public class RequestCtrlInvocationHandler implements InvocationHandler {
    private Object target;

    public RequestCtrlInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Objects.equals(method.getName(), "request")) {
            System.out.println("do something in proxy");
            return method.invoke(target, args);
        }
        return null;
    }
}
