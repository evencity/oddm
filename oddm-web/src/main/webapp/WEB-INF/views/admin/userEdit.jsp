<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
	var age = '${user.age}' 
	$(function() {
		var orgCount = 0;  
		$('#organizationId').combotree({
			url : '${ctx}/organization/dept',
			parentField : 'pid',
			lines : true,
		//	panelHeight : 'auto',
			value : '${user.organizationId}',
			panelHeight : 250
		});
		
		$('#roleIds').combotree({
			url : '${ctx}/role/tree',
			parentField : 'pid',
			lines : true,
		//	panelHeight : 'auto',
			multiple : true,
			//required: true,
			cascadeCheck : false,
			value : $.stringToList('${user.roleIds}')
		});
		
		$('#userEditForm').form({
			url : '${ctx}/user/edit',
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
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
		$("#sex").val('${user.sex}');
		$("#state").val('${user.state}');
		/* $('#age').datebox('setValue',age);  */
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="userEditForm" method="post">
			<div class="light-info" style="overflow: hidden;padding: 3px;">
				<div></div>
			</div>
			<table class="grid">
				<tr>
					<td>登录名</td>
					<td><input name="id" type="hidden"  value="${user.id}"><input type="hidden" name="password" value="${user.password}"/>
					<input name="loginname" type="text" placeholder="请输入登录名称" class="easyui-validatebox" data-options="required:true,validType:['isBlank','length[0,64]']" value="${user.loginname}"></td>
					<td>姓名</td>
					<td><input name="username" type="text" placeholder="请输入姓名" class="easyui-validatebox" data-options="required:true,validType:['isBlank','length[0,64]']"value="${user.username}"></td>
				</tr>
				<tr>
					<td>性别</td>
					<td><select name="sex" id="sex"  class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="1">保密</option>
							<option value="2">男</option>
							<option value="3">女</option>
					</select></td>
					<td>入职日期</td>
					<td><input type="text" name="age" id="age" value="${user.age}" class="inputs easyui-datebox"/></td>
				</tr>
				<tr>
					<td>部门</td>
					<td><select id="organizationId" name="organizationId" style="width: 140px; height: 29px;" class="easyui-validatebox" data-options="required:true,validType:['isBlank','length[0,64]']"></select></td>
					<td>角色</td>
					<td><input  id="roleIds" name="roleIds" style="width: 140px; height: 29px;"/></td>
				</tr>
				<tr>
					<td>状态</td>
					<td><select name="state" id="state"  class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="1">正常</option>
							<option value="2">冻结</option>
						</select>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>