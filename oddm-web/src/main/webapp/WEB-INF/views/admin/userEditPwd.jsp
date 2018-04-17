<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
	$(function() {
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
		
		$('#editUserPwdForm').form({
			url : '${ctx}/user/editUserPwd',
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
					parent.$.messager.alert('提示', result.msg, 'info');
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<c:if test="${sessionInfo.name == null}">
			<div>登录已超时，请重新登录.</div>
			<script type="text/javascript" charset="utf-8">
				try {
					window.location.href='${ctx}/admin/index';
				} catch (e) {
				}
			</script>
		</c:if>
		<c:if test="${sessionInfo.name != null}">
			<form id="editUserPwdForm" method="post">
				<table class="grid">
					<tr>
						<th>登录名</th>
						<td>${sessionInfo.name}</td>
					</tr>
					<tr>
						<th>原密码</th>
						<td><input name="oldPwd" type="password" placeholder="请输入原密码" class="easyui-validatebox" data-options="required:true,validType:'isBlank'"></td>
					</tr>
					<tr>
						<td>新密码： </td>
						<td>
							<input id="password" name="password" validType="password" class="easyui-validatebox" required="true" type="password" value="" invalidMessage="密码必须由 4-16位字母、数字、特殊符号线组成"/>
						</td>
					</tr>
					<tr>
						<td>确认新密码： </td>
						<td><input type="password" name="repassword" id="repassword" required="true" class="easyui-validatebox"  validType="equalTo['#password']" invalidMessage="两次输入密码不匹配"/></td>
					</tr>
				</table>
			</form>
		</c:if>
	</div>
</div>