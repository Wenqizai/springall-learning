package com.wenqi.springioc.instance;

import cn.hutool.crypto.SecureUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author Wenqi Liang
 * @date 2022/12/12
 */
public class PasswordDecodePostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof PasswordDecodable) {
            String encodedPassword = ((PasswordDecodable) bean).getEncodedPassword();
            String decodePassword = decodePassword(encodedPassword);
            ((PasswordDecodable) bean).setDecodedPassword(decodePassword);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private String decodePassword(String encodedPassword) {
        return SecureUtil.des().decryptStr(encodedPassword);
    }
}
