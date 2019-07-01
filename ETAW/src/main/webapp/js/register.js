$(document).ready(function () {
    $("#veri_btn").click(function registerGetVerification() {  //注册界面获取验证码
        if ($("#phone").val()=="" ) {
            $("#phone").focus();
            layer.msg("请输入手机号",{icon: 0,time: 1500});
            return;
        }

        if ($("#password").val()=="") {
            $("#password").focus();
            layer.msg("请输入密码",{icon: 0,time: 1500});
            return;
        }
        verifyLogin();
    });
    $("#registerBtn").click(function registerBtn() {
        if ($("#phone").val()=="" ) {
            $("#phone").focus();
            layer.msg("请输入手机号",{icon: 0,time: 1500});
            return;
        }
        if ($("#password").val()=="") {
            $("#password").focus();
            layer.msg("请输入密码",{icon: 0,time: 1500});
            return;
        }
        if ($("#verification").val()=="") {
            $("#verification").focus();
            layer.msg("请输入验证码",{icon: 0,time: 1500});
            return;
        }
        if ($("#verification").val()==verifiedCode){
            $.ajax({
                url:'/RegisterServlet',
                type:'post',
                dataType:"json",
                data:{"phone":$("#phone").val(),"password":$("#password").val()},//数据为登录名和登录密码

                beforeSend:function(){
                    $('#registerBtn').val("注册中");
                },

                success:function(data){//处理返回的信息，true则跳转，false则提示密码错误
                    if (data.isSuccess){
                        layer.alert("注册成功",{icon:1},function () {
                            window.location.href = "login.jsp";
                        });

                    } else {
                        var message = data.message;
                        layer.msg(message, {icon: 2,time: 1500, anim: 6});
                        $('#registerBtn').val("注册");
                        console.log(data);

                    }
                },
                error:function(data){
                    console.log(data);
                    layer.msg("网络异常，请重试", {icon: 2,time: 1500, anim: 6});
                    $('#registerBtn').val("注册");
                }
            });
        }

    });
});


