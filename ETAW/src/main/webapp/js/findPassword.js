/**
 * 找回密码界面
 * @author 毕修平
 */
$(document).ready(function () {

   $("#submit_btn").click(function () {
       if($("#password").val().length<6){
           $("#password").focus();
           layer.msg("密码至少由六位字母数字符号组成",{icon: 0,time: 1500});
           return;
       }
       //向后台发送找回密码请求
       $.ajax({
           url:'/FindPassServlet',
           type:'post',
           dataType:"json",
           data:{"phone":$("#phone").val(),"password":$("#password").val()},
           success:function(data){
               if (data.isSuccess){
                   layer.alert("密码更改成功",{icon:1},function () {
                       window.location.href = "login.jsp";
                   });
               } else{
                   var message = data.message;
                   layer.msg(message, {icon: 2,time: 1500, anim: 6});
               }

           },
           error:function(err){
               console.log(err);
               layer.msg("网络异常，请重试", {icon: 2,time: 1500, anim: 6});
           }
       });
   });
});

function nextBtn() {
    if ($("#phone").val()!="" ){
        $("#inputVerification")[0].style.display="block";
        $("#sure_btn")[0].style.display="block";
    }
    verifyLogin();
}
