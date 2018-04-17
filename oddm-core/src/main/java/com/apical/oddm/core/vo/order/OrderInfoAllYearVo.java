package com.apical.oddm.core.vo.order;

import java.math.BigDecimal;

/**
 * 年的月，与订单数对应关系
 * @author LGX
 * 2017-3-1
 */
public class OrderInfoAllYearVo {

	private String yearMonth;
	
	private Integer quantitys;

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

	public void setQuantitysDto(BigDecimal quantitysDto) {
		this.quantitys = quantitysDto.intValue();
	}
}
