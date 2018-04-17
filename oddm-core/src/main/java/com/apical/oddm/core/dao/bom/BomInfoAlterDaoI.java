package com.apical.oddm.core.dao.bom;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.bom.BomInfoAlt;
import com.apical.oddm.core.model.page.Pager;

/**
 * BOM信息更改记录表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface BomInfoAlterDaoI extends BaseDaoI<BomInfoAlt> {

	/**
	 * 通过BOM信息外键查询关联的所有列表
	 * @param bominfoId bom信息id
	 * @return
	 */
	public List<BomInfoAlt> listGrid(int bominfoId);
	
	/**
	 * 分页获取
	 * @param bominfoId bom信息id
	 * @return 
	 */
	public Pager<BomInfoAlt> dataGrid(int bominfoId);
}
