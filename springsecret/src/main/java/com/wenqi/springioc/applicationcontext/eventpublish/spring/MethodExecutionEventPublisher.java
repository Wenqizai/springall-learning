package com.wenqi.springioc.applicationcontext.eventpublish.spring;

import com.wenqi.springioc.applicationcontext.eventpublish.MethodExecutionStatus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;


/**
 * @author liangwenqi
 * @date 2022/12/29
 */
public class MethodExecutionEventPublisher implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher eventPublisher;

    public void methodToMonitor() {
        MethodExecutionEvent beginEvt = new MethodExecutionEvent(this, "methodToMonitor", MethodExecutionStatus.BEGIN);
        this.eventPublisher.publishEvent(beginEvt);
        // 执行实际方法逻辑
        // ...
        MethodExecutionEvent endEvt = new MethodExecutionEvent(this, "methodToMonitor", MethodExecutionStatus.END);
        this.eventPublisher.publishEvent(endEvt);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }
}
