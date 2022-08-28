package com.wenqi.spring.bean.definition;

import com.wenqi.spring.bean.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @author Wenqi Liang
 * @date 2022/8/28
 */
@Configuration
public class BeanGarbageCollectionDemo {
    public static void main(String[] args) throws InterruptedException {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration class (配置类)
        applicationContext.register(BeanInitializationDemo.class);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 关闭 Spring 应用上下文
        applicationContext.close();
        System.out.println("Spring 应用上下文已关闭 ...");
        Thread.sleep(5000L);
        // 强制触发 GC
        System.gc();
        Thread.sleep(5000L);
    }
}
