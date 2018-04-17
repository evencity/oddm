package com.apical.oddm.facade.manufacture;

import com.apical.oddm.core.model.produce.Manufacture;
import com.apical.oddm.core.model.produce.ManufactureFitting;
import com.apical.oddm.core.model.produce.ManufacturePackage;
import com.apical.oddm.core.model.produce.ManufactureShell;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.manufacture.command.ManufactureCommand;
import com.apical.oddm.facade.manufacture.command.ManufactureFittingCommand;
import com.apical.oddm.facade.manufacture.command.ManufacturePackageCommand;
import com.apical.oddm.facade.manufacture.command.ManufactureShellCommand;
import com.apical.oddm.facade.manufacture.dto.ManufactureAltDTO;


public interface ManufactureAltFacade {

	/**
	 * 分页获取
	 * @param mftId
	 * @return 
	 */
	public BasePage<ManufactureAltDTO> dataGrid(Integer id,PageCommand pageCommand);
	
	/**********************改*********************/
	/**
	 * 计算变更记录,总提交按钮调用
	 * @param manufactureBefore
	 * @param manufactureCommand
	 * @param currUserId 当前用户id
	 */
	public void editAllRecord(Manufacture manufactureBefore, 
			ManufactureCommand manufactureCommand, Integer currUserId, String currUserName);
	/**
	 * 外壳
	 * @param mftShellBefore
	 * @param mftShellCommand
	 * @param currUserId
	 */
	public void editShellRecord(ManufactureShell mftShellBefore, 
			ManufactureShellCommand mftShellCommand, Integer currUserId, String currUserName);
	/**
	 * 配件
	 * @param mftFittingBefore
	 * @param mftFittingCommand
	 * @param currUserId
	 */
	public void editFittingRecord(ManufactureFitting mftFittingBefore, 
			ManufactureFittingCommand mftFittingCommand, Integer currUserId, String currUserName);
	/**
	 * 包材
	 * @param mftPackageBefore
	 * @param mftPackageCommand
	 * @param currUserId
	 */
	public void editPackageRecord(ManufacturePackage mftPackageBefore, 
			ManufacturePackageCommand mftPackageCommand, Integer currUserId, String currUserName);
	
	/**********************增*********************/
	/**
	 * 记录增加外壳的变更记录
	 * @param mftShellCommand
	 * @param currUserId 当前用户id
	 */
	public void addShellRecord(ManufactureShellCommand mftShellCommand, Integer currUserId, String currUserName);
	/**
	 * 记录增加配件的变更记录
	 * @param mftFittingCommand
	 * @param currUserId 当前用户id
	 */
	public void addFittingRecord(ManufactureFittingCommand mftFittingCommand, Integer currUserId, String currUserName);
	/**
	 * 记录增加包材的变更记录
	 * @param mftPackageCommand
	 * @param currUserId 当前用户id
	 */
	public void addPackageRecord(ManufacturePackageCommand mftPackageCommand, Integer currUserId, String currUserName);
	
	/**********************删*********************/
	/**
	 * 外壳
	 * @param mftShellBefore
	 * @param mftShellCommand
	 * @param currUserId
	 */
	public void delShellRecord(ManufactureShell mftShellBefore, Integer currUserId, String currUserName);
	/**
	 * 配件
	 * @param mftFittingBefore
	 * @param mftFittingCommand
	 * @param currUserId
	 */
	public void delFittingRecord(ManufactureFitting mftFittingBefore, Integer currUserId, String currUserName);
	/**
	 * 包材
	 * @param mftPackageBefore
	 * @param mftPackageCommand
	 * @param currUserId
	 */
	public void delPackageRecord(ManufacturePackage mftPackageBefore, Integer currUserId, String currUserName);
	/********************************************/

	/**
	 * 增加生产任务书未读记录
	 * @param orderNo 
	 * @param currUserId 当前用户id
	 * @param orderId 
	 */
	public void addMftUnreadInfo(String orderNo , int currUserId, int mftId);
}
