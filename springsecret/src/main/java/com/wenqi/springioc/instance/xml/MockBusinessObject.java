package com.wenqi.springioc.instance.xml;

import java.util.List;

/**
 * @author liangwenqi
 * @date 2022/10/26
 */
public class MockBusinessObject {
    private String dependency1;
    private int dependency2;
    private List<String> dependency3;

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

    public String getDependency1() {
        return dependency1;
    }

    public void setDependency1(String dependency1) {
        this.dependency1 = dependency1;
    }

    public int getDependency2() {
        return dependency2;
    }

    public void setDependency2(int dependency2) {
        this.dependency2 = dependency2;
    }

    public List<String> getDependency3() {
        return dependency3;
    }

    public void setDependency3(List<String> dependency3) {
        this.dependency3 = dependency3;
    }

    @Override
    public String toString() {
        return "MockBusinessObject{" +
                "dependency1='" + dependency1 + '\'' +
                ", dependency2=" + dependency2 +
                '}';
    }
}
