package com.apical.oddm.web.controller.sale;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.application.sys.SysConfigServiceI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.core.model.sys.SysConfig;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.sale.lc.SaleLcCmd;
import com.apical.oddm.facade.sale.lc.SaleLcDto;
import com.apical.oddm.facade.sale.lc.SaleLcFacadeI;
import com.apical.oddm.facade.sale.util.SaleLcExportExcel;
import com.apical.oddm.facade.util.TimeUtil;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.pageModel.base.Json;

@Controller
@RequestMapping("/saleLc")
public class SaleLcController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(SaleLcController.class);

	@Autowired
	private SaleLcFacadeI saleLcFacade;

	@Autowired
	private SysConfigServiceI sysConfigService;
	
	@RequestMapping("/manager")
	public String manager() {
		return "/sale/lc/saleLcList";
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody  //jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public Pager<SaleLcDto> dataGrid(HttpServletRequest request, SaleLcCmd saleLcCmd, PageCommand pageCommand) {  //此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		try {
			/*SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
			Integer userId = sessionInfo.getId();
			if (userId == null) return null;
			SystemContext.setCurrUserId(userId);*/
			SystemContext.setPageOffset(pageCommand.getPage());
			SystemContext.setPageSize(pageCommand.getRows());
			SystemContext.setOrder(pageCommand.getOrder());
			SystemContext.setSort("t."+pageCommand.getSort());
			return saleLcFacade.dataGrid(saleLcCmd);
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}
	
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		SysConfig sysConfig = sysConfigService.get("currency_alphabet");
		request.setAttribute("sysConfig", sysConfig);
		return "/sale/lc/saleLcAdd";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Json add(@RequestBody SaleLcCmd saleLcCmd, HttpServletRequest request) {
		//SessionPi sessionPi = (SessionPi) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		try {
			//System.err.println("saleLcCmd   "+saleLcCmd);
			Serializable success = saleLcFacade.add(saleLcCmd);
			if (success != null) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！");
			}
		} catch (ConstraintViolationException e) {
			j.setMsg("发票号:"+saleLcCmd.getInvoiceNo()+"已经存在！");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("系统出错：" + e.getMessage());
			log.error("添加失败", e);
		}
		return j;
	}
	
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		SaleLcDto saleLcDto = saleLcFacade.get(id);
		SysConfig sysConfig = sysConfigService.get("currency_alphabet");
		request.setAttribute("sysConfig", sysConfig);
		request.setAttribute("saleLcDto", saleLcDto);
		return "/sale/lc/saleLcEdit";
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(@RequestBody SaleLcCmd saleLcCmd, HttpServletRequest request) {
		Json j = new Json();
		if (saleLcCmd.getId() == null) {
			j.setMsg("id不能为空！");
		} else {
			try {
				//System.err.println("saleLcCmd   "+saleLcCmd);
				saleLcFacade.edit(saleLcCmd);
				j.setSuccess(true);
				j.setMsg("添加成功！");
			} catch (DataIntegrityViolationException e) {
				j.setMsg("发票号:"+saleLcCmd.getInvoiceNo()+"已经存在！");
			} catch (Exception e) {
				j.setSuccess(false);
				j.setMsg("系统出错：" + e.getMessage());
				log.error("添加失败", e);
			}
		}
		return j;
	}
	
	@RequestMapping("/getPage")
	public String getPage(HttpServletRequest request, Integer id) {
		SaleLcDto saleLcDto = saleLcFacade.get(id);
		request.setAttribute("saleLcDto", saleLcDto);
		return "/sale/lc/saleLcDetail";
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		try {
			saleLcFacade.delete(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	@RequestMapping("/excelExport")
	public void excelExport(SaleLcCmd saleLcCmd, HttpServletResponse response, HttpServletRequest request) {
		//SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		try {
			List<SaleLcDto> list = saleLcFacade.list(saleLcCmd);
			String fileName = "(信用证台账)LC收款台账  ";
			HSSFWorkbook wb = SaleLcExportExcel.export(list);
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
