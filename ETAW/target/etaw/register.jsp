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
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
    <script src="js/jquery-3.3.1.min.js"></script>
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
            <div class="icon1"><input class="input_c" name = "account" type="text" placeholder="输入手机号" onfocus="this.placeholder=''" onblur="this.placeholder='输入手机号'" id="account"></div>
            <div class="icon1"><input class="input_c" type="password" placeholder="输入密码" onfocus="this.placeholder=''"onblur="this.placeholder='输入密码'" id="password"></div>
            <input type="submit" id = "register_btn" class="register_btn" value="注册">
        </form>
    </div>

    <script type="text/javascript">
        /*'{"username":username,"password":password}' , */

        $("#register_btn").on('click',function(){

            var username = $("#account").val();
            var password = $("#password").val();
            console.log(username+":"+password);
            var user = {"username":username,"password":password};

            $.ajax({
                url:"${APP_PATH}/register/registerServlet" ,
                type:"POST" ,
                data:user ,
                success:function(res){
                    console.log(res);
                    if(res == "注册成功"){
                        alert("注册成功;跳转登录界面");
                        window.location.href="index.jsp";
                    }else{
                        alert("注册失败,原因："+res);
                    }
                }
            });
        });
    </script>
</body>
</html>
