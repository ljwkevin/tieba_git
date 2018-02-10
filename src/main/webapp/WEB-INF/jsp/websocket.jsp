<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>My WebSocket</title>
</head>
<style>
    img{
        float: left;
        width: 50%;
        height: 40px;
    }
    .zuijin{
        height: 40px;
        float: left;
        width: 100%;
        text-align: center;
        line-height: 40px;
        color: white;
    }

    .myOnemessage{
        float: right;
        margin-right: 30px;
        width: 100%;
    }
    .otherOnemessage{
        float: left;
        margin-left: 30px;
        width: 100%;
    }
    .otherMessageInfo{
        display: block;
        text-align: left;
        width: 100%;
    }
    .messageInfo{
        display: block;
        text-align: right;
        width: 100%;
    }
    .otherMessageSpan{
        padding-left: 10px;
        padding-right: 10px;
        float: left;
        display: block;
        line-height: 40px;
        background-color: #2683F5;
        border-radius: 5px;
        text-align: left;
        white-space:normal;
        word-break:break-all;
        word-wrap:break-word;
        max-width:300px;
    }
    .messageSpan{
        float: right;
        display: block;
        padding-left: 10px;
        padding-right: 10px;
        line-height: 40px;
        background-color: #2683F5;
        border-radius: 5px;
        text-align: left;
        white-space:normal;
        word-break:break-all;
        word-wrap:break-word;
        max-width:300px;
    }
    #message{
        width: 100%;
        height: 300px;
        overflow-y:scroll;
        overflow-x:hidden;
    }
</style>
<meta charset="utf-8"/>
<body style="margin-top: 0px;">
<jsp:include page="/head.jsp"></jsp:include>
<div style="width: 70%;margin-left: 15%;">
    <div style="width: 25%;float: left;">
        <div style="background-color: #414A73;color: white;height: 50px;text-align:center;line-height: 50px;">
            私信消息
        </div>
        <div style="background-color: #282D45;">
            <img id="zuijinimg" src="/zuijinlianxi.png">
            <img id="haoyouimg" src="/haoyou.png">
        </div>
        <div id="zuijinlianxi" style="height: 480px;background-color: #282D45;">
        </div>
        <div id="friends" style="height: 480px;background-color: #282D45;display: none;">
            <c:forEach var="friend" items="${friends}">
                <div class="zuijin">${friend.username2}</div>
            </c:forEach>
        </div>
    </div>
    <div style="width: 75%;float: left;">
        <div style="border: 1px solid #CFCFCF;background-color: #F8F8F8;height: 50px;text-align: center;line-height: 50px;font-size: 20px;">
            正在与
            <span style="font-weight: bold;" id="people">${toUserName}</span>
            对话中
        </div>
        <div id="user" style="display: none;">${user.username}</div>
        <div id="message">
        </div>
    </div>
    <div style="float: left;margin-top: 30px;width: 70%;margin-left: 3%;">
        <div style="border: 1px solid #BBBDBF;height: 100px;padding-left: 10px;padding-bottom: 10px;">
            <textarea style="width: 95%;height: 80%;margin-top: 10px;" id="text"></textarea>
        </div>
        <button onclick="send()">发送</button>
        <button onclick="closeWebSocket()">关闭连接退出登录</button>
    </div>
