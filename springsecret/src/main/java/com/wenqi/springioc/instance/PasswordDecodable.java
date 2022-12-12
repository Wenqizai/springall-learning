package com.wenqi.springioc.instance;

/**
 * @author Wenqi Liang
 * @date 2022/12/12
 */
public interface PasswordDecodable {
    String getEncodedPassword();

    void setDecodedPassword(String password);
}
