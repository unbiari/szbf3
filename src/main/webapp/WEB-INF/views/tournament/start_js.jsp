<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/inc/commonTaglib.jsp"%>

<!DOCTYPE html>
<html lang="cn">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CGV</title>

<script src="${pageContext.request.contextPath}/common/js/csrCommon.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/szbf.css">
<script src="${pageContext.request.contextPath}/common/js/szbf.js"></script>

<script>
var seq_num_my;
var seq_num_your;
var master_flag;
var room_name;
var now_turn_count;
var now_temp_count; // 시간 흐름을 표시할 때 사용
var intDiff = parseInt(30); //倒计时总秒数量

//ajax...

// j_object.put("RT", "0"); // 1 보다 크면 오류...
// j_object.put("msg", "game created, i am room slave");
// j_object.put("my", "slave");
// j_object.put("master", user_key);
// j_object.put("room", room_key);
var callback_join_blind = function() {
	if (Csr_Xhr.readyState == 4) {
		if (Csr_Xhr.status == 200) {
			var data = Csr_Xhr.responseText;
			var obj = eval("(" + data + ")");
			console.log( obj["RT"] );
			console.log( obj["msg"] );
			if(obj["RT"] == 0) {
				if( obj["my"] == "master" ) {
					master_flag = true;
					My_Stage.master_flag = true;
					
					Next_Btn.style.visibility = "visible";
					Next_Btn.innerHTML = "Wating..."
					now_temp_count = 1;
					now_temp_count *= 1; // type casting.
					setTimeout( check_room_created(), 3000 ); // 3000msec 이후에					
				}
				else { // obj["my"] == "slave")
					master_flag = false;
					My_Stage.master_flag = false;
					
					seq_num_your = obj["master"];
					room_name = obj["room"];
			
					Next_Btn.style.visibility = "visible";
					Next_Btn.innerHTML = "Replacement and then Next";
					Next_Btn.onclick = function () {
						var position_str;
						position_str = Replacement_end();
						send_replace(position_str);
						
						Next_Btn.innerHTML = "Replaced Waiting...";
						Next_Btn.onclick = null;
					}
				}
			}
			else {
				alert(obj["msg"]);
			}
		}
		else {
			alert("网络异常,请检查网络设置");
		}
	}	
};

// j_object.put("RT", "0"); // 1 보다 크면 오류...
// j_object.put("msg", "game created, i am room master");
// j_object.put("my", "master");
// j_object.put("slave", atomRoom.memoryRoom.key_slave);
// j_object.put("room", atomUser.memoryUser.room_key);
var callback_check_room_created = function() { // only master call
	if (Csr_Xhr.readyState == 4) {
		if (Csr_Xhr.status == 200) { // ok,master,slave:JPN5678,room:KOR1234_JPN5678_20150620164100
			var data = Csr_Xhr.responseText;
			var obj = eval("(" + data + ")");
			console.log( obj["RT"] );
			console.log( obj["msg"] );
			if(obj["RT"] == 0) {
				seq_num_your = obj["slave"];
				room_name = obj["room"];
				Next_Btn.innerHTML = "Replacement and then Next";
				Next_Btn.onclick = function () {
					var position_str;
					position_str = Replacement_end();
					send_replace(position_str);
					
					Next_Btn.innerHTML = "Replaced Waiting...";
					Next_Btn.onclick = null;
				}
			}
			else {
				// alert( obj["msg"] );
				now_temp_count++;
				Next_Btn.innerHTML = "Wating..." + now_temp_count;
				setTimeout( check_room_created, 3000 ); // 3000msec 이후에					
			}
		}
		else {
			alert("网络异常,请检查网络设置");
		}
	}	
};

// j_object.put("RT", "0"); // 1 보다 크면 오류...
// j_object.put("msg", "send_replace success");
var callback_send_replace = function() {
	if (Csr_Xhr.readyState == 4) {
		if (Csr_Xhr.status == 200) {
			var data = Csr_Xhr.responseText;
			var obj = eval("(" + data + ")");
			console.log( obj["RT"] );
			console.log( obj["msg"] );
			if(obj["RT"] == 0) { // client.do?check_game_start&country=KOR&mobilePhone=1234
				now_temp_count = 1;
				now_temp_count *= 1; // type casting.
				setTimeout( check_game_start, 3000 ); // 3000msec 이후에					
			}
			else {
				alert( "Error send_replace : " + obj["msg"] );
			}
		}
		else {
			alert("网络异常,请检查网络设置");
		}
	}	
};

