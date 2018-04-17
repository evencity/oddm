<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
    <meta charset="utf-8">
    <!-- 引入 echarts.js -->
    <script src="${ctx}/lib/echarts/echarts.min.js" type="text/javascript"></script>
    <script type="text/javascript">
  //格式化整数，以逗号分开
    function formatNum(strNum) {
    	if(strNum == null || strNum == "") return 0;
    	if (strNum.length <= 3) return strNum; 
    	if (!/^(\+|-)?(\d+)(\.\d+)?$/.test(strNum)) return strNum;
    	var a = RegExp.$1, b = RegExp.$2, c = RegExp.$3;
    	var re = new RegExp();
    	re.compile("(\\d)(\\d{3})(,|$)");
    	while (re.test(b)) {
    		b = b.replace(re, "$1,$2$3");
    	}
    	return a + "" + b + "" + c;
    }
    </script>
</head>
<body>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style=""></div>
    <script type="text/javascript">
    	var yearMothArray = []; //定义数组
    	var quantitysArray = [];
		$.ajax({
			url : '${ctx}/order/dataGridStatisticsAllYear?yearStart=2013',
			type : "GET",
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			async: true,
			success : function(result) {
				var startPercent = 0;//定义x轴可以从哪个百分比拖动，要显示3年内的曲线
				var maxQuantitys = 0;
				for(var v in result) {
					//console.log(result[v].yearMonth+"\t"+result[v].quantitys);
					var sp = result[v].yearMonth.split('-');
					if (startPercent == 0) startPercent = 100-100/(result.length-1)*36; //最后一个是月份
					if (sp[1] == "1") {
						yearMothArray.push(result[v].yearMonth+"月");//数组循环赋值
					} else {
						yearMothArray.push(sp[1]);//数组循环赋值
					}
					quantitysArray.push(result[v].quantitys);
					if (result[v].quantitys > maxQuantitys) {
						maxQuantitys = result[v].quantitys;
					}
				}
				var len = (maxQuantitys + '').length;
				var len = (maxQuantitys + '').length;
				if ((maxQuantitys + '').substr(1, 2) >= 5) {//四舍五入的作用
					maxQuantitys = (parseInt((maxQuantitys + '').substr(0, 1))+1)*Math.pow(10,(len-1));
				} else {
					maxQuantitys = (maxQuantitys + '').substr(0, 1)*Math.pow(10,(len-1));
				}
				maxQuantitys = maxQuantitys+(maxQuantitys/2);//最后取再加一半，这样图就好看了
				//console.log("len "+maxQuantitys);
				//console.log(yearMothArray);
				//console.log(quantitysArray);
		        // 基于准备好的dom，初始化echarts实例
		        var myChart = echarts.init(document.getElementById('main'));
				option = {
				    title: {
				        text: '年月份-订单数 曲线图',
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
						data:['订单数量']
					},
					grid: {//控制图的大小等 http://echarts.baidu.com/echarts2/doc/doc.html#Grid
						x: '80px',//左边页间隔
						x2: '1.5%',//右边页间隔
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
							name: '订单数量',
							min: 0,
							max: maxQuantitys,
							interval: maxQuantitys/10,
							axisLabel: {
								formatter: '{value}'
							}
						}
					],
					series: [{
							name:'订单数量',
							type:'line',
							smooth: true,//折线平滑
							data: quantitysArray,
							itemStyle : {normal: {label : {
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
    </script>
 	<style>
 		body {margin: 0;padding:0}
		#main {width: 100%;height:500px;margin: 0;padding:0}
	</style>
</body>
</html>