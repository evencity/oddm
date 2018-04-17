package com.apical.oddm.web.controller.material;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.material.MaterialPackingFacade;
import com.apical.oddm.facade.material.command.MaterialPackingCommand;
import com.apical.oddm.facade.material.dto.MaterialPackingDTO;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.pageModel.base.Json;

@Controller
@RequestMapping("/materialPacking")
public class MaterialPackingController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(MaterialPackingController.class);
	@Autowired
	private MaterialPackingFacade materialPackingFacade;

	@RequestMapping("/manager")
	public String manager() {
		return "/material/materialPacking";
	}

	/**
	 * 该注解用于将Controller的方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后， 写入到Response对象的body数据区。 使用时机：返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用。
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public BasePage<MaterialPackingDTO> dataGrid(MaterialPackingCommand materialPackingCommand, PageCommand pageCommand) {
		BasePage<MaterialPackingDTO> pageList = materialPackingFacade.pageList(materialPackingCommand, pageCommand);
		return pageList;
	}

	@RequestMapping("/dataGridForOrder")
	@ResponseBody
	public BasePage<MaterialPackingDTO> dataGridForOrder(MaterialPackingCommand materialPackingCommand, PageCommand pageCommand) {
		BasePage<MaterialPackingDTO> pageList = materialPackingFacade.pageList(materialPackingCommand, pageCommand);
		return pageList;
	}

	@RequestMapping("/listIsBase")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public List<MaterialPackingDTO> listIsBase() { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象

		return materialPackingFacade.listIsBase(2);
	}

	@RequestMapping("/packingPage")
	public String packingPage(HttpServletRequest request, String materialPackingIds) {
		request.setAttribute("materialPackingIds", materialPackingIds);
		System.out.println(request.getAttribute("materialPackingIds"));
		return "/material/packing";
	}

	@RequestMapping("/packingForOrderPage")
	public String packingForOrder(HttpServletRequest request, String materialPackingNames) {
		request.setAttribute("materialPackingNames", materialPackingNames);
		return "/material/packingForOrder";
	}

	@RequestMapping("/addPage")
	public String addPage() {
		return "/material/materialPackingAdd";
	}

	@RequestMapping("/add")
	@ResponseBody
	public Json add(MaterialPackingCommand materialPackingCommand) {
		Json j = new Json();
		/*
		 * if (materialPackingDTO != null) { j.setMsg("用户名已存在!"); } else {
		 */
		try {
			materialPackingCommand.setName(materialPackingCommand.getName().trim());
			Boolean success = materialPackingFacade.add(materialPackingCommand);
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
			log.error("系统出错", e);
		}

		// }
		return j;
	}

	@RequestMapping("/get")
	@ResponseBody
	public MaterialPackingDTO get(Integer id) {
		return materialPackingFacade.get(id);
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		try {
			materialPackingFacade.delete(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			log.error("系统出错", e);
		}
		return j;
	}

	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MaterialPackingDTO materialPackingDTO = materialPackingFacade.get(id);
		request.setAttribute("materialPacking", materialPackingDTO);
		System.out.println(request.getAttribute("materialPacking"));
		return "/material/materialPackingEdit";
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MaterialPackingCommand materialPackingCommand) {
		Json j = new Json();
		try {
			materialPackingCommand.setName(materialPackingCommand.getName().trim());
			materialPackingFacade.edit(materialPackingCommand);
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
