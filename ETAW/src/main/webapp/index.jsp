<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<title>员工离职因素分析网站</title>
		<link rel="stylesheet" href="plugins/layui/css/layui.css">
	</head>

	<body class="layui-layout-body">
		<div class="layui-layout layui-layout-admin">

			<jsp:include page="header.jsp" />

			<div class="layui-body" style="background-color: #eeeeee;  ">
				<!-- 内容主体区域 -->
				<div style="padding: 15px;">
					<i class="layui-icon layui-icon-face-smile" style="font-size: 40px; color: #FF5722;">你好</i>
					<br><br>
					<p style="font-size: 30px; color: #009688;">欢迎使用员工离职分析系统</p>
				</div>


			</div>

			<jsp:include page="footer.jsp" />
		</div>

		<script src="plugins/layui/layui.js"></script>

		<script>
			document.getElementById("formAccount").value = window.localStorage.id;
			// $.ajax({
			// 	type: "post",
			// 	url: "http://localhost:8080/UploadServlet",
			// 	dataType: "json",
			// 	data: new FormData(document.getElementById("upload")),
			// 	success: function (res) {
			// 		console.log(res);
			// 	}
			// });

			//JavaScript代码区域
			layui.use('element', function() {
				var element = layui.element;
			});

			layui.use('upload', function() {
				var $ = layui.jquery,
						upload = layui.upload;

                upload.render({
                    elem: '#supplement_file'
                    , url: 'http://localhost:8080/UploadServlet'
                    , accept: 'file'
                    , auto: false
                    // , bindAction: '#upfile' //关闭的上传按钮   html中此id所在元素也被注释
                    ,multiple: true
                    , done: function (res) {
                        alert("上传成功");
                    }
                });

                function fsubmit(fd) {
                    $.ajax({
                        url: "http://localhost:8080/UploadServlet",
                        type: "POST",
                        data: fd,
                        async : false,
                        contentType: false,   //jax 中 contentType 设置为 false 是为了避免 JQuery 对其操作，从而失去分界符，而使服务器不能正常解析文件
                        processData: false,   //当设置为true的时候,jquery ajax 提交的时候不会序列化 data，而是直接使用data
                        error : function(request) {
                            parent.layer.alert("网络超时");
                        },
                        success: function (data) {
                            alert("上传成功！");
                        }
                    });
                    return false;
                }

                $("#upup").on("click",function () {
                    var formSatellite = document.getElementById("up_form");//获取所要提交form的id
                    var formAccount = document.getElementById("formAccount").valueOf();
                    var fs1 = new FormData(formSatellite);  //用所要提交form做参数建立一个formdata对象
                    //var fs2 = new FormData(formAccount);
                    console.log(fs1);
                    fsubmit(fs1);//调用函数
                    //fsubmit(fs2);
                })

			});
		</script>

	</body>

</html>