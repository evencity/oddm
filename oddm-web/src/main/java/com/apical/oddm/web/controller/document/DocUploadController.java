package com.apical.oddm.web.controller.document;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.apical.oddm.core.constant.DocumentConst;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.document.DocumentFacade;
import com.apical.oddm.facade.document.command.DocumentCommand;
import com.apical.oddm.facade.document.dto.DocumentDTO;
import com.apical.oddm.facade.order.OrderInfoFacade;
import com.apical.oddm.facade.websocket.DocCheckCache;
import com.apical.oddm.facade.websocket.WebSocketMsg;
import com.apical.oddm.facade.websocket.WebSocketSessionUtils;
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
@RequestMapping("/docUpload")
public class DocUploadController {

	private static final Logger log = LoggerFactory.getLogger(DocUploadController.class);
	@Autowired
	private DocumentFacade documentFacade;

	@Autowired
	private OrderInfoFacade orderInfoFacade;

	@RequestMapping("/manager")
	public String manager() {
		return "/document/docUpload";
	}

	@RequestMapping("/editPage")
	public String editPage(String docName, HttpServletRequest request) {
		request.setAttribute("documentName", docName);
		return "/document/docUploadEdit";
	}

	/**
	 * 美工
	 * 分页查询文档基础信息//1.文档未发布 4.文档未通过审核 
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<DocumentDTO> dataGrid(DocumentCommand documentCommand, PageCommand pageCommand, HttpServletRequest request) { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		if (documentCommand.getOrderNo() == "") {
			documentCommand.setOrderNo(null);
		}
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		if ("state".equals(pageCommand.getSort())) {
			if ("asc".equals(pageCommand.getOrder())) {
				SystemContext.setOrder("desc");
			} else {
				SystemContext.setOrder("asc");
			}
		}
		SystemContext.setSort("t."+pageCommand.getSort());

		Set<Integer> set = new HashSet<Integer>();
		//set.add(1);
		//set.add(4);
		set.add(DocumentConst.unpublished);
		set.add(DocumentConst.rejected);
		//美工
		//documentCommand.setType(1);
		documentCommand.setType(DocumentConst.artistDoc);
		BasePage<DocumentDTO> pageList = documentFacade.dataGridOrderInfo(documentCommand, set, pageCommand);
		return pageList;
	}
	
	/**
	 * 其他个人
	 * 分页查询文档基础信息//1.文档未发布 4.文档未通过审核
	 */
	@RequestMapping("/dataGridForOtherPerson")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<DocumentDTO> dataGridForOtherPerson(DocumentCommand documentCommand, PageCommand pageCommand, HttpServletRequest request) { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		if (documentCommand.getOrderNo() == "") {
			documentCommand.setOrderNo(null);
		}
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		if ("state".equals(pageCommand.getSort())) {
			if ("asc".equals(pageCommand.getOrder())) {
				SystemContext.setOrder("desc");
			} else {
				SystemContext.setOrder("asc");
			}
		}
		SystemContext.setSort("t."+pageCommand.getSort());

		Set<Integer> set = new HashSet<Integer>();
		//set.add(1);
		//set.add(4);
		set.add(DocumentConst.unpublished);
		set.add(DocumentConst.rejected);
		//其他个人
		//documentCommand.setType(2);
		documentCommand.setType(DocumentConst.otherDoc);
		documentCommand.setUserId(sessionInfo.getId());
		BasePage<DocumentDTO> pageList = documentFacade.dataGridOrderInfo(documentCommand, set, pageCommand);
		return pageList;
	}
	/**
	 * 其他全部
	 * 分页查询文档基础信息//1.文档未发布 4.文档未通过审核
	 */
	@RequestMapping("/dataGridForOtherAll")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<DocumentDTO> dataGridForOtherAll(DocumentCommand documentCommand, PageCommand pageCommand, HttpServletRequest request) { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		if (documentCommand.getOrderNo() == "") {
			documentCommand.setOrderNo(null);
		}
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		if ("state".equals(pageCommand.getSort())) {
			if ("asc".equals(pageCommand.getOrder())) {
				SystemContext.setOrder("desc");
			} else {
				SystemContext.setOrder("asc");
			}
		}
		SystemContext.setSort("t."+pageCommand.getSort());

		Set<Integer> set = new HashSet<Integer>();
		//set.add(1);
		//set.add(4);
		set.add(DocumentConst.unpublished);
		set.add(DocumentConst.rejected);
		//其他
		//documentCommand.setType(2);
		documentCommand.setType(DocumentConst.otherDoc);
		BasePage<DocumentDTO> pageList = documentFacade.dataGridOrderInfo(documentCommand, set, pageCommand);
		return pageList;
	}
	/**
	 * 包含以上三种
	 * 分页查询文档基础信息//1.文档未发布 4.文档未通过审核
	 */
	@RequestMapping("/dataGridAll")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<DocumentDTO> dataGridAll(DocumentCommand documentCommand, PageCommand pageCommand, HttpServletRequest request) { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		if (documentCommand.getOrderNo() == "") {
			documentCommand.setOrderNo(null);
		}
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		if ("state".equals(pageCommand.getSort())) {
			if ("asc".equals(pageCommand.getOrder())) {
				SystemContext.setOrder("desc");
			} else {
				SystemContext.setOrder("asc");
			}
		}
		SystemContext.setSort("t."+pageCommand.getSort());

		Set<Integer> set = new HashSet<Integer>();
		//set.add(1);
		//set.add(4);
		set.add(DocumentConst.unpublished);
		set.add(DocumentConst.rejected);
		BasePage<DocumentDTO> pageList = documentFacade.dataGridOrderInfo(documentCommand, set, pageCommand);
		return pageList;
	}

