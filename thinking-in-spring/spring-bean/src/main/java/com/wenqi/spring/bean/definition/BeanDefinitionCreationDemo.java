package com.wenqi.spring.bean.definition;

import com.wenqi.ioc.overview.domain.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * {@link org.springframework.beans.factory.config.BeanDefinition} 构建示例
 *
 * 1. BeanDefinitionBuilder
 * 2. AbstractBeanDefinition 以及派生类
 *
 * @author Wenqi Liang
 * @date 2022/8/21
 */
public class BeanDefinitionCreationDemo {
    public static void main(String[] args) {
        // 1. 通过 BeanDefinitionBuilder 构建
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        // 通过属性设置
        beanDefinitionBuilder
                .addPropertyValue("id", 34)
                .addPropertyValue("name", "BeanDefinitionBuilder 属性设置");
        // 获取实列
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        // BeanDefinition 并非 Bean 终态, 可以自定义修改

        // 2. 通过 AbstractBeanDefinition 以及派生类
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        // 设置 Bean 类型
        genericBeanDefinition.setBeanClass(User.class);
        // 通过 MutablePropertyValues 批量操作属性
        MutablePropertyValues propertyValues = new MutablePropertyValues();
//        propertyValues.addPropertyValue("id", 35);
//        propertyValues.addPropertyValue("name", "GenericBeanDefinition 属性设置");
        propertyValues.add("id", 35)
                .add("name", "GenericBeanDefinition 属性设置");
        // 通过 set MutablePropertyValues 批量操作属性
        genericBeanDefinition.setPropertyValues(propertyValues);
        System.out.println("占位....");
    }
}
