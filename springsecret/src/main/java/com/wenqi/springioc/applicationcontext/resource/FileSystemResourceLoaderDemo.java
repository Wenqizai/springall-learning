package com.wenqi.springioc.applicationcontext.resource;

import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;

/**
 * @author liangwenqi
 * @date 2022/12/14
 */
public class FileSystemResourceLoaderDemo {

    @Test
    public void test01() {
        ResourceLoader resourceLoader = new FileSystemResourceLoader();
        Resource fileResource = resourceLoader.getResource("D:/spring21site/README");
        System.out.println(fileResource instanceof FileSystemResource);
        System.out.println(fileResource.exists());
        Resource urlResource = resourceLoader.getResource("file:D:/spring21site/README");
        System.out.println(urlResource instanceof UrlResource);
    }
}
