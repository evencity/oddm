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
	.color_F60 {color: #F60}
	.color_green {color: green;}
	.color_blue {color: blue;}
	a{cursor: pointer;text-decoration: none;color: #000}
	.autoInput{width: 100%;border:1px solid #CCC;border-radius: 4px;}
	.datagrid-cell {   /* easyui不会自动识别英文字符串自动换行，nowrap: false不生效 */
		white-space: pre-wrap;
		word-wrap: break-word;
		word-break:break-all;
		line-height: 16px;
	}
	div.datagrid-pager.pagination { /* 屏蔽分页显示*/
		display: none;
	}
	
</style> 
<title>订单信息</title>
	<script type="text/javascript">
	var dataGrid;
	//默认查询当月
	var defaultDate = new Date();
	var currentYear = defaultDate.getFullYear();
	$(function() {
		//获取币种
		var jsonCurrency = "${sysConfig.value}";
		var dataCurr = [];
		if (jsonCurrency != null && ""!=jsonCurrency && typeof(jsonCurrency) != "undefined") {
			var vv = jsonCurrency.split('|');
			for (var key in vv){
				var arr = {value: vv[key]}; dataCurr.push(arr);
			}
		} else {
			dataCurr.push({value: "USD"});
			dataCurr.push({value: "RMB"});
		}
		$('#currency').combobox({
			textField: 'value',
			editable: false,
			value: 'USD',
			panelHeight : 'auto',
			data: dataCurr,
			onChange: function(newValue, oldValue){
				searchFun();//查询
			}
		});
		var dataCurr = [];dataCurr.push({value: "业务名称"});dataCurr.push({value: "客户名称"});dataCurr.push({value: "事业部"});;dataCurr.push({value: "国家地区"})
		$('#category').combobox({
			textField: 'value',
			panelHeight : 'auto',
			editable: false,
			data: dataCurr,
			value: "业务名称",//默认值
			onChange: function(newValue, oldValue){
				searchFun();//查询
			}
		});
		//初始化年份选择框
        $('#dateYear').val(currentYear);
        $('#dateYear').numberspinner({
        	required: true,
			min: 2016,
            max: currentYear,
        	onSpinUp: function() {
        		searchFun();//查询
        	},
        	onSpinDown: function() {
        		searchFun();//查询
        	},
        });
        $('#category, #currency, #dateYear').parent().children().css({
       		"width":"100%",
        });
        $('#category, #currency, #dateYear').next().find('input').css({
        	"width":"100%",
        });
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
			url : '${ctx}/saleInfo/dataGridStatisticsYear?year='+currentYear+'&type=1'+'&currency=USD',
			striped : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'dateOrder',
			sortOrder : 'asc',
			nowrap:false,
			pageSize : 2000000,
			pageList : [ 10, 20, 30, 40, 50, 100, 2000000 ],
			view: myview,
            emptyMsg: '没有找到相关数据',
			onLoadSuccess:function(data){ 
            },
            rownumbers : true,
			frozenColumns : [ [ {
				title : '名称',
				width : '10%',
				field : 'name',
				align:'center',
				formatter : function(value, row, index) {
					if (value == null || value.trim() == "") {
						return "其他";
					} else{
						return value;
					}
				}
			},{
				title : '1月',
				width : '6%',
				field : 'quantitysM1',
				align:'center',
				formatter : function(value, row, index) {
					return toMoney(value);
				}
			},{
				title : '2月',
				width : '6%',
				field : 'quantitysM2',
				align:'center',
				formatter : function(value, row, index) {
					return toMoney(value);
				}
			},{
				title : '3月',
				width : '6%',
				field : 'quantitysM3',
				align:'center',
				formatter : function(value, row, index) {
					return toMoney(value);
				}
			},{
				title : '4月',
				width : '6%',
				field : 'quantitysM4',
				align:'center',
				formatter : function(value, row, index) {
					return toMoney(value);
				}
			},{
				title : '5月',
				width : '6%',
				field : 'quantitysM5',
				align:'center',
				formatter : function(value, row, index) {
					return toMoney(value);
				}
			},{
				title : '6月',
				width : '6%',
				field : 'quantitysM6',
				align:'center',
				formatter : function(value, row, index) {
					return toMoney(value);
				}
			},{
				title : '7月',
				width : '6%',
				field : 'quantitysM7',
				align:'center',
				formatter : function(value, row, index) {
					return toMoney(value);
				}
			},{
				title : '8月',
				width : '6%',
				field : 'quantitysM8',
				align:'center',
				formatter : function(value, row, index) {
					return toMoney(value);
				}
			},{
				title : '9月',
				width : '6%',
				field : 'quantitysM9',
				align:'center',
				formatter : function(value, row, index) {
					return toMoney(value);
				}
			},{
				title : '10月',
				width : '6%',
				field : 'quantitysM10',
				align:'center',
				formatter : function(value, row, index) {
					return toMoney(value);
				}
			},{
				title : '11月',
				width : '6%',
				field : 'quantitysM11',
				align:'center',
				formatter : function(value, row, index) {
					return toMoney(value);
				}
			},{
				title : '12月',
				width : '6%',
				field : 'quantitysM12',
				align:'center',
				formatter : function(value, row, index) {
					return toMoney(value);
				}
			},{
				title : '合计',
				width : '9%',
				field : 'total',
				align:'center',
				formatter : function(value, row, index) {
					if (!isNaN(value)) {
						return toMoney(value);
					} else {
						return 0;
					}
				}
			},{
				title : '占比',
				width : '9%',
				field : 'percent',
				align:'center',
				formatter : function(value, row, index) {
					if (row.name == "合计") return value;
					if (!isNaN(row.totals)) {
						return toMoney((row.total)/row.totals*100)+"%";
					} else {
						return 0;
					}
				}
			},
			
		] ],  // background-color: red;
			toolbar : '#toolbar',
			onLoadSuccess: function(data) {
				$('.datagrid-btable, .datagrid-btable tr td').css({"border-collapse":"collapse","border":"1px solid #ccc"});
		      //表头加粗
			 	$(".datagrid-header-row td div span").each(function(i,th){
		     		var val = $(th).text();
		     		$(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
		     		if (val == "1月" || val == "2月" || val == "3月") {
		     			$(this).parent().parent().css({
		     				"background-color":"#99CC00",
		     			});
		     		} else if (val == "4月" || val == "5月" || val == "6月") {
		     			$(this).parent().parent().css({
		     				"background-color":"#FFCC00",
		     			});
		     		} else if (val == "7月" || val == "8月" || val == "9月") {
		     			$(this).parent().parent().css({
		     				"background-color":"#00CCFF",
		     			});
		     		} else  if (val == "10月" || val == "11月" || val == "12月"){
		     			$(this).parent().parent().css({
		     				"background-color":"#CC99FF",
		     			});
		     		}
		     	});
				//插入统计行
				var rows = dataGrid.datagrid('getRows')//获取当前的数据行
	            var quantitysM1=0, quantitysM2=0, quantitysM3=0, quantitysM4=0,
	            	quantitysM5=0, quantitysM6=0, quantitysM7=0, quantitysM8=0,
	            	quantitysM9=0, quantitysM10=0, quantitysM11=0, quantitysM12=0;
		     	var totalAll = 0;
		     	var percentAll = 0;
		     	var totals = 0;//一年的总订单数量
	            var i = 0;
	            for (i; i < rows.length; i++) {//遍历所有行
            		quantitysM1 += rows[i]['quantitysM1'];quantitysM2 += rows[i]['quantitysM2'];
            		quantitysM3 += rows[i]['quantitysM3'];quantitysM4 += rows[i]['quantitysM4'];
            		quantitysM5 += rows[i]['quantitysM5'];quantitysM6 += rows[i]['quantitysM6'];
            		quantitysM7 += rows[i]['quantitysM7'];quantitysM8 += rows[i]['quantitysM8'];
            		quantitysM9 += rows[i]['quantitysM9'];quantitysM10 += rows[i]['quantitysM10'];
            		quantitysM11 += rows[i]['quantitysM11'];quantitysM12 += rows[i]['quantitysM12'];
            		totalAll += rows[i]['total'];
            		if (totals == 0) {
            			totals = rows[i]['totals'];
            		}
	            }
	            percentAll = toMoney(totalAll/totals*100)+"%"
	            if (i > 0) {
		           //添加最后一行统计
	        	   dataGrid.datagrid('appendRow',{
						name: '合计',
						quantitysM1: quantitysM1,quantitysM2: quantitysM2,quantitysM3: quantitysM3,
						quantitysM4: quantitysM4,quantitysM5: quantitysM5,quantitysM6: quantitysM6,
						quantitysM7: quantitysM7,quantitysM8: quantitysM8,quantitysM9: quantitysM9,
						quantitysM10: quantitysM10,quantitysM11: quantitysM11,quantitysM12: quantitysM12,
						total: totalAll,percent: percentAll,
					});
	            }
	            //所有列进行合并操作
		        //$(this).datagrid("autoMergeCells");
		        //指定列进行合并操作
				$('.datagrid-btable, .datagrid-btable tr td').css({"border-collapse":"collapse","border-bottom":"1px solid #ccc"});
			},
			//改变行的css
			rowStyler:function(index,row){
		       if (row.name == "合计"){
		           return 'font-weight: bold;border:1px solid red';
		       }
		   }, 
		});
		
		$("body").keydown(function(event) {
		    if (event.keyCode == "13") {//keyCode=13是回车键
		    	searchFun();
		    }
		});
	});
	
	function searchFun() {
		var isValid = $('#searchForm').form('validate'); // 验证不通过则不执行
		if (!isValid) {
			return; // 验证不通过则不执行
		}
		var currency = $('#currency').combobox('getValue');
		var category = $('#category').combobox('getValue');
		var year = $('#dateYear').val().trim();
		if(currency == "" && category == "" && year == ""){
			parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
		}else{
			var type = 1;
			if (category == "客户名称") {
				type = 2;
			} else if (category == "事业部"){
				type = 3;
			} else if (category == "国家地区"){
				type = 4;
			} else if (category == "工厂机型"){
				type = 5;
			}
			dataGrid.datagrid({url : '${ctx}/saleInfo/dataGridStatisticsYear?year='+year
					+'&currency='+currency
					+'&type='+type
			});  
		}  
	}
	function changeToPercent(num){
		 if(!/\d+\.?\d+/.test(num)){
		  return "0";
		 }
		 var result = (num * 100).toString(),
		  index = result.indexOf(".");
		 if(index == -1 || result.substr(index+1).length <= 4){
		  return result + "%";
		 }
		 return result.substr(0, index + 5) + "%";
		}
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	 <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
		<form id="searchForm">
			<table>
				<tr>
					<th style="width: 8%;text-align: right">分类统计:</th>
					<td style="width: 9%"><input id="category" class=""/></td>
					<th style="width: 5%;text-align: right">币种:</th>
					<td style="width: 7%"><input id="currency" class=""/></td>
					<th style="width: 8%;text-align: right">下单年份:</th> 
					<td style="width: 8%"><input id="dateYear"/></td> 
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
					</td> 
				</tr>
			</table>
		</form>
	</div> 
	
	<div data-options="region:'center',border:false" style="top: 60px">
		<table id="dataGrid" data-options="fit:true,border:true" ></table>
	</div> 
	<div id="toolbar" style="display: none;">
	</div>
</body>
</html>
<script src="${ctx}/lib/util/util_common.js" type="text/javascript" charset="utf-8"></script>