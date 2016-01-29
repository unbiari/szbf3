<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--<%@ include file="/common/inc/taglib.jsp"%> --%>

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
<%@ include file="/common/inc/commonJavascript.jsp"%>
<script src="${pageContext.request.contextPath}/common/js/cgvCommon.js"></script>
<%@ include file="/common/inc/autoLogin.jsp"%>
<!-- ********** Javascript 임포트 끝 ********** -->
<!-- ********** 사용자 정의 스크립트 시작 ********** -->
<script>
$(document).ready(function(){
	$.mobile.ajaxLinksEnabled=false;
	$.mobile.ajaxFomsEnabled=false;
	$.mobile.ajaxEnabled=false;
	
	
	$( "#pop_login" ).popup({
		afterclose: function( event, ui ) {
			// go_login();
        }
    });
});

function autoLoginSuccess(){
	
};

// type 0 = 빠른예매 1 = 영화정보 2 = 나의 예매 내역 3 = 회원가입 4 = 앱 다운
var go_read = function(type){
	var url = "";
	if(type == 0){
		if(window.localStorage){
			//로컬 스토리지 지원
			if(window.localStorage.getItem("localFolg")){
				//지역정보를 가지고 있음
				$("#cinemaId").val(window.localStorage.getItem("sCinemaId"));
				$("#cinemaNm").val(window.localStorage.getItem("sCinemaNm"));
				//url = "/cplanall.do?cinemaId="+sCinId;
				url = "/cinfo.do";
			}else{
				//지역정보를 가지고 있지 않음
				url = "/cinfo.do";
			}
		}else{
			//로컬 스토리지 지원 안함
		}
		//빠른예매
	}else if(type == 1){
		//영화정보
		url = "/movieInfo.do";
	}else if(type == 2){
		if(loginFlag == "T"){
			//로그인시 내역페이지
			url = "/myPage.do";
		}else{
			//미로그인시 로그인
			$( "#pop_login" ).popup( "open" );
			return;
		}
	}else if(type == 3){
		url = "/insertMem.do";
		$("#nextPagInfo").val("/");
	}else if(type == 4){
		url = "/app.do";
	}else if(type == 5){
		//로그아웃
		$( "#popup_logout" ).popup( "open" );
		return;
	}
	//다음페이지 URL
	$("#frmList").attr("action", url);
    $("#frmList").submit();
};

function go_login() {
	/* $("#nextPagInfo").val("/myPage.do");
	//로그인으로 이동
	$("#frmList").attr("action", "/login.do");
    $("#frmList").submit(); */
    
    location.replace("/login.do?nextPagInfo=/myPage.do");
}

function go_logout(){
	//로그아웃
	window.localStorage.removeItem("autoLogin");
	window.localStorage.removeItem("userId");
	window.localStorage.removeItem("userPass");
	//로그이웃
	location.replace("/logout.do");
}

</script>
<!-- ********** 사용자 정의 스크립트 끝 ********** -->
<!-- ********** Javascript 영역 끝 ********** -->
</head>
<body>
<form action="" method="post" id="frmList" >
<input type="hidden" id="cinemaId" name="cinemaId" value="">
<input type="hidden" id="cinemaNm" name="cinemaNm" value="">
<input type="hidden" id="nextPagInfo" name="nextPagInfo" value="">
</form>
<div data-role="page" data-theme="x"> <!-- data-role="page" Start -->

<section data-role="content">
<!-- ---------------------------- data-role="content" Start ---------------------------- -->

	<!-- 인덱스 -->
	<article class="wrap_type1">

		<img src="${pageContext.request.contextPath}/images/bg_index.jpg" src="cgv" class="mainvisual" />
		<section class="indexwrap">

			<div class="idx_cont">
				<div class="t_quick">
					<div class="lft"><a href="javascript:go_read(0);"><img src="${pageContext.request.contextPath}/images/main1.gif" alt="快速购票" /></a></div>
					<div class="rgt"><a href="javascript:go_read(1);"><img src="${pageContext.request.contextPath}/images/main2.gif" alt="影片信息" /></a></div>
				</div>
				<div class="m_quick">
					<div class="lft"><a href="javascript:go_read(2);"><img src="${pageContext.request.contextPath}/images/main3.gif" alt="我的CGV" /></a></div>
					<div class="rgt">
						<div class="tp"><a href="javascript:go_read(3);"><img src="${pageContext.request.contextPath}/images/main4.gif" alt="注册" /></a></div>
						<div class="btm"><a href="javascript:go_read(4);"><img src="${pageContext.request.contextPath}/images/main5.gif" alt="APP下载" /></a></div>
					</div>
				</div>
				<div class="b_banner">
					<img src="${pageContext.request.contextPath}/images/4.jpg" alt="" />
				</div>
			</div>
		</section>
		
	</article>

	<!-- 샘플팝업 -->
	<div data-role="popup" id="pop_login" data-overlay-theme="b" data-theme="z" data-dismissible="false" data-close-btn="right" style="max-width:400px;">
    <div role="main" class="ui-content">
			<p>请先登录</p>
			<div class="ui-grid-a">
				<!-- <div class="ui-block-a"><a href="#" data-role="button" data-theme="a" data-shadow="false" data-inline="true" data-rel="back">确定</a></div> -->
				<div class="ui-block-a"><a href="javascript:go_login();" data-role="button" data-theme="a" data-shadow="false" data-inline="true">确认</a></div>
				<div class="ui-block-b"><a href="#" data-role="button" data-theme="b" data-shadow="false" data-inline="true" data-rel="back">取消</a></div>
			</div>
    </div>
	</div>

<!-- ---------------------------- data-role="content" End ---------------------------- -->
</section>
</div><!-- data-role="page" End -->
</body>
</html>
<%-- ******************** HTML 코드 끝******************** --%>