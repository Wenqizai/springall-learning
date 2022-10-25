package com.wenqi.springioc.beanfactory;

import com.wenqi.springioc.instance.FXNewsProvider;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;

/**
 * @author liangwenqi
 * @date 2022/10/25
 */
public class PropertiesRegisterAndBind {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanRegistry = new DefaultListableBeanFactory();
        BeanFactory container = bindViaPropertiesFile(beanRegistry);
        FXNewsProvider newsProvider = (FXNewsProvider) container.getBean("fXNewsProvider");
        newsProvider.getAndPersistNews();
    }

    public static BeanFactory bindViaPropertiesFile(BeanDefinitionRegistry registry) {
        PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(registry);
        reader.loadBeanDefinitions("classpath:./beanfactory/binding-config.properties");
        return (BeanFactory) registry;
    }
}
