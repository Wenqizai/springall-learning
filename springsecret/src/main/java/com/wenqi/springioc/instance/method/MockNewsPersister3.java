package com.wenqi.springioc.instance.method;

import com.wenqi.springioc.instance.IFXNewsPersister;
import org.springframework.beans.factory.ObjectFactory;


/**
 * @author liangwenqi
 * @date 2022/12/7
 */
public class MockNewsPersister3 implements IFXNewsPersister {
    private ObjectFactory newsBeanFactory;

    public void persistNews(FXNewsBean beanFactory) {
        persistNewes();
    }

    public void persistNewes() {
        System.out.println("persist bean: " + getNewsBean());
    }

    public FXNewsBean getNewsBean() {
        return (FXNewsBean) newsBeanFactory.getObject();
    }

    public void setNewsBeanFactory(ObjectFactory newsBeanFactory) {
        this.newsBeanFactory = newsBeanFactory;
    }
}
