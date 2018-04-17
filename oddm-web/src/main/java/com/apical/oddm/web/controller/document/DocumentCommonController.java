package com.apical.oddm.web.controller.document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.document.DocumentCommonFacade;
import com.apical.oddm.facade.document.command.DocumentCommonCommand;
import com.apical.oddm.facade.document.dto.DocumentCommonDTO;
import com.apical.oddm.web.pageModel.base.Json;

/**
 * @description 类的描述：
 * @author 作者 : zhuzh
 * @date 创建时间：2016年11月10日 上午9:37:10
 * @version 1.0
 */

@Controller
@RequestMapping("/documentCommon")
public class DocumentCommonController {

	private static final Logger log = LoggerFactory.getLogger(DocumentCommonController.class);
	@Autowired
	private DocumentCommonFacade documentCommonFacade;

	@RequestMapping("/manager")
	public String manager() {
		return "/document/documentCommon";
	}

	@RequestMapping("/documentCommonList")
	public String documentCommonList() {
		return "/document/documentCommonList";
	}

	@RequestMapping("/dataGrid")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<DocumentCommonDTO> dataGrid(DocumentCommonCommand documentCommonCommand, PageCommand pageCommand) {
		System.out.println(documentCommonCommand);
		BasePage<DocumentCommonDTO> pageList = documentCommonFacade.dataGridByMtName(documentCommonCommand.getNameMt(), pageCommand);
		return pageList;
	}

	@RequestMapping("/dataGridForDocument")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<DocumentCommonDTO> dataGridForDocument(DocumentCommonCommand documentCommonCommand, PageCommand pageCommand) {
		System.out.println(documentCommonCommand);
		BasePage<DocumentCommonDTO> pageList = documentCommonFacade.dataGridByMtName(documentCommonCommand.getNameMt(), pageCommand);
		return pageList;
	}
	
	@RequestMapping("/addPage")
	public String addPage() {
		return "/document/documentCommonAdd";
	}

	@RequestMapping("/addFile")
	@ResponseBody
	public Json addFile(HttpServletRequest request, DocumentCommonCommand documentCommandcCommand) throws IllegalStateException, IOException {
		Json j = new Json();
		documentCommandcCommand.setUploadtime(new Date());
		documentCommandcCommand.setState(2);
		if ("".equals(documentCommandcCommand.getDescription())) {
			documentCommandcCommand.setDescription(null);
		}
		try {
			Boolean success = documentCommonFacade.add(documentCommandcCommand);
			if (success) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！");
			}
		} catch (ConstraintViolationException e) {
			// TODO: handle exception
			j.setSuccess(false);
			j.setMsg("物料文档已经存在！");
			log.error(e.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			j.setSuccess(false);

			j.setMsg("添加失败！");
			log.error(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/add")
	@ResponseBody
	public Json add(HttpServletRequest request, DocumentCommonCommand documentCommonCommand) throws IllegalStateException, IOException {
		Json j = new Json();
		documentCommonCommand.setUploadtime(new Date());
		// documentCommonCommand.setVersion(0);//上传成功后，处于业务未审核状态2
		documentCommonCommand.setState(1);// 此处需要确定文档状态
		if ("".equals(documentCommonCommand.getDescription())) {
			documentCommonCommand.setDescription(null);
		}
		try {
			Boolean success = documentCommonFacade.add(documentCommonCommand);
			if (success) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！");
			}
		} catch (ConstraintViolationException e) {
			j.setSuccess(false);
			j.setMsg("物料文档已经存在！");
			log.error(e.getMessage());
		} catch (Exception e) {
			j.setSuccess(false);

			j.setMsg("添加失败！");
			log.error(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/get")
	@ResponseBody
	public DocumentCommonDTO get(Integer id) {
		return documentCommonFacade.get(id);
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		try {
			documentCommonFacade.delete(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String nameMt) {

		request.setAttribute("nameMt", nameMt);
		return "/document/documentCommonEdit";
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(DocumentCommonCommand documentCommonCommand) {
		Json j = new Json();
		if ("".equals(documentCommonCommand.getDescription())) {
			documentCommonCommand.setDescription(null);
		}
		try {
			documentCommonFacade.updatePath(documentCommonCommand);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/getListByMtName")
	@ResponseBody
	public List<DocumentCommonDTO> getListByMtName(String nameMaterial) {
		List<DocumentCommonDTO> listByMtName = new ArrayList<DocumentCommonDTO>();
		if (nameMaterial != null && !"".equals(nameMaterial.trim())) {
			listByMtName = documentCommonFacade.getListByMtName(nameMaterial);
			return listByMtName;
		}
		return listByMtName;
	}

}
