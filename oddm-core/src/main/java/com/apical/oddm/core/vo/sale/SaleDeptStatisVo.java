package com.apical.oddm.core.vo.sale;

/**
 * 分事业部销售数据
select t.product_name name, sum(t.quantity * t.unit_price) amount, DATE_FORMAT(t.order_date, '%Y%m') months from sale_info t where t.currency = 'USD' 
group by months, name
order by t.order_date asc ;
 * @author lgx
 * 2016-11-2
 */
public class SaleDeptStatisVo {

	/**
	 * 产品名称
	 */
	private String productType;
	
	/**
	 * 当月销售总额
	 */
	private Double amount;
	
	/**
	 * 月份
	 */
	private String months;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getMonths() {
		return months;
	}

	public void setMonths(String months) {
		this.months = months;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	
}
