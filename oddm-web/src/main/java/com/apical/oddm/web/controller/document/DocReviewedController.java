package com.apical.oddm.web.controller.document;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.core.constant.DocumentConst;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.document.DocumentFacade;
import com.apical.oddm.facade.document.dto.DocumentDTO;
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
@RequestMapping("/docReviewed")
public class DocReviewedController {

	private static final Logger log = LoggerFactory.getLogger(DocReviewedController.class);
	@Autowired
	private DocumentFacade documentFacade;

	@RequestMapping("/manager")
	public String manager() {
		return "/document/docReviewed";
	}

	/**
	 * 分页查询文档基础信息
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<DocumentDTO> dataGrid(PageCommand pageCommand, HttpServletRequest request, String nameMt) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		//BasePage<DocumentDTO> pageList = documentFacade.dataAuditByBizName(2, sessionInfo.getId(), pageCommand);
		BasePage<DocumentDTO> pageList = documentFacade.dataAuditByBizName(DocumentConst.uploaded, sessionInfo.getId(), pageCommand);
		return pageList;
	}

	@RequestMapping("/reviewPage")
	public String editPage(HttpServletRequest request, String docName, String path) {
		request.setAttribute("docName", docName);
		request.setAttribute("path", path);
		return "/document/docReviewedReview";
	}

	/**
	 * 审核操作
	 */
	@RequestMapping("/review")
	@ResponseBody
	public Json reviewed(Integer id, Integer pass, Integer orderId, HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		try {
			documentFacade.reviewed(id, pass, orderId, sessionInfo.getId());
			j.setMsg("审核完毕！！");
			j.setSuccess(true);
		} catch (ServiceException e) {
			j.setMsg("操作失败！");
			j.setSuccess(true);
			// j.setMsg(e.getMessage());
			log.error("系统出错：", e);
		}
		return j;
	}

}
