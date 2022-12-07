package com.wenqi.springioc.instance.method;

import com.wenqi.springioc.instance.IFXNewsPersister;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * @author Wenqi Liang
 * @date 2022/11/28
 */
public class MockNewsPersister2 implements IFXNewsPersister, BeanFactoryAware {
    private BeanFactory beanFactory;

    public void persistNews(FXNewsBean beanFactory) {
        persistNewes();
    }

    public void persistNewes() {
        System.out.println("persist bean: " + getNewsBean());
    }

    public FXNewsBean getNewsBean() {
        return (FXNewsBean) beanFactory.getBean("newsBean");
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
