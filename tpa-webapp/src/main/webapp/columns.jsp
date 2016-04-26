<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
</head>
<body>
<h2><a href="/">Tpa-Generator</a></h2>
<h4>[三]生成 In ${ip}:${port}/${schema}&nbsp;&nbsp;[<a href="tables?ip=${ip}&port=${port}&userName=${userName}&password=${password}&schema=${schema}">返回</a>]</h4>
<h4>表名:${table.tableName}</h4>
<h4>Model名: <input name="JavaTypeName" type="text" value="${table.beanName}" /></h4>

<form method="post" action="down">
    <table border="1" cellpadding="0" cellspacing="0" bordercolor="#002211" style="border-collapse:collapse;">
        <thead>
            <tr>
                <td>列名</td>
                <td>数据库类型</td>
                <td>属性名</td>
                <td>java类型</td>
                <td>注释</td>
            </tr>
        </thead>
        <c:forEach items="${table.columns}" var="column">
            <tr>
                <td>${column.columnName}</td>
                <td>${column.columnType}</td>
                <td><input name="${column.fieldName}.javaName" type="text" value="${column.fieldName}"></td>
                <td>${column.javaType}</td>
                <td>${column.comment}</td>
            </tr>
        </c:forEach>
    </table>
    <input name="tableConfigJson" type="hidden" value="${tableConfigJson}"/>
    <input type="submit" name="下载"/>
</form>
</body>
</html>
