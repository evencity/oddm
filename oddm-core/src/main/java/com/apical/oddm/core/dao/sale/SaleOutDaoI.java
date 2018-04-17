package com.apical.oddm.core.dao.sale;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SaleOut;

/**
 * 销售信息表dao操作接口
 * @author lgx
 * 2017-3-13
 */
public interface SaleOutDaoI extends BaseDaoI<SaleOut> {

	/**
	 * 获取详细信息
	 * @param id
	 * @return 快加载
	 */
	public SaleOut getDetail(int id);
	
	/**
	 * 通过订单id获取SaleOut信息
	 * @param orderId
	 * @param lazy true慢，false快
	 * @return
	 */
	public SaleOut getByOrderId(int orderId, boolean lazy);
	
	/**
	 * 通过参数（仅支持传以下参数）获取销售信息列表
	 * @param saleOut.getOrderInfo().setOrderNo() 订单号
	 * @param saleOut.getOrderInfo().setClientName() 客户名称
	 * @param saleOut.getOrderInfo().setClientCode() 客户名称编码
	 * @param saleOut.getOrderInfo().setMerchandiser() 所属跟单
	 * @param saleOut.getOrderInfo().setSeller() 所属业务
	 * @param saleOut.getOrderInfo().setDistrict() 国家
	 * @param saleOut.getOrderInfo().setMerchandiserId() 所属跟单
	 * @param saleOut.getOrderInfo().setSellerId() 所属业务
	 * @param saleOut.getOrderInfo().setDateOrderMonth() 下单月份
	 * 
	 * @param saleOut.getProductType() 产品类型
	 * @param saleOut.setCurrency() 币种
	 * @param saleOut.setDateShipmentMonth() 出货月份
	 * @param saleOut.setDateShipmentStart() < 大于 小于< saleOut.setDateShipmentEnd(),出货日期
	 * @return
	 */
	public Pager<SaleOut> dataGrid(SaleOut saleOut);
	
	/**
	/**
	 * 通过参数（仅支持传以下参数）获取销售信息列表
	 * @param saleOut.getOrderInfo().setOrderNo() 订单号
	 * @param saleOut.getOrderInfo().setClientName() 客户名称
	 * @param saleOut.getOrderInfo().setClientCode() 客户名称编码
	 * @param saleOut.getOrderInfo().setMerchandiser() 所属跟单
	 * @param saleOut.getOrderInfo().setSeller() 所属业务
	 * @param saleOut.getOrderInfo().setDistrict() 国家
	 * @param saleOut.getOrderInfo().setMerchandiserId() 所属跟单
	 * @param saleOut.getOrderInfo().setSellerId() 所属业务
	 * @param saleOut.getOrderInfo().setDateOrderMonth() 下单月份
	 * 
	 * @param saleOut.getProductType() 产品类型
	 * @param saleOut.setCurrency() 币种
	 * @param saleOut.setDateShipmentMonth() 出货月份
	 * @param saleOut.setDateShipmentStart() < 大于 小于< saleOut.setDateShipmentEnd(),出货日期
	 * @return
	 */
	public List<SaleOut> list(SaleOut saleOut);
	
	/**
	 * 返回分事业部销售数据统计
	 * @param year 格式要求：2016
	 * @param currency 币种
	 * @return
	 */
//	public List<SaleDeptStatisVo> getSaleStatisVo(String year, String currency);
	
	/**
	 * 分别按销售员、客户名、产品类型统计一年的订单数量、销售数量、备品数量、销售总额
	 * @param type 见SaleOutYearVo定义
	 * @param year 2016
	 * @param currency 币种
	 * @return
	 */
//	public List<SaleOutYearVo> getSaleOutYearVoList(int type, String year, String currency);

	/**
	 * 统计历史年度销售曲线
	 * @param yearStart
	 * @param yearEnd
	 * @param currency
	 * @return
	 */
//	public List<SaleOutAllYearVo> getSaleOutAllYearVoList(String yearStart, String yearEnd, String currency);
}
