package com.wenqi.springioc.instance.destory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Wenqi Liang
 * @date 2022/12/12
 */
public class ApplicationLauncher {
    public static void main(String[] args) {
        beanFactoryDestroy();
        applicationContextDestroy();
    }

    private static void applicationContextDestroy() {
        BeanFactory container = new ClassPathXmlApplicationContext("...");
        Bean bean = (Bean) container.getBean("...");
        // 钩子犯法
        ((AbstractApplicationContext)container).registerShutdownHook();
    }

    private static void beanFactoryDestroy() {
        BeanFactory container = new XmlBeanFactory(new ClassPathResource("..."));
        Bean bean = (Bean) container.getBean("...");
        // 执行销毁方法
        ((ConfigurableListableBeanFactory)container).destroySingletons();
        // 应用程序退出，容器关闭
    }
}
