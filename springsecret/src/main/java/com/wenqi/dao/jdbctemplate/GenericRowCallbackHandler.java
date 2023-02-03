package com.wenqi.dao.jdbctemplate;

import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liangwenqi
 * @date 2023/2/3
 */
public class GenericRowCallbackHandler<T> implements RowCallbackHandler {
    private final List<T> collections = new ArrayList<>();

    @Override
    public void processRow(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setFirstName(rs.getString(1));
        customer.setLastName(rs.getString(2));
        collections.add(customer);
    }
}
