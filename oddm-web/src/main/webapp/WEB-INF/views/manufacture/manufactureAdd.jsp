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
	.textareacss{width:100%;border:1px solid #CCC;text-align: left;font-size:12px;color:#999;border-radius: 4px;}
	.textareacss2{width:100%;background:none;border: 0px}
	.choose{color: #06c;cursor: pointer;}
	.color_f60 {color: #f60;}
	.color069 {color :#069}
	.inputs {color :#069}
	.text_left{text-align: left;}
	.tip{height: 16px;width: 16px;display: inline-block;padding: 0px 4px;cursor: pointer;}
	.btn-default {border: 1px solid #DDDDDD;background: white;padding: 10px 18px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #333;}
	.btn-default:hover {border: 1px solid #DDDDDD;background: #04B5F9;padding: 10px 18px;border-radius: 8px;cursor: pointer;font-size: 1.1em;color: #FFF;}
</style>
<jsp:include page="../inc.jsp"></jsp:include>

<script type="text/javascript">
	var packing_count = 0;
	$(function() {
		
		//加载包材标题
		$.ajax({
			url:"${ctx}/manufacturePackageTitle/listAll",
			type:"GET",
			dataType: "json",
            contentType: "application/json; charset=utf-8", 
            success: function(data){
            	if(data != null){
            		for(var i in data){
            			var $content = $('<table border="0" cellpadding="0" cellspacing="0" class="bordertable" id="manufacturePackage_'+ data[i].id +'">'+
            					 			'<tr class="tabletop" >'+
											'<th style="border-right: 0px" colspan="2"><span >'+ data[i].name +'</span></th>'+
											'<th align="right" style="border-left: 0px">'+
												'<span onclick="addManufacturePackingFun('+ data[i].id +');" class="icon-add tip"  title="添加"></span>'+
											'</th>'+
										'</tr></table>'); 
            			$content.data('id',data[i].id)
            			$('#manufacturePackings').append($content);
            			
            		}
            	}
            }
		})
		
		
		
	});
	var shell_count = 0;
	function addManufactureShellFun(){
		var content = '<tr class="manufactureShell">'+
				'<td style="padding:3px"><textarea class="textareacss shell_name" id="" rows="5" ></textarea></td>'+
				'<td style="padding:3px"><input class=" " type="file" id="shellInput_'+shell_count+'"><div><img id="shellImgPr_'+shell_count+'" /></div></td>'+
				'<td style="padding:3px"><textarea class="textareacss shell_color" rows="5" ></textarea></td>'+
				'<td style="padding:3px"><textarea class="textareacss shell_craft" rows="5" ></textarea></td>'+
				'<td style="padding:3px"><textarea class="textareacss shell_silk" rows="5" ></textarea></td>'+
				'<td style="padding:3px"><textarea class="textareacss shell_description" rows="5" ></textarea></td>'+
				'<td style="padding: 0px">'+
					'<span onclick="delManufactureShellFun(this);" class="icon-del tip"  title="删除"></span>'+
				'</td>'+
			'</tr>';
		$('#manufactureShells').append(content);
		
		$("#shellInput_"+shell_count).uploadPreview({ Img: "shellImgPr_"+shell_count, Width: '100%', Height: 'auto' });
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
				$(obj).parent().parent().remove();
			}
		});
		
	}
	function addManufactureFittingFun(){
		var content = '<tr class="manufactureFitting">'+
				'<td style="padding:3px"><textarea class="textareacss fitting_name" rows="5" ></textarea></td>'+
				'<td style="padding:3px"><input class=" " type="file"></td>'+
				'<td style="padding:3px"><textarea class="textareacss fitting_specification" rows="5" ></textarea></td>'+
				'<td style="padding:3px"><textarea class="textareacss fitting_packBag" rows="5" ></textarea></td>'+
				'<td style="padding:3px"><textarea class="textareacss fitting_lable" rows="5" ></textarea></td>'+
				'<td style="padding:3px"><textarea class="textareacss fitting_description" rows="5" ></textarea></td>'+
				'<td style="padding: 0px">'+
					'<span onclick="delManufactureFittingFun(this);" class="icon-del tip"  title="删除"></span>'+
				'</td>'+
			'</tr>';
		$('#manufactureFittings').append(content);
		//校验
		$('.shell_name').validatebox({    
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
				$(obj).parent().parent().remove();
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
		$content.data('titleId',titleId)
		var content = '<tr align="left" class="">'+
						'<td width="10%"><span>图片：<span></td>'+
						'<td style="padding:3px"><input class=" " type="file"></td>'+
					'</tr>';
		$("#manufacturePackage_"+titleId).append(content);
		var content = '<tr align="left" class="">'+
							'<td width="10%"><span>描述：<span></td>'+
							'<td style="padding:3px" ><textarea class="textareacss packing_description" rows="3" ></textarea></td>'+
						'</tr>';
		$("#manufacturePackage_"+titleId).append(content);
		
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
				$(obj).parent().parent().next().next().remove();
				$(obj).parent().parent().next().remove();
				$(obj).parent().parent().remove();
			}
		});
		
	}
	
	function manufactureAdd(){
		var isValid = $('#manufactureAddForm').form('validate');
		console.log("manufactureAdd 验证"+isValid)
		if(!isValid){
			return;
		}
		//基本信息
		var orderNo = $("#orderNo").html();
		var clientName = $("#clientName").html();
		
		var dateIssue = $('#dateIssue').datebox('getValue');
		var dateShipment = $('#dateShipment').datebox('getValue');
		var version = $('#version').val().trim();
		var drafter = $('#drafter').val().trim();
		var auditor = $('#auditor').val().trim();
		var approver = $('#approver').val().trim();
		var remark = $('#remark').val().trim();
		var notice = $('#notice').val().trim();
		
		//软件
		var versionApp = $('#versionApp').val().trim();
		var versionCore = $('#versionCore').val().trim();
		var versionMap = $('#versionMap').val().trim();
		var preFile = $('#preFile').val().trim();
		var uuid = $('#uuid').val().trim();
		var versionFirmware = $('#versionFirmware').val().trim();
		var description = $('#description').val().trim();
		var otherRequire = $('#otherRequire').val().trim();
		var os_notice = $('#os_notice').val().trim();
		var manufactureOsCommand = {
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
			var color = tdValue.eq(2).find("textarea").val().trim();
			var craft = tdValue.eq(3).find("textarea").val().trim();
			var silk = tdValue.eq(4).find("textarea").val().trim();
			var description = tdValue.eq(5).find("textarea").val().trim();
			
			var manufactureShell = {
				name : name,
				picPath : picPath,
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
			var specification = tdValue.eq(2).find("textarea").val().trim();
			var packBag = tdValue.eq(3).find("textarea").val().trim();
			var label = tdValue.eq(4).find("textarea").val().trim();
			var description = tdValue.eq(5).find("textarea").val().trim();
			
			var manufactureFitting = {
				name : name,
				picPath : picPath,
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
			var tdValue = $(this).children();
			var pictTitle = tdValue.eq(1).find("input").val().trim();
			var picPath = "";
			var specification = $(this).next().next().children().eq(1).find("textarea").val().trim();
			var titleId = $(this).data('titleId');
			console.log(pictTitle+"::"+specification+"::"+titleId)
			var manufacturePacking = {
				pictTitle : pictTitle,
				picPath : picPath,
				description : description,
				titleId : titleId
			}
			manufacturePackings.push(manufacturePacking);
		})
		
		
		var param = {
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
				
				manufactureOsCommand : manufactureOsCommand,
				manufactureShellCommands : manufactureShells,
				manufactureFittingCommands : manufactureFittings,
				manufacturePackageCommands : manufacturePackings
				
		}
		 progressLoad();
		 $.ajax({
        	type: 'POST',
            url: "${ctx}/manufacture/add" ,
            data: JSON.stringify(param) ,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function(data){
           	 if(data.success){
           		 parent.$.messager.alert('提示', '添加成功');
           		 var parent_tabs = parent.$('#index_tabs');
           		 /* var index = parent_tabs.tabs('getTabIndex', parent.$('#index_tabs').tabs('getSelected'));
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
				); */
           	 }else{
           		 parent.$.messager.alert('提示', '添加失败');
           	 }
            }
       }) 
       progressClose(); 
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
	                if (!RegExp("\.(" + opts.ImgType.join("|") + ")$", "i").test(this.value.toLowerCase())) {
	                    alert("选择文件错误,图片类型必须是" + opts.ImgType.join("，") + "中的一种");
	                    this.value = "";
	                    return false
	                }
	                var isIE = /msie/.test(navigator.userAgent.toLowerCase()); 
	                if (isIE) {
	                	//alert("IE")
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
	                    console.log(55555)
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
		                        if(height > 300){
				                	$("#" + opts.Img).css({
			                            'max-height': '300px'
			                        });
				                }
				                if(width > 300){
				                	$("#" + opts.Img).css({
			                            'max-width': '300px'
			                        });
				                } 
	                     	};
	                      	image.src= data;
		                }; 
		                reader.readAsDataURL(fileData);
	                }
	                opts.Callback()
	            }
	        })
	    }
	});
		 
