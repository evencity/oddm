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
	.datagrid-view .datagrid-body {overflow-y: auto;overflow-x: hidden;}
	.color_F60 {color: #F60}
	.color_green {color: green;}
	a{cursor: pointer;text-decoration: none;}
	.autoInput{width: 95%}
	input {border:1px solid #CCC;font-size:12px;height:19px;border-radius: 4px;}
</style> 
<c:if test="${fn:contains(sessionInfo.resourceList, '/order/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/order/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>

<title>订单信息</title>
	<script type="text/javascript">
	var dataGrid;
	$(function() {
		var myview = $.extend({},$.fn.datagrid.defaults.view,{
		    onAfterRender:function(target){
		        $.fn.datagrid.defaults.view.onAfterRender.call(this,target);
		        var opts = $(target).datagrid('options');
		        var vc = $(target).datagrid('getPanel').children('div.datagrid-view');
		        vc.children('div.datagrid-empty').remove();
		        if (!$(target).datagrid('getRows').length){
		            var d = $('<div class="datagrid-empty"></div>').html(opts.emptyMsg || 'no records').appendTo(vc);
		            d.css({
		                position:'absolute',
		                left:0,
		                top:50,
		                width:'100%',
		                textAlign:'center'
		            });
		        }
		    }
	});
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}/orderReview/dataGrid',
			striped : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'desc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200],
			view: myview,
            emptyMsg: '没有找到相关数据',
			onLoadSuccess:function(data){ 
				
            },
            onDblClickRow : function(index, row){
            	showOrderFun(row.id,row.orderNo,row.sellerId)
	        },
            rownumbers : true,
			frozenColumns : [ [ {
				field:'ck',
				checkbox:true,
				width : '5%'
			},{
				width : '12%',
				title : '订单号',
				field : 'orderNo',
				formatter : function(value, row, index) {
					if(row.pid ==  null){
						return value + '<span class="color_F60">[新单]</span>';
					}else{
						return value;
					} 
				}
			},/*  {
				width : '13%',
				title : '创建时间',
				field : 'timestamp',
				sortable : true
			}, */ {
				field : 'updatetime',
				title : '更新时间',
				width : '15%',
				sortable : true
			}, {
				width : '15%',
				title : '客户名称',
				field : 'clientNameCode',
			},{
				width : '15%',
				title : '客户机型',
				field : 'productClient'
			} ,{
				width : '10%',
				title : '工厂机型',
				field : 'productFactory'
			} ,{
				width : '8%',
				title : '所属业务',
				field : 'seller'
			},{
				width : '8%',
				title : '所属跟单',
				field : 'merchandiser'
			},{
				width : '15%',
				title : '操作',
				field : 'state',
				formatter : function(value, row, index) {
					switch (value) {
						case 1:
							var str = '&nbsp;';
							str += $.formatString('<a href="javascript:void(0)" onclick="showOrderFun(\'{0}\',\'{1}\',\'{2}\');" >未编辑</a>', row.id,row.orderNo,row.sellerId);
							return str;
						case 2:
							var str = '&nbsp;';
							str += $.formatString('<a href="javascript:void(0)" onclick="showOrderFun(\'{0}\',\'{1}\',\'{2}\');" ><span class="color_F60">提交订单</span></a>', row.id,row.orderNo,row.sellerId);
							return str;
						case 3:
							var str = '&nbsp;';
							str += $.formatString('<a href="javascript:void(0)" onclick="showOrderFun(\'{0}\',\'{1}\',\'{2}\');" ><span class="color_green">审核订单</span></a>', row.id,row.orderNo,row.sellerId);
							return str;
						case 4:
							var str = '&nbsp;';
							str += $.formatString('<a href="javascript:void(0)" onclick="showOrderFun(\'{0}\',\'{1}\',\'{2}\');" ><span class="color_F60">审核不通过</span></a>', row.id,row.orderNo,row.sellerId);
							return str;
						case 5:
							var str = '&nbsp;';
							str += $.formatString('<a href="javascript:void(0)" onclick="showOrderFun(\'{0}\',\'{1}\',\'{2}\');" ><span class="color_green">审核通过</span></a>', row.id,row.orderNo,row.sellerId);
							return str;
						case 6:
							var str = '&nbsp;';
							str += $.formatString('<a href="javascript:void(0)" onclick="showOrderFun(\'{0}\',\'{1}\',\'{2}\');" >完结</a>', row.id,row.orderNo,row.sellerId);
							return str;
					}
				}
			}/* , {
				field : 'action',
				title : '操作',
				width : '12%',
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" onclick="showOrderFun(\'{0}\',\'{1}\');" >查看</a>', row.id,row.orderNo);
					return str;
				}
			} */ ] ],
			toolbar : '#toolbar'
		});
		
		$("body").keydown(function(event) {
		    if (event.keyCode == "13") {//keyCode=13是回车键
		    	var orderNo = $('#orderNo').val().trim();
				var clientName = $('#clientName').val().trim();
				var productClient = $('#productClient').val().trim();
				var seller = $('#seller').val().trim();
				var productFactory = $('#productFactory').val().trim();
				if(orderNo == "" && clientName == "" && productClient == "" && seller == "" && productFactory == ""){
					parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
				}else{
					 dataGrid.datagrid('load', {
						orderNo:orderNo,
						clientName:clientName,
						productClient:productClient,
						productFactory:productFactory,
						seller:seller
					}); 
				}   
		    }
		});
	});
	
	
	function showOrderFun(id,orderNo,sellerId){
		var url = '';
		if(sellerId == null || typeof(sellerId) == 'undefined'){
	  		 url = "${ctx}/order/getPage?id="+id;
	  	}else{
	  		 url = "${ctx}/order/getPage?id="+id+"&sellerId="+sellerId;
	  	}
	  	
	  	var title = "订单:"+orderNo;
		window.parent.addTab({
			url : url,
			title : title,
		});
	}
	
	function reviewOrderFun() {
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}

		parent.$.modalDialog({
			title : '订单审核：'+row.orderNo,
			width : 300,
			height : 160,
			href : '${ctx}/order/orderReviewPage',
			buttons : [ {
				text : '提交',
				handler : function() {
					var state = parent.$("input[name='orderPassOrNo']:checked").val();
					if(state != 'undefined'){
						/* $.ajax({
				         	type: 'POST',
				            url: "${ctx}/order/orderReview?orderId=" + row.id +"&state=" + state ,
				            dataType: "json",
				            
				            contentType: "application/json; charset=utf-8",
							success : function(data) {
								if (data.success) {
									parent.$.messager.alert('提示', result.msg, 'info');
									dataGrid.datagrid('clearSelections');
									dataGrid.datagrid('reload');
								}else{
									parent.$.messager.alert('提示', data.msg, 'info');
								}
							}
						})
						parent.$.modalDialog.handler.dialog('close'); */
						progressLoad();
						$.post('${ctx}/order/orderReview', {
							orderId : row.id,
							state : state
						}, function(result) {
							if (result.success) {
								parent.$.messager.alert('提示', result.msg, 'info');
								dataGrid.datagrid('clearSelections');
								dataGrid.datagrid('reload');
							} else {
								parent.$.messager.alert('错误', result.msg, 'error');
							}
							progressClose();
						}, 'JSON');
						parent.$.modalDialog.handler.dialog('close');
					}
				}
			} ]
		});
	}
	
	function searchFun() {
		var orderNo = $('#orderNo').val().trim();
		var clientName = $('#clientName').val().trim();
		var productClient = $('#productClient').val().trim();
		var seller = $('#seller').val().trim();
		var productFactory = $('#productFactory').val().trim();
		if(orderNo == "" && clientName == "" && productClient == "" && seller == "" && productFactory == ""){
			parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
		}else{
			 dataGrid.datagrid('load', {
				orderNo:orderNo,
				clientName:clientName,
				productClient:productClient,
				productFactory:productFactory,
				seller:seller
			}); 
		}  
	}
	function cleanFun() {
		$('#orderNo').val('');
		$('#clientName').val('');
		$('#productClient').val('');
		$('#seller').val('');
		$('#productFactory').val('');
		//dataGrid.datagrid('load', {});
	}
	
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	 <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
		<!-- <form id="searchForm"> -->
			<table>
				<tr>
					<th>订单编号:</th>
					<td><input name="orderNo" placeholder="请输入订单编号" id="orderNo" class="autoInput"/></td>
					<th>客户名称:</th>
					<td><input name="clientName" placeholder="请输入客户名称" id="clientName" class="autoInput"/></td>
					<th>工厂机型:</th>
					<td><input name="productFactory" placeholder="请输入工厂机型" id="productFactory" class="autoInput"/></td>
					<th>客户机型:</th>
					<td><input name="productClient" placeholder="请输入客户机型" id="productClient" class="autoInput"/></td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
					</td> 
				</tr>
				<tr>
					<th>所属业务:</th>
					<td><input name="seller" placeholder="请输入业务名称" id="seller" class="autoInput"/></td> 
				</tr>
			</table>
		<!-- </form> -->
	</div> 
	
	<div data-options="region:'center',border:false" style="top:60px">
		<table id="dataGrid" data-options="fit:true,border:true" ></table>
	</div> 
	<div id="toolbar" style="display: none;">
			<a onclick="reviewOrderFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">审核</a>
	</div>
</body>
</html>