package com.apical.oddm.web.controller.manufacture;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.manufacture.ManufactureFacade;
import com.apical.oddm.facade.manufacture.command.ManufactureCommand;
import com.apical.oddm.facade.manufacture.dto.ManufactureDTO;
import com.apical.oddm.facade.order.OrderInfoFacade;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;
import com.apical.oddm.web.framework.constant.GlobalConstant;
import com.apical.oddm.web.pageModel.base.Json;
import com.apical.oddm.web.pageModel.base.SessionInfo;


@Controller
@RequestMapping("/manufacture")
public class ManufactureController {

	private static final Logger log = LoggerFactory.getLogger(ManufactureController.class);
	@Autowired
	private ManufactureFacade manufactureFacade;
	
	@Autowired
	private OrderInfoFacade orderInfoFacade;
	
	@RequestMapping("/manager")
	public String manager() {
		return "/manufacture/manufacture";
	}
	
	/**
	 * 分页查询
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<ManufactureDTO> dataGrid(ManufactureCommand manufactureCommand, PageCommand pageCommand, HttpServletRequest request) { 
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		SystemContext.setCurrUserId(sessionInfo.getId());
		BasePage<ManufactureDTO> dataGrid = manufactureFacade.dataGrid(manufactureCommand, pageCommand);
		return dataGrid;
	}
	
	@RequestMapping("/checkOrderNoPage")
	public String checkOrderNoPage(HttpServletRequest request) {
		return "/manufacture/manufacture_checkOrderNoPage";
	}

	// 判断订单号是否存在订单表中
	@RequestMapping("/checkOrderForOrder")
	@ResponseBody
	public Json checkOrderForOrder(OrderInfoCommand orderInfoCommand, HttpServletRequest request) {
		Json j = new Json();
		try {
			Boolean byOrderNo = manufactureFacade.getByOrder(orderInfoCommand);
			if (byOrderNo) {
				j.setSuccess(true);
				j.setMsg("订单号存在！");
			} else {
				j.setSuccess(false);
				j.setMsg("您输入的订单号不存在！");
			}
		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	// 判断订单号是否存在任务书中
	@RequestMapping("/getManufactureByOrderNo")
	@ResponseBody
	public Json getManufactureByOrderNo(String orderNo, HttpServletRequest request) {
		Json j = new Json();
		try {
			ManufactureDTO manufactureByOrderNo = manufactureFacade.getManufactureByOrderNo(orderNo);
			if (manufactureByOrderNo != null) {
				j.setSuccess(true);
				j.setMsg("您输入的订单号已绑定对应的包装工艺指导书，请直接编辑！");
			} else {
				j.setSuccess(false);
				j.setMsg("您输入的订单号不存在！");
			}
		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/*@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request,OrderInfoCommand orderInfoCommand) {
		if(orderInfoCommand.getOrderNo() != null ){
			OrderInfoDTO byOrderDTO = manufactureFacade.getByOrderDTO(orderInfoCommand);
			request.setAttribute("orderInfo", byOrderDTO);
		}
		return "/manufacture/manufactureAdd";
	}*/
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request,OrderInfoCommand orderInfoCommand) {
		if(orderInfoCommand.getOrderNo() != null ){
			OrderInfoDTO byOrderDTO = manufactureFacade.getByOrderDTO(orderInfoCommand);
			if(byOrderDTO != null){
				ManufactureCommand manufactureCommand = new ManufactureCommand();
				if(byOrderDTO.getOrderNo() != null){
					manufactureCommand.setOrderNo(byOrderDTO.getOrderNo());
				}
				if(byOrderDTO.getClientName() != null){
					manufactureCommand.setClientName(byOrderDTO.getClientName());
				}
				manufactureCommand.setOrderId(byOrderDTO.getId());
				Integer id = manufactureFacade.addManufacture(manufactureCommand);
				request.setAttribute("manufactureId", id);
			}
		}
		return "/manufacture/manufactureEdit";
	}

	@RequestMapping("/add")
	@ResponseBody
	public Json add(@RequestBody ManufactureCommand manufactureCommand, HttpServletRequest request) {
		//SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		try {
			Boolean success = manufactureFacade.add(manufactureCommand);
			if (success) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！");
			}
		}  catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("系统出错：" + e.getMessage());
			log.error("任务书添加失败", e);
		}
		return j;
	}
	
	@RequestMapping("/getPage")
	public String getPage(HttpServletRequest request, Integer id) {
		request.setAttribute("manufactureId", id);
		return "/manufacture/manufactureDetails";
	}
	
	@RequestMapping("/printPage")
	public String printPage(HttpServletRequest request, Integer id) {
		request.setAttribute("manufactureId", id);
		return "/manufacture/manufacturePrint";
	}

	
	@RequestMapping("/get")
	@ResponseBody
	public ManufactureDTO get(Integer id, HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		ManufactureDTO manufactureDTO = manufactureFacade.getManufacture(id,sessionInfo.getId());
		return manufactureDTO;
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		request.setAttribute("manufactureId", id);
		return "/manufacture/manufactureEdit";
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(@RequestBody ManufactureCommand manufactureCommand, HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		try {
			manufactureFacade.edit(manufactureCommand,sessionInfo.getId(),sessionInfo.getName());
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (ServiceException e) {
			j.setMsg("编辑失败：" + e.getMessage());
			log.error("编辑失败", e);
		}
		return j;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete( Integer id, HttpServletRequest request) {
		
		Json j = new Json();
		try {
			manufactureFacade.delete(id);
			j.setSuccess(true);
			j.setMsg("删除成功！");
		}  catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("系统出错：" + e.getMessage());
			log.error("任务书删除失败", e);
		}
		return j;
	}
	
	@RequestMapping("/review")
	@ResponseBody
	public Json review(Integer id, HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		try {
			manufactureFacade.review(id,sessionInfo.getId());
			j.setSuccess(true);
			j.setMsg("审核成功！");
		} catch (ServiceException e) {
			j.setMsg("编辑失败：" + e.getMessage());
			log.error("编辑失败", e);
		}
		return j;
	}
}
