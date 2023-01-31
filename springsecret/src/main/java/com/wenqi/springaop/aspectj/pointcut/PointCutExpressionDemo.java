package com.wenqi.springaop.aspectj.pointcut;

/**
 * @author liangwenqi
 * @date 2023/1/31
 */

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointCutExpressionDemo {

    @Pointcut("execution(void method1())")  // Pointcut expression
    public void method1Exception() {
    }

    @Pointcut("method1Exception()")  // Pointcut signature
    public void method2Exception() {
    }

    /* Pointcut声明方式的逻辑运算 */

    @Pointcut("execution(void method1())")
    private void method1Exec() { // 声明private仅在本aspect中引用, 声明public可被其他的aspect引用(这时可以定义一个公共的aspect用来定义所有的pointcut)
    }

    @Pointcut("execution(void method2())")
    private void method2Exec() {
    }

    @Pointcut("execution(void methodl()) || execution(void method2())")
    public void bothMethodExec() {
    }

    @Pointcut("method1Exec() || method2Exec ()")
    public void bothMethodExec2() {
    }
}
