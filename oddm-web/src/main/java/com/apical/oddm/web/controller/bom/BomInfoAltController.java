package com.apical.oddm.web.controller.bom;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.bom.BomInfoAltFacade;
import com.apical.oddm.facade.bom.dto.BomInfoAltDTO;

@Controller
@RequestMapping("/bomAlt")
public class BomInfoAltController {

	@Autowired
	private BomInfoAltFacade bomInfoAltFacade;
	
	@RequestMapping(value="/bomAltPage")
	public String bomInfoAltPage(HttpServletRequest request, int id) {
		request.setAttribute("bomId", id);
		return "/bom/bomInfoAlt";
	}
	
	@ResponseBody
	@RequestMapping(value="/dataGrid")
	public BasePage<BomInfoAltDTO> dataGrid(Integer BomId, PageCommand pageCommand) {
		return bomInfoAltFacade.dataGrid(BomId, pageCommand);
	}
}
