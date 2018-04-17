package com.apical.oddm.web.controller.websocket;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.apical.oddm.facade.websocket.DocCheckCache;
import com.apical.oddm.facade.websocket.MsgCache;
import com.apical.oddm.facade.websocket.WebSocketMsg;
import com.apical.oddm.facade.websocket.WebSocketSessionUtils;

/**
 * handler，处理网页请求
 * @author lgx
 * 2016-11-17
 */
public class AnalysisWebSocketHandler extends TextWebSocketHandler{
	private static final Logger log = LoggerFactory.getLogger(AnalysisWebSocketHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        log.debug("afterConnectionEstablished: "+session);
        String userId = MapUtils.getString(session.getAttributes(),"userId");
		//System.err.println("AnalysisWebSocketHandler"+session.getAttributes());
        //{sessionInfo=SessionInfo [id=61, loginname=admin, name=admin, ip=null, hasRoles=true, resourceList=[/encase/addEncasePage, /documentCommon/add, /product/edit, /orderFollowup/dataGridAll, /materialBare/add, /order/showOrderFollowup, /materialFitting/dataGird, /orderFollowup/edit, /bomMaterial/del, /documentCommon/edit, /document/dataOrderInfoGrid, /materialBare/delete, /materialFitting/edit, /materialFollowup/dataGrid, /order/edit, /manufacture/edit, /productType/add, /bom/delete, /document/download, /materialPacking/dataGrid, /docUpload/dataGrid, /materialFollowup/edit, /order/dataGrid, /manufacture/add, /productType/delete, /document/delete, /user/manager, /materialPacking/edit, /documentCommon/dataGrid, /product/add, /materialBare/dataGrid, /order/showBom, /encase/manager, /orderFollowup/add, /sysNotice/manager, /order/dataGridAll, /bomMaterial/edit, /document/dataOrderInfoGridAll, /eccase/view, /documentCommon/download, /role/grant, /product/delete, /materialBare/edit, /order/addNeworder, /materialFitting/add, /order/add, /docReviewed/dataGrid, /documentCommon/delete, /productType/dataGrid, /bom/find, /document/upload, /materialFitting/delete, /materialFollowup/add, /order/review, /manufacture/delete, /user/resetPwd, /orderReview/dataGrid, /docDownload/dataGrid, /productType/edit, /bom/edit, /document/add, /materialPacking/add, /product/dataGird, /materialFollowup/delete, /materialPacking/adds, /materialBare/manager, /order/showDocument, /manufacture/find, /orderFollowup/dataGrid, /order/managerAll, /bomMaterial/addPage, /document/managerAll, /materialPacking/delete, /style/images/icons/ORDER.png, /order/manager, /docReviewed/review, /role/manager, /docDownload/download, /document/manager, /sysConfig/manager, /materialPacking/manager, /docReviewed/manager, /orderReview/manager, /materialFitting/manager, /document/manage, /organization/manager, /resource/manager, /style/images/icons/DOC.png, /product/manager, /orderFollowup/managerAll, /docUpload/manager, /order/changeMerchandiser, /productType/manager, /monitor/manager, /orderFollowup/manager, /order/changeSeller, /style/images/icons/Fitting.png, /documentCommon/manager, /docDownload/manager, /style/images/icons/Model.png], resourceAllList=[/bom/find, /bom/delete, /bom/edit, /bomMaterial/addPage, /bomMaterial/edit, /bomMaterial/del, /materialPacking/adds, /order/managerAll, /order/dataGridAll, /order/add, /order/edit, /order/review, /order/dataGrid, /order/showDocument, /order/showBom, /order/showOrderFollowup, /order/addNeworder, /orderReview/dataGrid, /orderFollowup/dataGrid, /orderFollowup/add, /orderFollowup/edit, /manufacture/edit, /manufacture/delete, /manufacture/add, /manufacture/find, /materialFollowup/dataGrid, /materialFollowup/add, /materialFollowup/edit, /materialFollowup/delete, /user/manager, /orderFollowup/dataGridAll, /user/resetPwd, /role/grant, /sysNotice/manager, /document/dataOrderInfoGrid, /document/upload, /document/download, /document/add, /document/delete, /document/managerAll, /document/dataOrderInfoGridAll, /docReviewed/dataGrid, /materialFollowup/dataGridAll, /bomMaterialContact/add, /bomMaterialContact/edit, /bomMaterialContact/delete, /orderFollowup/excel, /docUpload/dataGrid, /documentCommon/dataGrid, /documentCommon/add, /documentCommon/download, /documentCommon/edit, /encase/manager, /documentCommon/delete, /encase/addEncasePage, /eccase/view, /docDownload/dataGrid, /materialBare/manager, /materialBare/dataGrid, /materialBare/add, /materialBare/edit, /materialBare/delete, /materialPacking/dataGrid, /materialPacking/add, /materialPacking/edit, /materialPacking/delete, /materialFitting/dataGird, /materialFitting/add, /materialFitting/edit, /materialFitting/delete, /product/dataGird, /product/add, /product/edit, /product/delete, /productType/dataGrid, /productType/add, /productType/edit, /productType/delete, /style/images/icons/ORDER.png, /order/manager, /role/manager, /sysConfig/manager, /document/manager, /docReviewed/review, /docDownload/download, /materialPacking/manager, /document/manage, /orderReview/manager, /organization/manager, /docReviewed/manager, /materialFitting/manager, /style/images/icons/DOC.png, /orderFollowup/managerAll, /resource/manager, /docUpload/manager, /product/manager, /style/images/icons/Fitting.png, /order/changeMerchandiser, /order/changeSeller, /orderFollowup/manager]], HTTP.SESSION.ID=613D96DDF90C312D1847466F2B3CC7EF, userId=61}
        //记录连接的session
        WebSocketSessionUtils.add(userId,session);
    }
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        //System.out.println("handleTextMessage");
        String userId = MapUtils.getString(session.getAttributes(),"userId");
       // System.out.println("userIduserId:"+userId);
       /* String partnerInfo = MapUtils.getString(session.getAttributes(),"partnerInfo");//客户端信息pojo
        if (null != partnerInfo){
            //TODO 进行配对
        }else{*/
        //TODO 处理其他消息
        if ("1001".equals(message.getPayload())) {
        	WebSocketMsg msg = new WebSocketMsg("1001", "连接建立", "消息推送连接建立成功！");
        	//TextMessage returnMessage = new TextMessage("Hi:"+userId+"..your message:"+message.getPayload()+" I have received,now I tell you");
        	session.sendMessage(new TextMessage(msg.toString()));//欢迎登陆
        	
        	Map<WebSocketMsg, Long> setMsg = MsgCache.get(userId); //发送上一次缓存数据
        	if (setMsg != null && !setMsg.isEmpty()) {
            	MsgCache.removeAll(userId);
            	
        		for (WebSocketMsg m : setMsg.keySet()) {
                	WebSocketSessionUtils.sendMessageToTarget(userId,m);
        		}
        	}
        	//DocCheckCache.sendDocMsg(userId);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        log.error("", exception);
        exception.printStackTrace();
        String userId = MapUtils.getString(session.getAttributes(),"userId");
        WebSocketSessionUtils.remove(userId, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        log.debug("afterConnectionClosed");
        String userId = MapUtils.getString(session.getAttributes(),"userId");
        WebSocketSessionUtils.remove(userId, session);
    }
}
