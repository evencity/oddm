package com.apical.oddm.web.controller.order;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.core.constant.OrderInfoConst;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.order.OrderInfoFacade;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;
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
@RequestMapping("/orderReview")
public class OrderReviewController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(OrderReviewController.class);
	@Autowired
	private OrderInfoFacade orderInfoFacade;

	@RequestMapping("/manager")
	public String manager() {
		return "/order/orderReview";
	}

	/**
	 * 分页查询定单基础信息
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<OrderInfoDTO> dataGrid(OrderInfoCommand orderInfoCommand, PageCommand pageCommand, HttpServletRequest request) { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		if ("".equals(orderInfoCommand.getOrderNo())) {
			orderInfoCommand.setOrderNo(null);
		}
		if ("".equals(orderInfoCommand.getClientName())) {
			orderInfoCommand.setClientName(null);
		}
		if ("".equals(orderInfoCommand.getProductClient())) {
			orderInfoCommand.setProductClient(null);
		}
		if ("".equals(orderInfoCommand.getSeller())) {
			orderInfoCommand.setSeller(null);
		}
		// 这里暂时这样写
		Set<Integer> set = new HashSet<Integer>();
		// set.add(2);
		set.add(OrderInfoConst.submitaudit);
		// set.add(OrderInfoConst.rejected);
		orderInfoCommand.setSellerId(sessionInfo.getId());
		BasePage<OrderInfoDTO> pageList = orderInfoFacade.dataGrid(orderInfoCommand, pageCommand, set, sessionInfo.getHasRoles());
		return pageList;
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
			log.error("系统出错:", e);
		}
		return j;
	}

}
