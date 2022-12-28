package com.wenqi.springioc.applicationcontext.eventpublish.custom;

/**
 * @author liangwenqi
 * @date 2022/12/28
 */
public class SimpleMethodExecutionEventListener implements MethodExecutionEventListener {
    @Override
    public void onMethodBegin(MethodExecutionEvent event) {
        String methodName = event.getMethodName();
        System.out.println("start to execute the method[" + methodName + "].");
    }

    @Override
    public void onMethodEnd(MethodExecutionEvent event) {
        String methodName = event.getMethodName();
        System.out.println("finished to execute the method[" + methodName + "].");
    }
}
