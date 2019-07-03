<%--
  Created by IntelliJ IDEA.
  User: MQD
  Date: 2019/7/2
  Time: 9:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>找回密码</title>
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="plugins/layui/layui.all.js"></script>
    <script type="text/javascript" src="js/findPassword.js"></script>
    <%--<script type="text/javascript" src="js/register.js"></script>--%>
    <script type="text/javascript" src="js/login.js"></script>
    <link rel="stylesheet" type="text/css" href="plugins/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="css/findPassword.css">
</head>
<body>
<div class="top-nav">
    <div class="top-nav-left">
        <p style="color: #ffffff">员工离职分析</p>
    </div>
    <div class="login-nav-right">
        <a href="login.jsp" class="active">主页</a>
        <a href="#">游客进入</a>
        <a href="#">关于我们</a>
    </div>
    <div class="clear"></div>
</div>
<div class="div_main">
    <form class="layui-form" method="post">
        <h1>找回密码</h1>
        <div class="icon1" id="inputPhone">
            <input class="input_c" name="phone" type="tel" placeholder="输入手机号" onfocus="this.placeholder=''"
                   onblur="this.placeholder='输入手机号'" id="phone" maxlength="11" regex="^[1][3,4,5,7,8][0-9]{9}$">
        </div>
        <button class="layui-btn layui-btn-normal" id="veri_btn" type="button" onclick="nextBtn()">下一步<i class="layui-icon layui-icon-right"></i></button>
        <div class="icon1" id="inputVerification">
            <input class="input_c" name="password" type="number" placeholder="输入验证码"
                   onfocus="this.placeholder=''" onblur="this.placeholder='输入验证码'" id="verification">
        </div>
        <button class="layui-btn layui-btn-normal" id="sure_btn" type="button" onclick="judgeVerification()">确定<i class="layui-icon layui-icon-right"></i></button>
        <div class="icon1" id="inputPass">
            <input class="input_c" type="password" placeholder="输入密码" onfocus="this.placeholder=''"
                   onblur="this.placeholder='输入密码'" id="password">
        </div>
        <button class="layui-btn layui-btn-normal" id="submit_btn"type="button" ><i class="layui-icon layui-icon-edit"></i>提交</button>
        <input type="text" id="alert_type" name="alert_type" value="isFind" style="visibility: hidden">
    </form>
</div>
</body>
</html>
