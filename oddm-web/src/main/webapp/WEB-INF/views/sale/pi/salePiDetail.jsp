<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>
	.ordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:left;}
	.tabletitle{padding-left:4px;padding-top:8px;height:26px;text-align:left; background-color:#c8d7e1;color:#333;font-weight: bold;font-family: "宋体";}
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
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 0px;width: 100%;" >
		<!-- 基础信息 -->
	    <table border="0" cellpadding="0" cellspacing="10px" class="ordertable">
    		<caption  class="tabletitle">PI明细：</caption >
	        <tr>
	            <td class="lefttd" width="10%">PI编号：</td>
	            <td class="td_body" width="15%">${salePiDto.piNo }</td>
	            <td class="lefttd" width="10%">客户名称：</td>
	            <td class="td_body" width="15%">${salePiDto.clientName }</td>
	           	<td class="lefttd" width="10%">业务员：</td>
	            <td class="td_body" width="15%">${salePiDto.seller }</td>
	        	<td class="lefttd" width="10%">国家：</td>
	            <td class="td_body" width="15%">${salePiDto.district }</td>
	        </tr>
     	    <tr>
	            <td class="lefttd" width="10%">摘要：</td>
	            <td class="td_body" width="15%">${salePiDto.summary }</td>
	            <td class="lefttd" width="10%">下单日期：</td>
	            <td class="td_body" width="15%">${salePiDto.orderDate }</td> 
	            <td class="lefttd" width="10%">总金额：</td>
	            <td class="td_body" width="15%">${salePiDto.currency }<fmt:formatNumber type="number" value="${salePiDto.amount }" pattern=",000.00" maxFractionDigits="2"/></td>
	            <td class="lefttd" width="10%"></td>
	            <td class="" width="15%"></td>
	        </tr>
	        <tr>
	            <td class="lefttd" width="10%">PO编号：</td>
	            <td class="td_body" colspan="3" width="40%">${salePiDto.poNo }</td>
	            <td class="lefttd" width="10%">备注：</td>
	            <td class="td_body" colspan="3" width="40%">${salePiDto.description }</td>
	        </tr>
	    </table>
	</div>
</div>
<style>
	body {
		padding-top: 12px;
	}
</style>
<script src="${ctx}/lib/util/util_common.js" type="text/javascript" charset="utf-8"></script>