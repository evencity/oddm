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
		word-break:break-all;
		line-height: 18px;
	}
</style>
<script type="text/javascript">
	var dataGrid;
	var materialBareMainNames = "${materialBareMainNames}";
	
	$(function() {
		if(materialBareMainNames != ""){
			var mainNames = materialBareMainNames.split(',');
			for(var i in mainNames){
				var content = '<li>'+ mainNames[i] +'</li>'
				$('#choose_hardware_main').append(content);
			}
		}else{
			$.ajax({
				url:'${ctx}' + '/materialBare/listforBase',
				type:"GET",
				dataType: "json",
	            contentType: "application/json; charset=utf-8", 
	            success: function(data){
	            	console.log(data)
					for(var i in data){
	 					var $content = $('<li id='+ data[i].id +'>'+ data[i].name +'</li>')
	     				$('#choose_hardware_main').append($content);
					}
				}
			})
		}
		
		
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}' + '/materialBare/dataGridforBare',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : false,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'asc',
			nowrap:false,
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200 ],
			onDblClickRow : function(index, row){
				if($('#choose_hardware_main li').length > 0){
					var falg = false;
					$('#choose_hardware_main li').each(function(){
						var name = $(this).text();
						if(name == row.name){
							falg = true;
						}   
					})
					if(!falg){
						var $content = $('<li id='+ row.id +'>'+ row.name +'</li>')
						$('#choose_hardware_main').append($content);
					}
				}else{
					var content = $('<li id='+ row.id +'>'+ row.name +'</li>')
					$('#choose_hardware_main').append(content);
				}
	        },  
			frozenColumns : [ [ 
			   {
				width : '10%',
				title : '序号',
				field : 'id',
				sortable : true,
				hidden : true
			}, {
				width : '30%',
				title : '物料名称',
				field : 'name',
				sortable : true
			},  {
				width : '20%',
				title : '物料类型',
				field : 'typeName',
				sortable : true
			},{
				width :  '15%',
				title : '基础物料',
				field : 'isbase',
				sortable : true,
				formatter : function(value, row, index) {
					switch (value) {
						case 1:
							return '否';
						case 2:
							return '是';
					}
				}
			},{
				width : '35%',
				title : '描述',
				field : 'description'
			} ] ]
		});
		$('#choose_hardware_main').off('dblclick','li').on('dblclick','li',function(){
			$(this).remove();
		});
		$('#hardware_materialName').off('keydown').on('keydown',function(event){
			 if (event.keyCode == 13) {//keyCode=13是回车键
				/*  console.log(event.keyCode)
               	 alert(event.keyCode)
			 	 return false; */
				 dataGrid.datagrid('load', {
						name:$('#hardware_materialName').val().trim(),
					});
             }
		});
		
	});
	function searchFun() {
		dataGrid.datagrid('load', {
			name:$('#hardware_materialName').val().trim(),
		});
	}
	function cleanFun() {
		$('#hardware_materialName').val('');
		//dataGrid.datagrid('load', {});
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'west',border:false" title="" style="overflow:auto;padding: 3px;width:30%;" >
		<div class="choose">
			<div class="title">已选物料</div>
			<div ><ul id="choose_hardware_main"></ul></div>
		</div>
		
	</div>
	<div data-options="region:'center',border:false" title="" style="overflow: auto;" >
		<div class="easyui-layout" data-options="fit:true,border:false" >
			<div data-options="region:'north',border:false" title="" style="overflow: auto;padding: 3px;" >
			<!-- 	<form id="searchForm"> -->
					<table>
						<tr>
							<th>物料名称:</th>
							<td><input name="name" placeholder="请输入物料名称" id="hardware_materialName"/></td>
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
