package com.wenqi.dao.transaction;

import com.wenqi.dao.common.DaoException;
import com.wenqi.dao.common.DataSourceUtil;
import com.wenqi.dao.common.MyTransactionDefinition;
import com.wenqi.dao.common.TransactionUtil;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import javax.sql.DataSource;

/**
 * @author liangwenqi
 * @date 2023/2/13
 */
public class FooService {
    private PlatformTransactionManager platformTransactionManager;

    public void serviceMethod() {
        MyTransactionDefinition definition = new MyTransactionDefinition();
        DataSourceTransactionManager transactionManager = TransactionUtil.getTransactionManager(DataSourceUtil.getDataSource());
        TransactionStatus transactionStatus = transactionManager.getTransaction(definition);
        try {
            // dao层 doing something

        } catch (DaoException e) {
            transactionManager.rollback(transactionStatus);
            throw e;
        }
        transactionManager.commit(transactionStatus);
    }
}
