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

                            <input type="file" name="uploadFile" />

                            <input type="hidden" name="account" id="formAccount"/>

                            <button class="layui-btn layui-btn-normal" lay-submit="" id="upup" lay-filter="upup">提交</button>

                        </form>


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
    document.getElementById("formAccount").value = window.localStorage.id;
    // $.ajax({
    // 	type: "post",
    // 	url: "http://localhost:8080/UploadServlet",
    // 	dataType: "json",
    // 	data: new FormData(document.getElementById("upload")),
    // 	success: function (res) {
    // 		console.log(res);
    // 	}
    // });

    //JavaScript代码区域
    layui.use('element', function() {
        var element = layui.element;
    });

    layui.use('upload', function() {
        var $ = layui.jquery,
            upload = layui.upload;

        upload.render({
            elem: '#supplement_file'
            , url: 'http://localhost:8080/UploadServlet'
            , accept: 'file'
            , auto: false
            // , bindAction: '#upfile' //关闭的上传按钮   html中此id所在元素也被注释
            ,multiple: true
            , done: function (res) {
                alert("上传成功");
            }
        });

        function fsubmit(fd) {
            $.ajax({
                url: "http://localhost:8080/UploadServlet",
                type: "POST",
                data: fd,
                async : false,
                contentType: false,   //jax 中 contentType 设置为 false 是为了避免 JQuery 对其操作，从而失去分界符，而使服务器不能正常解析文件
                processData: false,   //当设置为true的时候,jquery ajax 提交的时候不会序列化 data，而是直接使用data
                error : function(request) {
                    parent.layer.alert("网络超时");
                },
                success: function (data) {
                    alert("上传成功！");
                }
            });
            return false;
        }

        $("#upup").on("click",function () {
            var formSatellite = document.getElementById("up_form");//获取所要提交form的id
            var formAccount = document.getElementById("formAccount").valueOf();
            var fs1 = new FormData(formSatellite);  //用所要提交form做参数建立一个formdata对象
            //var fs2 = new FormData(formAccount);
            console.log(fs1);
            fsubmit(fs1);//调用函数
            //fsubmit(fs2);
        })

    });
</script>

</body>

</html>
