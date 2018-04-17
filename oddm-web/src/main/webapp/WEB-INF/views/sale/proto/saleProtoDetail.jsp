<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>
	.ordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:left;}
	.tabletitle{padding-left:4px;padding-top:8px;height:26px;text-align:left; background-color:#c8d7e1;color:#333;font-weight: bold;font-family: "宋体";}
	td {padding:10px 10px 0px 0px;}
	table {padding-bottom:10px;}
	.lefttd{align: left; text-align: right;vertical-align:top}
	.td_body{   /* easyui不会自动识别英文字符串自动换行，nowrap: false不生效 */
		white-space: pre-wrap;
		word-wrap:break-word;
		word-break:break-all;
		line-height: 18px;
		color: #069;
		vertical-align:top;
	}
</style>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript">
$(function() {
});
</script>
<div class="styleeasyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow: auto;padding: 0px;width: 100%;" >
		<form id="saleProtoEditForm" method="post">
		<!-- 基础信息 -->
	    <table border="0" cellpadding="0" cellspacing="0px" class="ordertable" style="">
    	  <caption  class="tabletitle">样机明细：</caption >
     	  <tr>
  	         	<td class="lefttd" width="10%">客户名称：</td>
	            <td class="td_body">${saleProtoDto.clientName }</td>
           	    <td class="lefttd" width="10%">国家区域：</td>
	            <td class="td_body">${saleProtoDto.district }</td>
	            <td class="lefttd" width="10%">产品名称：</td>
	            <td class="td_body">${saleProtoDto.productName }</td>
	            <td class="lefttd" width="10%">尺寸：</td>
	            <td class="td_body"><fmt:formatNumber type="number" value="${saleProtoDto.size }" pattern="0.0" maxFractionDigits="1"/></td>
	        </tr>
	        <tr>
	       		<td class="lefttd" width="10%">外壳型号：</td>
	            <td class="td_body">${saleProtoDto.shell }</td>
	            <td class="lefttd" width="10%">外观/软件：</td>
	            <td class="td_body">${saleProtoDto.facade }</td>
	        	<td class="lefttd" width="10%">数量：</td>
	            <td class="td_body">${saleProtoDto.quantity }</td>
          	    <td class="lefttd" width="10%">是否收款：</td>
	            <td class="td_body">${saleProtoDto.payed }</td>
	             </tr>
	        <tr>
	            <td class="lefttd" width="10%">送样日期：</td>
	            <td class="td_body">${saleProtoDto.dateSend }</td> 
	         	<td class="lefttd" width="10%">收款日期：</td>
	            <td class="td_body">${saleProtoDto.datePayed }</td> 
	           	<td class="lefttd" width="10%">退还日期：</td>
	            <td class="td_body">${saleProtoDto.dateReturn }</td>
	           	<td class="lefttd" width="10%"></td>
	            <td class="td_body"></td>
	           </tr>
	          	<td class="lefttd" width="10%">PCBA型号：</td>
	            <td class="td_body" colspan="3">${saleProtoDto.pcba }</td>
	            <td class="lefttd" width="10%">备注：</td>
	            <td class="td_body" colspan="3" >${saleProtoDto.description }</td>
	        </tr>
	    </table>
	</form>
	</div>
</div>
<style>
	body {
		padding-top: 12px;
	}
</style>
<script src="${ctx}/lib/util/util_common.js" type="text/javascript" charset="utf-8"></script>
