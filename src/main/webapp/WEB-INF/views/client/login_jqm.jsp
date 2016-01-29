<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/inc/commonTaglib.jsp"%>

<!DOCTYPE html>
<html lang="cn">
<head>

<meta charset="utf-8">
<meta content="initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0"
	name="viewport">
<meta name="format-detection" content="telephone=no">

<title>CGV China Mobile Web</title>

<%@ include file="/common/inc/commonJavascript.jsp"%>
<script src="${pageContext.request.contextPath}/common/js/cgvCommon.js"></script>
<%@ include file="/common/inc/autoLogin.jsp"%>

<script>
	$(document).ready(function(){
		
		$.mobile.ajaxLinksEnabled=false;
		$.mobile.ajaxFomsEnabled=false;
		$.mobile.ajaxEnabled=false;
		
		//1일 3회 예매 재한
		var oIdDate = window.localStorage.getItem("oIdDate");
		var newDate = new Date();
		var toDay = newDate.getFullYear() + "-" + newDate.getMonth()+1 + "-" + newDate.getDate();
		if(oIdDate == toDay){
			var oIdList = window.localStorage.getItem("oIdList");
			$("#oIdList").val(oIdList);
		}
		
		//로그인 되있는 상태에는 메인으로 이동
		if(loginFlag == "T"){
			var nextPagInfo = $("#nextPagInfo").val();
			if(nextPagInfo){
				$("#frmList").attr("action", nextPagInfo);
			}else{
				$("#frmList").attr("action", "/");
			}
			
		    $("#frmList").submit();
		}
		
		//포커스 이동
		$( "#pop_idNull" ).popup({
			afterclose: function( event, ui ) {
				$("#userId").focus();
	        }
	    });
		
		$( "#pop_passNull" ).popup({
			afterclose: function( event, ui ) {
				$("#userPass").focus();
	        }
	    });
	});

	function autoLoginSuccess(){
		var url = $("#nextPagInfo").val();
		if(!url){
			url = "/";
		}
		location.replace(url);
	};

	var go_nextView = function(){
		var userId = $("#userId").val();
		var userPass = $("#userPass").val();
		var country = $("#country").val();
		if(!userId){
			//아이디 미입력
			$( "#pop_idNull").popup( "open" );
			return;
		}else if(!userPass){
			//비밀번호 미입력
			$( "#pop_passNull" ).popup( "open" );
			return;
		}
		
		$.ajax({
			type : "POST",
			async : true,
			url : "<c:url value='/login.do'/>",
			dataType : "json",
			timeout : 30000,
			cache : false,
			data : {'userId':$("#userId").val(),
				'userPass':$("#userPass").val(),
			    'country':$("#country").val()},
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",

			error : function(request, status, error) {
				//통신 에러 발생시 처리
				$( "#pop_server_error" ).popup( "open" );
			},

			success : function(data) {
				if("exception" == data.result){
					$( "#pop_server_error" ).popup( "open" );
					return;
				}
				
				var successParam = data.successParam;
				
				//문자전송
				if("F" == successParam){
					//로그인 실패
					$( "#pop_login_error" ).popup( "open" );
				}else if("L" == successParam){
					//다음페이지 URL
					var url = $("#nextPagInfo").val();
					location.replace(url);
				}else{
					//성공시
					if($("input:checkbox[id='autologin']").is(":checked")){
						//자동 로그인 시 로컬 스토리지에 회원정보 저장
						if(window.localStorage){
							window.localStorage.setItem("autoLogin", "T");
							window.localStorage.setItem("userId", $("#userId").val());
							window.localStorage.setItem("userPass", $("#userPass").val());
						}
					}
					//다음페이지 URL
					var url = $("#nextPagInfo").val();
					location.replace(url);
				}
			}
		});
	};

	var go_insertMem = function(){
		$("#frmList").attr("action", "/insert_mbr.do");
	    $("#frmList").submit(); 
	};

	function go_pwdSearch() {
		$("#frmList").attr("action", "/pwdSearch.do");
	    $("#frmList").submit();
	}
	</script>

</head>

