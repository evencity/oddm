<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 <style>
 *{padding:0;margin:0;border:0;}
body{font-family: "宋体", Arial, Helvetica, sans-serif;font-size:9pt;font-weight:400;font-style:normal;font-variant:normal;color:#000;line-height:140%; background-color:#FFF;}
.A4page{width:172mm;}
.tdtitle{width:100%;height:5mm; font-size:11pt;font-weight:600; color:#000;padding-top:2mm;}
.lefttd{width:17mm;}.righttd{width:26mm;}
.bl{color:#000;}
.showselectdtable
{
   /*  width: 170mm; */
   width: 100%;
    border-right: 1px solid #666666;
    border-bottom: 1px solid #666666;
}
.showselectdtable tr td{border-left:1px solid #666666;border-top:1px solid #666666;padding-left:1mm;}
.selectd_titletd{height:4mm;}
.bottdiv{padding-top:1mm;}
.maintitles{
    text-align:center;
    font-size: 16pt;
    font-weight: bold;padding:3mm 0mm;
}
.tableTitle{
	margin: 8px 0px;
}
 
</style> 
    <title>订单信息表</title>
<script type="text/javascript">
	window.onload = function() {
		PrintPage();
    }
	var hkey_root,hkey_path,hkey_key
	hkey_root="HKEY_CURRENT_USER"
	hkey_path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\"

	// 设置页眉页脚为空
	function PageSetup_Null()
	{
	 try{
	  var RegWsh = new ActiveXObject("WScript.Shell") ;
	  hkey_key="header" ;
	  RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"") ;
	  hkey_key="footer" ;
	  RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"") ;
	  }
	 catch(e){}
	}

	// 设置页眉页脚为默认值
	function PageSetup_Default()
	{
	 try{
	  var RegWsh = new ActiveXObject("WScript.Shell") ;
	  hkey_key="header" ;
	  RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"&w&b页码，&p/&P") ;
	  hkey_key="footer" ;
	  RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"&u&b&d") ;
	  }
	 catch(e){}
	}

	// 打印
	function PrintPage()
	{
	 PageSetup_Null() ;
	 window.print() ;
	 PageSetup_Default() ;
	}
