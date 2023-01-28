package com.wenqi.springaop.advice.introduction;

/**
 * @author liangwenqi
 * @date 2023/1/28
 */
public class Developer implements IDeveloper {
    @Override
    public void developSoftware() {
        System.out.println("I am happy with programming");
    }
}
