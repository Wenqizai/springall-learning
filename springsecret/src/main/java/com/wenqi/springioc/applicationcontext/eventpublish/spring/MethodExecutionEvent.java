package com.wenqi.springioc.applicationcontext.eventpublish.spring;

import com.wenqi.springioc.applicationcontext.eventpublish.MethodExecutionStatus;
import org.springframework.context.ApplicationEvent;

/**
 * 对方法执行情况进行发布和监听
 *
 * @author liangwenqi
 * @date 2022/12/28
 */
public class MethodExecutionEvent extends ApplicationEvent {

    private static final long serialVersionUID = 2320653480L;
    private String methodName;
    private MethodExecutionStatus methodExecutionStatus;

    public MethodExecutionEvent(Object source) {
        super(source);
    }

    public MethodExecutionEvent(Object source, String methodName, MethodExecutionStatus methodExecutionStatus) {
        super(source);
        this.methodName = methodName;
        this.methodExecutionStatus = methodExecutionStatus;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public MethodExecutionStatus getMethodExecutionStatus() {
        return methodExecutionStatus;
    }

    public void setMethodExecutionStatus(MethodExecutionStatus methodExecutionStatus) {
        this.methodExecutionStatus = methodExecutionStatus;
    }
}
