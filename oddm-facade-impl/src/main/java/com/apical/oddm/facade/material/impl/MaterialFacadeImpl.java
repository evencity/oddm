package com.apical.oddm.facade.material.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.material.MaterialServiceI;
import com.apical.oddm.core.model.material.Material;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.material.MaterialFacade;
import com.apical.oddm.facade.material.dto.MaterialDTO;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2017年2月5日 下午8:30:10 
 * @version 1.0 
 */
@Component("materialFacade")
public class MaterialFacadeImpl implements MaterialFacade{

	@Autowired
	private MaterialServiceI materialService;
	@Override
	public BasePage<MaterialDTO> dataGridBySuperType(MaterialDTO materialDTOQuery, Set<Integer> type,PageCommand pageCommand) {
		// TODO Auto-generated method stub
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		if (pageCommand.getSort().equals("typeName")) {
			SystemContext.setSort("t.type");
		} else {
			SystemContext.setSort("t." + pageCommand.getSort());
		}
		Material materialQuery = new Material() ;
		if(materialDTOQuery != null && materialDTOQuery.getName() != null){
			//materialQuery.setName(materialDTOQuery.getName());
			BeanUtils.copyProperties(materialDTOQuery, materialQuery);
		}
		// SystemContext.setSort("t."+pageCommand.getSort());
		Pager<Material> dataGrid = null;
		dataGrid = materialService.dataGridBySuperType(materialQuery, type);
		BasePage<MaterialDTO> basePage = new BasePage<MaterialDTO>();
		if (dataGrid != null) {
			if (dataGrid.getRows().size() > 0) {
				List<MaterialDTO> list = new ArrayList<MaterialDTO>();
				for (Material material : dataGrid.getRows()) {
					MaterialDTO materialDTO = new MaterialDTO();
					BeanUtils.copyProperties(material, materialDTO);
					if(material.getMaterialType() != null){
						materialDTO.setType(material.getMaterialType().getId());
						materialDTO.setTypeName(material.getMaterialType().getName());
					}
					list.add(materialDTO);
				}
				basePage.setTotal(dataGrid.getTotal());
				basePage.setRows(list);
			}
		}

		return basePage;
	}

}
