package com.wenqi.springioc.instance.xml.factory.factorybean;

import org.springframework.beans.factory.FactoryBean;

import java.time.LocalDate;

/**
 * @author Wenqi Liang
 * @date 2022/11/27
 */
public class NextDayDateFactoryBean implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        return LocalDate.now().plusDays(1);
    }

    @Override
    public Class<?> getObjectType() {
        return LocalDate.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
