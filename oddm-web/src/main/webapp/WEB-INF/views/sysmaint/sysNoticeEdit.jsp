<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/ueditor.config.js"></script> 
<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/ueditor.all.min.js"></script> 
<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/lang/zh-cn/zh-cn.js"></script> 
<style type="text/css">
	#sysNoticeEdit {
		z-index: 1;
	}
</style>
<script type="text/javascript">
	$(function() {
		var editor = new UE.ui.Editor({initialFrameHeight: 400}); //initialFrameWidth:'100%',
		editor.render("content"); 
		
		var id = "${sysNotice.id}";   //如果编辑成功，服务器会返回该记录的id
		var state = "${sysNotice.state}";//1 暂存、2 发布
		$('#btn-summit').bind('click', function(){
			state = 2;
	    });
		$('#btn-temporary').bind('click', function(){
			state = 1;
	    });
		$('#sysNoticeEditForm').form({
			url : '${pageContext.request.contextPath}/sysNotice/edit',
			onSubmit : function(param) {
				param.state = state;
				param.id = id;
				progressLoad();
				var isValid = $(this).form('validate');
				if (!isValid) {
					progressClose();
				}
				return isValid;
			},
			success : function(result) {
				progressClose();
				result = $.parseJSON(result);
				if(result.success){
					if (state == 2) {
						 parent.$.messager.alert('提示', '发布成功');
		           		 var parent_tabs = parent.$('#index_tabs');
		           		 var index = parent_tabs.tabs('getTabIndex', parent.$('#index_tabs').tabs('getSelected'));
							 var tab = parent_tabs.tabs('getTab', index);
							 if (tab.panel('options').closable) {
								parent_tabs.tabs('close', index);
							 }
							parent_tabs.tabs('select', "公告通知");
							parent_tabs.tabs('update', {
								tab : parent_tabs.tabs('getSelected'), //获取当前被选中的页面
								options : {
									//Title : '新标题',
									}
								}
							);
					} else {
						//刷新上一个tab，本tab不动
						var parent_tabs = parent.$('#index_tabs');
						var refeshTab = parent_tabs.tabs('getTab', "公告通知");
						parent_tabs.tabs('update', {
							tab : refeshTab,  //获取当前被选中的页面
							options : {
								//Title : '新标题',
								}
							}
						);
					}
           	 }else{
           		if (state == 2) {
             		 parent.$.messager.alert('提示', '暂存失败');
           		} else {
              		 parent.$.messager.alert('提示', '发布失败');
           		}
           	 }
			}
		});
		
	});
</script>
<div id="sysNoticeEdit" >
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 0px;" >
		<form id="sysNoticeEditForm" method="post">
			<div id="title" class="div_title" style="height: 30px;padding-top: 2px" >标题：<input style="width: 40%;height: 90%" name="title" size="40" type="text" placeholder="输入标题" class="easyui-validatebox span2" data-options="required:true,validType:['isBlank','length[0,64]']" value="${sysNotice.title}"></div>
			<table style="width: 100%">
				<tr > 
				<td class="div_title" style="height: 30px;width: 40%">发布时间：<input name="pubdate" style="" size="25" placeholder="" class="easyui-datetimebox" data-options="required:true,editable:false" value="${sysNotice.pubdate}"></td>
				<td class="div_title" align="top" style="height: 50px;width: 10%;text-align: right;">描述：</td>
				<td class="div_title" align="top" style="height: 50px;width: 50%"><textarea style="height: 100%;width: 90%" name="description"  maxlength="500" >${sysNotice.description}</textarea></td>
				</tr>
			</table>
			<textarea id="content" class="ckeditor" name="content" maxlength="50000">${sysNotice.content}</textarea>
			<br>
			<div style="text-align: center"><button id="btn-temporary" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">暂存</button>
			&nbsp;&nbsp;&nbsp;<button id="btn-summit" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">发布</button>
			</div>
		</form>
	</div>
</div>
<style>
	.div_title {
		font-weight: bold;
	}
	#title {
		background-color: #c8d7e1;
	}
	#sysNoticeEdit {
		width: 210mm;
		margin: 0 auto;
		background-color: #eee;
		border: solid gray;
		border-width: thin;
	}
</style>