package com.wenqi.ioc.overview.dependency.lookup;

import com.wenqi.ioc.overview.annotation.Super;
import com.wenqi.ioc.overview.domain.SuperUser;
import com.wenqi.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * 依赖查找示例
 * 1. 通过名称查找
 *
 * @author Wenqi Liang
 * @date 2022/8/6
 */
public class DependencyLookupDemo {
    public static void main(String[] args) {
        // 配置XML配置文件
        // 启动Spring应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:META-INF/dependency-lookup-context.xml");
//        lookupSuperInRealTime(beanFactory);
//        lookupInRealTime(beanFactory);
//        lookupInLazy(beanFactory);
//        lookUpByType(beanFactory);
//        lookupByCollectionType(beanFactory);

        // 通过注解查找Bean
        lookUpByAnnotation(beanFactory);
    }

    /**
     * 根据注解查找
     *
     * @param beanFactory
     */
    private static void lookUpByAnnotation(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            // key -> bean name, value -> 对象
            Map<String, User> users = (Map)listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("查找到所有 @Super 的 User 集合对象: " + users);
        }
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

    /**
     * 根据类型查找
     *
     * @param beanFactory
     */
    private static void lookUpByType(BeanFactory beanFactory) {
        User user = beanFactory.getBean(User.class);
        System.out.println("byType 实时查找: " + user);
    }

    /**
     * 根据名称查找
     *
     * 实时查找Bean
     * @param beanFactory
     */
    private static void lookupSuperInRealTime(BeanFactory beanFactory) {
        SuperUser superUser = (SuperUser) beanFactory.getBean(SuperUser.class);
        System.out.println("byName-super-user 实时查找: " + superUser);
    }

    /**
     * 根据名称查找
     *
     * 实时查找Bean
     * @param beanFactory
     */
    private static void lookupInRealTime(BeanFactory beanFactory) {
        User user = (User) beanFactory.getBean("user");
        System.out.println("byName 实时查找: " + user);
    }

    /**
     * 根据名称查找
     *
     * 延时查找Bean
     * @param beanFactory
     */
    private static void lookupInLazy(BeanFactory beanFactory) {
        ObjectFactory objectFactory = (ObjectFactory) beanFactory.getBean("objectFactory");
        User user = (User) objectFactory.getObject();
        System.out.println("byName 延时查找: " + user);
    }
}
