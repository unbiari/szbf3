/**
 * Main			JQuery
 * @author		Eunok Kim (publisher@to-be.co.kr)
 * @version	0.1
 * @date		2014/04/01
**/




/////////////////////////////////////////////////////////////////////////////////////////// 레이어팝업닫기 Start
$(document).ready(function(){
	var checkWidth = $('.ui-controlgroup-controls').find('.ui-checkbox:last-child').width();
	$('.ui-checkbox').find('.ui-corner-all').height(checkWidth - 12);
	$('.number_row').height(checkWidth - 6);	
});	
/////////////////////////////////////////////////////////////////////////////////////////// 레이어팝업닫기 End