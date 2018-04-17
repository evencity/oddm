package com.apical.oddm.facade.encase.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.encase.EncaseInfoServiceI;
import com.apical.oddm.application.encase.EncaseListServiceI;
import com.apical.oddm.core.model.encase.EncaseInfo;
import com.apical.oddm.core.model.encase.EncaseList;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.encase.assembler.EncaseAssembler;
import com.apical.oddm.facade.encase.assembler.EncaseListAssembler;
import com.apical.oddm.facade.encase.dto.EncaseInfoDTO;
import com.apical.oddm.facade.encase.dto.EncaseListDTO;
import com.apical.oddm.facade.encase.facade.EncaseFacade;
import com.apical.oddm.facade.pageModel.DataGrid;
import com.apical.oddm.facade.util.TimeUtil;

@Component("encaseFacade")
public class EncaseFacadeImpl implements EncaseFacade {

	@Autowired 
	private EncaseInfoServiceI encaseInfoServiceI;
	
	@Autowired 
	private EncaseListServiceI encaseListServiceI;
	
	@Override
	public DataGrid getEncaseInfoInPage(EncaseInfoDTO encaseDTO,
		 PageCommand pageCommand) {
		 DataGrid dg = new DataGrid();
		 int page = pageCommand.getPage();
		 int rows = pageCommand.getRows();
		 String name = "";
		 Date encaseDate = null;
		 String company = "";
		 
		 System.out.println("第几页："+page);
		 System.out.println("每页显示："+rows);
		
		 SystemContext.setPageOffset(page);
		 SystemContext.setPageSize(rows);
		
		 if(encaseDTO.getName() != null && !"".equals(encaseDTO.getName())){
			 name = encaseDTO.getName();
		 }
		 if(encaseDTO.getEncaseDate() != null && !"".equals(encaseDTO.getEncaseDate())){
			 encaseDate = TimeUtil.stringToDate(encaseDTO.getEncaseDate());
		 }
		 if(encaseDTO.getCompany() != null && !"".equals(encaseDTO.getCompany())){
			 company = encaseDTO.getCompany();
		 }
		 
		/* Pager<Object[]> dataGrid = encaseInfoServiceI.dataArray();
		 List<EncaseInfoDTO> encaseInfos = new ArrayList<EncaseInfoDTO>();
		 for (Object[] o : dataGrid.getRows()) {
			encaseInfos.add(EncaseAssembler.toEncaseInfoDTO((EncaseInfo)o[0],(Long)o[1],(Long)o[2]));
		 }
		 System.out.println("总个数："+dataGrid.getTotal());
		 SystemContext.setPageOffset(null);
		 SystemContext.setPageSize(null);
		 Map<String, Integer> map = new HashMap<String, Integer>();
		 map.put("pageSize", rows);   //页面的大小
		 map.put("curRow",(page-1)*rows);  //当前记录数
		 dg.setRows(encaseInfos);    //设置数据
		 dg.setTotal(Long.valueOf(dataGrid.getTotal()));   //设置数据表格记录数
*/		 return dg;
	}

	@Override
	public boolean addEncaseInfo(EncaseInfoDTO encaseInfo){
		
		EncaseInfo encaseInfoz = new EncaseInfo();
		encaseInfoz.setName(encaseInfo.getName());
		
		System.out.println("time:"+encaseInfo.getEncaseDate());
		System.out.println("time:"+encaseInfo.getTimestamp());
		encaseInfoz.setEncaseDate(TimeUtil.stringToDate(encaseInfo.getEncaseDate()));
		encaseInfoz.setTimestamp(TimeUtil.stringToTimestamp(encaseInfo.getTimestamp()));
		encaseInfoz.setCompany(encaseInfo.getCompany());
		encaseInfoz.setAddress(encaseInfo.getAddress());
		encaseInfoz.setZipcode(encaseInfo.getZipcode());
		encaseInfoz.setTelphone(encaseInfo.getTelphone());
		encaseInfoz.setHomepage(encaseInfo.getHomepage());
		encaseInfoz.setDescription(encaseInfo.getDescription());
		encaseInfoz.setUnitWeight(encaseInfo.getUnitWeight());
		encaseInfoz.setUnitLength(encaseInfo.getUnitLength());
			
		Set<EncaseList>	 set = new HashSet<EncaseList>();
			
		for(EncaseListDTO s : encaseInfo.getEncaseList()){
			EncaseList  encaseListz = new EncaseList();
			encaseListz.setCNo(s.getCNo());
			encaseListz.setItemNo(s.getItemNo());
			encaseListz.setDescription(s.getDescription());
			encaseListz.setQty(s.getQty());
			encaseListz.setUnit(s.getUnit());
			encaseListz.setRemark(s.getRemark());
			encaseListz.setOrderNo(s.getOrderNo());
			encaseListz.setProductFatory(s.getProductFatory());
			encaseListz.setQtyCtn(s.getQtyCtn());
			encaseListz.setNW(s.getnW());
			encaseListz.setGW(s.getgW());
			encaseListz.setLength(s.getLength());
			encaseListz.setWidth(s.getWidth());
			encaseListz.setHight(s.getHight());
			encaseListz.setEncaseInfo(encaseInfoz);
			set.add(encaseListz);
		}
			encaseInfoz.setEncaseLists(set);
			encaseInfoServiceI.add(encaseInfoz);
			
		return true;
	}

