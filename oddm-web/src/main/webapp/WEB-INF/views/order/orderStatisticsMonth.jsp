<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<style type="text/css">
	.datagrid-view .datagrid-body {overflow-y: auto;overflow-x: hidden;}
	.color_F60 {color: #F60}
	.color_green {color: green;}
	.color_blue {color: blue;}
	a{cursor: pointer;text-decoration: none;color: #000}
	.autoInput{width: 100%}
	.datagrid-cell {   /* easyui不会自动识别英文字符串自动换行，nowrap: false不生效 */
		white-space: pre-wrap;
		word-wrap: break-word;
		word-break:break-all;
		line-height: 18px;
	}
	input {border:1px solid #CCC;font-size:12px;height:19px;border-radius: 4px;}
</style> 
<title>订单信息</title>
	<script type="text/javascript">
	var dataGrid;
	//默认查询当月
	var defaultDate = new Date();
	var dateOrderMonth = defaultDate.getFullYear() + '-' + (defaultDate.getMonth()+1);
	var db;
	$(function() {
		//初始化月份选择框
		db = $('#datemonth');
		getMothDb(db);
        $('#datemonth').parent().children().css({
        	"width":"80%",
        });
        var first = 1;//用来修复第一次进入页面就搜索的bug
        $('#datemonth').datebox({
            onChange: function(date){
            	if(first != 1) {
            		searchFun();
            	} else {
            		first ++;
            	}
            }
        });
        $('#datemonth').datebox('setValue', dateOrderMonth);//的放在onChange后面，否则有bug
		$.extend($.fn.datagrid.methods, {//重写合并单元格方法
		    autoMergeCells: function(jq, fields) {
		        return jq.each(function() {
		            var target = $(this);
		            if (!fields) {
		                fields = target.datagrid("getColumnFields");
		            }
		            var rows = target.datagrid("getRows");
		            var i = 0,
		            j = 0,
		            temp = {};
		            for (i; i < rows.length; i++) {
		                var row = rows[i];
		                j = 0;
		                for (j; j < fields.length; j++) {
		                    var field = fields[j];
		                    var tf = temp[field];
		                    if (!tf) {
		                        tf = temp[field] = {};
		                        tf[row[field]] = [i];
		                    } else {
		                        var tfv = tf[row[field]];
		                        if (tfv) {
		                            tfv.push(i);
		                        } else {
		                            tfv = tf[row[field]] = [i];
		                        }
		                    }
		                }
		            }
		            $.each(temp,
		            function(field, colunm) {
		                $.each(colunm,
		                function() {
		                    var group = this;
		 
		                    if (group.length > 1) {
		                        var before, after, megerIndex = group[0];
		                        for (var i = 0; i < group.length; i++) {
		                            before = group[i];
		                            after = group[i + 1];
		                            if (after && (after - before) == 1) {
		                                continue;
		                            }
		                            var rowspan = before - megerIndex + 1;
		                            if (rowspan > 1) {
		                                target.datagrid('mergeCells', {
		                                    index: megerIndex,
		                                    field: field,
		                                    rowspan: rowspan
		                                });
		                            }
		                            if (after && (after - before) != 1) {
		                                megerIndex = after;
		                            }
		                        }
		                    }
		                });
		            });
		        });
		    }
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
			url : '${ctx}/order/dataGridStatisticsMonth?dateOrderMonth='+dateOrderMonth,
			striped : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'dateOrder',
			sortOrder : 'asc',
			nowrap:false,
			pageSize : 999999,
			pageList : [ 10, 20, 30, 40, 50, 100, 999999 ],
			view: myview,
            emptyMsg: '没有找到相关数据',
			onLoadSuccess:function(data){ 
            },
            onDblClickRow : function(index, row){
            	if (row.id != null) {
    				showOrderFun(row.id,row.orderNo);
            	}
	        },
            rownumbers : true,
			frozenColumns : [ [ {
				width : '5%',
				field : 'dateOrderWeek',
			}, {
				field : 'dateOrder',
				title : '下单日期',
				width : '10%',
				sortable : true,
			}, {
				width : '12%',
				title : '订单号',
				field : 'orderNo',
				formatter : function(value, row, index) {
					if(row.pid ==  null){
						return value + '<span class="color_F60">[新单]</span>';
					}else{
						return value;
					} 
				}
			},{
				width : '14%',
				title : '客户名称',
				field : 'clientName',
			},{
				width : '8%',
				title : '所属业务',
				field : 'seller',
			},{
				width : '8%',
				title : '所属跟单',
				field : 'merchandiser',
			},{
				width : '8%',
				title : '国家',
				field : 'district',
			},{
				width : '11%',
				title : '客户机型',
				field : 'productClient',
			},{
				width : '9%',
				title : '工厂机型',
				field : 'productFactory',
			},{
				width : '6%',
				title : '产品类型',
				field : 'productType',
			},{
				width : '6%',
				title : '订单数量',
				field : 'quantity',
				formatter : function(value, row, index) {
					return formatNum(value);
				}
			}, {
				field : 'none',
				title : '操作',
				width : '5%',
				formatter : function(value, row, index) {
					if (value == "noneFlag") return "";
					var str = '&nbsp;';
					//str += $.formatString('<img onclick="showOrderFun(\'{0}\',\'{1}\');" src="{2}" title="查看" style="cursor:pointer"/></img>', row.id,row.orderNo,"${ctx}/jslib/easyui1.4.2/themes/icons/search.png");
					str += $.formatString('<img onclick="showOrderFun(\'{0}\',\'{1}\');" src="{2}" title="查看" style="cursor:pointer"/></img>', row.id,row.orderNo,"${ctx}/jslib/easyui1.4.2/themes/icons/search.png");
					return str;
				}
			}   ] ],
			toolbar : '#toolbar',
			onLoadSuccess: function(data) {
				$('.datagrid-btable, .datagrid-btable tr td').css({"border-collapse":"collapse","border":"1px solid #ccc"});
		      //表头加粗
			/* 	$(".datagrid-header-row td div span").each(function(i,th){
		     		var val = $(th).text();
		     		 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
		     	}); */
				//插入统计行
				var rows = dataGrid.datagrid('getRows')//获取当前的数据行
	            var quantitys=0;  //统计订单总数
	            var quantityss=0;  //统计订单总数
	            var week = "第1周";
	            var tempWeek;
	            var i = 0;
	            var orderTotal = 0;
	            for (i; i < rows.length; i++) {//遍历所有行
	            	if (i == 0) {
	            		week = rows[i]['dateOrderWeek'];
	            	}
	            	tempWeek = rows[i]['dateOrderWeek'];
	            	if (week != tempWeek) {
	            		week = rows[i]['dateOrderWeek'];
	            		dataGrid.datagrid('insertRow',{//追加行
	            			index: i,	// 索引从0开始
	            			row: {
	            				dateOrderWeek: rows[(i-1)]['dateOrderWeek'],
	            				productType: '合计',
								quantity: formatNum(quantitys),
								none : "noneFlag",
								pid: "-11",
	            			}
						});
	            		quantitys = 0;
	            	} else {
	            		orderTotal++;
		            	quantitys += rows[i]['quantity'];
		            	quantityss += rows[i]['quantity'];
	            	}

	            }
	            if (i > 0) {
		           //添加最后一行统计
	        	   dataGrid.datagrid('appendRow',{
	        		   	dateOrderWeek: week,
						productType: '合计',
						quantity: formatNum(quantitys),
						none : "noneFlag",
						pid: "-11",
					});
	            	dataGrid.datagrid('appendRow',{
						productType: '总计',
						quantity: formatNum(quantityss),
						none : "noneFlag",
						pid: "-11",
						productClient: "共"+orderTotal+"单",
					});
	            }
	            //所有列进行合并操作
		        //$(this).datagrid("autoMergeCells");
		        //指定列进行合并操作
		        $(this).datagrid("autoMergeCells", ['dateOrderWeek']);
				$('.datagrid-btable, .datagrid-btable tr td').css({"border-collapse":"collapse","border-bottom":"1px solid #ccc"});
			},
			//改变行的css
			rowStyler:function(index,row){
		       if (row.none == "noneFlag"){
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
	
	function showOrderFun(id,orderNo){
	  	//var url = "${ctx}/order/getPage?id="+id;
  		var url = "${ctx}/order/getPage?id="+id;
	  	var title = "订单:"+orderNo;
		window.parent.addTab({
			url : url,
			title : title,
		});
	}
	
	function searchFun() {
		var orderNo = $('#orderNo').val().trim();
		var clientName = $('#clientName').val().trim();
		var productClient = $('#productClient').val().trim();
		var seller = $('#seller').val().trim();
		var productFactory = $('#productFactory').val().trim();
		dateOrderMonth = $('#datemonth').datebox('getValue');
		if(dateOrderMonth == "" && orderNo == "" && clientName == "" && productClient == "" && seller == "" && productFactory == ""){
			parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
		}else{
			dataGrid.datagrid({url : '${ctx}/order/dataGridStatisticsMonth?dateOrderMonth='+dateOrderMonth
					+"&orderNo="+orderNo
					+"&clientName="+clientName
					+"&productClient="+productClient
					+"&productFactory="+productFactory
					+"&seller="+seller
					});  
		}  
	}
	function cleanFun() {
		$('#clientName').val('');
		$('#orderNo').val('');
		$('#productClient').val('');
		$('#seller').val('');
		$('#productFactory').val('');
		
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
	function yearFun() {
		var url = "${ctx}/order/getYearStatPage?";
	  	var title = "订单年度汇总";
		window.parent.addTab({
			url : url,
			title : title,
		});
	}
	function yearCharFun() {
		var url = "${ctx}/order/getYearStatChartPage?";
	  	var title = "订单年度汇总曲线";
		window.parent.addTab({
			url : url,
			title : title,
		});
	}
	
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	 <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
		<!-- <form id="searchForm"> -->
			<table>
				<tr>
					<th style="width: 6%;text-align: right">订单编号:</th>
					<td style="width: 10%"><input name="orderNo" placeholder="请输入订单编号" id="orderNo" class="autoInput"/></td>
					<th style="width: 6%;text-align: right">客户名称:</th>
					<td style="width: 10%"><input name="clientName" placeholder="请输入客户名称" id="clientName" class="autoInput"/></td>
					<th style="width: 6%;text-align: right">所属业务:</th>
					<td style="width: 10%"><input name="seller" placeholder="请输入业务名称" id="seller" class="autoInput"/></td> 
					<th style="width: 6%;text-align: right">下单月份:</th> 
					<td style="width: 8%"><input type="text" id="datemonth"></td> 
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="yearFun();">年度汇总</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="yearCharFun();">年度曲线</a>
					</td> 
				</tr>
				<tr>
					<th style="width: 6%;text-align: right">客户机型:</th>
					<td style="width: 10%"><input name="productClient" placeholder="请输入客户机型" id="productClient" class="autoInput"/></td>
					<th style="width: 6%;text-align: right">工厂机型:</th>
					<td style="width: 10%"><input name="productFactory" placeholder="请输入工厂机型" id="productFactory" class="autoInput"/></td>
				</tr>
			</table>
		<!-- </form> -->
	</div> 
	
	<div data-options="region:'center',border:false" style="top: 60px">
		<table id="dataGrid" data-options="fit:true,border:true" ></table>
	</div> 
	<div id="toolbar" style="display: none;">
	</div>
</body>
</html>
<script src="${ctx}/lib/util/util_common.js" type="text/javascript" charset="utf-8"></script>