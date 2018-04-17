<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {

		var json = [{"id":"1","name":"裸机物料"},{"id":"2","name":"包材物料"},{"id":"3","name":"配件物料"}];
		$('#materialTypeType').combobox({
			data : json,
			valueField:'id',    
			textField:'name',
			value : '${materialType.type}',
			panelHeight : 'auto'
		});
		
		$('#materialTypeAddForm').form({
			url : '${pageContext.request.contextPath}/materialType/add',
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
		<form id="materialTypeAddForm" method="post">
			<table class="grid">
				<tr>
					<td>物料名称</td>
					<td><input name="name" type="text" placeholder="请输入物料名称" class="easyui-validatebox span2" data-options="required:true,validType:['isBlank','length[0,64]']" value=""></td>
				</tr>
				<tr>
					<td>类型</td>
					<td><select id="materialTypeType" name="type" style="width: 140px; height: 29px;" class="easyui-validatebox" data-options="required:true,validType:['isBlank','length[0,64]']"></select></td>
				</tr>
				<tr>
					<td>描述</td>
					<td colspan="3"><textarea name="description" rows="" cols=""  maxlength="200" class="easyui-validatebox " data-options="validType:['isBlank','length[0,64]']"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
</div>