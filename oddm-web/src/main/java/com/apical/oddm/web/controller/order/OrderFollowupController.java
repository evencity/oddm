package com.apical.oddm.web.controller.order;

import java.io.File;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.core.constant.OrderFollowupConst;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.order.OrderFollowupFacade;
import com.apical.oddm.facade.order.command.OrderFollowupCommand;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderFollowupDTO;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;
import com.apical.oddm.facade.order.util.OrderFollowupExportExcel;
import com.apical.oddm.facade.util.TimeUtil;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.framework.constant.GlobalConstant;
import com.apical.oddm.web.pageModel.base.Json;
import com.apical.oddm.web.pageModel.base.SessionInfo;

/**
 * @description 类的描述：
 * @author 作者 : zhuzh
 * @date 创建时间：2016年11月1日 下午1:44:14
 * @version 1.0
 */
@Controller
@RequestMapping("/orderFollowup")
public class OrderFollowupController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(OrderFollowupController.class);
	@Autowired
	private OrderFollowupFacade orderFollowupFacade;

	@RequestMapping("/managerAll")
	public String managerAll() {
		return "/order/orderFollowupAll";
	}

	/**
	 * 分页查询定单基础信息
	 */
	@RequestMapping("/dataGridAll")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<OrderFollowupDTO> dataGridAll(OrderInfoCommand orderInfoCommand, PageCommand pageCommand) { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		if ("".equals(orderInfoCommand.getOrderNo())) {
			orderInfoCommand.setOrderNo(null);
		}
		if ("".equals(orderInfoCommand.getClientName())) {
			orderInfoCommand.setClientName(null);
		}
		if ("".equals(orderInfoCommand.getClientNameCode())) {
			orderInfoCommand.setClientNameCode(null);
		}
		if ("".equals(orderInfoCommand.getProductClient())) {
			orderInfoCommand.setProductClient(null);
		}
		if ("".equals(orderInfoCommand.getMerchandiser())) {
			orderInfoCommand.setMerchandiser(null);
		}
		BasePage<OrderFollowupDTO> pageList = orderFollowupFacade.dataGrid(orderInfoCommand, pageCommand);
		return pageList;
	}

	@RequestMapping("/manager")
	public String manager() {
		return "/order/orderFollowup";
	}

	/**
	 * 分页查询定单基础信息
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<OrderFollowupDTO> dataGrid(OrderInfoCommand orderInfoCommand, PageCommand pageCommand, HttpServletRequest request) { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		if ("".equals(orderInfoCommand.getOrderNo())) {
			orderInfoCommand.setOrderNo(null);
		}
		if ("".equals(orderInfoCommand.getClientName())) {
			orderInfoCommand.setClientName(null);
		}
		if ("".equals(orderInfoCommand.getClientNameCode())) {
			orderInfoCommand.setClientNameCode(null);
		}
		if ("".equals(orderInfoCommand.getProductClient())) {
			orderInfoCommand.setProductClient(null);
		}
		if ("".equals(orderInfoCommand.getMerchandiser())) {
			orderInfoCommand.setMerchandiser(null);
		}
		orderInfoCommand.setSellerId(sessionInfo.getId());
		orderInfoCommand.setMerchandiserId(sessionInfo.getId());
		BasePage<OrderFollowupDTO> pageList = orderFollowupFacade.dataGrid(orderInfoCommand, pageCommand);
		return pageList;
	}

	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request, String orderNo) {
		request.setAttribute("orderNo", orderNo);
		return "/order/orderFollowupAdd";
	}

	@RequestMapping("/add")
	@ResponseBody
	public Json add(@RequestBody OrderFollowupCommand orderFollowupCommand) {
		Json j = new Json();
		try {
			Boolean success = orderFollowupFacade.add(orderFollowupCommand);
			if (success) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
				return j;
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！");
				return j;
			}
		} catch (ConstraintViolationException e) {
			j.setMsg("跟单号已存在！");
			log.error("跟单已经存在：", e);
			return j;
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/getPage")
	public String getPage(HttpServletRequest request, Integer id) {
		request.setAttribute("orderFollowupId", id);
		return "/order/orderFollowupDetails";
	}

	@RequestMapping("/get")
	@ResponseBody
	public OrderFollowupDTO get(Integer id) {
		OrderFollowupDTO orderFollowupDTO = orderFollowupFacade.get(id);
		return orderFollowupDTO;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		try {
			orderFollowupFacade.delete(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			log.error("删除orderFollowup失败：", e);
		}
		return j;
	}

	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		request.setAttribute("orderFollowupId", id);
		return "/order/orderFollowupEdit";
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(@RequestBody OrderFollowupCommand orderFollowupCommand, HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		try {
			orderFollowupFacade.edit(orderFollowupCommand, sessionInfo.getId());
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

/*	@RequestMapping("/editForSubmit")
	@ResponseBody
	public Json editForSubmit(@RequestBody OrderFollowupCommand orderFollowupCommand, HttpServletRequest request) {
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		try {
			//orderFollowupCommand.setState(OrderFollowupConst.save);
			orderFollowupFacade.edit(orderFollowupCommand, sessionInfo.getId());
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}*/

	@RequestMapping("/editForFinish")
	@ResponseBody
	public Json editForFinish(@RequestBody OrderFollowupCommand orderFollowupCommand, HttpServletRequest request) {
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		try {
			orderFollowupCommand.setState(OrderFollowupConst.archive);
			orderFollowupFacade.edit(orderFollowupCommand, sessionInfo.getId());
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/queryOrderFollowForOrderIdPage")
	public String queryOrderFollowForOrderId(HttpServletRequest request, Integer id) {
		request.setAttribute("orderFollowupId", id);
		return "/order/orderFollowupForOrderId";
	}

	/*
	 * @RequestMapping("/queryOrderFollowForOrderId")
	 * 
	 * @ResponseBody public BasePage<OrderFollowupDTO> queryOrderFollowForOrderId(Integer orderId,PageCommand pageCommand) { OrderFollowupCommand orderFollowupCommand = new
	 * OrderFollowupCommand(); orderFollowupCommand.setOrderId(orderId); BasePage<OrderFollowupDTO> pageList = orderFollowupFacade.dataGrid(orderFollowupCommand,pageCommand);
	 * return pageList; }
	 */

	@RequestMapping("/checkOrderNoPage")
	public String checkOrderNoPage(HttpServletRequest request) {
		return "/order/orderFollowup_checkOrderNoPage";
	}

	// 判断订单号是否存在订单表中
	@RequestMapping("/checkOrderForOrder")
	@ResponseBody
	public Json checkOrderForOrder(OrderInfoCommand orderInfoCommand, HttpServletRequest request) {
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		try {
			if (sessionInfo.getId() != null) {
				orderInfoCommand.setMerchandiserId(sessionInfo.getId());
				orderInfoCommand.setSellerId(sessionInfo.getId());
				Boolean byOrderNo = orderFollowupFacade.getByOrder(orderInfoCommand);
				if (byOrderNo) {
					j.setSuccess(true);
					j.setMsg("订单号存在！");
				} else {
					j.setSuccess(false);
					j.setMsg("您输入的订单号不存在！");
				}
			}
		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/checkOrderForOrderAll")
	@ResponseBody
	public Json checkOrderForOrderAll(OrderInfoCommand orderInfoCommand) {
		Json j = new Json();
		try {
			Boolean byOrderNo = orderFollowupFacade.getByOrder(orderInfoCommand);
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

	// 判断订单号是否存在跟单表中
	@RequestMapping("/checkOrderForOrderfollow")
	@ResponseBody
	public Json checkOrderForOrderfollow(String orderNo) {
		Json j = new Json();
		try {
			Boolean byOrderInfo = orderFollowupFacade.getByOrderInfo(orderNo.trim());

			if (byOrderInfo) {
				j.setSuccess(true);
				j.setMsg("您输入的订单号已绑定对应的跟单，请直接编辑！");
			} else {
				j.setSuccess(false);
				j.setMsg("订单号不存在！");
			}

		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	// 判断订单中是否存在跟单
	@RequestMapping("/checkOrderfollow")
	@ResponseBody
	public Json checkOrderfollow(OrderInfoCommand orderInfoCommand) {
		Json j = new Json();
		try {
			OrderFollowupDTO byOrderInfo = orderFollowupFacade.getByOrderInfo(orderInfoCommand);

			if (byOrderInfo.getId() != null) {
				j.setSuccess(true);
			} else {
				j.setSuccess(false);
				j.setMsg("查询不到相关跟单内容！！！");
			}

		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/getOrderInfoByOrderNo")
	@ResponseBody
	public OrderInfoDTO getOrderInfoByOrderNo(String orderNo) {
		return orderFollowupFacade.getOrderInfoByOrderNo(orderNo.trim());
	}

	@RequestMapping("/excelPage")
	public String excelPage() {
		return "/order/orderFollowup_excelPage";
	}

	@RequestMapping("/excelExport")
	public void excelExport(Integer state, HttpServletResponse response, HttpServletRequest request, OrderInfoCommand orderInfoCommand) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		List<OrderFollowupDTO> listAll = null;
		Set<Integer> set = new HashSet<Integer>();
		String fileName = "";
		// OrderInfoCommand orderInfoCommand = new OrderInfoCommand();
		orderInfoCommand.setMerchandiserId(sessionInfo.getId());
		orderInfoCommand.setSellerId(sessionInfo.getId());
		if (state == 1) {
			listAll = orderFollowupFacade.listAll(orderInfoCommand, set);
			fileName = "全部跟单";
		}
		if (state == 2) {
			set.add(OrderFollowupConst.archive);
			listAll = orderFollowupFacade.listAll(orderInfoCommand, set);
			fileName = "完结跟单";
		}
		if (state == 3) {
			set.add(OrderFollowupConst.save);
			set.add(OrderFollowupConst.temporarysave);
			listAll = orderFollowupFacade.listAll(orderInfoCommand, set);
			fileName = "未完结跟单";
		}
		try {
			String imagePath = File.separator + "style" + File.separator + "images" + File.separator + "orderFollowLogo.png";

			String realPath = request.getSession().getServletContext().getRealPath(imagePath);
			HSSFWorkbook wb = OrderFollowupExportExcel.export(listAll, realPath);
			fileName = new String(fileName.getBytes("gb2312"), "ISO-8859-1");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + TimeUtil.dateToStringForExcel(new Date()) + ".xls");
			OutputStream ouputStream = response.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("导表出错：", e);
		}
	}

	@RequestMapping("/excelExportAll")
	public void excelExportAll(Integer state, HttpServletResponse response, HttpServletRequest request, OrderInfoCommand orderInfoCommand) {
		List<OrderFollowupDTO> listAll = null;
		String fileName = "";
		Set<Integer> set = new HashSet<Integer>();
		// OrderInfoCommand orderInfoCommand = new OrderInfoCommand();
		if (state == 1) {
			listAll = orderFollowupFacade.listAll(orderInfoCommand, set);
			fileName = "全部跟单";
		}
		if (state == 2) {
			set.add(OrderFollowupConst.archive);
			listAll = orderFollowupFacade.listAll(orderInfoCommand, set);
			fileName = "完结跟单";
		}
		if (state == 3) {
			set.add(OrderFollowupConst.save);
			set.add(OrderFollowupConst.temporarysave);
			listAll = orderFollowupFacade.listAll(orderInfoCommand, set);
			fileName = "未完结跟单";
		}
		try {
			String imagePath = File.separator + "style" + File.separator + "images" + File.separator + "orderFollowLogo.png";
			String realPath = request.getSession().getServletContext().getRealPath(imagePath);
			HSSFWorkbook wb = OrderFollowupExportExcel.export(listAll, realPath);
			fileName = new String(fileName.getBytes("gb2312"), "ISO-8859-1");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + TimeUtil.dateToStringForExcel(new Date()) + ".xls");
			OutputStream ouputStream = response.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("导表出错：", e);
		}
	}
}
