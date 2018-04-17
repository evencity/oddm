<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div >
	<table class="grid" style="padding-left: 10%">
		<tr>
			<td>
				客户名称：<input id="clientName" type="text" placeholder="请输入" class="easyui-validatebox" >
			</td>
		</tr>
		<tr>
			<td>
				产品名称：<input id="productName" type="text" placeholder="请输入" class="easyui-validatebox" >
			</td>
		</tr>
		<tr>
			<td>
				国家地区：<input id="district" type="text" placeholder="请输入" >
			</td>
		</tr>
		<tr>
			<td>
				送样日期：<input id="dateSendStart" placeholder="请输入" class="easyui-datebox" editable="false" size="18">
				&nbsp;到&nbsp;<input id="dateSendEnd" type="text" placeholder="请输入" class="easyui-datebox" editable="false" size="18">
			</td>
		</tr>
	</table>
	<!-- <br/>
	&nbsp;&nbsp;说明：如果下单开始时间留空，则默认导出当前年分全部销售数据。 -->
</div>
<style> 
</style>
