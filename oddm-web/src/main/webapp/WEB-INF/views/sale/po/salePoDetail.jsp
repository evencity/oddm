<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
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
		background-repeat:  no-repeat ;
		background-position: left top;
		background-size: 150px 45px;
	}
	/* #addImage { 
		margin:-4px;
	}  */
			
</style>

<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript">
 //提交到后台
	$(function() {
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
		//必须重新计算css等
		$('.tr_insert_new').each(function(){
	        var tdArr = $(this).children();
	        var qlity = tdArr.eq(3).text(); //产品数量
	        var uniP = tdArr.eq(5).text(); //单价

	        uniP = parseFloat(uniP.replace(/,/g,""));
	        tdArr.eq(6).text(toMoney((qlity*uniP).toFixed(2), 2));
	    });
		calcTotal();
		//遍历计算No.的长度，动态计算、显示编号的宽度
		var len = 0;
		$('.td_number').each(function(){
			if($(this).text().length > len) {
				len = $(this).text().length;
			}
		});
		if (len <= 5) {
			$('.td_dynamic').css({ //td_dynamic
				"width": 5+"%",
			});
		}
		
		//使用ajax提交
		$('#btn-summit').click(function(){
			summit();
		});
		$('#btn-audit').click(function(){
			summitAudit();
		});
	});
 
 
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
	
	//变更记录对话框
	function openAlterInfo() {
		parent.$.modalDialog({
			title : '订单变更记录',
			width : 800,
			height : 600,
			href : '${ctx}/salePo/poAltPage?poId=${salePoDto.id}',
			buttons : [ {
				text : '关闭',
				handler : function() {
					parent.$.modalDialog.handler.dialog('close');
				}
			} ]
		});
	}
