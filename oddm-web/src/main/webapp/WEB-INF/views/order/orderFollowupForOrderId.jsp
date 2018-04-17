<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />

<title>跟单信息</title>
	<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}/orderFollowup/dataGrid',
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
                	$.message.alert('信息','查询无数据');
                	searchFun();
                }  
            } ,
			frozenColumns : [ [ {
				field:'ck',
				checkbox:true,
			}, {
				width : '3%',
				title : '序号',
				field : 'id',
				hidden:true
			},   {
				width : '10%',
				title : '客户名称',
				field : 'clientName',
				formatter:function(value,row,index){
                    return row.orderInfoDTO.clientName;
                }
			},{
				width : '10%',
				title : '订单号',
				field : 'orderNo',
				formatter:function(value,row,index){
                    return row.orderInfoDTO.orderNo;
                }
			},  {
				width : '7%',
				title : '销售员',
				field : 'seller',
				sortable : true
			},{
				width : '8%',
				title : '跟单员',
				field : 'merchandiser'
			} ,{
				width : '7%',
				title : '产品型号',
				field : 'productFactory',
				formatter:function(value,row,index){
                    return row.orderInfoDTO.productFactory;
                }
			} ,{
				width : '8%',
				title : '客户型号',
				field : 'bizName',
				formatter:function(value,row,index){
                    return row.orderInfoDTO.productClient;
                }
			} , {
				width : '7%',
				title : '方案',
				field : 'plan',
			} ,{
				width : '8%',
				title : '订单数量/pcs',
				field : 'quantity',
				formatter:function(value,row,index){
                    return row.orderInfoDTO.quantity;
                }
			} ,{
				width : '8%',
				title : '客户交期',
				field : 'dateClient'
			},{
				width : '7%',
				title : '工厂交期',
				field : 'dateFactory'
			},{
				field : 'status',
				title : '状态更新',
				width : '7%',
			},{
				field : 'action',
				title : '操作',
				width : '8%',
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" onclick="showOrderFollowupFun(\'{0}\',\'{1}\');" >查看订单</a>', row.id,row.orderInfoDTO.orderNo);
					return str;
				}
			} ] ],
			toolbar : '#toolbar'
		});
	});
	
	function editOrderFollowupFun(id,orderNo){
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
	  	var url = "${ctx}/orderFollowup/editPage?id="+row.id;
	  	var title = "编辑跟单："+ row.orderInfoDTO.orderNo;
		window.parent.addTab({
			url : url,
			title : title,
		});
	}
	function showOrderFollowupFun(id,orderNo){
	  	var url = "${ctx}/orderFollowup/getPage?id="+id;
	  	var title = "跟单："+orderNo;
		window.parent.addTab({
			url : url,
			title : title,
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
	 <!-- <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
		<form id="searchForm">
			<table>
				<tr>
					<th>订单编号</th>
					<td><input name="orderNo" placeholder="请输入订单编号"/></td>
					<th>客户名称</th>
					<td><input name="clientName" placeholder="请输入客户名称"/></td>
					<th>客户机型</th>
					<td><input name="productClient" placeholder="请输入客户机型"/></td>
					<th>跟单员</th>
					<td><input name="merchandiser" placeholder="请输入跟单员名称"/></td>
					<td>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>  -->
	
	<div data-options="region:'center',border:false">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div> 
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/orderFollowup/edit')}">
			<a onclick="editOrderFollowupFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">编辑</a>
		</c:if>
	</div>
</body>
</html>