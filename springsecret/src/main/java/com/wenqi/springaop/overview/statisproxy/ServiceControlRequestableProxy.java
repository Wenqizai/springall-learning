package com.wenqi.springaop.overview.statisproxy;


import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import com.wenqi.springaop.overview.IRequestable;


/**
 * @author liangwenqi
 * @date 2023/1/27
 */
public class ServiceControlRequestableProxy implements IRequestable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceControlRequestableProxy.class);
    private final IRequestable requestable;

    public ServiceControlRequestableProxy(IRequestable requestable) {
        this.requestable = requestable;
    }

    @Override
    public void request() {
        System.out.println("doing something.....");
        requestable.request();
    }
}
