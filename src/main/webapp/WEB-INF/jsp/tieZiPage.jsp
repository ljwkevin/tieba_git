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
<style>
    #center{
        background-color: white;
        width: 70%;
        margin-left: 15%;
        float: left;
    }
    a{
        text-decoration: none;
    }
    a:visited{
        color: #2D64B3;
    }
    #huifuform{
        margin-top: 40px;
        float: left;
        padding-bottom: 200px;
    }
    .huifu{
        padding-top: 15px;
        border-bottom: 1px solid #E1E4E6;
        width: 100%;
        float: left;
    }
    .userinfo{
        width: 100px;
        background-color: #FBFBFD;
        padding-left: 20px;
        font-size: 13px;
    }
    .userinfo,.content{
        float: left;
    }
    .userinfo img{
        width: 80px;
        height: 80px;
        margin-left: 3px;
        margin-top: 3px;
    }

</style>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="/head.jsp"></jsp:include>
<jsp:include page="/search.jsp"></jsp:include>
<div style="width: 100%;background-color: #DCEFFE;float: left;">
    <div id="center">
        <div style="background-color: #F5F7FA;color: #2D64B3;font-weight: bold;
        font-size: 10px;height: 40px;border: 1px solid #DBDCE0;line-height: 40px;">
            <a style="margin-left: 20px;" href="/tieba/openTieZi?TieZiId=${tieZi.id}&page=1">首页</a>
            <c:forEach var="index" begin="1" end="${pageMax}">
                <c:choose>
                    <c:when test="${index!=page}">
                        <a href="/tieba/openTieZi?TieZiId=${tieZi.id}&page=${index}">${index}</a>
                    </c:when>
                    <c:otherwise>
                        <span style="color: black">${index}</span>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <a href="/tieba/openTieZi?TieZiId=${tieZi.id}&page=${pageMax}">尾页</a>
            <span style="display: inline-block;margin-left: 10px;">
                <span style="color: #FF6617">${fn:length(huiFus)}</span>
                回复贴,共
                <span style="color: #FF6617">${pageMax}</span>
                页</span>
        </div>
        <div style="height: 50px;line-height: 50px;border: 1px solid #DBDCE0;
        padding-left: 30px;font-family: 微软雅黑">贴子名字：${tieZi.title}</div>
        <div style="margin-top: 20px;">
            <c:forEach var="huiFu" items="${huiFus}">
                <div class="huifu">
                    <div style="float: left;width: 100%;">
                        <div class="userinfo">
                            <div style="border: 1px solid #CCCCCC; width: 86px;height: 86px;">
                                <a href="/tieba/openUserInfo?userPetname=${huiFu.userpetname}">
                                    <c:choose>
                                        <c:when  test="${empty huiFu.user.userInfo.headimgurl}">
                                            <img src="/default.jpg">
                                        </c:when>
                                        <c:when test = "${fn:contains(huiFu.user.userInfo.headimgurl,'default')}">
                                            <img src="/default.jpg">
                                        </c:when>
                                        <c:otherwise>
                                                <img src="/${huiFu.user.username}.jpg">
                                        </c:otherwise>
                                    </c:choose>
                                </a>
                            </div>
                            <div style="margin-top: 15px;margin-left: 10px;margin-right: 10px;">
                                <a href="/tieba/openUserInfo?userPetname=${huiFu.userpetname}">${huiFu.userpetname}</a>
                            </div>
                            <div style="margin-top: 15px;margin-left: 10px;margin-right: 10px;">级别：${huiFu.user.userTiebas[0].level}</div>
                        </div>
                        <div class="content" style="float: left;width: 80%;"><span>${huiFu.content}</span></div>
                    </div>
                    <div style="float: right;margin-right: 40px;color: #999999;font-size: 12px;font-family: 微软雅黑;">
                        <span class="louceng">${huiFu.louceng}楼</span>
                        <span>${huiFu.time}</span>
                    </div>
                    <div style="float: left;width: 95%;padding-bottom: 20px;">
                        <div class="openHuifuForm" style="float: right;"><button>回复</button></div>
                        <c:if test="${fn:length(huiFu.pingLunList) != 0}">
                            <div class="pinglunCheck" style="float: right;"><button>收起评论</button></div>
                            <div class="pinglundiv" style="margin-left: 120px;border: 1px solid #F0F1F2;background-color: #F7F8FA;margin-top: 30px;">
                                <c:forEach var="pinglun" items="${huiFu.pingLunList}">
                                    <div style="padding-top: 5px;padding-left: 5px;padding-bottom: 5px;">
                                        <a href="/tieba/openUserInfo?userPetname=${pinglun.userpetname}" style="font-size: 13px;">${pinglun.userpetname}:</a>
                                        <span>${pinglun.context}</span>
                                        <span style="float: right;font-size: 14px;">${pinglun.time}</span><br>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>
                        <div class="pinglunform" style="display: none;margin-left: 120px;margin-top: 10px;">
                            <form action="/tieba/wtirePingLun">
                                <textarea name="context" style="width: 60%;height: 50px;"></textarea>
                                <input name="tiebaId" type="hidden" value="${tieZi.tiebaid}" />
                                <input name="tieziid" value="${tieZi.id}" type="hidden" />
                                <input name="userpetname" type="hidden"  value="${userInfo.userpetname}" />
                                <input name="huifuid" type="hidden"  value="${huiFu.id}" />
                                <button type="submit">发表</button>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div id="huifuform">
            <form action="/tieba/wtireHuiFu">
                <div style="border: 1px solid #DFDFDF;height: 150px;width: 700px;margin-left: 30px;">
                    <textarea style="margin-left: 10px;margin-top: 10px;width: 680px;height: 130px;" name="content"></textarea>
                </div>
                <button style="margin-left: 30px;margin-top: 5px;" type="submit">发表</button>
                <input name="tiebaId" type="hidden"  value="${tieZi.tiebaid}" />
                <input name="userpetname" type="hidden"  value="${userInfo.userpetname}" />
                <input name="tieziid" type="hidden"  value="${tieZi.id}" />
            </form>
        </div>
    </div>
</div>
<script type="text/javascript" src="/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
    $(".pinglunCheck").click(function(){
        var txt = $(this).text();
        if(txt.indexOf("收起")!=-1){
            $(this).next(".pinglundiv").hide();
            txt = "展开评论";
        }else{
            $(this).next(".pinglundiv").show();
            txt = "收起评论";
        }
        $(this).find("button").html(txt);
    });
    $(".openHuifuForm").click(function(){
        var pinglunform = $(this).siblings(".pinglunform");
        var display = pinglunform.css('display');
        if(display=='none'){
            pinglunform.show();
        }
        else{
            pinglunform.hide();
        }
    })
    var huifus = $('.huifu');
    var tieZiId = "${tieZi.id}";
    for (var i = 0; i < huifus.length; i++) {
        var louceng = huifus.eq(i).find('.louceng').text();
        var content = huifus.eq(i).find('.content');
        var contenthtml = content.html();
        if (contenthtml.indexOf("img") != -1) {
            contenthtml = contenthtml.substring(0, contenthtml.length - 3);
            contenthtml += "<img src='/" + tieZiId + "_" + louceng + ".jpg' />"
            content.html(contenthtml);
        }
    }
</script>
</body>
</html>
