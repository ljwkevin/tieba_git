<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/25
  Time: 15:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<style>
    ul{
        margin: 0px;
        padding: 0px;
        list-style:none;
    }
    a{
        text-decoration: none;
    }
    html,body{
        height: 100%;
    }
</style>
<body style="margin-top: 0px;">
    <jsp:include page="/head.jsp"></jsp:include>
    <jsp:include page="/search.jsp"></jsp:include>
    <div style="width: 80%;margin-left: 15%;height: 100%;">
        <div style="border-radius: 5px;width: 20%;border: 1px solid #DCDCDC;float: left;height: 2700px;margin-top: 70px;margin-right: 70px;background-color: #F5F8FA;">
            <ul>
                <li style="border-radius: 5px;color: white;background-color: #3F95E5;height: 40px;line-height: 40px;">
                    <span style="margin-left: 10%;">全部类别分类</span>
                </li>
                <c:forEach var="categoryOne" items="${CategoriesOne}" varStatus="status">
                    <li style="width: 100%;border-bottom: 1px dashed #B1B4B6;height: 40px;line-height: 40px;">
                        <a style="color: #333333;margin-left: 15%;" href="/tieba/selectChildById?categoryId=${categoryOne.categoryid}&page=1&OnePageCount=20">
                            <span>${categoryOne.categoryname}</span>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <ul>
            <c:forEach var="categoryOne" items="${CategoriesOne}" varStatus="status">
                <li style="margin-top: 70px;width: 35%;float: left;height: 100px;padle">
                    <a style="color: #01417A;" href="/tieba/selectChildById?categoryId=${categoryOne.categoryid}&page=1&OnePageCount=20">
                        <span>${categoryOne.categoryname}</span>
                    </a>
                    <div style="font-size: 12px;margin-top: 20px;">
                        <c:forEach var="childcategory" items="${categoryOne.categoryChilds}" varStatus="status">
                            <a style="padding-right: 5%;color: #444444;display: inline-block;border-right: 1px solid #E3E3E3;" href="/tieba/selectChildById?categoryId=${childcategory.categoryid}&page=1&OnePageCount=20">
                                <span>${childcategory.categoryname}</span>
                            </a>
                        </c:forEach>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</body>
</html>
