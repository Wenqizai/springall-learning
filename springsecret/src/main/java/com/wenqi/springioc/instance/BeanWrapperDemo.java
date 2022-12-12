package com.wenqi.springioc.instance;

import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * @author Wenqi Liang
 * @date 2022/12/11
 */
public class BeanWrapperDemo {
    public static void main(String[] args) throws Exception {

    }

    @Test
    public void test() throws Exception {
        Object provider = Class.forName("com.wenqi.springioc.instance.FXNewsProvider").newInstance();
        Object listener = Class.forName("com.wenqi.springioc.instance.impl.DowJonesNewsListener").newInstance();
        Object persister = Class.forName("com.wenqi.springioc.instance.impl.DowJonesNewsPersister").newInstance();

        BeanWrapper newsProvider = new BeanWrapperImpl(provider);
        BeanWrapper newsListener = new BeanWrapperImpl(listener);
        BeanWrapper newsPersister = new BeanWrapperImpl(persister);

        assertTrue(newsProvider.getWrappedInstance() instanceof FXNewsProvider);
        assertSame(provider, newsProvider.getWrappedInstance());
        assertSame(listener, newsProvider.getPropertyValue("newsListener"));
        assertSame(persister, newsProvider.getPropertyValue("newPersistener"));
    }
}
