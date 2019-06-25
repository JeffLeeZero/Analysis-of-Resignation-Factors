<%--
  Created by IntelliJ IDEA.
  User: 万宇
  Date: 2019/6/25
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>员工离职因素历史数据分析</title>

    <link rel="stylesheet" href="plugins/layui/css/layui.css">
</head>

<body class="layui-layout-body">
    <div class="layui-layout layui-layout-admin">

        <jsp:include page="header.jsp"/>

        <div class="layui-body" style="background-color: #eeeeee;  ">

            <!--Step:1 Prepare a dom for ECharts which (must) has size (width & hight)-->
            <!--Step:1 为ECharts准备一个具备大小（宽高）的Dom-->
            <div id="bar" style="height:500px;border:1px solid #ccc;padding:10px;"></div>

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
                        'echarts/chart/bar',
                        'echarts/chart/line'
                    ],

                    function (ec) {
                        //---柱状图
                        var myChart = ec.init(document.getElementById('bar'));
                        myChart.setOption({
                            title : {
                                text: '公司离职历史数据',
                                subtext: '2010年-2018年'
                            },
                            tooltip : {
                                trigger: 'axis'
                            },
                            legend: {
                                data:['离职人数','总人数']
                            },
                            toolbox: {
                                show : true,
                                feature : {
                                    mark : {show: true},
                                    dataView : {show: true, readOnly: false},
                                    magicType : {show: true, type: ['bar']},
                                    restore : {show: true},
                                    saveAsImage : {show: true}
                                }
                            },
                            calculable : true,
                            xAxis : [
                                {
                                    type : 'category',
                                    data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
                                }
                            ],
                            yAxis : [
                                {
                                    type : 'value'
                                }
                            ],
                            series : [
                                {
                                    name:'总人数',
                                    type:'bar',
                                    data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
                                    markPoint : {
                                        data : [
                                            {type : 'max', name: '最大值'},
                                            {type : 'min', name: '最小值'}
                                        ]
                                    },
                                    markLine : {
                                        data : [
                                            {type : 'average', name: '平均值'}
                                        ]
                                    }
                                },
                                {
                                    name:'离职人数',
                                    type:'bar',
                                    data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
                                    markPoint : {
                                        data : [
                                            {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
                                            {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
                                        ]
                                    },
                                    markLine : {
                                        data : [
                                            {type : 'average', name : '平均值'}
                                        ]
                                    }
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
