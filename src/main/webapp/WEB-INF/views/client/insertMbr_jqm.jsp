<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/inc/commonTaglib.jsp"%>

<!DOCTYPE html>
<html lang="cn">
<head>

<meta charset="utf-8">
<meta content="initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0" name="viewport">
<meta name="format-detection" content="telephone=no">

<title>CGV</title>

<%@ include file="/common/inc/commonJavascript.jsp"%>
<script src="${pageContext.request.contextPath}/common/js/cgvCommon.js"></script>
<%@ include file="/common/inc/autoLogin.jsp"%>
	
<script>
$(document).ready(function(){
	$.mobile.ajaxLinksEnabled=false;
	$.mobile.ajaxFomsEnabled=false;
	$.mobile.ajaxEnabled=false;
	//숫자만 입력 로직
	$("input:text[datetimeOnly]").ForceNumericOnly();
});

function autoLoginSuccess(){
	
};

$(document).bind('pageinit', function() {
	setTimeout(function(){
	var resultFlag = "${resultFlag}";
		if(resultFlag == "F"){
			$( "#pop_server_error" ).popup( "open" );
			return;
		}
	}, 500);
});

var go_insert = function(){
	var mobileNo = $("#mobileNo").val();
	
	//휴대폰번호 null값 채크
	var mobileCk = /^[0-9]{11}$/; //11글자(숫자)
	if(!mobileCk.test($.trim(mobileNo))){
		$( "#pop_wrong_word" ).popup( "open" );
		$("#mobileNo").focus();
		return;
	}
	
	var passVal = $("#password").val();
	var pass_regx=/^[0-9a-zA-Z]{8,20}$/;

	if(!pass_regx.test(passVal)) {
		$( "#pop_pw_short" ).popup( "open" );
		$("#password").focus();
    	return;
    }
	
	var passCk = /^.*(?=.{8,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/; //8~20글자(숫자, 영어포함)
	if(!passCk.test(passVal)){
		$( "#pop_pw_short" ).popup( "open" );
		$("#password").focus();
		return;
	}
	
	var specialCk = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]"; //8~20글자(숫자, 영어포함)
					 
	var user_name = $("#username").val()
	if(user_name == ""){
		alert("请输入名字");
		return;
	}
	
	var countryCode = $("#countryCode").val()
	if(countryCode == "0"){
		alert("请选择影城");
		return;
	}
	
	//비밀번호와 비밀번호 확인이 다음
	if($.trim($("#password").val()) != $.trim($("#passwordRe").val())){
		$( "#pop_pw_error" ).popup( "open" );
		$("#passwordRe").focus();
		return;
	} 
	$("#frmList").attr("action", "/insertMemPro.do");
    $("#frmList").submit();
};

function init() {
	$("#mobileNo").val("");
	$("#memberId").val("");
	$("#password").val("");
	$("#passwordRe").val("");
	$("#email").val("");
}
</script>

</head>
<body>
<div data-role="page" data-theme="z"> <!-- data-role="page" Start -->

<section data-role="content">
<!-- ---------------------------- data-role="content" Start ---------------------------- -->

	<!-- 회원가입 -->
	<article class="wrap_type1">

		<hgroup data-role="header" data-theme="b" class="page-title">
			<h2>注册</h2>
			<a href="#" data-corners="false" data-role="button" data-inline="true" data-rel="back" data-button="back" data-icon="carat-l" data-iconpos="notext" data-shadow="false" data-theme="v">이전</a>
			<a rel="external" href="/" data-corners="false" data-role="button" data-inline="true" data-button="base" data-icon="home" data-iconpos="notext" data-shadow="false" data-theme="v">Home</a>
		</hgroup>

		<form action="" method="post" id="frmList" >
		<input type="hidden" value="${nextPagInfo }" id="nextPagInfo" name="nextPagInfo">
			<fieldset data-role="controlgroup" data-inset="false" data-corners="false" data-corners="false" data-theme="a">
				<div class="ui-field-contain">
					<label for="" class="ui-theme-a">选择国家</label>

					<select id="countryCode" name="countryCode" >
						<c:forEach var="item" items="${result}" varStatus="status" >
							 <option value ="${item[0]}" >${item[1]} : ${item[2]}</option>
						</c:forEach>					 
					</select>					
				</div>
				
				<div class="ui-field-contain">
					<label for="" class="ui-theme-a">手机号码(11)</label>
					<input type="text" name="mobileNo" datetimeOnly="true" id="mobileNo" data-theme="z" data-corners="false" data-shadow="false" placeholder="请输入手机号码" maxlength="11" />
				</div>
				<div class="ui-field-contain">
					<label for="" class="ui-theme-a">账户(~10)</label>
					<input type="text" name="memberId" id="memberId" data-theme="z" data-corners="false" data-shadow="false" placeholder="请输入账户" maxlength="10" />
				</div>
				
				<div class="ui-field-contain">
					<label for="" class="ui-theme-a">密 码</label>
					<input type="password" name="password" id="password" data-theme="z" data-corners="false" data-shadow="false" placeholder="8~20位字母和数字组成" maxlength="20" />
				</div>
				<div class="ui-field-contain">
					<label for="" class="ui-theme-a">确认密码</label>
					<input type="password" id="passwordRe" data-theme="z" data-corners="false" data-shadow="false" placeholder="请重新输入密码" maxlength="20" />
				</div>
				
				<div class="ui-field-contain">
					<label for="" class="ui-theme-a">"EMail(~30)"</label>
					<input type="text" name="email" id="email" data-theme="z" data-corners="false" data-shadow="false" placeholder="Email" maxlength="30" />
				</div>
				
			</fieldset>
			<fieldset class="btnwrap btmpos">
				<div class="ui-grid-a">
					<div class="ui-block-a"><input type="button" onclick="go_insert();" data-role="button" data-theme="a" data-shadow="false" data-corners="true" value="确定"></div>
				</div>
			</fieldset>
		</form>

	</article>

	<!-- 인증코드 인증 오류시 팝업 -->
	<div data-role="popup" id="pop_code_error" data-overlay-theme="b" data-theme="z" data-dismissible="false" data-close-btn="right" style="max-width:400px;">
    <div role="main" class="ui-content">
			<p>认证要求失败<br/>请短信查收您的认证码</p>
			<a href="#" data-role="button" data-theme="a" data-shadow="false" data-inline="true" data-rel="back">确定</a>
    </div>
	</div>

	<!-- 인증코드 인증 성공시 팝업 -->
	<div data-role="popup" id="pop_code_seccess" data-overlay-theme="b" data-theme="z" data-dismissible="false" data-close-btn="right" style="max-width:400px;">
    <div role="main" class="ui-content">
			<p>正确,您的手机<br/>号码已认证成功</p>
			<a href="#" data-role="button" data-theme="a" data-shadow="false" data-inline="true" data-rel="back">确定</a>
    </div>
	</div>

	<!-- 핸드폰 번호 영역에 다른 문자 입력시 팝업 -->
	<div data-role="popup" id="pop_wrong_word" data-overlay-theme="b" data-theme="z" data-dismissible="false" data-close-btn="right" style="max-width:400px;">
    <div role="main" class="ui-content">
			<p>请输入正确的手机号码</p>
			<a href="#" data-role="button" data-theme="a" data-shadow="false" data-inline="true" data-rel="back">确定</a>
    </div>
	</div>

	<!-- 이미 등록된 회원인 경우 팝업 -->
	<div data-role="popup" id="pop_already_member" data-overlay-theme="b" data-theme="z" data-dismissible="false" data-close-btn="right" style="max-width:400px;">
    <div role="main" class="ui-content">
			<p>此手机号码已被注册!</p>
			<a href="#" data-role="button" data-theme="a" data-shadow="false" data-inline="true" data-rel="back">确定</a>
    </div>
	</div>

	<!-- 비밀번호 오류 팝업 -->
	<div data-role="popup" id="pop_pw_error" data-overlay-theme="b" data-theme="z" data-dismissible="false" data-close-btn="right" style="max-width:400px;">
    <div role="main" class="ui-content">
			<p>输入不一致,请输入密码和重复密码</p>
			<a href="#" data-role="button" data-theme="a" data-shadow="false" data-inline="true" data-rel="back">确定</a>
    </div>
	</div>

	<!-- 비밀번호를 8자 이하로 입력시 팝업 -->
	<div data-role="popup" id="pop_pw_short" data-overlay-theme="b" data-theme="z" data-dismissible="false" data-close-btn="right" style="max-width:400px;">
    <div role="main" class="ui-content">
			<p>请输入8-20位数字和字母组合</p>
			<a href="#" data-role="button" data-theme="a" data-shadow="false" data-inline="true" data-rel="back">确定</a>
    </div>
	</div>
	
	<!-- 문자발송 성공 -->
	<div data-role="popup" id="pop_sms_seccess" data-overlay-theme="b" data-theme="z" data-dismissible="false" data-close-btn="right" style="max-width:400px;">
    <div role="main" class="ui-content">
			<p>验证码已发送</p>
			<a href="#" data-role="button" data-theme="a" data-shadow="false" data-inline="true" data-rel="back">确定</a>
    </div>
	</div>
	
	<!-- 문자발송 실패 -->
	<div data-role="popup" id="pop_sms_error" data-overlay-theme="b" data-theme="z" data-dismissible="false" data-close-btn="right" style="max-width:400px;">
    <div role="main" class="ui-content">
			<p>验证码发送失败,请重新发送</p>
			<a href="#" data-role="button" data-theme="a" data-shadow="false" data-inline="true" data-rel="back">确定</a>
    </div>
	</div>
	
	<!-- 인증번호검사 미실행 -->
	<div data-role="popup" id="pop_no_check" data-overlay-theme="b" data-theme="z" data-dismissible="false" data-close-btn="right" style="max-width:400px;">
    <div role="main" class="ui-content">
			<p>请确认输入认证码</p>
			<a href="#" data-role="button" data-theme="a" data-shadow="false" data-inline="true" data-rel="back">确定</a>
    </div>
	</div>
	
	<!-- 통신 에러 팝업 -->
	<div data-role="popup" id="pop_server_error" data-overlay-theme="b" data-theme="z" data-dismissible="false" data-close-btn="right" style="max-width:400px;">
    <div role="main" class="ui-content">
			<p>网络异常,请检查网络设置</p>
			<a href="#" data-role="button" data-theme="a" data-shadow="false" data-inline="true" data-rel="back">关闭</a>
    </div>
	</div>

<!-- ---------------------------- data-role="content" End ---------------------------- -->
</section>

<!-- ---------------------------- data-role="footer" End ---------------------------- 
<footer data-role="footer" data-tap-toggle="false" >
	<nav data-role="navbar" data-menu="footer">
		<ul>
			<li><a rel="external" data-role="button" data-icon="footer_m_01" href="../commonService/total_menu.html"  data-icon="menu">전체메뉴</a></li>
			<li><a rel="external" data-role="button" data-icon="footer_m_02" id="btnStartCertManager" onclick="onButtonClickListener(this.id, null)">인증센터</a></li>
			<li><a rel="external" data-role="button" data-icon="footer_m_03" href="../index.html">HOME</a></li>
			<li><a rel="external" data-role="button" data-icon="footer_m_04" href="tel:1688-8114">고객센터</a></li>
			<li>
				<a rel="external" data-role="button" data-icon="footer_m_05" id="btnLogin" onclick="onButtonClickListener(this.id, null)">로그인</a>
				<!--a rel="external" data-role="button" data-icon="footer_m_06" id="btnLogout">로그아웃</a>
			</li>
		</ul>
	</nav>
</footer>
<!-- ---------------------------- data-role="footer" End ---------------------------- -->

</div><!-- data-role="page" End -->
</body>
</html>
