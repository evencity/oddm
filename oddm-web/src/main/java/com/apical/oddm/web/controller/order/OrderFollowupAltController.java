package com.apical.oddm.web.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.order.OrderFollowupAlterFacade;
import com.apical.oddm.facade.order.dto.OrderFollowupAlterDTO;
import com.apical.oddm.web.controller.base.BaseController;

/**
 * @description 类的描述：
 * @author 作者 : zhuzh
 * @date 创建时间：2016年11月1日 下午1:44:14
 * @version 1.0
 */
@Controller
@RequestMapping("/orderFollowupAlt")
public class OrderFollowupAltController extends BaseController {

	@Autowired
	private OrderFollowupAlterFacade orderFollowupAlterFacade;

	@RequestMapping("/orderFollowupAltPage")
	public String orderFollowupAltPage(Integer orderFollowupId, HttpServletRequest request) {
		request.setAttribute("orderFollowupId", orderFollowupId);
		return "/order/orderFollowupAlt";
	}

	/**
	 * 分页查询定单基础信息
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public BasePage<OrderFollowupAlterDTO> dataGrid(Integer orderFollowupId, PageCommand pageCommand) {
		BasePage<OrderFollowupAlterDTO> basePage = orderFollowupAlterFacade.dataGrid(orderFollowupId, pageCommand);
		return basePage;
	}
}
