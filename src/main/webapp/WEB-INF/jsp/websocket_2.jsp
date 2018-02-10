<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>My WebSocket</title>
</head>
<style>
    #message{
        width: 200px;
        height: 15px;
        font-size: 10px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }
</style>
<meta charset="utf-8"/>
<body>
    <div id="message"></div>
</body>

<script type="text/javascript">
    var websocket = null;

    //判断当前浏览器是否支持WebSocket
    if('WebSocket' in window){
        var username = "${user.username}";
        websocket = new WebSocket("ws://localhost:8080/websocket/"+username);
    }
    else{
        alert('Not support websocket');
    }

    //连接发生错误的回调方法
    websocket.onerror = function(){
        document.getElementById('message').innerHTML = "失败";
    };

    //连接成功建立的回调方法
    websocket.onopen = function(event){
        document.getElementById('message').innerHTML = "连接成功";
    }

    //接收到消息的回调方法
    websocket.onmessage = function(event){
        setMessageInnerHTML(event.data);
    }

    //连接关闭的回调方法
    websocket.onclose = function(){
        document.getElementById('message').innerHTML = "连接关闭";
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function(){
        websocket.close();
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML){
        if(innerHTML.indexOf(",")!=-1){
            var splits = innerHTML.split(",");
            if(splits[0]!="${user.username}") {
                document.getElementById('message').innerHTML = splits[0]+":"+splits[2];
            }
        }
    }
</script>
</html>