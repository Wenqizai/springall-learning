package com.wenqi.springioc.applicationcontext.eventpublish.custom;

/**
 * @author liangwenqi
 * @date 2022/12/28
 */
public class EventDemo {
    public static void main(String[] args) {
        MethodExecutionEventPublisher publisher = new MethodExecutionEventPublisher();
        publisher.addMethodExecutionEventListener(new SimpleMethodExecutionEventListener());
        publisher.methodToMonitor();
    }
}
