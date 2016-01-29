<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/inc/taglib.jsp"%>

<c:set var="datePattern"><fmt:message key="date.format"></fmt:message></c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>User_Tbl CRUD</title>
	
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
	<h2>User_Tbl CRUD</h2>

	<table id="dg" title="User_Tbl" class="easyui-datagrid" style="width:1500px;height:500px"
			url="${ctx}/user_tbl.do?method=get_lists"
			toolbar="#toolbar" pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="user_ID" width="80">user_ID</th>
				<th field="user_PW" width="80">user_PW</th>
				<th field="user_Level" width="80">user_Level</th>
				<th field="user_Country" width="80">user_Country</th>
				<th field="user_Email" width="80">user_Email</th>
				<th field="user_TelNum" width="80">user_TelNum</th>
				<th field="count_Total" width="80">count_Total</th>
				<th field="count_Win" width="80">count_Win</th>
				<th field="count_Loss" width="80">count_Loss</th>
				<th field="count_MaxWin" width="80">count_MaxWin</th>
				<th field="count_NowWin" width="80">count_NowWin</th>
				<th field="count_Over" width="80">count_Over</th>
				<th field="gold_Total" width="80">gold_Total</th>
				<th field="gold_GetLast" width="80">gold_GetLast</th>
				<th field="tournament_Flag" width="80">tournament_Flag</th>
				<th field="insert_Time" width="80">insert_Time</th>
				<th field="last_Time" width="80">last_Time</th>
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
				<label>user_ID:</label>
				<input name="user_ID" class="easyui-validatebox" data-options="validType:'length[1,20]'" required="true">
			</div>
			<div class="fitem">
				<label>user_PW:</label>
				<input name="user_PW" class="easyui-validatebox" data-options="validType:'length[1,32]'" required="true">
			</div>
			<div class="fitem">
				<label>user_Level:</label>
				<input name="user_Level" class="easyui-numberbox">			
			</div>
			<div class="fitem">
				<label>tournament_Flag:</label>
				<input name="tournament_Flag" class="easyui-validatebox" data-options="validType:'length[1,1]'" required="true">
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
		url = '${ctx}/user_tbl.do?method=create';
	}
	function editRecord(){
		var row = $('#dg').datagrid('getSelected');
		if (row){
			$('#dlg').dialog('open').dialog('setTitle','Edit Record');
			$('#fm').form('load',row);
			url = '${ctx}/user_tbl.do?method=update&user_SeqNum='+row.user_SeqNum;
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
					$.post('${ctx}/user_tbl.do?method=remove',{user_SeqNum:row.user_SeqNum},function(result){
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
