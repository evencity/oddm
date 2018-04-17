package com.apical.oddm.facade.material.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.material.MaterialBareServiceI;
import com.apical.oddm.application.material.MaterialTypeServiceI;
import com.apical.oddm.core.model.material.MaterialBare;
import com.apical.oddm.core.model.material.MaterialType;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.material.MaterialBareFacade;
import com.apical.oddm.facade.material.command.MaterialBareCommand;
import com.apical.oddm.facade.material.dto.MaterialBareDTO;

/**
 * @description 类的描述：
 * @author 作者 : zhuzh
 * @date 创建时间：2016年10月28日 上午9:14:30
 * @version 1.0
 */
@Component("materialBareFacade")
public class MaterialBareFacadeImpl implements MaterialBareFacade {

	@Autowired
	private MaterialBareServiceI materialBareService;

	@Autowired
	private MaterialTypeServiceI materialTypeService;

	@Override
	public BasePage<MaterialBareDTO> pageList(MaterialBareCommand materialBareCommand, PageCommand pageCommand) {
		// TODO Auto-generated method stub
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		if (pageCommand.getSort().equals("typeName")) {
			SystemContext.setSort("t.type");
		} else {
			SystemContext.setSort("t." + pageCommand.getSort());
		}
		Pager<MaterialBare> dataGrid = null;
		BasePage<MaterialBareDTO> basePage = new BasePage<MaterialBareDTO>();
		if (materialBareCommand != null) {
			Set<Integer> set = new HashSet<Integer>();
			set.add(1);
			set.add(2);
			set.add(5);
			MaterialBare materialBareQuery = new MaterialBare();
			if (materialBareCommand.getName() != null) {
				materialBareQuery.setName(materialBareCommand.getName());
			}
			if (materialBareCommand.getTypeId() != null) {
				materialBareQuery.setMaterialType(new MaterialType(materialBareCommand.getTypeId()));
			}
			dataGrid = materialBareService.dataGridBySuperType(materialBareQuery, set);

			if (dataGrid != null && dataGrid.getRows().size() > 0) {
				List<MaterialBareDTO> list = new ArrayList<MaterialBareDTO>();
				for (MaterialBare materialBare : dataGrid.getRows()) {
					MaterialBareDTO materialBareDTO = new MaterialBareDTO();
					BeanUtils.copyProperties(materialBare, materialBareDTO);
					if (materialBare.getMaterialType() != null) {
						// MaterialType materialType = materialTypeService.get(materialBare.getType());
						materialBareDTO.setTypeId(materialBare.getMaterialType().getType());
						materialBareDTO.setTypeName(materialBare.getMaterialType().getName());
					}
					list.add(materialBareDTO);
				}
				basePage.setTotal(dataGrid.getTotal());
				basePage.setRows(list);
			}
		}

		return basePage;
	}

