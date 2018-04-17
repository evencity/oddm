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
	.docEditTitle{padding-left: 10px;height: 35%;font-size: 1.2em}
	.color_069{color: #069}
	.btn-default {border: 1px solid #DDDDDD;background: white;padding: 4px 13px;border-radius: 5px;cursor: pointer;color: #333;margin-right: 10px}
	.datagrid-view1 .datagrid-body {overflow-y: auto;overflow-x: hidden;}
</style>
	<script type="text/javascript">
	var documentId = "${documentId}";
	//var documentName = "${documentName}";
	var documentName = $("#documentName").text();
	var orderId = "${orderId}";
	var orderIdForInput = "${orderIdForInput}";
	$(function() {
		$.ajax({
			url : '${ctx}/document/get?id=' + documentId,
			type : "GET",
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				
				documentName = documentName.replace("@@",data.nameMt);
				$("#documentName").text( documentName.substring(0, documentName.lastIndexOf("V")) );
				documentName = documentName.replace(new RegExp(' ',"gm"),"_").replace(new RegExp('\\+',"gm"),"_");
			//	$("#documentName").attr("data-fileName",documentName)
				documentName = documentName.replace(/[\*\:\"\<\>\?\|\/\\ ]/g,"");     
				console.log(documentName)
				$('#orderDocName').text(documentName+".rar");
			
				$('#nameMt').val(data.nameMt);
			}
		})
		clearInput();		
	});
	function clearInput(){ 
		
		var _iframe = document.getElementById('document_'+orderIdForInput).contentWindow;  
		var input  =_iframe.document.getElementById('documentFileUpdate'); 
		input.outerHTML = input.outerHTML;
		console.log("清除订单input文档选择")
		
	}
	
	function openBrowseEdit(){ 
		 var ie=navigator.appName=="Microsoft Internet Explorer" ? true : false;  
		 console.log('document_'+orderIdForInput)
		 
		if(ie){ 
			var _iframe = document.getElementById('document_'+orderIdForInput).contentWindow;  
			var _div =_iframe.document.getElementById('documentFileUpdate').click(); 
			/* document.getElementById("testFile").click();  */
		}else{
			var a = document.createEvent("MouseEvents");//FF的处理 
			a.initEvent("click", true, true);  
			var _iframe = document.getElementById('document_'+orderIdForInput).contentWindow;  
			var _div =_iframe.document.getElementById('documentFileUpdate').dispatchEvent(a); 
			/* document.getElementById("testFile").dispatchEvent(a);  */
		} 
	} 
	
	</script>
</head>
<div class="docEditTitle">文档信息：<span class="color_069" id="orderDocName"></span></div>

	<div class="docEditTitle">文档上传：
			<button  class=" btn-default " onclick="javascript:openBrowseEdit();" >选择文档</button><span id="documentFilename"></span>
	</div>
	<div id="uploadProgterssbar" class="easyui-progressbar" data-options="value:0" style="width: 280px;display: none;margin-top: 10px;" ></div>
	<span id="documentName" style="display:none">${documentName}</span><br>
	&nbsp;&nbsp;<span style="color:red">(*注意：如果文件名中包含  \ /:*?"&lt;&gt;|  ,会自动屏蔽不显示！)</span>
</html>