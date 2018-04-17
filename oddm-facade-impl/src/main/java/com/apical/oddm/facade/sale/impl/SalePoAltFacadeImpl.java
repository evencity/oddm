package com.apical.oddm.facade.sale.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
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

import com.apical.oddm.application.sale.SalePoAlterServiceI;
import com.apical.oddm.application.sale.SalePoServiceI;
import com.apical.oddm.application.sale.SalePoUnreadServiceI;
import com.apical.oddm.application.sys.UserServiceI;
import com.apical.oddm.core.constant.CrudConst;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SalePo;
import com.apical.oddm.core.model.sale.SalePoAlt;
import com.apical.oddm.core.model.sale.SalePoList;
import com.apical.oddm.core.model.sys.User;
import com.apical.oddm.facade.pool.ThreadPoolBiz;
import com.apical.oddm.facade.sale.po.SalePoAltFacade;
import com.apical.oddm.facade.sale.po.cmd.SalePoCmd;
import com.apical.oddm.facade.sale.po.cmd.SalePoListCmd;
import com.apical.oddm.facade.sale.po.dto.SalePoAltDto;

@Component("salePoAltFacade")
public class SalePoAltFacadeImpl implements SalePoAltFacade {

	private static final Logger log = LoggerFactory.getLogger(SalePoAltFacadeImpl.class);

	private static FastDateFormat dayFmat = FastDateFormat.getInstance("yyyy-MM-dd");

	@Autowired
	private SalePoAlterServiceI salePoAlterService;
	
	@Autowired
	private SalePoUnreadServiceI salePoUnreadService;
	
	@Autowired
	private UserServiceI userService;
	
	@Autowired
	private SalePoServiceI salePoService;
	
	@Override
	public Pager<SalePoAltDto> dataGrid(Integer poId) {
		Pager<SalePoAlt> dataGrid = salePoAlterService.dataGrid(poId);
		Pager<SalePoAltDto> pagerDto = new Pager<SalePoAltDto>();
		if (dataGrid != null) {
			BeanUtils.copyProperties(dataGrid, pagerDto);
			List<SalePoAlt> rows = dataGrid.getRows();
			if (rows != null && !rows.isEmpty()) {
				List<SalePoAltDto> newRows = new LinkedList<SalePoAltDto>();
				for (SalePoAlt atl: rows) {
					SalePoAltDto newAlt = new SalePoAltDto();
					BeanUtils.copyProperties(atl, newAlt);
					newRows.add(newAlt);
				}
				pagerDto.setRows(newRows);
			}
		}
		return pagerDto;
	}

