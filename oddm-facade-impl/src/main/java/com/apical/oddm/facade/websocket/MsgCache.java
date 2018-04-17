package com.apical.oddm.facade.websocket;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apical.oddm.facade.websocket.WebSocketMsg;

/**
 * 缓存客户端离线未能收到的数据，当缓存过大要考虑删除
 * @author lgx
 * 2016-11-29
 */
public class MsgCache {
	private static final Logger log = LoggerFactory.getLogger(MsgCache.class);

    //缓存没法送成功的数据，下次登录自动发送
	private static Map<String, Map<WebSocketMsg, Long>> webSocketMsgCache = new ConcurrentHashMap<String, Map<WebSocketMsg, Long>>();

	/**
	 * 检查缓存，清理，避免过大
	 */
	public static void excuteCheckCache() {
		try {
			for (Map.Entry<String, Map<WebSocketMsg, Long>> cache1 : webSocketMsgCache.entrySet()) {
				Map<WebSocketMsg, Long> cache2 = cache1.getValue();
				if (cache2 != null) {
					for (Map.Entry<WebSocketMsg, Long> cache : cache2.entrySet()) {
						if ((System.currentTimeMillis() - cache.getValue()) > 5*24*60*60*1000) {//天
							cache2.remove(cache.getKey());
							if (cache2.isEmpty()) {
								webSocketMsgCache.remove(cache1.getKey());
							}
						}
					}
				}
				
			}
			log.info("执行定时任务清空消息缓存");
		} catch (Exception e) {
			log.error("执行定时任务清空消息缓存错误", e);
		}
		//DocCheckCache.removeTimeout();
	}
    /**
     * 缓存操作
     * @param userId
     * @param msg
     */
    public static void addCacheMsg(String userId, WebSocketMsg msg) {
    	Map<WebSocketMsg, Long> msgMap = webSocketMsgCache.get(userId);
    	if (msgMap == null) {
    		msgMap = new ConcurrentHashMap<WebSocketMsg, Long>();
    		webSocketMsgCache.put(userId, msgMap);
    		msgMap.put(msg, System.currentTimeMillis());
    	} else {
    		if (!msgMap.containsKey(msg)) {
        		msgMap.put(msg, System.currentTimeMillis());
    		}
    	}
    }

    /**
     * @param userId
     * @return
     */
    public static Map<WebSocketMsg, Long> get(String userId) {
        return webSocketMsgCache.get(userId);
    }

    /**
     * @param userId
     */
/*    public static void remove(String userId, WebSocketMsg msg) {
    	Map<WebSocketMsg, Long> msgMap = webSocketMsgCache.get(userId);
    	if (msgMap != null && msgMap.containsKey(msg)) {
    		msgMap.remove(msg);
    		if (msgMap.isEmpty()) {
    			webSocketMsgCache.remove(userId);
    		}
    	}
    }*/
    
    /**
     * 把用户的所有消息移除
     * @param userId
     */
    public static void removeAll(String userId) {
    	webSocketMsgCache.remove(userId);
    }
    
}
