<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<style type="text/css">
	#online {
		float: right;
		padding: 20px 40px;
		line-height: 20px;
		//border: gray solid 1px;
		//height: 94%;
	}
</style>

<script type="text/javascript">
/* var i = 0;
setInterval(getOnlineCount, 1000);
function getOnlineCount(){
	i++;
	$.ajax({
		url :'${ctx}/monitor/usersOnline',
		type : "GET",
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				console.log(data);
				$('#onlinecount').html(data+""+i);
			}
	  })
} */
	$(function() {
		var url = '${ctx}/monitor/usersOnline';
		$.ajax({
			url :url,
			type : "GET",
 			dataType : "json",
 			contentType : "application/json; charset=utf-8",
 			success : function(data) {
 			//	console.log(data);
 				$('#loginUser').html(data.loginUser);
 				$('#loginUsers').html(data.loginUsers);
 				var userNameSet = "<br>";
 				/* if (data.userNameSet != null) { //遍历 数组方式1
 					for (var i=0; i<data.userNameSet.length; i++){
 	 					//console.log("当前在线用户： "+data.userNameSet[i]);
 	 					userNameSet += data.userNameSet[i] +"<br>";
 	 				}
 					$('#userNameSet').html(userNameSet);
 				} */
 				if (data.userNameMap != null) {
 					for (var key in data.userNameMap){ //遍历map方式
 	 					//console.log("当前在线用户： "+data.userNameMap[key]);
 	 					userNameSet += key+"&nbsp;&nbsp;&nbsp;"+data.userNameMap[key] +"<br>";
 	 				}
 					$('#userNameSet').html(userNameSet);
 				}
 			}
 	  })
	});
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id= "online">
	当前链接页面数： <span id= "loginUsers" style="color: red;"></span> <br>
	当前在线账号数： <span id= "loginUser" style="color: blue;"></span> <br>
	在线用户名： <span id= "userNameSet" style="color: blue;"></span>
	</div>
</body>
</html>