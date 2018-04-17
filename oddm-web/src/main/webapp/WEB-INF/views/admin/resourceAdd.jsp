<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
	

	$(function() {
		
		$('#pid').combotree({
			url : '${ctx}/resource/allTree?flag=false',
			parentField : 'pid',
			lines : true,
			//panelHeight : 'auto'
			panelHeight : 300
		});
		
		$('#resourceAddForm').form({
			url : '${ctx}/resource/add',
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
					parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为resource.jsp页面预定义好了
					//parent.layout_west_tree.tree('reload');
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
		
	});
</script>
<div style="padding: 3px;">
	<form id="resourceAddForm" method="post">
		<table class="grid">
			<tr>
				<td>资源名称</td>
				<td><input name="name" type="text" placeholder="请输入资源名称" class="easyui-validatebox span2" data-options="required:true,validType:['isBlank','length[0,64]']" ></td>
				<td>资源类型</td>
				<td><select name="resourcetype" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="1">菜单</option>
							<option value="2">按钮</option>
				</select></td>
			</tr>
			<tr>
				<td>资源路径</td>
				<td><input name="url" type="text" placeholder="请输入资源路径" class="easyui-validatebox span2" data-options="required:true,validType:['isBlank','length[0,64]']"></td>
				<td>菜单图标</td>
				<td ><input  name="icon" class="easyui-validatebox "  data-options="required:true,validType:['isBlank','length[0,64]']"/></td>
			</tr>
			<tr>
				<td>排序</td>
				<td ><input  name="seq" class="easyui-numberbox"  data-options="required:true"/></td>
			</tr>
			<tr>
				<td>上级资源</td>
				<td colspan="3"><select id="pid" name="pid" style="width: 200px; height: 29px;"></select>
				<a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#pid').combotree('clear');" >清空</a></td>
			</tr>
			<tr></tr>
		</table>
	</form>
</div>