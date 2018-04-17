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
	.freeze{font-weight: 700;color: #F60}
	.datagrid-cell {   /* easyui不会自动识别英文字符串自动换行，nowrap: false不生效 */
		white-space: pre-wrap;
		word-wrap: break-word;
		word-break:break-all;
		line-height: 18px;
	}
	input {border:1px solid #CCC;font-size:12px;height:18px;border-radius: 4px;}
</style>
<c:if test="${fn:contains(sessionInfo.resourceList, '/product/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/product/freeze')}">
	<script type="text/javascript">
	$.canFreeze = true;
	</script>
</c:if>

<title>机型管理</title>
	<script type="text/javascript">
	var dataGrid;
	$(function() {
		$('#typeId').combobox({
			url : '${pageContext.request.contextPath}/productType/dataGrid',
			 valueField:'id',    
			 textField:'name',
			 panelHeight : 'auto',
			 editable:false ,
			 onSelect : function(param){
				  dataGrid.datagrid('load', {
						typeId:param.id
					}); 
			  }
		});
		
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
			url : '${ctx}' + '/product/dataGrid',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'desc',
			view: myview,
            emptyMsg: '没有找到相关数据',
			onLoadSuccess:function(data){ 
				 /* $(this).datagrid("fixRownumber");   */
            },
            onDblClickRow : function(index, row){
            	showDetailsFun(row.id,row.name);
	        },
			nowrap:false,
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200],
			frozenColumns : [ [ {
				field:'ck',
				checkbox:true,
				width : '5%'
			},{
				width : '100',
				title : '序号',
				field : 'id',
				hidden : true
			}, {
				width : '20%',
				title : '机型号',
				field : 'name',
				sortable : true
			},  {
				width : '20%',
				title : '类型',
				field : 'typeName',
				sortable : true
			},{
				width : '15%',
				title : '状态',
				field : 'state',
				sortable : true,
				formatter : function(value, row, index) {
					switch (value) {
					case 1:
						return '正常';
					case 2:
						return '<span class="freeze">冻结</span>';
					}
				}
			} ,{
				width : '30%',
				title : '描述',
				field : 'description'
			} ,{
				field : 'action',
				title : '操作',
				width : '15%',
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					/* if ($.canEdit) {
						str += $.formatString('<a href="javascript:void(0)" onclick="editFun(\'{0}\',\'{1}\');" >编辑</a>', row.id,row.name);
						str += '&nbsp;&nbsp;|&nbsp;&nbsp;';*/
					/* if ($.canFreeze) {
						str += $.formatString('<a href="javascript:void(0)" onclick="freezeFun(\'{0}\');" >冻结</a>', row.id);
						str += '&nbsp;&nbsp;|&nbsp;&nbsp;';					
					}  */
					str += $.formatString('<img onclick="showDetailsFun(\'{0}\',\'{1}\');" src="{2}" title="查看" style="cursor:pointer"/></img>', row.id,row.name,"${ctx}/jslib/easyui1.4.2/themes/icons/search.png");
					return str;
				}
			} ] ],
			toolbar : '#toolbar'
		});
		
		$("body").keydown(function(event) {
		    if (event.keyCode == "13") {//keyCode=13是回车键
		    	var name = $('#name').val().trim();
				//var typeId = $('#typeId').combobox('getValue').trim();
				if(name != '' ){
					dataGrid.datagrid('load', {
						name:name,
				//		typeId:typeId
					});
				}else{
					parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
				}
		    }
		});
	});

	function showDetailsFun(id,name){
	  	var url = "${pageContext.request.contextPath}/product/getPage?id="+id;
	  	var title = "机型"+name;
		window.parent.addTab({
			url : url,
			title : title,
		});
	}
	
	function addFun() {
		var url = "${pageContext.request.contextPath}/product/addPage";
	  	var title = "机型添加";
	  	window.parent.addTab({
			url : url,
			title : title,
		});
	  	parent.$.modalDialog.openner_dataGrid = dataGrid;
	}
	
	function deleteFun(id) {
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		parent.$.messager.confirm('询问', '您是否要删除当前机型？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/product/delete', {
					id : row.id
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('reload');
					}
					progressClose();
				}, 'JSON');
			}
		});
	}
	
	function editFun() {
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		var url = "${pageContext.request.contextPath}/product/editPage?id="+row.id;
	  	var title = "机型："+row.name;
		window.parent.addTab({
			url : url,
			title : title,
		});
		parent.$.modalDialog.openner_dataGrid = dataGrid;
	}
	
	//冻结
	function freezeFun(id) {
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		parent.$.messager.confirm('询问', '您是否要冻结当前机型？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/product/freeze', {
					id : row.id
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('reload');
					}
					progressClose();
				}, 'JSON');
			}
		});
	}
	
	function searchFun() {
		var name = $('#name').val().trim();
	//	var typeId = $('#typeId').combobox('getValue').trim();
		if(name != ''){
			dataGrid.datagrid('load', {
				name:name,
				//typeId:typeId
			});
		}else{
			parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
		}
	}
	function cleanFun() {
		$('#name').val('');
		$('#typeId').combobox('setValue','');
		//dataGrid.datagrid('load', {});
	}
	
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
		<!-- <form id="searchForm"> -->
			<table>
				<tr>
					<th>分类:</th>
					<td><select  id="typeId" style="width: 120px; height: 20px;" class="easyui-validatebox"></select></td> 
					<th>机型号:</th>
					<td><input name="name" placeholder="请输入机型号" id="name"/></td>
					<td>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
				</tr>
			</table>
		<!-- </form> -->
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/product/add')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/product/edit')}">
			<a onclick="editFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">编辑</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/product/delete')}">
			<a onclick="deleteFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-del'">删除</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/product/freeze')}">
			<a onclick="freezeFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">冻结/解冻</a>
		</c:if>
	</div>
</body>
</html>