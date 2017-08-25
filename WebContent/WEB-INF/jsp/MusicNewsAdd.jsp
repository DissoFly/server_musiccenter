<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加资讯</title>
</head>
<body>
	<h2>添加资讯</h2>
	<form action="music_news_add" method="post"
		enctype="multipart/form-data">
		<fieldset>
			<legend>添加信息</legend>
			<p>
				<label for="title">资讯标题：</label> <input type="text" id="title"
					name="title">
			</p>
			<p>
				<label for="srcPath">资讯封面：</label> <input type="file" id="srcPath"
					name="srcPath">
			</p>
			<p>
				<label for="text">资讯正文：</label> <input type="text" id="text"
					name="text">
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