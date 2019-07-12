<%--
登录权限管理
author:李沛昊
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    <%
    String account = "";
    account = (String)session.getAttribute("account");
    if(account==""||account==null){
        response.sendRedirect("./login.jsp");
    }
%>
</script>
