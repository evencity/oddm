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
				url : '${ctx}/materialFollowup/dataGrid',
				striped : true,
				pagination : true,
				singleSelect : true,
				idField : 'id',
				sortName : 'id',
				sortOrder : 'desc',
				nowrap:false,
				onDblClickRow : function(index, row){
					showMaterialFollowupFun(row.id,row.orderNo);
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
						switch (value) {
						case 5:
							return '<span class="color_F60">未齐料'+isRead+'</span>';
						case 6:
							return '<span class="color_green">已齐料'+isRead+'</span>';
						}
					}
				}   , {
					field : 'action',
					title : '操作',
					width : '5%',
					formatter : function(value, row, index) {
						var str = '&nbsp;';
						str += $.formatString('<img onclick="showMaterialFollowupFun(\'{0}\',\'{1}\');" src="{2}" title="查看" style="cursor:pointer"/></img>', row.id,row.orderNo,"${ctx}/jslib/easyui1.4.2/themes/icons/search.png");
						return str;
					}
				}   ] ],
				toolbar : '#toolbar'
			});
		}else{
			dataGrid = $('#dataGrid').datagrid({
				url : '${ctx}/materialFollowup/dataGridAll',
				striped : true,
				pagination : true,
				singleSelect : true,
				idField : 'id',
				sortName : 'id',
				sortOrder : 'desc',
				nowrap:false,
				onDblClickRow : function(index, row){
					showMaterialFollowupFun(row.id,row.orderNo);
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
						switch (value) {
						case 5:
							return '<span class="color_F60">未齐料'+isRead+'</span>';
						case 6:
							return '<span class="color_green">已齐料'+isRead+'</span>';
						}
					}
				}   , {
					field : 'action',
					title : '操作',
					width : '5%',
					formatter : function(value, row, index) {
						var str = '&nbsp;';
						str += $.formatString('<img onclick="showMaterialFollowupFun(\'{0}\',\'{1}\');" src="{2}" title="查看" style="cursor:pointer"/></img>', row.id,row.orderNo,"${ctx}/jslib/easyui1.4.2/themes/icons/search.png");
						return str;
					}
				}   ] ],
				toolbar : '#toolbar'
			});
		}
		
		
		$("body").keydown(function(event) {
		    if (event.keyCode == "13") {//keyCode=13是回车键
		    	searchFun();
		    }
		});
	});
	
	function showMaterialFollowupFun(id,orderNo){
		$.ajax({
			type: 'POST',
		    url: "${ctx}/materialFollowup/checkExcitByOrderId?id="+id, 
		    dataType: "json",
		    contentType: "application/json; charset=utf-8",
		    success: function(data){
		    	if(data.success){
		    		var url = "${ctx}/materialFollowup/getPage?id="+id;
		    	  	var title = "物料交期:"+orderNo;
		    		window.parent.addTab({
		    			id : id,
		    			url : url,
		    			title : title,
		    		});
		    	}else{
		    		parent.$.messager.alert('提示', data.msg,  'info');
		    	}
		    }
		})
	}
	/* function addMaterialFollowupFun(){
		parent.$.modalDialog({
			title : '查询订单',
			width : 300,
			height : 160,
			href : '${ctx}/materialFollowup/checkOrderNoPage',
			buttons : [ {
				text : '确定',
				handler : function() {
					var orderNo = parent.$('#orderNoForMaterialFollowup').val().trim();
					if(orderNo == null || "" == orderNo){
						parent.$.messager.alert('提示', '订单号不能为空！', 'info');
						return;
					}
					$.ajax({
						type: 'POST',
					    url: "${ctx}/materialFollowup/checkOrderForOrder?orderNo="+orderNo, 
					    dataType: "json",
					    contentType: "application/json; charset=utf-8",
					    success: function(data){
					    	if(data.success){
								//订单主表中订单号存在，则继续查询跟单表中是否存在数据					    		
					    		console.log(data.msg)
					    		$.ajax({
									type: 'POST',
								    url: "${ctx}/materialFollowup/checkExcitByOrderNo?orderNo="+orderNo, 
								    dataType: "json",
								    contentType: "application/json; charset=utf-8",
								    success: function(data){
								    	if(data.success){
								    		parent.$.messager.alert('提示', data.msg, 'info');
								    	}else{
								    		//若订单号未被绑定，则进行添加
								    		var url = "${ctx}/materialFollowup/addPage?orderNo="+orderNo;
								    	  	//var title = "添加物料交期:"+ orderNo;
								    	  	var title = "物料交期(编辑):"+ orderNo;
								    		window.parent.addTab({
								    			url : url,
								    			title : title,
								    		});
								    		parent.$.modalDialog.handler.dialog('close'); 
								    		
								    	}
								    }
					    		})
					    		
					    	}else{
					    		parent.$.messager.alert('提示', data.msg,  'info');
					    	}
					    }
					})
				}
			} ]
		});
		
	} */
	function editMaterialFollowupFun(){
		
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		/* $.ajax({
			type: 'POST',
		    url: "${ctx}/materialFollowup/checkExcitByOrderId?id="+row.id, 
		    dataType: "json",
		    contentType: "application/json; charset=utf-8",
		    success: function(data){
		    	if(data.success){
		    		var url = "${ctx}/materialFollowup/editPage?id="+row.id;
		    	  	var title = "物料交期(编辑):"+ row.orderNo ;
		    		window.parent.addTab({
		    			id : row.id,
		    			url : url,
		    			title : title,
		    		});
		    	}else{
		    		parent.$.messager.alert('提示', data.msg,  'info');
		    	}
		    }
		}) */
		var url = "${ctx}/materialFollowup/editPage?id="+row.id;
	  	var title = "物料交期(编辑):"+ row.orderNo ;
		window.parent.addTab({
			id : row.id,
			url : url,
			title : title,
		});
	} 
	
	function excelMaterialFollowupFun(id){
		parent.$.modalDialog({
			title : '导出物料交期Excel',
			width : 400,
			height : 250,
			href : '${ctx}/materialFollowup/excelPage',
			buttons : [ {
				text : '确定',
				handler : function() {
					var materialFollowExcel = parent.$("input[name=materialFollowExcel]:checked").val()
					console.log(materialFollowExcel)
					if(materialFollowExcel == null || typeof(materialFollowExcel) == 'undefined'){
						parent.$.messager.alert('提示', '请选择导出内容！', 'info');
						return;
					}
					var seller = parent.$('#seller').val();
					if(seller != null || typeof(seller) != 'undefined'){
						seller = seller.trim();
					}
					var clientName = parent.$('#clientName').val();
					if(clientName != null || typeof(clientName) != 'undefined'){
						clientName = clientName.trim();
					}
					var merchandiser = parent.$('#merchandiser').val();
					if(merchandiser != null || typeof(merchandiser) != 'undefined'){
						merchandiser = merchandiser.trim();
					}
					var dateOrderStart = parent.$('#dateOrderStart').datebox('getValue');
					if(dateOrderStart == null || typeof(dateOrderStart) == 'undefined'){
						dateOrderStart = '';
					}
					var dateOrderEnd = parent.$('#dateOrderEnd').datebox('getValue');
					if(dateOrderEnd == null || typeof(dateOrderEnd) == 'undefined'){
						dateOrderEnd = '';
					}
					download_file("${ctx}/materialFollowup/excelExport?state="+materialFollowExcel+"&seller="+seller+"&clientName="+clientName+"&merchandiser="+merchandiser+"&dateOrderStart="+dateOrderStart+"&dateOrderEnd="+dateOrderEnd);
					parent.$.modalDialog.handler.dialog('close');
				}
			} ]
		});
	}
	function download_file(url)  
	{  
	    if (typeof (download_file.iframe) == "undefined")  
	    {  
	        var iframe = document.createElement("iframe");  
	        download_file.iframe = iframe; 
	        document.body.appendChild(download_file.iframe);  
	        download_file.iframe.addEventListener( "load", function(){
		         //代码能执行到这里说明已经载入成功完毕了
		           this.removeEventListener( "load", arguments.call, false);
	         //这里是回调函数
		         parent.parent.$.messager.alert('提示', '导出excel出错，请联系管理员！');
		   }, false);
	    }  
	    
		download_file.iframe.src = url;  
	    download_file.iframe.style.display = "none";  
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
		var productFactory = $('#productFactory').val().trim();
		if(orderNo == "" && clientName == "" && productClient == "" && seller == "" && productFactory == "" && clientBrand == ''){
			parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
		}else{
			if($.hasRoles){
				dataGrid.datagrid('load', {
					orderNo:orderNo,
					clientName:clientName,
					clientBrand : clientBrand,
					productClient:productClient,
					productFactory:productFactory,
					seller:seller
				}); 
			}else{
				dataGrid.datagrid('load', {
					orderNo:orderNo,
					clientNameCode:clientNameCode,
					clientBrand : clientBrand,
					productClient:productClient,
					productFactory:productFactory,
					seller:seller
				}); 
			}
		}  
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
				</tr>
			</table>
		<!-- </form> -->
	</div> 
	
	<div data-options="region:'center',border:false" style="top: 60px">
		<table id="dataGrid" data-options="fit:true,border:true" ></table>
	</div> 
	<div id="toolbar" style="display: none;">
		<%-- <c:if test="${fn:contains(sessionInfo.resourceList, '/materialFollowup/add')}">
			<a onclick="addMaterialFollowupFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if> --%>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/materialFollowup/edit')}">
			<a onclick="editMaterialFollowupFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">编辑</a>
		</c:if> 
		<%-- <c:if test="${fn:contains(sessionInfo.resourceList, '/materialFollowup/delete')}">
			<a onclick="deleteMaterialFollowupFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-del'">删除</a>
		</c:if> --%>
		 <c:if test="${fn:contains(sessionInfo.resourceList, '/materialFollowup/excel')}">
			<a onclick="excelMaterialFollowupFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">导出excel</a>
		</c:if>
	</div>
</body>
</html>