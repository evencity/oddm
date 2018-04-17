<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {

		$('#sysConfigAddForm').form({
			url : '${pageContext.request.contextPath}/sysConfig/add',
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
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;" >
		<form id="sysConfigAddForm" method="post">
			<table class="grid">
				<tr>
					<td>名称</td>
					<td><input name="key" size="40" type="text" placeholder="输入名称" class="easyui-validatebox span2" data-options="required:true,validType:['isBlank','length[0,64]']" value=""></td>
				</tr>
				<tr>
					<td>值</td>
					<td><input name="value" size="40" type="text" placeholder="输入值" class="easyui-validatebox span2" data-options="required:true,validType:['isBlank','length[0,64]']" value=""></td>
				</tr>
				<tr>
					<td>描述</td>
					<td colspan="3"><textarea name="description" rows="" cols="50" maxlength="200" ></textarea></td>
				</tr>
			</table>
		</form>
	</div>
</div>
