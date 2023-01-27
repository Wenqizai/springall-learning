package com.wenqi.springaop.overview.statisproxy;

import com.wenqi.springaop.overview.IRequestable;
import com.wenqi.springaop.overview.RequestableImpl;

/**
 * @author liangwenqi
 * @date 2023/1/27
 */
public class StatisProxyDemo {
    public static void main(String[] args) {
        // 使用代理对象 ServiceControlRequestableProxy, 而不是目标对象 RequestableImpl
        IRequestable requestable = new ServiceControlRequestableProxy(new RequestableImpl());
        requestable.request();
    }
}
