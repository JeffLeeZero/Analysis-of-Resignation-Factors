<%--
  Created by IntelliJ IDEA.
  User: 毕修平
  Date: 2019/7/2
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>注册界面</title>
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="plugins/layui/layui.all.js"></script>
    <script type="text/javascript" src="js/register.js"></script>
    <script type="text/javascript" src="js/login.js"></script>

    <link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body>
<%--<div class="background" >--%>
    <%--<img class="scale_Bg" src="img/login_bg.jpg" style="background-attachment: fixed" />--%>
<%--</div>--%>

<div class="background">
    <img style=" position: absolute;top: 0;left: 0;width: 100%;height: 100%;
        overflow: hidden;" src="img/login_bg.jpg" />
</div>

<div class="top-nav">4
    <div class="top-nav-left">
        <p style=" color: #515151 " >员工离职分析</p>
    </div>
    <div class="login-nav-right">
        <a href="login.jsp" class="active">登录</a>
        <a href="#">关于我们</a>
    </div>
    <div class="clear"></div>
</div>
<div class="div_main float_in">
    <form class="layui-form" method="post">
        <h1>REGISTER</h1>
        <div class="icon1">
            <input class="input_c" name="phone" type="tel" placeholder="输入手机号" onfocus="this.placeholder=''"
                   onblur="this.placeholder='输入手机号'" id="phone" maxlength="11" regex="^[1][3,4,5,7,8][0-9]{9}$">
        </div>
        <div class="icon1"><input class="input_c" type="password" placeholder="输入密码" onfocus="this.placeholder=''"
                                  onblur="this.placeholder='输入密码'" id="password">
        </div>

        <div class="icon1"><input class="input_c" name="password" type="number" placeholder="输入验证码"
                                  onfocus="this.placeholder=''" onblur="this.placeholder='输入验证码'" id="verification">
        </div>
        <input id="veri_btn" type="button" class="submit_btn" value="获取验证码">
        <input id="registerBtn" type="button" class="register_btn" value="注册">
        <input type="text" id="alert_type" name="alert_type" value="isRegister" style="visibility: hidden">

    </form>
</div>

</body>

</html>
