<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<style type="text/css">
	.datagrid-view .datagrid-body {overflow-y: auto;overflow-x: hidden;}
	.color_f60{color: #F60}
	.color_green{color: green;}
	.autoInput{width: 95%}
	.datagrid-cell {   /* easyui不会自动识别英文字符串自动换行，nowrap: false不生效 */
		white-space: pre-wrap;
		word-wrap: break-word;
		word-break:break-all;
		line-height: 18px;
	}
	input {border:1px solid #CCC;font-size:12px;height:19px;border-radius: 4px;}
</style>
<script src="${ctx}/jslib/aliyun-oss-sdk-4.4.4.min.js"></script>
<script src="${ctx}/jslib/promise-6.1.0.js"></script>
<meta http-equiv="X-UA-Compatible" content="edge" />
<c:if test="${fn:contains(sessionInfo.resourceList, '/documentCommon/add')}">
	<script type="text/javascript">
		$.canAdd = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/documentCommon/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/documentCommon/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/documentCommon/download')}">
	<script type="text/javascript">
		$.canDownload = true;
	</script>
</c:if>

<title>订单信息</title>
	<script type="text/javascript">
	var dataGrid;
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
	$(function() {
		var myview = $.extend({},$.fn.datagrid.defaults.view,{
		    onAfterRender:function(target){
		        $.fn.datagrid.defaults.view.onAfterRender.call(this,target);
		        var opts = $(target).datagrid('options');
		        var vc = $(target).datagrid('getPanel').children('div.datagrid-view');
		        vc.children('div.datagrid-empty').remove();
		        if (!$(target).datagrid('getRows').length){
		            var d = $('<div class="datagrid-empty"></div>').html(opts.emptyMsg || 'no records').appendTo(vc);
		            d.css({
		                position:'absolute',
		                left:0,
		                top:50,
		                width:'100%',
		                textAlign:'center'
		            });
		        }
		    }
	});
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}/documentCommon/dataGrid',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'uploadtime',
			sortOrder : 'desc',
			pageSize : 50,
			nowrap:false,
			pageList : [ 10, 20, 30, 40, 50, 100, 200 ],
			view: myview,
            emptyMsg: '没有找到相关数据',
			onLoadSuccess:function(data){ 
				
            },
            frozenColumns : [ [ {
				width : '10%',
				title : '序号',
				field : 'id',
				hidden:true
			}, {
				width : '10%',
				title : '版本',
				field : 'version',
				formatter:function(value,row,index){
                    return "V"+row.version;
                }
			},  {
				width : '30%',
				title : '物料名',
				field : 'nameMt',
			}/* , {
				width : '25%',
				title : '描述',
				field : 'description',
			} */,{
				width : '20%',
				title : '上传时间',
				field : 'uploadtime'
			} ,{
				width : '20%',
				title : '状态',
				field : 'state',
				formatter : function(value, row, index) {
					if(row.path == null){
						return '<span class="color_f60">文档未发布</span>';
					}else{
						return '<span class="color_green">文档已发布</span>';
					}
				}
			} ,{
				field : 'action',
				title : '操作',
				width : '20%',
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					if ($.canEdit) {
						if(row.path == null){
							str += $.formatString('<a href="javascript:void(0)" onclick="uploadFun(\'{0}\',\'{1}\',\'{2}\',\'{3}\');" >上传</a>', row.id,row.nameMt,row.version);
						}else{
							str += $.formatString('<a href="javascript:void(0)" onclick="editFun(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\');" >更新</a>', row.id,row.nameMt,row.version,row.path);
						}
					}
					if ($.canDelete) {
						str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
						str += $.formatString('<a href="javascript:void(0)" onclick="deleteFun(\'{0}\',\'{1}\',\'{2}\',\'{3}\');" >删除</a>', row.id,row.path,row.version,row.nameMt);
					}
					
					if ($.canDownload) {
						if(row.path != null){
							str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
							str += $.formatString('<a href="javascript:void(0)" onclick="documentCommonDownloadFun(\'{0}\',\'{1}\');" ><span class="color_green">文档下载</span></a>', row.id,row.path);
						}
					}
					return str;
				}
			} ] ],
			toolbar : '#toolbar'
		});
		
		$("body").keydown(function(event) {
		    if (event.keyCode == "13") {//keyCode=13是回车键
		    	var nameMt = $('#nameMt').val().trim();
				if(nameMt != ''){
					dataGrid.datagrid('load', {
						nameMt:nameMt
					});
				}else{
					parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
				}
		    }
		});
		
	});
	
	function addFun() {
		parent.$.modalDialog({
			title : '添加文档',
			width : 750,
			height : 500,
			href : '${ctx}/documentCommon/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					var storeAs;
					var client;
					var OSSURL = '';
					var documentPath = "";
					/* var param = {
							"fileType":"documentCommon",
							"fileDetail":"order",
							"year":year,
							"orderNo":orderNo
					}; */
					/* console.log(parent.$('#materialName').text()) */
					if(parent.$('#commonNameMt').text().trim() == ""){
						parent.$.messager.alert('提示', '物料不能为空！', 'info');
						return;
					}
					if(parent.$('#commonVersion').val().length <= 0){
						parent.$.messager.alert('提示', '版本不能为空！', 'info');
						return;
					}
					if(parseInt(parent.$('#commonVersion').val().trim()) <= 0){
						parent.$.messager.alert('提示', '版本号要大于1！', 'info');
						return;
					}
					if(parseInt(parent.$('#commonVersion').val().trim()) >= 100000000){
						parent.$.messager.alert('提示', '您输入的版本号过大！', 'info');
						return;
					}
					
					if(document.getElementById('documentCommonFile').files[0] == undefined){
						$.ajax({
							url : '${ctx}/documentCommon/add?nameMt='+ parent.$('#commonNameMt').data('nameMt').trim()+"&version="+parent.$('#commonVersion').val().trim(),
							type : "GET",
							dataType : "json",
							contentType : "application/json; charset=utf-8",
							async: false,
							success : function(data) {
								if(data.success){
									parent.$.messager.alert('提示',  data.msg, 'info');
									parent.$.modalDialog.handler.dialog('close');
									var parent_tabs = parent.$('#index_tabs');
									parent_tabs.tabs('update', {
										tab : parent_tabs.tabs('getSelected'), //获取当前被选中的页面
										options : {}
										}
									);
									
								}else{
									parent.$.messager.alert('提示', data.msg, 'info');
									parent.$.modalDialog.handler.dialog('close');
								}
							}
						})
						return ;
					}else{
						//获取token
						
						$.ajax({
							url : '${pageContext.request.contextPath}/token/update?fileType=document&fileDetail=common',
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
								var file = document.getElementById('documentCommonFile').files[0];
								var fileName = file.name;
								var materialName = parent.$('#commonNameMt').text();
								//var documentName =  materialName +"V"+parent.$('#commonVersion').val().trim()+"."+fileName.substring(fileName.lastIndexOf('.') + 1);
								var documentName = materialName;
								console.log(documentName)
								documentPath = data.path + documentName.replace(new RegExp(' ',"gm"),"_").replace(new RegExp('\\+',"gm"),"_");
								console.log(file.name + ' => ' + documentPath);
							    client.multipartUpload(documentPath, file,{
							        progress: fileProgress
							    }).then(function (result) {
							        OSSURL = result.url;
							        $.ajax({
										url : '${ctx}/documentCommon/addFile?nameMt='+parent.$('#commonNameMt').data('nameMt').trim()+"&path="+documentPath+"&version="+parent.$('#commonVersion').val().trim(),
										type : "GET",
										dataType : "json",
										contentType : "application/json; charset=utf-8",
										async: false,
										success : function(data) {
											if(data.success){
												parent.$.messager.alert('提示', data.msg, 'info');
												parent.$.modalDialog.handler.dialog('close');
												dataGrid.datagrid('load', {});
												
											}else{
												parent.$.messager.alert('提示',  data.msg, 'info');
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
	}
	
	function deleteFun(id,path,version,nameMaterial) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		console.log(path)
		if(path == 'undefined'){
			console.log("...")
			$.ajax({
	         	type: 'POST',
	             url: "${ctx}/documentCommon/delete?id="+id ,
	             dataType: "json",
	             contentType: "application/json; charset=utf-8",
	             async: false,
	             success: function(data){
	            	 if(data.success){
	            		 parent.$.messager.alert('提示', '删除成功');
	            		 dataGrid.datagrid('load', {});
	            	 }else{
	            		 parent.$.messager.alert('提示', '删除失败');
	            	 }
	            	 
	             }
	        })
		}else{
			var isusedMessage = "";
			$.ajax({
				url : '${pageContext.request.contextPath}/document/isExistPath?path='+path.trim(),
				type : "GET",
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				async: false,
				success : function(data) {
					console.log(data);
					if(data.length > 0){
						isusedMessage = "该文档已被";
						for(var i in data){
							isusedMessage += '<span style="color:#f60">&nbsp;&nbsp;'+data[i].orderInfoDTO.orderNo+"&nbsp;&nbsp;</span>,"; 
						}
						isusedMessage = isusedMessage.substring(0,isusedMessage.length - 1);
						isusedMessage += "使用！";
					}
				}
			})
			if(isusedMessage != ""){
				 parent.$.messager.alert('提示', isusedMessage+'</br>请先删除关联订单的通用文档！');
			}else{
				parent.$.messager.confirm('询问', '您是否要<span style="color:#f60">&nbsp;删除&nbsp;</span>当前文档？', function(b) {
					if (b) {
						$.ajax({
							url : '${pageContext.request.contextPath}/token/update?fileType=document&fileDetail=common&filePath='+path,
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
							 	documentPath = data.path;
								console.log(' => ' + path); 
							    client.delete(path).then(function (result) {
							    	
							    	
							    	
							    }).catch(function (err) {
							        console.log(err);
							        parent.$.messager.alert('提示', '文档删除出错！！', 'info');
							    });
							}
						})
						$.ajax({
				         	type: 'POST',
				             url: "${ctx}/documentCommon/delete?id="+id ,
				             dataType: "json",
				             contentType: "application/json; charset=utf-8",
				             async: false,
				             success: function(data){
				            	 if(data.success){
				            		 parent.$.messager.alert('提示', '删除成功!');
				            		 dataGrid.datagrid('load', {});
				            	 }else{
				            		 parent.$.messager.alert('提示', '删除失败');
				            	 }
				            	 
				             }
				        })
					}
				});
			}
			
		}
	}
	
	function editFun(id,nameMt,version,path) {
		
		if(path != null){
			var oldDocumentPath = path;
			var isusedMessage = "";
			$.ajax({
				url : '${pageContext.request.contextPath}/document/isExistPath?path='+path.trim(),
				type : "GET",
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				async: false,
				success : function(data) {
					console.log(data);
					if(data.length > 0){
						isusedMessage = '该文档已被';
						for(var i in data){
							isusedMessage += '<span style="color:#f60">'+data[i].orderInfoDTO.orderNo+"</span>,&nbsp;&nbsp;"; 
						}
						isusedMessage = isusedMessage.substring(0,isusedMessage.length - 1);
						isusedMessage += "使用！<br>";
					}
				}
			})
		}
		
		parent.$.messager.confirm('询问', isusedMessage+'您是否要<span style="&nbsp;color:#f60"&nbsp;>更新</span>当前文档？', function(b) {
			if (b) {
				parent.$.modalDialog({
					title : '文档更新',
					width : 350,
					height : 180,
					href : '${ctx}/documentCommon/editPage?nameMt=' + encodeURIComponent(nameMt+"V"+version),
					buttons : [ {
						text : '更新',
						handler : function() {
							
							if(document.getElementById('documentCommonFileUpload').files[0] == undefined){
								parent.$.messager.alert('提示', '请先选择要更新的文档！！', 'info');
							}else{
								$.ajax({
									url : '${pageContext.request.contextPath}/token/update?fileType=document&fileDetail=common',
									type : "GET",
									dataType : "json",
									contentType : "application/json; charset=utf-8",
									async: false,
									success : function(data) {
										console.log(data)
										 client = new OSS.Wrapper({
											region: data.region,
											accessKeyId: data.accessKeyId,
											accessKeySecret: data.accessKeySecret,
											stsToken : data.securityToken,
											bucket: data.bucket
									    });
										parent.$('#uploadProgterssbar').css('display','block');
										var file = document.getElementById('documentCommonFileUpload').files[0];
										var fileName = file.name;
										//var documentName = parent.$('#documentCommonName').text()+"V"+version+"."+fileName.substring(fileName.lastIndexOf('.') + 1);
										var documentName = parent.$('#documentCommonName').text();
										console.log(documentName)
										documentPath = data.path + documentName.replace(new RegExp(' ',"gm"),"_").replace(new RegExp('\\+',"gm"),"_");
										console.log(file.name + ' => ' + documentPath);
										
									    client.multipartUpload(documentPath, file,{
									        progress: progress
									    }).then(function (result) {
									        OSSURL = result.url;
									        
									        $.ajax({
												url : '${ctx}/documentCommon/edit?version='+ version +'&id='+id+"&path="+documentPath+"&nameMt="+nameMt,
												dataType : "json",
												contentType : "application/json; charset=utf-8",
												async: false,
												success : function(data) {
													if(data.success){
														 parent.$.messager.alert('提示', '文档上传成功！！', 'info');
														//删除旧文档
														/* $.ajax({
															url : '${pageContext.request.contextPath}/token/update?fileType=document&fileDetail=common&filePath='+oldDocumentPath,
															type : "GET",
															dataType : "json",
															contentType : "application/json; charset=utf-8",
															async: false,
															success : function(data) {
																console.log(data)
																 client = new OSS.Wrapper({
																	region: data.region,
																	accessKeyId: data.accessKeyId,
																	accessKeySecret: data.accessKeySecret,
																	stsToken : data.securityToken,
																	bucket: data.bucket
															    });
															 	
															    client.delete(path).then(function (result) {
															    	console.log('删除成功')
															    	
															    }).catch(function (err) {
															        console.log(err);
															        parent.$.messager.alert('提示', '文档删除出错！！', 'info');
															    });
															}
														}) */
														parent.$.modalDialog.handler.dialog('close');
														dataGrid.datagrid('load', {});
														
													}else{
														parent.$.messager.alert('提示', '文档上传失败！！', 'info');
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
			}
		})
		
	}
	
	function uploadFun(id,nameMt,version) {
		parent.$.modalDialog({
			title : '文档上传',
			width : 350,
			height : 180,
			href : '${ctx}/documentCommon/editPage?nameMt=' + nameMt+"V"+version,
			buttons : [ {
				text : '上传',
				handler : function() {
					
					if(document.getElementById('documentCommonFileUpload').files[0] == undefined){
						parent.$.messager.alert('提示', '请先选择要上传的文档！！', 'info');
					}else{
						$.ajax({
							url : '${pageContext.request.contextPath}/token/update?fileType=document&fileDetail=common',
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
								parent.$('#uploadProgterssbar').css('display','block');
								var file = document.getElementById('documentCommonFileUpload').files[0];
								var fileName = file.name;
								//var documentName = parent.$('#documentCommonName').text()+"V"+version+"."+fileName.substring(fileName.lastIndexOf('.') + 1);
								var documentName = parent.$('#documentCommonName').text();
								documentPath = data.path + documentName;
								console.log(file.name + ' => ' + documentPath);
								
							    client.multipartUpload(documentPath, file,{
							        progress: progress
							    }).then(function (result) {
							        OSSURL = result.url;
							        
							        $.ajax({
										url : '${ctx}/documentCommon/edit?version='+ version +'&id='+id+"&path="+documentPath+"&nameMt="+nameMt,
										dataType : "json",
										contentType : "application/json; charset=utf-8",
										async: false,
										success : function(data) {
											if(data.success){
												parent.$.messager.alert('提示', '文档上传成功！！', 'info');
												parent.$.modalDialog.handler.dialog('close');
												var parent_tabs = parent.$('#index_tabs');
												parent_tabs.tabs('update', {
													tab : parent_tabs.tabs('getSelected'), //获取当前被选中的页面
													options : {
														//Title : '新标题',
														}
													}
												);
											}else{
												parent.$.messager.alert('提示', data.msg, 'info');
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
	}
	
	function documentCommonDownloadFun(id,path) {
		var client;
		var param = {filePath:path}
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
						
					    var saveAs = path.split('/')[path.split("/").length-1];
					    console.log(objectKey + ' => ' + saveAs);
					    var result = client.signatureUrl(objectKey, {
					      expires: 3600,
					      response: {
					        'content-disposition': 'attachment; filename="' + (saveAs) + '"'
					      }
					    });
					    console.log(result);
					    download_file(result)
					   /*  window.location = result; */
				}else{
					 parent.$.messager.alert('提示', '文档下载异常，请联系管理员！');
				}
				 
			}
		})
	}
	
	
	
	function searchFun() {
		var nameMt = $('#nameMt').val().trim();
		if(nameMt != ''){
			dataGrid.datagrid('load', {
				nameMt:nameMt
			});
		}else{
			parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
		}
	}
	function cleanFun() {
		$('#nameMt').val('');
	//	dataGrid.datagrid('load', {});
	}
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
	 function handleFileName(){
		var file = document.getElementById('documentCommonFile').files[0];
		parent.$('#documentCommonFilename').text(file.name);
		var documentName =  parent.$('#commonNameMt').html().trim();
		if(documentName != null && typeof(document) != 'undefined'){
			if(documentName != file.name){
				  parent.parent.$.messager.alert('提示', '文档不匹配！');
				  parent.clearInput();
				  parent.$('#documentCommonFilename').text('');
			}
		}
	 }
		
	function handleFileNameUpload(){
		var file = document.getElementById('documentCommonFileUpload').files[0];
		parent.$('#documentCommonFilename').text(file.name);
		var documentName =  parent.$('#documentCommonName').html().trim();
		if(documentName != null && typeof(document) != 'undefined'){
			if(documentName != file.name){
				  parent.parent.$.messager.alert('提示', '文档不匹配！');
				  parent.clearInput();
				  parent.$('#documentCommonFilename').text('');
			}
		}
    }
	
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	 <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
	<!-- 	<form id="searchForm"> -->
			<table>
				<tr>
					<th>物料名称</th>
					<td><input name="nameMt" placeholder="请输入物料名" id="nameMt"  class="autoInput"/></td>
					<td>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
				</tr>
			</table>
	<!-- 	</form> -->
	</div> 
	
	<div data-options="region:'center',border:false">
		<table id="dataGrid" data-options="fit:true,border:true" ></table>
	</div> 
		<div  style="display:none"><input type="file" id="documentCommonFile" onchange="handleFileName();" accept=".rar"><input type="file" id="documentCommonFileUpload" onchange="handleFileNameUpload();" accept=".rar"></div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/documentCommon/add')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
		
	</div> 
</body>
</html>