</script>
<body>
<div class="styleeasyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;width: 95%;margin:0 3% 0 2%" >
	<div style="text-align: right;height: 20px;font-weight: bold;">
		<c:choose>
		   <c:when test="${salePoDto.state >= '4'}">  
		   	<span><a href="javascript:void(0)" onclick="openAlterInfo()">[变更记录]</a></span>&nbsp;
		   </c:when>
		   <c:otherwise>
		   </c:otherwise>
		</c:choose>
		<span><a href="${ctx}/salePo/printPage?id=${salePoDto.id}" target="_blank">[打印]</a></span>
	</div>
		<form id="salePoDetailForm" method="post">
			<table class="tableGrid" style="border-collapse:collapse; border: solid 1px gray;width:100%;background-color: #eee"> <!-- ;margin-left: 6px -->
				<tr>
		            <td id="table_company" colspan="26" valign="bottom" style="height: 54px;font-weight: bold;font-size:20px;text-align:center;">${salePoDto.company}</td>
		        </tr>
				<tr>
		            <td colspan="26" valign="bottom" style="text-align:center;">${salePoDto.address}</td>
		        </tr>
				<tr>
		            <td colspan="26" valign="bottom" style="text-align:center;text-align:center;">电话:${salePoDto.tel}&emsp;传真:${salePoDto.fax}&emsp;网址:${salePoDto.homepage}</td>
		        </tr>
				<tr>
		            <td colspan="26" valign="center" style="font-weight: bold;font-size:20px;height: 40px;text-align:center;">Purchase Order</td>
		        </tr>
				<tr class="tr_border" style="height: 35px;">
					<td colspan="6"  style="width: 24%;" class="t_title">客户名称</td>
					<td colspan="8" style="width: 49%;color: #069;">${salePoDto.clientName }</td>
					<td colspan="1" style="width: 8%;">客户代码</td>
					<td colspan="1" style="width: 10%;color: #069;">${salePoDto.clientNameCode }</td>
					
					<td colspan="5" style="width: 10%;" class="t_title">订单编号</td>
					<td colspan="5" style="width: 15%;color: #069;">${salePoDto.orderNo }<c:if test="${not empty salePoDto.piNo }"><br>(${salePoDto.piNo })</c:if></td>
				</tr>
				 <tr class="tr_border" style="height: 35px;">
					<td colspan="6" rowspan=2 style="width: 24%;" class="t_title">业务划分</td>
					<td colspan="10" style="width: 51%;">销售经理：<span style="color: #069;">${salePoDto.seller }</span></td>
					<td colspan="5" rowspan=2 style="width: 10%;" class="t_title">下单日期</td>
					<td colspan="5" rowspan=2 style="width: 15%;color: #069;"><fmt:formatDate value="${salePoDto.dateDelivery }" type="date" dateStyle="long"/></td>
				</tr>
				<tr style="height: 35px;">
					<td colspan="5" style="width: 26.5%;">产品经理：<span style="color: #069;">${salePoDto.pm }</span></td>
					<td colspan="5" style="width: 26.5%;border-left:solid 1px gray;">跟单：<span style="color: #069;">${salePoDto.merchandiser }</span></td>
				</tr>
				<tr class="tr_border" style="height: 35px;">
					<td colspan="6"  style="width: 24%;" class="t_title">预计验货日期</td>
					<td colspan="10" style="width: 51%;text-align:center;color: #069;"><fmt:formatDate value="${salePoDto.dateExaminePre }" type="date" dateStyle="long"/></td>
					<td colspan="5" style="width: 10%;" class="t_title">交货日期</td>
					<td colspan="5" style="width: 15%;color: #069;"><fmt:formatDate value="${salePoDto.dateDelivery }" type="date" dateStyle="long"/></td>
				</tr>
				<tr class="tr_border" style="height: 35px;">
					<td colspan="6"  style="width: 24%;" class="t_title">加工工厂</td>
					<td colspan="20" style="width: 76%;"></td>
				</tr><!--  样式由下面控制 -->
		    	<tr class="tr_insert" class="tr_border t_title" style="height: 35px;">  
					<td colspan="3" style="width: 12%;" class="t_title td_dynamic">NO.</td>
					<td colspan="3" style="width: 12%;" class="t_title">产品名称</td>
					
					<td colspan="7" style="width: 30%;" class="t_title">型号规格</td>
					<td colspan="1" style="width: 8%;" class="t_title">数量</td>
					<td colspan="1" style="width: 5%;" class="t_title">币种</td>
					<td colspan="1" style="width: 8%;" class="t_title">单价</td>
				 		
					<td colspan="5" style="width: 10%;" class="t_title">金额</td>
					<td colspan="5" style="width: 14%;" class="t_title">备注</td>
				</tr>
				
				<c:forEach items="${salePoDto.salePoListsDto }" var="item" varStatus="status">  
				  <tr class="tr_insert tr_insert_new" style="height: 80px;color: #069;">  
				    <td colspan="3" style="width: 12%;" class="td_number td_dynamic">${item.number}</td>
				    <td colspan="3" style="width: 12%;">${item.productName}</td>
				    <td colspan="7" style="width: 30%;word-break: break-all;">${item.specification }</td>
				    <td colspan="1" style="width: 8%;">${item.quality }</td>
					<td class="td_currency" colspan="1" style="width: 5%;">${salePoDto.currency }</td>
					<td colspan="1" style="width: 8%;"><fmt:formatNumber type="number" value="${item.unitPrice }" pattern=",000.00" maxFractionDigits="2"/></td>
					<td class="td_prices" colspan="5" style="width: 10%;">0.00</td>
					<td colspan="5" style="width: 14%;font-weight: bold;word-break: break-all;">${item.description }</td>
				  </tr>  
				</c:forEach>  
				
				<tr class="tr_border" style="height: 35px;">  
					<td colspan="6" style="width: 24%;" class="t_title">合计(美金大写)：</td>
					<td colspan="9" style="width: 45%;font-weight: bold;color: #069;" id="td_total_gbk" ></td>
					<td colspan="1" style="width: 6%;" class="t_title">小写：</td>
					<td colspan="5" style="width: 10%;font-weight: bold;color: #069;" id="td_total">0.00</td>
					<td colspan="5" style="width: 15%;" class="t_title"></td>
				</tr>
				<tr>
					<td colspan="26" valign="top" style="height: 150px;text-align:left;color: #069;"><pre>${salePoDto.description }</pre></td>
				</tr>
				<tr class="tr_border" style="height: 35px;">
					<td colspan="6"  style="width: 24%;font-size:14px;">拟制</td>
					<td colspan="5" style="width: 25.5%;"></td>
					<td colspan="5" style="width: 25.5%;border-left:solid 1px gray;font-size:14px;">批准</td>
					<td colspan="10" style="width: 25%;font-size:14px;">需方签章</td>
				</tr>
				<tr class="tr_border" style="height: 40px;">
					<td class="t_title" colspan="6" style="width: 24%;color: #069;">${salePoDto.maker }</td>
					<td colspan="5" style="width: 25.5%;"></td>
					<td class="t_title" colspan="5" style="width: 25.5%;border-left:solid 1px gray;color: #069;">${salePoDto.approver }</td>
					<td class="t_title" colspan="10" style="width: 25%;"> </td>
				</tr>
			</table>
			<br>
			<c:choose>
			   <c:when test="${salePoDto.state == '1' || salePoDto.state == '3'}">  
			   			<div style="text-align: center;font-weight: bold;"><a href="javascript:void(0)" id="btn-summit" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">提交审核</a></div>
			   </c:when>
			   <c:when test="${salePoDto.state == '2' && salePoDto.approverId == sessionInfo.id}"> 
			   			<div style="text-align: center;font-weight: bold;"><a href="javascript:void(0)" id="btn-audit" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">审核</a></div>
			   </c:when>
			   <c:otherwise>
			   </c:otherwise>
			</c:choose>
		</form>
	</div>
	<div id="poDialogContent" style="display: none;" >
		<div style="height: 100%;width: 100%;text-align: center;">
			<div style="padding-top: 45px;">
			批准人：<input id="ip_po_approverName" name="name" style="width: 50%;" placeholder="请输入名称" type="text" value="">
			</div>
		</div>
	</div>
	<div id="poDialogContentAudit" style="display: none;" >
		<div style="height: 100%;width: 100%;text-align: center;">
			<div id="orderReviewDiv" style="padding-top: 45px;">
				<input type="radio" name="salePoPassOrNo" value="1">通过
				<input type="radio" name="salePoPassOrNo" value="2">不通过
			</div>
		</div>
	</div>
