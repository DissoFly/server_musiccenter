<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>确认音乐信息</title>

</head>
<script>
	function openWin(url, width, height) {
		var phxWin = window.open(url, '', 'width=' + width + ',height='
				+ height + ',left=' + (screen.width - width) / 2 + ',top='
				+ (screen.height - height) / 2 + '');
	}
</script>
<body>
	<p>
		<c:if test="${requestScope.message !=null}">
			<h4>返回信息：</h4><h4 style="color: red;">${requestScope.message}</h4>
		</c:if>
	</p>
	<c:if
		test="${requestScope.uploadMessages !=null&&fn:length(requestScope.uploadMessages)>0}">
		<p id="uploadMessages">歌曲确认
		<ul>
			<c:forEach var="uploadMessage" items="${requestScope.uploadMessages}"
				varStatus="um">

				<li>确认编号：${uploadMessage.fileID}</li>
				<li>文件名：${uploadMessage.fileName}</li>
				<li>上传人： <c:forEach var="user" items="${requestScope.users}">
						<c:if test="${uploadMessage.userID ==user.userId}">
							${user.account}
							<c:out value=""></c:out>
						</c:if>
					</c:forEach>
				</li>
				<input type="button"
					onClick="openWin('listen/${uploadMessage.fileID}',400,300)"
					value="试听">
				<form action="music_confirm">
					<input type="hidden" name="page" value="${requestScope.page}" />
					<input type="hidden" name="fileId" value="${uploadMessage.fileID}" />
					<p>
						<label for="songName">歌曲名：</label> <input type="text"
							value="${requestScope.musicMessages[um.index].songName}"
							id="songName" name="songName" tabindex="1">
					</p>
					<p>
						<label for="artist">歌手名：</label> <input type="text" id="artist"
							value="${requestScope.musicMessages[um.index].artist}"
							name="artist" tabindex="2">
					</p>
					<p>
						<label for="album">专辑名：</label> <input type="text" id="album"
							value="${requestScope.musicMessages[um.index].album}"
							name="album" tabindex="3"> <input id="submit"
							type="submit" tabindex="4" value="确定">
					</p>
				</form>
			</c:forEach>
		</ul>
		</p>
		<c:if test="${requestScope.page>0 }">
			<a href="${requestScope.page-1 }">上一页</a>
		</c:if>
	共${requestScope.confirmCount }条待确认信息，第${requestScope.page+1
	}/${requestScope.allPage+1 }页
	<c:if test="${(requestScope.page+1)<=requestScope.allPage }">
			<a href="${requestScope.page+1 }">下一页</a>
		</c:if>
	</c:if>
	<c:if
		test="${requestScope.uploadMessages ==null||fn:length(requestScope.uploadMessages)==0}">
		<p>暂没有音乐待确认</p>
	</c:if>
	<p/>
	<a href="/musicCenter/main">返回主菜单</a>
</body>
</html>