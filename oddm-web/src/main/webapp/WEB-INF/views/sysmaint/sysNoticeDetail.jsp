<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/ueditor.config.js"></script> 
<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/ueditor.all.min.js"></script> 
<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/lang/zh-cn/zh-cn.js"></script> 
<style type="text/css">
	#content {
		margin: 0 auto; 
		width: 98%;
	}
	
</style>
<script type="text/javascript">
	$(function() {
	});
</script>
<div style="margin-top: -20px;">
	<%-- <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 0px;" >
		<table>
			<tr>
				<td>标题</td>
				<td><input id="title" size="40" type="text" placeholder="输入标题" class="easyui-validatebox span2" data-options="required:true,validType:['isBlank','length[0,64]']" value="${sysNotice.title}"></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;发布时间</td>
				<td><input id="pubdate" size="25" placeholder="" class="easyui-datetimebox" data-options="required:true,editable:false" value="${sysNotice.pubdate}"></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;描述</td>
				<td colspan="3"><textarea id="description" rows="3" cols="50" maxlength="500">${sysNotice.description}</textarea></td>
			</tr>
			<tr>
			</tr>
		</table>
	</div> --%>
	<div style="display: none;"><textarea id="content2">${sysNotice.content}</textarea></div>
	<div id ="content"></div>
</div>
<style>
	* {
	font-size: inherit;
	}
</style>
<script type="text/javascript">
	$('#content').html($('#content2').val());
	$('#content').css({
		"width":"205mm",
		/* "border":"solid gray",
		"border-width": "thin", */
	});
</script>