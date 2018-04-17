package com.apical.oddm.web.controller.material;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.facade.material.MaterialTypeFacade;
import com.apical.oddm.facade.material.command.MaterialTypeCommand;
import com.apical.oddm.facade.material.dto.MaterialTypeDTO;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.pageModel.base.Json;

@Controller
@RequestMapping("/materialType")
public class MaterialTypeController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(MaterialTypeController.class);
	@Autowired
	private MaterialTypeFacade materialTypeFacade;

	@RequestMapping("/manager")
	public String manager() {
		return "/material/materialType";
	}

	/**
	 * 该注解用于将Controller的方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后， 写入到Response对象的body数据区。 使用时机：返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用。
	 */
	/*
	 * @RequestMapping("/dataGrid")
	 * 
	 * @ResponseBody //jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致 public BasePage<MaterialTypeDTO> dataGrid(MaterialTypeCommand materialTypeCommand, PageCommand
	 * pageCommand) { //此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象 BasePage<MaterialTypeDTO> pageList = materialTypeFacade.pageList(materialTypeCommand,pageCommand); return pageList; }
	 */

	@RequestMapping("/dataGrid")
	// 用data是为了统一名称
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public List<MaterialTypeDTO> dataGrid() { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		List<MaterialTypeDTO> pageList = materialTypeFacade.pageList();
		return pageList;
	}

	/**
	 * 裸机,配件,包材物料添加类型
	 * 
	 * @return
	 */
	@RequestMapping("/materialType")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public List<MaterialTypeDTO> materialType(String type) { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		Set<Integer> set = new HashSet<Integer>();
		String[] strings = type.split(",");
		for (String s : strings) {
			set.add(Integer.parseInt(s));
		}
		List<MaterialTypeDTO> dataGrid = materialTypeFacade.dataGrid(set);
		return dataGrid;
	}

	@RequestMapping("/addPage")
	public String addPage() {
		return "/material/materialTypeAdd";
	}

	@RequestMapping("/add")
	@ResponseBody
	public Json add(MaterialTypeCommand materialTypeCommand) {
		Json j = new Json();
		/*
		 * if (materialTypeDTO != null) { j.setMsg("用户名已存在!"); } else {
		 */
		try {
			materialTypeCommand.setName(materialTypeCommand.getName().trim());
			Boolean success = materialTypeFacade.add(materialTypeCommand);
			if (success) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！");
			}
		} catch (ConstraintViolationException e) {
			j.setMsg("物料已存在！");
			log.error("系统报错", e);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			log.error("系统报错", e);
		}

		// }
		return j;
	}

	@RequestMapping("/get")
	@ResponseBody
	public MaterialTypeDTO get(Integer id) {
		return materialTypeFacade.get(id);
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		try {
			materialTypeFacade.delete(id);
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
		MaterialTypeDTO materialTypeDTO = materialTypeFacade.get(id);

		request.setAttribute("materialType", materialTypeDTO);
		return "/material/materialTypeEdit";
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MaterialTypeCommand materialTypeCommand) {
		Json j = new Json();
		try {
			materialTypeCommand.setName(materialTypeCommand.getName().trim());
			materialTypeFacade.edit(materialTypeCommand);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (DataIntegrityViolationException e) {//
			System.out.println("..........................................................................");
			log.error("系统报错", e);
			j.setMsg("物料已存在！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			log.error("系统报错", e);
		}

		return j;
	}

}
