package com.wenqi.springaop.weave.intf;

/**
 * @author liangwenqi
 * @date 2023/1/28
 */
public class MockTask implements ITask {
    @Override
    public void execute() {
        System.out.println("task executed.");
    }
}
