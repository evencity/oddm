<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../inc.jsp"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:if test="${fn:contains(sessionInfo.resourceList, '/bomMaterialContact/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/bomMaterialContact/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGridz;
	$(function() {
	dataGrid = $('#dataGridz').datagrid({
			url : '${pageContext.request.contextPath}/bomMaterialContact/getBomMaterialContactInPage',
			nowrap : true,
			idField : 'id',
			pagination : true,
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			nowrap:false,
			frozenColumns : [ [ {
				width : '150',
				title : '姓名',
				field : 'contacts',
				sortable : true
			}, {
				width : '180',
				title : '手机号',
				field : 'cellphone',
				sortable : true
			}, {
				width : '180',
				title : '公司名',
				field : 'company',
				sortable : true
			}, {
				width : '150',
				title : '座机号',
				field : 'telphone',
				sortable : true
			} , {
				width : '150',
				title : '邮箱',
				field : 'email',
				sortable : true
			} , {
				width : '150',
				title : 'fax',
				field : 'fax',
				sortable : true
			} ,{
				field : 'action',
				title : '操作',
				width : 200,
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					if(row.isdefault!=0){
						str += '&nbsp;&nbsp;';
						if ($.canEdit) {
							str += $.formatString('<a href="javascript:void(0)" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
						}
						str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
						if ($.canDelete) {
							str += $.formatString('<a href="javascript:void(0)" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
						}
					}
					return str;
				}
			} ] ],
   			toolbar : '#toolbar',
		})
	});
	function addFun() {
		parent.$.modalDialog({
			title : '添加',
			width : 450,
			height : 300,
			href : '${ctx}/bomMaterialContact/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#bomContactAddForm');
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
		parent.$.messager.confirm('询问', '您是否要删除当前联系人？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/bomMaterialContact/del', {
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
			id = rows[0].materialName;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '编辑',
			width : 450,
			height : 300,
			href : '${ctx}/bomMaterialContact/editPage?id='+ id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#bomMaterialEditForm');
					f.submit();
				}
			} ]
		});
	}
	
	function searchFunz() {
		dataGrid.datagrid('load', $.serializeObject($('#searchFormz')));
	}
	</script>

</head>

<body class="easyui-layout" data-options="fit:true,border:false">

	<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
		<form id="searchFormz">
			<table>
				<tr>
					<th>姓名:</th>
					<td><input name="contacts" placeholder=""/></td>
					<td>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFunz();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGridz" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/bomMaterialContact/add')}"> 
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
	</div>
  

<iframe name="ajaxFrame" style="display: none;"></iframe>
            <div style="text-align:right;margin-right:10px;margin-top:55px;">  
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'ope-finish'" onclick="restartNetworks()">确定</a>  
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'ope-cancel'" onclick="cancels()">取消</a>  
            </div>                  
</body>
</html>