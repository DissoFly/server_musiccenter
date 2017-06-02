<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>登陆MusicCenter</title>
</head>
<body>
	<p align="center">欢迎登陆MusicCenter</p>
	<form action="logining" method="post">
		<table>
			<p align="center">
				<label for="account">用户名：</label> <input type="text" id="account"
					name="account" tabindex="1">
			</p>
			<p align="center">
				<label for="password">密 码：</label> <input type="password"
					id="password" name="password" tabindex="2">
			</p>
			<p align="center">
				<input id="submit" type="submit" tabindex="3" value="确定">
			</p>
		</table>
	</form>
	<c:if test="${requestScope.errors !=null}">
		<p id="errors">
			Error(s)!
			<ul>
			<c:forEach var="error" items="${requestScope.errors}">
				<li>${error}</li>
			</c:forEach>
			</ul>
		</p>
	</c:if>
</body>
</html>