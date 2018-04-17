package com.apical.oddm.facade.sale.pi;

import java.io.Serializable;
import java.util.List;

import com.apical.oddm.core.model.page.Pager;

/**
 * PI门面层接口
 * @author lgx
 * 2017-2-20
 */
public interface SalePiFacadeI {

	public Pager<SalePiDto> dataGrid(SalePiCmd salePiCmd);

	public Serializable add(SalePiCmd salePiCmd);
	
	public SalePiDto get(Integer id);

	public void edit(SalePiCmd salePiCmd);

	public void delete(Integer id);

	/**
	 * 通过制定参数查询单个pi记录
	 * @param salePi.setPiNo();
	 * @return
	 */
	public SalePiDto getSalePi(SalePiCmd salePiCmd);

	public List<SalePiDto> list(SalePiCmd salePiCmd);
}
