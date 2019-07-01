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

				<form method="post" action="UploadServlet" enctype="multipart/form-data">

					<div class="layui-upload">
						<div class="layui-upload-list" style="margin-left: 50px; margin-right: 50px">
							<table class="layui-table">
								<thead>
								<tr><th>文件名</th>
									<th>大小</th>
									<th>状态</th>
									<th>操作</th>
								</tr></thead>
								<tbody id="demoList"></tbody>
							</table>
						</div>
						<button type="button" class="layui-btn layui-btn-normal" id="testList" style="margin: 20px 450px">选择文件</button>
						<button type="button" class="layui-btn" id="testListAction" style="margin:10px 450px">开始上传</button>
					</div>
				</form>
			</div>

			<jsp:include page="footer.jsp" />
		</div>

		<script src="plugins/layui/layui.js"></script>
		<script>
			//JavaScript代码区域
			layui.use('element', function() {
				var element = layui.element;

			});

			layui.use('upload', function() {
				var $ = layui.jquery,
					upload = layui.upload;

				//多文件列表示例
				var demoListView = $('#demoList')
						,uploadListIns = upload.render({
					elem: '#testList'
					,url: 'http://localhost:8080/UploadServlet'
					,accept: 'file'
					,multiple: true
					,auto: false
					,bindAction: '#testListAction'
					,choose: function(obj){
						var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
						//读取本地文件
						obj.preview(function(index, file, result){
							var tr = $(['<tr id="upload-'+ index +'">'
								,'<td>'+ file.name +'</td>'
								,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
								,'<td>等待上传</td>'
								,'<td>'
								,'<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
								,'<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
								,'</td>'
								,'</tr>'].join(''));

							//单个重传
							tr.find('.demo-reload').on('click', function(){
								obj.upload(index, file);
							});

							//删除
							tr.find('.demo-delete').on('click', function(){
								delete files[index]; //删除对应的文件
								tr.remove();
								uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
							});

							demoListView.append(tr);
						});
					}
					,done: function(res, index, upload){
						if(res.code == 0){ //上传成功
							var tr = demoListView.find('tr#upload-'+ index)
									,tds = tr.children();
							tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
							tds.eq(3).html(''); //清空操作
							return delete this.files[index]; //删除文件队列已经上传成功的文件
						}
						this.error(index, upload);
					}
					,error: function(index, upload){
						var tr = demoListView.find('tr#upload-'+ index)
								,tds = tr.children();
						tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
						tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
					}
				});
			});
		</script>

	</body>

</html>