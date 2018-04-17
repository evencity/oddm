package com.apical.oddm.web.controller.sys;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.apical.oddm.application.sys.UserServiceI;
import com.apical.oddm.core.exception.OddmRuntimeException;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.sys.OrganizationFacade;
import com.apical.oddm.facade.sys.RoleFacade;
import com.apical.oddm.facade.sys.UserFacade;
import com.apical.oddm.facade.sys.command.UserCommand;
import com.apical.oddm.facade.sys.dto.UserDTO;
import com.apical.oddm.facade.websocket.WebSocketMsg;
import com.apical.oddm.facade.websocket.WebSocketSessionUtils;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.pageModel.base.Json;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserFacade userFacade;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private OrganizationFacade organizationFacade;

	@Autowired
	private RoleFacade roleFacade;

	@RequestMapping("/manager")
	public String manager() {
		return "/admin/user";
	}

	/**
	 * 该注解用于将Controller的方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后， 写入到Response对象的body数据区。 使用时机：返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用。
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<UserDTO> dataGrid(UserCommand userCommand, PageCommand pageCommand) { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		return userFacade.dataGrid(userCommand, pageCommand);
	}
	/**用户修改密码控制层屏蔽掉**/
/*
	@RequestMapping("/editPwdPage")
	public String editPwdPage() {
		return "/admin/userEditPwd";
	}

	@RequestMapping("/editUserPwd")
	@ResponseBody
	public Json editUserPwd(HttpServletRequest request, String oldPwd, String password) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		try {
			// System.out.println("sessionInfo.getId()sessionInfo.getId()sessionInfo.getId() :"+sessionInfo.getId());
			boolean editUserPwd = userFacade.editUserPwd(sessionInfo.getId(), oldPwd, password);
			if (editUserPwd) {
				j.setSuccess(true);
				j.setMsg("密码修改成功！");
			} else {
				j.setSuccess(true);
				j.setMsg("密码修改出错！");
			}
		} catch (OddmRuntimeException o) {
			log.error(o.getMessage());
			// j.setSuccess(true);
			j.setMsg(o.getMessage());
		} catch (Exception e) {
			log.error("", e);
			j.setMsg(e.getMessage());
		}
		return j;
	}*/

	@RequestMapping("/editPwdForAdminPage")
	public String editPwdPage(HttpServletRequest request,Integer id) {
		if(id != null){
			UserDTO user = userFacade.get(id);
			request.setAttribute("user", user);
		}
		return "/admin/editPwdForAdminPage";
	}

	@RequestMapping("/editUserPwdForAdmin")
	@ResponseBody
	public Json editUserPwdForAdmin(HttpServletRequest request, UserCommand userCommand) {
		Json j = new Json();
		try {
			String password = HtmlUtils.htmlUnescape(userCommand.getPassword());
			userCommand.setPassword(password);
			log.debug("userCommand:"+userCommand);
			boolean editUserPwd = userFacade.editUserPwdForAdmin(userCommand.getId(), userCommand.getPassword());
			if (editUserPwd) {
				j.setSuccess(true);
				j.setMsg("密码修改成功！");
			} else {
				j.setSuccess(true);
				j.setMsg("密码修改出错！");
			}
		} catch (OddmRuntimeException o) {
			log.error(o.getMessage());
			// j.setSuccess(true);
			j.setMsg(o.getMessage());
		} catch (Exception e) {
			log.error("", e);
			j.setMsg(e.getMessage());
		}
		return j;
	}
	@RequestMapping("/addPage")
	public String addPage() {
		return "/admin/userAdd";
	}

	@RequestMapping("/add")
	@ResponseBody
	public Json add(UserCommand userCommand) {
		// System.out.println(userCommand);
		Json j = new Json();

		try {
			userCommand.setLoginname(userCommand.getLoginname());
			Boolean success = userFacade.add(userCommand);
			if (success) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
				return j;
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！");
				return j;
			}
		} catch (ConstraintViolationException e) {
			j.setMsg("用户名已存在!");
		} catch (Exception e) {
			j.setSuccess(false);
			log.error(e.getMessage());
			j.setMsg(e.getMessage());
			e.printStackTrace();
			System.err.println();
		}
		return j;
	}

	@RequestMapping("/get")
	@ResponseBody
	public UserDTO get(Integer id) {
		return userFacade.get(id);
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		try {
			userFacade.delete(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage());
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/resetPwd")
	@ResponseBody
	public Json resetPwd(Integer id) {
		Json j = new Json();
		try {
			Boolean success = userFacade.updatePassword(id);
			if (success) {
				j.setMsg("重置密码成功！");
				j.setSuccess(true);
				WebSocketSessionUtils.sendMessageToTarget(id + "", new WebSocketMsg("重置密码", "重置密码成功！"));
			} else {
				j.setMsg("重置密码失败！");
				j.setSuccess(true);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		UserDTO u = userFacade.get(id);
		request.setAttribute("user", u);
		// System.out.println("......................"+u);
		return "/admin/userEdit";
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(UserCommand userCommand) {
		Json j = new Json();
		System.out.println("edit userCommand " + userCommand);
		try {
			userFacade.edit(userCommand);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (DataIntegrityViolationException e) {//
			// System.out.println("..........................................................................");
			log.error("系统报错", e);
			j.setMsg("名称已存在！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			log.error("系统报错", e);
		}
		return j;
	}

	@RequestMapping("/resetall")
	@ResponseBody
	public Json resetall() {
		Json j = new Json();
		try {
			BasePage<UserDTO> dataGrid = userFacade.dataGrid(new UserCommand(), new PageCommand());
			List<UserDTO> rows2 = dataGrid.getRows();
			for (UserDTO ud : rows2) {
				// userFacade.updatePassword(ud.getId());
				userService.updateDefaultPassword(ud.getId());
			}
			j.setSuccess(true);
			j.setMsg("重置成功");
		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/listOrderAuditor")
	@ResponseBody
	public List<UserDTO> listOrderAuditor(String username) {
		System.out.println("xxx"+username);
		return userFacade.listOrderAuditor(username);
	}

	@RequestMapping("/getByUsName")
	@ResponseBody
	public Json getByUsName(String username) {
		Json j = new Json();
		try {
			Boolean byUsName = userFacade.getByUsName(username);
			if (byUsName) {
				j.setSuccess(true);
				j.setMsg("用户名存在");
			} else {
				j.setSuccess(false);
				j.setMsg("用户名不存在");
			}

		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/listSeller")
	@ResponseBody
	public List<UserDTO> listSeller(String username) {
		return userFacade.listSeller(username);
	}

	@RequestMapping("/listMerchandiser")
	@ResponseBody
	public List<UserDTO> listMerchandiser(String username) {
		return userFacade.listMerchandiser(username);
	}
}
