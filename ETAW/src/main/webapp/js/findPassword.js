$(document).ready(function () {
   // $("#veri_btn").click(function () {
   //     //　 发送验证码，显示验证码输入框
   //
   // });
   $("#submit_btn").click(function () {
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

function submitBtn() {

}