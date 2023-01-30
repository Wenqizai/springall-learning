package com.wenqi.springaop.weave.proxyfactorybean;

import com.wenqi.springaop.weave.intf.MockTask;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author liangwenqi
 * @date 2023/1/30
 */
public class ProxyFactoryBeanDemo {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:aop/spring-aop-context.xml");
        MockTask taskProxy = (MockTask) context.getBean("taskProxy");
        taskProxy.execute();

        // class com.wenqi.springaop.weave.intf.MockTask$$EnhancerBySpringCGLIB$$1dfd946d
        // because of (proxyTargetClass == true)
        System.out.println(context.getBean("taskProxy").getClass());
    }
}
