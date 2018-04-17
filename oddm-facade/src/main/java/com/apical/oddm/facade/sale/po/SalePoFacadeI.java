package com.apical.oddm.facade.sale.po;

import java.io.Serializable;
import java.util.Set;

import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.User;
import com.apical.oddm.facade.sale.po.cmd.SalePoCmd;
import com.apical.oddm.facade.sale.po.dto.SalePoDto;
/**
 * 内部订单门面层接口
 * @author lgx
 * 2016-12-30
 */
public interface SalePoFacadeI {

	/**
	 * 获取分页列表
	 * @param salePoDto
	 * @param states
	 * @return
	 */
	public Pager<SalePoDto> dataGrid(SalePoDto salePoDto, Set<Integer> states);

	/**
	 * 添加
	 * @param salePoCmd
	 * @return
	 */
	public Serializable add(SalePoCmd salePoCmd);

	/**
	 * 增加po的时候，需要的基本信息，包括订单的、公司名称地址等
	 * @param orderId
	 * @return 慢加载
	 */
	public SalePoDto getBaseInfo(Integer orderId);

	/**
	 * 查询是否有订单信息
	 * @param salePoCmd.setOrderNo()
	 * @return
	 */
	public OrderInfo getOrderInfo(SalePoCmd salePoCmd);

	/**获取详细信息
	 * @param id
	 * @param currUserId 
	 * @return
	 */
	public SalePoDto getDetail(Integer id, Integer currUserId);

	/**
	 * 编辑
	 * @param salePoCmd
	 */
	public void edit(SalePoCmd salePoCmd, Integer currUserId);

	/**
	 * 设置批准人
	 * @param user
	 * @param poId 
	 */
	public void updateApprover(User user, Integer poId);

	/**
	 * 审核通过更新状态
	 * @param state
	 * @param id
	 */
	public void audit(Integer currUserId, Integer state, Integer poId);

}
