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
<h2>登录</h2>
<section>
    <form action="<%=path%>/user/login" method="post">
        <p><input type="text" name="userAccount" placeholder="输入用户名"></p>
        <p><input type="text" name="password" placeholder="输入密码"></p>
        <p><input type="submit" value="登录"><a href="<%=path%>/user/toRegister">注册</a></p>
        <p style="color: red;"><%=null==subject.getSession().getAttribute("loginMsg")?"":subject.getSession().getAttribute("loginMsg")%></p>
    </form>
</section>
<section>
    <h3>试着跳转权限控制页面</h3>
    <a href="<%=path%>/user/userManage">userManage</a>
</section>
</body>
</html>
