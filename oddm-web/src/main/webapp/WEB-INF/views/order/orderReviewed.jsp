<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<style type="text/css">
	.datagrid-view1 .datagrid-body {overflow-y: auto;overflow-x: hidden;}
</style>

<title>订单信息</title>
	<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}/docReviewed/dataGrid',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'asc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200],
			onLoadSuccess : function(data) {  
                if (data.total == 0) {  
                	parent.$.message.alert('信息','查询无数据');
                	searchFun();
                }  
            } ,
            frozenColumns : [ [{
				width : '10%',
				title : 'NO.',
				field : 'id',
				sortable : true,
				hidden:true
			}, {
				width : '10%',
				title : '版本',
				field : 'version',
				formatter:function(value,row,index){
                    return "V"+row.version;
                }
			}, {
				title : '文档名',
				field : 'nameMt',
				width : '40%',
				formatter:function(value,row,index){
					var docName = row.orderInfoDTO.productFactory + row.nameMt + row.orderInfoDTO.orderNo + "("+ row.orderInfoDTO.productClient +")V"+row.version;
                    return docName;
                }
			},  {
				width : '20%',
				title : '上传时间',
				field : 'uploadtime'
			} , {
				field : 'state',
				title : '操作',
				width : '30%',
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					var docName = row.orderInfoDTO.productFactory + row.nameMt + row.orderInfoDTO.orderNo + "("+ row.orderInfoDTO.productClient +")";
					if(row.state == 2){
						str += $.formatString('<a href="javascript:void(0)" onclick="docReviewed_reviewed(\'{0}\',\'{1}\',\'{2}\');" >审核文档</a>', row.id, row.orderInfoDTO.id, docName);
					}
					if(row.state == 4){
						str = "审核未通过";
					}
					return str;
				}
			} ] ],
			toolbar : '#toolbar'
		});
		
	});
	
	
	function docReviewed_reviewed(id,orderId,docName){
		parent.$.modalDialog({
			title : '文档审核',
			width : 300,
			height : 160,
			href : '${ctx}/docReviewed/reviewPage?docName='+docName,
			buttons : [ {
				text : '提交',
				handler : function() {
					var state = parent.$("input[name='passOrNo']:checked").val();
					$.ajax({
			         	type: 'POST',
			            url: "${ctx}/docReviewed/review?id=" + id +"&pass=" + state +"&orderId="+orderId,
			            dataType: "json",
			            contentType: "application/json; charset=utf-8",
						success : function(data) {
							if (data.success) {
								parent.$.messager.alert('提示', data.msg, 'info');
								parent.$.modalDialog.handler.dialog('close');
								var parent_tabs = parent.$('#index_tabs');
								parent_tabs.tabs('update', {
									tab : parent_tabs.tabs('getSelected'), //获取当前被选中的页面
									options : {}
									}
								);
							}else{
								parent.$.messager.alert('提示', data.msg, 'info');
							}
						}
					})
					
				}
			} ]
		});
	}
	
	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
	
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	 <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
		<form id="searchForm">
			<table>
				<tr>
					<th>文档名：</th>
					<td><input name="documentName" placeholder="请输入文档名"/></td>
					<td>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
				</tr>
			</table>
		</form>
	</div> 
	
	<div data-options="region:'center',border:false">
		<table id="dataGrid" data-options="fit:true,border:true" ></table>
	</div> 
	<div id="toolbar" style="display: none;">
		
	</div>
</body>
</html>