<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/12
  Time: 10:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
    <title>Title</title>
</head>
<style>
    a{
        text-decoration:none;
    }
    .huifuNum{
        border-radius:2px 2px 2px 2px;
        border: 1px solid #EBEBEB;
        display: block;
        width: 50px;
        height: 25px;
        float: left;
        margin-left: 30px;
        text-align: center;
        line-height: 25px;
        font-size: 12px;
        background-color: #F7F7F7;
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
    #center{
        background-color: white;
        width: 70%;
        margin-left: 15%;
    }
    hr{
        height:1px;border:none;border-top:1px dashed #E4E6EB;"
    }
    #tieZidiv{
        border: 1px solid #E4E6EB;
    }
    #daohang{
        display: block;
        border: 1px solid #DBDCE0;
        background-color: #EDEEF2;
        height: 50px;
    }
    #daohang ul{
        padding-left: 0px;
        margin-top: 0px;
    }
    #daohang li{
        float: left;
        list-style-type: none;
        width: 70px;
        height: 50px;
        text-align: center;
        line-height: 50px;
        background-color: #DEE0E4;
    }
    #tieBaInfo{
        border-top: 1px solid #DBDCE0;
        margin-top: 20px;
    }
    #tiebaHead{
        border: 3px solid #FFFFFF;
        width: 150px;
        margin-left: 30px;
        margin-bottom: 15px;
    }
    #tiebaName{
        float: left;
        color: #333333;
        display: block;
        font-weight: bold;
        font-size: 24px;
    }
    #signature{
        float: left;
        color: #4C4C4C;
        display: block;
        font-size: 15px;
        margin-top: 15px;
        font-family: 微软雅黑;
    }
    #guanzhu{
        margin-left: 30px;
        margin-top: -3px;
        float: left;
        display: block;
        border: 1px solid #E64C4C;
        border-radius: 4px;
    }
    #guanzhuNum{
        color: #FD7F89;
        margin-left: 10px;
        margin-top: 5px;
        float: left;
    }
    #tieziNum{
        color: #FD7F89;
        float: left;
        margin-top: 5px;
        margin-left: 30px;
    }
    .tishitext{
        color: #AAAAAA;
        font-size: 10px;
    }
    #mulu{
        float: left;
        margin-left: 100px;
        margin-top: 5px;
    }
    #qiandao{
        float: left;
        margin-left: 100px;
    }
</style>
<body>
    <jsp:include page="/head.jsp"></jsp:include>
    <jsp:include page="/search.jsp"></jsp:include>
    <div style="width: 100%;background-color: #DCEFFE;">
        <div id="center">
            <div>
                <div id="tieBaInfo">
                    <div id="tiebaHead"><img src="/${tieBa.name}.jpg"/>
                    </div>
                </div>
                <div style="float: left;margin-left: 200px;margin-top: -80px;">
                    <div id="tiebaName">${tieBa.name}</div>
                    <div id="guanzhu">
                        <a href="/tieba/guanzhu?tiebaId=${tieBa.id}">
                            <c:choose>
                                <c:when test="${userTieba.isfollow eq '1'}">
                                    <img src="/quxiaoguanzhu.png">
                                </c:when>
                                <c:otherwise>
                                    <img src="/guanzhu.png">
                                </c:otherwise>
                            </c:choose>
                        </a>
                    </div>
                    <div id="guanzhuNum"><span class="tishitext">关注:</span><span>${guanzhuNum}</span></div>
                    <div id="tieziNum"><span class="tishitext">帖子:</span><span>${tieziNum}</span></div>
                    <div id="mulu"><span class="tishitext">目录:</span>
                        <a href="/tieba/selectChildById?categoryId=${fromCategory.categoryid}&page=1&OnePageCount=20">${fromCategory.categoryname}</a>
                    </div>
                    <div id="signature">${tieBa.signature}</div>
                    <div id="qiandao">
                        <c:choose>
                            <c:when test="${userTieba.isqiandao eq '1'}">
                                <img src="/qiandaohou.png">
                            </c:when>
                            <c:otherwise>
                            <a href="/tieba/qiandao?tiebaId=${tieBa.id}">
                                <img src="/qiandao.png">
                            </a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            <div id="daohang">
                <ul>
                    <li><a href="/tieba/openTiebaById?tiebaId=${tieBa.id}&page=1&type=-1">看帖</a></li>
                    <li><a href="/tieba/openTiebaById?tiebaId=${tieBa.id}&page=1&type=1">精品</a></li>
                    <li>
                        <a href="/writeTieZi.jsp?tieBaId=${tieBa.id}">写帖子</a>
                    </li>
                </ul>
            </div>
            <div id="tieZidiv">
                <hr>
                <c:forEach var="tieZi" items="${tieZiList}">
                    <div class="tiezi">
                        <div class="huifuNum">${tieZi.huifuNum}</div>
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
                <a style="margin-left: 20px;" href="/tieba/openTiebaById?tiebaId=${tieBa.id}&page=1&type=-1">首页</a>
                <c:forEach var="index" begin="1" end="${pageMax}">
                    <c:choose>
                        <c:when test="${index!=page}">
                            <a href="/tieba/openTiebaById?tiebaId=${tieBa.id}&type=-1&page=${index}">${index}</a>
                        </c:when>
                        <c:otherwise>
                            <span style="color: black">${index}</span>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <a href="/tieba/openTiebaById?tiebaId=${tieBa.id}&page=${pageMax}&type=-1">尾页</a>
            </div>
        </div>
    </div>
</body>
</html>
