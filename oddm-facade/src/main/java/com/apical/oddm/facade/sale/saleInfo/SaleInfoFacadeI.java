package com.apical.oddm.facade.sale.saleInfo;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.vo.sale.SaleInfoAllYearVo;
import com.apical.oddm.facade.sale.saleInfo.cmd.SaleInfoCmd;
import com.apical.oddm.facade.sale.saleInfo.dto.SaleInfoDto;
import com.apical.oddm.facade.sale.saleInfo.dto.SaleInfoYearDto;
/**
 * 销售信息门面层接口
 * @author lgx
 * 2016-12-30
 */
public interface SaleInfoFacadeI {

	/**
	 * 获取分页列表
	 * @param saleInfoCmd
	 * @return
	 * @throws ParseException 
	 */
	public Pager<SaleInfoDto> dataGrid(SaleInfoCmd saleInfoCmd) throws ParseException;

	public Pager<SaleInfoDto> dataGridStatisticsMonth(SaleInfoCmd saleInfoCmd) throws ParseException;

	/**
	 * 导Excel等用到
	 * @param salePoCmd
	 * @return
	 */
	public List<SaleInfoDto> list(SaleInfoCmd saleInfoCmd) throws ParseException;
	
	/**
	 * 添加
	 * @param saleInfoCmd
	 * @return
	 */
	public Serializable add(SaleInfoCmd saleInfoCmd);

	/**
	 * 增加po的时候，需要的基本信息，包括订单的、公司名称地址等
	 * @param orderId
	 * @return 慢加载
	 */
	public SaleInfoDto getBaseInfo(Integer orderId);

	/**
	 * 查询是否有订单信息
	 * @param saleInfoCmd.setOrderNo()
	 * @return
	 */
	public OrderInfo getOrderInfo(SaleInfoCmd saleInfoCmd);

	/**获取详细信息
	 * @param id
	 * @param currUserId 
	 * @return
	 */
	public SaleInfoDto getDetail(Integer id);

	/**
	 * 编辑
	 * @param saleInfoCmd
	 */
	public void edit(SaleInfoCmd saleInfoCmd);

	public List<SaleInfoAllYearVo> getSaleInfoAllYearVoList(String yearStart,
			String yearEnd, String currency);

	public List<SaleInfoYearDto> getSaleInfoYearSellerVoList(Integer type,
			String year, String currency);

}
