package com.wenqi.dao.jdbctemplate;

import com.wenqi.dao.common.UnExceptedDataAccessException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import java.sql.SQLException;

/**
 * @author liangwenqi
 * @date 2023/2/3
 */
public class ToySqlExceptionTranslator extends SQLErrorCodeSQLExceptionTranslator {
    @Override
    protected DataAccessException customTranslate(String task, String sql, SQLException sqlEx) {
        if (sqlEx.getErrorCode() == 123456) {
            String msg = new StringBuffer()
                    .append("unExcepted data access exception raised when executing.")
                    .append(task)
                    .append(" with SQL> ")
                    .append(sql)
                    .toString();
            return new UnExceptedDataAccessException(msg, sqlEx);
        }
        return null;
    }
}
