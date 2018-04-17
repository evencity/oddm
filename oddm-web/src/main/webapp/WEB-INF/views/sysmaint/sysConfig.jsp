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
	.datagrid-cell {   /* easyui不会自动识别英文字符串自动换行，nowrap: false不生效 */
		white-space: pre-wrap;
		word-wrap: break-word;
		word-break:break-all;
		line-height: 18px;
	}
</style>
<c:if test="${fn:contains(sessionInfo.resourceList, '/sysConfig/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
 </c:if>
<%--	<c:if test="${fn:contains(sessionInfo.resourceList, '/sysConfig/delete')}">
		<script type="text/javascript">
		$.canDelete = false;
		</script>
	</c:if> --%>

<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}' + '/sysConfig/dataGrid',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'key', //编辑、删除操作按钮用到 row.key
			sortName : 'key',
			sortOrder : 'asc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200],
			frozenColumns : [ [ {
				width : '20%',
				title : '名称',
				field : 'key',
				sortable : true
			}, {
				width : '32%',
				title : '值',
				field : 'value',
				sortable : true
			},{
				width : '40%',
				title : '描述',
				field : 'description'
			} , {
				field : 'action',
				title : '操作',
				width : '8%',
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					if ($.canEdit) {
						str += $.formatString('<a href="javascript:void(0)" onclick="editFun(\'{0}\');" >编辑</a>', row.key);
					if ($.canDelete) {
						str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
						str += $.formatString('<a href="javascript:void(0)" onclick="deleteFun(\'{0}\');" >删除</a>', row.key);
					}
					return str;
				}
			}} ] ],
			toolbar : '#toolbar'
		});
	});
	
	function addFun() {
		parent.$.modalDialog({
			title : '添加',
			width : 400,
			height : 200,
			href : '${ctx}/sysConfig/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#sysConfigAddForm');
					f.submit();
				}
			} ]
		});
	}
	
	function deleteFun(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		//console.log("ididid:"+id);
		parent.$.messager.confirm('询问', '您是否要删除当前产品类型？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/sysConfig/delete', {
					id : id
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
	
	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '编辑',
			width : 400,
			height : 200,
			href : '${ctx}/sysConfig/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#sysConfigEditForm');
					f.submit();
				}
			} ]
		});
	}
	
	
	
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/sysConfig/add')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
	</div>
</body>
</html>