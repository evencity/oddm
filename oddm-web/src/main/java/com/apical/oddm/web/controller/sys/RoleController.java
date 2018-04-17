package com.apical.oddm.web.controller.sys;

import java.util.ArrayList;
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

import com.apical.oddm.application.sys.RoleServiceI;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.base.dto.Tree;
import com.apical.oddm.facade.sys.RoleFacade;
import com.apical.oddm.facade.sys.UserFacade;
import com.apical.oddm.facade.sys.command.RoleCommand;
import com.apical.oddm.facade.sys.dto.ResourceDTO;
import com.apical.oddm.facade.sys.dto.RoleDTO;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.framework.constant.GlobalConstant;
import com.apical.oddm.web.pageModel.base.Json;
import com.apical.oddm.web.pageModel.base.SessionInfo;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleServiceI roleService;
	@Autowired
	private RoleFacade roleFacade;
	@Autowired
	private UserFacade userFacade;

	@RequestMapping("/manager")
	public String manager() {
		return "/admin/role";
	}

	@RequestMapping("/dataGrid")
	@ResponseBody
	public BasePage<RoleDTO> dataGrid(PageCommand pageCommand) {
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		SystemContext.setSort("t." + pageCommand.getSort());
		return roleFacade.dataGrid();
	}

	@RequestMapping("/tree")
	@ResponseBody
	public List<Tree> tree() {

		return roleFacade.roleList();
	}

	@RequestMapping("/addPage")
	public String addPage() {
		return "/admin/roleAdd";
	}

	@RequestMapping("/add")
	@ResponseBody
	public Json add(RoleCommand roleCommand) {
		Json j = new Json();
		try {
			roleCommand.setName(roleCommand.getName().trim());
			Boolean success = roleFacade.add(roleCommand);
			if (success) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！");
			}
		} catch (ConstraintViolationException e) {
			log.error(e.getMessage());
			j.setMsg("角色已存在！");
		} catch (Exception e) {
			log.error(e.getMessage());
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		try {
			roleFacade.delete(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage());
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/get")
	@ResponseBody
	public RoleDTO get(Integer id) {
		return roleFacade.get(id);
	}

	@RequestMapping("/getResource")
	@ResponseBody
	public RoleDTO getResource(Integer id) {

		return roleFacade.getRoleResource(id);
	}

	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		RoleDTO roleDTO = this.get(id);
		request.setAttribute("role", roleDTO);
		return "/admin/roleEdit";
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(RoleCommand roleCommand) {
		Json j = new Json();
		try {
			roleCommand.setName(roleCommand.getName().trim());
			roleFacade.edit(roleCommand);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (DataIntegrityViolationException e) {//
			System.out.println("..........................................................................");
			log.error("系统报错", e);
			j.setMsg("名称已存在！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			log.error("系统报错", e);
		}
		return j;
	}

	@RequestMapping("/grantPage")
	public String grantPage(HttpServletRequest request, Integer id) {
		RoleDTO r = this.get(id);
		request.setAttribute("role", r);
		return "/admin/roleGrant";
	}

	@RequestMapping("/grant")
	@ResponseBody
	public Json grant(RoleCommand roleCommand, HttpServletRequest request) {
		Json j = new Json();
		try {
			roleFacade.grant(roleCommand.getId(), roleCommand.getResourceIds());
			// 更新sessioninfo
			SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
			List<ResourceDTO> userResource = userFacade.getUserResource(sessionInfo.getId());
			List<String> resource = new ArrayList<String>();
			for (ResourceDTO r : userResource) {
				resource.add(r.getUrl());
			}
			sessionInfo.setResourceList(resource);
			j.setMsg("授权成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage());
			j.setMsg(e.getMessage());
		}
		return j;
	}

}
