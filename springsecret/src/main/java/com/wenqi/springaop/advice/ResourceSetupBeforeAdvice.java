package com.wenqi.springaop.advice;

import cn.hutool.core.io.FileUtil;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.core.io.Resource;

import java.lang.reflect.Method;

/**
 * before advice 在使用资源之前创建资源if not exist
 * @author liangwenqi
 * @date 2023/1/27
 */
public class ResourceSetupBeforeAdvice implements MethodBeforeAdvice {
    private Resource resource;

    public ResourceSetupBeforeAdvice(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        if (!resource.exists()) {
            FileUtil.mkdir(resource.getFile());
        }
    }
}
