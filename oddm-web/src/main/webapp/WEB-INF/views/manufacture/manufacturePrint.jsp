<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<jsp:include page="../inc.jsp"></jsp:include>
<style>
	body{padding: 10px;font-family:"宋体"; }
	.title{text-align: center;font-size: 1.8em;font-family: "宋体";font-weight: 800;}
	.tabletitle{text-align:left; color:black;font-size:1.4em; font-weight:800;}
	.tabletop{text-align:left; background-color:#BFBFBF;color:black;font-size:1.2em; font-weight:800;}
	.tabletop td{text-align: center;}
	.inputs{border:1px solid #CCC;width:90%;padding:5px 0 2px 4px;font-size:12px;color:#999;}
	.lefttd{width:65px;}
    .lefttd90{width:90px; text-align:right;}
    .righttd{color069;}
    .table{width:100%;color:#000;text-align:center;border-collapse:collapse;}
	.table td{padding:10px 3px;height:20px;}
    .bordertable{width:100%;color:#000;margin-bottom:15px;font-size:0.8em;text-align:center;border-collapse:collapse;}
	.bordertable td{border:1px solid #000;padding:10px 3px;height:20px;}
	.bordertable th{border:1px solid #000;padding:10px 0 10px 6px;height:20px;}
	.precss{word-wrap:break-word;width:100%;white-space: normal;}
	.precss2{word-wrap:break-word;width:100%;text-align: left;}
	.choose{color: #06c;cursor: pointer;}
	.color_f60 {color: #f60;}
	.alignCenter{text-align: center;background: #BFBFBF;font-weight: 800}
	.alignLeft{text-align: left;}
	.text_left{text-align: left;}
</style>

<script src="${ctx}/jslib/aliyun-oss-sdk-4.4.4.min.js"></script>
<script src="${ctx}/jslib/promise-6.1.0.js"></script>
<script type="text/javascript">
	var manufactureId = "${manufactureId}";
	var shell_count = 0;
	var fitting_count = 0;
	var packing_count = 0;
	var orderNo ;
	$(function() {
		
		$.ajax({
			url:"${ctx}/manufacture/get?id="+ manufactureId,
			type:"GET",
			dataType: "json",
            contentType: "application/json; charset=utf-8", 
            async : false,
            success: function(data){
            	if(data != null){
            		$('#version').html(data.version);
            		$('#clientName').html(data.clientName);
            		$('#dateIssue').html(data.dateIssue);
            		$('#dateShipment').html(data.dateShipment);
            		$('#orderNo').html(data.orderNo);
            		//getToken(data.orderNo);
            		orderNo = data.orderNo;
            		$('#drafter').html(data.drafter);
            		$('#auditor').html(data.auditor);
            		$('#approver').html(data.approver);
            		$('#remark').html(data.remark);
            		$('#notice').html(data.notice);
            		//软件
            		if(data.manufactureOsDTO != null){
            			var manufactureOsDTO = data.manufactureOsDTO;
            			$('#versionApp').html(manufactureOsDTO.versionApp);
                		$('#versionCore').html(manufactureOsDTO.versionCore);
                		$('#versionMap').html(manufactureOsDTO.versionMap);
                		$('#preFile').html(manufactureOsDTO.preFile);
                		$('#uuid').html(manufactureOsDTO.uuid);
                		$('#versionFirmware').html(manufactureOsDTO.versionFirmware);
                		$('#description').html(manufactureOsDTO.description);
                		$('#otherRequire').html(manufactureOsDTO.otherRequire);
                		$('#os_notice').html(manufactureOsDTO.notice);
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
            				}else{
            					manufactureShellDTOs[i].pictPaths = getPicPath(manufactureShellDTOs[i].pictPaths);
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
            				
            				if(manufactureShellDTOs[i].pictPaths != ''){
            					var content = '<tr>'+
			    						'<td><span class="color069">'+ manufactureShellDTOs[i].name +'</span></td>'+
			    						'<td><img class="color069" id="shellImgPr_'+shell_count+'" src="'+ manufactureShellDTOs[i].pictPaths +'"></td>'+
			    						'<td><span class="color069 "  >'+ manufactureShellDTOs[i].color +'</span></td>'+
			    						'<td><span class="color069 " >'+ manufactureShellDTOs[i].craft +'</span></td>'+
			    						'<td><span class="color069">'+ manufactureShellDTOs[i].silk +'</span></td>'+
			    						'<td><span class="color069">'+ manufactureShellDTOs[i].description +'</span></td>'+
			    					'</tr>';
            				}else{
            					var content = '<tr>'+
			    						'<td><span class="color069">'+ manufactureShellDTOs[i].name +'</span></td>'+
			    						'<td><span class="color069" id="shellImgPr_'+shell_count+'" >图片未上传</span></td>'+
			    						'<td><span class="color069 "  >'+ manufactureShellDTOs[i].color +'</span></td>'+
			    						'<td><span class="color069 " >'+ manufactureShellDTOs[i].craft +'</span></td>'+
			    						'<td><span class="color069">'+ manufactureShellDTOs[i].silk +'</span></td>'+
			    						'<td><span class="color069">'+ manufactureShellDTOs[i].description +'</span></td>'+
			    					'</tr>';
            				}
            				$('#manufactureShells').append(content);
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
            				}else{
            					manufactureFittingDTOs[i].pictPaths = getPicPath(manufactureFittingDTOs[i].pictPaths);
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
            				if(manufactureFittingDTOs[i].pictPaths != ''){
	            				var content = '<tr>'+
			    						'<td><span class="color069">'+ manufactureFittingDTOs[i].name +'</span></td>'+
			    						'<td><img class="color069" id="fittingImgPr_'+fitting_count+'" src="'+ manufactureFittingDTOs[i].pictPaths +'"></td>'+
			    						'<td><span class="color069 ">'+ manufactureFittingDTOs[i].specification +'</span></td>'+
			    						'<td><span class="color069 " >'+ manufactureFittingDTOs[i].packBag +'</span></td>'+
			    						'<td><span class="color069">'+ manufactureFittingDTOs[i].label +'</span></td>'+
			    						'<td><span class="color069">'+ manufactureFittingDTOs[i].description +'</span></td>'+
			    					'</tr>';
            				}else{
            					var content = '<tr>'+
			    						'<td><span class="color069">'+ manufactureFittingDTOs[i].name +'</span></td>'+
			    						'<td><span class="color069" id="fittingImgPr_'+fitting_count+'">图片未上传</span></td>'+
			    						'<td><span class="color069 ">'+ manufactureFittingDTOs[i].specification +'</span></td>'+
			    						'<td><span class="color069 " >'+ manufactureFittingDTOs[i].packBag +'</span></td>'+
			    						'<td><span class="color069">'+ manufactureFittingDTOs[i].label +'</span></td>'+
			    						'<td><span class="color069">'+ manufactureFittingDTOs[i].description +'</span></td>'+
			    					'</tr>';
            				}
            				$('#manufactureFittings').append(content);
            				if(manufactureFittingDTOs[i].pictAttribute != null && typeof(manufactureFittingDTOs[i].pictAttribute) != 'undefined'){
            					var pictAttributes = manufactureFittingDTOs[i].pictAttribute.split("*");
            					var picWidth = pictAttributes[0];
                				var pictHeight = pictAttributes[1];
                				if(picWidth > (parseInt(picMaxWidth_fitting))){
				                	$("#fittingImgPr_" + fitting_count).css({
			                            'max-width': picMaxWidth_fitting
			                        });
				                }
            				}
            				fitting_count++;
            			}
            		}
            		
            		//包材
            		if(data.manufacturePackageDTOs != null && data.manufacturePackageDTOs.length > 0){
            			var manufacturePackageDTOs = data.manufacturePackageDTOs;
            			var map = {};
            			for(var i in manufacturePackageDTOs){
            				if(manufacturePackageDTOs[i].titleId != null){
            					
            					if(map[manufacturePackageDTOs[i].titleId] != null){
            						map[manufacturePackageDTOs[i].titleId].push(manufacturePackageDTOs[i])
            					}else{
            						var data = [];
            						data.push(manufacturePackageDTOs[i]);
            						map[manufacturePackageDTOs[i].titleId] = data;
            					}
            				}
            			}
            			var picPath = "";
            			for(var i in map){
            				if(map[i][0] != null && typeof(map[i][0]) != 'undefined'){
	            				var content_title = '<tr class="tabletop"><th colspan="3" >'+ map[i][0].manufacturePackageTitleDTO.name+'</th></tr>'; 
	            				$('#manufacturePaking').append(content_title);
            				}
            				for(var j in map[i]){
            					if(map[i][j] != null && typeof(map[i][j]) != 'undefined'){
            						if(map[i][j].titleId != 3){
            							var title = parseInt(j) + 1;
                						
                						if(map[i][j].pictTitle == null || typeof(map[i][j].pictTitle) == 'undefined'){
                        					map[i][j].pictTitle = "";
                        				}
                						var content_pictTitle = '<tr class="text_left">'+
    						    						'<td>'+ title +'、<span class="color069" >'+ map[i][j].pictTitle +'</span></td>'+
    						    					'</tr>';
    						    		$('#manufacturePaking').append(content_pictTitle);
    			    					if( map[i][j].pictPaths != null && typeof( map[i][j].pictPaths) != 'undefined'){
    			    						/* var pics = map[i][j].pictPaths.split(",");
    			    						for(var k in pics){
    			    							picPath = getPicPath(pics[k]);
    			    							var content_pictPaths = '<tr  class="text_left">'+
    						    						'<td><img class="color069" src="'+ picPath +'"></td>'+
    						    					'</tr>';
    			    							$('#manufacturePaking').append(content_pictPaths);
    			    						} */
    		    							picPath = getPicPath(map[i][j].pictPaths);
    		    							if(picPath != ''){
    			    							var content_pictPaths = '<tr  class="text_left">'+
    					    						'<td><img class="color069" src="'+ picPath +'" id="packingImgPr_'+packing_count+'"></td>'+
    					    					'</tr>';
    			    						}else{
    			    							var content_pictPaths = '<tr  class="text_left">'+
    					    						'<td><span class="color069" src="'+ picPath +'">图片未上传</span></td>'+
    					    					'</tr>';
    			    						}
    		    							
    		    							$('#manufacturePaking').append(content_pictPaths);
    		    							var picMaxWidth_packing = $('#packingImgPr_'+packing_count).parent().css('width');
    		    							console.log(picMaxWidth_packing)
    		    							if(map[i][j].pictAttribute != null && typeof(map[i][j].pictAttribute) != 'undefined'){
    		                					var pictAttributes = map[i][j].pictAttribute.split("*");
    		                					var picWidth = pictAttributes[0];
    		                    				var pictHeight = pictAttributes[1];
    		                    				if(picWidth > (parseInt(picMaxWidth_packing))){
    		    				                	$("#packingImgPr_" + packing_count).css({
    		    			                            'max-width': picMaxWidth_packing
    		    			                        });
    		    				                }
    		                				}
    		                				packing_count++;
    			    					}
    			    					
    			    					if(map[i][j].description == null || typeof(map[i][j].description) == 'undefined'){
                        					map[i][j].description = "";
                        				}
    									var content_description = '<tr  class="text_left">'+
    						    						'<td><pre class="precss2" >'+ map[i][j].description +'</pre></td>'+
    						    					'</tr>';
    								
    									
    									$('#manufacturePaking').append(content_description);
            						}else{
            							if(map[i][j].description == null || typeof(map[i][j].description) == 'undefined'){
                        					map[i][j].description = "";
                        				}
            							var content_description = '<tr  class="text_left">'+
							    						'<td><pre class="color069" >'+ map[i][j].description +'</pre></td>'+
							    					'</tr>';
										$('#manufacturePaking').append(content_description);
            						}
            					}
            					
            				}
            			}
            			
            		}
            	}
            }
		})
		isImgLoad();
	});
	var isLoad = true; // 控制变量（判断图片是否 加载完成）
	var t_img;
	isImgLoad(function(){//判断全部打印图片加载完成
        window.print();
        // 加载完成
	});

	//判断图片加载的函数
	function isImgLoad(callback){
	    // 查找所有打印图，迭代处理
	    $('.printImg').each(function(){
	        // 找到为0就将isLoad设为false，并退出each
	       
	        if(this.height === 0){
	        //	alert(6666)
	            isLoad = false;
	            return false;
	        }
	    });
	    // 为true，没有发现为0的。加载完毕
	    if(isLoad){
	    	//alert(33333)
	        clearTimeout(t_img); // 清除定时器
	        // 回调函数
	        callback();
	    }else{
	    	// 为false，因为找到了没有加载完成的图，将调用定时器递归
	        isLoad = true;
	     //   alert(222)
	        t_img = setTimeout(function(){
	            isImgLoad(callback); // 递归扫描
	        },500); // 我这里设置的是500毫秒就扫描一次，可以自己调整
	    }
	}
	
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
</script>
<div>
    <div class="title">订单包装工艺指导书</div>
	<!-- 基础信息 -->
	<table border="0" cellpadding="0" cellspacing="0" class="table">
        <tr class="tabletitle">
            <td colspan="10">一、基础信息：</td>
        </tr>
    </table>
    <table border="0" cellpadding="0" cellspacing="0" class="bordertable">
        <tr>
        	 <td class="alignCenter" width="10%" >版本号</td>
        	 <td class="alignCenter" width="10%">客户名称</td>
        	 <td class="alignCenter" width="10%">发行日期</td>
        	 <td class="alignCenter" width="10%">出货日期</td>
        	 <td class="alignCenter" width="10%">生产单号</td>
        	 <td class="alignCenter" width="10%">拟稿人员</td>
        	 <td class="alignCenter" width="10%">审核人员</td>
        	 <td class="alignCenter" width="10%">批准</td>
        	 <td class="alignCenter" width="20%">备注</td>
        </tr>
        <tr>
        	 <td class="alignLeft"><div class="color069" id="version"> </div></td>
        	 <td class="alignLeft" ><div class="color069" id="clientName"> </div></td>
	       	 <td class="alignLeft" ><div class="color069" id="dateIssue"> </div></td>
	       	 <td class="alignLeft"><div class="color069" id="dateShipment"> </div></td>
	       	  <td class="alignLeft"><div class="color069" id="orderNo"> </div></td>
	       	  <td class="alignLeft"><div class="color069" id="drafter"> </div></td>
	       	  <td class="alignLeft"><div class="color069" id="auditor"> </div></td>
	       	  <td class="alignLeft"><div class="color069" id="approver"> </div></td>
	       	  <td class="alignLeft"><pre class="precss" id="remark" rows="3" readonly="readonly"> </pre></td>
        </tr>
    </table>
    <!-- 软件 -->
    <table border="0" cellpadding="0" cellspacing="0" class="table">
        <tr class="tabletitle">
            <td colspan="7">二、软件信息： </td>
        </tr>
    </table>
    <table border="0" cellpadding="0" cellspacing="0" class="bordertable">
        <tr>
        	<td class="alignCenter" width="10%">应用版本</td>
        	<td class="alignCenter" width="10%">内核版本</td>
        	<td class="alignCenter" width="10%">地图版本 </td>
        	<td class="alignCenter" width="10%">预存文件</td>
        	<td class="alignCenter" width="10%">UUID</td>
        	<td class="alignCenter" width="10%">固件版本</td>
        	<td class="alignCenter" width="40%">备注</td>
        </tr>
        <tr>
        	<td class="alignLeft" ><div class="color069" id="versionApp"> </div></td>
        	<td class="alignLeft"><div class="color069 " id="versionCore"/></div></td>
        	<td class="alignLeft" ><div class="color069" id="versionMap"> </div></td>
        	<td class="alignLeft"><div class="color069" id="preFile" > </div></td>
        	<td class="alignLeft"><div class="color069" id="uuid"> </div></td>
        	<td class="alignLeft" ><div  class="color069 " id="versionFirmware" /></div></td>
        	<td class="alignLeft"><pre class="precss" id="description" rows="3" readonly="readonly"> </pre></td>
        </tr>
        <tr>
	     	<td class="lefttd">其他软件要求</td>
	     	<td class="righttd" colspan="6"><pre class="precss2" id="otherRequire" rows="3" readonly="readonly"> </pre></td>
        </tr>
        <tr>
        	 <td class="lefttd">注意事项</td>
        	 <td class="righttd" colspan="6"><pre class="precss2" id="os_notice" rows="3" readonly="readonly"> </pre></td>
        </tr>
    </table>
    
    <!-- 机身外壳 -->
     <table border="0" cellpadding="0" cellspacing="0" class="bordertable" id="manufactureShells">
        <tr class="tabletitle" >
            <td colspan="8" style="border: 0;">三、机身外壳：</td>
        </tr>
		<tr   class="tabletop">
            <td width="10%">名称</td>
            <td width="40%">效果图</td>
            <td width="10%">颜色</td>
            <td width="10%">喷漆工艺</td>
            <td width="10%">丝印</td>
            <td width="20%">备注</td>
        </tr>
        </table>
     
    
    <!-- 配件信息-->
     <table border="0" cellpadding="0" cellspacing="0" class="bordertable" id="manufactureFittings">
        <tr class="tabletitle">
            <td colspan="6" style="border: 0;">四、订单配件： </td>
        </tr>
        <tr   class="tabletop">
            <td width="10%">名称</td>
            <td width="40%">图片</td>
            <td width="20%">型号/规格</td>
            <td width="7%">包装袋</td>
            <td width="8%">标贴要求</td>
            <td width="15%">备注</td>
        </tr>
        
    </table>
    
      <!-- 包材信息-->
     <table border="0" cellpadding="0" cellspacing="0" class="bordertable" id="manufacturePaking">
        <tr class="tabletitle" >
            <td colspan="3" style="border: 0;">五、订单包材及包装(请将包材的所有因素描述清晰) </td>
        </tr>
        
        
    </table>
      <!-- 其他注意事项-->
     <table border="0" cellpadding="0" cellspacing="0" class="bordertable">
        <tr class="tabletitle" >
            <td colspan="3" style="border: 0;">六、其他注意事项： </td>
        </tr>
        <tr style="text-align: left;">
        	<td colspan="3"><pre  id="notice" class="precss2" ></pre></td>
        </tr>
    </table>
    
</div>
<div align="center">
</div>