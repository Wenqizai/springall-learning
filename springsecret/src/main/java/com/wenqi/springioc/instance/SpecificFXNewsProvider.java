package com.wenqi.springioc.instance;

import com.wenqi.springioc.instance.FXNewsProvider;
import com.wenqi.springioc.instance.IFXNewsListener;
import com.wenqi.springioc.instance.IFXNewsPersister;

/**
 * @author Wenqi Liang
 * @date 2022/11/20
 */
public class SpecificFXNewsProvider extends FXNewsProvider {
    private IFXNewsListener newsListener;
    private IFXNewsPersister newPersistener;

    public IFXNewsListener getNewsListener() {
        return newsListener;
    }

    @Override
    public void setNewsListener(IFXNewsListener newsListener) {
        this.newsListener = newsListener;
    }

    public IFXNewsPersister getNewPersistener() {
        return newPersistener;
    }

    @Override
    public void setNewPersistener(IFXNewsPersister newPersistener) {
        this.newPersistener = newPersistener;
    }
}
