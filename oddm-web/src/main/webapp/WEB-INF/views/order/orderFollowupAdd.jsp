<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>
	.ordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:left;}
	.tabletitle{text-align:left; background-color:#c8d7e1;color:#333;font-size:1.4em; font-weight:500;}
	.tabletop{text-align:left; background-color:#DCE0E2;color:#333;font-size:1.2em; font-weight:500;}
	.ordertable tr td{padding:10px 0 10px 6px;}
	.inputs{border:1px solid #CCC;width:90%;padding:5px 0 2px 4px;font-size:12px;color:#999;border-radius: 4px}
	.lefttd{width:85px;}
	.lefttdRed{width:85px;color: #f60}
    .righttd{color:#069;}
    .bordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:center;border-collapse:collapse;}
	.bordertable td{border:1px solid #ccc;padding:10px 0 10px 6px;height:20px;}
	.textareacss{border:1px solid #CCC;width:96%;padding:5px 0 2px 4px;font-size:12px;color:#999;border-radius: 4px;height: 35px}
	.textareacssPeople{border:1px solid #CCC;width:60%;padding:5px 0 2px 4px;font-size:12px;color:#999;border-radius: 4px;height: 35px}
	.choose{color: #06c;cursor: pointer;}
	.color_f60 {color: #f60;}
	.tip{height: 16px;width: 16px;display: inline-block;padding: 0px 4px;cursor: pointer;}
	.btn-default {border: 1px solid #DDDDDD;background: white;padding: 10px 18px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #333;}
	.btn-default:hover {border: 1px solid #DDDDDD;background: #04B5F9;padding: 10px 18px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #FFF;}
</style>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		var orderNo = "${orderNo}";
		 $.ajax({
			url:"${ctx}/orderFollowup/getOrderInfoByOrderNo?orderNo="+ orderNo,
			type:"GET",
			dataType: "json",
            contentType: "application/json; charset=utf-8", 
            success: function(data){
            	$('#clientName').html(data.clientName);
            	$('#orderNo').html(data.orderNo);
            	$('#orderNo').data('orderId',data.id)
            	$('#seller').html(data.seller);
            	$('#merchandiser').html(data.merchandiser);
            	$('#productFactory').html(data.productFactory);
            	$('#productClient').html(data.productClient);
            	
            	$('#quantity').html(data.quantity);
            	/* 
				$('#shipmentTotal').html(0)
				$("#shipmentNo").numberbox({  
					  "onChange":function(){  
					      //var shipmentTotal = parseInt(data.shipmentTotal);
					     // console.log(shipmentTotal)
						  var shipmentNo = parseInt($('#shipmentNo').numberbox('getValue'));
					      shipmentTotal = 0+shipmentNo;
					      $('#shipmentTotal').html(shipmentTotal)
					  }  
				}); 
				 */
            }
		}) 
		
		$('#addOrderFollowupBtn').off('click').on('click',function(){
			
			var isValid = $('#orderFollowupForm').form('validate');
			console.log("orderFollowupForm 验证"+isValid)
			if(!isValid){
				return;
			}
			
			//数据校验
        	var plan = $('#plan').val();
        	if(plan.length > 64){
				parent.$.messager.alert('提示', '方案不能超过64个字符！', 'info');
		        return false;
			}
        	var dateClient = $('#dateClient').datebox('getValue');
        	var dateFactory = $('#dateFactory').datebox('getValue');
			var status = $('#status').val()
			if(status.length > 200){
				parent.$.messager.alert('提示', '更新状态不能超过200个字符！', 'info');
		        return false;
			}
			var description = $('#description').val()
			if(description.length > 200){
				parent.$.messager.alert('提示', '备注不能超过200个字符！', 'info');
		        return false;
			}
			
			var bootLogo = $("input[name=bootLogo]:checked").val()
			if(bootLogo == null){
				parent.$.messager.alert('提示', '请选择开机画面状态！', 'info');
		        return false;
			}
           	var uuid = $("input[name=uuid]:checked").val()
           	if(uuid == null){
				parent.$.messager.alert('提示', '请选择UUID状态！', 'info');
		        return false;
			}
           	var specification = $("input[name=specification]:checked").val()
           	if(specification == null){
				parent.$.messager.alert('提示', '请选择说明书和卡类状态！', 'info');
		        return false;
			}
           	var carton = $("input[name=carton]:checked").val()
        	if(carton == null){
				parent.$.messager.alert('提示', '请选择卡通箱状态！', 'info');
		        return false;
			}
           	var map = $("input[name=map]:checked").val()
        	if(map == null){
				parent.$.messager.alert('提示', '请选择地图状态！', 'info');
		        return false;
			}
           	var shell = $("input[name=shell]:checked").val()
        	if(shell == null){
				parent.$.messager.alert('提示', '请选择机身外壳状态！', 'info');
		        return false;
			}
           	var membrane = $("input[name=membrane]:checked").val()
        	if(membrane == null){
				parent.$.messager.alert('提示', '请选择贴膜类状态！', 'info');
		        return false;
			}
           	var fitting = $("input[name=fitting]:checked").val()
        	if(fitting == null){
				parent.$.messager.alert('提示', '请选择配件状态！', 'info');
		        return false;
			}
           	var sorfware = $("input[name=sorfware]:checked").val()
        	if(sorfware == null){
				parent.$.messager.alert('提示', '请选择软件升级状态！', 'info');
		        return false;
			}
           	var hardware = $("input[name=hardware]:checked").val()
        	if(hardware == null){
				parent.$.messager.alert('提示', '请选择机身硬件状态！', 'info');
		        return false;
			}
           	var colorbox = $("input[name=colorbox]:checked").val();
        	if(colorbox == null){
				parent.$.messager.alert('提示', '请选择彩盒状态！', 'info');
		        return false;
			}
           	var inspection = $("input[name=inspection]:checked").val()
        	if(inspection == null){
				parent.$.messager.alert('提示', '请选择验货状态！', 'info');
		        return false;
			}
           	var preFile = $("input[name=preFile]:checked").val()
        	if(preFile == null){
				parent.$.messager.alert('提示', '请选择预存文件状态！', 'info');
		        return false;
			}
           	var tags = $("input[name=tags]:checked").val()
        	if(tags == null){
				parent.$.messager.alert('提示', '请选择贴纸类状态！', 'info');
		        return false;
			}
           	var packing = $("input[name=packing]:checked").val()
        	if(packing == null){
				parent.$.messager.alert('提示', '请选择包装类状态！', 'info');
		        return false;
			}
           	var agency= $("input[name=agency]:checked").val()
        	if(agency == null){
				parent.$.messager.alert('提示', '请选择代付状态！', 'info');
		        return false;
			}
           	var payment = $("input[name=payment]:checked").val()
        	if(payment == null){
				parent.$.messager.alert('提示', '请选择付款状态！', 'info');
		        return false;
			}
           	var orderId = $('#orderNo').data('orderId');
           	
           	//出货记录
          /*  	var orderFollowupOuts = [];
           	$('#orderFollowupOutTable').find('.orderFollowupOut').each(function(){
           		var tdValue = $(this).children();
           		var shipmentDate = tdValue.eq(1).find('input').datebox('getValue');
           		var shipmentNo =  tdValue.eq(3).find('input').numberbox('getValue');
           		var orderFollowupOut = {
           				shipmentDate : shipmentDate,
           				shipmentNo : shipmentNo
           		}
           		if(shipmentDate != "" && shipmentNo!= ""){
           			orderFollowupOuts.push(orderFollowupOut);
           		}
           	}) */
           	
           	var statusAbnormal = typeof($('#statusAbnormal').val()) == 'undefined' ? null : $('#statusAbnormal').val().trim();
        	var dutyOfficer = typeof($('#dutyOfficer').val()) == 'undefined' ? null : $('#dutyOfficer').val().trim();
        	var solution = typeof($('#solution').val()) == 'undefined' ? null : $('#solution').val().trim();
        	var shipment = typeof($('#shipment').val()) == 'undefined' ? null : $('#shipment').val().trim();
        	
        	var level = typeof($("input[name=level]:checked").val()) == 'undefined' ? null : $("input[name=level]:checked").val();
           	var param = {
					//基础
					/* merchandiser : merchandiser, */
					plan : plan,
					dateClient : dateClient ,
					dateFactory : dateFactory,
					//shipmentDate : shipmentDate,
					//shipmentNo : shipmentNo,
					shipmentTotal :  $('#shipmentTotal').html(),
					status : status,
					description : description,
					bootLogo : bootLogo,
					uuid : uuid,
					specification : specification,
					carton : carton,
					map : map,
					shell : shell,
					membrane : membrane,
					fitting : fitting,
					sorfware : sorfware,
					hardware : hardware,
					colorbox : colorbox,
					inspection : inspection,
					preFile : preFile,
					tags : tags,
					packing : packing,
					agency : agency,
					payment : payment,
					orderId : orderId,
					statusAbnormal : statusAbnormal,
					dutyOfficer : dutyOfficer,
					solution : solution,
					shipment : shipment,
					level : level
					//orderFollowupOutCommands : orderFollowupOuts
			}
           console.log(param)
           	progressLoad();
			$.ajax({
	         	type: 'POST',
	             url: "${ctx}/orderFollowup/add" ,
	             data: JSON.stringify(param) ,
	             dataType: "json",
	             contentType: "application/json; charset=utf-8",
	             async: false,
	             success: function(data){
	            	 if(data.success){
	            		 parent.$.messager.alert('提示', '添加成功');
	            		 var parent_tabs = parent.$('#index_tabs');
	            		 var index = parent_tabs.tabs('getTabIndex', parent.$('#index_tabs').tabs('getSelected'));
	 					 var tab = parent_tabs.tabs('getTab', index);
	 					 if (tab.panel('options').closable) {
	 						parent_tabs.tabs('close', index);
	 					 }
	 					parent_tabs.tabs('select', "个人跟单");
	 					parent_tabs.tabs('select', "跟单信息");
	 					parent_tabs.tabs('update', {
							tab : parent_tabs.tabs('getSelected'), //获取当前被选中的页面
							options : {
								//Title : '新标题',
								}
							}
						);
	            	 }else{
	            		 parent.$.messager.alert('提示', '添加失败');
	            	 }
	            	 
	             }
	        })
	        progressClose();
		})
		
		/* var shipmentTotal = $('#shipmentTotal');//the element I want to monitor
			shipmentTotal.bind('DOMNodeInserted', function(e) {
				var shipmentTotal = parseInt($(e.target).html());
				var quantity = parseInt($('#quantity').html());
				console.log(shipmentTotal +".........."+ quantity)
				if(shipmentTotal > quantity){
					//parent.$.messager.alert('警告', '出货总数大于订单数！');
					$('#showMessage').text('出货总数大于订单数！');
					$('#showMessage').addClass('colorWarning')
				}else{
					$('#showMessage').text('');
					$('#showMessage').removeClass('colorWarning')
				}
			});  */
	});
	
	/* function addOrderFollowupOutFun(){
		var $content = $('<tr class="orderFollowupOut">'+
						'<td class="lefttd" width="10%">出货日期：</td>'+
				        '<td class="righttd" width="15%"><input  class="shipmentDate inputs" style="width: 100%"></td>'+
				     	'<td class="lefttd" width="10%">出货数量：</td>'+
				        '<td class="" width="15%"><input class="shipmentNo inputs"  style="width: 100%"></td>'+
				        '<td style="padding: 0px;text-align:right">'+
							'<span onclick="deleteOrderFollowupOutFun(this);" class="icon-del tip"  title="删除"></span>'+
						'</td>'+
			        '</tr>');
		$('#orderFollowupOutTable').append($content);
		$content.find('.shipmentDate').datebox({
			required : "required",
			editable : false,
		}); 
		$content.find('.shipmentNo').numberbox({   
			required : "required",
			min:0,
			max:2147483647,
			value : 0,
			"onChange":function(newValue,oldValue){// 计算总额  
				var total = 0;
				$('#orderFollowupOutTable').find('.shipmentNo').each(function(){
					if($(this).val() != "" ){
						var shipmentNo = parseInt($(this).val());
						total += shipmentNo;
					}
				})
				$('#shipmentTotal').html(total)
			}
		});
	}
	function deleteOrderFollowupOutFun(obj){
		var trValue = $(obj).parent().parent();
		var tdValue = trValue.children();
		
		var shipmentDate = tdValue.eq(1).find('input').datebox('getValue');
   		var shipmentNo =  tdValue.eq(3).find('input').numberbox('getValue');
   		if(shipmentDate != '' || shipmentNo != ''){
			parent.$.messager.confirm('询问', '您是否要删除整行数据？', function(b) {
				if (b) {
					trValue.remove();
					var total = 0;
					$('#orderFollowupOutTable').find('.shipmentNo').each(function(){
						if($(this).val() != "" ){
							var shipmentNo = parseInt($(this).val());
							total += shipmentNo;
						}
					})
					$('#shipmentTotal').html(total)
				}
			});
		}else{
			trValue.remove();
			var total = 0;
			$('#orderFollowupOutTable').find('.shipmentNo').each(function(){
				if($(this).val() != "" ){
					var shipmentNo = parseInt($(this).val());
					total += shipmentNo;
				}
			})
			$('#shipmentTotal').html(total)
		}
		
	} */

</script>

<div id="">
	<!-- 基础信息 -->
	<form id="orderFollowupForm">
	<table border="0" cellpadding="0" cellspacing="0" class="ordertable">
        <tr class="tabletitle">
            <td colspan="8">基础信息： </td>
        </tr>
        <tr>
         	<td class="lefttd" width="8%">客户名称：</td>
            <td class="righttd" width="17%"><div id="clientName" ></div></td>
            <td class="lefttd" width="8%">订单编号：</td>
            <td class="righttd" width="17%"><div  id="orderNo"></div></td>
            <td class="lefttd" width="8%">销售员：</td>
            <td class="righttd" width="17%"><div  id="seller"></div></td>
            <td class="lefttd" width="8%"> 跟单员：
            <td class="righttd" width="17%"><div class="" id="merchandiser"></div></td>
        </tr>
        <tr>
            <td class="lefttd">产品型号：</td>
            <td class="righttd"><div  id="productFactory"></div></td>
            <td class="lefttd">客户型号：</td>
            <td class="righttd"><div id="productClient"></div></td>
            
            <td class="lefttd">订单数量/pcs：</td>
            <td class="righttd"><div id="quantity"></div></td>       
        </tr>
       
    </table>
    
    <table border="0" cellpadding="0" cellspacing="0" class="ordertable">
        <tr class="tabletitle">
            <td colspan="8">跟单情况： </td>
        </tr>
        <tr>            
        	<td class="lefttd">开机画面：</td>
            <td class="righttd">
            	<input type="radio" value="3" name="bootLogo" >OK
            	<input type="radio" value="2" name="bootLogo">NO
				<input type="radio" value="1" name="bootLogo">N/A              	
            </td>
            <td class="lefttd"> UUID号/其他 ：
            <td class="righttd">
				<input type="radio" value="3" name="uuid">OK
            	<input type="radio" value="2" name="uuid">NO
				<input type="radio" value="1" name="uuid">N/A       
			</td>
            <td class="lefttd">说明书和卡类： </td>
            <td class="righttd">
            	<input type="radio" value="3" name="specification">OK
            	<input type="radio" value="2" name="specification">NO
				<input type="radio" value="1" name="specification">N/A             	  
            </td>
            <td class="lefttd">卡通箱：</td>
            <td class="righttd">
				<input type="radio" value="3" name="carton">OK
            	<input type="radio" value="2" name="carton">NO
				<input type="radio" value="1" name="carton">N/A          	
			</td>
        </tr>
        <tr>            
        	<td class="lefttd">地图：</td>
            <td class="righttd">
            	<input type="radio" value="3" name="map">OK
            	<input type="radio" value="2" name="map">NO
				<input type="radio" value="1" name="map">N/A             	
            </td>
            <td class="lefttd"> 机身外壳：
            <td class="righttd">
				<input type="radio" value="3" name="shell">OK
            	<input type="radio" value="2" name="shell">NO
				<input type="radio" value="1" name="shell">N/A             	  
			</td>
            <td class="lefttd">贴膜类： </td>
            <td class="righttd">
            	<input type="radio" value="3" name="membrane">OK
            	<input type="radio" value="2" name="membrane">NO
				<input type="radio" value="1" name="membrane">N/A            	  
            </td>
            <td class="lefttd">配件：</td>
            <td class="righttd">
				<input type="radio" value="3" name="fitting">OK
            	<input type="radio" value="2" name="fitting">NO
				<input type="radio" value="1" name="fitting">N/A             	
			</td>
        </tr>
         <tr>            
        	<td class="lefttd">软件升级：</td>
            <td class="righttd">
            	<input type="radio" value="3" name="sorfware">OK
            	<input type="radio" value="2" name="sorfware">NO
				<input type="radio" value="1" name="sorfware">N/A            	
            </td>
            <td class="lefttd">机身硬件：</td>
            <td class="righttd">
            	<input type="radio" value="3" name="hardware">OK
            	<input type="radio" value="2" name="hardware">NO
				<input type="radio" value="1" name="hardware">N/A            	
            </td>
            <td class="lefttd">彩盒：
            <td class="righttd">
				<input type="radio" value="3" name="colorbox">OK
            	<input type="radio" value="2" name="colorbox">NO
				<input type="radio" value="1" name="colorbox">N/A            	  
			</td>
            <td class="lefttd">验货： </td>
            <td class="righttd">
            	<input type="radio" value="3" name="inspection">OK
            	<input type="radio" value="2" name="inspection">NO
				<input type="radio" value="1" name="inspection">N/A          	  
            </td>
            </tr>
          <tr>
            <td class="lefttd">预存文件：</td>
            <td class="righttd">
				<input type="radio" value="3" name="preFile">OK
            	<input type="radio" value="2" name="preFile">NO
				<input type="radio" value="1" name="preFile">N/A              	
			</td>
                    
        	<td class="lefttd">贴纸类：</td>
            <td class="righttd">
            	<input type="radio" value="3" name="tags">OK
            	<input type="radio" value="2" name="tags">NO
				<input type="radio" value="1" name="tags">N/A             	
            </td>
            <td class="lefttd">包装袋：
            <td class="righttd">
				<input type="radio" value="3" name="packing">OK
            	<input type="radio" value="2" name="packing">NO
				<input type="radio" value="1" name="packing">N/A              	  
			</td>
            <td class="lefttd">代付： </td>
            <td class="righttd">
            	<input type="radio" value="3" name="agency">OK
            	<input type="radio" value="2" name="agency">NO
				<input type="radio" value="1" name="agency">N/A            	  
            </td>
           </tr>
          <tr>
            <td class="lefttd">付款：</td>
            <td class="righttd">
				<input type="radio" value="3" name="payment">OK
            	<input type="radio" value="2" name="payment">NO
				<input type="radio" value="1" name="payment">N/A         	
			</td>
        </tr>
         <tr>
        	<td class="lefttd" width="10%">方案：</td>
            <td class="righttd" width="15%"><input class="inputs easyui-validatebox" id="plan" data-options="validType:['isBlank_','length[0,64]']" >
             <td class="lefttd" width="10%">客户交期:</td>
            <td class="righttd" width="15%"><input class=" easyui-datebox"editable="false" style="width: 90%" id="dateClient" ></td>
        
         	<td class="lefttd" width="10%">计划回复交期：</td>
            <td class="righttd" width="15%"><input  id="dateFactory" class=" easyui-datebox" editable="false" style="width: 90%"></td>
        </tr>
        <tr>
            <td class="lefttd" >正常状态更新：</td>
            <td class="righttd" colspan="3"><textarea class="textareacss easyui-validatebox" id="status"  data-options="validType:['isBlank_','length[0,200]']"></textarea></td>
        	<td class="lefttd">出货数量和日期：</td>
            <td class="righttd" colspan="3"><textarea class="textareacss easyui-validatebox" id="shipment"   data-options="validType:['isBlank_','length[0,100]']"></textarea></td>
        </tr>
        <tr>
       		<td class="lefttdRed" >导常问题之责任部门或责任人：</td>
            <td class="righttd" colspan="3"><textarea class="textareacssPeople easyui-validatebox" id="dutyOfficer"  data-options="validType:['isBlank_','length[0,64]']"></textarea></td>
            <td class="lefttdRed">异常等级区分：</td>
            <td class="righttd" colspan="3">
				<input type="radio" value="1" name="level">一般
            	<input type="radio" value="2" name="level">严重
				<input type="radio" value="3" name="level">非常严重
			</td>
        </tr>
        <tr>
       		<td class="lefttdRed" >异常状态记录：</td>
            <td class="righttd" colspan="3"><textarea class="textareacss easyui-validatebox" id="statusAbnormal"  data-options="validType:['isBlank_','length[0,200]']"></textarea></td>
            <td class="lefttdRed">是否定出解决方案：</td>
            <td class="righttd" colspan="3"><textarea class="textareacss easyui-validatebox" id="solution"   data-options="validType:['isBlank_','length[0,100]']"></textarea></td>
        </tr>
        <tr>
        	<td class="lefttd">备注：</td>
            <td class="righttd" colspan="3"><textarea class="textareacss easyui-validatebox" id="description"   data-options="validType:['isBlank_','length[0,200]']"></textarea></td>
        </tr>
    </table>
    
    
   <!--  <table border="0" cellpadding="0" cellspacing="0" class="ordertable" id="orderFollowupOutTable" style="margin-bottom: 0px">
        <tr class="tabletitle">
            <td colspan="4">出货记录：</td>
            <th align="right" style="border-left: 0px">
				<span onclick="addOrderFollowupOutFun();" class="icon-add tip"  title="添加"></span>
			</th>
        </tr>
    </table>
     <table border="0" cellpadding="0" cellspacing="0" class="ordertable" >
        <tr class="">
            <td class="lefttd" width="10%">出货总数：</td>
               <td class="righttd" width="40%" ><span id="shipmentTotal">0</span>&nbsp;&nbsp;&nbsp;<span id="showMessage"></span></td>
			<td class="lefttd" width="10%"></td>
            <td class="righttd" width="15%"></td>
			<td class="lefttd" width="10%"></td>
            <td class="righttd" width="15%"></td>
        </tr>
    </table> -->
    </form>
</div>
<div  align="center">
	<a class="btn-default" id="addOrderFollowupBtn">添加跟单</a>
</div>