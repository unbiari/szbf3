<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/inc/commonTaglib.jsp"%>

<!DOCTYPE html>
<html lang="cn">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script src="${pageContext.request.contextPath}/common/js/csrCommon.js"></script>

<script>
	// ajax...	
	var callback = function() {
		if (Csr_Xhr.readyState == 4) {
			if (Csr_Xhr.status == 200) {
				var data = Csr_Xhr.responseText;
				var obj = eval("(" + data + ")");
				console.log( obj["RT"] );
				console.log( obj["msg"] );
				if(obj["RT"] == 0) {
					window.localStorage.setItem("SZBFuserId", document.getElementById("userId").value);
					window.localStorage.setItem("SZBFuserPass", document.getElementById("userPass").value);
					
					window.location.assign( "${pageContext.request.contextPath}/clientMain.do?seqNum=" + obj["seqNum"] ); // 로그인 성공한 경우...
				}
				else {
					alert("用户名或者密码错误:" + obj["msg"]);
				}
			}
			else {
				alert("网络异常,请检查网络设置");
			}
		}	
	};

	var go_login = function() {
		var userId = document.getElementById("userId").value;
		var userPass = document.getElementById("userPass").value;
		
		if(!userId || userId==""){
			//아이디 미입력
			alert("请输入登录账户");
			document.getElementById("userId").focus();
			return;
		}else if(!userPass || userPass==""){
			//비밀번호 미입력
			alert("请输入密码");
			document.getElementById("userPass").focus();
			return;
		}
		
		var url = "${pageContext.request.contextPath}/login.do?userId=" + userId + "&userPass=" + userPass;
		Csr_ajax_get( url, callback );
	};
	
	var go_pwdSearch = function() {
		window.location.assign( "${pageContext.request.contextPath}/pwdSearch.do" );
	};
	
	var go_insertMbr = function() {
		window.location.assign( "${pageContext.request.contextPath}/insertMbr.do" );
	};
	
	var go_auto_login = function() {
		var user_id = window.localStorage.getItem("SZBFuserId");
		var user_pass = window.localStorage.getItem("SZBFuserPass");
		
		if( !user_id || user_id=="" || !user_pass || user_pass=="" ) {
			alert("No saved data");
			return;
		}
		document.getElementById("userId").value = user_id;
		document.getElementById("userPass").value = user_pass;
		
		go_login();
	};
</script>

</head>
<body>
	<h2><spring:message code="login.title" /></h2>

	<form>
		<fieldset>
			<legend>THANK YOU!</legend>
			<div>
				<label>用 户</label> <input type="text" id="userId" placeholder="昵称" maxlength=10 />
			</div>
			<div>
				<label>密 码</label> <input type="password" id="userPass" placeholder="密码" maxlength=20 />
			</div>
			<br>
			<input type="button" onclick="go_login();" value="确定">
		</fieldset>
		<fieldset>
			<input type="button" onclick="go_auto_login();" value="Auto Login">
		</fieldset>
		<fieldset>
			<span>
				<a href="javascript:go_insertMbr();">注册</a>
			</span>
			<span>
				<a href="javascript:go_pwdSearch();">找回密码</a>
			</span>
		</fieldset>
		<fieldset>
			<legend>Choose Language</legend>
			<span>
				<a href="<c:url value='loginPre.do?locale=en_US'/>" >English</a>
			</span>
			<span>
				<a href="<c:url value='loginPre.do?locale=ko_KR'/>" >Korean</a>
			</span>
			<span>
				<a href="<c:url value='loginPre.do?locale=cn_ZH'/>" >Chinese</a>
			</span>
		</fieldset>
	</form>
</body>
</html>
