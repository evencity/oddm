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
        .btn34{width:90px;height:35px;line-height:18px;font-size:18px;color:#666;	padding-bottom:4px;}
        .inputs{border:1px solid #CCC;width:140px;padding:5px 0px 2px 4px;font-size:12px;color:#999;}
		.inputz{border:1px solid #CCC;width:70px;padding:5px 5px 5px 5px;font-size:12px;color:#999;text-align:center;margin-left:-5px}
  		.shanchu{width:14px; height:14px; margin:0px 15px 0px 0px; background:url(${pageContext.request.contextPath}/lib/images/del.jpg); cursor:pointer;}
    
    	#selectInputMenu{
		position: absolute;
		background: white;
		z-index: 1;
		border: 1px solid;
		overflow: auto;
		max-height: 200px;
		}
		#selectInputMenu ul{
			padding: 0px;
			margin : 0px;
			list-style: none;
		}
		#selectInputMenu ul li{
			padding : 8px;
			cursor: pointer;
		}
		#selectInputMenu li:hover{
			background: #0cf;
		}
    </style>
</head>
<body>
<div id="selectInputMenu"></div>
<div class="">
    <table border="0px" cellpadding="0" cellspacing="0" class="ordertable">
        <tr class="tabletitle">
            <td colspan="6">基础信息:</td>
        </tr>
        <tr class="f1">
             <td class="lefttd">装箱单名称：</td>
             <td class="righttd" ><input class="inputs" name="namez"></td>
			 <td class="lefttd">装箱日期：</td>
     		 <td class="righttd"><input class="inputs easyui-datebox"  value="" id="encaseDate" size="16"></td>
     		 <td class="lefttd">创建日期：</td>
     		 <td class="righttd"><input class="inputs easyui-datebox form_datetime"  value="" id="timestamp" size="16"></td>
        </tr>
        <tr class="f1">
            <td class="lefttd">公司名称：</td>
            <td class="righttd"><input class="inputs" name="company"></td>
            <td class="lefttd">Zipcode：</td>
            <td class="righttd"><input class="inputs" name="zipcode"></td>
            <td class="lefttd">Tel：</td>
            <td class="righttd"><input class="inputs" name="telphone"></td>
        </tr>
        <tr class="f1">
            <td class="lefttd">Add：</td>
            <td class="righttd" colspan="3"><input class="inputs" style="width:530px" name="address"> </td>
            <td class="lefttd">重量单位：</td>
             <td colspan="3" class="righttd"><input type="text" class="inputs select_static" name="unitWeight" id="unitWeight" 
	         msg="g|kg|t" missingMessage="此项为必填项" required="true" 
	         /></td>
	         
        </tr>
        <tr class="f1">
            <td class="lefttd">Homepage：</td>
            <td class="righttd" colspan="3"><input style="width:530px;" class="inputs" name="homepage"/></td>
            <td class="lefttd">长度单位：</td>
            <td colspan="3" class="righttd"><input type="text" class="inputs select_static" name="unitLength" id="unitLength" 
	        msg="M|CM|MM" missingMessage="此项为必填项" required="true" 
	        /></td>
        </tr>
    </table>
    <table border="1px" cellpadding="0" cellspacing="0" class="bordertable" id="declare_customs_table">
        <tr class="tabletitle">
            <td colspan="19" style="padding: 10px;font-size: 0.8em;">装箱 
           <%--  <img style="width: 20px;height: 20px;float:right;margin-left: 10px" id="dels" alt="" src="${pageContext.request.contextPath}/style/images/delEn.png"> --%>
            <img style="width: 20px;height: 20px;float:right;" id="adds" alt="" src="${pageContext.request.contextPath}/style/images/addEn.png"></td>
        </tr>
        <tr style="background: #DCE0E2;font-weight: 800;">
        	<td colspan="2" width="8%">C/NO<br>箱及编号</td>
        	<td colspan="2" width="8%">Item No.<br>产品编号(客户型号)</td>
        	<td colspan="1" width="4%">UNIT<br>单位</td>
        	<td colspan="2" width="10%">Description</td>
        	<td colspan="1" width="10%">REMARK<br>备注</td>
            <td colspan="1" width="10%">订单号</td>
            <td colspan="2" width="10%">工厂机型</td> 
            <td colspan="1" width="1%"></td>   
        </tr>
        <tbody class="f22">
	         <tr class="declare_customs_addBefore f2 1d">
	            <td colspan="2"><input style="width:95%" class="inputz" name="cNo"></td>
	            <td colspan="2"><input style="width:95%" class="inputz" name="itemNo"></td>
	            <td colspan="1"><input style="width:95%" class="inputz" name="description"></td>
	            <td colspan="2"><input style="width:95%" class="inputz unit" name="unit"></td>
	            <td colspan="1"><input style="width:95%" class="inputz" name="remark"></td>
	            <td colspan="1"><input style="width:95%" class="inputz" name="orderNo"></td>
	            <td colspan="2"><input style="width:95%" class="inputz" name="productFatory"></td>
	            <td colspan="1" width="1%"><span class="shanchu" style="float:right" onclick="delmtr($(this));" id=""></span></td>
	        </tr>
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
            <td colspan="2" width="4%">G.W.</td>
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
	        <tr class="f2 1d">
	            <td ><input class="inputz NW" style="width:95%" name="nW"></td>
	            <td ><input class="inputz GW" style="width:95%" name="gW" ></td>
	            <td ><input class="inputz M1" style="width:95%"name="length"></td>
	            <td ><input class="inputz M2" style="width:95%" name="width"></td>
	            <td ><input class="inputz M3" style="width:95%" name="hight"></td>
	            <td ><input class="inputz qtyCtn" style="width:95%" name="qtyCtn" ></td>
	         	<td ><input class="inputz qty" style="width:95%" name="qty" ></td>
	            <td onmouseover="encasez($(this));"><span class="encasezz"></span></td>
	            <td onmouseover="M3($(this));"><span class="M3z"></span></td>
	            <td onmouseover="NW($(this));"><span class="NWz"></span></td>
	            <td colspan="2"  onmouseover="GW($(this));"><span class="GWz"></span></td>
	        </tr>
     	</tbody>
       <!-- 	<tbody class="f23">
	       
        </tbody> -->
        <tr class="f1">
            <td colspan="2" class="declare_customs_addBefore" style="background: #DCE0E2;font-weight: 800;">装箱情况</td>
            <td colspan="10"><textarea style="width:98%;height:80px;" class="inputs" name="description"></textarea></td>
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
            <td colspan="2"></td>
        </tr>
        <tr>
            <td rowspan="2" colspan="5">总计（TOTAL）</td>
            <td ></td>
            <td ></td>
            <td onmouseover="qtyz($(this));"><span></span></td>
            <td style="height: 40px" onmouseover="m3z($(this));"><span></span></td>
            <td onmouseover="nwz($(this));"><span></span></td>
            <td colspan="2" onmouseover="gwz($(this));"><span></span></td>
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

