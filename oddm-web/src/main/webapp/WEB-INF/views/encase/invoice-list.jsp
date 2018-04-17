<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="edge" />
<jsp:include page="../inc.jsp"/>
<c:if test="${fn:contains(sessionInfo.resourceList, '/invoice/view')}">
	<script type="text/javascript">
		$.canFind = true;
	</script>
</c:if>
<style type="text/css">
	.autoInput{width: 95%}
	.datagrid-cell {   /* easyui不会自动识别英文字符串自动换行，nowrap: false不生效 */
		white-space: pre-wrap;
		word-wrap: break-word;
		word-break:break-all;
		line-height: 18px;
	}
	input {border:1px solid #CCC;font-size:12px;height:18px;border-radius: 4px;}
</style> 
<script type="text/javascript">
	var dataGrid;
	$(function() {
	dataGrid = $('#dataGrid').datagrid({
			url:'${pageContext.request.contextPath}/invoice/getInvoiceInfoInPage',
			idField : 'id',
			pagination : true,
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			nowrap:false,
			onDblClickRow : function(index, row){
				findFun(row.id);
	        },
			pageSize : 50,
			frozenColumns : [ [ {
				field : 'id',
				title : '序号',
				width : 150,
				checkbox : true
			}]],
			 columns:[[
						 {field:'name',title:'发票名称',width:'12%'},
		        		 {field:'to',title:'客户名称',width:'15%'},
		        		 {field:'companyName',title:'公司名称',width:'15%'},
		        		 {field:'dateInvoice',title:'发票日期',width:'15%'},
		        		 {field:'piNo',title:'P/I NO',width:'12%'},
		        		 {field:'currency',title:'币种',width:'7%'},
		        		 {field:'timestamp',title:'更新时间',width:'15%'},
        		 {
				field : 'action',
				title : '操作',
				width : '5%',
				formatter : function(value, row, index) {
					var str = '';
					/* if ($.canEdit) {
						str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
					}
						str += '&nbsp;'; */
					if ($.canFind) {	
						str += $.formatString('<img onclick="findFun(\'{0}\');" src="{1}" title="查看"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/search.png');
					}
					/* 	str += '&nbsp;';
					if ($.canDelete) {	
						str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
					} */
					return str;
				}
			} 
   			]],
   			toolbar : '#toolbar',
		})
	});
	/**添加*/
	function addFun() {
		//showRestartDialog();
		
		 var url = "${pageContext.request.contextPath}/invoice/addInvoicePage";
    	 var title = "发票"+"(添加)";
		 window.parent.addTab({
			url : url,
			title : title,
			id : "addinvoice" 
		 }); 
	}
	
	/**批量删除*/
	function batchDelete() {
		var rows = dataGrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.$.messager.confirm('确认', '您是否要删除当前选中的表单？', function(r) {
				if(r) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
   					type: "POST",
   					url: "${pageContext.request.contextPath}/invoice/batchDelete",
   					dataType:"json",
   					data:{
   						ids : ids.join(',')
   					},
   					success: function(j) {
     					if(j.success == true) {
     						dataGrid.datagrid('load');
							dataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
							parent.$.messager.alert('提示', j.msg, 'info');
							
     					} else {
     						parent.$.messager.alert('错误', '删除失败', 'error');
     						
     					}
     				
   				}
				});
					parent.$.messager.progress('close');	
				}
			})
		} else {
			parent.$.messager.show({
				title : '提示',
				msg : '请勾选要删除的记录！'
			});
		}
	}
	
	/**查看invoice*/
	function findFun(id) {
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
		//	parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		 var url = "${pageContext.request.contextPath}/invoice/findInvoicePage?id="+id;
    	 var title = "发票："+row.name+"(查看)";
		 window.parent.addTab({
			url : url,
			title : title,
			id : "invoice_"+id
			/* iconCls : node.iconCls */
		 });
		
	}
	/**修改invoice*/
	function editFun() {
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		var url = "${pageContext.request.contextPath}/invoice/editInvoicePage?id="+row.id;
		var title = "发票"+row.name+"编辑";
	 	window.parent.addTab({
		url : url,
		title : title,
	 });
	}
	
	/**删除invoice*/
	function deleteFun(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要删除当前表单？', function(b) {
			if (b) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.ajax({
   				type: "POST",
   				url: "${pageContext.request.contextPath}/invoice/del?id="+id,
   				dataType:"json",
   				success: function(j){
     				if(j.success == true) {
     						$.messager.show({
							title:'操作正确',
							msg:j.msg,
							timeout:2000,
							showType:'slide'
					});
							dataGrid.datagrid('reload');
     				} else {
     					parent.$.messager.alert('提示', j.msg, 'error');
     				}
     				
   				}
   				
				});
				parent.$.messager.progress('close');	
				} else {
					parent.$.messager.show({
						title : '提示',
						msg : '错误失败'
					});
				}
		});
	}
	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	
	function searchNum(){
	
	}
</script>
</head>

<body class="easyui-layout" data-options="fit:true,border:false">

	<div data-options="region:'north',border:false" style=" overflow: hidden;background-color: #fff">
		<form id="searchForm">
			<table>
				<tr>
					<th>发票名称:</th>
					<td><input name="name" placeholder="" class="autoInput"/></td>
					<th>客户名称:</th>
					<td><input name="to" placeholder="" class="autoInput"/></td>
					<td>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/invoice/add')}"> 
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/invoice/edit')}">
			<a onclick="editFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">编辑</a>
		</c:if>
	</div>
  
	<!-- 添加 -->
    <div  id="restartDialog" class="easyui-dialog" title="选择订单" style="width: 400px; height: 180px;display:none;"  
                    data-options="iconCls:'pag-list',modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true">  
        <div style="margin-left: 5px;margin-right: 5px;margin-top: 5px;">           
            <div class="data-tips-info">  
     
     <div style="margin-top:30px;">   
           订单号：<input id="materialNamez" type="text" placeholder="请输入订单号" class="easyui-validatebox span2" style="width:200px;height:20px" data-options="required:true" value=""/>
     		
     </div>     
            <div style="text-align:right;margin-right:10px;margin-top:55px;">  
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'ope-finish'" onclick="restartNetwork()">确定</a>  
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'ope-cancel'" onclick="cancel()">取消</a>  
            </div>                  
        </div>          
    </div> 
</div>


</body>
	
		<script language="javascript" type="text/javascript"> 
		function showRestartDialog(){   
			$("#materialNamez").val("");
			$("#restartDialog").css("display","block");
	        $("#restartDialog").dialog('open');  
	    }  
	      
	    function restartNetwork(){  
	    	var orderNo = $("#materialNamez").val();
	    	console.log(orderNo);
			$.ajax({
					type: "POST",
					url: "${pageContext.request.contextPath}/invoice/checkOrderNo?orderNo="+orderNo,
					dataType:"json",
					success: function(j){
	 				if(j.success) {
	 					var url = "${pageContext.request.contextPath}/invoice/addinvoicePage?orderNo="+orderNo;
	 			    	 var title = "invoice："+orderNo+"(添加)";
	 					 window.parent.addTab({
	 						url : url,
	 						title : title,
	 						id : "addinvoice" 
	 					 }); 
	 					 $('#restartDialog').window('close');          
	 				} else {
	 					parent.$.messager.alert('提示', j.msg, 'error');
	 				}
	 				
					}
					
				});
	        cancel();  
	    }  
	      
	    function cancel(){  
	        $('#restartDialog').window('close');          
	    }   
</script>
	
</html>