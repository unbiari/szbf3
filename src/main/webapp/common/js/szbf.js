// from Constant.java

const MAL_BLIND = 0; // only client used...
const MAL_KING = 1;
const MAL_SPY = 2;
const MAL_ASS = 3;
const MAL_ENG = 4;
const MAL_5 = 5;
const MAL_6 = 6;
const MAL_7 = 7;
const MAL_8 = 8;
const MAL_MINE = 9;
const MAL_NONE = -99;

const MAL_MAX = 9;
const PAN_SIZE = 24;
const PAN_INDEX_ARR = [1, 5, 10, 16, 21];
const PAN_INDEX_NONE = 0; // 죽은말은 index 를 갖지 않는다.
const MASTER_INDEX_START = PAN_INDEX_ARR[3]; // 16;
const SLAVE_INDEX_START = MAL_MAX;

const TURN_COUNT_START = 100;
const INDEX_ADD = 10;

const IMAGE_SIZE_SCALE = [0.6, 0.62, 0.65, 0.7, 0.8]; // , 0.9];
const ANI_TIME_MOVE = 100; // 100 msec
const ANI_TIME_FIGHT = 500; // 500 msec

const PAN = [
	[ 0, 0 ], // 사용하지 않음 from 1...
	[ 92, 90 ],
	[ 138, 90 ],
	[ 184, 90 ],
	[ 230, 90 ],
	[ 60, 136 ],
	[ 110, 136 ],
	[ 160, 136 ],
	[ 208, 136 ],
	[ 260, 136 ],
	[ 28, 188 ],
	[ 80, 188 ],
	[ 134, 188 ],
	[ 186, 188 ],
	[ 240, 188 ],
	[ 292, 188 ],
	[ 44, 242 ],
	[ 102, 242 ],
	[ 160, 242 ],
	[ 218, 242 ],
	[ 276, 242 ],
	[ 62, 306 ],
	[ 128, 306 ],
	[ 192, 306 ],
	[ 256, 306 ],
];

const HEX_NUM = 6;
const PAN_NONE = 0;

const Pan_Neighbor = [
	[0,0,0,0,0,0],
	[0,0,0,2,5,6],
	[0,0,1,3,6,7],
	[0,0,2,4,7,8],
	[0,0,3,0,8,9],
	[0,1,0,6,10,11],
	[1,2,5,7,11,12],
	[2,3,6,8,12,13],
	[3,4,7,9,13,14],
	[4,0,8,0,14,15],
	[0,5,0,11,0,16],
	[5,6,10,12,16,17],
	[6,7,11,13,17,18],
	[7,8,12,14,18,19],
	[8,9,13,15,19,20],
	[9,0,14,0,20,0],
	[10,11,0,17,0,21],
	[11,12,16,18,21,22],
	[12,13,17,19,22,23],
	[13,14,18,20,23,24],
	[14,15,19,0,24,0],
	[16,17,0,22,0,0],
	[17,18,21,23,0,0],
	[18,19,22,24,0,0],
	[19,20,23,0,0,0],
];

const MAL_IMG_ARR = [ 
	["images/client/enemy.png",67,65],
						     
	["images/client/jan_1.png",92,95],
	["images/client/jan_2.png",76,80],
	["images/client/jan_3.png",80,84],
	["images/client/jan_4.png",78,87],
	["images/client/jan_5.png",67,94],
	["images/client/jan_6.png",67,94],
	["images/client/jan_7.png",70,94],
	["images/client/jan_8.png",66,94],
	["images/client/jan_9.png",82,70],
						     
	["images/client/lei_1.png",83,89],
	["images/client/lei_2.png",72,81],
	["images/client/lei_3.png",67,85],
	["images/client/lei_4.png",72,88],
	["images/client/lei_5.png",60,91],
	["images/client/lei_6.png",62,93],
	["images/client/lei_7.png",61,94],
	["images/client/lei_8.png",64,100],
	["images/client/lei_9.png",79,69],
];
const MAL_IMG_ARR_YOUR_START = 10;

const MARK_TO_ARR_MAX = 8; //


// Variable...

var Replace_Arr;
var My_Stage;
var Mal_Arr;
var Mark_From; // from 은 1개
var Mark_To_Arr; // to 는 여러개...
var Next_Btn;

