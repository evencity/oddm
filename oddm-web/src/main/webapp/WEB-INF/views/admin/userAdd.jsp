<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">


	$(function() {
		
		$('#organizationId').combotree({
			url : '${ctx}/organization/dept',
			parentField : 'pid',
			lines : true,
			//panelHeight : 'auto'
		});
		
		$('#roleIds').combotree({
		    url: '${ctx}/role/tree',
		    multiple: true,
		    required: true,
		   // panelHeight : 'auto'
		});
		
		$('#userAddForm').form({
			url : '${ctx}/user/add',
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
					parent.$.messager.alert('提示', result.msg, 'warning');
				}
			}
		});
		$.extend($.fn.validatebox.defaults.rules, {  
		    /*必须和某个字段相等*/
		    equalTo: {
		        validator:function(value,param){
		            return $(param[0]).val() == value;
		        },
		        message:'字段不匹配'
		    },
		    password : {
		    	 validator : function(value) {
		             return /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[~!@#$%^&*()_+`\-={}:';'<>?,.\/]).{4,16}$/.test(value);
		         },
		         message : '密码必须由 4-16位字母、数字、特殊符号线组成'
		    }
		});
	});
	
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="userAddForm" method="post">
			<table class="grid">
				<tr>
					<td>登录名</td>
					<td><input name="loginname" type="text" placeholder="请输入登录名称" class="easyui-validatebox" data-options="required:true,validType:['isBlank','length[0,64]']" value=""></td>
					<td>姓名</td>
					<td><input name="username" type="text" placeholder="请输入姓名" class="easyui-validatebox" data-options="required:true,validType:['isBlank','length[0,64]']" value=""></td>
				</tr>
				<tr>
					<!-- <td>密码</td>
					<td><input name="password" type="password" placeholder="请输入密码" class="easyui-validatebox" data-options="required:true,validType:'isBlank'"></td>
					<td>密码</td>
					<td><input name="password" type="password" placeholder="请输入密码" class="easyui-validatebox" data-options="required:true,validType:'isBlank'"></td> -->
					<td>密码： </td>
					<td>
						<input id="password" name="password" validType="password" class="easyui-validatebox" required="true" type="password" value="" invalidMessage="密码必须由 4-16位字母、数字、特殊符号线组成"/>
					</td>
					<td>确认密码： </td>
					<td><input type="password" name="repassword" id="repassword" required="true" class="easyui-validatebox"  validType="equalTo['#password']" invalidMessage="两次输入密码不匹配"/></td>
				</tr>
				<tr>
					<td>性别</td>
					<td><select name="sex" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="1" >保密</option>
							<option value="2" selected="selected">男</option>
							<option value="3" >女</option>
					</select></td>
				
					<td>入职日期</td>
					<td><input type="text" name="age" class="inputs easyui-datebox"/></td>
				</tr>
				<tr>
					<td>部门</td>
					<td><select id="organizationId" name="organizationId" style="width: 140px; height: 29px;" class="easyui-validatebox" data-options="required:true,validType:['isBlank','length[0,64]']"></select></td>
					<td>角色</td>
					<td><select id="roleIds"  name="roleIds"   style="width: 140px; height: 29px;"></select></td>
				</tr>
				<!-- tr>
					<td>电话</td>
					<td colspan="3">
						<input type="text" name="phone" class="easyui-numberbox"/>
					</td>
				</tr> -->
			</table>
		</form>
	</div>
</div>