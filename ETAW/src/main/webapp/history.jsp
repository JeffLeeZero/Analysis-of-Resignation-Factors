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

        <jsp:include page="header.jsp"/>
            <div class="layui-body">
                <table class="layui-hide" id="test" lay-filter="test"></table>

                <script type="text/html" id="toolbarDemo">
</script>

                <script type="text/html" id="barDemo">
                    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
                    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
                </script>


                <script>
                    layui.use('table', function(){
                        var table = layui.table;

                        table.render({
                            elem: '#test'
                            ,url:'http://localhost:8080/HistoryServlet'
                            ,toolbar: '#toolbarDemo'
                            ,contentType:'application/json'
                            ,method:"POST"
                            ,deal:function(res){
                                return{
                                    code:0,msg:"",count:1000,data:res.resData
                                }
                            }
                            ,where:{
                                "reqId":window.localStorage.id
                            }
                            ,cols: [[
                                {type: 'checkbox', fixed: 'left'}
                                ,{field:'worker_number', title:'编号', width:80, fixed: 'left', unresize: true, sort: true}
                                ,{field:'satisfaction_level', title:'满意度', sort: true,width:80, edit: 'text'}
                                ,{field:'last_evaluation', title:'考核', width:80,  sort: true,edit: 'text'}
                                ,{field:'average_monthly_hours', title:'月工时', width:80,  sort: true,edit: 'text'}
                                ,{field:'number_project', title:'参与项目数', width:80,sort: true, width:80}
                                ,{field:'time_spend_company', title:'工龄', sort: true,}
                                ,{field:'work_accident', title:'工伤', width:80}
                                ,{field:'promotion', title:'是否晋升', width:80}
                                ,{field:'sales', title:'部门', width:120, sort: true}
                                ,{field:'salary', title:'薪资', width:80}
                                ,{field:'left', title:'结果', width:80}
                                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:100}
                            ]]
                            ,page: false
                        });



                        //监听行工具事件
                        table.on('tool(test)', function(obj){
                            var data = obj.data;
                            //console.log(obj)
                            if(obj.event === 'del'){
                                layer.confirm('真的删除行么', function(index){
                                    obj.del();
                                    layer.close(index);
                                });
                            } else if(obj.event === 'edit'){
                                layer.prompt({
                                    formType: 2
                                    ,value: data.email
                                }, function(value, index){
                                    obj.update({
                                        email: value
                                    });
                                    layer.close(index);
                                });
                            }
                        });
                    });
                </script>
                    <%--<table class="layui-table" lay-size="lg" style="width: 100vw;">--%>
                        <%--<thead>--%>
                        <%--<tr class="td">--%>
                            <%--<th align="center" width="80">员工编号</th>--%>
                            <%--<th align="center" width="80">满意度</th>--%>
                            <%--<th align="center" width="80">上次评价</th>--%>
                            <%--<th align="center" width="80">月工作时长</th>--%>
                            <%--<th align="center" width="80">参与项目数量</th>--%>
                            <%--<th align="center" width="80">工龄</th>--%>
                            <%--<th align="center" width="80">工作事故</th>--%>
                            <%--<th align="center" width="80">是否晋升</th>--%>
                            <%--<th align="center" width="80">部门</th>--%>
                            <%--<th align="center" width="80">薪资水平</th>--%>
                            <%--<th align="center" width="80">预测结果</th>--%>
                        <%--</tr>--%>
                        <%--</thead>--%>
                        <%--<tbody>--%>
                        <%--<c:forEach items="${sessionScope.workerList}" var="workerList" varStatus="loop">--%>
                        <%--<tr>--%>
                            <%--<td align="center">--%>
                                    <%--${workerList.getWorker_number()}--%>
                            <%--</td>--%>
                            <%--<td align="center">--%>
                                    <%--${workerList.getSatisfaction_level()}--%>
                            <%--</td>--%>
                            <%--<td align="center">--%>
                                    <%--${workerList.getLast_evaluation()}--%>
                            <%--</td>--%>
                            <%--<td align="center">--%>
                                    <%--${workerList.getAverage_monthly_hours()}--%>
                            <%--</td>--%>
                            <%--<td align="center">--%>
                                    <%--${workerList.getNumber_project()}--%>
                            <%--</td>--%>
                            <%--<td align="center">--%>
                                    <%--${workerList.getTime_spend_company()}--%>
                            <%--</td>--%>
                            <%--<td align="center">--%>
                                    <%--${workerList.getWork_accident()}--%>
                            <%--</td>--%>
                            <%--<td align="center">--%>
                                    <%--${workerList.getPromotion()}--%>
                            <%--</td>--%>
                            <%--<td align="center">--%>
                                    <%--${workerList.getSales()}--%>
                            <%--</td>--%>
                            <%--<td align="center">--%>
                                    <%--${workerList.getSalary()}--%>
                            <%--</td>--%>
                            <%--<td align="center">--%>
                                    <%--${workerList.getLeft()}--%>
                            <%--</td>--%>

                            <%--</c:forEach>--%>
                        <%--</tbody>--%>
                    <%--</table>--%>
            </div>
        <%--<div class="layui-body" style="background-color: #eeeeee;  ">--%>



            <%--<!--Step:1 Prepare a dom for ECharts which (must) has size (width & hight)-->--%>
            <%--<!--Step:1 为ECharts准备一个具备大小（宽高）的Dom-->--%>
            <%--<div id="bar" style="height:500px;border:1px solid #ccc;padding:10px;"></div>--%>

            <%--<!--Step:2 Import echarts.js-->--%>
            <%--<!--Step:2 引入echarts.js-->--%>
            <%--<script src="js/echarts.js"></script>--%>

            <%--<script type="text/javascript">--%>
                <%--// Step:3 conifg ECharts's path, link to echarts.js from current page.--%>
                <%--// Step:3 为模块加载器配置echarts的路径，从当前页面链接到echarts.js，定义所需图表路径--%>
                <%--require.config({--%>
                    <%--paths: {--%>
                        <%--echarts: './js'--%>
                    <%--}--%>
                <%--});--%>

                <%--// Step:4 require echarts and use it in the callback.--%>
                <%--// Step:4 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径--%>
                <%--require(--%>
                    <%--[--%>
                        <%--'echarts',--%>
                        <%--'echarts/chart/bar',--%>
                        <%--'echarts/chart/line'--%>
                    <%--],--%>

                    <%--function (ec) {--%>
                        <%--//---柱状图--%>
                        <%--var myChart = ec.init(document.getElementById('bar'));--%>
                        <%--myChart.setOption({--%>
                            <%--title : {--%>
                                <%--text: '公司离职历史数据',--%>
                                <%--subtext: '2010年-2018年'--%>
                            <%--},--%>
                            <%--tooltip : {--%>
                                <%--trigger: 'axis'--%>
                            <%--},--%>
                            <%--legend: {--%>
                                <%--data:['离职人数','总人数']--%>
                            <%--},--%>
                            <%--toolbox: {--%>
                                <%--show : true,--%>
                                <%--feature : {--%>
                                    <%--mark : {show: true},--%>
                                    <%--dataView : {show: true, readOnly: false},--%>
                                    <%--magicType : {show: true, type: ['bar']},--%>
                                    <%--restore : {show: true},--%>
                                    <%--saveAsImage : {show: true}--%>
                                <%--}--%>
                            <%--},--%>
                            <%--calculable : true,--%>
                            <%--xAxis : [--%>
                                <%--{--%>
                                    <%--type : 'category',--%>
                                    <%--data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']--%>
                                <%--}--%>
                            <%--],--%>
                            <%--yAxis : [--%>
                                <%--{--%>
                                    <%--type : 'value'--%>
                                <%--}--%>
                            <%--],--%>
                            <%--series : [--%>
                                <%--{--%>
                                    <%--name:'总人数',--%>
                                    <%--type:'bar',--%>
                                    <%--data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],--%>
                                    <%--markPoint : {--%>
                                        <%--data : [--%>
                                            <%--{type : 'max', name: '最大值'},--%>
                                            <%--{type : 'min', name: '最小值'}--%>
                                        <%--]--%>
                                    <%--},--%>
                                    <%--markLine : {--%>
                                        <%--data : [--%>
                                            <%--{type : 'average', name: '平均值'}--%>
                                        <%--]--%>
                                    <%--}--%>
                                <%--},--%>
                                <%--{--%>
                                    <%--name:'离职人数',--%>
                                    <%--type:'bar',--%>
                                    <%--data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],--%>
                                    <%--markPoint : {--%>
                                        <%--data : [--%>
                                            <%--{name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},--%>
                                            <%--{name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}--%>
                                        <%--]--%>
                                    <%--},--%>
                                    <%--markLine : {--%>
                                        <%--data : [--%>
                                            <%--{type : 'average', name : '平均值'}--%>
                                        <%--]--%>
                                    <%--}--%>
                                <%--}--%>
                            <%--]--%>
                        <%--});--%>
                    <%--}--%>
                <%--);--%>
            <%--</script>--%>

        <%--</div>--%>
        <jsp:include page="footer.jsp"/>
    </div>

<script>
    var request = $.ajax({
        url: "http://localhost:8080/HistoryServlet",
        type: "POST",
        contentType: false,   //jax 中 contentType 设置为 false 是为了避免 JQuery 对其操作，从而失去分界符，而使服务器不能正常解析文件
        processData: false,   //当设置为true的时候,jquery ajax 提交的时候不会序列化 data，而是直接使用data
        error : function(request) {

        },
        success: function (data) {

        }
    });


    //JavaScript代码区域
    layui.use('element', function(){
        var element = layui.element;

    });

    layui.use('upload', function(){
        var $ = layui.jquery
            ,upload = layui.upload;

    });

</script>

</body>
<style>
    .td{
        min-width: 50px;
        max-width: 50px;
    }
</style>

</html>
