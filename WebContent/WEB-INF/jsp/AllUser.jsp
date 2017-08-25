<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>所有账号</title>
</head>
<body>
	<table border="1">
		<tr>
			<td>编号</td>
			<td>账号名</td>
			<td>email</td>
			<td>电话号码</td>
			<td>是否管理员</td>
			<td>是否冻结</td>
			<td>创建时间</td>
			<td>修改时间</td>
		</tr>
		<c:forEach var="user" items="${requestScope.users}"
			varStatus="um">
			<tr>
				<td>${user.userId }</td>
				<td>${user.account }</td>
				<td>${user.email }</td>
				<td>${user.phoneNumber }</td>
				<td><c:if test="${user.isAdmin==false}">
					否
					</c:if> <c:if test="${user.isAdmin==true}">
					是
					</c:if></td>
				<td><c:if test="${user.isFreeze==false}">
					否
					</c:if> <c:if test="${user.isFreeze==true}">
					是
					</c:if></td>
				<td>${user.createDate }</td>
				<td>${user.editDate }</td>
			</tr>
		</c:forEach>

	</table>
	<c:if test="${requestScope.page>0 }">
		<a href="${requestScope.page-1 }">上一页</a>
	</c:if>
	共${requestScope.userCount }条评论，第${requestScope.page+1
	}/${requestScope.allPage+1 }页
	<c:if test="${(requestScope.page)<requestScope.allPage }">
		<a href="${requestScope.page+1 }">下一页</a>
	</c:if>
	<p />
	<a href="/musicCenter/main">返回主菜单</a>
</body>
</html>