package com.wenqi.springioc.applicationcontext.eventpublish.custom;

import com.wenqi.springioc.applicationcontext.eventpublish.MethodExecutionStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author liangwenqi
 * @date 2022/12/28
 */
public class MethodExecutionEventPublisher {

    private final List<MethodExecutionEventListener> listeners = new ArrayList<>();

    public void methodToMonitor() {
        MethodExecutionEvent event2Publish = new MethodExecutionEvent(this, "methodToMonitor");
        // 执行业务逻辑方法前发布事件
        publishEvent(MethodExecutionStatus.BEGIN, event2Publish);
        System.out.println("执行业务逻辑方法....");
        // 执行业务逻辑方法后发布事件
        publishEvent(MethodExecutionStatus.END, event2Publish);
    }

    protected void publishEvent(MethodExecutionStatus status, MethodExecutionEvent event) {
        List<MethodExecutionEventListener> copyListeners = new ArrayList<>(listeners);
        for (MethodExecutionEventListener listener : copyListeners) {
            if (Objects.equals(status, MethodExecutionStatus.BEGIN)) {
                listener.onMethodBegin(event);
            } else {
                listener.onMethodEnd(event);
            }
        }
    }

    public void addMethodExecutionEventListener(MethodExecutionEventListener listener) {
        this.listeners.add(listener);
    }

    public void removeMethodExecutionEventListener(MethodExecutionEventListener listener) {
        this.listeners.remove(listener);
    }

    public void removeAllListeners() {
        this.listeners.clear();
    }
}
