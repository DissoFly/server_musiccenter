<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>所有歌曲</title>
</head>
<body>
	<table border="1">
		<tr>
			<td>编号</td>
			<td>歌名</td>
			<td>歌手</td>
			<td>专辑</td>
			<td>上传者编号</td>
		</tr>
		<c:forEach var="publicSong" items="${requestScope.publicSongs}"
			varStatus="um">
			<tr>
				<td>${publicSong.songID }</td>
				<td>${publicSong.songName }</td>
				<td>${publicSong.artist }</td>
				<td>${publicSong.album }</td>
				<td>${publicSong.userID }</td>
			</tr>
		</c:forEach>

	</table>
	<c:if test="${requestScope.page>0 }">
		<a href="${requestScope.page-1 }">上一页</a>
	</c:if>
	共${requestScope.songCount }首歌，第${requestScope.page+1 }/${requestScope.allPage+1 }页
	<c:if test="${(requestScope.page)<requestScope.allPage }">
		<a href="${requestScope.page+1 }">下一页</a>
	</c:if>
	<p />
	<a href="/musicCenter/main">返回主菜单</a>
</body>
</html>