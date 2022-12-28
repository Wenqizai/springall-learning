package com.wenqi.springioc.applicationcontext.eventpublish.custom;

import java.util.EventObject;

/**
 * 对方法执行情况进行发布和监听
 *
 * @author liangwenqi
 * @date 2022/12/28
 */
public class MethodExecutionEvent extends EventObject {

    private static final long serialVersionUID = 549620653480L;
    private String methodName;

    public MethodExecutionEvent(Object source) {
        super(source);
    }

    public MethodExecutionEvent(Object source, String methodName) {
        super(source);
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
