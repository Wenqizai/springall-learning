package com.wenqi.dao.jdbctemplate;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author liangwenqi
 * @date 2023/2/6
 */
public class RowCallbackHandlerResultSetExtractor implements ResultSetExtractor {

    private final RowCallbackHandler rowCallbackHandler;

    public RowCallbackHandlerResultSetExtractor(RowCallbackHandler rowCallbackHandler) {
        this.rowCallbackHandler = rowCallbackHandler;
    }

    @Override
    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
        while (rs.next()) {
            this.rowCallbackHandler.processRow(rs);
        }
        return null;
    }
}
