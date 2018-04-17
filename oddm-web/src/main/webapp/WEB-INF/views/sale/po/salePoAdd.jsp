<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>
	#selectInputMenu {
		overflow: auto;
	}
	.tableGrid {
		text-align:center;
	}
	.t_title {
	font-weight: bold;
	}
	
	.tr_border, .tr_border td, .tr_insert td{
		border:solid 1px gray;
	}

	#table_company {
		background-image: url("${ctx}/style/images-sale/salepo-apical-logo.jpg");
		background-repeat:  no-repeat;
		background-position: left top;
		background-size: 150px 45px;
	}
	input, textarea {
		border:1px solid #CCC;
		border-radius: 4px;
	}
</style>

<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript">
 //提交到后台
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
		$('#ip_currency').combobox({
			textField: 'value',
			panelHeight : 'auto',
			editable: false,
			data: dataCurr,
			onChange: function(newValue, oldValue){
				$('.td_currency').text(newValue);
			}
		});
		
		//控制币种自动变化
		/* $("#ip_currency").focusout(function() {
			$('.td_currency').text($('#ip_currency').combobox('getValue'));
		}); */
		//使用ajax提交
		$('#btn-summit').click(function(){
			summit();
		});
		
		$("#addImage").click(function(){
			var currency = $('#ip_currency').combobox('getValue');
			if (currency == null || currency=="") {
				$.messager.alert({
        			title: "提示框",
        			msg: "请先输入币种！",
        			timeout: 0,//毫秒
        			height: 100,
        			width:200,
        		});
				return;
			}
			var number = "<input class=\"number\" name=\"salePoListsDto.number\" style=\"width: 100%;height: 100%\" placeholder=\"请输入\" type=\"text\" value=\"\">";
			var productName = "<input class=\"productName\" name=\"salePoListsDto.productName\" style=\"width: 100%;height: 100%\" placeholder=\"请输入\" type=\"text\" value=\"\">";
			var specification = "<textarea class=\"specification\" name=\"salePoListsDto.specification\" style=\"width: 100%;height: 100%\" placeholder=\"请输入\" ></textarea>";
			var quality = "<input class=\"quality\" name=\"salePoListsDto.quality\" style=\"width: inherit;height: inherit\" placeholder=\"请输入\" type=\"text\" class=\"easyui-numberbox\" value=\"0\">";
			var unitPrice = "<input class=\"unitPrice\" name=\"salePoListsDto.unitPrice\" style=\"width: inherit;height: inherit\" placeholder=\"请输入\" type=\"text\" class=\"easyui-numberbox\" value=\"0.00\">";
			var prices = "0.00";
			var description = "<textarea class=\"description\" name=\"salePoListsDto.description\" style=\"width: 100%;height: 100%;font-weight: bold;\" placeholder=\"请输入\" ></textarea>";
			var del = "<img class=\"delImage\" src=\"${ctx}/jslib/easyui1.4.2/themes/icons/del.png\" style=\"cursor:pointer;\"></img>";
			
			var td1 = "<td colspan=\"3\" style=\"width: 12%;\">"+number+"</td>";
			var td2 = "<td colspan=\"3\" style=\"width: 12%;\">"+productName+"</td>";
			var td3 = "<td colspan=\"7\" style=\"width: 30%;\">"+specification+"</td>";
			var td4 = "<td colspan=\"1\" style=\"width: 8%;\">"+quality+"</td>";
			var td5 = "<td class=\"td_currency\" colspan=\"1\" style=\"width: 5%;color: blue;\">"+currency+"</td>";
			var td6 = "<td colspan=\"1\" style=\"width: 8%;\">"+unitPrice+"</td>";
			var td7 = "<td class=\"td_prices\" colspan=\"5\" style=\"width: 10%;color: blue;\">"+prices+"</td>";
			var td8 = "<td colspan=\"4\" style=\"width: 12.5%;\">"+description+"</td>";
			var td9 = "<td colspan=\"1\" style=\"width: 1.5%;\">"+del+"</td>";

			var row = "<tr class=\"tr_insert tr_insert_new\" style=\"height: 80px;\">"+td1+td2+td3+td4+td5+td6+td7+td8+td9+"</tr>";
			
			$('.tableGrid .tr_insert').last().after(row); // 插入
			reloadCss();
		});
	
		function reloadCss() {
	 		/*****************使用easyui的数字验证框，要考虑css*******************/
	 		$('.number').validatebox({
			    required: false,
				validType:['length[0,64]'],
			});
			$('.productName').validatebox({    
			    required: true,
				validType:['length[0,64]'],
			});
			$('.quality').numberbox({
				min:0,precision:0,max:2147483647,
				"onChange":function(newValue,oldValue){// 计算总额  
					var qlity = newValue;
					var uniP = $(this).parent().next().next().children().next().children().next().val();
					//qlity = parseFloat(qlity.replace(/,/g,""));
					uniP = parseFloat(uniP.replace(/,/g,""));
					$(this).parent().next().next().next().text(toMoney((qlity*uniP).toFixed(2), 2));
					calcTotal();
				 }
			}); 
			$('.unitPrice').numberbox({    
				min:0,precision:2,max:99999999.99,
				groupSeparator:",",
				"onChange":function(newValue,oldValue){// 计算总额  
					var qlity = $(this).parent().prev().prev().children().next().children().next().val();
					var uniP = newValue;
					//qlity = parseFloat(qlity.replace(/,/g,""));
					uniP = parseFloat(uniP.replace(/,/g,""));
					$(this).parent().next().text(toMoney((qlity*uniP).toFixed(2), 2));
					calcTotal();
				}
			}); 
			$('.specification').validatebox({  
				validType:['length[0,200]'],
			});
			$('.description').validatebox({  
				validType:['length[0,100]']
			});
			/*********************重新加载部分css***********************/
			$('.numberbox').css({//.quality的input要用inherit
				"width":"98%",
				"height":"100%",
				"text-align":"center",
			});
			$('.numberbox .textbox-text').css({//居中
				"text-align":"center",
				"padding":"0px",
				"padding-top":"44%", 
			});
			$('.tr_insert td').css({
				"border":"solid 1px gray",
			});
			
			$('.tr_insert_new td input').css({
				"text-align":"center",
			});
			/* $('.tr_insert_new td textarea').css({
				"text-align":"center",
			}); */
			$('.delImage, #addImage').parent().css({
				"border-left":"hidden",
			});
			/*********************绑定删除行事件***********************/
			$(".delImage").click(function(){
				$(this).parent().parent().remove();
				calcTotal();
			});
	 	}
	});
	//计算 小写总额
	function calcTotal() {
		var total = 0;
		$ ('.td_prices').each (function (){
			//console.log(parseFloat(this.innerHTML.replace(/,/g,"")));
			total += parseFloat(this.innerHTML.replace(/,/g,""));
        })
       	total = total.toFixed(2);
		$('#td_total_gbk').text(toGBK(total));
		$('#td_total').text(toMoney(total+"", 2));
	}
	
	//金钱数值 转换成中文
	function toGBK(n) {
        if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
            return "数据非法";
        var unit = "仟佰拾亿仟佰拾万仟佰拾元角分", str = "";
            n += "00";
        var p = n.indexOf('.');
        if (p >= 0)
            n = n.substring(0, p) + n.substr(p+1, 2);
            unit = unit.substr(unit.length - n.length);
        for (var i=0; i < n.length; i++)
            str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
        return str.replace(/零(仟|佰|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整").replace(/角$/g, "角整").replace(/^分?/g, "");
	}
</script>
<body>
<div class="styleeasyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;width: 95%;margin:0 3% 0 2%" >
		<form id="salePoAddForm" method="post">
			<table class="tableGrid" style="border-collapse:collapse; border:solid 1px gray;width:100%;background-color: #f0f4f7"> <!-- ;margin-left: 6px -->
				<tr>
		            <td id="table_company" colspan="26" valign="bottom" style="height: 50px;text-align:center;"><input id="ip_company" name="company" style="background-color:inherit ;border:0px;font-weight: bold;font-size:20px;height: 80%;width: 290px;" type="text" placeholder="请输入公司名" class="easyui-validatebox" data-options="required:true,validType:['length[0,100]']" value="${salePoDto.company }"></td>
		        </tr>
				<tr>
		            <td colspan="26" valign="bottom" style="text-align:center;"><input id="ip_address" name="address" style="background-color:inherit ;border:0px;height: 100%;width: 290px" type="text" placeholder="请输入公司地址" class="easyui-validatebox" data-options="required:true,alidType:['length[0,200]']" value="${salePoDto.address }"></td>
		        </tr>
				<tr>
		            <td colspan="26" valign="bottom" style="text-align:center;text-align:center;">电话:<input id="ip_tel" name="tel" style="background-color:inherit ;border:0px;height: 100%" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true,validType:['length[0,20]']" value="${salePoDto.tel }"> 
		                        传真:<input id="ip_fax" name="fax" style="background-color:inherit ;border:0px;height: 100%" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true,validType:['length[0,20]']" value="${salePoDto.fax }"> 网址:<input id="ip_homepage" name="homepage" style="background-color:inherit ;border:0px;height: 100%" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true,validType:['length[0,100]']" value="${salePoDto.homepage }"></td>
		        </tr>
				<tr>
		            <td colspan="26" valign="center" style="font-weight: bold;font-size:22px;height: 40px;">Purchase Order</td>
		            <span id="span_orderNo" style="font-weight: normal;text-align: right"> 订单号：${salePoDto.orderNo }</span>
		        </tr>
				<tr class="tr_border" style="height: 35px;">
					<td colspan="6"  style="width: 24%;" class="t_title">客户名称</td>
					<td colspan="8" style="width: 49%;">${salePoDto.clientName }</td>
					<td colspan="1" style="width: 8%;">客户代码</td>
					<td colspan="1" style="width: 10%;">${salePoDto.clientNameCode }</td>
					
					<td colspan="5" style="width: 10%;" class="t_title">PI编号</td>
					<td colspan="5" style="width: 15%;"><input id="ip_piNo" name="piNo" style="height: 100%;width: 100%" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:false,validType:['length[0,64]']" value=""></td>
				</tr>
				 <tr class="tr_border" style="height: 35px;">
					<td colspan="6" rowspan=2 style="width: 24%;" class="t_title">业务划分</td>
					<td colspan="10" style="width: 51%;">销售经理：${salePoDto.seller }</td>
					<td colspan="5" rowspan=2 style="width: 10%;" class="t_title">下单日期</td>
					<td colspan="5" rowspan=2 style="width: 15%;"><fmt:formatDate value="${salePoDto.dateDelivery }" type="date" dateStyle="long"/></td>
				</tr>
				<tr style="height: 35px;">
					<td colspan="5" style="width: 26.5%;">产品经理：<input id="ip_pm" name="pm" style="height: 100%;width: 35%" type="text" placeholder="请输入" class="easyui-validatebox" data-options="validType:['length[0,64]']" value=""></td>
					<td colspan="5" style="width: 26.5%;border-left:solid 1px gray;">跟单：${salePoDto.merchandiser }</td>
				</tr>
				<tr class="tr_border" style="height: 35px;">
					<td colspan="6"  style="width: 24%;" class="t_title">预计验货日期</td>
					<td colspan="10" style="width: 51%;text-align: center;"><input id="ip_dateExaminePre" name="dateExaminePre" style="width: 30%;height: 100%" type="text" class="easyui-datebox" data-options="required:false,editable:false " value=""></td>
					<td colspan="5" style="width: 10%;" class="t_title">交货日期</td>
					<td colspan="5" style="width: 15%;"><fmt:formatDate value="${salePoDto.dateDelivery }" type="date" dateStyle="long"/></td>
				</tr>
				<tr class="tr_border" style="height: 35px;">
					<td colspan="6"  style="width: 24%;" class="t_title">加工工厂</td>
					<td colspan="20" style="width: 76%;"></td>
				</tr><!--  样式由下面控制 -->
		    	<tr class="tr_insert" class="tr_border t_title" style="height: 35px;">  
					<td colspan="3" style="width: 12%;" class="t_title">NO.</td>
					<td colspan="3" style="width: 12%;" class="t_title">产品名称</td>
					
					<td colspan="7" style="width: 30%;" class="t_title">型号规格</td>
					<td colspan="1" style="width: 8%;" class="t_title">数量</td>
					<td colspan="1" style="width: 5%;" class="t_title">币种</td>
					<td colspan="1" style="width: 8%;" class="t_title">单价</td>
				 		
					<td colspan="5" style="width: 10%;" class="t_title">金额</td>
					<td colspan="4" style="width: 12.5%;" class="t_title">备注</td>
					<td colspan="1" style="width: 1.5%;border-left:hidden;"><img id="addImage" src="${ctx}/jslib/easyui1.4.2/themes/icons/add.png" style="cursor:pointer;"></img></td>
				</tr>
				
				<tr class="tr_border" style="height: 35px;">  
					<td colspan="6" style="width: 24%;" class="t_title">合计(美金大写)：</td>
					<td colspan="9" style="width: 45%;font-weight: bold;color: blue;" id="td_total_gbk" ></td>
					<td colspan="1" style="width: 6%;" class="t_title">小写：</td>
					<td colspan="5" style="width: 10%;font-weight: bold;color: blue;" id="td_total">0.00</td>
					<td colspan="5" style="width: 15%;" class="t_title">币种:<input id="ip_currency" name="currency" style="width: 52%;height: 100%;color:red" type="text" placeholder="输入币种" class="easyui-validatebox" data-options="required:true,validType:['length[0,20]']" value="USD"></td>				</tr>
				<tr>
					<td colspan="26" style="height: 200px;"><textarea id="tx_description" name="description" style="width: 100%;height: 100%;" placeholder="请输入" maxlength="2000" ></textarea></td>
				</tr>
				<tr class="tr_border" style="height: 35px;">
					<td colspan="6"  style="width: 24%;font-size:14px;">拟制</td>
					<td colspan="5" style="width: 25.5%;"></td>
					<td colspan="5" style="width: 25.5%;border-left:solid 1px gray;font-size:14px;">批准</td>
					<td colspan="10" style="width: 25%;font-size:14px;">需方签章</td>
				</tr>
				<tr class="tr_border" style="height: 40px;">
					<td class="t_title" colspan="6" style="width: 24%;"><input id="ip_maker" name="maker" style="width: 100%;height: 100%;text-align:center;" placeholder="请输入" type="text" class="easyui-validatebox" data-options="validType:['length[0,64]']" value="${sessionInfo.name}"></td>
					<td colspan="5" style="width: 25.5%;"></td>
					<td class="t_title" colspan="5" style="width: 25.5%;border-left:solid 1px gray;"></td>
					<td class="t_title" colspan="10" style="width: 25%;"> </td>
				</tr>
			</table>
			<br>
			<div style="text-align: center;font-weight: bold;"><a href="javascript:void(0)" id="btn-summit" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">提交</a></div>
		</form>
	</div>
</div>
</body>
<style>
	* {
	  font-size: 13px; 
	  font-family: "Microsoft YaHei";
	}
	body {
		padding: 0;
		padding-top:1%;
	}
	input, textarea{font-family: "Microsoft YaHei";}
	#span_orderNo {
		float: right;
		position: absolute;
		right: 3.5%;
		top: 130px;
	}
</style>
<script>
function summit() {
	var isValid = $('#salePoAddForm').form('validate'); // 验证不通过则不执行
	if (!isValid) {
		return; // 验证不通过则不执行
	}
	

	progressLoad(); // 继续执行则开启进度条
 	var salePoListCmd = [];
	$('.tr_insert_new').each(function(){
        var tdArr = $(this).children();
        var number = tdArr.eq(0).find('input').val()==null?null:tdArr.eq(0).find('input').val().trim(); //产品编号
        var productName = tdArr.eq(1).find('input').val()==null?null:tdArr.eq(1).find('input').val().trim(); //产品类型
        var specification = tdArr.eq(2).find('textarea').val()==null?null:tdArr.eq(2).find('textarea').val().trim(); //规格型号
        var quality = tdArr.eq(3).find('input').val(); //产品数量
        var unitPrice = tdArr.eq(5).find('input').val(); //单价
        var description = tdArr.eq(7).find('textarea').val()==null?null:tdArr.eq(7).find('textarea').val().trim(); //收入金额
        var salePoList = {
        		number : number,
        		productName : productName,
        		specification : specification,
        		quality : quality,
        		unitPrice : unitPrice,	
        		description : description,	
		};
        salePoListCmd.push(salePoList);

    });
 	var salePoCmd = {
			pm: $('#ip_pm').val().trim(),
			orderId: "${salePoDto.orderId }",
			company: $('#ip_company').val()==null?null:$('#ip_company').val().trim(),
			address: $('#ip_address').val()==null?null:$('#ip_address').val().trim(),
			tel: $('#ip_tel').val()==null?null:$('#ip_tel').val().trim(),
			fax: $('#ip_fax').val()==null?null:$('#ip_fax').val().trim(),
			homepage: $('#ip_homepage').val()==null?null:$('#ip_homepage').val().trim(),
			piNo: $('#ip_piNo').val()==null?null:$('#ip_piNo').val().trim(),
			dateExaminePre: $('#ip_dateExaminePre').datebox('getValue'),
			currency: $('#ip_currency').combobox('getValue')==null?null:$('#ip_currency').combobox('getValue').trim(),
			description: $('#tx_description').val()==null?null:$('#tx_description').val().trim(),
			maker: $('#ip_maker').val()==null?null:$('#ip_maker').val().trim(),
			//approver: $('#ip_approver').val()==null?null:$('#ip_approver').val().trim(), //审核的时候才写入数据库
			salePoListCmd: salePoListCmd,
	};
	$.ajax({
		url : '${pageContext.request.contextPath}/salePo/add',
		type : "POST",
		data : JSON.stringify(salePoCmd),
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		async: false,
		success : function(result) {
			progressClose();
			if (result.success) {
				parent.$.messager.alert('提示', '添加成功');
       			var parent_tabs = parent.$('#index_tabs');
       		 	var index = parent_tabs.tabs('getTabIndex', parent.$('#index_tabs').tabs('getSelected'));
				 var tab = parent_tabs.tabs('getTab', index);
				 if (tab.panel('options').closable) {
					parent_tabs.tabs('close', index);
				 }
				parent_tabs.tabs('select', "个人PO")
				parent_tabs.tabs('select', "PO信息");
				parent_tabs.tabs('update', {
					tab : parent_tabs.tabs('getSelected'), //获取当前被选中的页面
					options : {
						//Title : '新标题',
						}
					}
				);
			} else {
				parent.$.messager.alert('错误', result.msg, 'error');
			}
		}
	}); 
 }
</script>
<script src="${ctx}/lib/util/util_common.js" type="text/javascript" charset="utf-8"></script>