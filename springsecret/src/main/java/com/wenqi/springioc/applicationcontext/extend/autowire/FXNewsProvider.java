package com.wenqi.springioc.applicationcontext.extend.autowire;

import com.wenqi.springioc.instance.IFXNewsListener;
import com.wenqi.springioc.instance.IFXNewsPersister;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wenqi Liang
 * @date 2023/1/8
 */
public class FXNewsProvider {
    @Autowired
    private IFXNewsListener newsListener;
    @Autowired
    private IFXNewsPersister newsPersister;

    @Autowired
    public FXNewsProvider(IFXNewsListener newsListener, IFXNewsPersister newsPersister) {
        this.newsListener = newsListener;
        this.newsPersister = newsPersister;
    }

    public FXNewsProvider() {
    }
}
