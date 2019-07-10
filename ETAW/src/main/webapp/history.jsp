<%--
  Created by IntelliJ IDEA.
  User: 万宇
  Date: 2019/6/25
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<title>员工离职因素历史数据分析</title>
		<script type="text/javascript" src="js/share.js"></script>

		<script src="plugins/layui/layui.js"></script>
		<link rel="stylesheet" href="plugins/layui/css/layui.css">
	</head>

	<body class="layui-layout-body">
		<div class="layui-layout layui-layout-admin">

			<jsp:include page="header.jsp" />
			<div class="layui-body">
				<div id="history_table">
					<table class="layui-hide" id="test" lay-filter="test"></table>
				</div>
				<button onclick="deleteHistory()" class="layui-btn layui-btn-danger" type="button" id="delete_button"><i class="layui-icon layui-icon-delete"></i></button>

				<jsp:include page="footer.jsp" />
			</div>

			<script type="text/html" id="toolbarDemo"></script>

			<script type="text/html" id="barDemo">
				<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
			</script>

			<script>
				layui.use('table', function() {
					var table = layui.table;

					table.render({
						id: "history_table",
						elem: '#test',
						title: '历史预测数据',
						url: '/HistoryServlet',
						toolbar: '#toolbarDemo',
						contentType: 'application/json',
						method: "POST",
						deal: function(res) {
							return {
								code: 0,
								msg: "",
								count: 1000,
								data: res.resData
							}
						},
						where: {
							"reqId": window.localStorage.id
						},
						cols: [
							[{
								type: 'checkbox',
								fixed: 'left'
							}, {
								field: 'worker_number',
								title: '编号',
								width: 110,
								fixed: 'left',
								unresize: true,
								sort: true
							}, {
								field: 'satisfaction_level',
								title: '满意度',
								sort: true,
								width: 100
							}, {
								field: 'last_evaluation',
								title: '考核',
								width: 80
							}, {
								field: 'average_monthly_hours',
								title: '月工时',
								width: 120,
								sort: true
							}, {
								field: 'number_project',
								title: '参与项目数',
								width: 130,
								sort: true,
							}, {
								field: 'time_spend_company',
								title: '工龄',
								sort: true,
								width: 80
							}, {
								field: 'work_accident',
								title: '工伤',
								width: 80
							}, {
								field: 'promotion',
								title: '是否晋升',
								width: 120
							}, {
								field: 'sales',
								title: '部门',
								width: 120
							}, {
								field: 'salary',
								title: '薪资',
								width: 100
							}, {
								field: 'left',
								title: '结果',
								width: 100
							}]
						],
						page: false,
						event: true

					});

				});
				var deleteHistory = function() {
					var table = layui.table;
					var checkStatus = table.checkStatus('history_table'); //idTest 即为基础参数 id 对应的值

					var delData = checkStatus.data //获取选中行的数据
					console.log(checkStatus.data.length) //获取选中行数量，可作为是否有选中行的条件
					console.log(checkStatus.isAll) //表格是否全选
					if(checkStatus.data.length < 1) {
						layer.msg('请选择要删除的数据！', {
							icon: 2
						});
						return;
					}
					layer.confirm('真的删除行么', function(index) {
						$.ajax({
							type: "POST",
							url: "/DeleteHistoryServlet",
							async: true,
							data: JSON.stringify({
								reqId: window.localStorage.id,
								reqParam: delData
							}),
							success: function(res) {
								table.reload('history_table', {
									deal: function(res) {
										return {
											code: 0,
											msg: "",
											count: 1000,
											data: res.resData
										}
									},
									where: {
										"reqId": window.localStorage.id
									},
								});
							}
						});
						layer.close(index);
					});
				}
			</script>

			<!--<script>
			var request = $.ajax({
				url: "http://localhost:8080/HistoryServlet",
				type: "POST",
				contentType: false, //jax 中 contentType 设置为 false 是为了避免 JQuery 对其操作，从而失去分界符，而使服务器不能正常解析文件
				processData: false, //当设置为true的时候,jquery ajax 提交的时候不会序列化 data，而是直接使用data
				error: function(request) {

				},
				success: function(data) {

				}
			});

			//JavaScript代码区域
			layui.use('element', function() {
				var element = layui.element;

			});

			layui.use('upload', function() {
				var $ = layui.jquery,
					upload = layui.upload;

			});
		</script>-->

	</body>
	<style>
		#history_table {
			position: absolute;
			width: 90%;
			left: 2.5%;
			top: 5%;
		}
		
		#delete_button {
			position: fixed;
			bottom: 10%;
			right: 3%;
			width: 60px;
			height: 60px;
			border-radius: 50%;
			z-index: 1000;
		}
		
		#delete_button i {
			font-size: 25px;
		}
	</style>

</html>
