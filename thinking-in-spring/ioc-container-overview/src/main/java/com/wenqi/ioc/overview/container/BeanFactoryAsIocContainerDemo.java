package com.wenqi.ioc.overview.container;

import com.wenqi.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * 底层容器示例
 *
 * @author Wenqi Liang
 * @date 2022/8/13
 */
public class BeanFactoryAsIocContainerDemo {
    public static void main(String[] args) {
        // 创建BeanFactory容器
        // DefaultListableBeanFactory implements BeanDefinitionRegistry
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // XML配置文件ClassPath路径
        String location = "classpath:META-INF/dependency-lookup-context.xml";
        // 加载配置
        int beanDefinitionCount = reader.loadBeanDefinitions(location);
        System.out.println("Bean定义加载Bean的数量: " + beanDefinitionCount);
        // 依赖查找集合对象
        lookupByCollectionType(beanFactory);
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
