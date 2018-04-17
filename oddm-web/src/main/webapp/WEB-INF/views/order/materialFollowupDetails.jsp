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
    .lefttd90{width:90px; text-align:right;}
    .righttd{color:#069;}
    .bordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:center;border-collapse:collapse;}
	.bordertable td{border:1px solid #ccc;padding:10px 0 10px 6px;height:20px;}
	.textareacss{width:96%;background:none;border: 0px}
	.choose{color: #06c;cursor: pointer;}
	.color_f60 {color: #f60;}
	.clickCss{cursor: pointer;text-decoration: underline;}
	pre {white-space: pre-wrap;word-wrap: break-word;text-align: left;margin: 0}
</style>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${sessionInfo.hasRoles == true}">
	<script type="text/javascript">
		$.hasRoles = true;
	</script>
</c:if>
<script type="text/javascript">
	var orderId = "${orderId}"
	$(function() {
		 $.ajax({
			url:"${ctx}/materialFollowup/get?id="+ orderId,
			type:"GET",
			dataType: "json",
            contentType: "application/json; charset=utf-8", 
            success: function(data){
            	if(data != null && data.length > 0){
           			if(data[0].orderInfoDTO != null){
               			$('#orderNo').html(data[0].orderInfoDTO.orderNo);
               			$('#seller').html(data[0].orderInfoDTO.seller);
               			$('#merchandiser').html(data[0].orderInfoDTO.merchandiser);
               			$('#quantity').html(data[0].orderInfoDTO.quantity);
               			$('#clientName').html(data[0].orderInfoDTO.clientName);
               		}
            		for(var i in data){
            			if(data[i].section == null || typeof(data[i].section) == 'undefined'){
            				data[i].section = "";
            			}
            			if(data[i].mtCode == null || typeof(data[i].mtCode) == 'undefined'){
            				data[i].mtCode = "";
            			}
            			if(data[i].prodLine == null || typeof(data[i].prodLine) == 'undefined'){
            				data[i].prodLine = "";
            			}
            			if(data[i].quality == null || typeof(data[i].quality) == 'undefined'){
            				data[i].quality = "";
            			}
            			if(data[i].dateCommit == null || typeof(data[i].dateCommit) == 'undefined'){
            				data[i].dateCommit = "";
            			}
            			if(data[i].dateDeliver == null || typeof(data[i].dateDeliver) == 'undefined'){
            				data[i].dateDeliver = "";
            			}
            			if(data[i].dateSubmit == null || typeof(data[i].dateSubmit) == 'undefined'){
            				data[i].dateSubmit = "";
            			}
            			if(data[i].dateFinish == null || typeof(data[i].dateFinish) == 'undefined'){
            				data[i].dateFinish = "";
            			}
            			if(data[i].dateTo == null || typeof(data[i].dateTo) == 'undefined'){
            				data[i].dateTo = "";
            			}
            			if(data[i].dateOnline == null || typeof(data[i].dateOnline) == 'undefined'){
            				data[i].dateOnline = "";
            			}
            			if(data[i].specification == null || typeof(data[i].specification) == 'undefined'){
            				data[i].specification = "";
            			}
            			if(data[i].mtCondition == null || typeof(data[i].mtCondition) == 'undefined'){
            				data[i].mtCondition = "";
            			}
            			var $content = $('<table border="0" cellpadding="0" cellspacing="0" class="ordertable" >'+
            		        '<tr class="tabletitle">'+
            		            '<td colspan="8"><span class="section"></span> <a  class="clickCss " onClick="materialFollowupAltList('+ data[i].id +');"> [变更记录]</a> </td>'+
            		        '</tr>'+
            		          '<tr>'+
            		            
            		            '<td class="lefttd" width="10%">物料编码：</td>'+
            		            '<td class="righttd" width="15%">'+ data[i].mtCode +'</td>'+
            		            '<td class="lefttd" width="10%"> 生产顺位：'+
            		            '<td class="righttd" width="15%">'+ data[i].prodLine +'</td>'+
            		            '<td class="lefttd" width="10%">批量：</td>'+
            		            '<td class="righttd"width="15%">'+ data[i].quality +'</td>'+
            		            '<td class="lefttd" width="10%"></td>'+
            		            '<td class="righttd" width="15%"></td>'+
            		        '</tr>'+
            		        '<tr>'+
            		            '<td class="lefttd">料号确定日期：</td>'+
            		            '<td class="righttd">'+ data[i].dateCommit +'</td>'+
            		            '<td class="lefttd">交货日期：</td>'+
            		            '<td class="righttd">'+ data[i].dateDeliver +'</td>'+
            		            '<td class="lefttd" >订单下达日期：</td>'+
            		            '<td class="righttd" >'+ data[i].dateSubmit +'</td>'+
            		            '<td class="lefttd" >齐料日期：</td>'+
            		            '<td class="righttd" >'+ data[i].dateFinish +'</td>'+
            		        '</tr>'+
            		       ' <tr>'+         
            		           ' <td class="lefttd" >到今天为止天数 :'+
            		            '<td class="righttd" >'+ data[i].dateTo +'</td>'+
            		            '<td class="lefttd"  >组装预计上线日期： </td>'+
            		            '<td class="righttd" >'+ data[i].dateOnline +' </td>'+
            		        '</tr>'+
            		        '<tr>'+
            		            '<td class="lefttd">规格：</td>'+
            		            '<td class="righttd" colspan="3"><pre>'+ data[i].specification +'</pre></td>'+
            		            '<td class="lefttd">整机物料齐套状况：</td>'+
            		            '<td class="righttd" colspan="3"><pre>'+ data[i].mtCondition +'</pre></td>'+
            		       '</tr>'+
            		    '</table>');
            			$content.data('id',data[i].id)
            			$('#materialFollowupList').append($content);
            			
            		}
            		countSection();
            	}else{
            		parent.$.messager.alert('提示', '查询不到相关物料交期！！',  'info');
            		 var parent_tabs = parent.$('#index_tabs');
            		 var index = parent_tabs.tabs('getTabIndex', parent.$('#index_tabs').tabs('getSelected'));
 					 var tab = parent_tabs.tabs('getTab', index);
 					 if (tab.panel('options').closable) {
 						parent_tabs.tabs('close', index);
 					 }
 					parent_tabs.tabs('select', "个人物料交期");
 					parent_tabs.tabs('select', "物料交期");
 					parent_tabs.tabs('update', {
						tab : parent_tabs.tabs('getSelected'), //获取当前被选中的页面
						options : {
							//Title : '新标题',
							}
						}
					);
            	}
            }
		 })
		 
	});
	function materialFollowupAltList(id){
		if(id != null && typeof(id) != 'undefined'){
			parent.$.modalDialog({
				title : '物料交期变更记录',
				width : 800,
				height : 600,
				href : '${ctx}/materialFollowupAlt/materialFollowupAltPage?materialFollowupId='+id,
				buttons : [ {
					text : '关闭',
					handler : function() {
						parent.$.modalDialog.handler.dialog('close');
					}
				} ]
			});
		}
	}
	function countSection(){
		var total = $('#materialFollowupList').find('table .tabletitle .section').length;
		
		$('#materialFollowupList').find('table .tabletitle .section').each(function(index){
			index += 1
			//console.log("计算拆单数量")
			$(this).text("拆单:"+index+"/"+total)
		})
	}
</script>
<div id="content">
	<!-- 基础信息 -->
	<table border="0" cellpadding="0" cellspacing="0" class="ordertable">
        <tr class="tabletitle">
            <td colspan="8">基础信息： </td>
        </tr>
        <tr>
         	<td class="lefttd" width="10%">订单编号：</td>
            <td class="righttd" width="15%"><div id="orderNo"></div> </td>
            <td class="lefttd" width="10%">跟单名称:</td>
            <td class="righttd" width="15%"><div id="merchandiser"></div></td>
            <td class="lefttd" width="10%">业务名称： </td>
            <td class="" width="15%"><div id="seller"></div></td>   
            <td class="lefttd" width="10%">订单数量：</td>
	        <td class="righttd" width="15%"><div id="quantity"></div></td>    
            
        </tr>
      	<tr>
      		<td class="lefttd" >客户名称：</td>
            <td class="righttd" ><div id="clientName"></div></td> 
      	</tr>
    </table>
    <div id="materialFollowupList"></div>
   
</div>