<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
</script>
<style>
	.textLeft{text-align: left;color: #069}
	.textRight{text-align: right;}
	.grid{table-layout: fixed;}
	.grid td{padding:2px 0px 2px 8px;word-wrap: break-word;}
</style>
<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;" >
		<table class="grid">
			<tr>
				<td width="30%" class="textRight">厂家：</td>
				<td class="textLeft" >${bomMaterialContact.company}</td>
				
			 </tr>
			<tr> 
				<td class="textRight">联系人：</td>
				<td class="textLeft">${bomMaterialContact.contacts}</td>
			</tr>
			<tr>
				<td class="textRight">手机号：</td>
				<td class="textLeft">${bomMaterialContact.telphone}</td>
			</tr>
			
			<tr>
				<td class="textRight"> 座机号：</td>
				<td class="textLeft">${bomMaterialContact.cellphone}</td>
			</tr>
			<tr>
				<td class="textRight">邮箱：</td>
				<td class="textLeft">${bomMaterialContact.email}</td>
			</tr>
			<tr> 
				<td class="textRight">fax：</td>
				<td class="textLeft">${bomMaterialContact.fax}</td>
			</tr>
		</table>
	</div>
</div>