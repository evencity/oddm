package com.apical.oddm.facade.bom;

import com.apical.oddm.core.model.bom.BomInfo;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.bom.dto.BomDTO;
import com.apical.oddm.facade.bom.dto.BomInfoAltDTO;

public interface BomInfoAltFacade {
	/**
	 * 通过订单信息外键查询关联的所有列表
	 * @param BomId 订单信息id
	 * @return
	 */
	public BasePage<BomInfoAltDTO> dataGrid(Integer BomId,PageCommand pageDTO);

	/**
	 * 添加BOM单变更记录
	 * @param 
	 * @return
	 */
	public Boolean add(BomInfoAltDTO BomInfoAltDTO);

	/**
	 * 计算变更记录仪
	 * @param BomInfoBefore
	 * @param BomInfoDTO
	 * @param currUserId
	 */
	public void addEditRecord(BomInfo BomInfoBefore,
			BomDTO bomDTO, Integer currUserId,String currUserName);
	
	/**
	 * 增加BOM单未读记录
	 * @param currUserId 当前用户id
	 * @param BomId 
	 */
	public void addBomUnreadInfo(Integer currUserId, Integer BomId,String orderNo,Integer choose,String clientNameCode);
}
