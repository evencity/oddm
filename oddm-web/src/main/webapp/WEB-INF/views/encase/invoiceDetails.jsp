<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script src="${ctx}/lib/util/util_common.js" type="text/javascript" charset="utf-8"></script>
<jsp:include page="../inc.jsp"></jsp:include>
<style>
	.body{font-family: "宋体"}
	.ordertable{background-color:#F2F2F2;width:100%;color:#666;margin-bottom:10px;font-size:0.8em;text-align:left;}
	.tabletitle{text-align:left; background-color:#DDEBF7;color:#333;font-size:1.4em; font-weight:500;}
	.tabletop{text-align:left; background-color:#D9D9D9;color:#333;font-size:1.2em; font-weight:500;}
	.ordertable tr td{padding:6px 0 6px 6px;}
	.ordertable tr th{padding:6px 0 6px 6px;}
	.tabletop th{padding:6px 0 6px 0px;text-align: center;}
	.lefttd{text-align:right;width: 70px}
    .righttd{color:#069;}
    .bordertable{background-color:#F2F2F2;width:100%;color:#666;margin-bottom:10px;font-size:0.8em;text-align:center;border-collapse:collapse;table-layout:fixed;}
	.bordertable tr td{ border:1px solid #ccc; padding:6px 0 6px 6px;height:20px;word-wrap: break-word}
	.bordertable tr th{padding:6px 0 6px 0px;border:1px solid #ccc;}
	.text_left{text-align: left;}
	.text_center{text-align: center;}
	.btn-default {border: 1px solid #DDDDDD;background: white;padding: 10px 18px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #333;}
	.btn-default:hover {border: 1px solid #DDDDDD;background: #04B5F9;padding: 10px 18px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #FFF;}
	pre {white-space: pre-wrap;word-wrap: break-word;text-align: left;margin: 0}
</style>

<script type="text/javascript">
	var invoiceId = "${invoiceId}";
	var toCharValue = "￥";
	$(function() {
		$.ajax({
			url:"${ctx}/invoice/get?id="+ invoiceId,
			type:"GET",
			dataType: "json",
            contentType: "application/json; charset=utf-8", 
            success: function(data){
            	if(data != null){
            		console.log(data)
            		$('#name').html(data.name)
            		$('#companyName').html(data.companyName)
            		$('#companyAddr').html(data.companyAddr)
            		$('#to_').html(data.to_)
            		$('#address').html(data.address)
            		$('#tel').html(data.tel)
            		$('#fax').html(data.fax)
            		$('#piNo').html(data.piNo)
            		$('#currency').html(data.currency)
            		if(data.currency != null){
            			$('#UNITPRICE_title').text('('+ data.currency +')')
            			$('#AMOUNT_title').text('('+ data.currency +')')
            		}
            		toCharValue = toCharFun(data.currency);
            		$('#brand').html(data.brand)
            		$('#shippingMethod').html(data.shippingMethod)
            		$('#incoterms').html(data.incoterms)
            		$('#origion').html(data.origion)
            		$('#payment').html(data.payment)
            		$('#dateInvoice').html(data.dateInvoiceString)
            		var qtys = 0;
            		var amounts = 0;
            		if(data.invoiceListDTOs != null && data.invoiceListDTOs.length > 0){
            			var  invoiceLists = data.invoiceListDTOs;
            			for(var i in invoiceLists){
            				var description = invoiceLists[i].description == null ? '' : invoiceLists[i].description;
            				var qty = invoiceLists[i].qty == null ? 0 : invoiceLists[i].qty;
            				var unitPrice = invoiceLists[i].unitPrice == null ? '' : toMoney((invoiceLists[i].unitPrice ).toFixed(2), 2);
            				var orderNo = invoiceLists[i].orderNo == null ? '' : invoiceLists[i].orderNo;
            				var model = invoiceLists[i].model == null ? '' : invoiceLists[i].model;
            				var total = 0;
            				if(invoiceLists[i].unitPrice != null && invoiceLists[i].qty != null){
            					amount = invoiceLists[i].unitPrice * invoiceLists[i].qty;
            					total = toMoney((invoiceLists[i].unitPrice * invoiceLists[i].qty).toFixed(2), 2);
            				}
            				var content = '<tr>'+
											'<td><pre>'+description+'</pre></td>'+
											'<td>'+qty+'</td>'+
											'<td><span class="">'+toCharValue+'</span>'+unitPrice+'</td>'+
											'<td><span class="">'+toCharValue+'</span>'+total+'</td>'+
											'<td>'+orderNo+'</td>'+
											'<td>'+model+'</td>'+
										'</tr>';
							$('#invoiceListTable').append(content);
							qtys += qty;
							amounts += amount;
            			}
            			
            		}
            		
            		var content = '<tr style="height: 40px">'+
									'<th align="right">Amount：</th>'+
									'<th>'+qtys+'</th>'+
									'<th></th>'+
									'<th><span class="">'+toCharValue+'</span>'+toMoney(amounts.toFixed(2), 2)+'</th>'+
									'<th></th>'+
									'<th></th>'+
								'</tr>';
					$('#invoiceListTable').append(content);
            	}
            }
		 })
	
	})
	function toCharFun(type){
		var temp = {
				"USD":"$",
				"RMB":"￥",
				"EUR":"€",
				"GBP":"￡",
		}
		if(temp[type] == '' || typeof(temp[type]) == 'undefined' || temp[type] == null || temp[type] == 'undefined'){
			return "￥";
		}
		return temp[type];
	}
</script>
<body style="padding: 10px">
<div id='content'>
	<!-- 基础信息 -->
	<table border="0" cellpadding="0" cellspacing="0" class="ordertable">
        <tr class="tabletitle">
            <th colspan="8">
            	基础信息:&nbsp;&nbsp;
            </th>
        </tr>
        <tr>
            <td class="lefttd"  >公司名称:</td>
            <td class="righttd"  colspan="3"><div id='companyName'></div></td>
        </tr>
        <tr>
            <td class="lefttd"  >公司地址:</td>
            <td class="righttd"  colspan="3"><div id='companyAddr'></div></td>
           
        </tr>
        <tr>
            <td class="lefttd"  >to:</td>
            <td class="righttd" ><div id='to_'></div></td>
            <td class="lefttd" >DATE:</td>
            <td class="righttd" ><div id='dateInvoice'></div></td>
        </tr>
        <tr>
            <td class="lefttd" >address:</td>
            <td class="righttd" width="45%"><div id='address'></div></td>
            <td class="lefttd">P/I NO:</td>
            <td class="righttd" ><div id='piNo'></div></td>
        </tr>
       <tr>
            <td class="lefttd"  >Tel:</td>
            <td class="righttd" ><div id='tel'></div></td>
            <td class="lefttd" >Fax:</td>
            <td class="righttd" ><div id='fax'></div></td>
        </tr>
       <tr>
            <td class="lefttd" >币种:</td>
            <td class="righttd" ><div id='currency'></div></td>
        </tr>
    </table>
  <table border="0" cellpadding="0" cellspacing="0" class="bordertable" style="margin: 0px">
        <tr class="tabletitle">
            <th colspan="6">
            	&nbsp;&nbsp;Commercial Invoice
            </th>
        </tr>
  </table>
  <table border="0" cellpadding="0" cellspacing="0" class="bordertable" id="invoiceListTable" style="margin-bottom: 0">
        <tr class="tabletop">
            <th width="45%">PRODUCT DESCRIPTION</th>
            <th width="10%">QTY</th>
             <th width="10%" >UNIT PRICE<span id="UNITPRICE_title"></span></th>
            <th width="15%" >AMOUNT<span id="AMOUNT_title"></span></th>
            <th width="10%">订单号</th>
            <th width="10%">工厂机型</th>
        </tr>
    </table>
  <table border="0" cellpadding="0" cellspacing="0" class="ordertable" >
        <tr>
            <td class="lefttd"  >Shipping Mark/brand:</td>
            <td class="righttd" ><div id='brand'></div></td>
            </tr>
        <tr>
            <td class="lefttd">Shipping Method:</td>
            <td class="righttd" ><div id='shippingMethod'></div></td>
            </tr>
        <tr>
            <td class="lefttd" >Incoterms:</td>
            <td class="righttd" ><div id='incoterms'></div></td>
            </tr>
        <tr>
            <td class="lefttd" >Origion:</td>
            <td class="righttd" ><div id=origion></div></td>
        </tr>
        <tr>
            <td class="lefttd"  >Payment:</td>
            <td class="righttd" ><div id='payment'></div></td>
        </tr>
    </table>
</div>
<div align="center">
	
</div>
<%-- <div id='content'>
	<!-- 基础信息 -->
	<table border="0" cellpadding="0" cellspacing="0" class="ordertable">
        <tr class="tabletitle">
            <th colspan="8">
            	基础信息:&nbsp;&nbsp;
            	<span onclick="bomAltListFun();" id='bomAltListBtn' class="showContactCss">[变更记录]</span>&nbsp; 
            	<c:if test="${fn:contains(sessionInfo.resourceList, '/bom/excel')}">
					<span onclick="excelExportFun();" id='excelExportBtn' class="showContactCss menu_hidden">[导出EXCEL]</span>
				</c:if>
            </th>
        </tr>
        <tr>
            <td class="lefttd" width="10%" >发票单名称:</td>
            <td class="righttd" width="15%" ><div id='name'></div></td>
            <td class="lefttd" width="10%" >公司名称:</td>
            <td class="righttd" width="15%" ><div id='companyName'></div></td>
            <td class="lefttd" width="10%" >公司地址:</td>
            <td class="righttd" width="15%" colspan="3"><div id='companyAddr'></div></td>
           
        </tr>
        <tr>
            <td class="lefttd" width="10%" >to:</td>
            <td class="righttd" width="15%"><div id='to_'></div></td>
            <td class="lefttd" width="10%" >DATE:</td>
            <td class="righttd" width="15%"><div id='dateInvoice'></div></td>
            <td class="lefttd" width="10%">address:</td>
            <td class="righttd" width="15%" colspan="3"><div id='address'></div></td>
            
        </tr>
       <tr>
            <td class="lefttd" width="10%" >Tel:</td>
            <td class="righttd" width="15%"><div id='tel'></div></td>
            <td class="lefttd" width="10%">Fax:</td>
            <td class="righttd" width="15%"><div id='fax'></div></td>
            <td class="lefttd" width="10%">P/I NO:</td>
            <td class="righttd" width="15%"><div id='piNo'></div></td>
            <td class="lefttd" width="10%">币种:</td>
            <td class="righttd" width="15%"><div id='currency'></div></td>
        </tr>
        <tr>
            <td class="lefttd" width="10%" >Shipping Mark/brand:</td>
            <td class="righttd" width="15%"><div id='brand'></div></td>
            <td class="lefttd" width="10%">Shipping Method:</td>
            <td class="righttd" width="15%"><div id='shippingMethod'></div></td>
            <td class="lefttd" width="10%">Incoterms:</td>
            <td class="righttd" width="15%"><div id='incoterms'></div></td>
            <td class="lefttd" width="10%">Origion:</td>
            <td class="righttd" width="15%"><div id=origion></div></td>
        </tr>
        <tr>
            <td class="lefttd" width="10%" >Payment:</td>
            <td class="righttd" width="15%"><div id='payment'></div></td>
            <td class="lefttd" width="10%"></td>
            <td class="righttd" width="15%"></td>
            <td class="lefttd" width="10%"></td>
            <td class="righttd" width="15%"></td>
            <td class="lefttd" width="10%"></td>
            <td class="righttd" width="15%"></td>
        </tr>
    </table>
  <table border="0" cellpadding="0" cellspacing="0" class="bordertable" style="margin: 0px">
        <tr class="tabletitle">
            <th colspan="6">
            	&nbsp;&nbsp;Commercial Invoice
            </th>
        </tr>
  </table>
  <table border="0" cellpadding="0" cellspacing="0" class="bordertable" id="invoiceListTable">
        <tr class="tabletop">
            <th width="45%">PRODUCT DESCRIPTION</th>
            <th width="10%">QTY</th>
             <th width="10%" >UNIT PRICE<span id="UNITPRICE_title"></span></th>
            <th width="15%" >AMOUNT<span id="AMOUNT_title"></span></th>
            <th width="10%">订单号</th>
            <th width="10%">工厂机型</th>
        </tr>
    </table>
  
</div>
<div align="center">
	
</div> --%>
</body>
