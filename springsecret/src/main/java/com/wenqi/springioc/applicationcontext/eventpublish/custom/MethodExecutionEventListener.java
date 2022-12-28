package com.wenqi.springioc.applicationcontext.eventpublish.custom;

import java.util.EventListener;

/**
 * 针对不同的事件发布时机提供相应的处理方法定义
 *
 * @author liangwenqi
 * @date 2022/12/28
 */
public interface MethodExecutionEventListener extends EventListener {
    /**
     * 处理方法开始执行的时候发布的MethodExecutionEvent事件
     */
    void onMethodBegin(MethodExecutionEvent event);

    /**
     * 处理方法执行将结束时候发布的MethodExecutionEvent事件
     */
    void onMethodEnd(MethodExecutionEvent event);
}
