package com.apical.oddm.facade.manufacture.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Description;

public class ManufactureCommand implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date dateShipment;//出货日期
	private String drafter;//拟稿
	private String auditor;//审核
	private Date dateIssue;//发行日期
	private String approver;//批准
	private String remark;//备注
	private String orderNo;//订单号
	private String clientName;//顾客名称及
	private String timestamp;//创建时间
	private String updatetime;//更新时间
	private Integer state;//1-录入；2-完结
	private String notice;//注意事项
	private String version;//版本号
	private Integer orderId;
	
	private Integer unread; //1未读，供传查询用，页面只显示1未读即可
	
	private ManufactureOsCommand manufactureOsCommand ;
	
	private List<ManufactureFittingCommand> manufactureFittingCommands = new ArrayList<ManufactureFittingCommand>();
	
	private List<ManufacturePackageCommand> manufacturePackageCommands = new ArrayList<ManufacturePackageCommand>();
	
	private List<ManufactureShellCommand> manufactureShellCommands = new ArrayList<ManufactureShellCommand>();

	public Integer getUnread() {
		return unread;
	}

	public void setUnread(Integer unread) {
		this.unread = unread;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Description(value="拟稿")
	public String getDrafter() {
		return drafter;
	}

	public void setDrafter(String drafter) {
		this.drafter = drafter;
	}

	@Description(value="审核")
	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	@Description(value="出货日期")
	public Date getDateShipment() {
		return dateShipment;
	}

	public void setDateShipment(Date dateShipment) {
		this.dateShipment = dateShipment;
	}

	@Description(value="发行日期")
	public Date getDateIssue() {
		return dateIssue;
	}

	public void setDateIssue(Date dateIssue) {
		this.dateIssue = dateIssue;
	}

	@Description(value="批准")
	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	@Description(value="备注")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	@Description(value="其他注意事项")
	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	@Description(value="版本")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public ManufactureOsCommand getManufactureOsCommand() {
		return manufactureOsCommand;
	}

	public void setManufactureOsCommand(ManufactureOsCommand manufactureOsCommand) {
		this.manufactureOsCommand = manufactureOsCommand;
	}

	public List<ManufactureFittingCommand> getManufactureFittingCommands() {
		return manufactureFittingCommands;
	}

	public void setManufactureFittingCommands(List<ManufactureFittingCommand> manufactureFittingCommands) {
		this.manufactureFittingCommands = manufactureFittingCommands;
	}

	public List<ManufacturePackageCommand> getManufacturePackageCommands() {
		return manufacturePackageCommands;
	}

	public void setManufacturePackageCommands(List<ManufacturePackageCommand> manufacturePackageCommands) {
		this.manufacturePackageCommands = manufacturePackageCommands;
	}

	public List<ManufactureShellCommand> getManufactureShellCommands() {
		return manufactureShellCommands;
	}

	public void setManufactureShellCommands(List<ManufactureShellCommand> manufactureShellCommands) {
		this.manufactureShellCommands = manufactureShellCommands;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "ManufactureCommand [id="
				+ id
				+ ", dateShipment="
				+ dateShipment
				+ ", drafter="
				+ drafter
				+ ", auditor="
				+ auditor
				+ ", dateIssue="
				+ dateIssue
				+ ", approver="
				+ approver
				+ ", remark="
				+ remark
				+ ", orderNo="
				+ orderNo
				+ ", clientName="
				+ clientName
				+ ", timestamp="
				+ timestamp
				+ ", updatetime="
				+ updatetime
				+ ", state="
				+ state
				+ ", notice="
				+ notice
				+ ", version="
				+ version
				+ ", manufactureOsCommand="
				+ manufactureOsCommand
				+ ", manufactureFittingCommands="
				+ (manufactureFittingCommands != null ? manufactureFittingCommands
						.subList(0, Math.min(manufactureFittingCommands.size(),
								maxLen)) : null)
				+ ", manufacturePackageCommands="
				+ (manufacturePackageCommands != null ? manufacturePackageCommands
						.subList(0, Math.min(manufacturePackageCommands.size(),
								maxLen)) : null)
				+ ", manufactureShellCommands="
				+ (manufactureShellCommands != null ? manufactureShellCommands
						.subList(0, Math.min(manufactureShellCommands.size(),
								maxLen)) : null) + "]";
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
}
