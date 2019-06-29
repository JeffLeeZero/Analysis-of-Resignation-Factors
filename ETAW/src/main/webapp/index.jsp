<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>员工离职因素分析网站</title>
    <link rel="stylesheet" href="plugins/layui/css/layui.css">
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
                <legend>导入数据</legend>
            </fieldset>

            <div class="layui-upload-drag" id="test10" style="margin-left: 50px;" >
                <i class="layui-icon"></i>
                <p>点击上传，或将文件拖拽到此处</p>
            </div>

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
                        <tr>
                            <td>340</td>
                            <td>34</td>
                            <td>10%</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>


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
