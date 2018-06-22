<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ page import="org.apache.shiro.subject.Subject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Subject subject = SecurityUtils.getSubject();
    String path = request.getContextPath();
%>
<html>
<head>

</head>
<body>
<h2>注册</h2>
<section>
    <form action="<%=path%>/user/register" method="post">
        <p><input type="text" name="userName" placeholder="输入姓名"></p>
        <p><input type="text" name="userAccount" placeholder="输入账号"></p>
        <p><input type="text" name="password" placeholder="输入密码"></p>
        <p><input type="submit" value="注册"><a href="<%=path%>/user/toLogin">返回</a></p>
        <p style="color: red;"><%=null==request.getAttribute("registerMsg")?"":request.getAttribute("registerMsg")%></p>
    </form>
</section>
</body>
</html>
