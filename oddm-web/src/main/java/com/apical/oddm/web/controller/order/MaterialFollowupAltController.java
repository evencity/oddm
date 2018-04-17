package com.apical.oddm.web.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.materialFollow.MaterialFollowupAltFacade;
import com.apical.oddm.facade.materialFollow.dto.MaterialFollowupAlterDTO;
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
@RequestMapping("/materialFollowupAlt")
public class MaterialFollowupAltController extends BaseController {

	@Autowired
	private MaterialFollowupAltFacade materialFollowupAltFacade;

	@RequestMapping("/materialFollowupAltPage")
	public String orderAltPage(Integer materialFollowupId, HttpServletRequest request) {
		request.setAttribute("materialFollowupId", materialFollowupId);
		return "/order/materialFollowupAlt";
	}

	/**
	 * 分页查询定单基础信息
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public BasePage<MaterialFollowupAlterDTO> dataGrid(Integer materialFollowupId, PageCommand pageCommand) {
		BasePage<MaterialFollowupAlterDTO> basePage = materialFollowupAltFacade.dataGrid(materialFollowupId, pageCommand);
		return basePage;
	}
}
