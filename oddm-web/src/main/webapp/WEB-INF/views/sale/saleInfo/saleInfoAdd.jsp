<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
</style>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript">
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
	}
	$('#currency').combobox({
		textField: 'value',
		panelHeight : 'auto',
		editable: false,
		data: dataCurr,
	});
	
	$('#quantitySale').numberbox({
		required: true,
		min:0,precision:0,max:2147483647,
		validType:'quantitySaleValidate',
		onChange:function(newValue,oldValue){// 计算总额  
			var qlity = newValue;
			var uniP = $("#unitPrice").val();
			//qlity = parseFloat(qlity.replace(/,/g,""));
			uniP = parseFloat(uniP.replace(/,/g,""));
			$('#total').text(toMoney((qlity*uniP).toFixed(2), 2));
		}
	}); 
	$('#unitPrice').numberbox({    
		required: true,
		min:0,precision:2,max:99999999.99,
		groupSeparator:",",
		onChange:function(newValue,oldValue){// 计算总额  
			var qlity = $("#quantitySale").val();
			var uniP = newValue;
			//qlity = parseFloat(qlity.replace(/,/g,""));
			uniP = parseFloat(uniP.replace(/,/g,""));
			$('#total').text(toMoney((qlity*uniP).toFixed(2), 2));
		}
	}); 
	$('#spare').numberbox({   
		required: true,
		min:0,precision:0,max:2147483647,
		validType:'spareValidatex',
	});
	
	$.extend($.fn.validatebox.defaults.rules, {
		quantitySaleValidate: {
	        validator: function (value, param) {
	        if(value != null && (parseInt(value)+parseInt($('#spare').numberbox('getValue'))) != parseInt($('#quantity').text()) ){
                $.fn.validatebox.defaults.rules.quantitySaleValidate.message ="销售数量加备品数量须等于订单数！";
	            return false;
	        }
            return true;
	        },
	        message: ''
	    },
	    spareValidatex: {
	        validator: function (value, param) {
	        	if(value != null && (parseInt(value)+parseInt($('#quantitySale').numberbox('getValue'))) != parseInt($('#quantity').text()) ){
	                 $.fn.validatebox.defaults.rules.spareValidatex.message ="销售数量加备品数量须等于订单数！";
	 	            return false;
	 	        }
	            return true;
		        },
		        message: ''
		    },
	});
});


</script>
<div class="styleeasyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 0px;width: 100%;" >
		<form id="saleInfoAddForm" method="post">
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
	            <td class="" width="15%"> <input id="quantitySale" value="0"/></td>
	            <td class="lefttd" width="10%">单价：
	            <td class="" width="15%"><input id="unitPrice" value="0"/></td>
	        	<td class="lefttd" width="10%">总金额：</td>
	            <td class="righttd" id="total" width="15%">0.00</td>
	            <td class="lefttd" width="10%">币种：</td>
	            <td class="" width="15%"><input id="currency" name="currency" style="width: 52%;height: 100%;color:red" type="text" placeholder="输入币种" class="easyui-validatebox" data-options="required:true,validType:['length[0,20]']" value="USD"/></td>
	        </tr>
	         <tr>
	            <td class="lefttd" width="10%">备品数量：</td>
	            <td class="righttd" width="15%"><input id="spare" value="0"/></td>
	        </tr>
	        <tr>
	            <td class="lefttd">型号规格：</td>
	            <td class="righttd" colspan="3"><textarea id="specification"  class="textareacss" rows="3" maxlength="200"></textarea></td>
	            <td class="lefttd" >备注：</td>
	            <td class="righttd" colspan="3"><textarea id="description"  class="textareacss" rows="3" maxlength="200"></textarea></td>
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
$('#btn-summit').click(function(){
	summit();
});
function summit() {
	var isValid = $('#saleInfoAddForm').form('validate'); // 验证不通过则不执行
	if (!isValid) {
		return; // 验证不通过则不执行
	}
	progressLoad(); // 继续执行则开启进度条
 	var saleInfoListCmd = [];
 	var saleInfoCmd = {
			orderId: "${saleInfoDto.orderId }",
			quantitySale: $('#quantitySale').val().trim(),
			unitPrice: $('#unitPrice').val().trim(),
			spare: $('#spare').val().trim(),
			description: $('#description').val()==null?null:$('#description').val().trim(),
			specification: $('#specification').val()==null?null:$('#specification').val().trim(),
			currency: $('#currency').combobox('getValue')==null?null:$('#currency').combobox('getValue').trim(),
	};
	$.ajax({
		url : '${pageContext.request.contextPath}/saleInfo/add',
		type : "POST",
		data : JSON.stringify(saleInfoCmd),
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		async: false,
		success : function(result) {
			progressClose();
			if (result.success) {
				parent.$.messager.alert('提示', '添加成功');
       			var parent_tabs = parent.$('#index_tabs');
       		 	var index = parent_tabs.tabs('getTabIndex', parent.$('#index_tabs').tabs('getSelected'));
				 var tab = parent_tabs.tabs('getTab', index);
				 if (tab.panel('options').closable) {
					parent_tabs.tabs('close', index);
				 }
				parent_tabs.tabs('select', "个人销售")
				parent_tabs.tabs('select', "销售信息");
				parent_tabs.tabs('update', {
					tab : parent_tabs.tabs('getSelected'), //获取当前被选中的页面
					options : {
						//Title : '新标题',
						}
					}
				);
			} else {
				parent.$.messager.alert('错误', result.msg, 'error');
			}
		}
	}); 
 }
</script>