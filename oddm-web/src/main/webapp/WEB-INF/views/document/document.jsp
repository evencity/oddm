<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<style type="text/css">
	.datagrid-view .datagrid-body {overflow-y: auto;overflow-x: hidden;}
	.color_green{color: green;}
	.autoInput{width: 95%}
	.color_F60 {color: #F60}
	.datagrid-cell {   /* easyui不会自动识别英文字符串自动换行，nowrap: false不生效 */
		white-space: pre-wrap;
		word-wrap: break-word;
		word-break:break-all;
		line-height: 18px;
	}
	input {border:1px solid #CCC;font-size:12px;height:19px;border-radius: 4px;}
</style>
<meta http-equiv="X-UA-Compatible" content="edge" />
<c:if test="${fn:contains(sessionInfo.resourceList, '/document/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/document/delete')}">
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
			url : '${ctx}/document/dataOrderInfoGrid',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'desc',
			nowrap:false,
			onDblClickRow : function(index, row){
				showDocumentByOrderIdFun(row.id,row.orderNo);
	        },
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200],
			view: myview,
            emptyMsg: '没有找到相关数据',
			onLoadSuccess:function(data){ 
				
            },
            frozenColumns : [ [ {
				field:'ck',
				checkbox:true,
				width : '5%'
			},{
				width : '10%',
				title : '序号',
				field : 'id',
				sortable : true,
				hidden:true
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
				width : '13%',
				sortable : true
			}, {
				width : '15%',
				title : '客户名称',
				field : 'clientNameCode',
			},{
				width : '12%',
				title : '客户机型',
				field : 'productClient'
			} ,{
				width : '10%',
				title : '工厂机型',
				field : 'productFactory'
			} ,{
				width : '10%',
				title : '所属业务',
				field : 'seller'
			},{
				width : '10%',
				title : '所属跟单',
				field : 'merchandiser'
			}/* ,{
				width : '10%',
				title : '状态',
				field : 'state',
				formatter : function(value, row, index) {
					switch (value) {
						case 1:
							return '暂存';
						case 2:
							return '待提交审核';
						case 3:
							return '<span class="color_green">已提交审核</span>';
						case 4:
							return '审核不通过';
						case 5:
							return '<span class="color_green">审核通过</span>';
						case 6:
							return '完结';
					}
				}
			} */ ,{
				field : 'action',
				title : '操作',
				width : '15%',
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" onclick="showDocumentByOrderIdFun(\'{0}\',\'{1}\');" >查看资料</a>', row.id,  row.orderNo);
					return str;
				}
			} ] ],
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
	
	
	function showDocumentByOrderIdFun(orderId,orderNo){
	  	var url = "${ctx}/order/getAllDocumentByOrderIdPage?orderId="+orderId;
	  	var title = "订单:"+orderNo+"资料";
		window.parent.addTab({
			url : url,
			title : title,
			id : "document_"+orderId
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
					<td><input name="orderNo" placeholder="请输入订单编号" id="orderNo"  class="autoInput"/></td>
					<th>客户名称:</th>
					<td><input name="clientName" placeholder="请输入客户名称" id="clientName"  class="autoInput"/></td>
					<th>工厂机型:</th>
					<td><input name="productFactory" placeholder="请输入工厂机型" id="productFactory"  class="autoInput"/></td>
					<th>客户机型:</th>
					<td><input name="productClient" placeholder="请输入客户机型" id="productClient"  class="autoInput"/></td>
				 <td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
					</td> 
				</tr>
				<tr>
					<th>所属业务:</th>
					<td><input name="seller" placeholder="请输入业务名称" id="seller"  class="autoInput"/></td> 
				</tr>
			</table>
		<!-- </form> -->
	</div> 
	
	<div data-options="region:'center',border:false" style="top: 60px">
		<table id="dataGrid" data-options="fit:true,border:true" ></table>
	</div> 
	<%-- <div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/document/add')}">
			<a onclick="addOrderFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/document/edit')}">
			<a onclick="editOrderFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">编辑</a>
		</c:if>
	</div> --%>
</body>
</html>