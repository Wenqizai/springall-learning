package com.wenqi.dao.jdbctemplate;

import com.wenqi.dao.common.DataSourceUtil;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author liangwenqi
 * @date 2023/2/3
 */
public class Demo {
    public static void main(String[] args) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSourceUtil.getDataSource());
        jdbcTemplate.setExceptionTranslator(new ToySqlExceptionTranslator());
        // use jdbcTemplate doing something
    }
}
