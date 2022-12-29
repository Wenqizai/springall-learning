package com.wenqi.springioc.applicationcontext.eventpublish.spring;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 *
 * @author liangwenqi
 * @date 2022/12/28
 */
public class MethodExecutionEventListener implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof MethodExecutionEvent) {
            // 执行MethodExecutionEvent相关的业务逻辑
            System.out.println("MethodExecutionEvent do something ...");
            System.out.println("execute the method[" + ((MethodExecutionEvent) event).getMethodName() + "]. time: " + System.currentTimeMillis());
        }
    }
}
