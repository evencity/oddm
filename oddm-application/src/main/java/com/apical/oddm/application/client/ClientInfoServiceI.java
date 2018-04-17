package com.apical.oddm.application.client;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.client.ClientInfo;


/**
 * 客户信息表操作接口
 * @author lgx
 * 2016-10-26
 */
public interface ClientInfoServiceI extends BaseServiceI<ClientInfo> {

	/**
	 * 获取用户信息(仅支持下面参数)
	 * @param clientInfo.setName() 传入客户姓名
	 * @param clientInfo.setNameCode() 传入客户名称编号
	 * @return
	 */
	public ClientInfo get(ClientInfo clientInfo);
	
}
