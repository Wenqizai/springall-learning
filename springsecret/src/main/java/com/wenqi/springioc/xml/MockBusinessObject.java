package com.wenqi.springioc.xml;

/**
 * @author liangwenqi
 * @date 2022/10/26
 */
public class MockBusinessObject {
    private String dependency1;
    private int dependency2;

    public MockBusinessObject(String dependency1, int dependency2) {
        this.dependency1 = dependency1;
        this.dependency2 = dependency2;
    }

    public MockBusinessObject(String dependency1) {
        this.dependency1 = dependency1;
    }

    public MockBusinessObject(int dependency2) {
        this.dependency2 = dependency2;
    }

    @Override
    public String toString() {
        return "MockBusinessObject{" +
                "dependency1='" + dependency1 + '\'' +
                ", dependency2=" + dependency2 +
                '}';
    }
}
