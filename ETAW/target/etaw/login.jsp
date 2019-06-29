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
    <div class="div_main" id="passLogin">
        <form class="layui-form" method="post">
            <h1>LOGIN IN</h1>
            <div class="icon1"><input class="input_c" name="account" type="text" placeholder="输入手机号" onfocus="this.placeholder=''"onblur="this.placeholder='输入手机号'" id="account"></div>
            <div class="icon1"><input class="input_c"  name="password" type="password" placeholder="输入密码" onfocus="this.placeholder=''"onblur="this.placeholder='输入密码'" id="password"></div>
            <input id="login_btn" type="button" class="submit_btn" value="登录">
            <input id="register_btn" type="button" class="register_btn" value="注册">
            <a class="login_type" id="login_phone">手机验证码登陆</a>
            <a class="forgetPass">忘记密码</a>
            <input type="text" id="alert_type" name="alert_type" value="${requestScope.alert_type}" style="visibility: hidden">
        </form>
    </div>
    <div class="div_main" id="veriLogin">
        <form class="layui-form" method="post">
            <h1>LOGIN IN</h1>
            <div class="icon1">
                <input class="input_c" name="phone" type="tel" placeholder="输入手机号" onfocus="this.placeholder=''"onblur="this.placeholder='输入手机号'" id="phone" maxlength="11" regex="^[1][3,4,5,7,8][0-9]{9}$">
            </div>
            <div class="icon1"><input class="input_c"  name="password" type="number" placeholder="输入验证码" onfocus="this.placeholder=''"onblur="this.placeholder='输入验证码'" id="verification"></div>
            <input id="veri_btn" type="button" class="submit_btn" value="获取验证码" onclick="verifyLogin(this)">
            <input id="veriLogin_btn" type="button" class="register_btn" value="登录" onclick="loginWithVerification()  ">
            <a class="login_type" id="login_pass">密码登陆</a>
            <a class="forgetPass">忘记密码</a>
            <script>
                var verifiedCode; //验证码
                var interValObj; //timer变量，控制时间
                var count = 60;  //间隔时间
                var curCount;    //当前剩余秒数
                var verifyBtn = $("#veri_btn");

                function verifyLogin(node) {
                    if ($("#phone").val()=="" ) {
                        $("#phone").focus();
                        layer.msg("请输入手机号",{icon: 0,time: 1500});
                        return;
                    }

                    $.ajax({
                            url:'/SmsLoginServlet',
                            type:'post',
                            dataType:"json",
                            data:{"phone":$("#phone").val()},
                        success:function(data){
                            getCheckCodeTime();
                            verifiedCode = data.message;
                            // layer.msg(message, {icon: 2,time: 1500});
                        },
                        error:function(err){
                            console.log(err);
                            layer.msg("网络异常，请重试", {icon: 2,time: 1500, anim: 6});
                        }
                    });

                }
                function getCheckCodeTime() {
                    curCount = count;
                    verifyBtn.attr("disabled","disabled");
                    verifyBtn.val(curCount+"后可重新获取");
                    interValObj = window.setInterval(setTime,1000);
                }

                function setTime() {
                    if (curCount <= 0) {
                        window.clearInterval(InterValObj);//停止计时器
                        verifyBtn.removeAttr("disabled");//启用按钮
                        verifyBtn.val("重新发送验证码");
                    }
                    else {
                        --curCount;
                        verifyBtn.val(curCount+"后可重新获取");
                    }
                }

                function loginWithVerification() {
                    if ($("#phone").val()=="" ) {
                        $("#phone").focus();
                        layer.msg("请输入手机号",{icon: 0,time: 1500});
                        return;
                    }

                    if ($("#verification").val()=="") {
                        $("#verification").focus();
                        layer.msg("请输入验证码",{icon: 0,time: 1500});
                        return;
                    }
                    if($("#verification").val()==verifiedCode){
                        window.location.href = "index.jsp";
                    }else{
                        layer.msg("验证码错误",{icon: 5,time: 1500});
                    }
                }
            </script>
        </form>
    </div>
    <div class="copyright">
        <p>© 2019 Employee Turnover Analyze Web| Design by team zpj</p>
    </div>
</body>
</html>
