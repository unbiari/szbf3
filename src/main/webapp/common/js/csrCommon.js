var Csr_Xhr; // global variable...

// var url = "http://localhost:8080/loginPre.do?country=KOR&mobilePhone=1234";
var Csr_ajax_get = function(url, callback) {
	if (window.ActiveXObject) { // IE8 이전버전 호환
		Csr_Xhr = new ActiveXObject("Microsoft.XMLHTTP");
	} else {
		Csr_Xhr = new XMLHttpRequest();
	}
	
	Csr_Xhr.open("GET", url, true); // aync = false...
	Csr_Xhr.onreadystatechange = callback;
	Csr_Xhr.send(null);
};

// url = "http://localhost:8080/loginPre.do";
// arg = "country=KOR&mobilePhone=1234&name=최영빈";
var Csr_ajax_post = function(url, arg, callback) {
	if (window.ActiveXObject) { // IE8 이전버전 호환
		Csr_Xhr = new ActiveXObject("Microsoft.XMLHTTP");
	} else {
		Csr_Xhr = new XMLHttpRequest();
	}

	Csr_Xhr.open("POST", url, true); // aync = false...
	Csr_Xhr.onreadystatechange = callback;
	Csr_Xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded");
	Csr_Xhr.send(arg);
};

/* ajax callback 사용 예제
var callback = function() {
	if (Csr_Xhr.readyState == 4) {
		if (Csr_Xhr.status == 200) {
			var data = Csr_Xhr.responseText;
			var obj = eval("(" + data + ")");
			console.log( obj["result"] );
			console.log( obj["total"] );
			console.log( obj.key );
			console.log( obj.name );
		}
	}	
};
*/
