<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
    <meta charset="utf-8">
    <!-- 引入 echarts.js -->
    <script src="${ctx}/lib/echarts/echarts.min.js" type="text/javascript"></script>
    <script type="text/javascript">
    var currency = "USD";//币种默认为USD
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
        $('#currency').parent().children().css({
       		"width":"100%",
        });
        $('#currency').next().find('input').css({
        	"width":"100%",
        });
        searchFun();
	});
	function searchFun() {
		var isValid = $('#searchForm').form('validate'); // 验证不通过则不执行
		if (!isValid) {
			return; // 验证不通过则不执行
		}
		currency = $('#currency').combobox('getValue');
		if(currency == ""){
			parent.$.messager.alert('提示', '输入项不能为空或空格！', 'info');
		}else{
			getChart(currency);
		}  
	}
    </script>
</head>
<body>
	 <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
		<form id="searchForm">
			<table>
				<tr>
					<th style="width: 5%;text-align: right">币种:</th>
					<td style="width: 7%"><input id="currency" class=""/></td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
					</td> 
				</tr>
			</table>
		</form>
	</div> 
	</div>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style=""></div>
    <script type="text/javascript">
    	function getChart(currency) {
	    	var yearMothArray = []; //定义数组
	    	var totalsArray = [];
	    	var quantitySalesArray = [];
			$.ajax({
				url : '${ctx}/saleInfo/dataGridStatisticsAllYear?yearStart=2013&currency='+currency,
				type : "GET",
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				async: true,
				success : function(result) {
					var startPercent = 0;//定义x轴可以从哪个百分比拖动，要显示3年内的曲线
					var maxQuantitySales = 0;//y轴的最大值计算
					var maxTotals = 0;//y轴的最大值计算
					for(var v in result) {
						//console.log(result[v].yearMonth+"\t"+result[v].totals);
						var sp = result[v].yearMonth.split('-');
						if (startPercent == 0) startPercent = 100-100/(result.length-1)*36; //最后一个是月份
						if (sp[1] == "1") {
							yearMothArray.push(result[v].yearMonth+"月");//数组循环赋值
						} else {
							yearMothArray.push(sp[1]);//数组循环赋值
						}
						totalsArray.push(result[v].totals);
						quantitySalesArray.push(result[v].quantitySales);
						
						if (result[v].totals > maxTotals) {
							maxTotals = result[v].totals;
						}
						if (result[v].quantitySales > maxQuantitySales) {
							maxQuantitySales = result[v].quantitySales;
						}
					}
					//计算 销售额y坐标最大值
					maxTotals = maxTotals.toFixed(0);
					var len = (maxTotals + '').length;
					if ((maxTotals + '').substr(1, 2) >= 5) {//四舍五入的作用
						maxTotals = (parseInt((maxTotals + '').substr(0, 1))+1)*Math.pow(10,(len-1));
					} else {
						maxTotals = (maxTotals + '').substr(0, 1)*Math.pow(10,(len-1));
					}
					maxTotals = maxTotals+(maxTotals/2);//最后取再加一半，这样图就好看了
					
					//计算 销售数量y坐标最大值
					len = (maxQuantitySales + '').length;
					if ((maxQuantitySales + '').substr(1, 2) >= 5) {//四舍五入的作用
						maxQuantitySales = (parseInt((maxQuantitySales + '').substr(0, 1))+1)*Math.pow(10,(len-1));
					} else {
						maxQuantitySales = (maxQuantitySales + '').substr(0, 1)*Math.pow(10,(len-1));
					}
					maxQuantitySales = maxQuantitySales+(maxQuantitySales/2);//最后取再加一半，这样图就好看了
					//console.log(yearMothArray);
			        // 基于准备好的dom，初始化echarts实例
			        var myChart = echarts.init(document.getElementById('main'));
					option = {
					    title: {
					        text: '年月份-销售额 曲线图',
					       // subtext: '纯属虚构'
					    },
						tooltip: {
							trigger: 'axis'
						},
						toolbox: {
							feature: {//右上角小工具
								dataView: {show: true, readOnly: false},
								magicType: {show: true, type: ['line', 'bar']},
								restore: {show: true},
								saveAsImage: {show: true}
							}
						},
						legend: {
					        data:['销售额','销售数量'],
					       // x: 'left' //靠左，默认居中
						},
						grid: {//控制图的大小等 http://echarts.baidu.com/echarts2/doc/doc.html#Grid
							x: '90px',//左边页间隔
							x2: '80px',//右边页间隔
							//width: '96%', // auto默认,图宽度
						},
					    dataZoom: [//定义x或y轴可以拖动
			               {
			                   type: 'slider',
			                   realtime: true,
			                   start: startPercent,
			                   end: 100,
			               }
			           ],
						xAxis: [
							{
								type: 'category',
								data: yearMothArray.map(function (str) {
					                return str.replace('-', '\n')//可以替换x坐标轴的数字
					            }),
							    axisLabel:{
					                 //X轴刻度配置
					                 interval:0 //0：表示全部显示不间隔；auto:表示自动根据刻度个数和宽度自动设置间隔个数
					            }
							}
						],
						yAxis: [
							{
								type: 'value',
								name: '销售额('+currency+')',
								min: 0,
								max: maxTotals,
								interval: maxTotals/10,
								axisLabel: {
									formatter: '{value}'
								}
							},
					        {
							    type: 'value',
					            name: '销售数量',
					            min: 0,
					            max: maxQuantitySales,
					            interval: maxQuantitySales/10,
					            axisLabel: {
					                formatter: '{value}' //formatter: '{value} °C'
					            }
					        }
						],
						series: [{
								name:'销售额',
								type:'line',
								smooth: true,//折线平滑
								data: totalsArray,
								itemStyle : {normal: {label : {
									show: true,
									formatter: function (c) {//模板变量有 {a}、{b}、{c}，分别表示系列名，数据名，数据值。
										var json = $(c)[0] ;
										for (var key in json) {
											if (key == "value") {
												return toMoney(json[key]);
											}
									    }
										return c;
									},
									}}},//让点显示数值，如果设置axisLabel则有写点不会显示
							},{
								name:'销售数量',
								type:'line',
								smooth: true,//折线平滑
								yAxisIndex: 1,//以y右边轴，否则很矮
								data: quantitySalesArray,
								itemStyle : {normal: {
									//color: 'rgb(11, 8, 92)', //设置线条颜色
									label : {
									show: true,
									formatter: function (c) {//模板变量有 {a}、{b}、{c}，分别表示系列名，数据名，数据值。
										var json = $(c)[0] ;
										for (var key in json) {
											if (key == "value") {
												return formatNum(json[key]);
											}
									    }
										return c;
									},
									}}},//让点显示数值，如果设置axisLabel则有写点不会显示
							},
						]
					};
			
			        // 使用刚指定的配置项和数据显示图表。
			        myChart.setOption(option);
				}
			});
    	};
    </script>
    <script src="${ctx}/lib/util/util_common.js" type="text/javascript" charset="utf-8"></script>
 	<style>
 		body {margin: 0;padding:0}
		#main {width: 100%;height:500px;margin: 0;padding:0}
	</style>
</body>
</html>