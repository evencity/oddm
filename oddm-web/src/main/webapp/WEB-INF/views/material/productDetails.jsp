<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<style>
	.ordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:left;}
	.tabletitle{text-align:left; background-color:#c8d7e1;color:#333;font-size:1.4em; font-weight:500;}
	.tabletop{text-align:left; background-color:#DCE0E2;color:#333;font-size:1.2em; font-weight:500;}
	.ordertable tr td{padding:10px 0 10px 6px;}
	.inputs{border:1px solid #CCC;width:90%;padding:5px 0 2px 4px;font-size:12px;color:#999;}
	.lefttd{width:65px;}
    .lefttd90{width:90px; text-align:right;}
    .righttd{color:#069;}
    .bordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:center;border-collapse:collapse;}
	.bordertable td{border:1px solid #ccc;padding:10px 0 10px 6px;height:20px;}
 	.textareacss{width:90%;border: 0px} 
	.choose{color: #06c;cursor: pointer;}
	.table_input{border: 0;background: none;padding: 5px 0;width: 95%;color: #666;}
</style>
<jsp:include page="../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />

<title>机型管理</title>
	<script type="text/javascript">
	var productId = ${productId}
	$(function() {
		$.ajax({
         	type: 'GET',
             url: "${ctx}/product/get?id="+productId ,
             dataType: "json",
             contentType: "application/json; charset=utf-8",
             success: function(data){
            	var name = data.name;
            	var type = data.typeName;
            	var description = data.description;
            	var materialBares = data.materialBareDTOs;
            	var materialFittings = data.materialFittingDTOs;
            	var materialPackings = data.materialPackingDTOs; 
            	var hardware ="";
            	var shell ="";
            	var screen ="";
            	var fitting ="";
            	var packing ="";
            	$("#product_name").text(name);
            	$("#product_type").text(type);
            	$("#product_description").text(description);
            	
            	for(var i in materialBares){
            		if(materialBares[i].typeId == 1){
                		//硬件
                		hardware += materialBares[i].name  + "，";;
                	}
    				if(materialBares[i].typeId == 2){
                		//外壳
                		shell += materialBares[i].name  + " ，";;
                	}
    				if(materialBares[i].typeId == 3){
    					//屏
    					screen += materialBares[i].name  + "，";;
    				}
            	}
            	shell = shell.substring(0,shell.length - 1);
            	$('#materialBare_hardware').append('<td style="color:#06c" align="left">'+ hardware +'</td>');
            	$('#materialBare_shell').append('<td style="color:#06c"  align="left">'+ shell +'</td>')
            	$('#materialBare_screen').append('<td style="color:#06c"  align="left">'+ screen +'</td>')
            	if(materialFittings != null){
            		for(var i in materialFittings){
                    	fitting += materialBares[i].name  + "，";;
                	}
                	$('#materialFitting').append('<td style="color:#06c" align="left">'+ fitting +'</td>')
            	}
            	if(materialPackings != null){
            		for(var i in materialPackings){
                    	packing += materialBares[i].name +"，";
                	}
                	$('#materialPacking').append('<td style="color:#06c" align="left">'+ packing +'</td>')
            	}
            	
             }
       })
	});
	
	</script>
</head>
<body>
	<div>
		<table class="grid">
			<tr class="tabletitle">
	            <td colspan="4">机型详情</td>
	        </tr>
			<tr >
				<td width="20%">机型名称</td>
				<td  colspan="3" id="product_name"></td>
			</tr>
			<tr>
				<td>类型</td>
				<td  colspan="3" id="product_type"></td>
			</tr>
			<tr>
				<td>描述</td>
				<td  colspan="3" id="product_description"></td>
			</tr>
		</table>
	    
	    <!-- 裸机包装 -->
	     <!-- <table border="0" cellpadding="0" cellspacing="0" class="bordertable" style="margin:0px;">
	        <tr class="tabletitle">
	            <td colspan="4">裸机包装组 </td>
	        </tr>
	        <tr class="tabletop">
	            <td colspan="4">硬件部件： </td>
	        </tr>
	         <tr id="materialBare_hardware"></tr>
        </table> -->
        <table border="0" cellpadding="0" cellspacing="0" class="bordertable" style="margin:0px;">
	        <tr class="tabletitle">
	            <td colspan="4">外壳组件： </td>
	        </tr>
	         <tr id="materialBare_shell"></tr>
	    </table>
       <!--  <table border="0" cellpadding="0" cellspacing="0" class="bordertable">
	        <tr class="tabletop">
	            <td colspan="4">屏： </td>
	        </tr>
	         <tr id="materialBare_screen"></tr>
	    </table> -->
	    
	    <!-- 配件信息-->
	     <!-- <table border="0" cellpadding="0" cellspacing="0" class="bordertable">
	        <tr class="tabletitle">
	            <td colspan="4">配件部分：  </td>
	        </tr>
	         <tr id="materialFitting"></tr>
	    </table>
	    
	      包材信息
	     <table border="0" cellpadding="0" cellspacing="0" class="bordertable">
	        <tr class="tabletitle">
	            <td colspan="4">包材部分：  </td>
	        </tr>
	         <tr id="materialPacking"></tr>
	    </table> -->
	</div>
	
</body>
</html>