var get_scale = function ( pan_index ) {
	var scale;

	if( pan_index < PAN_INDEX_ARR[1] )
		scale = IMAGE_SIZE_SCALE[0];
	else if( pan_index < PAN_INDEX_ARR[2] )
		scale = IMAGE_SIZE_SCALE[1];
	else if( pan_index < PAN_INDEX_ARR[3] )
		scale = IMAGE_SIZE_SCALE[2];
	else if( pan_index < PAN_INDEX_ARR[4] )
		scale = IMAGE_SIZE_SCALE[3];
	else
		scale = IMAGE_SIZE_SCALE[4];

	return scale;
}

var mal_replace_start = function( pan_index )
{
	var idx = 0, i = 0;
	var temp_mal;
	var temp_mark_to;
	var mark_to_arr_index = 0;

	Mark_From.style.left = PAN[ pan_index ][0] + "px";
	Mark_From.style.top = PAN[ pan_index ][1] + "px";
	Mark_From.pan_index = pan_index; // save variable...
	Mark_From.style.visibility = "visible";

	for( idx in Mal_Arr ) {
		i = 1 * idx; // type casting to integer...
		temp_mal = Mal_Arr[i];
		temp_mal.my_div.onclick = null; // reset click function...

		if( temp_mal.pan_index != pan_index ) {
			temp_mark_to = Mark_To_Arr[mark_to_arr_index];
			mark_to_arr_index++;
			
			temp_mark_to.style.visibility = "visible";

			temp_mark_to.style.left = PAN[ temp_mal.pan_index ][0] + "px";
			temp_mark_to.style.top = PAN[ temp_mal.pan_index ][1] + "px";

			temp_mark_to.pan_index = temp_mal.pan_index;
			temp_mark_to.onclick = function(){
				mal_replace_end( this.pan_index );
			}
		}
		Mark_To_Arr.now_index_count = mark_to_arr_index;
	}
	
	console.log( "mal_replace_start() = " + pan_index );
};

var mal_replace_end = function( pan_index )
{
	var index_from, index_to;
	var idx = 0, i = 0;
	var temp_mark;
	var i_from, i_to;
	var temp_mal;

	index_from = Mark_From.pan_index; // save variable...
	index_to = pan_index;

	Mark_From.style.visibility = "hidden";

	// for( idx in Mark_To_Arr ) {
	for( i=0; i<Mark_To_Arr.now_index_count; i++ ) {
		// i = 1 * idx; // type casting to integer...
		temp_mark = Mark_To_Arr[i];
		temp_mark.onclick = null; // reset click function...
		temp_mark.style.visibility = "hidden";
	}
	Mark_To_Arr.now_index_count = 0;
	
	// replace mal
	for( idx in Mal_Arr ) {
		i = 1 * idx; // type casting to integer...
		temp_mal = Mal_Arr[i];

		if( temp_mal.pan_index == index_from ) {
			temp_mal.my_div.style.left = PAN[ index_to ][0] + "px";
			temp_mal.my_div.style.top = PAN[ index_to ][1] + "px";

			temp_mal.scale = get_scale( index_to );
			temp_mal.my_img.width = parseInt( temp_mal.image_w * temp_mal.scale );
			temp_mal.my_img.height = parseInt( temp_mal.image_h * temp_mal.scale );

			i_from = i;
		}
		else if( temp_mal.pan_index == index_to )
		{
			temp_mal.my_div.style.left = PAN[ index_from ][0] + "px";
			temp_mal.my_div.style.top = PAN[ index_from ][1] + "px";

			temp_mal.scale = get_scale( index_from );
			temp_mal.my_img.width = parseInt( temp_mal.image_w * temp_mal.scale );
			temp_mal.my_img.height = parseInt( temp_mal.image_h * temp_mal.scale );

			i_to = i;
		}
	}
	Mal_Arr[ i_from ].pan_index = index_to;
	Mal_Arr[ i_to ].pan_index = index_from;

	// enable event
	for( idx in Mal_Arr ) {
		i = 1 * idx; // type casting to integer...
		temp_mal = Mal_Arr[i];
		temp_mal.my_div.pan_index = temp_mal.pan_index; // div 에 저장하기 위해서 사용...

		temp_mal.my_div.onclick = function() {
			mal_replace_start( this.pan_index ); // 위에서 저장한 것을 사용...
		}
	}

	console.log( "mal_replace_end() = " + "from:" + index_from + ", to:" + index_to );
};