	@Override
	public void addEditRecord(SalePo salePoBefore, SalePoCmd salePoCmd, Integer currUserId1) {
		if (salePoBefore == null) return;
		ThreadPoolBiz.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				SalePoAlt alt1 = null;
				String currUserId = currUserId1+"";
				//String userId = null;;
				String orderNo = null;
				AtomicInteger i = new AtomicInteger(0);// 统计变成项的多少，更新订单为未读
				try {
					String username = userService.get(currUserId1).getUsername();
					OrderInfo orderInfo = salePoBefore.getOrderInfo();
					//userId = orderInfo.getSellerId()!=null?orderInfo.getSellerId()+"":null;
					orderNo = orderInfo.getOrderNo();
					alt1 = new SalePoAlt(new SalePo(salePoBefore.getId()), username);
				} catch (Exception e) {
					log.error("", e);
					return;
				}
				// po基本信息
				try {
					Class<? extends SalePoCmd> cl = salePoCmd.getClass();
					Method[] methods = cl.getMethods();
					for (Method m : methods) {
						for (Annotation a : m.getAnnotations()) {
							if (a instanceof Description) {
								compareString(salePoBefore.getClass().getMethod(m.getName(), new Class[0]).invoke(salePoBefore,new Object[0]),
										m.invoke(salePoCmd,new Object[0]), ((Description) a).value(), alt1, currUserId, orderNo, i);
							}
						}
					}
				} catch (Exception e) {
					log.error("计算po基本变更记录错误", e);
				}
				try {
					// po列表
					Set<SalePoList> beforeSet = salePoBefore.getSalePoLists();//
					if (salePoCmd.getSalePoListCmd() != null && !salePoCmd.getSalePoListCmd().isEmpty()) {
						List<SalePoListCmd> afterSet = salePoCmd.getSalePoListCmd();
						if (beforeSet == null || beforeSet.isEmpty()) {// 原有数据为空，则全部是增加的
							for (SalePoListCmd c : afterSet) {
								compareString("", "NO.：" + c.getNumber() + "；产品名称：" + c.getProductName() + "；规格型号：" + c.getSpecification() + "；数量：" + c.getNumber()+ "；单价：" + c.getUnitPrice()+ "；备注：" + c.getDescription(), "产品", alt1, currUserId, orderNo, i);
							}
						} else {
							Map<Integer, SalePoList> tempMap = new HashMap<>();
							for (SalePoList before : beforeSet) {
								tempMap.put(before.getId(), before);
							}
							for (SalePoListCmd c : afterSet) {
								if (c.getId() == null) {// 增加
									compareString("", "NO.：" + c.getNumber() + "；产品名称：" + c.getProductName() + "；规格型号：" + c.getSpecification() + "；数量：" + c.getNumber()+ "；单价：" + c.getUnitPrice()+ "；备注：" + c.getDescription(), "产品", alt1, currUserId, orderNo, i);
								} else {// 删除或者修改
									if (tempMap.get(c.getId()) != null) {// 修改操作
										SalePoList h = tempMap.get(c.getId());
										Class<? extends SalePoListCmd> cl = c.getClass();
										Method[] methods = cl.getMethods();
										for (Method m : methods) {
											for (Annotation a : m.getAnnotations()) {
												if (a instanceof Description) {
													compareString(h.getClass().getMethod(m.getName(), new Class[0]).invoke(h,new Object[0]),
															m.invoke(c,new Object[0]), "产品:["+c.getProductName()+"]-"+((Description) a).value(), alt1, currUserId, orderNo, i);
												}
											}
										}
										tempMap.remove(c.getId());// 移除掉修改的，剩下的就应该删除操作
									} else {// 增加，多人操作可能会导致：A先查出，B删除，A再插入。
										compareString("", "NO.：" + c.getNumber() + "；产品名称：" + c.getProductName() + "；规格型号：" + c.getSpecification() + "；数量：" + c.getNumber()+ "；单价：" + c.getUnitPrice()+ "；备注：" + c.getDescription(), "产品", alt1, currUserId, orderNo, i);
									}
								}
							}
							for (SalePoList c : tempMap.values()) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
								compareString("NO.：" + c.getNumber() + "；产品名称：" + c.getProductName() + "；规格型号：" + c.getSpecification() + "；数量：" + c.getNumber()+ "；单价：" + c.getUnitPrice()+ "；备注：" + c.getDescription(), "", "产品", alt1, currUserId, orderNo, i);
							}
						}
					} else{//避免删光，没有变更记录
						if (beforeSet != null && !beforeSet.isEmpty()) {
							for (SalePoList c : beforeSet) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
								compareString("NO.：" + c.getNumber() + "；产品名称：" + c.getProductName() + "；规格型号：" + c.getSpecification() + "；数量：" + c.getNumber()+ "；单价：" + c.getUnitPrice()+ "；备注：" + c.getDescription(), "", "产品", alt1, currUserId, orderNo, i);
							}
						}
					}
					if (i.get() > 0) {// 修改相应的订单为未读
						int currUid = currUserId1 == null ? 0 : currUserId1;
						addSalePoUnreadInfo(currUid, salePoBefore.getId());
						salePoService.updateUpdateTime(salePoBefore.getId());
					}
				} catch (Exception e) {
					log.error("PO列表变更记录错误"+orderNo, e);
				}
			}});
	}

	/**
	 * 比较量字符串
	 * @param before 变更前
	 * @param after 变更后
	 * @param item 变更项
	 * @param alt 
	 * @param currUserId 当前登录用户
	 * @param i 统计更改的几项，为0则没有更改
	 */
	private void compareString(Object before, Object after, String item, SalePoAlt alt, String currUserId, String orderNo, AtomicInteger i) {
		//System.err.println("beforebeforebefore: " + before+"\t ,after:"+after);
		String changeBe = null;
		String changeAf = null;
		//bug注意：1、null
		if (before instanceof Date) {// 格式化日期后再判断
			changeBe = dayFmat.format(before);
		} else if (before != null) {// null+"" 会变成 "null",小心
			changeBe = StringUtils.stripToNull(before+"");//会转成null
			if (changeBe != null) changeBe = changeBe.replaceAll("\r|\n", "");
		}
		if (after instanceof Date) {// 格式化日期后再判断
			changeAf = dayFmat.format(after);
		} else if (after != null) {
			changeAf = StringUtils.stripToNull(after+""); //会转成null
			if (changeAf != null) changeAf = changeAf.replaceAll("\r|\n", "");
		}
		//bug注意：2、换行符
		if (StringUtils.equalsIgnoreCase(changeBe, changeAf)) {
			// x do nothing
			//System.err.println("do nothing " + item);
			return;
		}
		alt = new SalePoAlt(alt.getSalePo(), alt.getOperator());
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
			salePoAlterService.add(alt);
		} catch (Exception e) {
			log.error("变更记录错误："+orderNo, e);
		}
		/*if (!StringUtils.equals(currUserId, sellerId)) {
			WebSocketSessionUtils.sendMessageToTarget(sellerId, new WebSocketMsg("跟单"+orderNo+"修改通知", alt.toString()));
		}*/
	}

	@Override
	public void addSalePoUnreadInfo(int currUserId, int poId) {
		ThreadPoolBiz.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				List<User> users = null;
				try {
					users = userService.listUsersWithSalePoResource();
					// 批量添加未读信息
					salePoUnreadService.addSalePoUnreadBatch(currUserId, users, poId);
				} catch (Exception e) {
					log.error("批量添加内部订单未读信息错误,poId:" + poId, e);
				}
			}
		});
	}
}
