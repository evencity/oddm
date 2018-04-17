package com.apical.oddm.facade.sale.proto;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

import com.apical.oddm.core.model.page.Pager;

/**
 * 样机门面层接口
 * @author lgx
 * 2017-2-20
 */
public interface SaleProtoFacadeI {

	public Pager<SaleProtoDto> dataGrid(SaleProtoCmd saleProtoCmd) throws ParseException;

	public void delete(Integer id);

	public SaleProtoDto get(Integer id);

	public void edit(SaleProtoCmd saleProtoCmd);

	public Serializable add(SaleProtoCmd saleProtoCmd);

	public List<SaleProtoDto> list(SaleProtoCmd saleProtoCmd) throws ParseException;
	

}
