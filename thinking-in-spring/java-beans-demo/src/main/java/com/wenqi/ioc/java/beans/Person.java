package com.wenqi.ioc.java.beans;

import lombok.ToString;

/**
 * @author Wenqi Liang
 * @date 2022/8/6
 */
@ToString
public class Person {
    String name;
    Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
