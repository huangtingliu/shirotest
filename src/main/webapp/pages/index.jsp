<%@ page import="org.apache.shiro.subject.Subject" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Subject subject = SecurityUtils.getSubject();
%>
<html>
<head>
    <title>首页</title>
</head>
<body>
<h2>首页</h2>
<h2><%=subject.getPrincipal()%>, welcome</h2>
<h3>试着跳转权限控制页面</h3>
<a href="/userManage">userManage</a>
<a href="/logout">logout</a>
<a href="/userDel">userDel</a>
</body>
</html>
