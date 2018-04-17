<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>
	.ordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:left;}
	.tabletitle{text-align:left; background-color:#c8d7e1;color:#333;font-size:1.4em; font-weight:500;}
	.tabletop{text-align:left; background-color:#DCE0E2;color:#333;font-size:1.2em; font-weight:500;}
	.ordertable tr td{padding:10px 0 10px 6px;}
	.inputs{border:1px solid #CCC;width:90%;padding:5px 0 2px 4px;font-size:12px;color:#999;}
	.lefttd{width:65px;}
    .lefttd90{width:90px; text-align:right;}
    .righttd{color069;}
    .bordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:center;border-collapse:collapse;}
	.bordertable td{border:1px solid #ccc;padding:10px 0 10px 6px;height:20px;}
	.textareacss{width:100%;background:none;border: 0px;text-align: left;}
	.choose{color: #06c;cursor: pointer;}
	.color_f60 {color: #f60;}
	.color069 {color :#069}
	.menu_hidden{display: none}
		.btn-default {border: 1px solid #DDDDDD;background: white;padding: 10px 18px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #333;}
	.btn-default:hover {border: 1px solid #DDDDDD;background: #04B5F9;padding: 10px 18px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #FFF;}
</style>
<jsp:include page="../inc.jsp"></jsp:include>

<script type="text/javascript">
	var orderId = "${orderId}";
	var sellerId = "${sellerId}";
	function compare(pid,order){
		var orderParent ;
		$.ajax({
			url:"${ctx}/order/getBaseOrderInfoDTO?orderId="+ pid,
			type:"GET",
			dataType: "json",
			async: false,
            contentType: "application/json; charset=utf-8", 
            success: function(data){
            	orderParent = data;
            	var isNew = '<span class="color_f60">&nbsp;[新]</span>';
            	//基础信息比较
            	if(order.productFactory != null && orderParent.productFactory != null){
            		if(order.productFactory.trim() != orderParent.productFactory.trim()){
                		order.productFactory = order.productFactory + isNew;
                	}
            	}
            	
            	if(order.clientName != null && orderParent.clientName != null){
            		if(order.clientName.trim() != orderParent.clientName.trim()){
                		order.clientName = order.clientName + isNew;
                	}
            	}
            	
            	if(order.clientBrand != null && orderParent.clientBrand != null){
            		if(order.clientBrand.trim() != orderParent.clientBrand.trim()){
                		order.clientBrand = order.clientBrand + isNew;
                	}
            	}
            	
            	if(order.clientNameCode != null && orderParent.clientNameCode != null){
            		if(order.clientNameCode.trim() != orderParent.clientNameCode.trim()){
                		order.clientNameCode = order.clientNameCode + isNew;
                	}
            	}
            	if(order.district != null && orderParent.district != null){
            		if(order.district.trim() != orderParent.district.trim()){
                		order.district = order.district + isNew;
                	}
            	}
            	if(order.productClient != null && orderParent.productClient != null){
            		if(order.productClient.trim() != orderParent.productClient.trim()){
                		order.productClient = order.productClient + isNew;
                	}
            	}
            	if(order.payment != null && orderParent.payment != null){
            		if(order.payment.trim() != orderParent.payment.trim()){
                		order.payment = order.payment + isNew;
                	}
            	}
            	if(order.placeDelivery != null && orderParent.placeDelivery != null){
            		if(order.placeDelivery.trim() != orderParent.placeDelivery.trim()){
                		order.placeDelivery = order.placeDelivery + isNew;
                	}
            	}
            	/* if(order.dateOrder.trim() != orderParent.dateOrder.trim()){
            		order.dateOrder = order.dateOrder + isNew;
            	}
            	if(order.dateDelivery.trim() != orderParent.dateDelivery.trim()){
            		order.dateDelivery = order.dateDelivery + isNew;
            	} 
            	if(order.placeDelivery.trim() != orderParent.placeDelivery.trim()){
            		order.placeDelivery = order.placeDelivery + isNew;
            	}
            	if(order.payment.trim() != orderParent.payment.trim()){
            		order.payment = order.payment + isNew;
            	}*/
            	//外壳
            	/* for(var i in order.orderShellDTOs){
            		var flag = false;
            		for(var j in orderParent.orderShellDTOs){
            			if(order.orderShellDTOs[i].name.trim() == orderParent.orderShellDTOs[j].name.trim() ){
            				flag = true;
            				var isNewFlag = false;
            				if(order.orderShellDTOs[i].color != orderParent.orderShellDTOs[j].color){
            					isNewFlag = true;
            				}
            				if(order.orderShellDTOs[i].craft != orderParent.orderShellDTOs[j].craft){
            					isNewFlag = true;
            				}
            				if(order.orderShellDTOs[i].silkScreen != orderParent.orderShellDTOs[j].silkScreen){
            					isNewFlag = true;
            				}
            				if(isNewFlag){
            					order.orderShellDTOs[i].name = order.orderShellDTOs[i].name.trim() + isNew;
            				}
            			}
            		}
            		if(!flag){
            			order.orderShellDTOs[i].name = order.orderShellDTOs[i].name + isNew;
            		}
            	}
            	
            	for(var i in order.orderHardwareDTOs){
            		var flag = false;
            		for(var j in orderParent.orderHardwareDTOs){
            			if(order.orderHardwareDTOs[i].name.trim() == orderParent.orderHardwareDTOs[j].name.trim() ){
            				flag = true;
            				var isNewFlag = false;
            				if(order.orderHardwareDTOs[i].specification != null ){
            					if(order.orderHardwareDTOs[i].specification.trim() != orderParent.orderHardwareDTOs[j].specification.trim()){
                					isNewFlag = true;
                				}
            				}
            				if(order.orderHardwareDTOs[i].supplier != null){
            					if(order.orderHardwareDTOs[i].supplier.trim() != orderParent.orderHardwareDTOs[j].supplier.trim()){
                					isNewFlag = true;
                				}
            				}
            				if(isNewFlag){
            					order.orderHardwareDTOs[i].name = order.orderHardwareDTOs[i].name.trim() + isNew;
            				}
            			}
            		}
            		if(!flag){
            			order.orderHardwareDTOs[i].name = order.orderHardwareDTOs[i].name + isNew;
            		}
            	}
            	for(var i in order.orderFittingDTOs){
            		var flag = false;
            		for(var j in orderParent.orderFittingDTOs){
            			if(order.orderFittingDTOs[i].name.trim() == orderParent.orderFittingDTOs[j].name.trim() ){
            				flag = true;
            				var isNewFlag = false;
            				if(order.orderFittingDTOs[i].specification != null){
            					if(order.orderFittingDTOs[i].specification.trim() != orderParent.orderFittingDTOs[j].specification.trim()){
                					isNewFlag = true;
                				}
            				}
            				if(order.orderFittingDTOs[i].supplier != null){
            					if(order.orderFittingDTOs[i].supplier.trim() != orderParent.orderFittingDTOs[j].supplier.trim()){
                					isNewFlag = true;
                				}
            				}
            				if(isNewFlag){
            					order.orderFittingDTOs[i].name = order.orderFittingDTOs[i].name.trim() + isNew;
            				}
            			}
            		}
            		if(!flag){
            			order.orderFittingDTOs[i].name = order.orderFittingDTOs[i].name + isNew;
            		}
            	}
            	for(var i in order.orderPackingDTOs){
            		var flag = false;
            		for(var j in orderParent.orderPackingDTOs){
            			if(order.orderPackingDTOs[i].name.trim() == orderParent.orderPackingDTOs[j].name.trim() ){
            				flag = true;
            				var isNewFlag = false;
            				if(order.orderPackingDTOs[i].specification != null){
            					if(order.orderPackingDTOs[i].specification.trim() != orderParent.orderPackingDTOs[j].specification.trim()){
                					isNewFlag = true;
                				}
            				}
            				if(order.orderPackingDTOs[i].supplier != null){
            					if(order.orderPackingDTOs[i].supplier.trim() != orderParent.orderPackingDTOs[j].supplier.trim()){
                					isNewFlag = true;
                				}
            				}
            				if(isNewFlag){
            					order.orderPackingDTOs[i].name = order.orderPackingDTOs[i].name.trim() + isNew;
            				}
            			}
            		}
            		if(!flag){
            			order.orderPackingDTOs[i].name = order.orderPackingDTOs[i].name + isNew;
            		}
            	} */
            }
		})
		return order;
	}
	$(function() {
	//	console.log(sellerId)
		$.ajax({
			url:"${ctx}/order/get?id="+ orderId+"&date="+new Date().getTime(),
			type:"GET",
			dataType: "json",
			async: false,
            contentType: "application/json; charset=utf-8", 
            success: function(data){
            	var order = data;
            	console.log(order)
            	var isNewFlag = '<span class="color_f60">&nbsp;[新]</span>';
            	if(data.state == 2 || data.state == 4){
            		$("#orderSubmitToSeller").css('display','inline-block');
            		//$("#orderSubmitToSeller").show();
            	}
            	if(data.state == 3){
            		$("#orderReviewBtn").css('display','inline-block');
            		//$("#orderReviewBtn").show();
            	}
            	if(data.state == 5){
            		$("#orderInfoAltList").css('display','inline-block');
            		//$("#orderReviewBtn").show();
            	}
            	if(data.pid != null){
            		order = compare(data.pid,order);
            	}
            	data = order
            	if(data.pid == null){
            		//新单
            		$('#show_isNeworder').parent().parent().css('display','none');
            		$('#isNeworder').text('[新单]');
            	}else{
            		//翻单
            		$('#show_isNeworder').css('display','block');
            		$('#isNeworder').text('[翻单]');
            		var order = data.orderInfoDTO;
            		$('#show_isNeworder').html(order.orderNo+"-("+ order.productFactory +")- 客："+order.productClient);
            		$('#show_isNeworder').data('id',order.id);
            		$('#show_isNeworder').data('orderNo',order.orderNo);
            	}
            	$('#orderNo').html(data.orderNo);
            	$('#merchandiser').html(data.merchandiser);
            	$('#seller').html(data.seller);
            	$('#productFactory').html(data.productFactory);
            	$('#quantity').html('<span style="color:red">'+ data.quantity +'</span>');
            	$('#clientName').html(data.clientName);
            	$('#clientBrand').html(data.clientBrand);
            	$('#clientNameCode').html(data.clientNameCode);
            	$('#district').html(data.district);
            	$('#productClient').html(data.productClient);
            	$('#dateOrder').html(data.dateOrder);
            	$('#dateExamine').html(data.dateExamine);
            	$('#dateDelivery').html(data.dateDelivery);
            	$('#placeDelivery').html(data.placeDelivery);
            	$('#payment').html(data.payment);
            
            	var orderOSDTO = data.orderOSDTO;
            	
            	var orderHardwareDTOs = data.orderHardwareDTOs;
            	var orderShellDTOs = data.orderShellDTOs;
            	var orderFittingDTOs = data.orderFittingDTOs;
            	var orderPackingDTOs = data.orderPackingDTOs;
            	
            	
            	//软件信息
            	if(orderOSDTO.iscustom == 1){
            		$('#iscustom').html("定制软件");
            	}else if(orderOSDTO.iscustom == 2){
            		$('#iscustom').html("公版软件");
            	}
            	if(orderOSDTO.isrepeat == 2){
            		$('#isrepeat').html("[翻单软件]");
            	}else{
            		$('#isrepeat').html("[新软件]");
            	} 
            	$('#gui').html(orderOSDTO.gui);
            	$('#uuid').html(orderOSDTO.uuid);
            	$('#langOs').html(orderOSDTO.langOs);
            	$('#langDefault').html(orderOSDTO.langDefault);
            	$('#timezone').html(orderOSDTO.timezone);
            	$('#bootLogo').html(orderOSDTO.bootLogo);
            	$('#preFile').html(orderOSDTO.preFile);
            	$('#preMap').html(orderOSDTO.preMap);
            	$("#version").html(orderOSDTO.version);
            	//备注
            	$('#orderSpare').html(data.orderSpare);
            	$('#description').html(data.description);
            	
            	if(orderHardwareDTOs.length > 0){
            		for(var i in orderHardwareDTOs){
            			if(orderHardwareDTOs[i].specification == 'undefined' || orderHardwareDTOs[i].specification == null){
                			orderHardwareDTOs[i].specification = "";
                		}
                		if(orderHardwareDTOs[i].supplier == 'undefined'|| orderHardwareDTOs[i].supplier == null){
                			orderHardwareDTOs[i].supplier = "";
                		}
                		if(orderHardwareDTOs[i].isNew != 'undefined'|| orderHardwareDTOs[i].isNew != null){
                			if(orderHardwareDTOs[i].isNew == 1 ){
                				orderHardwareDTOs[i].name = orderHardwareDTOs[i].name;
                			}
                			if(orderHardwareDTOs[i].isNew == 2){
                				if(data.pid == null){
                    				//新单 不显示新
                					orderHardwareDTOs[i].name = orderHardwareDTOs[i].name;
                    			}else{
	                				orderHardwareDTOs[i].name += isNewFlag;
                    			}
                			}
                		}
                		var content = '<tr>'+
                						'<td><span class="color069">'+ orderHardwareDTOs[i].name +'</span></td>'+
                						'<td><span class="color069">'+ orderHardwareDTOs[i].supplier +'</span></td>'+
                						'<td><span class="color069">'+ orderHardwareDTOs[i].specification +'</span></td>'+
                					'</tr>';
                		$('#materialBareMain').parent().append(content);
                	}
            	}
            	
            	if(orderShellDTOs.length > 0){
            		for(var i in orderShellDTOs){
                		var silkScreen = "";
                		var color = '';
                		if(orderShellDTOs[i].silkScreen == 2){
                			silkScreen = '有';
                		}else{
                			silkScreen = '无';
                		}
                		if(orderShellDTOs[i].color == 'undefined' || orderShellDTOs[i].color == null){
                			orderShellDTOs[i].color = "";
                		}
                		if(orderShellDTOs[i].craft == 'undefined' || orderShellDTOs[i].craft == null){
                			orderShellDTOs[i].craft = "";
                		}
                		if(orderShellDTOs[i].isNew != 'undefined'|| orderShellDTOs[i].isNew != null){
                			if(orderShellDTOs[i].isNew == 1 ){
                				orderShellDTOs[i].name = orderShellDTOs[i].name;
                			}
                			if(orderShellDTOs[i].isNew == 2){
                				if(data.pid == null){
                					//新单 不显示新
                					orderShellDTOs[i].name = orderShellDTOs[i].name;
                				}else{
                					orderShellDTOs[i].name += isNewFlag;
                				}
                				
                			}
                		}
                		var content = '<tr>'+
                						'<td>部件：<span class="color069">'+ orderShellDTOs[i].name +'</span></td>'+
                						'<td>颜色：<span class="color069 "  >'+ orderShellDTOs[i].color +'</span></td>'+
                						'<td>工艺：<span class="color069 " >'+ orderShellDTOs[i].craft +'</span></td>'+
                						'<td>丝印：<span class="color069">'+ silkScreen +'</span></td>'+
                					'</tr>';
                		$('#materialBare_shell').parent().append(content);
                	}
                	
            	}
            	
            	if(orderFittingDTOs.length > 0){
            		for(var i in orderFittingDTOs){
            			var specification = "";
                		var supplier = '';
                		if(orderFittingDTOs[i].specification == 'undefined' || orderFittingDTOs[i].specification == null){
                			orderFittingDTOs[i].specification = "";
                		}
                		if(orderFittingDTOs[i].supplier == 'undefined'|| orderFittingDTOs[i].supplier == null){
                			orderFittingDTOs[i].supplier = "";
                		}
                		if(orderFittingDTOs[i].isNew != 'undefined'|| orderFittingDTOs[i].isNew != null){
                			if(orderFittingDTOs[i].isNew == 1 ){
                				orderFittingDTOs[i].name = orderFittingDTOs[i].name;
                			}
                			if(orderFittingDTOs[i].isNew == 2){
                				if(data.pid == null){
                					//新单 不显示新
                					orderFittingDTOs[i].name = orderFittingDTOs[i].name;
                				}else{
                					orderFittingDTOs[i].name += isNewFlag;
                				}
                				
                			}
                		}
                		var content = '<tr>'+
                						'<td><span class="color069">'+ orderFittingDTOs[i].name +'</span></td>'+
                						'<td><span class="color069">'+ orderFittingDTOs[i].supplier +'</span></td>'+
                						'<td><span class="color069">'+ orderFittingDTOs[i].specification +'</span></td>'+
                					'</tr>';
                		$('#materialFitting').parent().append(content);
                	}
            	}
            	
            	
            	if(orderPackingDTOs.length > 0){
            		for(var i in orderPackingDTOs){
            			if(orderPackingDTOs[i].specification == 'undefined' || orderPackingDTOs[i].specification == null){
            				orderPackingDTOs[i].specification = "";
                		}
                		if(orderPackingDTOs[i].supplier == 'undefined'|| orderPackingDTOs[i].supplier == null){
                			orderPackingDTOs[i].supplier = "";
                		}
                		if(orderPackingDTOs[i].isNew != 'undefined'|| orderPackingDTOs[i].isNew != null){
                			if(orderPackingDTOs[i].isNew == 1 ){
                				orderPackingDTOs[i].name = orderPackingDTOs[i].name;
                			}
                			if(orderPackingDTOs[i].isNew == 2){
                				if(data.pid == null){
                					//新单 不显示新
                					orderPackingDTOs[i].name = orderPackingDTOs[i].name;
                				}else{
                					orderPackingDTOs[i].name += isNewFlag;
                				}
                				
                			}
                		}
                		var content = '<tr>'+
                						'<td><span class="color069">'+ orderPackingDTOs[i].name +'</span></td>'+
                						'<td><span class="color069">'+ orderPackingDTOs[i].supplier +'</span></td>'+
                						'<td><span class="color069">'+ orderPackingDTOs[i].specification +'</span></td>'+
                					'</tr>';
                		$('#materialPacking').parent().append(content);
                	}
            	}
            	
            }
		})
		
		$('#show_isNeworder').off('click').on('click',function(){
			var orderId = $('#show_isNeworder').data('id');
			var orderNo = $('#show_isNeworder').data('orderNo');
			var url = "${ctx}/order/getPage?id="+orderId;
		  	var title = "订单"+orderNo;
			window.parent.addTab({
				url : url,
				title : title,
			});
		})
		$('#orderInfoAltList').off('click').on('click',function(){
			parent.$.modalDialog({
				title : '订单变更记录',
				width : 800,
				height : 600,
				href : '${ctx}/orderAlt/orderAltPage?orderId='+orderId,
				buttons : [ {
					text : '关闭',
					handler : function() {
						parent.$.modalDialog.handler.dialog('close');
					}
				} ]
			});
		})
		$("#orderSubmitToSeller").off('click').on('click',function(){
			parent.$.modalDialog({
				title : '审核提交',
				width : 300,
				height : 160,
				href : '${ctx}/order/submitToSeller',
				buttons : [ {
					text : '确定',
					handler : function() {
						var seller = parent.$('#sellerName');
						
						if(seller.data('sellerId') != 'undefined' && seller.val() != null && seller.val().trim() != ""){
							var isExit = false;
							$.ajax({
								url:"${ctx}/user/listOrderAuditor?username="+encodeURIComponent(seller.val().trim()),
								type:"POST",
								dataType: "json",
								async: false,
					            contentType: "application/json; charset=utf-8", 
					            success: function(data){
					            	if(data.length > 0){
					            		$.ajax({
											url:"${ctx}/order/addSeller?ordrId="+ orderId+"&sellerId="+seller.data('sellerId')+"&seller="+encodeURIComponent(seller.val().trim()),
											type:"POST",
											dataType: "json",
								            contentType: "application/json; charset=utf-8", 
								            success: function(data){
								            	if (data.success) {
													parent.$.messager.alert('提示', data.msg, 'info');
													var parent_tabs = parent.$('#index_tabs');
								            		var index = parent_tabs.tabs('getTabIndex', parent.$('#index_tabs').tabs('getSelected'));
								 					var tab = parent_tabs.tabs('getTab', index);
								 					if (tab.panel('options').closable) {
								 						parent_tabs.tabs('close', index);
								 					}
								 					parent_tabs.tabs('select', "订单信息");
								 					parent_tabs.tabs('update', {
														tab : parent_tabs.tabs('getSelected'), //获取当前被选中的页面
														options : {
															//Title : '新标题',
															}
														}
													);
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
		})
		
		$("#orderReviewBtn").off('click').on('click',function(){
			parent.$.modalDialog({
				title : '订单审核',
				width : 300,
				height : 160,
				href : '${ctx}/order/orderReviewPage',
				buttons : [ {
					text : '提交',
					handler : function() {
						var state = parent.$("input[name='orderPassOrNo']:checked").val();
						if(state != 'undefined'){
							$.ajax({
					         	type: 'POST',
					            url: "${ctx}/order/orderReview?orderId=" + orderId +"&state=" + state ,
					            dataType: "json",
					            
					            contentType: "application/json; charset=utf-8",
								success : function(data) {
									if (data.success) {
										parent.$.messager.alert('提示', data.msg, 'info');
										var parent_tabs = parent.$('#index_tabs');
					            		var index = parent_tabs.tabs('getTabIndex', parent.$('#index_tabs').tabs('getSelected'));
					 					var tab = parent_tabs.tabs('getTab', index);
					 					if (tab.panel('options').closable) {
					 						parent_tabs.tabs('close', index);
					 					}
					 					parent_tabs.tabs('select', "订单信息");
					 					parent_tabs.tabs('update', {
											tab : parent_tabs.tabs('getSelected'), //获取当前被选中的页面
											options : {
												//Title : '新标题',
												}
											}
										);
									}else{
										parent.$.messager.alert('提示', data.msg, 'info');
									}
								}
							})
							parent.$.modalDialog.handler.dialog('close');
						}
					}
				} ]
			});
		})
	});
	
</script>
<div>
	<!-- 基础信息 -->
	<table border="0" cellpadding="0" cellspacing="0" class="ordertable">
        <tr class="tabletitle">
            <td colspan="8">基础信息：<span id="isNeworder" class="color_f60"></span>&nbsp;&nbsp;&nbsp;<span ><a id="orderInfoAltList" class="menu_hidden" style="cursor: pointer;text-decoration: underline;">[变更记录]</a></span>&nbsp;&nbsp;&nbsp;<span><a id="orderPrint" href="${ctx}/order/printPage?id=${orderId}" target="_blank">[打印]</a></span> </td>
        </tr>
        <tr >
            <td class="lefttd">翻单单号：</td>
            <td  class="righttd" colspan="7"><div id="show_isNeworder" style="cursor: pointer;text-decoration: underline;"> </div></td>
        </tr>
        <tr>
            <td class="lefttd" width="10%">订单号：</td>
            <td class="righttd" width="15%"><div class="color069" id="orderNo"> </div></td>
            <td class="lefttd" width="10%">跟单名称：</td>
            <td class="righttd" width="15%"><div class="color069" id="merchandiser"> </div></td>
            <td class="lefttd" width="10%">业务名称：</td>
            <td class="righttd" width="15%"><div class="color069" id="seller"> </div></td>
            <td class="lefttd" width="10%"> 工厂机型：</td>
            <td class="righttd" width="15%"><div class="color069" id="productFactory"> </div></td>
        </tr>
        <tr>
            <td class="lefttd">订单数量： </td>
            <td class=""><div class="color069" id="quantity"> </div></td>
            <td class="lefttd">客户名称：</td>
            <td class="righttd"><div class="color069" id="clientName"> </div></td>
            <td class="lefttd">客户铭牌：</td>
            <td class="righttd"><div class="color069" id="clientBrand"> </div></td>
            <td class="lefttd">客户编码：</td>
            <td class="righttd"><div class="color069" id="clientNameCode"> </div></td>
        </tr>
        <tr>
            <td class="lefttd">所在国家：</td>
            <td class="righttd"><div class="color069" id="district"> </div></td>
       
            <td class="lefttd">客户型号：</td>
            <td class="righttd"><div class="color069" id="productClient"> </div></td>        
            <td class="lefttd">下单日期：</td>
            <td class="righttd"><div class="color069"  id="dateOrder"> </div></td>
            <td class="lefttd">验货日期：</td>
            <td class="righttd"><div class="color069" id="dateExamine"> </div></td>
        </tr>
        <tr>
            <td class="lefttd">交货日期:</td>
            <td class="righttd"><div class="color069" id="dateDelivery" > </div></td>
            <td class="lefttd">交货地点：</td>
            <td class="righttd"><div  class="color069 " id="placeDelivery"/></div></td>
        	<td class="lefttd">付款方式：</td>
            <td class="righttd"><div type="text" class="color069 "   id="payment" /></div></td>
        </tr>
    </table>
    
    <!-- 外壳工艺 -->
    <table border="0" cellpadding="0" cellspacing="0" class="ordertable" >
        <tr class="tabletop">
            <td colspan="4">外壳工艺：</td>
        </tr>
         <tr id="materialBare_shell">
        </tr>
    </table>
    <table border="0" cellpadding="0" cellspacing="0" class="ordertable">
        <tr class="tabletitle">
            <td colspan="8">软件信息： </td>
        </tr>
        <tr>
        	<td class="lefttd">软件：</td>
            <td class="righttd">
            	<div  class="color_f60" id="isrepeat"></div>
            </td>
            
            <td class="lefttd">是否定制：</td>
            <td colspan="1">
            	<div  class="color069" id="iscustom"></div>
            </td>
             <td class="lefttd">软件版本号：</td>
             <td class="righttd">
            	<div  class="color_f60" id="version"></div>
            </td>
        </tr>
        <tr>            
        	<td class="lefttd">GUI：</td>
            <td class="righttd"><div class="color069" id="gui"> </div></td>
            <td class="lefttd"> UUID号 ：
            <td class="righttd"><div class="color069 " id="uuid"/></div></td>
            <td class="lefttd">系统语言： </td>
            <td class=""><div class="color069" id="langOs"> </div></td>
            <td class="lefttd">默认语言：</td>
            <td class="righttd"><div class="color069" id="langDefault" > </div></td>
        </tr>
        <tr>
            <td class="lefttd">地区时间：</td>
            <td class="righttd"><div class="color069" id="timezone"> </div></td>
           <td class="lefttd">开机画面：</td>
            <td class="righttd"><div  class="color069 " id="bootLogo" /></div></td>
            <td class="lefttd">预存文件：</td>
            <td class="righttd"><div  class="color069 " id="preFile" /></div></td>
            <td class="lefttd">预存地图：</td>
            <td class="righttd"><div  class="color069 " id="preMap" /></div></td>
        </tr>
    </table>
    
    <!-- 裸机包装 -->
     <table border="0" cellpadding="0" cellspacing="0" class="bordertable" >
        <tr class="tabletitle">
            <td colspan="3">硬件部件：</td>
        </tr>
		<tr id="materialBareMain"  class="tabletop">
            <td width="25%">物料名称</td>
            <td width="30%">供应商</td>
            <td width="45%">规格型号</td>
        </tr>
        </table>
     
    
    <!-- 配件信息-->
     <table border="0" cellpadding="0" cellspacing="0" class="bordertable">
        <tr class="tabletitle">
            <td colspan="3">配件部分： </td>
        </tr>
        <tr id="materialFitting"  class="tabletop">
            <td width="25%">物料名称</td>
            <td width="30%">供应商</td>
            <td width="45%">规格型号</td>
        </tr>
        
    </table>
    
      <!-- 包材信息-->
     <table border="0" cellpadding="0" cellspacing="0" class="bordertable">
        <tr class="tabletitle">
            <td colspan="3">包材部分： </td>
        </tr>
        <tr id="materialPacking"  class="tabletop">
            <td width="25%">物料名称</td>
            <td width="30%">供应商</td>
            <td width="45%">规格型号</td>
        </tr>
        
    </table>
      <!-- 备品-->
     <table border="0" cellpadding="0" cellspacing="0" class="bordertable">
        <tr class="tabletitle">
            <td colspan="3">订单备品： </td>
        </tr>
        <tr>
        	<td colspan="3"><pre readonly="readonly" id="orderSpare" class="textareacss" cols="8"></pre></td>
        </tr>
    </table>
     <!-- 备品说明-->
     <table border="0" cellpadding="0" cellspacing="0"  class="bordertable">
        <tr class="tabletitle">
            <td colspan="3">备注说明： </td>
        </tr>
        <tr>
        	<td colspan="3"><pre readonly="readonly" id="description" class="textareacss" cols="8"></pre></td>
        </tr>
	</table>
</div>
<div align="center">
	<button id="orderSubmitToSeller" class='btn-default menu_hidden'>提交审核</button> 
	<%-- <c:if test="${fn:contains(sessionInfo.resourceList, '/order/review')}">
		<button id="orderReviewBtn" class='btn-default menu_hidden'>审核</button> 
	</c:if> --%>
	<c:choose>
		<c:when test="${fn:contains(sessionInfo.resourceList, '/order/allReview')}">
			<button id="orderReviewBtn" class='btn-default menu_hidden'>审核</button> 
		</c:when>
		<c:otherwise>
			<c:if test="${sessionInfo.id == sellerId}">
				<c:if test="${fn:contains(sessionInfo.resourceList, '/order/review')}">
					<button id="orderReviewBtn" class='btn-default menu_hidden'>审核</button> 
				</c:if>
			</c:if>
		</c:otherwise>
	</c:choose>
	
</div>