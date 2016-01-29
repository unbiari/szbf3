<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/common/inc/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>CSR PROJECT</title>
</head>
 <body class="easyui-layout">  
    <div data-options="region:'west',split:true,title:'北京尚大科技'" style="width:150px;">
   		<div class="easyui-accordion" data-options="border:false">  
            <div title="Table CRUD" style="overflow:auto;">  
               <a href="${ctx}/easyusers.do?method=init_users" target="mainFrame">EasyUsers</a><br>
               <a href="${ctx}/co_Type_Head.do?method=init_lists" target="mainFrame"> get_headlists</a><br>
               <a href="${ctx}/co_Type_Detail.do?method=init_toplists" target="mainFrame"> get_toplists</a> <br>
               <a href="${ctx}/co_item.do?method=init_lists" target="mainFrame">Co_Item</a><br> 
               <a href="${ctx}/pjt_project.do?method=init_lists" target="mainFrame">PJT_Project</a><br>  
               <a href="${ctx}/pjt_expense.do?method=init_lists" target="mainFrame">PJT_Expense</a><br>
               <a href="${ctx}/co_btb_head.do?method=init_lists" target="mainFrame">CO_BTB_Head</a><br>
               <a href="${ctx}/co_btb_delivery.do?method=init_lists" target="mainFrame">CO_BTB_Delivery</a><br>
               <a href="${ctx}/sm_salesorder_head.do?method=init_lists" target="mainFrame">SM_SalesOrder_Head</a><br> 
               <a href="${ctx}/sm_salesorder_detail.do?method=init_lists" target="mainFrame">SM_SalesOrder_Detail</a><br> 
               <a href="${ctx}/pur_purorder_head.do?method=init_lists" target="mainFrame">PUR_PurOrder_Head</a><br> 
               <a href="${ctx}/pur_purorder_detail.do?method=init_lists" target="mainFrame">PUR_PurOrder_Detail</a><br> 
               <a href="${ctx}/ac_cashplan.do?method=init_lists" target="mainFrame">AC_CashPlan</a><br> 
               <a href="${ctx}/hd_store.do?method=init_lists" target="mainFrame">HD_Store</a><br> 
               <a href="${ctx}/ac_cash.do?method=init_lists" target="mainFrame">AC_Cash</a><br> 
               <a href="${ctx}/gl_invoice_head.do?method=init_lists" target="mainFrame">GL_Invoice_Head</a><br> 
               <a href="${ctx}/game_tbl.do?method=init_lists" target="mainFrame">G_Tbl</a><br> 
               <a href="${ctx}/user_tbl.do?method=init_lists" target="mainFrame">U_Tbl</a><br> 
               <br><br>
           </div>
           <div title="Project">  
               <a href="${ctx}/pjt_project.do?method=project_lists" target="mainFrame">Project List</a><br>  
               <a href="${ctx}/project_create.do" target="mainFrame">Project Create</a><br> 
               <a href="${ctx}/sales_create.do" target="mainFrame">Sales Create</a><br> 
               <a href="${ctx}/pur_purorder_head.do?method=purchase_lists" target="mainFrame">Purchase List</a><br> 
               <a href="${ctx}/purchase_create.do" target="mainFrame">Purchase Create</a><br> 
               <a href="${ctx}/expense_create.do" target="mainFrame">Expense Create</a><br> 
               <br><br>
           </div>  
           <div title="HelpDesk" data-options="selected:true">  
               <a href="${ctx}/helpdesk.do" target="mainFrame">HelpDesk Main</a><br> 
               <a href="${ctx}/hd_store.do?method=init_lists" target="mainFrame">Store Info</a><br> 
               <br><br>
           </div>  
           <div title="Master"  >  
               content3  
               <br><br>
           </div>  
           <div title="ETC"  >  
               content3  
               <br><br>
           </div>  
       </div>  
    
    </div>  
    <div data-options="region:'center'">  
      <!--IFrame页面，右边页面-->
		<iframe style="width: 100%;height: 100%" frameborder="0" name="mainFrame" id="mainFrame" scrolling="yes" style="z-index:1px;"></iframe>
    </div>  
	</body>  
</html>