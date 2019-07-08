<%--
  Created by IntelliJ IDEA.
  User: MQD
  Date: 2019/7/8
  Time: 17:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>离职文章</title>
    <script type="text/javascript" src="js/share.js"></script>
    <link rel="stylesheet" href="plugins/layui/css/layui.css">
    <link type="text/css" rel="stylesheet" href="css/passage.css">
</head>
<body>
<div class="layui-layout layui-layout-admin">
<jsp:include page="header.jsp" />

    <div>
        <div class="page_front pageFront_animation" id="page_1">
            <span id="title_span">标题</span>
            <span id="author_span">xxx</span>
            <span id="content_span">团长，你在干什么，不要停下来</span>
        </div>
        <div class="page_back pageBack_animation" id="page_2">

        </div>
        <button type="button" id="next_btn" title="下一篇"><li class="layui-icon layui-icon-next"></li></button>
        <button type="button" id="pre_btn" title="上一篇"><li class="layui-icon layui-icon-prev"></li></button>
    </div>
    <script>
        function onNextBtnPressed() {
            var pageFront = document.getElementById("page_1");
            var pageBack = document.getElementById("page_2");
            pageFront.className='page_front pageFront_animation';
            pageBack.className='page_back pageBack_animation';
            setTimeout(nextPassage,1000);
        }
        function nextPassage() {

        }
    </script>
<jsp:include page="footer.jsp"/>
</div>
</body>
</html>
