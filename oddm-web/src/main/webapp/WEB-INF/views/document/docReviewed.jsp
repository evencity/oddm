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
	.datagrid-view1 .datagrid-body {overflow-y: auto;overflow-x: hidden;}
	.autoInput{width: 95%}
	.datagrid-cell {   /* easyui不会自动识别英文字符串自动换行，nowrap: false不生效 */
		white-space: pre-wrap;
		word-wrap: break-word;
		word-break:break-all;
		line-height: 18px;
	}
</style>

<c:if test="${fn:contains(sessionInfo.resourceList, '/docReviewed/review')}">
	<script type="text/javascript">
		$.canReview = true;
	</script>
</c:if>
<title>订单信息</title>
	<script type="text/javascript">
	var dataGrid;
	$(function() {
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
			url : '${ctx}/docReviewed/dataGrid',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'uploadtime',
			sortOrder : 'desc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200 ],
			view: myview,
            emptyMsg: '没有找到相关数据',
			onLoadSuccess:function(data){ 
				
            },
            frozenColumns : [ [{
				width : '10%',
				title : 'NO.',
				field : 'id',
				sortable : true,
				hidden:true
			}, {
				width : '10%',
				title : '版本',
				field : 'version',
				formatter:function(value,row,index){
                    return "V"+row.version;
                }
			}, {
				title : '路径',
				field : 'path',
				hidden : true
			},{
				title : '文档名',
				field : 'nameMt',
				width : '50%',
				formatter:function(value,row,index){
					var docName = row.orderInfoDTO.productFactory + row.nameMt + row.orderInfoDTO.orderNo + "("+ row.orderInfoDTO.productClient +")V"+row.version;
                    return docName;
                }
			},  {
				width : '20%',
				title : '上传时间',
				field : 'uploadtime'
			} , {
				field : 'state',
				title : '操作',
				width : '20%',
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					//var docName = row.orderInfoDTO.productFactory + row.nameMt + row.orderInfoDTO.orderNo + "("+ row.orderInfoDTO.productClient +")";
					var docName = row.orderInfoDTO.productFactory + row.nameMt + row.orderInfoDTO.orderNo + "("+ row.orderInfoDTO.productClient +")V"+row.version;
					if(row.state == 2){
						if($.canReview){
							//str += "<a href='javascript:void(0)' onclick='docReviewed_reviewed("+row.id+", "+row.orderInfoDTO.id+", "+docName+", "+row.path+")'> 审核文档</a>";
							str += $.formatString('<a href="javascript:void(0)" onclick="docReviewed_reviewed(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\');" >审核文档</a>', row.id, row.orderInfoDTO.id, encodeURI(docName),row.path, row.orderInfoDTO.sellerId);
						}else{
							str = "审核文档";
						}
					}
					if(row.state == 4){
						str = "审核未通过";
					}
					return str;
				}
			} ] ],
			toolbar : '#toolbar'
		});
		/* $("body").keydown(function(event) {
		    if (event.keyCode == "13") {//keyCode=13是回车键
		    	var documentName = $('#documentName').val().trim();
				if(documentName == "" ){
					parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
				}else{
					dataGrid.datagrid('load', {
						documentName:documentName
					});
				}  
		    }
		}); */
	});
	
	
	function docReviewed_reviewed(id,orderId,docName,path, sellerId ){
		/* console.log(docName); */
		docName = decodeURI(docName)
	//	console.log(sellerId)
		var fname = docName.substring(0,docName.lastIndexOf("V"))
		/* console.log(sellerId+"==="+fname);
		return ; */
		
		parent.$.modalDialog({
			title : '文档审核',
			width : 300,
			height : 160,
			href : '${ctx}/docReviewed/reviewPage?docName='+encodeURIComponent(docName.replace(new RegExp(' ',"gm"),"_").replace(new RegExp('\\+',"gm"),"_"))+'&path='+encodeURIComponent(path.replace(new RegExp(' ',"gm"),"_").replace(new RegExp('\\+',"gm"),"_")),
			buttons : [ {
				text : '提交',
				handler : function() {
					
					var state = parent.$("input[name='documentPassOrNo']:checked").val();
					if(typeof(state) != 'undefined'){
						$.ajax({
				         	type: 'POST',
				            url: "${ctx}/docReviewed/review?id=" + id +"&pass=" + state +"&orderId="+orderId,
				            dataType: "json",
				            contentType: "application/json; charset=utf-8",
							success : function(data) {
								if (data.success) {
									parent.$.messager.alert('提示', data.msg, 'info');
									parent.$.modalDialog.handler.dialog('close');
									var parent_tabs = parent.$('#index_tabs');
									parent_tabs.tabs('update', {
										tab : parent_tabs.tabs('getSelected'), //获取当前被选中的页面
										options : {}
										}
									);
									//审核成功后删除文档审核提示缓存
									
									
								//	console.log("sellerId"+sellerId+"=== fname"+fname)
								}else{
									parent.$.messager.alert('提示', data.msg, 'info');
								}
								/* if(sellerId != null && sellerId != 'undefined'){
									$.post("${ctx}/docUpload/removeDocCache" ,{sellerId:sellerId,fileName:fname}, function(){} )
								} */
							}
						})
					}else{
						parent.$.messager.alert('提示', '请选择审核结果！', 'info');
					}
					
				}
			} ]
		});
	}
	
	function searchFun() {
		var documentName = $('#documentName').val().trim();
		if(documentName == "" ){
			parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
		}else{
			dataGrid.datagrid('load', {
				documentName:documentName
			});
		}  
	}
	function cleanFun() {
		orderNo:$('#documentName').val('');
	//	dataGrid.datagrid('load', {});
	}
	
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	 <!-- <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
		<form id="searchForm">
			<table>
				<tr>
					<th>文档名：</th>
					<td><input name="documentName" placeholder="请输入文档名" id="documentName"/></td>
					<td>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>  -->
	
	<div data-options="region:'center',border:false">
		<table id="dataGrid" data-options="fit:true,border:true" ></table>
	</div> 
	<!-- <div id="toolbar" style="display: none;">
		
	</div> -->
</body>
</html>