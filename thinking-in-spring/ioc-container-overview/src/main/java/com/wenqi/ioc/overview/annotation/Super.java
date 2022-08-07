package com.wenqi.ioc.overview.annotation;

import java.lang.annotation.*;

/**
 * 标注用户注解
 *
 * @author Wenqi Liang
 * @date 2022/8/7
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Super {
}
