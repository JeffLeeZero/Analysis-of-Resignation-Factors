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

    <link rel="stylesheet" href="plugins/layui/css/layui.css">
</head>

<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">

    <jsp:include page="header.jsp"/>



    <div class="layui-body" style="background-color: #eeeeee;  ">

        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
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
                    <c:forEach items="${workers}" var="worker">
                    <tr>
                        <td align="center"><%=(String)session.getAttribute("allNumber") %></td>
                        <td align="center"><%=(String)session.getAttribute("leftNumber") %></td>
                        <td align="center"><%=(String)session.getAttribute("leftRatio") %>%</td>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px">
            <legend>总体分析</legend>
        </fieldset>

        <!--Step:1 Prepare a dom for ECharts which (must) has size (width & hight)-->
        <!--Step:1 为ECharts准备一个具备大小（宽高）的Dom-->
        <div id="pie" style="height:500px;border:1px solid #ccc;padding:10px;"></div>

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

            // Step:4 require echarts and use it in the callback.
            // Step:4 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
            require(
                [
                    'echarts',
                    'echarts/chart/pie',
                ],

                function (ec) {
                    //---饼状图
                    var myChart = ec.init(document.getElementById('pie'));
                    myChart.setOption({
                        title : {
                            text: '员工离职因素总体分析',
                            subtext: '2019年',
                            x:'center'
                        },
                        tooltip : {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                        },
                        legend: {
                            orient : 'vertical',
                            x : 'left',
                            data:['离职','未离职']
                        },
                        toolbox: {
                            show : true,
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
                                name:'访问来源',
                                type:'pie',
                                radius : '55%',
                                center: ['50%', '60%'],
                                data:[
                                    {value:<%=(String)session.getAttribute("allNumber") %>, name:'离职'},
                                    {value:<%=(String)session.getAttribute("remainNumber") %>, name:'未离职'}
                                ]
                            }
                        ]
                    });
                }
            );
        </script>

    </div>
    <jsp:include page="footer.jsp"/>
</div>

<script src="plugins/layui/layui.js"></script>
<script>
    //JavaScript代码区域
    layui.use('element', function(){
        var element = layui.element;

    });

    layui.use('upload', function(){
        var $ = layui.jquery
            ,upload = layui.upload;

        //拖拽上传
        upload.render({
            elem: '#test10'
            ,url: '/upload/'
            ,done: function(res){
                console.log(res)
            }
        });
    });
</script>

</body>
</html>
