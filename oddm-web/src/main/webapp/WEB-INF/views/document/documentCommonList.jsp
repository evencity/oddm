<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
	.datagrid-view .datagrid-body {overflow-y: auto;overflow-x: hidden;}
	.color_f60{color: #F60}
	.color_green{color: green;}
	.datagrid-cell {   /* easyui不会自动识别英文字符串自动换行，nowrap: false不生效 */
		white-space: pre-wrap;
		word-wrap: break-word;
		word-break:break-all;
		line-height: 18px;
	}
</style>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>订单信息</title>
	
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	 <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
		<form id="searchForm">
			<table>
				<tr>
					<th>物料名称</th>
					<td><input name="nameMt" placeholder="请输入物料名"/></td>
					<td>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
				</tr>
			</table>
		</form>
	</div> 
	
	<div data-options="region:'center',border:false">
		<table id="documentCommonList" data-options="fit:true,border:true" ></table>
	</div> 
	 <script type="text/javascript">
	$(function() {
		documentCommonList = $('#documentCommonList').datagrid({
			url : '${ctx}/documentCommon/dataGrid',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'asc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200],
            frozenColumns : [ [ {
				width : '10%',
				title : '序号',
				field : 'id',
				hidden:true
			}, {
				width : '15%',
				title : '版本',
				field : 'version',
				formatter:function(value,row,index){
                    return "V"+row.version;
                }
			},  {
				width : '30%',
				title : '物料名',
				field : 'nameMt',
			},{
				title : '路径',
				field : 'path',
				hidden : true
			},{
				width : '20%',
				title : '上传时间',
				field : 'uploadtime'
			} ,{
				width : '15%',
				title : '状态',
				field : 'state',
				formatter : function(value, row, index) {
					switch (value) {
						case 1:
							return '<span class="color_f60">文档未发布</span>';
						case 2:
							return '<span class="color_green">文档已发布</span>';
					}
				}
			}] ],
		});
		
	});
	
	
	</script>
</body>
</html>