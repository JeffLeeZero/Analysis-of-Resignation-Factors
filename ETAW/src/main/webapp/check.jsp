<%--
  Created by IntelliJ IDEA.
  User: 11209
  Date: 2019/7/10
  Time: 11:22
  To change this template use File | Settings | File Templates.
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