</div>
</body>
<script type="text/javascript" src="/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
    var websocket = null;
    var myArray=new Array();
    $("#zuijinimg").click(function(){
        $("#zuijinlianxi").show();
        $("#friends").hide();
    });
    $("#haoyouimg").click(function(){
        $("#friends").show();
        $("#zuijinlianxi").hide();
    });
    $(document).on('click', ".zuijin", function () {
        var user = $("#user").html();
        var people = $("#people").html();
        websocket.send($(this).text());
        $(this).css("color","white");
        if($("#people").text()==$(this).text()){
            return;
        }
        $("#message").html("");
        $(this).css("background-color","#1F233A");
        $("#people").text($(this).text());
        for(var i =0;i<myArray.length;i++){
            var splits = myArray[i];
            splits[4]==1;
            if(splits[0]==$("#user").html()&&splits[1]==$("#people").html()){
                var wenben = $("<span></span>").addClass("messageSpan");
                wenben.text(splits[2]);
                var webbeninfo = $("<span></span>").addClass("messageInfo");
                var mymessage = $("<div></div>").addClass("myOnemessage");
                webbeninfo.text(splits[0]+","+splits[3]);
                mymessage.append(webbeninfo).append(wenben);
                $("#message").append(mymessage);
            }else if(splits[0]==$("#people").html()&&splits[1]==$("#user").html()){
                var wenben = $("<span></span>").addClass("otherMessageSpan");
                wenben.text(splits[2]);
                var webbeninfo = $("<span></span>").addClass("otherMessageInfo");
                var othermessage = $("<div></div>").addClass("otherOnemessage");
                webbeninfo.text(splits[0]+","+splits[3]);
                othermessage.append(webbeninfo).append(wenben);
                $("#message").append(othermessage);
            }
        }
        $("#message")[0].scrollTop = $("#message")[0].scrollHeight;
    })

    //判断当前浏览器是否支持WebSocket
    if('WebSocket' in window){
        var username = "${user.username}";
        websocket = new WebSocket("ws://localhost:8080/websocket/"+username);
    }
    else{
        alert('Not support websocket')
    }

    //连接发生错误的回调方法
    websocket.onerror = function(){
//        setMessageInnerHTML("error");
    };

    //连接成功建立的回调方法
    websocket.onopen = function(event){
//        setMessageInnerHTML("success");
    }

    //接收到消息的回调方法
    websocket.onmessage = function(event){
        setMessageInnerHTML(event.data);
    }

    //连接关闭的回调方法
    websocket.onclose = function(){
        setMessageInnerHTML("close");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function(){
        websocket.close();
    }
    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML){
        var lianxidivAll = $("#zuijinlianxi");
        var splits = innerHTML.split(",");
        var peopleName = splits[0];
        var peopleName2 = splits[1];
        var readFlag = splits[4];
        var zuijins = lianxidivAll.find(".zuijin");
        var zuijins2 = $(".zuijin");
        var flag = true;
        for(var i=0;i<zuijins.length;i++){
            var zuijin = zuijins.eq(i).text();
            if(zuijin==""){
                continue;
            }
            if(peopleName==$("#user").html()){
                if(zuijin==peopleName2){
                    flag = false;break;
                }
            }else if(peopleName2==$("#user").html()){
                if(zuijin==peopleName){
                    if(readFlag==0){
                        zuijins.eq(i).css("color","red");
                    }
                    flag = false;break;
                }
            }
        }
        myArray.push(splits);
        if(flag==true){
            var onepeople = $("<div></div>").addClass("zuijin");
            if(peopleName==$("#user").html()){
                onepeople.text(peopleName2);
            }else if(peopleName2==$("#user").html()){
                onepeople.text(peopleName);
                if(readFlag==0){
                    onepeople.css("color","red");
                }
            }
            lianxidivAll.prepend(onepeople);
        }
        if(splits[0]==$("#user").html()&&splits[1]==$("#people").html()){
            var wenben = $("<span></span>").addClass("messageSpan");
            wenben.text(splits[2]);
            var webbeninfo = $("<span></span>").addClass("messageInfo");
            var mymessage = $("<div></div>").addClass("myOnemessage");
            webbeninfo.text(splits[0]+","+splits[3]);
            mymessage.append(webbeninfo).append(wenben);
            $("#message").append(mymessage);
        }else if(splits[0]==$("#people").html()&&splits[1]==$("#user").html()){
            var wenben = $("<span></span>").addClass("otherMessageSpan");
            wenben.text(splits[2]);
            var webbeninfo = $("<span></span>").addClass("otherMessageInfo");
            var othermessage = $("<div></div>").addClass("otherOnemessage");
            webbeninfo.text(splits[0]+","+splits[3]);
            othermessage.append(webbeninfo).append(wenben);
            $("#message").append(othermessage);
        }
        $("#message")[0].scrollTop = $("#message")[0].scrollHeight;

//        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }

    //关闭连接
    function closeWebSocket(){
        websocket.close();
    }

    //发送消息
    function send(){
        var user = $("#user").html();
        var people = $("#people").html();
        var text = document.getElementById('text').value;
        var message = user+","+people+","+text;
        $("#text").val("");
        websocket.send(message);
    }
</script>
</html>