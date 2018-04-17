package com.apical.oddm.application.bom;

import java.io.Serializable;
import java.util.List;

import com.apical.oddm.core.model.bom.BomMaterial;
import com.apical.oddm.core.model.page.Pager;

/**
 * BOM物料表操作接口
 * @author lgx
 * 2016-10-16
 */
public interface BomMaterialServiceI {

	/**
	 * 分页获取BOM物料列表
	 * @return BOM物料
	 */
	public Pager<BomMaterial> dataGrid();
	
	/**
	 * 分页获取BOM物料列表
	 * @param bomMaterial.getMaterialName() 物料名
	 * @param bomMaterial.getMtlCode()  物料编码
	 * @return BOM物料
	 */
	public Pager<BomMaterial> dataGrid(BomMaterial bomMaterial);
	
	/**
	 * 批量导入数据
	 * @param bomMaterial
	 */
	public void saveBomMaterialBatch(List<BomMaterial> bomMaterial);
	
	/**
	 * 批量删除数据
	 * @param materialCode
	 */
	public void deletBomMaterialBatch(List<String> materialCode);
	
	/**
	 * 添加对象
	 * @param bomMaterial
	 * @return 主键
	 */
	public Serializable add(BomMaterial bomMaterial);
	/**
	 * 编辑对象
	 * @param bomMaterial
	 */
	public void edit(BomMaterial bomMaterial);
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
