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
		dataCurr.push({value: "$"});
		dataCurr.push({value: "¥"});
		dataCurr.push({value: "€"});
		dataCurr.push({value: "￡"});
	}
	$('#currency,#amount,#orderDate').css({
		"height":"30px",
	})
	$('#currency').combobox({
		textField: 'value',
		panelHeight : 'auto',
		editable: false,
		data: dataCurr,
	});
	
	$('#amount').numberbox({    
		required: true,
		min:0,precision:2,max:99999999.99,
		groupSeparator:",",
	});
	
	//自定义验证规则 名称为poNoValidate，验证po编号的输入
	$.extend($.fn.validatebox.defaults.rules, {
		piNoValidate: {
	        validator: function (value, param) {
	        if(value != null && value.length != 11){
                $.fn.validatebox.defaults.rules.piNoValidate.message ="PI编号必须为11位！";
	            return false;
	        }
            return true;
	        },
	        message: ''
	    },
		piNoValidatex: {
	        validator: function (value, param) {
	        	var isNotExist = true;
		        if(value != null && value.length == 11){
		        	$.ajax({
		        		url : '${ctx}/salePi/isExistSalePi?piNo='+value,
		        		type : "post",
		        		dataType : "json",
		        		contentType : "application/json; charset=utf-8",
		        		async: false,
		        		success : function(result) {
		        			if (result.success) {
		    	                //$.fn.validatebox.defaults.rules.piNoValidate.message ="";
		    	                isNotExist = false;
		                        return false;
		        			} else {
		        				isNotExist = true;
		        				return true;
		        			}
		        		},
		        	}); 
		        }
		        return isNotExist;
	        },
	        message: '您输入的PI编号已经重复！'
	    },
	    poNoValidate: {
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
	                $.fn.validatebox.defaults.rules.poNoValidate.message ="为规范输入，多个PO编号，请以 / 分开，且单个PO编号为10位！";
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
	$('#district').combobox({
		 textField: 'value',
		 selectOnNavigation: false,//解决只能键盘导航键选择第一行的问题
		 onChange: function(newValue, oldValue){
			var url = "${ctx}/order/listTopDistrictJson?district="+newValue;
			$('#district').combobox('reload',url); 
		 },
	 });
	 $('#clientName, #district').next().css({//.quality的input要用inherit
			"height":"30px",
	 });
	 $('#clientName, #district').next().find('a').css({//.quality的input要用inherit
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
		<form id="salePiAddForm" method="post">
		<!-- 基础信息 -->
	    <table border="0" cellpadding="0" cellspacing="14px" class="ordertable">
    		<caption  class="tabletitle">PI明细：</caption >
	        <tr>
	            <td class="lefttd" width="15%">PI编号：</td>
	            <td class=""> <input id="piNo" value="" class="easyui-validatebox" data-options="required:true,validType:['piNoValidate','piNoValidatex']"/></td>
	            <td class="lefttd" width="15%">客户名称：</td>
	           	<td class=""><input id="clientName" value="" class="" data-options="required:true,validType:['length[0,64]']"/></td>
	           	<td class="lefttd" width="10%">业务员：</td>
	            <td class=""> <input id="seller" value="" class="easyui-validatebox" data-options="required:true,validType:['length[0,64]']"/></td>
	        	<td class="lefttd" width="10%">国家：</td>
	            <td class=""> <input id="district" value="" class="" data-options="required:true,validType:['length[0,64]']"/></td>
	        </tr>
     	    <tr>
	            <td class="lefttd" width="15%">摘要：</td>
	            <td class=""><input id="summary" value="" class="easyui-validatebox" data-options="required:true,validType:['length[0,64]']"/></td>
	            <td class="lefttd" width="15%">下单日期：</td>
	            <td class=""><input id="orderDate" value="" class="easyui-datebox"  data-options="editable:false,required:true" style="height: 30px"/></td> 
	            <td class="lefttd" width="10%">总金额：</td>
	            <td class=""><input id="amount" value="0.00"/></td>
	            <td class="lefttd" width="10%">币种：</td>
	            <td class=""><input id="currency" name="currency" style="width: 52%;color:red" type="text" placeholder="输入币种" class="easyui-validatebox" data-options="required:true,validType:['length[0,20]']" value="$"/></td>
	        </tr>
	        <tr>
	            <td class="lefttd" width="15%">PO编号：</td>
	            <td class="" colspan="3"><textarea id="poNo" class="textareacss easyui-validatebox" data-options="required:false,validType:['poNoValidate','length[0,200]']" rows="3" onkeydown="checkEnter(event)"></textarea></td>
	            <td class="lefttd" width="15%">备注：</td>
	            <td class="" colspan="3"><textarea id="description" class="textareacss" rows="3" maxlength="200"></textarea></td>
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
	var isValid = $('#salePiAddForm').form('validate'); // 验证不通过则不执行
	if (!isValid) {
		return; // 验证不通过则不执行
	}
	progressLoad(); // 继续执行则开启进度条
 	var salePiCmd = {
 			piNo: $('#piNo').val().trim(),
 			seller: $('#seller').val().trim(),
 			clientName: $('#clientName').val().trim(),
 			district: $('#district').val().trim(),
 			clientName: $('#clientName').val().trim(),
 			
 			orderDate: $('#orderDate').datebox('getValue'),
 			summary: $('#summary').val().trim(),
 			amount: $('#amount').val().trim(),
			currency: $('#currency').combobox('getValue').trim(),

 			poNo: $('#poNo').val().trim(),
 			description: $('#description').val().trim(),
	};
	$.ajax({
		url : '${ctx}/salePi/add',
		type : "POST",
		data : JSON.stringify(salePiCmd),
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
				parent_tabs.tabs('select', "PI汇总");
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