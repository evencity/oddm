package com.apical.oddm.core.dao.client;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.client.ClientInfo;

/**
 * 客户信息表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface ClientInfoDaoI extends BaseDaoI<ClientInfo> {

	/**
	 * 获取用户信息(支持下面参数)
	 * @param clientInfo.setName() 传入客户姓名
	 * @param clientInfo.setNameCode() 传入客户名称编号
	 * @return
	 */
	public ClientInfo get(ClientInfo clientInfo);
}
