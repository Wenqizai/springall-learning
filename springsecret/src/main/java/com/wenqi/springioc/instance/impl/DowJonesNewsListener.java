package com.wenqi.springioc.instance.impl;

import com.wenqi.springioc.instance.IFXNewsListener;
import com.wenqi.springioc.instance.PasswordDecodable;
import com.wenqi.springioc.instance.method.FXNewsBean;
import org.springframework.stereotype.Component;

/**
 * @author liangwenqi
 * @date 2022/10/24
 */
@Component
public class DowJonesNewsListener implements IFXNewsListener, PasswordDecodable {
    private String password;

    public String[] getAvailableNewsIds() {
        // 省略
        return null;
    }

    public FXNewsBean getNewsByPK(String newsId) {
        // 省略
        return null;
    }

    public void postProcessIfNecessary(String newsId) {
        // 省略
    }

    public String getEncodedPassword() {
        return this.password;
    }

    public void setDecodedPassword(String password) {
        this.password = password;
    }
}
