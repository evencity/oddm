<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>
	.ordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:left;}
	.tabletitle{text-align:left; background-color:#c8d7e1;color:#333;font-size:1.4em; font-weight:500;}
	.tabletop{text-align:left; background-color:#DCE0E2;color:#333;font-size:1.2em; font-weight:500;}
	.tabletop td{text-align: center;}
	.ordertable tr td{padding:10px 0 10px 6px;}
	.ordertable tr th{padding:10px 0 10px 6px;}
	.inputs{border:1px solid #CCC;width:170px;padding:5px 0 2px 4px;font-size:12px;color:#999;border-radius: 4px;}
	.lefttd{width:65px;}
    .lefttd90{width:90px; text-align:right;}
    .bordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:center;border-collapse:collapse;}
	.bordertable tr td{border:1px solid #ccc;padding:10px 0 10px 6px;height:20px;}
	.bordertable tr th{border:1px solid #ccc;padding:10px 0 10px 6px;height:20px;}
	.textareacss{width:100%;border:1px solid #CCC;text-align: left;font-size:12px;border-radius: 4px;}
	.textareacss2{width:100%;background:none;border: 0px}
	.choose{color: #06c;cursor: pointer;}
	.color_f60 {color: #f60;}
	.color069 {color :#069}
	.color_green{color:green}
	.inputs {color :#069}
	.text_left{text-align: left;}
	.tip{height: 16px;width: 16px;display: inline-block;padding: 0px 4px;cursor: pointer;}
	.btn-default {border: 1px solid #DDDDDD;background: white;padding: 10px 18px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #333;}
	.btn-default:hover {border: 1px solid #DDDDDD;background: #04B5F9;padding: 10px 18px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #FFF;}
	.upload_a{float:left;padding: 2px 6px;position: relative;border: 1px solid ;line-height: 24px;height: 24px;font-size: 14px;border-radius: 4px;cursor: pointer;overflow: hidden;display: inline-block;text-decoration: none;}
	.uploadInput{position: absolute;font-size: 100px;right: 0;top: 0;opacity: 0;cursor: pointer;}
	.uploadTip{float:left;margin:0 30px 0 10px;line-height: 34px;font-size: 12px;color: #b3b3b3}
	.uploadDiv{float: left;width: 100%}
	.uploadBar{width: 100%;height:18px; display:none}
</style>
<jsp:include page="../inc.jsp"></jsp:include>
<script src="${ctx}/jslib/aliyun-oss-sdk-4.4.4.min.js"></script>
<script src="${ctx}/jslib/promise-6.1.0.js"></script>
<script type="text/javascript">
	var manufactureId = "${manufactureId}";
	var shell_count = 0;
	var fitting_count = 0;
	var packing_count = 0;
	var orderNo;//订单号
	var state;//当前的状态
	var data = {};
	$(function() {
		$.ajax({
			url:"${ctx}/manufacturePackageTitle/listAll",
			type:"GET",
			dataType: "json",
            contentType: "application/json; charset=utf-8", 
            async: false,
            success: function(data){
            	if(data != null){
            		for(var i in data){
            			if(data[i].id == 3){
            				var $content = $('<table border="0" cellpadding="0" cellspacing="0" class="bordertable manufacturePackageTable" id="manufacturePackage_'+ data[i].id +'">'+
			    					 			'<tr class="tabletop" >'+
													'<td style="border-right: 0px;text-align:left" colspan="2"><span ><b>'+ data[i].name +'</b></span></td>'+
												'</tr>'+
												'<tr align="left" class="manufacturePacking">'+
													'<td width="10%"><span>描述：<span></td>'+
													'<td style="padding:3px" ><textarea class="textareacss" id="packingSN" rows="3" ></textarea></td>'+
												'</tr>'+
											'</table>'); 
			    			$content.data('id',data[i].id)
			    			$content.data('titleName',data[i].name)
			    			console.log($content.data('titleName'))
			    			$('#manufacturePackings').append($content);
			    			/* var content = '<tr align="left" class="manufacturePacking">'+
												'<td width="10%"><span>描述：<span></td>'+
												'<td style="padding:3px" ><textarea class="textareacss packing_description" rows="3" ></textarea></td>'+
											'</tr>';
							$content.append(content); */
			    			
			    			
            			}else{
            				var $content = $('<table border="0" cellpadding="0" cellspacing="0" class="bordertable manufacturePackageTable" id="manufacturePackage_'+ data[i].id +'">'+
			    					 			'<tr class="tabletop" >'+
												'<td style="border-right: 0px;text-align:left" colspan="2"><span ><b>'+ data[i].name +'</b></span></td>'+
												'<td align="right" style="border-left: 0px;text-align:right">'+
													'<span onclick="addManufacturePackingFun('+ data[i].id +');" class="icon-add tip"  title="添加"></span>'+
												'</td>'+
											'</tr></table>'); 
			    			$content.data('id',data[i].id)
			    			$content.data('titleName',data[i].name)
			    			$('#manufacturePackings').append($content);
            			}
            		}
            	}
            }
		})
		
		$.ajax({
			url:"${ctx}/manufacture/get?id="+ manufactureId,
			type:"GET",
			dataType: "json",
            contentType: "application/json; charset=utf-8", 
            async: false,
            success: function(data){
            	if(data != null){
            		$('#version').val(data.version);
            		$('#clientName').html(data.clientName);
            		$('#dateIssue').datebox('setValue',data.dateIssue);
            		$('#dateShipment').datebox('setValue',data.dateShipment);
            		$('#orderNo').html(data.orderNo);
            		orderNo = data.orderNo;
            		state = data.state;
            		$('#drafter').val(data.drafter);
            		$('#auditor').val(data.auditor);
            		$('#approver').val(data.approver);
            		$('#remark').val(data.remark);
            		$('#notice').val(data.notice);
            		//软件
            		if(data.manufactureOsDTO != null){
            			var manufactureOsDTO = data.manufactureOsDTO;
            			$('#versionApp').val(manufactureOsDTO.versionApp);
            			$('#versionApp').data('id',manufactureOsDTO.id);
                		$('#versionCore').val(manufactureOsDTO.versionCore);
                		$('#versionMap').val(manufactureOsDTO.versionMap);
                		$('#preFile').val(manufactureOsDTO.preFile);
                		$('#uuid').val(manufactureOsDTO.uuid);
                		$('#versionFirmware').val(manufactureOsDTO.versionFirmware);
                		$('#description').val(manufactureOsDTO.description);
                		$('#otherRequire').val(manufactureOsDTO.otherRequire);
                		$('#os_notice').val(manufactureOsDTO.notice);
            		}
            		
            		//外壳
            		var picMaxWidth_shell = $('#manufactureShells .tabletop td').eq(1).css('width');//图片的最大宽度为标题的宽
            		if(data.manufactureShellDTOs != null && data.manufactureShellDTOs.length > 0){
            			var manufactureShellDTOs = data.manufactureShellDTOs;
            			for(var i in manufactureShellDTOs){
            				if(manufactureShellDTOs[i].color == null || typeof(manufactureShellDTOs[i].color) == 'undefined'){
            					manufactureShellDTOs[i].color = "";
            				}
            				if(manufactureShellDTOs[i].pictPaths == null || typeof(manufactureShellDTOs[i].pictPaths) == 'undefined'){
            					manufactureShellDTOs[i].pictPaths = "";
            				}
            				if(manufactureShellDTOs[i].craft == null || typeof(manufactureShellDTOs[i].craft) == 'undefined'){
            					manufactureShellDTOs[i].craft = "";
            				}
            				if(manufactureShellDTOs[i].silk == null || typeof(manufactureShellDTOs[i].silk) == 'undefined'){
            					manufactureShellDTOs[i].silk = "";
            				}
            				if(manufactureShellDTOs[i].description == null || typeof(manufactureShellDTOs[i].description) == 'undefined'){
            					manufactureShellDTOs[i].description = "";
            				}
            				var picPath = getPicPath(manufactureShellDTOs[i].pictPaths);
            				var $content = $('<tr class="manufactureShell">'+
				    						'<td style="padding:3px"><textarea class="textareacss shell_name" rows="8">'+ manufactureShellDTOs[i].name +'</textarea></td>'+
				    						'<td style="padding:3px">'+
					    						'<div class="uploadDiv"><a class="upload_a"><input class="uploadInput" type="file" id="shellInput_'+shell_count+'"  accept="image/*">选择文件</a><span class="uploadTip">未选择任何文件</span></div>'+
					    						'<div><img id="shellImgPr_'+shell_count+'" src="'+ picPath +'"/></div>'+
					    						'<div class="uploadBar" id="shellBar_'+ shell_count +'" ></div>'+
				    						'</td>'+
				    						'<td style="padding:3px"><textarea class="textareacss shell_color"  rows="8">'+ manufactureShellDTOs[i].color +'</textarea></td>'+
				    						'<td style="padding:3px"><textarea class="textareacss shell_craft" rows="8">'+ manufactureShellDTOs[i].craft +'</textarea></td>'+
				    						'<td style="padding:3px"><textarea class="textareacss shell_silk" rows="8">'+ manufactureShellDTOs[i].silk +'</textarea></td>'+
				    						'<td style="padding:3px"><textarea class="textareacss shell_description" rows="8">'+ manufactureShellDTOs[i].description +'</textarea></td>'+
				    						'<td style="padding: 0px">'+
					    						'<span onclick="delManufactureShellFun(this);" class="icon-del tip"  title="删除"></span>'+
					    					'</td>'+
				    					'</tr>');
            				$content.data('id',manufactureShellDTOs[i].id);
            				$content.data('oldPicpath',manufactureShellDTOs[i].pictPaths)
            				$('#manufactureShells').append($content);
            				//校验
            				$('.shell_name').validatebox({   
            					required: true,   
            				    validType: ['isBlank_','length[0,64]']  
            				});
            				$('.shell_color').validatebox({    
            				    validType: ['isBlank_','length[0,10]']  
            				});
            				$('.shell_craft').validatebox({    
            				    validType: ['isBlank_','length[0,64]']  
            				});
            				$('.shell_silk').validatebox({    
            				    validType: ['isBlank_','length[0,200]']  
            				});
            				$('.shell_description').validatebox({    
            				    validType: ['isBlank_','length[0,500]']  
            				});
            				if(manufactureShellDTOs[i].pictAttribute != null && typeof(manufactureShellDTOs[i].pictAttribute) != 'undefined'){
            					var pictAttributes = manufactureShellDTOs[i].pictAttribute.split("*");
            					var picWidth = pictAttributes[0];
                				var pictHeight = pictAttributes[1];
                				if(picWidth > parseInt(picMaxWidth_shell)){
				                	$("#shellImgPr_" + shell_count).css({
			                            'max-width': picMaxWidth_shell
			                        });
				                }
            				}
            				$("#shellInput_"+shell_count).data('picPath',manufactureShellDTOs[i].pictPaths);
            				$("#shellImgPr_"+shell_count).data('pictAttribute',manufactureShellDTOs[i].pictAttribute);
            				$("#shellInput_"+shell_count).uploadPreview({ Img: "shellImgPr_"+shell_count, Width: '100%', Height: 'auto' ,maxWidth:picMaxWidth_shell});
            				//进度条
            				$("#shellBar_"+shell_count).progressbar({value:0})
            				shell_count++;
            			}
            		}
            		
            		//配件
            		var picMaxWidth_fitting = $('#manufactureFittings .tabletop td').eq(1).css('width');//图片的最大宽度为标题的宽
            		if(data.manufactureFittingDTOs != null && data.manufactureFittingDTOs.length > 0){
            			var manufactureFittingDTOs = data.manufactureFittingDTOs;
            			for(var i in manufactureFittingDTOs){
            				if(manufactureFittingDTOs[i].specification == null || typeof(manufactureFittingDTOs[i].specification) == 'undefined'){
            					manufactureFittingDTOs[i].specification = "";
            				}
            				if(manufactureFittingDTOs[i].pictPaths == null || typeof(manufactureFittingDTOs[i].pictPaths) == 'undefined'){
            					manufactureFittingDTOs[i].pictPaths = "";
            				}
            				if(manufactureFittingDTOs[i].packBag == null || typeof(manufactureFittingDTOs[i].packBag) == 'undefined'){
            					manufactureFittingDTOs[i].packBag = "";
            				}
            				if(manufactureFittingDTOs[i].label == null || typeof(manufactureFittingDTOs[i].label) == 'undefined'){
            					manufactureFittingDTOs[i].label = "";
            				}
            				if(manufactureFittingDTOs[i].description == null || typeof(manufactureFittingDTOs[i].description) == 'undefined'){
            					manufactureFittingDTOs[i].description = "";
            				}
            				var picPath = getPicPath(manufactureFittingDTOs[i].pictPaths);
            				var $content = $('<tr class="manufactureFitting">'+
				    						'<td style="padding:3px"><textarea class="textareacss fitting_name" rows="8">'+ manufactureFittingDTOs[i].name +'</textarea></td>'+
				    						'<td style="padding:3px">'+
					    						'<div class="uploadDiv"><a class="upload_a"><input class="uploadInput" type="file" id="fittingInput_'+fitting_count+'" accept="image/*" >选择文件</a><span class="uploadTip">未选择任何文件</span></div>'+
					    						'<div><img id="fittingImgPr_'+fitting_count+'" src="'+ picPath +'"/></div>'+
					    						'<div class="uploadBar" id="fittingBar_'+ fitting_count +'" ></div>'+
				    						'</td>'+
				    						'<td style="padding:3px"><textarea class="textareacss fitting_specification"  rows="8">'+ manufactureFittingDTOs[i].specification +'</textarea></td>'+
				    						'<td style="padding:3px"><textarea class="textareacss fitting_packBag" rows="8">'+ manufactureFittingDTOs[i].packBag +'</textarea></td>'+
				    						'<td style="padding:3px"><textarea class="textareacss fitting_lable" rows="8">'+ manufactureFittingDTOs[i].label +'</textarea></td>'+
				    						'<td style="padding:3px"><textarea class="textareacss fitting_description" rows="8">'+ manufactureFittingDTOs[i].description +'</textarea></td>'+
				    						'<td style="padding: 0px">'+
					    						'<span onclick="delManufactureFittingFun(this);" class="icon-del tip"  title="删除"></span>'+
					    					'</td>'+
				    					'</tr>');
				    		$content.data('id',manufactureFittingDTOs[i].id)
				    		$content.data('oldPicpath',manufactureFittingDTOs[i].pictPaths)
            				$('#manufactureFittings').append($content);
				    		
				    		//校验
				    		$('.fitting_name').validatebox({    
				    			required: true,   
				    		    validType: ['isBlank_','length[0,64]']  
				    		});
				    		$('.fitting_specification').validatebox({    
				    		    validType: ['isBlank_','length[0,200]']  
				    		});
				    		$('.fitting_packBag').validatebox({    
				    		    validType: ['isBlank_','length[0,64]']  
				    		});
				    		$('.fitting_lable').validatebox({    
				    		    validType: ['isBlank_','length[0,200]']  
				    		});
				    		$('.fitting_description').validatebox({    
				    		    validType: ['isBlank_','length[0,500]']  
				    		});
				    		if(manufactureFittingDTOs[i].pictAttribute != null && typeof(manufactureFittingDTOs[i].pictAttribute) != 'undefined'){
            					var pictAttributes = manufactureFittingDTOs[i].pictAttribute.split("*");
            					var picWidth = pictAttributes[0];
                				var pictHeight = pictAttributes[1];
                				if(picWidth > parseInt(picMaxWidth_fitting)){
				                	$("#fittingImgPr_" + fitting_count).css({
			                            'max-width': picMaxWidth_fitting
			                        });
				                }
            				}
            				$("#fittingInput_"+fitting_count).data('picPath',manufactureFittingDTOs[i].pictPaths);
            				$("#fittingImgPr_"+fitting_count).data('pictAttribute',manufactureFittingDTOs[i].pictAttribute);
            				$("#fittingInput_"+fitting_count).uploadPreview({ Img: "fittingImgPr_"+fitting_count, Width: '100%', Height: 'auto' ,maxWidth:picMaxWidth_fitting});
            				//进度条
            				$("#fittingBar_"+fitting_count).progressbar({value:0})
            				fitting_count++;
            			}
            		}
            		
            		//包材
            		if(data.manufacturePackageDTOs != null && data.manufacturePackageDTOs.length > 0){
            			var manufacturePackageDTOs = data.manufacturePackageDTOs;
            			for(var i in manufacturePackageDTOs){
            				if(manufacturePackageDTOs[i].pictTitle == null || typeof(manufacturePackageDTOs[i].pictTitle) == 'undefined'){
            					manufacturePackageDTOs[i].pictTitle = "";
            				}
            				if(manufacturePackageDTOs[i].description == null || typeof(manufacturePackageDTOs[i].description) == 'undefined'){
            					manufacturePackageDTOs[i].description = "";
            				}
            				if(manufacturePackageDTOs[i].titleId != null || typeof(manufacturePackageDTOs[i].titleId) == 'undefined'){
            					var titleId = manufacturePackageDTOs[i].titleId;
            					if(titleId != 3 || titleId != '3'){
            						var $content = $('<tr align="left" class="manufacturePacking" >'+
				            						'<td width="10%"><span>标题：<span></td>'+
				            						'<td style="padding:3px" width="90%"><input class="inputs packing_name" style="width:90%" value="'+ manufacturePackageDTOs[i].pictTitle +'"></td>'+
				            						'<td style="padding: 0px" align="right">'+
				            							'<span onclick="delManufacturePackingFun(this);" class="icon-del tip"  title="删除"></span>'+
				            						'</td>'+
				            					'</tr>');
				            		$("#manufacturePackage_"+titleId).append($content);
				            		$content.data('titleId',titleId);
				            		var titleName = $("#manufacturePackage_"+titleId).data('titleName')
				            		$content.data('titleName',titleName);
				            		$content.data('id',manufacturePackageDTOs[i].id)
				            		
				            		var picPath = getPicPath(manufacturePackageDTOs[i].pictPaths);
				            		var $content = $('<tr align="left" class="">'+
				            						'<td width="10%"><span>图片：<span></td>'+
				            						'<td style="padding:3px">'+
					        							'<div class="uploadDiv"><a class="upload_a"><input class="uploadInput" type="file" id="packingInput_'+packing_count+'" accept="image/*">选择文件</a><span class="uploadTip">未选择任何文件</span></div>'+
					        							'<div><img id="packingImgPr_'+packing_count+'" src="'+ picPath +'"/></div>'+
					        							'<div class="uploadBar" id="packingBar_'+ packing_count +'" ></div>'+
					        						'</td>'+
				            					'</tr>');
				            		$content.data('id',manufacturePackageDTOs[i].id)
				            		$content.data('oldPicpath',manufacturePackageDTOs[i].pictPaths)
				            		$("#manufacturePackage_"+titleId).append($content);
				            		
				            		
				            		var picMaxWidth_packing = $("#packingBar_"+packing_count).parent().outerWidth(true);
				            		if(manufacturePackageDTOs[i].pictAttribute != null && typeof(manufacturePackageDTOs[i].pictAttribute) != 'undefined'){
		            					var pictAttributes = manufacturePackageDTOs[i].pictAttribute.split("*");
		            					var picWidth = pictAttributes[0];
		                				var pictHeight = pictAttributes[1];
		                				if(picWidth > parseInt(picMaxWidth_packing)){
						                	$("#packingImgPr_" + packing_count).css({
					                           // 'max-width': picMaxWidth_packing
					                           'width' : '100%'
					                        });
						                }
		            				}
		            				$("#packingInput_"+packing_count).data('picPath',manufacturePackageDTOs[i].pictPaths);
		            				$("#packingImgPr_"+packing_count).data('pictAttribute',manufacturePackageDTOs[i].pictAttribute);
		            				$("#packingInput_"+packing_count).uploadPreview({ Img: "packingImgPr_"+packing_count, Width: '100%', Height: 'auto' ,maxWidth:picMaxWidth_packing});
		            				//进度条
		            				$("#packingBar_"+packing_count).progressbar({value:0})
		            				packing_count++;
		            				
		            				
				            		
				            		var content = '<tr align="left" class="">'+
				            							'<td width="10%"><span>描述：<span></td>'+
				            							'<td style="padding:3px" ><textarea class="textareacss packing_description" rows="3" >'+ manufacturePackageDTOs[i].description +'</textarea></td>'+
				            						'</tr>';
				            		$("#manufacturePackage_"+titleId).append(content);
				            		
				            		$('.packing_name').validatebox({    
				            			required: true,   
				            		    validType: ['isBlank_','length[0,64]']  
				            		});
				            		$('.packing_description').validatebox({    
				            		    validType: ['isBlank_','length[0,200]']  
				            		});
            					}else{
            						var textarea = $("#manufacturePackage_"+titleId).find('textarea');
            						if(textarea != null && typeof(textarea) != 'undefined'){
            							textarea.val(manufacturePackageDTOs[i].description);
            							$("#manufacturePackage_"+titleId).data('contentId',manufacturePackageDTOs[i].id);
            						}
            					}
            				}
            			}
            		}
            	}
            }
		})
		$('#packingSN').validatebox({    
			required: true,   
		    validType: ['isBlank_','length[0,200]']  
		});
		
	});
	
	function getToken(orderNo){
		var clientToken = {};
		if(orderNo != null && typeof(orderNo) != null ){
			$.ajax({
				url : '${pageContext.request.contextPath}/token/update?fileType=picture&fileDetail=produce&orderNo='+orderNo,
				type : "POST",
				dataType : "json",
				async : false,
				contentType : "application/json; charset=utf-8",
				success : function(data) {
					client = new OSS.Wrapper({
						region: data.region,
						accessKeyId: data.accessKeyId,
						accessKeySecret: data.accessKeySecret,
						stsToken : data.securityToken,
						bucket: data.bucket,
					});
					clientToken.region = data.region;
					clientToken.accessKeyId = data.accessKeyId;
					clientToken.accessKeySecret = data.accessKeySecret;
					clientToken.securityToken = data.securityToken;
					clientToken.bucket = data.bucket
					clientToken.path = data.path;
					clientToken.expiration = data.expiration;
					//$.extend(clientToken,{'path':data.path} );  
				}
			})
		}
		return clientToken;
	}
	function isEmptyObject(e) {  
	    var t;  
	    for (t in e)  
	        return !1;  
	    return !0  
	} 
	//计算是否超时
	function isOvertime(data){
		var overtime = new Date(data);
		if(overtime.getTime() - new Date().getTime() < 0){
			return true;
		}
		return false;
	}
	//获取图片的阿里云路径
	var data = {};
	function getPicPath(filePath){
		var path = '';
		if(filePath != ''){
			if(isEmptyObject(data)){
				data = getToken(orderNo);
			}
			if(isOvertime(data.expiration)){
				 data = getToken(orderNo);
			}
			if(data != null && data != {}){
				client = new OSS.Wrapper({
					region: data.region,
					accessKeyId: data.accessKeyId,
					accessKeySecret: data.accessKeySecret,
					stsToken : data.securityToken,
					bucket: data.bucket
		        });
				var objectKey = filePath;
			    console.log(objectKey + ' => ');
				var result = client.signatureUrl(objectKey, {
				     expires: 3600,
				});
				path = result;
			}
		}
		return path;
	}
	
	
	function addManufactureShellFun(){
		var picMaxWidth_shell = $('#manufactureShells .tabletop td').eq(1).css('width');//图片的最大宽度为标题的宽
		var content = '<tr class="manufactureShell">'+
				'<td style="padding:3px"><textarea class="textareacss shell_name" id="" rows="8" ></textarea></td>'+
				'<td style="padding:3px">'+
					'<div class="uploadDiv"><a class="upload_a"><input class="uploadInput" type="file" id="shellInput_'+shell_count+'" accept="image/*">选择文件</a><span class="uploadTip" >未选择任何文件</span></div>'+
					'<div><img id="shellImgPr_'+shell_count+'" /></div>'+
					'<div class="uploadBar" id="shellBar_'+ shell_count +'" ></div>'+
				'</td>'+
				'<td style="padding:3px"><textarea class="textareacss shell_color" rows="8" ></textarea></td>'+
				'<td style="padding:3px"><textarea class="textareacss shell_craft" rows="8" ></textarea></td>'+
				'<td style="padding:3px"><textarea class="textareacss shell_silk" rows="8" ></textarea></td>'+
				'<td style="padding:3px"><textarea class="textareacss shell_description" rows="8" ></textarea></td>'+
				'<td style="padding: 0px">'+
					'<span onclick="delManufactureShellFun(this);" class="icon-del tip"  title="删除"></span>'+
				'</td>'+
			'</tr>';
		$('#manufactureShells').append(content);
		$("#shellInput_"+shell_count).uploadPreview({ Img: "shellImgPr_"+shell_count, Width: '100%', Height: 'auto' ,maxWidth:picMaxWidth_shell});
		//进度条
		$("#shellBar_"+shell_count).progressbar({value:0})
		shell_count ++ ;
		//校验
		$('.shell_name').validatebox({   
			required: true,   
		    validType: ['isBlank_','length[0,64]']  
		});
		$('.shell_color').validatebox({    
		    validType: ['isBlank_','length[0,10]']  
		});
		$('.shell_craft').validatebox({    
		    validType: ['isBlank_','length[0,64]']  
		});
		$('.shell_silk').validatebox({    
		    validType: ['isBlank_','length[0,200]']  
		});
		$('.shell_description').validatebox({    
		    validType: ['isBlank_','length[0,500]']  
		});
		
		
	}
	function delManufactureShellFun(obj){
		parent.$.messager.confirm('询问', '您是否要删除整行数据？', function(b) {
			if (b) {
				//删除数据库数据和阿里的路径
				var trObj = $(obj).parent().parent();
				var id = trObj.data('id');
				if(id != null && typeof(id) != 'undefined'){
					progressLoad();
					$.post('${ctx}/manufactureShell/delete', {
						id : id,
						state : state
					}, function(result) {
						if (result.success) {
							parent.$.messager.alert('提示', result.msg, 'info');
							var inputValue = trObj.children().eq(1).find("input[type='file']");
							console.log(inputValue)
							if(inputValue.data('picPath') != null && typeof(inputValue.data('picPath')) != 'undefined'){
								deletePicOSS(inputValue.data('picPath'))
							}else{
								var oldPicpath = trObj.data('oldPicpath')
								if(oldPicpath != null && typeof(oldPicpath) != 'undefined'){
									deletePicOSS(oldPicpath)
								}
							}
							trObj.remove();
						}else{
							parent.$.messager.alert('提示', result.msg, 'info');
						}
						progressClose();
					}, 'JSON');
					progressClose(); 
				}
				
			}
		});
		
	}
	
	function addManufactureFittingFun(){
		var picMaxWidth_fitting = $('#manufactureFittings .tabletop td').eq(1).css('width');//图片的最大宽度为标题的宽
		var content = '<tr class="manufactureFitting">'+
				'<td style="padding:3px"><textarea class="textareacss fitting_name" rows="8" ></textarea></td>'+
				'<td style="padding:3px">'+
					'<div class="uploadDiv"><a class="upload_a"><input class="uploadInput" type="file" id="fittingInput_'+fitting_count+'"  accept="image/*">选择文件</a><span class="uploadTip">未选择任何文件</span></div>'+
					'<div><img id="fittingImgPr_'+fitting_count+'" /></div>'+
					'<div class="uploadBar" id="fittingBar_'+ fitting_count +'" ></div>'+
				'</td>'+
				'<td style="padding:3px"><textarea class="textareacss fitting_specification" rows="8" ></textarea></td>'+
				'<td style="padding:3px"><textarea class="textareacss fitting_packBag" rows="8" ></textarea></td>'+
				'<td style="padding:3px"><textarea class="textareacss fitting_lable" rows="8" ></textarea></td>'+
				'<td style="padding:3px"><textarea class="textareacss fitting_description" rows="8" ></textarea></td>'+
				'<td style="padding: 0px">'+
					'<span onclick="delManufactureFittingFun(this);" class="icon-del tip"  title="删除"></span>'+
				'</td>'+
			'</tr>';
		$('#manufactureFittings').append(content);
		$("#fittingInput_"+fitting_count).uploadPreview({ Img: "fittingImgPr_"+fitting_count, Width: '100%', Height: 'auto' ,maxWidth:picMaxWidth_fitting });
		//进度条
		$("#fittingBar_"+fitting_count).progressbar({value:0})
		fitting_count ++ ;
		//校验
		$('.fitting_name').validatebox({    
			required: true,   
		    validType: ['isBlank_','length[0,64]']  
		});
		$('.fitting_specification').validatebox({    
		    validType: ['isBlank_','length[0,200]']  
		});
		$('.fitting_packBag').validatebox({    
		    validType: ['isBlank_','length[0,64]']  
		});
		$('.fitting_lable').validatebox({    
		    validType: ['isBlank_','length[0,200]']  
		});
		$('.fitting_description').validatebox({    
		    validType: ['isBlank_','length[0,500]']  
		});
	}
	function delManufactureFittingFun(obj){
		parent.$.messager.confirm('询问', '您是否要删除整行数据？', function(b) {
			if (b) {
				//删除数据库数据和阿里的路径
				var trObj = $(obj).parent().parent();
				var id = trObj.data('id');
				if(id != null && typeof(id) != 'undefined'){
					progressLoad();
					$.post('${ctx}/manufactureFitting/delete', {
						id : id,
						state : state
					}, function(result) {
						if (result.success) {
							parent.$.messager.alert('提示', result.msg, 'info');
							var inputValue = trObj.children().eq(1).find("input[type='file']");
							if(inputValue.data('picPath') != null && typeof(inputValue.data('picPath')) != 'undefined'){
								deletePicOSS(inputValue.data('picPath'))
							}else{
								var oldPicpath = trObj.data('oldPicpath')
								if(oldPicpath != null && typeof(oldPicpath) != 'undefined'){
									deletePicOSS(oldPicpath)
								}
							}
							trObj.remove();
						}else{
							parent.$.messager.alert('提示', result.msg, 'info');
						}
						progressClose();
					}, 'JSON');
					progressClose(); 
				}
			}
		});
		
	}
	
	function addManufacturePackingFun(titleId){
		var $content = $('<tr align="left" class="manufacturePacking" >'+
						'<td width="10%"><span>标题：<span></td>'+
						'<td style="padding:3px" width="90%"><input class="inputs packing_name" style="width:90%" ></td>'+
						'<td style="padding: 0px" align="right">'+
							'<span onclick="delManufacturePackingFun(this);" class="icon-del tip"  title="删除"></span>'+
						'</td>'+
					'</tr>');
		$("#manufacturePackage_"+titleId).append($content);
		var titleName = $("#manufacturePackage_"+titleId).data('titleName');
		$content.data('titleId',titleId)
		$content.data('titleName',titleName)
		
		var content = '<tr align="left" class="">'+
						'<td width="10%"><span>图片：<span></td>'+
						'<td style="padding:3px">'+
							'<div class="uploadDiv"><a class="upload_a"><input class="uploadInput" type="file" id="packingInput_'+packing_count+'" accept="image/*">选择文件</a><span class="uploadTip">未选择任何文件</span></div>'+
							'<div><img id="packingImgPr_'+packing_count+'" /></div>'+
							'<div class="uploadBar" id="packingBar_'+ packing_count +'" ></div>'+
						'</td>'+
					'</tr>';
		$("#manufacturePackage_"+titleId).append(content);
		
		var content = '<tr align="left" class="">'+
							'<td width="10%"><span>描述：<span></td>'+
							'<td style="padding:3px" ><textarea class="textareacss packing_description" rows="3" ></textarea></td>'+
						'</tr>';
		$("#manufacturePackage_"+titleId).append(content);
		var picMaxWidth_packing = $("#packingBar_"+packing_count).parent().css('width');
		$("#packingInput_"+packing_count).uploadPreview({ Img: "packingImgPr_"+packing_count, Width: '100%', Height: 'auto' ,maxWidth:picMaxWidth_packing});
		//进度条
		$("#packingBar_"+packing_count).progressbar({value:0})
		
		packing_count ++ ;
		
		$('.packing_name').validatebox({    
			required: true,   
		    validType: ['isBlank_','length[0,64]']  
		});
		$('.packing_description').validatebox({    
		    validType: ['isBlank_','length[0,200]']  
		});
	}
	function delManufacturePackingFun(obj){
		parent.$.messager.confirm('询问', '您是否要删除整行数据？', function(b) {
			if (b) {
				//删除数据库数据和阿里的路径
				var trObj = $(obj).parent().parent();
				
				var id = trObj.next().data('id');
				var inputValue = trObj.next().find("input[type='file']");
				var picPath = inputValue.data('picPath');
				var oldPicpath = trObj.next().data('oldPicpath')
				if(id != null && typeof(id) != 'undefined'){
					progressLoad();
					$.post('${ctx}/manufacturePackage/delete', {
						id : id,
						state : state
					}, function(result) {
						if (result.success) {
							parent.$.messager.alert('提示', result.msg, 'info');
							
							if(picPath != null && typeof(picPath) != 'undefined'){
								deletePicOSS(picPath)
							}else{
								if(oldPicpath != null && typeof(oldPicpath) != 'undefined'){
									deletePicOSS(oldPicpath)
								}
							}
							
							
						}else{
							parent.$.messager.alert('提示', result.msg, 'info');
						}
						progressClose();
					}, 'JSON');
					progressClose(); 
				}
				trObj.next().next().remove();
				trObj.next().remove();
				trObj.remove();
			}
		});
	}
	function manufactureEdit(){
		var isValid = $('#manufactureEditForm').form('validate');
		console.log("manufactureEdit 验证"+isValid)
		if(!isValid){
			return;
		}
		//基本信息
		var orderNo = $("#orderNo").html();
		var clientName = $("#clientName").html();
		
		var dateIssue = $('#dateIssue').datebox('getValue');
		var dateShipment = $('#dateShipment').datebox('getValue');
		var version = $('#version').val()==null?null:$('#version').val().trim();
		var drafter = $('#drafter').val()==null?null:$('#drafter').val().trim();
		var auditor = $('#auditor').val()==null?null:$('#auditor').val().trim();
		var approver = $('#approver').val()==null?null:$('#approver').val().trim();
		var remark = $('#remark').val()==null?null:$('#remark').val().trim();
		var notice = $('#notice').val()==null?null:$('#notice').val().trim();
		
		//软件
		var versionApp = $('#versionApp').val()==null?null:$('#versionApp').val().trim();
		var versionCore = $('#versionCore').val()==null?null:$('#versionCore').val().trim();
		var versionMap = $('#versionMap').val()==null?null:$('#versionMap').val().trim();
		var preFile = $('#preFile').val()==null?null:$('#preFile').val().trim();
		var uuid = $('#uuid').val()==null?null:$('#uuid').val().trim();
		var versionFirmware = $('#versionFirmware').val()==null?null:$('#versionFirmware').val().trim();
		var description = $('#description').val()==null?null:$('#description').val().trim();
		var otherRequire = $('#otherRequire').val()==null?null:$('#otherRequire').val().trim();
		var os_notice = $('#os_notice').val()==null?null:$('#os_notice').val().trim();
		var manufactureOsCommand = {
				id : $('#versionApp').data('id'),
				versionApp : versionApp,
				versionCore : versionCore,
				versionMap : versionMap,
				preFile : preFile,
				uuid : uuid,
				versionFirmware : versionFirmware,
				description : description,
				otherRequire : otherRequire,
				notice : os_notice,
		}
		//外壳
		var manufactureShells = [];
		$('#manufactureShells').find('.manufactureShell').each(function(){
			var tdValue = $(this).children();
			var name = tdValue.eq(0).find("textarea").val().trim();
			
			var picPath = "";
			var fileValue = tdValue.eq(1).find("input[type='file']");
			if(fileValue != null || typeof(fileValue) != 'undefined'){
				picPath = fileValue.data('picPath');
			}
			var pictAttribute = "";
			var imageValue = tdValue.eq(1).find("img");
			if(imageValue != null || typeof(imageValue) != 'undefined'){
				pictAttribute = imageValue.data('pictAttribute');
			}
			var color = tdValue.eq(2).find("textarea").val().trim();
			var craft = tdValue.eq(3).find("textarea").val().trim();
			var silk = tdValue.eq(4).find("textarea").val().trim();
			var description = tdValue.eq(5).find("textarea").val().trim();
			
			var manufactureShell = {
				id : $(this).data('id'),
				name : name,
				pictPaths : picPath,
				pictAttribute : pictAttribute,
				color : color,
				craft : craft,
				silk : silk,
				description : description
			}
			manufactureShells.push(manufactureShell);
		})
		
		//配件
		var manufactureFittings = [];
		$('#manufactureFittings').find('.manufactureFitting').each(function(){
			var tdValue = $(this).children();
			var name = tdValue.eq(0).find("textarea").val().trim();
			var picPath = "";
			var fileValue = tdValue.eq(1).find("input[type='file']");
			if(fileValue != null || typeof(fileValue) != 'undefined'){
				picPath = fileValue.data('picPath');
			}
			var pictAttribute = "";
			var imageValue = tdValue.eq(1).find("img");
			if(imageValue != null || typeof(imageValue) != 'undefined'){
				pictAttribute = imageValue.data('pictAttribute');
			}
			var specification = tdValue.eq(2).find("textarea").val().trim();
			var packBag = tdValue.eq(3).find("textarea").val().trim();
			var label = tdValue.eq(4).find("textarea").val().trim();
			var description = tdValue.eq(5).find("textarea").val().trim();
			
			var manufactureFitting = {
				id : $(this).data('id'),
				name : name,
				pictPaths : picPath,
				pictAttribute : pictAttribute,
				specification : specification,
				packBag : packBag,
				label : label,
				description : description
			}
			manufactureFittings.push(manufactureFitting);
		})
		
		//包材
		var manufacturePackings = [];
		$('#manufacturePackings').find('.manufacturePacking').each(function(){
			var table = $(this).parents('.manufacturePackageTable');
			if(table.attr('id') != null && typeof(table.attr('id')) != 'undefined'){
				var tableId = table.attr('id')
				console.log(tableId)
				if(tableId.indexOf('3') >= 0){
					//alert( table.data('id')+ "::::"+table.data('titleId'))
					var description = table.find("textarea").val();
					if(description != null && typeof(description) != 'undefined'){
						description = description.trim()
					}else{
						description = '';
					}
					var manufacturePacking = {
						id : table.data('contentId'),
						description : description,
						titleId : table.data('id')
					}
					manufacturePackings.push(manufacturePacking);
				}else{
					var tdValue = $(this).children();
					var pictTitle = tdValue.eq(1).find("input").val().trim();
					var picPath = "";
					var fileValue = $(this).next().children().eq(1).find("input[type='file']");
					console.log(fileValue)
					if(fileValue != null || typeof(fileValue) != 'undefined'){
						if(fileValue.data('picPath') != null && typeof(fileValue.data('picPath')) != 'undefined'){
							picPath = fileValue.data('picPath');
						}
					}
					var pictAttribute = "";
					var imageValue = $(this).next().children().eq(1).find("img");
					if(imageValue != null || typeof(imageValue) != 'undefined'){
						pictAttribute = imageValue.data('pictAttribute');
					}
					var description = $(this).next().next().children().eq(1).find("textarea").val().trim();
					var titleId = $(this).data('titleId');
					var manufacturePacking = {
						id : $(this).data('id'),
						pictTitle : pictTitle,
						pictPaths : picPath,
						pictAttribute : pictAttribute,
						description : description,
						titleId : titleId
					}
					manufacturePackings.push(manufacturePacking);
				}
			}
			
		})
		
		
		var param = {
				id : manufactureId,
				orderNo : orderNo,
				clientName : clientName,
				dateIssue : dateIssue,
				dateShipment : dateShipment,
				version : version,
				drafter : drafter,
				auditor : auditor,
				approver : approver,
				remark : remark,
				notice : notice,
				state : state,
				
				manufactureOsCommand : manufactureOsCommand,
				manufactureShellCommands : manufactureShells,
				manufactureFittingCommands : manufactureFittings,
				manufacturePackageCommands : manufacturePackings
				
		}
		 progressLoad();
		 $.ajax({
        	type: 'POST',
            url: "${ctx}/manufacture/edit" ,
            data: JSON.stringify(param) ,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function(data){
           	 if(data.success){
           		 parent.$.messager.alert('提示', '修改成功');
           		 var parent_tabs = parent.$('#index_tabs');
           		 var index = parent_tabs.tabs('getTabIndex', parent.$('#index_tabs').tabs('getSelected'));
				 var tab = parent_tabs.tabs('getTab', index);
				 if (tab.panel('options').closable) {
					parent_tabs.tabs('close', index);
				 }
				parent_tabs.tabs('select', "订单包装工艺指导书");
				parent_tabs.tabs('update', {
					tab : parent_tabs.tabs('getSelected'), //获取当前被选中的页面
					options : {
						//Title : '新标题',
						}
					}
				);  
           	 }else{
           		 parent.$.messager.alert('提示', '修改失败');
           	 }
            }
       }) 
       progressClose(); 
	}
	function uploadFile(obj,barId){
		//改变span的值
		var file = obj.files[0];
		$(obj).parent().next().text(file.name);
		var bar = $('#'+barId);
		//文件上传
		var orderNo = $('#orderNo').html();
		if(file != null || file != undefined || typeof(file) != 'undefined' || orderNo != null || typeof(orderNo) != 'undefined'){
			if(isEmptyObject(data)){
				data = getToken(orderNo);
			}
			if(isOvertime(data.expiration)){
				 data = getToken(orderNo);
			}
			if(data != null && data != {}){
				client = new OSS.Wrapper({
					region: data.region,
					accessKeyId: data.accessKeyId,
					accessKeySecret: data.accessKeySecret,
					stsToken : data.securityToken,
					bucket: data.bucket
		        });
				bar.css('display','block');
				var picPath = "";
				if(barId.indexOf('shell') >= 0){
					picPath = data.path+"(外壳_"
	    		}
	    		if(barId.indexOf('fitting') >= 0){
	    			picPath = data.path+"(配件_"
	    		}
	    		if(barId.indexOf('packing') >= 0){
	    			picPath = data.path+"(包材_"
	    		}
	    		picPath += formatDate(new Date(),"yyyy-MM-dd_hh:mm:ss_xx")+")"+file.name;
			    client.multipartUpload(picPath, file,{
			        progress : function(p){
			        	return function (done) {
			      		   var value = Math.floor(p * 100);
			      		   bar.progressbar('setValue', value); 
			      		   done();
			      		}
			        }
			    }).then(function (result) {
			    	bar.parent().find('.uploadTips').remove()
			    	bar.css('display','none')
			    	//bar.css('display','block');
			    	$(obj).data('picPath',picPath);
			    	var content = '<div class="uploadTips"><span class="color_green">上传成功！！！</span></div>';
			    	bar.parent().append(content);
			    	
			    	//上传成功后要删除阿里的旧文件，更新数据表
			    	//更新数据库 1,当前的材料id 2,旧图片的路径
			    	var id = $(obj).parents('tr').data('id');
			    	var oldPicpath = $(obj).parents('tr').data('oldPicpath');
			    	
			    	if(id != null && typeof(id) != 'undefined' && oldPicpath != null && typeof(oldPicpath) != 'undefined'){
			    		console.log(barId.indexOf('shell'))
			    		if(barId.indexOf('shell') >= 0){
			    			updatePicObj_shell(id,oldPicpath,picPath,$(obj).parents('tr'));
			    		}
			    		if(barId.indexOf('fitting') >= 0){
			    			updatePicObj_fitting(id,oldPicpath,picPath,$(obj).parents('tr'));
			    		}
			    		if(barId.indexOf('packing') >= 0){
			    			updatePicObj_packing(id,oldPicpath,picPath,$(obj).parents('tr'));
			    		}
			    	}else{
			    		if(barId.indexOf('shell') >= 0){
			    			addPicObj_shell($(obj).data('picPath'),$(obj).parents('tr'))
			    		}
			    		if(barId.indexOf('fitting') >= 0){
			    			addPicObj_fitting($(obj).data('picPath'),$(obj).parents('tr'))
			    		}
			    		if(barId.indexOf('packing') >= 0){
			    			addPicObj_packing($(obj).data('picPath'),$(obj).parents('tr'))
			    		}
			    		
			    	}
			    }).catch(function (err) {
			        console.log(err);
			        bar.parent().find('.uploadTips').remove()
			    	var content = '<div class="uploadTips">'+
			    						'<span class="color_f60">上传失败！！！</span>'+
			    						'<span onclick="uploadFile('+obj+','+barId+')" style="cursor: pointer;">重新上传</span>'+
			    						'</div>';
			    	bar.css('display','block');
			    	bar.parent().append(content);
			    });
			}
		}
	}
	
	//更新文件：id 旧路径  新路径  旧路径存放容器
	function updatePicObj_shell(id,oldPicpath,newPicpath,oldPicpathObj){
		var tdValue = oldPicpathObj.children();
		var name = tdValue.eq(0).find("textarea").val().trim();
		console.log(oldPicpathObj.children().eq(0))
		if(name.length < 0){
			parent.$.messager.alert('提示', '名称不能为空！', 'info');
			return;
		}
		if(name.length > 64){
			parent.$.messager.alert('提示', '名称长度不能大于64！', 'info');
			return;
		}
		var imageValue = oldPicpathObj.children().eq(1).find("img");
		if(imageValue != null || typeof(imageValue) != 'undefined'){
			pictAttribute = imageValue.data('pictAttribute');
		}
		var color = tdValue.eq(2).find("textarea").val().trim();
		var craft = tdValue.eq(3).find("textarea").val().trim();
		var silk = tdValue.eq(4).find("textarea").val().trim();
		var description = tdValue.eq(5).find("textarea").val().trim();
		
		var param = {
			id : id,
			name : name,
			pictPaths : newPicpath,
			manufactureId : manufactureId,
			pictAttribute : pictAttribute,
			color : color,
			craft : craft,
			silk : silk,
			description : description,
			orderNo : orderNo,
			state : state
		}
		$.ajax({
			url:"${ctx}/manufactureShell/edit",
			type:"POST",
			data: JSON.stringify(param) ,
			dataType: "json",
            contentType: "application/json; charset=utf-8", 
            async: false,
            success: function(data){
            	if(data.success){
            		oldPicpathObj.data('oldPicpath',newPicpath);
            		deletePicOSS(oldPicpath);
            	}else{
            		parent.$.messager.alert('提示', '编辑失败！', 'info');
            	}
            }
		})
		
	}
	//添加文件：id 旧路径  新路径  旧路径存放容器
	function addPicObj_shell(newPicpath,oldPicpathObj){
		var tdValue = oldPicpathObj.children();
		var name = tdValue.eq(0).find("textarea").val().trim();
		if(name.length < 0){
			parent.$.messager.alert('提示', '名称不能为空！', 'info');
			return;
		}
		if(name.length > 64){
			parent.$.messager.alert('提示', '名称长度不能大于64！', 'info');
			return;
		}
		var imageValue = oldPicpathObj.children().eq(1).find("img");
		if(imageValue != null || typeof(imageValue) != 'undefined'){
			pictAttribute = imageValue.data('pictAttribute');
		}
		var color = tdValue.eq(2).find("textarea").val().trim();
		var craft = tdValue.eq(3).find("textarea").val().trim();
		var silk = tdValue.eq(4).find("textarea").val().trim();
		var description = tdValue.eq(5).find("textarea").val().trim();
		var param = {
			name : name,
			pictPaths : newPicpath,
			manufactureId : manufactureId,
			pictAttribute : pictAttribute,
			color : color,
			craft : craft,
			silk : silk,
			description : description,
			orderNo : orderNo,
			state : state
		}
		$.ajax({
			url:"${ctx}/manufactureShell/add",
			type:"POST",
			data: JSON.stringify(param) ,
			dataType: "json",
            contentType: "application/json; charset=utf-8", 
            async: false,
            success: function(data){
            	if(data.success){
            		oldPicpathObj.data('oldPicpath',newPicpath);
            		oldPicpathObj.data('id',data.obj.id);
            	}else{
            		parent.$.messager.alert('提示', '编辑失败！', 'info');
            	}
            }
		})
		
	}
	//更新文件：id 旧路径  新路径  旧路径存放容器
	function updatePicObj_fitting(id,oldPicpath,newPicpath,oldPicpathObj){
		var tdValue = oldPicpathObj.children()
		var name = tdValue.eq(0).find("textarea").val().trim();
		console.log(oldPicpathObj.children().eq(0))
		if(name.length < 0){
			parent.$.messager.alert('提示', '名称不能为空！', 'info');
			return;
		}
		if(name.length > 64){
			parent.$.messager.alert('提示', '名称长度不能大于64！', 'info');
			return;
		}
		var imageValue = oldPicpathObj.children().eq(1).find("img");
		if(imageValue != null || typeof(imageValue) != 'undefined'){
			pictAttribute = imageValue.data('pictAttribute');
		}
		var specification = tdValue.eq(2).find("textarea").val().trim();
		var packBag = tdValue.eq(3).find("textarea").val().trim();
		var label = tdValue.eq(4).find("textarea").val().trim();
		var description = tdValue.eq(5).find("textarea").val().trim();
		var param = {
			id : id,
			name : name,
			pictPaths : newPicpath,
			manufactureId : manufactureId,
			pictAttribute : pictAttribute,
			specification : specification,
			packBag : packBag,
			label : label,
			description : description,
			orderNo : orderNo,
			state : state
		}
		$.ajax({
			url:"${ctx}/manufactureFitting/edit",
			type:"POST",
			data: JSON.stringify(param) ,
			dataType: "json",
            contentType: "application/json; charset=utf-8", 
            async: false,
            success: function(data){
            	if(data.success){
            		oldPicpathObj.data('oldPicpath',newPicpath);
            		deletePicOSS(oldPicpath);
            	}else{
            		parent.$.messager.alert('提示', '编辑失败！', 'info');
            	}
            }
		})
		
	}
	function addPicObj_fitting(newPicpath,oldPicpathObj){
		var tdValue =  oldPicpathObj.children();
		var name = tdValue.eq(0).find("textarea").val().trim();
		if(name.length < 0){
			parent.$.messager.alert('提示', '名称不能为空！', 'info');
			return;
		}
		if(name.length > 64){
			parent.$.messager.alert('提示', '名称长度不能大于64！', 'info');
			return;
		}
		var imageValue = oldPicpathObj.children().eq(1).find("img");
		if(imageValue != null || typeof(imageValue) != 'undefined'){
			pictAttribute = imageValue.data('pictAttribute');
		}
		var specification = tdValue.eq(2).find("textarea").val().trim();
		var packBag = tdValue.eq(3).find("textarea").val().trim();
		var label = tdValue.eq(4).find("textarea").val().trim();
		var description = tdValue.eq(5).find("textarea").val().trim();
		var param = {
			name : name,
			pictPaths : newPicpath,
			manufactureId : manufactureId,
			pictAttribute : pictAttribute,
			specification : specification,
			packBag : packBag,
			label : label,
			description : description,
			orderNo : orderNo,
			state : state
		}
		$.ajax({
			url:"${ctx}/manufactureFitting/add",
			type:"POST",
			data: JSON.stringify(param) ,
			dataType: "json",
            contentType: "application/json; charset=utf-8", 
            async: false,
            success: function(data){
            	if(data.success){

            		oldPicpathObj.data('oldPicpath',newPicpath);
            		oldPicpathObj.data('id',data.obj.id);
            	}else{
            		parent.$.messager.alert('提示', '编辑失败！', 'info');
            	}
            }
		})
		
	}
	//更新文件：id 旧路径  新路径  旧路径存放容器
	function updatePicObj_packing(id,oldPicpath,newPicpath,oldPicpathObj){
		var name = oldPicpathObj.prev().find("input").val().trim();
		console.log(oldPicpathObj.prev())
		if(name.length < 0){
			parent.$.messager.alert('提示', '名称不能为空！', 'info');
			return;
		}
		if(name.length > 64){
			parent.$.messager.alert('提示', '名称长度不能大于64！', 'info');
			return;
		}
		var imageValue = oldPicpathObj.find("img");
		if(imageValue != null || typeof(imageValue) != 'undefined'){
			pictAttribute = imageValue.data('pictAttribute');
		}
		var titleId = oldPicpathObj.prev().data('titleId');
		var titleName = oldPicpathObj.prev().data('titleName');
		var description = oldPicpathObj.next().children().eq(1).find("textarea").val().trim();
		var param = {
			id : id,
			pictTitle : name,
			pictPaths : newPicpath,
			manufactureId : manufactureId,
			pictAttribute : pictAttribute,
			titleId : titleId,
			titleName : titleName,
			description : description,
			orderNo : orderNo,
			state : state,
			
		}
		$.ajax({
			url:"${ctx}/manufacturePackage/edit",
			type:"POST",
			data: JSON.stringify(param) ,
			dataType: "json",
            contentType: "application/json; charset=utf-8", 
            async: false,
            success: function(data){
            	if(data.success){
            		oldPicpathObj.data('oldPicpath',newPicpath);
            		deletePicOSS(oldPicpath);
            	}else{
            		parent.$.messager.alert('提示', '编辑失败！', 'info');
            	}
            }
		})
		
	}
	//添加文件：id 旧路径  新路径  旧路径存放容器
	function addPicObj_packing(newPicpath,oldPicpathObj){
		var name = oldPicpathObj.prev().find("input").val().trim();
		console.log(oldPicpathObj.prev())
		if(name.length < 0){
			parent.$.messager.alert('提示', '名称不能为空！', 'info');
			return;
		}
		if(name.length > 64){
			parent.$.messager.alert('提示', '名称长度不能大于64！', 'info');
			return;
		}
		var imageValue = oldPicpathObj.find("img");
		if(imageValue != null || typeof(imageValue) != 'undefined'){
			pictAttribute = imageValue.data('pictAttribute');
		}
		var titleId = oldPicpathObj.prev().data('titleId');
		var titleName = oldPicpathObj.prev().data('titleName');
		var description = oldPicpathObj.next().children().eq(1).find("textarea").val().trim();
		var param = {
			pictTitle : name,
			pictPaths : newPicpath,
			manufactureId : manufactureId,
			pictAttribute : pictAttribute,
			titleId : titleId,
			titleName : titleName,
			description : description,
			orderNo : orderNo,
			state : state,
		}
		$.ajax({
			url:"${ctx}/manufacturePackage/add",
			type:"POST",
			data: JSON.stringify(param) ,
			dataType: "json",
            contentType: "application/json; charset=utf-8", 
            async: false,
            success: function(data){
            	if(data.success){
            		oldPicpathObj.data('oldPicpath',newPicpath);
            		oldPicpathObj.data('id',data.obj.id);
            		oldPicpathObj.prev().data('id',data.obj.id);
            		oldPicpathObj.prev().data('titleId',data.obj.titleId)
            	}else{
            		parent.$.messager.alert('提示', '编辑失败！', 'info');
            	}
            }
		})
		
	}
	
	function deletePicOSS(oldPicpath){
		console.log("删除方法："+oldPicpath)
		if(isEmptyObject(data)){
			data = getToken(orderNo);
		}
		if(isOvertime(data.expiration)){
			 data = getToken(orderNo);
		}
		if(oldPicpath != null || oldPicpath != '' || typeof(oldPicpath) != 'undefined'){
			
			if(data != null && data != {}){
				client = new OSS.Wrapper({
					region: data.region,
					accessKeyId: data.accessKeyId,
					accessKeySecret: data.accessKeySecret,
					stsToken : data.securityToken,
					bucket: data.bucket
		        });
				client.delete(oldPicpath).then(function (result) {
			    	console.log(".oldPicpath....删除成功")
			    }).catch(function (err) {
			        console.log(err);
			    });
			}
		}
	}
	jQuery.fn.extend({
	    uploadPreview: function (opts) {
	        var _self = this,
	            _this = $(this);
	        opts = jQuery.extend({
	            Img: "ImgPr",
	            Width: 100,
	            Height: 100,
	            ImgType: ["gif", "jpeg", "jpg", "bmp", "png"],
	            maxWidth : 300,
	            Callback: function () {}
	        }, opts || {});
	        _self.getObjectURL = function (file) {
	            var url = null;
	            if (window.createObjectURL != undefined) {
	                url = window.createObjectURL(file)
	            } else if (window.URL != undefined) {
	                url = window.URL.createObjectURL(file)
	            } else if (window.webkitURL != undefined) {
	                url = window.webkitURL.createObjectURL(file)
	            }
	            return url
	        };
	        _this.change(function () {
	            if (this.value) {
	            	var inputId = $(this).attr('id');
	            	//限制类型
	                if (!RegExp("\.(" + opts.ImgType.join("|") + ")$", "i").test(this.value.toLowerCase())) {
	                    parent.$.messager.alert('提示', "选择文件错误,图片类型必须是" + opts.ImgType.join("，") + "中的一种", 'info');
	                    this.value = "";
	                    return false;
	                }
	                console.log(this.files[0].size)
	                //限制大小
	                if (this.files[0].size > 1024*1024*1) {
	                    parent.$.messager.alert('提示', "选择文件错误,图片的大小不能超过1M", 'info');
	                    this.value = "";
	                    return false;
	                }
	                var isIE = /msie/.test(navigator.userAgent.toLowerCase()); 
	                if (isIE) {
	                    try {
	                        $("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]))
	                    } catch (e) {
	                        var src = "";
	                        var obj = $("#" + opts.Img);
	                        var div = obj.parent("div")[0];
	                        _self.select();
	                        if (top != self) {
	                            window.parent.document.body.focus()
	                        } else {
	                            _self.blur()
	                        }
	                        src = document.selection.createRange().text;
	                        document.selection.empty();
	                        obj.hide();
	                        obj.parent("div").css({
	                            'filter': 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)',
	                            'width': opts.Width,
	                            'height': opts.Height
	                        });
	                        div.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = src
	                    }
	                } else {
	                    $("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]));
	                }
	              //读取图片数据
                    var fileData = this.files[0];
             		var reader = new FileReader();
             		var width ;
             		var height; 
             		reader.onload = function (e) {
                     	var data = e.target.result;
                    	//加载图片获取图片真实宽度和高度
                     	var image = new Image();
                      	image.onload=function(){
	                        width = image.width;
	                        height = image.height;
	                       /*  if(height > 300){
			                	$("#" + opts.Img).css({
		                            'max-height': '300px'
		                        });
			                } */
			                if(width > parseInt(opts.maxWidth)){
			                	$("#" + opts.Img).css({
		                            'max-width': opts.maxWidth
		                        });
			                } 
			                $("#" + opts.Img).data('pictAttribute',width +'*'+height)
                     	};
                      	image.src= data;
	                }; 
	                reader.readAsDataURL(fileData);
	               	var id = $(this).parents('.uploadDiv').next().next().attr('id')
	               	if(id != null && typeof(id) != 'undefined'){
	               		uploadFile(this,id);
	               	}
	               // this.outerHTML = this.outerHTML;
	                opts.Callback()
	            }
	        }),
	        _this.click(function () {
	        	var inputId = $(this).attr('id');
            	if(inputId.indexOf('shell') >= 0 || inputId.indexOf('fitting') >= 0){
            		var name = $(this).parents('td').prev().find("textarea").val().trim();
	        		if(name.length <= 0){
	        			parent.$.messager.alert('提示', '名称不能为空！', 'info');
	        			return false;
	        		}
	        		if(name.length > 64){
	        			parent.$.messager.alert('提示', '名称长度不能大于64！', 'info');
	        			return false;
	        		}
            	}
            	if(inputId.indexOf('packing') >= 0){
            		var name = $(this).parents('tr').prev().find("input").val().trim();
	        		if(name.length <= 0){
	        			parent.$.messager.alert('提示', '名称不能为空！', 'info');
	        			return false;
	        		}
	        		if(name.length > 64){
	        			parent.$.messager.alert('提示', '名称长度不能大于64！', 'info');
	        			return false;
	        		}
            	}
            	return true;
	    	})
	    }
	});
	 //格式化CST日期的字串
    function formatCSTDate(strDate,format){
      return formatDate(new Date(strDate),format);
    }
     
    //格式化日期,
    function formatDate(date,format){
      var paddNum = function(num){
        num += "";
        return num.replace(/^(\d)$/,"0$1");
      }
      //指定格式字符
      var cfg = {
         yyyy : date.getFullYear() //年 : 4位
        ,yy : date.getFullYear().toString().substring(2)//年 : 2位
        ,M  : date.getMonth() + 1  //月 : 如果1位的时候不补0
        ,MM : paddNum(date.getMonth() + 1) //月 : 如果1位的时候补0
        ,d  : date.getDate()   //日 : 如果1位的时候不补0
        ,dd : paddNum(date.getDate())//日 : 如果1位的时候补0
        ,hh : date.getHours()  //时
        ,mm : date.getMinutes() //分
        ,ss : date.getSeconds() //秒
        ,xx : date.getMilliseconds()//毫秒
      }
      format || (format = "yyyy-MM-dd hh:mm:ss");
      return format.replace(/([a-z])(\1)*/ig,function(m){return cfg[m];});
    } 
</script>
<div>
<form id="manufactureEditForm">
	<!-- 基础信息 -->
	<table border="0" cellpadding="0" cellspacing="0" class="ordertable">
        <tr class="tabletitle">
            <td colspan="8"><b>一、基础信息：</b></td>
        </tr>
        <tr>
         <td class="lefttd" width="7%">生产单号： </td>
            <td class="righttd" width="18%"><div class="color069" id="orderNo">${orderInfo.orderNo}</div> </td>
            <td class="lefttd" width="7%">客户名称：</td>
            <td class="righttd" width="18%"><div class="color069" id="clientName">${orderInfo.clientName}</div> </td>
            <td class="lefttd" width="7%">发行日期：</td>
            <td class="righttd" width="18%"><input  class="inputs easyui-datebox" editable="false" id="dateIssue"> </td>
            <td class="lefttd" width="7%"> 出货日期：</td>
            <td class="righttd" width="18%"><input  class="inputs easyui-datebox"  editable="false" id="dateShipment"> </td>
        </tr>
        <tr>
            <td class="lefttd" >版本号：</td>
            <td class="righttd" ><input class="inputs easyui-validatebox" data-options="validType:['isBlank_','length[0,20]']"  id="version" > </td>
            <td class="lefttd">拟稿人员：</td>
            <td class="righttd"><input class="inputs easyui-validatebox" data-options="validType:['isBlank_','length[0,64]']" id="drafter"> </td>
            <td class="lefttd">审核人员：</td>
            <td class="righttd"><input class="inputs easyui-validatebox" data-options="validType:['isBlank_','length[0,64]']" id="auditor"> </td>
            <td class="lefttd">批准：</td>
            <td class="righttd"><input class="inputs easyui-validatebox" data-options="validType:['isBlank_','length[0,64]']"  id="approver"></td>
        </tr>
        <tr>
            <td class="lefttd">备注：</td>
            <td class="righttd" colspan="1"><textarea class="textareacss easyui-validatebox" data-options="validType:['isBlank_','length[0,100]']" id="remark" rows="5">xx</textarea></td>
        </tr>
    </table>
    <!-- 软件 -->
    <table border="0" cellpadding="0" cellspacing="0" class="ordertable">
        <tr class="tabletitle">
            <td colspan="8"><b>二、软件信息：</b> </td>
        </tr>
        <tr>            
        	<td class="lefttd">应用版本：</td>
            <td class="righttd"><input class="inputs easyui-validatebox" data-options="validType:['isBlank_','length[0,64]']"  id="versionApp"> </td>
            <td class="lefttd">内核版本 ：</td>
            <td class="righttd"><input class="inputs  easyui-validatebox" data-options="validType:['isBlank_','length[0,64]']" id="versionCore"/></td>
            <td class="lefttd">地图版本： </td>
            <td class=""><input class="inputs easyui-validatebox" data-options="validType:['isBlank_','length[0,64]']"  id="versionMap"></td>
            <td class="lefttd">预存文件：</td>
            <td class="righttd"><input class="inputs easyui-validatebox" data-options="validType:['isBlank_','length[0,64]']"  id="preFile" > </td>
        </tr>
        <tr>
            <td class="lefttd">UUID：</td>
            <td class="righttd"><input class="inputs easyui-validatebox" data-options="validType:['isBlank_','length[0,64]']"  id="uuid"> </td>
           	<td class="lefttd">固件版本：</td>
            <td class="righttd" ><input  class="inputs easyui-validatebox" data-options="validType:['isBlank_','length[0,64]']"  id="versionFirmware" /></input></td>
           
            <td class="lefttd">备注：</td>
            <td class="righttd" colspan="3"><textarea class="textareacss  easyui-validatebox" data-options="validType:['isBlank_','length[0,500]']"  id="description" rows="5"></textarea> </td>
        </tr>
        <tr>
            <td class="lefttd">其他软件要求：</td>
            <td class="righttd" colspan="3"><textarea class="textareacss  easyui-validatebox" data-options="validType:['isBlank_','length[0,500]']"  id="otherRequire" rows="5"></textarea></td>
            <td class="lefttd">注意事项：</td>
            <td class="righttd" colspan="3"><textarea  class="textareacss  easyui-validatebox" data-options="validType:['isBlank_','length[0,500]']" id="os_notice" rows="5"></textarea></td>
        </tr>
    </table>
    
    <!-- 机身外壳 -->
     <table border="0" cellpadding="0" cellspacing="0" class="bordertable" id="manufactureShells">
        <tr class="tabletitle">
            <td colspan="6" style="border-right: 0px"><b>三、机身外壳：</b></td>
            <td align="right" style="border-left: 0px;padding: 0px">
				<span onclick="addManufactureShellFun();" class="icon-add tip"  title="添加"></span>
			</td>
        </tr>
		<tr class="tabletop">
            <td width="10%">名称</td>
            <td width="40%">效果图</td>
            <td width="10%">颜色</td>
            <td width="10%">喷漆工艺</td>
            <td width="10%">丝印</td>
            <td width="20%">备注</td>
            <td style="width: 0px;padding: 0px"></td>
        </tr>
    </table>
     
    
    <!-- 配件信息-->
     <table border="0" cellpadding="0" cellspacing="0" class="bordertable" id="manufactureFittings" >
        <tr class="tabletitle">
            <td colspan="6" style="border-right: 0px"><b>四、订单配件： </b></td>
            <td align="right" style="border-left: 0px;padding: 0px">
				<span onclick="addManufactureFittingFun();" class="icon-add tip"  title="添加"></span>
			</td>
        </tr>
        <tr  class="tabletop">
            <td width="10%">名称</td>
            <td width="40%">图片</td>
            <td width="20%">型号/规格</td>
            <td width="7%">包装袋</td>
            <td width="8%">标贴要求</td>
            <td width="15%">备注</td>
            <td style="width: 0px;padding: 0px"></td>
        </tr>
        
    </table>
    
      <!-- 包材信息-->
     <table border="0" cellpadding="0" cellspacing="0" class="bordertable" id="manufacturePackings">
        <tr class="tabletitle">
            <td colspan="4"><b>五、订单包材及包装 </b></td>
        </tr>
        
        
    </table>
      <!-- 其他注意事项-->
     <table border="0" cellpadding="0" cellspacing="0" class="bordertable">
        <tr class="tabletitle">
            <td colspan="3"><b>六、其他注意事项：</b> </td>
        </tr>
        <tr>
        	<td colspan="3"><textarea  id="notice" class="textareacss2 easyui-validatebox" data-options="validType:['isBlank_','length[0,500]']"  rows="5"></textarea></td>
        </tr>
    </table>
    </form>
</div>
<div align="center">
	<button id="orderSubmitToSeller" class='btn-default' onclick="manufactureEdit();">提交</button> 
</div>