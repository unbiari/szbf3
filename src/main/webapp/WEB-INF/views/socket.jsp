<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false" pageEncoding="utf-8"%>
<html>
<head>
<title>Home</title>
<script type="text/javascript">
	var wsocket;
 
	function connect() {
		// 웹소켓 생성
		wsocket = new WebSocket("ws://localhost:8080/csr/echo.socket");
		// 메세지가 왔을때 호출할 메소드 지정
		wsocket.onmessage = onMessage;
		wsocket.onopen = onOpen;
		document.getElementById("send2").addEventListener("click", sendFile, false);
	}
 
	// 메세지가 왔을때 호출되는 메소드
	function onOpen(evt) {
		alert("connected... msg = " + evt.data);
	}
 
	// 메세지가 왔을때 호출되는 메소드
	function onMessage(evt) {
		alert("from server... msg = " + evt.data);
	}
 
	// 서버로 데이터를 보낼때 호출
	function send(data){
		wsocket.send(data);
	}
 
	function sendFile(){
		var file = document.getElementById('file').files[0];
		wsocket.send('filename:'+file.name);
		alert('test');
 
		var reader = new FileReader();
		var rawData = new ArrayBuffer(); 

		reader.loadend = function() {
		}
	 
		reader.onload = function(e) {
			rawData = e.target.result;
			wsocket.send(rawData);
			alert("파일 전송이 완료 되었습니다.")
			wsocket.send('end');
		}

		reader.readAsArrayBuffer(file);
	}
 
	window.addEventListener("load", connect, false);
</script>
</head>
<body>
    <h1>Hello world!</h1>

    <P>The time on the server is ${serverTime}.</P>

    <table>
        <tr>
            <td><button id="send" onclick="send('asdf')">send</button></td>
        </tr>
        <tr>
            <td><input id="file" type="file"><input id="send2"
                type="button" value="send"></td>
        </tr>
    </table>

</body>
</html>
