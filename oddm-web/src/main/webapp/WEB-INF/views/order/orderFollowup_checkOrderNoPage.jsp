<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>
</style>
<div style="margin: 25px">
	<table >
		<tr>
			<td>订单编号：</td>
			<td>
				<input id="orderNoForOrderfollow" type="text" placeholder="请输入订单编号" class="easyui-validatebox" data-options="required:true,validType:'isBlank'">
			</td>
		</tr>
	</table>
</div>
