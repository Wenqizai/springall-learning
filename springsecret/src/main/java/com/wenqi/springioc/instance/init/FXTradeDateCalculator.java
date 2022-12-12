package com.wenqi.springioc.instance.init;

import org.springframework.orm.ibatis.SqlMapClientOperations;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Wenqi Liang
 * @date 2022/12/12
 */
public class FXTradeDateCalculator {
    public static final DateTimeFormatter FRONT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final Set<LocalDate> holidaySet = new HashSet<>();
    private static final String holidayKey = "JPY";
    private SqlMapClientTemplate sqlMapClientTemplate;

    public FXTradeDateCalculator(SqlMapClientTemplate sqlMapClientTemplate) {
        this.sqlMapClientTemplate = sqlMapClientTemplate;
    }

    /**
     * 使用init-method, 确保被调用, holidaySet已经完成初始化
     * 当然实现InitializingBean接口, 重写方法也可以完成相同效果
     */
    public void setupHolidays() {
        List<LocalDate> holidays = getSystemHolidays();
        if (CollectionUtils.isEmpty(holidays)) {
            for (int i = 0; i < holidays.size(); i++) {
                String holiday = String.valueOf(holidays.get(i));
                LocalDate date = LocalDate.parse(holiday, FRONT_DATE_FORMATTER);
                holidaySet.add(date);
            }
        }
    }

    public DateCalculator<LocalDate> getForwardDateCalculator() {
        return LocalDateKitCalculatorsFactory
                .getDefaultInstance()
                .getDateCalculator(holidayKey, HolidayHandlerType.FORWARD);
    }

    public DateCalculator<LocalDate> getBackwardDateCalculator() {
        return LocalDateKitCalculatorsFactory
                .getDefaultInstance()
                .getDateCalculator(holidayKey, HolidayHandlerType.BACKWARD);
    }

    public List getSystemHolidays() {
        return getSqlMapClientTemplate().queryForList("CommonContext.holiday", null);
    }

    private SqlMapClientOperations getSqlMapClientTemplate() {
        return sqlMapClientTemplate;
    }
}
