package com.apical.oddm.web.controller.material;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.material.MaterialFacade;
import com.apical.oddm.facade.material.dto.MaterialDTO;
import com.apical.oddm.web.controller.base.BaseController;

@Controller
@RequestMapping("/material")
public class MaterialController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(MaterialController.class);
	@Autowired
	private MaterialFacade materialFacade;

	@RequestMapping("/dataGrid")
	@ResponseBody
	public BasePage<MaterialDTO> dataGrid(MaterialDTO materialDTO, PageCommand pageCommand) {

		BasePage<MaterialDTO> pageList = materialFacade.dataGridBySuperType(materialDTO, null, pageCommand);
		return pageList;
	}

	@RequestMapping("/dataGridForOther")
	@ResponseBody
	public BasePage<MaterialDTO> dataGridForOther(MaterialDTO materialDTO, PageCommand pageCommand) {
		Set<Integer> set = new HashSet<Integer>();
		set.add(1);
		set.add(2);
		set.add(4);
		set.add(5);
		BasePage<MaterialDTO> pageList = materialFacade.dataGridBySuperType(materialDTO, set, pageCommand);
		return pageList;
	}
}
