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
					window.location.assign( "${pageContext.request.contextPath}/clientMain.do?seqNum=" + obj["seqNum"] ); // 로그인 성공한 경우...
				}
				else
					alert("用户名或者密码错误:" + obj["msg"]);
			}
			else {
				alert("网络异常,请检查网络设置");
			}
		}	
	};

	var go_login = function() {
		window.location.assign( "${pageContext.request.contextPath}/loginPre.do" );
	};
	
	var go_pwdSearch = function() {
		window.location.assign( "${pageContext.request.contextPath}/pwdSearch.do" );
	};
	
	var go_insertMbr = function() {
		var userId = document.getElementById("userId").value;
		var userPass = document.getElementById("userPass").value;
		var userPass2 = document.getElementById("userPass2").value;
		var userPhone = document.getElementById("userPhone").value;
		var countryCode = document.getElementById("countryCode").value;
		
		var userCk = /^[0-9a-zA-Z]{1,10}$/;
		var passCk = /^[0-9a-zA-Z]{4,20}$/;
		var phoneCk = /^[0-9]{11}$/; //11글자(숫자)
				
		if(!userId || userId=="" || !userCk.test(userId)){
			//아이디 미입력
			alert("请输入登录账户:영문 또는 숫자, 최대 10자");
			document.getElementById("userId").focus();
			return;
		}else if(!userPass || userPass=="" || !passCk.test(userPass)){
			//비밀번호 미입력
			alert("请输入密码:영문 또는 숫자, 최소 4자 최대 20자");
			document.getElementById("userPass").focus();
			return;
		}else if(userPass != userPass2) {
			alert("비밀 번호가 다름");
			document.getElementById("userPass2").focus();
			return;
		}else if(userPhone!="" && !phoneCk.test(userPhone)) {
			alert("폰 형식이 잘못 됨:숫자 11자");
			document.getElementById("userPhone").focus();
			return;
		}
		
		//var url = "${pageContext.request.contextPath}/insertMbr.do";
		var url = "<c:url value='/insertMbr.do'/>";
		var arg = "userId=" + userId + "&userPass=" + userPass + "&countryCode=" + countryCode + "&userPhone=" + userPhone;
		Csr_ajax_post( url, arg, callback );
	};
	
</script>

</head>
<body>
	<h2>注册</h2>

	<form>
		<fieldset>
			<legend>Thank You!</legend>
			<div>
				<label>选择国家</label>

				<select id="countryCode">
					<c:forEach var="item" items="${countryList}" varStatus="status" >
						 <option value ="${item[0]}" >${item[1]} : ${item[2]}</option>
					</c:forEach>					 
				</select>					
			</div>
			<div>
				<label>用 户</label> <input type="text" id="userId" placeholder="昵称" maxlength=10 />
			</div>
			<div>
				<label>密 码</label> <input type="password" id="userPass" placeholder="密码" maxlength=20 />
			</div>
			<div>
				<label>密 码2</label> <input type="password" id="userPass2" placeholder="密码" maxlength=20 />
			</div>
			<div>
				<label>TEL</label> <input type="text" id="userPhone" placeholder="手机号码" maxlength=11 />
			</div>
			<br>
			<input type="button" onclick="go_insertMbr();" value="确定">
		</fieldset>
		<fieldset>
			<span>
				<a href="javascript:go_login();">登陆</a>
			</span>
			<span>
				<a href="javascript:go_pwdSearch();">找回密码</a>
			</span>
		</fieldset>
	</form>

</body>
</html>