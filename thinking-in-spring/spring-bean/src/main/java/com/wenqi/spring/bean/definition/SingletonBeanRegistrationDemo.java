package com.wenqi.spring.bean.definition;

import com.wenqi.spring.bean.factory.DefaultUserFactory;
import com.wenqi.spring.bean.factory.UserFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Wenqi Liang
 * @date 2022/8/28
 */
public class SingletonBeanRegistrationDemo {
    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 创建一个外部 userFactory 对象
        DefaultUserFactory userFactory = new DefaultUserFactory();
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        // 注册外部单例对象
        beanFactory.registerSingleton("userFactory", userFactory);
        // 启动 Spring 应用上下文
        applicationContext.refresh();

        // 通过依赖查找的方式来获取 UserFactory
        UserFactory userFactoryByLookUp = beanFactory.getBean("userFactory", UserFactory.class);
        System.out.println("userFactory == userFactoryByLookUp : " + (userFactory == userFactoryByLookUp));

        // 关闭 Spring 应用上下文
        applicationContext.close();
    }
}
