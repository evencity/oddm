package com.apical.oddm.web.controller.sysv2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.application.sys.ResourceServiceI;
import com.apical.oddm.application.sys.UserServiceI;
import com.apical.oddm.core.model.sys.Resource;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.framework.constant.GlobalConstant;
import com.apical.oddm.web.pageModel.base.SessionInfo;
import com.apical.oddm.web.pageModel.base.Tree;

@Controller
@RequestMapping("/resourceV2")
public class ResourceControllerV2 extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(ResourceControllerV2.class);

	@Autowired
	private ResourceServiceI resourceService;

	@Autowired
	private UserServiceI userService;
	
/*	@RequestMapping("/manager")
	public String manager() {
		return "/admin/resource";
	}*/

	@RequestMapping("/tree")
	@ResponseBody
	public List<Tree> tree(HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(GlobalConstant.SESSION_INFO);
		List<Tree> lt = new ArrayList<Tree>();
		try {
			List<Resource> l = userService.getUserResource(sessionInfo.getId());
			if ((l != null) && (l.size() > 0)) {
				for (Resource r : l) {
					if (r.getResourcetype() == 1) {//只选菜单，不选按钮（1-菜单；2-按钮）
						Tree tree = new Tree();
						tree.setId(r.getId()+"");
						tree.setIconCls("icon-folder");//一级菜单图标
						if (r.getResource() != null) {
							tree.setPid(r.getResource().getId()+"");//资源id
							tree.setIconCls("icon-btn"); //二级菜单图标
							Map<String, Object> attr = new HashMap<String, Object>();
							attr.put("url", r.getUrl()); //菜单连接
							tree.setAttributes(attr); //只需二级菜单做链接
						} else {
							tree.setState("closed");
						}
						tree.setText(r.getName()); //菜单名
						lt.add(tree);
					}
				}
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return lt;
	}

	/*	@RequestMapping("/allTree")
	@ResponseBody
	public List<Tree> allTree(boolean flag) {// true获取全部资源,false只获取菜单资源
		List<Resource> l = resourceService.treeGrid();
		List<Tree> lt = new ArrayList<Tree>();
		if ((l != null) && (l.size() > 0)) {
			for (Resource r : l) {
				Tree tree = new Tree();
				tree.setId(r.getId().toString());
				if (r.getResource() != null) {
					tree.setPid(r.getResource().getId().toString());
				}
				tree.setText(r.getName());
				tree.setIconCls(r.getIcon());
				Map<String, Object> attr = new HashMap<String, Object>();
				attr.put("url", r.getUrl());
				tree.setAttributes(attr);
				lt.add(tree);
			}
		}
		return lt;
	}

	@RequestMapping("/treeGrid")
	@ResponseBody
	public List<ResourceDto> treeGrid() {
		List<ResourceDto> lr = new ArrayList<ResourceDto>();
		List<Resource> l = resourceService.treeGrid();
		if ((l != null) && (l.size() > 0)) {
			for (Resource t : l) {
				//System.out.println("排序 "+ t.getSeq());
				ResourceDto r = new ResourceDto();
				BeanUtils.copyProperties(t, r);
				r.setCstate(t.getState());
				if (t.getResource() != null) {
					r.setPid(t.getResource().getId());
				}
				r.setIconCls(t.getIcon());
				lr.add(r);
			}
		}
		return lr;
	}

	@RequestMapping("/get")
	@ResponseBody
	public ResourceDto get(Integer id) {
		Resource t = resourceService.get(id);
		ResourceDto r = new ResourceDto();
		BeanUtils.copyProperties(t, r);
		r.setCstate(t.getState());
		if (t.getResource() != null) {
			r.setPid(t.getResource().getId());
			r.setPname(t.getResource().getName());
		}
		return r;
	}

	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		ResourceDto r = this.get(id);
		request.setAttribute("resource", r);
		return "/admin/resourceEdit";
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ResourceDto r) throws InterruptedException {
		Json j = new Json();
		try {
			Resource t = resourceService.get(r.getId());
			t.setDescription(r.getDescription());
			t.setIcon(r.getIcon());
			t.setName(r.getName());
			if ((r.getPid() != null) && !"".equals(r.getPid())) {
				t.setResource(resourceService.get(r.getPid()));
			}
			t.setResourcetype(r.getResourcetype());
			t.setSeq(r.getSeq());
			t.setState(r.getCstate());
			t.setUrl(r.getUrl());
			t.setDescription(r.getDescription());
			resourceService.edit(t);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		try {
			resourceService.delete(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/addPage")
	public String addPage() {
		return "/admin/resourceAdd";
	}

	@RequestMapping("/add")
	@ResponseBody
	public Json add(ResourceDto r) {
		Json j = new Json();
		try {
			Resource t = new Resource();
			t.setDescription(r.getDescription());
			t.setIcon(r.getIcon());
			t.setName(r.getName());
			if ((r.getPid() != null) && !"".equals(r.getPid())) {
				t.setResource(resourceService.get(r.getPid()));
			}
			t.setResourcetype(r.getResourcetype());
			t.setSeq(r.getSeq());
			t.setState(r.getCstate());
			t.setUrl(r.getUrl());
			resourceService.add(t);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}*/

}
