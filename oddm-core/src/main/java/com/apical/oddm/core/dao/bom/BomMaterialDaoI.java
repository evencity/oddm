package com.apical.oddm.core.dao.bom;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.bom.BomMaterial;
import com.apical.oddm.core.model.page.Pager;

/**
 * BOM物料表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface BomMaterialDaoI extends BaseDaoI<BomMaterial> {

	/**
	 * 分页获取BOM物料列表
	 * @return BOM物料
	 */
	public Pager<BomMaterial> dataGrid();
	
	/**
	 * 分页获取BOM物料列表
	 * @param bomMaterial
	 * @return BOM物料
	 */
	public Pager<BomMaterial> dataGrid(BomMaterial bomMaterial);
	
	/**
	 * 批量导入数据
	 * @param bomMaterial
	 */
	public void saveBomMaterialBatch(List<BomMaterial> bomMaterial);

	/**
	 * 根据物料编码删除对象
	 * @param materialCode
	 */
	public void delete(String materialCode);
	/**
	 * 根据物料编码
	 * @param materialCode
	 * @return
	 */
	public BomMaterial get(String materialCode);
	
	/**
	 * 根据物料名称查询列表
	 * @param materialName
	 * @return
	 */
	public List<BomMaterial> getByMaterialName(String materialName);
}
