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
			url : '${ctx}' + '/saleProto/dataGrid',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			nowrap:false,// :当true时，显示数据在同一行上。默认true。 false自动换行显示
			idField : 'id', //编辑、删除操作按钮用到 row.key
			sortName : 'id',
			sortOrder : 'desc',
			onDblClickRow : function(index, row){
            	showDetail(row.id, row.productName);
	        },
	        view: myview,
	        emptyMsg: '没有找到相关数据',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200],
			frozenColumns : [ [ {
				field:'ck',
				checkbox:true,
				width : '5%'
			}, {
				width : '13%',
				title : '客户名称',
				field : 'clientName',
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
			},{
				width : '10%',
				title : '产品名称',
				field : 'productName',
				sortable : true,
				align:'left',
			},{
				width : '10%',
				title : '国家地区',
				field : 'district',
				sortable : true,
				align:'left',
			},{
				width : '5%',
				title : '尺寸',
				field : 'size',
				align:'left',
			}, {
				width : '14%',
				title : 'PCBA型号',
				field : 'pcba',
				align:'left',
			},{
				width : '12%',
				title : '外观/软件',
				field : 'facade',
				sortable : true,
				align:'left',
			},{
				width : '6%',
				title : '数量',
				field : 'quantity',
				align:'left',
			},{
				width : '9%',
				title : '送样日期',
				field : 'dateSend',
				align:'left',
				sortable : true,
				formatter:function(value,row,index){
	                return dayformatter(value);
                }
			} , {
				field : 'action',
				title : '操作',
				width : '4%',
				align:'left',
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					str += $.formatString('<img onclick="showDetail(\'{0}\',\'{1}\');" src="{2}" title="查看" style="cursor:pointer"/></img>', row.id, row.productName,"${ctx}/jslib/easyui1.4.2/themes/icons/search.png");
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
   		var url = "${ctx}/saleProto/addPage?";
   	  	var title = "添加样机";
   		window.parent.addTab({
   			url : url,
   			title : title,
   		});
	}
	
	function showDetail(id, title){
	  	var url = "${ctx}/saleProto/getPage?id="+id;
	  	var title = "查看样机:"+title;
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
		var productName = row.productName; if (productName == null) productName="";
		var facade = row.facade; if (facade == null) facade="";
		parent.$.messager.confirm('询问', '您是否要删除当前记录，样机'+productName+'外观'+facade+'？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/saleProto/delete', {
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
	  	var url = '${ctx}/saleProto/editPage?id=' + id;
	  	var title = "编辑样机:"+row.productName;
		window.parent.addTab({
			id : id,
			url : url,
			title : title,
		});
		
	}
	function searchFun() {
		var productName = $('#productName').val().trim();
		var clientName = $('#clientName').val().trim();
		var district = $('#district').val().trim();
		var dateDeliveryStart = $('#dateSendStart').datebox('getValue');
		var dateDeliveryEnd = $('#dateSendEnd').datebox('getValue');
		if(productName == "" && clientName == "" && district == "" && dateDeliveryStart == "" && dateDeliveryEnd == ""){
			parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
		}else{
			dataGrid.datagrid('load', {
				dateDeliveryStart: dateDeliveryStart,
				dateDeliveryEnd: dateDeliveryEnd,
				productName: productName,
				clientName: clientName,
				district: district,
			}); 
		} 
	}
	function cleanFun() {
		$('.autoInput').val('');
		$('#dateSendStart').datebox('setValue',"");
		$('#dateSendEnd').datebox('setValue',"");
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
				parent.$('#dateSendStart').datebox('setValue', new Date().getFullYear()+'-01-01');//设置错误，可以自动默认为当前日期
				parent.$('#dateSendEnd').datebox('setValue', '23432432');//设置错误，可以自动默认为当前日期
		    	//可以设置初始值
		    },
		    buttons:[{
				text:'确定',
				handler:function(){
					var district = parent.$('#district').val().trim();
					var clientName = parent.$('#clientName').val().trim();
		 			var productName = parent.$('#productName').val().trim();
		 			var dateSendStart = parent.$('#dateSendStart').datebox('getValue');
		 			var dateSendEnd = parent.$('#dateSendEnd').datebox('getValue');
		 		/* 	if (clientName == "" && seller == "" && productType == "") {
		 				//设置初始值
						dateOrderStart = new Date().getFullYear()+'-01-01';
			 			dateOrderEnd = parent.$('#dateOrderEnd').datebox('getValue');
			 			//parent.$('#dateOrderEnd').datebox('setValue', '');//清空
		 			} */
		 			download_file("${ctx}/saleProto/excelExport?clientName="+clientName+"&productName="+productName
		 					+"&district="+district
		 					+"&dateSendStart="+dateSendStart
		 					+"&dateSendEnd="+dateSendEnd);
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
	<div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
		<table>
			<tr>
				<th>客户名称:</th>
					<td><input name="clientName" placeholder="请输入" id="clientName" class="autoInput"/></td>
				<th>产品名称:</th>
					<td><input name="productName" placeholder="请输入" id="productName" class="autoInput"/></td> 
				<th>送样日期起</th>
					<td><input name="dateSendStart" placeholder="请输入" id="dateSendStart" class="easyui-datebox" data-options="editable:false" style="width:92%"/></td> 
				<th>送样日期止</th>
					<td><input name="dateSendEnd" placeholder="请输入" id="dateSendEnd" class="easyui-datebox" data-options="editable:false" style="width:92%"/></td> 
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
					</td>
			</tr>
			<tr>
				<th>国家地区:</th>
					<td><input name="district" placeholder="请输入" id="district" class="autoInput"/></td>
			</tr>
		</table>
	</div>
	
	<div data-options="region:'center',border:false">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/saleProto/addAll')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/saleProto/editAll')}">
			<a onclick="editFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">编辑</a>
		</c:if> 
		<c:if test="${fn:contains(sessionInfo.resourceList, '/saleProto/deleteAll')}">
			<a onclick="deleteFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-del'">删除</a>
		</c:if> 
		 <c:if test="${fn:contains(sessionInfo.resourceList, '/saleProto/excelAll')}">
			<a onclick="excelFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">导出excel</a>
		</c:if>
	</div>
	<div id="dialogExcelExport" ></div>
	<div id="dialogExcelExportPage"  style="display: none;" >
        <%@ include file="saleProtoExcelExport.jsp" %>  
    </div>
</body>
<script src="${ctx}/lib/util/util_date.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/lib/util/util_common.js" type="text/javascript" charset="utf-8"></script>
</html>