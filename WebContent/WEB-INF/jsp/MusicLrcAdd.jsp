<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script>
	function openWin(url, width, height) {
		var phxWin = window.open(url, '', 'width=' + width + ',height='
				+ height + ',left=' + (screen.width - width) / 2 + ',top='
				+ (screen.height - height) / 2 + '');
	}
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加歌词</title>
</head>
<body>
	<h2>添加歌词</h2>
	<form action="music_lrc_add" method="post"
		enctype="multipart/form-data">
		<fieldset>
			<legend>添加信息</legend>
			<p>
				<label for="songId">歌曲编号：</label> <input type="text" id="songId"
					name="songId"><input type="button"
					onClick="openWin('allMusic/0',800,600)" value="查看所有歌曲">
			</p>
			<p>
				<label for="srcPath">添加歌词文件：</label> <input type="file" id="srcPath"
					name="srcPath">
			</p>
			<p>
				<input id="submit" type="submit" value="确定">
			</p>
		</fieldset>
	</form>
	<c:if test="${requestScope.messages !=null}">
	返回信息：
		<div style="color: red;">
			<c:forEach var="message" items="${requestScope.messages}">
				${message}<br />
			</c:forEach>

		</div>
	</c:if>
	<p/>
	<a href="/musicCenter/main">返回主菜单</a>
</body>
</html>