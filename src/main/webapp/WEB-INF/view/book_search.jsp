<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>solr搜索</title>
    <script type="text/javascript" src="${ctx}/jquery-1.9.1.min.js"></script>
</head>

<body>
    <div>
        <input type="text" id="query">
        <a href="javascript:query()" >搜索</a>
        <a href="javascript:queryAll()" >搜索全部</a>

    </div>

    <div>
        <ul id="book_ul">
        </ul>
    </div>

</body>
</html>
<script>
    function query() {
        var value = $("#query").val();
        $.ajax({
            url:"/book/query",
            data:{
                "query":value
            },
            type:"GET",
            success:function (data) {
                $("#book_ul").empty();
                $.each(data, function (index, value) {
                    $("#book_ul").append("<li>" + value.description + "</li>");
                })
            }
        });
    }

    function queryAll() {
        $.ajax({
            url:"/book/queryAll",
            type:"POST",
            success:function (data) {
                $("#book_ul").empty();
                $.each(data,function (index,value) {
                    $("#book_ul").append("<li>"+value.description+"</li>");
                })
            }
        });
    }
</script>
