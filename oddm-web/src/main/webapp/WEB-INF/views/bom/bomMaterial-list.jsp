<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../inc.jsp"/>
<c:if test="${fn:contains(sessionInfo.resourceList, '/bomMaterial/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/bomMaterial/del')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<style type="text/css">
	.datagrid-cell {   /* easyui不会自动识别英文字符串自动换行，nowrap: false不生效 */
		white-space: pre-wrap;
		word-wrap: break-word;
		line-height: 18px;
		word-break:break-all;
	}
	input {border:1px solid #CCC;font-size:12px;height:19px;border-radius: 4px;}
</style>
<script type="text/javascript">
	var dataGridz;
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
	dataGrid = $('#dataGridz').datagrid({
			url : '${pageContext.request.contextPath}/bomMaterial/dataGrid',
			striped : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'desc',
			nowrap:false,
			view: myview,
		    emptyMsg: '没有找到相关数据',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200],
				
		    rownumbers : true,
			
			frozenColumns : [ [ {
				field : 'id',
				title : '序号',
				width : 150,
				checkbox : true
			}]],
			columns : [ [  {
				width : '13%',
				title : '物料编号',
				field : 'mtlCode',
				sortable : true
			}, {
				width : '20%',
				title : '物料名称',
				field : 'materialName',
				sortable : true
			},{
				width : '35%',
				title : '规格和型号',
				field : 'specification',
			}, {
				width : '16%',
				title : '描述',
				field : 'description',
			}, {
				width : '12%',
				title : '更新时间',
				field : 'updatetime',
			}
			] ],
   			toolbar : '#toolbar',
		})
		
		$("body").keydown(function(event) {
		    if (event.keyCode == "13") {//keyCode=13是回车键
		    	var materialName = $('#materialName').val().trim();
				var mtlCode = $('#mtlCode').val().trim();
				if( materialName == "" && mtlCode == ""){
					parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
				}else{
					dataGrid.datagrid('load', {
						materialName:materialName,
						mtlCode:mtlCode
					}); 
					
				}
		    }
		});
	});
	function addFun() {
		parent.$.modalDialog({
			title : '添加',
			width : 450,
			height : 300,
			href : '${ctx}/bomMaterial/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#bomMaterialAddForm');
					f.submit();
				}
			} ]
		});
	}
	/**批量添加*/
	function addFuns() {
		showRestartDialogs();
	    	/*  */
	}
	function deleteFun() {
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		parent.$.messager.confirm('询问', '您是否要删除当前裸机物料？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/bomMaterial/del', {
					materialCode : row.mtlCode
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('clearSelections');
						dataGrid.datagrid('reload');
					} else {
						parent.$.messager.alert('错误', result.msg, 'error');
					}
					progressClose();
				}, 'JSON');
			}
		});
	}
	
	function editFun() {
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		parent.$.modalDialog({
			title : '编辑',
			width : 450,
			height : 300,
			href : '${ctx}/bomMaterial/editPage?mtlCode='+ row.mtlCode,
			buttons : [ {
				text : '提交',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#bomMaterialEditForm');
					f.submit();
				}
			} ]
		});
	}
	
	function searchFunz() {
		var materialName = $('#materialName').val().trim();
		var mtlCode = $('#mtlCode').val().trim();
		if( materialName == "" && mtlCode == ""){
			parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
		}else{
			dataGrid.datagrid('load', {
				materialName:materialName,
				mtlCode:mtlCode
			}); 
			
		}
	}
	function cleanFun() {
		$('#materialName').val('');
		$('#mtlCode').val('');
	}
	
	</script>

</head>

