package com.wenqi.springaop.targetsource;

import com.wenqi.springaop.weave.intf.ITask;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.target.HotSwappableTargetSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author liangwenqi
 * @date 2023/1/30
 */
public class HotSwappableTargetSourceDemo {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:aop/spring-aop-context.xml");
        Object proxy3 = context.getBean("targetProxy3");
        Object initTarget = ((Advised) proxy3).getTargetSource().getTarget();

        HotSwappableTargetSource hotSwappableTargetSource = (HotSwappableTargetSource)context.getBean("hotSwappableTargetSource");
        Object oldTarget = hotSwappableTargetSource.swap(new ITask() {
            @Override
            public void execute() {
                System.out.println("new Task after swap");
            }
        });
        System.out.println(initTarget == oldTarget);
        System.out.println(initTarget.toString());
        System.out.println(oldTarget.toString());
    }
}
