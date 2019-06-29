window.onload=function (ev) {  };
$(document).ready(function () {
    $("#login_btn").click(function () {

        if ($("#account").val()=="" ) {
            $("#account").focus();
            layer.msg("请输入用户名",{icon: 0,time: 1500});
            return;
        }

        if ($("#password").val()=="") {
            $("#password").focus();
            layer.msg("请输入密码",{icon: 0,time: 1500});
            return;
        }
        $.ajax({
<<<<<<< HEAD
            url:'/LoginServlet',
=======
            url:'<%=request.getContextPath()%>/LoginServlet',
>>>>>>> 61793572e62f7d7aa7a96a5f550a11af3f9cd88b
            type:'post',
            dataType:"json",
            data:{"account":$("#account").val(),"password":$("#password").val()},//数据为登录名和登录密码

            beforeSend:function(){
                $('#login_btn').val("登录中");
            },

            success:function(data){//处理返回的信息，true则跳转，false则提示密码错误
                if (data.isSuccess){

                    window.location.href = "index.jsp";
                } else {
                    var message = data.message;
                    layer.msg(message, {icon: 2,time: 1500, anim: 6});
                    $('#login_btn').val("登录");
                    console.log(data);

                }
            },
            error:function(data){
                console.log(data);
                layer.msg("网络异常，请重试", {icon: 2,time: 1500, anim: 6});
                $('#login_btn').val("登录");
            }
        });
    });

    $("#login_phone").click(function () {
        $("#veriLogin")[0].style.display='block';
        $("#passLogin")[0].style.display='none';
    });
    $("#login_pass").click(function () {
        $("#veriLogin")[0].style.display='none';
        $("#passLogin")[0].style.display='block';
    });
});

