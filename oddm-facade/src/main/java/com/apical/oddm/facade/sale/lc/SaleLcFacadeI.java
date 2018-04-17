package com.apical.oddm.facade.sale.lc;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

import com.apical.oddm.core.model.page.Pager;

/**
 * LC门面层接口
 * @author lgx
 * 2017-2-20
 */
public interface SaleLcFacadeI {

	public Pager<SaleLcDto> dataGrid(SaleLcCmd saleLcCmd) throws ParseException;

	public void delete(Integer id);

	public Serializable add(SaleLcCmd saleLcCmd);

	public SaleLcDto get(Integer id);

	public void edit(SaleLcCmd saleLcCmd);

	public List<SaleLcDto> list(SaleLcCmd saleLcCmd) throws ParseException;

}