</script>
<body style="padding: 0px;background: white">
	<div>
	   <div class="maintitles">深圳市爱培科技术股份有限公司 </div>
		<!-- 基础信息 -->
		 <table border="0" cellpadding="0" cellspacing="0" class="tableTitle" >
       		 <tr class="tdtitle">
	            <td colspan="8">基础信息：
	            	 <c:if test="${orderInfoDTO.pid==null}">
	            		<span id="isNeworder" class="color_f60">[新订单]</span> 
					</c:if>
					 <c:if test="${orderInfoDTO.pid!=null}">
	            		<span id="isNeworder" class="color_f60">[返单]</span> 
					</c:if>
	            </td>
	        </tr>
       </table>
		<table border="0" cellpadding="0" cellspacing="0" class="">
	       
	        <tr>
	            <td class="lefttd">订单号：</td>
	            <td class="righttd">${orderInfoDTO.orderNo} </td>
	            <td class="lefttd">跟单名称：</td>
	            <td class="righttd">${orderInfoDTO.merchandiser}  </td>
	            <td class="lefttd">业务名称：</td>
	            <td class="righttd">${orderInfoDTO.seller}  </td>
	            <td class="lefttd"> 工厂机型：</td>
	            <td class="righttd">${orderInfoDTO.productFactory}  </td>
	        </tr>
	        <tr>
	            <td class="lefttd">订单数量： </td>
	            <td class=""> ${orderInfoDTO.quantity}</td>
	            <td class="lefttd">客户名称：</td>
	            <c:if test="${orderInfoDTO.clientName == orderInfoDTOParent.clientName}">
            		<td class="righttd"> ${orderInfoDTO.clientName}</td>
				</c:if>
				 <c:if test="${orderInfoDTO.clientName != orderInfoDTOParent.clientName}">
            		<td class="righttd"> ${orderInfoDTO.clientName}[新]</td>
				</c:if>
	            <td class="lefttd">客户铭牌：</td>
	            <c:if test="${orderInfoDTO.clientBrand == orderInfoDTOParent.clientBrand}">
            		<td class="righttd"> ${orderInfoDTO.clientBrand}</td>
				</c:if>
				 <c:if test="${orderInfoDTO.clientBrand != orderInfoDTOParent.clientBrand}">
            		<td class="righttd"> ${orderInfoDTO.clientBrand}[新]</td>
				</c:if>
	            <td class="lefttd">客户编码：</td>
	            <td class="righttd">${orderInfoDTO.clientNameCode}  </td>
	        </tr>
	        <tr>
	            <td class="lefttd">所在国家：</td>
	            <c:if test="${orderInfoDTO.district == orderInfoDTOParent.district}">
            		 <td class="righttd">${orderInfoDTO.district}  </td>
				</c:if>
				 <c:if test="${orderInfoDTO.district != orderInfoDTOParent.district}">
            		<td class="righttd"> ${orderInfoDTO.district}[新]</td>
				</c:if>
	           
	       
	            <td class="lefttd">客户型号：</td>
	            <c:if test="${orderInfoDTO.productClient == orderInfoDTOParent.productClient}">
            		<td class="righttd"> ${orderInfoDTO.productClient} </td>      
				</c:if>
				 <c:if test="${orderInfoDTO.productClient != orderInfoDTOParent.productClient}">
            		<td class="righttd"> ${orderInfoDTO.productClient} [新]</td>
				</c:if>
	              
	            <td class="lefttd">下单日期：</td>
	            <td class="righttd"> ${orderInfoDTO.dateOrder} </td>
	            <td class="lefttd">验货日期：</td>
	            <td class="righttd">${orderInfoDTO.dateExamine}  </td>
	        </tr>
	        <tr>
	            <td class="lefttd">交货日期:</td>
	            <td class="righttd">${orderInfoDTO.dateDelivery}  </td>
	        
	            <td class="lefttd">交货地点：</td>
	            <c:if test="${orderInfoDTO.placeDelivery == orderInfoDTOParent.placeDelivery}">
            		 <td class="righttd">${orderInfoDTO.placeDelivery}</td>   
				</c:if>
				 <c:if test="${orderInfoDTO.placeDelivery != orderInfoDTOParent.placeDelivery}">
            		<td class="righttd"> ${orderInfoDTO.placeDelivery} [新]</td>
				</c:if>
	           
	        	<td class="lefttd">付款方式：</td>
	        	 <c:if test="${orderInfoDTO.payment == orderInfoDTOParent.payment}">
            		 <td class="righttd">${orderInfoDTO.payment}</td>   
				</c:if>
				 <c:if test="${orderInfoDTO.payment != orderInfoDTOParent.payment}">
            		<td class="righttd"> ${orderInfoDTO.payment} [新]</td>
				</c:if>
	           
	            <c:if test="${orderInfoDTO.pid!=null}}">
            		<td class="lefttd">返单单号：</td>
            		<td class="righttd" colspan="3">${orderInfoDTO.orderInfoDTO.orderNo}( ${orderInfoDTO.orderInfoDTO.productFactory} ) 客："+${orderInfoDTO.orderInfoDTO.productClient}</td>
				</c:if>
	            
	        </tr>
	    </table>
	    
	    <!-- 外壳工艺 -->
       <table border="0" cellpadding="0" cellspacing="0" class="tableTitle" >
       		<tr class="tdtitle">
	            <td colspan="4">外壳工艺：</td>
	        </tr>
       </table>
	    <table border="0" cellpadding="0" cellspacing="0" style=" width: 100%;" >
	        
	        <c:forEach items="${orderInfoDTO.orderShellDTOs}" var="item">
	        	<tr>
	        		
					<c:if test="${item.isNew == 1}">
	            		<td>部件：<span class="color069">${item.name} </span></td>
					</c:if>
					<c:if test="${item.isNew == 2}">
	            		<td>部件：<span class="color069">${item.name}[新]</span></td>
					</c:if>
					
					<td>颜色：<span class="color069">${item.color}</span></td>
					<td>工艺：<span class="color069">${item.craft} </span></td>
					 <c:if test="${item.silkScreen == 1}">
	            		<td>丝印：<span class="color069">无</span></td>
					</c:if>
					<c:if test="${item.silkScreen == 2}">
	            		<td>丝印：<span class="color069">有</span></td> 
					</c:if>
					
				</tr>
	        </c:forEach>
	    </table>
	   <table border="0" cellpadding="0" cellspacing="0" class="tableTitle" >
       		<tr class="tdtitle">
	            <td colspan="8">软件信息： </td>
	        </tr>
       </table>
	    <table border="0" cellpadding="0" cellspacing="0" class="">
	       
	        <tr>
	        	<td class="lefttd">软件：</td>
	            <td colspan="">
	             	<c:if test="${orderInfoDTO.orderOSDTO.isrepeat == 1}">
	            		<span id="isrepeat" class="color_f60">[新软件]</span> 
					</c:if>
					<c:if test="${orderInfoDTO.orderOSDTO.isrepeat == 2}">
	            		<span id="isrepeat" class="color_f60">[返单软件]</span> 
					</c:if>
	            </td>
	            <td class="lefttd">是否定制：</td>
	            <td colspan="5">
	             	<c:if test="${orderInfoDTO.orderOSDTO.iscustom == 1}">
	            		<span id="isNeworder" class="color_f60">定制软件</span> 
					</c:if>
					<c:if test="${orderInfoDTO.orderOSDTO.iscustom == 2}">
	            		<span id="isNeworder" class="color_f60">公版软件</span> 
					</c:if>
	            </td>
	        </tr>
	        <tr>            
	        	<td class="lefttd">GUI：</td>
	            <td class="righttd"> ${orderInfoDTO.orderOSDTO.gui} </td>
	            <td class="lefttd"> UUID号 ：</td>
	            <td class="righttd">${orderInfoDTO.orderOSDTO.uuid} </td>
	            <td class="lefttd">系统语言： </td>
	            <td class="">${orderInfoDTO.orderOSDTO.langOs} </td>
	            <td class="lefttd">默认语言：</td>
	            <td class="righttd">${orderInfoDTO.orderOSDTO.langDefault} </td>
	        </tr>
	        <tr>
	            <td class="lefttd">地区时间：</td>
	            <td class="righttd"> ${orderInfoDTO.orderOSDTO.timezone} </td>
	           <td class="lefttd">开机画面：</td>
	            <td class="righttd">${orderInfoDTO.orderOSDTO.bootLogo}</td>
	            <td class="lefttd">预存文件：</td>
	            <td class="righttd"> ${orderInfoDTO.orderOSDTO.preFile}</td>
	            <td class="lefttd">预存地图：</td>
	            <td class="righttd">${orderInfoDTO.orderOSDTO.preMap} </td>
	        </tr>
	    </table>
	    
	    <!-- 裸机包装 -->
	    <table border="0" cellpadding="0" cellspacing="0" class="tableTitle" >
       <tr class="tdtitle">
	            <td colspan="3">硬件部件：</td>
	        </tr>
       </table>
	     <table border="0" cellpadding="0" cellspacing="0" class="showselectdtable" style="margin: 0px">
	       
			<tr id="materialBareMain">
	            <td width="25%">物料名称</td>
	            <td width="30%">供应商</td>
	            <td width="45%">规格型号</td>
	        </tr>
	         <c:forEach items="${orderInfoDTO.orderHardwareDTOs}" var="item">
	        	<tr>
	        		<c:if test="${item.isNew == 1}">
	            		<td>${item.name} </td>
					</c:if>
					<c:if test="${item.isNew == 2}">
	            		<td>${item.name}[新]</td>
					</c:if>
					<td>${item.supplier}</td>
					<td>${item.specification}</td>
				</tr>
	        </c:forEach>
	    </table>
	     
	    
	    <!-- 配件信息-->
	    <table border="0" cellpadding="0" cellspacing="0" class="tableTitle" >
        	<tr class="tdtitle">
	            <td colspan="3">配件部分： </td>
	        </tr>
       </table>
	     <table border="0" cellpadding="0" cellspacing="0" class="showselectdtable">
	       
	        <tr id="materialFitting">
	            <td width="25%">物料名称</td>
	            <td width="30%">供应商</td>
	            <td width="45%">规格型号</td>
	        </tr>
	         <c:forEach items="${orderInfoDTO.orderFittingDTOs}" var="item">
	        	<tr>
					<c:if test="${item.isNew == 1}">
	            		<td>${item.name} </td>
					</c:if>
					<c:if test="${item.isNew == 2}">
	            		<td>${item.name}[新]</td>
					</c:if>
					<td>${item.supplier}</td>
					<td>${item.specification}</td>
				</tr>
	        </c:forEach>
	    </table>
	    
	      <!-- 包材信息-->
	   <table border="0" cellpadding="0" cellspacing="0" class="tableTitle" >
        	<tr class="tdtitle">
	            <td colspan="3">包材部分： </td>
	        </tr>
       </table>
	     <table border="0" cellpadding="0" cellspacing="0" class="showselectdtable">
	       
	        <tr id="materialPacking">
	            <td width="25%">物料名称</td>
	            <td width="30%">供应商</td>
	            <td width="45%">规格型号</td>
	        </tr>
	         <c:forEach items="${orderInfoDTO.orderPackingDTOs}" var="item">
	        	<tr>
					<c:if test="${item.isNew == 1}">
	            		<td>${item.name} </td>
					</c:if>
					<c:if test="${item.isNew == 2}">
	            		<td>${item.name}[新]</td>
					</c:if>
					<td>${item.supplier}</td>
					<td>${item.specification}</td>
				</tr>
	        </c:forEach>
	        
	    </table>
	      <!-- 备品-->
	      <table border="0" cellpadding="0" cellspacing="0" class="tableTitle">
	        <tr class="tdtitle">
	            <td colspan="3">订单备品： </td>
	        </tr>
	        
	    </table>
	     <table border="0" cellpadding="0" cellspacing="0" class="">
	       
	        <tr>
	        	<td colspan="3">${orderInfoDTO.orderSpare} </td>
	        </tr>
	    </table>
	     <!-- 备品说明-->
	     <table border="0" cellpadding="0" cellspacing="0"  class="tableTitle">
	        <tr class="tdtitle">
	            <td colspan="3">备注说明： </td>
	        </tr>
	       
		</table>
	     <table border="0" cellpadding="0" cellspacing="0"  class="">
	       
	        <tr>
	        	<td colspan="3">${orderInfoDTO.description}  </td>
	        </tr>
		</table>
	 </div>
</body>