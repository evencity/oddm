package com.apical.oddm.web.controller.token;

/**
oddm资源存储：
1）普通订单文档：
document/order/2016/订单号/文件名
2）通用文档：
document/common/2016/文件名
3）生产任务书图片：
picture/produce/2016/订单号/文件名
4）首件图片：
picture/prototype/2016/订单号/文件名

 * 网页端请求token传以下参数
 * @author lgx
 * 2016-11-14
 */
public class TokenCmd {

	private String fileType;
	
	private String fileDetail;

	private String year;
	
	private String orderNo;

	private String filePath;

	public String getFileType() {
		return fileType;
	}

	/**
	 * 第一级目录document、picture
	 * @param fileType 文件类型
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileDetail() {
		return fileDetail;
	}

	/**
	 * 第二级目录 ：order、common、produce、prototype等
	 * @param fileDetail
	 */
	public void setFileDetail(String fileDetail) {
		this.fileDetail = fileDetail;
	}

	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo 订单号
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getFilePath() {
		return filePath;
	}

	/**
	 * 文件全路径，下载查询只传这个参数，其他不需传
	 * @param filePath 
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getYear() {
		return year;
	}

	/**
	 * 传的年一定要对，否则读取不出文件，建议使用订单的创建时间解析成年
	 * @param year 2016、2017....
	 */
	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "TokenCmd [fileType=" + fileType + ", fileDetail=" + fileDetail
				+ ", year=" + year + ", orderNo=" + orderNo + ", filePath="
				+ filePath + "]";
	}

}
