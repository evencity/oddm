<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<style type="text/css">
	.datagrid-view .datagrid-body {overflow-y: auto;overflow-x: hidden;}
	.autoInput{width: 95%}
	.datagrid-cell {   /* easyui不会自动识别英文字符串自动换行，nowrap: false不生效 */
		white-space: pre-wrap;
		word-wrap: break-word;
		word-break:break-all;
		line-height: 18px;
	}	
	input {border:1px solid #CCC;font-size:12px;height:19px;border-radius: 4px;}
</style>
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
			url : '${ctx}' + '/salePo/dataGridPerson',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			nowrap:false,// :当true时，显示数据在同一行上。默认true。 false自动换行显示
			idField : 'id', //编辑、删除操作按钮用到 row.key
			sortName : 'id',
			sortOrder : 'desc',
			onDblClickRow : function(index, row){
            	showDetail(row.id, row.unread==1?1:0, row.orderNo);
	        },
	        view: myview,
	        emptyMsg: '没有找到相关数据',
	        pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200],
			frozenColumns : [ [ {
				field:'ck',
				checkbox:true,
				width : '5%'
			},{
				width : '12%',
				title : '订单号',
				field : 'orderNo',
				align:'left',
			},{
				width : '14%',
				title : '更新时间',
				field : 'updatetime',
				sortable : true,
				align:'left',
				formatter:function(value,row,index){  
	                return dateformatter(value);
                } 
			}, {
				width : '22%',
				title : '客户名称',
				field : 'clientName',
				sortable : true,
				align:'left',
			}, {
				width : '7%',
				title : '销售',
				field : 'seller',
				align:'left',
			}, {
				width : '7%',
				title : '跟单',
				field : 'merchandiser',
				sortable : true,
				align:'left',
			},{
				width : '10%',
				title : 'PI编号',
				field : 'piNo',
				align:'left',
			},{
				width : '10%',
				title : '预计验货日期',
				field : 'dateExaminePre',
				sortable : true,
				align:'left',
				formatter:function(value,row,index){  
	                return dayformatter(value);
                } 
			},{
				width : '11%',
				title : '状态',
				field : 'state',
				align:'left',
				formatter : function(value, row, index) {
					var unreadFlag = "";
					if(row.unread == 1){
						unreadFlag = '<span style="color: #F60">&nbsp;[未读]</span>';
					}
					switch (value) {
					case 1:
						return '待提交审核';
					case 2:
						return '已提交审核';
					case 3:
						return '审核不通过';
					case 4:
						return '审核通过'+unreadFlag;
					}
				},
				styler: function(value,row,index){
					if (value == 4) {
						return 'color: green;';
					} else if (value == 3) {
						return 'color: red;';
					} else if (value == 2) {
						return 'color: blue;';
					} else if (value == 1) {
						return 'color: #F60;';
					}
				}
			} , {
				field : 'action',
				title : '操作',
				width : '4%',
				align:'left',
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					str += $.formatString('<img onclick="showDetail(\'{0}\',\'{1}\',\'{2}\');" src="{3}" title="查看" style="cursor:pointer"/></img>', row.id, row.unread==1?1:0, row.orderNo,"${ctx}/jslib/easyui1.4.2/themes/icons/search.png");
					return str;
				}
			}
		  ] ],
			toolbar : '#toolbar'
		});
		
		$("body").keydown(function(event) {
		    if (event.keyCode == "13") {//keyCode=13是回车键
		    	var piNo = $('#ip_piNo').val().trim();
				var orderNo = $('#ip_orderNo').val().trim();
				var clientName = $('#ip_clientName').val().trim();
				var seller = $('#ip_seller').val().trim();
				if(piNo == "" && orderNo == "" && clientName == "" && seller == ""){
					parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
				}else{
					dataGrid.datagrid('load', {
						piNo:piNo,
						orderNo:orderNo,
						clientName:clientName,
						seller:seller
					}); 
				} 
		    }
		});
	});
	function addFun(){
		var content = $('#poDialogContentOrderNo').html();
		parent.$.modalDialog({
			title : '查询订单',
			width : 300,
			height : 160,
			content: content,
			//href : '${ctx}/salePo/checkOrderNoPage',
			buttons : [ {
				text : '确定',
				handler : function() {
					var orderNo = parent.$('#ip_orderNoForSalePo').val().trim();
					if(orderNo == null || "" == orderNo){
						parent.$.messager.alert('提示', '订单号不能为空！', 'info');
						return;
					}
					$.ajax({
						type: 'POST',
					    url: "${ctx}/salePo/checkOrderForOrderPerson?orderNo="+orderNo, 
					    dataType: "json",
					    contentType: "application/json; charset=utf-8",
					    success: function(data){
					    	if(data.success){
								//订单主表中订单号存在，则继续查询跟单表中是否存在数据					    		
					    		//console.log("orderId: "+data.obj)
					    		var url = "${ctx}/salePo/addPage?orderId="+data.obj;
					    	  	var title = "添加PO:"+orderNo;
					    		window.parent.addTab({
					    			url : url,
					    			title : title,
					    		});
					    		parent.$.modalDialog.handler.dialog('close');
					    	}else{
					    		parent.$.messager.alert('提示', data.msg,  'info');
					    	}
					    }
					})
				}
			} ]
		});
	}
	
	function showDetail(id, unread, title){
	  	var url = "${ctx}/salePo/getPage?id="+id+"&unread="+unread;;
	  	var title = "查看PO:"+title;
  		window.parent.addTab({
  			id : id, // 给iframe赋予一个id，方便从新打开判断是否是相同的单
			url : url,
			title : title,
		});
	}
	function deleteFun() {
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		var id = row.id;
		parent.$.messager.confirm('询问', '您是否要删除当前记录，订单号：'+row.orderNo+'？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/salePo/delete', {
					id : id
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('clearSelections');
						dataGrid.datagrid('reload');
					}
					progressClose();
				}, 'JSON');
			}
		});
	}
	
	function editFun(){
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		var id = row.id;
		var unread = row.unread==1?1:0;
	  	var url = '${ctx}/salePo/editPage?id=' + id+"&unread="+unread;
	  	var title = "编辑PO:"+row.orderNo;
		window.parent.addTab({
			id : id,
			url : url,
			title : title,
		});
		
	}
	function searchFun() {
		var piNo = $('#ip_piNo').val().trim();
		var orderNo = $('#ip_orderNo').val().trim();
		var clientName = $('#ip_clientName').val().trim();
		var seller = $('#ip_seller').val().trim();
		if(piNo == "" && orderNo == "" && clientName == "" && seller == ""){
			parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
		}else{
			dataGrid.datagrid('load', {
				piNo:piNo,
				orderNo:orderNo,
				clientName:clientName,
				seller:seller
			}); 
		} 
	}
	function searchForNoReadFun() {
		dataGrid.datagrid('load', {
			unread:1
		});
	}
	function cleanFun() {
		$('#title').val('');
		//dataGrid.datagrid('load', {});
	}
	
	function excelFun() {
		var row = $('#dataGrid').datagrid('getSelected');
		if (row){
			//console.log('Item ID:'+row.id+"Price:"+row.piNo);
			download_file("${ctx}/salePo/excelExportPerson?id="+row.id);
		} else {
			parent.$.messager.alert('提示', '请选择一行！', 'info');
		}
	}
	function download_file(url) {  
        if (typeof (download_file.iframe) == "undefined") {  
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
	
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
		<table>
			<tr>
				<th>订单编号:</th>
					<td><input name="orderNo" placeholder="请输入订单编号" id="ip_orderNo" class="autoInput"/></td>
				<th>客户名称:</th>
					<td><input name="clientName" placeholder="请输入客户名称" id="ip_clientName" class="autoInput"/></td>
				<th>销售:</th>
					<td><input name="seller" placeholder="请输入业务名称" id="ip_seller" class="autoInput"/></td> 
				<th>PI编号:</th>
					<td><input name="piNo" placeholder="请输入PI编号" id="ip_piNo" class="autoInput"/></td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchForNoReadFun();">未读查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
					</td>
			</tr>
		</table>
	</div>
	
	<div data-options="region:'center',border:false">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/salePo/addPerson')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/salePo/editPerson')}">
			<a onclick="editFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">编辑</a>
		</c:if> 
		<c:if test="${fn:contains(sessionInfo.resourceList, '/salePo/deletePerson')}">
			<a onclick="deleteFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-del'">删除</a>
		</c:if> 
		 <c:if test="${fn:contains(sessionInfo.resourceList, '/salePo/excelPerson')}">
			<a onclick="excelFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">导出excel</a>
		</c:if>
	</div>
	
	<div id="poDialogOrderNo" ></div>
	<div id="poDialogContentOrderNo" style="display: none;" >
		<div style="height: 100%;width: 100%;text-align: center;">
			<div style="padding-top: 45px;">
			订单编号：<input id="ip_orderNoForSalePo" name="name" style="width: 50%;" placeholder="请输入订单编号" type="text" value="">
			</div>
		</div>
	</div>
</body>
<script src="${ctx}/lib/util/util_date.js" type="text/javascript" charset="utf-8"></script>
</html>