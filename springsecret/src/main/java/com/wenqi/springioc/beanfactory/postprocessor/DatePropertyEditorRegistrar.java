package com.wenqi.springioc.beanfactory.postprocessor;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

import java.beans.PropertyEditor;
import java.util.Date;

/**
 * @author liangwenqi
 * @date 2022/12/8
 */
public class DatePropertyEditorRegistrar implements PropertyEditorRegistrar {
    private PropertyEditor propertyEditor;

    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(Date.class, getPropertyEditor());
    }

    public PropertyEditor getPropertyEditor() {
        return propertyEditor;
    }

    public void setPropertyEditor(PropertyEditor propertyEditor) {
        this.propertyEditor = propertyEditor;
    }
}