var set_opacity = function(obj, val)
{
	obj.style.opacity = (val/100);
	obj.style.MozOpacity = (val/100);
	obj.style.KhtmlOpacity = (val/100);
	obj.style.filter = 'alpha(opacity='+val+')';
};

var Replacement_end = function() { // 강제 배치 종료
	var idx, i;
	var temp_mal;
	var temp_mark;
	var position_str = "P";

	if( Mark_To_Arr.now_index_count == 0 ) { // 현재 배치 선택된 상태가 아니라면
		for( idx in Mal_Arr ) {
			i = 1 * idx; // type casting to integer...
			temp_mal = Mal_Arr[i];
			temp_mal.my_div.onclick = null; // reset click function...
		}
	}
	else {
		Mark_From.style.visibility = "hidden";

		// for( idx in Mark_To_Arr ) {
		for( i=0; i<Mark_To_Arr.now_index_count; i++ ) {
			// i = 1 * idx; // type casting to integer...
			temp_mark = Mark_To_Arr[i];
			temp_mark.onclick = null; // reset click function...
			temp_mark.style.visibility = "hidden";
		}
		Mark_To_Arr.now_index_count = 0;
	}

	// make position...
	Mal_Arr.sort( function(a,b) { return a.pan_index - b.pan_index } );
	for( idx in Mal_Arr ) {
		i = 1 * idx; // type casting to integer...
		temp_mal = Mal_Arr[i];
		position_str += temp_mal.mal_id;
	}

	console.log( "Replacement_end() = " + position_str );
	return position_str;
};

var Mal = function (id, mal_id, x, y, image_w, image_h, image_url, pan_index) {
	this.id = id; // div id

	this.mal_id = mal_id; // 음수이면 상대말 , 0 이면 상대말 안보임
	
	this.x = x;
	this.y = y;

	this.image_w = image_w;
	this.image_h = image_h;
	this.image_url = image_url;
	
	// this.live_flag = false;
	this.pan_index = pan_index;
	// this.owner_flag = true;

	this.scale = get_scale( pan_index );
	
	this.ani_count_now = 0;
	this.ani_count_max = 0; // 종료 count 숫자
	this.ani_org_x = 0; // x = ani_org_x + (ani_offset_x * ani_count_now)
	this.ani_org_y = 0;
	this.ani_org_scale = 0.0; // x = ani_org_scale + (ani_offset_scale * ani_count_now)
	this.ani_offset_x = 0;
	this.ani_offset_y = 0;
	this.ani_offset_scale = 0.0;

	this.my_div = document.createElement("div");
	this.my_div.id = id;
	this.my_div.style.position = "absolute";
	this.my_div.style.left = x + "px";
	this.my_div.style.top = y + "px";

	this.my_img = document.createElement("img");
	this.my_img.id = "img" + id;
	this.my_img.width = parseInt( image_w * this.scale );
	this.my_img.height = parseInt( image_h * this.scale );
	this.my_img.src = image_url;

	this.my_div.style.margin = parseInt( -this.my_img.height/2 ) + "px 0px 0px " + parseInt( -this.my_img.width/2 ) + "px";
	this.my_div.style.cursor = "pointer";

	this.my_div.appendChild(this.my_img);
	My_Stage.appendChild(this.my_div);

	if( mal_id > 0 ) { // 처음 생성될 때는 배치 상태
		this.my_div.onclick = function(){
			mal_replace_start( pan_index );
		}
	}
};

///////////////////////////////////////////////

