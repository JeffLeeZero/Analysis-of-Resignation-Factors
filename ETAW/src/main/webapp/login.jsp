<%@ page import="java.io.PrintWriter" %><%--
  Created by IntelliJ IDEA.
  User: MQD
  Date: 2019/6/26
  Time: 19:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>员工离职登录页面</title>

    <script src="js/jquery-3.3.1.min.js"></script>
    <script  src="plugins/layui/layui.all.js"></script>
    <script type="text/javascript" src="js/login.js" ></script>


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
        <form class="layui-form" method="post">
            <h1>LOGIN IN</h1>
            <div class="icon1"><input class="input_c" name="account" type="text" placeholder="输入手机号" onfocus="this.placeholder=''"onblur="this.placeholder='输入手机号'" id="account"></div>
            <div class="icon1"><input class="input_c"  name="password" type="password" placeholder="输入密码" onfocus="this.placeholder=''"onblur="this.placeholder='输入密码'" id="password"></div>
            <input type="button" class="register_btn" value="验证码登录">
            <input id="login_btn" type="button" class="submit_btn" value="登录">

            <script >

                $("#login_btn").click(function () {

                    if ($("#account").val()=="" ) {
                        $("#account").focus();
                        layer.msg("请输入用户名",{icon: 0,time: 1500});
                        return;
                    }

                    if ($("#password").val()=="") {
                        $("#password").focus();
                        layer.msg("请输入密码",{icon: 0,time: 1500});
                        return;
                    }
                    $.ajax({
                        url:'<%=request.getContextPath()%>/LoginServelet',
                        type:'post',
                        dataType:"json",
                        data:{"account":$("#account").val(),"password":$("#password").val()},//数据为登录名和登录密码

                        beforeSend:function(){
                            $('#login_btn').val("登录中");
                        },

                        success:function(data){//处理返回的信息，true则跳转，false则提示密码错误
                            if (data.isSuccess){

                                window.location.href = "index.jsp";
                            } else {
                                var message = data.message;
                                layer.msg(message, {icon: 2,time: 1500, anim: 6});
                                $('#login_btn').val("登录");
                                console.log(data);

                            }
                            // if(data==false){
                            //     $("[name='password']").val("");
                            //     layer.msg("用户不存在", {icon: 5,time: 1500})
                            //     $('#login_btn').val("登录");
                            // }else{
                            //     window.location.href = 'index.jsp';
                            // }
                        },
                        error:function(data){
                            console.log(data);
                            layer.msg("网络异常，请重试", {icon: 2,time: 1500, anim: 6});
                            $('#login_btn').val("登录");
                        }
                    });
                })

            </script>
            <p><a href="#">忘记密码</a></p>
            <input type="text" id="alert_type" name="alert_type" value="${requestScope.alert_type}" style="visibility: hidden">
        </form>
    </div>
    <div class="copyright">
        <p>© 2019 Employee Turnover Analyze Web| Design by team zpj</p>
    </div>
</body>
</html>
