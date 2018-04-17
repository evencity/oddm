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
	.lefttd{width: 52px}
	.righttd{width: 12%}
	.datagrid-cell {   /* easyui不会自动识别英文字符串自动换行，nowrap: false不生效 */
		white-space: pre-wrap;
		word-wrap: break-word;
		line-height: 18px;
		word-break:break-all;
	}
	input {border:1px solid #CCC;font-size:12px;height:19px;border-radius: 4px;}
</style> 
<c:if test="${fn:contains(sessionInfo.resourceList, '/bom/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
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
			url : '${ctx}/bom/dataGrid',
			striped : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'desc',
			nowrap:false,
			view: myview,
		    emptyMsg: '没有找到相关数据',
		    onDblClickRow : function(index, row){
		           showBomFun(row.id,row.orderInfoDTO.orderNo);
		    },
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200],
				
		    rownumbers : true,
			frozenColumns : [ [ {
				field:'ck',
				checkbox:true,
				width : '5%'
			}, {
				width : '12%',
				title : '物料编号',
				field : 'materialCode',
			}, {
				field : 'orderNo',
				title : '订单编号',
				width : '10%',
				formatter : function(value, row, index) {
					return row.orderInfoDTO.orderNo;
				}
			}, {
				field : 'productName',
				title : '品名',
				width : '15%',
			},{
				field : 'maker',
				title : '制表人',
				width : '10%',
			
			}, {
				width : '13%',
				title : '更新时间',
				field : 'updatetime',
				sortable : true,
				align:'left',
				formatter:function(value,row,index){  
	                return dateformatter(value);
                }  
			},{
				width : '10%',
				title : '数量',
				field : 'quantity',
				formatter : function(value, row, index) {
					return row.orderInfoDTO.quantity;
				}
			},{
				width : '10%',
				title : '版本',
				field : 'version',
			},{
				width : '9%',
				title : '状态',
				field : 'state',
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
						
							return '<span class="color_green">已齐料'+unread+'</span>';
						case 7:
							return '<span class="color_green">完结'+isRead+'</span>';
					}
				}
			}, {
				width : '8%',
				title : '操作',
				field : 'action',
				formatter:function(value,row,index){
					var str = '';
					str += $.formatString('<img onclick="showBomFun(\'{0}\',\'{1}\');" src="{2}" title="查看" style="cursor:pointer"/></img>',  row.id,row.orderInfoDTO.orderNo,"${ctx}/jslib/easyui1.4.2/themes/icons/search.png");
	                   return str;
	               }
			}  ] ], 
			toolbar : '#toolbar'
		}); 
		
		
		$("body").keydown(function(event) {
		    if (event.keyCode == "13") {//keyCode=13是回车键
		    	searchFun() ;
		    }
		});
	});
	
	

