package com.apical.oddm.facade.websocket;

import com.alibaba.fastjson.JSONObject;

/**
 * handler，处理网页请求
 * @author lgx
 * 2016-11-17
 */
public class WebSocketMsg {

	/**
	 * 传输命令
	 */
	public String cmd;
	
	/**
	 * 推送消息标题
	 */
	private String title;
	
	/**
	 * 消息体
	 */
	private String body;

	public WebSocketMsg(String title, String body) {
		this.title = title;
		this.body = body;
	}

	public WebSocketMsg(String cmd, String title, String body) {
		this.cmd = cmd;
		this.title = title;
		this.body = body;
	}

	public WebSocketMsg() {
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
	
}
