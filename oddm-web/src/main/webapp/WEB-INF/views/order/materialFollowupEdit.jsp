<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
	.ordertable{background-color:#eee;width:100%;color:#666;margin-bottom:10px;font-size:0.8em;text-align:left;}
	.tabletitle{text-align:left; background-color:#c8d7e1;color:#333;font-size:1.4em; font-weight:500;}
	.tabletop{text-align:left; background-color:#DCE0E2;color:#333;font-size:1.2em; font-weight:500;}
	.ordertable tr td{padding:10px 0 10px 6px;}
	.ordertable tr th{padding:10px 0 10px 6px;}
	.inputs{border:1px solid #CCC;width:145px;padding:5px 0 2px 4px;font-size:12px;color:#999;border-radius: 4px;;height:22px}
	.lefttd{width:85px;}
    .lefttd90{width:90px; text-align:right;}
    .righttd{color:#069;}
    .bordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:center;border-collapse:collapse;}
	.bordertable td{border:1px solid #ccc;padding:10px 0 10px 6px;height:20px;}
	.textareacss{width:96%;border-radius: 5px;border: 0px;height: 48px}
	.choose{color: #06c;cursor: pointer;}
	.btn-default {border: 1px solid #DDDDDD;margin-right: 3px;padding: 7px 9px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #333;}
	.btn-default:hover {border: 1px solid #DDDDDD;margin-right: 3px;background: #04B5F9;padding: 7px 9px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #FFF;}
	.color_f60 {color: #f60;}
	.color_green {color: green;}
	.tip{height: 16px;width: 16px;display: inline-block;padding: 0px 4px;cursor: pointer;}
	.contain{margin-bottom: 10px}
	.hiddenMenu{display:none;}
	pre {white-space: pre-wrap;word-wrap: break-word;text-align: left;margin: 0}
</style>

<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/materialFollowup/editFinishAll')}">
	<script type="text/javascript">
		$.canEditFinishAll = true;//完结后的修改
	</script>
</c:if>
<%-- <c:if test="${fn:contains(sessionInfo.resourceList, '/materialFollowup/editFinishAll')}">
	<script type="text/javascript">
		$.canEditFinishAll = true;//完结后的修改
	</script>
</c:if> --%>
<c:if test="${fn:contains(sessionInfo.resourceList, '/materialFollowup/add')}">
	<script type="text/javascript">
		$.canAdd = true;
	</script>
</c:if>
<c:if test="${sessionInfo.hasRoles == true}">
	<script type="text/javascript">
		$.hasRoles = true;
	</script>
</c:if>
<script type="text/javascript">
	var orderId = '${orderId}';
	var orderQuantity;
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
               			orderQuantity = data[0].orderInfoDTO.quantity;
               			$('#clientName').html(data[0].orderInfoDTO.clientName);
               		}
           			var $content ;
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
            			if(data[i].state == null){
            				$content = $('<div class="contain"><form class="materialEditForm"><table border="0" cellpadding="0" cellspacing="0" class="ordertable" >'+
    	            		        '<tr class="tabletitle">'+
    	            		            '<th colspan="8"><span class="section"></span> <span class="color_f60 isFinish">&nbsp;&nbsp;(未齐料)</span> </th>'+
    	            		        '</tr>'+
    	            		          '<tr>'+
    	            		            '<td class="lefttd" width="10%">物料编码：</td>'+
    	            		            '<td class="righttd" width="15%"><input  class="mtCode inputs" value="'+ data[i].mtCode +'"></td>'+
    	            		            '<td class="lefttd" width="10%"> 生产顺位：'+
    	            		            '<td class="righttd" width="15%"><input class="prodLine inputs" value="'+ data[i].prodLine +'"></td>'+
    	            		            '<td class="lefttd" width="10%">批量：</td>'+
    	            		            '<td class="righttd"width="15%"><input class=" quality "> </td>'+
    	            		            '<td class="lefttd" width="10%"></td>'+
    	            		            '<td class="righttd" width="15%"> </td>'+
    	            		        '</tr>'+
    	            		        '<tr>'+
    	            		            '<td class="lefttd">料号确定日期：</td>'+
    	            		            '<td class="righttd"><input class="dateCommit " style="width:90%"></td>'+
    	            		            '<td class="lefttd">交货日期：</td>'+
    	            		            '<td class="righttd"><input class="dateDeliver " ></td>'+
    	            		            '<td class="lefttd" >订单下达日期：</td>'+
    	            		            '<td class="righttd" ><input class="dateSubmit "></td>'+
    	            		            '<td class="lefttd" >齐料日期：</td>'+
    	            		            '<td class="righttd" ><input class="dateFinish "></td>'+
    	            		        '</tr>'+
    	            		       ' <tr>'+         
    	            		           ' <td class="lefttd" >到今天为止天数 :'+
    	            		            '<td class="righttd" ><span class="dateTo">'+ data[i].dateTo +'</span></td>'+
    	            		            '<td class="lefttd"  >组装预计上线日期： </td>'+
    	            		            '<td class="righttd" ><input class="dateOnline "></td>'+
    	            		        '</tr>'+
    	            		        '<tr>'+
    	            		            '<td class="lefttd">规格：</td>'+
    	            		            '<td class="righttd" colspan="3"><textarea class="specification textareacss" rows="3">'+ data[i].specification +'</textarea></td>'+
    	            		            '<td class="lefttd">整机物料齐套状况：</td>'+
    	            		            '<td class="righttd" colspan="3"><textarea class="mtCondition textareacss" rows="3">'+ data[i].mtCondition +'</textarea></td>'+
    	            		       '</tr>'+
    	            		    '</table></form>'+
    	            		    '<div align="center">'+
    						   	 	'<button class="btn-default" onclick="submitMaterualFollow(this);">提交</button>'+
    						   		'<button class="btn-default" style="display:none"  onclick="finishMaterualFollow(this);">齐料完结</button>'+
    					    	'</div>'+
    					   	'</div>')
    						
                			$content.data('id',data[i].id)
                			$('#main').append($content);
                			/* $content.find('.section').validatebox({    
                			    validType: ['isBlank_','length[0,10]']  
                			}); */
                			$content.find('.mtCode').validatebox({
                				required : "required",
                			    validType: ['isBlank_','length[0,20]']  
                			});
                			$content.find('.prodLine').validatebox({    
                			    validType: ['isBlank_','length[0,64]']  
                			});
                			$content.find('.quality').numberbox({    
                				required : "required",
                				min:0,
                				max:2147483647,
                				/* validType: ['qualityValidate'] ,
                				"onChange":function(newValue,oldValue){// 计算总额  
                					var total = 0;
                					alert(6666)
                				} */
                			});
                			
                			$content.find('.quality').numberbox('setValue',data[i].quality)
                			
                			$content.find('.dateCommit').datebox({    
                				editable : false,
                				"onChange":function(newValue,oldValue){// 计算总额  
                					$(this).parents('table').find('.dateTo').text(countDateTo(newValue));
                				}
                			});
                			$content.find('.dateCommit').datebox('setValue',data[i].dateCommit)
                			
                			$content.find('.dateDeliver').datebox({    
                				editable : false,
                			});
                			$content.find('.dateDeliver').datebox('setValue',data[i].dateDeliver)
                			
                			$content.find('.dateSubmit').datebox({    
                				editable : false,
                			});
                			$content.find('.dateSubmit').datebox('setValue',data[i].dateSubmit)
                			
                			$content.find('.dateFinish').datebox({    
                				editable : false,
                			});
                			$content.find('.dateFinish').datebox('setValue',data[i].dateFinish)
                			
                			$content.find('.dateOnline').datebox({    
                				editable : false,
                			});
                			$content.find('.dateOnline').datebox('setValue',data[i].dateOnline)
                			
                			$content.find('.specification').validatebox({    
                			    validType: ['isBlank_','length[0,200]']  
                			});
                			$content.find('.mtCondition').validatebox({    
                			    validType: ['isBlank_','length[0,500]']  
                			});
            			}else{
            				
            				if(data[i].state < 3  ){
            				$content = $('<div class="contain"><form class="materialEditForm"><table border="0" cellpadding="0" cellspacing="0" class="ordertable" >'+
    	            		        '<tr class="tabletitle">'+
    	            		            '<th colspan="8"><span class="section"></span> <span class="color_f60 isFinish">&nbsp;&nbsp;(未齐料)</span> </th>'+
    	            		        '</tr>'+
    	            		          '<tr>'+
    	            		            '<td class="lefttd" width="10%">物料编码：</td>'+
    	            		            '<td class="righttd" width="15%"><input  class="mtCode inputs" value="'+ data[i].mtCode +'"></td>'+
    	            		            '<td class="lefttd" width="10%"> 生产顺位：'+
    	            		            '<td class="righttd" width="15%"><input class="prodLine inputs" value="'+ data[i].prodLine +'"></td>'+
    	            		            '<td class="lefttd" width="10%">批量：</td>'+
    	            		            '<td class="righttd"width="15%"><input class=" quality "> </td>'+
    	            		            '<td class="lefttd" width="10%"></td>'+
    	            		            '<td class="righttd" width="15%"> </td>'+
    	            		        '</tr>'+
    	            		        '<tr>'+
    	            		            '<td class="lefttd">料号确定日期：</td>'+
    	            		            '<td class="righttd"><input class="dateCommit "></td>'+
    	            		            '<td class="lefttd">交货日期：</td>'+
    	            		            '<td class="righttd"><input class="dateDeliver " ></td>'+
    	            		            '<td class="lefttd" >订单下达日期：</td>'+
    	            		            '<td class="righttd" ><input class="dateSubmit "></td>'+
    	            		            '<td class="lefttd" >齐料日期：</td>'+
    	            		            '<td class="righttd" ><input class="dateFinish "></td>'+
    	            		        '</tr>'+
    	            		       ' <tr>'+         
    	            		           ' <td class="lefttd" >到今天为止天数 :'+
    	            		            '<td class="righttd" ><span class="dateTo">'+ data[i].dateTo +'</span></td>'+
    	            		            '<td class="lefttd"  >组装预计上线日期： </td>'+
    	            		            '<td class="righttd" ><input class="dateOnline "></td>'+
    	            		        '</tr>'+
    	            		        '<tr>'+
    	            		            '<td class="lefttd">规格：</td>'+
    	            		            '<td class="righttd" colspan="3"><textarea class="specification textareacss" rows="3">'+ data[i].specification +'</textarea></td>'+
    	            		            '<td class="lefttd">整机物料齐套状况：</td>'+
    	            		            '<td class="righttd" colspan="3"><textarea class="mtCondition textareacss" rows="3">'+ data[i].mtCondition +'</textarea></td>'+
    	            		       '</tr>'+
    	            		    '</table></form>'+
    	            		    '<div align="center">'+
    						   	 	'<button class="btn-default" onclick="submitMaterualFollow(this);">提交</button>'+
    						   	 	'<button class="btn-default" onclick="finishMaterualFollow(this);">齐料完结</button>'+
    					    	'</div>'+
    					   	'</div>')
    						
                			$content.data('id',data[i].id)
                			$('#main').append($content);
                			/* $content.find('.section').validatebox({    
                			    validType: ['isBlank_','length[0,10]']  
                			}); */
                			$content.find('.mtCode').validatebox({
                				required : "required",
                			    validType: ['isBlank_','length[0,20]']  
                			});
                			$content.find('.prodLine').validatebox({    
                			    validType: ['isBlank_','length[0,64]']  
                			});
                			$content.find('.quality').numberbox({    
                				required : "required",
                				min:0,
                				max:2147483647,
                				/* validType: ['qualityValidate'] ,
                				"onChange":function(newValue,oldValue){// 计算总额  
                					var total = 0;
                					alert(6666)
                				} */
                			});
                			
                			$content.find('.quality').numberbox('setValue',data[i].quality)
                			
                			$content.find('.dateCommit').datebox({    
                				editable : false,
                				"onChange":function(newValue,oldValue){// 计算总额  
                					$(this).parents('table').find('.dateTo').text(countDateTo(newValue));
                				}
                			});
                			$content.find('.dateCommit').datebox('setValue',data[i].dateCommit)
                			
                			$content.find('.dateDeliver').datebox({    
                				editable : false,
                			});
                			$content.find('.dateDeliver').datebox('setValue',data[i].dateDeliver)
                			
                			$content.find('.dateSubmit').datebox({    
                				editable : false,
                			});
                			$content.find('.dateSubmit').datebox('setValue',data[i].dateSubmit)
                			
                			$content.find('.dateFinish').datebox({    
                				editable : false,
                			});
                			$content.find('.dateFinish').datebox('setValue',data[i].dateFinish)
                			
                			$content.find('.dateOnline').datebox({    
                				editable : false,
                			});
                			$content.find('.dateOnline').datebox('setValue',data[i].dateOnline)
                			
                			$content.find('.specification').validatebox({    
                			    validType: ['isBlank_','length[0,200]']  
                			});
                			$content.find('.mtCondition').validatebox({    
                			    validType: ['isBlank_','length[0,500]']  
                			});
					   	}else if(data[i].state >= 3){
					   		//大于3(齐料完结),以后可在这里进行权限判断（齐料完结后依然可以编辑）
					   		if($.canEditFinishAll){
					   			$content = $('<div class="contain"><form class="materialEditForm"><table border="0" cellpadding="0" cellspacing="0" class="ordertable" >'+
	    	            		        '<tr class="tabletitle">'+
	    	            		            '<th colspan="8"><span class="section"></span> <span class="color_green isFinish">&nbsp;&nbsp;(已齐料)</span> </th>'+
	    	            		        '</tr>'+
	    	            		          '<tr>'+
	    	            		            '<td class="lefttd" width="10%">物料编码：</td>'+
	    	            		            '<td class="righttd" width="15%"><input  class="mtCode inputs" value="'+ data[i].mtCode +'"></td>'+
	    	            		            '<td class="lefttd" width="10%"> 生产顺位：'+
	    	            		            '<td class="righttd" width="15%"><input class="prodLine inputs" value="'+ data[i].prodLine +'"></td>'+
	    	            		            '<td class="lefttd" width="10%">批量：</td>'+
	    	            		            '<td class="righttd"width="15%"><input class=" quality"> </td>'+
	    	            		            '<td class="lefttd" width="10%"></td>'+
	    	            		            '<td class="righttd" width="15%"> </td>'+
	    	            		        '</tr>'+
	    	            		        '<tr>'+
	    	            		            '<td class="lefttd">料号确定日期：</td>'+
	    	            		            '<td class="righttd"><input class="dateCommit "></td>'+
	    	            		            '<td class="lefttd">交货日期：</td>'+
	    	            		            '<td class="righttd"><input class="dateDeliver " ></td>'+
	    	            		            '<td class="lefttd" >订单下达日期：</td>'+
	    	            		            '<td class="righttd" ><input class="dateSubmit "></td>'+
	    	            		            '<td class="lefttd" >齐料日期：</td>'+
	    	            		            '<td class="righttd" ><input class="dateFinish "></td>'+
	    	            		        '</tr>'+
	    	            		       ' <tr>'+         
	    	            		           ' <td class="lefttd" >到今天为止天数 :'+
	    	            		            '<td class="righttd" ><span class="dateTo">'+ data[i].dateTo +'</span></td>'+
	    	            		            '<td class="lefttd"  >组装预计上线日期： </td>'+
	    	            		            '<td class="righttd" ><input class="dateOnline "></td>'+
	    	            		        '</tr>'+
	    	            		        '<tr>'+
	    	            		            '<td class="lefttd">规格：</td>'+
	    	            		            '<td class="righttd" colspan="3"><textarea class="specification textareacss" rows="3">'+ data[i].specification +'</textarea></td>'+
	    	            		            '<td class="lefttd">整机物料齐套状况：</td>'+
	    	            		            '<td class="righttd" colspan="3"><textarea class="mtCondition textareacss" rows="3">'+ data[i].mtCondition +'</textarea></td>'+
	    	            		       '</tr>'+
	    	            		    '</table></form>'+
	    	            		    '<div align="center">'+
	    						   	 	'<button class="btn-default" onclick="finishMaterualFollow(this);">提交</button>'+
	    					    	'</div>'+
	    					   	'</div>')
	    						
	                			$content.data('id',data[i].id)
	                			$('#main').append($content);
	                			/* $content.find('.section').validatebox({    
	                			    validType: ['isBlank_','length[0,10]']  
	                			}); */
	                			$content.find('.mtCode').validatebox({
	                				required : "required",
	                			    validType: ['isBlank_','length[0,20]']  
	                			});
	                			$content.find('.prodLine').validatebox({    
	                			    validType: ['isBlank_','length[0,64]']  
	                			});
	                			$content.find('.quality').numberbox({    
	                				required : "required",
	                				min:0,
	                				max:2147483647,
	                				validType: ['qualityValidate']  
	                			});
	                			$content.find('.quality').numberbox('setValue',data[i].quality)
	                			
	                			$content.find('.dateCommit').datebox({    
	                				editable : false,
	                				"onChange":function(newValue,oldValue){// 计算总额  
	                					$(this).parents('table').find('.dateTo').text(countDateTo(newValue));
	                				}
	                			});
	                			$content.find('.dateCommit').datebox('setValue',data[i].dateCommit)
	                			
	                			$content.find('.dateDeliver').datebox({    
	                				editable : false,
	                			});
	                			$content.find('.dateDeliver').datebox('setValue',data[i].dateDeliver)
	                			
	                			$content.find('.dateSubmit').datebox({    
	                				editable : false,
	                			});
	                			$content.find('.dateSubmit').datebox('setValue',data[i].dateSubmit)
	                			
	                			$content.find('.dateFinish').datebox({    
	                				editable : false,
	                			});
	                			$content.find('.dateFinish').datebox('setValue',data[i].dateFinish)
	                			
	                			$content.find('.dateOnline').datebox({    
	                				editable : false,
	                			});
	                			$content.find('.dateOnline').datebox('setValue',data[i].dateOnline)
	                			
	                			$content.find('.specification').validatebox({    
	                			    validType: ['isBlank_','length[0,200]']  
	                			});
	                			$content.find('.mtCondition').validatebox({    
	                			    validType: ['isBlank_','length[0,500]']  
	                			});
					   		}else{
					   			$content = $('<div class="contain"><form class="materialAddForm"><table border="0" cellpadding="0" cellspacing="0" class="ordertable ">'+
	                				    '<tr class="tabletitle">'+
	                				    	'<th colspan="8"><span class="section"></span><span class="color_green isFinish">&nbsp;&nbsp;(已齐料)</span> </th>'+
	                			        '</tr>'+
	                			        '<tr>'+
	                			            '<td class="lefttd" width="10%">物料编号：</td>'+
	                			            '<td class="righttd" width="15%">'+data[i].mtCode+'</td>'+
	                			            '<td class="lefttd" width="10%"> 生产顺位：</td>'+
	                			            '<td class="righttd" width="15%">'+data[i].prodLine+'</td>'+
	                			        	'<td class="lefttd" width="10%">批量：</td>'+
	                			            '<td class="righttd" width="15%"><span class=" quality ">'+data[i].quality+'</span></td>'+
	                			            '<td class="lefttd" width="10%"></td>'+
	                			            '<td class="righttd" width="15%"> </td>'+
	                			        '</tr>'+
	                			        '<tr>'+
	                			            '<td class="lefttd">料号确定日期：</td>'+
	                			            '<td class="righttd">'+data[i].dateCommit+'</td>'+
	                			            '<td class="lefttd">交货日期：</td>'+
	                			            '<td class="righttd">'+data[i].dateDeliver+'</td>'+
	                			        	'<td class="lefttd" >订单下达日期：</td>'+
	                			            '<td class="righttd" >'+data[i].dateSubmit+'</td>'+
	                			            '<td class="lefttd" >齐料日期：</td>'+
	                			            '<td class="righttd" >'+data[i].dateFinish+'</td>'+
	                			        '</tr>'+
	                			        '<tr>'+
	    	            			        ' <td class="lefttd" >到今天为止天数 :'+
	    		        		            '<td class="righttd" ><span class="">'+data[i].dateTo+'</span></td>'+
	                			            '<td class="lefttd" >组装预计上线日期： </td>'+
	                			            '<td class="righttd" >'+data[i].dateOnline+'</td>'+
	                			        '</tr>'+
	                			        '<tr>'+
	                			            '<td class="lefttd">规格：</td>'+
	                			            '<td class="righttd" colspan="3"><pre class="specification ">'+data[i].specification+'</pre></td>'+
	                			            '<td class="lefttd">整机物料齐套状况：</td>'+
	                			            '<td class="righttd" colspan="3"><pre class="mtCondition " >'+data[i].mtCondition+'</pre></td>'+
	                			        '</tr>'+
	                			    '</table></form>'+
	                			  '</div>')
	                			$content.data('id',data[i].id)
	                  			$('#main').append($content);
					   		}
					   	}
					   }
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
	
	function submitMaterualFollow(obj){//false 代表编辑     true 代表齐料完结
		var id = typeof($(obj).parent().parent().data('id')) == 'undefined' ? null : $(obj).parent().parent().data('id');
		var total =  0 ;
		
		var isValid = $(obj).parent().parent().find('.materialEditForm').form('validate');
		console.log("materialEditForm 验证"+isValid)
		if(!isValid){
			return;
		}
		$('#main').find('.contain table').each(function(){
			//console.log($(this).find('.quality').val() == '')
			var valueTemp = $(this).find('.quality').val() == '' ? $(this).find('.quality').text() : $(this).find('.quality').val();
			//console.log($(this).find('.quality').text())
			//console.log($(this).find('.quality').text()+"...."+valueTemp)
			if(valueTemp != null && typeof(valueTemp) != 'undefined' && valueTemp != ''){
				total += parseInt(valueTemp);
			}
			
		})
		console.log(total+"......"+orderQuantity)
		if(total > orderQuantity){
			parent.$.messager.alert('提示', '当前的批量总数大于订单数量！', 'info');
			return;
		}
		
		if(id == null){
			addMaterualFollow(obj);
		}else{
			editFun(obj, "${ctx}/materialFollowup/editForSubmit",false,id);
		}
		
	}
	function finishMaterualFollow(obj){
		var id = typeof($(obj).parent().parent().data('id')) == 'undefined' ? null : $(obj).parent().parent().data('id');
		var table = $(obj).parent().parent().find('table');//获取table
		
		var isValid = $(obj).parent().parent().find('.materialEditForm').form('validate');
		console.log("materialEditForm 验证"+isValid)
		if(!isValid){
			return;
		}
		//需要验证
		var prodLine = table.find('.prodLine').val().trim();
		if(prodLine.length < 1){
			parent.$.messager.alert('提示', '生产顺位不能为空！', 'info');
			table.find('.prodLine').focus();
			table.find('.prodLine').select(); 
		    return false;
		}
		/* var quality = table.find('.quality').val().trim();
		if(quality.length < 1){
			parent.$.messager.alert('提示', '批量不能为空！', 'info');
			table.find('.quality').focus();
			table.find('.quality').select(); 
		    return false;
		} */
		var dateCommit = table.find('.dateCommit').datebox('getValue');
		if(dateCommit.length < 1){
			parent.$.messager.alert('提示', '料号确定日期不能为空！', 'info');
			table.find('.dateCommit').focus();
			table.find('.dateCommit').select(); 
		    return false;
		}
		
		var dateDeliver = table.find('.dateDeliver').datebox('getValue');
		if(dateDeliver.length < 1){
			parent.$.messager.alert('提示', '交货日期不能为空！', 'info');
			table.find('.dateDeliver').focus();
			table.find('.dateDeliver').select(); 
		    return false;
		}
		var dateSubmit = table.find('.dateSubmit').datebox('getValue');
		if(dateSubmit.length < 1){
			parent.$.messager.alert('提示', '订单下达日期不能为空！', 'info');
			table.find('.dateSubmit').focus();
			table.find('.dateSubmit').select(); 
		    return false;
		}
		var dateFinish = table.find('.dateFinish').datebox('getValue');
		if(dateFinish.length < 1){
			parent.$.messager.alert('提示', '齐料日期不能为空！', 'info');
			table.find('.dateFinish').focus();
			table.find('.dateFinish').select(); 
		    return false;
		}
		var dateOnline = table.find('.dateOnline').datebox('getValue');
		if(dateOnline.length < 1){
			parent.$.messager.alert('提示', '组装上线日期不能为空！', 'info');
			table.find('.dateOnline').focus();
			table.find('.dateOnline').select(); 
		    return false;
		}
		var specification = table.find('.specification').val().trim();
		if(specification.length < 1){
			parent.$.messager.alert('提示', '规格不能为空！', 'info');
			table.find('.specification').focus();
			table.find('.specification').select(); 
		       return false;
		}
		var total =  0 ;
		$('#main').find('.contain table').each(function(){
			//console.log($(this).find('.quality').val() == '')
			var valueTemp = $(this).find('.quality').val() == '' ? $(this).find('.quality').text() : $(this).find('.quality').val();
			//console.log($(this).find('.quality').text())
			//console.log($(this).find('.quality').text()+"...."+valueTemp)
			if(valueTemp != null && typeof(valueTemp) != 'undefined' && valueTemp != ''){
				total += parseInt(valueTemp);
			}
			
		})
		console.log(total+"......"+orderQuantity)
		if(total > orderQuantity){
			parent.$.messager.alert('提示', '当前的批量总数大于订单数量！', 'info');
			return;
		}
		
		
		if(id != null){
			parent.$.messager.confirm('询问', '您是否确定以齐料完结？完结后未经授权不可修改！', function(b) {
				if (b) {
					editFun(obj, "${ctx}/materialFollowup/editForFinish",true,id);
				}
			});
		}
	}
	function editFun(obj,url,flag,id){
		var me = $(obj);
		var prev = me.prev();
		var table = me.parent().parent().find('table');//获取table
		/* var isValid = $(obj).parent().parent().find('.materialEditForm').form('validate');
		console.log("materialEditForm 验证"+isValid)
		if(!isValid){
			return;
		} */
		
		
		
		//var section = typeof(table.find('.section').val()) == 'undefined' ? null : table.find('.section').val().trim();
		var mtCode = typeof(table.find('.mtCode').val()) == 'undefined' ? null : table.find('.mtCode').val().trim();
		var prodLine = typeof(table.find('.prodLine').val()) == 'undefined' ? null : table.find('.prodLine').val().trim();
		var quality = typeof(table.find('.quality').val()) == 'undefined' ? null : table.find('.quality').val().trim();
		
		var dateCommit = typeof(table.find('.dateCommit').datebox('getValue')) == 'undefined' ? null : table.find('.dateCommit').datebox('getValue');
		var dateDeliver = typeof(table.find('.dateDeliver').datebox('getValue')) == 'undefined' ? null : table.find('.dateDeliver').datebox('getValue');
		var dateSubmit = typeof(table.find('.dateSubmit').datebox('getValue')) == 'undefined' ? null : table.find('.dateSubmit').datebox('getValue');
		var dateFinish = typeof(table.find('.dateFinish').datebox('getValue')) == 'undefined' ? null : table.find('.dateFinish').datebox('getValue');
		var dateOnline = typeof(table.find('.dateOnline').datebox('getValue')) == 'undefined' ? null : table.find('.dateOnline').datebox('getValue');
		
		var specification = typeof(table.find('.specification').val()) == 'undefined' ? null : table.find('.specification').val().trim();
		var mtCondition = typeof(table.find('.mtCondition').val()) == 'undefined' ? null : table.find('.mtCondition').val().trim();
		
		var dateTo = typeof(table.find('.dateTo').text()) == 'undefined' ? null : table.find('.dateTo').text().trim();
		
		var params = {
				 id : id,
				// section : section,
				 mtCode : mtCode,
				 prodLine : prodLine,
				 dateSubmit : dateSubmit,
				 dateCommit : dateCommit,
				 dateDeliver : dateDeliver,
				 quality : quality,
				 specification : specification,
				 dateFinish : dateFinish,
				 dateOnline : dateOnline,
				 mtCondition : mtCondition,
				 orderId : orderId
		}
		if(orderId != null){
			 progressLoad();
			 $.ajax({
	         	type: 'POST',
	             url: url ,
	             data: JSON.stringify(params) ,
	             dataType: "json",
	             contentType: "application/json; charset=utf-8",
	             async: false,
	             success: function(data){
	            	 if(data.success){
	            		 parent.$.messager.alert('提示', '编辑成功');
	            		 if(flag){
	            			 if(!$.canEditFinishAll){
	            				 var cotainDiv = table.parents('.contain');
		                		 cotainDiv.empty();
		                		 var $content = $('<form class="materialAddForm"><table border="0" cellpadding="0" cellspacing="0" class="ordertable ">'+
		                				    '<tr class="tabletitle">'+
		                				    	'<th colspan="8"><span class="section"></span><span class="color_green isFinish">&nbsp;&nbsp;(已齐料)</span>  </th>'+
		                			        '</tr>'+
		                			        '<tr>'+
		                			            '<td class="lefttd" width="10%">物料编号：</td>'+
		                			            '<td class="righttd" width="15%">'+mtCode+'</td>'+
		                			            '<td class="lefttd" width="10%"> 生产顺位：</td>'+
		                			            '<td class="righttd" width="15%">'+prodLine+'</td>'+
		                			        	'<td class="lefttd" width="10%">批量：</td>'+
		                			            '<td class="righttd" width="15%"><span class="quality">'+quality+'</span></td>'+
		                			            '<td class="lefttd" width="10%"></td>'+
		                			            '<td class="righttd" width="15%"> </td>'+
		                			        '</tr>'+
		                			        '<tr>'+
		                			            '<td class="lefttd">料号确定日期：</td>'+
		                			            '<td class="righttd">'+dateCommit+'</td>'+
		                			            '<td class="lefttd">交货日期：</td>'+
		                			            '<td class="righttd">'+dateDeliver+'</td>'+
		                			        	'<td class="lefttd" >订单下达日期：</td>'+
		                			            '<td class="righttd" >'+dateSubmit+'</td>'+
		                			            '<td class="lefttd" >齐料日期：</td>'+
		                			            '<td class="righttd" >'+dateFinish+'</td>'+
		                			        '</tr>'+
		                			        '<tr>'+
		    	            			        ' <td class="lefttd" >到今天为止天数 :'+
		    		        		            '<td class="righttd" ><span class="dateTo">'+dateTo+'</span></td>'+
		                			            '<td class="lefttd" >组装预计上线日期： </td>'+
		                			            '<td class="righttd" >'+dateOnline+'</td>'+
		                			        '</tr>'+
		                			        '<tr>'+
		                			            '<td class="lefttd">规格：</td>'+
		                			            '<td class="righttd" colspan="3"><pre class="specification ">'+specification+'</pre></td>'+
		                			            '<td class="lefttd">整机物料齐套状况：</td>'+
		                			            '<td class="righttd" colspan="3"><pre class="mtCondition " >'+mtCondition+'</pre></td>'+
		                			        '</tr>'+
		                			    '</table></form>')
		                		cotainDiv.append($content)
		                		countSection()
	            			 }else{
	            				 var cotainDiv = table.parents('.contain');
	            				 table.find('.isFinish').text('已齐料').removeClass('color_f60').addClass('color_green');
	            				 me.prev().css('display','none');
	            				 me.html('提交')
	            			 }
	            		 }
	            	 }else{
	            		 parent.$.messager.alert('提示', '编辑失败');
	            	 }
	             }
	        }) 
	        progressClose();
		}
	}
	
	//添加数据库操作
	function addMaterualFollow(obj){
		
		var me = $(obj);
		var table = me.parent().parent().find('table');//获取table
		
		var isValid = $(obj).parent().parent().find('.materialEditForm').form('validate');
		console.log("materialEditForm 验证"+isValid)
		if(!isValid){
			return;
		}
		
		//var section = typeof(table.find('.section').val()) == 'undefined' ? null : table.find('.section').val().trim();
		var mtCode = typeof(table.find('.mtCode').val()) == 'undefined' ? null : table.find('.mtCode').val().trim();
		var prodLine = typeof(table.find('.prodLine').val()) == 'undefined' ? null : table.find('.prodLine').val().trim();
		var quality = typeof(table.find('.quality').val()) == 'undefined' ? null : table.find('.quality').val().trim();
		
		var dateCommit = typeof(table.find('.dateCommit').datebox('getValue')) == 'undefined' ? null : table.find('.dateCommit').datebox('getValue');
		var dateDeliver = typeof(table.find('.dateDeliver').datebox('getValue')) == 'undefined' ? null : table.find('.dateDeliver').datebox('getValue');
		var dateSubmit = typeof(table.find('.dateSubmit').datebox('getValue')) == 'undefined' ? null : table.find('.dateSubmit').datebox('getValue');
		var dateFinish = typeof(table.find('.dateFinish').datebox('getValue')) == 'undefined' ? null : table.find('.dateFinish').datebox('getValue');
		var dateOnline = typeof(table.find('.dateOnline').datebox('getValue')) == 'undefined' ? null : table.find('.dateOnline').datebox('getValue');
		
		var specification = typeof(table.find('.specification').val()) == 'undefined' ? null : table.find('.specification').val().trim();
		var mtCondition = typeof(table.find('.mtCondition').val()) == 'undefined' ? null : table.find('.mtCondition').val().trim();
		
		var params = {
				// section : section,
				 mtCode : mtCode,
				 prodLine : prodLine,
				 dateSubmit : dateSubmit,
				 dateCommit : dateCommit,
				 dateDeliver : dateDeliver,
				 quality : quality,
				 specification : specification,
				 dateFinish : dateFinish,
				 dateOnline : dateOnline,
				 mtCondition : mtCondition,
				 orderId : orderId
		}
		progressLoad();
		$.ajax({
			type: 'POST',
			url: "${ctx}/materialFollowup/add" ,
			data: JSON.stringify(params) ,
			dataType: "json",
			contentType: "application/json; charset=utf-8",
			async: false,
			success: function(data){
            	 if(data.success){
            		 parent.$.messager.alert('提示', data.msg);
            		 me.parent().parent().data('id',data.obj)//存放id在外层div
            		 me.parent().parent().find('table > .tabletitle > td > .tip').css('display','none')
            		 me.next().css('display','inline-block')
            		 countSection()
            	 }else{
            		 parent.$.messager.alert('提示', data.msg);
            	 }
             }
        }) 
        progressClose();
	}
	function countDateTo(dataCommit){
		var day = ( new Date() - new Date(dataCommit).getTime() )/(1000*3600*24);
		if(day > 0){
			return parseInt(day);
		}else{
			return 0;
		}
	}
	function countSection(){
		var total = $('#main').find('table .tabletitle .section').length;
		
		$('#main').find('table .tabletitle .section').each(function(index){
			index += 1
			//console.log("计算拆单数量")
			$(this).text("拆单:"+index+"/"+total)
		})
	}
	
	function addFun(){
		if(!$.canAdd){
			parent.$.messager.alert('提示', '您没有添加权限！！请联系管理员。');
			return;
		}
		var total =  0 ;
		$('#main').find('.contain table').each(function(){
			//console.log($(this).find('.quality').val() == '')
			var valueTemp = $(this).find('.quality').val() == '' ? $(this).find('.quality').text() : $(this).find('.quality').val();
			//console.log($(this).find('.quality').text())
			//console.log($(this).find('.quality').text()+"...."+valueTemp)
			if(valueTemp != null && typeof(valueTemp) != 'undefined' && valueTemp != ''){
				total += parseInt(valueTemp);
			}
			
		})
		console.log(total+"......"+orderQuantity)
		if(total - orderQuantity >= 0){
			parent.$.messager.alert('提示', '当前的批量总数:'+total+',大于等于订单数量:'+orderQuantity+',无法继续添加交期！', 'info');
			return;
		}
		var $content = $('<div class="contain"><form class="materialEditForm"><table border="0" cellpadding="0" cellspacing="0" class="ordertable ">'+
						    '<tr class="tabletitle">'+
					            '<th colspan="7"><span class="section"></span> <span class="color_f60 isFinish">&nbsp;&nbsp;(未齐料)</span></th>'+
					            '<td align="right" style="border-left: 0px;padding: 0px">'+
									'<span onclick="deleteFun(this);" class="icon-del tip"  title="删除"></span>'+
								'</td>'+
					        '</tr>'+
					        '<tr>'+
					            '<td class="lefttd" width="10%">物料编号：</td>'+
					            '<td class="righttd" width="15%"><input class="mtCode inputs"></td>'+
					            '<td class="lefttd" width="10%"> 生产顺位：</td>'+
					            '<td class="righttd" width="15%"><input class="prodLine inputs"></td>'+
					        	'<td class="lefttd" width="10%">批量：</td>'+
					            '<td class="righttd" width="15%"><input class=" quality" /></td>'+
					            '<td class="lefttd" width="10%"></td>'+
					            '<td class="righttd" width="15%"></td>'+
					        '</tr>'+
					        '<tr>'+
					            '<td class="lefttd">料号确定日期：</td>'+
					            '<td class=""><input class="dateCommit "></td>'+
					            '<td class="lefttd">交货日期：</td>'+
					            '<td class=""><input class="dateDeliver " ></td>'+
					        	'<td class="lefttd" >订单下达日期：</td>'+
					            '<td class="righttd" ><input class="dateSubmit "></td>'+
					            '<td class="lefttd" >齐料日期：</td>'+
					            '<td class="righttd" ><input class="dateFinish "></td>'+
					        '</tr>'+
					        '<tr>'+
						        '<td class="lefttd" >到今天为止天数 :'+
	        		            '<td class="righttd" ><span class="dateTo"></span></td>'+
					            '<td class="lefttd" >组装预计上线日期： </td>'+
					            '<td class="righttd" > <input class="dateOnline "></td>'+
					        '</tr>'+
					        '<tr>'+
					            '<td class="lefttd">规格：</td>'+
					            '<td class="righttd" colspan="3"><textarea class="specification textareacss" rows="3"></textarea></td>'+
					            '<td class="lefttd">整机物料齐套状况：</td>'+
					            '<td class="righttd" colspan="3"><textarea class="mtCondition textareacss" rows="3"></textarea></td>'+
					        '</tr>'+
					    '</table></form>'+
					    '<div align="center">'+
					   	 	'<button class="btn-default" onclick="submitMaterualFollow(this);">提交</button>'+
					   	 	'<button class="btn-default hiddenMenu" onclick="finishMaterualFollow(this);">齐料完结</button>'+
				    	'</div>'+
					   	'</div>')
		$('#main').append($content)
		/* $content.find('.section').validatebox({    
		    validType: ['isBlank_','length[0,10]']  
		}); */
		$content.find('.mtCode').validatebox({
			required : "required",
		    validType: ['isBlank_','length[0,20]']  
		});
		$content.find('.prodLine').validatebox({    
		    validType: ['isBlank_','length[0,64]']  
		});
		$content.find('.quality').numberbox({  
			required : "required",
			min:0,
			max:2147483647, 
			/* validType: ['qualityValidate'] ,
			"onChange":function(newValue,oldValue){// 计算总额  
				var total = 0;
				alert(6666)
			}   */
		});
		$content.find('.dateCommit').datebox({    
			editable : false,
			"onChange":function(newValue,oldValue){// 计算到今天为止天数
				$(this).parents('table').find('.dateTo').text(countDateTo(newValue));
			}
		});
		$content.find('.dateDeliver').datebox({    
			editable : false,
		});
		$content.find('.dateSubmit').datebox({    
			editable : false,
		});
		$content.find('.dateFinish').datebox({    
			editable : false,
		});
		$content.find('.dateOnline').datebox({    
			editable : false,
		});
		$content.find('.specification').validatebox({    
		    validType: ['isBlank_','length[0,200]']  
		});
		$content.find('.mtCondition').validatebox({    
		    validType: ['isBlank_','length[0,500]']  
		});
		countSection()
	}
	function deleteFun(obj){
		
		$(obj).parents('.contain').remove();
		countSection()
	}
</script>
<body style="padding:10px">
<div id='main'>
	<!-- 基础信息 -->
	<table border="0" cellpadding="0" cellspacing="0" class="ordertable">
        <tr class="tabletitle">
            <td colspan="7">基础信息： </td>
            <td align="right" style="border-left: 0px;padding: 0px">
				<span onclick="addFun();" class="icon-add tip"  title="添加"></span>
			</td>
        </tr>
        <tr>
         	<td class="lefttd" width="10%">订单编号：</td>
            <td class="righttd" width="15%"><div id="orderNo"></div> </td>
            <td class="lefttd" width="10%">业务名称： </td>
            <td class="righttd" width="15%"><div id="seller"></div></td>       
            <td class="lefttd" width="10%">跟单名称:</td>
            <td class="righttd" width="15%"><div id="merchandiser"></div></td>
            <td class="lefttd" width="10%">订单数量：</td>
            <td class="righttd" width="15%"><div id="quantity"></div></td> 
        </tr>
        <tr>
        	<td class="lefttd">客户名称：</td>
            <td class="righttd"><div id="clientName"></div></td> 
        </tr>
       
    </table>
</div>
</body>