package com.wenqi.springioc.applicationcontext.resource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * @author liangwenqi
 * @date 2022/12/27
 */
public class PathMatchingResourcePatternResolverDemo {
    public static void main(String[] args) {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        Resource fileResource = resourceResolver.getResource("D:/spring21site/README");
        System.out.println(fileResource instanceof ClassPathResource);
        System.out.println(fileResource.exists());
        resourceResolver = new PathMatchingResourcePatternResolver(new FileSystemResourceLoader());
        fileResource = resourceResolver.getResource("D:/spring21site/README");
        System.out.println(fileResource instanceof FileSystemResource);
        System.out.println(fileResource.exists());
    }
}
