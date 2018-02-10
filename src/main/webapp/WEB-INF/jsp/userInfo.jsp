<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/1/4
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
    <title>Title</title>
</head>
<base target="_blank">
<style>
    #back{
        position: relative;
        background-image: url("/bg_v1_1001.jpg");
        background-repeat: no-repeat;
        background-position:top;
        width: 100%;
        height: 2000px;
    }
    #center{
        border: 1px solid #8FC0D3;
        width: 70%;
        margin-left: 15%;
        background-color: #FFFFFF;
        position: relative;
        top: 300px;
        height: 700px;
    }
    #userImg{
        width: 157px;
        height: 157px;
        border: 1px solid #E0E2E5;
        background-color: #FFFFFF;
        border-radius: 2px;
        position: relative;
        top: -60px;
        left: 30px;
        float: left;
    }
    #userImg img{
        width: 150px;
        height: 150px;
        margin: 3px 0 0 3px;
    }
    #userinfo{
        float: left;
        margin-left: 200px;
        position: relative;
        top: 10px;
    }
    .userTiebaLevel{
        float: right;
        width: 0px;
        height: 0px;
        border-right: 12px solid transparent;
        border-bottom: 20px solid #E98A00;
        border-left: 12px solid transparent;
        padding-top: 7px;
        margin-right: 3px;
        color: white;
    }
    #loveTieba{
        float: left;
        margin-left: 20px;
        width: 100%;
    }
    #dongtai{
        margin-top: 30px;
        float: left;
        margin-left: 20px;
        width: 100%;
    }
    a{
        text-decoration: none;
    }
</style>
<body>
<jsp:include page="/head.jsp"></jsp:include>
<jsp:include page="/search.jsp"></jsp:include>
<div id="back">
    <div id="center">
        <div style="width: 100%;border-bottom: 2px solid #D7DBDE;height: 100px;background-color: #F5F7FA;">
            <div id="userImg">
                <c:choose>
                    <c:when  test="${empty oneUserInfo.userInfo.headimgurl}">
                        <img src="/default.jpg">
                    </c:when>
                    <c:when test = "${fn:contains(oneUserInfo.userInfo.headimgurl,'default')}">
                        <img src="/default.jpg">
                    </c:when>
                    <c:otherwise>
                        <img src="/${oneUserInfo.username}.jpg">
                    </c:otherwise>
                </c:choose>
            </div>
            <div id="userInfo">
                <c:if test="${oneUserInfo.username!=user.username}">
                    <c:if test="${guanzhu!=1}">
                        <div style="color: #333333;font-size: 22px;font-weight: bold;">
                            ${oneUserInfo.username}
                            <a href="/tieba/UserUserGuanZhu?userName1=${user.username}&userName2=${oneUserInfo.username}&guanzhu=1">
                                <img style="margin-left: 20px;" src="/guanzhu.png">
                            </a>
                        </div>
                    </c:if>
                    <c:if test="${guanzhu==1}">
                        <div style="color: #333333;font-size: 22px;font-weight: bold;">
                            ${oneUserInfo.username}
                            <a href="/tieba/UserUserGuanZhu?userName1=${user.username}&userName2=${oneUserInfo.username}&guanzhu=0">
                                <img style="margin-left: 20px;" src="/quxiaoguanzhu.png">
                            </a>
                        </div>
                    </c:if>
                    <div stdle="float: left;"><a href="/tieba/openWebSocket?userPetname=${oneUserInfo.username}"><button>私信</button></a></div>
                </c:if>
                <div style="color: #797C80;margin-top: 10px;font-size: 12px;">用户名:${oneUserInfo.username}</div>
            </div>
        </div>
        <div id="loveTieba">
            <div style="font-size: 16px;">爱逛的吧</div>
            <c:forEach var="oneUsertieba" items="${oneUserInfo.userTiebas}" varStatus="status">
                <c:if test="${status.index<6}">
                    <a style="color: #444444;margin-top: 20px;display: block;width: 20%;"  href="/tieba/openTiebaById?tiebaId=${oneUsertieba.tiebaid}&&page=1&&type=-1">
                        <button style="background-color: #FBFBFC;height: 40px;line-height: 30px;width: 100%;">
                                <span style="float: left;margin-left: 5px;">${oneUsertieba.tiebaName}</span>
                                <div class="userTiebaLevel">
                                    <span style="display: block;font-family: 微软雅黑;font-size: 2px;position: relative;top: -2px;left: -5px;">${oneUsertieba.level}</span>
                                </div>
                        </button>
                    </a>
                </c:if>
            </c:forEach>
        </div>
        <div id="dongtai">
            <div style="font-size: 16px;">最近动态</div>
            <div style="margin-top: 30px;">
                <c:forEach var="tiezi" items="${tiezis}" varStatus="status">
                    <c:if test="${status.index<6}">
                        <div style="margin-top: 15px;width: 90%;">
                            <div style="margin-top: 5px;"><a style="color: #666666;" href="/tieba/openTiebaById?tiebaId=${tiezi.tiebaid}&&page=1&&type=-1">${tiezi.tiebaName}</a></div>
                            <div style="margin-top: 5px;">
                                <a href="/tieba/openTieZi?TieZiId=${tiezi.id}&page=1">
                                    <div style="color: #2962B6;font-size: 18px;">标题：${tiezi.title}</div>
                                </a>
                            </div>
                            <div style="color: #919499;font-size: 14px;margin-top: 5px;">
                                <a href="/tieba/openUserInfo?userPetname=${tiezi.userpetname}">
                                    楼主：${tiezi.userpetname}
                                </a>
                            </div>
                            <span style="float: right;">创建时间：${tiezi.cteatetime}</span>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>
