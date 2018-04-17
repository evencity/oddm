package com.apical.oddm.web.controller.document;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.apical.oddm.application.document.DocumentUnreadServiceI;
import com.apical.oddm.core.constant.DocumentConst;
import com.apical.oddm.core.constant.OrderInfoConst;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.document.DocumentFacade;
import com.apical.oddm.facade.document.command.DocumentCommand;
import com.apical.oddm.facade.document.dto.DocumentDTO;
import com.apical.oddm.facade.order.OrderInfoFacade;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;
import com.apical.oddm.web.framework.constant.GlobalConstant;
import com.apical.oddm.web.pageModel.base.Json;
import com.apical.oddm.web.pageModel.base.SessionInfo;

/**
 * @description 类的描述：
 * @author 作者 : zhuzh
 * @date 创建时间：2016年11月10日 上午9:37:10
 * @version 1.0
 */

@Controller
@RequestMapping("/document")
public class DocumentController {

	private static final Logger log = LoggerFactory.getLogger(DocumentController.class);

	@Autowired
	private DocumentFacade documentFacade;

	@Autowired
	private OrderInfoFacade orderInfoFacade;

	@Autowired
	private DocumentUnreadServiceI documentUnreadService;

	@RequestMapping("/manager")
	public String manager() {
		return "/document/document";
	}

	@RequestMapping("/managerAll")
	public String managerAll() {
		return "/document/documentAll";
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		try {
			documentFacade.delete(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/get")
	@ResponseBody
	public DocumentDTO get(Integer id) {
		DocumentDTO documentDTO = documentFacade.get(id);
		return documentDTO;
	}

	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id, String documentName, Integer orderId, Integer orderIdForInput) {
		request.setAttribute("documentId", id);
		request.setAttribute("documentName", documentName);
		request.setAttribute("orderId", orderId);
		request.setAttribute("orderIdForInput", orderIdForInput);
		return "/document/documentEdit";
	}

	/*@RequestMapping("/edit")
	@ResponseBody
	public Json edit(DocumentCommand documentCommand, HttpServletRequest request) {
		//SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		try {
			//documentCommand.setUserId(sessionInfo.getId());
			String path = HtmlUtils.htmlUnescape(documentCommand.getPath());
			documentCommand.setPath(path);
			documentFacade.editDocPath(documentCommand);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			// TODO: handle exception
			j.setSuccess(false);
			j.setMsg("添加失败！");
			log.error("edit出错", e);
		}
		return j;
	}*/
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(@RequestBody DocumentCommand documentCommand, HttpServletRequest request) {
		//SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		try {
			//documentCommand.setUserId(sessionInfo.getId());
			String path = HtmlUtils.htmlUnescape(documentCommand.getPath());
			documentCommand.setPath(path);
			documentFacade.editDocPath(documentCommand);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			// TODO: handle exception
			j.setSuccess(false);
			j.setMsg("添加失败！");
			log.error("edit出错", e);
		}
		return j;
	}

	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request, Integer orderId, Integer orderIdForInput) {
		request.setAttribute("orderIdForInput", orderIdForInput);
		if (orderId != null) {
			SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
			OrderInfoDTO baseOrderInfoDTO = orderInfoFacade.getBaseOrderInfoDTO(orderId, sessionInfo.getHasRoles());
			request.setAttribute("orderNo", baseOrderInfoDTO.getOrderNo());
			request.setAttribute("productFactory", baseOrderInfoDTO.getProductFactory());
			request.setAttribute("productClient", baseOrderInfoDTO.getProductClient());
			request.setAttribute("orderId", orderId);
			System.out.println(baseOrderInfoDTO);
		}

		return "/document/documentAdd";
	}

