<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="edge" />

<title>订单信息</title>
<!-- 美工文档 -->
<c:if test="${fn:contains(sessionInfo.resourceList, '/document/artDocument')}">
	<script type="text/javascript">
		$.artDocument = true;
	</script>
</c:if>
<!-- 其他个人文档 -->
<c:if test="${fn:contains(sessionInfo.resourceList, '/document/otherPersonDoument')}">
	<script type="text/javascript">
		$.otherPersonDoument = true;
	</script>
</c:if>
<!-- 其他全部文档 -->
<c:if test="${fn:contains(sessionInfo.resourceList, '/document/otherAllDocument')}">
	<script type="text/javascript">
		$.otherAllDocument = true;
	</script>
</c:if>
<style type="text/css">
	.ordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:left;}
	.tabletitle{text-align:left; background-color:#c8d7e1;color:#333;font-size:1.4em; font-weight:500;}
	.tabletop{text-align:left; background-color:#DCE0E2;color:#333;font-size:1.2em; font-weight:500;}
	.inputs{border:1px solid #CCC;padding: 1px 0;font-size:12px;color:#999;border-radius: 3px;}
	.ordertable tr td{padding:10px 0 10px 6px;}
	.docAddTitle{padding: 5px 0px 0px 10px;height: 7%;font-size: 1.2em;}
	.floatLeft{float: left;}
	.color_069{color: #069}
	.color_f60{color: #f60;font-size: 1em}
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
	/* var productFactory = "${productFactory}";
	var orderNo = "${orderNo}";
	var productClient = "${productClient}";
	
	var orderId = "${orderId}";
	var orderIdForInput = "${orderIdForInput}"; */
	var productFactory = $("#productFactory").text();
	var orderNo = $("#orderNo").text();
	var productClient =$("#productClient").text();
	var orderId = "${orderId}"
	var orderIdForInput = "${orderIdForInput}";
	var dataGrid;
	$(function() {
		/* $.ajax({
			url : '${ctx}/order/getBaseOrderInfoDTO?orderId=' + orderId,
			type : "GET",
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				$('#orderNo').text(data.orderNo);
				$('#productFactory').text(data.productFactory);
				$('#productClient').text(data.productClient);
			}
		}) */
		if($.artDocument && ($.otherAllDocument || $.otherPersonDoument)){
			dataGrid = $('#dataGrid').datagrid({
				url : '${ctx}' + '/material/dataGrid',
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
					$.ajax({
						url : '${pageContext.request.contextPath}/document/isExistDocument?orderId='+orderId+"&mtName="+row.name.trim(),
						type : "GET",
						dataType : "json",
						contentType : "application/json; charset=utf-8",
						async: false,
						success : function(data) {
							if(data.success){
								 parent.$.messager.alert('提示', data.msg);
								 $('#materialName').text("");
							}else{
								$('#materialName').text(productFactory.replace(new RegExp(' ',"gm"),"_").replace(new RegExp('\\+',"gm"),"_")+row.name+orderNo+"("+ productClient.replace(new RegExp(' ',"gm"),"_").replace(new RegExp('\\+',"gm"),"_") +")V1.rar");
								$('#materialName').data('materialName',row.name);
								$('#materialName').data('typeId',row.typeId);
								$('#nameMt').val($('#materialName').text())
							}
						}
					})
					
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
					width : '20%',
					title : '类型',
					field : 'typeName',
					sortable : true
				}, {
					width : '50%',
					title : '描述',
					field : 'description'
				}  ] ],
			});
		}
		if($.artDocument){
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
					$.ajax({
						url : '${pageContext.request.contextPath}/document/isExistDocument?orderId='+orderId+"&mtName="+row.name.trim(),
						type : "GET",
						dataType : "json",
						contentType : "application/json; charset=utf-8",
						async: false,
						success : function(data) {
							if(data.success){
								 parent.$.messager.alert('提示', data.msg);
								 $('#materialName').text("");
							}else{
								$('#materialName').text(productFactory.replace(new RegExp(' ',"gm"),"_").replace(new RegExp('\\+',"gm"),"_")+row.name+orderNo+"("+ productClient.replace(new RegExp(' ',"gm"),"_").replace(new RegExp('\\+',"gm"),"_") +")V1.rar");
								$('#materialName').data('materialName',row.name);
								$('#materialName').data('typeId',row.typeId);
								$('#nameMt').val($('#materialName').text())
							}
						}
					})
					
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
		}
		
		if($.otherPersonDoument || $.otherAllDocument){
			dataGrid = $('#dataGrid').datagrid({
				url : '${ctx}' + '/material/dataGrid',
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
					$.ajax({
						url : '${pageContext.request.contextPath}/document/isExistDocument?orderId='+orderId+"&mtName="+row.name.trim(),
						type : "GET",
						dataType : "json",
						contentType : "application/json; charset=utf-8",
						async: false,
						success : function(data) {
							if(data.success){
								 parent.$.messager.alert('提示', data.msg);
								 $('#materialName').text("");
							}else{
								$('#materialName').text(productFactory.replace(new RegExp(' ',"gm"),"_").replace(new RegExp('\\+',"gm"),"_")+row.name+orderNo+"("+ productClient.replace(new RegExp(' ',"gm"),"_").replace(new RegExp('\\+',"gm"),"_") +")V1.rar");
								$('#materialName').data('materialName',row.name);
								$('#materialName').data('typeId',row.typeId);
								$('#nameMt').val($('#materialName').text())
							}
						}
					})
					
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
					width :  '15%',
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
				},{
					width : '40%',
					title : '描述',
					field : 'description'
				}  ] ],
			});
		}
			
		
		
		
		$('#docAddnameMt').off('keydown').on('keydown',function(event){
			 if (event.keyCode == 13) {//keyCode=13是回车键
				/*  console.log(event.keyCode)
               	 alert(event.keyCode)
			 	 return false; */
				 dataGrid.datagrid('load', {
						name:$('#docAddnameMt').val().trim(),
					});
             }
		});
		
		//清除iframe的input内容
		clearInput();
	});

	function searchFun() {
		dataGrid.datagrid('load', {
			name:$('#docAddnameMt').val().trim(),
		});
	}
	function cleanFun() {
		$('#docAddnameMt').val('');
		dataGrid.datagrid('load', {});
	}
	function clearInput(){ 
		var _iframe = document.getElementById('document_'+orderIdForInput).contentWindow;  
		var input  =_iframe.document.getElementById('documentFile'); 
		input.outerHTML = input.outerHTML;
		console.log("清除订单input文档选择")
	} 
	function openBrowse(){ 
		var ie = navigator.appName=="Microsoft Internet Explorer" ? true : false;  
		if(ie){ 
			var _iframe = document.getElementById('document_'+orderIdForInput).contentWindow;  
			var _div =_iframe.document.getElementById('documentFile').click(); 
		}else{
			console.log('非ie'+'document_'+orderIdForInput)
			var a = document.createEvent("MouseEvents");//FF的处理 
			a.initEvent("click", true, true);  
			var _iframe = document.getElementById('document_'+orderIdForInput).contentWindow;  
			var _div =_iframe.document.getElementById('documentFile').dispatchEvent(a); 
		} 
	} 
	
	</script>
