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
	.color_F60 {color: #F60}
	.color_green {color: green;}
	.color_blue {color: blue;}
	a{cursor: pointer;text-decoration: none;color: #000}
	.autoInput{width: 95%}
	.datagrid-cell {   /* easyui不会自动识别英文字符串自动换行，nowrap: false不生效 */
		white-space: pre-wrap;
		word-wrap: break-word;
		word-break:break-all;
		line-height: 18px;
	}
	input {border:1px solid #CCC;font-size:12px;height:19px;border-radius: 4px;}
</style> 
<c:if test="${fn:contains(sessionInfo.resourceList, '/order/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/order/editableAll')}">
	<script type="text/javascript">
		$.canEditable = true;
	</script>
</c:if>
<%-- <c:if test="${fn:contains(sessionInfo.resourceList, '/order/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if> --%>
<c:if test="${sessionInfo.hasRoles == true}">
	<script type="text/javascript">
		$.hasRoles = true;
	</script>
</c:if>
<title>订单信息</title>
	<script type="text/javascript">

	var dataGrid;
    
	$(function() {
		 $.extend($.fn.datagrid.methods, {  
		        fixRownumber :function(jq) {  
		              return jq.each(function() {  
		                    var panel = $(this).datagrid("getPanel");  
		                    //获取最后一行的number容器,并拷贝一份  
		                    var clone = $(".datagrid-cell-rownumber", panel).last().clone();  
		                    //由于在某些浏览器里面,是不支持获取隐藏元素的宽度,所以取巧一下  
		                    clone.css({  
		                          "position":"absolute",  
		                          left : -1000  
		                    }).appendTo("body");  
		                    var width = clone.width("auto").width();  
		                    //默认宽度是25,所以只有大于25的时候才进行fix  
		                    if(width > 25) {  
		                          //多加5个像素,保持一点边距  
		                          $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);  
		                          //修改了宽度之后,需要对容器进行重新计算,所以调用resize  
		                          $(this).datagrid("resize");  
		                          //一些清理工作  
		                          clone.remove();  
		                          clone =null;  
		                    }else{  
		                          //还原成默认状态  
		                          $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");  
		                    }  
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
		if($.hasRoles){
			dataGrid = $('#dataGrid').datagrid({
				url : '${ctx}/order/dataGridAll',
				striped : true,
				pagination : true,
				singleSelect : true,
				idField : 'id',
				sortName : 'id',
				sortOrder : 'desc',
				nowrap:false,
				onDblClickRow : function(index, row){
				//	showOrderFun(row.id,row.orderNo);
					showOrderFun(row.id,row.orderNo,row.sellerId);
		        },
				pageSize : 50,
				pageList : [ 10, 20, 30, 40, 50, 100, 200 ],
				view: myview,
	            emptyMsg: '没有找到相关数据',
				onLoadSuccess:function(data){ 
					 $(this).datagrid("fixRownumber");  
	            },
	            rownumbers : true,
				frozenColumns : [ [ {
					field:'ck',
					checkbox:true,
					width : '5%'
				}, {
					width : '12%',
					title : '订单号',
					field : 'orderNo',
					sortable : true,
					formatter : function(value, row, index) {
						if(row.pid ==  null){
							return value + '<span class="color_F60">[新单]</span>';
						}else{
							return value;
						} 
					}
				}, {
					field : 'updatetime',
					title : '更新时间',
					width : '14%',
					sortable : true,
				}, {
					width : '16%',
					title : '客户名称',
					field : 'clientNameCode',
				},{
					width : '13%',
					title : '客户机型',
					field : 'productClient',
				} ,{
					width : '10%',
					title : '工厂机型',
					field : 'productFactory',
				} ,{
					width : '8%',
					title : '所属业务',
					field : 'seller',
				},{
					width : '8%',
					title : '所属跟单',
					field : 'merchandiser',
				},{
					width : '11%',
					title : '状态',
					field : 'state',
					sortable : true,
					formatter : function(value, row, index) {
						var isRead = "";
						if(row.isRead == 1){
							isRead = '<span class="color_F60">[未读]</span>';
						}else{
							isRead = '';
						}
						switch (value) {
							case 1:
								return '未编辑';
							case 2:
								return '<span class="color_F60">待提交审核'+isRead+'</span>';
							case 3:
								var str = '&nbsp;';
								
								return '<span class="color_blue">已提交审核'+isRead+'</span>';
							case 4:
								
								return '<span class="color_F60">审核不通过'+isRead+'</span>';
							case 5:
								
								return '<span class="color_green">审核通过'+isRead+'</span>';
							case 6:
							
								return '<span class="color_green">已齐料'+isRead+'</span>';
							case 7:
								return '<span class="color_green">完结'+isRead+'</span>';
						}
					}
				}   , {
					field : 'action',
					title : '操作',
					width : '5%',
					formatter : function(value, row, index) {
						var str = '&nbsp;';
						//str += $.formatString('<img onclick="showOrderFun(\'{0}\',\'{1}\');" src="{2}" title="查看" style="cursor:pointer"/></img>', row.id,row.orderNo,"${ctx}/jslib/easyui1.4.2/themes/icons/search.png");
						str += $.formatString('<img onclick="showOrderFun(\'{0}\',\'{1}\',\'{2}\');" src="{3}" title="查看" style="cursor:pointer"/></img>', row.id,row.orderNo,row.sellerId,"${ctx}/jslib/easyui1.4.2/themes/icons/search.png");
						return str;
					}
				}   ] ],
				toolbar : '#toolbar'
			});
		}else{
			dataGrid = $('#dataGrid').datagrid({
				url : '${ctx}/order/dataGridAll',
				striped : true,
				pagination : true,
				singleSelect : true,
				idField : 'id',
				sortName : 'id',
				sortOrder : 'desc',
				nowrap:false,
				onDblClickRow : function(index, row){
//					showOrderFun(row.id,row.orderNo);
					showOrderFun(row.id,row.orderNo,row.sellerId);
		        },
				pageSize : 50,
				pageList : [ 10, 20, 30, 40, 50, 100, 200 ],
				view: myview,
	            emptyMsg: '没有找到相关数据',
				onLoadSuccess:function(data){ 
					 $(this).datagrid("fixRownumber");  
	            },
	            rownumbers : true,
				frozenColumns : [ [ {
					field:'ck',
					checkbox:true,
					width : '5%'
				}, {
					width : '12%',
					title : '订单号',
					field : 'orderNo',
					sortable : true,
					formatter : function(value, row, index) {
						if(row.pid ==  null){
							return value + '<span class="color_F60">[新单]</span>';
						}else{
							return value;
						} 
					}
				}, {
					field : 'updatetime',
					title : '更新时间',
					width : '14%',
					sortable : true
				}, {
					width : '16%',
					title : '客户编码',
					field : 'clientNameCode',
				},{
					width : '13%',
					title : '客户机型',
					field : 'productClient',
				} ,{
					width : '10%',
					title : '工厂机型',
					field : 'productFactory',
				} ,{
					width : '8%',
					title : '所属业务',
					field : 'seller',
				},{
					width : '8%',
					title : '所属跟单',
					field : 'merchandiser',
				},{
					width : '12%',
					title : '状态',
					field : 'state',
					sortable : true,
					formatter : function(value, row, index) {
						var isRead = "";
						if(row.isRead == 1){
							isRead = '<span class="color_F60">[未读]</span>';
						}else{
							isRead = '';
						}
						switch (value) {
							case 1:
								return '暂存';
							case 2:
								return '<span class="color_F60">待提交审核'+isRead+'</span>';
							case 3:
								var str = '&nbsp;';
								
								return '<span class="color_blue">已提交审核'+isRead+'</span>';
							case 4:
								
								return '<span class="color_F60">审核不通过'+isRead+'</span>';
							case 5:
								
								return '<span class="color_green">审核通过'+isRead+'</span>';
							case 6:
							
								return '<span class="color_green">已齐料'+isRead+'</span>';
							case 7:
								return '<span class="color_green">完结'+isRead+'</span>';
						}
					}
				}   , {
					field : 'action',
					title : '操作',
					width : '5%',
					formatter : function(value, row, index) {
						var str = '&nbsp;';
						//str += $.formatString('<img onclick="showOrderFun(\'{0}\',\'{1}\');" src="{2}" title="查看" style="cursor:pointer"/></img>', row.id,row.orderNo,"${ctx}/jslib/easyui1.4.2/themes/icons/search.png");
						str += $.formatString('<img onclick="showOrderFun(\'{0}\',\'{1}\',\'{2}\');" src="{3}" title="查看" style="cursor:pointer"/></img>', row.id,row.orderNo,row.sellerId,"${ctx}/jslib/easyui1.4.2/themes/icons/search.png");
						return str;
					}
				}   ] ],
				toolbar : '#toolbar'
			});
		}
		
		
		$("body").keydown(function(event) {
		    if (event.keyCode == "13") {//keyCode=13是回车键
		    	var orderNo = $('#orderNo').val().trim();
				if($.hasRoles){
					var clientName = $('#clientName').val().trim();
					var clientBrand = $('#clientBrand').val().trim()
				}else{
					var clientNameCode = $('#clientNameCode').val().trim();
				}
				var productClient = $('#productClient').val().trim();
				var merchandiser = $('#merchandiser').val().trim();
				var seller = $('#seller').val().trim();
				var productFactory = $('#productFactory').val().trim();
				if(orderNo == "" && clientName == "" && productClient == "" && seller == "" && productFactory == "" && clientBrand == '' && merchandiser == ''){
					parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
				}else{
					if($.hasRoles){
						dataGrid.datagrid('load', {
							orderNo:orderNo,
							clientName:clientName,
							clientBrand : clientBrand,
							productClient:productClient,
							productFactory:productFactory,
							seller:seller,
							merchandiser:merchandiser
						}); 
					}else{
						dataGrid.datagrid('load', {
							orderNo:orderNo,
							clientNameCode:clientNameCode,
							clientBrand : clientBrand,
							productClient:productClient,
							productFactory:productFactory,
							seller:seller,
							merchandiser:merchandiser
						}); 
					}
				}
		    }
		});
	});
	
	/* function deleteOrderFun() {
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		parent.$.messager.confirm('询问', '您是否要删除当前选项？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/order/delete', {
					id : row.id
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('clearSelections');
						dataGrid.datagrid('reload');
					} else {
						parent.$.messager.alert('错误', result.msg, 'error');
					}
					progressClose();
				}, 'JSON');
			}
		});
	} */
	function showOrderFun(id,orderNo,sellerId){
	  	//var url = "${ctx}/order/getPage?id="+id;
	  	var url ;
	  	if(typeof(sellerId) == 'undefined' || sellerId == null || sellerId == 'undefined'){
	  		
	  		 url = "${ctx}/order/getPage?id="+id;
	  	}else{
	  		 url = "${ctx}/order/getPage?id="+id+"&sellerId="+sellerId;
	  	}
	  	var title = "订单:"+orderNo;
		window.parent.addTab({
			url : url,
			title : title,
		});
	}
	function addOrderFun(){
	  	var url = "${ctx}/order/addPage";
	  	var title = "订单添加";
		window.parent.addTab({
			url : url,
			title : title,
		});
		
	}
	function editOrderFun(){
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		//console.log(row.state+!$.canEditable);
		if (row.state > 5) {//订单在齐料和归档后不可修改
			if (!$.canEditable) {
				parent.$.messager.alert('提示', '订单已经齐料或完结，未经授权不可以修改！', 'info');
				return;
			}
		}
	  	var url = "${ctx}/order/editPage?orderId="+row.id;
	  	var title = "订单:"+ row.orderNo +"(编辑)";
		window.parent.addTab({
			url : url,
			title : title,
		});
		
	}
	function addNeworderFun(){
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
	  	var url = "${ctx}/order/addNewPage?pid="+row.id;
	  	var title = "创建"+ row.orderNo +"的翻单";
		window.parent.addTab({
			url : url,
			title : title,
			id : "orderAddNew_"+row.orderNo
		});
		
	}
	
	
	function changeMerchandiser(){
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		var orderId = row.id;
		parent.$.modalDialog({
			title : '变更跟单',
			width : 300,
			height : 160,
			href : '${ctx}/order/changeMerchandiserPage',
			buttons : [ {
				text : '确定',
				handler : function() {
					var merchandiser = parent.$('#merchandiserName');
					console.log(merchandiser.data('merchandiserId'))
					if(merchandiser.data('merchandiserId') != 'undefined' && merchandiser.val() != null && merchandiser.val().trim() != ""){
						$.ajax({
							url:"${ctx}/user/listMerchandiser?username="+encodeURIComponent(merchandiser.val().trim()),
							type:"POST",
							dataType: "json",
				            contentType: "application/json; charset=utf-8", 
				            success: function(data){
				            	if(data.length > 0){
				            		$.ajax({
										url:"${ctx}/order/changeMerchandiser?id="+ orderId+"&merchandiserId="+merchandiser.data('merchandiserId')+"&merchandiser="+encodeURIComponent(merchandiser.val().trim()),
										type:"POST",
										dataType: "json",
							            contentType: "application/json; charset=utf-8", 
							            success: function(data){
							            	if (data.success) {
												parent.$.messager.alert('提示', data.msg, 'info');
												dataGrid.datagrid('load', {});
											}else{
												parent.$.messager.alert('提示', data.msg, 'info');
											}
							            }
									})
									parent.$.modalDialog.handler.dialog('close');
				            	}else{
				            		parent.$.messager.alert('提示', "您输入的跟单名称不存在！", 'info');
				            	}
				            }
						})
					}else{
	            		parent.$.messager.alert('提示', "请输入跟单名称！", 'info');
	            	}
				}
			} ]
		});
		
	}
	function changeSeller(){
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		var orderId = row.id;
		parent.$.modalDialog({
			title : '变更业务',
			width : 300,
			height : 160,
			href : '${ctx}/order/changeSellerPage',
			buttons : [ {
				text : '确定',
				handler : function() {
					var seller = parent.$('#sellerName');
					
					if(seller.data('sellerId') != 'undefined' && seller.val() != null && seller.val().trim() != ""){
						$.ajax({
							url:"${ctx}/user/listSeller?username="+encodeURIComponent(seller.val().trim()),
							type:"POST",
							dataType: "json",
				            contentType: "application/json; charset=utf-8", 
				            success: function(data){
				            	if(data.length > 0){
				            		$.ajax({
										url:"${ctx}/order/changeSeller?id="+ orderId+"&sellerId="+seller.data('sellerId')+"&seller="+encodeURIComponent(seller.val().trim()),
										type:"POST",
										dataType: "json",
							            contentType: "application/json; charset=utf-8", 
							            success: function(data){
							            	if (data.success) {
												parent.$.messager.alert('提示', data.msg, 'info');
												dataGrid.datagrid('load', {});
											}else{
												parent.$.messager.alert('提示', data.msg, 'info');
											}
							            }
									})
									parent.$.modalDialog.handler.dialog('close');
				            	}else{
				            		parent.$.messager.alert('提示', "您输入的业务名称不存在！", 'info');
				            	}
				            }
						})
					}else{
	            		parent.$.messager.alert('提示', "请输入业务名称！", 'info');
	            	}
				}
			} ]
		});
	} 
	
	
	
	function queryOrderDocFun(){
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		if(row.state < 5){
			parent.$.messager.alert('提示', '当前订单还未通过审核，不能查看相关文档！', 'info');
			return;
		}
	  	var url = "${ctx}/order/getAllDocumentByOrderIdPage?orderId="+row.id;
	  	var title = "订单:"+row.orderNo+"资料";
		window.parent.addTab({
			url : url,
			title : title,
			id : "document_"+row.id
		});
		
	}
	
	function qeuryOrderFollowFun(){
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		if(row.state < 5){
			parent.$.messager.alert('提示', '当前订单还未通过审核，不能查看相关跟单信息！', 'info');
			return;
		}
		$.ajax({
			type: 'POST',
		    url: "${ctx}/orderFollowup/checkOrderfollow?id="+row.id, 
		    dataType: "json",
		    contentType: "application/json; charset=utf-8",
		    success: function(data){
		    	if(data.success){
		    		var url = "${ctx}/order/getByOrderInfoPage?orderId="+row.id;
		    		var title = "跟单:"+row.orderNo;
		    		window.parent.addTab({
		    			url : url,
		    			title : title,
		    			id : "orderFollowup_"+row.id
		    		});
		    	}else{
		    		parent.$.messager.alert('提示', data.msg,  'info');
		    	}
		    }
		})
		
	}
	
	//查看物料交期
	function qeuryMaterialFollowupFun(){
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		if(row.state < 5){
			parent.$.messager.alert('提示', '当前订单还未通过审核，不能查看相关物料交期！', 'info');
			return;
		}
		$.ajax({
			type: 'POST',
		    url: "${ctx}/materialFollowup/checkExcitByOrderId?id="+row.id, 
		    dataType: "json",
		    contentType: "application/json; charset=utf-8",
		    success: function(data){
		    	if(data.success){
		    		var url = "${ctx}/order/getMaterialFollowupPage?orderId="+row.id;
		    		var title = "物料交期:"+row.orderNo;
		    		window.parent.addTab({
		    			url : url,
		    			title : title,
		    			id : "materialFollowup_"+row.id
		    		});
		    	}else{
		    		parent.$.messager.alert('提示', data.msg,  'info');
		    	}
		    }
		})
	}
	
	//查看包装工艺
	function qeuryManufactureFun(){
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		if(row.state < 5){
			parent.$.messager.alert('提示', '当前订单还未通过审核，不能查看相关包装工艺！', 'info');
			return;
		}
		$.ajax({
			type: 'POST',
			url: "${ctx}/manufacture/getManufactureByOrderNo?orderNo="+row.orderNo, 
		    dataType: "json",
		    contentType: "application/json; charset=utf-8",
		    success: function(data){
		    	if(data.success){
		    		var url = "${ctx}/order/getManufacturePage?orderNo="+row.orderNo;
		    		var title = "指导书:"+row.orderNo;
		    		window.parent.addTab({
		    			url : url,
		    			title : title,
		    			id : "mnufacture_"+row.id
		    		});
		    	}else{
		    		parent.$.messager.alert('提示', "查询不到相关包装工艺的内容！",  'info');
		    	}
		    }
		})
	}
	
	function qeuryOrderBomFun(){
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		if(row.state < 5){
			parent.$.messager.alert('提示', '当前订单还未通过审核，不能查看相关Bom！', 'info');
			return;
		}
		$.ajax({
			type: 'POST',
			 url: "${ctx}/bom/getBomByOrderNo?orderNo="+row.orderNo, 
		    dataType: "json",
		    contentType: "application/json; charset=utf-8",
		    success: function(data){
		    	if(data.success){
		    		var url = "${pageContext.request.contextPath}/bom/findBomInfoByOrderNo?orderId="+row.id;
		    		var title = "BOM单:"+row.orderNo;
		    		window.parent.addTab({
		    			url : url,
		    			title : title,
		    			/* iconCls : node.iconCls */
		    		});
		    	}else{
		    		parent.$.messager.alert('提示', "查询不到相关Bom的内容！",  'info');
		    	}
		    }
		})
	}
	
	
	
	function searchFun() {
		var orderNo = $('#orderNo').val().trim();
		if($.hasRoles){
			var clientName = $('#clientName').val().trim();
			var clientBrand = $('#clientBrand').val().trim()
		}else{
			var clientNameCode = $('#clientNameCode').val().trim();
		}
		var productClient = $('#productClient').val().trim();
		var seller = $('#seller').val().trim();
		var merchandiser = $('#merchandiser').val().trim();
		var productFactory = $('#productFactory').val().trim();
		if(orderNo == "" && clientName == "" && productClient == "" && seller == "" && productFactory == "" && clientBrand == '' && merchandiser == ''){
			parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
		}else{
			if($.hasRoles){
				dataGrid.datagrid('load', {
					orderNo:orderNo,
					clientName:clientName,
					clientBrand : clientBrand,
					productClient:productClient,
					productFactory:productFactory,
					seller:seller,
				 	merchandiser:merchandiser 
				}); 
			}else{
				dataGrid.datagrid('load', {
					orderNo:orderNo,
					clientNameCode:clientNameCode,
					clientBrand : clientBrand,
					productClient:productClient,
					productFactory:productFactory,
					seller:seller,
				    merchandiser:merchandiser 
				}); 
			}
		}  
	}
	function searchForNoReadFun() {
		dataGrid.datagrid('load', {
			isRead:1
		});
	}
	function cleanFun() {
		if($.hasRoles){
			$('#clientName').val('');
			$('#clientBrand').val('');
		}else{
			$('#clientNameCode').val('');
		}
		$('#orderNo').val('');
		$('#productClient').val('');
		$('#seller').val('');
		$('#productFactory').val('');
		
		
		//dataGrid.datagrid('load', {});
	}
	
	
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	 <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
		<!-- <form id="searchForm"> -->
			<table>
				<tr>
					<th>订单编号:</th>
					<td><input name="orderNo" placeholder="请输入订单编号" id="orderNo" class="autoInput"/></td>
					<c:if test="${sessionInfo.hasRoles == true}">
						<th>客户名称:</th>
						<td><input name="clientName" placeholder="请输入客户名称" id="clientName" class="autoInput"/></td>
					</c:if>
					<c:if test="${sessionInfo.hasRoles == false}">
						<th>客户编码:</th>
						<td><input name="clientNameCode" placeholder="请输入客户编码" id="clientNameCode" class="autoInput"/></td>
					</c:if>
					<th>工厂机型:</th>
					<td><input name="productFactory" placeholder="请输入工厂机型" id="productFactory" class="autoInput"/></td>
					<th>客户机型:</th>
					<td><input name="productClient" placeholder="请输入客户机型" id="productClient" class="autoInput"/></td>
					 <td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchForNoReadFun();">未读订单</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
					</td> 
				</tr>
				<tr>
					<c:if test="${sessionInfo.hasRoles == true}">
						<th>客户铭牌:</th>
						<td><input name="clientBrand" placeholder="请输入客户铭牌" id="clientBrand" class="autoInput"/></td>
					</c:if>
					<th>所属业务:</th>
					<td><input name="seller" placeholder="请输入业务名称" id="seller" class="autoInput"/></td> 
					<th>所属跟单:</th>
					<td><input name="merchandiser" placeholder="请输入所属跟单" id="merchandiser" class="autoInput"/></td>
				</tr>
			</table>
		<!-- </form> -->
	</div> 
	
	<div data-options="region:'center',border:false" style="top: 60px">
		<table id="dataGrid" data-options="fit:true,border:true" ></table>
	</div> 
	<div id="toolbar" style="display: none;">
		<%-- <c:if test="${fn:contains(sessionInfo.resourceList, '/order/allDelete')}">
			<a onclick="deleteOrderFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-del'">删除</a>
		</c:if> --%>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/order/allAdd')}">
			<a onclick="addOrderFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加订单</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/order/allEdit')}">
			<a onclick="editOrderFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">编辑订单</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/order/allAddNeworder')}">
			<a onclick="addNeworderFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">创建翻单</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/order/allChangeMerchandiser')}">
			<a onclick="changeMerchandiser();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">变更跟单</a>
		</c:if> 
		<c:if test="${fn:contains(sessionInfo.resourceList, '/order/allChangeSeller')}">
			<a onclick="changeSeller();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">变更业务</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/order/allShowOrderFollowup')}">
			<a onclick="qeuryOrderFollowFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查看跟单</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/order/allShowBom')}">
			<a onclick="qeuryOrderBomFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查看BOM单</a>
		</c:if>
 		<c:if test="${fn:contains(sessionInfo.resourceList, '/order/allShowDocument')}">
			<a onclick="queryOrderDocFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查看订单资料</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/order/allShowMaterialFollowup')}">
			<a onclick="qeuryMaterialFollowupFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查看物料交期</a>
		</c:if> 
		<c:if test="${fn:contains(sessionInfo.resourceList, '/order/allShowManufacture')}">
			<a onclick="qeuryManufactureFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查看包装工艺</a>
		</c:if>
	</div>
</body>
</html>