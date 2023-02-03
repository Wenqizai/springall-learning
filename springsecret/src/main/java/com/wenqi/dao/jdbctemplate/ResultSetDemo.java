package com.wenqi.dao.jdbctemplate;

import com.wenqi.dao.common.DataSourceUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liangwenqi
 * @date 2023/2/3
 */
public class ResultSetDemo {
    public static void main(String[] args) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSourceUtil.getDataSource());
        String sql = "select * from customer";

        // 1. ResultSetExtractor
        List<Customer> list1 = jdbcTemplate.query(sql, new ResultSetExtractor<List<Customer>>() {
            @Override
            public List<Customer> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Customer> customerList = new ArrayList<>();
                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.setFirstName(rs.getString(1));
                    customer.setLastName(rs.getString(2));
                    customerList.add(customer);
                }
                return customerList;
            }
        });

        // 2. RowMapper
        List<Customer> list2 = jdbcTemplate.query(sql, new RowMapper<Customer>() {
            @Override
            public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
                Customer customer = new Customer();
                customer.setFirstName(rs.getString(1));
                customer.setLastName(rs.getString(2));
                return customer;
            }
        });

        // 2. RowCallbackHandler
        final List<Customer> customerList = new ArrayList<>();
        jdbcTemplate.query(sql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                Customer customer = new Customer();
                customer.setFirstName(rs.getString(1));
                customer.setLastName(rs.getString(2));
                customerList.add(customer);
            }
        });
    }
}
