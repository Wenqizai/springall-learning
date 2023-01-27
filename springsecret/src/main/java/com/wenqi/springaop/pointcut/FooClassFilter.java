package com.wenqi.springaop.pointcut;

import com.wenqi.springioc.instance.xml.factory.Foo;
import org.springframework.aop.ClassFilter;

import java.util.Objects;

/**
 * @author liangwenqi
 * @date 2023/1/27
 */
public class FooClassFilter implements ClassFilter {
    @Override
    public boolean matches(Class<?> clazz) {
        return Objects.equals(Foo.class, clazz);
    }
}
