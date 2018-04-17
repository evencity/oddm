<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		$.extend($.fn.validatebox.defaults.rules, {
			materialCodeLengthValidate : {
		        validator : function(value, param) {
		        	if(value.length != param)
		        		return false;
		            return true;
		        },
		        message : '物料编码的长度只能为14个字符！！'
		    },
		});
		
		$('#bomMaterialAddForm').form({
			url : '${pageContext.request.contextPath}/bomMaterial/add',
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
		<form id="bomMaterialAddForm" method="post">
			<table class="grid">
				<tr>
					<td>物料编号</td>
					<td><input name="mtlCode" type="text" placeholder="请输入物料编号" class="easyui-validatebox span3" style="width:250px;height:20px"  data-options="validType:['isBlank_','materialCodeLengthValidate[14]']" required="required" value=""></td>
				</tr>
				<tr>
					<td>物料名称</td>
					<td><input name="materialName" type="text" placeholder="请输入物料名称" class="easyui-validatebox span2" style="width:250px;height:20px" data-options="validType:['isBlank_','length[0,64]']" required="required" value=""></td>
				</tr>
				<tr>
					<td>描述</td>
					<td colspan="3"><input name="description"  style="width:250px;height:20px" class="easyui-validatebox" data-options="validType:['isBlank_','length[0,200]']" ></textarea></td>
				</tr>
				<tr>
					<td>规格型号及封装</td>
					<td><textarea name="specification"  rows="5" cols="38" class="easyui-validatebox span2" data-options="validType:['isBlank_','length[0,200]']" value=""></textarea></td>
				</tr>
				
			</table>
		</form>
	</div>
</div>