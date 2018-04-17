<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="edge" />
<link rel="shortcut icon" href="${ctx}/style/images/index/favicon.png" />

<!-- [my97日期时间控件] -->
<%-- <script type="text/javascript" src="${ctx}/jslib/My97DatePicker/WdatePicker.js" charset="utf-8"></script>
 --%>
<!-- [jQuery] -->
<script src="${ctx}/jslib/easyui1.4.2/jquery.min.js" type="text/javascript" charset="utf-8"></script>

<!-- [EasyUI] -->
<link id="easyuiTheme" rel="stylesheet" href="${ctx}/jslib/easyui1.4.2/themes/<c:out value="${cookie.easyuiThemeName.value}" default="gray"/>/easyui.css" type="text/css">
<link id="easyuiTheme" rel="stylesheet" href="${ctx}/jslib/easyui1.4.2/themes/icon.css" type="text/css">
<script type="text/javascript" src="${ctx}/jslib/easyui1.4.2/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/jslib/easyui1.4.2/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>

<!-- [扩展JS] -->
<script type="text/javascript" src="${ctx}/jslib/extJs.js" charset="utf-8"></script>

<!-- [扩展lightmvc样式] -->
<link rel="stylesheet" href="${ctx}/style/css/lightmvcV2.css" type="text/css">

<style>
.messager-body{
	overflow: auto;
}
</style>
<script type="text/javascript">
	$.ajaxSetup({
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			if (XMLHttpRequest.status == 403) {
				alert('您没有权限访问此资源或进行此操作');
				return false;
			}
		},
		complete : function(XMLHttpRequest, textStatus) {
			var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头,sessionstatus，
			if (sessionstatus == 'timeout') {
				//如果超时就处理 ，指定要跳转的页面  
				alert('登录超时, 请重新登录.');
				var loginVersion = XMLHttpRequest.getResponseHeader("login-verion");
				if (loginVersion == 'V2') {
					window.top.location.href = "${ctx}/adminV2/index"; //跳转到登陆页面V2
				} else {
					window.top.location.href = "${ctx}/"; //跳转到登陆页面
				}
			}
		}
	});
</script>