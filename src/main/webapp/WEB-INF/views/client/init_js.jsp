<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/inc/commonTaglib.jsp"%>

<!DOCTYPE html>
<html lang="cn">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CGV</title>

<script src="${pageContext.request.contextPath}/common/js/szbf.js"></script>

<script>
window.onload = function(){
	var width = document.getElementById("board").width;
	var height = document.getElementById("board").height;

	console.log( width + "," + height);
	
	if( "${seqNum}" == 0 )
		alert( "error" );
	else
		Start_Init("stage", width, height, "${seqNum}"); // stage id
};
</script>

</head>

<body>

<h1>
	Hello CGV!!
</h1>

	<div id="stage" style="position:relative" >
		<img id="board" src="${pageContext.request.contextPath}/images/client/board.png" alt="Board" />
	</div>

</body>

</html>
