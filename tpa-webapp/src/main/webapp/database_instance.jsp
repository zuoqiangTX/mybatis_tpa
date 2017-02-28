<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
<h4>[一]选择数据库 [<a href="instance_edit">添加</a>]</h4>
<table border="1" cellpadding="0" cellspacing="0" bordercolor="#002211" style="border-collapse:collapse;">
    <thead>
    <tr>
        <td>IP</td>
        <td>Port</td>
        <td>Schema</td>
        <td>DBType</td>
        <td>UserName</td>
        <td>Password</td>
        <td>Operator</td>
    </tr>
    </thead>
    <c:forEach items="${database_Instance}" var="database">
        <tr>
            <td>
                <a href="tables?ip=${database.ip}&port=${database.port}&userName=${database.userName}&password=${database.password}&schema=${database.schema}">${database.ip}</a>
            </td>
            <td>${database.port}</td>
            <td>${database.schema}</td>
            <td>${database.dbType}</td>
            <td>${database.userName}</td>
            <td>${database.password}</td>
            <td>
                <a href="javascript:beforeOpen('instance_edit_delete?ip=${database.ip}&port=${database.port}&schema=${database.schema}')">删除</a>
                <a href="instance_edit?ip=${database.ip}&port=${database.port}&userName=${database.userName}&password=${database.password}&schema=${database.schema}">修改</a>
            </td>
        </tr>
    </c:forEach>
</table>
<script>
    function beforeOpen(url){
        var confirmValue=window.confirm("是否删除");
        if(confirmValue){
            window.open(url);
        }
    }
</script>
</body>
</html>
