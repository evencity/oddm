package com.apical.oddm.facade.manufacture;

import com.apical.oddm.facade.manufacture.command.ManufactureFittingCommand;
import com.apical.oddm.facade.manufacture.dto.ManufactureFittingDTO;



/**
 * 生产任务书配件表操作接口
 * @author zzh
 */
public interface ManufactureFittingFacade  {
	/**
	 * 添加对象
	 * @param t
	 * @return
	 */
	public ManufactureFittingDTO add(ManufactureFittingCommand manufactureFittingCommand,Integer currUserId,String currUserName);
	/**
	 * 编辑对象，在数据库查出的对象前提下修改，如果不变化，则不会执行更新语句
	 * @param t
	 */
	public ManufactureFittingDTO edit(ManufactureFittingCommand manufactureFittingCommand,Integer currUserId,String currUserName);//使用merge(t)
	/**
	 * 根据id删除对象
	 * @param id
	 */
	public void delete(Integer id,Integer state,Integer currUserId,String currUserName);
	/**
	 * 根据id加载对象，处理用户等是懒加载
	 * @param id
	 * @return 懒加载对象
	 */
	public ManufactureFittingDTO get(Integer id);
}
