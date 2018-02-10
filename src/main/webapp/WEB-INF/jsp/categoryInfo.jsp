<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/12
  Time: 9:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<style>
    .category {
        width: 200px;
        height: 200px;
        float: left;
    }

    img, a {
        display: block;
    }


    iframe {
        float: right;
        height: 70px;
    }

    #center{
        width: 70%;
        margin-left: 15%;
    }

    ul{
        margin: 0px;
        padding: 0px;
        list-style: none;
    }
    a{
        text-decoration: none;
    }
</style>
<body>
<jsp:include page="/head.jsp"></jsp:include>
<jsp:include page="/search.jsp"></jsp:include>
<div id="center">
    <div style="border-radius: 5px;width: 20%;border: 1px solid #DCDCDC;float: left;height: 1600px;margin-right: 70px;background-color: #F5F8FA;">
        <ul>
            <li style="border-radius: 5px;color: white;background-color: #3F95E5;height: 40px;line-height: 40px;">
                <span style="margin-left: 10%;">全部贴吧分类</span>
            </li>
            <li style="width: 100%;height: 40px;line-height: 40px;">
                <a style="color: #333333;margin-left: 15%;font-size: 16px;" href="/tieba/selectChildById?categoryId=${categoryOne.categoryid}&page=1&OnePageCount=20">
                    <span>${categoryOne.categoryname}</span>
                </a>
                <div style="font-size: 12px;margin-left: 10px;">
                    <c:forEach var="childcategory" items="${categoryOne.categoryChilds}" varStatus="status">
                        <c:choose>
                            <c:when test="${pCategoryId eq childcategory.categoryid}">
                                <a style="padding-right: 5%;color: white;background-color: #3F95E5;display: inline-block;" href="/tieba/selectChildById?categoryId=${childcategory.categoryid}&page=1&OnePageCount=20">
                                    <span style="margin-left: 10px;">${childcategory.categoryname}</span>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a style="padding-right: 5%;color: #444444;display: inline-block;" href="/tieba/selectChildById?categoryId=${childcategory.categoryid}&page=1&OnePageCount=20">
                                    <span style="margin-left: 10px;">${childcategory.categoryname}</span>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </li>
        </ul>
    </div>
    <div>
        <c:forEach var="tiebaInfo" items="${Tiebas}">
            <div class="category">
                <a href="/tieba/openTiebaById?tiebaId=${tiebaInfo.id}&&page=1&&type=-1">
                    <img src="/${tiebaInfo.name}.jpg"/><span>${tiebaInfo.name}</span>
                </a>
            </div>
        </c:forEach>
    </div>
    <div>
        <c:forEach var="index" begin="1" end="${pageMax}">
            <c:choose>
                <c:when test="${index!=page}">
                    <a href="/tieba/selectChildById?categoryId=${pCategoryId}&page=${index}&OnePageCount=20">${index}</a>
                </c:when>
                <c:otherwise>
                    <a>${index}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>

</div>
</body>
</html>
