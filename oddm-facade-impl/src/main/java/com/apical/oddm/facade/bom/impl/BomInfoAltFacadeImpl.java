package com.apical.oddm.facade.bom.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.bom.BomInfoAlterServiceI;
import com.apical.oddm.application.bom.BomInfoServiceI;
import com.apical.oddm.application.bom.BomUnreadServiceI;
import com.apical.oddm.application.sys.UserServiceI;
import com.apical.oddm.core.constant.CrudConst;
import com.apical.oddm.core.model.bom.BomInfo;
import com.apical.oddm.core.model.bom.BomInfoAlt;
import com.apical.oddm.core.model.bom.BomMaterialContact;
import com.apical.oddm.core.model.bom.BomMaterialRef;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.core.model.sys.User;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.bom.BomInfoAltFacade;
import com.apical.oddm.facade.bom.dto.BomDTO;
import com.apical.oddm.facade.bom.dto.BomInfoAltDTO;
import com.apical.oddm.facade.bom.dto.BomMaterialContactDTO;
import com.apical.oddm.facade.bom.dto.BomMaterialRefDTO;
import com.apical.oddm.facade.order.impl.OrderInfoAltFacadeImpl;
import com.apical.oddm.facade.pool.ThreadPoolBiz;
import com.apical.oddm.facade.util.FractionalUtil;
import com.apical.oddm.facade.util.TimeUtil;
import com.apical.oddm.facade.websocket.WebSocketMsg;
import com.apical.oddm.facade.websocket.WebSocketSessionUtils;

@Component("bomInfoAltFacade")
public class BomInfoAltFacadeImpl implements BomInfoAltFacade {

	private static final Logger log = LoggerFactory.getLogger(OrderInfoAltFacadeImpl.class);
	
	@Autowired
	private BomInfoAlterServiceI bomInfoAlterServiceI;
	
	@Autowired
	private BomInfoServiceI bomInfoServiceI;
	
	@Autowired
	private UserServiceI userService;
	
	@Autowired
	private BomUnreadServiceI bomUnreadService ;
	
	private static FastDateFormat dayFmat = FastDateFormat.getInstance("yyyy-MM-dd");

	@Override
	public BasePage<BomInfoAltDTO> dataGrid(Integer BomId, PageCommand pageCommand) {
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		SystemContext.setSort("t." + pageCommand.getSort());
		
		Pager<BomInfoAlt> dataGrid = bomInfoAlterServiceI.dataGrid(BomId);
		BasePage<BomInfoAltDTO> basePage = new BasePage<BomInfoAltDTO>();
		if(dataGrid != null && dataGrid.getRows().size() > 0){
			List<BomInfoAltDTO> list = new ArrayList<BomInfoAltDTO>();
			for(BomInfoAlt BomInfoAlter : dataGrid.getRows()){
				BomInfoAltDTO BomInfoAltDTO = new BomInfoAltDTO();
				BeanUtils.copyProperties(BomInfoAlter, BomInfoAltDTO);
				if(BomInfoAlter.getTimestamp() != null){
					BomInfoAltDTO.setTimestamp(TimeUtil.timestampToStrings(BomInfoAlter.getTimestamp()));
				}
				list.add(BomInfoAltDTO);
			}
			basePage.setRows(list);
			basePage.setTotal(dataGrid.getTotal());
		}
		return basePage;
	}

	@Override
	public Boolean add(BomInfoAltDTO BomInfoAltDTO) {
		BomInfo BomInfo = bomInfoServiceI.get(BomInfoAltDTO.getId());
		BomInfoAlt BomInfoAlt = new BomInfoAlt();
		BeanUtils.copyProperties(BomInfoAlt,BomInfoAltDTO);
		BomInfoAlt.setBomInfo(BomInfo);
		
		Serializable add = bomInfoAlterServiceI.add(BomInfoAlt);
		if(add != null){
			return true;
		}
		return false;
	}

