package com.wenqi.springioc.applicationcontext.resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author liangwenqi
 * @date 2022/12/13
 */
public class DefaultResourceLoaderDemo {
    public static void main(String[] args) {

    }

    @Test
    public void test01() throws IOException {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource fakeFileResource = resourceLoader.getResource("D:/spring21site/README");
        Assert.assertTrue(fakeFileResource instanceof ClassPathResource);
        Assert.assertFalse(fakeFileResource.exists());

        Resource urlResource1 = resourceLoader.getResource("file:D:/spring21site/README");
        Assert.assertTrue(urlResource1 instanceof UrlResource);
        Resource urlResource2 = resourceLoader.getResource("http://www.spring21.cn");
        Assert.assertTrue(urlResource2 instanceof UrlResource);
        try {
            fakeFileResource.getFile();
            fail("no such file with path[" + fakeFileResource.getFilename() + "] exists in classpath");
        } catch (FileNotFoundException e) {

        }
        try {
            urlResource1.getFile();
        } catch (FileNotFoundException e) {
            fail();
        }
    }

    private void fail(String message) {
        System.out.println("fail with message: " + message);
    }

    private void fail() {
        System.out.println("fail.......");
    }
}
