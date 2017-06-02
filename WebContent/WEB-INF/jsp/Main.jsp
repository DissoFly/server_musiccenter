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
	<a href="musicConfirm/0">确认音乐信息</a>
	<a href="musicLrcAdd">添加歌词</a>
	<a href="allMusic/0">查看所有歌曲</a>
	<p>
	推送管理
	</p>
	<a href="musicList/add">添加歌单</a>
	<a href="musicNewsAdd">添加新闻资讯</a>
	<p>
	评论管理
	</p>
	<a href="allComment/0">查看所有评论</a>
	<a href="comment">屏蔽评论</a>
	<p>
	账号管理
	</p>
	<a href="register">注册账号</a>
	<a href="freezeUser">冻结账号</a>
	<a href="unfreezeUser">解冻账号</a>
	<a href="allUser/0">查看所有账号</a>
	<p>
	日志信息
	</p>
	<a href="allLogs/0">查看日志</a>
	
</body>
</html>