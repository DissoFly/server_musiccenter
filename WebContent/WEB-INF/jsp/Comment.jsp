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
<title>评论处理</title>
</head>
<body>
<h2>评论处理</h2>
	<form action="comment_unlook" method="post"
		enctype="multipart/form-data">
		<fieldset>
			<legend>评论屏蔽</legend>
			<p>
				<label for="ids">评论编号：</label> <input type="text" id="ids"
					name="ids">*输入评论编号，用英文符号";"隔开<input type="button"
					onClick="openWin('allComment/0',800,600)" value="查看所有评论">
			</p>
			<p>
				<label for="reason">屏蔽理由：</label> <input type="text" id="reason"
					name="reason">
			</p>
			<p>
				<input id="submit" type="submit" value="确定屏蔽">
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
	<p />
	<a href="/musicCenter/main">返回主菜单</a>
</body>
</html>