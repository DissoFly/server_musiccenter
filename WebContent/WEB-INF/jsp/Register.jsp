<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册账号</title>
</head>
<body>
	<h2>注册账号</h2>
	<form action="register_admin" method="post"
		enctype="multipart/form-data">
		<fieldset>
			<legend>注册账号</legend>
			<p>
				<label for="account">用户名：</label> <input type="text" id="account"
					name="account">
			</p>
			<p>
				<label for="password">密码：</label> <input type="password"
					id="password" name="password">
			</p>
			<p>
				<label for="email">电子邮箱：</label> <input type="text" id="email"
					name="email">
			</p>
			<p>
				<label for="isAdmin">管理员：</label>
				<input type="radio" id="isAdmin"
					name="isAdmin" value="false" checked="checked">否 
					<input type="radio" id="isAdmin"
					name="isAdmin" value="true">是  
			</p>
			<p>
				<label for="phoneNumber">手机号码：</label> <input type="text"
					onkeyup="this.value=this.value.replace(/\D/g, '')" id="phoneNumber"
					name="phoneNumber">
			</p>
			<p>
				<label for="avatar"> 头像：</label> <input type="file" id="avatar"
					name="avatar">
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