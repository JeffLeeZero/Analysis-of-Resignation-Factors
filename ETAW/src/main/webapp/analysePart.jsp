<%--
  Created by IntelliJ IDEA.
  User: 万宇
  Date: 2019/6/25
  Time: 19:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<title>员工离职因素部门分析</title>
		<script src="js/jquery-3.3.1.min.js"></script>
		<link rel="stylesheet" href="plugins/layui/css/layui.css" media="all">
	</head>

	<body class="layui-layout-body">
		<div class="layui-layout layui-layout-admin">

			<jsp:include page="header.jsp" />

			<div class="layui-body" style="background-color: #eeeeee;  ">

				<div class="layui-inline">
					<fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px">
						<legend>选择影响因素</legend>
					</fieldset>
					<div class="layui-form">
						<div class="layui-form-item">
							<div class="layui-input-block" style="margin-left: 50px">
								<select name="modules" lay-filter="attribute">
									<option value="">请选择</option>
									<option value="sales">sales</option>
									<option value="satisfaction_level">satisfaction_level</option>
									<option value="last_evaluation">last_evaluation</option>
									<option value="average_montly_hours">average_montly_hours</option>
									<option value="number_project">number_project</option>
									<option value="time_spend_company">time_spend_company</option>
									<option value="Work_accident">Work_accident</option>
									<option value="promotion_last_5years">promotion_last_5years</option>
									<option value="salary">salary</option>
								</select>
							</div>
						</div>
					</div>
				</div>

				<fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px">
					<legend>部门结果</legend>
				</fieldset>
				<div id="pie" style="height:450px;border:1px solid #ccc;padding:10px;"></div>
				<script src="js/echarts.js"></script>

			</div>
			<jsp:include page="footer.jsp" />
		</div>

		<script src="plugins/layui/layui.js"></script>
		<script type="text/javascript">

			require.config({
				paths: {
					echarts: './js'
				}
			});
			layui.use(['element', 'form'], function() {
				var element = layui.element,
					form = layui.form;
				form.on('select(attribute)', (data) => {
					$.ajax({
						type: "post",
						url: "/AnalysisPartServlet",
						dataType: "json",
						data: JSON.stringify({
							"reqId": window.localStorage.id,
							"reqParam": {
								"name": data.value
							}
						}),
						success: function(res) {
							console.log(res);
							var header = [];
							var value = [];
							var list = res.resData.list
							for(var i = 0; i < list.length; i++) {
								header[i] = list[i].value;
								value[i] = list[i].ratio;
							}
							if(res.resData.isSeperated) {
								require(
									[
										'echarts',
										'echarts/chart/bar',
									],
									function(ec) {
										// 基于准备好的dom，初始化echarts图表
										var myChart = ec.init(document.getElementById('pie'));

										var option = {
											tooltip: {
												show: true
											},
											legend: {
												data: ['离职率']
											},
											xAxis: [{
												type: 'category',
												data: header,
												axisLabel: {
			                    interval: 0,
			                    rotate: 20, // 20度角倾斜显示(***这里是关键)
			               		},
											}],
											yAxis: [{
												type: 'value'
											}],
											series: [{
												"name": "离职率",
												"type": "bar",
												"data": value,
												itemStyle: {
													normal: {
														label: {
															show: true,
															position: 'top',
															formatter: '{b}'
														},
														color: '#2f4554'
													}
												},
											}]
										};

										// 为echarts对象加载数据
										myChart.setOption(option);
									})
							} else {
								require(
									[
										'echarts',
										'echarts/chart/line',
									],
									function(ec) {
										// 基于准备好的dom，初始化echarts图表
										var myChart = ec.init(document.getElementById('pie'));

										var option = {
											title: {
												text: '单因素离职率分析'
											},
											tooltip: {
												trigger: 'axis'
											},
											xAxis: [{
												name: data.value,
												type: 'category',
												boundaryGap: true,
												data: header,
												axisLine:{
													lineStyle: {
														color: '#2f4554'
													}
												}
											}],
											yAxis: [{
												max: 1,
												min: 0,
												type: 'value',
												axisLabel: {
													formatter: '{value}'
												},
												axisLine:{
													lineStyle: {
														color: '#2f4554'
													}
												}
											}],
											series: [{
												name: data.value,
												type: 'line',
												data: value,
												markLine: {
													data: [{
														type: 'average',
														name: '平均值'
													}],
													itemStyle: {
														normal: {
															"color": '#2f4554',
															lineStyle: {
																color: '#2f4554'
															}
	
														}
													}
												},
												itemStyle: {
													normal: {
														"color": '#2f4554',
														lineStyle: {
															color: '#2f4554'
														}

													}
												}
											}]
										};

										// 为echarts对象加载数据
										myChart.setOption(option);
									})
							}

						}
					});
				});

			});
		</script>

	</body>

</html>