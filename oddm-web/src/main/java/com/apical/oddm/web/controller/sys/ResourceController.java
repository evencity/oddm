package com.apical.oddm.web.controller.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.facade.sys.ResourceFacade;
import com.apical.oddm.facade.sys.UserFacade;
import com.apical.oddm.facade.sys.command.ResourceCommand;
import com.apical.oddm.facade.sys.dto.ResourceDTO;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.framework.constant.GlobalConstant;
import com.apical.oddm.web.pageModel.base.Json;
import com.apical.oddm.web.pageModel.base.SessionInfo;

@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(ResourceController.class);

	@Autowired
	private ResourceFacade resourceFacade;

	@Autowired
	private UserFacade userFacade;

	@RequestMapping("/manager")
	public String manager() {
		return "/admin/resource";
	}

	@RequestMapping("/tree")
	@ResponseBody
	public List<ResourceDTO> tree(HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(GlobalConstant.SESSION_INFO);
		if (sessionInfo == null || sessionInfo.getId() == null) return null;
		return userFacade.getUserResource(sessionInfo.getId());
	}

	// 获取菜单
	@RequestMapping("/allTree")
	@ResponseBody
	public List<ResourceDTO> allTree() {// true获取全部资源,false只获取菜单资源
		List<ResourceDTO> l = resourceFacade.menuList();
		return l;
	}

	// 获取全部资源
	@RequestMapping("/treeGrid")
	@ResponseBody
	public List<ResourceDTO> treeGrid() {
		return resourceFacade.treeGrid();
	}

	@RequestMapping("/get")
	@ResponseBody
	public ResourceDTO get(Integer id) {

		return resourceFacade.get(id);
	}

	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		ResourceDTO r = resourceFacade.get(id);
		request.setAttribute("resource", r);
		return "/admin/resourceEdit";
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ResourceCommand resourceCommand) throws InterruptedException {
		Json j = new Json();
		try {
			resourceCommand.setName(resourceCommand.getName().trim());
			resourceFacade.edit(resourceCommand);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (DataIntegrityViolationException e) {//
			//System.out.println("..........................................................................");
			log.error("系统报错", e);
			j.setMsg("资源路径已存在！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			log.error("系统报错", e);
		}
		return j;
	}

	/*
	 * @RequestMapping("/delete")
	 * 
	 * @ResponseBody public Json delete(Integer id) { Json j = new Json(); try { resourceFacade.delete(id); j.setMsg("删除成功！"); j.setSuccess(true); } catch (Exception e) {
	 * j.setMsg(e.getMessage()); } return j; }
	 */

	@RequestMapping("/addPage")
	public String addPage() {
		return "/admin/resourceAdd";
	}

	@RequestMapping("/add")
	@ResponseBody
	public Json add(ResourceCommand resourceCommand) {
		Json j = new Json();
		try {
			resourceCommand.setName(resourceCommand.getName().trim());
			Boolean success = resourceFacade.add(resourceCommand);
			if (success) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！");
			}
		} catch (ConstraintViolationException e) {
			j.setMsg("资源已存在！");
		} catch (Exception e) {
			log.error("", e);
			e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}

}
