package com.apical.oddm.facade.sale.saleInfo.dto;

/**
 * @author lgx
 * 2016-2-28
 */
public class SaleInfoYearDto {
	
	private Double quantitysM1 = 0D;
	private Double quantitysM2 = 0D;
	private Double quantitysM3 = 0D;
	private Double quantitysM4 = 0D;
	private Double quantitysM5 = 0D;
	private Double quantitysM6 = 0D;
	private Double quantitysM7 = 0D;
	private Double quantitysM8 = 0D;
	private Double quantitysM9 = 0D;
	private Double quantitysM10 = 0D;
	private Double quantitysM11 = 0D;
	private Double quantitysM12 = 0D;

	private String name;
	
	private Double total = 0D;//单个销售1~12月份合计
	private Double totals = 0D; //全年总计
	private Double percent = 0D;// total/totals

	public Double getQuantitysM1() {
		return quantitysM1;
	}

	public void setQuantitysM1(Double quantitysM1) {
		this.quantitysM1 = quantitysM1;
	}

	public Double getQuantitysM2() {
		return quantitysM2;
	}

	public void setQuantitysM2(Double quantitysM2) {
		this.quantitysM2 = quantitysM2;
	}

	public Double getQuantitysM3() {
		return quantitysM3;
	}

	public void setQuantitysM3(Double quantitysM3) {
		this.quantitysM3 = quantitysM3;
	}

	public Double getQuantitysM4() {
		return quantitysM4;
	}

	public void setQuantitysM4(Double quantitysM4) {
		this.quantitysM4 = quantitysM4;
	}

	public Double getQuantitysM5() {
		return quantitysM5;
	}

	public void setQuantitysM5(Double quantitysM5) {
		this.quantitysM5 = quantitysM5;
	}

	public Double getQuantitysM6() {
		return quantitysM6;
	}

	public void setQuantitysM6(Double quantitysM6) {
		this.quantitysM6 = quantitysM6;
	}

	public Double getQuantitysM7() {
		return quantitysM7;
	}

	public void setQuantitysM7(Double quantitysM7) {
		this.quantitysM7 = quantitysM7;
	}

	public Double getQuantitysM8() {
		return quantitysM8;
	}

	public void setQuantitysM8(Double quantitysM8) {
		this.quantitysM8 = quantitysM8;
	}

	public Double getQuantitysM9() {
		return quantitysM9;
	}

	public void setQuantitysM9(Double quantitysM9) {
		this.quantitysM9 = quantitysM9;
	}

	public Double getQuantitysM10() {
		return quantitysM10;
	}

	public void setQuantitysM10(Double quantitysM10) {
		this.quantitysM10 = quantitysM10;
	}

	public Double getQuantitysM11() {
		return quantitysM11;
	}

	public void setQuantitysM11(Double quantitysM11) {
		this.quantitysM11 = quantitysM11;
	}

	public Double getQuantitysM12() {
		return quantitysM12;
	}

	public void setQuantitysM12(Double quantitysM12) {
		this.quantitysM12 = quantitysM12;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public Double getTotals() {
		return totals;
	}

	public void setTotals(Double totals) {
		this.totals = totals;
	}
	
}
