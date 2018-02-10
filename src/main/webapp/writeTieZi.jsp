<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/12
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/tieba/writeTieZi">
    题目：<input type="text" name="title" value="" />
    贴吧id:<input type="text" name="tiebaid" value="<%=request.getParameter("tieBaId")%>" />
    正文：<textarea name="content"></textarea>
    昵称:<input type="text" name="userpetname" value="${userInfo.userpetname}" />
    <button type="submit">提交</button>
</form>
</body>
</html>
