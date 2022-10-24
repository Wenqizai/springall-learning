package com.wenqi.springioc.beanfactory;

import com.wenqi.springioc.instance.DowJonesNewsListener;
import com.wenqi.springioc.instance.DowJonesNewsPersister;
import com.wenqi.springioc.instance.FXNewsProvider;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean 的注册和绑定
 *
 * @author liangwenqi
 * @date 2022/10/24
 */
public class BeanRegisterAndBind {
    public static void main(String[] args) {
        // 同时具备 BeanFactory 和 BeanDefinitionRegistry 功能
        DefaultListableBeanFactory beanRegistry = new DefaultListableBeanFactory();
        BeanFactory container = bindViaCode(beanRegistry);
        FXNewsProvider newsProvider = (FXNewsProvider) container.getBean("fXNewsProvider");
        newsProvider.getAndPersistNews();
    }

    public static BeanFactory bindViaCode(BeanDefinitionRegistry registry) {
        AbstractBeanDefinition newsProvider = new RootBeanDefinition(FXNewsProvider.class, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
        AbstractBeanDefinition newsListener = new RootBeanDefinition(DowJonesNewsListener.class, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
        AbstractBeanDefinition newsPersister = new RootBeanDefinition(DowJonesNewsPersister.class, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);

        // 将bean定义注册到容器中
        registry.registerBeanDefinition("fXNewsProvider", newsProvider);
        registry.registerBeanDefinition("djListener", newsListener);
        registry.registerBeanDefinition("djPersister", newsPersister);

        // 指定依赖关系
        // 1. 可以通过构造方法注入方式
        ConstructorArgumentValues argValues = new ConstructorArgumentValues();
        argValues.addIndexedArgumentValue(0, newsListener);
        argValues.addIndexedArgumentValue(1, newsPersister);
        newsProvider.setConstructorArgumentValues(argValues);
        // 2. 或者通过setter方法注入方式
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("newsListener", newsListener));
        propertyValues.addPropertyValue(new PropertyValue("newsPersister", newsPersister));
        //newsProvider.setPropertyValues(propertyValues);

        // 绑定完成
        return (BeanFactory) registry;
    }
}
