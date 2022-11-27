package com.wenqi.springioc.instance.xml.factory.factorybean;

import java.time.LocalDate;

/**
 * @author Wenqi Liang
 * @date 2022/11/27
 */
public class NextDayDateDisplayer {
    private LocalDate dateOfNextDay;

    public LocalDate getDateOfNextDay() {
        return dateOfNextDay;
    }

    public void setDateOfNextDay(LocalDate dateOfNextDay) {
        this.dateOfNextDay = dateOfNextDay;
    }
}
