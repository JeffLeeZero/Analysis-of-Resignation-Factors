<%@ page import="java.io.PrintWriter" %>
<%--
  Created by IntelliJ IDEA.
  User: 毕修平
  Date: 2019/6/29
  Time: 19:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta http-equiv="X-UA-Compatible" content="ie=edge" />
		<title>员工离职登录页面</title>

		<!--默认样式-->
		<link rel="stylesheet" href="plugins/login/css/reset.css">

		<!--响应式框架-->
		<link rel="stylesheet" type="text/css" href="plugins/login/css/bootstrap.css">

		<!--css3动画库-->
		<link rel="stylesheet" href="plugins/login/css/animate.css">

		<!--banner大图基础样式-->
		<link rel="stylesheet" href="plugins/login/css/slick.css">

		<!--页面滚动基础样式-->
		<link rel="stylesheet" href="plugins/login/css/jquery.fullPage.css" />

		<!--导航样式-->
		<link rel="stylesheet" href="plugins/login/css/head.css" />

		<!--图片和滚屏样式-->
		<link rel="stylesheet" href="plugins/login/css/index.css">

		<link rel="stylesheet" type="text/css" href="css/login.css">

		<script src="js/jquery-3.3.1.min.js"></script>
		<script src="plugins/layui/layui.all.js"></script>
		<script type="text/javascript" src="js/login.js"></script>

		<script src="plugins/login/js/jquery.min.js"></script>
		<script src="plugins/login/js/jquery.fullPage.min.js"></script>
		<script src="plugins/login/js/index_slick.js"></script>
		<script src="plugins/login/js/index.js"></script>
	</head>

	<body>

		<header class="header">
			<div class="container clearfix">
				<div class="fr nav">

					<ul class="navbar_nav" data-in="fadeInDown" data-out="fadeOutUp">

						<div class="top-nav-left">
							<p style="color: #626262">员工离职分析</p>
						</div>

						<div class="login-nav-right">

							<a href="./frontPage.jsp" class="active">主页</a>

							<a href="#">关于我们</a>
						</div>

					</ul>
				</div>
				<a href="javascript:void(0)" id="navToggle">
					<span></span>
				</a>
			</div>
		</header>

		<div id="index_main" class="index_main">
			<!--导航-->
			<div class="section section1">
				<div class="index_banner">

					<div class="item">
					
						<div class="background">
							<img class="scale_Bg" src="img/login_bg.jpg" />
						</div>
						<div class="div_main float_in" id="passLogin">
							<form class="layui-form" method="post">
								<h1>LOGIN IN</h1>
								<div class="icon1"><input class="input_c" name="account" type="text" placeholder="输入手机号" onfocus="this.placeholder=''" onblur="this.placeholder='输入手机号'" id="account"></div>
								<div class="icon1"><input class="input_c" name="password" type="password" placeholder="输入密码" onfocus="this.placeholder=''" onblur="this.placeholder='输入密码'" id="password"></div>
								<input id="login_btn" type="button" class="submit_btn" value="登录" name="loginBtn">
								<input id="register_btn" type="button" class="register_btn" value="注册">
								<a class="login_type" id="login_phone">手机验证码登陆</a>
								<a class="forgetPass" href="findPassword.jsp">忘记密码</a>
							</form>
						</div>
						<div class="div_main float_in" id="veriLogin">
							<form class="layui-form" method="post">
								<h1>LOGIN IN</h1>
								<div class="icon1">
									<input class="input_c" name="phone" type="tel" placeholder="输入手机号" onfocus="this.placeholder=''" onblur="this.placeholder='输入手机号'" id="phone" maxlength="11" regex="^[1][3,4,5,7,8][0-9]{9}$">
								</div>
								<div class="icon1"><input class="input_c" name="password" type="number" placeholder="输入验证码" onfocus="this.placeholder=''" onblur="this.placeholder='输入验证码'" id="verification"></div>
								<input id="veri_btn" type="button" class="submit_btn" value="获取验证码" onclick="verifyLogin()">
								<input id="veriLogin_btn" type="button" class="register_btn" value="登录" onclick="loginWithVerification()" name="typeBtn">
								<a class="login_type" id="login_pass">密码登陆</a>
								<a class="forgetPass" href="findPassword.jsp">忘记密码</a>
								<input type="text" id="alert_type" name="alert_type" value="isLogin" style="visibility:hidden">
							</form>
						</div>
						<div class="copyright">
							<p>© 2019 Employee Turnover Analyze Web| Design by team zpj</p>
						</div>

						<div class="inner">
							<div class="block_txt">

							</div>
						</div>
					</div>
				</div>
				<div class="left slick_txt">
					<div class="prev slick_arrow"></div>
				</div>
				<div class="right slick_txt">
					<div class="next slick_arrow"></div>
				</div>
			</div>
		</div>

	</body>


</html>
