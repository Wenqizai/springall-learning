package com.wenqi.springioc.beanfactory;

import com.wenqi.springioc.instance.FXNewsProvider;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @author liangwenqi
 * @date 2022/10/25
 */
public class XMLRegisterAndBind {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanRegistry = new DefaultListableBeanFactory();
        BeanFactory container = bindViaXMLFile(beanRegistry);
        FXNewsProvider newsProvider = (FXNewsProvider) container.getBean("fXNewsProvider");
        newsProvider.getAndPersistNews();
    }

    private static BeanFactory bindViaXMLFile(DefaultListableBeanFactory beanRegistry) {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanRegistry);
        reader.loadBeanDefinitions("classpath:beanfactory/news-config.xml");
        return beanRegistry;
        // 或者直接return
        //return new XmlBeanFactory(new ClassPathResource("../beanfactory/new-config.xml"));
    }
}
