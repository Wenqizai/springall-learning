package com.wenqi.spring.bean.definition;

import com.wenqi.spring.bean.factory.DefaultUserFactory;
import com.wenqi.spring.bean.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @author Wenqi Liang
 * @date 2022/8/28
 */
@Configuration
public class BeanInitializationDemo {
    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration class (配置类)
        applicationContext.register(BeanInitializationDemo.class);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 非延迟初始化在 Spring 应用上下文启动完成后, 进行初始化 (去掉 @Lazy 可以看效果)
        System.out.println("Spring 应用上下文已启动 ...");
        // 依赖查找 UserFactory
        UserFactory userFactory = applicationContext.getBean(UserFactory.class);
        System.out.println(userFactory);
        // 这里可以看出 Bean 的销毁是clone触发, 还是用完Bean之后触发的(答案是close触发)
        System.out.println("Spring 应用上下文准备关闭 ...");
        // 关闭 Spring 应用上下文
        applicationContext.close();
        System.out.println("Spring 应用上下文已关闭 ...");
    }

    @Bean(initMethod = "initUserFactory", destroyMethod = "doDestroy")
//    @Lazy
    public UserFactory userFactory() {
        return new DefaultUserFactory();
    }
}
