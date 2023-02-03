package com.wenqi.dao.common;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;

/**
 * @author liangwenqi
 * @date 2023/2/3
 */
public class DataSourceUtil {
    private DataSourceUtil() {
    }

    public static DataSource getDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("");
        druidDataSource.setUrl("");
        druidDataSource.setUsername("");
        druidDataSource.setPassword("");
        return druidDataSource;
    }
}
