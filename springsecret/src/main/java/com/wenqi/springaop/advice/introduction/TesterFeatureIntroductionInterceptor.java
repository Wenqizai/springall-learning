package com.wenqi.springaop.advice.introduction;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

/**
 * @author liangwenqi
 * @date 2023/1/28
 */
public class TesterFeatureIntroductionInterceptor extends DelegatingIntroductionInterceptor implements ITester {

    private boolean busyAsTester;

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        if (isBusyAsTester() && mi.getMethod().getName().contains("developSoftware")) {
            throw new RuntimeException("你想累死我呀?");
        }
        return super.invoke(mi);
    }

    @Override
    public boolean isBusyAsTester() {
        return busyAsTester;
    }

    @Override
    public void testSoftware() {
        System.out.println("I will ensure the quality.");
    }
}
