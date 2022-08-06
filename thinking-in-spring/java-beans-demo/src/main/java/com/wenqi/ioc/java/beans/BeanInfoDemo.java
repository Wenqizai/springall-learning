package com.wenqi.ioc.java.beans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyEditorSupport;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * {@link java.beans.BeanInfo} 示例
 *
 * @author Wenqi Liang
 * @date 2022/8/6
 */
public class BeanInfoDemo {
    public static void main(String[] args) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class, Object.class);
        Stream.of(beanInfo.getPropertyDescriptors()).forEach(propertyDescriptor -> {
            // propertyDescriptor 允许调价属性编辑器 - PropertyEditor
            // 类型对应: GUI -> text(String) -> PropertyType
            // name -> String
            // age -> Integer
            // java.beans.PropertyDescriptor[name=age; propertyType=class java.lang.Integer; readMethod=public java.lang.Integer com.wenqi.ioc.java.beans.Person.getAge(); writeMethod=public void com.wenqi.ioc.java.beans.Person.setAge(java.lang.Integer)]
            // java.beans.PropertyDescriptor[name=class; propertyType=class java.lang.Class; readMethod=public final native java.lang.Class java.lang.Object.getClass()]
            // java.beans.PropertyDescriptor[name=name; propertyType=class java.lang.String; readMethod=public java.lang.String com.wenqi.ioc.java.beans.Person.getName(); writeMethod=public void com.wenqi.ioc.java.beans.Person.setName(java.lang.String)]
            Class<?> propertyType = propertyDescriptor.getPropertyType();
            String propertyName = propertyDescriptor.getName();

            // 为age属性增加PropertyEditor
            if (Objects.equals("age", propertyName)) {
                // age: 输入 String -> 输出 Integer
                propertyDescriptor.setPropertyEditorClass(StringToIntegerPropertyEditor.class);
            }
        });

    }

    static class StringToIntegerPropertyEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            Integer value = Integer.valueOf(text);
            setValue(value);
        }
    }
}
