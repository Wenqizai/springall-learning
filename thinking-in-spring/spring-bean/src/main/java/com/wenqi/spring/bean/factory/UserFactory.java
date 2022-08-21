package com.wenqi.spring.bean.factory;

import com.wenqi.ioc.overview.domain.User;

/**
 * {@link com.wenqi.ioc.overview.domain.User} 工厂类
 * @author Wenqi Liang
 * @date 2022/8/21
 */
public interface UserFactory {

    default User createUser() {
        return User.createUser();
    }
}
