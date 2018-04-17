<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script src="${ctx}/jslib/easyui1.4.2/jquery.min.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/jslib/easyui1.4.2/jquery.cookie.js" type="text/javascript"></script>
${msg}
<script type="text/javascript" charset="utf-8">
	console.log($.cookie("login-version"));
	var loginVersion = "";//$.cookie("login-version");//实现不了，暂时屏蔽
	if (loginVersion == "V2") {
		setTimeout("parent.location.href='${ctx}/adminV2/index'",1500);
	} else {
		setTimeout("parent.location.href='${ctx}/'",1500);
	}
</script>