<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="edge" />
<jsp:include page="../inc.jsp"/>

<%-- <c:if test="${fn:contains(sessionInfo.resourceList, '/invoice/view')}">
	<script type="text/javascript">
		$.canFind = true;
	</script>
</c:if> --%>
<style type="text/css">
	.autoInput{width: 95%}
	.datagrid-cell {   /* easyui不会自动识别英文字符串自动换行，nowrap: false不生效 */
		white-space: pre-wrap;
		word-wrap: break-word;
		word-break:break-all;
		line-height: 18px;
	}
	input {border:1px solid #CCC;font-size:12px;height:18px;border-radius: 4px;}
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
				url:'${ctx}/invoice/dataGrid',
				idField : 'id',
				pagination : true,
				striped : true,
				rownumbers : true,
				pagination : true,
				singleSelect : true,
				view: myview,
			    emptyMsg: '没有找到相关数据',
				nowrap:false,
				onDblClickRow : function(index, row){
					showFun(row.id,row.name);
		        },
				pageSize : 50,
				frozenColumns : [ [ {
					field : 'id',
					title : '序号',
					width : 150,
					checkbox : true
				},
					 {field:'name',title:'发票名称',width:'11%'},
	        		 {field:'to_',title:'客户名称',width:'12%'},
	        		 {field:'companyName',title:'公司名称',width:'12%'},
	        		 {field:'dateInvoiceString',title:'发票日期',width:'8%'},
	        		 {field:'piNo',title:'P/I NO',width:'12%'},
	        		 {field:'qtys',title:'订单总数',width:'8%'},
	        		 {field:'amount',title:'总金额',width:'12%',
	        			formatter : function(value, row, index) {
	 					if (value == null) {
							return 0;
						}
						return toCharFun(row.currency)+toMoney((value).toFixed(2), 2);
					}},
	        		 {field:'currency',title:'币种',width:'5%'},
	        		 {field:'updatetime',title:'更新时间',width:'12%'},
	        		 {
					field : 'action',
					title : '操作',
					width : '5%',
					formatter : function(value, row, index) {
						var str = '';
						str += $.formatString('<img onclick="showFun(\'{0}\',\'{1}\');" src="{2}" title="查看"/>', row.id,row.name, '${ctx}/jslib/easyui1.4.2/themes/icons/search.png');
						return str;
					}
				} 
	   			]],
	   			toolbar : '#toolbar',
			})
		$("body").keydown(function(event) {
		    if (event.keyCode == "13") {//keyCode=13是回车键
		    	searchFun() ;
		    }
		});
	});
	/**添加*/
	function addFun() {
		 var url = "${ctx}/invoice/addPage";
    	 var title = "发票添加";
		 window.parent.addTab({
			url : url,
			title : title,
		 }); 
	}

	/**查看invoice*/
	function showFun(id,name) {
		 var url = "${ctx}/invoice/getPage?id="+id;
    	 //var title = "发票："+name;
    	 var title = "发票详情";
		 window.parent.addTab({
			url : url,
			title : title,
			id : "invoiceShow_"+id
			/* iconCls : node.iconCls */
		 });
		
	}
	/**修改invoice*/
	function editFun() {
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		var url = "${ctx}/invoice/editPage?id="+row.id;
		//var title = "发票:"+row.name+"(编辑)";
		var title = "发票编辑";
	 	window.parent.addTab({
		url : url,
		title : title,
		
		id : "invoiceEdit_"+row.id
	 });
	}
	
	/**删除invoice*/
	function deleteFun() {
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		parent.$.messager.confirm('询问', '您是否要删除当前选项？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/invoice/delete', {
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
	function excelFun(){
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		download_file('${ctx}/invoice/excelExport?id='+row.id);
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
		var name = $('#name').val().trim();
		//var to_ = $('#to_').val().trim();
		var piNo = $('#piNo').val().trim();
		var dateOrderStart = $('#dateOrderStart').datebox('getValue');
		var dateOrderEnd = $('#dateOrderEnd').datebox('getValue');
		if(name == ""  && piNo == "" && dateOrderStart == "" && dateOrderEnd == ""){
			parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
		}else{
			dataGrid.datagrid('load', {
				piNo:piNo,
				name:name,
				dateInvoiceStart: dateOrderStart,
				dateInvoiceEnd: dateOrderEnd,
				//to_:to_,
			}); 
			
		}
	}
	
	function cleanFun(){
		$('#name').val('');
		$('#piNo').val('');
		//$('#to_').val('');
		$('#dateOrderStart').datebox('setValue',"");
		$('#dateOrderEnd').datebox('setValue',"");
	}
	function toCharFun(type){
		var temp = {
				"USD":"$",
				"RMB":"￥",
				"EUR":"€",
				"GBP":"￡",
		}
		if(temp[type] == '' || typeof(temp[type]) == 'undefined' || temp[type] == null || temp[type] == 'undefined'){
			return "￥";
		}
		return temp[type];
	}
</script>
</head>

<body class="easyui-layout" data-options="fit:true,border:false">

	<div data-options="region:'north',border:false" style=" overflow: hidden;background-color: #fff">
		<table>
			<tr>
				<th>发票名称:</th>
				<td><input id="name" placeholder="请输入发票名称" class="autoInput"/></td>
				<th>P/I NO:</th>
				<td><input id="piNo" placeholder="请输入P/I NO" class="autoInput"/></td> 
				<th style="text-align: right">下单日期起</th>
					<td><input name="dateOrderStart" placeholder="请输入" id="dateOrderStart" class="easyui-datebox" data-options="editable:false" style="width:92%"/></td> 
				<th style="text-align: right">下单日期止</th>
					<td><input name="dateOrderEnd" placeholder="请输入" id="dateOrderEnd" class="easyui-datebox" data-options="editable:false" style="width:92%"/></td> 
				<!-- <th>客户名称:</th>
				<td><input id="to_" placeholder="请输入客户名称" class="autoInput"/></td> -->
				<td>
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/invoice/delete')}"> 
			<a onclick="deleteFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-del'">删除</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/invoice/add')}"> 
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/invoice/edit')}">
			<a onclick="editFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">编辑</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/invoice/excel')}">
			<a onclick="excelFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">导出excel</a>
		</c:if>
	</div>

</body>
<%-- <script src="${ctx}/lib/util/util_date.js" type="text/javascript" charset="utf-8"></script> --%>
<script src="${ctx}/lib/util/util_common.js" type="text/javascript" charset="utf-8"></script>

</html>