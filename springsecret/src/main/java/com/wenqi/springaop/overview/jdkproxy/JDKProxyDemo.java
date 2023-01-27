package com.wenqi.springaop.overview.jdkproxy;

import com.wenqi.springaop.overview.IRequestable;
import com.wenqi.springaop.overview.RequestableImpl;

import java.lang.reflect.Proxy;

/**
 * @author liangwenqi
 * @date 2023/1/27
 */
public class JDKProxyDemo {
    public static void main(String[] args) {
        IRequestable requestable = (IRequestable) Proxy.newProxyInstance(JDKProxyDemo.class.getClassLoader(), new Class[]{IRequestable.class}, new RequestCtrlInvocationHandler(new RequestableImpl()));
        requestable.request();
    }
}