	/*@RequestMapping("/addFile")
	@ResponseBody
	public Json addFile(HttpServletRequest request, DocumentCommand documentCommand) throws IllegalStateException, IOException {
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		//documentCommand.setUploadtime(new Date());
		if(documentCommand.getVersion() != null){
			Integer version = documentCommand.getVersion();
			version += 1;
			documentCommand.setVersion(version);
		}
		//documentCommand.setState(2);// 此处需要确定文档状态
		documentCommand.setState(DocumentConst.uploaded);
		documentCommand.setUserId(sessionInfo.getId());
		//特殊字符处理
		String path = HtmlUtils.htmlUnescape(documentCommand.getPath());
		documentCommand.setPath(path);
		try {
			documentFacade.uploadFile(documentCommand);
			j.setSuccess(true);
			j.setMsg("添加成功！");
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
	public Json addFile(HttpServletRequest request,@RequestBody DocumentCommand documentCommand) throws IllegalStateException, IOException {
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		//documentCommand.setUploadtime(new Date());
		if(documentCommand.getVersion() != null){
			Integer version = documentCommand.getVersion();
			version += 1;
			documentCommand.setVersion(version);
		}
		//documentCommand.setState(2);// 此处需要确定文档状态
		documentCommand.setState(DocumentConst.uploaded);
		documentCommand.setUserId(sessionInfo.getId());
		//特殊字符处理
		String path = HtmlUtils.htmlUnescape(documentCommand.getPath());
		documentCommand.setPath(path);
		try {
			documentFacade.uploadFile(documentCommand);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			// TODO: handle exception
			j.setSuccess(false);
			j.setMsg("添加失败！");
			log.error("addFile出错", e);
		}
		return j;
	}
	
	/**
	 * 上传成功后添加文档审核提示缓存
	 * @param sellerId
	 * @param msg
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/uploadDocTips", method=RequestMethod.POST)
	@ResponseBody
	public Json uploadDocTips(Integer sellerId, String msg, HttpSession session) {
		//SessionInfo sessionInfo = (SessionInfo) session.getAttribute(GlobalConstant.SESSION_INFO);
		/*WebSocketMsg webMsg = new WebSocketMsg();
		webMsg.setBody(msg);
		webMsg.setTitle("提示");
		webMsg.setCmd("1111");//对于1111的信息不进行删除缓存
*/		WebSocketSessionUtils.sendMessageToTarget(sellerId+ "", new WebSocketMsg("提示","文档 "+msg+ "已上传，请及时审核！"));
		return new Json();
	}
	
	/**	
	 * 删除文档审核提示缓存信息
	 * @param selleId
	 * @param fileName
	 * @return
	 */
	@RequestMapping(value="/removeDocCache", method=RequestMethod.POST)
	public void removeDocCache(String sellerId, String fileName) {
		if(sellerId != null && fileName != null){
			DocCheckCache.removeCache(fileName, sellerId);
		}
	}

	
}
