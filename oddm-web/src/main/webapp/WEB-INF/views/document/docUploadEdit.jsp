<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="X-UA-Compatible" content="edge" />

<title></title>
<style type="text/css">
	.ordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:left;}
	.tabletitle{text-align:left; background-color:#c8d7e1;color:#333;font-size:1.4em; font-weight:500;}
	.tabletop{text-align:left; background-color:#DCE0E2;color:#333;font-size:1.2em; font-weight:500;}
	.ordertable tr td{padding:10px 0 10px 6px;}
	.docEditTitle{padding-left: 10px;height: 40px;font-size: 1.2em}
	.color_069{color: #069}
	.datagrid-view1 .datagrid-body {overflow-y: auto;overflow-x: hidden;}
	.btn-default {border: 1px solid #DDDDDD;background: white;padding: 4px 13px;border-radius: 5px;cursor: pointer;color: #333;margin-right: 10px}
</style>
	<script type="text/javascript">
	function openBrowse(){ 
		var ie = navigator.appName=="Microsoft Internet Explorer" ? true : false;  
		if(ie){ 
			var _iframe = document.getElementById('/docUpload/manager').contentWindow;  
			var _div =_iframe.document.getElementById('documentUploadFile').click(); 
			/* document.getElementById("testFile").click();  */
		}else{
			var a = document.createEvent("MouseEvents");//FF的处理 
			a.initEvent("click", true, true);  
			var _iframe = document.getElementById('/docUpload/manager').contentWindow;  
			var _div =_iframe.document.getElementById('documentUploadFile').dispatchEvent(a); 
			/* document.getElementById("testFile").dispatchEvent(a);  */
		}  
	} 
	function clearInput(){ 
		
		var _iframe = document.getElementById('/docUpload/manager').contentWindow;  
		var input  =_iframe.document.getElementById('documentUploadFile'); 
		input.outerHTML = input.outerHTML;
		console.log("清除订单input文档选择")
		
	}
	</script>
</head>
<div class="docEditTitle">文档信息：<span class="color_069" id="orderDocName">${documentName}.rar</span></div>

	<div class="docEditTitle">文档上传：
		<button  class=" btn-default " onclick="javascript:openBrowse();" >选择文档</button><span id="documentUploadFilename"></span>
	</div>
<div id="uploadProgterssbar" class="easyui-progressbar" data-options="value:0" style="width: 280px;display: none" ></div>
</html>