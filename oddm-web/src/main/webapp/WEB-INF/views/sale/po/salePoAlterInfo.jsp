<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>
	.datagrid-view .datagrid-body {overflow-y: auto;overflow-x: hidden;}
</style>
<script type="text/javascript">
	var dataGrid;
	var poId = "${poId}";
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
			url : '${ctx}/salePo/dataGridAltInfo?poId='+poId,
			striped : true,
			rownumbers : true,
			pagination : true,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'desc',
			pageSize : 50,
			nowrap:false,
			pageList : [ 10, 20, 30, 40, 50, 100, 200],
			view: myview,
            emptyMsg: '没有找到相关数据',
			onLoadSuccess:function(data){ emptyMsg
				
            },
			frozenColumns : [ [ {
				width : '10%',
				title : '序号',
				field : 'id',
				sortable : true,
				hidden : true
			}, {
				width : '17%',
				title : '变更时间',
				field : 'timestamp',
				sortable : true,
				formatter:function(value,row,index){  
	                return dateformatter(value);
                }
			},  {
				width : '18%',
				title : '变更项',
				field : 'alteritem',
				sortable : true
			},{
				width : '25%',
				title : '变更前',
				field : 'beforeInfo'
			} ,{
				width : '25%',
				title : '变更后',
				field : 'afterInfo'
			} ,{
				width : '9%',
				title : '操作人',
				field : 'operator'
			},{
				width : '7%',
				title : '操作',
				field : 'operateType',
				formatter : function(value, row, index) {
					switch (value) {
						case 1:
							return '增加';
						case 2:
							return '删除';
						case 3:
							return '修改';
					}
				}

			} ] ]
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow: auto;padding: 3px;" >
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
</div>
<script src="${ctx}/lib/util/util_date.js" type="text/javascript" charset="utf-8"></script>