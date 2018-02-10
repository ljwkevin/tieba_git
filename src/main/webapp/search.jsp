<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/25
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body style="margin-top: 0px;">
    <div style="height: 105px;border-bottom: 1px solid #F0F0F0;">
        <div style="display: block;float: left;margin-left: 25%;position: relative;top: 10%;">
            <input style="width: 535px;height:42px;border: 1px solid #CCCCCC;float: left;" type="text" id="search" />
            <div id="gotieba" style="width: 105px;height: 42px;background-color: #317EF3;color: white;float: left;line-height: 42px;">
                <span style="margin-left: 20px;">进入贴吧</span>
            </div>
            <div id="searchIndex" style="margin-left: 5px;float: left;width: 105px;height: 40px;border: 1px solid #3388FF;color: #3388FF;line-height: 40px;">
                <span style="margin-left: 20px;">全吧搜索</span>
            </div>
        </div>
    </div>
</body>
<script type="text/javascript" src="/js/jquery-3.2.1.min.js"></script>
<script>
    $("#gotieba").click(function(){
        search = $("#search").val();
        window.location.href="/tieba/search?key="+search;
    });
    $("#searchIndex").click(function(){
        search = $("#search").val();
        window.location.href="/tieba/selectBySolrTitleIndex?page=1&&key="+search;
    });
</script>
</html>
