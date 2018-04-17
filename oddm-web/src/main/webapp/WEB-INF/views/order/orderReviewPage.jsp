<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="edge" />

<title>订单信息</title>
<style type="text/css">
	.ordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:left;}
	.tabletitle{text-align:left; background-color:#c8d7e1;color:#333;font-size:1.4em; font-weight:500;}
	.tabletop{text-align:left; background-color:#DCE0E2;color:#333;font-size:1.2em; font-weight:500;}
	.ordertable tr td{padding:10px 0 10px 6px;}
	.docAddTitle{padding-left: 10px;height: 5%;font-size: 1.2em}
	.color_069{color: #069}
	.datagrid-view1 .datagrid-body {overflow-y: auto;overflow-x: hidden;}
	#orderReviewDiv{margin: 35px 0px 0px 80px;}
</style>
	<script type="text/javascript">
	</script>
</head>

<div>
	<div id="orderReviewDiv">
		<input type="radio" name="orderPassOrNo" value="1">通过
		<input type="radio" name="orderPassOrNo" value="2">不通过
	</div>
</div>
</html>