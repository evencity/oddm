package com.apical.oddm.web.controller.sysmaint;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.WebSocketSession;

import com.apical.oddm.facade.websocket.WebSocketSessionUtils;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.pageModel.base.SessionInfo;

@Controller
@RequestMapping("/monitor")
public class MonitorController extends BaseController {
	
	//private static final Logger log = LoggerFactory.getLogger(MonitorController.class);

	@RequestMapping("/manager")
	public String manager() {
		return "/sysmaint/monitor";
	}

	 /**
	 * @return 用户在线数量
	 */
	@RequestMapping("/usersOnline")
	@ResponseBody
	public Monitor usersOnline(){
		Monitor monitor = new Monitor();
		monitor.setLoginUser(WebSocketSessionUtils.getOnlineCount());
		monitor.setLoginUsers(WebSocketSessionUtils.getOnlineCountAll());
		Set<WebSocketSession> allSession = WebSocketSessionUtils.getAllSession();
		Map<String, Integer> userNameMap = new HashMap<String, Integer>();
		for (WebSocketSession session : allSession) {
			// System.out.println(""+session.getRemoteAddress().getAddress().getHostAddress()); //ip地址
			SessionInfo sessionInfo = (SessionInfo) MapUtils.getObject(session.getAttributes(),"sessionInfo");
			
			if (userNameMap.get(sessionInfo.getName()) == null) {
				userNameMap.put(sessionInfo.getName(), 1);
			} else {
				userNameMap.put(sessionInfo.getName(), userNameMap.get(sessionInfo.getName())+1);
			}
		}
		monitor.setUserNameMap(userNameMap);
		return monitor;
	}

	
}
