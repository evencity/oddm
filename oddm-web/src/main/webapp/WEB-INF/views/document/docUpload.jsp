<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<style type="text/css">
	.datagrid-view1 .datagrid-body {overflow-y: auto;overflow-x: hidden;}
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

<title>订单信息</title>
<script src="${ctx}/jslib/aliyun-oss-sdk-4.4.4.min.js"></script>
<script src="${ctx}/jslib/promise-6.1.0.js"></script>
	<script type="text/javascript">
	var dataGrid;
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
		if($.artDocument && $.otherAllDocument ){
			//所有
			dataGrid = $('#dataGrid').datagrid({
				url : '${ctx}/docUpload/dataGridAll',
				striped : true,
				rownumbers : true,
				pagination : true,
				singleSelect : true,
				idField : 'id',
				sortName : 'uploadtime',
				sortOrder : 'desc',
				pageSize : 50,
				pageList : [ 10, 20, 30, 40, 50, 100, 200],
				view: myview,
	            emptyMsg: '没有找到相关数据',
				onLoadSuccess:function(data){ 
					
	            },
	            frozenColumns : [ [{
					width : '10%',
					title : 'NO.',
					field : 'id',
					sortable : true,
					hidden :true
				}, {
					width : '5%',
					title : '版本',
					field : 'version',
					formatter:function(value,row,index){
	                    return "V"+row.version;
	                }
				}, {
					title : '文档名',
					field : 'nameMt',
					width : '45%',
					formatter:function(value,row,index){
						var docName = row.orderInfoDTO.productFactory + row.nameMt + row.orderInfoDTO.orderNo + "("+ row.orderInfoDTO.productClient +")V"+row.version;
	                    return docName;
	                }
				},  {
					width : '20%',
					title : '上传时间',
					field : 'uploadtime',
					sortable : true,
				} , {
					field : 'state',
					title : '文档状态',
					width : '15%',
					sortable : true,
					formatter : function(value, row, index) {
						if(row.state == 1){
							return '<span>文档未发布</span>';
						}
						if(row.state == 4){
							return '<span class="color_f60">未通过审核</span>';
						}
					}
				}, {
					field : 'action',
					title : '操作',
					width : '15%',
					formatter : function(value, row, index) {
						var str = '&nbsp;';
						var orderNo = row.orderInfoDTO.orderNo;
						var year = new Date(row.orderInfoDTO.timestamp).getFullYear();
						var version = row.version;
						var docName = row.orderInfoDTO.productFactory + row.nameMt + row.orderInfoDTO.orderNo + "("+ row.orderInfoDTO.productClient.trim() +")V"+parseInt(row.version+1);
						var oldPath = row.orderInfoDTO.productFactory + row.nameMt + row.orderInfoDTO.orderNo + "("+ row.orderInfoDTO.productClient.trim() +")V"+parseInt(row.version);
						if(row.state == 1){
							str += $.formatString('<a href="javascript:void(0)" onclick="docUpload_upload(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\',\'{5}\',\'{6}\');" >上传文档</a>', row.id, row.orderInfoDTO.id, docName,orderNo,year,version,oldPath);
						}
						if(row.state == 4){
							str += $.formatString('<a href="javascript:void(0)" styple="color:#f60" onclick="docUpload_upload(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\',\'{5}\',\'{6}\');" >上传文档</a>', row.id, row.orderInfoDTO.id, docName,orderNo,year,version,oldPath);
						}
						return str;
					}
				} ] ],
				toolbar : '#toolbar'
			});
		}else{
			if($.artDocument){
				//美工
				dataGrid = $('#dataGrid').datagrid({
					url : '${ctx}/docUpload/dataGrid',
					striped : true,
					rownumbers : true,
					pagination : true,
					singleSelect : true,
					idField : 'id',
					sortName : 'uploadtime',
					sortOrder : 'desc',
					pageSize : 50,
					pageList : [ 10, 20, 30, 40, 50, 100, 200],
					view: myview,
		            emptyMsg: '没有找到相关数据',
					onLoadSuccess:function(data){ 
						
		            },
		            frozenColumns : [ [{
						width : '10%',
						title : 'NO.',
						field : 'id',
						sortable : true,
						hidden :true
					}, {
						width : '5%',
						title : '版本',
						field : 'version',
						formatter:function(value,row,index){
		                    return "V"+row.version;
		                }
					}, {
						title : '文档名',
						field : 'nameMt',
						width : '35%',
						formatter:function(value,row,index){
							var docName = row.orderInfoDTO.productFactory + row.nameMt + row.orderInfoDTO.orderNo + "("+ row.orderInfoDTO.productClient +")V"+row.version;
		                    return docName;
		                }
					},  {
						width : '25%',
						title : '上传时间',
						field : 'uploadtime',
						sortable : true,
					} , {
						field : 'state',
						title : '文档状态',
						width : '20%',
						sortable : true,
						formatter : function(value, row, index) {
							if(row.state == 1){
								return '<span>文档未发布</span>';
							}
							if(row.state == 4){
								return '<span class="color_f60">未通过审核</span>';
							}
						}
					}, {
						field : 'action',
						title : '操作',
						width : '15%',
						formatter : function(value, row, index) {
							var str = '&nbsp;';
							var orderNo = row.orderInfoDTO.orderNo;
							var year = new Date(row.orderInfoDTO.timestamp).getFullYear();
							var version = row.version;
							var docName = row.orderInfoDTO.productFactory + row.nameMt + row.orderInfoDTO.orderNo + "("+ row.orderInfoDTO.productClient.trim() +")V"+parseInt(row.version+1);
							var oldPath = row.orderInfoDTO.productFactory + row.nameMt + row.orderInfoDTO.orderNo + "("+ row.orderInfoDTO.productClient.trim() +")V"+parseInt(row.version);
							if(row.state == 1){
								str += $.formatString('<a href="javascript:void(0)" onclick="docUpload_upload(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\',\'{5}\',\'{6}\');" >上传文档</a>', row.id, row.orderInfoDTO.id, docName,orderNo,year,version,oldPath);
							}
							if(row.state == 4){
								str += $.formatString('<a href="javascript:void(0)" styple="color:#f60" onclick="docUpload_upload(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\',\'{5}\',\'{6}\');" >上传文档</a>', row.id, row.orderInfoDTO.id, docName,orderNo,year,version,oldPath);
							}
							return str;
						}
					} ] ],
					toolbar : '#toolbar'
				});
			}
			if($.otherAllDocument ){
				//其他全部
				dataGrid = $('#dataGrid').datagrid({
					url : '${ctx}/docUpload/dataGridForOtherAll',
					striped : true,
					rownumbers : true,
					pagination : true,
					singleSelect : true,
					idField : 'id',
					sortName : 'uploadtime',
					sortOrder : 'desc',
					pageSize : 50,
					pageList : [ 10, 20, 30, 40, 50, 100, 200],
					view: myview,
		            emptyMsg: '没有找到相关数据',
					onLoadSuccess:function(data){ 
						
		            },
		            frozenColumns : [ [{
						width : '10%',
						title : 'NO.',
						field : 'id',
						sortable : true,
						hidden :true
					}, {
						width : '5%',
						title : '版本',
						field : 'version',
						formatter:function(value,row,index){
		                    return "V"+row.version;
		                }
					}, {
						title : '文档名',
						field : 'nameMt',
						width : '35%',
						formatter:function(value,row,index){
							var docName = row.orderInfoDTO.productFactory + row.nameMt + row.orderInfoDTO.orderNo + "("+ row.orderInfoDTO.productClient +")V"+row.version;
		                    return docName;
		                }
					},  {
						width : '25%',
						title : '上传时间',
						field : 'uploadtime',
						sortable : true,
					} , {
						field : 'state',
						title : '文档状态',
						width : '20%',
						sortable : true,
						formatter : function(value, row, index) {
							if(row.state == 1){
								return '<span>文档未发布</span>';
							}
							if(row.state == 4){
								return '<span class="color_f60">未通过审核</span>';
							}
						}
					}, {
						field : 'action',
						title : '操作',
						width : '15%',
						formatter : function(value, row, index) {
							var str = '&nbsp;';
							var orderNo = row.orderInfoDTO.orderNo;
							var year = new Date(row.orderInfoDTO.timestamp).getFullYear();
							var version = row.version;
							var docName = row.orderInfoDTO.productFactory + row.nameMt + row.orderInfoDTO.orderNo + "("+ row.orderInfoDTO.productClient.trim() +")V"+parseInt(row.version+1);
							var oldPath = row.orderInfoDTO.productFactory + row.nameMt + row.orderInfoDTO.orderNo + "("+ row.orderInfoDTO.productClient.trim() +")V"+parseInt(row.version);
							if(row.state == 1){
								str += $.formatString('<a href="javascript:void(0)" onclick="docUpload_upload(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\',\'{5}\',\'{6}\');" >上传文档</a>', row.id, row.orderInfoDTO.id, docName,orderNo,year,version,oldPath);
							}
							if(row.state == 4){
								str += $.formatString('<a href="javascript:void(0)" styple="color:#f60" onclick="docUpload_upload(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\',\'{5}\',\'{6}\');" >上传文档</a>', row.id, row.orderInfoDTO.id, docName,orderNo,year,version,oldPath);
							}
							return str;
						}
					} ] ],
					toolbar : '#toolbar'
				});
			}else{
				if($.otherPersonDoument){
					//其他个人
					dataGrid = $('#dataGrid').datagrid({
						url : '${ctx}/docUpload/dataGridForOtherPerson',
						striped : true,
						rownumbers : true,
						pagination : true,
						singleSelect : true,
						idField : 'id',
						sortName : 'uploadtime',
						sortOrder : 'desc',
						pageSize : 50,
						pageList : [ 10, 20, 30, 40, 50, 100, 200],
						view: myview,
			            emptyMsg: '没有找到相关数据',
						onLoadSuccess:function(data){ 
							
			            },
			            frozenColumns : [ [{
							width : '10%',
							title : 'NO.',
							field : 'id',
							sortable : true,
							hidden :true
						}, {
							width : '5%',
							title : '版本',
							field : 'version',
							formatter:function(value,row,index){
			                    return "V"+row.version;
			                }
						}, {
							title : '文档名',
							field : 'nameMt',
							width : '35%',
							formatter:function(value,row,index){
								var docName = row.orderInfoDTO.productFactory + row.nameMt + row.orderInfoDTO.orderNo + "("+ row.orderInfoDTO.productClient +")V"+row.version;
			                    return docName;
			                }
						},  {
							width : '25%',
							title : '上传时间',
							field : 'uploadtime',
							sortable : true,
						} , {
							field : 'state',
							title : '文档状态',
							width : '20%',
							sortable : true,
							formatter : function(value, row, index) {
								if(row.state == 1){
									return '<span>文档未发布</span>';
								}
								if(row.state == 4){
									return '<span class="color_f60">未通过审核</span>';
								}
							}
						}, {
							field : 'action',
							title : '操作',
							width : '15%',
							formatter : function(value, row, index) {
								var str = '&nbsp;';
								var orderNo = row.orderInfoDTO.orderNo;
								var year = new Date(row.orderInfoDTO.timestamp).getFullYear();
								var version = row.version;
								var docName = row.orderInfoDTO.productFactory + row.nameMt + row.orderInfoDTO.orderNo + "("+ row.orderInfoDTO.productClient.trim() +")V"+parseInt(row.version+1);
								var oldPath = row.orderInfoDTO.productFactory + row.nameMt + row.orderInfoDTO.orderNo + "("+ row.orderInfoDTO.productClient.trim() +")V"+parseInt(row.version);
								if(row.state == 1){
									str += $.formatString('<a href="javascript:void(0)" onclick="docUpload_upload(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\',\'{5}\',\'{6}\');" >上传文档</a>', row.id, row.orderInfoDTO.id, docName,orderNo,year,version,oldPath);
								}
								if(row.state == 4){
									str += $.formatString('<a href="javascript:void(0)" styple="color:#f60" onclick="docUpload_upload(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\',\'{5}\',\'{6}\');" >上传文档</a>', row.id, row.orderInfoDTO.id, docName,orderNo,year,version,oldPath);
								}
								return str;
							}
						} ] ],
						toolbar : '#toolbar'
					});
				}
			}
		}
		
		$("body").keydown(function(event) {
		    if (event.keyCode == "13") {//keyCode=13是回车键
		    	var orderNo = $('#orderNo').val().trim();
				if(orderNo != ''){
					dataGrid.datagrid('load', {
						orderNo:orderNo
					});
				}else{
					parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
				}
		    }
		});
		
	});
	var progress = function (p) {
		return function (done) {
		  var bar = parent.$('#uploadProgterssbar');
		  var value = Math.floor(p * 100);
		   bar.progressbar('setValue', value); 
		  done();
		}
	};
	
	function docUpload_upload(id,orderId,docName,orderNo,year,version,oldPath){
		parent.$.modalDialog({
			title : '文档添加',
			width : 350,
			height : 180,
			href : '${ctx}/docUpload/editPage?docName='+encodeURIComponent(docName.replace(new RegExp(' ',"gm"),"_").replace(new RegExp('\\+',"gm"),"_")),
			buttons : [ {
				text : '提交',
				handler : function() {
					if(document.getElementById('documentUploadFile').files[0] != undefined ){
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
								//显示进度条
								parent.$('#uploadProgterssbar').css('display','block');
								var file = document.getElementById('documentUploadFile').files[0];
								var fileName = file.name;
								var documentName = docName +"."+fileName.substring(fileName.lastIndexOf('.') + 1);
								var documentPath = data.path + documentName.replace(new RegExp(' ',"gm"),"_").replace(new RegExp('\\+',"gm"),"_");
								console.log(file.name + ' => ' + documentPath);
								
							    client.multipartUpload(documentPath, file,{
							        progress: progress
							    }).then(function (result) {
							    	 console.log('upload success: %j', result);
							    	 var param = {
							        		 version : version,
							        		 id : id,
							        		 path : documentPath,
							        		 orderId : orderId,
							         }
							         $.ajax({
										/* url : '${ctx}/docUpload/addFile?orderId='+orderId+"&path="+documentPath+"&id="+id+"&version="+version, */
										url : '${ctx}/docUpload/addFile',
										data : JSON.stringify(param),
										dataType : "json",
										contentType : "application/json; charset=utf-8",
										async: false,
										success : function(data) {
											if(data.success){
												parent.$.messager.alert('提示', '文档上传成功！！', 'info');
												parent.$.modalDialog.handler.dialog('close');
												dataGrid.datagrid('load', {});
												if(version > 0){
													console.log("版本大于0说明文档存在，需要删除")
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
															var oldDocumentPath = (oldPath+'.rar').replace(new RegExp(' ',"gm"),"_").replace(new RegExp('\\+',"gm"),"_");
															oldDocumentPath = data.path + oldDocumentPath;
															console.log(' 文档删除=> ' + oldDocumentPath)
														    client.delete(oldDocumentPath).then(function (result) {
														    	console.log(".deleteDoc....删除成功")
														    }).catch(function (err) {
														        console.log(err);
														    });
														}
													})
												}
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
					}else{
						parent.$.messager.alert('提示', '请先选择需要上传的文档！！', 'info');
					}
					
					
				}
			} ]
		});
	}
	
	
	function searchFun() {
		var orderNo = $('#orderNo').val().trim();
		if(orderNo != ''){
			dataGrid.datagrid('load', {
				orderNo:orderNo
			});
		}else{
			parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
		}
	}
	function cleanFun() {
		$('#orderNo').val('');
		//dataGrid.datagrid('load', {});
	}
	function handleFileName(){
		var file = document.getElementById('documentUploadFile').files[0];
		var fileName = parent.$('#documentUploadFilename').text(file.name);
		var documentName =  parent.$('#orderDocName').html().trim();
		if(documentName != null && typeof(document) != 'undefined'){
			if(escape2Html(documentName) != file.name){
				  parent.parent.$.messager.alert('提示', '文档不匹配！');
				  parent.clearInput();
				  parent.$('#documentUploadFilename').text('');
			}
		}
    }
	function escape2Html(str) {
		 var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"'};
		 return str.replace(/&(lt|gt|nbsp|amp|quot);/ig,function(all,t){return arrEntities[t];});
	}
	
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	 <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
		<!-- <form id="searchForm"> -->
			<table>
				<tr>
					<th>订单编号：</th>
					<td><input name="orderNo" placeholder="请输入订单号" id="orderNo"  class="autoInput"/></td>
					<td>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
				</tr>
			</table>
		<!-- </form> -->
	</div> 
	
	<div  style="display:none"><input type="file" id="documentUploadFile" onchange="handleFileName();" accept=".rar"></div>
	
	<div data-options="region:'center',border:false">
		<table id="dataGrid" data-options="fit:true,border:true" ></table>
	</div> 
</body>
</html>