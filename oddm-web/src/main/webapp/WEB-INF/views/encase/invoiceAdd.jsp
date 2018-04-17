<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>
	.body{font-family: "宋体"}
	.ordertable{background-color:#F2F2F2;width:100%;color:#666;margin-bottom:10px;font-size:0.8em;text-align:left;}
	.tabletitle{text-align:left; background-color:#DDEBF7;color:#333;font-size:1.4em; font-weight:500;}
	.tabletop{text-align:left; background-color:#D9D9D9;color:#333;font-size:1.2em; font-weight:500;}
	.ordertable tr td{padding:1px 0 1px 4px;}
	.ordertable tr th{padding:1px 0 1px 4px;}
	.tabletop th{padding:6px 0 4px 0px;text-align: center;}
	/* .lefttd{text-align:right;} */
	.lefttd{text-align:right;width: 70px}
    .inputs{border:1px solid #CCC;width:100%;padding:5px 0 2px 4px;font-size:12px;color:#069;border-radius: 4px;}
    .inputsBase{border:1px solid #CCC;padding:5px 0 2px 4px;font-size:12px;color:#069;border-radius: 4px;}
    .inputsBaseLeft{border:1px solid #CCC;width:88%;padding:5px 0 2px 4px;font-size:12px;color:#069;border-radius: 4px;}
    .righttd{color:#069;}
    .bordertable{background-color:#F2F2F2;width:100%;color:#666;margin-bottom:10px;font-size:0.8em;text-align:center;border-collapse:collapse;table-layout:fixed;}
	.bordertable tr td{ border:1px solid #ccc; padding:4px 0 4px 4px;height:20px;word-wrap: break-word}
	.bordertable tr th{padding:4px 0 4px 0px;border:1px solid #ccc;}
	.text_left{text-align: left;}
	.textareacssTitle{width:68%;border:1px solid #CCC;text-align: left;font-size:12px;color:#069;border-radius: 4px;height: 35px}
	.textareacssBase{width:88%;border:1px solid #CCC;text-align: left;font-size:12px;color:#069;border-radius: 4px;height: 35px}
	.textareacss{width:100%;border:1px solid #CCC;text-align: left;font-size:12px;color:#069;border-radius: 4px;height: 100px}
	.text_center{text-align: center;}
	.textbox .textbox-text{color: #069}
	.tip{height: 16px;width: 16px;display: inline-block;padding: 0px 4px;cursor: pointer;}
	.btn-default {border: 1px solid #DDDDDD;background: white;padding: 10px 18px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #333;}
	.btn-default:hover {border: 1px solid #DDDDDD;background: #04B5F9;padding: 10px 18px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #FFF;}
</style>
<script src="${ctx}/lib/util/util_common.js" type="text/javascript" charset="utf-8"></script>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
	var currencyChar = "$";
	$(function() {
		/*********币种下拉选择框，默认为USD*********/
		var jsonCurrency = "${sysConfig.value}";
		var dataCurr = [];
		if (jsonCurrency != null && ""!=jsonCurrency && typeof(jsonCurrency) != "undefined") {
			var vv = jsonCurrency.split('|');
			for (var key in vv){
				var arr = {value: vv[key]}; dataCurr.push(arr);
			}
		} else {
			dataCurr.push({value: "USD"});
			dataCurr.push({value: "RMB"});
			dataCurr.push({value: "EUR"});
			dataCurr.push({value: "GBP"});
		}
		$('#currency').combobox({
			textField: 'value',
			panelHeight : 'auto',
			editable: false,
			data: dataCurr,
			onSelect:function(obj){
				currencyChar = toCharFun(obj.value);
				$('#UNITPRICE_title').text("UNITPRICE("+obj.value+")")
				$('#AMOUNT_title').text("AMOUNT("+obj.value+")")
				
				$('#invoiceListTable').find('.unitPrice').numberbox({
					prefix : currencyChar,
				})
				$('#invoiceListTable').find('.currencyChar').each(function(){
					$(this).text(currencyChar)
				})
				$('#AMOUNTS').prev().text(currencyChar);
			}
		});
		
	});
	/* 添加列表 */
	function addInvoiceListFun(obj){
		var $content = $('<tr class="invoiceList">'+
				'<td style="padding:1px"><textarea class="textareacss description"></textarea></td>'+
				'<td style="padding:1px"><input class="qty" style="width:98%"></td>'+
				'<td style="padding:1px"><input class="unitPrice " style="width:98%"></td>'+
				'<td style="padding:1px" ><span class="currencyChar"></span><span class="total">0.00</span></td>'+
				'<td style="padding:1px"><input class="orderNo inputs"></td>'+
				'<td style="padding:1px"><input class="model inputs"></td>'+
				'<td style="padding: 0px">'+
					'<span onclick="delInvoiceListFun(this);" class="icon-del tip"  title="删除"></span>'+
				'</td>'+
			'</tr>');
		$('#invoiceListTable').append($content);
		$content.find('.description').validatebox({
			required: true,
		    validType: ['isBlank_','length[0,500]'],
		});
		$content.find('.qty').numberbox({  
			required: true,
			min:0,precision:0,max:2147483647,
		    validType: ['isBlank_'],
		    onChange:function(newValue,oldValue){// 计算总额  
				var $next = $(this).parent().next();
		    	var uniP = $next.find('.unitPrice').val() == '' ? '0' : $next.find('.unitPrice').val().trim();
		    	uniP = parseFloat(uniP.replace(/,/g,""));
		    	$next.next().find('.currencyChar').text(currencyChar)
		    	$next.next().find('.total').text(toMoney((uniP*newValue).toFixed(2), 2))
		    	//改变总数
		    	countQTYS();
		    	countAMOUNTS();
			}

		});
		$content.find('.unitPrice').numberbox({    
			required: true,
			min:0,precision:2,max:99999999.99,
			groupSeparator:",",
			prefix : currencyChar,
			onChange:function(newValue,oldValue){// 计算总额  
				var qty = typeof($(this).parent().prev().find('input').val()) == 'undefined' ? '0' : $(this).parent().prev().find('input').val().trim();
				var uniP = newValue;
				uniP = parseFloat(uniP.replace(/,/g,""));
				$(this).parent().next().find('.currencyChar').text(currencyChar);
				$(this).parent().next().find('.total').text(toMoney((qty*newValue).toFixed(2), 2));
				countQTYS();
		    	countAMOUNTS();
			}
		}); 
		$content.find('.orderNo').validatebox({  
		    validType: ['isBlank_','length[0,20]','orderNoLengthValidate(10)','orderNoExcitValidate(10)'],
		});
		$content.find('.model').validatebox({  
		    validType: ['isBlank_','length[0,64]'],
		});
	}
	
	function delInvoiceListFun(obj){
		$(obj).parent().parent().remove();
		countQTYS();
    	countAMOUNTS();
	}
	
	function addInvoiceFun(){
		var isValid = $('#addInvoiceForm').form('validate');
		console.log("addInvoiceForm 验证"+isValid)
		if(!isValid){
			return;
		}
		var name = $('#name').val() == null ? null :  $('#name').val().trim();
		var companyName = $('#companyName').val() == null ? null :  $('#companyName').val().trim();
		var companyAddr = $('#companyAddr').val() == null ? null :  $('#companyAddr').val().trim();
		var to_ = $('#to_').val() == null ? null :  $('#to_').val().trim();
		var dateInvoice = $('#dateInvoice').datebox('getValue') == null ? null :  $('#dateInvoice').datebox('getValue').trim();
		var address = $('#address').val() == null ? null :  $('#address').val().trim();
		var tel = $('#tel').val() == null ? null :  $('#tel').val().trim();
		var fax = $('#fax').val() == null ? null :  $('#fax').val().trim();
		var piNo = $('#piNo').val() == null ? null :  $('#piNo').val().trim();
		var currency = $('#currency').combobox('getValue')==null?null:$('#currency').combobox('getValue').trim();
		var brand = $('#brand').val() == null ? null :  $('#brand').val().trim();
		var shippingMethod = $('#shippingMethod').val() == null ? shippingMethod :  $('#shippingMethod').val().trim();
		var incoterms = $('#incoterms').val() == null ? null :  $('#incoterms').val().trim();
		var origion = $('#origion').val() == null ? shippingMethod :  $('#origion').val().trim();
		var payment = $('#payment').val() == null ? null :  $('#payment').val().trim();
		
		var invoiceLists = [];
		$('#invoiceListTable').find('.invoiceList').each(function(){
			var $trValue = $(this);
			var invoiceList = {
					description : typeof($trValue.find('.description').val()) == 'undefiend' ? null : $trValue.find('.description').val().trim(),
					model : typeof($trValue.find('.model').val()) == 'undefiend' ? null : $trValue.find('.model').val().trim(),
					orderNo : typeof($trValue.find('.orderNo').val()) == 'undefiend' ? null : $trValue.find('.orderNo').val().trim(),
					qty : typeof($trValue.find('.qty').val()) == 'undefiend' ? null : $trValue.find('.qty').val().trim(),
					unitPrice : typeof($trValue.find('.unitPrice').val()) == 'undefiend' ? null : $trValue.find('.unitPrice').val().trim(),
			}
			invoiceLists.push(invoiceList)
		})
		
		var param = {
			name : name,
			companyName : companyName,
			companyAddr : companyAddr,
			to_ : to_,
			dateInvoice : dateInvoice,
			address : address,
			tel : tel,
			fax : fax,
			piNo : piNo,
			currency : currency,
			brand : brand,
			shippingMethod : shippingMethod,
			incoterms : incoterms,
			origion : origion,
			payment : payment,
			invoiceListDTOs : invoiceLists
		}
		console.log(param)
		progressLoad();
		 $.ajax({
       		type: 'POST',
            url: "${ctx}/invoice/add" ,
            data: JSON.stringify(param) ,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function(data){
	          	 if(data.success){
	          		 parent.$.messager.alert('提示', data.msg);
	          		 var parent_tabs = parent.$('#index_tabs');
          		 	 var index = parent_tabs.tabs('getTabIndex', parent.$('#index_tabs').tabs('getSelected'));
					 var tab = parent_tabs.tabs('getTab', index);
					 if (tab.panel('options').closable) {
						parent_tabs.tabs('close', index);
					 }
					 parent_tabs.tabs('select', "发票信息");
					 parent_tabs.tabs('update', {
						tab : parent_tabs.tabs('getSelected'), //获取当前被选中的页面
						options : {
							//Title : '新标题',
							}
						}
					 );
	          	 }else{
	          		 parent.$.messager.alert('提示', data.msg);
	          	 }
           }
      }) 
      progressClose();
	}
	$.extend($.fn.validatebox.defaults.rules, {
		tel:{
			validator:function(value,param){
				var mobile = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
				var tel = /^(?:13\d|15\d|18\d)-?\d{5}(\d{3}|\*{3})$/.test(value);
				if(mobile || tel){
					return true;
				}
				return false;
			},
			message:'您输入的联系号码不正确'
		},
		faxno: {// 验证传真
	         validator: function (value) {
	             //            return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.test(value);
	             return /^((\d{2,3})|(\d{3}\-))?(0\d{2,3}|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
	         },
	         message: '您输入的传真号码不正确'
		},
		orderNoLengthValidate : {
		        validator : function(value, param) {
		        	if(value.length != param)
		        		return false;
		            return true;
		        },
		        message : '订单号的长度只能为10个字符！！'
		},
		orderNoExcitValidate : {
	        validator : function(value, param) {
	        	var me = $(this)
	        	var flag = false;
	        	if(value.length != param)
	        		flag = false;
	        	else{
	        		 $.ajax({
        	       		type: 'POST',
        	            url: "${ctx}/invoice/getByOrderDTO?orderNo="+value ,
        	            dataType: "json",
    	                async : false,
        	            success: function(data){
        	            	if(data.id != null){
        	            		var productFactory = data.productFactory == null ? "" : data.productFactory.trim();
        	            		me.parent().next().find('.model').val(productFactory)
        	            		flag = true;
        	            	}else{
        	            		flag = false;
        	            	}
        	           	}
	        	     }) 
	        	}
	        	console.log(flag)
	            return flag;
	        },
	        message : '订单号不存在！！'
		},
	})
	function countQTYS(){
		var total = 0;
		$('#invoiceListTable').find('.qty').each(function(){
			total += parseFloat($(this).val().replace(/,/g,""));
		})
		if(isNaN(total)){
			total = 0;
		}
		$('#QTYS').text(total);
	}
	function countAMOUNTS(){
		var total = 0;
		$('#invoiceListTable').find('.total').each(function(){
			total += parseFloat(this.innerHTML.replace(/,/g,""));
		})
		if(isNaN(total)){
			total = 0;
		}
		$('#AMOUNTS').prev().text(currencyChar);
		$('#AMOUNTS').text(toMoney(total+"", 2));
	}
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
	<form id="addInvoiceForm">
	<table border="0" cellpadding="0" cellspacing="0" class="ordertable">
        <tr class="tabletitle">
            <th colspan="8">
            	&nbsp;&nbsp;基础信息:
            </th>
        </tr>
       <!--  <tr>
            <td class="lefttd" >公司名称:</td>
            <td class="righttd" ><textarea class="textareacssBase easyui-validatebox"  id="companyName" data-options="validType:['isBlank_','length[0,100]']"/></textarea></td>
        </tr>
        <tr>
            <td class="lefttd" >公司地址:</td>
            <td class="righttd" ><textarea class="textareacssBase easyui-validatebox"  id="companyAddr" data-options="validType:['isBlank_','length[0,200]']"/></textarea></td>
        </tr> -->
        <tr>
            <td class="lefttd" >公司名称:</td>
            <td class="righttd" colspan="3"><textarea class="textareacssTitle easyui-validatebox"  id="companyName" data-options="validType:['isBlank_','length[0,100]']"/></textarea></td>
        </tr>
        <tr>
            <td class="lefttd" >公司地址:</td>
            <td class="righttd" colspan="3"><textarea class="textareacssTitle easyui-validatebox"  id="companyAddr" data-options="validType:['isBlank_','length[0,200]']"/></textarea></td>
        </tr> 
        <tr>
            <td class="lefttd" width="7%" >to:</td>
            <td class="righttd" width="43%"><input type="text" class="inputsBaseLeft easyui-validatebox"  id="to_" data-options="validType:['isBlank_','length[0,100]']"/></td>
            <td class="lefttd"  width="7%">DATE:</td>
            <td class="righttd" width="43%"><input type="text" style="height: 25px" class=" easyui-datebox" editable="false"   id="dateInvoice"/></td>
        </tr>
        <tr>
            <td class="lefttd" >address:</td>
            <td class="righttd" ><textarea  class="textareacssBase easyui-validatebox"  id="address" data-options="validType:['isBlank_','length[0,300]']"/></textarea></td>
             <td class="lefttd">P/I NO:</td>
            <td class="righttd"><input type="text" class="inputsBase easyui-validatebox"  id="piNo" data-options="validType:['isBlank_','length[0,64]']"/></td>
        </tr>
       <tr>
            <td class="lefttd" >Tel:</td>
            <td class="righttd"><input type="text" class="inputsBase easyui-validatebox"  id="tel" data-options="validType:['isBlank_','length[0,20]','tel']"/></td>
            <td class="lefttd">Fax:</td>
            <td class="righttd"><input type="text" class="inputsBase easyui-validatebox"  id="fax" data-options="validType:['isBlank_','length[0,20]','faxno']"/></td>
        </tr>
        <tr>     
           
            <td class="lefttd">币种:</td>
            <td class="righttd"><input id='currency' style="height: 25px" placeholder="输入币种" class="easyui-validatebox" data-options="required:true,validType:['length[0,64]']" value="USD"></td>
        </tr>
    </table>
  <table border="0" cellpadding="0" cellspacing="0" class="bordertable" style="margin: 0px">
        <tr class="tabletitle">
            <th colspan="5" style="border-right: 0px">
            	&nbsp;&nbsp;Commercial Invoice
            </th>
             <th align="right" style="border-left: 0px">
					<span onclick="addInvoiceListFun('this');" class="icon-add tip"  title="添加"></span>
			</th>
        </tr>
  </table>
  <table border="0" cellpadding="0" cellspacing="0" class="bordertable" id="invoiceListTable" style="margin-bottom: 0">
        <tr class="tabletop">
            <th width="45%">PRODUCT DESCRIPTION</th>
            <th width="8%">QTY</th>
            <th width="12%" id="UNITPRICE_title">UNIT PRICE(USD)</th>
            <th width="12%" id="AMOUNT_title">AMOUNT(USD)</th>
            <th width="10%">订单号</th>
            <th width="10%">工厂机型</th>
           	<th width="3%"></th>
        </tr>
    </table>
    <table border="0" cellpadding="0" cellspacing="0" class="bordertable" style="margin-bottom: 0">
        <tr style="height: 40px">
            <th width="45%" style="border-top: 0">AMOUNT</th>
            <th width="8%" style="border-top: 0" id="QTYS">0</th>
            <th width="12%" style="border-top: 0"></th>
            <th width="12%" style="border-top: 0"><span></span><span  id="AMOUNTS">0.00</span ></th>
            <th width="10%" style="border-top: 0"></th>
            <th width="10%" style="border-top: 0"></th>
           	<th width="3%" style="border-top: 0"></th>
        </tr>
    </table>
    <table border="0" cellpadding="0" cellspacing="0" class="ordertable">
        
        <tr>
            <td class="lefttd" width="7%">Shipping Mark/brand:</td>
            <td class="righttd" width="43%"><input class="inputsBaseLeft easyui-validatebox"  id="brand" data-options="validType:['isBlank_','length[0,64]']"/></td>
            <td class="lefttd" width="7%">&nbsp;</td>
            <td class="" width="43%">&nbsp;</td>
        </tr>
        <tr>
            <td class="lefttd" >Shipping Method:</td>
            <td class="righttd"><input  class="inputsBaseLeft easyui-validatebox"  id="shippingMethod" data-options="validType:['isBlank_','length[0,64]']"/></td>
        </tr>
        <tr>
            <td class="lefttd" >Incoterms:</td>
            <td class="righttd" ><input  class="inputsBaseLeft easyui-validatebox"  id="incoterms" data-options="validType:['isBlank_','length[0,64]']"/></td>
        </tr>
        <tr>
            <td class="lefttd" >Origion:</td>
            <td class="righttd"><input  class="inputsBaseLeft easyui-validatebox"  id="origion" data-options="validType:['isBlank_','length[0,64]']"/></td>
        </tr>
        <tr>
            <td class="lefttd">Payment:</td>
            <td class="righttd"><input  class="inputsBaseLeft easyui-validatebox"  id="payment" data-options="validType:['isBlank_','length[0,64]']"/></td>
    </table>
    </form>
</div>
<!-- <div id='content'>
	<form id="addInvoiceForm">
	基础信息
	<table border="0" cellpadding="0" cellspacing="0" class="ordertable">
        <tr class="tabletitle">
            <th colspan="8">
            	&nbsp;&nbsp;基础信息:
            </th>
        </tr>
        <tr>
            <td class="lefttd" width="10%" >发票单名称:</td>
            <td class="righttd" width="15%" ><input type="text" class="inputs easyui-validatebox"  id="name" data-options="validType:['isBlank_','length[0,200]']" required="required"/></td>
            <td class="lefttd" width="10%" >公司名称:</td>
            <td class="righttd" width="15%" ><input type="text" class="inputs easyui-validatebox"  id="companyName" data-options="validType:['isBlank_','length[0,100]']"/></td>
            <td class="lefttd" width="10%" >公司地址:</td>
            <td class="righttd" width="15%" colspan="3"><input type="text" class="inputs easyui-validatebox"  id="companyAddr" data-options="validType:['isBlank_','length[0,200]']"/></td>
        </tr>
        <tr>
            <td class="lefttd" width="10%" >to:</td>
            <td class="righttd" width="15%"><input type="text" class="inputs easyui-validatebox"  id="to_" data-options="validType:['isBlank_','length[0,100]']"/></td>
            <td class="lefttd" width="10%" >DATE:</td>
            <td class="righttd" width="15%"><input type="text" class=" easyui-datebox" editable="false"   id="dateInvoice"/></td>
            <td class="lefttd" width="10%">address:</td>
            <td class="righttd" width="15%" colspan="3"><input type="text" class="inputs easyui-validatebox"  id="address" data-options="validType:['isBlank_','length[0,300]']"/></td>
            
        </tr>
       <tr>
            <td class="lefttd" width="10%" >Tel:</td>
            <td class="righttd" width="15%"><input type="text" class="inputs easyui-validatebox"  id="tel" data-options="validType:['isBlank_','length[0,20]','tel']"/></td>
            <td class="lefttd" width="10%">Fax:</td>
            <td class="righttd" width="15%"><input type="text" class="inputs easyui-validatebox"  id="fax" data-options="validType:['isBlank_','length[0,20]','faxno']"/></td>
            <td class="lefttd" width="10%">P/I NO:</td>
            <td class="righttd" width="15%"><input type="text" class="inputs easyui-validatebox"  id="piNo" data-options="validType:['isBlank_','length[0,64]']"/></td>
            <td class="lefttd" width="10%">币种:</td>
            <td class="righttd" width="15%"><input id='currency'style="width: 52%;height: 100%;color:red" placeholder="输入币种" class="easyui-validatebox" data-options="required:true,validType:['length[0,64]']" value="USD"></td>
        </tr>
        <tr>
            <td class="lefttd" width="10%" >Shipping Mark/brand:</td>
            <td class="righttd" width="15%"><input type="text" class="inputs easyui-validatebox"  id="brand" data-options="validType:['isBlank_','length[0,64]']"/></td>
            <td class="lefttd" width="10%">Shipping Method:</td>
            <td class="righttd" width="15%"><input type="text" class="inputs easyui-validatebox"  id="shippingMethod" data-options="validType:['isBlank_','length[0,64]']"/></td>
            <td class="lefttd" width="10%">Incoterms:</td>
            <td class="righttd" width="15%"><input type="text" class="inputs easyui-validatebox"  id="incoterms" data-options="validType:['isBlank_','length[0,64]']"/></td>
            <td class="lefttd" width="10%">Origion:</td>
            <td class="righttd" width="15%"><input type="text" class="inputs easyui-validatebox"  id="origion" data-options="validType:['isBlank_','length[0,64]']"/></td>
        </tr>
        <tr>
            <td class="lefttd" width="10%" >Payment:</td>
            <td class="righttd" width="15%"><input type="text" class="inputs easyui-validatebox"  id="payment" data-options="validType:['isBlank_','length[0,64]']"/></td>
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
            <th colspan="5" style="border-right: 0px">
            	&nbsp;&nbsp;Commercial Invoice
            </th>
             <th align="right" style="border-left: 0px">
					<span onclick="addInvoiceListFun('this');" class="icon-add tip"  title="添加"></span>
			</th>
        </tr>
  </table>
  <table border="0" cellpadding="0" cellspacing="0" class="bordertable" id="invoiceListTable" style="margin-bottom: 0">
        <tr class="tabletop">
            <th width="45%">PRODUCT DESCRIPTION</th>
            <th width="10%">QTY</th>
            <th width="10%" id="UNITPRICE_title">UNIT PRICE(USD)</th>
            <th width="12%" id="AMOUNT_title">AMOUNT(USD)</th>
            <th width="10%">订单号</th>
            <th width="10%">工厂机型</th>
           	<th width="3%"></th>
        </tr>
    </table>
    <table border="0" cellpadding="0" cellspacing="0" class="bordertable" >
        <tr style="height: 40px">
            <th width="45%" style="border-top: 0">AMOUNT</th>
            <th width="10%" style="border-top: 0" id="QTYS"></th>
            <th width="10%" style="border-top: 0"></th>
            <th width="12%" style="border-top: 0"><span></span><span  id="AMOUNTS"></span ></th>
            <th width="10%" style="border-top: 0"></th>
            <th width="10%" style="border-top: 0"></th>
           	<th width="3%" style="border-top: 0"></th>
        </tr>
    </table>
    </form>
</div> -->

<div align="center">
	<button id="" class='btn-default' onclick="addInvoiceFun()">提交</button> 
</div>
</body>