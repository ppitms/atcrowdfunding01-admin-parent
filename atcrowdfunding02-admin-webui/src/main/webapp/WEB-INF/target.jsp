<%--
  Created by IntelliJ IDEA.
  User: itman
  Date: 2022/3/14
  Time: 20:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>success</title>

    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}/">
</head>
<body>
 <h1>Success</h1>
${requestScope.adminList}
</body>
</html>
