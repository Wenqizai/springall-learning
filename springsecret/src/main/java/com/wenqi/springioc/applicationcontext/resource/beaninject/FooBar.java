package com.wenqi.springioc.applicationcontext.resource.beaninject;

import org.springframework.core.io.ResourceLoader;

/**
 * @author liangwenqi
 * @date 2022/12/27
 */
public class FooBar {
    private ResourceLoader resourceLoader;

    public void foo(String location) {
        System.out.println(getResourceLoader().getResource(location).getClass());
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
