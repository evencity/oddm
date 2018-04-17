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
			url : '${ctx}' + '/saleInfo/dataGridPerson',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			nowrap:false,// :当true时，显示数据在同一行上。默认true。 false自动换行显示
			idField : 'id', //编辑、删除操作按钮用到 row.key
			sortName : 'dateOrder',
			sortOrder : 'desc',
			onDblClickRow : function(index, row){
            	showDetail(row.id, row.orderNo);
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
				width : '11%',
				title : '订单号',
				field : 'orderNo',
				sortable : true,
				align:'left',
			},{
				width : '9%',
				title : '下单日期',
				field : 'dateOrder',
				sortable : true,
				align:'left',
				formatter:function(value,row,index){  
	                return dayformatter(value);
                }
			}, {
				width : '20%',
				title : '客户名称',
				field : 'clientName',
				align:'left',
			}, {
				width : '6%',
				title : '产品名称',
				field : 'productType',
				align:'left',
			},{
				width : '9%',
				title : '产品机型',
				field : 'productFactory',
				align:'left',
			}, {
				width : '7%',
				title : '销售',
				field : 'seller',
				align:'left',
			},{
				width : '7%',
				title : '数量',
				field : 'quantitySale',
				sortable : true,
				align:'left',
			},{
				width : '8%',
				title : '单价',
				field : 'unitPrice',
				sortable : true,
				align:'left',
				formatter : function(value, row, index) {
					if (value == null) {
						return 0;
					}
					return toMoney((value).toFixed(2), 2);
				}
			},{
				width : '11%',
				title : '金额',
				field : 'total',  //单价乘以数量
				align:'left',
				formatter : function(value, row, index) {
					if (row.unitPrice == null || row.quantitySale == null) {
						return 0;
					}
					return toMoney((row.unitPrice * row.quantitySale).toFixed(2), 2);
				}
			}, {
				width : '5%',
				title : '币种',
				field : 'currency',
				sortable : true,
				align:'left',
			}, {
				field : 'action',
				title : '操作',
				width : '4%',
				align:'left',
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					str += $.formatString('<img onclick="showDetail(\'{0}\',\'{1}\');" src="{2}" title="查看" style="cursor:pointer"/></img>', row.id, row.orderNo,"${ctx}/jslib/easyui1.4.2/themes/icons/search.png");
					return str;
				}
			}
		  ] ],
			toolbar : '#toolbar'
		});
		
		$("body").keydown(function(event) {
		    if (event.keyCode == "13") {//keyCode=13是回车键
		    	searchFun();
		    }
		});
	});
	function addFun(){
		var content = $('#dialogContentOrderNo').html();
		parent.$.modalDialog({
			title : '查询订单',
			width : 300,
			height : 160,
			content: content,
			//href : '${ctx}/saleInfo/checkOrderNoPage',
			buttons : [ {
				text : '确定',
				handler : function() {
					var orderNo = parent.$('#ip_orderNoForSaleInfo').val().trim();
					if(orderNo == null || "" == orderNo){
						parent.$.messager.alert('提示', '订单号不能为空！', 'info');
						return;
					}
					$.ajax({
						type: 'POST',
					    url: "${ctx}/saleInfo/checkOrderForOrderPerson?orderNo="+orderNo, 
					    dataType: "json",
					    contentType: "application/json; charset=utf-8",
					    success: function(data){
					    	if(data.success){
								//订单主表中订单号存在，则继续查询跟单表中是否存在数据					    		
					    		//console.log("orderId: "+data.obj)
					    		var url = "${ctx}/saleInfo/addPage?orderId="+data.obj;
					    	  	var title = "添加销售信息:"+orderNo;
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
	
	function showDetail(id, title){
	  	var url = "${ctx}/saleInfo/getPage?id="+id;
	  	var title = "销售信息:"+title;
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
				$.post('${ctx}/saleInfo/delete', {
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
	  	var url = '${ctx}/saleInfo/editPage?id=' + id;
	  	var title = "编辑销售信息:"+row.orderNo;
		window.parent.addTab({
			id : id,
			url : url,
			title : title,
		});
		
	}
	function searchFun() {
		var orderNo = $('#ip_orderNo').val().trim();
		var clientName = $('#ip_clientName').val().trim();
		var dateOrderStart = $('#dateOrderStart').datebox('getValue');
		var dateOrderEnd = $('#dateOrderEnd').datebox('getValue');
		if(orderNo == "" && clientName == "" && dateOrderStart == "" && dateOrderEnd == ""){
			parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
		}else{
			dataGrid.datagrid('load', {
				orderNo:orderNo,
				clientName:clientName,
				dateOrderStart: dateOrderStart,
				dateOrderEnd: dateOrderEnd,
			}); 
		} 
	}
	function cleanFun() {
		$('.autoInput').val('');
		$('#dateOrderStart').datebox('setValue',"");
		$('#dateOrderEnd').datebox('setValue',"");
	}
	function excelFun() {
		var content = $('#dialogExcelExportPage').html();
		parent.$.modalDialog({
		    title: '导出Excel',
			width : 400,
			height : 250,
		    closed: false,
		    cache: false,
		    content: content,
		    modal: true,
		    onOpen: function(){
				parent.$('#dateOrderStart').datebox('setValue', new Date().getFullYear()+'-01-01');//设置错误，可以自动默认为当前日期
				parent.$('#dateOrderEnd').datebox('setValue', '23432432');//设置错误，可以自动默认为当前日期
		    	//可以设置初始值
		    },
		    buttons:[{
				text:'确定',
				handler:function(){
					var clientName = parent.$('#clientName').val().trim();
		 			var seller = parent.$('#seller').val().trim();
		 			var productType = parent.$('#productType').val().trim();
		 			var dateOrderStart = parent.$('#dateOrderStart').datebox('getValue');
		 			var dateOrderEnd = parent.$('#dateOrderEnd').datebox('getValue');
		 		/* 	if (clientName == "" && seller == "" && productType == "") {
		 				//设置初始值
						dateOrderStart = new Date().getFullYear()+'-01-01';
			 			dateOrderEnd = parent.$('#dateOrderEnd').datebox('getValue');
			 			//parent.$('#dateOrderEnd').datebox('setValue', '');//清空
		 			} */
		 			download_file("${ctx}/saleInfo/excelExportPerson?clientName="+clientName+"&seller="+seller
		 					+"&productType="+productType
		 					+"&dateOrderStart="+dateOrderStart
		 					+"&dateOrderEnd="+dateOrderEnd);
				}
			}]
		}); 
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
				<th>下单日期起</th>
					<td><input name="dateOrderStart" placeholder="请输入" id="dateOrderStart" class="easyui-datebox" data-options="editable:false" style="width:92%"/></td> 
				<th>下单日期止</th>
					<td><input name="dateOrderEnd" placeholder="请输入" id="dateOrderEnd" class="easyui-datebox" data-options="editable:false" style="width:92%"/></td> 
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
					</td>
			</tr>
		</table>
	</div>
	
	<div data-options="region:'center',border:false">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/saleInfo/addPerson')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/saleInfo/editPerson')}">
			<a onclick="editFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">编辑</a>
		</c:if> 
		<c:if test="${fn:contains(sessionInfo.resourceList, '/saleInfo/deletePerson')}">
			<a onclick="deleteFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-del'">删除</a>
		</c:if> 
		 <c:if test="${fn:contains(sessionInfo.resourceList, '/saleInfo/excelPerson')}">
			<a onclick="excelFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">导出excel</a>
		</c:if>
	</div>
	
	<div id="dialogOrderNo" ></div>
	<div id="dialogContentOrderNo" style="display: none;" >
		<div style="height: 100%;width: 100%;text-align: center;">
			<div style="padding-top: 45px;">
			订单编号：<input id="ip_orderNoForSaleInfo" name="name" style="width: 50%;" placeholder="请输入订单编号" type="text" value="">
			</div>
		</div>
	</div>
	<div id="dialogExcelExport" ></div>
	<div id="dialogExcelExportPage"  style="display: none;" >
        <%@ include file="saleInfoExcelExport.jsp" %>  
    </div>
</body>
<script src="${ctx}/lib/util/util_date.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/lib/util/util_common.js" type="text/javascript" charset="utf-8"></script>
</html>