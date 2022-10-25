package com.wenqi.springioc.instance;

/**
 * @author liangwenqi
 * @date 2022/10/24
 */
public class FXNewsProvider {
    private IFXNewsListener newsListener;
    private IFXNewsPersister newPersistener;

    /**
     * 使用properties实例化和注册bean时, 必须要有空的构造器, 否则报错 No default constructor found
     * 因为 Spring 需要先创建实例, 再注入bean, 若不提供空的构造器, 就无法创建实例, 因此报错.
     */
    public FXNewsProvider() {
    }

    public FXNewsProvider(IFXNewsListener listener, IFXNewsPersister persister) {
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
        System.out.println("FXNewsProvider: getAndPersistNews method call ...");
    }
}
