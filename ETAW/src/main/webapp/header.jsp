<%--
  Created by IntelliJ IDEA.
  User: 毕修平
  Date: 2019/6/25
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
<script src="js/share.js"></script>
<div class="layui-header">
    <a class="layui-logo" href="inputPage.jsp"><i class="layui-icon layui-icon-home"></i>员工离职因素分析</a>
    <!-- 头部区域（可配合layui已有的水平导航） -->

    <form id="form" action="" method="post">
        <input type="hidden" name="_method" value="">
    </form>

    <ul class="layui-nav layui-layout-right">
        <li class="layui-nav-item"><a href="login.jsp">退出</a></li>
    </ul>
</div>

<div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
        <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
        <ul class="layui-nav layui-nav-tree">
            <li class="layui-nav-item layui-nav-itemed">
                <a class="">员工数据</a>
                <dl class="layui-nav-child">
                    <dd><a id="team1" href="inputPage.jsp" onclick="queryTeam()"><i class="layui-icon layui-icon-group"></i>
                        导入数据</a></dd>
                    <dd><a href="history.jsp"><i class="layui-icon layui-icon-friends"></i>&nbsp&nbsp&nbsp历史记录</a></dd>
                </dl>
            </li>

            <li class="layui-nav-item layui-nav-itemed">
                <a class="" href="javascript:;">整体数据</a>
                <dl class="layui-nav-child">
                    <dd><a id="team2" href="analysisChart.jsp" onclick="queryTeam()"><i
                            class="layui-icon layui-icon-group"></i> 统计分析</a></dd>
                    <%--<dd><a href="analysePart.jsp" onclick="queryAthlete()"><i class="layui-icon layui-icon-friends"></i>&nbsp&nbsp&nbsp单因素分析</a>--%>
                    <%--</dd>--%>
                </dl>
            </li>
            <li class="layui-nav-item layui-nav-itemed">
                <a class="" href="javascript:;">离职预测</a>
                <dl class="layui-nav-child">
                    <dd><a href="insertWorker.jsp"> 员工信息</a></dd>
                    <%--<dd><a href="analyseWorker.jsp">结果分析</a></dd>--%>
                </dl>
            </li>
            <li class="layui-nav-item layui-nav-itemed">
                <a class="" href="passage.jsp">相关文章</a>
            </li>
        </ul>
    </div>
</div>

</script>
