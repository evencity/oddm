package com.apical.oddm.core.constant;

/**
 * 内部订单
 * @author lgx
 * 2017-1-12
 */
public class SalePoConst {
	/**
	 * 保存，没提交审核前
	 */
	public static final int save = 1;

	/**
	 * 已经提交审核
	 */
	public static final int submitaudit = 2;

	/**
	 * 审核驳回 
	 */
	public static final int rejected = 3;
	
	/**
	 * 审核通过
	 */
	public static final int approved = 4;
	
	/**
	 * 完结归档
	 */
	public static final int archive = 5;
}
