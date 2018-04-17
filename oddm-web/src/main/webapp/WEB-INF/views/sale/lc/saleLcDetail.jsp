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

</script>
<div class="styleeasyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow: auto;padding: 0px;width: 100%;" >
		<form id="saleLcAddForm" method="post">
		<!-- 基础信息 -->
	    <table border="0" cellpadding="0" cellspacing="10px" class="ordertable">
    	  <caption  class="tabletitle">LC收款台账明细：</caption >
     	  <tr>
   	         	<td class="lefttd" width="10%">信用证号：</td>
	            <td class="td_body" width="15%">${saleLcDto.creditNo }</td>
	            <td class="lefttd" width="10%">信用证总金额：</td>
      	        <td class="td_body" width="15%"><fmt:formatNumber type="number" value="${saleLcDto.creditAmount }" pattern=",000.00" maxFractionDigits="2"/></td>
	            <td class="lefttd" width="10%"></td>
	            <td class="td_body" width="15%"></td>
	            <td class="lefttd" width="10%"></td>
	            <td class="td_body" width="15%"></td>
	        </tr>
	        <tr>
	       		<td class="lefttd" width="10%">发票号：</td>
	            <td class="td_body" width="15%">${saleLcDto.invoiceNo }</td>
	            <td class="lefttd" width="10%">发票金额：</td>
	            <td class="td_body" width="15%"><fmt:formatNumber type="number" value="${saleLcDto.invoiceAmount }" pattern=",000.00" maxFractionDigits="2"/></td>
	        	<td class="lefttd" width="10%">币种：</td>
	            <td class="td_body" width="15%">${saleLcDto.currency }</td>
	        	<td class="lefttd" width="10%"></td>
	            <td class="td_body" width="15%"></td>
	        </tr>
	        <tr>
	            <td class="lefttd" width="10%">客户：</td>
	            <td class="td_body" width="15%">${saleLcDto.clientName }</td>
           	    <td class="lefttd" width="10%">即期/远期：</td>
	            <td class="td_body" width="15%">${saleLcDto.spotForward }</td>
           	    <td class="lefttd" width="10%">交单日期：</td>
	            <td class="td_body" width="15%">${saleLcDto.dateDelivery }</td> 
	         	<td class="lefttd" width="10%">到账日期：</td>
	            <td class="td_body" width="15%">${saleLcDto.dateGet }</td> 
	        </tr>
	        <tr>
	         	<td class="lefttd" width="10%">订单号：</td>
	            <td class="td_body" colspan="3">${saleLcDto.orderNo }</td>
	            <td class="lefttd" width="10%">备注：</td>
	            <td class="td_body" colspan="3">${saleLcDto.description }</td>
	        </tr>
	    </table>
		<div style="text-align: center;font-weight: bold;"><a href="javascript:void(0)" id="btn-summit" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">提交</a></div>
	</form>
	</div>
</div>
<style>
	body {
		padding-top: 12px;
	}
</style>
<script src="${ctx}/lib/util/util_common.js" type="text/javascript" charset="utf-8"></script>
<script>
</script>