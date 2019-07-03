<%--
  Created by IntelliJ IDEA.
  User: 万宇
  Date: 2019/7/3
  Time: 16:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                        <th>员工编号</th>
                        <th>预测是否离职</th>
                        <th>预测准确率</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${workers}" var="worker">
                    <tr>
                        <td align="center">
                            <%=(String)session.getAttribute("allNumber") %>
                        </td>
                        <td align="center">
                            <%=(String)session.getAttribute("leftNumber") %>
                        </td>
                        <td align="center">
                            <%=(String)session.getAttribute("leftRatio") %>%</td>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>


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

