package com.wenqi.springaop.weave;

import com.wenqi.springaop.advice.introduction.Developer;
import com.wenqi.springaop.advice.introduction.IDeveloper;
import com.wenqi.springaop.advice.introduction.ITester;
import com.wenqi.springaop.advice.introduction.TesterFeatureIntroductionInterceptor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultIntroductionAdvisor;

/**
 * @author liangwenqi
 * @date 2023/1/28
 */
public class IntroductionProxyFactoryDemo {
    public static void main(String[] args) {
        TesterFeatureIntroductionInterceptor advice = new TesterFeatureIntroductionInterceptor();

        ProxyFactory weaver = new ProxyFactory(new Developer());
        weaver.setProxyTargetClass(true);
        weaver.setInterfaces(IDeveloper.class);
        weaver.addAdvice(advice);

        // DefaultIntroductionAdvisor advisor = new DefaultIntroductionAdvisor(advice, advice);
        // weaver.addAdvisor(advisor);

        Object proxy = weaver.getProxy();

        ((ITester)proxy).testSoftware();
        ((Developer)proxy).developSoftware();
    }
}
