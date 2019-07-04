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
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>数据导入</title>
    <link rel="stylesheet" href="plugins/layui/css/layui.css">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">

    <jsp:include page="header.jsp" />

    <div class="layui-body" style="background-color: #eeeeee;  ">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px">
            <legend>导入数据</legend>
        </fieldset>
        <form class="layui-form" action="#" enctype="multipart/form-data" id="up_form">

            <input type="file" name="uploadFile" />

            <input type="hidden" name="account" id="formAccount"/>

            <button class="layui-btn layui-btn-normal" lay-submit="" id="upup" lay-filter="upup">提交</button>

        </form>
    </div>

    <jsp:include page="footer.jsp" />
</div>

</html>
