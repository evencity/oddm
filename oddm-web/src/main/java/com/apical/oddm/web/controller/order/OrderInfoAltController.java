package com.apical.oddm.web.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.order.OrderInfoAltFacade;
import com.apical.oddm.facade.order.dto.OrderInfoAltDTO;
import com.apical.oddm.web.controller.base.BaseController;

/**
 * @description 类的描述：
 * @author 作者 : zhuzh
 * @date 创建时间：2016年11月1日 下午1:44:14
 * @version 1.0
 */
@Controller
@RequestMapping("/orderAlt")
public class OrderInfoAltController extends BaseController {

	@Autowired
	private OrderInfoAltFacade orderInfoAltFacade;

	@RequestMapping("/orderAltPage")
	public String orderAltPage(Integer orderId, HttpServletRequest request) {
		request.setAttribute("orderId", orderId);
		return "/order/orderAlt";
	}

	/**
	 * 分页查询定单基础信息
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public BasePage<OrderInfoAltDTO> dataGrid(Integer orderId, PageCommand pageCommand) {
		BasePage<OrderInfoAltDTO> basePage = orderInfoAltFacade.dataGrid(orderId, pageCommand);
		return basePage;
	}
}
