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
	input {border:1px solid #CCC;font-size:12px;height:18px;border-radius: 4px;}
</style>
<c:if test="${fn:contains(sessionInfo.resourceList, '/user/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/user/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/user/resetPwd')}">
	<script type="text/javascript">
		$.resetPwd = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/user/resetPwdForAdmin')}">
	<script type="text/javascript">
		$.resetPwdForAdmin = true;
	</script>
</c:if>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
	<script type="text/javascript">
	var dataGrid;
	var organizationTree;
	$(function() {
	
		organizationTree = $('#organizationTree').tree({
			url : '${ctx}/organization/userDept',
			parentField : 'pid',
			lines : true,
			onClick : function(node) {
				// dataGrid.datagrid('load', {
			    // organizationId: node.id
			//}); 
			}
		}); 
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}/user/dataGrid',
			fit : true,
			striped : true,
			rownumbers : true,
			pagination : true,
			nowrap:false,
			singleSelect : true,
			idField : 'id',
			sortName : 'createdatetime',//from Tuser t  where 1=1  order by t.createdatetime asc
			sortOrder : 'desc',
			pageSize : 100, //默认一页50行  会发送rows:"50"给后台
			pageList : [ 10, 20, 30, 40, 50, 100, 200 ],
			frozenColumns : [ [ {
				field:'ck',
				checkbox:true,
				width : '5%'
			},{
				width : '9%',
				title : '登录名',
				field : 'loginname',
				sortable : true
			}, {
				width :  '9%',
				title : '姓名',
				field : 'username',
				sortable : true
			},{
				width :  '10%',
				title : '部门ID',
				field : 'organizationId',
				hidden : true
			},{
				width :  '14%',
				title : '所属部门',
				field : 'organizationName',
				sortable : true,
			},{
				width :  '7%',
				title : '性别',
				field : 'sex',
				sortable : true,
				formatter : function(value, row, index) {
					switch (value) {
						case 1:
							return '保密';
						case 2:
							return '男';
						case 3:
							return '女';
					}
				}
			}, {
				width :  '7%',
				title : '工龄',
				field : 'age',
				formatter : function(value, row, index) {
					if (row.age == null) return "0 岁";
					var year = (new Date() - row.age)/(365 * 24 * 3600 * 1000);
					return Math.floor(year)+" 岁";
				}
			},{
				width : '7%',
				title : '状态',
				field : 'state',
				sortable : true,
				formatter : function(value, row, index) {
					switch (value) {
					case 1:
						return '正常';
					case 2:
						return '冻结';
					}
				},
				styler: function(value,row,index){
					if (value == 2){
						return 'color:green;font-weight:bold;';
					}
				}
			} ,{
				width :  '12%',
				title : '角色',
				field : 'roleNames',
			} ,{
				width :  '16%',
				title : '创建时间',
				sortable : true,
				field : 'createdatetime'
			} ,{
				field : 'action',
				title : '操作',
				width :  '19%',
				formatter : function(value, row, index) {
					var str = '';
					if(row.isdefault!=0){
						if ($.canEdit) {
							str += $.formatString('<a href="javascript:void(0)" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
						}
						if ($.canDelete) {
						str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
							str += $.formatString('<a href="javascript:void(0)" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
						}
						/* if ($.resetPwd) {
							str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
								str += $.formatString('<a href="javascript:void(0)" onclick="resetPwdFun(\'{0}\');" >密码重置</a>', row.id);
						} */
					}
					return str;
				}
			}] ],
			toolbar : '#toolbar'
		});
		
		$("body").keydown(function(event) {
		    if (event.keyCode == "13") {//keyCode=13是回车键
		    	var name = $('#name').val().trim();
				if(name != ''){
					dataGrid.datagrid('load', {
						username:name
					});
				}else{
					parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
				}
				
		    }
		});
	});
	
	function addFun() {
		parent.$.modalDialog({
			title : '添加',
			width : 500,
			height : 300,
			href : '${ctx}/user/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#userAddForm');
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
		parent.$.messager.confirm('询问', '您是否要删除当前用户？', function(b) {
			if (b) {
				var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
				if (currentUserId != id) {
					progressLoad();
					$.post('${ctx}/user/delete', {
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
				} else {
					parent.$.messager.show({
						title : '提示',
						msg : '不可以删除自己！'
					});
				}
			}
		});
	}
	
	function resetPwdForAdminFun(id) {
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '密码修改',
			width : 500,
			height : 300,
			href : '${ctx}/user/editPwdForAdminPage?id=' + id,
			buttons : [ {
				text : '重置',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#editUserPwdForAdminForm');
					f.submit();
					dataGrid.datagrid('clearSelections');
				}
			} ]
		});
	}
	function resetPwdFun(id) {
		
		parent.$.messager.confirm('询问', '您是否要重置当前用户密码？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/user/resetPwd', {
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
			width : 500,
			height : 300,
			href : '${ctx}/user/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#userEditForm');
					f.submit();
				}
			} ]
		});
	}
	
	function searchFun() {
		var name = $('#name').val().trim();
		if(name != ''){
			dataGrid.datagrid('load', {
				username:name
			});
		}else{
			parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
		}
		
	}
	function cleanFun() {
		$('#name').val('');
		//dataGrid.datagrid('load', {});
	}
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
	<!-- 	<form id="searchForm"> -->
			<table>
				<tr>
					<th>姓名:</th>
					<td><input name="username" placeholder="请输入用户姓名" id="name"/></td>
					<td>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
				</tr>
			</table>
		<!-- </form> -->
	</div>
	<div data-options="region:'center',border:true,title:'用户列表'" >
	
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	<div data-options="region:'west',border:true,split:false,title:'组织机构'"  style="width:200px;overflow: auto; ">
		<ul id="organizationTree"  style="width:160px;margin: 10px 10px 10px 10px">
		</ul>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/user/add')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/user/resetPwdForAdmin')}">
			<a onclick="resetPwdForAdminFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">密码修改</a>
		</c:if>
	</div>
</body>
</html>