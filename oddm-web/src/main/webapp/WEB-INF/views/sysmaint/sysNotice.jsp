<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<style type="text/css">
	.datagrid-view .datagrid-body {overflow-y: auto;overflow-x: hidden;}
	.datagrid-cell {   /* easyui不会自动识别英文字符串自动换行，nowrap: false不生效 */
		white-space: pre-wrap;
		word-wrap: break-word;
		word-break:break-all;
		line-height: 18px;
	}
	input {border:1px solid #CCC;font-size:12px;height:18px;border-radius: 4px;}
</style>
<c:if test="${fn:contains(sessionInfo.resourceList, '/sysNotice/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/sysNotice/delete')}">
	<script type="text/javascript">
	$.canDelete = true;
	</script>
</c:if>

<script type="text/javascript">
	function dateformatter(date){
		if (date == null || date == "") return "";
		return date.toLocaleString();  
	}
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}' + '/sysNotice/dataGrid',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			nowrap:false,// :当true时，显示数据在同一行上。默认true。 false自动换行显示
			idField : 'id', //编辑、删除操作按钮用到 row.key
			sortName : 'pubdate',
			sortOrder : 'desc',
			onDblClickRow : function(index, row){
            	showDetail(row.id, row.title);
	        },
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200],
			frozenColumns : [ [ {
				width : '25%',
				title : '标题',
				field : 'title',
				sortable : true,
				align:'left',
			},{
				width : '15%',
				title : '发布时间',
				field : 'pubdate',
				sortable : true,
				align:'left',
				formatter:function(value,row,index){  
	                return dateformatter(new Date(value));
                } 
			}, {
				width : '42%',
				title : '描述',
				field : 'description',
				align:'left',
			},{
				width : '8%',
				title : '状态',
				field : 'state',
				align:'left',
				formatter : function(value, row, index) {
					switch (value) {
					case 1:
						return '未发布';
					case 2:
						return '已发布';
					}
				},
				styler: function(value,row,index){
					if (value == 2){
						return 'color:green;';
					} else {
						return 'color:#F60;';
					}
				}
			} , {
				field : 'action',
				title : '操作',
				width : '10%',
				align:'left',
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					if ($.canEdit) {
						str += $.formatString('<a href="javascript:void(0)" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					if ($.canDelete) {
						str += $.formatString('<a href="javascript:void(0)" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
					}
					return str;
				}
			}} ] ],
			toolbar : '#toolbar'
		});
	});
	function addFun(){
	  	var url = "${ctx}/sysNotice/addPage";
	  	var title = "添加公告";
		window.parent.addTab({
			url : url,
			title : title,
		});
	}
	function showDetail(id, title){
	  	var url = "${ctx}/sysNotice/getPage?id="+id;
	  	var title = "公告:"+title;
		window.parent.addTab({
			url : url,
			title : title,
		});
	}
	function deleteFun(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		//console.log("ididid:"+id);
		parent.$.messager.confirm('询问', '您是否要删除当前产品类型？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/sysNotice/delete', {
					id : id
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('reload');
					}
					progressClose();
				}, 'JSON');
			}
		});
	}
	
	function editFun(id){
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
	  	var url = '${ctx}/sysNotice/editPage?id=' + id;
	  	var title = "编辑公告";
		window.parent.addTab({
			url : url,
			title : title,
		});
	}
	function searchFun() {
		var name = $('#title').val().trim();
		if(name != ''){
			dataGrid.datagrid('load', {
				title:name
			});
		}else{
			parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
		}
		
	}
	function cleanFun() {
		$('#title').val('');
		//dataGrid.datagrid('load', {});
	}
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
		<table>
			<tr>
				<th>标题:</th>
				<td><input name="title" placeholder="请输入标题名" id="title"/></td>
				<td>
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/sysNotice/add')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
	</div>
</body>
</html>