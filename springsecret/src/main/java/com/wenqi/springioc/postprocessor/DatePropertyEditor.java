package com.wenqi.springioc.postprocessor;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author liangwenqi
 * @date 2022/12/8
 */
public class DatePropertyEditor extends PropertyEditorSupport {
    private String datePattern;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(getDatePattern());
        LocalDate localDate = LocalDate.parse(text, dateTimeFormatter);
        setValue(Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
    }

    public String getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }
}