// j_object.put("RT", "0"); // 1 보다 크면 오류...
// j_object.put("msg", "match start");
// j_object.put("now_turn_count", atomRoom.memoryRoom.now_turn_count);
var callback_check_game_start = function() {
	if (Csr_Xhr.readyState == 4) {
		if (Csr_Xhr.status == 200) {
			var data = Csr_Xhr.responseText;
			var obj = eval("(" + data + ")");
			console.log( obj["RT"] );
			console.log( obj["msg"] );
			if(obj["RT"] == 0) {
				now_turn_count = obj["now_turn_count"];
				now_turn_count *= 1; // type casting to integer...
				game_start();
			}
			else if ( obj["now_turn_count"] == 101 ) { // start is 100
				now_temp_count++;
				Next_Btn.innerHTML = "Wating..." + now_temp_count;
				setTimeout( check_game_start, 3000 ); // 3000msec 이후에					
			}
			else {
				alert( "Error check_game_start : " + obj["msg"] );
			}
		}
		else {
			alert("网络异常,请检查网络设置");
		}
	}	
};

var my_sleep = function(msecs) 
{
    var start = new Date().getTime();
    var cur = start;
    while (cur - start < msecs) 
    {
        cur = new Date().getTime();
    }
}

// j_object.put("RT", "0"); // 1 보다 크면 오류...
// j_object.put("msg", "action:" + ret_action); // action 을 넘겨 줌
// j_object.put("action", ret_action); // action 을 넘겨 줌
// j_object.put("now_turn_count", turn_count_str); // atomRoom.memoryRoom.now_turn_count);
// j_object.put("last_position", ...); // 조건에 따라 추가 됨
// j_object.put("spy", ...); // 조건에 따라 추가 됨
var callback_check_game_action = function() { // O:M1042318,C:T105,O:F1061813
	if (Csr_Xhr.readyState == 4) {
		if (Csr_Xhr.status == 200) {
			var data = Csr_Xhr.responseText;
			var obj = eval("(" + data + ")");
			console.log( obj["RT"] );
			console.log( obj["msg"] );
			if(obj["RT"] == 0) {
				console.log( obj["action"] );
				if( obj["spy"] != null )
					console.log( obj["spy"] );
				if( obj["last_position"] != null )
					console.log( obj["last_position"] );
				
				if( obj["now_turn_count"] != (now_turn_count+1) ) { // 처음 now_turn_count = 2
					alert( "Error now_turn_count check_game_action : " + obj["now_turn_count"] );
					return;
				}
				// animation start...
				if( Parse_and_animation( now_turn_count+1, obj["action"], obj["spy"], obj["last_position"] ) == true ) {
					if( obj["last_position"] != null )
						return; // game end...
						
					setTimeout( function() {
						now_turn_count += 1;
						my_turn_start( master_flag );
					}, 2000); // 2 sec delay
				}
				else
					alert( "Error check game action" );
			}
			else if ( obj["now_turn_count"] == now_turn_count ) { // start is 100
				now_temp_count++;
				Next_Btn.innerHTML = "Wating..." + now_temp_count;
				setTimeout( check_game_action, 3000 ); // 3000msec 이후에					
			}
			else {
				alert( "Error check_game_action : " + obj["msg"] );
			}
		}
		else {
			alert("网络异常,请检查网络设置");
		}
	}	
};

// j_object.put("RT", "0"); // 1 보다 크면 오류...
// j_object.put("msg", "action:" + ret_action); // action 을 넘겨 줌
// j_object.put("action", ret_action); // action 을 넘겨 줌
// j_object.put("now_turn_count", atomRoom.memoryRoom.now_turn_count);
// j_object.put("last_position", ...); // 조건에 따라 추가 됨
// j_object.put("spy", ...); // 조건에 따라 추가 됨
var callback_send_game_action = function() { // O:M1042318,C:T105,O:F1061813
	if (Csr_Xhr.readyState == 4) {
		if (Csr_Xhr.status == 200) {
			var data = Csr_Xhr.responseText;
			var obj = eval("(" + data + ")");
			console.log( obj["RT"] );
			console.log( obj["msg"] );
			if(obj["RT"] == 0) {
				console.log( obj["action"] );
				if( obj["spy"] != null )
					console.log( obj["spy"] );
				if( obj["last_position"] != null )
					console.log( obj["last_position"] );
				
				// animation action...
				if( Parse_and_animation( now_turn_count+1, obj["action"], obj["spy"], obj["last_position"] ) == true ) {
					if( obj["last_position"] != null )
						return; // game end...
						
					setTimeout( function() {
						now_turn_count += 1;
						your_turn_start( master_flag );
					}, 2000); // 2 sec delay
				}
				else
					alert( "Error send game action ");
			}
			else {
				alert( "Error send_game_action : " + obj["msg"] );
			}
		}
		else {
			alert("网络异常,请检查网络设置");
		}
	}	
};

