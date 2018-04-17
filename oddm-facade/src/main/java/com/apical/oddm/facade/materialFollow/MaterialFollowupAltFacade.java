package com.apical.oddm.facade.materialFollow;

import com.apical.oddm.core.model.order.MaterialFollowup;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.materialFollow.command.MaterialFollowupAlterCommand;
import com.apical.oddm.facade.materialFollow.command.MaterialFollowupCommand;
import com.apical.oddm.facade.materialFollow.dto.MaterialFollowupAlterDTO;



/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年11月6日 下午2:12:42 
 * @version 1.0 
 */

public interface MaterialFollowupAltFacade {


	/**
	 * 通过订单信息外键查询关联的所有列表
	 * @param orderId 订单信息id
	 * @return
	 */
	public BasePage<MaterialFollowupAlterDTO> dataGrid(Integer materialFollowupId,PageCommand pageCommand);

	/**
	 * 添加定单变更记录
	 * @param 
	 * @return
	 */
	public Boolean add(MaterialFollowupAlterCommand materialFollowupAlterCommand);

	/**
	 * @param materialFollowu 变更前的对象
	 * @param materialFollowupCommand 变更后的对象
	 * @param currUserId1 当前用户id
	 */
	public void addEditRecord(MaterialFollowup materialFollowu, MaterialFollowupCommand materialFollowupCommand, Integer currUserId1);
}
