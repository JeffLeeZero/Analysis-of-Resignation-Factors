<%--
  Created by IntelliJ IDEA.
  User: MQD
  Date: 2019/7/3
  Time: 19:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>数据导入</title>
    <link rel="stylesheet" href="plugins/layui/css/layui.css">

    <link rel="stylesheet" type="text/css" href="plugins/background/css/bootstrap.css">
    <link rel="stylesheet" href="plugins/background/css/style.css">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">

    <jsp:include page="header.jsp" />

    <div id="top-image">
        <div id="content" class="container center-block">
            <div class="jumbotron">
                <div class="container" style="margin-left: 150px; margin-top:300px">
                    <div class="input-group input-group-lg">

                        <span class="input-group-addon" id="sizing-addon1">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true">
                            </span>
                        </span>

                        <form class="layui-form" action="#" enctype="multipart/form-data" id="up_form">

                                <input type="file" name = "uploadFile" class="form-control" placeholder="请导入文件" aria-describedby="sizing-addon1">

                                <input type="hidden" name="account" id="formAccount"/>
                            
                        <span class="input-group-btn">
                            <button class="layui-btn layui-btn-normal" lay-submit="" id="upup" lay-filter="upup" type="button">提 交</button>
                                <%--<button class="layui-btn layui-btn-normal" lay-submit="" id="upup" lay-filter="upup">提 交</button>--%>
                        </span>

                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>

    <%--<div class="layui-body" style="background-color: #eeeeee;  ">--%>
        <%--<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px">--%>
            <%--<legend>导入数据</legend>--%>
        <%--</fieldset>--%>
    <%--</div>--%>

    <jsp:include page="footer.jsp" />
</div>

<script src="plugins/background/js/jquery.min.js"></script>
<script src="plugins/background/js/ios-parallax.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        $('#top-image').iosParallax({
            movementFactor: 50
        });
    });
</script>

</body>

</html>
