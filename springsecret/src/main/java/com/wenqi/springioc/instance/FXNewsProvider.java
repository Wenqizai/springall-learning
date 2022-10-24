package com.wenqi.springioc.instance;

/**
 * @author liangwenqi
 * @date 2022/10/24
 */
public class FXNewsProvider {
    private IFXNewsListener newsListener;
    private IFXNewsPersister newPersistener;

    public FXNewsProvider(IFXNewsListener listener,IFXNewsPersister persister) {
        this.newsListener = listener;
        this.newPersistener = persister;
    }

    public void setNewsListener(IFXNewsListener newsListener) {
        this.newsListener = newsListener;
    }

    public void setNewPersistener(IFXNewsPersister newPersistener) {
        this.newPersistener = newPersistener;
    }

    public void getAndPersistNews() {

    }
}
