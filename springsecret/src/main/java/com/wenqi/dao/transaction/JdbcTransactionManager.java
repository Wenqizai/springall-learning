package com.wenqi.dao.transaction;

import com.wenqi.dao.common.DaoException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionStatus;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author liangwenqi
 * @date 2023/2/13
 */
public class JdbcTransactionManager implements PlatformTransactionManager {
    private DataSource dataSource;

    public JdbcTransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
        Connection connection;
        try {
            connection = dataSource.getConnection();
            TransactionResourceManager.bindResource(connection);
            return new DefaultTransactionStatus(connection, true, true, false, true, null);
        } catch (SQLException e) {
            throw new RuntimeException("can't get connection for tx", e);
        }
    }

    @Override
    public void commit(TransactionStatus status) throws TransactionException {
        Connection connection = (Connection) TransactionResourceManager.unbindResource();
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("commit failed with SQLException", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                // log
            }
        }
    }

    @Override
    public void rollback(TransactionStatus status) throws TransactionException {
        Connection connection = (Connection) TransactionResourceManager.unbindResource();
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException("rollback failed with SQLException", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                // log
            }
        }
    }
}
