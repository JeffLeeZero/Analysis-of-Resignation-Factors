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

        <div class="layui-tab">
            <ul class="layui-tab-title">
                <li class="layui-this" style="font-size:18px">单个员工数据</li>
                <li style="font-size:18px">多个员工数据</li>
            </ul>
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show">

                    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px">
                        <legend>填写表单</legend>
                    </fieldset>

                    <form class="layui-form" method="post" action="<%=request.getContextPath()%>/InsertWorkerServlet"  enctype="multipart/form-data">

                        <input type="hidden" name="Account" id="formAccount"/>

                        <div class="layui-form-item" style="margin-left: 200px">
                            <label class="layui-form-label" style="width: 200px">员工编号</label>
                            <div class="layui-input-block" style="margin-right: 300px">
                                <input type="text" name="Number" lay-verify="required" autocomplete="off" placeholder="请输入员工编号" class="layui-input" style="width: 200px">
                            </div>
                        </div>

                        <div class="layui-form-item" style="margin-left: 200px">
                            <label class="layui-form-label" style="width: 200px">该员工对公司满意度</label>
                            <div class="layui-input-block" style="margin-right: 300px">
                                <input type="text" name="SatisfactionLevel" lay-verify="required" autocomplete="off" placeholder="请输入0到1之间的数据" class="layui-input" style="width: 200px">
                            </div>
                        </div>

                        <div class="layui-form-item" style="margin-left: 200px">
                            <label class="layui-form-label" style="width: 200px">该员工上次考核评价</label>
                            <div class="layui-input-block" style="margin-right: 300px">
                                <input type="text" name="LastEvaluation" lay-verify="required" autocomplete="off" placeholder="请输入0到1之间的数据" class="layui-input" style="width: 200px">
                            </div>
                        </div>

                        <div class="layui-form-item" style="margin-left: 200px">
                            <label class="layui-form-label" style="width: 200px">该员工平均月工作时长</label>
                            <div class="layui-input-block" style="margin-right: 300px">
                                <input type="text" name="AverageMonthly" lay-verify="required" autocomplete="off" placeholder="请输入整数 以小时计" class="layui-input" style="width: 200px">
                            </div>
                        </div>

                        <div class="layui-form-item" style="margin-left: 200px">
                            <label class="layui-form-label" style="width: 200px">该员工参与项目数量</label>
                            <div class="layui-input-block" style="margin-right: 300px">
                                <input type="text" name="NumberProject" lay-verify="required" autocomplete="off" placeholder="请输入整数 视实际情况而定" class="layui-input" style="width: 200px">
                            </div>
                        </div>



                        <div class="layui-form-item" style="margin-left: 200px">
                            <label class="layui-form-label" style="width: 200px">该员工工龄</label>
                            <div class="layui-input-block" style="margin-right: 300px">
                                <input type="text" name="TimeSpendCompany" lay-verify="required" autocomplete="off" placeholder="请输入整数 视实际情况而定" class="layui-input" style="width: 200px">
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
                                        <option value="sales">销售部</option>
                                        <option value="accounting">会计部</option>
                                        <option value="hr">人力资源部</option>
                                        <option value="technical">技术部</option>
                                        <option value="support">供应部</option>
                                        <option value="management">管理部</option>
                                        <option value="IT">信息部</option>
                                        <option value="product_mng">产品部</option>
                                        <option value="marketing">市场部</option>
                                        <option value="RandD">研发部</option>
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
                                        <option value="high">高</option>
                                        <option value="medium">中</option>
                                        <option value="low">低</option>
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

                <div class="layui-tab-item">

                    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px">
                        <legend>选择文件</legend>
                    </fieldset>

                    <div class="form">
                        <form class="layui-form" action="#" enctype="multipart/form-data" id="up_form">

                            <input/>
                            <button type="button" class="layui-btn layui-btn-normal" name="uploadFile" id="test1">选择文件</button>
                            <input type="hidden" class="layui-input" name="account" id="account"/>
                            <button type="button" class="layui-btn layui-btn-normal" lay-submit="" id="upup" lay-filter="upup">提交</button>

                        </form>
                    </div>

                </div>

            </div>
        </div>

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
    document.getElementById("formAccount").value = window.localStorage.id;
    document.getElementById("account").value = window.localStorage.id;

    layui.use('element', function(){
        var $ = layui.jquery
            ,element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块

        //触发事件
        var active = {
            tabAdd: function(){
                //新增一个Tab项
                element.tabAdd('demo', {
                    title: '新选项'+ (Math.random()*1000|0) //用于演示
                    ,content: '内容'+ (Math.random()*1000|0)
                    ,id: new Date().getTime() //实际使用一般是规定好的id，这里以时间戳模拟下
                })
            }
        };

        $('.site-demo-active').on('click', function(){
            var othis = $(this), type = othis.data('type');
            active[type] ? active[type].call(this, othis) : '';
        });

        //Hash地址的定位
        var layid = location.hash.replace(/^#test=/, '');
        element.tabChange('test', layid);

        element.on('tab(test)', function(elem){
            location.hash = 'test='+ $(this).attr('lay-id');
        });

    });

    layui.use('upload', function() {
        var $ = layui.jquery,
            upload = layui.upload;

        upload.render({
            elem: '#test1'
            , url: 'http://localhost:8080/InsertMultiWorkerServlet'
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
                url: "http://localhost:8080/InsertMultiWorkerServlet",
                type: "POST",
                data: fd,
                contentType: false,   //Ajax 中 contentType 设置为 false 是为了避免 JQuery 对其操作，从而失去分界符，而使服务器不能正常解析文件
                processData: false,   //当设置为true的时候,jquery ajax 提交的时候不会序列化 data，而是直接使用data
                error : function(XMLHttpRequest, textStatus) {
                    //alert("网络超时")
                    //console.log(textStatus);
                    window.location.href = "/analyseMultiWorker.jsp";
                },
                success: function (data) {
                    alert("上传成功");
                    window.location.href="/analyseMultiWorker.jsp";
                }
            });
            return false;
        }

        $("#upup").on("click",function () {
            var formSatellite = document.getElementById("up_form");//获取所要提交form的id
            var fs1 = new FormData(formSatellite);  //用所要提交form做参数建立一个formdata对象
            fsubmit(fs1);//调用函数
        })
    });

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

<%--<style>--%>
    <%--#up_form{--%>
        <%--background-color: #FFFFFF;--%>
        <%--position: relative;--%>
    <%--}--%>

    <%--.form{--%>
        <%--position: absolute;--%>
        <%--top: 60%;--%>
        <%--width: 100%;--%>
        <%--height: 7%;--%>
        <%--text-align: left;--%>
    <%--}--%>
    <%--form{--%>
        <%--position: absolute;--%>
        <%--width: 57%;--%>
        <%--height: 100%;--%>
        <%--top: 60%;--%>
        <%--left: 21%;--%>
        <%--opacity: 100%;--%>
    <%--}--%>
    <%--#upup{--%>
        <%--background-color: #558AF5;--%>
        <%--width: 14%;--%>
        <%--height: 90%;--%>
        <%--right: 0;--%>
        <%--position: absolute;--%>
    <%--}--%>
    <%--#test1{--%>
        <%--background-color: #3f51b5;--%>
        <%--width: 18%;--%>
        <%--height: 90%;--%>
        <%--right: 16%;--%>
        <%--position: absolute;--%>
    <%--}--%>
    <%--form > input{--%>
        <%--width: 64%;--%>
        <%--height: 90%;--%>
        <%--right: 36%;--%>
        <%--position: absolute;--%>
    <%--}--%>
<%--</style>--%>
</html>
