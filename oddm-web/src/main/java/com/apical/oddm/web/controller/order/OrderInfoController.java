package com.apical.oddm.web.controller.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.apical.oddm.core.constant.OrderInfoConst;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.core.vo.order.OrderInfoAllYearVo;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.manufacture.ManufactureFacade;
import com.apical.oddm.facade.manufacture.command.ManufactureCommand;
import com.apical.oddm.facade.manufacture.dto.ManufactureDTO;
import com.apical.oddm.facade.materialFollow.MaterialFollowupFacade;
import com.apical.oddm.facade.materialFollow.dto.MaterialFollowupDTO;
import com.apical.oddm.facade.order.OrderFollowupFacade;
import com.apical.oddm.facade.order.OrderInfoFacade;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderFollowupDTO;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;
import com.apical.oddm.facade.order.dto.OrderInfoYearDto;
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
@RequestMapping("/order")
public class OrderInfoController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(OrderInfoController.class);
	@Autowired
	private OrderInfoFacade orderInfoFacade;
	@Autowired
	private OrderFollowupFacade orderFollowupFacade;
	@Autowired
	private MaterialFollowupFacade materialFollowupFacade;
	@Autowired
	private ManufactureFacade manufactureFacade;
	
	@RequestMapping("/managerAll")
	public String managerAll() {
		return "/order/orderAll";
	}

	@RequestMapping("/manager")
	public String manager() {
		return "/order/order";
	}

	@RequestMapping("/managerStatistics")
	public String summary() {
		return "/order/orderStatisticsMonth";
	}
	
	/**
	 * 分页查询定单基础信息
	 */
	@RequestMapping("/dataGridAll")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<OrderInfoDTO> dataGridAll(OrderInfoCommand orderInfoCommand, PageCommand pageCommand, HttpServletRequest request) { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		SystemContext.setCurrUserId(sessionInfo.getId());
		HashSet<Integer> states = null;

		if (sessionInfo.getResourceList().contains("/order/viewAllStates")) {//所有人列表
			//do nothing
		} else {
			states = new HashSet<Integer>();//如果没权限 则只能看到审核和齐料两个状态
			states.add(OrderInfoConst.approved);
			states.add(OrderInfoConst.materialfinished);
		}
		BasePage<OrderInfoDTO> pageList = orderInfoFacade.dataGrid(orderInfoCommand, pageCommand, states, sessionInfo.getHasRoles());
		return pageList;
	}

	/**
	 * 分页查询定单基础信息
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<OrderInfoDTO> dataGrid(OrderInfoCommand orderInfoCommand, PageCommand pageCommand, HttpServletRequest request) { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		// 这里暂时这样写
		orderInfoCommand.setSellerId(sessionInfo.getId());
		orderInfoCommand.setMerchandiserId(sessionInfo.getId());
		SystemContext.setCurrUserId(sessionInfo.getId());
		BasePage<OrderInfoDTO> pageList = orderInfoFacade.dataGrid(orderInfoCommand, pageCommand, null, sessionInfo.getHasRoles());
		return pageList;
	}

	@RequestMapping("/printPage")
	public String printPage(HttpServletRequest request, Integer id) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		OrderInfoDTO orderInfoDTO = orderInfoFacade.getOrderInfoDTO(id, sessionInfo.getHasRoles(),sessionInfo.getId());
		if (orderInfoDTO.getPid() != null) {
			OrderInfoDTO orderInfoDTO2 = orderInfoFacade.getBaseOrderInfoDTO(orderInfoDTO.getPid(), sessionInfo.getHasRoles());
			request.setAttribute("orderInfoDTOParent", orderInfoDTO2);
		}
		request.setAttribute("orderInfoDTO", orderInfoDTO);
		return "/order/orderPrint";
	}

	@RequestMapping("/addPage")
	public String addPage() {
		return "/order/orderAdd";
	}

	@RequestMapping("/add")
	@ResponseBody
	public Json add(@RequestBody OrderInfoCommand orderInfoCommand, HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		orderInfoCommand.setMerchandiserId(sessionInfo.getId());
		orderInfoCommand.setMerchandiser(sessionInfo.getName());
		//System.out.println("添加orderInfoCommand：" + orderInfoCommand);
		Json j = new Json();
		try {
			Boolean success = orderInfoFacade.add(orderInfoCommand);
			if (success) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！");
			}
		} catch (ConstraintViolationException e) {
			log.error(e.getMessage());
			j.setMsg("订单号已存在！");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("系统出错：" + e.getMessage());
			log.error("订单添加失败", e);
		}
		return j;
	}

	@RequestMapping("/addNewPage")
	public String addNewPage(HttpServletRequest request, String pid) {
		request.setAttribute("pid", pid);
		return "/order/orderNewAdd";
	}

	@RequestMapping("/addNewOrder")
	@ResponseBody
	public Json addNewOrder(@RequestBody OrderInfoCommand orderInfoCommand, HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		orderInfoCommand.setMerchandiserId(sessionInfo.getId());
		orderInfoCommand.setMerchandiser(sessionInfo.getName());

		Json j = new Json();
		try {
			Boolean success = orderInfoFacade.addNewOrder(orderInfoCommand, sessionInfo.getId());
			if (success) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！");
			}
		} catch (ConstraintViolationException e) {
			log.error(e.getMessage());
			j.setMsg("订单号已存在！");
		} catch (Exception e) {
			// j.setMsg("系统出错："+e.getMessage());
			log.error("系统出错：", e);
		}
		return j;
	}

	@RequestMapping("/getByOrderNo")
	@ResponseBody
	public Json getByOrderNo(String orderNo) {
		Json j = new Json();
		Boolean byOrderNo = orderInfoFacade.getByOrderNo(orderNo);
		if (byOrderNo) {
			j.setSuccess(true);
			j.setMsg("定单号已存在，请重新输入！");
		} else {
			j.setSuccess(false);
			j.setMsg("定单号可以使用");
		}
		return j;
	}

	@RequestMapping("/getPage")
	public String getPage(HttpServletRequest request, Integer id,Integer sellerId) {
		request.setAttribute("orderId", id);
		request.setAttribute("sellerId", sellerId);
		return "/order/orderDetails";
	}

	@RequestMapping("/get")
	@ResponseBody
	public OrderInfoDTO get(Integer id, HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		SystemContext.setCurrUserId(sessionInfo.getId());
		OrderInfoDTO orderInfoDTO = orderInfoFacade.getOrderInfoDTO(id, sessionInfo.getHasRoles(),sessionInfo.getId());
		return orderInfoDTO;
	}

	@RequestMapping("/getForAddNew")
	@ResponseBody
	public OrderInfoDTO getForAddNew(Integer id, HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		OrderInfoDTO orderInfoDTO = orderInfoFacade.getOrderInfoDTO(id, sessionInfo.getHasRoles(),sessionInfo.getId());
		if (orderInfoDTO != null) {
			//当前时间
			Date currentDate = new Date();
			orderInfoDTO.setDateOrder(TimeUtil.dateToString(currentDate));
			//翻单交期延迟35天
			long delay = currentDate.getTime()+35*24*60*60*1000L;
			String delayDate = TimeUtil.dateToString(new Date(delay));
			orderInfoDTO.setDateDelivery(delayDate);
			orderInfoDTO.setDateExamine(delayDate);
		}
		return orderInfoDTO;
	}

	@RequestMapping("/getAllDocumentByOrderIdPage")
	public String getAllDocumentByOrderIdPage(HttpServletRequest request, Integer orderId) {
		request.setAttribute("orderId", orderId);
		return "/document/documentByOrderId";
	}

	@RequestMapping("/getAllDocumentByOrderId")
	@ResponseBody
	public List<OrderInfoDTO> getAllDocumentByOrderId(Integer orderId) {
		List<OrderInfoDTO> allDocumentByOrderId = orderInfoFacade.getAllDocumentByOrderId(orderId);
		return allDocumentByOrderId;
	}

	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer orderId) {
		request.setAttribute("orderId", orderId);
		return "/order/orderEdit";
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(@RequestBody OrderInfoCommand orderInfoCommand, HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		/*
		 * orderInfoCommand.setMerchandiserId(sessionInfo.getId()); orderInfoCommand.setMerchandiser(sessionInfo.getName());
		 */
		Json j = new Json();
		try {
			orderInfoFacade.edit(orderInfoCommand, sessionInfo.getId());

			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (ServiceException e) {
			j.setMsg("编辑失败：" + e.getMessage());
			log.error("编辑失败", e);
		}
		return j;
	}

	@RequestMapping("/getBaseOrderInfoDTO")
	@ResponseBody
	public OrderInfoDTO getBaseOrderInfoDTO(Integer orderId, HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		return orderInfoFacade.getBaseOrderInfoDTO(orderId, sessionInfo.getHasRoles());
	}

	@RequestMapping("/getByProductFactory")
	@ResponseBody
	public OrderInfoDTO getByProductFactory(String productTypeName) {
		OrderInfoDTO byProductFactory = orderInfoFacade.getByProductFactory(productTypeName);
		return byProductFactory;
	}

	@RequestMapping("/listTopClientName")
	@ResponseBody
	public List<String> listTopClientName(String clientName) {
		return orderInfoFacade.listTopClientName(clientName);
	}

	//给easyui 的combobox用的json格式
	@RequestMapping("/listTopClientNameJson")
	@ResponseBody
	public List<JSONObject> listTopClientNameJson(String clientName) {
		List<JSONObject> list = new ArrayList<JSONObject>();
		List<String> listTopClientName = orderInfoFacade.listTopClientName(clientName);
		if (listTopClientName != null) {
			JSONObject json = null;
			for (String s : listTopClientName) {
				json = new JSONObject();
				json.put("value", s);
				list.add(json);
			}
		}
		return list;
	}
	
	@RequestMapping("/listTopClientBrand")
	@ResponseBody
	public List<String> listTopClientBrand(String clientBrand) {
		return orderInfoFacade.listTopClientBrand(clientBrand);
	}

	@RequestMapping("/listTopDistrict")
	@ResponseBody
	public List<String> listTopDistrict(String district) {
		return orderInfoFacade.listTopDistrict(district);
	}

	//给easyui 的combobox用的json格式
	@RequestMapping("/listTopDistrictJson")
	@ResponseBody
	public List<JSONObject> listTopDistrictJson(String district) {
		List<JSONObject> list = new ArrayList<JSONObject>();
		List<String> listTopClientName = orderInfoFacade.listTopDistrict(district);
		if (listTopClientName != null) {
			JSONObject json = null;
			for (String s : listTopClientName) {
				json = new JSONObject();
				json.put("value", s);
				list.add(json);
			}
		}
		return list;
	}
	
	@RequestMapping("/listTopGui")
	@ResponseBody
	public List<String> listTopGui(String gui) {
		return orderInfoFacade.listTopGui(gui);
	}

	@RequestMapping("/listTopLangOs")
	@ResponseBody
	public List<String> listTopLangOs(String langOs) {
		return orderInfoFacade.listTopLangOs(langOs);
	}

	@RequestMapping("/listTopLangDefault")
	@ResponseBody
	public List<String> listTopLangDefault(String langDefault) {
		return orderInfoFacade.listTopLangDefault(langDefault);
	}

	@RequestMapping("/listTopTimezone")
	@ResponseBody
	public List<String> listTopTimezone(String timezone) {
		return orderInfoFacade.listTopTimezone(timezone);
	}

	@RequestMapping("/submitToSeller")
	public String submitToSeller() {
		return "/order/order_submitToSeller";
	}

	@RequestMapping("/addSeller")
	@ResponseBody
	public Json addSeller(Integer ordrId, Integer sellerId, String seller) {
		Json j = new Json();
		try {
			Boolean addSeller = orderInfoFacade.addSeller(ordrId, sellerId, seller);
			if (addSeller) {
				j.setSuccess(true);
				j.setMsg("提交审核完成！！");
			} else {
				j.setSuccess(false);
				j.setMsg("提交审核失败！！");
			}
		} catch (ServiceException e) {
			j.setMsg("系统出错：" + e.getMessage());
			log.error("失败", e);
		}
		return j;
	}

	@RequestMapping("/orderReviewPage")
	public String orderReviewPage() {
		return "/order/orderReviewPage";
	}

	@RequestMapping("/orderReview")
	@ResponseBody
	public Json orderReview(Integer orderId, Integer state, HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
			Boolean orderReview = orderInfoFacade.orderReview(orderId, state, sessionInfo.getId());
			if (orderReview) {
				j.setSuccess(true);
				j.setMsg("审核完成！！");
			} else {
				j.setSuccess(false);
				j.setMsg("审核出错！！");
			}

		} catch (ServiceException e) {
			j.setMsg("系统出错：" + e.getMessage());
			log.error("失败", e);
		}
		return j;
	}

	// 根据订单查跟单
	@RequestMapping("/getByOrderInfoPage")
	public String getByOrderInfoPage(HttpServletRequest request, Integer orderId) {
		request.setAttribute("orderId", orderId);
		return "/order/order_queryOrderFollowup";
	}

	@RequestMapping("/getByOrderInfo")
	@ResponseBody
	public OrderFollowupDTO getByOrderInfo(OrderInfoCommand orderInfoCommand) {
		return orderFollowupFacade.getByOrderInfo(orderInfoCommand);
	}
	
	// 根据订单查物料交期
	@RequestMapping("/getMaterialFollowupPage")
	public String getMaterialFollowupPage(HttpServletRequest request, Integer orderId) {
		request.setAttribute("orderId", orderId);
		return "/order/order_queryMaterialFollowup";
	}

	@RequestMapping("/getMaterialFollowup")
	@ResponseBody
	public List<MaterialFollowupDTO> getMaterialFollowup(OrderInfoCommand orderInfoCommand) {
		return materialFollowupFacade.listAll(orderInfoCommand, null);
	}
		
	// 根据订单查包装工艺
	@RequestMapping("/getManufacturePage")
	public String getManufacturePage(HttpServletRequest request, String orderNo) {
		request.setAttribute("orderNo", orderNo);
		return "/order/order_queryManufacture";
	}

	@RequestMapping("/getManufacture")
	@ResponseBody
	public ManufactureDTO getManufacture(ManufactureCommand manufactureCommand,HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		return manufactureFacade.getManufacture(manufactureCommand, false, sessionInfo.getId());
	}

	// 修改跟单
	@RequestMapping("/changeMerchandiserPage")
	public String changeMerchandiserPage(HttpServletRequest request, Integer orderId) {
		request.setAttribute("orderId", orderId);
		return "/order/order_changeMerchandiserPage";
	}

	@RequestMapping("/changeMerchandiser")
	@ResponseBody
	public Json changeMerchandiser(OrderInfoCommand orderInfoCommand) {
		Json j = new Json();
		try {
			orderInfoFacade.changeMerchandiser(orderInfoCommand);
			j.setSuccess(true);
			j.setMsg("变更成功！！");

		} catch (Exception e) {
			j.setMsg("系统出错：" + e.getMessage());
			log.error("失败", e);
		}
		return j;
	}

	// 修改业务
	@RequestMapping("/changeSellerPage")
	public String changeSellerPage(HttpServletRequest request, Integer orderId) {
		request.setAttribute("orderId", orderId);
		return "/order/order_changeSellerPage";
	}

	@RequestMapping("/changeSeller")
	@ResponseBody
	public Json changeSeller(OrderInfoCommand orderInfoCommand) {
		Json j = new Json();
		try {
			orderInfoFacade.changeSeller(orderInfoCommand);
			j.setSuccess(true);
			j.setMsg("变更成功！！");

		} catch (Exception e) {
			j.setMsg("系统出错：" + e.getMessage());
			log.error("失败", e);
		}
		return j;
	}

	/**
	 * 分页查询定单基础信息
	 */
	@RequestMapping("/dataGridStatisticsMonth")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<OrderInfoDTO> dataGridStatistics(OrderInfoCommand orderInfoCommand, PageCommand pageCommand, HttpServletRequest request) { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		//SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		//SystemContext.setCurrUserId(sessionInfo.getId());
		HashSet<Integer> states = new HashSet<Integer>();states.add(OrderInfoConst.approved);states.add(OrderInfoConst.materialfinished);states.add(OrderInfoConst.archive);
		BasePage<OrderInfoDTO> pageList = orderInfoFacade.dataGridStatistics(orderInfoCommand, pageCommand, states);
		return pageList;
	}
	
	@RequestMapping("/getYearStatPage")
	public String getYearStatPage(HttpServletRequest request) {
		return "/order/orderStatisticsYear";
	}
	
	@RequestMapping("/getYearStatChartPage")
	public String getYearStatChartPage(HttpServletRequest request) {
		return "/order/orderStatisticsYearChart";
	}
	
	@RequestMapping("/dataGridStatisticsYear")
	@ResponseBody
	public List<OrderInfoYearDto> dataGridStatisticsSellerYear(Integer type, String year) { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		if (type == null || StringUtils.isBlank(year)) return null;
		List<OrderInfoYearDto> list = orderInfoFacade.getOrderInfoYearSellerVoList(type, year);
		return list;
	}
	
	@RequestMapping("/dataGridStatisticsAllYear")
	@ResponseBody
	public List<OrderInfoAllYearVo> getOrderInfoAllYearVoList(String yearStart,
			String yearEnd) {
		return orderInfoFacade.getOrderInfoAllYearVoList(yearStart, yearEnd);
	}
/*	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id, HttpServletRequest request) {
		Json j = new Json();
		try {
			orderInfoFacade.delete(id);
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			j.setSuccess(false);
			//j.setMsg("删除失败,系统出错：" + e.getMessage());
			j.setMsg("删除失败,请先删除相关订单绑定数据！error:" + e.getMessage());
			log.error("订单删除失败", e);
		}
		return j;
	}*/

}
