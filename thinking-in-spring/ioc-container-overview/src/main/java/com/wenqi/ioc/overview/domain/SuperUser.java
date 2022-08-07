package com.wenqi.ioc.overview.domain;

import com.wenqi.ioc.overview.annotation.Super;
import lombok.ToString;

/**
 * 超级用户
 *
 * @author Wenqi Liang
 * @date 2022/8/7
 */
@Super
@ToString(callSuper = true)
public class SuperUser extends User {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
