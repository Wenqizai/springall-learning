package com.wenqi.springioc.applicationcontext.messageresource;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.support.StaticMessageSource;

import java.util.Locale;
import java.util.Objects;

/**
 * @author liangwenqi
 * @date 2022/12/28
 */
public class MessageResourceDemo {
    public static void main(String[] args) {
        StaticMessageSource messageSource = new StaticMessageSource();
        messageSource.addMessage("menu.file", Locale.US, "File");
        messageSource.addMessage("menu.edit", Locale.US, "Edit");

        System.out.println(Objects.equals("File(F)", messageSource.getMessage("menu.file", new Object[]{"F"}, Locale.US)));
        System.out.println(Objects.equals("Edit", messageSource.getMessage("menu.edit", null,"Edit", Locale.US)));

        ResourceBundleMessageSource messageSource1 = new ResourceBundleMessageSource();
        messageSource1.setBasenames(new String[]{"conf/messages"});// 从 classpath加载资源文件
        System.out.println(Objects.equals("File(F)", messageSource1.getMessage("menu.file", new Object[]{"F"}, Locale.US)));


        ReloadableResourceBundleMessageSource messageSource2 = new ReloadableResourceBundleMessageSource();
        messageSource2.setBasenames(new String[]{"file:conf/messages"}); // 从文件系统加载资源文件
        System.out.println(Objects.equals("File(F)", messageSource2.getMessage("menu.file", new Object[]{"F"}, Locale.US)));
    }
}
