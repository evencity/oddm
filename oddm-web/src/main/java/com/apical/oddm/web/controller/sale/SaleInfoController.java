package com.apical.oddm.web.controller.sale;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.application.sale.SaleInfoServiceI;
import com.apical.oddm.application.sys.SysConfigServiceI;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.core.model.sale.SaleInfo;
import com.apical.oddm.core.model.sys.SysConfig;
import com.apical.oddm.core.vo.sale.SaleInfoAllYearVo;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.sale.saleInfo.SaleInfoFacadeI;
import com.apical.oddm.facade.sale.saleInfo.cmd.SaleInfoCmd;
import com.apical.oddm.facade.sale.saleInfo.dto.SaleInfoDto;
import com.apical.oddm.facade.sale.saleInfo.dto.SaleInfoYearDto;
import com.apical.oddm.facade.sale.util.SaleInfoExportExcel;
import com.apical.oddm.facade.util.TimeUtil;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.framework.constant.GlobalConstant;
import com.apical.oddm.web.pageModel.base.Json;
import com.apical.oddm.web.pageModel.base.SessionInfo;

@Controller
@RequestMapping("/saleInfo")
public class SaleInfoController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(SaleInfoController.class);

	@Autowired
	private SaleInfoFacadeI saleInfoFacade;
	
	@Autowired
	private SaleInfoServiceI saleInfoService;
	
	@Autowired
	private SysConfigServiceI sysConfigService;
	
	@RequestMapping("/manager")
	public String manager() {
		return "/sale/saleInfo/saleInfoList";
	}
	
	@RequestMapping("/managerPerson")
	public String managerPerson() {
		return "/sale/saleInfo/saleInfoListPerson";
	}
	
	@RequestMapping("/managerAudit")
	public String managerAudit() {
		return "/sale/saleInfo/saleInfoListAudit";
	}
	
	@RequestMapping("/managerStatistics")
	public String summary(HttpServletRequest request) {
		SysConfig sysConfig = sysConfigService.get("currency_alphabet");
		request.setAttribute("sysConfig", sysConfig);
		return "/sale/saleInfo/saleStatisticsMonth";
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody  //jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public Pager<SaleInfoDto> dataGrid(HttpServletRequest request, SaleInfoCmd saleInfoCmd, PageCommand pageCommand) {  //此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		try {
			SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
			Integer userId = sessionInfo.getId();
			if (userId == null) return null;
			SystemContext.setCurrUserId(userId);
			SystemContext.setPageOffset(pageCommand.getPage());
			SystemContext.setPageSize(pageCommand.getRows());
			SystemContext.setOrder(pageCommand.getOrder());
			SystemContext.setSort(pageCommand.getSort());
			return saleInfoFacade.dataGrid(saleInfoCmd);
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}
	
	/**
	 * 个人销售使用
	 * @param saleInfoDto
	 * @param state
	 * @param pageCommand
	 * @return
	 */
	@RequestMapping("/dataGridPerson")
	@ResponseBody   
	public Pager<SaleInfoDto> dataGridPerson(HttpServletRequest request, SaleInfoCmd saleInfoCmd, PageCommand pageCommand) {  //此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		try {
			SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
			Integer userId = sessionInfo.getId();
			if (userId == null) return null;
			SystemContext.setCurrUserId(userId);
			SystemContext.setPageOffset(pageCommand.getPage());
			SystemContext.setPageSize(pageCommand.getRows());
			SystemContext.setOrder(pageCommand.getOrder());
			SystemContext.setSort(pageCommand.getSort());
			saleInfoCmd.setSellerId(userId);
			saleInfoCmd.setMerchandiserId(userId);
			return saleInfoFacade.dataGrid(saleInfoCmd);
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}
	
	// 判断号是否存在表中
	@RequestMapping("/checkOrderForOrder")
	@ResponseBody
	public Json checkOrderForOrder(SaleInfoCmd saleInfoCmd, HttpServletRequest request) {
		Json j = new Json();
		//SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		try {
			OrderInfo orderInfo = saleInfoFacade.getOrderInfo(saleInfoCmd);
			if (orderInfo != null) {
				j.setObj(orderInfo.getId());
				j.setSuccess(true);
				j.setMsg("号存在！");
				//一对1关系，得再查一次SaleInfo
				SaleInfo byOrderId = saleInfoService.getByOrderId(orderInfo.getId(), true);
				if (byOrderId != null) {
					j.setSuccess(false);
					j.setMsg("您输入的号已绑定对应的销售信息，请直接编辑！");
				}
			} else {
				j.setSuccess(false);
				j.setMsg("您输入的号不存在！");
			}
		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	// 判断号是否存在表中
	@RequestMapping("/checkOrderForOrderPerson")
	@ResponseBody
	public Json checkOrderForOrderPerson(SaleInfoCmd saleInfoCmd, HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
			Integer userId = sessionInfo.getId();
			saleInfoCmd.setSellerId(userId);
			saleInfoCmd.setMerchandiserId(userId);
			OrderInfo orderInfo = saleInfoFacade.getOrderInfo(saleInfoCmd);
			if (orderInfo != null) {
				j.setObj(orderInfo.getId());
				j.setSuccess(true);
				j.setMsg("号存在！");
				//一对1关系，得再查一次SaleInfo
				SaleInfo byOrderId = saleInfoService.getByOrderId(orderInfo.getId(), true);
				if (byOrderId != null) {
					j.setSuccess(false);
					j.setMsg("您输入的号已绑定对应的销售信息，请直接编辑！");
				}
			} else {
				j.setSuccess(false);
				j.setMsg("您输入的号不存在或不是自己的！");
			}
		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request, Integer orderId) {
		if (orderId == null) return null;
		SaleInfoDto r = saleInfoFacade.getBaseInfo(orderId);
		SysConfig sysConfig = sysConfigService.get("currency_alphabet");
		request.setAttribute("saleInfoDto", r);
		request.setAttribute("sysConfig", sysConfig);
		return "/sale/saleInfo/saleInfoAdd";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Json add(@RequestBody SaleInfoCmd saleInfoCmd, HttpServletRequest request) {
		//SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		try {
			//System.err.println("saleInfoCmd   "+saleInfoCmd);
			Serializable success = saleInfoFacade.add(saleInfoCmd);
			if (success != null) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！");
			}
		} catch (ConstraintViolationException e) {
			j.setMsg("编号:"+saleInfoCmd.getOrderNo()+"已经存在！");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("系统出错：" + e.getMessage());
			log.error("添加失败", e);
		}
		return j;
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id, Integer unread) {
		if (id == null) return null;
		try {
		/*	SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
			Integer userId = sessionInfo.getId();*/
			SaleInfoDto r = saleInfoFacade.getDetail(id);
			SysConfig sysConfig = sysConfigService.get("currency_alphabet");
			request.setAttribute("sysConfig", sysConfig);
			request.setAttribute("saleInfoDto", r);
		} catch (Exception e) {
			log.error("",e);
			return "/error/500.jsp";
		}
		return "/sale/saleInfo/saleInfoEdit";
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(@RequestBody SaleInfoCmd saleInfoCmd, HttpServletRequest request) {
		Json j = new Json();
		try {
			/*SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
			Integer userId = sessionInfo.getId();*/
			saleInfoFacade.edit(saleInfoCmd);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (DataIntegrityViolationException e) {
			j.setMsg("编号:"+saleInfoCmd.getOrderNo()+"已经存在！");
		} catch (ServiceException e) {
			j.setMsg("编辑失败：" + e.getMessage());
			log.error("编辑失败", e);
		}
		return j;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		try {
			saleInfoService.delete(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/getPage")
	public String getPage(HttpServletRequest request, Integer id, Integer unread) {
		if (id == null) return null;
		try {
		/*	SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
			Integer userId = sessionInfo.getId();*/
			SaleInfoDto r = saleInfoFacade.getDetail(id);
			request.setAttribute("saleInfoDto", r);
		} catch (Exception e) {
			log.error("",e);
			return "/error/500.jsp";
		}
		return "/sale/saleInfo/saleInfoDetail";
	}
	
	/**
	 * @param id 销售表id
	 * @param response
	 * @param request
	 */
	@RequestMapping("/excelExport")
	public void excelExport(SaleInfoCmd saleInfoCmd, HttpServletResponse response, HttpServletRequest request) {
		//SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		try {
			List<SaleInfoDto> list = saleInfoFacade.list(saleInfoCmd);
			String fileName = "海外市场部销售汇总表  ";
			HSSFWorkbook wb = SaleInfoExportExcel.export(list, saleInfoCmd.getDateOrderStart(), saleInfoCmd.getDateOrderEnd());
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
	
	@RequestMapping("/excelExportPerson")
	public void excelExportPerson(SaleInfoCmd saleInfoCmd, HttpServletResponse response, HttpServletRequest request) {
		try {
			SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
			int userId = sessionInfo.getId();
			saleInfoCmd.setSellerId(userId);
			saleInfoCmd.setMerchandiserId(userId);
			List<SaleInfoDto> list = saleInfoFacade.list(saleInfoCmd);
			String fileName = "海外市场部销售汇总表  ";
			HSSFWorkbook wb = SaleInfoExportExcel.export(list, saleInfoCmd.getDateOrderStart(), saleInfoCmd.getDateOrderEnd());
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
	
	@RequestMapping("/dataGridStatisticsMonth")
	@ResponseBody  //jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public Pager<SaleInfoDto> dataGridStatisticsMonth(HttpServletRequest request, SaleInfoCmd saleInfoCmd, PageCommand pageCommand) {  //此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		try {
			SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
			Integer userId = sessionInfo.getId();
			if (userId == null) return null;
			SystemContext.setCurrUserId(userId);
			SystemContext.setPageOffset(pageCommand.getPage());
			SystemContext.setPageSize(pageCommand.getRows());
			SystemContext.setOrder(pageCommand.getOrder());
			SystemContext.setSort(pageCommand.getSort());
			return saleInfoFacade.dataGridStatisticsMonth(saleInfoCmd);
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}
	
	@RequestMapping("/getYearStatPage")
	public String getYearStatPage(HttpServletRequest request) {
		SysConfig sysConfig = sysConfigService.get("currency_alphabet");
		request.setAttribute("sysConfig", sysConfig);
		return "/sale/saleInfo/saleStatisticsYear";
	}
	
	@RequestMapping("/getYearStatChartPage")
	public String getYearStatChartPage(HttpServletRequest request) {
		SysConfig sysConfig = sysConfigService.get("currency_alphabet");
		request.setAttribute("sysConfig", sysConfig);
		return "/sale/saleInfo/saleStatisticsYearChart";
	}
	@RequestMapping("/dataGridStatisticsYear")
	@ResponseBody
	public List<SaleInfoYearDto> dataGridStatisticsSellerYear(Integer type, String year, String currency) { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		if (type == null || StringUtils.isBlank(year)) return null;
		List<SaleInfoYearDto> list = saleInfoFacade.getSaleInfoYearSellerVoList(type, year, currency);
		return list;
	}
	
	@RequestMapping("/dataGridStatisticsAllYear")
	@ResponseBody
	public List<SaleInfoAllYearVo> getSaleInfoAllYearVoList(String yearStart,
			String yearEnd, String currency) {
		return saleInfoFacade.getSaleInfoAllYearVoList(yearStart, yearEnd, currency);
	}
}
