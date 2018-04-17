package com.apical.oddm.web.controller.manufacture;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.manufacture.ManufactureAltFacade;
import com.apical.oddm.facade.manufacture.dto.ManufactureAltDTO;
import com.apical.oddm.web.controller.base.BaseController;

/**
 * @description 类的描述：
 * @author 作者 : zhuzh
 * @date 创建时间：2016年11月1日 下午1:44:14
 * @version 1.0
 */
@Controller
@RequestMapping("/manufactureAlt")
public class ManufactureAltController extends BaseController {

	@Autowired
	private ManufactureAltFacade manufactureAltFacade;

	@RequestMapping("/manufactureAltPage")
	public String manufactureAltPage(Integer manufactureId, HttpServletRequest request) {
		request.setAttribute("manufactureId", manufactureId);
		return "/manufacture/manufactureAlt";
	}

	/**
	 * 分页查询定单基础信息
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public BasePage<ManufactureAltDTO> dataGrid(Integer manufactureId, PageCommand pageCommand) {
		BasePage<ManufactureAltDTO> dataGrid = manufactureAltFacade.dataGrid(manufactureId, pageCommand);
		return dataGrid;
	}
}