var Start_init = function( stage_id, screen_width, screen_height ) {
	var i = 0;
	var idx;
	var temp_mark_to;
	var pan_index = 0;
	var mal_id = 0;
	
	My_Stage = document.getElementById(stage_id);
	My_Stage.screen_width = screen_width;
	My_Stage.screen_height = screen_width;
	My_Stage.master_flag = true; // default value

	//
	Mark_From = document.createElement("div");
	/*
	Mark_From.style.backgroundColor = "red";
	//Mark_From.style.display = "none";
	Mark_From.style.width = "40px";
	Mark_From.style.height = "40px";
	Mark_From.style.margin = "-20px 0px 0px -20px";
	Mark_From.style.position = "absolute";
	Mark_From.style["z-index"] = "10";
	*/
	Mark_From.setAttribute("class", "mark_from"); // defined in szbf.css
	
	Mark_From.style.visibility = "hidden"; // "visible"
	set_opacity(Mark_From, 50);
	My_Stage.appendChild(Mark_From);

	Mark_To_Arr = new Array();
	for( i=0; i<MARK_TO_ARR_MAX; i++ ) {
		temp_mark_to = document.createElement("div");
		/*
		temp_mark_to.style.backgroundColor = "blue";
		temp_mark_to.style.width = "40px";
		temp_mark_to.style.height = "40px";
		temp_mark_to.style.margin = "-20px 0px 0px -20px";
		temp_mark_to.style.position = "absolute";
		temp_mark_to.style.cursor = "pointer";
		temp_mark_to.style["z-index"] = "10";
		*/
		temp_mark_to.setAttribute("class", "mark_to"); // defined in szbf.css
		
		set_opacity(temp_mark_to, 50);
		temp_mark_to.style.visibility = "hidden";

		My_Stage.appendChild(temp_mark_to);
		Mark_To_Arr.push(temp_mark_to);
	}
	Mark_To_Arr.now_index_count = 0;
	
	//
	Next_Btn = document.createElement("button");
	Next_Btn.style.left = parseInt( My_Stage.screen_width/2 ) + "px";
	/*
	Next_Btn.style.top = "100px";
	Next_Btn.style.width = "180px";
	Next_Btn.style.height = "40px";
	Next_Btn.style.margin = "-20px 0px 0px -40px";
	Next_Btn.style.position = "absolute";
	Next_Btn.style["z-index"] = 20;
	*/
	Next_Btn.setAttribute("class", "next_btn"); // defined in szbf.css
	
	Next_Btn.style.visibility = "hidden";
	Next_Btn.innerHTML = "Next";
	set_opacity(Next_Btn, 60);
	My_Stage.appendChild(Next_Btn);

	// replace...
	Replace_Arr = [ 1, 2, 3, 4, 5, 6, 7, 8, 9 ];

	Replace_Arr.sort( function(a, b) {
		return Math.random() - 0.5;
	} );
	Replace_Arr.reverse();
	Replace_Arr.sort( function(a, b) {
		return Math.random() - 0.5;
	} );

	// make mal
	Mal_Arr = new Array();

	for( idx in Replace_Arr ) {
		i = 1 * idx; // type casting to integer...
		mal_id = Replace_Arr[i];
		pan_index = MASTER_INDEX_START + i;
		
		// make my mal
		var temp_mal = new Mal( i, mal_id, PAN[ pan_index ][0], PAN[ pan_index ][1],
			MAL_IMG_ARR[ mal_id ][1], MAL_IMG_ARR[ mal_id ][2], MAL_IMG_ARR[ mal_id ][0], pan_index );

		console.log( mal_id + "," + pan_index );
		Mal_Arr.push( temp_mal );
	}

	console.log( "Start_init()");
};

var Replace_your = function( replace ) {
	var pan_index = 0;
	var mal_id = 0;
	var i = 0;

	for( i=0; i<MAL_MAX; i++ ) {
		mal_id = MAL_BLIND;
		pan_index = i+1;
		
		// make your mal
		var temp_mal = new Mal( i+1+MAL_MAX, mal_id, PAN[ pan_index ][0], PAN[ pan_index ][1],
				MAL_IMG_ARR[ mal_id ][1], MAL_IMG_ARR[ mal_id ][2], MAL_IMG_ARR[ mal_id ][0], pan_index );

		Mal_Arr.push( temp_mal );
	}
	
	console.log( "Replace_your()" );
};

///////////////////////////////////////
var find_mal_from_pan = function( pan_index ) {
	var idx;
	var temp_mal;
	var i;
	
	for( idx in Mal_Arr ) {
		i = 1 * idx; // type casting to integer...
		temp_mal = Mal_Arr[i];
		if( temp_mal.pan_index == pan_index )
			return temp_mal.mal_id;
	}
	return MAL_NONE; // mal 이 존재하지 않음
}

