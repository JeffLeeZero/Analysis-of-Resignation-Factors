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

</head>
<body class="layui-layout-body">

<div class="layui-layout layui-layout-admin">

    <jsp:include page="header.jsp" />

        <div class="layui-body" style="background-color: #eeeeee;">

            <div class="background">

                <img src="img/map.png" />

            </div>

            <div class="steps">
                <div class="line1"></div>
                <p class="step1">Step1:上传离职数据文件，训练属于你的 人工智能</p>

                <div class="line2"></div>
                <p class="step2">Step2:查看统计数据，把握整体情况</p>

                <div class="line3"></div>
                <p class="step3">Step3:输入目标员工数据，预测离职与否</p>

            </div>

            <div class="form">
                <form class="layui-form" action="#" enctype="multipart/form-data" id="up_form">

                    <input/>
                    <button type="button" class="layui-btn layui-btn-normal" name="uploadFile" id="test1">选择文件</button>
                    <input type="hidden" class="layui-input" name="account" id="formAccount"/>
                    <button type="button" class="layui-btn layui-btn-normal" lay-submit="" id="upup" lay-filter="upup">提交</button>

                </form>
            </div>

            <div class="downloadFile">
                <p  style="color: black">离职数据文件具有严格的格式要求</p>
                <p style="color:black;">获取训练数据集样例文件，
                    <a href="static/model.csv" download="格式样例.csv" style="color: blue">点击下载</a>
                </p>
            </div>

            <div class="loading" style="display: none;">
                <img  class="loadingImage" src="img/loading.gif">
                <div class="loadingText">
                    <p>模型生成中...</p>
                </div>
            </div>

        </div>

    <jsp:include page="footer.jsp" />

</div>

<script src="plugins/layui/layui.js"></script>

<script type="text/javascript">
    document.getElementById("formAccount").value = window.localStorage.id;

    //JavaScript代码区域
    layui.use('element', function() {
        var element = layui.element;
    });

    layui.use('upload', function() {
        var $ = layui.jquery,
            upload = layui.upload;

        upload.render({
            elem: '#test1',
            url: '/UploadServlet',
            accept: 'file',
            auto: false,
            // , bindAction: '#upfile' //关闭的上传按钮   html中此id所在元素也被注释
            multiple: true,
            done: function (res) {
            }
        });
    });

    function fsubmit(fd) {
        $.ajax({
            url: "/UploadServlet",
            type: "POST",
            data: fd,
            contentType: false,   //jax 中 contentType 设置为 false 是为了避免 JQuery 对其操作，从而失去分界符，而使服务器不能正常解析文件
            processData: false,   //当设置为true的时候,jquery ajax 提交的时候不会序列化 data，而是直接使用data
            error : function(request) {
                $(".loading").toggle();
                alert("网络超时!");
                console.log(error);
                window.location.href = "inputPage.jsp";
            },
            success: function (data) {
                $(".loading").toggle();
                alert("上传成功！");
                window.location.href = "analyseAll.jsp";
            }
        });
        return false;
    }

    $("#upup").on("click",function () {
        $(".loading").toggle();
        var formSatellite = document.getElementById("up_form");//获取所要提交form的id
        var fs1 = new FormData(formSatellite);  //用所要提交form做参数建立一个formdata对象
        console.log(fs1);
        fsubmit(fs1);//调用函数
    })
</script>

</body>

<style>
    .background{
        position: absolute;
        left: 0px;
        top: 0px;
        width: 100%;
        height: 100%;

    }
   .background > img {
       position: absolute;
       left: 0;
       top: 0;
       width: 100%;
       height: 100%;
   }

    .steps{
        position: absolute;
        top: 20%;
        left: 30%;
        width: 40%;
        height: 30%;
    }
    .line1{
        position: absolute;
        left: 0;
        top: 30%;
        width: 1%;
        height: 15%;
        background-color: rgba(229, 28, 35, 1);
    }

    .step1{
        position: absolute;
        left: 5%;
        top: 30%;
        width: 100%;
        height: 5%;
        color: rgba(16, 16, 16, 1);
        font-size: 18px;
        text-align: left;
    }
    .line2{
        position: absolute;
        left: 0;
        top: 60%;
        width: 1%;
        height: 15%;
        background-color:  rgba(255, 242, 32, 1);
    }

    .step2{
        position: absolute;
        left: 5%;
        top: 60%;
        width: 100%;
        height: 5%;
        color: rgba(16, 16, 16, 1);
        font-size: 18px;
        text-align: left;
    }

    .line3{
        position: absolute;
        left: 0;
        top: 90%;
        width: 1%;
        height: 15%;
        background-color: rgba(77, 229, 28, 1);
    }

    .step3{
        position: absolute;
        left: 5%;
        top: 90%;
        width: 100%;
        height: 5%;
        color: rgba(16, 16, 16, 1);
        font-size: 18px;
        text-align: left;
    }

    #up_form{
        background-color: #FFFFFF;
        position: relative;
    }

    .form{
        position: absolute;
        top: 60%;
        width: 100%;
        height: 7%;
        text-align: left;
    }
    form{
        position: absolute;
        width: 57%;
        height: 100%;
        top: 60%;
        left: 21%;
        opacity: 100%;
    }
    #upup{
        background-color: #558AF5;
        width: 14%;
        height: 90%;
        right: 0;
        position: absolute;
    }
    #test1{
        background-color: #3f51b5;
        width: 18%;
        height: 90%;
        right: 16%;
        position: absolute;
    }
    form > input{
        width: 64%;
        height: 90%;
        right: 36%;
        position: absolute;
    }

    .downloadFile{
        position: absolute;
        width: 20%;
        height: 4%;
        left: 35%;
        top: 80%;
        font-size: 15px;
        text-align: left;
    }

    .loading{
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        opacity: 0.8;
        background-color: #FFFFFF;

    }


    .loadingImage{
        position: absolute;
        top: 30%;
        left: 30%;
        width: 30%;
        height: 30%;
    }

    .loadingText{
        position: absolute;
        top: 60%;
        left: 40%;
        width: 100%;
        height: 20%;
        color: black;
        font-size: 18px;
    }

		.layui-inline.layui-upload-choose{
			position: absolute;
			left: 0;
			bottom: 30%;
			font-size: 1.2vw;
			color: #000000;
		}
</style>

</html>
