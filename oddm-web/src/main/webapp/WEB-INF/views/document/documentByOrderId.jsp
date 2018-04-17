<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
	 body{margin: 0px;padding: 0px;}
	.table{font-size:1em;color:#333;background-color:#fff;border-collapse:collapse;width:100%;table-layout:fixed;}
    .table tr th{border:1px solid #999;text-align:center;background-color:#CCC;font-size:1em;padding:3px 0;font-weight:400;}
    .table tr:hover{background-color:#0cf}
    .table tr td{border:1px solid #999;text-align:center;font-size:1em;padding:8px 3px;/* cursor:pointer; */}
    .updateDoc,.addDoc,.deleteDoc,.addDocCommon {cursor:pointer;color: #F60;}
    .orderDocDownload {cursor:pointer;color: green;}
    .color_f60 {color: #f60;}
    .color_blue {color: blue;}
    .color_green {color: green;}
</style>
<jsp:include page="../inc.jsp"></jsp:include>
<title>订单信息</title>
<c:if test="${fn:contains(sessionInfo.resourceList, '/document/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/document/add')}">
	<script type="text/javascript">
		$.canAdd = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/document/download')}">
	<script type="text/javascript">
		$.canDownload = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/document/upload')}">
	<script type="text/javascript">
		$.canUpload = true;
	</script>
</c:if>

<!-- 美工文档 -->
<c:if test="${fn:contains(sessionInfo.resourceList, '/document/artDocument')}">
	<script type="text/javascript">
		$.artDocument = true;
	</script>
</c:if>
<!-- 其他个人文档 -->
<c:if test="${fn:contains(sessionInfo.resourceList, '/document/otherPersonDoument')}">
	<script type="text/javascript">
		$.otherPersonDoument = true;
	</script>
</c:if>
<!-- 其他全部文档 -->
<c:if test="${fn:contains(sessionInfo.resourceList, '/document/otherAllDocument')}">
	<script type="text/javascript">
		$.otherAllDocument = true;
	</script>
</c:if>
<script src="${ctx}/jslib/aliyun-oss-sdk-4.4.4.min.js"></script>
<script src="${ctx}/jslib/promise-6.1.0.js"></script>
<script type="text/javascript">
	var userId = "${sessionInfo.id}";
	var orderId = "${orderId}";
	//var sellerId = null;
	var progress = function (p) {
		return function (done) {
		  var bar = parent.$('#uploadProgterssbar');
		  var value = Math.floor(p * 100);
		  console.log(value)
		  bar.progressbar('setValue', value); 
		  done();
		}
	};
	var fileProgress = function (p) {
		return function (done) {
		  var bar = parent.$('#addFileProgterssbar');
		
		  var value = Math.floor(p * 100);
		  console.log(value)
		  bar.progressbar('setValue', value); 
		  done();
		}
	};
	function loadDoc(){
		$.ajax({
			url : '${ctx}/order/getAllDocumentByOrderId?orderId=' + orderId,
			type : "GET",
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			async: false,
			success : function(data) {
				console.log(data)
				if(data != null){
					var orderIdForInput = data[0].id;//选择文件用到判断iframe
					var nameExist = {};
					for(var i in data){
						sellerId = data[i].sellerId;
						var addOperate = "";
						if($.artDocument || $.otherPersonDoument || $.otherAllDocument){
							if ($.canAdd) {
								console.log(data[i].sellerId)
								addOperate = '<span class="addDoc" >添加</span>';
								if($.artDocument){
									addOperate += '&nbsp;|&nbsp;<span class="addDocCommon">引用通用文档</span>'; 
								}
								
							}
						}
						
						var order =  data[i];
						if(i == 0){
							var $orderContent = $('<tr id="order_'+ order.id +'" class="title">'+
									'<td colspan="6" >订单号<span class="color_f60">&nbsp;[当前单]</span>：'+ order.orderNo +'&nbsp;-- &nbsp;机型：<span class="color_f60">'+ order.productFactory +'</span></td>'+
									'<td  >'+ addOperate +'</td>'+
								'</tr>');
						}else{
							var $orderContent = $('<tr id="order_'+ order.id +'" class="title">'+
									'<td colspan="6" >订单号<span class="color_f60">&nbsp;[前&nbsp;&nbsp;'+ i +'&nbsp;&nbsp;单]</span>：'+ order.orderNo +'&nbsp;-- &nbsp;机型：<span class="color_f60">'+ order.productFactory +'</span></td>'+
									'<td >'+ addOperate +'</td>'+
								'</tr>');
						}
						if(orderIdForInput != null){
							$orderContent.data('orderIdForInput',orderIdForInput)
						}
						
						//$orderContent.data('timestamp',order.timestamp)
						$orderContent.data('orderNo',order.orderNo)
						$orderContent.data('productClient',order.productClient)
						$orderContent.data('productFactory',order.productFactory)
						$('#docList').append($orderContent);
						var docs = order.documentDTOs;
						var operate ;
						var uploadtime;
						if(docs.length > 0){
							for(var i in docs){
								var doc = docs[i];
								if(doc.type == 1 ){//美工文档
									var state = '';
									switch(doc.state){
										case 1:
											state = '<span class="color_f60">文档未发布</span>';
											break;
										case 2:
											state = '<span class="color_blue">业务未审核</span>';
											break;
										case 3:
											//审核通过
											if ($.canDownload) {
												state = '<span class="orderDocDownload" filepath="'+ doc.path +'">文档下载</span>';
											}else{
												state = '<span  filepath="'+ doc.path +'">文档审核通过</span>';
											}
											break;
										case 4:
											state = "审核未通过";
											break;
										case 5:
											state = "重新上传成功";
											break;
									}
									
									if($.artDocument){
										if(doc.version == 0){
											//版本为0则说明是没上传
											if ($.canUpload) {
												 operate = '<span class="updateDoc" data-sellerId="'+sellerId+'">上传</span>';
											}
										}else{
											//文档已经上传过了
											if ($.canUpload) {
												operate = '<span class="updateDoc" data-sellerId="'+sellerId+'">更新</span>';
											}
										}
										if($.canDelete){
												//路径是普通文档
												operate = operate + '&nbsp;|&nbsp;<span class="deleteDoc"  data-sellerId="'+sellerId+'" >删除</span>'
										}
									}
									if(operate == undefined){
										operate = "";
									}
									if(doc.uploadtime == undefined){
										uploadtime = "";
									}else{
										uploadtime = doc.uploadtime;
									}
									if(doc.codeMt == undefined){
										codeMt = "";
									}else{
										codeMt = doc.codeMt;
									}
									var documentSort = parseInt(i) + 1;
									var documentName = order.productFactory + doc.nameMt + order.orderNo +"("+ order.productClient +")";
									var docNam = documentName;
									if(doc.path != null && !(doc.path.indexOf('order') > -1)){//包含订单order字串，说明是普通文档
										documentName += '-<span class="color_f60">[通用文档]</span>';
									}
									var documentNameTemp = order.productFactory + doc.nameMt  +"("+ order.productClient +")";
									if(nameExist[documentNameTemp] == null){
										nameExist[documentNameTemp] = 1;
										documentName = documentName + '-<span class="color_f60">[新]</span>';
									}
									var	$docContent	= $('<tr id="docment_'+ doc.id +'">'+
														'<td>'+ documentSort +'</td>'+
														'<td>V'+ doc.version +'</td>'+
														'<td>'+ codeMt +'</td>'+
														'<td>'+ documentName +'</td>'+
														'<td>'+ uploadtime +'</td>'+
														'<td>'+ state +'</td>'+
														'<td>'+ operate +'</td>'+
													'</tr>');
									$docContent.data('orderId',order.id)
									$docContent.data('version',doc.version)
									$docContent.data('orderNo',order.orderNo)
									$docContent.data('productClient',order.productClient)
									$docContent.data('productFactory',order.productFactory)
									$docContent.data('timestamp',order.timestamp)
									$docContent.data('orderIdForInput',orderIdForInput)
									//添加文档名称用于删除缓存
									$docContent.data("documentName",docNam);
									//新增type字段和userid字段
									$docContent.data('type',doc.type)
									if(doc.path != null){
										$docContent.data('path',doc.path)
									}
									
									$('#docList').append($docContent);
								}
								if(doc.type == 2){//其他文档
									var state = '';
									switch(doc.state){
										case 1:
											state = '<span class="color_f60">文档未发布</span>';
											break;
										case 2:
											state = '<span class="color_blue">业务未审核</span>';
											break;
										case 3:
											//审核通过
											if ($.canDownload) {
												state = '<span class="orderDocDownload" filepath="'+ doc.path +'">文档下载</span>';
											}else{
												state = '<span  filepath="'+ doc.path +'">文档审核通过</span>';
											}
											break;
										case 4:
											state = "审核未通过";
											break;
										case 5:
											state = "重新上传成功";
											break;
									}
									
									if($.otherAllDocument){
										if(doc.version == 0){
											//版本为0则说明是没上传
											if ($.canUpload) {
												 operate = '<span class="updateDoc" data-sellerId="'+sellerId+'">上传</span>';
											}
										}else{
											//文档已经上传过了
											if ($.canUpload) {
												operate = '<span class="updateDoc" data-sellerId="'+sellerId+'">更新</span>';
											}
										}
										if($.canDelete){
												//路径是普通文档
												operate = operate + '&nbsp;|&nbsp;<span class="deleteDoc"  data-sellerId="'+sellerId+'">删除</span>'
										}
									}else{
										if($.otherPersonDoument){
											if(doc.userId == userId){
												if(doc.version == 0){
													//版本为0则说明是没上传
													if ($.canUpload) {
														 operate = '<span class="updateDoc" data-sellerId="'+sellerId+'">上传</span>';
													}
												}else{
													//文档已经上传过了
													if ($.canUpload) {
														operate = '<span class="updateDoc" data-sellerId="'+sellerId+'">更新</span>';
													}
												}
												if($.canDelete){
														//路径是普通文档
														operate = operate + '&nbsp;|&nbsp;<span class="deleteDoc"  data-sellerId="'+sellerId+'">删除</span>'
												}
											}
										}
									}
									
									
									if(operate == undefined){
										operate = "";
									}
									if(doc.uploadtime == undefined){
										uploadtime = "";
									}else{
										uploadtime = doc.uploadtime;
									}
									if(doc.codeMt == undefined){
										codeMt = "";
									}else{
										codeMt = doc.codeMt;
									}
									var documentSort = parseInt(i) + 1;
									var documentName = order.productFactory + doc.nameMt + order.orderNo +"("+ order.productClient +")";
									var docNam = documentName;
									if(doc.path != null && !(doc.path.indexOf('order') > -1)){//包含订单order字串，说明是普通文档
										documentName += '-<span class="color_f60">[通用文档]</span>';
									}
									var documentNameTemp = order.productFactory + doc.nameMt  +"("+ order.productClient +")";
									if(nameExist[documentNameTemp] == null){
										nameExist[documentNameTemp] = 1;
										documentName = documentName + '-<span class="color_f60">[新]</span>';
									}
									var	$docContent	= $('<tr id="docment_'+ doc.id +'">'+
														'<td>'+ documentSort +'</td>'+
														'<td>V'+ doc.version +'</td>'+
														'<td>'+ codeMt +'</td>'+
														'<td>'+ documentName +'</td>'+
														'<td>'+ uploadtime +'</td>'+
														'<td>'+ state +'</td>'+
														'<td>'+ operate +'</td>'+
													'</tr>');
									$docContent.data('orderId',order.id)
									$docContent.data('version',doc.version)
									$docContent.data('orderNo',order.orderNo)
									$docContent.data('productClient',order.productClient)
									$docContent.data('productFactory',order.productFactory)
									$docContent.data('timestamp',order.timestamp)
									$docContent.data('orderIdForInput',orderIdForInput)
								//添加文档名称用于删除缓存
									$docContent.data("documentName",docNam);
								
									
									//新增type字段和userid字段
									$docContent.data('type',doc.type)
									
									if(doc.path != null){
										$docContent.data('path',doc.path)
									}
									
									$('#docList').append($docContent);
								}
								
								operate = "";
							}
						}else{
							$('#docList').append('<tr><td colspan="7" >此单暂无相关文档</td></tr>')
						}
					}
				}else{
					console.log('数据找不到');
					$('#docList').append('<tr><td colspan="7" >没有文档资料！<span>点击添加</td></tr>')
				}
			}
		})
	}
	$(function() {
		loadDoc();
		
		$('#tablelist').off('click','.addDoc').on('click','.addDoc',function(){
			var thisTr = $(this).parent().parent();
			var orderId = thisTr.attr('id').substring(6);
			var orderNo = thisTr.data("orderNo");
			var orderIdForInput = thisTr.data("orderIdForInput");
			var productClient = encodeURI(thisTr.data('productClient'));
			var productFactory = thisTr.data('productFactory');
			//业务员id
			
			parent.$.modalDialog({
				title : '添加',
				width : 750,
				height : 500,
				href : '${ctx}/document/addPage?orderId='+orderId+"&orderIdForInput="+orderIdForInput,
				buttons : [ {
					text : '添加文档',
					handler : function() {
						var storeAs;
						var client;
						var OSSURL = '';
						var documentPath = "";
						var param = {
								"fileType":"document",
								"fileDetail":"order",
								//"year":year,
								"orderNo":orderNo
						};
						/* console.log(parent.$('#materialName').text()) */
						if(parent.$('#materialName').text().trim() == ""){
							parent.$.messager.alert('提示', '物料不能为空！', 'info');
							return;
						}
						/* if(parent.$('#materialCode').val().trim() == ""){
							parent.$.messager.alert('提示', '物料编码不能为空！', 'info');
							return;
						} */
						var typeId = 1;
						if(parent.$('#materialName').data('typeId') != 3){//3是包材
							typeId = 2;
						}else{
							typeId = 1;
						}
						
						if(document.getElementById('documentFile').files[0] == undefined){
							$.ajax({
								url : '${ctx}/document/add?orderId='+orderId+"&nameMt="+ parent.$('#materialName').data('materialName')+"&codeMt="+ parent.$('#materialCode').val().trim()+"&type="+typeId,
								type : "GET",
								dataType : "json",
								contentType : "application/json; charset=utf-8",
								async: false,
								success : function(data) {
									if(data.success){
										parent.$.messager.alert('提示', '文档添加成功！！', 'info');
										parent.$.modalDialog.handler.dialog('close');
										$('#docListFirst').nextAll().remove();
										
										loadDoc();
									}else{
										parent.$.messager.alert('提示', data.msg, 'info');
										parent.$.modalDialog.handler.dialog('close');
									}
								}
							})
						}else{
							//获取token
							var file = document.getElementById('documentFile').files[0];
							parent.$('#documentFilename').text(file.name);
							var materialName = parent.$('#materialName').text().trim();
							//var chooseFileName = materialName.substring(0,materialName.length-1)+"1.rar".trim();
							console.log(materialName+"...."+file.name.trim())
							if(materialName != file.name.trim() ){
								  parent.parent.$.messager.alert('提示', '文档不匹配！');
								  parent.clearInput();
								  parent.$('#documentFilename').text('');
							}
							var typeId = 1;
							if(parent.$('#materialName').data('typeId') != 3 ){//3是包材
								typeId = 2;
							}else{
								typeId = 1;
							}
							
							$.ajax({
								url : '${pageContext.request.contextPath}/token/update?fileType=document&fileDetail=order&orderNo='+orderNo,
								type : "GET",
								dataType : "json",
								contentType : "application/json; charset=utf-8",
								success : function(data) {
									console.log(data)
									 client = new OSS.Wrapper({
										region: data.region,
										accessKeyId: data.accessKeyId,
										accessKeySecret: data.accessKeySecret,
										stsToken : data.securityToken,
										bucket: data.bucket
								    });
									//storeAs = data.path+"Ammmmmmmmmm(4344)V1.txt";
									parent.$('#addFileProgterssbar').css('display','block');
									var file = document.getElementById('documentFile').files[0];
									var fileName = file.name;
									var materialName = parent.$('#materialName').data('materialName');
									var documentName = productFactory + materialName + orderNo +"("+ productClient +")"+"V1."+fileName.substring(fileName.lastIndexOf('.') + 1);
									documentPath = data.path + documentName.replace(new RegExp(' ',"gm"),"_").replace(new RegExp('\\+',"gm"),"_");
									console.log(file.name + ' => ' + documentPath);
								    client.multipartUpload(documentPath, file,{
								        progress: fileProgress
								    }).then(function (result) {
								         OSSURL = result.url;
								         var param = {
								        		 orderId : orderId,
								        		 path : documentPath,
								        		 nameMt : parent.$('#materialName').data('materialName'),
								        		 codeMt : parent.$('#materialCode').val(),
								        		 type : typeId,
								         }
								         console.log(JSON.stringify(param))
								         $.ajax({
											/* url : '${ctx}/document/addFile?orderId='+orderId+"&path="+documentPath +"&nameMt="+ parent.$('#materialName').data('materialName')+"&codeMt="+ parent.$('#materialCode').val()+"&type="+typeId, */
											url : '${ctx}/document/addFile',
											data:JSON.stringify(param),
											type : "POST",
											dataType : "json",
											contentType : "application/json; charset=utf-8",
											async: false,
											success : function(data) {
												if(data.success){
													parent.$.messager.alert('提示', '文档上传成功！！', 'info');
													parent.$.modalDialog.handler.dialog('close');
													$('#docListFirst').nextAll().remove();
													loadDoc();
													
												}else{
													parent.$.messager.alert('提示', data.msg, 'info');
													parent.$.modalDialog.handler.dialog('close');
												}
											}
										}) 
								    }).catch(function (err) {
								        console.log(err);
								        parent.$.messager.alert('提示', '文档上传出错！！', 'info');
								    });
								}
							})
						}
						
					}
				} ]
			});
		});
		
		$('#tablelist').off('click','.addDocCommon').on('click','.addDocCommon',function(){
			var thisTr = $(this).parent().parent();
			var orderId = thisTr.attr('id').substring(6);
			var year = new Date(thisTr.data("timestamp")).getFullYear();
			var orderNo = thisTr.data("orderNo");
			
			var productClient = thisTr.data('productClient');
			var productFactory = thisTr.data('productFactory');
			parent.$.modalDialog({
				title : '添加',
				width : 750,
				height : 500,
				href : '${ctx}/document/addCommonPage?orderId='+orderId,
				buttons : [ {
					text : '添加文档',
					handler : function() {
						//拿到文档路径、物料名
						if(parent.$('#materialName').text().trim() == ""){
							parent.$.messager.alert('提示', '物料不能为空！', 'info');
							return;
						}
						/* if(parent.$('#materialCode').val().trim() == ""){
							parent.$.messager.alert('提示', '物料编码不能为空！', 'info');
							return;
						} */
						var nameMt = parent.$('#materialName').text().trim();
						var codeMt = parent.$('#materialCode').val().trim();
						var version = parent.$('#materialName').data('version');
						var path =  parent.$('#materialName').data('path');
						$('#docListFirst').nextAll().remove();
						loadDoc();
						//发送请求替换复制文档路径和物料名
						$.ajax({
							url : '${ctx}/document/addCommon?orderId='+orderId+"&nameMt="+ nameMt +"&codeMt="+ codeMt+"&version="+ version+"&path="+ path,
							type : "GET",
							dataType : "json",
							contentType : "application/json; charset=utf-8",
							async: false,
							success : function(data) {
								if(data.success){
									parent.$.messager.alert('提示', '文档添加成功！！', 'info');
									parent.$.modalDialog.handler.dialog('close');
									$('#docListFirst').nextAll().remove();
									loadDoc();
								}else{
									parent.$.messager.alert('提示', data.msg, 'info');
									parent.$.modalDialog.handler.dialog('close');
								}
							}
						})
					}
				} ]
			});
		});
		
		
		$('#tablelist').off('click','.updateDoc').on('click','.updateDoc',function(){
			var thisTr = $(this).parent().parent();
			var docId = thisTr.attr('id').substring(8);
			var orderId = thisTr.data('orderId');
			//var year = new Date(thisTr.data("timestamp")).getFullYear();
			var version = parseInt(thisTr.data('version')) + 1;
			var orderNo = thisTr.data('orderNo');
		//	console.log(thisTr)
			var orderIdForInput = thisTr.data('orderIdForInput');
			console.log(orderIdForInput+"......")
			var productClient = thisTr.data('productClient');
			var productFactory = thisTr.data('productFactory');
			var documentName = productFactory+"@@"+orderNo+"("+productClient+")V"+version;
			var oldDocumentPath = thisTr.data('path');
		//	console.log(oldDocumentPath)
	        var isNew  =thisTr.data('isNew');
		
		    var sellerID = $(this).attr("data-sellerId");
			//新增type字段和userid字段
			var typeId = thisTr.data('type')
			parent.$.modalDialog({
				title : '更新文档',
				width : 350,
				height : 180,
				href : '${ctx}/document/editPage?id='+docId+"&documentName="+encodeURIComponent(documentName)+"&orderId="+orderId+"&orderIdForInput="+orderIdForInput,
				buttons : [ {
					text : '更新',
					handler : function() {
						var fileName = parent.$("#documentName").text();
						if(document.getElementById('documentFileUpdate').files[0] == undefined){
							parent.$.messager.alert('提示', '请先选择文档！！', 'info');
						}else{
							$.ajax({
								url : '${pageContext.request.contextPath}/token/update?fileType=document&fileDetail=order'+'&orderNo='+orderNo,
								type : "GET",
								dataType : "json",
								async: false,
								contentType : "application/json; charset=utf-8",
								success : function(data) {
								//	console.log(data)
									 client = new OSS.Wrapper({
										region: data.region,
										accessKeyId: data.accessKeyId,
										accessKeySecret: data.accessKeySecret,
										stsToken : data.securityToken,
										bucket: data.bucket
								    });
									var flag = false;
									parent.$('#uploadProgterssbar').css('display','block');
									var file = document.getElementById('documentFileUpdate').files[0];
								  
									/* var documentName = parent.$('#orderDocName').text()+"."+fileName.substring(fileName.lastIndexOf('.') + 1); */
									var documentName = parent.$('#orderDocName').text();
									console.log(documentName)
									documentPath = data.path + (documentName).replace(new RegExp(' ',"gm"),"_").replace(new RegExp('\\+',"gm"),"_");
									console.log(file.name + ' => ' + documentPath);
									//文件上传
								    client.multipartUpload(documentPath, file,{
								        progress: progress
								    }).then(function (result) {
								        OSSURL = result.url;
								        var param = {
								        		 version : version,
								        		 id : docId,
								        		 path : (documentPath).replace(new RegExp(' ',"gm"),"_").replace(new RegExp('\\+',"gm"),"_"),
								        		 orderId : orderId,
								        		 type : typeId,
								         }
								        $.ajax({
											/* url : '${ctx}/document/edit?version='+version+'&id='+docId+"&path="+(documentPath).replace(new RegExp(' ',"gm"),"_").replace(new RegExp('\\+',"gm"),"_")+"&orderId="+orderId+"&type="+typeId, */
											url : '${ctx}/document/edit',
											data : JSON.stringify(param),
											dataType : "json",
											contentType : "application/json; charset=utf-8",
											async: false,
											success : function(data) {
												if(data.success){
													$('#docListFirst').nextAll().remove();
													loadDoc();
													//删除之前的文档(如果是通用，则不能删除，是订单文档则删除)、
													if(oldDocumentPath != undefined && oldDocumentPath.indexOf('order') > -1){//包含订单order字串，说明是普通文档
														$.ajax({
															url : '${pageContext.request.contextPath}/token/update?fileType=document&fileDetail=order'+'&orderNo='+orderNo,
															type : "GET",
															dataType : "json",
															contentType : "application/json; charset=utf-8",
															success : function(data) {
																console.log(data)
																 client = new OSS.Wrapper({
																	region: data.region,
																	accessKeyId: data.accessKeyId,
																	accessKeySecret: data.accessKeySecret,
																	stsToken : data.securityToken,
																	bucket: data.bucket
															    });
																console.log(' 文档删除=> ' + oldDocumentPath)
															    client.delete(oldDocumentPath).then(function (result) {
															    	console.log(".deleteDoc....删除成功");
															    /* 	//删除成功后，删除文档审核提示缓存
															    	var selectId = thisTr.attr('id');
																	var fn = $("#"+selectId).data("documentName");
																	console.log(fn);
																	$.post("${ctx}/docUpload/removeDocCache",{"sellerId":sellerId,"fileName":fn}, function(){},'json') */
															    	
															    }).catch(function (err) {
															        console.log(err);
															    });
																
															}
														})
													} 
													parent.$.messager.alert('提示', '文档上传成功！！', 'info');
													parent.$.modalDialog.handler.dialog('close');
													//文档上传成功后添加通知提醒
													console.log('添加缓存'+fileName)
													if(sellerID != null && sellerID != "undefined"){
														$.ajax({
															url:"${ctx}/docUpload/uploadDocTips",
															data:{"sellerId":sellerID, "msg":fileName},
															dataType:"json",
															type:"post",
															success:function(data){
																
															}
														})
													}
													
													
												}else{
													parent.$.messager.alert('提示', data.msg, 'info');
													parent.$.modalDialog.handler.dialog('close');
												}
											}
										})
										
								    }).catch(function (err) {
								        console.log(err);
								        parent.$.messager.alert('提示', '文档上传出错！！', 'info');
								    });
								}
							})
						}
						
						
					}
				} ]
			});
		});
		$('#tablelist').off('click','.deleteDoc').on('click','.deleteDoc',function(){
			var docId = $(this).parent().parent().attr('id').substring(8);
			var thisTr = $(this).parent().parent();
		//	var year = new Date(thisTr.data("timestamp")).getFullYear();
			var orderNo = thisTr.data("orderNo");
			var oldDocumentPath = thisTr.data('path');
			var version = thisTr.data('version')
			
			//
			 var selectId = $(this).parent().parent().attr('id');
			 var fn = $("#"+selectId).data("documentName")
			var sellerId = $(this).attr("data-sellerId");
			parent.$.messager.confirm('询问', '是否要删除该文档',function(b){
				if (b) {
					
					if(version == '0'){
						$.ajax({
				         	type: 'POST',
				             url: "${ctx}/document/delete?id="+docId ,
				             dataType: "json",
				             contentType: "application/json; charset=utf-8",
				             success: function(data){
				            	 if(data.success){
				            		 parent.$.messager.alert('提示', '删除成功');
				            		 $('#docListFirst').nextAll().remove();
										loadDoc();
										//删除成功后，删除文档审核提示缓存
										/* if(sellerId != null  && sellerId != 'undefined') {
											$.post("${ctx}/docUpload/removeDocCache",{sellerId:sellerId,fileName:fn}, function(){})
										} */
				            	 }else{
				            		 parent.$.messager.alert('提示', '删除失败');
				            	 }
				             }
				        })
					}else{
						//获取文档的版本（为0则说明OSS上没有对应文档），判断OSS上是否有文档。
						if(oldDocumentPath != undefined && oldDocumentPath.indexOf('order') > -1){//包含订单order字串，说明是普通文档
							$.ajax({
								url : '${pageContext.request.contextPath}/token/update?fileType=document&fileDetail=order'+'&orderNo='+orderNo,
								type : "GET",
								dataType : "json",
								contentType : "application/json; charset=utf-8",
								success : function(data) {
									console.log(data)
									 client = new OSS.Wrapper({
										region: data.region,
										accessKeyId: data.accessKeyId,
										accessKeySecret: data.accessKeySecret,
										stsToken : data.securityToken,
										bucket: data.bucket
								    });
									/* documentPath = data.path;
									console.log(' => ' + documentPath); */
									console.log(' 文档删除=> ' + oldDocumentPath)
								    client.delete(oldDocumentPath).then(function (result) {
								    	console.log(".deleteDoc....删除成功")
								    	//删除成功后，删除文档审核提示缓存
										//删除成功后，删除文档审核提示缓存
									/* 	if(sellerId != null  && sellerId != 'undefined') {
											$.post("${ctx}/docUpload/removeDocCache",{"sellerId":sellerId,"fileName":fn}, function(){},'json')
										} */
								    }).catch(function (err) {
								        console.log(err);
								      //  parent.$.messager.alert('提示', '文档上传出错！！', 'info');
								    });
								}
							})
							$.ajax({
					         	type: 'POST',
					             url: "${ctx}/document/delete?id="+docId ,
					             dataType: "json",
					             contentType: "application/json; charset=utf-8",
					             success: function(data){
					            	 if(data.success){
					            		 parent.$.messager.alert('提示', '删除成功');
					            		 $('#docListFirst').nextAll().remove();
											loadDoc();
											//删除成功后，删除文档审核提示缓存
										/* if(sellerId != null  && sellerId != 'undefined') {
											$.post("${ctx}/docUpload/removeDocCache",{"sellerId":sellerId,"fileName":fn}, function(){},'json')
										} */
					            	 }else{
					            		 parent.$.messager.alert('提示', '删除失败');
					            	 }
					            	 
					             }
					        })
						}else{
							//通用文档删除数据库
							$.ajax({
						         	type: 'POST',
						             url: "${ctx}/document/delete?id="+docId ,
						             dataType: "json",
						             contentType: "application/json; charset=utf-8",
						             success: function(data){
						            	 if(data.success){
						            		 parent.$.messager.alert('提示', '删除成功');
						            		 $('#docListFirst').nextAll().remove();
												loadDoc();
											//删除成功后，删除文档审核提示缓存
											/* if(sellerId != null  && sellerId != 'undefined') {
												$.post("${ctx}/docUpload/removeDocCache",{"sellerId":sellerId,"fileName":fn}, function(){},'json')
											} */
						            	 }else{
						            		 parent.$.messager.alert('提示', '删除失败');
						            	 }
						             }
						        })
						}
					}
					
				}else{
				//	console.log(fn);
				}
			});
		});
		
		$('#tablelist').off('click','.orderDocDownload').on('click','.orderDocDownload',function(){
			var client;
			var filepath = $(this).attr('filepath');
			var param = {filePath:filepath}
			$.ajax({
				url : '${pageContext.request.contextPath}/token/download',
				type : "POST",
				data : JSON.stringify(param),
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				success : function(data) {
					if(data.status !='传参解析异常'){
						 client = new OSS.Wrapper({
								region: data.region,
								accessKeyId: data.accessKeyId,
								accessKeySecret: data.accessKeySecret,
								stsToken : data.securityToken,
								bucket: data.bucket
						    });
							console.log(data)
						    var objectKey = data.path;
							
						    var saveAs = filepath.split('/')[filepath.split("/").length-1];
						    console.log(objectKey + ' => ' + saveAs);
						    var result = client.signatureUrl(objectKey, {
						      expires: 3600,
						      response: {
						        'content-disposition': 'attachment; filename="' + (saveAs) + '"'
						      }
						    });
						    download_file(result)
						    
						   /*  window.location = result; */
						    console.log(result);
						    /* try{
						    	 
						    }catch(function (err) {
						        console.log(err);
						        parent.$.messager.alert('提示', '文档上传出错！！', 'info');
						    }); */
						   
					}else{
						 parent.$.messager.alert('提示', '文档下载异常，请联系管理员！');
					}
					 
				}
			})
		})
	});

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
     	         parent.parent.$.messager.alert('提示', '文档不存在！');
     	   }, false);
         }  
         
     	 download_file.iframe.src = url;  
         download_file.iframe.style.display = "none";  
     }  
	  
	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
	function handleFileName(){
		var file = document.getElementById('documentFile').files[0];
		parent.$('#documentFilename').text(file.name);
		if(parent.$('#materialName').text() == ''){
			parent.parent.$.messager.alert('提示', '请先选则物料名！');
			parent.clearInput();
			parent.$('#documentFilename').text('');
			return false;
		}
		var materialName = parent.$('#materialName').text().trim();
		console.log(materialName+"...."+file.name.trim())
		if(materialName != file.name.trim()){
			  parent.parent.$.messager.alert('提示', '文档不匹配！');
			  parent.clearInput();
			  parent.$('#documentFilename').text('');
		}
    }
	
	function handleFileNameUpdate(){
		var file = document.getElementById('documentFileUpdate').files[0];
		parent.$('#documentFilename').text(file.name);
		var materialName = parent.$('#orderDocName').text().trim();
		if(materialName != file.name){
			  parent.parent.$.messager.alert('提示', '文档不匹配！');
			  parent.clearInput();
			  parent.$('#documentFilename').text('');
		}
    }
</script>
</head>
<body style="margin: 0px;padding: 0px;">

	<div id="tablelist" style="padding: 10px;overflow: auto;">
		<table border="0" cellspacing="0" cellpadding="0" class="table" id="docList">
			<tr id="docListFirst">
				<th width="3%">NO.</th>
				<th width="8%">版本</th>
				<th width="10%">物料编码</th>
				<th width="35%">文档名</th>
				<th width="20%">上传时间</th>
				<th width="12%">文档状态</th>
				<th width="12%">操作</th>
			</tr>
		</table>
	</div>
	<div  style="display:none"><input type="file" id="documentFile" onchange="handleFileName();" accept=".rar"><input type="file" id="documentFileUpdate" onchange="handleFileNameUpdate();" accept=".rar"></div>
	
</body>
</html>