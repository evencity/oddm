package com.apical.oddm.facade.websocket;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于审核上传文档提示(此类不用 )
 * @author apk-666
 *
 */
public class DocCheckCache {

	private static Map<String, HashMap<String, Long>> docCheckMap = new ConcurrentHashMap<String,HashMap<String, Long>>();
	
	/**
	 * 添加缓存
	 * @param fileName
	 * @param userId
	 */
	public static void addCache(String fileName, String userId) {
		if(!docCheckMap.containsKey(userId)){
			HashMap<String, Long> map = new HashMap<String, Long>();
			docCheckMap.put(userId, map);
		} 
		HashMap<String, Long> hashMap = docCheckMap.get(userId);
		if(!hashMap.containsKey(fileName)){
			hashMap.put(fileName, System.currentTimeMillis());
		}
	}
	
	/**
	 * 删除缓存 
	 * @param fileName
	 * @param userId
	 */
	public static void removeCache(String fileName, String userId){
		if(docCheckMap.containsKey(userId)) {
			HashMap<String, Long> hashMap = docCheckMap.get(userId);
			Iterator<Entry<String, Long>> iterator = hashMap.entrySet().iterator();
			while(iterator.hasNext()){
				Entry<String, Long> next = iterator.next();
				if(next.getKey().equals(fileName))
					hashMap.remove(fileName);
			}
			if(hashMap.isEmpty())
				docCheckMap.remove(userId);
		}
	}
	
	/**
	 * 清空缓存
	 */
	public static void removeTimeout(){
		long currentTime = System.currentTimeMillis();
		Iterator<Entry<String, HashMap<String, Long>>> userIt = docCheckMap.entrySet().iterator();
		while(userIt.hasNext()) {
			Entry<String, HashMap<String, Long>> userEntry = userIt.next();
			HashMap<String, Long> userCache = userEntry.getValue();
			Iterator<Entry<String, Long>> docIt = userCache.entrySet().iterator();
			while(docIt.hasNext()){
				Entry<String, Long> docCache = docIt.next();
				if((currentTime - docCache.getValue()) > 7*24*60*60*1000) {//清空8天的的缓存
					userCache.remove(docCache.getKey());
				}
			}
			if(userCache.isEmpty()){
				docCheckMap.remove(userEntry.getKey());
			}
		}
	}
	
	public static void sendDocMsg(String userId){
		if(docCheckMap.containsKey(userId)){
			HashMap<String, Long> hashMap = docCheckMap.get(userId);
			for(Entry<String, Long> entry :hashMap.entrySet()){
				WebSocketMsg msg = new WebSocketMsg();
				msg.setCmd("1111");
				msg.setBody(entry.getKey());
				msg.setTitle("提示");
				WebSocketSessionUtils.sendMessageToTarget(userId, msg);
			}
		}
	}
	
}

