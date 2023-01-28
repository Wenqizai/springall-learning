package com.wenqi.springaop.weave.clazz;

import com.wenqi.springaop.advice.PerformanceMethodInterceptor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;

/**
 * @author liangwenqi
 * @date 2023/1/28
 */
public class ProxyFactoryClazzDemo {
    public static void main(String[] args) {
        NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor();
        advisor.setMappedName("execute");
        advisor.setAdvice(new PerformanceMethodInterceptor());

        ProxyFactory weaver = new ProxyFactory(new Executable());
        weaver.addAdvisor(advisor);
        Executable proxy = (Executable) weaver.getProxy();
        proxy.execute();
        System.out.println(proxy.getClass());
    }
}
