package com.wenqi.feign.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * @author Wenqi Liang
 * @date 2022/7/30
 */
public class Service implements BeanPostProcessor, ApplicationContextAware {

    ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        final Class beanNameClz;
        try {
            String beanNameOfFeignClientFactoryBean = "org.springframework.cloud.openfeign.FeignClientFactoryBean";
            beanNameClz = Class.forName(beanNameOfFeignClientFactoryBean);
        } catch (ClassNotFoundException e) {
            return bean;
        }

        Map beansOfType = applicationContext.getBeansOfType(beanNameClz);
        System.out.println(beansOfType);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        final Class beanNameClz;
        try {
            String beanNameOfFeignClientFactoryBean = "org.springframework.cloud.openfeign.FeignClientFactoryBean";
            beanNameClz = Class.forName(beanNameOfFeignClientFactoryBean);
        } catch (ClassNotFoundException e) {
            return bean;
        }

        applicationContext.getBeansOfType(beanNameClz).forEach((feignBeanName, beanOfFeignClientFactoryBean) -> {
            System.out.println(feignBeanName + "--->" + beanOfFeignClientFactoryBean);
        });
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
