package com.apical.oddm.core.constant;

/**
 * 文档状态类
 * @author lgx
 * 2017-3-2
 */
public class DocumentConst {

	public static final int unpublished = 1;//文档未发布
	
	public static final int uploaded  = 2;//业务未审核（上传成功后）

	public static final int approved  = 3;//审核通过

	public static final int rejected  = 4;//未通过审核

	public static final int artistDoc = 1; //美工文档
	
	public static final int otherDoc = 2; //结构文档 (其它文档)

}
