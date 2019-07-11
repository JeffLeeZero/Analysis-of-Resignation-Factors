<%--
  Created by IntelliJ IDEA.
  User: 11209
  Date: 2019/7/11
  Time: 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
session.invalidate()
<script>
    <%
    session.invalidate();
    response.sendRedirect("./login.jsp");
    %>
</script>

