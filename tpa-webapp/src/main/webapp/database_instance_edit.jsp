<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
</head>
<body>
<h2><a href="/">Tpa-Generator</a></h2>
<h4>数据库实例 [<a href="/">返回</a>]</h4>

<form method="post" action="instance_edit_submit">
    <table border="1" cellpadding="0" cellspacing="0" bordercolor="#002211" style="border-collapse:collapse;">
        <thead>
        <tr>
            <td>IP</td>
            <td>Port</td>
            <td>DBType</td>
            <td>userName</td>
            <td>password</td>
            <td>Schema</td>
        </tr>
        </thead>
        <tr>
            <input type="hidden" name="original_ip" value="${database.ip}"/>
            <input type="hidden" name="original_port" value="${database.port}"/>
            <input type="hidden" name="original_schema" value="${database.schema}"/>
            <td><input type="text" name="ip" value="${database.ip}"/></td>
            <td><input type="text" name="port" value="${database.port}"/></td>
            <td><input type="text" name="dbType" value="mysql"/></td>
            <td><input type="text" name="userName" value="${database.userName}"/></td>
            <td><input type="text" name="password" value="${database.password}"/></td>
            <td><input type="text" name="schema" value="${database.schema}"/></td>
        </tr>
    </table>
    <input type="submit"/>
</form>
</body>
</html>
