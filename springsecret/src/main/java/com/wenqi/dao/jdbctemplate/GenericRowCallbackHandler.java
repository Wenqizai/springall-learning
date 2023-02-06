package com.wenqi.dao.jdbctemplate;

import com.wenqi.dao.common.DataSourceUtil;
import org.springframework.jdbc.core.JdbcTemplate;
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
        collections.add((T) customer);
    }

    public List<T> getResult() {
        return collections;
    }

    public void clear() {
        collections.clear();
    }

    public static void main(String[] args) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSourceUtil.getDataSource());
        GenericRowCallbackHandler<Customer> handler = new GenericRowCallbackHandler<>();
        jdbcTemplate.query("select * from customer", handler);
        List<Customer> result = handler.getResult();
        result.clear();
    }
}
