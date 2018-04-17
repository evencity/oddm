package com.apical.oddm.web.controller.document;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.core.constant.DocumentConst;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.document.DocumentFacade;
import com.apical.oddm.facade.document.command.DocumentCommand;
import com.apical.oddm.facade.document.dto.DocumentDTO;
import com.apical.oddm.web.framework.constant.GlobalConstant;
import com.apical.oddm.web.pageModel.base.SessionInfo;

/**
 * @description 类的描述：
 * @author 作者 : zhuzh
 * @date 创建时间：2016年11月10日 上午9:37:10
 * @version 1.0
 */

@Controller
@RequestMapping("/docDownload")
public class DocDownloadController {

	@Autowired
	private DocumentFacade documentFacade;

	@RequestMapping("/manager")
	public String manager() {
		return "/document/docDownload";
	}

	/**
	 * 分页查询文档基础信息
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<DocumentDTO> dataGrid(DocumentCommand documentCommand, PageCommand pageCommand, HttpServletRequest request) { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		if (documentCommand.getOrderNo() == "") {
			documentCommand.setOrderNo(null);
		}
		Set<Integer> set = new HashSet<Integer>();
		//set.add(3);
		set.add(DocumentConst.approved);
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		SystemContext.setCurrUserId(sessionInfo.getId());
		BasePage<DocumentDTO> pageList = documentFacade.dataGrid(documentCommand, set, pageCommand);
		return pageList;
	}

}
