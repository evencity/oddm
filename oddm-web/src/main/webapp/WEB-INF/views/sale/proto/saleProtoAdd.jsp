<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>
	.ordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:left;}
	.tabletitle{padding-left:4px;padding-top:8px;height:26px;text-align:left; background-color:#c8d7e1;color:#333;font-weight: bold;font-family: "宋体";}
	input {border:1px solid #CCC;width:160px;height: 30px;font-size:12px;border-radius: 4px;}
	td {
	padding:10px 10px 0px 0px
	}
	.lefttd{align: left; text-align: right;}
	.textareacss{width:100%;border-radius: 5px;border: 0px;height: 48px}
</style>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript">
$(function() {
	$('#size, #quantity').css({
		"height":"30px",
	});
	$('#size').numberbox({
		required: true,
		min:0,precision:1,max:2147483647,
	}); 
	$('#quantity').numberbox({
		required: true,
		min:0,precision:0,max:2147483647,
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
		<form id="saleProtoAddForm" method="post">
		<!-- 基础信息 -->
	    <table border="0" cellpadding="0" cellspacing="0px" class="ordertable" style="padding-bottom:10px;">
    	  <caption  class="tabletitle">样机明细：</caption >
     	  <tr>
  	         	<td class="lefttd" width="10%">客户名称：</td>
	            <td class="td_body"> <input id="clientName" value="" class="easyui-validatebox" data-options="required:true,validType:['length[0,64]']"/></td>
           	    <td class="lefttd" width="10%">国家区域：</td>
	            <td class="td_body"> <input id="district" value="" class="easyui-validatebox" data-options="required:true,validType:['length[0,100]']"/></td>
	            <td class="lefttd" width="10%">产品名称：</td>
	            <td class="td_body"> <input id="productName" value="" class="easyui-validatebox" data-options="required:true,validType:['length[0,64]']"/></td>
	            <td class="lefttd" width="10%">尺寸：</td>
	            <td class="td_body"> <input id="size" value=""/></td>
	        </tr>
	        <tr>
	       		<td class="lefttd" width="10%">外壳型号：</td>
	            <td class="td_body"> <input id="shell" value="" class="easyui-validatebox" data-options="required:false,validType:['length[0,64]']"/></td>
	            <td class="lefttd" width="10%">外观/软件：</td>
	            <td class="td_body"> <input id="facade" value="" class="easyui-validatebox" data-options="required:true,validType:['length[0,64]']"/></td>
	        	<td class="lefttd" width="10%">数量：</td>
	            <td class="td_body"> <input id="quantity" value=""/></td>
          	    <td class="lefttd" width="10%">是否收款：</td>
	            <td class="td_body"> <input id="payed" value="" class="easyui-validatebox" data-options="required:false,validType:['length[0,64]']"/></td>
	             </tr>
	        <tr>
	            <td class="lefttd" width="10%">送样日期：</td>
	            <td class="td_body"><input id="dateSend" value="" class="easyui-datebox"  data-options="editable:false,required:true" style="height: 30px"/></td> 
	         	<td class="lefttd" width="10%">收款日期：</td>
	            <td class="td_body"><input id="datePayed" value="" class="easyui-datebox"  data-options="editable:false,required:false" style="height: 30px"/></td> 
	           	<td class="lefttd" width="10%">退还日期：</td>
	            <td class="td_body"> <input id="dateReturn" value="" class="easyui-datebox"  data-options="editable:false,required:false" style="height: 30px"/></td>
	           	<td class="lefttd" width="10%"></td>
	            <td class="td_body"></td>
	           </tr>
	          	<td class="lefttd" width="10%">PCBA型号：</td>
	            <td class="td_body" colspan="3"> <textarea id="pcba"  class="textareacss" rows="3" maxlength="100" onkeydown="checkEnter(event)"></textarea></td>
	            <td class="lefttd" width="10%">备注：</td>
	            <td class="td_body" colspan="3" ><textarea id="description" class="textareacss" rows="3" maxlength="200"></textarea></td>
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
	var isValid = $('#saleProtoAddForm').form('validate'); // 验证不通过则不执行
	if (!isValid) {
		return; // 验证不通过则不执行
	}
	progressLoad(); // 继续执行则开启进度条
 	var saleProtoCmd = {
 			clientName: $('#clientName').val().trim(),
 			district: $('#district').val().trim(),
 			productName: $('#productName').val().trim(),
 			size: $('#size').numberbox('getValue'),
 			
 			shell: $('#shell').val().trim(),
 			facade: $('#facade').val().trim(),
 			quantity: $('#quantity').numberbox('getValue'),
 			payed: $('#payed').val().trim(),

 			dateSend: $('#dateSend').datebox('getValue'),
 			datePayed: $('#datePayed').datebox('getValue'),
 			dateReturn: $('#dateReturn').datebox('getValue'),
 			
 			pcba: $('#pcba').val().trim(),
 			description: $('#description').val().trim(),
	};
	$.ajax({
		url : '${ctx}/saleProto/add',
		type : "POST",
		data : JSON.stringify(saleProtoCmd),
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
				parent_tabs.tabs('select', "样机汇总");
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