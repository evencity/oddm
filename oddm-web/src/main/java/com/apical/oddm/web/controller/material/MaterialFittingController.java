package com.apical.oddm.web.controller.material;

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
import com.apical.oddm.facade.material.MaterialFittingFacade;
import com.apical.oddm.facade.material.command.MaterialFittingCommand;
import com.apical.oddm.facade.material.dto.MaterialFittingDTO;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.pageModel.base.Json;

@Controller
@RequestMapping("/materialFitting")
public class MaterialFittingController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(MaterialFittingController.class);
	@Autowired
	private MaterialFittingFacade materialFittingFacade;

	@RequestMapping("/manager")
	public String manager() {
		return "/material/materialFitting";
	}

	/**
	 * 该注解用于将Controller的方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后， 写入到Response对象的body数据区。 使用时机：返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用。
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<MaterialFittingDTO> dataGrid(MaterialFittingCommand materialFittingCommand, PageCommand pageCommand) { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		BasePage<MaterialFittingDTO> pageList = materialFittingFacade.pageList(materialFittingCommand, pageCommand);
		return pageList;
	}

	@RequestMapping("/dataGridForOrder")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<MaterialFittingDTO> dataGridForOrder(MaterialFittingCommand materialFittingCommand, PageCommand pageCommand) { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		BasePage<MaterialFittingDTO> pageList = materialFittingFacade.pageList(materialFittingCommand, pageCommand);
		return pageList;
	}

	@RequestMapping("/fittingPage")
	public String fittingPage(HttpServletRequest request, String materialFittingIds) {
		request.setAttribute("materialFittingIds", materialFittingIds);
		System.out.println(request.getAttribute("materialFittingIds"));
		return "/material/fitting";
	}

	@RequestMapping("/fittingForOrderPage")
	public String fittingForOrderPage(HttpServletRequest request, String materialFittingNames) {
		request.setAttribute("materialFittingNames", materialFittingNames);
		System.out.println(request.getAttribute("materialFittingNames"));
		return "/material/fittingForOrder";
	}

	@RequestMapping("/addPage")
	public String addPage() {
		return "/material/materialFittingAdd";
	}

	@RequestMapping("/add")
	@ResponseBody
	public Json add(MaterialFittingCommand materialFittingCommand) {
		Json j = new Json();
		try {
			materialFittingCommand.setName(materialFittingCommand.getName().trim());
			Boolean success = materialFittingFacade.add(materialFittingCommand);
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

		return j;
	}

	@RequestMapping("/get")
	@ResponseBody
	public MaterialFittingDTO get(Integer id) {
		return materialFittingFacade.get(id);
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		try {
			materialFittingFacade.delete(id);
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
		MaterialFittingDTO materialFittingDTO = materialFittingFacade.get(id);

		request.setAttribute("materialFitting", materialFittingDTO);
		return "/material/materialFittingEdit";
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MaterialFittingCommand materialFittingCommand) {
		Json j = new Json();
		try {
			materialFittingCommand.setName(materialFittingCommand.getName().trim());
			materialFittingFacade.edit(materialFittingCommand);
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
