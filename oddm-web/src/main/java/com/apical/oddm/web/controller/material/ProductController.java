package com.apical.oddm.web.controller.material;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.material.ProductFacade;
import com.apical.oddm.facade.material.command.ProductCommand;
import com.apical.oddm.facade.material.dto.ProductDTO;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.pageModel.base.Json;

@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(ProductController.class);
	@Autowired
	private ProductFacade productFacade;

	@RequestMapping("/manager")
	public String manager() {
		return "/material/product";
	}

	/**
	 * 该注解用于将Controller的方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后， 写入到Response对象的body数据区。 使用时机：返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用。
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<ProductDTO> dataGrid(ProductCommand productCommand, PageCommand pageCommand) { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		BasePage<ProductDTO> pageList = productFacade.dataGrid(productCommand, pageCommand);
		return pageList;
	}

	@RequestMapping("/addPage")
	public String addPage() {
		return "/material/productAdd";
	}

	@RequestMapping("/add")
	@ResponseBody
	public Json add(@RequestBody ProductCommand productCommand) {
		Json j = new Json();

		try {
			productCommand.setName(productCommand.getName().trim());
			Boolean success = productFacade.add(productCommand);
			if (success) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！");
			}
		} catch (ConstraintViolationException e) {
			j.setMsg("机型已存在！");
			j.setSuccess(false);
			log.error("机型已经存在2！", e);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			j.setSuccess(false);
			j.setMsg("添加失败！");
			log.error("机型已经存在3！", e);
		}

		return j;
	}

	@RequestMapping("/getPage")
	public String getPage(HttpServletRequest request, Integer id) {
		request.setAttribute("productId", id);
		return "/material/productDetails";
	}

	@RequestMapping("/get")
	@ResponseBody
	public ProductDTO get(Integer id) {
		ProductDTO productDTO = null;
		if (id != null) {
			productDTO = productFacade.get(id);
		}
		return productDTO;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		try {
			productFacade.delete(id);
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
		ProductDTO productDTO = productFacade.get(id);
		request.setAttribute("productId", id);
		request.setAttribute("product", productDTO);
		return "/material/productEdit";
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(@RequestBody ProductCommand productCommand) {
		Json j = new Json();
		try {
			productCommand.setName(productCommand.getName().trim());
			productFacade.edit(productCommand);
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

	@RequestMapping("/listProduct")
	@ResponseBody
	public List<ProductDTO> listProduct(String productTypeName) {
		List<ProductDTO> list = new ArrayList<ProductDTO>();
		/*
		 * if(productTypeName != null && !"".equals(productTypeName)){ list = productFacade.listProduct(productTypeName.trim()); }
		 */
		list = productFacade.listProduct(productTypeName.trim());
		return list;
	}

	@RequestMapping("/getProductByName")
	@ResponseBody
	public ProductDTO getProductByName(String productName) {
		ProductDTO productByName = productFacade.getProductByName(productName);
		return productByName;
	}

	@RequestMapping("/isExistProduct")
	@ResponseBody
	public Json isExistProduct(String productName) {
		Json j = new Json();
		try {
			Boolean existProduct = productFacade.isExistProduct(productName);
			if (existProduct) {
				j.setSuccess(true);
			} else {
				j.setSuccess(false);
			}
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			log.error("系统报错", e);
		}
		return j;
	}
	
	@RequestMapping("/freeze")
	@ResponseBody
	public Json freeze(ProductCommand productCommand) {
		Json j = new Json();
		try {
			productFacade.freeze(productCommand);
			j.setMsg("冻结成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			log.error("系统报错", e);
		}
		return j;
	}
}
