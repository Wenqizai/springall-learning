package com.wenqi.dao.common;

import org.springframework.dao.DataAccessException;

import java.sql.SQLException;

/**
 * @author liangwenqi
 * @date 2023/2/3
 */
public class UnExceptedDataAccessException extends DataAccessException {
    public UnExceptedDataAccessException(String msg, SQLException sqlEx) {
        super(msg, sqlEx);
    }
}
