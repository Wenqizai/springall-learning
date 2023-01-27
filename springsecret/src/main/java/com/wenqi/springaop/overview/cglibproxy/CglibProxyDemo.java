package com.wenqi.springaop.overview.cglibproxy;

import com.wenqi.springaop.overview.Requestable;
import org.springframework.cglib.proxy.Enhancer;

/**
 * @author liangwenqi
 * @date 2023/1/27
 */
public class CglibProxyDemo {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Requestable.class);
        enhancer.setCallback(new RequestCtrlCallback());
        Requestable proxy = (Requestable) enhancer.create();
        proxy.request();
    }
}
