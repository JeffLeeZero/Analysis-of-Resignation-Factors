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

				<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px">
					<legend>导入数据</legend>
				</fieldset>

				<%--<form class="layui-form" method="post" action="<%=request.getContextPath()%>/UploadServlet"  enctype="multipart/form-data">--%>
					<%--<input type="hidden" name="account" id="formAccount"/>--%>

					<%--<input type="file" name="uploadFile" />--%>
					<%--<br/>--%>
					<%--<input type="submit" value="上传" />--%>

					<%--<div class="layui-form-item" style="margin-left: 300px">--%>
						<%--<div class="layui-input-block">--%>
							<%--<button type="submit" lay-submit="" lay-filter="demo1" class="layui-btn">上传</button>--%>
						<%--</div>--%>
					<%--</div>--%>

				<%--</form>--%>

				<%--<form id="upload" enctype="multipart/form-data" class="attachment">--%>
					<%--选择文件:--%>
					<%--<input type="hidden" name="account" id="formAccount"/>--%>
					<%--<input type="file" name="uploadFile" />--%>
					<%--<br/>--%>
					<%--<input type="submit" value="上传" />--%>
					<%--&lt;%&ndash;<button class="layui-btn" id="upload">提交</button>&ndash;%&gt;--%>
				<%--</form>--%>

                <%--<form class="layui-form" action="<%=request.getContextPath()%>/UploadServlet">--%>
                <%--选择文件:--%>
                <%--<input type="hidden" name="account" id="formAccount"/>--%>
                <%--<input type="file" name="uploadFile" />--%>
                <%--<input type="submit" value="上传" />--%>

                <%--<div class="layui-form-item">--%>
                <%--<div class="layui-input-block">--%>
                <%--<button class="layui-btn" id="commit" onclick="return false">立即提交</button>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</form>--%>

                <form class="layui-form" action="#" enctype="multipart/form-data" id="up_form">

                    <input type="file" name="uploadFile" />

                    <input type="hidden" name="account" id="formAccount"/>

                    <button class="layui-btn layui-btn-normal" lay-submit="" id="upup" lay-filter="upup">提交</button>

                </form>

					<%--<div class="layui-upload">--%>
						<%--<div class="layui-upload-list" style="margin-left: 50px; margin-right: 50px">--%>
							<%--<table class="layui-table">--%>
								<%--<thead>--%>
								<%--<tr><th>文件名</th>--%>
									<%--<th>大小</th>--%>
									<%--<th>状态</th>--%>
									<%--<th>操作</th>--%>
								<%--</tr></thead>--%>
								<%--<tbody id="demoList"></tbody>--%>
							<%--</table>--%>
						<%--</div>--%>
						<%--<input type="hidden" name="account" id="formAccount"/>--%>
						<%--<button type="button" class="layui-btn layui-btn-normal" id="testList" style="margin: 20px 450px">选择文件</button>--%>
						<%--<button type="button" class="layui-btn" id="testListAction" style="margin:10px 450px">开始上传</button>--%>
					<%--</div>--%>

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