$(document).ready(function () {
   // $("#veri_btn").click(function () {
   //     //　 发送验证码，显示验证码输入框
   //
   // });
   // $("#sure_btn").click(function () {
   //
   // });
});

function nextBtn() {
    $("#inputVerification")[0].style.display="block";
    $("#sure_btn")[0].style.display="block";
}

function sureBtn() {
    $("#inputVerification")[0].style.display="none";
    $("#sure_btn")[0].style.display="none";
    $("#inputPass")[0].style.display="block";
    $("#submit_btn")[0].style.display="block";
}