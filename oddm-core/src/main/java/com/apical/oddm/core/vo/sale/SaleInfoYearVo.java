package com.apical.oddm.core.vo.sale;

import java.math.BigDecimal;

/**
 * 定义根据业务名、客户名、产品类型查询pojo
 * @author lgx
 * 2014-2-28
 */
public class SaleInfoYearVo {
	public static final int sellerName = 1;//业务员名
	public static final int clientName = 2;//客户名
	public static final int productName = 3;//产品类型 PND MVR MID等
	public static final int districtName = 4;//国家地区
	public static final int productFactory = 5;//工厂机型

	private String name;
	
	private Integer quantitys;
	
	private Integer quantitySales;

	private Integer spares;

	private Double totals;

	private Integer month;

	public void setQuantitysDto(BigDecimal quantitysDto) {
		this.quantitys = quantitysDto.intValue();
	}
	
	public void setQuantitySalesDto(BigDecimal quantitySalesDto) {
		this.quantitySales = quantitySalesDto.intValue();
	}
	
	public void setSparesDto(BigDecimal sparesDto) {
		this.spares = sparesDto.intValue();
	}
	
	public void setTotalsDto(BigDecimal totalsDto) {
		this.totals = totalsDto.doubleValue();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantitys() {
		return quantitys;
	}

	public void setQuantitys(Integer quantitys) {
		this.quantitys = quantitys;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Double getTotals() {
		return totals;
	}

	public void setTotals(Double totals) {
		this.totals = totals;
	}

	public Integer getQuantitySales() {
		return quantitySales;
	}

	public void setQuantitySales(Integer quantitySales) {
		this.quantitySales = quantitySales;
	}

	public Integer getSpares() {
		return spares;
	}

	public void setSpares(Integer spares) {
		this.spares = spares;
	}
}
