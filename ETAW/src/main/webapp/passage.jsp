<%--
  Created by IntelliJ IDEA.
  User: MQD
  Date: 2019/7/8
  Time: 17:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="check.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>离职文章</title>
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="plugins/layui/layui.all.js"></script>
    <link rel="stylesheet" href="plugins/layui/css/layui.css">
    <link type="text/css" rel="stylesheet" href="css/passage.css">
</head>
<body>
<div class="layui-layout layui-layout-admin">
<jsp:include page="header.jsp" />

    <div class="layui-body">
        <img src="img/map.png"/>
        <div>

            <div class="page_front" id="page_1">
                <span id="title_span"></span>
                <span id="author_span"></span>
                <span id="content_span"></span>
            </div>

            <div class="page_back" id="page_2">

            </div>

            <div class="next">
                <button type="button" id="next_btn" title="下一篇" onclick="next()">
                    <img src="img/next.png" class="" />
                </button>
            </div>

            <div class="last">
                <button type="button" id="pre_btn" title="上一篇" onclick="pre()">
                    <img src="img/last.png" class="" />
                </button>
            </div>



            <input type="text" id="btnAction" name="btnAction" value="next" style="visibility:hidden">
        </div>
    </div>
    <script>
        var pageNum;    //文章索引号
        var count;      //文章数量
       $(function () {
            pageNum=0;
            console.log(pageNum);
            initData();
        }());
       function initData() {
           $.ajax({
               url:'/LoginPassServlet',
               type:'GET',
               dataType:"json",
               success:function(data){
                   count=data.count;
                   console.log(count);
                   $("#title_span").html(data.title);
                   $("#author_span").html(data.author);
                   $("#content_span").html("   "+data.content);
               },
               error:function(err){
                   console.log(err);
                   count=0;
                   $("#title_span").html("");
                   $("#author_span").html("");
                   $("#content_span").html("");
                   layer.msg("网络异常，请重试", {icon: 2,time: 1500, anim: 6});
               }
           });
       }
       function next() {
           $("#btnAction").val("next");
           pageNum+=1;
           console.log(pageNum);
           changePassage();
       }
       function pre() {
           $("#btnAction").val("pre");
           pageNum-=1;
           changePassage();
       }
       function changePassage() {
           if (pageNum>=count) {
               pageNum=count-1;
               layer.msg("已经是最后一篇了",{icon: 0,time: 1500});
               return;
           }
           if (pageNum<=0){
               pageNum=0;
               layer.msg("已经是第一一篇了",{icon: 0,time: 1500});
               return;
           }
            var pageFront = document.getElementById("page_1");
            var pageBack = document.getElementById("page_2");
            pageFront.className='page_front pageFront_animation';
            pageBack.className='page_back pageBack_animation';
            setTimeout(getPassage,500);
            setTimeout(changeClass,1600);
        }
        function changeClass() {
            var pageFront = document.getElementById("page_1");
            var pageBack = document.getElementById("page_2");
            pageFront.className='page_front';
            pageBack.className='page_back';
        }
        //到后台获取passage
        function getPassage() {
            $.ajax({
                url:'/LoginPassServlet',
                type:'POST',
                dataType:"json",
                data:{"btnAction":$("#btnAction").val(),"pageNum":pageNum},
                success:function(data){
                    $("#title_span").html(data.title);
                    $("#author_span").html(data.author);
                    $("#content_span").html('\t'+data.content);
                },
                error:function(err){
                    console.log(err);
                    count=0;
                    $("#title_span").html("");
                    $("#author_span").html("");
                    $("#content_span").html("");
                    layer.msg("网络异常，请重试", {icon: 2,time: 1500, anim: 6});
                }
            });
        }
    </script>
<jsp:include page="footer.jsp"/>
</div>
</body>

<style>
    .last{
        position: absolute;
        width: 50%;
        height: 50%;
        left: 0;
        top: 40%;
    }

    .next{
        position: absolute;
        width: 50%;
        height: 50%;
        left: 50%;
        top: 40%;
    }
</style>
</html>
