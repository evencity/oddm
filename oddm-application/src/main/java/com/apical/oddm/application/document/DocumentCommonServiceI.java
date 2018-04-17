package com.apical.oddm.application.document;

import java.util.List;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.document.DocumentCommon;
import com.apical.oddm.core.model.page.Pager;


/**
 * 通用文档（物料）资料表操作接口
 * @author lgx
 * 2016-10-16
 */
public interface DocumentCommonServiceI extends BaseServiceI<DocumentCommon> {

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
	 * 通过物料名，查询文档，模糊查询
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