<body>
	<div data-role="page" data-theme="z">
		<!-- data-role="page" Start -->

		<section data-role="content">
			<!-- ---------------------------- data-role="content" Start ---------------------------- -->

			<!-- 로그인 -->
			<article class="wrap_type1">

				<hgroup data-role="header" data-theme="b" class="page-title">
					<h2>登陆</h2>
					<a href="#" data-corners="false" data-role="button"
						data-inline="true" data-rel="back" data-button="back"
						data-icon="carat-l" data-iconpos="notext" data-shadow="false"
						data-theme="v">이전</a>
					<a rel="external" href="/" data-corners="false" data-role="button"
						data-inline="true" data-button="base" data-icon="home"
						data-iconpos="notext" data-shadow="false" data-theme="v">Home</a>
				</hgroup>

				<form action="" method="post" id="frmList">
					<input type="hidden" value="" name="cinemaNm" id="cinemaNm">
					<input type="hidden" name="oIdList" id="oIdList"> <input
						type="hidden" value="${nextPagInfo }" name="nextPagInfo"
						id="nextPagInfo" />
					<fieldset data-role="controlgroup" data-inset="false"
						data-corners="false" data-theme="a">
						<div class="ui-field-contain">
							<label for="" class="ui-theme-a">用 户</label> <input type="text"
								name="userId" id="userId" data-theme="z" data-corners="false"
								data-shadow="false" placeholder="Email/昵称/手机号码" />
						</div>
						<div class="ui-field-contain">
							<label for="" class="ui-theme-a">密 码</label> <input
								type="password" name="userPass" id="userPass" data-theme="z"
								data-corners="false" data-shadow="false" placeholder="密码" />
						</div>
					</fieldset>
					<fieldset data-role="controlgroup" data-inset="false"
						data-corners="false" data-theme="b">
						<div class="ui-field-contain btncombine">
							<input type="checkbox" value="T" checked="checked"
								name="autologin" id="autologin" data-theme="r"> <label
								for="autologin">记住用户名</label>
							<div class="ui-grid-a">
								<div class="ui-block-a">
									<a href="javascript:go_insertMem();" data-role="button"
										data-theme="c" data-shadow="false" data-mini="true">注册</a>
								</div>
								<div class="ui-block-b">
									<a href="javascript:go_pwdSearch();" data-role="button"
										data-theme="c" data-shadow="false" data-mini="true">找回密码</a>
								</div>
							</div>
						</div>
					</fieldset>
					<fieldset class="btnwrap btmpos">
						<input type="button" onclick="go_nextView();" data-role="button"
							data-theme="a" data-shadow="false" data-corners="true" value="确定">
					</fieldset>
				</form>

				<p class="customtext1" style="padding-top: 0.5em;">
					申请会员也可登陆 www.cgv.com.cn 注册
					<!-- <br/> -->
					<!-- <a href="javascript:go_insertMem();" data-role="button" data-theme="c" data-shadow="false" data-inline="true" style="width:56%;">注册</a> -->
				</p>

			</article>

			<!-- 로그인에러 팝업 -->
			<div data-role="popup" id="pop_login_error" data-overlay-theme="b"
				data-theme="z" data-dismissible="false" data-close-btn="right"
				style="max-width: 400px;">
				<div role="main" class="ui-content">
					<p>用户名或者密码错误</p>
					<a href="#" data-role="button" data-theme="a" data-shadow="false"
						data-inline="true" data-rel="back">关闭</a>
				</div>
			</div>

			<!-- 통신 에러 팝업 -->
			<div data-role="popup" id="pop_server_error" data-overlay-theme="b"
				data-theme="z" data-dismissible="false" data-close-btn="right"
				style="max-width: 400px;">
				<div role="main" class="ui-content">
					<p>网络异常,请检查网络设置</p>
					<a href="#" data-role="button" data-theme="a" data-shadow="false"
						data-inline="true" data-rel="back">关闭</a>
				</div>
			</div>

			<!-- 아이디 null -->
			<div data-role="popup" id="pop_idNull" data-overlay-theme="b"
				data-theme="z" data-dismissible="false" data-close-btn="right"
				style="max-width: 400px;">
				<div role="main" class="ui-content">
					<p>请输入登录账户</p>
					<a href="#" data-role="button" data-theme="a" data-shadow="false"
						data-inline="true" data-rel="back">关闭</a>
				</div>
			</div>

			<!-- pass null -->
			<div data-role="popup" id="pop_passNull" data-overlay-theme="b"
				data-theme="z" data-dismissible="false" data-close-btn="right"
				style="max-width: 400px;">
				<div role="main" class="ui-content">
					<p>请输入密码</p>
					<a href="#" data-role="button" data-theme="a" data-shadow="false"
						data-inline="true" data-rel="back">关闭</a>
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

	</div>
	<!-- data-role="page" End -->
</body>
</html>
