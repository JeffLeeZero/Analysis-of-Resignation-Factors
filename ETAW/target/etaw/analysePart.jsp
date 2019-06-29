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
					<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px">
						<legend>选择影响因素</legend>
					</fieldset>
					<div class="layui-form">
						<div class="layui-form-item">
							<div class="layui-input-block" style="margin-left: 50px">
								<select name="modules" lay-filter="attribute">
									<option value="">请选择</option>
									<option value="sales">sales</option>
									<option value="2">技术部</option>
									<option value="3">人事部</option>
									<option value="4">财务部</option>
									<option value="5">管理部</option>
								</select>
							</div>
						</div>
					</div>
				</div>

				<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px">
					<legend>部门结果</legend>
				</fieldset>
				<div id="pie" style="height:500px;border:1px solid #ccc;padding:10px;"></div>
				<script src="js/echarts.js"></script>

			</div>
			<jsp:include page="footer.jsp" />
		</div>

		<script src="plugins/layui/layui.js"></script>
		<script type="text/javascript">
			layui.use(['element', 'form'], function() {
				var element = layui.element,
					form = layui.form;
				form.on('select(attribute)', (data) => {
					$.ajax({
						type: "post",
						url: "http://localhost:8080/AnalysisPartServlet",
						dataType: "json",
						data: JSON.stringify({
							"reqId": "jeff11",
							"reqParam": {
								"name":data.value
							}
						}),
						success:function(res){
							console.log(res);
						}
					});
				});

			});

			require.config({
				paths: {
					echarts: './js'
				}
			});
			var attributes = [];
			var barRender = function(ec) {
				// 基于准备好的dom，初始化echarts图表
				var myChart = ec.init(document.getElementById('pie'));

				var option = {
					tooltip: {
						show: true
					},
					legend: {
						data: ['员工离职率']
					},
					xAxis: [{
						type: 'category',
						data: header
					}],
					yAxis: [{
						type: 'value'
					}],
					series: [{
						"name": "信息增益率",
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
			}

			

			require(
				[
					'echarts',
					'echarts/chart/bar',
				],

				function(ec) {
					var myChart = ec.init(document.getElementById('pie'));
					myChart.setOption({
						title: {
							text: '员工离职因素部门分析',
							subtext: '2019年',
							x: 'center'
						},
						tooltip: {
							trigger: 'item',
							formatter: "{a} <br/>{b} : {c} ({d}%)"
						},
						legend: {
							orient: 'vertical',
							x: 'left',
							data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
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
									type: ['pie'],
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
							data: [{
									value: 335,
									name: '直接访问'
								},
								{
									value: 310,
									name: '邮件营销'
								},
								{
									value: 234,
									name: '联盟广告'
								},
								{
									value: 135,
									name: '视频广告'
								},
								{
									value: 1548,
									name: '搜索引擎'
								}
							]
						}]
					});
				}
			);
		</script>

	</body>

</html>