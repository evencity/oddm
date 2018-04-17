<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<style type="text/css">
	.datagrid-view .datagrid-body {overflow-y: auto;overflow-x: hidden;}
	.autoInput{width: 95%}
	.datagrid-cell {   /* easyui不会自动识别英文字符串自动换行，nowrap: false不生效 */
		white-space: pre-wrap;
		word-wrap: break-word;
		word-break:break-all;
		line-height: 18px;
	}
	input {border:1px solid #CCC;font-size:12px;height:19px;border-radius: 4px;}
</style>
<script type="text/javascript">	
	var dataGrid;
	var db;
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
			url : '${ctx}' + '/salePi/dataGrid',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			nowrap: false,// :当true时，显示数据在同一行上。默认true。 false自动换行显示
			idField : 'id', //编辑、删除操作按钮用到 row.key
			sortName : 'id',
			sortOrder : 'desc',
			onDblClickRow : function(index, row){
            	showDetail(row.id, row.piNo);
	        },
	        view: myview,
	        emptyMsg: '没有找到相关数据',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200],
			frozenColumns : [ [ {
				field:'ck',
				checkbox:true,
				width : '5%'
			},{
				width : '10%',
				title : 'PI编号',
				field : 'piNo',
				sortable : true,
				align:'left',
			},{
				width : '14%',
				title : '更新时间',
				field : 'updatetime',
				sortable : true,
				align:'left',
				formatter:function(value,row,index){
	                return dateformatter(value);
                } 
			},{
				width : '9%',
				title : '下单日期',
				field : 'orderDate',
				align:'left',
				sortable : true,
				formatter:function(value,row,index){
	                return dayformatter(value);
                }
			},{
				width : '10%',
				title : 'PO编号',
				field : 'poNo',
				sortable : true,
				align:'left',
				formatter:function(value,row,index){
					if (value != null && value.length > 10) {
						value = value.replace(/\//g, '<br>'); 
					}
	                return value;
                }
			}, {
				width : '13%',
				title : '客户名称',
				field : 'clientName',
				align:'left',
			}, {
				width : '7%',
				title : '业务员',
				field : 'seller',
				align:'left',
			}, {
				width : '8%',
				title : '国家',
				field : 'district',
				align:'left',
			},{
				width : '10%',
				title : '摘要',
				field : 'summary',
				align:'left',
			},{
				width : '8%',
				title : '总金额',
				field : 'amount',
				align:'left',
				sortable : true,
				formatter : function(value, row, index) {
					if (value == null) {
						return 0;
					}
					return toMoney((value).toFixed(2), 2);
				}
			},{
				width : '4%',
				title : '币种',
				field : 'currency',
				align:'left',
				sortable : true,
			}, {
				field : 'action',
				title : '操作',
				width : '4%',
				align:'left',
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					str += $.formatString('<img onclick="showDetail(\'{0}\',\'{1}\');" src="{2}" title="查看" style="cursor:pointer"/></img>', row.id, row.piNo,"${ctx}/jslib/easyui1.4.2/themes/icons/search.png");
					return str;
				}
			}
		  ] ],
			toolbar : '#toolbar'
		});
		
		$("body").keydown(function(event) {
		    if (event.keyCode == "13") {//keyCode=13是回车键
		    	searchFun();
		    }
		});
		
		//只显示月份
		db = $('#datemonth');
		getMothDb(db);           
             
       //最底部 
	});
	var i=10000;
	function addFun(){
		i++;
		var url = "${ctx}/salePi/addPage?";
	  	var title = "添加PI";
		window.parent.addTab({
			id : i,
			url : url,
			title : title,
		});
	}
	
	function showDetail(id, title){
	  	var url = "${ctx}/salePi/getPage?id="+id;
	  	var title = "查看PI:"+title;
  		window.parent.addTab({
  			id : id, // 给iframe赋予一个id，方便从新打开判断是否是相同的单
			url : url,
			title : title,
		});
	}
	function deleteFun() {
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		var id = row.id;
		parent.$.messager.confirm('询问', '您是否要删除当前记录，PI编号：'+row.piNo+'？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/salePi/delete', {
					id : id
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('clearSelections');
						dataGrid.datagrid('reload');
					}
					progressClose();
				}, 'JSON');
			}
		});
	}
	
	function editFun(){
		var row = dataGrid.datagrid('getSelected');
		if(row == null){
			parent.$.messager.alert('提示', '请先选择一行', 'info');
			return;
		}
		var id = row.id;
	  	var url = '${ctx}/salePi/editPage?id=' + id;
	  	var title = "编辑PI:"+row.piNo;
		window.parent.addTab({
			id : id,
			url : url,
			title : title,
		});
		
	}
	function searchFun() {
		var piNo = $('#ip_piNo').val().trim();
		var poNo = $('#ip_poNo').val().trim();
		var clientName = $('#ip_clientName').val().trim();
		var seller = $('#ip_seller').val().trim();
		var orderMonth = $('#datemonth').datebox('getValue');
		if(poNo == "" && piNo == "" && clientName == "" && seller == "" && orderMonth == ""){
			parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
		}else{
			dataGrid.datagrid('load', {
				piNo: piNo,
				poNo: poNo,
				clientName: clientName,
				orderMonth: orderMonth,
				seller:seller
			}); 
		} 
	}
	function cleanFun() {
		$('.autoInput').val('');
		//dataGrid.datagrid('load', {});
	}
	function excelFun() {
		var content = $('#dialogExcelExportPage').html();
		parent.$.modalDialog({
		    title: '导出Excel',
			width : 400,
			height : 250,
		    closed: false,
		    cache: false,
		    content: content,
		    modal: true,
		    onOpen: function(){
				//parent.$('#dateOrderStart').datebox('setValue', new Date().getFullYear()+'-01-01');//设置错误，可以自动默认为当前日期
				//parent.$('#dateOrderEnd').datebox('setValue', '23432432');//设置错误，可以自动默认为当前日期
		    	//可以设置初始值
		    	getMothDb(parent.$('#orderMonth'));
		    	parent.$('#orderMonth').datebox('setValue', new Date().getFullYear()+'-'+(new Date().getMonth() + 1))
		    },
		    buttons:[{
				text:'确定',
				handler:function(){
					var clientName = parent.$('#clientName').val().trim();
		 			var seller = parent.$('#seller').val().trim();
		 			var district = parent.$('#district').val().trim();
		 			var orderMonth = parent.$('#orderMonth').datebox('getValue');
		 		/* 	if (clientName == "" && seller == "" && productType == "") {
		 				//设置初始值
						dateOrderStart = new Date().getFullYear()+'-01-01';
			 			dateOrderEnd = parent.$('#dateOrderEnd').datebox('getValue');
			 			//parent.$('#dateOrderEnd').datebox('setValue', '');//清空
		 			} */
		 			download_file("${ctx}/salePi/excelExport?clientName="+clientName+"&seller="+seller
		 					+"&district="+district
		 					+"&orderMonth="+orderMonth);
				}
			}]
		}); 
	}
	function download_file(url) {  
        if (typeof (download_file.iframe) == "undefined") {  
            var iframe = document.createElement("iframe");  
            download_file.iframe = iframe; 
            document.body.appendChild(download_file.iframe);  
            download_file.iframe.addEventListener( "load", function(){
    	         //代码能执行到这里说明已经载入成功完毕了
    	           this.removeEventListener( "load", arguments.call, false);
    	         //这里是回调函数
    	         parent.parent.$.messager.alert('提示', '导出Excel出错，请联系管理员！');
    	   }, false);
        }  
    	download_file.iframe.src = url;  
        download_file.iframe.style.display = "none";  
    } 
	
	//是一个iput框变为月份输入，传入jquery对象$('#orderMonth')
	function getMothDb(mothDb) {
		mothDb.datebox({
       	 editable:false,
            onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
                span.trigger('click'); //触发click事件弹出月份层
                if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                    tds = p.find('div.calendar-menu-month-inner td');
                    tds.click(function (e) {
                        e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                        var year = /\d{4}/.exec(span.html())[0]//得到年份
                        , month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
                        mothDb.datebox('hidePanel')//隐藏日期对象
                        .datebox('setValue', year + '-' + month); //设置日期的值
                    });
                }, 0);
                yearIpt.unbind();//解绑年份输入框中任何事件
            },
            parser: function (s) {
                if (!s) return new Date();
                var arr = s.split('-');
                return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
            },
            formatter: function (d) { return d.getFullYear() + '-' + (d.getMonth() + 1);//getMonth返回的是0开始的，忘记了。。已修正 
            }
        });
       // var defaultDate = new Date();//可以自动默认为当前月
		// mothDb.datebox('setValue', defaultDate.getFullYear() + '-' + (defaultDate.getMonth() + 1)); 
        var p = mothDb.datebox('panel'), //日期选择对象
            tds = false, //日期选择对象中月份
            aToday = p.find('a.datebox-current'),
            yearIpt = p.find('input.calendar-menu-year'),//年份输入框
            //显示月份层的触发控件
            span = aToday.length ? p.find('div.calendar-title span') ://1.3.x版本
            p.find('span.calendar-text'); //1.4.x版本
        if (aToday.length) {//1.3.x版本，取消Today按钮的click事件，重新绑定新事件设置日期框为今天，防止弹出日期选择面板
            aToday.unbind('click').click(function () {
                var now=new Date();
                mothDb.datebox('hidePanel').datebox('setValue', now.getFullYear() + '-' + (now.getMonth() + 1));
            });
        } 
	}
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
		<table>
			<tr>
				<th>PI编号:</th>
					<td><input name="piNo" placeholder="请输入PI编号" id="ip_piNo" class="autoInput"/></td>
				<th>客户名称:</th>
					<td><input name="clientName" placeholder="请输入客户名称" id="ip_clientName" class="autoInput"/></td>
				<th>销售:</th>
					<td><input name="seller" placeholder="请输入业务名称" id="ip_seller" class="autoInput"/></td> 
				<th>下单月份:</th>
					<td><input type="text" id="datemonth"></td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
					</td>
			</tr>
			<tr>
			<th>PO编号:</th>
					<td><input name="poNo" placeholder="请输入订单编号" id="ip_poNo" class="autoInput"/></td>
			</tr>
		</table>
	</div>
	
	<div data-options="region:'center',border:false">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/salePi/addAll')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/salePi/editAll')}">
			<a onclick="editFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">编辑</a>
		</c:if> 
		<c:if test="${fn:contains(sessionInfo.resourceList, '/salePi/deleteAll')}">
			<a onclick="deleteFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-del'">删除</a>
		</c:if> 
		 <c:if test="${fn:contains(sessionInfo.resourceList, '/salePi/excelAll')}">
			<a onclick="excelFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">导出excel</a>
		</c:if>
	</div>
	<div id="dialogExcelExport" ></div>
	<div id="dialogExcelExportPage"  style="display: none;" >
        <%@ include file="salePiExcelExport.jsp" %>  
    </div>
</body>
<script src="${ctx}/lib/util/util_date.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/lib/util/util_common.js" type="text/javascript" charset="utf-8"></script>
</html>