</script>
<div>
<form id="manufactureAddForm" >
	<!-- 基础信息 -->
	<table border="0" cellpadding="0" cellspacing="0" class="ordertable">
        <tr class="tabletitle">
            <th colspan="8">一、基础信息：</th>
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
            <td class="righttd" colspan="3"><textarea class="textareacss easyui-validatebox" data-options="validType:['isBlank_','length[0,100]']" id="remark"></textarea></td>
        </tr>
    </table>
    <!-- 软件 -->
    <table border="0" cellpadding="0" cellspacing="0" class="ordertable">
        <tr class="tabletitle">
            <th colspan="8">二、软件信息： </th>
        </tr>
        <tr>            
        	<td class="lefttd">应用版本：</td>
            <td class="righttd"><input class="inputs easyui-validatebox" data-options="validType:['isBlank_','length[0,64]']"  id="versionApp"> </td>
            <td class="lefttd">内核版本：
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
            <td class="righttd" colspan="3"><textarea class="textareacss  easyui-validatebox" data-options="validType:['isBlank_','length[0,500]']"  id="description"></textarea> </td>
        </tr>
        <tr>
            <td class="lefttd">其他软件要求：</td>
            <td class="righttd" colspan="3"><textarea class="textareacss  easyui-validatebox" data-options="validType:['isBlank_','length[0,500]']"  id="otherRequire"></textarea></td>
            <td class="lefttd">注意事项：</td>
            <td class="righttd" colspan="3"><textarea  class="textareacss  easyui-validatebox" data-options="validType:['isBlank_','length[0,500]']" id="os_notice"></textarea></td>
        </tr>
    </table>
    
    <!-- 机身外壳 -->
     <table border="0" cellpadding="0" cellspacing="0" class="bordertable" id="manufactureShells">
        <tr class="tabletitle">
            <th colspan="6" style="border-right: 0px">三、机身外壳：</th>
            <td align="right" style="border-left: 0px;padding: 0px">
				<span onclick="addManufactureShellFun();" class="icon-add tip"  title="添加"></span>
			</td>
        </tr>
		<tr   class="tabletop">
            <td width="10%">名称</td>
            <td width="30%">效果图</td>
            <td width="10%">颜色</td>
            <td width="10%">喷漆工艺</td>
            <td width="10%">丝印</td>
            <td width="30%">备注</td>
            <td style="width: 0px;padding: 0px"></td>
        </tr>
    </table>
     
    
    <!-- 配件信息-->
     <table border="0" cellpadding="0" cellspacing="0" class="bordertable" id="manufactureFittings" >
        <tr class="tabletitle">
            <th colspan="6" style="border-right: 0px">四、订单配件： </th>
            <td align="right" style="border-left: 0px;padding: 0px">
				<span onclick="addManufactureFittingFun();" class="icon-add tip"  title="添加"></span>
			</td>
        </tr>
        <tr  class="tabletop">
            <td width="10%">名称</td>
            <td width="30%">图片</td>
            <td width="20%">型号/规格</td>
            <td width="10%">包装袋(有/无)</td>
            <td width="10%">标贴要求</td>
            <td width="20%">备注</td>
            <td style="width: 0px;padding: 0px"></td>
        </tr>
        
    </table>
    
      <!-- 包材信息-->
     <table border="0" cellpadding="0" cellspacing="0" class="bordertable" id="manufacturePackings">
        <tr class="tabletitle" >
            <th colspan="4" style="border-left: 0px;">五、订单包材及包装(请将包材的所有因素描述清晰) </th>
        </tr>
    </table>
      <!-- 其他注意事项-->
     <table border="0" cellpadding="0" cellspacing="0" class="bordertable">
        <tr class="tabletitle">
            <th colspan="3">六、其他注意事项： </th>
        </tr>
        <tr>
        	<td colspan="3"><textarea  id="notice" class="textareacss2 easyui-validatebox" data-options="validType:['isBlank_','length[0,500]']" ></textarea></td>
        </tr>
    </table>
    </form>
</div>
<div align="center">
	<button id="manufactureAddBtn" class='btn-default' onclick="manufactureAdd();">添加</button> 
</div>