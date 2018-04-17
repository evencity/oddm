<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>
	.ordertable{background-color:#eee;width:100%;color:#666;margin:10px 0px;font-size:0.8em;text-align:left;}
	.tabletitle{text-align:left; background-color:#c8d7e1;color:#333;font-size:1.4em; font-weight:500;}
	.tabletop{text-align:left; background-color:#DCE0E2;color:#333;font-size:1.2em; font-weight:500;}
	.ordertable tr td{padding:10px 0 10px 6px;}
	.inputs{border:1px solid #CCC;width:145px;padding:5px 0 2px 4px;font-size:12px;color:#999;border-radius: 4px;height:22px}
	.lefttd{width:85px;}
    .lefttd90{width:90px; text-align:right;}
    .righttd{color:#069;}
    .bordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:center;border-collapse:collapse;}
	.bordertable td{border:1px solid #ccc;padding:10px 0 10px 6px;height:20px;}
	.textareacss{width:96%;border-radius: 5px;border: 0px;height: 48px}
	.choose{color: #06c;cursor: pointer;}
	.btn-default {border: 1px solid #DDDDDD;padding: 7px 9px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #333;margin-right: 3px;}
	.btn-default:hover {border: 1px solid #DDDDDD;background: #04B5F9;padding: 7px 9px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #FFF;margin-right: 3px;}
	.color_f60 {color: #f60;}
	.tip{height: 16px;width: 16px;display: inline-block;padding: 0px 4px;cursor: pointer;}
	pre {white-space: pre-wrap;word-wrap: break-word;text-align: left;margin: 0}
</style>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
	var orderId = '${orderInfoDTO.id}';
	$(function() {
		
	});
	function addFun(){
		var $content = $('<div class="contain"><form class="materialAddForm"><table border="0" cellpadding="0" cellspacing="0" class="ordertable ">'+
						    '<tr class="tabletitle">'+
						    	'<td colspan="7"><span class="section"></span> <span class="color_f60">&nbsp;&nbsp;(未齐料)</span> </td>'+
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
					            '<td class="" ><input class="dateSubmit "></td>'+
					            '<td class="lefttd" >齐料日期：</td>'+
					            '<td class="" ><input class="dateFinish "></td>'+
					        '</tr>'+
					        '<tr>'+
						        ' <td class="lefttd" >到今天为止天数 :'+
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
					   	 	'<button class="btn-default" onclick="addMaterualFollow(this);">提交</button>'+
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
		});
		$content.find('.dateCommit').datebox({    
			editable : false,
			"onChange":function(newValue,oldValue){// 计算总额  
				$content.find('.dateTo').text(countDateTo(newValue));
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
	
	//添加数据库操作
	function addMaterualFollow(obj){
		var me = $(obj);
		var table = me.parent().parent().find('table');//获取table
		
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
		
		var isValid = me.parent().parent().find('.materialAddForm').form('validate');
		console.log("materialAddForm 验证"+isValid)
		if(!isValid){
			return;
		}
		var dateTo = typeof(table.find('.dateTo').text()) == 'undefined' ? null : table.find('.dateTo').text().trim();
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
			url: "${ctx}/materialFollowup/_add" ,
			data: JSON.stringify(params) ,
			dataType: "json",
			contentType: "application/json; charset=utf-8",
			async: false,
			success: function(data){
            	 if(data.success){
            		 parent.$.messager.alert('提示', data.msg);
            		 var cotainDiv = table.parents('.contain');
            		 cotainDiv.empty();
            		 var $content = $('<form class="materialAddForm"><table border="0" cellpadding="0" cellspacing="0" class="ordertable ">'+
            				    '<tr class="tabletitle">'+
            				    	'<td colspan="8"><span class="section"></span> <span class="color_f60">&nbsp;&nbsp;(未齐料)</span> </td>'+
            			        '</tr>'+
            			        '<tr>'+
            			            '<td class="lefttd" width="10%">物料编号：</td>'+
            			            '<td class="righttd" width="15%">'+mtCode+'</td>'+
            			            '<td class="lefttd" width="10%"> 生产顺位：</td>'+
            			            '<td class="righttd" width="15%">'+prodLine+'</td>'+
            			        	'<td class="lefttd" width="10%">批量：</td>'+
            			            '<td class="righttd" width="15%">'+quality+'</td>'+
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
			console.log("计算拆单数量")
			$(this).text("拆单:"+index+"/"+total)
		})
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
	            <td class="righttd" width="15%"><div id="orderNo">${orderInfoDTO.orderNo}</div> </td>
	            <td class="lefttd" width="10%">业务名称： </td>
	            <td class="righttd" width="15%"><div id="seller">${orderInfoDTO.seller}</div></td>       
	            <td class="lefttd" width="10%">跟单名称:</td>
	            <td class="righttd" width="15%"><div id="merchandiser">${orderInfoDTO.merchandiser}</div></td>
	            <td class="lefttd" width="10%">订单数量：</td>
	            <td class="righttd" width="15%"><div id="quantity">${orderInfoDTO.quantity}</div></td> 
	        </tr>
	        <tr>
	        	<td class="lefttd">客户名称：</td>
	            <td class="righttd"><div id="clientName">${orderInfoDTO.clientName}</div></td> 
	        </tr>
	       
	    </table>
	   
	</div>
</body>