//////////////////////////////////////////
var join_blind_req = function() {
	var url = "${pageContext.request.contextPath}/client.do?join_blind&seqNum=" + seq_num_my + "&endWin=1";
	Csr_ajax_get( url, callback_join_blind );
};

var check_room_created = function() {
	var url = "${pageContext.request.contextPath}/client.do?check_room_created&seqNum=" + seq_num_my;
	Csr_ajax_get( url, callback_check_room_created );
};

var send_replace = function(position_str) { //client.do?send_replace&country=KOR&mobilePhone=1234&replace=P928356174
	var url = "${pageContext.request.contextPath}/client.do?send_replace&seqNum=" + seq_num_my + "&replace=" + position_str;
	Csr_ajax_get( url, callback_send_replace );
}

var check_game_start = function() {
	var url = "${pageContext.request.contextPath}/client.do?check_game_start&seqNum=" + seq_num_my;
	Csr_ajax_get( url, callback_check_game_start );
}

var check_game_action = function(master_flag) {
	var url = "${pageContext.request.contextPath}/client.do?check_game_action&seqNum=" + seq_num_my + "&turnCount=" + (now_turn_count+1);
	Csr_ajax_get( url, callback_check_game_action );
}

var Send_game_action = function( cmd, index_from, index_to ) { // called from szbf.js
	var action = cmd + (now_turn_count+1) + index_from + index_to;
	console.log( "action = " + action );
	
	var url = "${pageContext.request.contextPath}/client.do?send_game_action&seqNum=" + seq_num_my + "&action=" + action + "&turnCount=" + (now_turn_count+1);
	Csr_ajax_get( url, callback_send_game_action );
}

var my_turn_start = function(master_flag) {
	My_turn_init(master_flag);
}

var your_turn_start = function(master_flag) {
	now_temp_count = 1;
	now_temp_count *= 1;
	check_game_action(master_flag); // client.do?check_game_action&country=JPN&mobilePhone=5678
}

var game_start = function() { // 시작시 한번만 호출됨
	Replace_your();
	
	if(master_flag) {
		Next_Btn.style.visibility = "visible";
		my_turn_start(master_flag);
	}
	else {
		now_temp_count = 1;
		now_temp_count *= 1;
		check_game_action(master_flag); // client.do?check_game_action&country=JPN&mobilePhone=5678
	}
}

//////////////////////////////////////////////
window.onload = function(){
	var width = document.getElementById("board").width;
	var height = document.getElementById("board").height;

	console.log( width + "," + height);
	
	seq_num_my = "${seqNum}";
	if( seq_num_my == 0 )
		alert( "error" );
	else {
		Start_init("stage", width, height); // stage id
		join_blind_req();
	}
	
	// init css
	var time_tips = document.getElementById("time_tips");
	time_tips.style.width = width + "px";
	
	// call timer
    timer();
};
</script>

</head>

<body>

<h1>
	Hello CGV!!
</h1>
    <div class="time_tips" id="time_tips">
        请您在&nbsp;<span id="minute_show">--分</span><span id="second_show">--秒</span>&nbsp;内完成支付，超时将取消订单。
    </div>
	<div id="stage" style="position:relative" >
		<img id="board" src="${pageContext.request.contextPath}/images/client/board.png" alt="Board" />
	</div>
</body>

<!-- 倒计时 -->
<script type="text/javascript">
function timer(){
    window.setInterval(function(){
	    var second=0;//时间默认值
	    if(intDiff > 0){
	        second = Math.floor(intDiff);
	    }
	    var second_show = document.getElementById("second_show");
	    second_show.innerHTML = '<s></s>'+second+'秒';
	    intDiff--;
    }, 1000);
}
</script>

</html>
