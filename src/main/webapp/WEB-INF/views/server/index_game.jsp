<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/inc/taglib.jsp"%>

<c:set var="datePattern"><fmt:message key="date.format"></fmt:message></c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>Game_Tbl CRUD</title>
	
	<style type="text/css">
		#fm{
			margin:0;
			padding:10px 30px;
		}
		.ftitle{
			font-size:14px;
			font-weight:bold;
			color:#666;
			padding:5px 0;
			margin-bottom:10px;
			border-bottom:1px solid #ccc;
		}
		.fitem{
			margin-bottom:5px;
		}
		.fitem label{
			display:inline-block;
			width:120px;
		}
	</style>
</head>

<body>
	<h2>Game_Tbl CRUD</h2>

	<table id="dg" title="Game_Tbl" class="easyui-datagrid" style="width:1200px;height:500px"
			url="${ctx}/game_tbl.do?method=get_lists"
			toolbar="#toolbar" pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="room_Name" width="80">room_Name</th>
				<th field="count_Grade" width="80">count_Grade</th>
				<th field="avg_Grade" width="80">avg_Grade</th>
				<th field="master_ID" width="80">master_ID</th>
				<th field="guest_ID" width="80">guest_ID</th>
				<th field="master_WinFlag" width="80">master_WinFlag</th>
				<th field="master_Country" width="80">master_Country</th>
				<th field="guest_Country" width="80">guest_Country</th>
				<th field="master_GetGold" width="80">master_GetGold</th>
				<th field="guest_GetGold" width="80">guest_GetGold</th>
				<th field="game_TimeLength" width="80">game_TimeLength</th>
				<th field="game_TurnTotal" width="80">game_TurnTotal</th>
				<th field="winCount_Max" width="80">winCount_Max</th>
				<th field="winCount_Now" width="80">winCount_Now</th>
				<th field="insert_Time" width="80">insert_Time</th>
				<th field="last_Time" width="80">last_Time</th>
				<th field="game_History" width="80">game_History</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newRecord()">New Record</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRecord()">Edit Record</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeRecord()">Remove Record</a>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width:600px;height:580px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
		<div class="ftitle">Record Information</div>
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<label>count_Grade:</label>
				<input name="count_Grade" class="easyui-numberbox">			
			</div>
			<div class="fitem">
				<label>last_Time:</label>
				<input name="last_Time" class="easyui-datebox" data-options="" required="true">
			</div>
			<div class="fitem">
				<label>game_History:</label>
				<input name="game_History" class="easyui-validatebox" data-options="validType:'length[1,1024]'" required="true">
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveRecord()">Save</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
	</div>
</body>

</html>

<script type="text/javascript">
	var url;
	function newRecord(){
		$('#dlg').dialog('open').dialog('setTitle','New Record');
		$('#fm').form('clear');
		url = '${ctx}/game_tbl.do?method=create';
	}
	function editRecord(){
		var row = $('#dg').datagrid('getSelected');
		if (row){
			$('#dlg').dialog('open').dialog('setTitle','Edit Record');
			$('#fm').form('load',row);
			url = '${ctx}/game_tbl.do?method=update&game_SeqNum='+row.game_SeqNum;
		}
	}
	function saveRecord(){
		$('#fm').form('submit',{
			url: url,
			onSubmit: function(){
				return $(this).form('validate');
			},
			success: function(result){
				var result = eval('('+result+')');
				if (result.success){
					$('#dlg').dialog('close');		// close the dialog
					$('#dg').datagrid('reload');	// reload the record data
				} else {
					$.messager.show({
						title: 'Error',
						msg: result.msg
					});
				}
			}
		});
	}
	function removeRecord(){
		var row = $('#dg').datagrid('getSelected');
		if (row){
			$.messager.confirm('Confirm','Are you sure you want to remove this record?',function(r){
				if (r){
					$.post('${ctx}/game_tbl.do?method=remove',{game_SeqNum:row.game_SeqNum},function(result){
						if (result.success){
							$('#dg').datagrid('reload');	// reload the record data
						} else {
							$.messager.show({	// show error message
								title: 'Error',
								msg: result.msg
							});
						}
					},'json');
				}
			});
		}
	}
</script>
