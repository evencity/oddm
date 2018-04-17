package com.apical.oddm.facade.manufacture;

import java.util.List;

import com.apical.oddm.facade.manufacture.dto.ManufacturePackageTitleDTO;


public interface ManufacturePackageTitleFacade {

	/**
	 * 获取全部记录
	 * @return
	 */
	public List<ManufacturePackageTitleDTO> getListAll();
}
