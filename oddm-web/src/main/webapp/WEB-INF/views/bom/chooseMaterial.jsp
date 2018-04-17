<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>
	.choose{
		border: 1px solid #999;
		height: 99%;
		overflow: auto;
	}
	.choose .title{
		text-align: center;
		padding: 5px;
		height: 20px;
		background-color: #CCC;
		border-left: 1px solid #999;
		border-right: 1px solid #999;
		border-top: 1px solid #999;
	}
	.choose  ul{
		list-style: none;
		padding: 0px 5px;
	}
	.choose  ul li{
		padding: 5px;
		cursor: pointer;
	}
	.choose  ul li:hover{
		background: #CCC;
		
	}
	.datagrid-view .datagrid-body {overflow-y: auto;overflow-x: hidden;}
	.datagrid-cell {   /* easyui不会自动识别英文字符串自动换行，nowrap: false不生效 */
		white-space: pre-wrap;
		word-wrap: break-word;
		line-height: 18px;
		word-break:break-all;
	}
</style>
<script type="text/javascript">
	var dataGrid;
	var materialNames = "${materialNames}";
	var separator = "——";
	$(function() {
		if(materialNames != ""){
			var mainNames = materialNames.split(',');
			for(var i in mainNames){
				var content = '<li>'+ mainNames[i] +'</li>'
				
				$('#chooseMaterialMain').append(content);
			}
		}
		
		
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/bomMaterial/dataGridForBom',
			idField : 'id',
			sortName : 'updatetime',
			sortOrder : 'desc',
			pagination : true,
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			nowrap:false,
			pageSize : 50,
			onDblClickRow : function(index, row){
				if($('#chooseMaterialMain li').length > 0){
					var falg = false;
					$('#chooseMaterialMain li').each(function(){
						var name = $(this).text();
						//var rightName = row.mtlCode +"----" + row.materialName;
						var rightName = row.mtlCode +separator + row.materialName;
						//var rightName = row.mtlCode;
						if(name == rightName){
							falg = true;
						}   
					})
					if(!falg){
						//var materialName = row.mtlCode +"----" + row.materialName;
						var materialName = row.mtlCode +separator + row.materialName;
						//var materialName = row.mtlCode;
						var $content = $('<li data-materialName="'+row.materialName+'" data-specification="'+row.specification+'" data-description="'+row.description+'">'+ materialName +'</li>')
						
						$('#chooseMaterialMain').append($content);
					}
				}else{
					//var materialName = row.mtlCode +"----" + row.materialName;
					var materialName = row.mtlCode +separator + row.materialName;
					//var materialName = row.mtlCode;
					var $content = $('<li data-materialName="'+row.materialName+'" data-specification="'+row.specification+'" data-description="'+row.description+'">'+ materialName +'</li>')
					
					$('#chooseMaterialMain').append($content);
				}
	        },  
			frozenColumns : [ [ {
				width : '25%',
				title : '物料编号',
				field : 'mtlCode',
			},  {
				width : '30%',
				title : '物料名称',
				field : 'materialName',
			}, {
				width : '45%',
				title : '规格和型号',
				field : 'specification',
			}] ],
   			toolbar : '#toolbar',
		})
		$('#chooseMaterialMain').off('dblclick','li').on('dblclick','li',function(){
			$(this).remove();
		});
		$('#mtlCode').off('keydown').on('keydown',function(event){
			 if (event.keyCode == 13) {//keyCode=13是回车键
				 dataGrid.datagrid('load', {
					//	materialName:$('#materialName').val().trim(),
						mtlCode:$('#mtlCode').val().trim(),
					});
             }
		});
		
	});
	function searchFun() {
		dataGrid.datagrid('load', {
			//materialName:$('#materialName').val().trim(),
			mtlCode:$('#mtlCode').val().trim(),
		});
	}
	function cleanFun() {
	//	$('#materialName').val('');
		$('#mtlCode').val('');
		dataGrid.datagrid('load', {});
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'west',border:false" title="" style="overflow:auto;padding: 3px;width:40%;" >
		<div class="choose">
			<div class="title">已选物料</div>
			<div ><ul id="chooseMaterialMain"></ul></div>
		</div>
		
	</div>
	<div data-options="region:'center',border:false" title="" style="overflow: auto;" >
		<div class="easyui-layout" data-options="fit:true,border:false" >
			<div data-options="region:'north',border:false" title="" style="overflow: auto;padding: 3px;" >
			<!-- 	<form id="searchForm"> -->
					<table>
						<tr>
							<!-- <th>物料名称:</th>
							<td><input name="name" placeholder="请输入物料名称" id="materialName"/></td> -->
							<th>物料编码:</th>
							<td><input name="name" placeholder="请输入物料编码" id="mtlCode"/></td>
							<td>
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
						</tr>
					</table>
			<!-- 	</form> -->
			</div>
			<div data-options="region:'center',border:false" title="" style="overflow: auto;padding: 3px;" >
				<table id="dataGrid" data-options="fit:true,border:false"></table>
			</div>
		</div>
	</div>
</div>
