<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日志</title>
</head>
<body>
	<table border="1">
		<tr>
			<td>编号</td>
			<td>用户</td>
			<td>类型</td>
			<td>内容</td>
			<td>创建时间</td>
		</tr>
		<c:forEach var="logs" items="${requestScope.logses}"
			varStatus="um">
			<tr>
				<td>${logs.logsId }</td>
				<td>${logs.userId }</td>
				<td>${logs.entity }</td>
				<td>${logs.text}</td>
				<td>${logs.createDate}</td>
			</tr>
		</c:forEach>

	</table>
	<c:if test="${requestScope.page>0 }">
		<a href="${requestScope.page-1 }">上一页</a>
	</c:if>
	共${requestScope.logsCount }条日志，第${requestScope.page+1 }/${requestScope.allPage+1 }页
	<c:if test="${(requestScope.page)<requestScope.allPage }">
		<a href="${requestScope.page+1 }">下一页</a>
	</c:if>
	<p />
	<a href="/musicCenter/main">返回主菜单</a>
</body>
</html>