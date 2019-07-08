<%--
  Created by IntelliJ IDEA.
  User: MQD
  Date: 2019/7/5
  Time: 20:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <title>员工离职分析网站</title>

    <script src="js/findPassword.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="css/frontPage.css">
</head>
<body>
<div class="splash">
    <div class="counter">
        <div><h1>员工离职分析</h1></div>
    </div>
    <div class="tagline_div" >
        <p class="tagline" onmouseover="mouseOver(1)" onmouseout="mouseOut(1)">世界那么大，我想···</p>
        <img id="img_1" src="img/离职.png" >
    </div>
    <div class="calories_div" >
        <p class="calories" onmouseover="mouseOver(2)" onmouseout="mouseOut(2)">工作那么忙，你别想···</p>
        <img id="img_2" src="img/不敢离职.jpg">
    </div>
    <script>
        function mouseOver(flag) {
            if (flag==1){
                var img=document.getElementById("img_1");
                img.style.display='block';
            }else {
                var img=document.getElementById("img_2");
                img.style.display='block';
            }
        }
        function mouseOut(flag) {
            if (flag==1){
                var img=document.getElementById("img_1");
                img.style.display='none';
            }else {
                var img=document.getElementById("img_2");
                img.style.display='none';
            }
        }
    </script>

</div>
<div class="info" id="info_1">
    <p class="tagline">探求员工离职原因</p>
</div>
<div class="info" id="info_2">
    <p class="tagline">分析员工离职概率</p>
</div>
<div class="question" id="que_1">
    <p class="tagline">为何员工突然辞职</p>
</div>
<div class="question" id="que_2">
    <p class="tagline">哪个部门不堪重负</p>
</div>
<script language='javascript' type='text/javascript'>
    $(function () {
        setTime(function () {
           $("#info_2").css('display','block');
        },500);
    });
</script>
<div class="aside-nav bounceInUp animated" id="aside-nav">
    <a href="" class="aside-menu" title="按住拖动">菜单</a>

    <a href="javascript:void(0)"  class="menu-item menu-first">点</a>
    <a href="javascript:void(0)"  class="menu-item menu-second">击</a>
    <a href="javascript:void(0)"  class="menu-item menu-third">登</a>
    <a href="javascript:void(0)"  class="menu-item menu-line menu-fourth">录</a>
</div>
<script>
    (function () {
        var counter = document.querySelector('.counter');
        var diner = document.querySelector('h1');
        var menu = ['*', '@', '%', '￥', '#', '^_^'];
        var noms = ['辞职', '工资低!', '996!!', '家庭难顾', '不升职!'];
        var finished = 0;

        function jelly(e) {
            diner.removeEventListener('fit', jelly);
            diner.animate([{transform: 'scale(' + 1 / e.detail.scaleFactor + ')'}, {transform: 'scale(1.1)'}, {transform: 'scale(.9)'}, {transform: 'scale(1.05)'}, {transform: 'scale(.98)'}, {transform: 'scale(1)'}], {duration: 500});
        }

        // eat animation
        function nom(index) {
            var nommer = document.getElementById('nommer-' + index);
            var rotation = -20 + (Math.random() * 40);
            var scale = .75 + (Math.random() * .5);
            nommer.animate([{
                opacity: 0,
                transform: 'scale(' + .25 * scale + ') rotateZ(' + rotation + 'deg) translateY(0) '
            }, {
                opacity: 1,
                transform: 'scale(' + scale + ') rotateZ(' + rotation + 'deg) translateY(-.5em) '
            }], {duration: 250});
            diner.animate([{transform: 'scaleY(1)'}, {transform: 'scaleY(' + (.7 + (Math.random() * .2)) + ')'}, {transform: 'scaleY(1)'}], {duration: 100});
        }

        if ('animate' in diner) {
            diner.addEventListener('fit', jelly);
        }
        var dishes = menu.concat(menu).sort(function () {
            return .5 - Math.random();
        }).concat(['??']) // unfortunately there's no fly emoji
            .map(function (menuItem, i) {
                var offset = 33.33 + (Math.random() * 33.33);
                var dish = document.createElement('div');
                dish.textContent = menuItem;
                dish.setAttribute('aria-hidden', 'true');
                dish.setAttribute('data-nommer', i);
                dish.className = 'dish';
                dish.style.cssText = 'left:' + offset + '%; transition-delay: ' + ((i * 200) + Math.random() * 100) + 'ms';
                var nommer = document.createElement('div');
                nommer.id = 'nommer-' + i;
                nommer.textContent = noms[Math.floor(Math.random() * noms.length)];
                nommer.setAttribute('aria-hidden', 'true');
                nommer.className = 'nommer';
                nommer.style.cssText = 'left:' + offset + '%; opacity:0;';
                counter.appendChild(dish);
                counter.appendChild(nommer);
                return dish;
            });
        setTimeout(function () {
            requestAnimationFrame(function () {
                dishes.forEach(function (dish) {
                    dish.classList.add('drop');
                    dish.addEventListener('transitionend', burb);
                })
            });
        }, 500);

        function burb(e) {
            e.target.removeEventListener('transitionend', burb);
            e.target.style.opacity = 0;
            finished++;
            if (finished === dishes.length) {
                full();
            } else if ('animate' in diner) {
                nom(e.target.getAttribute('data-nommer'));
            }
        }

        function full() {
            fitty(diner, {minSize: 64});
        }
    }());</script>
</body>
</html>
