package com.apical.oddm.facade.manufacture;

import com.apical.oddm.facade.manufacture.command.ManufactureShellCommand;
import com.apical.oddm.facade.manufacture.dto.ManufactureShellDTO;




/**
 * 生产任务书配件表操作接口
 * @author zzh
 */
public interface ManufactureShellFacade  {
	/**
	 * 添加对象
	 * @param t
	 * @return
	 */
	public ManufactureShellDTO add(ManufactureShellCommand manufactureShellCommand,Integer currUserId,String currUserName);
	/**
	 * 编辑对象，在数据库查出的对象前提下修改，如果不变化，则不会执行更新语句
	 * @param t
	 */
	public ManufactureShellDTO edit(ManufactureShellCommand manufactureShellCommand,Integer currUserId,String currUserName);//使用merge(t)
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
	public ManufactureShellDTO get(Integer id);
}
