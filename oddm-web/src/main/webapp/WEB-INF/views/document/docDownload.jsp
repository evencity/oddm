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
	.color_f60{color: #F60;}
	.color_green{color: green;text-decoration: none;}
	.autoInput{width: 95%}
	.datagrid-cell {   /* easyui不会自动识别英文字符串自动换行，nowrap: false不生效 */
		white-space: pre-wrap;
		word-wrap: break-word;
		word-break:break-all;
		line-height: 18px;
	}
	input {border:1px solid #CCC;font-size:12px;height:19px;border-radius: 4px;}
</style>
<c:if test="${fn:contains(sessionInfo.resourceList, '/docDownload/download')}">
	<script type="text/javascript">
		$.canDownload = true;
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
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}/docDownload/dataGrid',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'uploadtime',
			sortOrder : 'desc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200 ],
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
					var docName = row.productFactory + row.nameMt + row.orderNo + "("+ row.productClient +")V"+row.version;
                    return docName;
                }
			},  {
				width : '20%',
				title : '上传时间',
				field : 'uploadtime'
			} , {
				field : 'state',
				title : '文档状态',
				width : '15%',
				formatter : function(value, row, index) {
					if(row.unread == 1){
						return '<span class="color_green">审核通过<span class="color_f60">&nbsp;[未下载]</span></span>';
					}else{
						return '<span class="color_green">审核通过</span>';
					}
				}
			}, {
				field : 'action',
				title : '操作',
				width : '15%',
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					if ($.canDownload) {
						str += $.formatString('<a href="javascript:void(0)" class="color_green" onclick="docDownload_download(\'{0}\',\'{1}\');" >下载文档</a>', row.id, row.path);
					}
					return str;
				}
			} ] ],
			toolbar : '#toolbar'
		});
		$("body").keydown(function(event) {
		    if (event.keyCode == "13") {//keyCode=13是回车键
		    	var orderNo = $('#orderNo').val().trim();
				if(orderNo != ''){
					dataGrid.datagrid('load', {
						orderNo:orderNo,
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
	
	function docDownload_download(id,filepath){
		if(filepath != null && typeof(filepath) != null){
			var param = {filePath:filepath}
			$.ajax({
				url : '${pageContext.request.contextPath}/token/download',
				type : "POST",
				data : JSON.stringify(param),
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				async: false,
				success : function(data) {
					if(data.status !='传参解析异常'){
						 client = new OSS.Wrapper({
								region: data.region,
								accessKeyId: data.accessKeyId,
								accessKeySecret: data.accessKeySecret,
								stsToken : data.securityToken,
								bucket: data.bucket
						    });
						 	dataGrid.datagrid('load', {});
						    var objectKey = data.path;
							
						    var saveAs = filepath.split('/')[filepath.split("/").length-1];
						    console.log(objectKey + ' => ' + saveAs);
						    var result = client.signatureUrl(objectKey, {
						      expires: 3600,
						      response: {
						        'content-disposition': 'attachment; filename="' + (saveAs) + '"'
						      }
						    });
						    download_file(result);
						   
						    $.ajax({
								url : '${pageContext.request.contextPath}/document/deleteNoreadRecord?docId='+id,
								type : "GET",
								dataType : "json",
								contentType : "application/json; charset=utf-8",
								async: false,
								success : function(data) {
									if(data){
										console.log('删除文档未读成功')
									}else{
										console.log('删除文档未读失败。。。。')
									}
								}
						    });
					}else{
						 parent.$.messager.alert('提示', '文档下载异常，请联系管理员！');
					}
					 
				}
			})
		}
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
	function searchForNoreadFun() {
		var orderNo = $('#orderNo').val().trim();
		dataGrid.datagrid('load', {
			orderNo:orderNo,
			unread : 1
		});
	}
	function cleanFun() {
		$('#orderNo').val('');
		//dataGrid.datagrid('load', {});
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
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchForNoreadFun();">未下载</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
				</tr>
			</table>
		<!-- </form> -->
	</div> 
	
	<div data-options="region:'center',border:false">
		<table id="dataGrid" data-options="fit:true,border:true" ></table>
	</div> 
</body>
</html>