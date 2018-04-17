<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
	

	$(function() {
		
		$('#pid').combotree({
			url : '${ctx}/organization/companyList',
			parentField : 'pid',
			lines : true,
			panelHeight : 'auto'
		});
		
		$('#organizationAddForm').form({
			url : '${ctx}/organization/add',
			onSubmit : function() {
				progressLoad();
				var isValid = $(this).form('validate');
				if (!isValid) {
					progressClose();
				}
				return isValid;
			},
			success : function(result) {
				progressClose();
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为organization.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
		
	});
</script>
<div style="padding: 3px;">
	<form id="organizationAddForm" method="post">
		<table class="grid">
			<tr>
				<td>部门名称</td>
				<td><input name="name" type="text" placeholder="请输入部门名称" class="easyui-validatebox" data-options="required:true,validType:['isBlank','length[0,64]']" ></td>
			</tr>
			<tr>
				<td>菜单图标</td>
				<td><input  name="icon" value="icon-folder" class="easyui-validatebox" data-options="required:true,validType:['isBlank','length[0,64]']"/></td>
			</tr>
			<tr>
				<td>排序</td>
				<td ><input  name="seq" value="" class="easyui-numberbox" data-options="required:true"/></td>
			</tr>
			<tr>
				<td>上级部门</td>
				<td colspan="3"><select id="pid" name="pid" style="width:200px;height: 29px;"></select>
				<a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#pid').combotree('clear');" >清空</a></td>
				
			</tr>
		</table>
	</form>
</div>