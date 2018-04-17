<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>

 <meta charset="utf-8">
<title>Insert title here</title>
<script src="./jslib/easyui1.4.2/jquery.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="./jslib/easyui1.4.2/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript">
$(function() {
	$('#testToken').form({
	    url:'${ctx}/user/resetall',
	    
	    success:function(result){
	    	console.log(result);
	    	result = $.parseJSON(result);
	    	
	    },
	    onLoadSuccess:function(data){
	    	console.log(data);
	    }
	});  
});  
function submitForm(){
	$('#testToken').submit();
	
}
</script>
</head>

<body>
	<form  method="post" id="testToken">
          <div class="login-head">
              <span>重置所有密码为默认值仅上线第一次使用</span>
          </div>
          <div class="login-bottom">
              <div class="login-submit">
                  <input id="loginSubmit" class="loginButton" value="验证" type="button" onclick="submitForm()"/>
              </div>
          </div>
</form>

</body>
</html>