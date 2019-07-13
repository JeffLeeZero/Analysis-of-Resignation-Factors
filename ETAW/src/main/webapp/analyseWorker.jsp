<%--
  Created by IntelliJ IDEA.
  User: 万宇
  Date: 2019/6/29
  Time: 15:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="check.jsp" %>
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

        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px">
            <legend>预测结果</legend>
        </fieldset>

        <div style="padding: 20px; background-color: #F2F2F2; margin-left: 300px">
            <div class="layui-row layui-col-space15">
                <div class="layui-col-md6">
                    <div class="layui-card">
                        <div class="layui-card-header">
                            <p style="font-size: medium; text-align: center">结果</p>
                        </div>

                        <div class="layui-card-body">
                            经预测，该员工
                            <div class = "result">
                                <%=(String)session.getAttribute("left") %>
                            </div>

                        </div>

                    </div>
                </div>
            </div>
        </div>

        <c:if test= "${sessionScope.left == '离职'}">

        <div style="padding: 20px; background-color: #F2F2F2; margin-left: 300px" id = "reason">
            <div class="layui-row layui-col-space15">
                <div class="layui-col-md6">
                    <div class="layui-card">
                        <div class="layui-card-header">
                            <p style="font-size: medium; text-align: center">分析</p>
                        </div>

                        <div class="layui-card-body">
                            分析原因：该员工离职的主要因素为
                            <div class="factor">
                                “<%=(String)session.getAttribute("factor") %>”
                            </div>
                            <%=(String)session.getAttribute("measure") %>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        </c:if>

        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px">
            <legend>分析准确率</legend>
        </fieldset>

        <!--Step:1 Prepare a dom for ECharts which (must) has size (width & hight)-->
        <!--Step:1 为ECharts准备一个具备大小（宽高）的Dom-->
        <div id="pie" style="height:300px;border:1px solid #ccc;padding:10px;"></div>

        <!--Step:2 Import echarts.js-->
        <!--Step:2 引入echarts.js-->
        <script src="js/echarts.js"></script>

        <script>
            if(document.getElementById("result").equals("不离职")){
                document.getElementById("reason").display = "none";
            }
        </script>

        <script type="text/javascript">

            // Step:3 conifg ECharts's path, link to echarts.js from current page.
            // Step:3 为模块加载器配置echarts的路径，从当前页面链接到echarts.js，定义所需图表路径
            require.config({
                paths: {
                    echarts: './js'
                }
            });

            require(
                [
                    'echarts',
                    'echarts/chart/pie',
                ],
                function(ec) {
                    // 基于准备好的dom，初始化echarts图表
                    var myChart = ec.init(document.getElementById('pie'));

                    var option = {
                        title : {
                            text: '该员工离职概率分析准确率',
                            x:'center'
                        },
                        tooltip : {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                        },
                        legend: {
                            orient : 'vertical',
                            x : 'left',
                            data:['准确','不准确']
                        },
                        toolbox: {
                            show : false,
                            feature : {
                                mark : {show: true},
                                dataView : {show: true, readOnly: false},
                                magicType : {
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
                                restore : {show: true},
                                saveAsImage : {show: true}
                            }
                        },
                        calculable : true,
                        series : [
                            {
                                type:'pie',
                                radius : '55%',
                                center: ['50%', '60%'],
                                data:[
                                    {value:<%=(String)session.getAttribute("accuracyRate") %>, name:'准确'},
                                    {value:<%=(String)session.getAttribute("notAccuracyRate") %>, name:'不准确'}
                                ]
                            }
                        ]
                    };

                    // 为echarts对象加载数据
                    myChart.setOption(option);
                }
            );
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
<style>
    .factor
    {
        color: red;
        font-size: 24px;
    }
</style>

</html>
