package com.apical.oddm.facade.order.dto;

/**
 * @author lgx
 * 2016-2-28
 */
public class OrderInfoYearDto {
	
	private Integer quantitysM1 = 0;
	private Integer quantitysM2 = 0;
	private Integer quantitysM3 = 0;
	private Integer quantitysM4 = 0;
	private Integer quantitysM5 = 0;
	private Integer quantitysM6 = 0;
	private Integer quantitysM7 = 0;
	private Integer quantitysM8 = 0;
	private Integer quantitysM9 = 0;
	private Integer quantitysM10 = 0;
	private Integer quantitysM11 = 0;
	private Integer quantitysM12 = 0;

	private String name;
	
	private Integer total = 0;//单个销售1~12月份合计
	private Integer totals = 0; //全年总计
	private Float percent = 0f;// total/totals

	public Integer getQuantitysM1() {
		return quantitysM1;
	}

	public void setQuantitysM1(Integer quantitysM1) {
		this.quantitysM1 = quantitysM1;
	}

	public Integer getQuantitysM2() {
		return quantitysM2;
	}

	public void setQuantitysM2(Integer quantitysM2) {
		this.quantitysM2 = quantitysM2;
	}

	public Integer getQuantitysM3() {
		return quantitysM3;
	}

	public void setQuantitysM3(Integer quantitysM3) {
		this.quantitysM3 = quantitysM3;
	}

	public Integer getQuantitysM4() {
		return quantitysM4;
	}

	public void setQuantitysM4(Integer quantitysM4) {
		this.quantitysM4 = quantitysM4;
	}

	public Integer getQuantitysM5() {
		return quantitysM5;
	}

	public void setQuantitysM5(Integer quantitysM5) {
		this.quantitysM5 = quantitysM5;
	}

	public Integer getQuantitysM6() {
		return quantitysM6;
	}

	public void setQuantitysM6(Integer quantitysM6) {
		this.quantitysM6 = quantitysM6;
	}

	public Integer getQuantitysM7() {
		return quantitysM7;
	}

	public void setQuantitysM7(Integer quantitysM7) {
		this.quantitysM7 = quantitysM7;
	}

	public Integer getQuantitysM8() {
		return quantitysM8;
	}

	public void setQuantitysM8(Integer quantitysM8) {
		this.quantitysM8 = quantitysM8;
	}

	public Integer getQuantitysM9() {
		return quantitysM9;
	}

	public void setQuantitysM9(Integer quantitysM9) {
		this.quantitysM9 = quantitysM9;
	}

	public Integer getQuantitysM10() {
		return quantitysM10;
	}

	public void setQuantitysM10(Integer quantitysM10) {
		this.quantitysM10 = quantitysM10;
	}

	public Integer getQuantitysM11() {
		return quantitysM11;
	}

	public void setQuantitysM11(Integer quantitysM11) {
		this.quantitysM11 = quantitysM11;
	}

	public Integer getQuantitysM12() {
		return quantitysM12;
	}

	public void setQuantitysM12(Integer quantitysM12) {
		this.quantitysM12 = quantitysM12;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Float getPercent() {
		return percent;
	}

	public void setPercent(Float percent) {
		this.percent = percent;
	}

	public Integer getTotals() {
		return totals;
	}

	public void setTotals(Integer totals) {
		this.totals = totals;
	}
	
}