	@Override
	public boolean updateEncaseInfo(EncaseInfoDTO encaseInfo) {
		EncaseInfo encaseInfoz = encaseInfoServiceI.getDetail(encaseInfo.getId());
		encaseInfoz.setName(encaseInfo.getName());
		System.out.println("================"+encaseInfo.getTimestamp());
		encaseInfoz.setEncaseDate(TimeUtil.stringToDate(encaseInfo.getEncaseDate()));
		encaseInfoz.setTimestamp(TimeUtil.stringToTimestamp(encaseInfo.getTimestamp()+""));
		encaseInfoz.setCompany(encaseInfo.getCompany());
		encaseInfoz.setAddress(encaseInfo.getAddress());
		encaseInfoz.setZipcode(encaseInfo.getZipcode());
		encaseInfoz.setTelphone(encaseInfo.getTelphone());
		encaseInfoz.setHomepage(encaseInfo.getHomepage());
		encaseInfoz.setDescription(encaseInfo.getDescription());
		encaseInfoz.setUnitWeight(encaseInfo.getUnitWeight());
		encaseInfoz.setUnitLength(encaseInfo.getUnitLength());
			
		Map<Integer, EncaseList> tempMap = new HashMap<>();
		for (EncaseList encaseList : encaseInfoz.getEncaseLists()) {
			tempMap.put(encaseList.getId(), encaseList);
		}
		
		Set<EncaseList>	 set = new HashSet<EncaseList>();
		
		for(EncaseListDTO s : encaseInfo.getEncaseList()){
			
			if(s.getId() == null){
				System.out.println("~~~~~~~~~~"+s.getId());
				System.out.println("~~~~~~~~~~"+s.getCNo());
				System.out.println("~~~~~~~~~~"+s.getgW());
				EncaseList  encaseListz = new EncaseList();
				encaseListz.setCNo(s.getCNo());
				encaseListz.setItemNo(s.getItemNo());
				encaseListz.setDescription(s.getDescription());
				encaseListz.setQty(s.getQty());
				encaseListz.setUnit(s.getUnit());
				encaseListz.setRemark(s.getRemark());
				encaseListz.setOrderNo(s.getOrderNo());
				encaseListz.setProductFatory(s.getProductFatory());
				encaseListz.setQtyCtn(s.getQtyCtn());
				encaseListz.setNW(s.getnW());
				encaseListz.setGW(s.getgW());
				encaseListz.setLength(s.getLength());
				encaseListz.setWidth(s.getWidth());
				encaseListz.setHight(s.getHight());
				encaseListz.setEncaseInfo(encaseInfoz);
				set.add(encaseListz);
			}else{
				System.out.println("----------"+s.getId());
				System.out.println("----------"+s.getCNo());
				System.out.println("----------"+s.getgW());
				EncaseList  encaseListz = tempMap.get(s.getId());
				encaseListz.setCNo(s.getCNo());
				encaseListz.setItemNo(s.getItemNo());
				encaseListz.setDescription(s.getDescription());
				encaseListz.setQty(s.getQty());
				encaseListz.setUnit(s.getUnit());
				encaseListz.setRemark(s.getRemark());
				encaseListz.setOrderNo(s.getOrderNo());
				encaseListz.setProductFatory(s.getProductFatory());
				encaseListz.setQtyCtn(s.getQtyCtn());
				encaseListz.setNW(s.getnW());
				encaseListz.setGW(s.getgW());
				encaseListz.setLength(s.getLength());
				encaseListz.setWidth(s.getWidth());
				encaseListz.setHight(s.getHight());
				encaseListz.setEncaseInfo(encaseInfoz);
				set.add(encaseListz);
				tempMap.remove(tempMap.get(s.getId()));
			}
		}
		for (EncaseList h : tempMap.values()) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
			System.out.println(h.getCNo());
			encaseInfoz.getEncaseLists().remove(h);
			
		}
			encaseInfoz.setEncaseLists(set);
			encaseInfoServiceI.edit(encaseInfoz);
		return true;
	}

	@Override
	public boolean delEncaseInfo(int ids) {
		encaseInfoServiceI.delete(ids);
		return true;
	}

	@Override
	public EncaseInfoDTO getEncaseInfoById(int id) {
		EncaseInfoDTO encaseInfo = EncaseAssembler.toEncaseInfoDTO(encaseInfoServiceI.get(id));
		System.out.println("id:"+id);
		List<EncaseListDTO> it= EncaseListAssembler.toEncaseListDTOs(encaseInfoServiceI.getDetail(id).getEncaseLists());
	      
        encaseInfo.setEncaseList(it);  
        return encaseInfo;
	}

	@Override
	public boolean delEncaseList(int id) {
		try {
			encaseListServiceI.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
