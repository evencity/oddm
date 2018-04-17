package com.apical.oddm.facade.sys;

import java.util.List;

import com.apical.oddm.facade.sys.command.OrganizationCommand;
import com.apical.oddm.facade.sys.dto.OrganizationDTO;
import com.apical.oddm.facade.sys.dto.UserDeptDTO;




public interface OrganizationFacade {


	/**
	 * 查询全部机构
	 * @return 返回机构列表
	 */
	public List<OrganizationDTO> treeGrid();

	/**
	 * 查询全部机构(包含用户)
	 * @return 返回机构列表
	 */
	public List<UserDeptDTO> usersList();
	
	/**
	 * 查询全部公司
	 * @return 返回机构列表
	 */
	public List<OrganizationDTO> companyList();
	
	/**
	 * 添加机构
	 * @param organization
	 * @return
	 */
	public Boolean add(OrganizationCommand organizationCommand);

	/**
	 * 删除机构
	 * @param id 机构表id
	 */
	public void delete(Integer id);

	/**
	 * 更新机构信息
	 * @param organization
	 */
	public void edit(OrganizationCommand organizationCommand);

	/**
	 * 查询指定机构
	 * @param id 机构表id
	 * @return
	 */
	public OrganizationDTO get(Integer id);

}
