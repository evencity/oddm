package com.apical.oddm.web.controller.sysv2;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.application.sys.ResourceServiceI;
import com.apical.oddm.application.sys.SysConfigServiceI;
import com.apical.oddm.application.sys.UserServiceI;
import com.apical.oddm.core.model.sys.Resource;
import com.apical.oddm.core.model.sys.Role;
import com.apical.oddm.core.model.sys.SysConfig;
import com.apical.oddm.core.model.sys.User;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.framework.constant.GlobalConstant;
import com.apical.oddm.web.pageModel.base.Json;
import com.apical.oddm.web.pageModel.base.SessionInfo;

@Controller
@RequestMapping("/adminV2")
public class IndexControllerV2 extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(IndexControllerV2.class);

	@Autowired
	private UserServiceI userService;

	@Autowired
	private ResourceServiceI resourceService;

	@Autowired
	private SysConfigServiceI sysConfigService;
	
	@RequestMapping("/index") //入口，检查session
	public String index(HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		if ((sessionInfo != null) && (sessionInfo.getId() != null)) {
			return "/indexV2";
		}
		return "/loginV2";
	}

	@ResponseBody
	@RequestMapping("/login")
	public Json login(User user, HttpSession httpSession) {
		Json j = new Json();
		try {
			user = userService.login(user);
			if (user != null) {
				if (user.getState() == 2) {
					j.setSuccess(false);
					j.setMsg("此账户已冻结！");
					return j;
				}
				j.setSuccess(true);
				j.setMsg("登陆成功！");
				
				SessionInfo sessionInfo = new SessionInfo();
				sessionInfo.setId(user.getId());
				sessionInfo.setLoginname(user.getLoginname());
				sessionInfo.setName(user.getUsername());
				// user.setIp(IpUtil.getIpAddr(getRequest()));
				List<Resource> userResource = userService.getUserResource(user.getId());
				List<String> resource = new LinkedList<String>();
				for (Resource r : userResource) {
					resource.add(r.getUrl());
				}
				sessionInfo.setResourceList(resource);
				List<Resource> treeGrid = resourceService.treeGrid();
				resource = new LinkedList<String>();
				for (Resource r : treeGrid) {
					resource.add(r.getUrl());
				}
				sessionInfo.setResourceAllList(resource);
				
				//处理角色，实现订单客户名称和编码显示判断
				Set<Role> roles = user.getRoles();
				String roleNames = "";
				if (roles != null && !roles.isEmpty()) {
					for(Role role: roles){
						roleNames += role.getName();
					}
					if(roleNames != null && !"".equals(roleNames)){
						SysConfig sysConfig = sysConfigService.get("order_clientnameorcode");
						if(sysConfig != null && sysConfig.getValue() != null){
							String[] values = sysConfig.getValue().split("\\|");
							for(String string : values){
								if(roleNames.contains(string)){
									sessionInfo.setHasRoles(true);
									break;
								}
							}
							if(sessionInfo.getHasRoles() == null){
								sessionInfo.setHasRoles(false);
							}
						}else {
							sessionInfo.setHasRoles(false);
						}
					}else {
						sessionInfo.setHasRoles(false);
					}
				}else {
					sessionInfo.setHasRoles(false);
				}
				
				httpSession.setAttribute(GlobalConstant.SESSION_INFO, sessionInfo);
			} else {
				j.setMsg("用户名或密码错误！");
			}
		} catch (Exception e) {
			log.error("登录异常", e);
			j.setSuccess(false);
			j.setMsg("服务器异常，请联系管理员！");
		}
		return j;
	}

	@ResponseBody
	@RequestMapping("/logout")
	public Json logout(HttpSession httpSession) {
		Json j = new Json();
		if (httpSession != null) {
			httpSession.setAttribute(GlobalConstant.SESSION_INFO, null);
			httpSession.invalidate();
			httpSession = null;
		}
		j.setSuccess(true);
		j.setMsg("注销成功！");
		return j;
	}

}