// hex area
var update_hex_area = function( my_mal_id, pan_index ) {
	var i;
	var temp_mal;
	var temp_index;
	var mark_to_arr_index = 0;
	var temp_mark_to;
	
	for( i=0; i<HEX_NUM; i++ ) {
		temp_index = Pan_Neighbor[ pan_index ][i];
		
		if( temp_index == PAN_NONE )
			continue;
		
		temp_mal = find_mal_from_pan( temp_index );
		
		if( temp_mal == MAL_BLIND ) {
			if( my_mal_id == MAL_MINE )
				continue; // 똥덩어리는 적을 공격하지 못함
		}
		else if( temp_mal > MAL_BLIND ) { // 자기편 말
			continue;
		}
	
		temp_mark_to = Mark_To_Arr[mark_to_arr_index];
		mark_to_arr_index++;
		
		temp_mark_to.style.visibility = "visible";

		temp_mark_to.style.left = PAN[ temp_index ][0] + "px";
		temp_mark_to.style.top = PAN[ temp_index ][1] + "px";

		temp_mark_to.pan_index = temp_index;
		temp_mark_to.onclick = function(){
			mal_action_end( this.pan_index );
		}
				
		Mark_To_Arr.now_index_count = mark_to_arr_index;
	}
	console.log( "update_hex_area()" );
};

var mal_action_start = function( pan_index )
{
	var idx = 0, i = 0;
	var temp_mal;
	var my_mal_id;

	Mark_From.style.left = PAN[ pan_index ][0] + "px";
	Mark_From.style.top = PAN[ pan_index ][1] + "px";
	Mark_From.pan_index = pan_index; // save variable...
	Mark_From.style.visibility = "visible";

	for( idx in Mal_Arr ) {
		i = 1 * idx; // type casting to integer...
		temp_mal = Mal_Arr[i];
		temp_mal.my_div.onclick = null; // reset click function...
		if( temp_mal.pan_index == pan_index )
			my_mal_id = temp_mal.mal_id;
	}
	update_hex_area( my_mal_id, pan_index ); // 이동 이나 액션이 가능한 target 의 event 등록
	
	Mark_From.onclick = function(){ // 자기 자신의 event 도 등록
		mal_action_end( this.pan_index );
	}
	
	console.log( "mal_action_start() = " + pan_index );
};

var mal_action_end = function( pan_index )
{
	var index_from, index_to;
	var i = 0;
	var temp_mark;
	var temp_mal;
	var cmd;

	index_from = Mark_From.pan_index; // save variable...
	index_to = pan_index;

	Mark_From.style.visibility = "hidden";

	for( i=0; i<Mark_To_Arr.now_index_count; i++ ) {
		temp_mark = Mark_To_Arr[i];
		temp_mark.onclick = null; // reset click function...
		temp_mark.style.visibility = "hidden";
	}
	Mark_To_Arr.now_index_count = 0;

	if( index_from == pan_index ) {
		click_event_start( master_flag );
		
		console.log( "mal_action_end() = reseted..." );
	}
	else {
		temp_mal = find_mal_from_pan( index_to );
		if( temp_mal == MAL_BLIND )
			cmd = "F";
		else
			cmd = "M";
		index_from += INDEX_ADD;
		index_to += INDEX_ADD;
		
		Send_game_action( cmd, index_from, index_to ); // defined in start_js.jsp
		
		console.log( "mal_action_end() = " + "cmd:" + cmd + ", from:" + index_from + ", to:" + index_to );
	}
};

var click_event_start = function(master_flag) {
	var idx;
	var temp_mal;
	
	// enable event
	for( idx in Mal_Arr ) {
		i = 1 * idx; // type casting to integer...
		temp_mal = Mal_Arr[i];
		temp_mal.my_div.pan_index = temp_mal.pan_index; // div 에 저장하기 위해서 사용...
		
		if( temp_mal.mal_id > 0 ) {
			temp_mal.my_div.onclick = function() {
				mal_action_start( this.pan_index );
			}
		}
	}
	
	console.log( "click_event_start()" );
};

var My_turn_init = function(master_flag) {
	click_event_start( master_flag );
	
	console.log( "My_turn_init()" );
};

