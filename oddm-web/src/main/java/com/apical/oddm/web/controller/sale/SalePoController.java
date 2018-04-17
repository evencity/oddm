package com.apical.oddm.web.controller.sale;

import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.application.sale.SalePoServiceI;
import com.apical.oddm.application.sys.SysConfigServiceI;
import com.apical.oddm.application.sys.UserServiceI;
import com.apical.oddm.core.constant.SalePoConst;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.core.model.sale.SalePo;
import com.apical.oddm.core.model.sys.SysConfig;
import com.apical.oddm.core.model.sys.User;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.sale.po.SalePoAltFacade;
import com.apical.oddm.facade.sale.po.SalePoFacadeI;
import com.apical.oddm.facade.sale.po.cmd.SalePoCmd;
import com.apical.oddm.facade.sale.po.dto.SalePoAltDto;
import com.apical.oddm.facade.sale.po.dto.SalePoDto;
import com.apical.oddm.facade.sale.util.SalePoExportExcel;
import com.apical.oddm.facade.util.TimeUtil;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.framework.constant.GlobalConstant;
import com.apical.oddm.web.pageModel.base.Json;
import com.apical.oddm.web.pageModel.base.SessionInfo;

@Controller
@RequestMapping("/salePo")
public class SalePoController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(SalePoController.class);

	@Autowired
	private SalePoFacadeI salePoFacade;

	@Autowired
	private SalePoAltFacade salePoAltFacade;
	
	@Autowired
	private SalePoServiceI salePoService;
	
	@Autowired
	private SysConfigServiceI sysConfigService;
	
	@RequestMapping("/manager")
	public String manager() {
		return "/sale/po/salePoList";
	}
	
	@RequestMapping("/managerPerson")
	public String managerPerson() {
		return "/sale/po/salePoListPerson";
	}
	
	@RequestMapping("/managerAudit")
	public String managerAudit() {
		return "/sale/po/salePoListAudit";
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody  //jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public Pager<SalePoDto> dataGrid(HttpServletRequest request, SalePoDto salePoDto, Integer state, PageCommand pageCommand) {  //此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		try {
			SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
			Integer userId = sessionInfo.getId();
			if (userId == null) return null;
			SystemContext.setCurrUserId(userId);
			SystemContext.setPageOffset(pageCommand.getPage());
			SystemContext.setPageSize(pageCommand.getRows());
			SystemContext.setOrder(pageCommand.getOrder());
			SystemContext.setSort("t."+pageCommand.getSort());
			Set<Integer> states = null;
			if (state != null) {
				states = new HashSet<Integer>();
				states.add(state);
			}
			return salePoFacade.dataGrid(salePoDto, states);
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}
	
	/**
	 * 个人内部订单使用
	 * @param salePoDto
	 * @param state
	 * @param pageCommand
	 * @return
	 */
	@RequestMapping("/dataGridPerson")
	@ResponseBody   
	public Pager<SalePoDto> dataGridPerson(HttpServletRequest request, SalePoDto salePoDto, Integer state, PageCommand pageCommand) {  //此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		try {
			SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
			Integer userId = sessionInfo.getId();
			if (userId == null) return null;
			SystemContext.setCurrUserId(userId);
			SystemContext.setPageOffset(pageCommand.getPage());
			SystemContext.setPageSize(pageCommand.getRows());
			SystemContext.setOrder(pageCommand.getOrder());
			SystemContext.setSort("t."+pageCommand.getSort());
			Set<Integer> states = null;
			if (state != null) {
				states = new HashSet<Integer>();
				states.add(state);
			}
			salePoDto.setSellerId(userId);
			salePoDto.setMerchandiserId(userId);
			return salePoFacade.dataGrid(salePoDto, states);
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}
	
	@RequestMapping("/dataGridAudit ")
	@ResponseBody  //jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public Pager<SalePoDto> dataGridAudit(HttpServletRequest request, SalePoDto salePoDto, PageCommand pageCommand) {  //此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		try {
			SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
			Integer userId = sessionInfo.getId();
			if (userId == null) return null;
			SystemContext.setPageOffset(pageCommand.getPage());
			SystemContext.setPageSize(pageCommand.getRows());
			SystemContext.setOrder(pageCommand.getOrder());
			SystemContext.setSort("t."+pageCommand.getSort());
			Set<Integer> states = new HashSet<Integer>();
			states.add(SalePoConst.submitaudit);
			
			if (sessionInfo.getResourceList().contains("/salePo/dataGridAllAudit")) {//所有人列表
				return salePoFacade.dataGrid(salePoDto, states);
			} else if(sessionInfo.getResourceList().contains("/salePo/dataGridPersonAudit")) {
				salePoDto.setApproverId(userId);
				return salePoFacade.dataGrid(salePoDto, states);
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}
	
	@RequestMapping("/poAltPage")
	public String poAltPage(Integer poId, HttpServletRequest request) {
		request.setAttribute("poId", poId);
		return "/sale/po/salePoAlterInfo";
	}
	
	@RequestMapping("/dataGridAltInfo")
	@ResponseBody
	public Pager<SalePoAltDto> dataGrid(Integer poId, PageCommand pageCommand) {
		//System.err.println(poId);
		if(poId != null){
			SystemContext.setPageOffset(pageCommand.getPage());
			SystemContext.setPageSize(pageCommand.getRows());
			SystemContext.setOrder(pageCommand.getOrder());
			SystemContext.setSort("t."+pageCommand.getSort());
			return salePoAltFacade.dataGrid(poId);
		}
		return null;
	}
	
	// 判断订单号是否存在订单表中
	@RequestMapping("/checkOrderForOrder")
	@ResponseBody
	public Json checkOrderForOrder(SalePoCmd salePoCmd, HttpServletRequest request) {
		Json j = new Json();
		//SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		try {
			OrderInfo orderInfo = salePoFacade.getOrderInfo(salePoCmd);
			if (orderInfo != null) {
				j.setObj(orderInfo.getId());
				j.setSuccess(true);
				j.setMsg("订单号存在！");
				//一对1关系，得再查一次SaleInfo
				SalePo byOrderId = salePoService.getByOrderId(orderInfo.getId(), true);
				if (byOrderId != null) {
					j.setSuccess(false);
					j.setMsg("您输入的订单号已绑定对应的PO信息，请直接编辑！");
				}
			} else {
				j.setSuccess(false);
				j.setMsg("您输入的订单号不存在！");
			}
		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	// 判断订单号是否存在订单表中
	@RequestMapping("/checkOrderForOrderPerson")
	@ResponseBody
	public Json checkOrderForOrderPerson(SalePoCmd salePoCmd, HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
			Integer userId = sessionInfo.getId();
			salePoCmd.setSellerId(userId);
			salePoCmd.setMerchandiserId(userId);
			OrderInfo orderInfo = salePoFacade.getOrderInfo(salePoCmd);
			if (orderInfo != null) {
				j.setObj(orderInfo.getId());
				j.setSuccess(true);
				j.setMsg("订单号存在！");
				//一对1关系，得再查一次SaleInfo
				SalePo byOrderId = salePoService.getByOrderId(orderInfo.getId(), true);
				if (byOrderId != null) {
					j.setSuccess(false);
					j.setMsg("您输入的订单号已绑定对应的PO信息，请直接编辑！");
				}
			} else {
				j.setSuccess(false);
				j.setMsg("您输入的订单号不存在或不是自己的订单！");
			}
		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request, Integer orderId) {
		if (orderId == null) return null;
		SalePoDto r = salePoFacade.getBaseInfo(orderId);
		SysConfig sysConfig = sysConfigService.get("currency_alphabet");
		request.setAttribute("salePoDto", r);
		request.setAttribute("sysConfig", sysConfig);
		return "/sale/po/salePoAdd";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Json add(@RequestBody SalePoCmd salePoCmd, HttpServletRequest request) {
		//SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		try {
			Serializable success = salePoFacade.add(salePoCmd);
			if (success != null) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！");
			}
		}/* catch (ConstraintViolationException e) {
			j.setMsg("PO编号:"+salePoCmd.getPoNo()+"已经存在！");
		} */catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("系统出错：" + e.getMessage());
			log.error("订单添加失败", e);
		}
		return j;
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id, Integer unread) {
		if (id == null) return null;
		try {
			SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
			Integer userId = sessionInfo.getId();
			if (unread != 1) userId = null;//有未读标志则，传用户id进去删除
			SalePoDto r = salePoFacade.getDetail(id, userId);
			SysConfig sysConfig = sysConfigService.get("currency_alphabet");
			request.setAttribute("sysConfig", sysConfig);
			request.setAttribute("salePoDto", r);
		} catch (Exception e) {
			log.error("",e);
			return "/error/500.jsp";
		}
		return "/sale/po/salePoEdit";
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(@RequestBody SalePoCmd salePoCmd, HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		try {
			Integer userId = sessionInfo.getId();
			salePoFacade.edit(salePoCmd, userId);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} /*catch (DataIntegrityViolationException e) {
			j.setMsg("PO编号:"+salePoCmd.getPoNo()+"已经存在！");
		} */catch (ServiceException e) {
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
			salePoService.delete(id);
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
			SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
			Integer userId = sessionInfo.getId();
			if (unread == null || unread != 1) userId = null;//有未读标志则，传用户id进去删除
			SalePoDto r = salePoFacade.getDetail(id, userId);
			request.setAttribute("salePoDto", r);
		} catch (Exception e) {
			log.error("",e);
			return "/error/500.jsp";
		}
		return "/sale/po/salePoDetail";
	}
	
	@RequestMapping("/printPage")
	public String printPage(HttpServletRequest request, Integer id) {
		if (id == null) return null;
		try {
			SalePoDto r = salePoFacade.getDetail(id, null);
			request.setAttribute("salePoDto", r);
		} catch (Exception e) {
			log.error("",e);
			return "/error/500.jsp";
		}
		return "/sale/po/salePoPrint";
	}
	
	@Autowired
	private UserServiceI userService;
	
	@RequestMapping("/getByUsName")
	@ResponseBody
	public Json getByUsName(String username, Integer poId) {
		Json j = new Json();
		try {
			User user = userService.getByUsName(username, true);
			if (user != null) {
				salePoFacade.updateApprover(user, poId);
				j.setSuccess(true);
				j.setMsg("提交成功!");
			} else {
				j.setSuccess(false);
				j.setMsg("用户名不存在!");
			}

		} catch (ServiceException e) {
			j.setMsg("提交异常，请联系管理员！"+e.getMessage());
		}
		return j;
	}
	
	@RequestMapping("/audit")
	@ResponseBody
	public Json audit(HttpServletRequest request, Integer state, Integer poId) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Integer userId = sessionInfo.getId()==null?0:sessionInfo.getId();
		Json j = new Json();
		if(state == null || poId == null) {
			j.setMsg("选择有误");
			return j;
		}
		try {
			salePoFacade.audit(userId, state, poId);
			j.setMsg("审核成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * @param id 内部订单表id
	 * @param response
	 * @param request
	 */
	@RequestMapping("/excelExport")
	public void excelExport(Integer id, HttpServletResponse response, HttpServletRequest request) {
		//SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		try {
			SalePoDto r = salePoFacade.getDetail(id, null);
			String fileName = "PO "+r.getOrderNo()+" ";
			String imagePath = File.separator + "style" + File.separator + "images-sale" + File.separator + "salepo-apical-logo.jpg";
			String realPath = request.getSession().getServletContext().getRealPath(imagePath);
			HSSFWorkbook wb = SalePoExportExcel.export(r, realPath);
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
	public void excelExportPerson(Integer id, HttpServletResponse response, HttpServletRequest request) {
		try {
			SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
			SalePoDto r = salePoFacade.getDetail(id, null);
			int userId = sessionInfo.getId();
			if (r.getSellerId() == userId || r.getMerchandiserId() == userId) {
				String fileName = "PO "+r.getOrderNo()+" ";
				String imagePath = File.separator + "style" + File.separator + "images-sale" + File.separator + "salepo-apical-logo.jpg";
				String realPath = request.getSession().getServletContext().getRealPath(imagePath);
				HSSFWorkbook wb = SalePoExportExcel.export(r, realPath);
				fileName = new String(fileName.getBytes("gb2312"), "ISO-8859-1");
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-disposition", "attachment;filename=" + fileName + TimeUtil.dateToStringForExcel(new Date()) + ".xls");
				OutputStream ouputStream = response.getOutputStream();
				wb.write(ouputStream);
				ouputStream.flush();
				ouputStream.close();
			} else {
				log.error("非法越界操作："+sessionInfo.getId());
			}
		} catch (Exception e) {
			log.error("导表出错：", e);
		}
	}
}
