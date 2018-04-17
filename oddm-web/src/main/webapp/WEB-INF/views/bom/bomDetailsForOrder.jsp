<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<jsp:include page="../inc.jsp"></jsp:include>
<style>
	.body{font-family: "宋体"}
	.ordertable{background-color:#F2F2F2;width:100%;color:#666;margin-bottom:10px;font-size:0.8em;text-align:left;}
	.tabletitle{text-align:left; background-color:#DDEBF7;color:#333;font-size:1.4em; font-weight:500;}
	.tabletop{text-align:left; background-color:#D9D9D9;color:#333;font-size:1.2em; font-weight:500;}
	.tabletop td{text-align: center;}
	.ordertable tr td{padding:5px 0 5px 5px;}
	.ordertable tr th{padding:5px 0 5px 5px;}
	.tabletop th{padding:6px 0 6px 0px;text-align: center;}
	.lefttd{/* width:65px */;text-align:right;}
    .lefttd90{width:90px; text-align:right;}
    .righttd{color:#069;/* width: 187px */}
    .bordertable{background-color:#F2F2F2;width:100%;color:#666;margin-bottom:10px;font-size:0.8em;text-align:center;border-collapse:collapse;}
	.bordertable tr td{ border:1px solid #ccc; padding:5px 0 5px 5px;height:20px;}
	.bordertable tr th{/* border:1px solid #ccc; */padding:5px 0 5px 0px;height:20px;}
	.textareacss2{width:100%;background:none;border: 0px}
	.choose{color: #06c;cursor: pointer;}
	.color_f60 {color: #f60;}
	.color069 {color :#069}
	.menu_hidden{display: none}
	.text_left{text-align: left;}
	.text_center{text-align: center;}
	.tip{height: 16px;width: 16px;display: inline-block;padding: 0px 4px;cursor: pointer;}
	.btn-default {border: 1px solid #DDDDDD;background: white;padding: 10px 18px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #333;}
	.btn-default:hover {border: 1px solid #DDDDDD;background: #04B5F9;padding: 10px 18px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #FFF;}
	.showContactCss{cursor: pointer;color: #06c;text-decoration: underline;}
	.choose{color: #06c;cursor: pointer;}
	pre {white-space: pre-wrap;word-wrap: break-word;text-align: left;margin: 0}
</style>

<script type="text/javascript">
	var orderId = "${orderId}";
	var tableSeq_1 = 0;
	var tableSeq_2 = 0;
	var tableSeq_3 = 0;
	var tableSeq_4 = 0;
	var tableSeq_5 = 0;
	var tableSeq_6 = 0;
	var tableSeq_7 = 0;
	var tableSeq_8 = 0;
	var tableSeq_9 = 0;
	$(function() {
		$.ajax({
			url:"${ctx}/bom/findBomInfo?orderId="+ orderId,
			type:"GET",
			dataType: "json",
            contentType: "application/json; charset=utf-8", 
            success: function(data){
            	
            	if(data != null){
            		console.log(data)
            		//$('#title').html(data.title);
            		$('#maker').html(data.maker);
            		$('#date').html(data.dateString);
            		$('#version').html(data.version);
            		$('#materialCode').html(data.materialCode);
            		$('#productName').html(data.productName);
            		//$('#brand').html(data.brand);
            		$('#specification').html(data.specification);
            		$('#description').html(data.description);
            		if(data.state <= 2){
                		$("#bomReviewBtn").css('display','inline-block');
                		//$("#orderSubmitToSeller").show();
                	}
            		if(data.state >= 5){
                		$("#bomAltListBtn").css('display','inline-block');
                		$("#excelExportBtn").css('display','inline-block');
                		
                	}
            		if(data.orderInfoDTO != null){
            			$('#orderNo').html(data.orderInfoDTO.orderNo);
                		$('#productFactory').html(data.orderInfoDTO.productFactory);
                		$('#quantity').html(data.orderInfoDTO.quantity);
                		$('#seller').html(data.orderInfoDTO.seller);
            		}
            		if(data.bomMaterialRefDTOs != null && data.bomMaterialRefDTOs.length > 0){
            			var bomMaterialRefDTOs = data.bomMaterialRefDTOs;
            			for(var i in bomMaterialRefDTOs){
            				var bomMaterialRef = bomMaterialRefDTOs[i];
            				var materialCode = bomMaterialRef.materialCode == null ? '' : bomMaterialRef.materialCode;
        					var productName = bomMaterialRef.productName == null ? '' : bomMaterialRef.productName;
        					var specification = bomMaterialRef.specification == null ? '' : bomMaterialRef.specification;
        					var description = bomMaterialRef.description == null ? '' : bomMaterialRef.description;
        					var brand = bomMaterialRef.brand == null ? '' : bomMaterialRef.brand;
        					var usageAmount = bomMaterialRef.usageAmount == null ? '' : bomMaterialRef.usageAmount;
            				if(bomMaterialRef.seq == 0 || bomMaterialRef.seq == '0'){
            					//seq 为0是标题
            					var trValue = $('#tableTitle_'+bomMaterialRef.type).find('.bomMaterialMain');
            					var tdValue = trValue.children();
            					tdValue.eq(1).find('div').html(materialCode);
            					tdValue.eq(3).find('div').html(productName);
            					
            					//tdValue.eq(5).find('pre').html(specification);
            					
            					var trValueNext = trValue.next().children();
            					
            					trValueNext.eq(1).find('pre').html(specification);
            					trValueNext.eq(3).find('pre').html(description);
            					//trValueNext.eq(1).find('pre').html(description);
            				}else{
            					//seq 大于0则为内容
            					if(bomMaterialRef.type != null ){
            						if(bomMaterialRef.type == 9 || bomMaterialRef.type == '9'){
                						/* var $content = $('<tr class="bomMaterial">'+
                    							'<td style="padding:6px 0px"><div class="text_center" >'+bomMaterialRef.seq+'</div></td>'+
                    							'<td ></td>'+
        								        '<td ><div class="text_left" >'+productName+'</div></td>'+
        								        '<td><pre class="" >'+specification+'</pre></td>'+
        								        '<td ></td>'+
        								        '<td ><div class="text_center" >'+usageAmount+'</div></td>'+
        								        '<td ></td>'+
        								        '<td ></td>'+
        								    '</tr>'); */
        								   // tableSeq_9++;
                						var $content = $('<tr class="bomMaterial">'+
                    							'<td style="padding:6px 0px"><div class="text_center" >'+bomMaterialRef.seq+'</div></td>'+
                    							'<td ></td>'+
        								        '<td ><div class="text_left" >'+productName+'</div></td>'+
        								        '<td><pre class="" >'+specification+'</pre></td>'+
        								        '<td ></td>'+
        								        '<td style="padding:6px 0px"><div class="text_center" >'+usageAmount+'</div></td>'+
        								        '<td ></td>'+
        								        '<td ></td>'+
        								    '</tr>'); 
                					}else{
                						/* var $content = $('<tr class="bomMaterial">'+
                    							'<td style="padding:6px 0px"><div class="text_center" >'+bomMaterialRef.seq+'</div></td>'+
        								        '<td ><div class="text_center" >'+materialCode+'</div></td>'+
        								        '<td ><div class="text_left" >'+productName+'</div></td>'+
        								        '<td><pre class="" >'+specification+'</pre></td>'+
        								        '<td ><div class="text_center" >'+brand+'</div></td>'+
        								        '<td ><div class="text_center" >'+usageAmount+'</div></td>'+
        								        '<td ><pre class="text_center" >'+description+'</pre></td>'+
        								        '<td ><span onclick="showContact(this);" class="showContactCss">查看</span></td>'+
        								    '</tr>'); */
        								
                						var $content = $('<tr class="bomMaterial">'+
                    							'<td style="padding:6px 0px"><div class="text_center" >'+bomMaterialRef.seq+'</div></td>'+
        								        '<td ><div class="text_center" >'+materialCode+'</div></td>'+
        								        '<td ><div class="text_left" >'+productName+'</div></td>'+
        								        '<td><pre class="" >'+specification+'</pre></td>'+
        								        '<td ><div class="text_center" >'+brand+'</div></td>'+
        								        '<td style="padding:6px 0px"><div class="text_center" >'+usageAmount+'</div></td>'+
        								        '<td ><pre class="text_center" >'+description+'</pre></td>'+
        								        '<td ><span onclick="showContact(this);" class="showContactCss">查看</span></td>'+
        								    '</tr>');
                					}
                					
            					}
            					
            					if(bomMaterialRef.materialContactDTO != null){
            						var materialContactDTO = bomMaterialRef.materialContactDTO;
            						var company = materialContactDTO.company == null ? '' : materialContactDTO.company;
            						var contacts = materialContactDTO.contacts == null ? '' : materialContactDTO.contacts;
            						var telphone = materialContactDTO.telphone == null ? '' : materialContactDTO.telphone;
            						var cellphone = materialContactDTO.cellphone == null ? '' : materialContactDTO.cellphone;
            						var email = materialContactDTO.email == null ? '' : materialContactDTO.email;
            						var fax = materialContactDTO.fax == null ? '' : materialContactDTO.fax;
            						
            						if(company == null || company == ''){
            							$content.find('.showContactCss').text("查看")
            						}else{
            							$content.find('.showContactCss').text(company)
            						}
            						//$content.find('.showContactCss').text(company)
            						$content.data('company',company);
            						$content.data('contacts',contacts);
            						$content.data('telphone',telphone);
            						$content.data('cellphone',cellphone);
            						$content.data('email',email);
            						$content.data('fax',fax);
            					}else{
            						$content.find('.showContactCss').text("");
            					}
            					$('#tableContent_'+bomMaterialRef.type).append($content)
            				}
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
	
	//变更记录
	$('#manufactureAltList').off('click').on('click',function(){
		parent.$.modalDialog({
			title : '指导书变更记录',
			width : 800,
			height : 600,
			href : '${ctx}/manufactureAlt/manufactureAltPage?bomId='+bomId,
			buttons : [ {
				text : '关闭',
				handler : function() {
					parent.$.modalDialog.handler.dialog('close');
				}
			} ]
		});
	})
});	
	function showContact(obj){
		var trValue = $(obj).parent().parent();
		var contacts = trValue.data('contacts');
		
		if(contacts == null || typeof(contacts) == 'undefined' || contacts == ''){
			 parent.$.messager.alert('提示', '暂无相关联系人！！');
		}else{
			var contacts = typeof(trValue.data('contacts')) == 'undefined' ? "" : encodeURIComponent(trValue.data('contacts'));
			var company = typeof(trValue.data('company')) == 'undefined' ? "" : encodeURIComponent(trValue.data('company'));
			var telphone = typeof(trValue.data('telphone')) == 'undefined' ? "" : encodeURIComponent(trValue.data('telphone'));
			var cellphone = typeof(trValue.data('cellphone')) == 'undefined' ? "" : encodeURIComponent(trValue.data('cellphone'));
			var email = typeof(trValue.data('email')) == 'undefined' ? "" : encodeURIComponent(trValue.data('email'));
			var fax = typeof(trValue.data('fax')) == 'undefined' ? "" : encodeURIComponent(trValue.data('fax'));
			
			var param = "company="+company+"&contacts="+contacts+"&telphone="+telphone+"&cellphone="+cellphone+"&email="+email+"&fax="+fax;
			parent.$.modalDialog({
				title : 'Bom联系人查看',
				width : 350,
				height : 250,
				href : '${ctx}/bom/showBomContactPage?'+(param),
				buttons : [ {
					text : '关闭',
					handler : function() {
						parent.$.modalDialog.handler.dialog('close');
					}
				} ] 
			});
		}
	}
	
	function excelExportFun(id){
		download_file('${ctx}/bom/excelExport?id='+bomId);
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
	function bomAltListFun(){
		parent.$.modalDialog({
			title : 'Bom变更记录',
			width : 800,
			height : 600,
			href : '${ctx}/bomAlt/bomAltPage?id='+bomId,
			buttons : [ {
				text : '关闭',
				handler : function() {
					parent.$.modalDialog.handler.dialog('close');
				}
			} ]
		});
	}
	
	function bomReviewFun(){
		parent.$.messager.confirm('询问', '您是否确定审核通过？(操作后将会产生<span style="color:#f60">变更记录</span>！)', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/bom/review', {
					id : bomId
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						var parent_tabs = parent.$('#index_tabs');
						var index = parent_tabs.tabs('getTabIndex', parent.$('#index_tabs').tabs('getSelected'));
						var tab = parent_tabs.tabs('getTab', index);
						if (tab.panel('options').closable) {
							parent_tabs.tabs('close', index);
						}
						parent_tabs.tabs('select', "BOM表单");
						parent_tabs.tabs('update', {
							tab : parent_tabs.tabs('getSelected'), //获取当前被选中的页面
							options : {
								//Title : '新标题',
								}
							}
						); 
					} else {
						parent.$.messager.alert('错误', result.msg, 'error');
					}
					progressClose();
				}, 'JSON');
			}
		});
	}
	
	function countSeq(type){
		switch (type)
		{
		case 1:
			tableSeq_1 ++;
			return tableSeq_1;
		case 2:
			tableSeq_2 ++;
			return tableSeq_2;
		case 3:
			tableSeq_3 ++;
			return tableSeq_3;
		case 4:
			tableSeq_4 ++;
			return tableSeq_4;
		case 5:
			tableSeq_5 ++;
			return tableSeq_5;
		case 6:
			tableSeq_6 ++;
			return tableSeq_6;
		case 7:
			tableSeq_7 ++;
			return tableSeq_7;
		case 8:
			tableSeq_8 ++;
			return tableSeq_8;
		}
		
	} 

	/* function countSeq(type){
		var map = {
			tableSeq_1 : tableSeq_1,
			tableSeq_2 : tableSeq_2,
			tableSeq_3 : tableSeq_3,
			tableSeq_4 : tableSeq_4,
			tableSeq_5 : tableSeq_5,
			tableSeq_6 : tableSeq_6,
			tableSeq_7 : tableSeq_7,
			tableSeq_8 : tableSeq_8,
			tableSeq_9 : tableSeq_9,
			
		}
		return map['tableSeq_'+type] ++ ;
		
	} */
</script>
<div id='content'>
	<!-- 基础信息 -->
	<table border="0" cellpadding="0" cellspacing="0" class="ordertable">
        <tr class="tabletitle">
            <th colspan="8">
            	基础信息:&nbsp;&nbsp;
            	<span onclick="bomAltListFun();" id='bomAltListBtn' class="showContactCss">[变更记录]</span>&nbsp; 
            	<c:if test="${fn:contains(sessionInfo.resourceList, '/bom/excel')}">
					<span onclick="excelExportFun();" id='excelExportBtn' class="showContactCss menu_hidden">[导出EXCEL]</span>
				</c:if>
            </th>
        </tr>
        <tr>
            <td class="lefttd" width="10%" >订单号:</td>
            <td class="righttd" width="15%"><div id='orderNo'></div></td>
            <td class="lefttd" width="10%">工厂机型:</td>
            <td class="righttd" width="15%"><div id='productFactory'></div></td>
            <td class="lefttd" width="10%">数量:</td>
            <td class="righttd" width="15%"><div id='quantity'></div></td>
            <td class="lefttd" width="10%">所属业务:</td>
            <td class="righttd" width="15%"><div id='seller'></div></td>
        </tr>
        <tr>
            <!-- <td class="lefttd">清单标题: </td>
            <td class="righttd"><div class="text_left"  id="title"/></div></td> -->
            <td class="lefttd">制表人:</td>
            <td class="righttd"><div  class="text_left"  id="maker"/></div></td>
            <td class="lefttd">日期:</td>
            <td class="righttd"><div  class="text_left"   id="date"/></div></td>
            <td class="lefttd">版本:</td>
            <td class="righttd"><div  class="text_left"  id="version" /></div></td>
        </tr>
        <tr>
            <td class="lefttd">物料编码:</td>
            <td class="righttd"><div  class="text_left"  id="materialCode" /></div></td>
            <td class="lefttd">品名:</td>
            <td class="righttd"><div  class="text_left"  id="productName" /></div> </td>
            <!-- <td class="lefttd">品牌:</td>
            <td class="righttd"><div  class="text_left"  id="brand" /></div> </td> -->
            
        </tr> 
        <tr>
         	<td class="lefttd">规格型号与封装:</td>
            <td class="righttd" colspan="3"><pre id="specification" class="text_left" ></pre></td>
            <td class="lefttd">描述:</td>
            <td class="righttd" colspan="3"><pre id="description" class="text_left" ></pre></td>
        </tr>
    </table>
  
  <!-- 第一部分:裸机包装模组 -->
   	   <table id="tableTitle_1" border="0" cellpadding="0" cellspacing="0" class="ordertable" style="margin-bottom: 0px;margin-top: 10px">
		    <tr class="tabletitle">
		        <th colspan="8">第一部分:裸机包装模组 </th>
		        
		    </tr>
		  	<tr class="bomMaterialMain">
		      	<td width="10%" class="lefttd">物料编码:</td>
		        <td width="20%" class="righttd"><div  class="text_left"   /></div></td>
		        <td width="5%" class="lefttd">品名:</td>
		        <td width="20%" class="righttd"><div  class="text_left"  /></div></td>
		        <td class="lefttd" width="5%"></td>
		        <td class="righttd" width="15%"></td>
		        <td class="lefttd" width="10%"></td>
		        <td class="righttd" width="15%"></td>
		  	</tr>
		  	<tr>
		  		<td class="lefttd">规格型号及封装:</td>
		        <td colspan="3" class="righttd" ><pre  class="text_left" ></pre></td>
		  		<td class="lefttd">描述:</td>
		        <td colspan="3" class="righttd" ><pre  class="righttd" ></pre></td>
		  	</tr> 
		  	
    	</table>
    	<table id="tableContent_1" border="0" cellpadding="0" cellspacing="0" class="bordertable">
		    <tr class="tabletop">
		        <th width="3%" class="text_left">序号 </th>
		        <th width="12%" class="text_left">物料编码</th>
		        <th width="20%" class="text_left">品名</th>
		        <th width="30%" class="text_left">规格型号及封装</th>
		        <th width="7%" class="text_left">品牌</th>
		        <th width="5%" class="text_left">用量</th>
		        <th width="13%" class="text_left">描述</th>
		        <th width="10%" class="text_left">联系人</th>
		    </tr>
	    </table>
	   <!-- 一、裸机成品 -->
	    <table id="tableTitle_2" border="0" cellpadding="0" cellspacing="0" class="ordertable" style="margin-bottom: 0px;margin-top: 0px">
		    <tr class="tabletitle">
		        <th colspan="8">一、裸机成品</th>
		        
		    </tr>
		 	<tr class="bomMaterialMain">
		      	<td width="10%" class="lefttd">物料编码:</td>
		        <td width="20%" class="righttd"><div  class="text_left"   /></div></td>
		        <td width="5%" class="lefttd">品名:</td>
		        <td width="20%" class="righttd"><div  class="text_left"  /></div></td>
		        <td class="lefttd" width="5%"></td>
		        <td class="righttd" width="15%"></td>
		        <td class="lefttd" width="10%"></td>
		        <td class="righttd" width="15%"></td>
		  	</tr>
		  	<tr>
		  		<td class="lefttd">规格型号及封装:</td>
		        <td colspan="3" class="righttd" ><pre  class="text_left" ></pre></td>
		  		<td class="lefttd">描述:</td>
		        <td colspan="3" class="righttd" ><pre  class="righttd" ></pre></td>
		  	</tr>  
		  	
    	</table>
   	   <!--  <table id="tableContent_2" border="0" cellpadding="0" cellspacing="0" class="bordertable">
		    <tr class="tabletop">
		        <th width="3%" class="text_left">序号 </th>
		        <th width="10%" class="text_left">物料编码</th>
		        <th width="20%" class="text_left">品名</th>
		        <th width="27%" class="text_left">规格型号及封装</th>
		        <th width="10%" class="text_left">品牌</th>
		        <th width="5%" class="text_left">用量</th>
		        <th width="15%" class="text_left">描述</th>
		        <th width="10%" class="text_left">联系人</th>
		    </tr>
	    </table> -->
	    
	    <table id="tableTitle_3" border="0" cellpadding="0" cellspacing="0" class="ordertable" style="margin-bottom: 0px;margin-top: 0px">
		    <tr class="tabletitle">
		        <th colspan="8">a.散料，无虚拟件料号</th>
		        
		    </tr>
		  	<!-- <tr class="bomMaterialMain">
		      	<td width="10%" class="lefttd">物料编码:</td>
		        <td width="15%" class="righttd"><div  class="text_left"   /></div></td>
		        <td width="10%" class="lefttd">品名:</td>
		        <td width="20%" class="righttd"><div  class="text_left"  /></div></td>
		        <td class="lefttd" width="5%"></td>
		        <td class="righttd" width="15%"></td>
		        <td class="lefttd" width="10%"></td>
		        <td class="righttd" width="15%"></td>
		  	</tr>
		  	<tr>
		  		<td class="lefttd">规格型号及封装:</td>
		        <td colspan="3" class="righttd" ><pre  class="text_left" ></pre></td>
		  		<td class="lefttd">描述:</td>
		        <td colspan="3" class="righttd" ><pre  class="righttd" ></pre></td>
		  	</tr> --> 
    	</table>
   	    <table id="tableContent_3" border="0" cellpadding="0" cellspacing="0" class="bordertable">
		   <tr class="tabletop">
		        <th width="3%" class="text_left">序号 </th>
		        <th width="12%" class="text_left">物料编码</th>
		        <th width="20%" class="text_left">品名</th>
		        <th width="30%" class="text_left">规格型号及封装</th>
		        <th width="7%" class="text_left">品牌</th>
		        <th width="5%" class="text_left">用量</th>
		        <th width="13%" class="text_left">描述</th>
		        <th width="10%" class="text_left">联系人</th>
		    </tr>
	    </table>
	    
	    <table id="tableTitle_4" border="0" cellpadding="0" cellspacing="0" class="ordertable" style="margin-bottom: 0px;margin-top: 0px">
		    <tr class="tabletitle">
		        <th colspan="8">b.外壳模组</th>
		      
		    </tr>
		  	 <tr class="bomMaterialMain">
		      	<td width="10%" class="lefttd">物料编码:</td>
		        <td width="20%" class="righttd"><div  class="text_left"   /></div></td>
		        <td width="5%" class="lefttd">品名:</td>
		        <td width="20%" class="righttd"><div  class="text_left"  /></div></td>
		        <td class="lefttd" width="5%"></td>
		        <td class="righttd" width="15%"></td>
		        <td class="lefttd" width="10%"></td>
		        <td class="righttd" width="15%"></td>
		  	</tr>
		  	<tr>
		  		<td class="lefttd">规格型号及封装:</td>
		        <td colspan="3" class="righttd" ><pre  class="text_left" ></pre></td>
		  		<td class="lefttd">描述:</td>
		        <td colspan="3" class="righttd" ><pre  class="righttd" ></pre></td>
		  	</tr>  
    	</table>
    	<table id="tableContent_4" border="0" cellpadding="0" cellspacing="0" class="bordertable">
		     <tr class="tabletop">
		        <th width="3%" class="text_left">序号 </th>
		        <th width="12%" class="text_left">物料编码</th>
		        <th width="20%" class="text_left">品名</th>
		        <th width="30%" class="text_left">规格型号及封装</th>
		        <th width="7%" class="text_left">品牌</th>
		        <th width="5%" class="text_left">用量</th>
		        <th width="13%" class="text_left">描述</th>
		        <th width="10%" class="text_left">联系人</th>
		    </tr>
	    </table>
   	   
	    
	    <table id="tableTitle_5" border="0" cellpadding="0" cellspacing="0" class="ordertable" style="margin-bottom: 0px;margin-top: 0px">
		    <tr class="tabletitle">
		        <th colspan="8">c.显示模组</th>
		        
		    </tr>
		  	<tr class="bomMaterialMain">
		      	<td width="10%" class="lefttd">物料编码:</td>
		        <td width="20%" class="righttd"><div  class="text_left"   /></div></td>
		        <td width="5%" class="lefttd">品名:</td>
		        <td width="20%" class="righttd"><div  class="text_left"  /></div></td>
		        <td class="lefttd" width="5%"></td>
		        <td class="righttd" width="15%"></td>
		        <td class="lefttd" width="10%"></td>
		        <td class="righttd" width="15%"></td>
		  	</tr>
		  	<tr>
		  		<td class="lefttd">规格型号及封装:</td>
		        <td colspan="3" class="righttd" ><pre  class="text_left" ></pre></td>
		  		<td class="lefttd">描述:</td>
		        <td colspan="3" class="righttd" ><pre  class="righttd" ></pre></td>
		  	</tr> 
    	</table>
    	<table id="tableContent_5" border="0" cellpadding="0" cellspacing="0" class="bordertable">
		    <tr class="tabletop">
		        <th width="3%" class="text_left">序号 </th>
		        <th width="12%" class="text_left">物料编码</th>
		        <th width="20%" class="text_left">品名</th>
		        <th width="30%" class="text_left">规格型号及封装</th>
		        <th width="7%" class="text_left">品牌</th>
		        <th width="5%" class="text_left">用量</th>
		        <th width="13%" class="text_left">描述</th>
		        <th width="10%" class="text_left">联系人</th>
		    </tr>
	    </table>
   	   
	    
	    <table id="tableTitle_6" border="0" cellpadding="0" cellspacing="0" class="ordertable" style="margin-bottom: 0px;margin-top: 10px">
		    <tr class="tabletitle">
		        <th colspan="8">第二部分:彩盒模组部分</th>
		        
		    </tr>
		  	<tr class="bomMaterialMain">
		      	<td width="10%" class="lefttd">物料编码:</td>
		        <td width="20%" class="righttd"><div  class="text_left"   /></div></td>
		        <td width="5%" class="lefttd">品名:</td>
		        <td width="20%" class="righttd"><div  class="text_left"  /></div></td>
		        <td class="lefttd" width="5%"></td>
		        <td class="righttd" width="15%"></td>
		        <td class="lefttd" width="10%"></td>
		        <td class="righttd" width="15%"></td>
		  	</tr>
		  	<tr>
		  		<td class="lefttd">规格型号及封装:</td>
		        <td colspan="3" class="righttd" ><pre  class="text_left" ></pre></td>
		  		<td class="lefttd">描述:</td>
		        <td colspan="3" class="righttd" ><pre  class="righttd" ></pre></td>
		  	</tr> 
    	</table>
    	<table id="tableContent_6" border="0" cellpadding="0" cellspacing="0" class="bordertable">
		    <tr class="tabletop">
		        <th width="3%" class="text_left">序号 </th>
		        <th width="12%" class="text_left">物料编码</th>
		        <th width="20%" class="text_left">品名</th>
		        <th width="30%" class="text_left">规格型号及封装</th>
		        <th width="7%" class="text_left">品牌</th>
		        <th width="5%" class="text_left">用量</th>
		        <th width="13%" class="text_left">描述</th>
		        <th width="10%" class="text_left">联系人</th>
		    </tr>
	    </table>
	    
	    <table id="tableTitle_7" border="0" cellpadding="0" cellspacing="0" class="ordertable" style="margin-bottom: 0px;margin-top: 10px">
		    <tr class="tabletitle">
		        <th colspan="8">第三部分:卡通箱模组部分</th>
		        
		    </tr>
		  	<tr class="bomMaterialMain">
		      	<td width="10%" class="lefttd">物料编码:</td>
		        <td width="20%" class="righttd"><div  class="text_left"   /></div></td>
		        <td width="5%" class="lefttd">品名:</td>
		        <td width="20%" class="righttd"><div  class="text_left"  /></div></td>
		        <td class="lefttd" width="5%"></td>
		        <td class="righttd" width="15%"></td>
		        <td class="lefttd" width="10%"></td>
		        <td class="righttd" width="15%"></td>
		  	</tr>
		  	<tr>
		  		<td class="lefttd">规格型号及封装:</td>
		        <td colspan="3" class="righttd" ><pre  class="text_left" ></pre></td>
		  		<td class="lefttd">描述:</td>
		        <td colspan="3" class="righttd" ><pre  class="righttd" ></pre></td>
		  	</tr> 
		  	<!-- <tr class="bomMaterialMain">
		      	<td width="10%" class="lefttd">物料编码:</td>
		        <td width="15%" class="righttd"><div  class="text_left" /></div></td>
		        <td width="10%" class="lefttd">品名:</td>
		        <td width="15%" class="righttd"><div  class="text_left" /></div></td>
		        <td width="15%" class="">规格型号及封装:</td>
		        <td width="" class="righttd" colspan="3"><pre  class="text_left" ></pre></td>
		  	</tr>
		  	<tr>
		  		<td  >描述:</td>
		        <td ><pre  class="righttd" ></pre></td>
		  	</tr>
 -->    	</table>
    	<table id="tableContent_7" border="0" cellpadding="0" cellspacing="0" class="bordertable">
		    <tr class="tabletop">
		        <th width="3%" class="text_left">序号 </th>
		        <th width="12%" class="text_left">物料编码</th>
		        <th width="20%" class="text_left">品名</th>
		        <th width="30%" class="text_left">规格型号及封装</th>
		        <th width="7%" class="text_left">品牌</th>
		        <th width="5%" class="text_left">用量</th>
		        <th width="13%" class="text_left">描述</th>
		        <th width="10%" class="text_left">联系人</th>
		    </tr>
	    </table>
   	    
	    
	    <table id="tableTitle_8" border="0" cellpadding="0" cellspacing="0" class="ordertable" style="margin-bottom: 0px;margin-top: 10px">
		    <tr class="tabletitle">
		        <th colspan="8">第四部分:配件及其他包材类</th>
		        
		    </tr>
		  	<!-- <tr class="bomMaterialMain">
		      	<td width="10%" class="lefttd">物料编码:</td>
		        <td width="20%" class="righttd"><div  class="text_left"   /></div></td>
		        <td width="5%" class="lefttd">品名:</td>
		        <td width="20%" class="righttd"><div  class="text_left"  /></div></td>
		        <td class="lefttd" width="5%"></td>
		        <td class="righttd" width="15%"></td>
		        <td class="lefttd" width="10%"></td>
		        <td class="righttd" width="15%"></td>
		  	</tr>
		  	<tr>
		  		<td class="lefttd">规格型号及封装:</td>
		        <td colspan="3" class="righttd" ><pre  class="text_left" ></pre></td>
		  		<td class="lefttd">描述:</td>
		        <td colspan="3" class="righttd" ><pre  class="righttd" ></pre></td>
		  	</tr>  -->
		  
    	</table>
   		<table id="tableContent_8" border="0" cellpadding="0" cellspacing="0" class="bordertable">
		    <tr class="tabletop">
		        <th width="3%" class="text_left">序号 </th>
		        <th width="12%" class="text_left">物料编码</th>
		        <th width="20%" class="text_left">品名</th>
		        <th width="30%" class="text_left">规格型号及封装</th>
		        <th width="7%" class="text_left">品牌</th>
		        <th width="5%" class="text_left">用量</th>
		        <th width="13%" class="text_left">描述</th>
		        <th width="10%" class="text_left">联系人</th>
		    </tr>
	    </table>
	    
	    <table id="tableTitle_9" border="0" cellpadding="0" cellspacing="0" class="ordertable" style="margin-bottom: 0px;margin-top: 10px">
		    <tr class="tabletitle">
		        <th colspan="8">第五部分:备品</th>
		       
		    </tr>
		  <!-- 	<tr class="bomMaterialMain">
		      	<td width="10%" class="text_left">物料编码:</td>
		        <td width="40%"><div  class="text_left"/></div></td>
		        <td width="10%" class="text_left">品名:</td>
		        <td width="40%"><div  class="text_left" /></div></td>
		  	</tr>
		  	<tr>
		  		<td>规格型号及封装:</td>
		        <td><pre  class="text_left" ></pre></td>
		  		<td  >描述:</td>
		        <td ><pre  class="righttd" ></pre></td>
		  	</tr> -->
    	</table>
    	<!-- <table id="tableContent_9" border="0" cellpadding="0" cellspacing="0" class="bordertable">
		    <tr class="tabletop">
		        <th width="5%" class="text_left">序号 </th>
		        <th width="15%" class="text_left">物料编码</th>
		        <th width="20%" class="text_left">品名</th>
		        <th width="25%" class="text_left">规格型号及封装</th>
		        <th width="10%" class="text_left">品牌</th>
		        <th width="5%" class="text_left">用量</th>
		        <th width="15%" class="text_left">描述</th>
		        <th width="5%" class="text_left">联系人</th>
		    </tr>
	    </table> -->
	    <table id="tableContent_9" border="0" cellpadding="0" cellspacing="0" class="bordertable">
		    <tr class="tabletop">
		        <th width="3%" class="text_left">序号 </th>
		        <th width="12%" class="text_left"></th>
		        <th width="20%" class="text_left">品名</th>
		        <th width="30%" class="text_left">规格型号及封装</th>
		        <th width="7%" class="text_left"></th>
		        <th width="5%" class="text_left">用量</th>
		        <th width="13%" class="text_left"></th>
		        <th width="10%" class="text_left"></th>
		    </tr>
	    </table>
</div>
<div align="center">
	<c:if test="${fn:contains(sessionInfo.resourceList, '/bom/review')}">
		<button id="bomReviewBtn" class='btn-default menu_hidden' onclick="bomReviewFun();">审核通过</button> 
	</c:if>
</div>