package com.apical.oddm.facade.websocket;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * 缓存session、发送消息工具类
 * @author lgx
 * 2016-11-17
 */
public class WebSocketSessionUtils {
	private static final Logger log = LoggerFactory.getLogger(WebSocketSessionUtils.class);

    //private static Map<String, WebSocketSession> clients = new ConcurrentHashMap<String, WebSocketSession>();
    //单个发送用到
	private static Map<String, Set<WebSocketSession>> mapClientSession = new ConcurrentHashMap<String, Set<WebSocketSession>>();

    //广播用到
    private static Set<WebSocketSession> clientAllSession = new CopyOnWriteArraySet<WebSocketSession>();
    
    //获取在线用户名对应的id
    public static Set<WebSocketSession> getAllSession() {
    	return clientAllSession;
    }
    //在线数
    public static int getOnlineCount() {
    	return mapClientSession.size();
    }
    //所有在线数
    public static int getOnlineCountAll() {
    	return clientAllSession.size();
    }
    /**
     * <p>记录一个连接</p>
     * @param userId
     * @param webSocketSession
     */
    public static void add(String userId, WebSocketSession webSocketSession) {
    	if (userId == null) return;
    	Set<WebSocketSession> wss = mapClientSession.get(userId);
    	if (wss == null) {
    		wss = new CopyOnWriteArraySet<WebSocketSession>();
			mapClientSession.put(userId, wss);
			wss.add(webSocketSession);
    	} else {
    		if (!wss.contains(webSocketSession)) {
    			wss.add(webSocketSession);
    		}
    	}
    	clientAllSession.add(webSocketSession);
    }

    /**
     * <p>通过id获取连接</p>
     * @param userId
     * @return
     */
    public static Set<WebSocketSession> get(String userId) {
        return mapClientSession.get(userId);
    }

    /**
     * <p>移除连接</p>
     * @param userId
     */
    public static void remove(String userId, WebSocketSession webSocketSession) {
    	if (userId == null) return;
    	Set<WebSocketSession> wss = mapClientSession.get(userId);
    	if (wss != null && wss.contains(webSocketSession)) {
    		wss.remove(webSocketSession);
    		if (wss.isEmpty()) {
    			mapClientSession.remove(userId);
    		}
    	}
    	clientAllSession.remove(webSocketSession);
    }
    
    public static int size() {
        return mapClientSession.size();
    }
    
    /**
     * 发给单一 用户
     * @param userId
     * @param msg
     */
    public static void sendMessageToTarget(String userId, WebSocketMsg msg) {
    	if (userId == null) return;
    	Set<WebSocketSession> setWss = mapClientSession.get(userId);
    	if (setWss != null && !setWss.isEmpty()) {
    		//上传文件提示特殊处理
             sendMessage(new TextMessage(msg.toString()), setWss);
             /*if(StringUtils.isNotBlank(msg.getCmd()) && "1111".equals(msg.getCmd())){//添加文档审核缓存
     			DocCheckCache.addCache(msg.getBody(), userId);
     		 }*/
    	} else {
    		MsgCache.addCacheMsg(userId, msg);
    		/*if(StringUtils.isNotBlank(msg.getCmd()) && "1111".equals(msg.getCmd())){
    			DocCheckCache.addCache(msg.getBody(), userId);
    		}else{
    			
    		}*/
        	
    	}
    }

    /**
     * 封装发送方法
     * @param message
     * @param setWss
     */
    private static void sendMessage(TextMessage message, Set<WebSocketSession> setWss) {
    	for (WebSocketSession webSocketSession : setWss) {
    		if (webSocketSession.isOpen()) {
                try {
                    webSocketSession.sendMessage(message);
                } catch (Exception e) {
                	log.error("", e);
                }
            } else {
            	
            	setWss.remove(webSocketSession);
            }
    	}
    }

    /*private static void sendMsgNotRemove(TextMessage message, Set<WebSocketSession> setWss){
    	for (WebSocketSession webSocketSession : setWss) {
    		if (webSocketSession.isOpen()) {
                try {
                    webSocketSession.sendMessage(message);
                } catch (Exception e) {
                	log.error("", e);
                }
            }
    	}
    }*/
    
    /**
     * 广播
     * @param msg
     */
/*    @Deprecated
    public static void sendMessageToAllTarget2(WebSocketMsg msg) {
    	TextMessage message = new TextMessage(msg.toString());
        Iterator<Map.Entry<String, Set<WebSocketSession>>>  iterator = mapClientSession.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Set<WebSocketSession>> enter = (Map.Entry<String, Set<WebSocketSession>>) iterator.next();
            Set<WebSocketSession> ws = mapClientSession.get(enter.getKey());
            sendMessage(message,ws);
        }
        //??MsgCache.addCacheMsg(userId, msg);
    }*/
    
    /**
     * 广播，用这个
     * @param msg
     */
   /* public static void sendMessageToAllTarget(WebSocketMsg msg) {
    	TextMessage message = new TextMessage(msg.toString());
        Iterator<WebSocketSession>  iterator = clientAllSession.iterator();
        while (iterator.hasNext()) {
            WebSocketSession webSocketSession = iterator.next();
            if (webSocketSession.isOpen()) {
            	try {
            		webSocketSession.sendMessage(message);
    			} catch (Exception e) {
    				log.error("", e);
            	}
            } else {
            	clientAllSession.remove(webSocketSession);
            }
        }
    }*/
}