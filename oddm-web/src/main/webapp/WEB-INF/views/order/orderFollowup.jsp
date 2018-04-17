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
	.color_F60 {color: #F60}
	.color_green {color: green;}
	.color_blue {color: blue;}
	a{cursor: pointer;text-decoration: none;}
	.datagrid-cell {   /* easyui不会自动识别英文字符串自动换行，nowrap: false不生效 */
		white-space: pre-wrap;
		word-wrap: break-word;
		word-break:break-all;
		line-height: 18px;
	}
	input {border:1px solid #CCC;font-size:12px;height:19px;border-radius: 4px;}
</style>
<c:if test="${fn:contains(sessionInfo.resourceList, '/orderFollowup/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/orderFollowup/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>

<title>跟单信息</title>
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
			url : '${ctx}/orderFollowup/dataGrid',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'desc',
			nowrap:false,
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200 ],
			view: myview,
            emptyMsg: '没有找到相关数据',
            onDblClickRow : function(index, row){
            	showOrderFollowupFun(row.id,row.orderInfoDTO.orderNo,row.orderInfoDTO.id);
	        },
			frozenColumns : [ [ {
				field:'ck',
				checkbox:true,
			}, {
				width : '3%',
				title : '序号',
				field : 'id',
				hidden:true
			},  {
				width : '10%',
				title : '订单号',
				field : 'orderNo',
				formatter:function(value,row,index){
                    return row.orderInfoDTO.orderNo;
                }
			},   {
				width : '14%',
				title : '更新时间',
				field : 'updatetime',
				sortable : true,
			} ,{
				width : '12%',
				title : '客户名称',
				field : 'clientName',
				formatter:function(value,row,index){
                    return row.orderInfoDTO.clientName;
                }
			},{
				width : '10%',
				title : '客户型号',
				field : 'bizName',
				formatter:function(value,row,index){
                    return row.orderInfoDTO.productClient;
                }
			} ,{
				width : '8%',
				title : '产品型号',
				field : 'productFactory',
				formatter:function(value,row,index){
                    return row.orderInfoDTO.productFactory;
                }
			}, {
				width : '8%',
				title : '业务员',
				field : 'seller',
				formatter:function(value,row,index){
                    return row.orderInfoDTO.seller;
                }
			} ,{
				width : '8%',
				title : '跟单员',
				field : 'merchandiser',
				formatter:function(value,row,index){
                    return row.orderInfoDTO.merchandiser;
                }
			} /* , {
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
			} */ ,{
				width : '8%',
				title : '客户交期',
				field : 'dateClient'
			},{
				width : '8%',
				title : '工厂交期',
				field : 'dateFactory'
			},{
				field : 'state',
				title : '状态',
				width : '6%',
				formatter : function(value, row, index) {
					switch (value) {
					case 1:
						var str = '&nbsp;';
						str += '<span class="color_blue">未编辑<span>';
						return str;
					case 2:
						var str = '&nbsp;';
						str += '<span class="color_F60">已提交<span>';
						return str;
					case 3:
						var str = '&nbsp;';
						str += '<span class="color_green">完结<span>';
						return str;
					}
					
				}
			},{
				field : 'action',
				title : '操作',
				width : '5%',
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					str += $.formatString('<img onclick="showOrderFollowupFun(\'{0}\',\'{1}\',\'{2}\');" src="{3}" title="查看" style="cursor:pointer"/></img>', row.id,row.orderInfoDTO.orderNo,row.orderInfoDTO.id,"${ctx}/jslib/easyui1.4.2/themes/icons/search.png");
					return str;
				}
			} ] ],
			toolbar : '#toolbar'
		});
		
		$("body").keydown(function(event) {
		    if (event.keyCode == "13") {//keyCode=13是回车键
		    	/* dataGrid.datagrid('load', $.serializeObject($('#searchForm'))); */
				var orderNo = $('#orderNo').val().trim();
				var clientName = $('#clientName').val().trim();
				var productClient = $('#productClient').val().trim();
				var productFactory = $('#productFactory').val().trim();
				var merchandiser = $('#merchandiser').val().trim();
				var seller = $('#seller').val().trim();
				if(orderNo == "" && clientName == "" && productClient == "" && merchandiser == "" && productFactory == "" && seller ==''){
					parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
				}else{
					 dataGrid.datagrid('load', {
						orderNo:orderNo,
						clientName:clientName,
						productClient:productClient,
						productFactory:productFactory,
						merchandiser:merchandiser,
						seller:seller
					}); 
				}   
		    }
		});
	});
	
	function addOrderFollowupFun(){
		parent.$.modalDialog({
			title : '查询订单',
			width : 300,
			height : 160,
			href : '${ctx}/orderFollowup/checkOrderNoPage',
			buttons : [ {
				text : '确定',
				handler : function() {
					var orderNo = parent.$('#orderNoForOrderfollow').val().trim();
					if(orderNo == null || "" == orderNo){
						parent.$.messager.alert('提示', '订单号不能为空！', 'info');
						return;
					}
					$.ajax({
						type: 'POST',
					    url: "${ctx}/orderFollowup/checkOrderForOrder?orderNo="+orderNo, 
					    dataType: "json",
					    contentType: "application/json; charset=utf-8",
					    success: function(data){
					    	if(data.success){
								//订单主表中订单号存在，则继续查询跟单表中是否存在数据					    		
					    		console.log(data.msg)
					    		$.ajax({
									type: 'POST',
								    url: "${ctx}/orderFollowup/checkOrderForOrderfollow?orderNo="+orderNo, 
								    dataType: "json",
								    contentType: "application/json; charset=utf-8",
								    success: function(data){
								    	if(data.success){
								    		parent.$.messager.alert('提示', data.msg, 'info');
								    	}else{
								    		//若订单号未被绑定，则进行添加
								    		var url = "${ctx}/orderFollowup/addPage?orderNo="+orderNo;
								    	  	var title = "添加跟单："+ orderNo;
								    		window.parent.addTab({
								    			url : url,
								    			title : title,
								    		});
								    		parent.$.modalDialog.handler.dialog('close');
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
	  	/* var url = "${ctx}/orderFollowup/addPage?";
	  	var title = "添加跟单："+ o;
		window.parent.addTab({
			url : url,
			title : title,
		}); */
	}
	
	function deleteOrderFollowupFun(){
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		if (row) {
			parent.$.messager.confirm('询问', '您是否要删除物料交期记录!', function(b) {
				if (b) {
					progressLoad();
					$.post('${ctx}/orderFollowup/delete', {
						id : row.id
					}, function(result) {
						if (result.success) {
							parent.$.messager.alert('提示', result.msg, 'info');
							dataGrid.datagrid('clearSelections');
							dataGrid.datagrid('reload');
						}else{
							parent.$.messager.alert('提示', result.msg, 'info');
						}
						progressClose();
					}, 'JSON');
				}
			});
		}
	} 

	function editOrderFollowupFun(){
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		/* if(row.state == 3){
			parent.$.messager.alert('提示', '该跟单已经完结！', 'info');
			return;
		} */
	  	var url = "${ctx}/orderFollowup/editPage?id="+row.id;
	  	var title = "编辑跟单："+ row.orderInfoDTO.orderNo;
		window.parent.addTab({
			url : url,
			title : title,
		});
	}
	
	function showOrderFollowupFun(id,orderNo,orderId){
	  	var url = "${ctx}/orderFollowup/getPage?id="+id;
	  	var title = "跟单:"+orderNo;
		window.parent.addTab({
			url : url,
			title : title,
			id : 'orderFollowup_'+orderId
		});
	}
	
	function excelOrderFollowupFun(){
		parent.$.modalDialog({
			title : '导出跟单Excel',
			width : 400,
			height : 250,
			href : '${ctx}/orderFollowup/excelPage',
			buttons : [ {
				text : '确定',
				handler : function() {
					var orderFollowExcel = parent.$("input[name=orderFollowExcel]:checked").val()
					if(orderFollowExcel == null || typeof(orderFollowExcel) == 'undefined'){
						parent.$.messager.alert('提示', '请选择导出内容！', 'info');
						return;
					}
					var seller = parent.$('#seller').val();
					if(seller != null || typeof(seller) != 'undefined'){
						seller = seller.trim();
					}
					var clientName = parent.$('#clientName').val();
					if(clientName != null || typeof(clientName) != 'undefined'){
						clientName = clientName.trim();
					}
					var merchandiser = parent.$('#merchandiser').val();
					if(merchandiser != null || typeof(merchandiser) != 'undefined'){
						merchandiser = merchandiser.trim();
					}
					var dateOrderStart = parent.$('#dateOrderStart').datebox('getValue');
					if(dateOrderStart == null || typeof(dateOrderStart) == 'undefined'){
						dateOrderStart = '';
					}
					var dateOrderEnd = parent.$('#dateOrderEnd').datebox('getValue');
					if(dateOrderEnd == null || typeof(dateOrderEnd) == 'undefined'){
						dateOrderEnd = '';
					}
					download_file("${ctx}/orderFollowup/excelExport?state="+orderFollowExcel+"&seller="+seller+"&clientName="+clientName+"&merchandiser="+merchandiser+"&dateOrderStart="+dateOrderStart+"&dateOrderEnd="+dateOrderEnd);
					parent.$.modalDialog.handler.dialog('close');
				}
			} ]
		});
	}
	function download_file(url)  
    {  
        if (typeof (download_file.iframe) == "undefined")  
        {  
            var iframe = document.createElement("iframe");  
            download_file.iframe = iframe; 
            document.body.appendChild(download_file.iframe);  
            download_file.iframe.addEventListener( "load", function(){
    	         //代码能执行到这里说明已经载入成功完毕了
    	           this.removeEventListener( "load", arguments.call, false);
    	         //这里是回调函数
    	         parent.parent.$.messager.alert('提示', '导出Excel出错，请联系管理员！');
    	   }, false);
        }  
        
    	download_file.iframe.src = url;  
        download_file.iframe.style.display = "none";  
    } 
	function searchFun() {
		/* dataGrid.datagrid('load', $.serializeObject($('#searchForm'))); */
		var orderNo = $('#orderNo').val().trim();
		var clientName = $('#clientName').val().trim();
		var productClient = $('#productClient').val().trim();
		var productFactory = $('#productFactory').val().trim();
		var merchandiser = $('#merchandiser').val().trim();
		var seller = $('#seller').val().trim();
		if(orderNo == "" && clientName == "" && productClient == "" && merchandiser == "" && productFactory == "" && seller ==''){
			parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
		}else{
			 dataGrid.datagrid('load', {
				orderNo:orderNo,
				clientName:clientName,
				productClient:productClient,
				productFactory:productFactory,
				merchandiser:merchandiser,
				seller:seller
			}); 
		}   
	}
	function cleanFun() {
		orderNo:$('#orderNo').val('');
		clientName:$('#clientName').val('');
		productClient:$('#productClient').val('');
		productFactory:$('#productFactory').val('');
		merchandiser:$('#merchandiser').val('');
		seller:$('#seller').val('');
		//dataGrid.datagrid('load', {});
	}
	
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	 <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
		<!-- <form id="searchForm"> -->
			<table>
				<tr>
					<th>订单编号</th>
					<td><input name="orderNo" placeholder="请输入订单编号" id="orderNo"/></td>
					<th>客户名称</th>
					<td><input name="clientName" placeholder="请输入客户名称" id="clientName"/></td>
					<th>工厂机型</th>
					<td><input name="productFactory" placeholder="请输入工厂机型" id="productFactory"/></td>
					<th>客户机型</th>
					<td><input name="productClient" placeholder="请输入客户机型" id="productClient"/></td>
					<td>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
				</tr>
				<tr>
					<th>跟单员</th>
					<td><input name="merchandiser" placeholder="请输入跟单员名称" id="merchandiser"/></td>
					<th>业务员</th>
					<td><input name="seller" placeholder="请输入跟单员名称" id="seller"/></td>
				</tr>
			</table>
		<!-- </form> -->
	</div> 
	
	<div data-options="region:'center',border:false" style="top: 60px">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div> 
	<div id="toolbar" style="display: none;">
		
		<c:if test="${fn:contains(sessionInfo.resourceList, '/orderFollowup/add')}">
			<a onclick="addOrderFollowupFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/orderFollowup/edit')}">
			<a onclick="editOrderFollowupFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">编辑</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/orderFollowup/delete')}">
			<a onclick="deleteOrderFollowupFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-del'">删除</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/orderFollowup/excel')}">
			<a onclick="excelOrderFollowupFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">导出Excel</a>
		</c:if>
	</div>
</body>
</html>