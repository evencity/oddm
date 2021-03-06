package com.apical.oddm.web.controller.sale;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.sale.proto.SaleProtoCmd;
import com.apical.oddm.facade.sale.proto.SaleProtoDto;
import com.apical.oddm.facade.sale.proto.SaleProtoFacadeI;
import com.apical.oddm.facade.sale.util.SaleProtoExportExcel;
import com.apical.oddm.facade.util.TimeUtil;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.pageModel.base.Json;

@Controller
@RequestMapping("/saleProto")
public class SaleProtoController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(SaleProtoController.class);

	@Autowired
	private SaleProtoFacadeI saleProtoFacade;

	@RequestMapping("/manager")
	public String manager() {
		return "/sale/proto/saleProtoList";
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody  //jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public Pager<SaleProtoDto> dataGrid(HttpServletRequest request, SaleProtoCmd saleProtoCmd, PageCommand pageCommand) {  //此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		try {
			/*SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
			Integer userId = sessionInfo.getId();
			if (userId == null) return null;
			SystemContext.setCurrUserId(userId);*/
			SystemContext.setPageOffset(pageCommand.getPage());
			SystemContext.setPageSize(pageCommand.getRows());
			SystemContext.setOrder(pageCommand.getOrder());
			SystemContext.setSort("t."+pageCommand.getSort());
			return saleProtoFacade.dataGrid(saleProtoCmd);
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}
	
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		request.setAttribute("sysConfig", null);
		return "/sale/proto/saleProtoAdd";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Json add(@RequestBody SaleProtoCmd saleProtoCmd, HttpServletRequest request) {
		//SessionPi sessionPi = (SessionPi) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		try {
			//System.err.println("saleProtoCmd   "+saleProtoCmd);
			Serializable success = saleProtoFacade.add(saleProtoCmd);
			if (success != null) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！");
			}
		}/* catch (ConstraintViolationException e) {
			j.setMsg("发票号:"+saleProtoCmd.getInvoiceNo()+"已经存在！");
		}*/ catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("系统出错：" + e.getMessage());
			log.error("添加失败", e);
		}
		return j;
	}
	
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		SaleProtoDto saleProtoDto = saleProtoFacade.get(id);
		request.setAttribute("saleProtoDto", saleProtoDto);
		return "/sale/proto/saleProtoEdit";
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(@RequestBody SaleProtoCmd saleProtoCmd, HttpServletRequest request) {
		Json j = new Json();
		if (saleProtoCmd.getId() == null) {
			j.setMsg("id不能为空！");
		} else {
			try {
				//System.err.println("saleProtoCmd   "+saleProtoCmd);
				saleProtoFacade.edit(saleProtoCmd);
				j.setSuccess(true);
				j.setMsg("添加成功！");
			}/* catch (DataIntegrityViolationException e) {
				j.setMsg("发票号:"+saleProtoCmd.getInvoiceNo()+"已经存在！");
			} */catch (Exception e) {
				j.setSuccess(false);
				j.setMsg("系统出错：" + e.getMessage());
				log.error("添加失败", e);
			}
		}
		return j;
	}
	
	@RequestMapping("/getPage")
	public String getPage(HttpServletRequest request, Integer id) {
		SaleProtoDto saleProtoDto = saleProtoFacade.get(id);
		request.setAttribute("saleProtoDto", saleProtoDto);
		return "/sale/proto/saleProtoDetail";
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		try {
			saleProtoFacade.delete(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	@RequestMapping("/excelExport")
	public void excelExport(SaleProtoCmd saleProtoCmd, HttpServletResponse response, HttpServletRequest request) {
		//SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		try {
			List<SaleProtoDto> list = saleProtoFacade.list(saleProtoCmd);
			String fileName = "样机汇总表  ";
			HSSFWorkbook wb = SaleProtoExportExcel.export(list);
			fileName = new String(fileName.getBytes("gb2312"), "ISO-8859-1");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + TimeUtil.dateToStringForExcel(new Date()) + ".xls");
			OutputStream ouputStream = response.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			log.error("导表出错：", e);
		}
	}
}
