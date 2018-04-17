package com.apical.oddm.web.controller.sys;

import java.util.LinkedList;
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

import com.apical.oddm.application.sys.OrganizationServiceI;
import com.apical.oddm.core.model.sys.Organization;
import com.apical.oddm.core.model.sys.User;
import com.apical.oddm.facade.sys.OrganizationFacade;
import com.apical.oddm.facade.sys.command.OrganizationCommand;
import com.apical.oddm.facade.sys.dto.OrganizationDTO;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.pageModel.base.Json;
import com.apical.oddm.web.pageModel.base.Tree;

@Controller
@RequestMapping("/organization")
public class OrganizationController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(OrganizationController.class);
	@Autowired
	private OrganizationServiceI organizationService;
	@Autowired
	private OrganizationFacade organizationFacade;

	@RequestMapping("/manager")
	public String manager() {
		return "/admin/organization";
	}

	@RequestMapping("/treeGrid")
	@ResponseBody
	public List<OrganizationDTO> treeGrid() {
		return organizationFacade.treeGrid();
	}

	// 公司列表
	@RequestMapping("/companyList")
	@ResponseBody
	public List<OrganizationDTO> companyList() {
		return organizationFacade.companyList();
	}

	// 获取部门用户列表
	@RequestMapping("/userDept")
	@ResponseBody
	public List<Tree> userDept() {
		List<Tree> listTree = new LinkedList<>();
		List<Tree> listChd = null;
		Tree tree = null;
		Tree treeChd = null;
		try {
			List<Organization> treeGrid = organizationService.treeGrid();
			if (treeGrid != null && !treeGrid.isEmpty()) {
				for (Organization o : treeGrid) {
					tree = new Tree();
					tree.setId(o.getId() + "");
					if (o.getOrganization() != null) {
						tree.setIconCls("icon-btn");
						tree.setPid(o.getOrganization().getId() + "");
						if (o.getUsers() != null && !o.getUsers().isEmpty()) {
							listChd = new LinkedList<>();
							tree.setState("closed");
							for (User u : o.getUsers()) {
								treeChd = new Tree();
								treeChd.setText(u.getUsername());
								treeChd.setPid(o.getId() + "");
								treeChd.setId(u.getId() + "");
								if (u.getSex() == 3) {// 女
									treeChd.setIconCls("icon-female");
								} else {
									treeChd.setIconCls("icon-male2");
								}
								listChd.add(treeChd);
							}
							tree.setChildren(listChd);
						}
					} else {
						tree.setIconCls(o.getIcon());
					}
					tree.setText(o.getName());
					listTree.add(tree);
				}
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return listTree;
	}

	// 获取部门用户列表
	/*
	 * @RequestMapping("/orgTree")
	 * 
	 * @ResponseBody public List<Tree> orgTree() { List<Organization> treeGrid2 = organizationService.treeGrid2(); List<Tree> usersList = new LinkedList<Tree> (); List<Tree>
	 * children = null; Tree tree = null; Tree treeCd= null; for (Organization org : treeGrid2) { //if (org.getOrganization() != null) {
	 * System.out.println("usersListusersListusersList \n"+org.getName()); tree = new Tree(); tree.setId(org.getId()+""); tree.setIconCls(org.getIcon());
	 * tree.setText(org.getName()); for (Organization o : org.getOrganizations()) { children = new LinkedList<Tree> (); treeCd= new Tree(); treeCd.setText(o.getName());
	 * treeCd.setPid(org.getId()+""); treeCd.setId(o.getId()+""); treeCd.setIconCls(o.getIcon()); children.add(treeCd); } tree.setChildren(children); usersList.add(tree); //} }
	 * return usersList; }
	 */
	// 获取部门列表（不包含公司）
	@RequestMapping("/dept")
	@ResponseBody
	public List<OrganizationDTO> dept() {
		return organizationFacade.treeGrid();
	}

	@RequestMapping("/addPage")
	public String addPage() {
		return "/admin/organizationAdd";
	}

	@RequestMapping("/add")
	@ResponseBody
	public Json add(OrganizationCommand organizationCommand) {
		Json j = new Json();
		try {
			organizationCommand.setName(organizationCommand.getName().trim());
			Boolean success = organizationFacade.add(organizationCommand);
			if (success) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！");
			}
		} catch (ConstraintViolationException e) {
			log.error(e.getMessage());
			j.setMsg("部门已存在！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/get")
	@ResponseBody
	public OrganizationDTO get(Integer id) {
		return organizationFacade.get(id);
	}

	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		OrganizationDTO organizationDTO = organizationFacade.get(id);
		request.setAttribute("organization", organizationDTO);
		return "/admin/organizationEdit";
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(OrganizationCommand organizationCommand) throws InterruptedException {
		Json j = new Json();
		try {
			organizationCommand.setName(organizationCommand.getName().trim());
			organizationFacade.edit(organizationCommand);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			log.error("", e);
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		try {
			Organization organization = organizationService.getChildren(id);
			if (organization.getOrganizations() != null && !organization.getOrganizations().isEmpty()) {
				j.setMsg("请先删除子节点！");
				j.setSuccess(false);
			} else {
				organizationService.delete(id);
				j.setMsg("删除成功！");
				j.setSuccess(true);
			}
		} catch (ServiceException e) {
			j.setMsg("删除失败" + e.getMessage());
			j.setSuccess(false);
		}
		return j;
	}
}
