package com.wenqi.springaop.weave.proxyfactorybean.introduction;

/**
 * @author liangwenqi
 * @date 2023/1/30
 */
public class CounterImpl implements ICounter {
    private int counter;

    @Override
    public void resetCounter() {
        counter = 0;
    }

    @Override
    public int getCounter() {
        counter++;
        return counter;
    }

}
