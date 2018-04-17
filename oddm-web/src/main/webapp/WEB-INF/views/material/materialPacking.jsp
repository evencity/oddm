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
<c:if test="${fn:contains(sessionInfo.resourceList, '/materialPacking/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/materialPacking/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>

<title>包材物料</title>
	<script type="text/javascript">
	var dataGrid;
	$(function() {
		$('#typeId').combobox({
			url : '${pageContext.request.contextPath}/materialType/materialType?type=3',
			/* parentField : 'pid', */
			 valueField:'id',    
			 textField:'name',
			 panelHeight : 'auto',
			  editable:false ,
			  onSelect : function(param){
				  dataGrid.datagrid('load', {
						typeId:param.id
					}); 
			  }
		});
		
		var myview = $.extend({},$.fn.datagrid.defaults.view,{
		    onAfterRender:function(target){
		        $.fn.datagrid.defaults.view.onAfterRender.call(this,target);
		        var opts = $(target).datagrid('options');
		        var vc = $(target).datagrid('getPanel').children('div.datagrid-view');
		        vc.children('div.datagrid-empty').remove();
		        if (!$(target).datagrid('getRows').length){
		            var d = $('<div class="datagrid-empty"></div>').html(opts.emptyMsg || 'no records').appendTo(vc);
		            d.css({
		                position:'absolute',
		                left:0,
		                top:50,
		                width:'100%',
		                textAlign:'center'
		            });
		        }
		    }
	});
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}' + '/materialPacking/dataGrid',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'desc',
			view: myview,
            emptyMsg: '没有找到相关数据',
			onLoadSuccess:function(data){ 
				 /* $(this).datagrid("fixRownumber");   */
            },
			nowrap:false,
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200 ],
			frozenColumns : [ [ {
				width : '100',
				title : '序号',
				field : 'id',
				sortable : true,
				hidden : true
			}, {
				width : '15%',
				title : '物料名称',
				field : 'name',
				sortable : true
			},  {
				width : '15%',
				title : '类型',
				field : 'typeName',
				sortable : true
			}, {
				width : '15%',
				title : '客制化/通用',
				field : 'normal',
				sortable : true,
				formatter : function(value, row, index) {
					switch (value) {
					case 1:
						return '通用';
					case 2:
						return '客制化';
					}
				}
			},{
				width :  '10%',
				title : '基础物料',
				field : 'isbase',
				sortable : true,
				formatter : function(value, row, index) {
					switch (value) {
						case 1:
							return '否';
						case 2:
							return '是';
					}
				}
			} ,{
				width : '30%',
				title : '描述',
				field : 'description'
			} , {
				field : 'action',
				title : '操作',
				width : "15%",
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
		$("body").keydown(function(event) {
		    if (event.keyCode == "13") {//keyCode=13是回车键
		    	var name = $('#name').val().trim();
			//	var typeId = $('#typeId').combobox('getValue').trim();
				if(name != '' ){
					dataGrid.datagrid('load', {
						name:name,
				//		typeId:typeId
					});
				}else{
					parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
				}
		    }
		});
	});
	
	function addFun() {
		parent.$.modalDialog({
			title : '添加',
			width : 500,
			height : 300,
			href : '${ctx}/materialPacking/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#materialPackingAddForm');
					f.submit();
				}
			} ]
		});
	}
	
	function deleteFun(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要删除当前包材物料？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/materialPacking/delete', {
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
	
	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '编辑',
			width : 500,
			height : 300,
			href : '${ctx}/materialPacking/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#materialPackingEditForm');
					f.submit();
				}
			} ]
		});
	}
	
	function searchFun() {
		var name = $('#name').val().trim();
	//	var typeId = $('#typeId').combobox('getValue').trim();
		if(name != '' ){
			dataGrid.datagrid('load', {
				name:name,
			//	typeId:typeId
			});
		}else{
			parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
		}
	}
	function cleanFun() {
		$('#name').val('');
		$('#typeId').combobox('setValue','');
	//	dataGrid.datagrid('load', {});
	}
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
		<!-- <form id="searchForm"> -->
			<table>
				<tr>
					<td>类型:</td>
					<td><select  id="typeId" style="width: 120px; height: 20px;" class="easyui-validatebox"></select></td>
					<th>物料名称:</th>
					<td><input name="name" placeholder="请输入物料名称" id="name"/></td>
					<td>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
				</tr>
			</table>
		<!-- </form> -->
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/materialPacking/add')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
	</div>
</body>
</html>