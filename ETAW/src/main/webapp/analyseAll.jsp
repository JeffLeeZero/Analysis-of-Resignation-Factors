<%--
  Created by IntelliJ IDEA.
  User: 万宇
  Date: 2019/6/25
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<title>员工离职因素总体分析</title>
		<script src="js/jquery-3.3.1.min.js"></script>
		<link rel="stylesheet" href="plugins/layui/css/layui.css">
	</head>

	<body class="layui-layout-body">
		<div class="layui-layout layui-layout-admin">

			<jsp:include page="header.jsp" />

			<div class="layui-body" style="background-color: #eeeeee;  ">

				<fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
					<legend>数据统计</legend>
				</fieldset>

				<div class="layui-col-md12">
					<div class="layui-card" style="margin-left: 200px; margin-right: 200px;">
						<table class="layui-table" lay-size="lg">
							<colgroup>
								<col width="150">
								<col width="200">
								<col>
							</colgroup>
							<thead>
								<tr>
									<th>现有员工数据</th>
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

				<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px">
					<legend>总体分析</legend>
				</fieldset>

				<!--Step:1 Prepare a dom for ECharts which (must) has size (width & hight)-->
				<!--Step:1 为ECharts准备一个具备大小（宽高）的Dom-->
				<div id="attr_weight_pie" style="height:400px;border:1px solid #ccc;padding:10px;"></div>

				<!--Step:2 Import echarts.js-->
				<!--Step:2 引入echarts.js-->
				<script src="js/echarts.js"></script>

				<script type="text/javascript">
					// Step:3 conifg ECharts's path, link to echarts.js from current page.
					// Step:3 为模块加载器配置echarts的路径，从当前页面链接到echarts.js，定义所需图表路径

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
									value: res.resData[i].D*1000
								};

								header[i] = res.resData[i].name;
							}
							console.log(attrs);
							require(
								[
									'echarts',
									'echarts/chart/pie',
									'echarts/chart/funnel'
								],
								function(ec) {
									// 基于准备好的dom，初始化echarts图表
									var myChart = ec.init(document.getElementById('attr_weight_pie'));
									var option1 = {
										title: {
											text: '某站点用户访问来源',
											subtext: '纯属虚构',
											x: 'center'
										},
										tooltip: {
											trigger: 'item',
											formatter: "{a} <br/>{b} : {c} ({d}%)"
										},
										toolbox: {
											show: true,
											feature: {
												mark: {
													show: true
												},
												dataView: {
													show: true,
													readOnly: false
												},
												magicType: {
													show: true,
													type: ['pie', 'funnel'],
													option: {
														funnel: {
															x: '25%',
															width: '50%',
															funnelAlign: 'left',
															max: 1548
														}
													}
												},
												restore: {
													show: true
												},
												saveAsImage: {
													show: true
												}
											}
										},
										calculable: true,
										series: [{
											name: '访问来源',
											type: 'pie',
											radius: '55%',
											center: ['50%', '60%'],
											data: attrs
										}]
									};
									var option = {
										title: {
											text: '各因素信息增益率比重',
											x: 'center'
										},
										tooltip: {
											trigger: 'item',
											formatter: "{a} <br/>{b} : {c} ({d}%)"
										},
										calculable: true,
										toolbox: {
											show: true,
											feature: {
												magicType: {
													show: true,
													type: ['pie', 'funnel'],
													option: {
														funnel: {
															x: '25%',
															width: '50%',
															funnelAlign: 'left',
															max: 1548
														}
													}
												},
												saveAsImage: {
													show: true
												}
											}
										},
										calculable: false,
										series: [{
											name: '因素重要性',
											type: 'pie',
											radius: '55%',
											center: ['50%', '60%'],
											data: attrs
										}]
									};

									// 为echarts对象加载数据
									myChart.setOption(option1);
								}
							)
						},
						error: function(err) {}
					});
				</script>

			</div>
			<jsp:include page="footer.jsp" />
		</div>

		<script src="plugins/layui/layui.js"></script>
		<script>
			//JavaScript代码区域
			layui.use('element', function() {
				var element = layui.element;
			});
		</script>

	</body>

</html>