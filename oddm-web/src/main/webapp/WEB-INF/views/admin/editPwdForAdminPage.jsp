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
		
		$('#editUserPwdForAdminForm').form({
			url : '${ctx}/user/editUserPwdForAdmin',
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
	//string转chars
	function toArray(str){
	    if(typeof str !="string"){
	        return [];
	    }
	    var arr=[];
	    for(var i=0;i<str.length;i++){
	        arr.push(str.charAt(i))
	    } 
	    return arr;
	}
 	
  //生成随机密码
  	var str = "abcdefghijkmnpq/!@#$%*/rstuvwxyz23456789!@#$%*ABCDEFGHIJKLMNPQRSTUVWXYZ";
  //var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
	function GetRandomNum() {
		var chars =  toArray(str);
		var len = str.length-1;
	//	console.log(chars);
	     var res = "";
	     for(var i = 0; i < 12 ; i ++) {//12为密码长度
	         var id = Math.ceil(Math.random()*len);
	         res += chars[id];
	     }
	     return res;
	}

	function generateRandPassword() {
		var password = GetRandomNum();
		$('#password').val(password);
	}
	
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
			<form id="editUserPwdForAdminForm" method="post">
				<table class="grid">
					<tr>
						<td>用户名</td>
						<td>${user.username}</td>
					</tr>
					<tr>
						<td>新密码： </td>
						<td>
							<input type="hidden" name="id" value="${user.id}">
							<input id="password" name="password" validType="password" class="easyui-validatebox" required="true" type="text" value="" invalidMessage="密码必须由 4-16位字母、数字、特殊符号线组成"/>
							<a href="javascript:return false;" onclick="generateRandPassword();">随机生成</a>
						</td>
					</tr>
					<!-- <tr>
						<td>确认新密码： </td>
						<td><input type="password" name="repassword" id="repassword" required="true" class="easyui-validatebox"  validType="equalTo['#password']" invalidMessage="两次输入密码不匹配"/></td>
					</tr> -->
				</table>
			</form>
		</c:if>
	</div>
</div>