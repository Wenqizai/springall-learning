package com.wenqi.spring.bean.factory;

import com.wenqi.ioc.overview.domain.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * {@link com.wenqi.ioc.overview.domain.User} Bean 通过 FactoryBean 方式实现
 *
 * @author Wenqi Liang
 * @date 2022/8/21
 */
public class UserFactoryBean implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        return User.createUser();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
