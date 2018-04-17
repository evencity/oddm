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

import com.apical.oddm.facade.material.ProductTypeFacade;
import com.apical.oddm.facade.material.command.ProductTypeCommand;
import com.apical.oddm.facade.material.dto.ProductTypeDTO;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.pageModel.base.Json;

@Controller
@RequestMapping("/productType")
public class ProductTypeController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(ProductTypeController.class);
	@Autowired
	private ProductTypeFacade productTypeFacade;

	@RequestMapping("/manager")
	public String manager() {
		return "/material/productType";
	}

	/**
	 * 该注解用于将Controller的方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后， 写入到Response对象的body数据区。 使用时机：返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用。
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public List<ProductTypeDTO> dataGrid() { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		List<ProductTypeDTO> pageList = productTypeFacade.pageList();
		return pageList;
	}

	@RequestMapping("/addPage")
	public String addPage() {
		return "/material/productTypeAdd";
	}

	@RequestMapping("/add")
	@ResponseBody
	public Json add(ProductTypeCommand productTypeCommand) {
		Json j = new Json();
		/*
		 * if (productTypeDTO != null) { j.setMsg("用户名已存在!"); } else {
		 */
		try {
			productTypeCommand.setName(productTypeCommand.getName().trim());
			productTypeFacade.add(productTypeCommand);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (ConstraintViolationException e) {
			j.setMsg("机型类型已存在！");
			j.setSuccess(false);
			log.error("机型类型已经存在2！", e);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}

		// }
		return j;
	}

	@RequestMapping("/get")
	@ResponseBody
	public ProductTypeDTO get(Integer id) {
		return productTypeFacade.get(id);
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		try {
			productTypeFacade.delete(id);
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
		ProductTypeDTO productTypeDTO = productTypeFacade.get(id);

		request.setAttribute("productType", productTypeDTO);
		return "/material/productTypeEdit";
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ProductTypeCommand productTypeCommand) {
		Json j = new Json();
		try {
			productTypeCommand.setName(productTypeCommand.getName().trim());
			productTypeFacade.edit(productTypeCommand);
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
