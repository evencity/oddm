package com.apical.oddm.web.controller.material;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.material.MaterialBareFacade;
import com.apical.oddm.facade.material.command.MaterialBareCommand;
import com.apical.oddm.facade.material.dto.MaterialBareDTO;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.pageModel.base.Json;

@Controller
@RequestMapping("/materialBare")
public class MaterialBareController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(MaterialBareController.class);
	@Autowired
	private MaterialBareFacade materialBareFacade;

	@RequestMapping("/manager")
	public String manager() {
		return "/material/materialBare";
	}

	/**
	 * 该注解用于将Controller的方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后， 写入到Response对象的body数据区。 使用时机：返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用。
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public BasePage<MaterialBareDTO> dataGrid(MaterialBareCommand materialBareCommand, PageCommand pageCommand) {
		
		BasePage<MaterialBareDTO> pageList = materialBareFacade.pageList(materialBareCommand, pageCommand);
		return pageList;
	}

	@RequestMapping("/listforShell")
	@ResponseBody
	public List<MaterialBareDTO> listforShell() {
		Set<Integer> set = new HashSet<Integer>();
		List<MaterialBareDTO> list = new ArrayList<MaterialBareDTO>();
		try {
			set.add(1);
			list = materialBareFacade.listGridBySuperType(set, 2);
		} catch (ServiceException e) {
			e.getMessage();
		}
		return list;
	}

	@RequestMapping("/listforBase")
	@ResponseBody
	public List<MaterialBareDTO> listforBase() {
		Set<Integer> set = new HashSet<Integer>();
		List<MaterialBareDTO> list = new ArrayList<MaterialBareDTO>();
		try {
			set.add(2);
			list = materialBareFacade.listGridBySuperType(set, 2);
		} catch (ServiceException e) {
			e.getMessage();
		}
		return list;
	}

	@RequestMapping("/listforPacking")
	@ResponseBody
	public List<MaterialBareDTO> listforPacking() {
		Set<Integer> set = new HashSet<Integer>();
		List<MaterialBareDTO> list = new ArrayList<MaterialBareDTO>();
		try {
			set.add(3);
			list = materialBareFacade.listGridBySuperType(set, 2);
		} catch (ServiceException e) {
			e.getMessage();
		}
		return list;
	}

	@RequestMapping("/listforFitting")
	@ResponseBody
	public List<MaterialBareDTO> listforFitting() {
		Set<Integer> set = new HashSet<Integer>();
		List<MaterialBareDTO> list = new ArrayList<MaterialBareDTO>();
		try {
			set.add(4);
			list = materialBareFacade.listGridBySuperType(set, 2);
		} catch (ServiceException e) {
			e.getMessage();
		}
		return list;
	}

	@RequestMapping("/dataGridforBare")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<MaterialBareDTO> dataGridforBare(PageCommand pageCommand, String name) {
		BasePage<MaterialBareDTO> pageList = new BasePage<MaterialBareDTO>();
		if (name != null && !"".equals(name)) {
			pageList = materialBareFacade.dataGridByName(name, pageCommand);
		} else {
			Set<Integer> set = new HashSet<Integer>();
			set.add(2);
			pageList = materialBareFacade.dataGridBySuperType(set, pageCommand);
		}

		return pageList;
	}

	@RequestMapping("/dataGridforShell")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<MaterialBareDTO> dataGridforShell(PageCommand pageCommand, String name) {
		BasePage<MaterialBareDTO> pageList = new BasePage<MaterialBareDTO>();
		if (name != null && !"".equals(name)) {
			pageList = materialBareFacade.dataGridByName(name, pageCommand);
		} else {
			Set<Integer> set = new HashSet<Integer>();
			set.add(1);
			pageList = materialBareFacade.dataGridBySuperType(set, pageCommand);
		}

		return pageList;
	}

	@RequestMapping("/addPage")
	public String addPage() {
		return "/material/materialBareAdd";
	}

	@RequestMapping("/add")
	@ResponseBody
	public Json add(MaterialBareCommand materialBareCommand) {
		Json j = new Json();
		/*
		 * if (materialBareDTO != null) { j.setMsg("用户名已存在!"); } else {
		 */
		try {
			materialBareCommand.setName(materialBareCommand.getName().trim());
			Boolean success = materialBareFacade.add(materialBareCommand);
			if (success) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！");
			}
		} catch (ConstraintViolationException e) {
			log.error("系统报错", e);
			j.setMsg("物料已存在！");
		} catch (Exception e) {
			log.error("系统报错", e);
			j.setMsg(e.getMessage());
		}

		// }
		return j;
	}

	@RequestMapping("/get")
	@ResponseBody
	public MaterialBareDTO get(Integer id) {
		return materialBareFacade.get(id);
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		try {
			materialBareFacade.delete(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			log.error("系统报错", e);
		}
		return j;
	}

	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MaterialBareDTO materialBareDTO = materialBareFacade.get(id);

		request.setAttribute("materialBare", materialBareDTO);
		return "/material/materialBareEdit";
	}

	// 以下是给定单使用
	@RequestMapping("/hardwareForOrderPage")
	public String hardwareForOrderPage(HttpServletRequest request, String materialBareMainNames) {
		request.setAttribute("materialBareMainNames", materialBareMainNames);
		return "/material/hardwareForOrder";
	}

	@RequestMapping("/shellForOrderPage")
	public String shellForOrderPage(HttpServletRequest request, String materialBare_shellNames) {
		request.setAttribute("shellNames", materialBare_shellNames);
		return "/material/shellForOrder";
	}

	// 以下三个是给机型使用
	@RequestMapping("/hardwarePage")
	public String hardwarePage(HttpServletRequest request, String materialBare_hardwareIds) {
		request.setAttribute("hardwareIds", materialBare_hardwareIds);
		return "/material/hardware";
	}

	@RequestMapping("/shellPage")
	public String shellPage(HttpServletRequest request, String materialBare_shellIds) {
		request.setAttribute("shellIds", materialBare_shellIds);
		return "/material/shell";
	}

	@RequestMapping("/screenPage")
	public String screenPage(HttpServletRequest request, String materialBare_screenIds) {
		request.setAttribute("screenIds", materialBare_screenIds);
		return "/material/screen";
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MaterialBareCommand materialBareCommand) {
		Json j = new Json();
		try {
			materialBareCommand.setName(materialBareCommand.getName().trim());
			materialBareFacade.edit(materialBareCommand);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (DataIntegrityViolationException e) {//
			log.error("系统报错", e);
			j.setMsg("物料已存在！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			log.error("系统报错", e);
		}
		return j;
	}
	
	//文档选择物料使用，列出所有的除     包材     物料
	@RequestMapping("/dataGridforDocument")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<MaterialBareDTO> dataGridforDocument(PageCommand pageCommand, String name) {
		BasePage<MaterialBareDTO> pageList = new BasePage<MaterialBareDTO>();
		if (name != null && !"".equals(name)) {
			pageList = materialBareFacade.dataGridByName(name, pageCommand);
		} else {
			Set<Integer> set = new HashSet<Integer>();
			set.add(1);
			set.add(2);
			set.add(4);
			set.add(5);
			pageList = materialBareFacade.dataGridBySuperType(set, pageCommand);
		}

		return pageList;
	}
}