<div align="center">
    <button id="save_declare_customs" class="btn34">保存</button>
</div>
<script>
$("#save_declare_customs").on("click",function(){
	
	var name = $(".inputs[name=namez]").val();
	if(name.length < 1){
			parent.$.messager.alert('提示', '装箱单名称不能为空！', 'info');
	        return false;
	} 
	 var encaseDate = $("#encaseDate").datebox('getValue');
	 if(encaseDate.length < 1){
			parent.$.messager.alert('提示', '装箱日期不能为空！', 'info');
	        return false;
	} 
	 var timestamp = $("#timestamp").datebox('getValue');
	 if(timestamp.length < 1){
			parent.$.messager.alert('提示', '创建日期不能为空！', 'info');
	        return false;
	} 
	 var company = $(".inputs[name=company]").val();
	 if(company.length < 1){
			parent.$.messager.alert('提示', '公司名称不能为空！', 'info');
	        return false;
	} 
	 var zipcode = $(".inputs[name=zipcode]").val();
	 if(zipcode.length < 1){
			parent.$.messager.alert('提示', 'zipcode不能为空！', 'info');
	        return false;
	} 
	 var tel = $(".inputs[name=telphone]").val();
	 if(tel.length < 1){
			parent.$.messager.alert('提示', 'Tel不能为空！', 'info');
	        return false;
	} 
	 var address = $(".inputs[name=address]").val();
	 if(address.length < 1){
			parent.$.messager.alert('提示', 'Address不能为空！', 'info');
	        return false;
	} 
	 var homepage = $(".inputs[name=homepage]").val();
	 if(homepage.length < 1){
			parent.$.messager.alert('提示', 'Homepage不能为空！', 'info');
	        return false;
	}
		 var a = $(".f1");
		 var c = $(".f22");
		 var d = $(".f24");
	  	 var params = {};
		 var lists = [];
		 var namez;
		 var company;
		 var address;
		 var zipcode;
		 var telphone;
		 var homepage;
		 var unitWeight;
		 var unitLength;
		 var description;
		 var encaseDate = $("#encaseDate").datebox('getValue');
		 var timestamp = $("#timestamp").datebox('getValue');
		 a.find('.inputs').each(function(){
	 		 var $this = $(this);
	        var name = $this.attr('name');
		  	 if(name){
		  		 if(name=='namez'){
		  			 namez = $this.val();
		  		 }
		  		 if(name=='company'){
		  			company = $this.val();
		  		 }
		  		if(name=='address'){
		  			address = $this.val();
		  		 }
		  		if(name=='zipcode'){
		  			zipcode = $this.val();
		  		 }
		  		if(name=='telphone'){
		  			telphone = $this.val();
		  		 }
		  		if(name=='homepage'){
		  			homepage = $this.val();
		  		 }
		  		if(name=='unitWeight'){
		  			unitWeight = $this.val();
		  		 }
		  		if(name=='unitLength'){
		  			unitLength = $this.val();
		  		 }
		  		if(name=='description'){
		  			description = $this.val();
		  		 }
		       } 
		  });
		
		c.find(".inputz[name=cNo]").each(function(){
			var cNo = c.find('.inputz[name=cNo]').val();
			var itemNo = c.find('.inputz[name=itemNo]').val();
			var description = c.find('.inputz[name=description]').val();
			var unit = c.find('.inputz[name=unit]').val();
			var remark = c.find('.inputz[name=remark]').val();
			var orderNo = c.find('.inputz[name=orderNo]').val();
			var productFatory = c.find('.inputz[name=productFatory]').val();
			
			var nW = d.find('.inputz[name=nW]').val();
			var gW = d.find('.inputz[name=gW]').val();
			var length = d.find('.inputz[name=length]').val();
			var width = d.find('.inputz[name=width]').val();
			var hight = d.find('.inputz[name=hight]').val();
			var qtyCtn = d.find('.inputz[name=qtyCtn]').val();
			var qty = d.find('.inputz[name=qty]').val();
			
			var en = {};
			en = {
					cNo:cNo,
					itemNo:itemNo,
					description:description,
					unit:unit,
					remark:remark,
					orderNo:orderNo,
					productFatory:productFatory,
					nW:nW,
					gW:gW,
					length:length,
					width:width,
					hight:hight,
					qtyCtn:qtyCtn,
					qty:qty
			}
			lists.push(en);
		})
	
		
		params = {
			  name:namez,
			  company:company,
			  address:address,
			  zipcode:zipcode,
			  telphone:telphone,
			  homepage:homepage,
			  unitWeight:unitWeight,
			  unitLength:unitLength,
			  description:description,
			  encaseDate:encaseDate,
			  timestamp:timestamp,
			  encaseList:lists
		}
	     	 $.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath}/encase/add",
				data:JSON.stringify(params),
				dataType:"json",
				async: false,
				contentType: "application/json; charset=utf-8",
				success: function(j) {
					if(j.success){
						 progressClose();
	            		 parent.$.messager.alert('提示', '添加成功');
	            		 var parent_tabs = parent.$('#index_tabs');
	            		 var index = parent_tabs.tabs('getTabIndex', parent.$('#index_tabs').tabs('getSelected'));
	 					 var tab = parent_tabs.tabs('getTab', index);
	 					 if (tab.panel('options').closable) {
	 						parent_tabs.tabs('close', index);
	 					 }
	 					parent_tabs.tabs('select', "装箱单");
	 					parent_tabs.tabs('update', {
							tab : parent_tabs.tabs('getSelected'), //获取当前被选中的页面
							options : {
								//Title : '新标题',
								}
							}
						);
	            	 }else{
	            		 progressClose();
	            		 parent.$.messager.alert('提示', '添加失败');
	            	 }
			}
		});   
})
var flag = 0;
$("#adds").on("click",function(){
	flag = flag + 1;
	$(".f22").append( '<tr class="declare_customs_addBefore f2 '+flag+'">'
			+'<td colspan="2"><input style="width:95%;" class="inputz" name="cNo"></td>'
			+'<td colspan="2"><input style="width:95%;" class="inputz" name="itemNo"></td>'
			+'<td colspan="1"><input style="width:95%;" class="inputz" name="description"></td>'
			+'<td colspan="2"><input style="width:95%;" class="inputz unit" name="unit"></td>'
			+'<td colspan="1"><input style="width:95%;" class="inputz" name="remark"></td>'
			+'<td colspan="1"><input style="width:95%;" class="inputz" name="orderNo"></td>'
			+'<td colspan="2"><input style="width:95%;" class="inputz" name="productFatory">'
			+'<td><span class="shanchu" style="float:right" onclick="delmtr($(this));" id=""></span></td>'
			+'</td>'
		    +'</tr>');
	$(".f24").append(' <tr class="f2 '+flag+'">'
			+'<td ><input class="inputz NW" style="width:95%;" name="nW"></td>'
			+'<td ><input class="inputz GW" style="width:95%;" name="gW" ></td>'
			+'<td ><input class="inputz M1" style="width:95%;" name="length"></td>'
			+'<td ><input class="inputz M2" style="width:95%;" name="width"></td>'
			+'<td ><input class="inputz M3" style="width:95%;" name="hight"></td>'
			+'<td ><input class="inputz qtyCtn" style="width:95%;" name="qtyCtn" ></td>'
			+'<td ><input class="inputz qty" style="width:95%;" name="qty" ></td>'
			+'<td style="display:none"><input class="inputz name="spl" value="spl"></td>'
			+'<td onmouseover="encasez($(this));"><span class="encasezz"></span></td>'
			+'<td onmouseover="M3($(this));"><span class="M3z"></span></td>'
			+'<td onmouseover="NW($(this));"><span class="NWz"></span></td>'
			+'<td colspan="2" onmouseover="GW($(this));"><span class="GWz"></span></td>'
            +'</tr>');
})
function delmtr($this){
	var className = $this.parent().parent().attr('class').split(" ")[2];
	$this.parent().parent().parent().parent().find("."+className+"").remove();
}

