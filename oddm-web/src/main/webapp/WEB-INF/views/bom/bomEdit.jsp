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
	.ordertable tr td{padding:3px 0 3px 5px;}
	.ordertable tr th{padding:3px 0 3px 5px;}
	.tabletop th{padding:3px 0 3px 0px;text-align: center;}
	.inputs{border:1px solid #CCC;width:100%;padding:5px 0 2px 4px;font-size:12px;color:#069;border-radius: 4px;}
	.lefttd{/* width:65px; */ text-align:right;}
    .righttd{color:#069;/* width: 187px */}
    .bordertable{background-color:#F2F2F2;width:100%;color:#666;margin-bottom:10px;font-size:0.8em;text-align:center;border-collapse:collapse;table-layout:fixed;}
	.bordertable tr td{/* border:1px solid #ccc; */padding:3px 0 3px 0px;height:20px;word-wrap: break-word}
	.bordertable tr th{/* border:1px solid #ccc; */padding:3px 0 3px 0px;height:20px;word-wrap: break-word}
	.textareacss{width:100%;border:1px solid #CCC;text-align: left;font-size:12px;color:#069;border-radius: 4px;height: 40px;}
	.textareacssMain{width:100%;border:1px solid #CCC;text-align: left;font-size:12px;color:#069;border-radius: 4px;height: 45px;}
	.choose{color: #06c;cursor: pointer;}
	.color_f60 {color: #f60;}
	.color069 {color :#069}
	.text_left{text-align: left;}
	.text_center{text-align: center;}
	.tip{height: 16px;width: 16px;display: inline-block;padding: 0px 4px;cursor: pointer;}
	.btn-default {border: 1px solid #DDDDDD;background: white;padding: 10px 18px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #333;}
	.btn-default:hover {border: 1px solid #DDDDDD;background: #04B5F9;padding: 10px 18px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #FFF;}
	.showContactCss{cursor: pointer;color: #06c;text-decoration: underline;}
	.choose{color: #06c;cursor: pointer;}
	.validatebox-invalid {background-color: #FFF1F1;border-color: #FFC1C1;}/* 验证加深颜色 */
	.textbox .textbox-text{color: #069}
</style>

<script type="text/javascript">
	var bomId = "${bomId}";
	var orderId ;
	var state ;
	var separator = "——";
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
			url:"${ctx}/bom/get?id="+ bomId,
			type:"GET",
			dataType: "json",
			async: false,
            contentType: "application/json; charset=utf-8", 
            success: function(data){
            	if(data != null){
            		console.log(data)
            		//$('#title').val(data.title);
            		$('#maker').val(data.maker);
            		$('#date').datebox('setValue',data.dateString);
            		$('#version').val(data.version);
            		$('#materialCode').val(data.materialCode);
            		$('#productName').val(data.productName);
            		//$('#brand').val(data.brand);
            		$('#specification').val(data.specification);
            		$('#description').val(data.description);
            		state = data.state;
            		$('#materialCode').validatebox({  
            			required : true,
					    validType: ['isBlank_','length[0,20]']  
					});
            		if(data.orderInfoDTO != null){
            			orderId = data.orderInfoDTO.id;
            			$('#orderNo').html(data.orderInfoDTO.orderNo);
                		$('#productFactory').html(data.orderInfoDTO.productFactory);
                		$('#quantity').html(data.orderInfoDTO.quantity);
                		$('#seller').html(data.orderInfoDTO.seller);
            		}
            		if(data.bomMaterialRefDTOs != null && data.bomMaterialRefDTOs.length > 0){
            			var bomMaterialRefDTOs = data.bomMaterialRefDTOs;
            			for(var i in bomMaterialRefDTOs){
            				
            				var bomMaterialRef = bomMaterialRefDTOs[i];
            				var id = bomMaterialRef.id == null ? '' : bomMaterialRef.id;
            				var materialCode = bomMaterialRef.materialCode == null ? '' : bomMaterialRef.materialCode;
        					var productName = bomMaterialRef.productName == null ? '' : bomMaterialRef.productName;
        					var specification = bomMaterialRef.specification == null ? '' : bomMaterialRef.specification;
        					var description = bomMaterialRef.description == null ? '' : bomMaterialRef.description;
        					var brand = bomMaterialRef.brand == null ? '' : bomMaterialRef.brand;
        					var usageAmount = bomMaterialRef.usageAmount == null ? '' : bomMaterialRef.usageAmount;
        					
        					/* if(materialCodeArray[materialCode] == null){
        						materialCodeArray[materialCode] = 1;
        					} */
        					
            				if(bomMaterialRef.seq == 0 || bomMaterialRef.seq == '0'){
            					//seq 为0是标题
            					var trValue = $('#tableTitle_'+bomMaterialRef.type).find('.bomMaterialMain');
            					trValue.data('id',id)
            					var tdValue = trValue.children();
            					//console.log(materialCode)
            					tdValue.eq(1).find('input').val(materialCode);
            					tdValue.eq(3).find('input').val(productName);
            					var trValueNext = trValue.next().children();
            					trValueNext.eq(1).find('textarea').val(specification);
            					trValueNext.eq(3).find('textarea').val(description);
            				}else{
            					//seq 大于0则为内容
            					var $content;
            					if(bomMaterialRef.type == 9 || bomMaterialRef.type == '9'){
            						//备品例外
            						$content = $('<tr class="bomMaterial tableContent_'+ bomMaterialRef.type +'">'+
	            							'<td style="padding:1px"><span class="seq">'+bomMaterialRef.seq+'</span></td>'+
	            							'<td style="padding:1px"></td>'+
	            					        '<td style="padding:1px"><textarea class="textareacssMain productName" >'+productName+'</textarea></td>'+
	            					        '<td style="padding:1px"><textarea class="textareacssMain specification" >'+specification+'</textarea></td>'+
	            					        '<td style="padding:1px"></td>'+
	            					        '<td style="padding:1px"><textarea class="textareacssMain count" >'+usageAmount+'</textarea></td>'+
	            					        '<td style="padding:1px"></td>'+
	            					        '<td style="padding:1px"></td>'+
	            					        '<td style="padding: 0px">'+
	            								'<span onclick="delbomMaterialFun(this);" class="icon-del tip"  title="删除"></span>'+
	            							'</td>'+
									    '</tr>');
            						$content.data('id',id)
            						tableSeq_9 = bomMaterialRef.seq;
            					}else{
            						$content = $('<tr class="bomMaterial bomMaterialValidate tableContent_'+ bomMaterialRef.type +'">'+
            								'<td style="padding:1px"><span class="seq">'+bomMaterialRef.seq+'</span></td>'+
                							'<td style="padding:1px"><textarea class="textareacssMain materialCode materialCodeAuto materialCodeRequired" >'+materialCode+'</textarea></td>'+
                					        '<td style="padding:1px"><textarea class="textareacssMain productName" >'+productName+'</textarea></td>'+
                					        '<td style="padding:1px"><textarea class="textareacssMain specification" >'+specification+'</textarea></td>'+
                					        '<td style="padding:1px"><textarea class="textareacssMain brand" >'+brand+'</textarea></td>'+
                					        '<td style="padding:1px"><textarea class="textareacssMain count" >'+usageAmount+'</textarea></td>'+
                					        '<td style="padding:1px"><textarea class="textareacssMain description" >'+description+'</textarea></td>'+
                					        '<td style="padding:1px"><span onclick="addContact(this);" class="showContactCss">查看</span></td>'+
                					        '<td style="padding: 0px">'+
                								'<span onclick="delbomMaterialFun(this);" class="icon-del tip"  title="删除"></span>'+
                							'</td>'+
    								    '</tr>');
                					$content.data('id',id)
                					
                					setSeq(bomMaterialRef.type,bomMaterialRef.seq);
            					}
            					
            					$('#tableContent_'+bomMaterialRef.type).append($content)
            					
            					$content.find('.productName').validatebox({    
								    validType: ['isBlank_','length[0,64]']  
								});
            					$content.find('.specification').validatebox({    
								    validType: ['isBlank_','length[0,200]']  
								});
            					$content.find('.brand').validatebox({    
								    validType: ['isBlank_','length[0,64]']  
								});
            					$content.find('.count').validatebox({    
								    validType: ['isBlank_','length[0,64]','countValidate']  
								});
            					$content.find('.description').validatebox({    
								    validType: ['isBlank_','length[0,200]']  
								}); 
								
            					if(bomMaterialRef.materialContactDTO != null){
            						var materialContactDTO = bomMaterialRef.materialContactDTO;
            						var id = materialContactDTO.id == null ? null : materialContactDTO.id;
            						var company = materialContactDTO.company == null ? null : materialContactDTO.company;
            						var contacts = materialContactDTO.contacts == null ? null : materialContactDTO.contacts;
            						var telphone = materialContactDTO.telphone == null ? null : materialContactDTO.telphone;
            						var cellphone = materialContactDTO.cellphone == null ? null : materialContactDTO.cellphone;
            						var email = materialContactDTO.email == null ? null : materialContactDTO.email;
            						var fax = materialContactDTO.fax == null ? null : materialContactDTO.fax;
            						
            						var $span = $content.children().eq(7).find('span');
            						if(company == null || company == ''){
            							$content.find('.showContactCss').text("查看")
            						}else{
            							$content.find('.showContactCss').text(company)
            						}
            						
            						$span.data('id',id);
            						$span.data('contactsCompany',company);
            						$span.data('contactsName',contacts);
            						$span.data('contactsTelphone',telphone);
            						$span.data('contactsCellphone',cellphone);
            						$span.data('contactsEmail',email);
            						$span.data('contactsFax',fax);
            					}else{
            						$content.find('.showContactCss').text("添加联系人");
            					}
            					
            				}
            			}
            		}
            	}
            }
		 })
		   
		 
		 //内容的自动填充
		 $('#content').off('change','.materialCodeAuto').on('change','.materialCodeAuto',function(){
				var value = $(this).val() == null ? '' : $(this).val().trim();
				var me =  $(this);
				if(value != ''){
					$.ajax({
						url:"${ctx}/bom/getByMaterialCode?materialCode="+ value,
						type:"POST",
						dataType: "json",
			            contentType: "application/json; charset=utf-8", 
			            success: function(data){
			            	if(data != null){
			            		if(!isEmptyObject(data)){
			            			console.log(data)
			            			var table = me.parents('tr');
			            			table.find('.productName').val(data.materialName);
			            			table.find('.specification').val(data.specification);
			            			table.find('.description').val(data.description);
			            		}
			            	}
			            }
					})
				}
				
			});
	 	
	 	//主表内容
 	    $('#materialCode').off('change').on('change',function(){
			var value = $(this).val() == null ? '' : $(this).val().trim();
			if(value != ''){
				$.ajax({
					url:"${ctx}/bom/getByMaterialCode?materialCode="+ value,
					type:"POST",
					dataType: "json",
		            contentType: "application/json; charset=utf-8", 
		            success: function(data){
		            	if(data != null){
		            		if(!isEmptyObject(data)){
		            			console.log(data)
		            			$('#productName').val(data.materialName);
		            			$('#specification').val(data.specification);
		            			$('#description').val(data.description);	            		
		            		}
		            	}
		            }
				})
			}
		});	
	 	//标题的自动填充
		$('#content').off('change','.materialCodeAutoTitle').on('change','.materialCodeAutoTitle',function(){
			var value = $(this).val() == null ? '' : $(this).val().trim();
			var me =  $(this);
			if(value != ''){
				$.ajax({
					url:"${ctx}/bom/getByMaterialCode?materialCode="+ value,
					type:"POST",
					dataType: "json",
		            contentType: "application/json; charset=utf-8", 
		            success: function(data){
		            	if(data != null){
		            		if(!isEmptyObject(data)){
		            			console.log(data)
		            			var table = me.parents('table');
		            			table.find('.productName').val(data.materialName);
		            			table.find('.specification').val(data.specification);
		            			table.find('.description').val(data.description);
		            		}
		            	}
		            }
				})
			}
		});
	 	
	 	//延时数据校验
		setTimeout(function(){
			 $('.materialCode').validatebox({
				 validType: ['isBlank_','materialCodeLengthValidate[14]' ,'materialCodeValidate' ],
			 }); 
		},2000);  
		
		$('.materialCodeRequired').validatebox({
			 required: true,   
		 }); 
		 
	});	
	//判断对象是否为空对象
	function isEmptyObject(e) {  
	    var t;  
	    for (t in e)  
	        return !1;  
	    return !0  
	} 
	//传入的tableId
	function addbomMaterialFun(tableId){
		var $content;
		var seq = tableId.substring(13)
		if(tableId.indexOf('9') > 0){
			$content = $('<tr class="bomMaterial '+tableId+'">'+
					'<td style="padding:1px"><span class="seq">'+addSeq('9')+'</span></td>'+
					'<td style="padding:1px"></td>'+
			        '<td style="padding:1px"><textarea class="textareacssMain productName" ></textarea></td>'+
			        '<td style="padding:1px"><textarea class="textareacssMain specification" ></textarea></td>'+
			        '<td style="padding:1px"></td>'+
			        '<td style="padding:1px"><textarea class="textareacssMain count" ></textarea></td>'+
			        '<td style="padding:1px"></td>'+
			        '<td style="padding:1px"></td>'+
			        '<td style="padding: 0px">'+
						'<span onclick="delbomMaterialFun(this);" class="icon-del tip"  title="删除"></span>'+
					'</td>'+
			    '</tr>');
		}else{
			$content = $('<tr class="bomMaterial bomMaterialValidate '+tableId+'">'+
				'<td style="padding:1px"><span class="seq">'+addSeq(seq)+'</span></td>'+
		        '<td style="padding:1px"><textarea class="textareacssMain materialCode materialCodeAuto materialCodeRequired" ></textarea></td>'+
		        '<td style="padding:1px"><textarea class="textareacssMain productName" ></textarea></td>'+
		        '<td style="padding:1px"><textarea class="textareacssMain specification" ></textarea></td>'+
		        '<td style="padding:1px"><textarea class="textareacssMain brand" ></textarea></td>'+
		        '<td style="padding:1px"><textarea class="textareacssMain count" ></textarea></td>'+
		        '<td style="padding:1px"><textarea class="textareacssMain description" ></textarea></td>'+
		        '<td style="padding:1px"><span onclick="addContact(this);" class="showContactCss">添加联系人</span></td>'+
		        '<td style="padding: 0px">'+
					'<span onclick="delbomMaterialFun(this);" class="icon-del tip"  title="删除"></span>'+
				'</td>'+
		    '</tr>');
		}
		
		$('#'+tableId).append($content);
		
		$content.find('.materialCode').validatebox({  
		    validType: ['isBlank_','materialCodeLengthValidate[14]' ,'materialCodeValidate' ] ,
		});
		$content.find('.materialCodeRequired').validatebox({
			 required: true,   
		}); 
		$content.find('.productName').validatebox({    
		    validType: ['isBlank_','length[0,64]']  
		});
		$content.find('.specification').validatebox({    
		    validType: ['isBlank_','length[0,200]']  
		});
		$content.find('.brand').validatebox({    
		    validType: ['isBlank_','length[0,64]']  
		});
		$content.find('.count').validatebox({    
		    validType: ['isBlank_','length[0,64]','countValidate']  
		});
		$content.find('.description').validatebox({    
		    validType: ['isBlank_','length[0,200]']  
		}); 
	}
	
	//删除整行数据
	function delbomMaterialFun(obj){
		var $span = $(obj).parent().prev().find('span');
		var tableId = typeof($(obj).parents('table').attr('id')) == 'undefined' ? null : $(obj).parents('table').attr('id');
		
		var contactsName = typeof($span.data('contactsName')) == 'undefined' ? "" :  ($span.data('contactsName'));
		var contactsTelphone = typeof($span.data('contactsTelphone')) == 'undefined' ? "" :  ($span.data('contactsTelphone'));
		var trValue = $(obj).parent().parent();
		var materialCode = trValue.find('.materialCode').val() == null ? '' : trValue.find('.materialCode').val().trim();
		var productName = trValue.find('.productName').val() == null ? '' : trValue.find('.productName').val().trim();
		var specification = trValue.find('.specification').val() == null ? '' : trValue.find('.specification').val().trim();
		var brand = trValue.find('.brand').val() == null ? '' : trValue.find('.brand').val().trim();
		var count = trValue.find('.count').val() == null ? '' : trValue.find('.count').val().trim();
		var description = trValue.find('.description').val() == null ? '' : trValue.find('.description').val().trim();
		
		if(contactsName != '' || contactsTelphone != '' || materialCode != '' || productName != '' || specification != '' || brand != ''|| count != ''|| description != ''){
			parent.$.messager.confirm('询问', '您是否要删除整行数据？', function(b) {
				if (b) {
					$(obj).parent().parent().remove();
					if(tableId != null){
						resetSeq(tableId)
					}
				}
			});
		}else{
			$(obj).parent().parent().remove();
			if(tableId != null){
				resetSeq(tableId)
			}
		}
		
	}
	//选择标题
	function chooseBomMaterialTitle(tableId){
		var materialNames = "";
		var trValue = $('#'+tableId).find('.bomMaterialMain');
		var materialCode = trValue.children().eq(1).find('input').val() == null ? '' : trValue.children().eq(1).find('input').val().trim();//物料的值
		var productName = trValue.children().eq(3).find('input').val() == null ? '' : trValue.children().eq(3).find('input').val().trim();//物料的值
		if(materialCode != ''){
			materialNames = materialCode+separator+ productName;
		}
		parent.$.modalDialog({
			title : '物料选择',
			width : 900,
			height : 600,
			href : '${ctx}/bom/chooseBomMaterialTitle?materialNames='+ encodeURIComponent(materialNames),
			buttons : [ {
				text : '确定',
				handler : function() {
					if(parent.$('#chooseMaterialMain li').length > 0){
						var li = parent.$('#chooseMaterialMain').children().eq(0);
						var materialName = "";
						var specification = "";
						var description = "";
						if(li.attr('data-materialName') != null && typeof(li.attr('data-materialName')) != 'undefined'){
							materialName = li.attr('data-materialName');
						}
						if(li.attr('data-specification') != null && typeof(li.attr('data-specification')) != 'undefined'){
							specification = li.attr('data-specification');
						}
						if(li.attr('data-description') != null && typeof(li.attr('data-description')) != 'undefined'){
							description = li.attr('data-description');
						}
						var chooseNames = li.text().trim().split(separator)[0];
						
						trValue.children().eq(1).find('input').val(chooseNames) 
						trValue.children().eq(3).find('input').val(materialName) 
						var trValueNext = trValue.next();
						trValueNext.children().eq(1).find('textarea').val(specification) 
						trValueNext.children().eq(3).find('textarea').val(description) 
					}else{
						//清空数据
						trValue.children().eq(1).find('input').val('') 
						trValue.children().eq(3).find('input').val('') 
						var trValueNext = trValue.next();
						trValueNext.children().eq(1).find('textarea').val('') 
						trValueNext.children().eq(3).find('textarea').val('') 
					}
					parent.$.modalDialog.handler.dialog('close');
				}
			} ] 
		});
	}
	
	//选择
	function chooseBomMaterial(tableId){
		
		var materialNames = "";
		$('#'+tableId).find('.'+tableId).each(function(){
			
			if( $(this).find('.materialCode').val().trim() != ""){
				var materialCode =  $(this).find('.materialCode').val() == null ? '' :  $(this).find('.materialCode').val().trim();
				var productName  =  $(this).find('.productName ').val() == null ? '' :  $(this).find('.productName ').val().trim();
				//materialNames += materialCode+"----"+ productName + ","; 
				materialNames += materialCode+separator+ productName + ","; 
			}else{
				reduceSeq(tableId.substring(13))
			}
		})
		var seq = tableId.substring(13);
		materialNames = materialNames.substring(0,materialNames.length - 1).replace(/[\r\n]/g,"");
		parent.$.modalDialog({
			title : '物料选择',
			width : 900,
			height : 600,
			href : '${ctx}/bom/chooseBomMaterial?materialNames='+ encodeURIComponent(materialNames),
			buttons : [ {
				text : '确定',
				handler : function() {
					if(parent.$('#chooseMaterialMain li').length > 0){
						var isExcit = false;
						$('#'+tableId).find('.'+tableId).each(function(){
							var name = $(this).find('.materialCode').val().trim();
							parent.$('#chooseMaterialMain li').each(function(){
								//var chooseNames = $(this).text().trim().split("----")[0];
								var chooseNames = $(this).text().trim().split(separator)[0];
								//var chooseNames = $(this).text().trim()
								if(name == chooseNames){
									//存在相同，保留
									isExcit = true;
								}   
							})
							if(!isExcit){
								 $(this).remove();
							}
							isExcit = false;
						})
						
						var flag = false;
						parent.$('#chooseMaterialMain li').each(function(){
							var li = $(this);
							$('#'+tableId).find('.'+tableId).each(function(){
								var name = $(this).find('.materialCode').val().trim();
								//var chooseNames = li.text().trim().split("----")[0];
								var chooseNames = li.text().trim().split(separator)[0];
								//var chooseNames = li.text().trim()
								if(name == chooseNames){
									flag = true;
								}
							})
							if(!flag){
								var materialName = "";
								var specification = "";
								var description = "";
								
								if(li.attr('data-materialName') != null && typeof(li.attr('data-materialName')) != 'undefined'){
									materialName = li.attr('data-materialName');
								}
								
								if(li.attr('data-specification') != null && typeof(li.attr('data-specification')) != 'undefined'){
									specification = li.data('specification');
								}
								if(li.attr('data-description') != null && typeof(li.attr('data-description')) != 'undefined'){
									description = li.data('description');
								}
								//var chooseNames = li.text().trim().split("----")[0];
								var chooseNames = $(this).text().trim().split(separator)[0];
								var $content = $('<tr class="bomMaterial bomMaterialValidate '+tableId+'">'+
												'<td style="padding:1px"><span class="seq">'+addSeq(seq)+'</span></td>'+
										        '<td style="padding:1px"><textarea class="textareacssMain materialCode materialCodeAuto materialCodeRequired" >'+chooseNames+'</textarea></td>'+
										        '<td style="padding:1px"><textarea class="textareacssMain productName" >'+materialName+'</textarea></td>'+
										        '<td style="padding:1px"><textarea class="textareacssMain specification" >'+specification+'</textarea></td>'+
										        '<td style="padding:1px"><textarea class="textareacssMain brand" ></textarea></td>'+
										        '<td style="padding:1px"><textarea class="textareacssMain count" ></textarea></td>'+
										        '<td style="padding:1px"><textarea class="textareacssMain description" >'+description+'</textarea></td>'+
										        '<td style="padding:1px"><span onclick="addContact(this);" class="showContactCss">添加联系人</span></td>'+
										        '<td style="padding: 0px">'+
													'<span onclick="delbomMaterialFun(this);" class="icon-del tip"  title="删除"></span>'+
												'</td>'+
										    '</tr>');
							   
								$('#'+tableId).append($content);
								$content.find('.materialCode').validatebox({  
								    validType: ['isBlank_','materialCodeLengthValidate[14]','materialCodeValidate' ],
								});
								$content.find('.materialCodeRequired').validatebox({
									 required: true,   
								});
								$content.find('.productName').validatebox({    
								    validType: ['isBlank_','length[0,64]']  
								});
								$content.find('.specification').validatebox({    
								    validType: ['isBlank_','length[0,200]']  
								});
								$content.find('.brand').validatebox({    
								    validType: ['isBlank_','length[0,64]']  
								});
								$content.find('.count').validatebox({    
								    validType: ['isBlank_','length[0,64]','countValidate']  
								});
								$content.find('.description').validatebox({    
								    validType: ['isBlank_','length[0,200]']  
								}); 
							}
							flag = false;
						})
					}else{
						$('#'+tableId).find('.'+tableId).each(function(){
							$(this).remove();
						})
						setSeq(parseInt(seq),0)
					}
					parent.$.modalDialog.handler.dialog('close');
				}
			} ] 
		});
	}
	function addContact(obj){
		if($(obj).data('contactsName') == null || typeof($(obj).data('contactsName')) == 'undefined'){
			parent.$.modalDialog({
				title : 'Bom联系人添加',
				width : 350,
				height : 250,
				href : '${ctx}/bom/addBomContactPage',
				buttons : [ {
					text : '添加',
					handler : function() {
						var isValid = parent.$('#bomContactAddForm').form('validate');
						if(!isValid){
							return;
						}
						var contactsName = parent.$('#contactsName').val() == null ? null : parent.$('#contactsName').val().trim();
						var contactsTelphone = parent.$('#contactsTelphone').val() == null ? null : parent.$('#contactsTelphone').val().trim();
						var contactsCompany = parent.$('#contactsCompany').val() == null ? null : parent.$('#contactsCompany').val().trim();
						var contactsCellphone = parent.$('#contactsCellphone').val() == null ? null : parent.$('#contactsCellphone').val().trim();
						var contactsEmail = parent.$('#contactsEmail').val() == null ? null : parent.$('#contactsEmail').val().trim();
						var contactsFax = parent.$('#contactsFax').val() == null ? null : parent.$('#contactsFax').val().trim();
						
						//将信息存入当前的span中
						$(obj).data('contactsName',contactsName);
						$(obj).data('contactsTelphone',contactsTelphone);
						$(obj).data('contactsCompany',contactsCompany);
						$(obj).data('contactsCellphone',contactsCellphone);
						$(obj).data('contactsEmail',contactsEmail);
						$(obj).data('contactsFax',contactsFax);
						
						
						$(obj).text(contactsCompany)
						// 消息将显示在顶部中间
						/* parent.$.messager.show({
							title:'消息提示',
							msg:'<span style="color:green;font-size:1.2em">添加联系人成功！！</span>',
							timeout:500,
							showType:'fade',
							style:{
								right:'',
								top:parent.$('body').height()/2,
								bottom:''
							}
						}); */
						parent.$.modalDialog.handler.dialog('close');
					}
				}
				] 
			});
		}else{
			var contactsName = typeof($(obj).data('contactsName')) == 'undefined' ? "" :  ($(obj).data('contactsName'));
			var contactsTelphone = typeof($(obj).data('contactsTelphone')) == 'undefined' ? "" :  ($(obj).data('contactsTelphone'));
			var contactsCompany = typeof($(obj).data('contactsCompany')) == 'undefined' ? "" :  ($(obj).data('contactsCompany'));
			var contactsCellphone = typeof($(obj).data('contactsCellphone')) == 'undefined' ? "" :  ($(obj).data('contactsCellphone'));
			var contactsEmail = typeof($(obj).data('contactsEmail')) == 'undefined' ? "" :  ($(obj).data('contactsEmail'));
			var contactsFax = typeof($(obj).data('contactsFax')) == 'undefined' ? "" :  ($(obj).data('contactsFax'));
			
			var param = "company="+contactsCompany+"&contacts="+contactsName+"&telphone="+contactsTelphone+"&cellphone="+contactsCellphone+"&email="+contactsEmail+"&fax="+contactsFax
			parent.$.modalDialog({
				title : 'Bom联系人编辑',
				width : 350,
				height : 250,
				//href : '${ctx}/bom/editBomContactPage?bomMaterialContact='+JSON.stringify(param),
				href : '${ctx}/bom/editBomContactPage?'+(param),
				buttons : [ {
					text : '编辑',
					handler : function() {
						var isValid = parent.$('#bomContactEditForm').form('validate');
						if(!isValid){
							return;
						}
						var contactsName = parent.$('#contactsName').val() == null ? null : parent.$('#contactsName').val().trim();
						var contactsTelphone = parent.$('#contactsTelphone').val() == null ? null : parent.$('#contactsTelphone').val().trim();
						var contactsCompany = parent.$('#contactsCompany').val() == null ? null : parent.$('#contactsCompany').val().trim();
						var contactsCellphone = parent.$('#contactsCellphone').val() == null ? null : parent.$('#contactsCellphone').val().trim();
						var contactsEmail = parent.$('#contactsEmail').val() == null ? null : parent.$('#contactsEmail').val().trim();
						var contactsFax = parent.$('#contactsFax').val() == null ? null : parent.$('#contactsFax').val().trim();
						
						//将信息存入当前的span中
						$(obj).data('contactsName',contactsName);
						$(obj).data('contactsTelphone',contactsTelphone);
						$(obj).data('contactsCompany',contactsCompany);
						$(obj).data('contactsCellphone',contactsCellphone);
						$(obj).data('contactsEmail',contactsEmail);
						$(obj).data('contactsFax',contactsFax);
						
						$(obj).text(contactsCompany)
						// 消息将显示在顶部中间
						/* parent.$.messager.show({
							title:'消息提示',
							msg:'<span style="color:green;font-size:1.2em">编辑联系人成功！！</span>',
							timeout:500,
							showType:'fade',
							style:{
								right:'',
								top:parent.$('body').height()/2,
								bottom:''
							}
						}); */
						parent.$.modalDialog.handler.dialog('close');
					}
				} ,
				{
					text : '删除',
					handler : function() {
						$(obj).data('contactsName',null);
						$(obj).data('contactsTelphone',null);
						$(obj).data('contactsCompany',null);
						$(obj).data('contactsCellphone',null);
						$(obj).data('contactsEmail',null);
						$(obj).data('contactsFax',null);
						
						$(obj).text("添加联系人")
						/* parent.$.messager.show({
							title:'消息提示',
							msg:'<span style="color:green;font-size:1.2em">删除联系人成功！！</span>',
							timeout:500,
							showType:'fade',
							style:{
								right:'',
								top:parent.$('body').height()/2,
								bottom:''
							}
						}); */
						parent.$.modalDialog.handler.dialog('close');
					}
				}] 
			});
		}
		
	}
	
	
	$.extend($.fn.validatebox.defaults.rules, {
		//用量验证
		countValidate : {
	        validator : function(value, param) {
	        	var flag = false;
	        	var reg = /^(((([1-9]\d*)|0))|([1-9]\d*\/[1-9]\d*))$/;
	            if(!reg.test(value)){
	                //alert("框里只能输入小数和分数");
	                flag = false;
	            }else{
	            	flag = true;
	            }
	            return flag;
	        },
	        message : '您输入用量必须是整数或者是形如“1/3”的分数'
	    },
	  //主表的物料编码校验
	    materialCodeValidateMain : {
	        validator : function(value, param) {
	        	var materialCode = $('#materialCode').val().trim();
	            var materialCodeExist = false;
	            $.ajax({
	                type : 'post',
	                async : false,
	                dataType: "json",
	                url: "${ctx}/bom/isExistMaterialCode?materialCode=" + materialCode,
	                success : function(data) {
	                	if(data.success){
	                		materialCodeExist = true;
		            	 }else{
		            		 materialCodeExist = false;
		            	 }
	                }
	            });
	            return materialCodeExist;
	        },
	        message : '您输入的物料编码已存在，请重新输入！！'
	    },
	    materialCodeValidate : {
	        validator : function(value, param) {
	        	var me = $(this);
	        	var isExist = true;
	        	$('#content').find('.materialCode').each(function(){
	        		
	        		//this为tr标签
	        		var materialCode = $(this).val().trim();
	        		if(me.is($(this))){//自己则跳过
	        			return true;
	        		}
	        		if(materialCode.trim() != ''){
	        			if(me.val().trim() == materialCode.trim()){
	        				isExist = false;
	        				return false;
		        		}
	        		}
	        		
	        	})
	            return isExist;
	        },
	        message : '在此BOM中你已经选择过该物料，你重新选择新物料！！'
	    },
	    materialCodeLengthValidate : {
	        validator : function(value, param) {
	        	if(value.length != param)
	        		return false;
	            return true;
	        },
	        message : '物料编码的长度只能为14个字符！！'
	    },
	});
	
	//添加bom的
	function editBomFun(){
		
		var isValid = $('#editBomFun').form('validate');
		console.log("addBomFun 验证"+isValid)
		if(!isValid){
			return;
		}
		
		var bomMaterialRefDTOs = [];
		//添加标题       标题的seq为0
		$('#content').find('.bomMaterialMain').each(function(){
			//遍历所有的标题  当前tr包含  物料编码和品名  下一个tr包含   规格型号及封装和描述
			var tdValue = $(this).children();
			var materialCode = tdValue.eq(1).find('input').val() == null ? null : tdValue.eq(1).find('input').val().trim();
			var productName = tdValue.eq(3).find('input').val() == null ? null : tdValue.eq(3).find('input').val().trim();
			
			var tdValueNext = $(this).next().children();
			var specification = tdValueNext.eq(1).find('textarea').val() == null ? null : tdValueNext.eq(1).find('textarea').val().trim();
			var description = tdValueNext.eq(3).find('textarea').val() == null ? null : tdValueNext.eq(3).find('textarea').val().trim();
			
			var type = $(this).parents('table').attr('id').substring(11);//截图数字tableTitle_1
			//console.log(type)
			var id = typeof($(this).data('id')) == 'undefined' ? null :  ($(this).data('id'));
			
			var bomMaterialRef = {
					id : id,
					materialCode : materialCode,
					productName : productName,
					specification : specification,
					description : description,
					type : type,
					seq : 0,  //标题
			}
			if(materialCode != null && materialCode != ''){
				bomMaterialRefDTOs.push(bomMaterialRef)
			}
			
		});
		
		//添加内容      内容的seq大于0
		$('#content').find('.bomMaterial').each(function(index){
			var id = typeof($(this).data('id')) == 'undefined' ? null :  ($(this).data('id'));
			var tdValue = $(this).children();
			
			var contentId = typeof($(this).parents('table').attr('id')) == 'undefined' ? null : $(this).parents('table').attr('id');
			if(contentId.indexOf('9') > 0){
				var productName = tdValue.eq(2).find('textarea').val() == null ? null : tdValue.eq(2).find('textarea').val().trim();
				var specification = tdValue.eq(3).find('textarea').val() == null ? null : tdValue.eq(3).find('textarea').val().trim();
				var count = tdValue.eq(5).find('textarea').val() == null ? null : tdValue.eq(5).find('textarea').val().trim();
			}else{
				var materialCode = tdValue.eq(1).find('textarea').val() == null ? null : tdValue.eq(1).find('textarea').val().trim();
				var productName = tdValue.eq(2).find('textarea').val() == null ? null : tdValue.eq(2).find('textarea').val().trim();
				var specification = tdValue.eq(3).find('textarea').val() == null ? null : tdValue.eq(3).find('textarea').val().trim();
				var brand  = tdValue.eq(4).find('textarea').val() == null ? null : tdValue.eq(4).find('textarea').val().trim();
				var count = tdValue.eq(5).find('textarea').val() == null ? null : tdValue.eq(5).find('textarea').val().trim();
				var description  = tdValue.eq(6).find('textarea').val() == null ? null : tdValue.eq(6).find('textarea').val().trim();
			}	
			
			var seq = tdValue.eq(0).find('.seq').text() == null ? 0 : tdValue.eq(0).find('.seq').text().trim();
			var type = $(this).parents('table').attr('id').substring(13);//截图数字tableContent_1
			
			//联系人
			var obj  = tdValue.eq(7).find('span');
			var contactsId = typeof($(obj).data('id')) == 'undefined' ? null :  ($(obj).data('id'));
			var contactsName = typeof($(obj).data('contactsName')) == 'undefined' ? null :  ($(obj).data('contactsName'));
			var contactsTelphone = typeof($(obj).data('contactsTelphone')) == 'undefined' ? null :  ($(obj).data('contactsTelphone'));
			var contactsCompany = typeof($(obj).data('contactsCompany')) == 'undefined' ? null :  ($(obj).data('contactsCompany'));
			var contactsCellphone = typeof($(obj).data('contactsCellphone')) == 'undefined' ? null :  ($(obj).data('contactsCellphone'));
			var contactsEmail = typeof($(obj).data('contactsEmail')) == 'undefined' ? null :  ($(obj).data('contactsEmail'));
			var contactsFax = typeof($(obj).data('contactsFax')) == 'undefined' ? null :  ($(obj).data('contactsFax'));
			
			//解析count  1  或者  1/3
			var usageAmount1;//分子
			var usageAmount2;//分母
			if(count != null){
				if(count.indexOf('/') > 0){
					usageAmount1 = count.split('/')[0];
					usageAmount2 = count.split('/')[1];
				}else{
					usageAmount1 = count;
					usageAmount2 = 1;
				}
			}
			var materialContactDTO = {
					id : contactsId,
					company : contactsCompany,  //厂家
					contacts : contactsName,  //姓名
					telphone : contactsTelphone,  //座机号
					cellphone : contactsCellphone, //手机号
					email : contactsEmail,   //邮箱
					fax : contactsFax      //传真	
			}
			var bomMaterialRef = null ;
			if(contactsName != null && contactsTelphone != null){
				bomMaterialRef = {
						id : id,
						materialCode : materialCode,
						productName : productName,
						specification : specification,
						description : description,
						type : type,
						brand : brand,
						usageAmount1 : usageAmount1,
						usageAmount2 : usageAmount2,
						//usageAmount : count,
						seq : seq,  //内容,
						materialContactDTO : materialContactDTO
				}
			}else{
				bomMaterialRef = {
						id : id,
						materialCode : materialCode,
						productName : productName,
						specification : specification,
						description : description,
						type : type,
						brand : brand,
						usageAmount1 : usageAmount1,
						usageAmount2 : usageAmount2,
						//usageAmount : count,
						seq : seq,  //内容,
				}
			}
			
			if(type == 9){
				bomMaterialRefDTOs.push(bomMaterialRef)
			}else{
				if(materialCode != null && materialCode != ''){
					bomMaterialRefDTOs.push(bomMaterialRef)
				}
			}
			
		});
		//基础信息
		//var title = $('#title').val() == null ? null : $('#title').val().trim();
		var maker = $('#maker').val() == null ? null : $('#maker').val().trim();
		var date = $('#date').datebox('getValue') == null ? null : $('#date').datebox('getValue').trim();
		var version = $('#version').val() == null ? null : $('#version').val().trim();
		var materialCode = $('#materialCode').val() == null ? null : $('#materialCode').val().trim();
		var productName = $('#productName').val() == null ? null : $('#productName').val().trim();
		//var brand = $('#brand').val() == null ? null : $('#brand').val().trim();
		var specification = $('#specification').val() == null ? null : $('#specification').val().trim();
		var description = $('#description').val() == null ? null : $('#description').val().trim();
		
		var param = {
				//基础
				id : bomId,
				orderId : orderId,
				//title : title,
				maker : maker,
				date : date,
				version : version,
				materialCode : materialCode,
				productName : productName,
				//brand : brand,
				specification : specification,
				description : description,
				state : state,
				bomMaterialRefDTOs : bomMaterialRefDTOs,
		}
		console.log(param)
		progressLoad();
		 $.ajax({
        	type: 'POST',
            url: "${ctx}/bom/edit" ,
            data: JSON.stringify(param) ,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function(data){
	          	 if(data.success){
	          		 parent.$.messager.alert('提示', data.msg);
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
	          	 }else{
	          		 parent.$.messager.alert('提示', data.msg);
	          	 }
            }
       }) 
       progressClose();
	}
	
	function resetSeq(tableId){
		reduceSeq(tableId.substring(13))
		$('#'+tableId).find('.seq').each(function(index,value){
			$(this).text(index+1);
		});
	}
	function addSeq(type){
		switch (type)
		{
		case '1':
			tableSeq_1 ++;
			return tableSeq_1;
		case '2':
			tableSeq_2 ++;
			return tableSeq_2;
		case '3':
			tableSeq_3 ++;
			return tableSeq_3;
		case '4':
			tableSeq_4 ++;
			return tableSeq_4;
		case '5':
			tableSeq_5 ++;
			return tableSeq_5;
		case '6':
			tableSeq_6 ++;
			return tableSeq_6;
		case '7':
			tableSeq_7 ++;
			return tableSeq_7;
		case '8':
			tableSeq_8 ++;
			return tableSeq_8;
		case '9':
			tableSeq_9 ++;
			return tableSeq_9;
		}
		
	}
	function reduceSeq(type){
		switch (type)
		{
		case '1':
			tableSeq_1 --;
			return tableSeq_1;
		case '2':
			tableSeq_2 --;
			return tableSeq_2;
		case '3':
			tableSeq_3 --;
			return tableSeq_3;
		case '4':
			tableSeq_4 --;
			return tableSeq_4;
		case '5':
			tableSeq_5 --;
			return tableSeq_5;
		case '6':
			tableSeq_6 --;
			return tableSeq_6;
		case '7':
			tableSeq_7 --;
			return tableSeq_7;
		case '8':
			tableSeq_8 --;
			return tableSeq_8;
		case '9':
			tableSeq_9 --;
			return tableSeq_9;
		}
	}
	function setSeq(tableId,before){
		switch (tableId)
		{
		case 1:
			tableSeq_1 = before;
			break;
		case 2:
			tableSeq_2 = before;
			break;
		case 3:
			tableSeq_3 = before;
			break;
		case 4:
			tableSeq_4 = before;
			break;
		case 5:
			tableSeq_5 = before;
			break;
		case 6:
			tableSeq_6 = before;
			break;
		case 7:
			tableSeq_7 = before;
			break;
		case 8:
			tableSeq_8 = before;
			break;
		}
	}
</script>
<body style="padding: 10px">
<div id='content'>
	<form id="editBomFun">
	<!-- 基础信息 -->
	<table border="0" cellpadding="0" cellspacing="0" class="ordertable">
        <tr class="tabletitle">
            <th colspan="8">基础信息:</th>
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
            <td class="righttd"><input type="text" class="inputs easyui-validatebox"  id="title" data-options="validType:['isBlank_','length[0,64]']" /></td> -->
            <td class="lefttd">制表人:</td>
            <td class="righttd"><input type="text" class="inputs easyui-validatebox"  id="maker" data-options="validType:['isBlank_','length[0,64]']"/></td>
            <td class="lefttd">日期:</td>
            <td class="righttd"><input type="text" class=" easyui-datebox" editable="false"   id="date"/></td>
            <td class="lefttd">版本:</td>
            <td class="righttd"><input type="text" class="inputs easyui-validatebox"  id="version" data-options="validType:['isBlank_','length[0,20]']"/></td>
        </tr>
        <tr>
            <td class="lefttd">物料编码:</td>
            <td class="righttd"><input type="text" class="inputs easyui-validatebox materialCode"  id="materialCode" data-options="required:true,validType:['isBlank_','materialCodeLengthValidate[14]','materialCodeValidate']"/></td>
            <td class="lefttd">品名:</td>
            <td class="righttd"><input type="text" class="inputs easyui-validatebox"  id="productName" data-options="validType:['isBlank_','length[0,64]']"/> </td>
           <!--  <td class="lefttd">品牌:</td>
            <td class="righttd"><input type="text" class="inputs easyui-validatebox"  id="brand" data-options="validType:['isBlank_','length[0,64]']"/> </td> -->
        </tr> 
        <tr>
         	<td class="lefttd">规格型号与封装:</td>
            <td class="righttd" colspan="3"><textarea id="specification" class="textareacss "  data-options="validType:['isBlank_','length[0,200]']"></textarea></td>
            <td class="lefttd">描述:</td>
            <td class="righttd" colspan="3"><textarea id="description" class="textareacss "  data-options="validType:['isBlank_','length[0,200]']"></textarea></td>
        </tr>
    </table>
  
  <!-- 第一部分:裸机包装模组 -->
   	   <table id="tableTitle_1" border="0" cellpadding="0" cellspacing="0" class="ordertable" style="margin-bottom: 0px;margin-top: 10px">
		    <tr class="tabletitle">
		        <th colspan="8">第一部分：裸机包装模组 
		        	<span onclick="chooseBomMaterialTitle('tableTitle_1');" class="choose">[选择]</span>
		        </th>
		       
		    </tr>
		   <tr class="bomMaterialMain">
		      	<td class="lefttd" width="10%">物料编码:</td>
		        <td class="righttd" width="20%"><input type="text" class="inputs easyui-validatebox materialCode materialCodeAutoTitle" data-options="validType:['isBlank_','materialCodeLengthValidate[14]','materialCodeValidate']"/></td>
		        <td class="lefttd" width="5%">品名:</td>
		        <td class="righttd" width="20%"><input type="text" class="inputs easyui-validatebox productName"  data-options="validType:['isBlank_','length[0,64]']"/></td>
		        <td class="lefttd" width="5%"></td>
		        <td class="righttd" width="15%"></td>
		        <td class="lefttd" width="10%"></td>
		        <td class="righttd" width="15%"></td>
		  	</tr>
		  	<tr>
		  		<td class="lefttd">规格型号及封装:</td>
		        <td colspan="3"><textarea  class="textareacss easyui-validatebox specification"  data-options="validType:['isBlank_','length[0,200]']"></textarea></td>
		  		<td class="lefttd" >描述:</td>
		        <td colspan="3"><textarea  class="textareacss easyui-validatebox description"  data-options="validType:['isBlank_','length[0,200]']"></textarea></td>
		  	</tr>
    	</table>
    	<table id="tableContent_1" border="0" cellpadding="0" cellspacing="0" class="bordertable">
		     <tr class="tabletop">
		        <th width="3%" class="text_left">序号 </th> 
		        <th width="12%" class="text_left">物料编码
		         	<span onclick="chooseBomMaterial('tableContent_1');" class="choose">[选择]</span>
		        </th>
		        <th width="20%" class="text_left">品名</th>
		        <th width="30%" class="text_left">规格型号及封装</th>
		        <th width="7%" class="text_left">品牌</th>
		        <th width="5%" class="text_left">用量</th>
		        <th width="13%" class="text_left">描述</th>
		        <th width="8%" class="text_left">联系人</th>
		        <th align="right" style="border-left: 0px">
					<span onclick="addbomMaterialFun('tableContent_1');" class="icon-add tip"  title="添加"></span>
				</th>
		    </tr>
	    </table>
	   <!-- 一、裸机成品 -->
	    <table id="tableTitle_2" border="0" cellpadding="0" cellspacing="0" class="ordertable" style="margin-bottom: 0px;margin-top: 0px">
		    <tr class="tabletitle">
		        <th colspan="8">一、裸机成品
		        	<span onclick="chooseBomMaterialTitle('tableTitle_2');" class="choose">[选择]</span>
		        </th>
		        
		    </tr>
		  	<tr class="bomMaterialMain">
		      	<td class="lefttd" width="10%">物料编码:</td>
		        <td class="righttd" width="20%"><input type="text" class="inputs easyui-validatebox  materialCodeAutoTitle" data-options="validType:['isBlank_','materialCodeLengthValidate[14]']"/></td>
		        <td class="lefttd" width="5%">品名:</td>
		        <td class="righttd" width="20%"><input type="text" class="inputs easyui-validatebox productName"  data-options="validType:['isBlank_','length[0,64]']"/></td>
		        <td class="lefttd" width="5%"></td>
		        <td class="righttd" width="15%"></td>
		        <td class="lefttd" width="10%"></td>
		        <td class="righttd" width="15%"></td>
		  	</tr>
		  	<tr>
		  		<td class="lefttd">规格型号及封装:</td>
		        <td colspan="3"><textarea  class="textareacss easyui-validatebox specification"  data-options="validType:['isBlank_','length[0,200]']"></textarea></td>
		  		<td class="lefttd" >描述:</td>
		        <td colspan="3"><textarea  class="textareacss easyui-validatebox description"  data-options="validType:['isBlank_','length[0,200]']"></textarea></td>
		  	</tr>
    	</table>
	    
	    <table id="tableTitle_3" border="0" cellpadding="0" cellspacing="0" class="ordertable" style="margin-bottom: 0px;margin-top: 0px">
		    <tr class="tabletitle">
		        <th colspan="8">a.散料，无虚拟件料号</th>
		    </tr>
    	</table>
   	    <table id="tableContent_3" border="0" cellpadding="0" cellspacing="0" class="bordertable">
		    <tr class="tabletop">
		        <th width="3%" class="text_left">序号 </th> 
		        <th width="12%" class="text_left">物料编码
		        	<span onclick="chooseBomMaterial('tableContent_3');" class="choose">[选择]</span>
		        </th>
		        <th width="20%" class="text_left">品名</th>
		        <th width="30%" class="text_left">规格型号及封装</th>
		        <th width="7%" class="text_left">品牌</th>
		        <th width="5%" class="text_left">用量</th>
		        <th width="13%" class="text_left">描述</th>
		        <th width="8%" class="text_left">联系人</th>
		        <th align="right" style="border-left: 0px">
					<span onclick="addbomMaterialFun('tableContent_3');" class="icon-add tip"  title="添加"></span>
				</th>
		    </tr>
	    </table>
	    
	    <table id="tableTitle_4" border="0" cellpadding="0" cellspacing="0" class="ordertable" style="margin-bottom: 0px;margin-top: 0px">
		    <tr class="tabletitle">
		        <th colspan="8">b.外壳模组
		        	<span onclick="chooseBomMaterialTitle('tableTitle_4');" class="choose">[选择]</span>
		        </th>
		        
		    </tr>
		  	<tr class="bomMaterialMain">
		      	<td class="lefttd" width="10%">物料编码:</td>
		        <td class="righttd" width="20%"><input type="text" class="inputs easyui-validatebox materialCode materialCodeAutoTitle" data-options="validType:['isBlank_','materialCodeLengthValidate[14]','materialCodeValidate']"/></td>
		        <td class="lefttd" width="5%">品名:</td>
		        <td class="righttd" width="20%"><input type="text" class="inputs easyui-validatebox productName"  data-options="validType:['isBlank_','length[0,64]']"/></td>
		        <td class="lefttd" width="5%"></td>
		        <td class="righttd" width="15%"></td>
		        <td class="lefttd" width="10%"></td>
		        <td class="righttd" width="15%"></td>
		  	</tr>
		  	<tr>
		  		<td class="lefttd">规格型号及封装:</td>
		        <td colspan="3"><textarea  class="textareacss easyui-validatebox specification"  data-options="validType:['isBlank_','length[0,200]']"></textarea></td>
		  		<td class="lefttd" >描述:</td>
		        <td colspan="3"><textarea  class="textareacss easyui-validatebox description"  data-options="validType:['isBlank_','length[0,200]']"></textarea></td>
		  	</tr>
    	</table>
    	<table id="tableContent_4" border="0" cellpadding="0" cellspacing="0" class="bordertable">
		    <tr class="tabletop">
		        <th width="3%" class="text_left">序号 </th> 
		        <th width="12%" class="text_left">物料编码
		        	<span onclick="chooseBomMaterial('tableContent_4');" class="choose">[选择]</span>
		        </th>
		        <th width="20%" class="text_left">品名</th>
		        <th width="30%" class="text_left">规格型号及封装</th>
		        <th width="7%" class="text_left">品牌</th>
		        <th width="5%" class="text_left">用量</th>
		        <th width="13%" class="text_left">描述</th>
		        <th width="8%" class="text_left">联系人</th>
		        <th align="right" style="border-left: 0px">
					<span onclick="addbomMaterialFun('tableContent_4');" class="icon-add tip"  title="添加"></span>
				</th>
		    </tr>
	    </table>
   	   
	    
	    <table id="tableTitle_5" border="0" cellpadding="0" cellspacing="0" class="ordertable" style="margin-bottom: 0px;margin-top: 0px">
		    <tr class="tabletitle">
		        <th colspan="8">c.显示模组
		        	<span onclick="chooseBomMaterialTitle('tableTitle_5');" class="choose">[选择]</span>
		        </th>
		        
		    </tr>
			<tr class="bomMaterialMain">
		      	<td class="lefttd" width="10%">物料编码:</td>
		        <td class="righttd" width="20%"><input type="text" class="inputs easyui-validatebox materialCode materialCodeAutoTitle" data-options="validType:['isBlank_','materialCodeLengthValidate[14]','materialCodeValidate']"/></td>
		        <td class="lefttd" width="5%">品名:</td>
		        <td class="righttd" width="20%"><input type="text" class="inputs easyui-validatebox productName"  data-options="validType:['isBlank_','length[0,64]']"/></td>
		        <td class="lefttd" width="5%"></td>
		        <td class="righttd" width="15%"></td>
		        <td class="lefttd" width="10%"></td>
		        <td class="righttd" width="15%"></td>
		  	</tr>
		  	<tr>
		  		<td class="lefttd">规格型号及封装:</td>
		        <td colspan="3"><textarea  class="textareacss easyui-validatebox specification"  data-options="validType:['isBlank_','length[0,200]']"></textarea></td>
		  		<td class="lefttd" >描述:</td>
		        <td colspan="3"><textarea  class="textareacss easyui-validatebox description"  data-options="validType:['isBlank_','length[0,200]']"></textarea></td>
		  	</tr>
    	</table>
    	<table id="tableContent_5" border="0" cellpadding="0" cellspacing="0" class="bordertable">
		    <tr class="tabletop">
		        <th width="3%" class="text_left">序号 </th> 
		        <th width="12%" class="text_left">物料编码
		        	<span onclick="chooseBomMaterial('tableContent_5');" class="choose">[选择]</span>
		        </th>
		        <th width="20%" class="text_left">品名</th>
		        <th width="30%" class="text_left">规格型号及封装</th>
		        <th width="7%" class="text_left">品牌</th>
		        <th width="5%" class="text_left">用量</th>
		        <th width="13%" class="text_left">描述</th>
		        <th width="8%" class="text_left">联系人</th>
		        <th align="right" style="border-left: 0px">
					<span onclick="addbomMaterialFun('tableContent_5');" class="icon-add tip"  title="添加"></span>
				</th>
		    </tr>
	    </table>
   	   
	    
	    <table id="tableTitle_6" border="0" cellpadding="0" cellspacing="0" class="ordertable" style="margin-bottom: 0px;margin-top: 10px">
		    <tr class="tabletitle">
		        <th colspan="8">第二部分：彩盒模组部分
		        	<span onclick="chooseBomMaterialTitle('tableTitle_6');" class="choose">[选择]</span>
		        </th>
		        
		    </tr>
		  	<tr class="bomMaterialMain">
		      	<td class="lefttd" width="10%">物料编码:</td>
		        <td class="righttd" width="20%"><input type="text" class="inputs easyui-validatebox materialCode materialCodeAutoTitle" data-options="validType:['isBlank_','materialCodeLengthValidate[14]','materialCodeValidate']"/></td>
		        <td class="lefttd" width="5%">品名:</td>
		        <td class="righttd" width="20%"><input type="text" class="inputs easyui-validatebox productName"  data-options="validType:['isBlank_','length[0,64]']"/></td>
		        <td class="lefttd" width="5%"></td>
		        <td class="righttd" width="15%"></td>
		        <td class="lefttd" width="10%"></td>
		        <td class="righttd" width="15%"></td>
		  	</tr>
		  	<tr>
		  		<td class="lefttd">规格型号及封装:</td>
		        <td colspan="3"><textarea  class="textareacss easyui-validatebox specification"  data-options="validType:['isBlank_','length[0,200]']"></textarea></td>
		  		<td class="lefttd" >描述:</td>
		        <td colspan="3"><textarea  class="textareacss easyui-validatebox description"  data-options="validType:['isBlank_','length[0,200]']"></textarea></td>
		  	</tr>
    	</table>
    	<table id="tableContent_6" border="0" cellpadding="0" cellspacing="0" class="bordertable">
		     <tr class="tabletop">
		        <th width="3%" class="text_left">序号 </th> 
		        <th width="12%" class="text_left">物料编码
		        	<span onclick="chooseBomMaterial('tableContent_6');" class="choose">[选择]</span>
		        </th>
		        <th width="20%" class="text_left">品名</th>
		        <th width="30%" class="text_left">规格型号及封装</th>
		        <th width="7%" class="text_left">品牌</th>
		        <th width="5%" class="text_left">用量</th>
		        <th width="13%" class="text_left">描述</th>
		        <th width="8%" class="text_left">联系人</th>
		        <th align="right" style="border-left: 0px">
					<span onclick="addbomMaterialFun('tableContent_6');" class="icon-add tip"  title="添加"></span>
				</th>
		    </tr>
	    </table>
	    
	    <table id="tableTitle_7" border="0" cellpadding="0" cellspacing="0" class="ordertable" style="margin-bottom: 0px;margin-top: 10px">
		    <tr class="tabletitle">
		        <th colspan="8">第三部分：卡通箱模组部分
		        	<span onclick="chooseBomMaterialTitle('tableTitle_7');" class="choose">[选择]</span>
		        </th>
		        
		    </tr>
		  	<tr class="bomMaterialMain">
		      	<td class="lefttd" width="10%">物料编码:</td>
		        <td class="righttd" width="20%"><input type="text" class="inputs easyui-validatebox materialCode materialCodeAutoTitle" data-options="validType:['isBlank_','materialCodeLengthValidate[14]','materialCodeValidate']"/></td>
		        <td class="lefttd" width="5%">品名:</td>
		        <td class="righttd" width="20%"><input type="text" class="inputs easyui-validatebox productName"  data-options="validType:['isBlank_','length[0,64]']"/></td>
		        <td class="lefttd" width="5%"></td>
		        <td class="righttd" width="15%"></td>
		        <td class="lefttd" width="10%"></td>
		        <td class="righttd" width="15%"></td>
		  	</tr>
		  	<tr>
		  		<td class="lefttd">规格型号及封装:</td>
		        <td colspan="3"><textarea  class="textareacss easyui-validatebox specification"  data-options="validType:['isBlank_','length[0,200]']"></textarea></td>
		  		<td class="lefttd" >描述:</td>
		        <td colspan="3"><textarea  class="textareacss easyui-validatebox description"  data-options="validType:['isBlank_','length[0,200]']"></textarea></td>
		  	</tr>
    	</table>
    	<table id="tableContent_7" border="0" cellpadding="0" cellspacing="0" class="bordertable">
		   <tr class="tabletop">
		        <th width="3%" class="text_left">序号 </th> 
		        <th width="12%" class="text_left">物料编码
		        	<span onclick="chooseBomMaterial('tableContent_7');" class="choose">[选择]</span>
		        </th>
		        <th width="20%" class="text_left">品名</th>
		        <th width="30%" class="text_left">规格型号及封装</th>
		        <th width="7%" class="text_left">品牌</th>
		        <th width="5%" class="text_left">用量</th>
		        <th width="13%" class="text_left">描述</th>
		        <th width="8%" class="text_left">联系人</th>
		        <th align="right" style="border-left: 0px">
					<span onclick="addbomMaterialFun('tableContent_7');" class="icon-add tip"  title="添加"></span>
				</th>
		    </tr>
	    </table>
   	    
	    
	    <table id="tableTitle_8" border="0" cellpadding="0" cellspacing="0" class="ordertable" style="margin-bottom: 0px;margin-top: 10px">
		    <tr class="tabletitle">
		        <th colspan="8">第四部分：配件及其他包材类</th>
		        
		    </tr>
		  	<!-- <tr class="bomMaterialMain">
		      	<td class="lefttd" width="10%">物料编码:</td>
		        <td class="righttd" width="20%"><input type="text" class="inputs easyui-validatebox materialCode materialCodeAutoTitle" data-options="validType:['isBlank_','length[0,20]','materialCodeValidate']"/></td>
		        <td class="lefttd" width="5%">品名:</td>
		        <td class="righttd" width="20%"><input type="text" class="inputs easyui-validatebox productName"  data-options="validType:['isBlank_','length[0,20]']"/></td>
		        <td class="lefttd" width="5%"></td>
		        <td class="righttd" width="15%"></td>
		        <td class="lefttd" width="10%"></td>
		        <td class="righttd" width="15%"></td>
		  	</tr>
		  	<tr>
		  		<td class="lefttd">规格型号及封装:</td>
		        <td colspan="3"><textarea  class="textareacss easyui-validatebox specification"  data-options="validType:['isBlank_','length[0,200]']"></textarea></td>
		  		<td class="lefttd" >描述:</td>
		        <td colspan="3"><textarea  class="textareacss easyui-validatebox description"  data-options="validType:['isBlank_','length[0,200]']"></textarea></td>
		  	</tr> -->
    	</table>
   		<table id="tableContent_8" border="0" cellpadding="0" cellspacing="0" class="bordertable">
		    <tr class="tabletop">
		        <th width="3%" class="text_left">序号 </th> 
		        <th width="12%" class="text_left">物料编码
		        	<span onclick="chooseBomMaterial('tableContent_8');" class="choose">[选择]</span>
		        </th>
		        <th width="20%" class="text_left">品名</th>
		        <th width="30%" class="text_left">规格型号及封装</th>
		        <th width="7%" class="text_left">品牌</th>
		        <th width="5%" class="text_left">用量</th>
		        <th width="13%" class="text_left">描述</th>
		        <th width="8%" class="text_left">联系人</th>
		        <th align="right" style="border-left: 0px">
					<span onclick="addbomMaterialFun('tableContent_8');" class="icon-add tip"  title="添加"></span>
				</th>
		    </tr>
	    </table>
	    
	    <table id="tableTitle_9" border="0" cellpadding="0" cellspacing="0" class="ordertable" style="margin-bottom: 0px;margin-top: 10px">
		    <tr class="tabletitle">
		        <th colspan="8">第五部分：备品<!-- <span onclick="chooseBomMaterial('tableContent_9');" class="choose">[选择]</span> --></th>
		    </tr>
		  	<!-- <tr class="bomMaterialMain">
		      	<td class="lefttd" width="10%">物料编码:</td>
		        <td class="righttd" width="15%"><input type="text" class="inputs easyui-validatebox"   data-options="validType:['isBlank_','length[0,20]']"/></td>
		        <td class="lefttd" width="10%">品名:</td>
		        <td class="righttd" width="15%"><input type="text" class="inputs easyui-validatebox"  data-options="validType:['isBlank_','length[0,20]']"/></td>
		        <td class="lefttd" width="10%"></td>
		        <td class="righttd" width="15%"></td>
		        <td class="lefttd" width="10%"></td>
		        <td class="righttd" width="15%"></td>
		  	</tr>
		  	<tr>
		  		<td class="lefttd">规格型号及封装:</td>
		        <td colspan="3"><textarea  class="textareacss"  data-options="validType:['isBlank_','length[0,200]']"></textarea></td>
		  		<td class="lefttd" >描述:</td>
		        <td colspan="3"><textarea  class="textareacss"  data-options="validType:['isBlank_','length[0,200]']"></textarea></td>
		  	</tr> -->
    	</table>
    	<table id="tableContent_9" border="0" cellpadding="0" cellspacing="0" class="bordertable">
		    <tr class="tabletop">
		      	<th width="3%" class="text_left">序号 </th> 
		        <th width="12%" class="text_left"></th>
		        <th width="20%" class="text_left">品名</th>
		        <th width="30%" class="text_left">规格型号及封装</th>
		        <th width="7%" class="text_left"></th>
		        <th width="5%" class="text_left">用量</th>
		        <th width="13%" class="text_left"></th>
		        <th width="8%" class="text_left"></th>
		        <th align="right" style="border-left: 0px">
					<span onclick="addbomMaterialFun('tableContent_9');" class="icon-add tip"  title="添加"></span>
				</th>
		    </tr>
	    </table>
	    
   	  </form>
</div>
<div align="center">
	<button id="" class='btn-default' onclick="editBomFun()">编辑</button> 
</div>
</body>