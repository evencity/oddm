package com.apical.oddm.core.constant;

/**
 * 工艺状态控制
 */
public class BomConst {
	/**
	 * 暂存，保留
	 */
	//public static final int temporarysave = 1;
	
	/**
	 * 保存，没提交审核前
	 */
	public static final int save = 2;

	/**
	 * 已经提交审核
	 */
	public static final int submitaudit = 3;

	/**
	 * 审核驳回 
	 */
	public static final int rejected = 4;

	/**
	 * 审核通过
	 */
	public static final int approved = 5;

	/**
	 * 已齐料
	 */
	public static final int materialfinished = 6;
	
	/**
	 * 完结归档
	 */
	public static final int archive = 7;

}
