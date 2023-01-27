package com.wenqi.springaop.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author liangwenqi
 * @date 2023/1/27
 */
public class DiscountMethodInterceptor implements MethodInterceptor {
    private static final Integer DEFAULT_DISCOUNT_RATIO = 80;
    private static final IntRange RATIO_RANGE = new IntRange(5, 95);

    private Integer discountRatio = DEFAULT_DISCOUNT_RATIO;
    private boolean campaignAvailable;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object returnValue = invocation.proceed();
        if (RATIO_RANGE.containsInteger(getDiscountRatio()) && isCampaignAvailable()) {
            return ((Integer)returnValue) * getDiscountRatio() / 10;
        }
        return returnValue;
    }

    public Integer getDiscountRatio() {
        return discountRatio;
    }

    public void setDiscountRatio(Integer discountRatio) {
        this.discountRatio = discountRatio;
    }

    public boolean isCampaignAvailable() {
        return campaignAvailable;
    }

    public void setCampaignAvailable(boolean campaignAvailable) {
        this.campaignAvailable = campaignAvailable;
    }
}
