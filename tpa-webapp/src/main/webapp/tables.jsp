<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
</head>
<body>
<style>
    input{
        width: 200px;
    }
    table td,table th{
        padding: 2px
    }
</style>
<h2><a href="/">Tpa-Generator</a></h2>
<h4>[二]选择表 In ${ip}:${port}/${schema}&nbsp;&nbsp;[<a href="/">返回</a>]</h4>
<table border="1" cellpadding="0" cellspacing="0" bordercolor="#002211" style="border-collapse:collapse;">
    <thead>
        <tr>
            <td>Tables</td>
        </tr>
    </thead>
    <c:forEach items="${tables}" var="table">
        <tr>
            <td><a href="columns?ip=${ip}&port=${port}&userName=${userName}&password=${password}&schema=${schema}&table=${table}">${table}</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
