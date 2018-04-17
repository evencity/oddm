package com.apical.oddm.core.vo.sale;

import java.math.BigDecimal;

/**
 * 年的月，与订单数对应关系
 * @author LGX
 * 2017-3-1
 */
public class SaleInfoAllYearVo {

	private String yearMonth;
	
	private Integer quantitys;

	private Integer quantitySales;

	private Integer spares;

	private Double totals;
	
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
	
	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public Integer getQuantitys() {
		return quantitys;
	}

	public void setQuantitys(Integer quantitys) {
		this.quantitys = quantitys;
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

	public Double getTotals() {
		return totals;
	}

	public void setTotals(Double totals) {
		this.totals = totals;
	}
}
