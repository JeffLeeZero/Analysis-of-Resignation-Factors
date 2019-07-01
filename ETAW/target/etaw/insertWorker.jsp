<%--
  Created by IntelliJ IDEA.
  User: 万宇
  Date: 2019/6/29
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>员工离职因素分析网站</title>
    <link rel="stylesheet" href="plugins/layui/css/layui.css" media="all">
</head>

<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">

    <jsp:include page="header.jsp"/>

    <div class="layui-body" style="background-color: #eeeeee;  ">
        <!-- 内容主体区域 -->
        <div style="padding: 15px;">
            <i class="layui-icon layui-icon-face-smile" style="font-size: 40px; color: #FF5722;">你好</i>
            <br><br>
            <p style="font-size: 30px; color: #009688;">欢迎使用员工离职分析系统</p>
        </div>

        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px">
            <legend>员工数据</legend>
        </fieldset>

        <form class="layui-form" method="post" action="<%=request.getContextPath()%>/InsertWorkerServlet"  enctype="multipart/form-data">

            <div class="layui-form-item" style="margin-left: 200px">
            <label class="layui-form-label" style="width: 200px">该员工对公司满意度</label>
            <div class="layui-input-block" style="margin-right: 300px">
                <input type="text" name="SatisfactionLevel" lay-verify="required" autocomplete="off" placeholder="" class="layui-input" style="width: 200px">
            </div>
            </div>

            <div class="layui-form-item" style="margin-left: 200px">
            <label class="layui-form-label" style="width: 200px">该员工上次考核评价</label>
            <div class="layui-input-block" style="margin-right: 300px">
                <input type="text" name="LastEvaluation" lay-verify="required" autocomplete="off" placeholder="" class="layui-input" style="width: 200px">
            </div>
            </div>

            <div class="layui-form-item" style="margin-left: 200px">
            <label class="layui-form-label" style="width: 200px">该员工参与项目数量</label>
            <div class="layui-input-block" style="margin-right: 300px">
                <input type="text" name="NumberProject" lay-verify="required" autocomplete="off" placeholder="" class="layui-input" style="width: 200px">
            </div>
            </div>

            <div class="layui-form-item" style="margin-left: 200px">
            <label class="layui-form-label" style="width: 200px">该员工平均月工作时长</label>
            <div class="layui-input-block" style="margin-right: 300px">
                <input type="text" name="AverageMonthly" lay-verify="required" autocomplete="off" placeholder="" class="layui-input" style="width: 200px">
            </div>
            </div>

            <div class="layui-form-item" style="margin-left: 200px">
            <label class="layui-form-label" style="width: 200px">该员工工龄</label>
            <div class="layui-input-block" style="margin-right: 300px">
                <input type="text" name="TimeSpendCompany" lay-verify="required" autocomplete="off" placeholder="" class="layui-input" style="width: 200px">
            </div>
            </div>

            <div class="layui-form-item"  style="margin-left: 200px">
                <label class="layui-form-label" style="width: 200px">该员工是否发生过工作事故</label>
                <div class="layui-input-block" style="margin-right: 300px">
                    <input type="radio" name="WorkAccident" value="0" title="是" checked>
                    <input type="radio" name="WorkAccident" value="1" title="否">
                </div>
            </div>

            <div class="layui-form-item" style="margin-left: 200px">
                <label class="layui-form-label" style="width: 200px">该员工是否得到晋升</label>
                <div class="layui-input-block"  style="margin-right: 300px">
                    <input type="radio" name="Promotion" value="0" title="是" checked>
                    <input type="radio" name="Promotion" value="1" title="否">
                </div>
            </div>

            <div class="layui-form-item" style="margin-left: 200px">
                <div class="layui-inline" >
                    <label class="layui-form-label" style="width: 200px">该员工所在部门</label>
                    <div class="layui-input-inline" style="margin-right: 300px">
                        <select name="Department" lay-verify="required" lay-search="">
                            <option value="">直接选择或搜索选择部门</option>
                            <option value="1">销售部</option>
                            <option value="2">技术部</option>
                            <option value="3">人事部</option>
                            <option value="4">财务部</option>
                            <option value="5">管理部</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="layui-form-item" style="margin-left: 200px">
                <div class="layui-inline">
                    <label class="layui-form-label" style="width: 200px">该员工薪资水平</label>
                    <div class="layui-input-inline" style="margin-right: 300px">
                        <select name="Salary" lay-verify="required" lay-search="">
                            <option value="">选择薪资水平</option>
                            <option value="0">高</option>
                            <option value="1">中</option>
                            <option value="2">低</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="layui-form-item" style="margin-left: 300px">
                <div class="layui-input-block">
                    <button type="submit" lay-submit="" lay-filter="demo1" class="layui-btn">立即提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>

    </div>
    <%
        String mess=(String)session.getAttribute("message");
        if("".equals(mess) || mess == null ){

        }
        else
        {
    %>
    <script type="text/javascript">alert("<%=mess%>");</script>
    <%
        }
        session.invalidate();
    %>

</div>
    </div>
    <jsp:include page="footer.jsp"/>
</div>
<script src="plugins/layui/layui.js" charset="utf-8"></script>
<script>
    layui.use('form', function(){
        var form = layui.form
            ,layer = layui.layer

        //监听指定开关
        form.on('switch(switchTest)', function(data){
            layer.msg('开关checked：'+ (this.checked ? 'true' : 'false'), {
                offset: '6px'
            });
            layer.tips('温馨提示：请注意开关状态的文字可以随意定义，而不仅仅是ON|OFF', data.othis)
        });

        //监听提交
        form.on('submit(demo1)', function(data){

            // layer.alert(JSON.stringify(data.field), {
            //     title: '最终的提交信息'
            // });

            $.ajax({
                url: "http://localhost:8080/InsertWorkerServlet",
                data:data.field,
                type:"POST",
                dataType:"json",
                success:function(msg){
                    //console.log(msg)
                    window.location.href="/analyseWorker.jsp";
                },
                error:function(error){
                    //alert(error)
                    window.location.href="/analyseWorker.jsp";
                }
            });
            return false;
        });
        form.render();
    });
</script>
</body>
</html>
