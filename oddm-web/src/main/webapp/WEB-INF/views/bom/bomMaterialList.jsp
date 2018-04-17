<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>
	.choose{
		border: 1px solid #999;
		height: 100%;
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
		padding: 0px 20px;
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
	var materialPackingNames = "${materialBareMainNames}";
	$(function() {
		if(materialPackingNames.length > 3){
			var packingNames = materialPackingNames.split(',');
			for(var i = 0; i < packingNames.length; i += 2){
				if(packingNames[i] != undefined && packingNames[i] != ""){
					var content = '<li>'+ packingNames[i].trim() +'&nbsp&nbsp&nbsp&nbsp'+ packingNames[i+1].trim() +'</li>'
					$('#choose_fitting').append(content);
				}
			}
		}
	
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}' + '/bomMaterial/dataGrid',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : false,
			scrollbarSize :0 , 
			fitColumns : false,
			fit:true,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'asc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200 ],
			onDblClickRow : function(index, row){
				if($('#choose_fitting li').length > 0){
					var falg = false;
				 	$('#choose_fitting li').each(function(){
						var name = $(this).text().trim().split("   ")[0];
						var names = row.mtlCode;
						if(name == names){
							falg = true;
						}   
					})
					if(!falg){
						var content = $('<li id='+ row.id +'>'+ row.mtlCode +'&nbsp&nbsp&nbsp&nbsp'+ row.materialName +'</li>')
						$('#choose_fitting').append(content);
					}
				}else{ 
					var content = $('<li id='+ row.id +'>'+ row.mtlCode +'&nbsp&nbsp&nbsp&nbsp'+ row.materialName +'</li>')
					$('#choose_fitting').append(content);
				}
	        },  
					frozenColumns : [ [ 
				{
					width : '150',
					title : '物料编号',
					field : 'mtlCode',
					sortable : true
				}, {
					width : '250',
					title : '物料名称',
					field : 'materialName',
					sortable : true
				},] ]
		});
		$('#choose_fitting').off('dblclick','li').on('dblclick','li',function(){
			$(this).remove();
		});
		$('#fitting_materialName').off('keydown').on('keydown',function(event){
			 if (event.keyCode == 13) {//keyCode=13是回车键
				/*  console.log(event.keyCode)
               	 alert(event.keyCode)
			 	 return false; */
				 dataGrid.datagrid('load', {
					 mtlCode:$('#fitting_materialName').val().trim(),
					});
             }
		});
		$(".datagrid-body").css("overflow-y","hidden");
	});
	function searchFun() {
		dataGrid.datagrid('load', {
			mtlCode:$('#fitting_materialName').val().trim(),
		});
	}
	function cleanFun() {
		$('#fitting_materialName').val('');
		//dataGrid.datagrid('load', {});
	}
	
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'west',border:false" title="" style="overflow:auto;padding: 3px;width:50%;" >
		<div class="choose">
			<div class="title">已选物料</div>
				<div style="float:left">
					<div style="width:400px;height: 50px;float:left;"><ul id="choose_fitting"></ul></div>
				</div>
		</div>
		
	</div>

	<div data-options="region:'center',border:false" title="" style="overflow:hidden;" >
		<div class="easyui-layout" data-options="fit:true,border:false" >
			<div data-options="region:'north',border:false" title="" style="overflow: auto;padding: 3px;" >
				<!-- <form id="searchForm"> -->
					<table id="fitting_table">
						<tr>
							<th>编号:</th>
							<td><input name="mtlCode" placeholder="请输入物料编号 " id="fitting_materialName"/></td>
							<td>
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
						</tr>
					</table>
				<!-- </form> -->
			</div>
			<div data-options="region:'center',border:false" title="" style="overflow: auto;padding: 3px;" >
			<table id="dataGrid" data-options="fit:true,border:false"></table>
			</div>
		</div>
		
	</div>
</div>
