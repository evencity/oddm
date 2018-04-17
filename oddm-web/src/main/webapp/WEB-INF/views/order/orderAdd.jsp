<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>
	.ordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:left;}
	.tabletitle{text-align:left; background-color:#c8d7e1;color:#333;font-size:1.4em; font-weight:500;}
	.tabletop{text-align:left; background-color:#DCE0E2;color:#333;font-size:1.2em; font-weight:500;}
	.ordertable tr td{padding:10px 0 10px 6px;}
	.inputs{border:1px solid #CCC;width:180px;padding:5px 0 2px 4px;font-size:12px;color:#999;border-radius: 4px;}
    .bordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:center;border-collapse:collapse;}
	.bordertable td{border:1px solid #ccc;padding:10px 0 10px 6px;height:20px;text-align:left}
	.lefttd{width:60px;}
    .righttd{color:#069;width: 180px}
	.textareacss{width:100%;background:none;border: 0px}
	.choose{color: #06c;cursor: pointer;}
	.table_input{border: 0;background: none;padding: 5px 0;width: 95%;color: #666;}
	.table_input:focus{color: #4C9ED9;}
	.centerDiv{margin: 0 auto;width: 10%}
	/* .shell_input{border: 0;background: none;padding: 5px 0;color: #666;width: 80%;}
	.shell_input:focus{color: #4C9ED9;} */
		.btn-default {border: 1px solid #DDDDDD;background: white;padding: 10px 18px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #333;}
	.btn-default:hover {border: 1px solid #DDDDDD;background: #04B5F9;padding: 10px 18px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #FFF;}
	#selectInputMenu{
		position: absolute;
		background: white;
		z-index: 1;
		border: 1px solid;
		overflow: auto;
		max-height: 200px;
	}
	#selectInputMenu ul{
		padding: 0px;
		margin : 0px;
		list-style: none;
	}
	#selectInputMenu ul li{
		padding : 8px;
		cursor: pointer;
	}
	#selectInputMenu li:hover{
		background: #0cf;
	}
</style>
<jsp:include page="../inc.jsp"></jsp:include>
<script src="${ctx }/lib/util/util_date.js"></script>
<script type="text/javascript">
	function getById(id){
		return document.getElementById(id);
	}
	$(function(){
		var currentDate = new Date();
		var delayTime = currentDate.getTime()+40*24*60*60*1000
		var delayDate =new Date(delayTime);//新建订单交期时间40天后
		
		$('#dateOrder').datebox('setValue',dateformatter(currentDate));
    	$('#dateExamine').datebox('setValue',dateformatter(delayDate));
    	$('#dateDelivery').datebox('setValue',dateformatter(delayDate));  
    	
		$('body').click(function(e) {
			//console.log(e.target.tagName)判断当前点击的元素
			if(e.target.tagName.toLocaleLowerCase() !='input'){
				$("#selectInputMenu").hide(); 
			}
		 }); 
		$('body').off('focus','.select_static ').on('focus','.select_static',function(e,msg){
			$("#selectInputMenu").css('display','block');
			var input = $(this);
			var width = $(this).css('width');
			var top = $(this).offset().top;
			var height = $(this).css('height')
			var left = $(this).offset().left;
			$("#selectInputMenu").empty();
			var $ul = $('<ul></ul>');
			$('#selectInputMenu').css('width',width);
			$('#selectInputMenu').css({ "top": top + parseInt(height), "left": left});
			
			var msg = $(this).attr('msg')
			var msg_array = msg.split("|");
			for(var i in msg_array){
				var content = '<li class="inputValueLi">'+ msg_array[i] +'</li>';
				$ul.append(content)
			}
			$('#selectInputMenu').append($ul)
			$('#selectInputMenu').off('click','.inputValueLi').on('click','.inputValueLi',function(e,msg){
				var liValue = $(this).html();
				input.val(liValue);
				$("#selectInputMenu").css('display','none');
			})
		})
		
		
		 //动态数据
		$('body').off('focus','.select_dynamic').on('focus','.select_dynamic',function(e,msg){
			$("#selectInputMenu").css('display','block');
			var input = $(this);
			var width = $(this).css('width');
			var top = $(this).offset().top;
			var height = $(this).css('height')
			var left = $(this).offset().left;
			var $ul = $('<ul></ul>');
			$('#selectInputMenu').css('width',width);
			$('#selectInputMenu').css({ "top": top + parseInt(height), "left": left});
			var url = $(this).attr('data-url');
			var value = $(this).val();
			$.ajax({
				type: 'POST',
				url: url +value.replace('+','%2B'),
			    dataType: "json",
			    contentType: "application/json; charset=utf-8",
			    success: function(data){
			    	$("#selectInputMenu").empty(); 
				  	console.log(data)
			    	var $ul = $('<ul></ul>');
				  	for(var i in data){
				  		var content = '<li class="inputValueLi">'+  data[i] +'</li>';
						$ul.append(content)
				  	}
				  	
					$('#selectInputMenu').append($ul);
					$('#selectInputMenu').off('click','.inputValueLi').on('click','.inputValueLi',function(e,msg){
						var liValue = $(this).html();
						input.val(liValue);
						$("#selectInputMenu").css('display','none');
						
					})
					
			   	}
			 })
			 
		})
		
		//机型
		 $('#productFactory').off('blur').on('blur',function(e,msg){
			//setTimeout(checkFactory,200); 
			//checkFactory();
			var falg = $(this).validatebox('isValid');
		})  
		$('body').off('focus','#productFactory').on('focus','#productFactory',function(e,msg){
			$("#selectInputMenu").css('display','block');
			var input = $(this);
			var width = $(this).css('width');
			var top = $(this).offset().top;
			var height = $(this).css('height')
			var left = $(this).offset().left;
			$("#selectInputMenu").empty();
			var $ul = $('<ul></ul>');
			$('#selectInputMenu').css('width',width);
			$('#selectInputMenu').css({ "top": top + parseInt(height), "left": left});
			
			var msg = $(this).attr('msg')
			var msg_array = msg.split("|");
			for(var i in msg_array){
				var content = '<li class="inputValueLi">'+ msg_array[i] +'</li>';
				$ul.append(content)
			}
			$('#selectInputMenu').append($ul)
			$('#selectInputMenu').off('click','.inputValueLi').on('click','.inputValueLi',function(e,msg){
				var liValue = $(this).html();
				input.val(liValue);
				
				$("#selectInputMenu").css('display','none');
				$('#materialBare_shell').nextAll().remove();
	    		$.ajax({
		         	type: 'GET',
		             url: "${ctx}/product/getProductByName?productName=" + $('#productFactory').val(),
		             dataType: "json",
		             contentType: "application/json; charset=utf-8",
		             async: false,
		             success: function(data){
		            	console.log(data)
		            	var orderShellDTOs = data.materialBareDTOs;
		            	if(orderShellDTOs.length > 0){
			        		for(var i in orderShellDTOs){
			            		var silkScreen ;
			            		if(orderShellDTOs[i].silkScreen == 2){
			            			//2代表是丝印
			            			silkScreen = 'checked="checked"';
			            		}else{
			            			silkScreen = '';
			            		}
			            		if(orderShellDTOs[i].color == 'undefined' || orderShellDTOs[i].color == null){
			            			orderShellDTOs[i].color = "";
			            		}
			            		if(orderShellDTOs[i].craft == 'undefined' || orderShellDTOs[i].craft == null){
			            			orderShellDTOs[i].craft = "";
			            		}
			            		var content = '<tr class="contain" id="'+ orderShellDTOs[i].id +'">'+'<td>部件：<span class="color069">'+ orderShellDTOs[i].name +'</span></td>'+
			            						'<td>颜色：<input  class="inputs select_static myvalidate"  msg="黑色|白色|银色|镀银|铁灰|枪色"  value="'+ orderShellDTOs[i].color +'" /></td>'+
			            						'<td>工艺：<input  class="inputs select_static" msg="素材|光UV|哑UV|橡胶油|雾面" value="'+ orderShellDTOs[i].craft +'" /></td>'+
			            						'<td>丝印：<input  class="color069" name="silkScreen" type="checkbox" '+ silkScreen +'" /></td>'+
			            					'</tr>';
			            		$('#materialBare_shell').parent().append(content);
			            	}
			        	}
		             }
		        })
			})
		})
		//数据初始化
		$.ajax({
			type: 'POST',
		    url: "${ctx}/product/listProduct?productTypeName="+ $('#productFactory').val(),
		    dataType: "json",
		    contentType: "application/json; charset=utf-8",
		    success: function(data){
			  	 console.log(data)
			  	var msg = "";
			  	for(var i in data){
			  		msg += data[i].name + "|";
			  	}
			  	console.log(msg)
			  	msg = msg.substring(0,msg.length - 1);
			  	$('#productFactory').attr('msg',msg) 
		    	
		   	}
		})
		
		 $('#productFactory').off('keyup').on('keyup',function(){
			 $('#materialBare_shell').nextAll().remove();
			 $.ajax({
				type: 'POST',
			    url: "${ctx}/product/listProduct?productTypeName="+ $('#productFactory').val(),
			    dataType: "json",
			    contentType: "application/json; charset=utf-8",
			    success: function(data){
			    	$("#selectInputMenu").empty(); 
				  	console.log(data)
			    	var $ul = $('<ul></ul>');
				  	for(var i in data){
				  		var content = '<li class="inputValueLi">'+  data[i].name +'</li>';
						$ul.append(content)
				  	}
					$('#selectInputMenu').append($ul)
					
			   	}
			 })
		 });
		
		$('.select_dynamic').off('keyup').on('keyup',function(){
			var url = $(this).attr('data-url');
			 $.ajax({
				type: 'POST',
				url: url + $(this).val(),
			    dataType: "json",
			    contentType: "application/json; charset=utf-8",
			    success: function(data){
			    	$("#selectInputMenu").empty(); 
				  	console.log(data)
			    	var $ul = $('<ul></ul>');
				  	for(var i in data){
				  		var content = '<li class="inputValueLi">'+  data[i] +'</li>';
						$ul.append(content)
				  	}
					$('#selectInputMenu').append($ul)
					
			   	}
			 })
		 });
		
		 $('body').off('blur','#orderNo').on('blur','#orderNo',function(){
			var orderNo = $('#orderNo').val().trim();
			/* if(orderNo.length == 0){
				parent.$.messager.alert('提示', '订单号不能为空！', 'info');
		        return ;
			} */
			if(/^[a-zA-Z0-9]{10,10}$/.test(orderNo)){
				$.ajax({
		         	type: 'POST',
		             url: "${ctx}/order/getByOrderNo?orderNo=" + orderNo,
		             dataType: "json",
		             contentType: "application/json; charset=utf-8",
		             success: function(data){
		            	 if(data.success){
		            		 parent.$.messager.alert('提示', data.msg);
		            	 }
		             }
		        })
			}
		}) 
		$('#orderAdd').off('click').on('click',function(){
			//基本信息
			var orderNo = $('#orderNo').val().trim();
			
			if(!/^[a-zA-Z0-9]{10,10}$/.test(orderNo)){
				parent.$.messager.alert('提示', '订单号应为10个数字或字母！', 'info');
				/* getById('orderNo').focus();
				getById('orderNo').select();  */
		        return ;
			}
			var orderNoExist = false;
			$.ajax({
	         	type: 'POST',
	             url: "${ctx}/order/getByOrderNo?orderNo=" + orderNo,
	             dataType: "json",
	             contentType: "application/json; charset=utf-8",
	             async: false,
	             success: function(data){
	            	 console.log(data.success)
	            	 if(data.success){
	            		 orderNoExist = true;
	            	 }
	             }
	        })
	        if(orderNoExist){
				parent.$.messager.alert('提示', '订单号已存在！', 'info');
				/* getById('orderNo').focus();
				getById('orderNo').select();  */
		        return ;
			}
			
			var productFactory = $('#productFactory').val().trim();
			 if(productFactory.length < 1){
				parent.$.messager.alert('提示', '工厂机型不能为空！', 'info');
				getById('productFactory').focus();
				getById('productFactory').select(); 
		        return false;
			} 
			if(productFactory.length > 64){
				parent.$.messager.alert('提示', '工厂机型不能超过64个字符！', 'info');
				getById('productFactory').focus();
				getById('productFactory').select(); 
			       return false;
			} 
			
			
			var productFactoryExist = true;
			$.ajax({
	         	type: 'POST',
	             url: "${ctx}/product/isExistProduct?productName=" + productFactory,
	             dataType: "json",
	             contentType: "application/json; charset=utf-8",
	             async: false,
	             success: function(data){
	            	 if(data.success){
	            		 productFactoryExist = true;
	            	 }else{
	            		 productFactoryExist = false;
	            	 }
	             }
	        })
	     
			
	        if(!productFactoryExist){
				parent.$.messager.alert('提示', '新机型不存在，请相关人员先录入机型！', 'info');
				 getById('productFactory').focus();
				getById('productFactory').select(); 
		        return ;
			}
			
			
			
			var quantity = $('#quantity').val().trim();
			if(quantity.length < 1){
				parent.$.messager.alert('提示', '订单数量不能为空！', 'info');
				getById('quantity').focus();
				getById('quantity').select(); 
		        return false;
			}
			if(parseInt(quantity) <= 0){
				parent.$.messager.alert('提示', '订单数量不能小于0！', 'info');
				getById('quantity').focus();
				getById('quantity').select(); 
		        return false;
			}
			if(parseInt(quantity) > 2147483647){
				parent.$.messager.alert('提示', '订单数量不能超过2147483647！', 'info');
				getById('quantity').focus();
				getById('quantity').select(); 
		        return false;
			}
			/* var bizName = $('#bizName').val(); */
			var payment = $('#payment').val().trim();
			if(payment.length < 1){
				parent.$.messager.alert('提示', '付款方式不能为空！', 'info');
				getById('payment').focus();
				getById('payment').select(); 
		        return false;
			} 
			if(payment.length > 100){
				parent.$.messager.alert('提示', '付款方式不能超过100个字符！', 'info');
				getById('payment').focus();
				getById('payment').select(); 
		        return false;
			} 
			var clientName = $('#clientName').val().trim();
			if(clientName.length < 1){
				parent.$.messager.alert('提示', '客户名称不能为空！', 'info');
				getById('clientName').focus();
				getById('clientName').select(); 
			       return false;
			} 
			if(clientName.length > 64 ){
				parent.$.messager.alert('提示', '客户名称不能超过64个字符！', 'info');
				getById('clientName').focus();
				getById('clientName').select(); 
			       return false;
			} 
			 
			 var clientBrand = $('#clientBrand').val().trim();
			 if(clientBrand.length < 1){
				parent.$.messager.alert('提示', '客户铭牌不能为空！', 'info');
				getById('clientBrand').focus();
				getById('clientBrand').select(); 
		        return false;
			} 
			 if(clientBrand.length > 64 ){
					parent.$.messager.alert('提示', '客户铭牌不能超过64个字符！', 'info');
					getById('clientBrand').focus();
					getById('clientBrand').select(); 
			        return false;
				} 
			var clientNameCode = $('#clientNameCode').val().trim();
			if(clientNameCode.length < 1){
				parent.$.messager.alert('提示', '客户编码不能为空！', 'info');
				$('#clientNameCode').focus();
				$('#clientNameCode').select(); 
		        return false;
			} 
			 if(clientNameCode.length > 64){
					parent.$.messager.alert('提示', '客户编码不能超过64个字符！', 'info');
					$('#clientNameCode').focus();
					$('#clientNameCode').select(); 
			        return false;
				} 
			var district = $('#district').val().trim();
			if(district.length < 1){
				parent.$.messager.alert('提示', '所在国家不能为空！', 'info');
				getById('district').focus();
				getById('district').select(); 
		        return false;
			} 
			if(district.length  > 100){
				parent.$.messager.alert('提示', '所在国家不能超过100个字符！', 'info');
				getById('district').focus();
				getById('district').select(); 
		        return false;
			} 
			var productClient = $('#productClient').val().trim();
			if(productClient.length < 1){
				parent.$.messager.alert('提示', '客户机型不能为空！', 'info');
				getById('productClient').focus();
				getById('productClient').select(); 
		        return false;
			} 
			if(productClient.length > 64){
				parent.$.messager.alert('提示', '客户机型不能超过64个字符！', 'info');
				getById('productClient').focus();
				getById('productClient').select(); 
		        return false;
			} 
			var dateOrder = $('#dateOrder').datebox('getValue');
			if(dateOrder.length < 1){
				parent.$.messager.alert('提示', '下单日期不能为空！', 'info');
				getById('dateOrder').focus();
				getById('dateOrder').select(); 
		        return false;
			} 
			var dateExamine = $('#dateExamine').datebox('getValue');
			if(dateExamine.length < 1){
				parent.$.messager.alert('提示', '验货日期不能为空！', 'info');
				getById('dateExamine').focus();
				getById('dateExamine').select(); 
		        return false;
			} 
			var dateDelivery = $('#dateDelivery').datebox('getValue');
			if(dateDelivery.length < 1){
				parent.$.messager.alert('提示', '交货日期不能为空！', 'info');
				getById('dateDelivery').focus();
				getById('dateDelivery').select(); 
		        return false;
			}
			var placeDelivery = $('#placeDelivery').val().trim();
			if(placeDelivery.length < 1){
				parent.$.messager.alert('提示', '交货地点不能为空！', 'info');
				getById('placeDelivery').focus();
				getById('placeDelivery').select(); 
		        return false;
			} 
			if(placeDelivery.length > 64){
				parent.$.messager.alert('提示', '交货地点不能超过64个字符！', 'info');
				getById('placeDelivery').focus();
				getById('placeDelivery').select(); 
		        return false;
			} 
			//软件信息
			var iscustom = $("input[name='iscustom']:checked").val();
			if(iscustom){
				iscustom = 1;
			}else{
				iscustom = 2;
			}
			var osVersion = $("#version").val().trim();
	      /*   if(osVersion == ""){
				parent.$.messager.alert('提示', '软件版本信息不能为空！', 'info');
				getById('version').focus();
				getById('version').select(); 
		        return false;
			}  */
		        
			var gui = $('#gui').val().trim();
			if(gui.length < 1){
				parent.$.messager.alert('提示', 'GUI不能为空！', 'info');
				getById('gui').focus();
				getById('gui').select(); 
		        return false;
			} 
			if(gui.length > 64){
				parent.$.messager.alert('提示', 'GUI不能超过64个字符！', 'info');
				getById('gui').focus();
				getById('gui').select(); 
		        return false;
			} 
			var uuid = $('#uuid').val().trim();
			if(uuid.length < 1){
				parent.$.messager.alert('提示', 'UUID不能为空！', 'info');
				getById('uuid').focus();
				getById('uuid').select(); 
		        return false;
			}
			if(uuid.length > 64){
				parent.$.messager.alert('提示', 'UUID不能超过64个字符！', 'info');
				getById('uuid').focus();
				getById('uuid').select(); 
		        return false;
			}
			var langOs = $('#langOs').val().trim();
			 if(langOs.length < 1){
				parent.$.messager.alert('提示', '系统语言不能为空！', 'info');
				getById('langOs').focus();
				getById('langOs').select(); 
		        return false;
			}  
			if(langOs.length > 64){
				parent.$.messager.alert('提示', '系统语言不能超过64个字符！', 'info');
				getById('langOs').focus();
				getById('langOs').select(); 
			       return false;
			} 
			var langDefault = $('#langDefault').val().trim();
			if(langDefault.length < 1){
				parent.$.messager.alert('提示', '默认语言不能为空！', 'info');
				getById('langDefault').focus();
				getById('langDefault').select(); 
		        return false;
			}
			if(langDefault.length > 64){
				parent.$.messager.alert('提示', '默认语言不能超过64个字符！', 'info');
				getById('langDefault').focus();
				getById('langDefault').select(); 
		        return false;
			}
			var timezone = $('#timezone').val().trim();
			if(timezone.length < 1){
				parent.$.messager.alert('提示', '地区时间不能为空！', 'info');
				getById('timezone').focus();
				getById('timezone').select(); 
		        return false;
			} 
			if(timezone.length > 64){
				parent.$.messager.alert('提示', '地区时间不能超过64个字符！', 'info');
				getById('timezone').focus();
				getById('timezone').select(); 
		        return false;
			} 
			var bootLogo = $('#bootLogo').val().trim();
			if(bootLogo.length < 1){
				parent.$.messager.alert('提示', '开机画面不能为空！', 'info');
				getByIdgetById('bootLogo').focus();
				getById('bootLogo').select(); 
		        return false;
			} 
			if(bootLogo.length > 200){
				parent.$.messager.alert('提示', '开机画面不能超过200个字符！', 'info');
				getByIdgetById('bootLogo').focus();
				getById('bootLogo').select(); 
		        return false;
			} 
			var preFile = $('#preFile').val().trim();
			if(preFile.length < 1){
				parent.$.messager.alert('提示', '预读文件不能为空！', 'info');
				getById('preFile').focus();
				getById('preFile').select(); 
		        return false;
			} 
			if(preFile.length > 200){
				parent.$.messager.alert('提示', '预读文件不能超过64个字符！', 'info');
				getById('preFile').focus();
				getById('preFile').select(); 
		        return false;
			} 
			var preMap = $('#preMap').val().trim();
			if(preMap.length < 1){
				parent.$.messager.alert('提示', '预读地图不能为空！', 'info');
				getById('preMap').focus();
				getById('preMap').select(); 
		        return false;
			}
			if(preMap.length > 64){
				parent.$.messager.alert('提示', '预读地图不能64个字符！', 'info');
				getById('preMap').focus();
				getById('preMap').select(); 
		        return false;
			}
			
			
			
			var orderHardwareCommands = [];
			//裸机物料(硬件)
			$('#materialBareMain').parent().find('.contain').each(function(){
				var tdValue = $(this).children();
				var hardware = {
						name : tdValue.eq(0).text().trim(),
						specification : tdValue.eq(2).find("input").val().trim(),
						supplier : tdValue.eq(1).find("input").val().trim()
				};
				orderHardwareCommands.push(hardware);
			})
			
			var orderShellCommands = [];
			var color_flag = [];
			var craft_flag = [];
			//裸机物料(外壳)
			$('#materialBare_shell').parent().find('.contain').each(function(index,element){
				var tdValue = $(this).children();
				var name = tdValue.eq(0).find('span').text().trim();
				
				var color = tdValue.eq(1).find("input").val().trim();
				tdValue.eq(1).find("input").attr('id','shell_color'+index);
				 if(color.length < 1){
					 color_flag.push(index);
				} 
				var craft = tdValue.eq(2).find("input").val().trim();
				tdValue.eq(2).find("input").attr('id','shell_craft'+index)
				 if(craft.length < 1){
					 craft_flag.push(index);
				} 
				
				var silkScreen = tdValue.eq(3).find("input[name='silkScreen']:checked").val()
				if(silkScreen == "on"){
					silkScreen = 2;
				}else{
					silkScreen = 1;
				}
				var shell = {
						name : name,
						color : color,
						craft : craft,
						silkScreen : silkScreen
				};
				orderShellCommands.push(shell);
			})
			for(var i in color_flag){
				parent.$.messager.alert('提示', '请选择外壳颜色！', 'info');
				getById("shell_color" + color_flag[i]).focus();
				getById("shell_color" + color_flag[i]).select();
				return false;
		        break;
			}
			for(var i in craft_flag){
				parent.$.messager.alert('提示', '请选择外壳工艺！', 'info');
				getById("shell_craft" + craft_flag[i]).focus();
				getById("shell_craft" + craft_flag[i]).select();
				return false;
		        break;
			}
			
			var orderFittingCommands = [];
			//配件物料
			$('#materialFitting').parent().find('.contain').each(function(){
				var tdValue = $(this).children();
				var fitting = {
						name : tdValue.eq(0).text().trim(),
						specification : tdValue.eq(2).find("input").val().trim(),
						supplier : tdValue.eq(1).find("input").val().trim()
				};
				orderFittingCommands.push(fitting);
			})
			
			var orderPackingCommands = [];
			//包材物料
			$('#materialPacking').parent().find('.contain').each(function(){
				var tdValue = $(this).children();
				var packing = {
						name : tdValue.eq(0).text().trim(),
						specification : tdValue.eq(2).find("input").val().trim(),
						supplier : tdValue.eq(1).find("input").val().trim()
				};
				orderPackingCommands.push(packing);
			})
			
			var orderSpare = $('#orderSpare').val().trim();
			 if(orderSpare.length < 1){
				parent.$.messager.alert('提示', '备品不能为空！', 'info');
				getById('orderSpare').focus();
				getById('orderSpare').select(); 
		       	return false;
			} 
			 if(orderSpare.length > 300){
					parent.$.messager.alert('提示', '订单备品不能超过300个字符！', 'info');
					getById('orderSpare').focus();
					getById('orderSpare').select(); 
			       	return false;
				} 
			var description = $('#description').val().trim();
			if(description.length > 1000){
				parent.$.messager.alert('提示', '备注说明不能超过1000个字符！', 'info');
				getById('description').focus();
				getById('description').select(); 
		       	return false;
			}
			if(!$('orderAddForm').form('validate')){
				return false;
			}
			
			var orderOSCommand = {
					//软件信息
					 iscustom : iscustom,
					 gui : gui,
					 uuid : uuid,
					 langOs : langOs,
					 langDefault : langDefault,
					 timezone : timezone,
					 bootLogo :  bootLogo,
					 preFile : preFile,
					 preMap : preMap,
					 version : osVersion
			}
			var param = {
					//基础
					orderNo : orderNo.toUpperCase(),
					productFactory : productFactory,
					quantity : quantity,
					/* bizName : bizName, */
					clientName : clientName ,
					clientBrand : clientBrand ,
					clientNameCode : clientNameCode,
					district : district,
					productClient : productClient,
					dateOrder : dateOrder,
					dateExamine : dateExamine,
					dateDelivery : dateDelivery,
					placeDelivery : placeDelivery,
					payment : payment,
					
					orderOSCommand : orderOSCommand,
					 
					orderHardwareCommands : orderHardwareCommands,
					orderShellCommands : orderShellCommands,
					/* orderScreenCommands : orderScreenCommands, */
					orderPackingCommands : orderPackingCommands,
					orderFittingCommands : orderFittingCommands,
					
					orderSpare : orderSpare,
					description : description
			}
			progressLoad();
			 $.ajax({
	         	type: 'POST',
	             url: "${ctx}/order/add" ,
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
	 					parent_tabs.tabs('select', "个人订单");
	 					parent_tabs.tabs('select', "订单信息");
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
	});
	
	
	
	//硬件
	function bare_HardwareChooseFun() {
		var materialBareMainNames = "";
		$('#materialBareMain').parent().find('.contain').each(function(){
			materialBareMainNames += $(this).find('td').text().trim() + ","; 
		})
		materialBareMainNames = materialBareMainNames.substring(0,materialBareMainNames.length - 1);
		parent.$.modalDialog({
			title : '硬件选择',
			width : 800,
			height : 600,
			href : '${ctx}/materialBare/hardwareForOrderPage?materialBareMainNames='+encodeURIComponent(materialBareMainNames),
			buttons : [ {
				text : '确定',
				handler : function() {
					console.log(parent.$('#choose_hardware_main li'))
					if(parent.$('#choose_hardware_main li').length > 0){
						console.log(parent.$('#choose_hardware_main li'))
						var isExcit = false;
						$('#materialBareMain').parent().find('.contain').each(function(){
							var name = $(this).find('td').text().trim();
							parent.$('#choose_hardware_main li').each(function(){
								if(name == $(this).text().trim()){
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
					 	parent.$('#choose_hardware_main li').each(function(){
							var li = $(this);
							$('#materialBareMain').parent().find('.contain').each(function(){
								var name = $(this).find('td').text().trim();
								if(name == li.text().trim()){
									flag = true;
								}   
							})
							if(!flag){
								var content = ' <tr class="contain">'+
					            '<td>'+ li.text() +'</td>'+
					            '<td><input class="table_input color069" ></td>'+
					            '<td><input class="table_input color069" ></td>'+
					       		'</tr>';
								$('#materialBareMain').parent().append(content);
							}
							flag = false;
						})  
					 	
					}else{
						$('#materialBareMain').parent().find('.contain').remove();
					}
					parent.$.modalDialog.handler.dialog('close');
				}
			} ] 
		});
	}
	//外壳
	function bare_ShellChooseFun() {
		var materialBare_shellNames = "";
		$('#materialBare_shell').parent().find('.contain').each(function(){
			materialBare_shellNames += $(this).find('span').text().trim() + ","; 
		})
		materialBare_shellNames = materialBare_shellNames.substring(0,materialBare_shellNames.length - 1);
		parent.$.modalDialog({
			title : '外壳选择',
			width : 800,
			height : 600,
			href : '${ctx}/materialBare/shellForOrderPage?materialBare_shellNames='+encodeURIComponent(materialBare_shellNames),
			buttons : [ {
				text : '确定',
				handler : function() {
					
					if(parent.$('#choose_shell li').length > 0){
						var isExcit = false;
						$('#materialBare_shell').parent().find('.contain').each(function(){
							var name = $(this).find('span').text().trim();
							parent.$('#choose_shell li').each(function(){
								if(name == $(this).text().trim()){
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
						parent.$('#choose_shell li').each(function(){
							var li = $(this);
							$('#materialBare_shell').parent().find('.contain').each(function(){
								var name = $(this).find('span').text().trim();
								if(name == li.text()){
									flag = true;
								}   
							})
							if(!flag){
								var content = ' <tr class="contain">'+
					            '<td id="shell_'+ li.attr('id') +'" width="25%">部件：<span class="color069">'+ li.text() +'</span></td>'+
					            '<td width="30%">颜色：<input class="inputs select_static " msg="黑色|白色|银色|镀银|铁灰|枪色" ></td>'+
					            '<td width="30%">工艺：<input class="inputs select_static" msg="素材|光UV|哑UV|橡胶油|雾面"></td>'+
					            '<td width="15%">丝印：<input name="silkScreen" type="checkbox"></td>'+
					       		'</tr>';
								$('#materialBare_shell').parent().append(content);
							}
							flag = false;
						})
					}else{
						$('#materialBare_shell').parent().find('.contain').remove();
					}
					parent.$.modalDialog.handler.dialog('close');
				}
			} ]
		});
	}
	//包材
	function packingChooseFun() {
		var materialPackingNames = "";
		$('#materialPacking').parent().find('.contain').each(function(){
			materialPackingNames +=  $(this).find('td:first').text().trim() + ","; 
			
		})
		materialPackingNames = materialPackingNames.substring(0,materialPackingNames.length - 1);
		parent.$.modalDialog({
			title : '包材选择',
			width : 800,
			height : 600,
			href : '${ctx}/materialPacking/packingForOrderPage?materialPackingNames='+encodeURIComponent(materialPackingNames),
			buttons : [ {
				text : '确定',
				handler : function() {
					if(parent.$('#choose_packing li').length > 0){
						var isExcit = false;
						$('#materialPacking').parent().find('.contain').each(function(){
							var name = $(this).find('td').text().trim();
							parent.$('#choose_packing li').each(function(){
								
								if(name == $(this).text().trim()){
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
						parent.$('#choose_packing li').each(function(){
							var li = $(this);
							$('#materialPacking').parent().find('.contain').each(function(){
								var name = $(this).find('td').text();
								if(name == li.text()){
									flag = true;
								}
							})
							if(!flag){
								var content = ' <tr class="contain">'+
					            '<td id="paking_'+ li.attr('id') +'">'+ li.text() +'</td>'+
					            '<td><input class="table_input" ></td>'+
					            '<td><input class="table_input" ></td>'+
					       		'</tr>';
								$('#materialPacking').parent().append(content);
							}
							flag = false;
						})
					}else{
						$('#materialPacking').parent().find('.contain').remove();
					}
					parent.$.modalDialog.handler.dialog('close');
				}
			} ]
		});
	}
	//配件
	function fittingChooseFun() {
		var materialFittingNames = "";
		$('#materialFitting').parent().find('.contain').each(function(){
			var fittingName = $(this).find('td:first').text();
			materialFittingNames +=  fittingName.trim() + ","; 
			
		})
		materialFittingNames = materialFittingNames.substring(0,materialFittingNames.length - 1);
		parent.$.modalDialog({
			title : '配件选择',
			width : 800,
			height : 600,
			href : '${ctx}/materialFitting/fittingForOrderPage?materialFittingNames='+encodeURIComponent(materialFittingNames),
			buttons : [ {
				text : '确定',
				handler : function() {
					
					if(parent.$('#choose_fitting li').length > 0){
						var isExcit = false;
						$('#materialFitting').parent().find('.contain').each(function(){
							var name = $(this).find('td').text().trim();
							parent.$('#choose_fitting li').each(function(){
								
								if(name == $(this).text().trim()){
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
						parent.$('#choose_fitting li').each(function(){
							var li = $(this);
							$('#materialFitting').parent().find('.contain').each(function(){
								var name = $(this).find('td').text().trim();
								if(name == li.text()){
									flag = true;
								}   
							})
							if(!flag){
								var content = ' <tr class="contain">'+
					            '<td id="fitting_'+ li.attr('id') +'">'+ li.text() +'</td>'+
					            '<td><input class="table_input" ></td>'+
					            '<td><input class="table_input" ></td>'+
					       		'</tr>';
								$('#materialFitting').parent().append(content);
							}
							flag = false;
						})
					}else{
						$('#materialFitting').parent().find('.contain').remove();
					}
					parent.$.modalDialog.handler.dialog('close');
				}
			} ]
		});
	}
	
	function checkFactory(){
		var productFactory = $('#productFactory').val().trim();
		if(productFactory != '' && productFactory != null && typeof(productFactory) != 'undefined'){
			var focusElementId = document.activeElement.id;
			console.log(document.activeElement)
			if(focusElementId == 'selectInputMenu'){
				return ;
			}
			var productFactoryExist = true;
			$.ajax({
	         	type: 'POST',
	             url: "${ctx}/product/isExistProduct?productName=" + productFactory,
	             dataType: "json",
	             contentType: "application/json; charset=utf-8",
	             async: false,
	             success: function(data){
	            	 if(data.success){
	            		 productFactoryExist = true;
	            	 }else{
	            		 productFactoryExist = false;
	            	 }
	             }
	        })
	        
	        if(!productFactoryExist){
				parent.$.messager.alert('提示', '新机型不存在，请相关人员先录入机型！', 'info');
				 getById('productFactory').focus();
				getById('productFactory').select(); 
		        return ;
			}
		}
	}
	$.extend($.fn.validatebox.defaults.rules, {
		productFactoryValidate : {
	        validator : function(value, param) {
	        	var productFactory = $('#productFactory').val().trim();
	            var productFactoryExist = false;
	            $.ajax({
	                type : 'post',
	                async : false,
	                dataType: "json",
	                url: "${ctx}/product/isExistProduct?productName=" + productFactory,
	                success : function(data) {
	                	if(data.success){
	                		//alert(55)
		            		 productFactoryExist = true;
		            	 }else{
		            		 productFactoryExist = false;
		            	 }
	                }
	            });
	            return productFactoryExist;
	        },
	        message : '新机型不存在，请相关人员先录入机型！'
	    }
	});

</script>




<body>
<div id="selectInputMenu"></div>
<div>
	<form id="orderAddForm">
	<!-- 基础信息 -->
	<table border="0" cellpadding="0" cellspacing="0" class="ordertable">
        <tr class="tabletitle">
            <td colspan="8">基础信息： </td>
        </tr>
        <tr>
            <td class="lefttd" >订单号类型：</td>
            <td class="righttd" width="20%"><input type="text" class="inputs easyui-validatebox" id="orderNo"  missingMessage="此项为必填项" required="true" validType="length[10,10]" invalidMessage="订单长度为10个数字或字母"/></td>
            <!-- <td class="lefttd"> 业务名称：
            <td class="righttd"><input type="text" class="inputs" id="bizName" /></td> -->
            <td class="lefttd"> 工厂机型：
            <td class="righttd"width="20%"><input type="text" class="inputs easyui-validatebox"  id="productFactory" missingMessage="此项为必填项" required="true" validType="productFactoryValidate"/></td>
            <td class="lefttd">订单数量： </td>
            <td class=""><input type="text" class="inputs easyui-numberbox" id="quantity"  required="true"  missingMessage="必须填写数字" /></td>
            <td class="lefttd">付款方式：</td>
            <!-- <td class="righttd"width="20%"><input type="text" class="inputs select_static  "  msg="TT|TC|TT+LC|FOC" id="payment" required="true"  missingMessage="此项为必填项" /></td> -->
            <td class="righttd"width="20%"><input type="text" class="inputs select_static  "  msg="TT|TC|TT+LC|FOC" id="payment"  missingMessage="此项为必填项" required="true" /></td>
            </tr>
        <tr>
            <td class="lefttd">客户名称：</td>
            <td class="righttd"><input type="text" class="inputs select_dynamic  " data-url="${ctx}/order/listTopClientName?clientName=" id="clientName" missingMessage="此项为必填项" required="true" /></td>
            <td class="lefttd">客户铭牌：</td>
            <td class="righttd"><input type="text" class="inputs select_dynamic  " data-url="${ctx}/order/listTopClientBrand?clientBrand=" id="clientBrand" missingMessage="此项为必填项" required="true" /></td>
            <td class="lefttd">客户编码：</td>
            <td class="righttd"><input type="text" class="inputs easyui-validatebox" id="clientNameCode"  required="true"/></td> 
            <td class="lefttd">所在国家：</td>
            <td class="righttd"><input type="text" class="inputs select_dynamic  " data-url="${ctx}/order/listTopDistrict?district=" id="district" missingMessage="此项为必填项" required="true" /></td>
        </tr>
        <tr>
            <td class="lefttd">客户型号：</td>
            <td class="righttd"><input type="text" class="inputs  " data-url="${ctx}/order/listTopClientName?productClient=" id="productClient" missingMessage="此项为必填项" required="true" /></td>
            <td class="lefttd">下单日期：</td>
            <td class="righttd"><input type="text"  id="dateOrder"  class="inputs easyui-datebox" required="required" editable="false"/></td>
            <td class="lefttd">验货日期：</td>
            <td class="righttd"><input type="text" class="inputs easyui-datebox" id="dateExamine" required="required" editable="false" width="170px"/></td>
            <td class="lefttd">交货日期:</td>
            <td class="righttd"><input type="text" class="inputs easyui-datebox" id="dateDelivery" required="required" editable="false"/></td>
        </tr>
        <tr>
            <td class="lefttd">交货地点：</td>
            <td class="righttd"><input type="text" class="inputs select_static  " msg="香港|深圳|待定" id="placeDelivery" missingMessage="此项为必填项" required="true" /></td>
         </tr>
    </table>
    
    <!-- 外壳工艺 -->
    <table border="0" cellpadding="0" cellspacing="0" class="bordertable" style="margin: 0px">
        <tr class="tabletop">
            <td colspan="4">外壳工艺：<span class="choose" onclick="bare_ShellChooseFun()">[选择物料]</span> </td>
        </tr>
        <tr id="materialBare_shell">
        </tr>
         </table>
        <table border="0" cellpadding="0" cellspacing="0" class="bordertable" >
    </table>
    
    <!-- 软件信息 -->
    <table border="0" cellpadding="0" cellspacing="0" class="ordertable">
        <tr class="tabletitle">
            <td colspan="8">软件信息： </td>
        </tr>
        <tr>
            <td class="lefttd">软件信息：</td>
            <td colspan="1">
            	<label><input type="radio" name="iscustom" value="true" id="" checked="checked"/>定制</label>
              		<label><input type="radio" name="iscustom" value="false" id=""/>公版</label>
              	</td>	
              	<td class="lefttd"> 软件版本号：
            	<td class="righttd"><input type="text" class="inputs select_static  "  id="version" msg="无|待定" missingMessage="此项为必填项" required="true" /></td> 
        </tr>
        <tr>
            <td class="lefttd">GUI：</td>
            <td class="righttd"><input type="text" class="inputs select_dynamic  " data-url="${ctx}/order/listTopGui?gui=" id="gui" missingMessage="此项为必填项" required="true" /></td>
            <td class="lefttd"> UUID号 ：
            <td class="righttd"><input type="text" class="inputs select_static  "  id="uuid" msg="32位UUID|16位UUID|待定" missingMessage="此项为必填项" required="true" /></td>
            <td class="lefttd">系统语言： </td>
            <td class="righttd"><input type="text" class="inputs select_dynamic  " data-url="${ctx}/order/listTopLangOs?langOs=" id="langOs" missingMessage="此项为必填项" required="true" /></td>
            <td class="lefttd">默认语言：</td>
            <td class="righttd"><input type="text" class="inputs select_dynamic  " data-url="${ctx}/order/listTopLangDefault?langDefault=" id="langDefault" missingMessage="此项为必填项" required="true" /></td>
        </tr>
        <tr>
            <td class="lefttd">地区时间：</td>
            <td class="righttd"><input type="text" class="inputs select_dynamic  " data-url="${ctx}/order/listTopTimezone?timezone=" id="timezone" missingMessage="此项为必填项" required="true" /></td>
            <td class="lefttd">开机画面：</td>
            <td class="righttd"><input type="text" class="inputs select_static  " id="bootLogo" msg="有见文档|系统默认|无" missingMessage="此项为必填项" required="true" /></td>
            <!-- <td class="lefttd">预存文件：</td>
            <td class="righttd"><input type="text" class="inputs select_static  " id="preFile" msg="无|待定|有，存Flash|有，存TF卡|有，存SD卡" missingMessage="此项为必填项" required="true" /></td>
            <td class="lefttd">预存地图：</td> 
            <td class="righttd"><input type="text" class="inputs select_static  " id="preMap" msg="无|待定|有，存Flash|有，存TF卡|有，存SD卡" missingMessage="此项为必填项" required="true" /></td> -->
            <td class="lefttd">预存文件：</td>
            <td class="righttd"><input type="text" class="inputs select_static  " id="preFile" msg="无|待定" missingMessage="此项为必填项" required="true" /></td>
            <td class="lefttd">预存地图：</td> 
            <td class="righttd"><input type="text" class="inputs select_static  " id="preMap" msg="无|待定" missingMessage="此项为必填项" required="true" /></td>
        </tr>
    </table>
    
    <!-- 裸机包装 -->
     <table border="0" cellpadding="0" cellspacing="0" class="bordertable" style="margin: 0px">
        <tr class="tabletitle">
            <td colspan="3">硬件信息：<span class="choose" onclick="bare_HardwareChooseFun()">[选择物料]</span> </td>
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
            <td colspan="3">配件部分：<span class="choose" onclick="fittingChooseFun()">[选择物料]</span> </td>
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
            <td colspan="3">包材部分：<span class="choose" onclick="packingChooseFun()">[选择物料]</span>  </td>
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
        	<td colspan="3"><textarea name="" id="orderSpare" class="textareacss " cols="1" rows="8" maxlength="300"></textarea></td>
        </tr>
    </table>
     <!-- 备品说明-->
     <table border="0" cellpadding="0" cellspacing="0" class="bordertable">
        <tr class="tabletitle">
            <td colspan="3">备注说明： </td>
        </tr>
        <tr>
        	<td colspan="3"><textarea name="" id="description" class="textareacss " cols="1" rows="8" maxlength="1000"></textarea></td>
        </tr>
	</table>
	</form>
</div>
<div class="" align="center">
	<!-- <a id="orderAdd_" class='btn-default'>暂存</a> --> <button id="orderAdd" class='btn-default'>提交</button> 
</div>
</body>
