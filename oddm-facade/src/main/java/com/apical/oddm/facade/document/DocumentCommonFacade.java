package com.apical.oddm.facade.document;

import java.util.List;

import com.apical.oddm.core.model.document.DocumentCommon;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.document.command.DocumentCommonCommand;
import com.apical.oddm.facade.document.dto.DocumentCommonDTO;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年11月6日 下午4:14:02 
 * @version 1.0 
 */

public interface DocumentCommonFacade {

	/**
	 * 分页获取通用文档列表
	 * @return
	 */
	public BasePage<DocumentCommonDTO> dataGrid();
	
	/**
	 * 通过物料名，查询文档，模糊查询
	 * @param nameMaterial
	 * @return
	 */
	public BasePage<DocumentCommonDTO> dataGridByMtName(String nameMaterial,PageCommand pageCommand);
	

	/**
	 * 根据Id删除文档信息
	 * @return 
	 */
	public void delete(Integer documentId);
	
	/**
	 * 添加
	 * @return 
	 */
	public Boolean add(DocumentCommonCommand documentCommonCommand);
	
	/**
	 * 添加
	 * @return 
	 */
	public void updatePath(DocumentCommonCommand documentCommonCommand);
	
	/**
	 * 根据Id获取文档信息
	 * @return 
	 */
	public DocumentCommonDTO get(Integer documentId);
	/**
	 * 通过物料名，查询文档，模糊查询
	 * @param nameMaterial
	 * @return
	 */
	public List<DocumentCommonDTO> getListByMtName(String nameMaterial);
}
