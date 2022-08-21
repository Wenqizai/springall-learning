package com.wenqi.spring.bean.definition;

import com.wenqi.ioc.overview.domain.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.genericBeanDefinition;

/**
 * 注册 BeanDefinition 示例
 *
 * @author Wenqi Liang
 * @date 2022/8/21
 */
// 3. 通过 @Import 方式
@Import(AnnotationBeanDefinitionDemo.Config.class) // 通过 @Import 来进行导入
public class AnnotationBeanDefinitionDemo {
    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration class (配置类)
        applicationContext.register(Config.class);

        // 通过 BeanDefinition 注册 API 实现
        // 1. 命名 Bean 的注册方式
        registerUserBeanDefinition(applicationContext, "mercyblitz-user");
        // 2. 非命名方式注册 Bean
        registerBeanDefinition(applicationContext);

        // 启动 Spring 应用上下文
        applicationContext.refresh();

        // 通过注解方式注册 Bean
        // 1. 通过 @Bean 方式定义
        // 2. 通过 @Component 方式
        // 3. 通过 @Import 方式

        // 上述方式均可引入 Bean, 那么会重复引入么? (不会)
        System.out.println("config 类型的所有 Beans" + applicationContext.getBeansOfType(Config.class));
        System.out.println("User 类型的所有 Beans" + applicationContext.getBeansOfType(User.class));

        // 显示关闭 Spring 应用上下文
        applicationContext.close();
    }

    /**
     * 通过 API 方式注册 Bean
     * <p>
     * 命名 Bean 的注册方式
     *
     * @param registry
     * @param beanName
     */
    public static void registerUserBeanDefinition(BeanDefinitionRegistry registry, String beanName) {
        BeanDefinitionBuilder beanDefinitionBuilder = genericBeanDefinition(User.class);
        beanDefinitionBuilder
                .addPropertyValue("id", 2L)
                .addPropertyValue("name", "小马哥-2 ");

        // 判断如果 beanName 参数是否存在
        if (StringUtils.hasText(beanName)) {
            // 注册 BeanDefinition
            registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getRawBeanDefinition());
        } else {
            // 非命名方式注册 Bean
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(), registry);
        }
    }

    /**
     * 通过 API 方式注册 Bean
     * <p>
     * 非命名方式注册 Bean
     *
     * @param registry
     */
    public static void registerBeanDefinition(BeanDefinitionRegistry registry) {
        registerUserBeanDefinition(registry, null);
    }

    // 2. 通过 @Component 方式
    @Component // 定义当前类作为 Spring Bean (组件)
    public static class Config {

        // 1. 通过 @Bean 方式定义

        /**
         * 通过 Java 注解的方式, 定义了一个 Bean
         *
         * @return
         */
        @Bean(name = {"user", "xiaomage-user"})
        public User user() {
            User user = new User();
            user.setId(1L);
            user.setName("小马哥");
            return user;
        }
    }

}
