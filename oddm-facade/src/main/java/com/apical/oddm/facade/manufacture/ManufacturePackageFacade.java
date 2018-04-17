package com.apical.oddm.facade.manufacture;

import com.apical.oddm.facade.manufacture.command.ManufacturePackageCommand;
import com.apical.oddm.facade.manufacture.dto.ManufacturePackageDTO;





/**
 * 生产任务书配件表操作接口
 * @author zzh
 */
public interface ManufacturePackageFacade  {
	/**
	 * 添加对象
	 * @param t
	 * @return
	 */
	public ManufacturePackageDTO add(ManufacturePackageCommand manufacturePackageCommand,Integer currUserId,String currUserName);
	/**
	 * 编辑对象，在数据库查出的对象前提下修改，如果不变化，则不会执行更新语句
	 * @param t
	 */
	public ManufacturePackageDTO edit(ManufacturePackageCommand manufacturePackageCommand,Integer currUserId,String currUserName);//使用merge(t)
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
	public ManufacturePackageDTO get(Integer id);
}
