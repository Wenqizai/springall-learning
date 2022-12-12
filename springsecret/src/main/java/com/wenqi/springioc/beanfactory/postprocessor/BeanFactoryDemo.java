package com.wenqi.springioc.beanfactory.postprocessor;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * @author liangwenqi
 * @date 2022/12/8
 */
public class BeanFactoryDemo {
    public static void main(String[] args) {
        // 此demo中可以看出ApplicationContext比BeanFactory智能多, 自动识别配置文件中的BeanFactoryPostProcess

        // 声明将被后处理的BeanFactory实例
        ConfigurableListableBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("classpath:beanfactory/postprocessor/spring-context.xml"));
        // 声明要使用的BeanFactoryPostProcessor
        PropertyPlaceholderConfigurer propertyPostProcessor = new PropertyPlaceholderConfigurer();
        propertyPostProcessor.setLocation(new ClassPathResource("beanfactory/postprocessor/conf/jdbc.properties"));
        // 执行后处理操作
        propertyPostProcessor.postProcessBeanFactory(beanFactory);
    }
}
