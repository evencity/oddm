<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
$.extend($.fn.validatebox.defaults.rules, {
	mobile: {
		validator: function (value, param) {
			return /^(?:13\d|15\d|18\d)-?\d{5}(\d{3}|\*{3})$/.test(value);
			//return /^1[3-8]+\d{9}$/.test(value);
		},
		message: '手机号码不正确'
	},
	tel:{
		validator:function(value,param){
			return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
		},
		message:'座机号码不正确'
	},
	faxno: {// 验证传真
         validator: function (value) {
             //            return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.test(value);
             return /^((\d{2,3})|(\d{3}\-))?(0\d{2,3}|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
         },
         message: '传真号码不正确'
	}
})
</script>
<style>
	.inputs{border:1px solid #CCC;width:70%;padding:5px 0 2px 4px;font-size:12px;color:#069;border-radius: 4px;}
	.textLeft{text-align: left;color: #069}
	.textRight{text-align: right;}
	.grid{table-layout: fixed;}
	.grid td{padding:2px 0px 2px 8px;word-wrap: break-word;}
</style>
<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;" >
		<form id="bomContactAddForm" method="post">
			<table class="grid">
				<tr>
					<td width="25%" class="textRight">厂家：</td>
					<td class="textLeft"><input name="company" id="contactsCompany"  type="text"  class="easyui-validatebox inputs"  placeholder="请输入厂家名称" required="required"  data-options="validType:['isBlank_','length[0,64]']"></td>
				</tr>
				<tr>
					<td class="textRight">联系人：</td>
					<td class="textLeft"><input name="contacts" id="contactsName" type="text" placeholder="请输入联系人姓名" class="easyui-validatebox inputs" required="required" data-options="validType:['isBlank_','length[0,64]']" value=""></td>
				</tr>
				<tr>
					<td class="textRight">手机号：</td>
					<td class="textLeft"><input name="telphone" id="contactsTelphone" type="text" placeholder="请输入联系人手机号" class="easyui-validatebox inputs"   data-options="validType:['isBlank_','mobile','length[0,20]']"></td>
				</tr>
				
				<tr>
					<td class="textRight">座机号：</td>
					<td class="textLeft"><input name="cellphone" id="contactsCellphone"  type="text" placeholder="请输入座机号" class="easyui-validatebox inputs" data-options="validType:['isBlank_','length[0,20]','tel']"></td>
				</tr>
				<tr>
					<td class="textRight">邮箱：</td>
					<td class="textLeft"><input name="email" id="contactsEmail" type="text"  class="easyui-validatebox inputs" placeholder="请输入邮箱" data-options="validType:['isBlank_','length[0,50]','email']"></td>
				</tr>
				<tr>
					<td class="textRight">fax：</td>
					<td class="textLeft"><input name="fax" id="contactsFax"  type="text"  class="easyui-validatebox inputs" placeholder="请输入传真" data-options="validType:['isBlank_','length[0,20]','faxno']"></td>
				</tr>
			</table>
		</form>
	</div>
</div>