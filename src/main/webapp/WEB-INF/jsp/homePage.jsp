<%@ page import="com.henu.bean.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<head>
    <meta charset="UTF-8" />
    <title>Title</title>
</head>
<style>
    #center{
        width: 70%;
        margin-left: 15%;
        float: left;
    }
    #centerLeft{
        float: left;
        border: 1px solid #DCDCDC;
        background-color: #F6F7FB;
        padding: 10px 10px 0px 10px;
        width: 20%;
        margin-bottom: 30px;
    }
    #userinfo{
        float: left;
        margin-left: 10px;
        padding-bottom: 30px;
    }
    #userinfo img{
        width: 80px;
        height: 80px;
        margin: 3px 0px 0px 3px;
    }
    #category{
        float: left;
        margin-top: 10px;
        width: 100%;
        padding-bottom: 50px;
    }
    a{
        text-decoration: none;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }
    .userTieba{
        border-radius: 4px;
        border: 1px solid #DDDDDD;
        background-color: #FBFBFB;
        width: 40%;
        font-size: 12px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        height: 30px;
        line-height: 30px;
        padding-left: 3px;
    }
    .userTiebaLevel{
        float: right;
        width: 0px;
        height: 0px;
        border-right: 9px solid transparent;
        border-bottom: 15px solid #E98A00;
        border-left: 9px solid transparent;
        padding-top: 7px;
        margin-right: 3px;
        color: white;
    }
    .leftMenu{
        width: 100%;
        font-family: 微软雅黑;
        padding-bottom: 7px;
        padding-top: 7px;
    }
    #category a{
        color: #999999;
        font-family: 微软雅黑;
    }
    ul{
        list-style:none;
        margin-top: 0px;
        padding-left: 0px;
    }
</style>
<body>
<jsp:include page="/head.jsp"></jsp:include>
<jsp:include page="/search.jsp"></jsp:include>
<div id="center">
    <div id="centerLeft">
        <c:if test="${not empty user}">
            <div class="leftMenu">用户信息</div>
            <div id="userinfo" style="width: 100%;">
                <div style="border: 1px solid #CCCCCC;background-color: white;width: 86px;height: 86px;float: left;">
                    <a href="">
                        <c:choose>
                            <c:when  test="${empty user.userInfo.headimgurl}">
                                <img src="/default.jpg">
                            </c:when>
                            <c:when test = "${fn:contains(user.userInfo.headimgurl,'default')}">
                                <img src="/default.jpg">
                            </c:when>
                            <c:otherwise>
                                <img src="/${user.username}.jpg">
                            </c:otherwise>
                        </c:choose>
                    </a>
                </div>
                <a href="/tieba/openUserInfo?userPetname=${sessionScope.user.userInfo.userpetname}" style="color: #2D91D9;font-size: 16px;display: inline-block;width: 40%;margin-left: 5%;">
                        ${sessionScope.user.userInfo.userpetname}
                </a>
            </div>
        </c:if>
        <div>
            <div class="leftMenu">爱逛的吧</div>
            <div>
                <c:forEach var="usertieba" items="${sessionScope.user.userTiebas}" varStatus="status">
                    <c:if test="${status.index<6}">
                        <div class="userTieba">
                            <a style="color: #444444;" href="/tieba/openTiebaById?tiebaId=${usertieba.tiebaid}&&page=1&&type=-1">
                                <span>${usertieba.tiebaName}</span>
                                <div class="userTiebaLevel">
                                    <span style="display: block;font-family: 微软雅黑;font-size: 6px;position: relative;top: -6px;left: -3px;">${usertieba.level}</span>
                                </div></a>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <div id="category">
            <div class="leftMenu">贴吧分类</div>
            <ul>
                <c:forEach var="categoryOne" items="${CategoriesOne}" varStatus="status">
                    <c:if test="${status.index<10}">
                        <li style="margin-top: 20px;border-bottom: 1px solid #E6E8F1;">
                            <a href="/tieba/selectChildById?categoryId=${categoryOne.categoryid}&page=1&OnePageCount=20">
                                <span>${categoryOne.categoryname}</span>
                            </a>
                            <div style="font-size: 12px;margin-top: 20px;">
                                <c:forEach var="childcategory" items="${categoryOne.categoryChilds}" varStatus="status">
                                    <c:if test="${status.index<6}">
                                        <a style="display: inline-block;margin-left: 3%;margin-right: 3%;" href="/tieba/selectChildById?categoryId=${childcategory.categoryid}&page=1&OnePageCount=20">
                                            <span>${childcategory.categoryname}</span>
                                        </a>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>
            <button style="display: block;width: 90%;background-color: #FDFDFD;">
                <a href="/tieba/selectAllCate">
                    <span>查看全部</span>
                </a>
            </button>
        </div>
    </div>
    <div id="centerRight">
        <div style="width: 100%;font-size: 28px;margin-top: 10px;">推荐贴吧</div>
        <c:forEach var="tiebaInfo" items="${Tiebas}">
            <div style="float: left">
                <a href="/tieba/openTiebaById?tiebaId=${tiebaInfo.id}&&page=1&&type=-1">
                    <img src="/${tiebaInfo.name}.jpg"/><span>${tiebaInfo.name}</span>
                </a>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>