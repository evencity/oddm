<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>
	.ordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:left;}
	.tabletitle{text-align:left; background-color:#c8d7e1;color:#333;font-size:1.4em; font-weight:500;}
	.tabletop{text-align:left; background-color:#DCE0E2;color:#333;font-size:1.2em; font-weight:500;}
	.ordertable tr td{padding:10px 0 10px 6px;}
	.inputs{border:1px solid #CCC;width:90%;padding:5px 0 2px 4px;font-size:12px;color:#999;border-radius: 4px;}
	.lefttd{width:85px;}
    .lefttd90{width:90px; text-align:right;}
    .righttd{color:#069;}
    .bordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:center;border-collapse:collapse;}
	.bordertable td{border:1px solid #ccc;padding:10px 0 10px 6px;height:20px;}
	.textareacss{width:96%;border-radius: 5px;border: 0px;height: 48px}
	.choose{color: #06c;cursor: pointer;}
		.btn-default {border: 1px solid #DDDDDD;background: white;padding: 10px 18px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #333;}
	.btn-default:hover {border: 1px solid #DDDDDD;background: #04B5F9;padding: 10px 18px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #FFF;}
	.color_f60 {color: #f60;}
	pre {
		white-space: pre-wrap;
		word-wrap: break-word;
	}
</style>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript">
$(function() {
	init();
});

//初始化函数调用
function init() {
	var qlity = $("#quantitySale").text();
	var uniP = $("#unitPrice").text();
	//qlity = parseFloat(qlity.replace(/,/g,""));
	uniP = parseFloat(uniP.replace(/,/g,""));
	$('#total').text(toMoney((qlity*uniP).toFixed(2), 2));
}

</script>
<div class="styleeasyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 0px;width: 100%" >
		<form id="saleInfoEditForm" method="post">
		<!-- 基础信息 -->
		<table border="0" cellpadding="0" cellspacing="0" class="ordertable">
	        <tr class="tabletitle">
	            <td colspan="8">基础信息： </td>
	        </tr>
	        <tr>
	         	<td class="lefttd" width="10%">订单编号：</td>
	            <td class="righttd" width="15%"><div id="orderNo">${saleInfoDto.orderNo}</div> </td>
	            <td class="lefttd" width="10%">业务名称： </td>
	            <td class="righttd" width="15%"><div id="seller">${saleInfoDto.seller}</div></td>       
	            <td class="lefttd" width="10%">跟单名称:</td>
	            <td class="righttd" width="15%"><div id="merchandiser">${saleInfoDto.merchandiser}</div></td>
	            <td class="lefttd" width="10%">订单数量：</td>
	            <td class="righttd" width="15%"><div id="quantity" style="color: red;">${saleInfoDto.quantity}</div> </td>
	        </tr>
	        <tr>
	            <td class="lefttd" width="10%">客户名称：</td>
	            <td class="righttd" width="15%"><div id="clientName">${saleInfoDto.clientName}</div></td> 
	         	<td class="lefttd" width="10%">所在国家： </td>
	            <td class="righttd" width="15%"><div id="district">${saleInfoDto.district}</div></td>       
	            <td class="lefttd" width="10%">产品机型：</td>
	            <td class="righttd" width="15%"><div id="productFactory">${saleInfoDto.productFactory}</div></td> 
	            <td class="lefttd" width="10%">产品名称：</td>
	            <td class="righttd" width="15%"><div id="productType">${saleInfoDto.productType}</div></td> 
	        </tr>
	        <tr>
	            <td class="lefttd" width="10%">下单日期：</td>
	            <td class="righttd" width="15%"><div id="dateOrder">${saleInfoDto.dateOrder}</div></td> 
	        </tr>
	    </table>
	    
	    <table border="0" cellpadding="0" cellspacing="0" class="ordertable">
	        <tr class="tabletitle">
	            <td colspan="8">销售情况：</td>
	        </tr>
	         <tr>
	            <td class="lefttd" width="10%">销售数量：</td>
	            <td class="righttd" width="15%" id="quantitySale">${saleInfoDto.quantitySale}</td>
	            <td class="lefttd" width="10%">单价：
	            <td class="righttd" width="15%" id="unitPrice"><fmt:formatNumber type="number" value="${saleInfoDto.unitPrice}" pattern=",000.00" maxFractionDigits="2"/></td>
	        	<td class="lefttd" width="10%">总金额：</td>
	            <td class="righttd" id="total" width="15%">0.00</td>
	            <td class="lefttd" width="10%">币种：</td>
	            <td class="righttd" width="15%">${saleInfoDto.currency}</td>
	        </tr>
	         <tr>
	            <td class="lefttd" width="10%">备品数量：</td>
	            <td class="righttd" width="15%">${saleInfoDto.spare}</td>
	        </tr>
	        <tr>
	            <td class="lefttd">型号规格：</td>
	            <td class="righttd" colspan="3" ><pre>${saleInfoDto.specification}</pre></td>
	            <td class="lefttd" >备注：</td>
	            <td class="righttd" colspan="3" ><pre>${saleInfoDto.description}</pre></td>
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