<body class="easyui-layout" data-options="fit:true,border:false">

	<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
		<form id="searchFormz">
			<table>
				<tr>
					<th>物料名称:</th>
					<td><input name="materialName" id="materialName" placeholder="请输入物料名称"/></td>
					<th>物料编号:</th>
					<td><input name="mtlCode" id="mtlCode" placeholder="请输入物料编码"/></td>
					<td >
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFunz();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGridz" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/bomMaterial/adds')}"> 
			<a  onclick="addFuns();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">批量导入</a>
		</c:if>
		 <c:if test="${fn:contains(sessionInfo.resourceList, '/bomMaterial/add')}"> 
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if> 
		<c:if test="${fn:contains(sessionInfo.resourceList, '/bomMaterial/edit')}"> 
			<a  onclick="editFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">编辑</a>
		</c:if> 
		<c:if test="${fn:contains(sessionInfo.resourceList, '/bomMaterial/del')}"> 
			<a  onclick="deleteFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-del'">删除</a>
		</c:if> 
	</div>
  
	<!-- 批量添加  -->
 <div  id="restartDialogs" class="easyui-dialog" title="上传文件" style="width: 400px; height: 180px;display:none;"  
                    data-options="iconCls:'pag-list',modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true">  
        <div style="margin-left: 5px;margin-right: 5px;margin-top: 5px;">           
            <div class="data-tips-info">  
   <%--  <form action="${pageContext.request.contextPath}/bomMaterial/uploadz" method="post" enctype="multipart/form-data" target="ajaxFrame">  
	<div style=" height: 60px;" >
	<input type="file" name="file" id="bomMtfCode" class="form-control up" style="width:340px;float:left"/> 
	<input type="submit" value="上传" type="button" class="btn btn-info"style="float:left"/>
	
	</div>
</form> --%>
<input type="file" id="file" name="myfile" />


<iframe name="ajaxFrame" style="display: none;"></iframe>
            <div style="text-align:right;margin-right:10px;margin-top:90px;">  
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'ope-finish'" onclick="restartNetworks()">确定</a>  
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'ope-cancel'" onclick="cancels()">取消</a>  
            </div>                  
        </div>          
    </div> 
</div>
	 
</body>
<script language="javascript" type="text/javascript"> 
		function showRestartDialogs(){   
			$("#restartDialogs").css("display","block");
	        $("#restartDialogs").dialog('open');  
	    }  
	      
	    function restartNetworks(){  
	    	 progressLoad();
	    	  var fileObj = document.getElementById("file").files[0]; // 获取文件对象

	            var FileController = "${pageContext.request.contextPath}/bomMaterial/uploadz";                    // 接收上传文件的后台地址 

	            // FormData 对象

	            var form = new FormData();

	            form.append("author", "hooyes");                        // 可以增加表单数据

	            form.append("file", fileObj);                           // 文件对象

	            // XMLHttpRequest 对象

	            var xhr = new XMLHttpRequest();

	            xhr.open("post", FileController, true);

	            xhr.onload = function () {
	                $.ajax({
	 	               type: "GET",
	 	               url: "${pageContext.request.contextPath}/bomMaterial/importExcel",
	 	               dataType: "json",
	 	               success: function(result){
	 	            	   progressClose();
	    	             	   if(result.success){
	    	              		 parent.$.messager.alert('提示',result.msg, '添加成功');
	    	              		 var parent_tabs = parent.$('#index_tabs');
	    	                var index = parent_tabs.tabs('getTabIndex', parent.$('#index_tabs').tabs('getSelected'));
	    	   					 var tab = parent_tabs.tabs('getTab', index);
	    	   					 /* if (tab.panel('options').closable) {
	    	   						parent_tabs.tabs('close', index);
	    	   					 }  */
	    	   					parent_tabs.tabs('select', "BOM虚拟料号");
	    	   					parent_tabs.tabs('update', {
	    	  						tab : parent_tabs.tabs('getSelected'), //获取当前被选中的页面
	    	  						options : {
	    	  							//Title : '新标题',
	    	  							}
	    	  						}
	    	  					);
	    	   				parent.$.modalDialog.openner_dataGrid = dataGridz;
	    	              	 }else{
	    	              		 parent.$.messager.alert('提示', result.msg, '添加失败');
	    	              	 } 
	    	               }
	 	         })
	 	        cancels(); 

	            };

	            xhr.send(form);
	    	 
	   //     var checked = $("#cleanUp").prop("checked");  
	   //     invokeAjax('/network/restartNetwork','networkId=' + $("#networkId").val() + '&cleanUp='+checked,'重新启动');  
	    }  
	      
	    function cancels(){  
	        $('#restartDialogs').window('close');          
	    }   
	    

	    //选择文件之后执行上传  
	  function onloads(){  
	        $.ajaxFileUpload({  
	            url:'${pageContext.request.contextPath}/bomMaterial/uploadz',  
	            secureuri:false,  
	            fileElementId:'fileToUpload',//file标签的id  
	            dataType: 'json',//返回数据的类型  
	           
	        });  
	  
	    }  
</script>
</html>