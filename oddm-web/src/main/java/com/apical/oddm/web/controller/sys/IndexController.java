package com.apical.oddm.web.controller.sys;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.application.sys.SysConfigServiceI;
import com.apical.oddm.core.model.sys.SysConfig;
import com.apical.oddm.facade.sys.ResourceFacade;
import com.apical.oddm.facade.sys.UserFacade;
import com.apical.oddm.facade.sys.command.UserLoginCommand;
import com.apical.oddm.facade.sys.dto.ResourceDTO;
import com.apical.oddm.facade.sys.dto.UserDTO;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.framework.constant.GlobalConstant;
import com.apical.oddm.web.pageModel.base.Json;
import com.apical.oddm.web.pageModel.base.SessionInfo;

@Controller
@RequestMapping("/admin")
public class IndexController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private UserFacade userInfoFacade;

	@Autowired
	private ResourceFacade resourceInfoFacade;

	@Autowired
	private SysConfigServiceI sysConfigService;

	@RequestMapping("/index")
	// 入口，检查session
	public String index(HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		if ((sessionInfo != null) && (sessionInfo.getId() != null)) {
			return "/index";
		}
		return "/login";
	}

	@ResponseBody
	@RequestMapping("/login")
	public Json login(UserLoginCommand userLoginCommand, HttpServletRequest request, HttpSession session) {
		log.debug("session: "+session.toString());
		Object logintimes = session.getAttribute("logintimes");
		if (logintimes == null) {
			session.setAttribute("logintimes", 1);
		} else {
			int i = (int) logintimes; i = i+1;
			session.setAttribute("logintimes", i);
			if (i > 50) {
				log.error("发现暴力破解:"+request.getRemoteAddr());
				return null;
			}
		}
		Json j = new Json();
		UserDTO userDTO = new UserDTO();
		try {
			userDTO = userInfoFacade.login(userLoginCommand);
			if (userDTO != null) {
				if (userDTO.getState() == 2) {
					j.setSuccess(false);
					j.setMsg("此账户已冻结！");
					return j;
				}
				j.setSuccess(true);
				j.setMsg("登陆成功！");

				SessionInfo sessionInfo = new SessionInfo();
				sessionInfo.setId(userDTO.getId());
				sessionInfo.setLoginname(userDTO.getLoginname());
				sessionInfo.setName(userDTO.getUsername());
				sessionInfo.setSex(userDTO.getSex());
				// 获取角色
				UserDTO userDTO2 = userInfoFacade.get(userDTO.getId());
				String roleNames = userDTO2.getRoleNames();

				if (roleNames != null && !"".equals(roleNames)) {
					SysConfig sysConfig = sysConfigService.get("order_clientnameorcode");
					if (sysConfig != null && sysConfig.getValue() != null) {
						String[] values = sysConfig.getValue().split("\\|");
						for (String string : values) {
							if (roleNames.contains(string)) {
								sessionInfo.setHasRoles(true);
								break;
							}
						}
						if (sessionInfo.getHasRoles() == null) {
							sessionInfo.setHasRoles(false);
						}
					} else {
						sessionInfo.setHasRoles(false);
					}
				} else {

					sessionInfo.setHasRoles(false);
				}
				List<ResourceDTO> userResource = userInfoFacade.getUserResource(userDTO.getId());

				List<String> resource = new ArrayList<String>();
				for (ResourceDTO r : userResource) {
					resource.add(r.getUrl());
				}
				sessionInfo.setResourceList(resource);

				List<ResourceDTO> treeGrid = resourceInfoFacade.treeGrid();
				resource = new LinkedList<String>();
				for (ResourceDTO r : treeGrid) {
					resource.add(r.getUrl());
				}
				sessionInfo.setResourceAllList(resource);
				session.setAttribute(GlobalConstant.SESSION_INFO, sessionInfo);
				//System.out.println(sessionInfo);
			} else {
				j.setMsg("用户名或密码错误！");
			}
		} catch (Exception e) {
			log.error("登录异常", e);
			j.setSuccess(false);
			j.setMsg("登录异常，请联系管理员！");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping("/logout")
	public Json logout(HttpSession session) {
		Json j = new Json();
		if (session != null) {
			session.setAttribute(GlobalConstant.SESSION_INFO, null);
			session.invalidate();
			session = null;
		}
		j.setSuccess(true);
		j.setMsg("注销成功！");
		return j;
	}
}
