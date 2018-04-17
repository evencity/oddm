package com.apical.oddm.core.dao.sys;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.SysNotice;

@Repository("sysNoticeDao")
public class SysNoticeDaoImpl extends BaseDaoImpl<SysNotice> implements SysNoticeDaoI {

	@Override
	public Pager<SysNotice> dataGrid(SysNotice sysNotice, Set<Integer> states) {
		String hql ="from SysNotice t where 1=1";
		Map<String, Object> map = new HashMap<String, Object>(2);
		if (states != null && !states.isEmpty()) {
			hql +=" and t.state in ("
					+ org.springframework.util.StringUtils.collectionToDelimitedString(states, ",") + ")";
		}
		if (sysNotice != null) {
			if (StringUtils.isNotBlank(sysNotice.getTitle())) {
				hql +=" and t.title like :title"; //发现不能用 like %?% 
				map.put("title", "%"+sysNotice.getTitle()+"%");
			}
		}
		hql += " order by t.pubdate desc";
		return this.findByAlias(hql, map);
	}

	@Override
	public Pager<SysNotice> dataGrid(Date startTime, Date endTime) {
		String hql ="from SysNotice t where t.pubdate>=:startTime and t.pubdate<=:endTime order by t.pubdate desc";
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return this.findByAlias(hql, map);
	}

	@Override
	public Pager<SysNotice> dataGrid(String title) {
		String hql ="from SysNotice t where t.title like ? order by t.pubdate desc"; //发现不能用 like %?% 
		return this.find(hql, "%"+title+"%");
	}
}
