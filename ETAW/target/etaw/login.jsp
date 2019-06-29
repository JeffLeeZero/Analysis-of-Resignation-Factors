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
<<<<<<< HEAD
            <div class="icon1"><input class="input_c" name="password" type="password" placeholder="输入密码" onfocus="this.placeholder=''"onblur="this.placeholder='输入密码'" id="password"></div>
=======
            <div class="icon1"><input class="input_c"  name="password" type="password" placeholder="输入密码" onfocus="this.placeholder=''"onblur="this.placeholder='输入密码'" id="password"></div>
<<<<<<< HEAD
            <input id="login_btn" type="button" class="submit_btn" value="登录">
            <input id="register_btn" type="button" class="register_btn" value="注册">
            <a class="login_type" id="login_phone">手机验证码登陆</a>
            <a class="forgetPass">忘记密码</a>
=======
>>>>>>> dffbce0d28f37f5b2eb534084f682744c2e69dc1
            <input type="button" class="register_btn" value="验证码登录">
            <input id="login_btn" type="submit" class="submit_btn" value="登录">

            <script type="text/javascript">

                $('#login_btn').click(function () {

                    if ($('#account').val().equals("") ) {
                        $("[name='account']").focus();
                        layer.msg("请输入用户名",{icon: 0,time: 1500});
                        return;
                    }

                    if ($('#password').val().equals("")) {
                        $("[name='password']").focus();
                        layer.msg("请输入密码",{icon: 0,time: 1500});
                        return;
                    }
                    $.ajax({
                        url:"<%=request.getContextPath()%>/LoginServelet",
                        type:"post",
                        dataType:"json",
                        data:{"account":$('#account').val(),"password":$('#password').val()},//数据为登录名和登录密码

                        beforeSend:function(){
                            $('#login_btn').val("登录中");
                        },

                        success:function(res){//处理返回的信息，true则跳转，false则提示密码错误
                            if (res.isSuccess){

                                window.location.href = "index.jsp";
                            } else
                            {
                                var req = JSON.parse(new HttpResponse());
                                layui.use('layer', function(){
                                    var message = req.message;
                                    var layer = layui.layer;
                                    layer.msg(message, {icon: 2,time: 1500, anim: 6});
                                });
                                console.log(res);

                            }
                            // if(data==false){
                            //     $("[name='password']").val("");
                            //     layer.msg("用户不存在", {icon: 5,time: 1500})
                            //     $('#login_btn').val("登录");
                            // }else{
                            //     window.location.href = 'index.jsp';
                            // }
                        },
                        error:function(res){
                            console.log(res);
                            layui.use('layer', function(){
                                var layer = layui.layer;
                                layer.msg("网络异常，请重试", {icon: 2,time: 1500, anim: 6});
                            });
                        }
                    });
                })

            </script>
            <p><a href="#">忘记密码</a></p>
>>>>>>> 9e843777920740539541d3412682ff6b10bc2869
            <input type="text" id="alert_type" name="alert_type" value="${requestScope.alert_type}" style="visibility: hidden">
        </form>
    </div>
    <div class="div_main" id="veriLogin">
        <form class="layui-form" method="post">
            <h1>LOGIN IN</h1>
            <div class="icon1">
                <input class="input_c" name="account" type="tel" placeholder="输入手机号" onfocus="this.placeholder=''"onblur="this.placeholder='输入手机号'" id="phone" maxlength="11" regex="^[1][3,4,5,7,8][0-9]{9}$">
            </div>
            <div class="icon1"><input class="input_c"  name="password" type="number" placeholder="输入密码" onfocus="this.placeholder=''"onblur="this.placeholder='输入验证码'" id="verification"></div>
            <input id="veri_btn" type="button" class="submit_btn" value="获取验证码" onclick="x()">
            <input id="veriLogin_btn" type="button" class="register_btn" value="登录">
            <a class="login_type" id="login_pass">密码登陆</a>
            <a class="forgetPass">忘记密码</a>
            <script>
                // $("#veri_btn").click(function () {
                //
                // });
                function x() {
                    $("#veri_btn").val("获取");
                }
            </script>
        </form>
    </div>
    <div class="copyright">
        <p>© 2019 Employee Turnover Analyze Web| Design by team zpj</p>
    </div>
</body>
</html>
