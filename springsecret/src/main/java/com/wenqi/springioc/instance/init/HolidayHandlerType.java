package com.wenqi.springioc.instance.init;

/**
 * @author Wenqi Liang
 * @date 2022/12/12
 * @desc
 */
public enum HolidayHandlerType {

    FORWARD, BACKWARD;

    private String value;
    private String desc;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
