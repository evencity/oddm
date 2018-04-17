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
<c:if test="${fn:contains(sessionInfo.resourceList, '/resource/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/resource/delete')}">
	<script type="text/javascript">
	</script>
</c:if>
<title>资源管理</title>
<script type="text/javascript">
	var treeGrid;
	$(function() {
		
		treeGrid = $('#treeGrid').treegrid({
			url : '${ctx}/resource/treeGrid',
			idField : 'id',
			treeField : 'name',
			parentField : 'pid',
			rownumbers : true,
			fit : true,
			fitColumns : false,
			border : false,
			frozenColumns : [ [ {
				title : '编号',
				field : 'id',
				width : '40%',
				hidden : true
			} ,{
				field : 'name',
				title : '资源名称',
				width : "22%"
			}, {
				field : 'url',
				title : '资源路径',
				width : "25%"
			}, {
				field : 'icon',
				title : '图标',
				width : "20%"
			}, {
				field : 'seq',
				title : '排序',
				width : "5%"
			}, {
				field : 'resourcetype',
				title : '资源类型',
				width : "10%",
				formatter : function(value, row, index) {
					switch (value) {
					case 1:
						return '菜单';
					case 2:
						return '按钮';
					}
				}
			}, {
				field : 'pid',
				title : '上级资源ID',
				width : '15%',
				hidden : true
			}, {
				field : 'action',
				title : '操作',
				width : "18%",
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					if ($.canEdit) {
					str += $.formatString('<a href="javascript:void(0)" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
					}
					if ($.canDelete) {
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
					}
					return str;
				}
			} ] ],
			toolbar : '#toolbar'
		});
	});
	
	function editFun(id) {
		if (id != undefined) {
			treeGrid.treegrid('select', id);
		}
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			parent.$.modalDialog({
				title : '编辑',
				width : 500,
				height : 350,
				href : '${ctx}/resource/editPage?id=' + node.id,
				buttons : [ {
					text : '编辑',
					handler : function() {
						parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
						var f = parent.$.modalDialog.handler.find('#resourceEditForm');
						f.submit();
					}
				} ]
			});
		}
	}
	
	function deleteFun(id) {
		if (id != undefined) {
			treeGrid.treegrid('select', id);
		}
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			parent.$.messager.confirm('询问', '您是否要删除当前资源？删除当前资源会连同子资源一起删除!', function(b) {
				if (b) {
					progressLoad();
					$.post('${pageContext.request.contextPath}/resource/delete', {
						id : node.id
					}, function(result) {
						if (result.success) {
							parent.$.messager.alert('提示', result.msg, 'info');
							treeGrid.treegrid('reload');
							parent.layout_west_tree.tree('reload');
						} else {
							parent.$.messager.alert('错误', result.msg, 'error');
						}
						progressClose();
					}, 'JSON');
				}
			});
		}
	}
	
	function addFun() {
		parent.$.modalDialog({
			title : '添加',
			width : 500,
			height : 350,
			href : '${ctx}/resource/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#resourceAddForm');
					f.submit();
				}
			} ]
		});
	}
	</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false"  style="overflow: hidden;">
			<table id="treeGrid"></table>
		</div>
	</div>
	
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/resource/add')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
	</div>
</body>
</html>