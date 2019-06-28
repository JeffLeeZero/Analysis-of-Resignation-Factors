window.onload = function () {

};
function layerAlert() {
   layer.alert("qing", {icon: 2});
    layui.use('layer', function(){
        var message = "sdf ";
        var layer = layui.layer;
        layer.msg(message, {icon: 2,time: 1500, anim: 6});
    });
    var aleryType = document.getElementById("alert_type");
    switch (aleryType.value()) {
        case "0":
            layer.alert('用户名不存在', {icon: 3});
            window.location.href="login.jsp";
            break;
        case "1":
            window.location.href="index.jsp";
            break;
        case "2":
            layer.alert('密码错误', {icon: 5});
            window.location.href="login.jsp";
            break;
        default:
            break;
    }
}