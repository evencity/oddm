package com.apical.oddm.core.vo.order;

import java.math.BigDecimal;

/**
 * 定义根据业务名、客户名、产品类型查询pojo
 * @author lgx
 * 2014-2-28
 */
public class OrderInfoYearVo {
	public static final int sellerName = 1;//业务员名
	public static final int clientName = 2;//客户名
	public static final int productName = 3;//产品类型 PND MVR MID等
	public static final int districtName = 4;//国家地区
	public static final int productFactory =5;//工厂机型

	private String name;
	
	private Integer quantitys;
	
	private Integer month;

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

/*	public BigDecimal getQuantitysDto() {
		return quantitysDto;
	}*/

	public void setQuantitysDto(BigDecimal quantitysDto) {
		this.quantitys = quantitysDto.intValue();
		//this.quantitysDto = quantitysDto;
	}
}
