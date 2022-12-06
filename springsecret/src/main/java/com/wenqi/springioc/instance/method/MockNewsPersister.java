package com.wenqi.springioc.instance.method;

import com.wenqi.springioc.instance.IFXNewsPersister;

/**
 * @author Wenqi Liang
 * @date 2022/11/28
 */
public class MockNewsPersister implements IFXNewsPersister {
    private FXNewsBean newsBean;

    public void persistNews(FXNewsBean beanFactory) {
        persistNewes();
    }

    public void persistNewes() {
        System.out.println("persist bean: " + getNewsBean());
    }

    public FXNewsBean getNewsBean() {
        return newsBean;
    }

    public void setNewsBean(FXNewsBean newsBean) {
        this.newsBean = newsBean;
    }
}
