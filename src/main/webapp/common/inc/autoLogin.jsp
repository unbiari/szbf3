<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>
var loginFlag;
if("${loginFlag}"){
	loginFlag = "${loginFlag}";
}else{
	loginFlag = "${sessionScope.loginFlag}";
}

$(document).ready(function(){
	// autoLogin();
});

function autoLogin() {
	alert("hello auto login");
	if(window.localStorage.getItem("autoLogin") == "T" &&
			loginFlag != "T"){
		$.ajax({
			type : "POST",
			async : true,
			url : "/loginPro.json",
			dataType : "json",
			timeout : 30000,
			cache : false,
			data : {'userId':window.localStorage.getItem("userId"),
				'userPass':window.localStorage.getItem("userPass")},
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	
			error : function(request, status, error) {
				//통신 에러 발생시 처리
				autoLogin();
			},
	
			success : function(data) {
				if("exception" == data.result){
					autoLogin();
				}
				var successParam = data.successParam;
				
				
				if("F" == successParam){
					//로그인 실패
					//window.location = "/login.do";
					//자동 로그인 해제
					//window.localStorage.removeItem("autoLogin");
					//window.localStorage.removeItem("userId");
					//window.localStorage.removeItem("userPass");
				}else{
					loginFlag = "T";
					
					autoLoginSuccess();
				}
			}
		});
	}
}
</script>