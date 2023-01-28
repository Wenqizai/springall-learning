package com.wenqi.springaop.weave.intf;

import com.wenqi.springaop.advice.PerformanceMethodInterceptor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;

/**
 * 基于接口的代理
 *
 * @author liangwenqi
 * @date 2023/1/28
 */
public class ProxyFactoryInterfaceDemo {
    public static void main(String[] args) {
        demo01();
        System.out.println("#################");
        demo02();
    }

    /**
     * 指定使用cglib代理
     */
    private static void demo02() {
        NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor();
        advisor.setMappedName("execute");
        advisor.setAdvice(new PerformanceMethodInterceptor());

        ProxyFactory weaver = new ProxyFactory(new MockTask());
        // 执行目标对象为类
        weaver.setProxyTargetClass(true);
        weaver.addAdvisor(advisor);

        ITask proxy = (ITask) weaver.getProxy();
        proxy.execute();

        // class com.wenqi.springaop.weave.intf.MockTask$$EnhancerBySpringCGLIB$$4336db79
        System.out.println((proxy.getClass()));
    }

    /**
     * 默认基于接口的jdk代理
     */
    private static void demo01() {
        NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor();
        advisor.setMappedName("execute");
        advisor.setAdvice(new PerformanceMethodInterceptor());

        MockTask mockTask = new MockTask();
        ProxyFactory weaver = new ProxyFactory(mockTask);
        // 可以不用, 这样weaver会检查目标对象所有实现的接口
        // weaver.setInterfaces(ITask.class);
        weaver.addAdvisor(advisor);

        ITask proxy = (ITask) weaver.getProxy();
        proxy.execute();

        // class com.sun.proxy.$Proxy0
        System.out.println((proxy.getClass()));
    }
}
