package com.wenqi.mvc.servlet;

import com.mysql.cj.conf.HostInfo;
import com.mysql.cj.jdbc.ConnectionImpl;
import lombok.SneakyThrows;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MVC时代之前使用Servlet
 * 代码可读性差, 数据访问逻辑, 业务处理逻辑, 视图渲染逻辑耦合在一起
 *
 * @author liangwenqi
 * @date 2023/3/17
 */
public class MockMagicServlet extends HttpServlet implements Servlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String paramName1 = req.getParameter("paramName1");
        String paramName2 = req.getParameter("paramName2");

        // doSomething ...

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<head><title>Page Title<title><head>");
        out.println("<body>");
        out.println("<table width=\"200\"border=\"1\">");
        out.println("<tr><td>title1</td><td>Title2</td><td>Title3</td></tr>");

        String sql = "select * from SomeTable where Column1 = ? and Column2 = ?";
        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, paramName1);
            ps.setInt(2, Integer.parseInt(paramName2));
            // 其他可能的参数设置
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString(1) + "</td>");
                out.println("<td>" + rs.getString(2) + "</td>");
                out.println("</tr>");
            }
            conn.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        out.close();
    }

    private Connection getConnection() throws SQLException {
        return new ConnectionImpl(new HostInfo());
    }


}
