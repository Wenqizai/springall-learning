package com.wenqi.mvc.servlet;

import com.mysql.cj.conf.HostInfo;
import com.mysql.cj.jdbc.ConnectionImpl;
import lombok.SneakyThrows;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 基于 {@link MockMagicServlet} 优化, 解耦数据接入层
 *
 * @author liangwenqi
 * @date 2023/3/17
 */
public class MockMagicServlet2 extends HttpServlet implements Servlet {

    private MockServletService mockServletService;

    public MockMagicServlet2() {
    }

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
        try (PrintWriter out = resp.getWriter()) {

            out.println("<html>");
            out.println("<head><title>Page Title<title><head>");
            out.println("<body>");
            out.println("<table width=\"200\"border=\"1\">");
            out.println("<tr><td>title1</td><td>Title2</td><td>Title3</td></tr>");

            List<InfoBean> infoList = mockServletService.query(paramName1, paramName2);

            for (InfoBean infoBean : infoList) {
                out.println("<tr>");
                out.println("<td>" + infoBean.getResult1() + "</td>");
                out.println("<td>" + infoBean.getResult2() + "</td>");
                out.println("</tr>");
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public MockServletService getMockServletService() {
        return mockServletService;
    }

    public void setMockServletService(MockServletService mockServletService) {
        this.mockServletService = mockServletService;
    }
}
