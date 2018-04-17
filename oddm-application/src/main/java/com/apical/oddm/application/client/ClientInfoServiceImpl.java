package com.apical.oddm.application.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.client.ClientInfoDaoI;
import com.apical.oddm.core.model.client.ClientInfo;

@Service("clientInfoService")
public class ClientInfoServiceImpl extends BaseServiceImpl<ClientInfo> implements ClientInfoServiceI {

	@Autowired
	private ClientInfoDaoI clientInfoDao;

	@Override
	public ClientInfo get(ClientInfo clientInfo) {
		return clientInfoDao.get(clientInfo);
	}

}
