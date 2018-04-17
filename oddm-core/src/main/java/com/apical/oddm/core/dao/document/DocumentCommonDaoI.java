package com.apical.oddm.core.dao.document;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.document.DocumentCommon;
import com.apical.oddm.core.model.page.Pager;

/**
 * 通用物料文档表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface DocumentCommonDaoI extends BaseDaoI<DocumentCommon> {

	/**
	 * 分页获取通用文档列表
	 * @return
	 */
	public Pager<DocumentCommon> dataGrid();
	
	/**
	 * 通过物料名，查询单个文档
	 * @param nameMaterial
	 * @return
	 */
	public DocumentCommon getByMtName(String nameMaterial);
	
	/**
	 * 通过物料名，查询单个文档
	 * @param nameMaterial
	 * @return
	 */
	public Pager<DocumentCommon> dataGridByMtName(String nameMaterial);
	
	/**
	 * 通过物料名，查询文档，模糊查询
	 * @param nameMaterial
	 * @return
	 */
	public List<DocumentCommon> getListByMtName(String nameMaterial);
}
