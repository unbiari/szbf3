<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>

<%-- ******************** HTML 코드 시작 ******************** --%>
<!DOCTYPE html>
<html lang="cn">
<head>

	<meta charset="utf-8">
	<meta content="initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0" name="viewport">
	<meta name="format-detection" content="telephone=no">
	
	<title>CGV</title>
	
	<!-- ********** Javascript 영역 시작 ********** -->
	<!-- ********** Javascript 임포트 시작 ********** -->
	<%-- <%@ include file="/common/inc/commonJavascript.jsp"%> --%>
	<!-- ********** Javascript 임포트 끝 ********** -->
	<!-- ********** 사용자 정의 스크립트 시작 ********** -->
	
	<style>
		div.my1 {
			margin-left:-20px;
			margin-top:-20px;
			width:40px;
			height:40px;
			cursor:pointer;
		}
	</style>
	
	<script src="/common/js/szbf.js"></script>
	
	<script>
	window.onload = function(){
		var width = document.getElementById("board").width;
		var height = document.getElementById("board").height;
	
		console.log( width + "," + height);
	
		Start_Init("stage", width, height, "${userId}"); // stage id
	};
	</script>

</head>

<body>

<h1>
	Hello CGV!!
</h1>

	<div id="stage" style="position:relative" >
		<img id="board" src="${pageContext.request.contextPath}/images/client/board.png" alt="Board" />

<!--
		<div id="my1" class="my1" style="position:absolute; left:0px; top:0px; " >
			<img id="img_my1" width="40px" height="40px" src="client/enemy.png" />
		</div>
-->

	</div>

</body>

</html>