var animation_move = function(target, i_from, index_from, index_to) {
	var temp_scale;
	var temp_to_x;
	var temp_to_y;
	
	temp_to_x = (PAN[ index_to ][0] - PAN[ index_from ][0]) + "px";
	temp_to_y = (PAN[ index_to ][1] - PAN[ index_from ][1]) + "px";

	temp_scale = 1.0 + ( get_scale( index_to ) - get_scale( index_from ) ) / get_scale( index_from ) ;
	
	target.my_div.addEventListener("transitionend", end_transition, true);

	console.log( temp_to_x + "," + temp_to_y + "," + temp_scale );
	
	target.my_div.style.transition = "translate 3s";
	target.my_div.style.transform = "translate(" + temp_to_x + "," + temp_to_y + ")"; // ") scale(" + temp_scale + ")";

	// event function
	function end_transition() {
		// target.my_div.removeEventListener("transitionend", end_transition);
		// alert( "end_transition" );
		
		target.my_div.style.left = PAN[ index_to ][0] + "px";
		target.my_div.style.top = PAN[ index_to ][1] + "px";

		target.scale = get_scale( index_to );
		target.my_img.width = parseInt( target.image_w * target.scale );
		target.my_img.height = parseInt( target.image_h * target.scale );
		
		Mal_Arr[ i_from ].pan_index = index_to;
	};
};

var animation_mark_start = function(index_from, index_to) {
	var temp_mark_to;
	
	set_opacity(Mark_From, 50);
	Mark_From.style.left = PAN[ index_from ][0] + "px";
	Mark_From.style.top = PAN[ index_from ][1] + "px";
	Mark_From.pan_index = PAN_NONE;
	Mark_From.style.visibility = "visible";

	temp_mark_to = Mark_To_Arr[0];
	
	set_opacity(temp_mark_to, 50);
	temp_mark_to.style.left = PAN[ index_to ][0] + "px";
	temp_mark_to.style.top = PAN[ index_to ][1] + "px";
	temp_mark_to.pan_index = PAN_NONE;
	temp_mark_to.style.visibility = "visible";

	setTimeout( function() {
			Mark_From.style.visibility = "hidden";
			temp_mark_to.style.visibility = "hidden";
		}, 2000); // 1 sec delay
};

var animation_spy_start = function( spy ) {
	var spy_index;
	var spy_mal;
	var idx;
	var i;
	var mal_id;
	var temp_mal;

	spy_index = spy.substring( 0, 2 ) - INDEX_ADD ;
	spy_mal = 1 * spy.substring( 2, 3 ); // typecasting...
	
	mal_id = MAL_IMG_ARR_YOUR_START + spy_mal - 1;
	
	for( idx in Mal_Arr ) {
		i = 1 * idx; // type casting to integer...
		temp_mal = Mal_Arr[i];

		if( temp_mal.pan_index == spy_index ) {
			temp_mal.my_img.width = parseInt( MAL_IMG_ARR[ mal_id ][1] * temp_mal.scale );
			temp_mal.my_img.height = parseInt( MAL_IMG_ARR[ mal_id ][2] * temp_mal.scale );
			temp_mal.my_img.src = MAL_IMG_ARR[ mal_id ][0];

			temp_mal.my_div.style.margin = parseInt( -temp_mal.my_img.height/2 ) + "px 0px 0px " + parseInt( -temp_mal.my_img.width/2 ) + "px";
			break;
		}
	}
	
	setTimeout( function() {
		temp_mal.my_img.width = parseInt( MAL_IMG_ARR[ MAL_BLIND ][1] * temp_mal.scale );
		temp_mal.my_img.height = parseInt( MAL_IMG_ARR[ MAL_BLIND ][2] * temp_mal.scale );
		temp_mal.my_img.src = MAL_IMG_ARR[ MAL_BLIND ][0];

		temp_mal.my_div.style.margin = parseInt( -temp_mal.my_img.height/2 ) + "px 0px 0px " + parseInt( -temp_mal.my_img.width/2 ) + "px";
	}, 2000); // 2 sec delay
};