	@Override
	public Boolean add(MaterialBareCommand materialBareCommand) {
		// TODO Auto-generated method stub
		MaterialBare materialBare = new MaterialBare();
		if (materialBareCommand != null) {
			BeanUtils.copyProperties(materialBareCommand, materialBare);
			MaterialType materialType = materialTypeService.get(materialBareCommand.getTypeId());
			if (materialType != null) {
				materialBare.setMaterialType(materialType);
			}

			Serializable id = materialBareService.add(materialBare);
			if (id != null) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		if (id != null) {
			materialBareService.delete(id);
		}
	}

	@Override
	public void edit(MaterialBareCommand materialBareCommand) {
		// TODO Auto-generated method stub
		if (materialBareCommand != null) {
			MaterialBare materialBare = new MaterialBare();
			BeanUtils.copyProperties(materialBareCommand, materialBare);
			MaterialType materialType = materialTypeService.get(materialBareCommand.getTypeId());
			if (materialType != null) {
				materialBare.setMaterialType(materialType);
			}
			materialBareService.edit(materialBare);
		}
	}

	@Override
	public MaterialBareDTO get(Integer id) {
		// TODO Auto-generated method stub
		if (id != null) {
			MaterialBare materialBare = materialBareService.get(id);
			MaterialBareDTO materialBareDTO = new MaterialBareDTO();
			BeanUtils.copyProperties(materialBare, materialBareDTO);
			if (materialBare.getMaterialType() != null) {
				MaterialType materialType = materialTypeService.get(materialBare.getType());
				materialBareDTO.setTypeId(materialBare.getType());
				materialBareDTO.setTypeName(materialType.getName());
			}
			return materialBareDTO;
		}
		return null;
	}

	@Override
	public BasePage<MaterialBareDTO> dataGridByType(Set<Integer> type, PageCommand pageCommand) {
		// TODO Auto-generated method stub
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		if (pageCommand.getSort().equals("typeName")) {
			SystemContext.setSort("t.type");
		} else {
			SystemContext.setSort("t." + pageCommand.getSort());
		}
		Pager<MaterialBare> dataGrid = null;
		dataGrid = materialBareService.dataGridByType(type);
		SystemContext.setPageOffset(null);
		SystemContext.setPageSize(null);
		BasePage<MaterialBareDTO> basePage = new BasePage<MaterialBareDTO>();
		if (dataGrid != null) {
			if (dataGrid.getRows().size() > 0) {
				List<MaterialBareDTO> list = new ArrayList<MaterialBareDTO>();
				for (MaterialBare materialBare : dataGrid.getRows()) {
					MaterialBareDTO materialBareDTO = new MaterialBareDTO();
					BeanUtils.copyProperties(materialBare, materialBareDTO);
					if (materialBare.getMaterialType() != null) {
						MaterialType materialType = materialTypeService.get(materialBare.getType());
						materialBareDTO.setTypeId(materialBare.getType());
						materialBareDTO.setTypeName(materialType.getName());
					}
					list.add(materialBareDTO);
				}
				basePage.setTotal(dataGrid.getTotal());
				basePage.setRows(list);
			}
		}

		return basePage;
	}

	@Override
	public List<MaterialBareDTO> listGridByType(Set<Integer> type, Integer isBase) {
		// TODO Auto-generated method stub
		List<MaterialBareDTO> list = new ArrayList<MaterialBareDTO>();
		List<MaterialBare> listGridByType = materialBareService.listGridByType(type, isBase);
		if (listGridByType != null) {
			for (MaterialBare materialBare : listGridByType) {
				MaterialBareDTO materialBareDTO = new MaterialBareDTO();
				BeanUtils.copyProperties(materialBare, materialBareDTO);
				if (materialBare.getMaterialType() != null) {
					MaterialType materialType = materialTypeService.get(materialBare.getType());
					materialBareDTO.setTypeId(materialBare.getType());
					materialBareDTO.setTypeName(materialType.getName());
				}
				list.add(materialBareDTO);
			}
		}
		return list;
	}

	// 1.2
	@Override
	public List<MaterialBareDTO> listGridBySuperType(Set<Integer> type, Integer isBase) {
		// TODO Auto-generated method stub
		List<MaterialBareDTO> list = new ArrayList<MaterialBareDTO>();
		List<MaterialBare> listGridByType = materialBareService.listGridBySuperType(type, isBase);
		if (listGridByType != null) {
			for (MaterialBare materialBare : listGridByType) {
				MaterialBareDTO materialBareDTO = new MaterialBareDTO();
				BeanUtils.copyProperties(materialBare, materialBareDTO);
				if (materialBare.getMaterialType() != null) {
					MaterialType materialType = materialTypeService.get(materialBare.getType());
					materialBareDTO.setTypeId(materialBare.getType());
					materialBareDTO.setTypeName(materialType.getName());
				}
				list.add(materialBareDTO);
			}
		}
		return list;
	}

	@Override
	public BasePage<MaterialBareDTO> dataGridByName(String materialName, PageCommand pageCommand) {
		// TODO Auto-generated method stub
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		if (pageCommand.getSort().equals("typeName")) {
			SystemContext.setSort("t.type");
		} else {
			SystemContext.setSort("t." + pageCommand.getSort());
		}
		Pager<MaterialBare> dataGrid = null;
		if (materialName != null) {
			dataGrid = materialBareService.dataGridByName(materialName);
			BasePage<MaterialBareDTO> basePage = new BasePage<MaterialBareDTO>();
			if (dataGrid != null) {
				if (dataGrid.getRows().size() > 0) {
					List<MaterialBareDTO> list = new ArrayList<MaterialBareDTO>();
					for (MaterialBare materialBare : dataGrid.getRows()) {
						MaterialBareDTO materialBareDTO = new MaterialBareDTO();
						BeanUtils.copyProperties(materialBare, materialBareDTO);
						if (materialBare.getMaterialType() != null) {
							MaterialType materialType = materialTypeService.get(materialBare.getType());
							materialBareDTO.setTypeId(materialBare.getType());
							materialBareDTO.setTypeName(materialType.getName());
						}
						list.add(materialBareDTO);
					}
					basePage.setTotal(dataGrid.getTotal());
					basePage.setRows(list);
				}
			}

			return basePage;
		}
		return null;
	}

	@Override
	public BasePage<MaterialBareDTO> dataGridBySuperType(Set<Integer> type, PageCommand pageCommand) {
		// TODO Auto-generated method stub
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		if (pageCommand.getSort().equals("typeName")) {
			SystemContext.setSort("t.type");
		} else {
			SystemContext.setSort("t." + pageCommand.getSort());
		}
		// SystemContext.setSort("t."+pageCommand.getSort());
		Pager<MaterialBare> dataGrid = null;
		dataGrid = materialBareService.dataGridBySuperType(null, type);
		BasePage<MaterialBareDTO> basePage = new BasePage<MaterialBareDTO>();
		if (dataGrid != null) {
			if (dataGrid.getRows().size() > 0) {
				List<MaterialBareDTO> list = new ArrayList<MaterialBareDTO>();
				for (MaterialBare materialBare : dataGrid.getRows()) {
					MaterialBareDTO materialBareDTO = new MaterialBareDTO();
					BeanUtils.copyProperties(materialBare, materialBareDTO);
					if (materialBare.getMaterialType() != null) {
						MaterialType materialType = materialBare.getMaterialType();
						materialBareDTO.setTypeId(materialBare.getType());
						materialBareDTO.setTypeName(materialType.getName());
					}
					list.add(materialBareDTO);
				}
				basePage.setTotal(dataGrid.getTotal());
				basePage.setRows(list);
			}
		}

		return basePage;
	}
}
