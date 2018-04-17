<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>
</style>
<div >
	<table class="grid">
		<tr>
			<td>
				<input type="radio" value="1" name="materialFollowExcel" checked="checked">全部
				<input type="radio" value="2" name="materialFollowExcel">已集齐
				<input type="radio" value="3" name="materialFollowExcel">未集齐
			</td>
		</tr>
		<tr>
			<td>
				客户名称：<input id="clientName" type="text" placeholder="请输入客户名称 " class="easyui-validatebox" >
			</td>
		</tr>
		<tr>
			<td>
				业务名称：<input id="seller" type="text" placeholder="请输入业务名称">
			</td>
		</tr>
		<tr>
			<td>
				跟单名称：<input id="merchandiser" type="text" placeholder="请输入跟单名称" >
			</td>
		</tr>
		<tr>
			<td>
				下单日期：<input id="dateOrderStart" placeholder="请输入跟单名称" class="easyui-datebox" editable="false" size="18">
				&nbsp;到&nbsp;<input id="dateOrderEnd" type="text" placeholder="请输入跟单名称" class="easyui-datebox" editable="false" size="18">
			</td>
		</tr>
	</table>
</div>
