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
    input[type=checkbox] {
	  zoom: 200%;
	}
</style>
<h2><a href="/">Tpa-Generator</a></h2>
<h4>[三]生成 In ${ip}:${port}/${schema}&nbsp;&nbsp;[<a href="tables?ip=${ip}&port=${port}&userName=${userName}&password=${password}&schema=${schema}">返回</a>]</h4>

<form method="post" action="down" onsubmit="return check(this)">
<h4>提醒：<a style="color:#FF3030">${table.alterMsg}</a></h4>
<h4>表名:${table.tableName}</h4>
<h4>Model名: <input name="JavaTypeName" type="text" class="input" value="${table.beanName}"/></h4>
<h4>Model注释: <input name="JavaTypeComment" type="text" class="input" value=""/></h4>
<h4>Model Package: <input name="package" type="text" class="input" value=""/></h4>
<h4>模型类型: 
	<select name="modelType">     
	  <option value="complex">复杂模型</option>     
	  <option value="simple">简单模型</option> 
	</select>
</h4>
<h4>(简单模型：mapper,dao,service,query;复杂模型包含facade,dto;)</h4>

    <table border="1" cellpadding="0" cellspacing="0" bordercolor="#002211" style="border-collapse:collapse;">
        <thead>
            <tr>
                <td>列名</td>
                <td>数据库类型</td>
                <td>属性名</td>
                <td>java类型</td>
                <td>注释</td>
                <td>findById</td>
                <td>where条件</td>
            </tr>
        </thead>
        <c:forEach items="${table.columns}" var="column">
            <tr>
                <td>${column.columnName}</td>
                <td>${column.columnType}</td>
                <td>
                    <input name="${column.fieldName}.javaName" type="text" value="${column.fieldName}">
                </td>
                <td>${column.javaType}</td>
                <td>
                    <input name="${column.fieldName}.comment" type="text" value="${column.comment}">
                </td>
                <td>
                	<c:if test="${column.columnType == 'INTEGER' || column.columnType == 'BIGINT' || column.columnType == 'VARCHAR' }">
                		<input type="radio" value="${column.fieldName}" name="findById" <c:if test="${column.primaryKey}">checked</c:if>></input>
                	</c:if>
                </td>
                <td>
                	<input type="checkbox" name="${column.fieldName}.where" value="100" title="选择/不选择" <c:if test="${column.columnType == 'INTEGER' || column.columnType == 'BIGINT' || column.columnType == 'VARCHAR' }">checked</c:if>>
                </td>
            </tr>
        </c:forEach>
    </table>
    <input name="tableConfigJson" type="hidden" value="${tableConfigJson}"/>
    <input type="submit" value="下载" style="margin-top: 10px"/>
</form>
<hr>
<h5>您的工程中需要引入这个语言包，这个包很简单，主要是一个Dao的父类和几个Annotation,不会对你的工程造成污染</h5>
<h5>当然如果您不使用Tpa为您生成的Dao实现，只是使用了自动生成的Mode和Mybatis文件片段的话，那您则不需要引入.</h5>
<h6>
    <textarea cols="100" rows="10">
        <dependency>
            <groupId>com.tbjfund.framework</groupId>
            <artifactId>tpa-lang</artifactId>
            <version>1.1</version>
        </dependency>
    </textarea>
</h6>

<script type="text/javascript">
	function check(){
		var msg = '${table.alterMsg}';
		if(msg.length != ''){
			alert(msg);
			
			return false;
		}
		
		return true;
	}
</script>

</body>
</html>
