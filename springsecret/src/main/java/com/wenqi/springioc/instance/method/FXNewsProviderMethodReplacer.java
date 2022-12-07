package com.wenqi.springioc.instance.method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;

/**
 * @author liangwenqi
 * @date 2022/12/7
 */
public class FXNewsProviderMethodReplacer implements MethodReplacer {
    private static final transient Log logger = LogFactory.getLog(FXNewsProviderMethodReplacer.class);

    @Override
    public Object reimplement(Object target, Method method, Object[] args) throws Throwable {
        logger.info("before executing method[" + method.getName() + "] on Object[" + target.getClass().getName()+"].");
        System.out.println("sorry, We will do nothing this time.");
        logger.info("end of executing method[" + method.getName() + "] on Object[" + target.getClass().getName()+"].");
        return null;
    }
}
