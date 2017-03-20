<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>添加音乐</title>

<script>
	var totalFileLength, totalUploaded, fileCount, filesUploaded;
	function debug(s) {
		var debug = document.getElementById('debug');
		if (debug) {
			debug.innerHTML = debug.innerHTML + '<br/>' + s;
		}
	}
	function onUploadComplete(e) {
		totalUploaded += document.getElementById('files').files[filesUploaded].size;
		filesUploaded++;
		debug('complete ' + filesUploaded + " of " + fileCount);
		debug('totalUploaded:' + totalUploaded);
		if (filesUploaded < fileCount) {
			uploadNext();
		} else {
			var bar = document.getElementById('bar');
			bar.style.width = '100%';
			bar.innerHTML = '100% complete';
			alert('Finished uploading file(s)');
		}
	}

	function onFileSelect(e) {
		var files = e.target.files;
		var output = [];
		fileCount = files.length;
		totalFileLength = 0;
		for ( var i = 0; i < fileCount; i++) {
			var file = files[i];
			output.push(file.name, ' (', Math.round(parseFloat(file.size/1024/1024)*1000)/1000, ' MB,',
					file.lastModifiedDate.toLocaleDateString(), ')');
			output.push('<br/>');
			debug('add ' + file.size);
			totalFileLength += file.size;
		}
		document.getElementById('selectedFiles').innerHTML = output.join('');
		debug('totalFileLength:' + totalFileLength);
	}

	function onUploadProgress(e) {
		if (e.lengthComputable) {
			var percentComplete = parseInt((e.loaded + totalUploaded) * 100
					/ totalFileLength);
			var bar = document.getElementById('bar');
			bar.style.width = percentComplete + '%';
			bar.innerHTML = percentComplete + '% complete';
		} else {
			debug('unable to compute');
		}
	}

	function onUploadFailed(e) {
		alert("Error uploading file");
	}

	function uploadNext() {
		var xhr = new XMLHttpRequest();
		var fd = new FormData();
		var file = document.getElementById('files').files[filesUploaded];
		fd.append("multipartFile", file);
		xhr.upload.addEventListener("progress", onUploadProgress, false);
		xhr.addEventListener("load", onUploadComplete, false);
		xhr.addEventListener("error", onUploadFailed, false);
		xhr.open("POST", "file_upload");
		debug('uploading ' + file.name);
		xhr.send(fd);
	}

	function startUpload() {
		totalUploaded = filesUploaded = 0;
		uploadNext();
	}
	window.onload = function() {
		document.getElementById('files').addEventListener('change',
				onFileSelect, false);
		document.getElementById('uploadButton').addEventListener('click',
				startUpload, false);
	}
</script>
</head>
<body>
<h1>批量添加音乐</h1>
<div id="progressBar" style="height: 20px;border: 2px solid green;">
	<div id="bar" style="height: 100%;background: #33dd33;width: 0%">
	
	</div>
</div>
<form>
	<input type="file" id="files" multiple/>
	<br/>
	<output id="selectedFiles"></output>
	<input id="uploadButton" type="button" value="Upload"/>
</form>
<div id="debug" style="height: 100px;border: 2px solid green;overflow:auto;">
</div>
<a href="main">返回主菜单</a>
</body>
</html>