var view_last_position = function( last_position ) {
	var mal_index;
	var mal_id;
	var idx;
	var i;
	var ii;
	var temp_mal;
	var temp_mal_id;
	var last_mal_count;
	
	last_mal_count = last_position.length / 3;
	
	for( i=0; i<last_mal_count; i++ ) {
		mal_index = 1 * last_position.substring( 0+(i*3), 2+(i*3) ) - INDEX_ADD ;
		temp_mal_id = 1 * last_position.substring( 2+(i*3), 3+(i*3) ); // typecasting...
		
		mal_id = MAL_IMG_ARR_YOUR_START + temp_mal_id - 1;
		
		for( idx in Mal_Arr ) {
			ii = 1 * idx; // type casting to integer...
			temp_mal = Mal_Arr[ii];
	
			if( temp_mal.pan_index == mal_index ) {
				temp_mal.my_img.width = parseInt( MAL_IMG_ARR[ mal_id ][1] * temp_mal.scale );
				temp_mal.my_img.height = parseInt( MAL_IMG_ARR[ mal_id ][2] * temp_mal.scale );
				temp_mal.my_img.src = MAL_IMG_ARR[ mal_id ][0];
	
				temp_mal.my_div.style.margin = parseInt( -temp_mal.my_img.height/2 ) + "px 0px 0px " + parseInt( -temp_mal.my_img.width/2 ) + "px";
				set_opacity(temp_mal.my_img, 50);
				break;
			}
		}
	}
};

var Parse_and_animation = function(turn_count, action, spy, last_position) {
	var index_from;
	var index_to;
	var cmd;
	var temp_count;
	var idx;
	var i;
	var temp_mal;
	var i_from, i_to;
	
	if( action.length < 5 )
		return false;
	cmd = action.substring(0,2);
	temp_count = action.substring(2,5);
	if( temp_count != turn_count )
		return false;
	if( cmd == "TO" )
		return true;
	
	index_from = action.substring(5, 7) - INDEX_ADD; // MV1032823
	index_to = action.substring(7, 9) - INDEX_ADD;
	
	animation_mark_start( index_from, index_to ); // from to 를 표시하고 자동적으로 사라지게 함
	if( spy != null )
		animation_spy_start( spy );
	if( last_position != null )
		view_last_position( last_position );

	if( cmd != "MV" && cmd != "FW" && cmd != "FL" && cmd != "MW" && cmd != "ML" )
		return false;
	
	setTimeout( function() {
		if( cmd == "MV" ) {
			//
			for( idx in Mal_Arr ) {
				i = 1 * idx; // type casting to integer...
				temp_mal = Mal_Arr[i];
	
				if( temp_mal.pan_index == index_from ) {
					temp_mal.my_div.style.left = PAN[ index_to ][0] + "px";
					temp_mal.my_div.style.top = PAN[ index_to ][1] + "px";
	
					temp_mal.scale = get_scale( index_to );
					temp_mal.my_img.width = parseInt( temp_mal.image_w * temp_mal.scale );
					temp_mal.my_img.height = parseInt( temp_mal.image_h * temp_mal.scale );
	
					i_from = i;
					// 나중에 구현 - animation_move( temp_mal, i_from, index_from, index_to );
					break;
				}
			}
			Mal_Arr[ i_from ].pan_index = index_to;
		}
		else if( cmd == "FW" ) {
			for( idx in Mal_Arr ) {
				i = 1 * idx; // type casting to integer...
				temp_mal = Mal_Arr[i];
	
				if( temp_mal.pan_index == index_to ) {
					temp_mal.my_div.style.visibility = "hidden";
					temp_mal.mal_id = MAL_NONE;
					
					i_to = i;
					break;
				}
			}
			Mal_Arr[ i_to ].pan_index = PAN_INDEX_NONE;
		}
		else if( cmd == "FL" ) {
			for( idx in Mal_Arr ) {
				i = 1 * idx; // type casting to integer...
				temp_mal = Mal_Arr[i];
	
				if( temp_mal.pan_index == index_from ) {
					temp_mal.my_div.style.visibility = "hidden";
					temp_mal.mal_id = MAL_NONE;
					
					i_from = i;
					break;
				}
			}
			Mal_Arr[ i_from ].pan_index = PAN_INDEX_NONE;
		}
		else if( cmd == "MW" || cmd == "ML" ) {
			if( cmd == "MW" ) {
				console.log( "Master Win" );
				alert( "Master Win" );
			}
			else {
				console.log( "Master Loss" );
				alert( "Master Loss" );
			}
		};
	}, 2000); // 2 sec delay
	
	return true;
}
