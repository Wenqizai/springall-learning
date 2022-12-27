package com.wenqi.springioc.applicationcontext.resource.beaninject;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

/**
 * @author liangwenqi
 * @date 2022/12/27
 */
public class FooBarApi2 implements ApplicationContextAware {
    private ResourceLoader resourceLoader;

    public void foo(String location) {
        System.out.println(getResourceLoader().getResource(location).getClass());
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.resourceLoader = applicationContext;
    }
}
