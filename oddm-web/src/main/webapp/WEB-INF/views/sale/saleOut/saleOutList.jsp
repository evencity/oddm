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
	.autoInput{width: 100%}
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
	//自定义视图，没有数据则显示 ‘没有找到相关数据’
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
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}' + '/saleOut/dataGrid',
			striped : true,
			//fit:true,//自动适应父容器，可以在body设置style="width: 1500px
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			nowrap:false,// :当true时，显示数据在同一行上。默认true。 false自动换行显示
			idField : 'id', //编辑、删除操作按钮用到 row.key
			sortName : 'shipmentDate',
			sortOrder : 'desc',
	        view: myview,
	        emptyMsg: '没有找到相关数据',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200],
			frozenColumns : [ [{
				width : '10%',
				title : '订单号',
				field : 'orderNo',
				sortable : true,
				align:'left',
			},{
				width : '14%',
				title : '录入时间',
				field : 'updatetime',
				sortable : true,
				align:'left',
				formatter:function(value,row,index){
	                return dateformatter(value);
                }
			}, {
				width : '18%',
				title : '客户名称',
				field : 'clientName',
				align:'left',
			}, {
				width : '7%',
				title : '销售',
				field : 'seller',
				align:'left',
				sortable : true,
			},{
				width : '8%',
				title : '国家',
				field : 'district',
				sortable : true,
				align:'left',
			},{
				width : '7%',
				title : '产品名称',
				field : 'productType',
				align:'left',
				sortable : true,
			},{
				width : '7%',
				title : '订单数量',
				field : 'quantity',
				align:'left',
				sortable : true,
				formatter : function(value, row, index) {
					return formatNum(value);
				}
			},{
				width : '7%',
				title : '出货数',
				field : 'shipmentNo',
				align:'left',
				formatter : function(value, row, index) {
					return formatNum(value);
				}
			},{
				width : '7%',
				title : '总出货数',
				field : 'shipmentTotal',
				align:'left',
				formatter : function(value, row, index) {
					return formatNum(value);
				}
			},{
				width : '9%',
				title : '出货日期',
				field : 'shipmentDate',
				align:'left',
				sortable : true,
				formatter:function(value,row,index){
	                return dayformatter(value);
                } 
			},{
				width : '6%', //备注一定得7%以上，否则滚动条会变大
				title : '备注',
				field : 'none',
				align:'left',
				formatter : function(value, row, index) {
					if(row.quantity <= row.shipmentTotal) {
						return "<span style=\"color: green;\">已出完</span>"
					} else {
						return "<span style=\"color: #F60;\">未出完</span>"
					}
				},
			}
		  ] ],
			toolbar : '#toolbar',
		});
		
		$("body").keydown(function(event) {
		    if (event.keyCode == "13") {//keyCode=13是回车键
		    	searchFun();
		    }
		});
		//只显示月份
		db = $('#datemonth');
        db.datebox({
        	 editable:false,
             onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
                 span.trigger('click'); //触发click事件弹出月份层
                 if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                     tds = p.find('div.calendar-menu-month-inner td');
                     tds.click(function (e) {
                         e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                         var year = /\d{4}/.exec(span.html())[0]//得到年份
                         , month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
                         db.datebox('hidePanel')//隐藏日期对象
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
		// db.datebox('setValue', defaultDate.getFullYear() + '-' + (defaultDate.getMonth() + 1)); 
         var p = db.datebox('panel'), //日期选择对象
             tds = false, //日期选择对象中月份
             aToday = p.find('a.datebox-current'),
             yearIpt = p.find('input.calendar-menu-year'),//年份输入框
             //显示月份层的触发控件
             span = aToday.length ? p.find('div.calendar-title span') ://1.3.x版本
             p.find('span.calendar-text'); //1.4.x版本
         if (aToday.length) {//1.3.x版本，取消Today按钮的click事件，重新绑定新事件设置日期框为今天，防止弹出日期选择面板
             aToday.unbind('click').click(function () {
                 var now=new Date();
                 db.datebox('hidePanel').datebox('setValue', now.getFullYear() + '-' + (now.getMonth() + 1));
             });
         } 
         $('#datemonth').parent().children().css({
        	 "width":"80%",
         });
         //var first = 1;//用来修复第一次进入页面就搜索的bug
         $('#datemonth').datebox({
             onChange: function(date){
            	 searchFun();
             }
         });
         //表头加粗
         /* $(".datagrid-header-row td div span").each(function(i,th){
     		var val = $(th).text();
     		 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
     	}); */
		/****底部*****/
	
	});
	
	
	function searchFun() {
		var orderNo = $('#ip_orderNo').val().trim();
		var clientName = $('#ip_clientName').val().trim();
		var seller = $('#ip_seller').val().trim();
		var shipmentMonth = $('#datemonth').datebox('getValue');
		if(orderNo == "" && clientName == "" && seller == "" && shipmentMonth == ""){
			parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
		}else{
			if (shipmentMonth == "") {
				dataGrid.datagrid('load', {
					orderNo:orderNo,
					clientName:clientName,
					seller:seller,
				}); 
			} else {//月份不为空，则另起一页
				var url = '${ctx}' + '/saleOut/getMonthPage?orderNo='+orderNo+'&clientName='+clientName+'&seller='+seller+'&shipmentMonth='+shipmentMonth;
			  	var title = "出库汇总:"+shipmentMonth;
				window.parent.addTab({
		  			id : shipmentMonth, // 给iframe赋予一个id，方便从新打开判断是否是相同的单
					url : url,
					title : title,
				});
			}
		}
	}
	function cleanFun() {
		$('.autoInput').val('');
		//dataGrid.datagrid('load', {});
		 db.datebox('setValue', ""); 
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
		    	getMothDb(parent.$('#shipmentMonth'));
		    	parent.$('#shipmentMonth').datebox('setValue', new Date().getFullYear()+'-'+(new Date().getMonth() + 1))
		    },
		    buttons:[{
				text:'确定',
				handler:function(){
		 			var orderNo = parent.$('#orderNo').val().trim();
					var clientName = parent.$('#clientName').val().trim();
		 			var seller = parent.$('#seller').val().trim();
		 			var district = parent.$('#district').val().trim();
		 			var shipmentMonth = parent.$('#shipmentMonth').datebox('getValue');
		 		/* 	if (clientName == "" && seller == "" && productType == "") {
		 				//设置初始值
						dateOrderStart = new Date().getFullYear()+'-01-01';
			 			dateOrderEnd = parent.$('#dateOrderEnd').datebox('getValue');
			 			//parent.$('#dateOrderEnd').datebox('setValue', '');//清空
		 			} */
		 			download_file("${ctx}/saleOut/excelExport?clientName="+clientName+"&seller="+seller
		 					+"&district="+district
		 					+"&orderNo="+orderNo
		 					+"&shipmentMonth="+shipmentMonth);
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
	
	var finish = true;
	function unfinishedFun() {
		if (finish) {
			finish = false;
		} else {
			finish = true;
		}
		dataGrid.datagrid('load', {
			finish : finish,
		});
	}
	function currMonStatFun() {
		var defaultDate = new Date();
		var shipmentMonth = defaultDate.getFullYear() + '-' + (defaultDate.getMonth() + 1);
		var url = '${ctx}' + '/saleOut/getMonthPage?shipmentMonth='+shipmentMonth;
	  	var title = "出库汇总:"+shipmentMonth;
		window.parent.addTab({
  			id : shipmentMonth, // 给iframe赋予一个id，方便从新打开判断是否是相同的单
			url : url,
			title : title,
		});
	}
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false"">
	<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
		<table>
			<tr>
				<th style="width: 6%;text-align: right">订单编号:</th>
					<td style="width: 10%"><input name="orderNo" placeholder="请输入订单编号" id="ip_orderNo" class="autoInput"/></td>
				<th style="width: 6%;text-align: right">客户名称:</th>
					<td style="width: 10%"><input name="clientName" placeholder="请输入客户名称" id="ip_clientName" class="autoInput"/></td>
				<th style="width: 4%;text-align: right">销售:</th>
					<td style="width: 10%"><input name="seller" placeholder="请输入业务名称" id="ip_seller" class="autoInput"/></td> 
				<th style="width: 6%;text-align: right">出货月份:</th>
					<td style="width: 10%"><input type="text" id="datemonth"></td> 
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="unfinishedFun();">未出完</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="currMonStatFun();">当月汇总</a>
					</td>
			</tr>
		</table>
	</div>
	
	<div data-options="region:'center',border:false">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		 <c:if test="${fn:contains(sessionInfo.resourceList, '/saleOut/excelAll')}">
			<a onclick="excelFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">导出excel</a>
		</c:if>
	</div>
	<div id="dialogExcelExport" ></div>
	<div id="dialogExcelExportPage"  style="display: none;" >
        <%@ include file="saleOutExcelExport.jsp" %>  
    </div>
</body>
<script src="${ctx}/lib/util/util_common.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/lib/util/util_date.js" type="text/javascript" charset="utf-8"></script>
</html>