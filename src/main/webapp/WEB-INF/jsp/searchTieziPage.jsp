<%--
  Created by IntelliJ IDEA.
  User: Shinelon
  Date: 2018/2/8
  Time: 12:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
    #tieZidiv{
        border: 1px solid #E4E6EB;
    }
    .zhuti{
        display: block;
        padding-left: 150px;
        color: #2D64B3;
    }
    .tiezi{
        height: 40px;
        margin-top: 10px;
    }
</style>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="/head.jsp"></jsp:include>
<jsp:include page="/search.jsp"></jsp:include>
<div style="font-family: 微软雅黑;font-size: 22px;padding-bottom: 50px;">以下为"${key}"的相关搜索内容:</div>
<div id="tieZidiv">
    <c:forEach var="tieZi" items="${tieZiList}">
        <div class="tiezi">
            <div class="zhuti">
                <a style="float: left;display: block;width: 500px;" href="/tieba/openTieZi?TieZiId=${tieZi.id}&page=1">
                    <span>${tieZi.title}</span>
                </a>
                <span style="display: block;float: left;margin-left: 40px;color: #999999;font-size: 12px;font-weight: bold;">
                        ${tieZi.userpetname}
                </span>
            </div>
        </div>
        <hr>
    </c:forEach>
</div>
<div style="background-color: #F5F7FA;color: #2D64B3;font-weight: bold;
        font-size: 10px;height: 40px;border: 1px solid #DBDCE0;line-height: 40px;">
    <a style="margin-left: 20px;" href="/tieba/selectBySolrTitleIndex?key=${key}&page=1">首页</a>
    <c:forEach var="index" begin="1" end="${pageMax}">
        <c:choose>
            <c:when test="${index!=page}">
                <a href="/tieba/selectBySolrTitleIndex?key=${key}&page=${index}">${index}</a>
            </c:when>
            <c:otherwise>
                <span style="color: black">${index}</span>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <a href="/tieba/selectBySolrTitleIndex?key=${key}&page=${pageMax}">尾页</a>
</div>
</body>
</html>
