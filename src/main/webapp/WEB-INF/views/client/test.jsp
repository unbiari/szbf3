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
	
	<script src="/common/js/jquery-1.11.0.min.js"></script>
	<script src="/common/js/szbf.js"></script>
	
	<script>
	window.onload = function(){
		var width = document.getElementById("board").width;
		var height = document.getElementById("board").height;
	
		console.log( width + "," + height);
		
		//$("#board").animate( {top:"100", left:"100"}, 10000, "linear", function() { alert("completed"); });
/* 		$("#board").animate( {top:"200", left:"150"}, { queue:false, duration:10000, step: function( now, fx){
			//$("#board").css("opacity", "0");
			console.log( "now = " + now);
			console.log( "fx = " + fx + ",fx.prop = " + fx.prop);
		} }, "linear", function() { alert("completed"); });
 */		//$("#board").toggle( 1000, function() { alert("end"); });
		//$("#board").hide( 1000, function() { alert("end"); });

		var moveX = -(1448 - $(document).width());

		var target = $("#board");

		function play()

		{

			target.attr({"style":""});

			target.unbind("transitionend webkitTransitionEnd oTransitionEnd MSTransitionEnd");

			target.css({"transition":"all 5s","transform":"translate("+moveX+"px, 200px)"}).bind("transitionend webkitTransitionEnd oTransitionEnd MSTransitionEnd", function(e){

			revers();

			});

		}
		
		document.getElementById("board").addEventListener("transitionend", updateTransition, true);
		function updateTransition() {
			alert( "end" );
		};
		
		function revers()
		{

			target.attr({"style":""});

			target.unbind("transitionend webkitTransitionEnd oTransitionEnd MSTransitionEnd");

			target.css({"transition":"all 5s","transform":"translate(0, 0)"}).bind("transitionend webkitTransitionEnd oTransitionEnd MSTransitionEnd", function(e){

			//play();

			});

		}

		play();
	};
	
	var toggle_two = function(obj_1, obj_2) {
		//obj_1.
		
	};
	</script>

</head>

<body>

<h1>
	Hello CGV!!
</h1>

	<div id="stage" style="position:relative" >
		<img id="board" src="${pageContext.request.contextPath}/images/app.png" alt="Board" style="position:relative" />

<!--
		<div id="my1" class="my1" style="position:absolute; left:0px; top:0px; " >
			<img id="img_my1" width="40px" height="40px" src="client/enemy.png" />
		</div>
-->

	</div>

</body>

</html>
