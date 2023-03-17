<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta content="text/html;charset=utf-8" http-equiv="Content-Type"/>
    <title>Page Title</title>
</head>
<body>
<table border="1">
    <tr>
        <td>Title1</td>
        <td>Title2</td>
        <td>Title3</td>
    </tr>
    <c:forEach items="${infoList}" var="infoBean">
        <tr>
            <td>${infoBean.column1}</td>
            <td>${infoBean.column2}</td>
            <td>${infoBean.column3}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>