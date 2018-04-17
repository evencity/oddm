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
	.color_blue {color: blue;}
	a{cursor: pointer;text-decoration: none;}
	.autoInput{width: 95%}
	.datagrid-cell {   /* easyui不会自动识别英文字符串自动换行，nowrap: false不生效 */
		white-space: pre-wrap;
		word-wrap: break-word;
		word-break:break-all;
		line-height: 18px;
	}
	input {border:1px solid #CCC;font-size:12px;height:19px;border-radius: 4px;}
</style> 
<c:if test="${fn:contains(sessionInfo.resourceList, '/manufacture/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/manufacture/delete')}">
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
			url : '${ctx}/manufacture/dataGrid',
			striped : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'desc',
			nowrap:false,
			onDblClickRow : function(index, row){
				showManufactureFun(row.id,row.orderNo);
	        },
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200 ],
			view: myview,
            emptyMsg: '没有找到相关数据',
			
            rownumbers : true,
			frozenColumns : [ [ {
				field:'ck',
				checkbox:true,
				width : '5%'
			}, {
				width : '5%',
				title : '版本号',
				field : 'version',
			}, {
				width : '10%',
				title : '生产订单号',
				field : 'orderNo',
			}, {
				width : '13%',
				title : '更新时间',
				field : 'updatetime',
				sortable : true,
			} ,{
				width : '13%',
				title : '客户名称',
				field : 'clientName',
			},{
				width : '10%',
				title : '发行日期',
				field : 'dateIssue'
			} ,{
				width : '10%',
				title : '出货日期',
				field : 'dateShipment'
			},{
				width : '9%',
				title : '拟稿',
				field : 'drafter'
			},{
				width : '9%',
				title : '审核',
				field : 'auditor'
			},{
				width : '12%',
				title : '状态',
				field : 'state',
				sortable : true,
				formatter : function(value, row, index) {
					var unread = "";
					if(row.unread == 1){
						unread = '<span class="color_F60">[未读]</span>';
					}else{
						isRead = '';
					} 
					switch (value) {
						case 1:
							return '<span class="color_blue">未编辑</span>';
						case 2:
							return '<span class="color_F60">待审核'+unread+'</span>';
						case 3:
							var str = '&nbsp;';
							
							return '<span class="color_blue">待审核'+unread+'</span>';
						case 4:
							
							return '<span class="color_F60">审核不通过'+unread+'</span>';
						case 5:
							
							return '<span class="color_green">审核通过'+unread+'</span>';
						case 6:
						
							return '<span class="color_green">完结'+unread+'</span>';
					}
				}
			} ,{
				field : 'action',
				title : '操作',
				width : '6%',
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					str += $.formatString('<img onclick="showManufactureFun(\'{0}\',\'{1}\');" src="{2}" title="查看" style="cursor:pointer"/></img>', row.id,row.orderNo,"${ctx}/jslib/easyui1.4.2/themes/icons/search.png");
					//str += $.formatString('<img onclick="deleteManufactureFun(\'{0}\',\'{1}\');" src="{2}" title="查看" style="cursor:pointer"/></img>', row.id,row.orderNo,"${ctx}/jslib/easyui1.4.2/themes/icons/del.png");
					return str;
				}
			}   ] ],
			toolbar : '#toolbar'
		});
		
		$("body").keydown(function(event) {
		    if (event.keyCode == "13") {//keyCode=13是回车键
		    	var orderNo = $('#orderNo').val().trim();
				var clientName = $('#clientName').val().trim();
				if(orderNo == "" && clientName == ""){
					parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
				}else{
					dataGrid.datagrid('load', {
						orderNo:orderNo,
						clientName:clientName,
					}); 
				} 
		    }
		});
	});
	/* function deleteManufactureFun(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要删除当前选项？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/manufacture/delete', {
					id : id
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('reload');
					} else {
						parent.$.messager.alert('错误', result.msg, 'error');
					}
					progressClose();
				}, 'JSON');
			}
		});
	} */
	function deleteManufactureFun(id) {
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		parent.$.messager.confirm('询问', '您是否要删除当前选项？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/manufacture/delete', {
					id : row.id
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
			}
		});
	}
	
	function showManufactureFun(id,orderNo){
	  	var url = "${ctx}/manufacture/getPage?id="+id;
	  	var title = "指导书:"+orderNo;
		window.parent.addTab({
			url : url,
			title : title,
		});
	}
	function addManufactureFun(){
		parent.$.modalDialog({
			title : '查询订单',
			width : 300,
			height : 160,
			href : '${ctx}/manufacture/checkOrderNoPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					var orderNo = parent.$('#orderNoForManufacture').val().trim();
					if(orderNo == null || "" == orderNo){
						parent.$.messager.alert('提示', '订单号不能为空！', 'info');
						return;
					}
					$.ajax({
						type: 'POST',
					    url: "${ctx}/manufacture/checkOrderForOrder?orderNo="+orderNo, 
					    dataType: "json",
					    contentType: "application/json; charset=utf-8",
					    success: function(data){
					    	if(data.success){
								//订单主表中订单号存在，则继续查询跟单表中是否存在数据
								$.ajax({
									type: 'POST',
								    url: "${ctx}/manufacture/getManufactureByOrderNo?orderNo="+orderNo, 
								    dataType: "json",
								    contentType: "application/json; charset=utf-8",
								    success: function(data){
								    	if(data.success){
											//订单主表中订单号存在，则继续查询跟单表中是否存在数据
								    		parent.$.messager.alert('提示', data.msg,  'info');
								    	}else{
								    		parent.$.messager.confirm('询问', '您已选择订单,确定后将生成指导书,是否继续？', function(b) {
								    			if (b) {
								    				var url = "${ctx}/manufacture/addPage?orderNo="+orderNo;
										    	  	var title = "编辑指导书:"+ orderNo;
										    	  //	parent.$.messager.alert('提示', "添加成功",  'info');
										    	  // 消息将显示在顶部中间
													parent.$.messager.show({
														title:'消息提示',
														msg:'<span style="color:green;font-size:1.2em">添加成功！！</span>',
														timeout:500,
														showType:'fade',
														style:{
															right:'',
															top:$(window).height()/2,
															bottom:''
														}
													});
										    		window.parent.addTab({
										    			url : url,
										    			title : title,
										    		});
										    		parent.$.modalDialog.handler.dialog('close');
								    			}
								    		});
								    		
								    	}
								    }
								})
					    	}else{
					    		parent.$.messager.alert('提示', data.msg,  'info');
					    	}
					    }
					})
				}
			} ]
		});
		
	}
	function editManufactureFun(){
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		/* if(row.state == 5){
			parent.$.messager.alert('提示', '此单已经审核通过！', 'info');
			return;
		} */
	  	var url = "${ctx}/manufacture/editPage?id="+row.id;
	  	var title = "编辑指导书:"+ row.orderNo ;
		window.parent.addTab({
			url : url,
			title : title,
		});
		
	}
		
	function searchFun() {
		var orderNo = $('#orderNo').val().trim();
		var clientName = $('#clientName').val().trim();
		if(orderNo == "" && clientName == ""){
			parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
		}else{
			
			dataGrid.datagrid('load', {
				orderNo:orderNo,
				clientName:clientName,
			}); 
		} 
	}
	function searchForNoReadFun() {
		dataGrid.datagrid('load', {
			unread:1
		});
	}
	function cleanFun() {
		$('#orderNo').val('');
		$('#clientName').val('');
	//	dataGrid.datagrid('load', {});
	}
	
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	 <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
		<!-- <form id="searchForm"> -->
			<table>
				<tr>
					<th>订单编号:</th>
					<td><input name="orderNo" placeholder="请输入订单编号" id="orderNo" class="autoInput"/></td>
					<th>客户名称:</th>
					<td><input name="orderNo" placeholder="请输入客户名称" id="clientName" class="autoInput"/></td>
					 <td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchForNoReadFun();">未读指导书</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
					</td> 
				</tr>
			</table>
		<!-- </form> -->
	</div> 
	
	<div data-options="region:'center',border:false" style="top: 30px">
		<table id="dataGrid" data-options="fit:true,border:true" ></table>
	</div> 
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/manufacture/add')}">
			<a onclick="addManufactureFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/manufacture/edit')}">
			<a onclick="editManufactureFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">编辑</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/manufacture/edit')}">
			<a onclick="deleteManufactureFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-del'">删除</a>
		</c:if>
		
	</div>
</body>
</html>