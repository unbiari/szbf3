<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/inc/commonTaglib.jsp"%>

<!DOCTYPE html>
<html lang="cn">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script>
	var go_event_detail = function() {
		window.location.assign( "${pageContext.request.contextPath}/eventDetail.do" );
	};

	var go_event_match = function() {
		window.location.assign( "${pageContext.request.contextPath}/eventMatch.do" );
	};

	var go_match = function() {
		window.location.assign( "${pageContext.request.contextPath}/client.do?blind_match&seqNum=" + "${seqNum}" );
	};
	
	var go_buddy = function() {
		window.location.assign( "${pageContext.request.contextPath}/buddy.do" );
	};
	
	var go_help = function() {
		window.location.assign( "${pageContext.request.contextPath}/help.do" );
	};
</script>

</head>
<body>
	<h2><spring:message code="login.title" /></h2>

	<div>
		<input type="button" onclick="go_event_detail();" value="이벤트 내용">
	</div>
	<br>
	<div>
		<input type="button" onclick="go_event_match();" value="이벤트 매치">
	</div>
	<br>
	<div>
		<input type="button" onclick="go_match();" value="블라인드 매치">
	</div>
	<br>
	<div>
		<input type="button" onclick="go_buddy();" value="버디">
	</div>
	<br>
	<div>
		<input type="button" onclick="go_help();" value="설명">
	</div>
</body>
</html>
