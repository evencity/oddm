package com.apical.oddm.facade.material.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.material.MaterialTypeServiceI;
import com.apical.oddm.core.model.material.MaterialType;
import com.apical.oddm.facade.material.MaterialTypeFacade;
import com.apical.oddm.facade.material.command.MaterialTypeCommand;
import com.apical.oddm.facade.material.dto.MaterialTypeDTO;

/**
 * @description 类的描述：
 * @author 作者 : zhuzh
 * @date 创建时间：2016年10月28日 下午1:46:54
 * @version 1.0
 */

@Component("materialTypeFacade")
public class MaterialTypeFacadeImpl implements MaterialTypeFacade {

	@Autowired
	private MaterialTypeServiceI materialTypeService;

	@Override
	public List<MaterialTypeDTO> pageList() {
		// TODO Auto-generated method stub
		List<MaterialType> dataGrid = materialTypeService.dataGrid();
		List<MaterialTypeDTO> list = new ArrayList<MaterialTypeDTO>();
		if (dataGrid != null && dataGrid.size() > 0) {
			for (MaterialType materialType : dataGrid) {
				MaterialTypeDTO materialTypeDTO = new MaterialTypeDTO();
				BeanUtils.copyProperties(materialType, materialTypeDTO);
				list.add(materialTypeDTO);
			}
			return list;
		}
		return null;
	}

	@Override
	public List<MaterialTypeDTO> dataGrid(Set<Integer> types) {
		// TODO Auto-generated method stub
		List<MaterialType> dataGrid = materialTypeService.dataGrid(types);
		List<MaterialTypeDTO> list = new ArrayList<MaterialTypeDTO>();
		if (dataGrid != null && dataGrid.size() > 0) {
			for (MaterialType materialType : dataGrid) {
				MaterialTypeDTO materialTypeDTO = new MaterialTypeDTO();
				BeanUtils.copyProperties(materialType, materialTypeDTO);
				list.add(materialTypeDTO);
			}
			return list;
		}
		return null;
	}

	@Override
	public Boolean add(MaterialTypeCommand materialTypeCommand) {
		// TODO Auto-generated method stub
		if (materialTypeCommand != null) {
			MaterialType materialType = new MaterialType();
			BeanUtils.copyProperties(materialTypeCommand, materialType);
			Serializable id = materialTypeService.add(materialType);
			if (id != null) {
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		if (id != null) {
			materialTypeService.delete(id);
		}
	}

	@Override
	public void edit(MaterialTypeCommand materialTypeCommand) {
		// TODO Auto-generated method stub
		if (materialTypeCommand != null) {
			MaterialType materialType = new MaterialType();
			BeanUtils.copyProperties(materialTypeCommand, materialType);
			materialTypeService.edit(materialType);
		}
	}

	@Override
	public MaterialTypeDTO get(Integer id) {
		// TODO Auto-generated method stub
		if (id != null) {
			MaterialType materialType = materialTypeService.get(id);
			MaterialTypeDTO materialTypeDTO = new MaterialTypeDTO();
			BeanUtils.copyProperties(materialType, materialTypeDTO);
			return materialTypeDTO;
		}
		return null;
	}

}
