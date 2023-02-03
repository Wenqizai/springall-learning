package com.wenqi.dao.jdbc;

import com.wenqi.DaoException;
import com.wenqi.DataSourceUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liangwenqi
 * @date 2023/2/3
 */
public class DAOWithA implements IDAO {

    @Override
    public int updateSomething(String sql) {
        int count;
        Connection con = null;
        Statement stmt = null;
        try {
            con = DataSourceUtil.getDataSource().getConnection();
            stmt = con.createStatement();
            count = stmt.executeUpdate(sql);
            stmt.close();
            stmt = null;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println("failed to close statement: " + e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.out.println("failed to close connection: " + e);
                }
            }
        }
        return count;
    }

    @Override
    public List<DaoPojo> selectList(String sql) {
        Connection con = null;
        Statement stmt = null;
        List<DaoPojo> daoPojoList = new ArrayList<>();
        try {
            con = DataSourceUtil.getDataSource().getConnection();
            stmt = con.createStatement();
            ResultSet rs1 = stmt.executeQuery(sql);
            while (rs1.next()) {
                String name = rs1.getString(1);
                int age = rs1.getInt(2);
                daoPojoList.add(new DaoPojo(name, age));
            }
            rs1.close();
            stmt.close();
            stmt = null;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println("failed to close statement: " + e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.out.println("failed to close connection: " + e);
                }
            }
        }
        return daoPojoList;
    }
}