</head>
<div class="docAddTitle floatLeft" style="width: 30%">订单号：<span class="color_069" id="orderNo">${orderNo}</span></div>
<div  class="docAddTitle floatLeft" style="width: 30%">工厂型号：<span class="color_069" id="productFactory">${productFactory}</span></div>
<div  class="docAddTitle floatLeft" style="width: calc(40% - 30px)">客户型号：<span class="color_069" id="productClient">${productClient}</span></div>
<div  class="docAddTitle floatLeft" style="width: 60%">文档名称<!-- (通用<input type="checkbox" id="toDocumentCommon"/>) -->：<span class="color_f60" id="materialName"></span></div>
<div  class="docAddTitle floatLeft" style="width: calc(40% - 20px)">物料编码：<input class="inputs" id="materialCode"></input></div>
<div class="docAddTitle floatLeft" style="width: calc(91% - 20px)">文档上传：
	<button  class=" btn-default " onclick="javascript:openBrowse();" >选择文档</button><span id="documentFilename"></span>
</div>
<div id="addFileProgterssbar" class="easyui-progressbar " data-options="value:0" style="width: 30%;display: none;margin-top: 10px;" ></div>
<div style="height:70%;" id="showAllMaterial">
	<div class="easyui-layout" data-options="fit:false,border:false" style="height: 100%;width:100%;overflow: auto;">
		<!-- <div data-options="region:'west',border:false" style="width: 15%;">
			<div>&nbsp;</div>
		</div> -->
		<div data-options="region:'center',border:false" style="width: 100%;" >
			<!-- <form id="searchForm"> -->
				<table>
					<tr>
						<!-- <th>分类:</th>
						<td><input name="type" placeholder=""/></td> -->
						<th>物料名称:</th>
						<td><input name="name" placeholder="请输入物料名称" id="docAddnameMt"/></td>
						<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
					</tr>
				</table>
			<!-- </form> -->
			<table id="dataGrid" data-options="border:false" style="height: 88%"></table>
		</div>
	</div>
</div>

</html>