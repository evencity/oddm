package com.apical.oddm.web.controller.encase;

import java.io.OutputStream;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.application.sys.SysConfigServiceI;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.encase.dto.InvoiceInfoDTO;
import com.apical.oddm.facade.encase.facade.InvoiceFacade;
import com.apical.oddm.facade.encase.util.ReportInvoiceExcel;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;
import com.apical.oddm.web.controller.order.OrderFollowupController;
import com.apical.oddm.web.pageModel.base.Json;
@Controller
@RequestMapping("/invoice")
public class InvoiceController {

	private static final Logger log = LoggerFactory.getLogger(OrderFollowupController.class);
	
	@Autowired
	private InvoiceFacade invoiceFacade;
	
	@Autowired
	private SysConfigServiceI sysConfigService;
	
	@RequestMapping("/manager")
	public String manager() {
		return "/encase/invoice";
	}
	@RequestMapping("/dataGrid")
	@ResponseBody
	public BasePage<InvoiceInfoDTO> dataGrid(InvoiceInfoDTO invoiceInfoDTO, PageCommand pageCommand, HttpServletRequest request) { // 
		try {
			return invoiceFacade.dataGrid(invoiceInfoDTO, pageCommand);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/getPage")
	public String getPage(HttpServletRequest request,Integer id) {
		request.setAttribute("invoiceId", id);
		return "/encase/invoiceDetails";
	}
	@RequestMapping("/get")
	@ResponseBody
	public InvoiceInfoDTO get(InvoiceInfoDTO invoiceInfoDTO, HttpServletRequest request) { // 
		return invoiceFacade.getInvoiceInfoById(invoiceInfoDTO.getId());
	}
	@RequestMapping("/addPage")
	public String addPage(InvoiceInfoDTO invoiceInfoDTO, HttpServletRequest request) {
		/*SysConfig sysConfig = sysConfigService.get("currency_alphabet");
		request.setAttribute("sysConfig", sysConfig);*/
		return "/encase/invoiceAdd";
	}

	@ResponseBody
	@RequestMapping("/add")
	public Json addInvoiceInfo(@RequestBody InvoiceInfoDTO invoice) {
		Json json = new Json();
		try {
			Integer id = invoiceFacade.add(invoice);
			if(id != null){
				json.setSuccess(true);
				json.setMsg("添加成功");
			}else {
				json.setMsg("添加失败");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("添加失败：",e);
			json.setMsg("添加失败:"+e);
			json.setSuccess(false);
		}
		return json;
	}

	

	@RequestMapping("/editPage")
	public String editPage(Integer id, HttpServletRequest request) {
		/*SysConfig sysConfig = sysConfigService.get("currency_alphabet");
		request.setAttribute("sysConfig", sysConfig);*/
		request.setAttribute("invoiceId", id);
		return "/encase/invoiceEdit";
	}
	@ResponseBody
	@RequestMapping("/edit")
	public Json updateInvoiceInfo(@RequestBody InvoiceInfoDTO invoice) {
		Json json = new Json();
		try {
			invoiceFacade.edit(invoice);
			json.setSuccess(true);
			json.setMsg("编辑成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.error("编辑出错：",e);
			json.setMsg("编辑失败");
			json.setSuccess(false);
		}
		
		return json;
	}

	@ResponseBody
	@RequestMapping(value="/delete")
	public Json delete(Integer id){
		Json json = new Json();
		try {
			invoiceFacade.detele(id);
			json.setSuccess(true);
			json.setMsg("删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.error("删除出错：",e);
			json.setMsg("删除失败"+e);
			json.setSuccess(false);
		}
		
		return json;
	}
	
	@RequestMapping("/getByOrderDTO")
	@ResponseBody
	public OrderInfoDTO getByOrderDTO(OrderInfoCommand orderInfoCommand, HttpServletRequest request) { // 
		OrderInfoDTO byOrderDTO = invoiceFacade.getByOrderDTO(orderInfoCommand);
		if(byOrderDTO == null){
			byOrderDTO = new OrderInfoDTO();
		}
		return byOrderDTO;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/excelExport")
	public void excelExport(HttpServletResponse response,HttpServletRequest request,Integer id) {
		if(id != null){
			try {
				String imgPath = request.getSession().getServletContext().getRealPath("/")+"/style/images/invoiceLogo.png";
				String fileName = "报关发票";
				/*if(invoiceFacade.get(id) != null){
					fileName += invoiceFacade.get(id).getName();
				}*/
				HSSFWorkbook wb = ReportInvoiceExcel.creatExcelInvoiceInfo(invoiceFacade.getInvoiceById(id),imgPath);
				fileName = new String(fileName.getBytes("gb2312"), "ISO-8859-1");
		        response.setContentType( "application/vnd.ms-excel" );    
		        response.setHeader( "Content-disposition" ,  "attachment;filename="+fileName+".xls" ); 
		        OutputStream ouputStream = response.getOutputStream();    
		        wb.write(ouputStream);    
		        ouputStream.flush();    
		        ouputStream.close();  
			} catch (Exception e) {
				// TODO: handle exception
				log.error("导表出错：",e);
			}
			}
	}
}
