<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>所有评论</title>
</head>
<body>
	<table border="1">
		<tr>
			<td>编号</td>
			<td>评论歌曲编号</td>
			<td>评论内容</td>
			<td>评论者id</td>
			<td>评论时间</td>
			<td>修改时间</td>
			<td>是否屏蔽</td>
			<td>屏蔽理由</td>
		</tr>
		<c:forEach var="comment" items="${requestScope.comments}"
			varStatus="um">
			<tr>
				<td>${comment.commentId }</td>
				<td>${comment.songId }</td>
				<td>${comment.text }</td>
				<td>${comment.userId }</td>
				<td>${comment.createDate }</td>
				<td>${comment.editDate }</td>
				<td><c:if test="${comment.unlook==false}">
					否
					</c:if> <c:if test="${comment.unlook==true}">
					是
					</c:if></td>
				<td>${comment.unlookReason }</td>
			</tr>
		</c:forEach>

	</table>
	<c:if test="${requestScope.page>0 }">
		<a href="${requestScope.page-1 }">上一页</a>
	</c:if>
	共${requestScope.commentCount }条评论，第${requestScope.page+1
	}/${requestScope.allPage+1 }页
	<c:if test="${(requestScope.page)<requestScope.allPage }">
		<a href="${requestScope.page+1 }">下一页</a>
	</c:if>
	<p />
	<a href="/musicCenter/main">返回主菜单</a>
</body>
</html>