package com.wenqi.springaop.advice;

/**
 * @author liangwenqi
 * @date 2023/1/27
 */
public class IntRange {
    private Integer lowRatio;
    private Integer highRatio;

    public IntRange(Integer lowRatio, Integer highRatio) {
        this.lowRatio = lowRatio;
        this.highRatio = highRatio;
    }

    public Integer getLowRatio() {
        return lowRatio;
    }

    public void setLowRatio(Integer lowRatio) {
        this.lowRatio = lowRatio;
    }

    public Integer getHighRatio() {
        return highRatio;
    }

    public void setHighRatio(Integer highRatio) {
        this.highRatio = highRatio;
    }

    public boolean containsInteger(Integer discountRatio) {
        return lowRatio <= discountRatio && discountRatio >= highRatio;
    }
}
