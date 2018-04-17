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
	.inputs{border:1px solid #CCC;padding: 1px 0;font-size:12px;color:#999;border-radius: 3px;}
	.ordertable tr td{padding:10px 0 10px 6px;}
	.docAddTitle{padding: 5px 0px 0px 10px;height: 6%;font-size: 1.2em;}
	.floatLeft{float: left;}
	.color_069{color: #069}
	.color_f60{color: #f60;font-size: 1.1em}
	.datagrid-view1 .datagrid-body {overflow-y: auto;overflow-x: hidden;}
	.docAddTitle .btn-default {border-radius: 5px;cursor: pointer;font-size: 1em;color: #f60;}
</style>
	<script type="text/javascript">
	var orderId = "${orderId}";
	var dataGrid;
	
	$(function() {
		$.ajax({
			url : '${ctx}/order/getBaseOrderInfoDTO?orderId=' + orderId,
			type : "GET",
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				$('#orderNo').text(data.orderNo);
				$('#productFactory').text(data.productFactory);
				$('#productClient').text(data.productClient);
			}
		})
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
			url : '${ctx}/documentCommon/dataGridForDocument',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'asc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200 ],
			view: myview,
            emptyMsg: '没有找到相关数据',
			onLoadSuccess:function(data){ 
				
            },
            onDblClickRow : function(index, row){
            	console.log(row.path)
            	if(row.path == null){
            		parent.$.messager.alert('提示', '文档未发布，暂不能选择！', 'info');
            	}else{
    				$.ajax({
    					url : '${pageContext.request.contextPath}/document/isExistDocument?orderId='+orderId.trim()+"&mtName="+row.nameMt.trim(),
    					type : "GET",
    					dataType : "json",
    					contentType : "application/json; charset=utf-8",
    					async: false,
    					success : function(data) {
    						if(data.success){
    							 parent.$.messager.alert('提示', data.msg);
    							 $('#materialName').text("");
    						}else{
    							$('#materialName').text(row.nameMt);
    		    				$('#materialName').data('path',row.path);
    		    				$('#materialName').data('version',row.version);
    						}
    					}
    				})
            	}
				
	        }, 
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
				width : '35%',
				title : '物料名',
				field : 'nameMt',
			},{
				title : '路径',
				field : 'path',
				hidden : true
			},{
				width : '25%',
				title : '上传时间',
				field : 'uploadtime'
			} ,{
				width : '25%',
				title : '状态',
				field : 'state',
				formatter : function(value, row, index) {
					if(row.path == null){
						return '<span class="color_f60">文档未发布</span>';
					}else{
						return '<span class="color_green">文档已发布</span>';
					}
					/* switch (value) {
						case 1:
							return '<span class="color_f60">文档未发布</span>';
						case 2:
							return '<span class="color_green">文档已发布</span>';
					} */
				}
			} ] ],
		});


		$('#docAddForCommonnameMt').off('keydown').on('keydown',function(event){
			 if (event.keyCode == 13) {//keyCode=13是回车键
				/*  console.log(event.keyCode)
               	 alert(event.keyCode)
			 	 return false; */
				 dataGrid.datagrid('load', {
					 nameMt:$('#docAddForCommonnameMt').val().trim(),
					});
             }
		});
	});

	function searchFun() {
		dataGrid.datagrid('load', {
			nameMt:$('#docAddForCommonnameMt').val().trim(),
		});
	}
	function cleanFun() {
		$('#docAddForCommonnameMt').val('');
		dataGrid.datagrid('load', {});
	}
	
	</script>
</head>
<div class="docAddTitle floatLeft" style="width: 30%">订单号：<span class="color_069" id="orderNo"></span></div>
<div  class="docAddTitle floatLeft" style="width: 30%">工厂型号：<span class="color_069" id="productFactory"></span></div>
<div  class="docAddTitle floatLeft" style="width: calc(40% - 30px)">客户型号：<span class="color_069" id="productClient"></span></div>
<div  class="docAddTitle floatLeft" style="width: 30%">物料名称：<span class="color_f60" id="materialName"></span></div>
<div  class="docAddTitle floatLeft" style="width: calc(70% - 20px)">物料编码：<input class="inputs" id="materialCode"></input></div>
<div style="height:85%;" id="showAllMaterial">
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
						<td><input name="name" placeholder="请输入物料名称" id="docAddForCommonnameMt"/></td>
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