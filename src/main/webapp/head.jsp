<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/14
  Time: 11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<style>
    #websocket1{
        z-index: 9999;
        position: relative;
        width: 100%;
        height: 100%;
    }
    #zhezhaoceng{
        z-index: 999;
        position: fixed;
        width: 100%;
        height: 100%;
        background-color: white;
        opacity:0.8;
    }
    #websocket1 iframe{
        width: 100%;
        height: 700px;
        opacity:1;
    }
    a{
        font-size: 12px;
        color: #333333;
        text-decoration: solid;
    }
</style>
<head>
    <title>Title</title>
</head>
<body style="margin-top: 0px;">
    <div style="width: 100%;float: left;border-bottom: 1px solid #F0F0F0;height: 40px;">
        <a href="/tieba/toHomePage" style="float: left;line-height: 40px;font-family: 微软雅黑;font-weight: bold;font-size: 12px;"><u>首页</u></a>
        <c:choose>
            <c:when test="${empty user}">
                <div style="float: right;">
                    <a href="/login.html">登录</a>
                </div>
            </c:when>
            <c:otherwise>
            <div style="float: right">
                <a href="/tieba/openWebSocket" target="_blank">
                    <button style="float: right;font-size: 13px;margin-left: 20px;width: 100px;height: 20px;margin-top: 10px;" id="websocket2">
                    打开聊天框
                </button>
                </a>
                <iframe id="iframe2" src="/tieba/openWebSocket2" style="width: 220px;height: 35px;float: right;"></iframe>
                <a href="/tieba/openUserInfo?userPetname=${userInfo.userpetname}" style="display: block;float: right;line-height: 35px;font-size: 13px;margin-right: 10px;">
                    <u>${userInfo.userpetname}</u>
                </a>
                <a href="" style="display: block;float: right;line-height: 35px;font-size: 13px;margin-right: 10px;">
                    <u>注销</u>
                </a>
            </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
<script type="text/javascript" src="/js/jquery-3.2.1.min.js"></script>

</html>
