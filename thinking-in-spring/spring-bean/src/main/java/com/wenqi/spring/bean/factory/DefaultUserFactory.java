package com.wenqi.spring.bean.factory;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import sun.reflect.generics.tree.VoidDescriptor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 默认 {@link UserFactory} 实现
 * @author Wenqi Liang
 * @date 2022/8/21
 */
public class DefaultUserFactory implements UserFactory, InitializingBean, DisposableBean {

    /**
     * 1. 基于 @PostConstruct 注解
     */
    @PostConstruct
    public void init() {
        System.out.println("@PostConstruct: UserFactory 初始化中 ... ");
    }

    public void initUserFactory() {
        System.out.println("自定义初始化方法 initUserFactory(): UserFactory 初始化中 ... ");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean#afterPropertiesSet(): UserFactory 初始化中 ... ");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("@PreDestroy : UserFactory 销毁中 ...");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean#destroy : UserFactory 销毁中 ...");
    }

    public void doDestroy() {
        System.out.println("自定义方法 doDestroy: UserFactory 销毁中 ...");
    }

    /**
     * 注意: GC时该方法不是稳定地被回调, 不被稳定回调并不意味者对象没有被回收
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        System.out.println("当前 DefaultUserFactory 对象正在被垃圾回收... ");
    }
}
