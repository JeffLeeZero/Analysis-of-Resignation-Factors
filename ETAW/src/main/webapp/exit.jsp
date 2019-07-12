
<%--
退出登录
author:李沛昊
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
session.invalidate()
<script>
    <%
    session.invalidate();
    response.sendRedirect("./login.jsp");
    %>
</script>

