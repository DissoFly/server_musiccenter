<%@ page contentType="text/html; charset=UTF-8"%>
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
<title>添加歌曲清单</title>
</head>
<body>
	<h2>添加歌曲清单</h2>
	<form action="add_list" method="post" enctype="multipart/form-data">
		<fieldset>
			<legend>添加信息</legend>
			<p>
				<label for="listName">歌单名称：</label> <input type="text" id="listName"
					name="listName">
			</p>
			<p>
				<label for="listAbout">歌单简介：</label> <input type="text"
					id="listAbout" name="listAbout">
			</p>
			<p>
				<label for="srcPath">歌单封面：</label> <input type="file" id="srcPath"
					name="srcPath">
			</p>
			<p>
				<label for="musicList">歌单列表：</label> <input type="text"
					id="musicList" name="musicList"> *输入歌曲编号，用英文符号";"隔开<input
					type="button" onClick="openWin('AllMusic/0',800,600)"
					value="查看所有歌曲">
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
				${message}<br/>
				</c:forEach>
			
		</div>
	</c:if>

</body>
</html>