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
    <title>Title</title>
</head>
<body>
<div class="splash">
    <div class="counter">
        <div><h1>员工离职分析</h1></div>
    </div>
    <p class="tagline">世界那么大，我想···</p>
    <p class="calories">工作那么忙，你别想···</p></div>
<style>
    html {
        background-color: #fff;
        color: #32353a;
        font-family: Tahoma, Verdana, Segoe, sans-serif;
        line-height: 1.5;
    }

    body {
        margin: 0;
    }

    h1 {
        font-family: 'American Typewriter', 'Rockwell Extra Bold', 'Book Antiqua', Georgia, serif;
        font-size: 4em;
    }

    .counter {
        margin-bottom: 1rem;
        border-bottom: .1875em dashed #d2d6dd;
    }

    .tagline {
        font-size: 1.125em;
        margin: 2rem 1.5rem;
    }

    .calories {
        color: #8b919b;
        font-size: .875em;
    }

    .splash {
        position: relative;
        height: 100vh;
        max-width: 30em;
        text-align: center;
    }

    .splash {
        box-sizing: border-box;
        max-width: 30em;
        margin: 0 auto;
        padding: 1.5em;
    }

    .counter {
        position: relative;
        padding-top: 30vh;
    }

    .counter h1 {
        position: relative;
        text-align: center;
        margin: 0;
        line-height: .625;
        z-index: 2;
        transform-origin: bottom center;
    }

    .dish {
        color: #e89c2b;
        position: absolute;
        width: 1em;
        height: 1em;
        margin-top: -.75em;
        margin-left: -.5em;
        font-size: 2rem;
        top: 0;
        transform: translateY(-3em);
        transition-duration: .75s;
        transition-timing-function: ease-in;
        transition-property: transform;
        z-index: 1;
    }

    .dish.drop {
        transform: translateY(30vh);
    }

    .nommer {
        position: absolute;
        bottom: 2em;
        margin-left: -1em;
        color: rgba(0, 0, 0, .5);
        font-size: 1.5rem;
        transform-origin: bottom center;
    }</style>
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
