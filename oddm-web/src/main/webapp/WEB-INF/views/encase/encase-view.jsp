<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../inc.jsp"/>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">


    <title>ODDM</title>
    <!--[if lt IE 9]>
    <script src="../theme/js/html5shiv.min.js"></script>
    <script src="../theme/js/respond.min.js"></script>
    <![endif]-->
    <style>
        .lefttd {
            width: 100px;
        }

        .righttd {
            color: #069;
            width: 234px
        }

        .bordertable {
            background-color: #eee;
            width: 100%;
            color: #666;
            margin-bottom: 15px;
            font-size: 1em;
            text-align: center;
            border-collapse: collapse;
        }

        .bordertable td {
            padding: 5px;
        }

        .tabletitle {
            padding: 10px;
        }
        .add_declare_customs ,.delete_declare_customs{
            cursor: pointer;
        }
        .ordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:left;}
        .tabletitle{text-align:left; background-color:#c8d7e1;color:#333;font-size:1.4em; font-weight:500;}
        .ordertable tr td{padding:10px 0 10px 6px;}
        .lefttd{width:85px;}
        .lefttd90{width:90px; text-align:center;}
        .righttd{color:#069;}
        .bl{color:#069;}
        .bordertable{background-color:#eee;width:100%;color:#666;margin-bottom:15px;font-size:0.8em;text-align:center;border-collapse:collapse;}
        .bordertable td{border:1px solid #ccc;padding:10px 0 10px 6px;height:20px;}
        .tabletitle span{cursor:pointer;font-size:1em;}
        .tabletop{background-color:#DCE0E2;}
        .color_f60{color:#f60;}
        
        .inputs{border:1px solid #CCC;width:140px;padding:5px 0px 2px 4px;font-size:12px;color:#999;}
		.inputz{border:1px solid #CCC;width:70px;padding:5px 5px 5px 5px;font-size:12px;color:#999;text-align:center;margin-left:-5px}
    </style>
</head>
<body>

<div class="">
<div class="encaseInfoIdz" style="display:none;" id="${encaseInfo.id}"></div>
    <table border="0px" cellpadding="0" cellspacing="0" class="ordertable">
        <tr class="tabletitle">
            <td colspan="6">基础信息：
             <span><a id="encaseExportExcel">[导出Excel]</a></span>
            </td>
        </tr>
        <tr class="f1">
             <td class="lefttd">装箱单名称：</td>
             <td class="righttd" >${encaseInfo.name}</td>
			 <td class="lefttd">装箱日期：</td>
     		 <td class="righttd">${encaseInfo.encaseDate}</td>
     		 <td class="lefttd">创建日期：</td>
     		 <td class="righttd">${encaseInfo.timestamp}</td>
        </tr>
         <tr class="f1">
            <td class="lefttd">公司名称：</td>
            <td class="righttd">${encaseInfo.company}</td>
           	<td class="lefttd">Zipcode：</td>
            <td class="righttd">${encaseInfo.zipcode}</td>
            <td class="lefttd">Tel：</td>
            <td class="righttd">${encaseInfo.telphone}</td>
            </td>
        </tr>
        <tr class="f1">
        	<td class="lefttd">Add：</td>
            <td class="righttd" colspan="3">${encaseInfo.address}</td>
            <td class="lefttd">重量单位：</td>
            <td class="righttd" id="unitWeight" >${encaseInfo.unitWeight}</td>
        </tr>
        <tr class="f1">
         	<td class="lefttd">Homepage：</td>
            <td class="righttd" colspan="3">${encaseInfo.homepage}</td>
            <td class="lefttd">长度单位：</td>
            <td class="righttd" id="unitLength" >${encaseInfo.unitLength}</td>
        </tr>
    </table>
    <table border="1px" cellpadding="0" cellspacing="0" class="bordertable" id="declare_customs_table">
        <tr class="tabletitle">
            <td colspan="19" style="padding: 10px;font-size: 0.8em;">装箱 
        </tr>
        <tr style="background: #DCE0E2;font-weight: 800;">
        	<td style="width:15%" colspan="2">C/NO<br>箱及编号</td>
        	<td style="width:10%" colspan="2">Item No.<br>产品编号(客户型号)</td>
        	<td style="width:10%" colspan="1">UNIT<br>单位</td>
        	<td style="width:15%" colspan="2">Description</td>
        	<td style="width:15%" colspan="1">REMARK<br>备注</td>
            <td style="width:15%" colspan="1">订单号</td>
            <td style="width:15%" colspan="2">工厂机型</td>  
        </tr>
        <tbody class="f22">
         <c:forEach items="${encaseInfo.encaseList}" var="item">
	         <tr class="declare_customs_addBefore f2">
	            <td colspan="2">${item['CNo']}</td>
	            <td colspan="2">${item['itemNo']}</td>
	            <td colspan="1">${item['description']}</td>
	            <td colspan="2">${item['unit']}</td>
	            <td colspan="1">${item['remark']}</td>
	            <td colspan="1">${item['orderNo']}</td>
	            <td colspan="2">${item['productFatory']}</td>
	        </tr>
	        </c:forEach>
        </tbody>
        
         <tr style="background: #DCE0E2;font-weight: 800;">
            <td colspan="7" width="10%">IN ONE CARTON</td>
            <td colspan="6">TOTAL</td>
        </tr>
        <tr style="background: #DCE0E2;font-weight: 800;">
           <!--  <td rowspan="2">QTY/Ctn<br>每箱的数量</td> -->
            <td >N.W./n<br>净重</td>
            <td >G.W./n<br>毛重</td>
            <td colspan="3">Carton Size(MM)/n<br>单个纸箱尺寸</td>
            <td >QTY/Ctn<br>每箱的数量</td>
            <td >QTY<br>数量</td>
            <td >CTN<br>装箱量</td>
            <td >Meas总尺寸<br>(单箱的立方*总箱数)</td>
            <td >N.W.</td>
            <td >G.W.</td>
        </tr>
        <tr style="background: #DCE0E2;font-weight: 800;">
         	<td class="unitWight"></td>
            <td class="unitWight"></td>
            <td >Length</td>
            <td >Width</td>
            <td >Heigh</td>
            <td ></td>
            <td ></td>
            <td >QTY<br>总箱数</td>
            <td class="unitLength"></td>
            <td class="unitWight"></td>
            <td colspan="2" class="unitWight"></td>
        </tr> 
 		<tbody class="f24">
 		 <c:forEach items="${encaseInfo.encaseList}" var="item">
	        <tr class="declare_customs_addBefore f2">
	            <td ><span class="NW">${item['nW']}</span></td>
	            <td ><span class="GW">${item['gW']}</span></td>
	            <td ><span class="M1">${item['length']}</span></td>
	            <td ><span class="M2">${item['width']}</span></td>
	            <td ><span class="M3">${item['hight']}</span></td>
	            <td ><span class="qtyCtn">${item['qtyCtn']}</span></td>
	         	<td ><span class="qtyz">${item['qty']}</span></td>
	            <td onmouseover="encasez($(this));"><span class="encasezz"></span></td>
	            <td onmouseover="M3($(this));"><span class="M3z"></span></td>
	            <td onmouseover="NW($(this));"><span class="NWz"></span></td>
	            <td onmouseover="GW($(this));"><span class="GWz"></span></td>
	        </tr>
	        </c:forEach>
     	</tbody>
       <!-- 	<tbody class="f23">
	       
        </tbody> -->
        <tr class="f1">
            <td colspan="2" class="declare_customs_addBefore" style="background: #DCE0E2;font-weight: 800;">装箱情况</td>
            <td colspan="9">${encaseInfo.description}</td>
        </tr>

        <tr>
            <td ></td>
            <td ></td>
            <td ></td>
            <td ></td>
            <td >TOTAL</td>
            <td ></td>
            <td onmouseover="countz($(this));"><span></span></td>
            <td class="unitz"><span></span></td>
            <td ></td>
            <td ></td>
            <td ></td>
        </tr>
        <tr>
            <td rowspan="2" colspan="5">总计（TOTAL）</td>
            <td ></td>
            <td ></td>
            <td onmouseover="qtyz($(this));"><span></span></td>
            <td onmouseover="m3z($(this));"><span></span></td>
            <td onmouseover="nwz($(this));"><span></span></td>
            <td onmouseover="gwz($(this));"><span></span></td>
        </tr>
         <tr style="font-weight: 800;">
            <td ></td>
            <td ></td>
            <td >CTN</td>
            <td ><span class="unitLength"></span></td>
            <td ><span class="unitWight"></span></td>
            <td colspan="2"><span class="unitWight"></span></td>
        </tr>  
    </table>
</div>
<script>
$(function(){
	 $(".encasezz").each(function(i){
		var i = $(this).parent().parent().find(".qtyz").text();
		var j = $(this).parent().parent().find(".qtyCtn").text();
		$(this).text(i / j);
	}) 
	
	 $(".NWz").each(function(i){
			var i = $(this).parent().parent().find(".encasezz").text();
			var j = $(this).parent().parent().find(".NW").text();
			$(this).text(i * j);
		}) 

	 $(".GWz").each(function(i){
			var i = $(this).parent().parent().find(".encasezz").text();
			var j = $(this).parent().parent().find(".GW").text();
			$(this).text(i * j);
		})
	 $(".M3z").each(function(i){
		var i = $(this).parent().parent().find(".encasezz").text();
		var x = $(this).parent().parent().find(".M1").text();
		var y = $(this).parent().parent().find(".M2").text();
		var z = $(this).parent().parent().find(".M3").text();
		$(this).text(x * y * z /1000000000*i);
	 }) 
	 
	var unitLength = $("#unitLength").html();
	var unitWeight = $("#unitWeight").html();
	$(".unitLength").text("("+unitLength+"³)");
	$(".unitWight").text("("+unitWeight+")");
})
	
	
	function countz(i){
		var unit = $(".unit").val();
		var count = 0;
		$(".f24").find(".qty").each(function(i){
			var n = parseInt($(this).val());
			count += n;
		})
		i.children().text(count); 
		$(".unitz").children().text(unit);
	}
	function qtyz(i){
		var count = 0;
		$(".f24").find(".encasezz").each(function(i){
			var n = parseInt($(this).text());
			count += n;
		})
		i.children().text(count); 
	}
	function m3z(i){
		var count = 0;
		$(".f24").find(".M3z").each(function(i){
			var n = parseInt($(this).text());
			count += n;
		})
		i.children().text(count); 
	}
	function nwz(i){
		var count = 0;
		$(".f24").find(".NWz").each(function(i){
			var n = parseInt($(this).text());
			count += n;
		})
		i.children().text(count); 
	}
	function gwz(i){
		var count = 0;
		$(".f24").find(".GWz").each(function(i){
			var n = parseInt($(this).text());
			count += n;
		})
		i.children().text(count); 
	}
	/*
	  * 导出Excel
	  */
	$("#encaseExportExcel").on("click",function(){
		var id = $(".encaseInfoIdz").attr("id");
		console.log("-----"+id); 
		download_file("${pageContext.request.contextPath}/encase/reportData?id="+id);
	})
	function download_file(url)  
		{  
		    if (typeof (download_file.iframe) == "undefined")  
		    {  
		        var iframe = document.createElement("iframe");  
		        download_file.iframe = iframe; 
		        document.body.appendChild(download_file.iframe);  
		        download_file.iframe.addEventListener( "load", function(){
			         //代码能执行到这里说明已经载入成功完毕了
			           this.removeEventListener("load", arguments.call, false);
		         //这里是回调函数
			         parent.parent.$.messager.alert('提示', '文件不存在！');
			   }, false);
		    }  
		    console.log(url);
			download_file.iframe.src = url;  
		    download_file.iframe.style.display = "none";  
		} 
</script>
</body>
</html>