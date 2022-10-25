package com.wenqi.springioc.instance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author liangwenqi
 * @date 2022/10/25
 */
@Component
public class FXNewsAnnoProvider {
    @Qualifier("dowJonesNewsListener")
    @Autowired
    private final IFXNewsListener newsListener;
    @Qualifier("dowJonesNewsPersister")
    @Autowired
    private final IFXNewsPersister newPersistener;

    public FXNewsAnnoProvider(@Qualifier("dowJonesNewsListener") IFXNewsListener newsListener, @Qualifier("dowJonesNewsPersister") IFXNewsPersister newPersistener) {
        this.newsListener = newsListener;
        this.newPersistener = newPersistener;
    }

    public void getAndPersistNews() {
        System.out.println("FXNewsAnnoProvider: getAndPersistNews method call ...");
    }
}
