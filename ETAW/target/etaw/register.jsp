<%--
  Created by IntelliJ IDEA.
  User: MQD
  Date: 2019/6/26
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册界面</title>
    <link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body>
    <div class="top-nav">
        <div class="top-nav-left">
            <p style="color: #ffffff">员工离职分析</p>
        </div>
        <div class="login-nav-right">
            <a href="login.jsp" class="active">主页</a>
            <a href="register.jsp">注册</a>
            <a href="#">游客进入</a>
            <a href="#">关于我们</a>
        </div>
        <div class="clear"></div>
    </div>
    <div class="div_main">
        <form class="layui-form">
            <h1>REGISTER</h1>
            <div class="icon1"><input class="input_c" type="text" placeholder="输入手机号" onfocus="this.placeholder=''"onblur="this.placeholder='输入手机号'" id="account"></div>
            <div class="icon1"><input class="input_c" type="password" placeholder="输入密码" onfocus="this.placeholder=''"onblur="this.placeholder='输入密码'" id="password"></div>
            <input type="submit" class="register_btn" value="注册">
        </form>
    </div>

</body>
</html>