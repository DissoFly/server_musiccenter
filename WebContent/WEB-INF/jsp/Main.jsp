<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>main</title>
</head>
<body>
	<form action="logout">
	你好，${sessionScope.user.account}
		<button type="submit" onclick="return window.confirm('确定要退出登录吗？')">退出</button>
	</form>
	<p>
	音乐管理
	</p>
	<a href="musicAdd">添加音乐</a>
	<a href="musicConfirm">确认音乐信息</a>
	<p>
	推送管理
	</p>
	<a href="musicList/add">添加歌单</a>
</body>
</html>