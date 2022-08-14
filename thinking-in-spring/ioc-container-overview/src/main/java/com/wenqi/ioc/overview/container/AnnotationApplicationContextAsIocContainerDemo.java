package com.wenqi.ioc.overview.container;

import com.wenqi.ioc.overview.domain.User;
import javafx.application.Application;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 注解能力
 *
 * @author Wenqi Liang
 * @date 2022/8/13
 */
@Configuration
public class AnnotationApplicationContextAsIocContainerDemo {
    public static void main(String[] args) {
        // 创建BeanFactory容器
        // DefaultListableBeanFactory implements BeanDefinitionRegistry
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 AnnotationApplicationContextAsIocContainerDemo 作为配置类 (Configuration.class)
        annotationConfigApplicationContext.register(AnnotationApplicationContextAsIocContainerDemo.class);
        // 启动应用上下文
        annotationConfigApplicationContext.refresh();
        // 依赖查找集合对象
        lookupByCollectionType(annotationConfigApplicationContext);
        // 关闭应用上下文
        annotationConfigApplicationContext.close();
    }

    /**
     * 通过Java注解的方式, 定义了一个Bean
     *
     */
    @Bean
    public User user() {
        User user = new User();
        user.setId(1L);
        user.setName("小马哥");
        return user;
    }

    /**
     * 根据集合类型查找
     *
     * @param beanFactory
     */
    private static void lookupByCollectionType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            // key -> bean name, value -> 对象
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("查找到所有的 User 集合对象: " + users);
        }
    }
}
