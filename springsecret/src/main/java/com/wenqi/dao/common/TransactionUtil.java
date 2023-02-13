package com.wenqi.dao.common;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author liangwenqi
 * @date 2023/2/13
 */
public class TransactionUtil {
    public static DataSourceTransactionManager getTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
