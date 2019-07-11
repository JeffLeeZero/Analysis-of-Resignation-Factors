<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<title>员工离职因素总体分析</title>
		<script src="js/jquery-3.3.1.min.js"></script>
		<link rel="stylesheet" href="plugins/layui/css/layui.css">
		<link rel="stylesheet" href="css/analysisChart.css" />
	</head>

	<body class="layui-layout-body">

		<div class="layui-layout layui-layout-admin">

			<jsp:include page="header.jsp" />

			<div class="layui-body">

				<div id="left_ratio" class="chart_block">
					<fieldset class="layui-elem-field layui-field-title title">
						<legend>总体情况</legend>
					</fieldset>
					<div class="layui-col-md12">
						<div class="layui-card" style="margin-left: 200px; margin-right: 200px;">
							<table class="layui-table">
								<colgroup>
									<col width="150">
									<col width="150">
									<col width="150">
								</colgroup>
								<thead>
									<tr>
										<th>现有数据</th>
										<th>已离职员工</th>
										<th>离职百分比</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td id="sumNumber" align="center"></td>
										<td id="leftCount" align="center"></td>
										<td id="leftRatio" align="center"></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div id="single_attr" class="chart_block">
					<fieldset class="layui-elem-field layui-field-title title">
						<legend>单因素分析</legend>
					</fieldset>
					<div class="layui-form attr_select">
						<div class="layui-form-item">
							<div class="layui-input-block" style="margin-left: 50px">
								<select name="modules" lay-filter="attribute">
									<option value="">请选择</option>
									<option value="sales" selected="">sales</option>
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
					<div id="single_attr_chart" style="height:80%;padding:10px;"></div>
				</div>
				<div id="attr_weight" class="chart_block">
					<fieldset class="layui-elem-field layui-field-title title">
						<legend>因素重要性</legend>
					</fieldset>

					<div id="attr_weight_pie" style="height:80%;padding:10px;"></div>
				</div>

			</div>
			<jsp:include page="footer.jsp" />
		</div>
		<%--//		<%@ include file="check.jsp" %>--%>

		<script src="plugins/layui/layui.js"></script>

		<script src="js/echarts.js"></script>

		<script type="text/javascript">
			require.config({
				paths: {
					echarts: './js'
				}
			});
			$.ajax({
				type: "post",
				url: "http://localhost:8080/LeftRatioServlet",
				dataType: "json",
				data: JSON.stringify({
					"reqId": window.localStorage.id,
					"reqParam": {}
				}),
				success: function(res) {
					var sum = parseInt(res.resData.list[0].ratio);
					var ratio = res.resData.list[1].ratio;
					var count = parseInt(sum * ratio);
					document.getElementById("sumNumber").innerText = sum;
					document.getElementById("leftCount").innerText = count;
					document.getElementById("leftRatio").innerText = (ratio * 100) + "%";
				}
			});
			$.ajax({
				type: "post",
				url: "http://localhost:8080/AnalysisAllServlet",
				dataType: "json",
				data: JSON.stringify({
					"reqId": window.localStorage.id,
					"reqParam": {}
				}),
				success: function(res) {
					console.log(res);
					var attrs = [{}];
					var header = [];
					for(var i = 0; i < res.resData.length; i++) {
						attrs[i] = {
							name: res.resData[i].name,
							value: res.resData[i].D
						};

						header[i] = res.resData[i].name;
					}
					require(
						[
							'echarts',
							'echarts/chart/pie',
							'echarts/chart/funnel'
						],
						function(ec) {
							// 基于准备好的dom，初始化echarts图表
							var myChart = ec.init(document.getElementById('attr_weight_pie'));

							var option = {
								title: {
									text: '各因素信息增益率比重',
									x: 'center'
								},
								tooltip: {
									trigger: 'item',
									formatter: "{a} <br/>{b} : <br/>{c} ({d}%)",
									textStyle: {
										fontSize: 14
									}
								},
								calculable: true,
								toolbox: {
									show: true,
									feature: {
										saveAsImage: {
											show: true
										}
									}
								},
								calculable: false,
								series: [{
									name: '因素重要性',
									type: 'pie',
									radius: '50%',
									center: ['50%', '60%'],
									data: attrs
								}]
							};

							// 为echarts对象加载数据
							myChart.setOption(option);
						}
					)
				},
				error: function(err) {}
			});
			layui.use(['element', 'form'], function() {
				var element = layui.element,
					form = layui.form;

				var getAttrRatio = (data) => {
					$.ajax({
						type: "post",
						url: "http://localhost:8080/AnalysisPartServlet",
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
										var myChart = ec.init(document.getElementById('single_attr_chart'));

										var option = {
											title: {
												text: '单因素离职率分析',
												x: 'center'
											},
											tooltip: {
												show: true
											},
											calculable: true,
											toolbox: {
												show: true,
												feature: {
													saveAsImage: {
														show: true
													}
												}
											},
											xAxis: [{
												name: data.value,
												type: 'category',
												data: header,
												axisLabel: {
													interval: 0,
													rotate: 20, // 20度角倾斜显示(***这里是关键)
												},
											}],
											yAxis: [{
												max: 1,
												min: 0,
												name: '离职率',
												type: 'value'
											}],
											series: [{
												"name": "离职率",
												"type": "bar",
												"data": value,
												itemStyle: {
													normal: {

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
										var myChart = ec.init(document.getElementById('single_attr_chart'));

										var option = {
											title: {
												text: '单因素离职率分析',
												x: 'center'
											},
											tooltip: {
												trigger: 'axis'
											},
											toolbox: {
												show: true,
												feature: {
													saveAsImage: {
														show: true
													}
												}
											},
											xAxis: [{
												name: data.value,
												type: 'category',
												boundaryGap: true,
												data: header,
												axisLine: {
													lineStyle: {
														color: '#2f4554'
													}
												}
											}],
											yAxis: [{
												name: '离职率',
												max: 1,
												min: 0,
												type: 'value',
												axisLabel: {
													formatter: '{value}'
												},
												axisLine: {
													lineStyle: {
														color: '#2f4554'
													}
												}
											}],
											series: [{
												name: '离职率',
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
				}
				form.on('select(attribute)', getAttrRatio);
				getAttrRatio({value:"sales"});
			});
		</script>

	</body>

</html>