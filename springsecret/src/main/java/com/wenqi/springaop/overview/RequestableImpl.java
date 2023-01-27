package com.wenqi.springaop.overview;

/**
 * @author liangwenqi
 * @date 2023/1/27
 */
public class RequestableImpl implements IRequestable {
    @Override
    public void request() {
        System.out.println("request processed in RequestableImpl");
    }
}
