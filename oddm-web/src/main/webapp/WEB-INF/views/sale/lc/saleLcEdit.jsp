<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>
	.ordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:left;}
	.tabletitle{padding-left:4px;padding-top:8px;height:26px;text-align:left; background-color:#c8d7e1;color:#333;font-weight: bold;font-family: "宋体";}
	input {border:1px solid #CCC;width:160px;height: 30px;padding:5px 0 2px 4px;font-size:12px;border-radius: 4px;}
	.lefttd{align: left; text-align: right}
	.textareacss{width:100%;border-radius: 5px;border: 0px;height: 48px}
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
	$('#currency,#creditAmount,#invoiceAmount,#dateDelivery,#dateGet').css({
		"height":"30px",
	})
	$('#currency').combobox({
		textField: 'value',
		panelHeight : 'auto',
		editable: false,
		data: dataCurr,
	});
	
	$('#creditAmount').numberbox({    
		required: true,
		min:0,precision:2,max:99999999.99,
		groupSeparator:",",
	});
	
	$('#invoiceAmount').numberbox({    
		required: true,
		min:0,precision:2,max:99999999.99,
		groupSeparator:",",
	});
	
	//自定义验证规则 
	$.extend($.fn.validatebox.defaults.rules, {
		orderNoValidate: {
	        validator: function (value, param) {
	        if(value != null && value != ""){
	        	var strs= new Array();
	        	strs = value.split("/");
	        	var flag = true;
	        	for (var key in strs) {
	        		//console.log("len "+strs[key].length);
	        		if (strs[key].length != 10) {
	        			flag = false;
	        		}
	        	}
	        	if (!flag) {
	                $.fn.validatebox.defaults.rules.orderNoValidate.message ="为规范输入，多个订单号，请以 / 分开，且单个订单号为10位！";
		            return false;
	        	}
	        }
            return true;
	        },
	        message: ''
	    }
	});
	 $('#clientName').combobox({
		 textField: 'value',
		 selectOnNavigation: false,//解决只能键盘导航键选择第一行的问题
		 onChange: function(newValue, oldValue){
			var url = "${ctx}/order/listTopClientNameJson?clientName="+newValue;
			$('#clientName').combobox('reload',url); 
		 },
	 });
	 $('#clientName').next().css({//.quality的input要用inherit
			"height":"30px",
	 });
	 $('#clientName').next().find('a').css({//.quality的input要用inherit
			"height":"30px",
	 });
});

//禁止textarea自动换行
function checkEnter(e) {
	var et=e||window.event;
	var keycode=et.charCode||et.keyCode;
	if(keycode==13) {
	if(window.event)
		window.event.returnValue = false;
		else
		e.preventDefault();//for firefox
	}
}


</script>
<div class="styleeasyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow: auto;padding: 0px;width: 100%;" >
		<form id="saleLcEditForm" method="post">
		<!-- 基础信息 -->
	    <table border="0" cellpadding="0" cellspacing="14px" class="ordertable">
    	  <caption  class="tabletitle">LC收款台账明细：</caption >
     	  <tr>
   	         	<td class="lefttd" width="10%">信用证号：</td>
	            <td class=""> <input id="creditNo" value="${saleLcDto.creditNo }" class="easyui-validatebox" data-options="required:false,validType:['piNoValidate']"/></td>
	            <td class="lefttd" width="15%">信用证总金额：</td>
	            <td class=""> <input id="creditAmount" value="${saleLcDto.creditAmount }"/></td>
	            <td class="lefttd" width="10%"></td>
	            <td class=""></td>
	            <td class="lefttd" width="10%"></td>
	            <td class=""></td>
	        </tr>
	        <tr>
	       		<td class="lefttd" width="10%">发票号：</td>
	            <td class=""> <input id="invoiceNo" value="${saleLcDto.invoiceNo }" class="easyui-validatebox" data-options="required:true,validType:['piNoValidate']"/></td>
	            <td class="lefttd" width="15%">发票金额：</td>
	            <td class="" > <input id="invoiceAmount" value="${saleLcDto.invoiceAmount }"/></td>
	        	<td class="lefttd" width="10%">币种：</td>
	            <td class=""><input id="currency" value="${saleLcDto.currency }" name="currency" style="width: 52%;color:red" type="text" placeholder="输入币种" class="easyui-validatebox" data-options="required:true,validType:['length[0,20]']"/></td>
	        	<td class="lefttd" width="10%"></td>
	            <td class=""></td>
	        </tr>
	        <tr>
	            <td class="lefttd" width="10%">客户：</td>
	            <td class=""> <input id="clientName" value="${saleLcDto.clientName }" class="easyui-validatebox" data-options="required:false,validType:['length[0,64]']"/></td>
           	    <td class="lefttd" width="15%">即期/远期：</td>
	            <td class=""><input id="spotForward" value="${saleLcDto.spotForward }" class="easyui-validatebox" data-options="required:false,validType:['length[0,64]']"/></td>
           	    <td class="lefttd" width="10%">交单日期：</td>
	            <td class=""><input id="dateDelivery" value="${saleLcDto.dateDelivery }" class="easyui-datebox"  data-options="editable:false,required:true" style="height: 30px"/></td> 
	         	<td class="lefttd" width="10%">到账日期：</td>
	            <td class=""><input id="dateGet" value="${saleLcDto.dateGet }" class="easyui-datebox"  data-options="editable:false,required:false" style="height: 30px"/></td> 
	        </tr>
	        <tr>
	         	<td class="lefttd" width="15%">订单号：</td>
	            <td class="" colspan="3"><textarea id="orderNo" class="textareacss easyui-validatebox" data-options="required:false,validType:['orderNoValidate','length[0,200]']" rows="3" onkeydown="checkEnter(event)">${saleLcDto.orderNo }</textarea></td>
	            <td class="lefttd" width="15%">备注：</td>
	            <td class="" colspan="3"><textarea id="description" class="textareacss" rows="3" maxlength="200">${saleLcDto.description }</textarea></td>
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
	var isValid = $('#saleLcEditForm').form('validate'); // 验证不通过则不执行
	if (!isValid) {
		return; // 验证不通过则不执行
	}
	progressLoad(); // 继续执行则开启进度条
 	var saleLcCmd = {
			id: "${saleLcDto.id }",
 			creditNo: $('#creditNo').val().trim(),
 			creditAmount: $('#creditAmount').val().trim(),
 			
 			invoiceNo: $('#invoiceNo').val().trim(),
 			invoiceAmount: $('#invoiceAmount').val().trim(),
 			currency: $('#currency').combobox('getValue').trim(),
 			
 			clientName: $('#clientName').val().trim(),
 			spotForward: $('#spotForward').val().trim(),
 			dateDelivery: $('#dateDelivery').datebox('getValue'),
 			dateGet: $('#dateGet').datebox('getValue'), 			

 			orderNo: $('#orderNo').val().trim(),
 			description: $('#description').val().trim(),
	};
	$.ajax({
		url : '${ctx}/saleLc/edit',
		type : "POST",
		data : JSON.stringify(saleLcCmd),
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		async: false,
		success : function(result) {
			progressClose();
			if (result.success) {
				parent.$.messager.alert('提示', '编辑成功');
       			var parent_tabs = parent.$('#index_tabs');
       		 	var index = parent_tabs.tabs('getTabIndex', parent.$('#index_tabs').tabs('getSelected'));
				 var tab = parent_tabs.tabs('getTab', index);
				 if (tab.panel('options').closable) {
					parent_tabs.tabs('close', index);
				 }
				parent_tabs.tabs('select', "LC收款台账");
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