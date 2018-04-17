<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {

		$('#materialPackingType').combobox({
			url : '${pageContext.request.contextPath}/materialType/materialType?type=3',
			/* parentField : 'pid', */
			 valueField:'id',    
			 textField:'name',
			 panelHeight : 'auto'
		});
		var json = [{"id":"1","name":"通用"},{"id":"2","name":"客制化"}];
		$('#normal').combobox({
			data : json,
			valueField:'id',    
			textField:'name' ,
			panelHeight : 'auto'
		});
		$('#materialPackingAddForm').form({
			url : '${pageContext.request.contextPath}/materialPacking/add',
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
		<form id="materialPackingAddForm" method="post">
			<table class="grid">
				<tr>
					<td>物料名称</td>
					<td><input name="name" type="text" placeholder="请输入物料名称" class="easyui-validatebox span2" data-options="required:true,validType:['isBlank','length[0,64]']" value=""></td>
				</tr>
				<tr>
					<td>类型</td>
					<td><select id="materialPackingType" name="typeId" style="width: 140px; height: 29px;" class="easyui-validatebox" data-options="required:true,validType:['isBlank','length[0,64]']"></select></td>
				</tr>
				<tr>
					<td>通用/客制化</td>
					<td><select id="normal" name="normal" style="width: 140px; height: 29px;" class="easyui-validatebox" data-options="required:true,validType:'isBlank'"></select></td>
				</tr>
				<tr>
					<td>基础物料</td>
					<td><select name="sex" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="2" selected="selected">是</option>
							<option value="1" >否</option>
					</select></td>
				
				</tr>
				<tr>
					<td>描述</td>
					<td colspan="3"><textarea name="description" rows="" cols=""  maxlength="200" class="easyui-validatebox " data-options="validType:['isBlank','length[0,200]']" ></textarea></td>
				</tr>
			</table>
		</form>
	</div>
</div>