/**************** 格式化日期，传入毫秒数 ****************/
	function dateformatter(milliseconds){
		var oDate = new Date(milliseconds),  
        oYear = oDate.getFullYear(),  
        oMonth = oDate.getMonth()+1,  
        oDay = oDate.getDate(),  
        oHour = oDate.getHours(),  
        oMin = oDate.getMinutes(),  
        oSen = oDate.getSeconds(),  
        oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay) +' '+ getzf(oHour) +':'+ getzf(oMin) +':'+getzf(oSen);//最后拼接时间  
        return oTime;  
	}
	//补0操作  
    function getzf(num){  
        if(parseInt(num) < 10){  
            num = '0'+num;  
        }  
        return num;  
    }  
	function showBomFun(id,orderNo){
		
	  	var url = "${ctx}/bom/getPage?id="+id;
	  	var title = "BOM单:"+orderNo;
		window.parent.addTab({
			url : url,
			title : title,
		});
	}
	
	function addBomFun(){
		parent.$.modalDialog({
			title : '查询订单',
			width : 300,
			height : 160,
			href : '${ctx}/bom/checkOrderNoPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					var orderNo = parent.$('#orderNoForBom').val().trim();
					if(orderNo == null || "" == orderNo){
						parent.$.messager.alert('提示', '订单号不能为空！', 'info');
						return;
					}
					$.ajax({
						type: 'POST',
					    url: "${ctx}/bom/checkOrderForOrder?orderNo="+orderNo, 
					    dataType: "json",
					    contentType: "application/json; charset=utf-8",
					    success: function(data){
					    	if(data.success){
								//订单主表中订单号存在，则继续查询跟单表中是否存在数据
								$.ajax({
									type: 'POST',
								    url: "${ctx}/bom/getBomByOrderNo?orderNo="+orderNo, 
								    dataType: "json",
								    contentType: "application/json; charset=utf-8",
								    success: function(data){
								    	if(data.success){
											//订单主表中订单号存在，则继续查询跟单表中是否存在数据
								    		parent.$.messager.alert('提示', data.msg,  'info');
								    	}else{
								    		//parent.$.messager.confirm('询问', '您已选择订单,确定后将生成指导书,是否继续？', function(b) {
								    			//if (b) {
								    				var url = "${ctx}/bom/addPage?orderNo="+orderNo;
										    	  	var title = "Bom添加:"+ orderNo;
										    		window.parent.addTab({
										    			url : url,
										    			title : title,
										    		});
										    		parent.$.modalDialog.handler.dialog('close');
								    		//	}
								    	//	});
								    		
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
	
	function deleteBomFun() {
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		parent.$.messager.confirm('询问', '您是否要删除当前选项？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/bom/delete', {
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
	function editBomFun(){
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
	  	var url = "${ctx}/bom/editPage?id="+row.id;
	  	var title = "BOM单:"+ row.orderInfoDTO.orderNo +"(编辑)";
		window.parent.addTab({
			url : url,
			title : title,
		});
		
	}
	function excelBomFun(){
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		download_file('${ctx}/bom/excelExport?id='+row.id);
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
		         parent.parent.$.messager.alert('提示', '导出excel出错，请联系管理员！');
		   }, false);
	    }  
	    
		download_file.iframe.src = url;  
	    download_file.iframe.style.display = "none";  
	} 
	function searchFun() {
		var orderNo = $('#orderNo').val().trim();
		var maker = $('#maker').val().trim();
		var productName = $('#productName').val().trim();
		var materialCode = $('#materialCode').val().trim();
		if(orderNo == "" && maker == "" && productName == "" && materialCode == ""){
			parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
		}else{
			dataGrid.datagrid('load', {
				orderNo:orderNo,
				maker:maker,
				productName:productName,
				materialCode:materialCode
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
		$('#maker').val('');
		$('#productName').val('');
		$('#materialCode').val('');
	//	dataGrid.datagrid('load', {});
	}
	
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	 <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
		<table>
			<tr>
				<th class=''>订单号:</th>
				<td class=""><input id="orderNo" placeholder="订单号" class="autoInput"/></td>
				<th class=''>物料编号:</th>
				<td class=""><input id="materialCode" placeholder="物料编号" class="autoInput"/></td>
				<th class=''>制表人:</th>
				<td class=""><input id="maker" placeholder="制表人" class="autoInput"/></td>
				<th class=''>品名:</th>
				<td class=""><input id="productName" placeholder="品名" class="autoInput"/></td>
				<td style="">
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchForNoReadFun();">未读Bom</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
				</td>
			</tr>
		</table>
	</div> 
	
	<div data-options="region:'center',border:false" style="top: 30px">
		<table id="dataGrid" data-options="fit:true,border:true" ></table>
	</div> 
	<div id="toolbar" style="display: none;">
		 
		<c:if test="${fn:contains(sessionInfo.resourceList, '/bom/add')}">
			<a onclick="addBomFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/bom/edit')}">
			<a onclick="editBomFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">编辑</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/bom/delete')}">
			<a onclick="deleteBomFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-del'">删除</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/bom/excel')}">
			<a onclick="excelBomFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">导出excel</a>
		</c:if>
		
	</div>
</body>
</html>