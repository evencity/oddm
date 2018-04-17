package com.apical.oddm.application.sale;

import java.util.Date;
import java.util.List;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SaleInfo;
import com.apical.oddm.core.vo.sale.SaleDeptStatisVo;
import com.apical.oddm.core.vo.sale.SaleInfoAllYearVo;
import com.apical.oddm.core.vo.sale.SaleInfoYearVo;


/**
 * 销售信息表操作接口
 * @author lgx
 * 2016-10-16
 */
public interface SaleInfoServiceI extends BaseServiceI<SaleInfo> {

	/**
	 * 获取详细信息
	 * @param id
	 * @return 快加载
	 */
	public SaleInfo getDetail(int id);
	
	/**
	 * 通过订单id获取SaleInfo信息
	 * @param orderId
	 * @param lazy true慢，false快
	 * @return 
	 */
	public SaleInfo getByOrderId(int orderId, boolean lazy);

	/**
	 * 分页获取销售信息列表
	 * @return
	 */
	public Pager<SaleInfo> dataGrid();
	
	/**
	 * 通过参数（仅支持传以下参数）获取销售信息列表
	 * @param saleInfo.getOrderInfo().setOrderNo() 订单号
	 * @param saleInfo.getOrderInfo().setClientName() 客户名称
	 * @param saleInfo.getOrderInfo().setMerchandiser() 所属跟单
	 * @param saleInfo.getOrderInfo().setSeller() 所属业务
	 * @param saleInfo.getOrderInfo().setDistrict() 国家
	 * @param saleInfo.getOrderInfo().setMerchandiserId() 所属跟单
	 * @param saleInfo.getOrderInfo().setSellerId() 所属业务	 * @return
	 */
	public Pager<SaleInfo> dataGrid(SaleInfo saleInfo);
	
	/**
	 * @param saleInfo.getOrderInfo().setOrderNo() 订单号
	 * @param saleInfo.getOrderInfo().setClientName() 客户名称
	 * @param saleInfo.getOrderInfo().setMerchandiser() 所属跟单
	 * @param saleInfo.getOrderInfo().setSeller() 所属业务
	 * @param saleInfo.getOrderInfo().setDistrict() 国家
	 * @param saleInfo.getOrderInfo().setMerchandiserId() 所属跟单
	 * @param saleInfo.getOrderInfo().setSellerId() 所属业务
	 * @param saleInfo.setProductType() 产品类型
	 * @param saleInfo.setDateOrderStart() < 大于 小于< saleInfo.setDateOrderEnd(),下单时间
	 * @return
	 */
	public List<SaleInfo> list(SaleInfo saleInfo);

	/**
	 * 通过下单日期分页获取列表
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return
	 */
	public Pager<SaleInfo> dataGridByDateOrder(Date startDate, Date endDate);
	
	/**
	 * 返回分事业部销售数据统计
	 * @param year 格式要求：2016
	 * @param currency 币种
	 * @return
	 */
	@Deprecated
	public List<SaleDeptStatisVo> getSaleStatisVo(String year, String currency);
	
	/**
	 * 分别按销售员、客户名、产品类型统计一年的订单数量、销售数量、备品数量、销售总额
	 * @param type 见SaleInfoYearVo定义
	 * @param year 2016
	 * @param currency 币种
	 * @return
	 */
	public List<SaleInfoYearVo> getSaleInfoYearVoList(int type, String year, String currency);

	/**
	 * 统计历史年度销售曲线
	 * @param yearStart
	 * @param yearEnd
	 * @param currency
	 * @return
	 */
	public List<SaleInfoAllYearVo> getSaleInfoAllYearVoList(String yearStart, String yearEnd, String currency);
}
