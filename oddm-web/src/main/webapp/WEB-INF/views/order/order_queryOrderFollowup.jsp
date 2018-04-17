<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>

	.ordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:left;}
	.tabletitle{text-align:left; background-color:#c8d7e1;color:#333;font-size:1.4em; font-weight:500;}
	.tabletop{text-align:left; background-color:#DCE0E2;color:#333;font-size:1.2em; font-weight:500;}
	.ordertable tr td{padding:10px 0 10px 6px;}
	.inputs{border:1px solid #CCC;width:90%;padding:5px 0 2px 4px;font-size:12px;color:#999;border-radius: 4px;}
	.lefttd{width:85px;}
	.lefttdRed{width:85px;color: #f60}
    .lefttd90{width:90px; text-align:right;}
    .righttd{color:#069;}
    .bordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:center;border-collapse:collapse;}
	.bordertable td{border:1px solid #ccc;padding:10px 0 10px 6px;height:20px;}
	.textareacss{width:100%;background:none;border: 0px}
	.choose{color: #06c;cursor: pointer;}
	.color_f60 {color: #f60;}
	pre {white-space: pre-wrap;word-wrap: break-word;text-align: left;margin: 0}
</style>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
	var orderId = "${orderId}";
	var orderFollowupId ;
	$(function() {
		 $.ajax({
			url:"${ctx}/order/getByOrderInfo?id="+ orderId,
			type:"GET",
			dataType: "json",
            contentType: "application/json; charset=utf-8", 
            success: function(data){
            	console.log(data)
            	if(data != null ){
            		if(data.orderInfoDTO != null){
            			
            			$('#clientName').html(data.orderInfoDTO.clientName);
                    	$('#orderNo').html(data.orderInfoDTO.orderNo);
                    	$('#seller').html(data.orderInfoDTO.seller);
                    	$('#merchandiser').html(data.orderInfoDTO.merchandiser);
                    	$('#productFactory').html(data.orderInfoDTO.productFactory);
                    	$('#productClient').html(data.orderInfoDTO.productClient);
                    	$('#quantity').html(data.orderInfoDTO.quantity);
                    	$('#plan').html(data.plan);
                    	
                    	$('#dateDelivery').html(data.dateClient);
                    	orderFollowupId = data.id;
                    	if(data.bootLogo == 3){
                    		$('#bootLogo').html('OK');
                    	}
        				if(data.bootLogo == 2){
        					$('#bootLogo').html('NO');
                    	}
        				if(data.bootLogo == 1){
        					$('#bootLogo').html('N/A');
        				}
        				
        				if(data.uuid == 3){
                    		$('#uuid').html('OK');
                    	}
        				if(data.uuid == 2){
        					$('#uuid').html('NO');
                    	}
        				if(data.uuid == 1){
        					$('#uuid').html('N/A');
        				}
        				
        				
        				if(data.specification == 3){
                    		$('#specification').html('OK');
                    	}
        				if(data.specification == 2){
        					$('#specification').html('NO');
                    	}
        				if(data.specification == 1){
        					$('#specification').html('N/A');
        				}
        				
        				
        				if(data.carton == 3){
                    		$('#carton').html('OK');
                    	}
        				if(data.carton == 2){
        					$('#carton').html('NO');
                    	}
        				if(data.carton == 1){
        					$('#carton').html('N/A');
        				}
        				
        				if(data.map == 3){
                    		$('#map').html('OK');
                    	}
        				if(data.map == 2){
        					$('#map').html('NO');
                    	}
        				if(data.map == 1){
        					$('#map').html('N/A');
        				}
        				
        				if(data.shell == 3){
                    		$('#shell').html('OK');
                    	}
        				if(data.shell == 2){
        					$('#shell').html('NO');
                    	}
        				if(data.shell == 1){
        					$('#shell').html('N/A');
        				}
        				
        				if(data.membrane == 3){
                    		$('#membrane').html('OK');
                    	}
        				if(data.membrane == 2){
        					$('#membrane').html('NO');
                    	}
        				if(data.membrane == 1){
        					$('#membrane').html('N/A');
        				}
        				
        				if(data.fitting == 3){
                    		$('#fitting').html('OK');
                    	}
        				if(data.fitting == 2){
        					$('#fitting').html('NO');
                    	}
        				if(data.fitting == 1){
        					$('#fitting').html('N/A');
        				}
        				
        				if(data.sorfware == 3){
                    		$('#sorfware').html('OK');
                    	}
        				if(data.sorfware == 2){
        					$('#sorfware').html('NO');
                    	}
        				if(data.sorfware == 1){
        					$('#sorfware').html('N/A');
        				}
        				
        				if(data.hardware == 3){
                    		$('#hardware').html('OK');
                    	}
        				if(data.hardware == 2){
        					$('#hardware').html('NO');
                    	}
        				if(data.hardware == 1){
        					$('#hardware').html('N/A');
        				}
        				
        				if(data.colorbox == 3){
                    		$('#colorbox').html('OK');
                    	}
        				if(data.colorbox == 2){
        					$('#colorbox').html('NO');
                    	}
        				if(data.colorbox == 1){
        					$('#colorbox').html('N/A');
        				}
        				
        				if(data.inspection == 3){
                    		$('#inspection').html('OK');
                    	}
        				if(data.inspection == 2){
        					$('#inspection').html('NO');
                    	}
        				if(data.inspection == 1){
        					$('#inspection').html('N/A');
        				}
        				
        				if(data.preFile == 3){
                    		$('#preFile').html('OK');
                    	}
        				if(data.preFile == 2){
        					$('#preFile').html('NO');
                    	}
        				if(data.preFile == 1){
        					$('#preFile').html('N/A');
        				}
                    	
        				if(data.tags == 3){
                    		$('#tags').html('OK');
                    	}
        				if(data.tags == 2){
        					$('#tags').html('NO');
                    	}
        				if(data.tags == 1){
        					$('#tags').html('N/A');
        				}
        				
        				if(data.packing == 3){
                    		$('#packing').html('OK');
                    	}
        				if(data.packing == 2){
        					$('#packing').html('NO');
                    	}
        				if(data.packing == 1){
        					$('#packing').html('N/A');
        				}
        				
        				if(data.agency == 3){
                    		$('#agency').html('OK');
                    	}
        				if(data.agency == 2){
        					$('#agency').html('NO');
                    	}
        				if(data.agency == 1){
        					$('#agency').html('N/A');
        				}
        				
        				if(data.payment == 3){
                    		$('#payment').html('OK');
                    	}
        				if(data.payment == 2){
        					$('#payment').html('NO');
                    	}
        				if(data.payment == 1){
        					$('#payment').html('N/A');
        				}
        				
        				$('#dateFactory').html(data.dateFactory);
        				//$('#dateShipment').html(data.dateShipment)
        				//$('#shipmentNo').html(data.shipmentNo)
        				//$('#shipmentTotal').html(data.shipmentTotal)
        				$('#status').html(data.status)
        				$('#description').html(data.description)
        				
						$('#shipment').html(data.shipment == null ? '' : data.shipment)
        				
        				if(data.level == 3){
                    		$('#level').html('非常严重');
                    	}
        				if(data.level == 2){
        					$('#level').html('严重');
                    	}
        				if(data.level == 1){
        					$('#level').html('一般');
        				}
        				$('#dutyOfficer').html(data.dutyOfficer == null ? '' : data.dutyOfficer)
        				$('#statusAbnormal').html(data.statusAbnormal == null ? '' : data.statusAbnormal)
        				$('#solution').html(data.solution == null ? '' : data.solution)
        				/* var total = 0;
        				if(data.orderFollowupOutDTOs != null && data.orderFollowupOutDTOs.length > 0){
        					
        					for(var i in data.orderFollowupOutDTOs){
        						var shipmentDate = data.orderFollowupOutDTOs[i].shipmentDate == null ? '' :data.orderFollowupOutDTOs[i].shipmentDate;
            					var shipmentNo = data.orderFollowupOutDTOs[i].shipmentNo == null ? '' :data.orderFollowupOutDTOs[i].shipmentNo;
            					var $content = $('<tr class="orderFollowupOut">'+
            							'<td class="lefttd" width="10%">出货日期：</td>'+
            					        '<td class="righttd" width="15%">'+shipmentDate+'</td>'+
            					     	'<td class="lefttd" width="10%">出货数量：</td>'+
            					        '<td class="righttd" width="15%">'+ shipmentNo +'</td>'+
            					        '<td class="lefttd" width="10%"></td>'+
            					        '<td class="" width="15%"></td>'+
            					        '<td class="lefttd" width="10%"></td>'+
            					        '<td class="" width="15%"></td>'+
            				        '</tr>');
            					$('#orderFollowupOutTable').append($content);
            					total += shipmentNo;
        					}
        					
        				}
        				$('#shipmentTotal').html(total) */
            		}else{
                		parent.$.messager.alert('提示', '当前单没有跟单信息！', 'info');
                		 var parent_tabs = parent.$('#index_tabs');
	            		 var index = parent_tabs.tabs('getTabIndex', parent.$('#index_tabs').tabs('getSelected'));
	 					 var tab = parent_tabs.tabs('getTab', index);
	 					 if (tab.panel('options').closable) {
	 						parent_tabs.tabs('close', index);
	 					 }
                	}
            	}else{
            		parent.$.messager.alert('提示', '当前单没有跟单信息！', 'info');
            		 var parent_tabs = parent.$('#index_tabs');
            		 var index = parent_tabs.tabs('getTabIndex', parent.$('#index_tabs').tabs('getSelected'));
 					 var tab = parent_tabs.tabs('getTab', index);
 					 if (tab.panel('options').closable) {
 						parent_tabs.tabs('close', index);
 					 }
            	}
            }
		}) 
		$('#orderFollowupAltList').off('click').on('click',function(){
			parent.$.modalDialog({
				title : '跟单记录',
				width : 800,
				height : 600,
				href : '${ctx}/orderFollowupAlt/orderFollowupAltPage?orderFollowupId='+orderFollowupId,
				buttons : [ {
					text : '关闭',
					handler : function() {
						parent.$.modalDialog.handler.dialog('close');
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
            <td colspan="8">基础信息：&nbsp;&nbsp;&nbsp;<a id="orderFollowupAltList" class="" style="cursor: pointer;text-decoration: underline;color: #069">[变更记录 ]</a>  </td>
        </tr>
        <tr>
         	<td class="lefttd" width="10%">客户名称：</td>
            <td class="righttd" width="15%"><div class="color069" id="clientName"> </div></td>
            <td class="lefttd" width="10%">订单编号：</td>
            <td class="righttd" width="15%"><div class="color069" id="orderNo"> </div></td>
            <td class="lefttd" width="10%">销售员：</td>
            <td class="righttd" width="15%"><div class="color069" id="seller"> </div></td>
            <td class="lefttd" width="10%"> 跟单员：
            <td class="righttd" width="15%"><div class="color069" id="merchandiser"> </div></td>
        </tr>
        <tr>
            <td class="lefttd">产品型号：</td>
            <td class="righttd"><div class="color069" id="productFactory"> </div></td>
            <td class="lefttd">客户型号：</td>
            <td class="righttd"><div class="color069" id="productClient"> </div></td>
             <td class="lefttd">订单数量/pcs： </td>
            <td class=""><div class="color_f60" id="quantity"> </div></td>       </tr>
       
    </table>
    
    <table border="0" cellpadding="0" cellspacing="0" class="ordertable">
        <tr class="tabletitle">
            <td colspan="8">跟单情况：</td>
        </tr>
        <tr>            
        	<td class="lefttd">开机画面：</td>
            <td class="righttd">
            	<div id="bootLogo"></div>       	
            </td>
            <td class="lefttd"> UUID号/其他 ：
            <td class="righttd">
            	<div id="uuid"></div>       	
			</td>
            <td class="lefttd">说明书和卡类： </td>
            <td class="righttd">
            	<div id="specification"></div>       
            </td>
            <td class="lefttd">卡通箱：</td>
            <td class="righttd">
            	<div id="carton"></div>       
			</td>
        </tr>
        <tr>            
        	<td class="lefttd">地图：</td>
            <td class="righttd">
            	<div id="map"></div>   
            </td>
            <td class="lefttd"> 机身外壳：
            <td class="righttd">
            	<div id="shell"></div>   
			</td>
            <td class="lefttd">贴膜类： </td>
            <td class="righttd">
            	<div id="membrane"></div>   
            </td>
            <td class="lefttd">配件：</td>
            <td class="righttd">
            	<div id="fitting"></div>   
			</td>
        </tr>
         <tr>            
        	<td class="lefttd">软件升级：</td>
            <td class="righttd">
           		<div id="sorfware"></div>   
            </td>
            <td class="lefttd">机身硬件：</td>
            <td class="righttd">
           		<div id="hardware"></div>   
            </td>
            <td class="lefttd">彩盒：
            <td class="righttd">
            	<div id="colorbox"></div>   
			</td>
            <td class="lefttd">验货： </td>
            <td class="righttd">
            	<div id="inspection"></div>   
            </td>
          </tr>
          <tr> 
            <td class="lefttd">预存文件：</td>
            <td class="righttd">
            	<div id="preFile"></div>   
			</td>
                   
        	<td class="lefttd">贴纸类：</td>
            <td class="righttd">
            	<div id="tags"></div>   
            </td>
            <td class="lefttd">包装袋：
            <td class="righttd">
            	<div id="packing"></div>   
			</td>
            <td class="lefttd">代付： </td>
            <td class="righttd">
            	<div id="agency"></div>   	
            </td>
          </tr>
          <tr>  
           	<td class="lefttd">付款：</td>
            <td class="righttd">
            	<div id="payment"></div> 
			</td>
        </tr>
        <tr>
          	<td class="lefttd" width="10%">方案：</td>
            <td class="righttd" width="15%"><div class="color069" id="plan"> </div></td>
            <td class="lefttd" width="10%">客户交期:</td>
            <td class="righttd" width="15%"><div class="color069" id="dateDelivery" > </div></td>
         	<td class="lefttd" width="10%">计划回复交期：</td>
            <td class="righttd" width="15%"><div class="color069" id="dateFactory"> </div></td>
            <td class="lefttd" width="10%"></td>
            <td class="righttd" width="15%"></td>
        </tr>
        
        <tr>
            <td class="lefttd" width="10%">正常状态更新：</td>
            <td class="righttd" colspan="3"><pre class="color069" id="status"> </pre></td>
           	<td class="lefttd">出货数量和日期：</td>
            <td class="righttd" colspan="3"><pre class="color069" id="shipment" ></pre></td>
        </tr>
        <tr>
            <td class="lefttdRed" width="10%">导常问题之责任部门或责任人：</td>
            <td class="righttd" colspan="3"><pre class="color069" id="dutyOfficer"> </pre></td>
           	<td class="lefttdRed">异常等级区分：</td>
            <td class="righttd" colspan="3"><pre class="color069" id="level" ></pre></td>
        </tr>
        <tr>
            <td class="lefttdRed" width="10%">异常状态记录：</td>
            <td class="righttd" colspan="3"><pre class="color069" id="statusAbnormal"> </pre></td>
           	<td class="lefttdRed">是否定出解决方案：</td>
            <td class="righttd" colspan="3"><pre class="color069" id="solution" ></pre></td>
        </tr>
        <tr>
         	<td class="lefttd" width="10%">备注：</td>
            <td class="righttd" colspan="3"><pre class="color069" id="description"> </pre></td>
        </tr>
    </table>
   <!--  <table border="0" cellpadding="0" cellspacing="0" class="ordertable" id="orderFollowupOutTable" style="margin-bottom: 0px">
        <tr class="tabletitle">
            <td colspan="8">出货记录：</td>
        </tr>
        
    </table>
    <table border="0" cellpadding="0" cellspacing="0" class="ordertable" >
        <tr class="">
            <td class="lefttd" width="10%">出货总数：</td>
            <td class="righttd" width="15%"><div id="shipmentTotal"></div></td>
			<td class="lefttd" width="10%"></td>
            <td class="righttd" width="15%"></td>
			<td class="lefttd" width="10%"></td>
            <td class="righttd" width="15%"></td>
			<td class="lefttd" width="10%"></td>
            <td class="righttd" width="15%"></td>
        </tr>
	</table> -->
</div>