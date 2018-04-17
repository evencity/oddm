package com.apical.oddm.core.dao.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.client.ClientInfo;

@Repository("clientInfoDao")
public class ClientInfoDaoImpl extends BaseDaoImpl<ClientInfo> implements ClientInfoDaoI {

	@Override
	public ClientInfo get(ClientInfo clientInfo) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t from ClientInfo t where 1=1";
		if (clientInfo != null) {
			if (StringUtils.isNotBlank(clientInfo.getName())) {
				hql +=" and t.name=:name";
				params.put("name", clientInfo.getName());
			}
			if (StringUtils.isNotBlank(clientInfo.getNameCode())) {
				hql +=" and t.nameCode=:nameCode";
				params.put("nameCode", clientInfo.getNameCode());
			}
		}
		return (ClientInfo) this.queryObjectByAlias(hql, params);
	}

}
