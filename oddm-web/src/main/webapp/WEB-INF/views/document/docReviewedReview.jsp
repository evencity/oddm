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
</style>
	<script type="text/javascript">
	//var docName = "${docName}";
	var filepath = "${path}";
	$(function() {
		//$('#docName').html(docName);
		$('#docName').off('click').on('click',function(){
			if(filepath != null){
				var param = {filePath:filepath}
				$.ajax({
					url : '${pageContext.request.contextPath}/token/download',
					type : "POST",
					data : JSON.stringify(param),
					dataType : "json",
					contentType : "application/json; charset=utf-8",
					success : function(data) {
						if(data.status !='传参解析异常'){
							 client = new OSS.Wrapper({
									region: data.region,
									accessKeyId: data.accessKeyId,
									accessKeySecret: data.accessKeySecret,
									stsToken : data.securityToken,
									bucket: data.bucket
							    });
							    var objectKey = data.path;
								
							    var saveAs = filepath.split('/')[filepath.split("/").length-1];
							    console.log(objectKey + ' => ' + saveAs);
							    var result = client.signatureUrl(objectKey, {
							      expires: 3600,
							      response: {
							        'content-disposition': 'attachment; filename="' + (saveAs) + '"'
							      }
							    });
							    download_file(result)
							    
							   /*  window.location = result; */
							    console.log(result);
							    /* try{
							    	 
							    }catch(function (err) {
							        console.log(err);
							        parent.$.messager.alert('提示', '文档上传出错！！', 'info');
							    }); */
							   
						}else{
							 parent.$.messager.alert('提示', '文档下载异常，请联系管理员！');
						}
						 
					}
				})
			}else{
				parent.$.messager.alert('提示', '文档路径出错！');
			}
			
		});
	});
	 function download_file(url)  
     {  
         if (typeof (download_file.iframe) == "undefined")  
         {  
             var iframe = document.createElement("iframe");  
             download_file.iframe = iframe; 
             document.body.appendChild(download_file.iframe);  
             download_file.iframe.addEventListener( "load", function(){
     	         //代码能执行到这里说明已经载入成功完毕了
     	           this.removeEventListener( "load", arguments.call, false);
     	         //这里是回调函数
     	         parent.parent.$.messager.alert('提示', '文档不存在！');
     	   }, false);
         }  
         
     	 download_file.iframe.src = url;  
         download_file.iframe.style.display = "none";  
     }  
	
	</script>
</head>

<div>
	<div id="docName" style="padding: 10px;cursor: pointer;text-decoration: underline;">${docName}</div>
	<div id="docReview">
		<input type="radio" name="documentPassOrNo" value="3">通过
		<input type="radio" name="documentPassOrNo" value="4">不通过
	</div>
</div>
</html>