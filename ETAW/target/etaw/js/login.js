
$(document).ready(function () {
    //注册按钮跳转到注册页面
    $("#register_btn").click(function () {
       window.location.href="register.jsp" ;
    });
    //登陆按钮触发事件
    $("#login_btn").click(function () {

        if ($("#account").val()=="" ) {
            $("#account").focus();
            layer.msg("请输入手机号",{icon: 0,time: 1500});
            return;
        }

        if ($("#password").val()=="") {
            $("#password").focus();
            layer.msg("请输入密码",{icon: 0,time: 1500});
            return;
        }
        $.ajax({
            url:'/LoginServlet',
            type:'post',
            dataType:"json",
            data:{"account":$("#account").val(),"password":$("#password").val()},//数据为登录名和登录密码

            beforeSend:function(){
                $('#login_btn').val("登录中");
            },

            success:function(data){//处理返回的信息，true则跳转，false则提示密码错误
                if (data.isSuccess){
                    window.localStorage.id = $("#account").val();
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
var verifiedCode; //验证码
var interValObj; //timer变量，控制时间
var count = 60;  //间隔时间
var curCount;    //当前剩余秒数


function verifyLogin() {
    if ($("#phone").val()=="" ) {
        $("#phone").focus();
        layer.msg("请输入手机号",{icon: 0,time: 1500});
        return;
    }

    $.ajax({
        url:'/SmsLoginServlet',
        type:'post',
        dataType:"json",
        data:{"phone":$("#phone").val(),"alert_type":$("#alert_type").val()},
        success:function(data){
            getCheckCodeTime();
            verifiedCode = data.message;
            alert(verifiedCode);
            // layer.msg(message, {icon: 2,time: 1500});
        },
        error:function(err){
            console.log(err);
            layer.msg("网络异常，请重试", {icon: 2,time: 1500, anim: 6});
        }
    });

}
function getCheckCodeTime() {
    curCount = count;
    $("#veri_btn").attr("disabled","disabled");
    $("#veri_btn").val(curCount+"后可重新获取");
    interValObj = setInterval(setTime,1000);
}

function setTime() {
    if (curCount <= 0) {
        $("#veri_btn").addClass("focus").css("pointer-events","");
        clearInterval(interValObj);//停止计时器
        $("#veri_btn").removeAttr("disabled");//启用按钮
        $("#veri_btn").val("重新发送验证码");
    }
    else {
        $("#veri_btn").addClass("focus").css("pointer-events","none");
        --curCount;
        $("#veri_btn").val(curCount+"后可重新获取");
    }
}

function loginWithVerification() {
    if ($("#phone").val()=="" ) {
        $("#phone").focus();
        layer.msg("请输入手机号",{icon: 0,time: 1500});
        return;
    }

    if ($("#verification").val()=="") {
        $("#verification").focus();
        layer.msg("请输入验证码",{icon: 0,time: 1500});
        return;
    }
    if($("#verification").val()==verifiedCode){
        window.location.href = "index.jsp";
    }else{
        layer.msg("验证码错误",{icon: 5,time: 1500});
    }
}

