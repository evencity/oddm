package com.apical.oddm.facade.document.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.document.DocumentServiceI;
import com.apical.oddm.application.document.DocumentUnreadServiceI;
import com.apical.oddm.application.sys.UserServiceI;
import com.apical.oddm.core.model.document.Document;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.sys.User;
import com.apical.oddm.facade.document.DocumentUnreadFacade;
import com.apical.oddm.facade.pool.ThreadPoolBiz;
import com.apical.oddm.facade.websocket.WebSocketMsg;
import com.apical.oddm.facade.websocket.WebSocketSessionUtils;

/**
 * 添加文档未下载实现
 * 
 * @author 2016-12-14
 */
@Component("documentUnreadFacade")
public class DocumentUnreadFacadeImpl implements DocumentUnreadFacade {

	private static final Logger log = LoggerFactory.getLogger(DocumentUnreadFacadeImpl.class);

	@Autowired
	private UserServiceI userService;

	@Autowired
	private DocumentServiceI documentService;

	@Autowired
	private DocumentUnreadServiceI documentUnreadService;

	@Override
	public void addDocUnreadBatch(int currUserId, int docId) {

		ThreadPoolBiz.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				List<User> users = null;
				try {
					users = userService.listUsersWithDocDownResource();
					// 批量添加未读信息
					documentUnreadService.addDocUnreadBatch(currUserId, users, docId);
				} catch (Exception e) {
					log.error("批量添加文档未读信息错误,文档id:" + docId, e);
				}
				try {
					if (users != null && !users.isEmpty()) {
						Document document = documentService.getDocDetail(docId);
						if (document != null) {
							OrderInfo orderInfo = document.getOrderInfo();
							String path = orderInfo.getProductFactory() + document.getNameMt() + orderInfo.getOrderNo() + "(" + orderInfo.getProductClient() + ")V"
									+ document.getVersion()+".rar";
							for (User u : users) {
								if (currUserId != u.getId()) {
									WebSocketSessionUtils.sendMessageToTarget(u.getId() + "", new WebSocketMsg("文档资料未下载消息", "文档名称：" + path + "。"));
								}
							}
						}
					}
				} catch (Exception e) {
					log.error("发送未下载消息错误", e);
				}
			}
		});
	}

}