	/*@RequestMapping("/addFile")
	@ResponseBody
	public Json addFile(HttpServletRequest request, DocumentCommand documentCommand) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		documentCommand.setUploadtime(new Date());
		//documentCommand.setVersion(1);// 上传成功后，处于业务未审核状态2
		documentCommand.setVersion(DocumentConst.unpublished);
		//documentCommand.setState(2);// 此处需要确定文档状态
		documentCommand.setState(DocumentConst.uploaded);
		documentCommand.setUserId(sessionInfo.getId());
		//特殊字符处理
		String path = HtmlUtils.htmlUnescape(documentCommand.getPath());
		documentCommand.setPath(path);
		try {
			Boolean success = documentFacade.add(documentCommand);
			if (success) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			j.setSuccess(false);
			j.setMsg("添加失败！");
			log.error("addFile出错", e);
		}
		return j;
	}*/
	@RequestMapping("/addFile")
	@ResponseBody
	public Json addFile(HttpServletRequest request, @RequestBody DocumentCommand documentCommand) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		documentCommand.setUploadtime(new Date());
		//documentCommand.setVersion(1);// 上传成功后，处于业务未审核状态2
		documentCommand.setVersion(DocumentConst.unpublished);
		//documentCommand.setState(2);// 此处需要确定文档状态
		documentCommand.setState(DocumentConst.uploaded);
		documentCommand.setUserId(sessionInfo.getId());
		//特殊字符处理
		String path = HtmlUtils.htmlUnescape(documentCommand.getPath());
		documentCommand.setPath(path);
		try {
			Boolean success = documentFacade.add(documentCommand);
			if (success) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			j.setSuccess(false);
			j.setMsg("添加失败！");
			log.error("addFile出错", e);
		}
		return j;
	}

	@RequestMapping("/add")
	@ResponseBody
	public Json add(HttpServletRequest request, DocumentCommand documentCommand) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		documentCommand.setUploadtime(new Date());
		documentCommand.setVersion(0);// 上传成功后，处于业务未审核状态2
		//documentCommand.setState(1);// 此处需要确定文档状态
		documentCommand.setState(DocumentConst.unpublished);
		documentCommand.setUserId(sessionInfo.getId());
		String path = HtmlUtils.htmlUnescape(documentCommand.getPath());
		documentCommand.setPath(path);
		try {
			Boolean success = documentFacade.add(documentCommand);
			if (success) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
			} else {
				j.setSuccess(false);
				j.setMsg("文档添加失败");
			}
		} catch (ConstraintViolationException e) {
			// TODO: handle exception
			j.setSuccess(false);
			j.setMsg("文档已经存在！");
			log.error("add出错", e);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("文档添加失败");
			log.error("add出错", e);
		}
		return j;
	}

	@RequestMapping("/addCommonPage")
	public String addCommonPage(HttpServletRequest request, Integer orderId) {
		request.setAttribute("orderId", orderId);
		return "/document/documentAddForCommon";
	}

	@RequestMapping("/addCommon")
	@ResponseBody
	public Json addCommon(HttpServletRequest request, DocumentCommand documentCommand) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		// documentCommand.setUploadtime(new Date());
		//documentCommand.setVersion(1);//上传成功后，处于业务未审核状态2
		//documentCommand.setState(2);// 此处需要确定文档状态
		documentCommand.setState(DocumentConst.uploaded);
		documentCommand.setUserId(sessionInfo.getId());
		documentCommand.setType(3);//此处只使用与美工
		try {
			Boolean success = documentFacade.add(documentCommand);
			if (success) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
			} else {
				j.setSuccess(false);
				j.setMsg("文档添加失败");
			}
		} catch (ConstraintViolationException e) {
			// TODO: handle exception
			j.setSuccess(false);
			j.setMsg("文档已经存在！");
			log.error(e.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			j.setSuccess(false);
			j.setMsg("文档添加失败！");
			log.error(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/dataOrderInfoGrid")
	@ResponseBody
	public BasePage<OrderInfoDTO> dataOrderInfoGrid(OrderInfoCommand orderInfoCommand, PageCommand pageCommand, HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		if ("".equals(orderInfoCommand.getOrderNo())) {
			orderInfoCommand.setOrderNo(null);
		}
		if ("".equals(orderInfoCommand.getClientName())) {
			orderInfoCommand.setClientName(null);
		}
		if ("".equals(orderInfoCommand.getProductClient())) {
			orderInfoCommand.setProductClient(null);
		}
		if ("".equals(orderInfoCommand.getProductFactory())) {
			orderInfoCommand.setProductFactory(null);
		}
		if ("".equals(orderInfoCommand.getSeller())) {
			orderInfoCommand.setSeller(null);
		}
		Set<Integer> set = new HashSet<Integer>();
		set.add(OrderInfoConst.approved);set.add(OrderInfoConst.materialfinished);
		if (sessionInfo.getId() != null) {
			orderInfoCommand.setSellerId(sessionInfo.getId());
			orderInfoCommand.setMerchandiserId(sessionInfo.getId());
		}
		System.out.println(orderInfoCommand);
		BasePage<OrderInfoDTO> pageList = documentFacade.dataOrderInfoGrid(orderInfoCommand, set, pageCommand, sessionInfo.getHasRoles());
		return pageList;
	}

	@RequestMapping("/dataOrderInfoGridAll")
	@ResponseBody
	public BasePage<OrderInfoDTO> dataOrderInfoGridAll(OrderInfoCommand orderInfoCommand, PageCommand pageCommand, HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		if ("".equals(orderInfoCommand.getOrderNo())) {
			orderInfoCommand.setOrderNo(null);
		}
		if ("".equals(orderInfoCommand.getClientName())) {
			orderInfoCommand.setClientName(null);
		}
		if ("".equals(orderInfoCommand.getProductClient())) {
			orderInfoCommand.setProductClient(null);
		}
		if ("".equals(orderInfoCommand.getProductFactory())) {
			orderInfoCommand.setProductFactory(null);
		}
		if ("".equals(orderInfoCommand.getSeller())) {
			orderInfoCommand.setSeller(null);
		}
		System.out.println(orderInfoCommand);
		Set<Integer> set = new HashSet<Integer>();
		set.add(OrderInfoConst.approved);set.add(OrderInfoConst.materialfinished);
		BasePage<OrderInfoDTO> pageList = documentFacade.dataOrderInfoGrid(orderInfoCommand, set, pageCommand, sessionInfo.getHasRoles());
		return pageList;
	}

	@RequestMapping("/isExistPath")
	@ResponseBody
	public List<DocumentDTO> isExistPath(String path) {
		return documentFacade.isExistPath(path);
	}

	@RequestMapping("/isExistDocument")
	@ResponseBody
	public Json isExistDocument(Integer orderId, String mtName) {
		Json j = new Json();
		try {
			Boolean success = documentFacade.isExistDocument(orderId, mtName);
			if (success) {
				j.setSuccess(true);
				j.setMsg("该订单中已存在相关文档！");
			} else {
				j.setSuccess(false);
				j.setMsg("文档不存在！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			j.setSuccess(false);
			log.error(e.getMessage());
		}
		return j;
	}

	// 下载成功后，删除未读文档表记录。
	@RequestMapping("/deleteNoreadRecord")
	@ResponseBody
	public Json deleteNoreadRecord(HttpServletRequest request, Integer docId) {
		Json j = new Json();
		try {
			if (docId != null) {
				SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
				documentUnreadService.delete(sessionInfo.getId(), docId);
				SystemContext.setCurrUserId(sessionInfo.getId());
				j.setSuccess(true);
			} else {
				j.setSuccess(false);
			}
		} catch (Exception e) {
			// TODO: handle exception
			j.setSuccess(false);
			log.error(e.getMessage());
		}
		return j;

	}
}
