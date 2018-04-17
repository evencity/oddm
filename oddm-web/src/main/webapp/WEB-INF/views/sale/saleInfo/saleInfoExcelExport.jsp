<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div >
	<table class="grid">
		<tr>
			<td>
				客户名称：<input id="clientName" type="text" placeholder="请输入" class="easyui-validatebox" >
			</td>
		</tr>
		<tr>
			<td>
				业务名称：<input id="seller" type="text" placeholder="请输入">
			</td>
		</tr>
		<tr>
			<td>
				产品名称：<input id="productType" type="text" placeholder="请输入" >
			</td>
		</tr>
		<tr>
			<td>
				下单日期：<input id="dateOrderStart" placeholder="请输入" class="easyui-datebox" editable="false" size="18">
				&nbsp;到&nbsp;<input id="dateOrderEnd" type="text" placeholder="请输入" class="easyui-datebox" editable="false" size="18">
			</td>
		</tr>
	</table>
	<!-- <br/>
	&nbsp;&nbsp;说明：如果下单开始时间留空，则默认导出当前年分全部销售数据。 -->
</div>
