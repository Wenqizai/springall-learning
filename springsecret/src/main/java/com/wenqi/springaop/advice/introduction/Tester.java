package com.wenqi.springaop.advice.introduction;

/**
 * @author liangwenqi
 * @date 2023/1/28
 */
public class Tester implements ITester{
    private boolean busyAsTester;

    @Override
    public boolean isBusyAsTester() {
        return busyAsTester;
    }

    @Override
    public void testSoftware() {
        System.out.println("I will ensure the quality.");
    }

    public void setBusyAsTester(boolean busyAsTester) {
        this.busyAsTester = busyAsTester;
    }
}