function encasez(i){
	var count = 0;
	var it = i.parent();
	var qty = it.find(".qty").val();
	var qtyCtn = it.find(".qtyCtn").val();
	var num1=parseInt(qty);   
    var num2=parseInt(qtyCtn);
    console.log(num1);
    console.log(num2);
    count  = num1 / num2;
    console.log(count);
	if(count != NaN){
		i.children().text(count); 
	}
	
}
function NW(i){
		 var nw_count = 0;
		 var it = i.parent();
		 var qty = it.find(".encasezz").text();
		 var nw = it.find(".NW").val();
         nw_count  = nw * qty;
		 if(nw_count != NaN){
			 i.children().text(nw_count); 
			}
}
function GW(i){
	 var gw_count = 0;
	 var it = i.parent();
	 var qty = it.find(".encasezz").text();
	 var gw = it.find(".GW").val();
     gw_count  = gw * qty;
	 if(gw_count != NaN){
		 i.children().text(gw_count); 
		}
}
function M3(i){
	 var it = i.parent();
	 var M1 = it.find(".M1").val();
	 var M2 = it.find(".M2").val();
	 var M3 = it.find(".M3").val();
	 var qty = it.find(".encasezz").text();
	 var num=parseInt(qty);   
	 var m_count = 0;
	 m_count = M1*M2*M3/1000000000*num;
	 if(m_count != NaN){
		 i.children().text(m_count); 
	 }
}
function countz(i){
	var unit = $(".unit").val();
	console.log("==="+unit);
	var count = 0;
	$(".f24").find(".qty").each(function(i){
		var n = parseInt($(this).val());
		count += n;
	})
	i.children().text(count); 
	$(".unitz").children().text(unit);
}
function qtyz(i){
//	var unit = $(".unit").val();
	var count = 0;
	$(".f24").find(".encasezz").each(function(i){
		var n = parseInt($(this).text());
		count += n;
	})
	i.children().text(count); 
	//$(".unitz").children().text(unit);
}
function m3z(i){
//	var unit = $(".unit").val();
	var count = 0;
	$(".f24").find(".M3z").each(function(i){
		var n = parseInt($(this).text());
		count += n;
	})
	i.children().text(count); 
	//$(".unitz").children().text(unit);
}
function nwz(i){
//	var unit = $(".unit").val();
	var count = 0;
	$(".f24").find(".NWz").each(function(i){
		var n = parseInt($(this).text());
		count += n;
	})
	i.children().text(count); 
	//$(".unitz").children().text(unit);
}
function gwz(i){
//	var unit = $(".unit").val();
	var count = 0;
	$(".f24").find(".GWz").each(function(i){
		var n = parseInt($(this).text());
		count += n;
	})
	i.children().text(count); 
	//$(".unitz").children().text(unit);
}
$('body').off('focus','.select_static ').on('focus','.select_static',function(e,msg){
	$("#selectInputMenu").css('display','block');
	var input = $(this);
	var width = $(this).css('width');
	var top = $(this).offset().top;
	var height = $(this).css('height')
	var left = $(this).offset().left;
	$("#selectInputMenu").empty();
	var $ul = $('<ul></ul>');
	$('#selectInputMenu').css('width',width);
	$('#selectInputMenu').css({"top": top + parseInt(height), "left": left});
	
	var msg = $(this).attr('msg')
	var msg_array = msg.split("|");
	for(var i in msg_array){
		var content = '<li class="inputValueLi">'+ msg_array[i] +'</li>';
		$ul.append(content)
	}
	$('#selectInputMenu').append($ul)
	$('#selectInputMenu').off('click','.inputValueLi').on('click','.inputValueLi',function(e,msg){
		var liValue = $(this).html();
		input.val(liValue);
		if(liValue == "MM" || liValue == "CM" || liValue == "M"){
			$(".unitLength").text("("+liValue+"³)");
		}
		if(liValue == "g" || liValue == "kg" || liValue == "t"){
			$(".unitWight").text("("+liValue+")");
		}
		$("#selectInputMenu").css('display','none');
	})
})

</script>
</body>
</html>