	/**
	 * 比较量字符串
	 * @param before 变更前
	 * @param after 变更后
	 * @param item 变更项
	 * @param alt 
	 * @param currUserId 当前登录用户
	 * @param orderNo 订单号
	 * @param i 统计更改的几项，为0则没有更改
	 */
	private void compareString(Object before, Object after, String item, BomInfoAlt alt, String currUserId, String orderNo, AtomicInteger i) {
		//System.err.println("beforebeforebefore: " + before+"\t ,after:"+after);
		String changeBe = null;
		String changeAf = null;
		//bug注意：1、null
		if (before instanceof Date) {// 格式化日期后再判断
			changeBe = dayFmat.format(before);
		} else if (before != null) {// null+"" 会变成 "null",小心
			changeBe = StringUtils.stripToNull(before+"");//会转成""
			if (changeBe != null)  changeBe = changeBe.replaceAll("\r|\n", "");
		}
		if (after instanceof Date) {// 格式化日期后再判断
			changeAf = dayFmat.format(after);
		} else if (after != null) {
			changeAf = StringUtils.stripToNull(after+""); //会转成""
			if (changeAf != null) changeAf = changeAf.replaceAll("\r|\n", "");
		}
		//bug注意：2、换行符
		if (StringUtils.equalsIgnoreCase(changeBe, changeAf)) {
			// x do nothing
			//System.err.println("do nothing " + item);
			return;
		}
		alt = new BomInfoAlt(alt.getBomInfo(), alt.getOperator());
		alt.setAlteritem(item);
		if (changeBe == null && changeAf != null) {
			// 增
			alt.setBeforeInfo(null);
			alt.setAfterInfo(changeAf);
			alt.setOperateType(CrudConst.add);
		} else if (changeBe != null && changeAf == null) {
			// 删
			alt.setBeforeInfo(changeBe);
			alt.setAfterInfo(null);
			alt.setOperateType(CrudConst.delete);
		} else {
			// 改
			alt.setBeforeInfo(changeBe);
			alt.setAfterInfo(changeAf);
			alt.setOperateType(CrudConst.update);
		}
		try {
			i.incrementAndGet();
			bomInfoAlterServiceI.add(alt);
		} catch (Exception e) {
			log.error("变更记录错误："+orderNo, e);
		}
		
	}

