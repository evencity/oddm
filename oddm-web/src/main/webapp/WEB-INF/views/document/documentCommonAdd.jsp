<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="edge" />

<title>订单信息</title>

<style type="text/css">
	.ordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:left;}
	.tabletitle{text-align:left; background-color:#c8d7e1;color:#333;font-size:1.4em; font-weight:500;}
	.tabletop{text-align:left; background-color:#DCE0E2;color:#333;font-size:1.2em; font-weight:500;}
	.inputs{border:1px solid #CCC;padding: 2px 0 2px 0px;font-size:12px;color:#999;border-radius: 3px;}
	.ordertable tr td{padding:10px 0 10px 6px;}
	.docAddTitle{padding: 3px 0px 3px 10px;height: 5%;font-size: 1.2em;}
	.floatLeft{width: 33%;float: left;}
	.color_069{color: #069}
	.datagrid-view1 .datagrid-body {overflow-y: auto;overflow-x: hidden;}
	.btn-default {border: 1px solid #DDDDDD;background: white;padding: 4px 13px;border-radius: 5px;cursor: pointer;color: #333;margin-right: 10px}
	.datagrid-cell {   /* easyui不会自动识别英文字符串自动换行，nowrap: false不生效 */
		white-space: pre-wrap;
		word-wrap: break-word;
		word-break:break-all;
		line-height: 18px;
	}
</style>
	<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}' + '/materialPacking/dataGridForOrder',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'asc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200 ],
			onDblClickRow : function(index, row){
				console.log($('#commonVersion').val().trim() != "")
				if($('#commonVersion').val().trim() == ""){
					$.messager.alert('提示', '请填写版本号！', 'info');
					return false;
				}
				$('#commonNameMt').text(row.name+'V'+$('#commonVersion').val().trim()+'.rar');
				$('#commonNameMt').data('nameMt',row.name);
				$('#nameMt').val($('#commonNameMt').text());
	        },  
			frozenColumns : [ [ {
				width : '5%',
				title : '序号',
				field : 'id',
				sortable : true,
				hidden : true
			}, {
				width : '30%',
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
				width : '40%',
				title : '描述',
				field : 'description'
			}  ] ],
		});
		
		$('#docCommonAddnameMt').off('keydown').on('keydown',function(event){
			 if (event.keyCode == 13) {//keyCode=13是回车键
				/*  console.log(event.keyCode)
               	 alert(event.keyCode)
			 	 return false; */
				 dataGrid.datagrid('load', {
						name:$('#docCommonAddnameMt').val().trim(),
					});
             }
		});
		clearInputCommon();
	});

	function searchFun() {
		dataGrid.datagrid('load', {
			name:$('#docCommonAddnameMt').val().trim(),
		});
	}
	function cleanFun() {
		$('#docCommonAddnameMt').val('');
		dataGrid.datagrid('load', {});
	}
	function clearInputCommon(){ 
		
		var _iframe = document.getElementById('/documentCommon/manager').contentWindow;  
		var input  =_iframe.document.getElementById('documentCommonFile'); 
		input.outerHTML = input.outerHTML;
		console.log("清除通用input文档选择")
	}	
	function openBrowseCommon(){ 
		var ie = navigator.appName=="Microsoft Internet Explorer" ? true : false;  
		if(ie){ 
			var _iframe = document.getElementById('/documentCommon/manager').contentWindow;  
			var _div =_iframe.document.getElementById('documentCommonFile').click(); 
			/* document.getElementById("testFile").click();  */
		}else{
			var a = document.createEvent("MouseEvents");//FF的处理 
			a.initEvent("click", true, true);  
			var _iframe = document.getElementById('/documentCommon/manager').contentWindow;  
			var _div =_iframe.document.getElementById('documentCommonFile').dispatchEvent(a); 
			/* document.getElementById("testFile").dispatchEvent(a);  */
		} 
	} 
	function clearInput(){ 
		
		var _iframe = document.getElementById('/documentCommon/manager').contentWindow;  
		var input  =_iframe.document.getElementById('documentCommonFile'); 
		input.outerHTML = input.outerHTML;
		console.log("清除订单input文档选择")
		
	}
	</script>
</head>

<div  class="docAddTitle" style="float: left;width: calc(50% - 20px)">文档名称：<span class="color_069" id="commonNameMt"></span></div>
<div  class="docAddTitle" style="float: left;width: 50%">版本号：<input type="text" class="inputs easyui-numberbox" id="commonVersion"  required="true"  missingMessage="必须填写数字" /></div>
<div class="docAddTitle" style="margin-bottom: 10px;float: left;width: calc(100% - 10px);">
	<div style="float: left;padding: 4px 13px 4px 2px;">文档上传：</div>
	<div style="float: left;"><button  class=" btn-default " onclick="javascript:openBrowseCommon();" >选择文档</button><span id="documentCommonFilename"></span></div>
	<div id="addFileProgterssbar" class="easyui-progressbar" data-options="value:0" style="width: 200px;display: none;margin-top:6px" ></div>
</div>
<!-- <div class="docAddTitle" style="margin-bottom: 20px;float: left;width: calc(100% - 10px);">
	<div style="float: left;padding: 4px 13px 4px 2px;">文档描述：</div>
	<div style="float: left;">
		<textarea style="width:calc(100% - 10px);" cols="50" rows="1" id="description"></textarea>
	</div>
	
</div> -->
<div style="height:72%">
	<div class="easyui-layout" data-options="fit:false,border:false" style="height: 100%;width:100%;overflow: auto;">
		<!-- <div data-options="region:'west',border:false" style="width: 15%;">
			<div>&nbsp;</div>
		</div> -->
		<div data-options="region:'center',border:false" style="width: 100%;">
			<!-- <form id="searchForm"> -->
				<table>
					<tr>
						<th>物料名称:</th>
						<td><input name="name" placeholder="请输入物料名称" id="docCommonAddnameMt"/></td>
						<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
					</tr>
				</table>
			<!-- </form> -->
			<table id="dataGrid" data-options="border:false" style="height: 85%"></table>
		</div>
	</div>
</div>

</html>