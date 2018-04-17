<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<style>
	.ordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:left;}
	.tabletitle{text-align:left; background-color:#c8d7e1;color:#333;font-size:1.4em; font-weight:500;}
	.tabletop{text-align:left; background-color:#DCE0E2;color:#333;font-size:1.2em; font-weight:500;}
	.ordertable tr td{padding:10px 0 10px 6px;}
	.inputs{border:1px solid #CCC;width:90%;padding:5px 0 2px 4px;font-size:12px;color:#999;}
	.lefttd{width:65px;}
    .lefttd90{width:90px; text-align:right;}
    .righttd{color:#069;}
    .bordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:center;border-collapse:collapse;text-align:left;}
	.bordertable td{border:1px solid #ccc;padding:10px 0 10px 6px;height:20px;}
 	.textareacss{width:90%;border: 0px} 
	.choose{color: #06c;cursor: pointer;}
	.table_input{border: 0;background: none;padding: 5px 0;width: 95%;color: #666;}
	span{cursor: pointer;color: red}
	#productEdit_editBtn{border: 1px solid #DDDDDD;background: white;padding: 10px 18px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #333;}
	#productEdit_editBtn:hover{border: 1px solid #DDDDDD;background: #04B5F9;padding: 10px 18px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #FFF;}
</style>
<jsp:include page="../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />

<title>机型修改</title>
	<script type="text/javascript">
	var productId = "${productId}";
	function getById(id){
		return document.getElementById(id);
	}
	$(function() {
		
		$('#productType').combobox({
			url : '${pageContext.request.contextPath}/productType/dataGrid',
			 valueField:'id',    
			 textField:'name',
			 value : '${product.typeId}',
			 panelHeight : 'auto'
		});
		
		//加载数据
		//基础物料
		$.ajax({
         	type: 'GET',
             url: "${ctx}/product/get?id=${productId}",
             dataType: "json",
             contentType: "application/json; charset=utf-8",
             success: function(data){
            	var name = data.name;
            	var type = data.typeName;
            	var description = data.description;
            	var materialBares = data.materialBareDTOs;
            	var materialFittings = data.materialFittingDTOs;
            	var materialPackings = data.materialPackingDTOs;
            	var hardware ="";
            	var shell ="";
            	var screen ="";
            	var fitting ="";
            	var packing ="";
            	$("#product_name").text(name);
            	$("#product_type").text(type);
            	$("#description").text(description);
            	
            	for(var i in materialBares){
        			if(materialBares[i].typeId == 1){
                		//硬件
        				var content = ' <tr class="contain">'+
        	            '<td id="hardware_'+ materialBares[i].id +'">'+ materialBares[i].name +' </td>'+
        	       		'</tr>';
        	   
        				$('#materialBare_hardware').parent().append(content);
                	}
        			if(materialBares[i].typeId == 2){
                		//外壳
        				var content = ' <tr class="contain">'+
        	            '<td id="shell_'+ materialBares[i].id +'">'+ materialBares[i].name +' </td>'+
        	       		'</tr>';
        				$('#materialBare_shell').parent().append(content);
                	}
        			if(materialBares[i].typeId == 3){
        				//屏
        				var content = ' <tr class="contain">'+
        	            '<td id="srceen_'+ materialBares[i].id +'" align="left">'+ materialBares[i].name +' </td>'+
        	       		'</tr>';
        				$('#materialBare_screen').parent().append(content);
        			}
        		}
        		
        		
        		//包材
        		for(var i in materialPackings){
        			var content = ' <tr class="contain">'+
                    '<td id="packing_'+ materialPackings[i].id +'">'+ materialPackings[i].name +' </td>'+
               		'</tr>';
        			$('#materialPacking').parent().append(content);
        		}
        		
        		//配件
        		for(var i in materialFittings){
        			var content = ' <tr class="contain">'+
                    '<td id="fitting_'+ materialFittings[i].id +'">'+ materialFittings[i].name +' </td>'+
               		'</tr>';
        			$('#materialFitting').parent().append(content);
        		}
             }
       })
		$("#description").val('${product.description}');
		
		$('body').off('click','#productEdit_editBtn').on('click','#productEdit_editBtn',function(){
			var materialBareIds = "";
			var materialFittingIds = "";
			var materialPackingIds = "";
			$('#materialBare_hardware').parent().find('.contain').each(function(){
				var id = $(this).find('td').attr('id');
				id = id.substring(9);
				materialBareIds += id +","
			})
			$('#materialBare_shell').parent().find('.contain').each(function(){
				var id = $(this).find('td').attr('id');
				id = id.substring(6);
				materialBareIds += id +","
			})
			$('#materialBare_screen').parent().find('.contain').each(function(){
				var id = $(this).find('td').attr('id');
				id = id.substring(7);
				materialBareIds += id +","
			})
			
			//包材
			$('#materialPacking').parent().find('.contain').each(function(){
				var id = $(this).find('td').attr('id');
				id = id.substring(8);
				materialPackingIds += id +","
			})
			
			//配件
			$('#materialFitting').parent().find('.contain').each(function(){
				var id = $(this).find('td').attr('id');
				id = id.substring(8);
				materialFittingIds += id +","
			})
			
			
			materialBareIds = materialBareIds.substring(0,materialBareIds.length - 1);
		//	materialFittingIds = materialFittingIds.substring(0,materialFittingIds.length - 1);
		//	materialPackingIds = materialPackingIds.substring(0,materialPackingIds.length - 1);
			var name = $('#name').val().trim();
			if(name.length < 1 ){
				parent.$.messager.alert('提示', '机型名不能为空！', 'info');
				getById('quantity').focus();
				getById('quantity').select(); 
		        return false;
			}
			if(name.length > 64 ){
				parent.$.messager.alert('提示', '机型名不能超过64个字符！', 'info');
				getById('quantity').focus();
				getById('quantity').select(); 
		        return false;
			}
			var type = $('#productType').combobox('getValue');
			var description = $('#description').val();
			if(type == "" || type == null){
				parent.$.messager.alert('提示', '机型类型不能为空');
				return false;
			}
			if(materialBareIds == "" || materialBareIds == null){
				 parent.$.messager.alert('提示', '机型必须绑定相关外壳！');
				return false;
			}
			var param = {
					"id":productId,
					"description":description,
					"name":name,
					"type":type,
					"materialBareIds":materialBareIds,
				//	"materialFittingIds":materialFittingIds,
					"materialPackingIds":materialPackingIds
					};
			$.ajax({
	         	type: 'POST',
	             url: "${ctx}/product/edit" ,
	             data: JSON.stringify(param) ,
	             dataType: "json",
	             contentType: "application/json; charset=utf-8",
	             success: function(data){
	            	 if(data.success){
	            		 parent.$.messager.alert('提示', '修改成功')
	            		 var parent_tabs = parent.$('#index_tabs');
	            		 var index = parent_tabs.tabs('getTabIndex', parent.$('#index_tabs').tabs('getSelected'));
	 					 var tab = parent_tabs.tabs('getTab', index);
	 					 if (tab.panel('options').closable) {
	 						parent_tabs.tabs('close', index);
	 					 }
	 					parent_tabs.tabs('select', "订单信息");
	 					parent_tabs.tabs('update', {
							tab : parent_tabs.tabs('getSelected'), //获取当前被选中的页面
							options : {
								//Title : '新标题',
								}
							}
						);
	            	 }else{
	            		 
	            	 }
	             }
	       })
		})
	});
	
	
	//包材
	function packingChooseFun() {
		var materialPackingIds = "";
		$('#materialPacking').parent().find('.contain').each(function(){
			materialPackingIds +=  $(this).find('td').attr('id').substring(8) + "|" + $(this).find('td').text() + ","; 
			
		})
		materialPackingIds = materialPackingIds.substring(0,materialPackingIds.length - 1);
		parent.$.modalDialog({
			title : '包材选择',
			width : 800,
			height : 600,
			href : '${ctx}/materialPacking/packingPage?materialPackingIds='+materialPackingIds,
			buttons : [ {
				text : '确定',
				handler : function() {
					$('#materialPacking').parent().find('.contain').each(function(){
						$(this).remove();
					})
					if(parent.$('#choose_packing li').length > 0){
						var flag = false;
						parent.$('#choose_packing li').each(function(){
							var li = $(this);
							$('#materialPacking').parent().find('.contain').each(function(){
								var id = $(this).find('td').attr('id');
								id = id.substring(8);
								if(id == li.attr('id')){
									flag = true;
								}
							})
							if(!flag){
								var content = ' <tr class="contain">'+
					            '<td id="srceen_'+ li.attr('id') +'" >'+ li.text() +'</td>'+
					       		'</tr>';
								$('#materialPacking').parent().append(content);
								
							}
							flag = false;
						})
					}
					parent.$.modalDialog.handler.dialog('close');
				}
			} ]
		});
	}
	//配件
	function fittingChooseFun() {
		var materialFittingIds = "";
		$('#materialFitting').parent().find('.contain').each(function(){
			materialFittingIds +=  $(this).find('td').attr('id').substring(8) + "|" + $(this).find('td').text() + ","; 
			
		})
		materialFittingIds = materialFittingIds.substring(0,materialFittingIds.length - 1);
		parent.$.modalDialog({
			title : '配件选择',
			width : 800,
			height : 600,
			href : '${ctx}/materialFitting/fittingPage?materialFittingIds='+materialFittingIds,
			buttons : [ {
				text : '确定',
				handler : function() {
					$('#materialFitting').parent().find('.contain').each(function(){
						$(this).remove();
					})
					if(parent.$('#choose_fitting li').length > 0){
						var flag = false;
						parent.$('#choose_fitting li').each(function(){
							var li = $(this);
							$('#materialFitting').parent().find('.contain').each(function(){
								var id = $(this).find('td').attr('id');
								id = id.substring(8);
								if(id == li.attr('id')){
									flag = true;
								}   
							})
							if(!flag){
								var content = ' <tr class="contain">'+
					            '<td id="srceen_'+ li.attr('id') +'">'+ li.text() +'</td>'+
					       		'</tr>';
								$('#materialFitting').parent().append(content);
								
							}
							flag = false;
						})
					}
					parent.$.modalDialog.handler.dialog('close');
				}
			} ]
		});
	}
	//硬件
	function bare_HardwareChooseFun() {
		var materialBare_hardwareIds = "";
		$('#materialBare_hardware').parent().find('.contain').each(function(){
			materialBare_hardwareIds +=  $(this).find('td').attr('id').substring(9) + "|" + $(this).find('td').text() + ","; 
			
		})
		materialBare_hardwareIds = materialBare_hardwareIds.substring(0,materialBare_hardwareIds.length - 1);
		parent.$.modalDialog({
			title : '硬件选择',
			width : 800,
			height : 600,
			href : '${ctx}/materialBare/hardwarePage?materialBare_hardwareIds='+materialBare_hardwareIds,
			buttons : [ {
				text : '确定',
				handler : function() {
					$('#materialBare_hardware').parent().find('.contain').each(function(){
						$(this).remove();
					})
					if(parent.$('#choose_hardware li').length > 0){
						var flag = false;
						parent.$('#choose_hardware li').each(function(){
							 var li = $(this);
							$('#materialBare_hardware').parent().find('.contain').each(function(){
								var id = $(this).find('td').attr('id');
								id = id.substring(9);
								if(id == li.attr('id')){
									flag = true;
								} 
							})
							if(!flag){
								var content = ' <tr class="contain">'+
					            '<td id="hardware_'+ $(this).attr('id') +'">'+ $(this).text() +'</td>'+
					       		'</tr>';
								$('#materialBare_hardware').parent().append(content);
							}
							flag = false;
						})
					}
					parent.$.modalDialog.handler.dialog('close');
				}
			} ] 
			/* buttons:'#bare_HardwareBtns' */
		});
	}
	//外壳
	function bare_ShellChooseFun() {
		var materialBare_shellIds = "";
		$('#materialBare_shell').parent().find('.contain').each(function(){
			materialBare_shellIds +=  $(this).find('td').attr('id').substring(6) + "|" + $(this).find('td').text().trim() + ","; 
			console.log(materialBare_shellIds)
		})
		materialBare_shellIds = materialBare_shellIds.substring(0,materialBare_shellIds.length - 1);
		parent.$.modalDialog({
			title : '外壳选择',
			width : 800,
			height : 600,
			href : '${ctx}/materialBare/shellPage?materialBare_shellIds='+materialBare_shellIds,
			buttons : [ {
				text : '确定',
				handler : function() {
					$('#materialBare_shell').parent().find('.contain').each(function(){
						$(this).remove();
					})
					if(parent.$('#choose_shell li').length > 0){
						var flag = false;
						parent.$('#choose_shell li').each(function(){
							var li = $(this);
							$('#materialBare_shell').parent().find('.contain').each(function(){
								var id = $(this).find('td').attr('id');
								id = id.substring(6);
								if(id == li.attr('id')){
									flag = true;
								}   
							})
							if(!flag){
								var content = ' <tr class="contain">'+
					            '<td id="shell_'+ li.attr('id') +'" align="left">'+ li.text() +'</td>'+
					       		'</tr>';
								$('#materialBare_shell').parent().append(content);
								
							}
							flag = false;
						})
					}
					parent.$.modalDialog.handler.dialog('close');
				}
			} ]
		});
	}
	//屏
	function bare_ScreenChooseFun() {
		var materialBare_screenIds = "";
		$('#materialBare_screen').parent().find('.contain').each(function(){
			materialBare_screenIds +=  $(this).find('td').attr('id').substring(7) + "|" + $(this).find('td').text() + ","; 
			
		})
		materialBare_screenIds = materialBare_screenIds.substring(0,materialBare_screenIds.length - 1);
		parent.$.modalDialog({
			title : '屏选择',
			width : 800,
			height : 600,
			href : '${ctx}/materialBare/screenPage?materialBare_screenIds='+materialBare_screenIds,
			buttons : [ {
				text : '确定',
				handler : function() {
					$('#materialBare_screen').parent().find('.contain').each(function(){
						$(this).remove();
					})
					if(parent.$('#choose_screen li').length > 0){
						var flag = false;
						parent.$('#choose_screen li').each(function(){
							var li = $(this);
							$('#materialBare_screen').parent().find('.contain').each(function(){
								var id = $(this).find('td').attr('id');
								id = id.substring(7);
								if(id == li.attr('id')){
									flag = true;
								}   
							})
							if(!flag){
								var content = ' <tr class="contain">'+
								            '<td id="srceen_'+ li.attr('id') +'">'+ li.text() +'</td>'+
								       		'</tr>';
								$('#materialBare_screen').parent().append(content);
								
							}
							flag = false;
						})
					}
					parent.$.modalDialog.handler.dialog('close');
				}
			} ]
		});
	}
	</script>
</head>
<body>
	<div>
		<table class="grid">
			<tr class="tabletitle">
	            <td colspan="2">机型添加 </td>
	        </tr>
			<tr>
				<td width="20%">机型名称</td>
				<td width="80%">
					<input id="name" type="text" style="width: 30%" placeholder="请输入物料名称" class="table_input easyui-validatebox" data-options="required:true,validType:['isBlank','length[0,64]']"value="${product.name}"></td>
			</tr>
			<tr>
				<td>类型</td>
				<td><select id="productType" name="type" style="width: 140px; height: 29px;" class="easyui-validatebox" data-options="required:true,validType:['isBlank','length[0,64]']"></select></td>
			</tr>
			<tr>
				<td>描述</td>
				<td colspan="3"><textarea class="table_input easyui-validatebox" data-options="validType:['isBlank','length[0,200]']"id="description" maxlength="200"></textarea></td>
			</tr>
		</table>
	    
	    <!-- 裸机包装 -->
	    <!--  <table border="0" cellpadding="0" cellspacing="0" class="bordertable" style="margin:0px;">
	        <tr class="tabletitle">
	            <td colspan="3">裸机包装组： </td>
	        </tr>
	        <tr class="tabletop">
	            <td colspan="3">硬件部件：<span class="choose" onclick="bare_HardwareChooseFun()">[选择物料]</span> </td>
	        </tr>
	        <tr id="materialBare_hardware">
	        </tr>
        </table> -->
        <table border="0" cellpadding="0" cellspacing="0" class="bordertable" style="margin:0px;">
	        <tr class="tabletitle">
	            <td colspan="3">外壳组件：<span class="choose" onclick="bare_ShellChooseFun()">[选择物料]</span> </td>
	        </tr>
	        <tr id="materialBare_shell">
	        </tr>
	    </table>
       <!--  <table border="0" cellpadding="0" cellspacing="0" class="bordertable">
	        <tr class="tabletop">
	            <td colspan="3">屏：<span class="choose" onclick="bare_ScreenChooseFun()">[选择物料]</span> </td>
	        </tr>
	        <tr id="materialBare_screen">
	        </tr>
	    </table>
	    
	    配件信息
	     <table border="0" cellpadding="0" cellspacing="0" class="bordertable">
	        <tr class="tabletitle">
	            <td colspan="3">配件部分：<span class="choose" onclick="fittingChooseFun()">[选择物料]</span>  </td>
	        </tr>
	        <tr id="materialFitting">
	        </tr>
	        
	    </table>
	    
	      包材信息
	     <table border="0" cellpadding="0" cellspacing="0" class="bordertable">
	        <tr class="tabletitle">
	            <td colspan="3">包材部分：<span class="choose" onclick="packingChooseFun()">[选择物料]</span>  </td>
	        </tr>
	        <tr id="materialPacking">
	        </tr>
	    </table> -->
	</div>
	<div style="margin-top: 10px;"  align="center">
		<button id="productEdit_editBtn">&nbsp;修&nbsp;改&nbsp;</button>
	</div>
</body>
</html>