</div>
</body>
<style>
	* {
	  font-size: 13px; 
	  font-family: "Microsoft YaHei";
	}
	body {
		padding:0px;
	}
</style>
<script>
	var content = $('#poDialogContent').html();
	function summit() {
		parent.$.modalDialog({
		    title: '提交审核',
		    width: 300,
		    height: 160,
		    closed: false,
		    cache: false,
		    content: content,
		    modal: true,
		    buttons:[{
				text:'确定',
				handler:function(){
					var userName = parent.$('#ip_po_approverName').val();
					if (userName==null || userName.trim()=="") {
			    		parent.$.messager.alert('提示', "请输入用户名",  'info');
			    		return;
					}
					userName = userName.trim();
					$.ajax({
						type: 'POST',
					    url: "${ctx}/salePo/getByUsName?username="+userName+"&poId=${salePoDto.id}", 
					    dataType: "json",
					    contentType: "application/json; charset=utf-8",
					    success: function(data){
					    	if(data.success){
								parent.$.modalDialog.handler.dialog('close');
								parent.$.messager.alert('提示', data.msg, 'info');
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
					    	}else{
					    		parent.$.messager.alert('提示', data.msg,  'info');
					    	}
					    }
					})
				}
			}]
		}); 
	};
	
	var contentAudit = $('#poDialogContentAudit').html();
	function summitAudit() {
		parent.$.modalDialog({
		    title: '审核',
		    width: 300,
		    height: 160,
		    closed: false,
		    cache: false,
		    content: contentAudit,
		    modal: true,
		    buttons:[{
				text:'确定',
				handler:function(){
					var state = parent.$("input[name='salePoPassOrNo']:checked").val();
					if (state == null || state=="") {
						parent.$.messager.alert('提示', "请选择是否通过",  'info');
			    		return;
					}
					$.ajax({
						type: 'POST',
					    url: "${ctx}/salePo/audit?state="+state+"&poId=${salePoDto.id}", 
					    dataType: "json",
					    contentType: "application/json; charset=utf-8",
					    success: function(data){
					    	if(data.success){
								parent.$.modalDialog.handler.dialog('close');
								parent.$.messager.alert('提示', data.msg, 'info');
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
					    	}else{
					    		parent.$.messager.alert('提示', data.msg,  'info');
					    	}
					    }
					})
				}
			}]
		}); 
	};
</script>
<script src="${ctx}/lib/util/util_common.js" type="text/javascript" charset="utf-8"></script>