	@Override
	public void addEditRecord(BomInfo bomInfoBefore, BomDTO bomDTO, Integer currUserId1,String currUserName) {
		// TODO Auto-generated method stub
		if (bomInfoBefore == null) {
			return;
		}
		ThreadPoolBiz.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				BomInfoAlt bomInfoAlt = null;
				Integer bomId = null;
				String orderNo = null;
				String currUserId = currUserId1 + "";
				String clientNameCode = bomInfoBefore.getClientNameCode();
				AtomicInteger i = new AtomicInteger(0);// 统计变成项的多少，更新订单为未读
				
				try {
					bomId = bomInfoBefore.getId();
					orderNo = bomInfoBefore.getOrderInfo().getOrderNo();
					
					bomInfoAlt = new BomInfoAlt(new BomInfo(bomId), currUserName);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				// 基本信息
				try {
					Class<? extends BomDTO> cl = bomDTO.getClass();
					Method[] methods = cl.getMethods();
					for (Method m : methods) {
						for (Annotation a : m.getAnnotations()) {
							if (a instanceof Description) {
								compareString(bomInfoBefore.getClass().getMethod(m.getName(), new Class[0]).invoke(bomInfoBefore, new Object[0]),
										m.invoke(bomDTO, new Object[0]), ((Description) a).value(), bomInfoAlt, currUserId, orderNo ,i);
							}
						}
					}
				} catch (Exception e) {
					log.error("计算Bom信息变更记录错误"+bomId, e);
				}
				
				try {
					// 物料
					String changeItem = "";
					String materialCodeString = "物料编码:";
					String productNameString = "；品名：";
					String brandString = "；品牌：";
					String usageAmountString = "；用量：";
					String specificationString = "；规格型号及封装：";
					String descriptionString = "；描述：";
					
					String companyString = "；厂家：";
					String contactsString = "；姓名：";
					String telphoneString = "；座机号：";
					String cellphoneString = "；手机号：";
					String emailString = "；邮箱：";
					String faxString = "；传真：";
					Set<BomMaterialRef> beforeSet = bomInfoBefore.getBomMaterialRefs();
					if (bomDTO.getBomMaterialRefDTOs() != null && !bomDTO.getBomMaterialRefDTOs().isEmpty()) {
						
						List<BomMaterialRefDTO> afterSet = bomDTO.getBomMaterialRefDTOs();
						if (beforeSet == null || beforeSet.isEmpty()) {// 原有数据为空，则全部是增加的
							String title = "-[标题]";
							for (BomMaterialRefDTO after : afterSet) {
								int type = after.getType().intValue();
								int seq = after.getSeq().intValue();
								//返回对应类型的值
								changeItem = typeToString(type);
								if(seq == 0 ){//标题
									compareString("",materialCodeString+after.getMaterialCode() + productNameString + after.getProductName() + specificationString +  after.getSpecification() + descriptionString + after.getDescription(),
											changeItem+title,bomInfoAlt,currUserId,orderNo,i);
								}else{
									//普通物料
									String usageAmount = "";
									if(after.getUsageAmount1() != null && after.getUsageAmount2() != null){
										usageAmount = FractionalUtil.showToString(after.getUsageAmount1(), after.getUsageAmount2())+"";
									}
									String afterString = materialCodeString+after.getMaterialCode() + productNameString + after.getProductName() + specificationString +  after.getSpecification() + brandString + after.getBrand() + usageAmountString + usageAmount + descriptionString + after.getDescription();
									if(after.getMaterialContactDTO() != null){
										BomMaterialContactDTO contact = after.getMaterialContactDTO();
										afterString += "联系人：["+companyString+contact.getCompany()+contactsString+contact.getContacts()+telphoneString+contact.getTelphone()+cellphoneString+contact.getCellphone()+emailString+contact.getEmail()+faxString+contact.getFax()+"]";
									}
									compareString("",afterString,changeItem,bomInfoAlt,currUserId,orderNo,i);
								}
								
							}
						}else{
							Map<Integer, BomMaterialRef> tempMap = new HashMap<>();
							for (BomMaterialRef before : beforeSet) {
								tempMap.put(before.getId(), before);
							}
							String title = "-[标题]";
							for (BomMaterialRefDTO after : afterSet) {
								int type = after.getType().intValue();
								int seq = after.getSeq().intValue();
								//返回对应类型的值
								changeItem = typeToString(type);
								
								if(after.getId() == null){//增加
									if(seq == 0 ){//标题
										compareString("",materialCodeString+after.getMaterialCode() + productNameString + after.getProductName() + specificationString +  after.getSpecification() + descriptionString + after.getDescription(),
												changeItem+title,bomInfoAlt,currUserId,orderNo,i);
									}else{
										//普通物料
										/*
										String usageAmount = "";
										if(after.getUsageAmount1() != null && after.getUsageAmount2() != null){
											usageAmount = FractionalUtil.showToString(after.getUsageAmount1(), after.getUsageAmount2())+"";
										}
										String afterString = materialCodeString+after.getMaterialCode() + productNameString + after.getProductName() + specificationString +  after.getSpecification() + brandString + after.getBrand() + usageAmountString + usageAmount + descriptionString + after.getDescription();
										if(after.getMaterialContactDTO() != null){
											BomMaterialContactDTO contact = after.getMaterialContactDTO();
											afterString += companyString+contact.getCompany()+contactsString+contact.getContacts()+telphoneString+contact.getTelphone()+cellphoneString+contact.getCellphone()+emailString+contact.getEmail()+faxString+contact.getFax();
										}
										compareString("",afterString,changeItem,bomInfoAlt,currUserId,orderNo,i);*/
										if(type != 9){
											String usageAmount = "";
											if(after.getUsageAmount1() != null && after.getUsageAmount2() != null){
												usageAmount = FractionalUtil.showToString(after.getUsageAmount1(), after.getUsageAmount2())+"";
											}
											String afterString = materialCodeString+after.getMaterialCode() + productNameString + after.getProductName() + specificationString +  after.getSpecification() + brandString + after.getBrand() + usageAmountString + usageAmount + descriptionString + after.getDescription();
											if(after.getMaterialContactDTO() != null){
												BomMaterialContactDTO contact = after.getMaterialContactDTO();
												afterString += companyString+contact.getCompany()+contactsString+contact.getContacts()+telphoneString+contact.getTelphone()+cellphoneString+contact.getCellphone()+emailString+contact.getEmail()+faxString+contact.getFax();
											}
											compareString("",afterString,changeItem,bomInfoAlt,currUserId,orderNo,i);
										}else{
											String usageAmount = "";
											if(after.getUsageAmount1() != null && after.getUsageAmount2() != null){
												usageAmount = FractionalUtil.showToString(after.getUsageAmount1(), after.getUsageAmount2())+"";
											}
											String afterString =  "品名：" + after.getProductName() + specificationString +  after.getSpecification() + usageAmountString + usageAmount ;
											compareString("",afterString,changeItem,bomInfoAlt,currUserId,orderNo,i);
										}
									}
								}else{// 删除或者修改
									BomMaterialRef bomMaterialRef = tempMap.get(after.getId());
									Class<? extends BomMaterialRefDTO> cl = after.getClass();
									Method[] methods = cl.getMethods();
									for (Method m : methods) {
										for (Annotation a : m.getAnnotations()) {
											if (a instanceof Description) {
												
												if(seq == 0 ){//标题
													if(m.getName().equals("getUsageAmount")){
														continue;
													}
													if(m.getName().equals("getBrand")){
														continue;
													}
													compareString(bomMaterialRef.getClass().getMethod(m.getName(), new Class[0]).invoke(bomMaterialRef, new Object[0]), m.invoke(after, new Object[0]), changeItem +title + after.getMaterialCode() +"-"+((Description) a).value(), bomInfoAlt, currUserId, orderNo, i);
												}else{
													/*if(m.getName().equals("getUsageAmount")){
														String usageAmount = "";
														if(after.getUsageAmount1() != null && after.getUsageAmount2() != null){
															usageAmount = FractionalUtil.showToString(after.getUsageAmount1(), after.getUsageAmount2())+"";
														}
														compareString(bomMaterialRef.getClass().getMethod(m.getName(), new Class[0]).invoke(bomMaterialRef, new Object[0]), usageAmount, changeItem + after.getMaterialCode() +"-"+((Description) a).value(), bomInfoAlt, currUserId, orderNo, i);
													}else {
														compareString(bomMaterialRef.getClass().getMethod(m.getName(), new Class[0]).invoke(bomMaterialRef, new Object[0]), m.invoke(after, new Object[0]), changeItem + after.getMaterialCode() +"-"+((Description) a).value(), bomInfoAlt, currUserId, orderNo, i);
													}*/
													if(type != 9){
														if(m.getName().equals("getUsageAmount")){
															String usageAmount = "";
															if(after.getUsageAmount1() != null && after.getUsageAmount2() != null){
																usageAmount = FractionalUtil.showToString(after.getUsageAmount1(), after.getUsageAmount2())+"";
															}
															compareString(bomMaterialRef.getClass().getMethod(m.getName(), new Class[0]).invoke(bomMaterialRef, new Object[0]), usageAmount, changeItem + after.getMaterialCode() +"-"+((Description) a).value(), bomInfoAlt, currUserId, orderNo, i);
														}else {
															compareString(bomMaterialRef.getClass().getMethod(m.getName(), new Class[0]).invoke(bomMaterialRef, new Object[0]), m.invoke(after, new Object[0]), changeItem + after.getMaterialCode() +"-"+((Description) a).value(), bomInfoAlt, currUserId, orderNo, i);
														}
													}else {
														if(m.getName().equals("getMaterialCode")){
															continue;
														}
														if(m.getName().equals("getBrand")){
															continue;
														}
														
														if(m.getName().equals("getDescription")){
															continue;
														}
														if(m.getName().equals("getUsageAmount")){
															String usageAmount = "";
															if(after.getUsageAmount1() != null && after.getUsageAmount2() != null){
																usageAmount = FractionalUtil.showToString(after.getUsageAmount1(), after.getUsageAmount2())+"";
															}
															compareString(bomMaterialRef.getClass().getMethod(m.getName(), new Class[0]).invoke(bomMaterialRef, new Object[0]), usageAmount, changeItem  +"-"+((Description) a).value(), bomInfoAlt, currUserId, orderNo, i);
														}else {
															compareString(bomMaterialRef.getClass().getMethod(m.getName(), new Class[0]).invoke(bomMaterialRef, new Object[0]), m.invoke(after, new Object[0]), changeItem  +"-"+((Description) a).value(), bomInfoAlt, currUserId, orderNo, i);
														}
													}
												}
											}
										}
									}
									//修改则分 联系人的增加   删除  和 修改
									if(after.getMaterialContactDTO() != null){//变更后联系人不为空 -- 增加或修改联系人
										BomMaterialContactDTO materialContactDTO = after.getMaterialContactDTO();
										if( bomMaterialRef.getContacts() != null){ //变更前联系人不为空 -- 修改联系人
											//System.err.println("..........修改联系人.............");
											BomMaterialContact contacts = bomMaterialRef.getContacts();
											Class<? extends BomMaterialContactDTO> class1 = materialContactDTO.getClass();
											Method[] ms = class1.getMethods();
											for (Method m : ms) {
												for (Annotation a : m.getAnnotations()) {
													if (a instanceof Description) {
														//修改
														compareString(contacts.getClass().getMethod(m.getName(), new Class[0]).invoke(contacts, new Object[0]),
																m.invoke(materialContactDTO, new Object[0]), 
																changeItem  + after.getMaterialCode() +"-联系人-"+((Description) a).value(), bomInfoAlt, currUserId, orderNo, i);
													}
												}
											}
										}else{//变更前联系人为空 -- 增加联系人
											compareString("","姓名："+materialContactDTO.getContacts()+"；手机号："+materialContactDTO.getCellphone()+"；公司名："+materialContactDTO.getCompany()+"；座机号："+materialContactDTO.getTelphone()+"；邮箱："+materialContactDTO.getEmail()+"；传真："+materialContactDTO.getFax()+"；",
													changeItem  + after.getMaterialCode() +"-联系人", bomInfoAlt, currUserId, orderNo, i);
										}
										
									}else{//变更后联系人为空 -- 删除联系人
										if(seq > 0){//排除标题
											if(bomMaterialRef.getContacts() != null){
												BomMaterialContact contacts = bomMaterialRef.getContacts(); 
												compareString("姓名："+contacts.getContacts()+"；手机号："+contacts.getCellphone()+"；公司名："+contacts.getCompany()+"；座机号："+contacts.getTelphone()+"；邮箱："+contacts.getEmail()+"；传真："+contacts.getFax()+"；","",
														changeItem  + after.getMaterialCode() +"-联系人", bomInfoAlt, currUserId, orderNo, i);
											}
										}
									}
									
									
									tempMap.remove(after.getId());// 移除掉修改的，剩下的就应该删除操作
								}
								
							}
							for (BomMaterialRef bomMaterialRef : tempMap.values()) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
								int type = bomMaterialRef.getType().intValue();
								int seq = bomMaterialRef.getSeq().intValue();
								//返回对应类型的值
								changeItem = typeToString(type);
								if(seq == 0){
									String afterString = materialCodeString+bomMaterialRef.getMaterialCode() + productNameString + bomMaterialRef.getProductName() + specificationString +  bomMaterialRef.getSpecification()  + descriptionString + bomMaterialRef.getDescription();
									compareString(afterString,"",changeItem+title,bomInfoAlt,currUserId,orderNo,i);
								}else {
									/*String usageAmount = "";
									if(bomMaterialRef.getUsageAmount1() != null && bomMaterialRef.getUsageAmount2() != null){
										usageAmount = FractionalUtil.showToString(bomMaterialRef.getUsageAmount1(), bomMaterialRef.getUsageAmount2())+"";
									}
									String afterString = materialCodeString+bomMaterialRef.getMaterialCode() + productNameString + bomMaterialRef.getProductName() + specificationString +  bomMaterialRef.getSpecification() + brandString + bomMaterialRef.getBrand() + usageAmountString +usageAmount + descriptionString + bomMaterialRef.getDescription();
									if(bomMaterialRef.getContacts() != null){
										BomMaterialContact contact = bomMaterialRef.getContacts();
										afterString += companyString+contact.getCompany()+contactsString+contact.getContacts()+telphoneString+contact.getTelphone()+cellphoneString+contact.getCellphone()+emailString+contact.getEmail()+faxString+contact.getFax();
									}
									compareString(afterString,"",changeItem,bomInfoAlt,currUserId,orderNo,i);*/
									if(type != 9){
										String usageAmount = "";
										if(bomMaterialRef.getUsageAmount1() != null && bomMaterialRef.getUsageAmount2() != null){
											usageAmount = FractionalUtil.showToString(bomMaterialRef.getUsageAmount1(), bomMaterialRef.getUsageAmount2())+"";
										}
										String afterString = materialCodeString+bomMaterialRef.getMaterialCode() + productNameString + bomMaterialRef.getProductName() + specificationString +  bomMaterialRef.getSpecification() + brandString + bomMaterialRef.getBrand() + usageAmountString + usageAmount + descriptionString + bomMaterialRef.getDescription();
										if(bomMaterialRef.getContacts() != null){
											BomMaterialContact contact = bomMaterialRef.getContacts();
											afterString += companyString+contact.getCompany()+contactsString+contact.getContacts()+telphoneString+contact.getTelphone()+cellphoneString+contact.getCellphone()+emailString+contact.getEmail()+faxString+contact.getFax();
										}
										compareString(afterString,"",changeItem,bomInfoAlt,currUserId,orderNo,i);
									}else{
										String usageAmount = "";
										if(bomMaterialRef.getUsageAmount1() != null && bomMaterialRef.getUsageAmount2() != null){
											usageAmount = FractionalUtil.showToString(bomMaterialRef.getUsageAmount1(), bomMaterialRef.getUsageAmount2())+"";
										}
										String afterString =   "品名：" + bomMaterialRef.getProductName() + specificationString +  bomMaterialRef.getSpecification() +  usageAmountString + usageAmount ;
										compareString(afterString,"",changeItem,bomInfoAlt,currUserId,orderNo,i);
									}
								}
								
							}
						}
					}else{//避免删光，没有变更记录
						if (beforeSet != null && !beforeSet.isEmpty()) {
							
							String title = "-[标题]";
							for (BomMaterialRef bomMaterialRef : beforeSet) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
								int type = bomMaterialRef.getType().intValue();
								int seq = bomMaterialRef.getSeq().intValue();
								//返回对应类型的值
								changeItem = typeToString(type);
								if(seq == 0){
									String afterString = materialCodeString+bomMaterialRef.getMaterialCode() + productNameString + bomMaterialRef.getProductName() + specificationString +  bomMaterialRef.getSpecification() + descriptionString + bomMaterialRef.getDescription();
									compareString(afterString,"",changeItem+title,bomInfoAlt,currUserId,orderNo,i);
								}else{
									if(type != 9){
										String usageAmount = "";
										if(bomMaterialRef.getUsageAmount1() != null && bomMaterialRef.getUsageAmount2() != null){
											usageAmount = FractionalUtil.showToString(bomMaterialRef.getUsageAmount1(), bomMaterialRef.getUsageAmount2())+"";
										}
										String afterString = materialCodeString+bomMaterialRef.getMaterialCode() + productNameString + bomMaterialRef.getProductName() + specificationString +  bomMaterialRef.getSpecification() + brandString + bomMaterialRef.getBrand() + usageAmountString + usageAmount + descriptionString + bomMaterialRef.getDescription();
										if(bomMaterialRef.getContacts() != null){
											BomMaterialContact contact = bomMaterialRef.getContacts();
											afterString += companyString+contact.getCompany()+contactsString+contact.getContacts()+telphoneString+contact.getTelphone()+cellphoneString+contact.getCellphone()+emailString+contact.getEmail()+faxString+contact.getFax();
										}
										compareString(afterString,"",changeItem,bomInfoAlt,currUserId,orderNo,i);
									}else{
										String usageAmount = "";
										if(bomMaterialRef.getUsageAmount1() != null && bomMaterialRef.getUsageAmount2() != null){
											usageAmount = FractionalUtil.showToString(bomMaterialRef.getUsageAmount1(), bomMaterialRef.getUsageAmount2())+"";
										}
										String afterString =   "品名：" + bomMaterialRef.getProductName() + specificationString +  bomMaterialRef.getSpecification() +  usageAmountString + usageAmount ;
										compareString(afterString,"",changeItem,bomInfoAlt,currUserId,orderNo,i);
									}
									
								}
								
							}
						}
					}
					if (i.get() > 0) {// 修改相应的订单为未读
						int currUid = currUserId1 == null ? 0 : currUserId1;
						addBomUnreadInfo(currUid,  bomInfoBefore.getId(), orderNo,2,clientNameCode);// 1 审核通过，2 (审核通过后)普通订单修改
						bomInfoServiceI.updateUpdateTime(bomInfoBefore.getId());
					}
				} catch (Exception e) {
					log.error("计算Bom物料变更记录错误"+orderNo, e);
				}
				
				
			}

			
		});
	}
	private String typeToString(int type) {
		String changeItem = "";
		if(type == 1){
			changeItem = "第一部分:裸机包装模组";
		}
		if(type == 2){
			changeItem = "一、裸机成品";
		}
		if(type == 3){
			changeItem = "a.散料，无虚拟件料号";
		}
		if(type == 4){
			changeItem = "b.外壳模组";
		}
		if(type == 5){
			changeItem = "c.显示模组";
		}
		if(type == 6){
			
			changeItem = "第二部分:彩盒模组部分 ";
		}
		if(type == 7){
			changeItem = "第三部分:卡通箱模组部分 ";
		}
		if(type == 8){
			changeItem = "第四部分:配件及其他包材 ";
		}
		if(type == 9){
			changeItem = "第五部分:备品  ";
		}
		return changeItem;
	}
	@Override
	public void addBomUnreadInfo(Integer currUserId, Integer BomId,String orderNo,Integer choose,String clientNameCode) {// 1 审核通过，2 (审核通过后)普通订单修改
		ThreadPoolBiz.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				List<User> users = null;
				try {
					users = userService.listUsersWithBomResource();
					// 批量添加未读信息
					bomUnreadService.addBomUnreadBatch(currUserId, users, BomId);
				} catch (Exception e) {
					log.error("批量添加Bom未读信息错误,BomId:" + BomId, e);
				}
				
				try {
					if (users != null && !users.isEmpty()) {
						if (StringUtils.isNotBlank(orderNo)) {
							if (choose == 1) {
								for (User u : users) {
									if (currUserId != u.getId()) {
										WebSocketSessionUtils.sendMessageToTarget(u.getId() + "", new WebSocketMsg("Bom未读消息", "Bom单已经审核通过,订单号：" + orderNo + "，物料编号："+ clientNameCode+"。"));
									}
								}
							} else if (choose == 2) {
								for (User u : users) {
									if (currUserId != u.getId()) {
										WebSocketSessionUtils.sendMessageToTarget(u.getId() + "", new WebSocketMsg("Bom未读消息", "Bom单有修改,请查看订单号：" + orderNo + "，物料编号："+ clientNameCode+"。"));
									}
								}
							}
						}
					}
				} catch (Exception e) {
					log.error("发送通知未读信息错误", e);
				}
			}
		});
	}
	

}
