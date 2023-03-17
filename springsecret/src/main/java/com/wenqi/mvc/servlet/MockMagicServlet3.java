package com.wenqi.mvc.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 基于 {@link MockMagicServlet2} 优化, 解耦视图渲染
 *
 * @author liangwenqi
 * @date 2023/3/17
 */
public class MockMagicServlet3 extends HttpServlet implements Servlet {

    private MockServletService mockServletService;

    public MockMagicServlet3() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

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

            req.setAttribute("infoList", infoList);
            forward(req, resp, "mvc/servlet/MockServlet.jsp");

        } catch (IOException | ServletException ioException) {
            ioException.printStackTrace();
        }
    }

    protected void forward(HttpServletRequest req, HttpServletResponse resp, String viewPath) throws ServletException, IOException {
        final RequestDispatcher requestDispatcher = req.getRequestDispatcher(viewPath);
        requestDispatcher.forward(req, resp);
    }

    public MockServletService getMockServletService() {
        return mockServletService;
    }

    public void setMockServletService(MockServletService mockServletService) {
        this.mockServletService = mockServletService;
    }
}
