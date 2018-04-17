package com.apical.oddm.facade.order.impl;

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

import com.apical.oddm.application.order.OrderInfoAlterServiceI;
import com.apical.oddm.application.order.OrderInfoServiceI;
import com.apical.oddm.application.order.OrderUnreadServiceI;
import com.apical.oddm.application.sys.UserServiceI;
import com.apical.oddm.core.constant.CrudConst;
import com.apical.oddm.core.constant.OrderInfoConst;
import com.apical.oddm.core.model.order.OrderFitting;
import com.apical.oddm.core.model.order.OrderHardware;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.order.OrderInfoAlt;
import com.apical.oddm.core.model.order.OrderOS;
import com.apical.oddm.core.model.order.OrderPacking;
import com.apical.oddm.core.model.order.OrderShell;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.core.model.sale.SalePoList;
import com.apical.oddm.core.model.sys.User;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.order.OrderInfoAltFacade;
import com.apical.oddm.facade.order.command.OrderFittingCommand;
import com.apical.oddm.facade.order.command.OrderHardwareCommand;
import com.apical.oddm.facade.order.command.OrderInfoAltCommand;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.command.OrderOSCommand;
import com.apical.oddm.facade.order.command.OrderPackingCommand;
import com.apical.oddm.facade.order.command.OrderShellCommand;
import com.apical.oddm.facade.order.dto.OrderInfoAltDTO;
import com.apical.oddm.facade.pool.ThreadPoolBiz;
import com.apical.oddm.facade.util.TimeUtil;
import com.apical.oddm.facade.websocket.WebSocketMsg;
import com.apical.oddm.facade.websocket.WebSocketSessionUtils;

/**
 * @description 类的描述：
 * @author 作者 : zhuzh
 * @date 创建时间：2016年11月6日 下午2:12:42
 * @version 1.0
 */
@Component("orderInfoAltFacade")
public class OrderInfoAltFacadeImpl implements OrderInfoAltFacade {

	private static final Logger log = LoggerFactory.getLogger(OrderInfoAltFacadeImpl.class);

	@Autowired
	private OrderInfoAlterServiceI orderInfoAlterService;

	@Autowired
	private OrderInfoServiceI orderInfoService;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private OrderUnreadServiceI orderUnreadService;

	private static FastDateFormat dayFmat = FastDateFormat.getInstance("yyyy-MM-dd");

	@Override
	public BasePage<OrderInfoAltDTO> dataGrid(Integer orderId, PageCommand pageCommand) {
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		SystemContext.setSort("t." + pageCommand.getSort());

		Pager<OrderInfoAlt> dataGrid = orderInfoAlterService.dataGrid(orderId);
		BasePage<OrderInfoAltDTO> basePage = new BasePage<OrderInfoAltDTO>();
		if (dataGrid != null && dataGrid.getRows().size() > 0) {
			List<OrderInfoAltDTO> list = new ArrayList<OrderInfoAltDTO>();
			for (OrderInfoAlt orderInfoAlter : dataGrid.getRows()) {
				OrderInfoAltDTO orderInfoAltDTO = new OrderInfoAltDTO();
				BeanUtils.copyProperties(orderInfoAlter, orderInfoAltDTO);
				if (orderInfoAlter.getTimestamp() != null) {
					orderInfoAltDTO.setTimestamp(TimeUtil.timestampToStrings(orderInfoAlter.getTimestamp()));
				}
				list.add(orderInfoAltDTO);
			}
			basePage.setRows(list);
			basePage.setTotal(dataGrid.getTotal());
		}
		return basePage;
	}

	@Override
	public Boolean add(OrderInfoAltCommand orderInfoAltCommand) {
		OrderInfo orderInfo = orderInfoService.get(orderInfoAltCommand.getOrderId());
		OrderInfoAlt orderInfoAlt = new OrderInfoAlt();
		BeanUtils.copyProperties(orderInfoAltCommand, orderInfoAlt);
		orderInfoAlt.setOrderInfo(orderInfo);

		Serializable add = orderInfoAlterService.add(orderInfoAlt);
		if (add != null) {
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
	 * @param sellerId 要发消息给对应的用户
	 * @param orderState 订单状态，只有审核过后才生成未读、websocket消息
	 * @param i 统计更改的几项，为0则没有更改
	 */
	private void compareString(Object before, Object after, String item, OrderInfoAlt alt, String currUserId, String sellerId, String merchandiserId, String orderNo,
			AtomicInteger i, Integer orderState) {
		// System.err.println("beforebeforebefore: " + before+"\t ,after:"+after);
		String changeBe = null;
		String changeAf = null;
		// bug注意：1、null
		if (before instanceof Date) {// 格式化日期后再判断
			changeBe = dayFmat.format(before);
		} else if (before != null) {// null+"" 会变成 "null",小心
			changeBe = StringUtils.stripToNull(before + "");// 会转成""
			if (changeBe != null)
				changeBe = changeBe.replaceAll("\r|\n", "");
		}
		if (after instanceof Date) {// 格式化日期后再判断
			changeAf = dayFmat.format(after);
		} else if (after != null) {
			changeAf = StringUtils.stripToNull(after + ""); // 会转成""
			if (changeAf != null)
				changeAf = changeAf.replaceAll("\r|\n", "");
		}
		// bug注意：2、换行符
		if (StringUtils.equalsIgnoreCase(changeBe, changeAf)) {
			// x do nothing
			// System.err.println("do nothing " + item);
			return;
		}
		alt = new OrderInfoAlt(alt.getOrderInfo(), alt.getOperator());
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
			orderInfoAlterService.add(alt);
			if (orderState != null && orderState >= OrderInfoConst.approved) {
				if (currUserId != null && sellerId != null && !StringUtils.equals(currUserId, sellerId)) {
					WebSocketSessionUtils.sendMessageToTarget(sellerId, new WebSocketMsg("订单" + orderNo + "修改通知", alt.toString()));
				}
				if (currUserId != null && merchandiserId != null && !StringUtils.equals(currUserId, merchandiserId)) {
					WebSocketSessionUtils.sendMessageToTarget(merchandiserId, new WebSocketMsg("订单" + orderNo + "修改通知", alt.toString()));
				}
			}
		} catch (Exception e) {
			log.error("变更记录错误："+orderNo, e);
		}
	}

	/*
	 * public static void main(String[] args) { String before = null; String after = "";
	 * 
	 * String changeBe = null; String changeAf = null; //bug注意：1、null if (before != null) {// null+"" 会变成 "null",小心 changeBe = StringUtils.stripToEmpty(before+"");//会转成"" changeBe
	 * = changeBe.replaceAll("\r|\n", ""); } if (after != null) { changeAf = StringUtils.stripToEmpty(after+""); //会转成"" changeAf = changeAf.replaceAll("\r|\n", ""); }
	 * System.out.println(StringUtils.equalsIgnoreCase(changeBe, changeAf)); }
	 */
	/**
	 * @param orderInfoBefore1
	 *            编辑前
	 * @param orderInfoCommand
	 *            编辑后
	 * @param currUserId
	 *            当前登录用户id
	 */
	@Override
	public void addEditRecord(OrderInfo orderInfoBefore1, OrderInfoCommand orderInfoCommand, Integer currUserId1) {
		if (orderInfoBefore1 == null) {
			return;
		}
		// BeanUtils.copyProperties(orderInfo,orderInfoBefore);//这个是浅克隆，子类不new的话，是相同对象，则会后线程安全问题
		ThreadPoolBiz.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				OrderInfo orderInfoBefore = orderInfoBefore1;// orderInfoService.getOrderInfo(orderInfoCommand.getId());// 又查一次 效率变低
				OrderInfoAlt alt1 = null;

				String currUserId = currUserId1 + "";
				String sellerId = null;
				String merchandiserId = null;
				String orderNo = null;
				Integer orderId = null;
				AtomicInteger i = new AtomicInteger(0);// 统计变成项的多少，更新订单为未读
				try {
					orderId = orderInfoBefore.getId();
					String username = userService.get(currUserId1).getUsername();
					alt1 = new OrderInfoAlt(new OrderInfo(orderId), username);
					sellerId = orderInfoBefore.getSellerId() != null ? orderInfoBefore.getSellerId() + "" : null;
					merchandiserId = orderInfoBefore.getMerchandiserId() != null ? orderInfoBefore.getMerchandiserId() + "" : null;
					orderNo = orderInfoBefore.getOrderNo();
				} catch (Exception e) {
					log.error("", e);
					return;
				}
				// 订单基本信息
				try {
					Class<? extends OrderInfoCommand> cl = orderInfoCommand.getClass();
					Method[] methods = cl.getMethods();
					for (Method m : methods) {
						for (Annotation a : m.getAnnotations()) {
							if (a instanceof Description) {
								compareString(orderInfoBefore.getClass().getMethod(m.getName(), new Class[0]).invoke(orderInfoBefore, new Object[0]),
										m.invoke(orderInfoCommand, new Object[0]), ((Description) a).value(), alt1, currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						}
					}
				} catch (Exception e) {
					log.error("计算订单信息变更记录错误"+orderNo, e);
				}
				try {
					// 软件信息
					if (orderInfoCommand.getOrderOSCommand() != null) {
						Set<OrderOS> orderOsSet = orderInfoBefore.getOrderOs();
						OrderOS orderOS = orderOsSet.iterator().next();
						OrderOSCommand orderOSCommand = orderInfoCommand.getOrderOSCommand();
						Class<? extends OrderOSCommand> cl = orderOSCommand.getClass();
						Method[] methods = cl.getMethods();
						for (Method m : methods) {
							for (Annotation a : m.getAnnotations()) {
								if (a instanceof Description) {
									/*
									 * if ("软件".equals(((Description) a).value())) {//这个是创建订单或翻单的时候程序就写好的，没必要判断 String isrepeatBe = "新软件"; if
									 * ("2".equals(""+orderOS.getClass().getMethod(m.getName(),new Class[0]).invoke(orderOS,new Object[0]))) { isrepeatBe = "返单软件"; } String
									 * isrepeatAf = "新软件"; if ("2".equals(""+m.invoke(orderOSCommand,new Object[0]))) { isrepeatAf = "返单软件"; } compareString(isrepeatBe, isrepeatAf,
									 * ((Description) a).value(), alt1, currUserId, userId, orderNo, i, orderInfoCommand.getState()); } else
									 */if ("是否定制".equals(((Description) a).value())) {
										String iscustomBe = "定制软件";
										if ("2".equals("" + orderOS.getClass().getMethod(m.getName(), new Class[0]).invoke(orderOS, new Object[0]))) {
											iscustomBe = "公版软件";
										}
										String iscustomAf = "定制软件";
										if ("2".equals("" + m.invoke(orderOSCommand, new Object[0]))) {
											iscustomAf = "公版软件";
										}
										compareString(iscustomBe, iscustomAf, ((Description) a).value(), alt1, currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
									} else {
										compareString(orderOS.getClass().getMethod(m.getName(), new Class[0]).invoke(orderOS, new Object[0]),
												m.invoke(orderOSCommand, new Object[0]), ((Description) a).value(), alt1, currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
									}
								}
							}
						}
					}
				} catch (Exception e) {
					log.error("计算订单os变更记录错误"+orderNo, e);
				}
				try {
					// 定单外壳
					if (orderInfoCommand.getOrderShellCommands() != null && !orderInfoCommand.getOrderShellCommands().isEmpty()) {
						Set<OrderShell> beforeSet = orderInfoBefore.getOrderShells();//
						Set<OrderShellCommand> afterSet = orderInfoCommand.getOrderShellCommands();
						if (beforeSet == null || beforeSet.isEmpty()) {// 原有数据为空，则全部是增加的
							for (OrderShellCommand c : afterSet) {
								String silk = "无";
								if (c.getSilkScreen() == 2)
									silk = "有";
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								/*compareString("", "部件：" + c.getName() + "；颜色：" + c.getColor() + "；工艺：" + c.getCraft() + "；丝印：" + silk + "；新旧物料：" + isNew, "外壳", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								//添加记录
								compareString("", "颜色：" + c.getColor() + "；工艺：" + c.getCraft() + "；丝印：" + silk + "；新旧物料：" + isNew, c.getName(), alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						} else {
							Map<Integer, OrderShell> tempMap = new HashMap<>();
							for (OrderShell before : beforeSet) {
								tempMap.put(before.getId(), before);
							}
							for (OrderShellCommand c : afterSet) {
								if (c.getId() == null) {// 增加
									String silk = "无";
									if (c.getSilkScreen() == 2)
										silk = "有";
									String isNew = "旧物料";
									if (c.getIsNew() == 2)
										isNew = "新物料";
									/*compareString("", "部件：" + c.getName() + "；颜色：" + c.getColor() + "；工艺：" + c.getCraft() + "；丝印：" + silk + "；新旧物料：" + isNew, "外壳", alt1,
											currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
									//添加记录
									compareString("", "颜色：" + c.getColor() + "；工艺：" + c.getCraft() + "；丝印：" + silk + "；新旧物料：" + isNew, c.getName(), alt1,
											currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
									
								} else {// 删除或者修改
									if (tempMap.get(c.getId()) != null) {// 修改操作
										OrderShell h = tempMap.get(c.getId());
										Class<? extends OrderShellCommand> cl = c.getClass();
										Method[] methods = cl.getMethods();
										for (Method m : methods) {
											for (Annotation a : m.getAnnotations()) {
												if (a instanceof Description) {
													// 物料名是不可能修改的，只有增、删
													if ("丝印".equals(((Description) a).value())) {

														// System.err.println("丝印1::::"+h.getClass().getMethod(m.getName(),new Class[0]).invoke(h,new Object[0]));
														String silkBe = "丝印：无";
														if ("2".equals("" + h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]))) {
															silkBe = "丝印：有";
														}
														// System.err.println("丝印2::::"+m.invoke(c,new Object[0]));
														String silkAf = "丝印：无";
														if ("2".equals("" + m.invoke(c, new Object[0]))) {
															silkAf = "丝印：有";
														}
														/*compareString(silkBe, silkAf, "外壳-" + h.getName() + "-" + ((Description) a).value(), alt1, currUserId, sellerId,
																merchandiserId, orderNo, i, orderInfoCommand.getState());*/
														//外壳变更记录
														compareString(silkBe, silkAf, h.getName(), alt1, currUserId, sellerId,
																merchandiserId, orderNo, i, orderInfoCommand.getState());
														
													} else if ("新旧物料".equals(((Description) a).value())) {

														// System.err.println("丝印1::::"+h.getClass().getMethod(m.getName(),new Class[0]).invoke(h,new Object[0]));
														String silkBe = "旧物料";
														if ("2".equals("" + h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]))) {
															silkBe = "新物料";
														}
														// System.err.println("丝印2::::"+m.invoke(c,new Object[0]));
														String silkAf = "旧物料";
														if ("2".equals("" + m.invoke(c, new Object[0]))) {
															silkAf = "新物料";
														}
														/*compareString(silkBe, silkAf, "外壳-" + h.getName() + "-" + ((Description) a).value(), alt1, currUserId, sellerId,
																merchandiserId, orderNo, i, orderInfoCommand.getState());*/
														//外壳变更记录
														compareString(silkBe, silkAf, h.getName(), alt1, currUserId, sellerId,
																merchandiserId, orderNo, i, orderInfoCommand.getState());
														
													} else {
														/*compareString(h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]), m.invoke(c, new Object[0]), "外壳-"
																+ h.getName() + "-" + ((Description) a).value(), alt1, currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
														compareString(h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]), m.invoke(c, new Object[0]), 
																h.getName(), alt1, currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
													}
												}
											}
										}
										tempMap.remove(c.getId());// 移除掉修改的，剩下的就应该删除操作
									} else {// 增加，多人操作可能会导致：A先查出，B删除，A再插入。
										String silk = "无";
										if (c.getSilkScreen() == 2)
											silk = "有";
										String isNew = "旧物料";
										if (c.getIsNew() == 2)
											isNew = "新物料";
										/*compareString("", "部件：" + c.getName() + "；颜色：" + c.getColor() + "；工艺：" + c.getCraft() + "；丝印：" + silk + "；新旧物料：" + isNew, "外壳", alt1,
												currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
										compareString("", "颜色：" + c.getColor() + "；工艺：" + c.getCraft() + "；丝印：" + silk + "；新旧物料：" + isNew, c.getName(), alt1,
												currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
									}
								}
							}
							for (OrderShell c : tempMap.values()) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
								String silk = "无";
								if (c.getSilkScreen() == 2)
									silk = "有";
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								/*compareString("部件：" + c.getName() + "；颜色：" + c.getColor() + "；工艺：" + c.getCraft() + "；丝印：" + silk + "；新旧物料：" + isNew, "", "外壳", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								compareString("颜色：" + c.getColor() + "；工艺：" + c.getCraft() + "；丝印：" + silk + "；新旧物料：" + isNew, "", c.getName(), alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						}
					}else{
						Set<OrderShell> beforeSet = orderInfoBefore.getOrderShells();//
						if (beforeSet != null && !beforeSet.isEmpty()) {
							for(OrderShell c : beforeSet){
								String silk = "无";
								if (c.getSilkScreen() == 2)
									silk = "有";
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								/*compareString("部件：" + c.getName() + "；颜色：" + c.getColor() + "；工艺：" + c.getCraft() + "；丝印：" + silk + "；新旧物料：" + isNew, "", "外壳", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								compareString("颜色：" + c.getColor() + "；工艺：" + c.getCraft() + "；丝印：" + silk + "；新旧物料：" + isNew, "", c.getName(), alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						}
					}
				} catch (Exception e) {
					log.error("计算订单外壳变更记录错误"+orderNo, e);
				}
				try {
					// 定单硬件物料
					if (orderInfoCommand.getOrderHardwareCommands() != null && !orderInfoCommand.getOrderHardwareCommands().isEmpty()) {
						Set<OrderHardware> beforeSet = orderInfoBefore.getOrderHardwares();//
						Set<OrderHardwareCommand> afterSet = orderInfoCommand.getOrderHardwareCommands();
						if (beforeSet == null || beforeSet.isEmpty()) {// 原有数据为空，则全部是增加的
							for (OrderHardwareCommand c : afterSet) {
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								/*compareString("", "物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "硬件", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								compareString("", "供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew,  c.getName(), alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
								
							}
						} else {
							Map<Integer, OrderHardware> tempMap = new HashMap<>();
							for (OrderHardware before : beforeSet) {
								tempMap.put(before.getId(), before);
							}
							for (OrderHardwareCommand c : afterSet) {
								if (c.getId() == null) {// 增加
									String isNew = "旧物料";
									if (c.getIsNew() == 2)
										isNew = "新物料";
									/*compareString("", "物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "硬件", alt1,
											currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
									compareString("", "供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew,  c.getName(), alt1,
											currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
								} else {// 删除或者修改
									if (tempMap.get(c.getId()) != null) {// 修改操作
										OrderHardware h = tempMap.get(c.getId());
										Class<? extends OrderHardwareCommand> cl = c.getClass();
										Method[] methods = cl.getMethods();
										for (Method m : methods) {
											for (Annotation a : m.getAnnotations()) {
												if (a instanceof Description) {
													// 物料名是不可能修改的，只有增、删
													if ("新旧物料".equals(((Description) a).value())) {
														// System.err.println("丝印1::::"+h.getClass().getMethod(m.getName(),new Class[0]).invoke(h,new Object[0]));
														String silkBe = "旧物料";
														if ("2".equals("" + h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]))) {
															silkBe = "新物料";
														}
														// System.err.println("丝印2::::"+m.invoke(c,new Object[0]));
														String silkAf = "旧物料";
														if ("2".equals("" + m.invoke(c, new Object[0]))) {
															silkAf = "新物料";
														}
														/*compareString(silkBe, silkAf, "硬件-" + h.getName() + "-" + ((Description) a).value(), alt1, currUserId, sellerId,
																merchandiserId, orderNo, i, orderInfoCommand.getState());*/
														compareString(silkBe, silkAf, h.getName(), alt1, currUserId, sellerId,
																merchandiserId, orderNo, i, orderInfoCommand.getState());
														
													} else {
														/*compareString(h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]), m.invoke(c, new Object[0]), "硬件-"
																+ h.getName() + "-" + ((Description) a).value(), alt1, currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
														compareString(h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]), m.invoke(c, new Object[0]),
																h.getName(), alt1, currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
													}
												}
											}
										}
										tempMap.remove(c.getId());// 移除掉修改的，剩下的就应该删除操作
									} else {// 增加，多人操作可能会导致：A先查出，B删除，A再插入。
										String isNew = "旧物料";
										if (c.getIsNew() == 2)
											isNew = "新物料";
										/*compareString("", "物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "硬件", alt1,
												currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
										compareString("", "供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, c.getName(), alt1,
												currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
									}
								}
							}
							for (OrderHardware c : tempMap.values()) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								/*compareString("物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", "硬件", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								compareString("供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", c.getName(), alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						}
					}else{
						Set<OrderHardware> beforeSet = orderInfoBefore.getOrderHardwares();//
						if (beforeSet != null && !beforeSet.isEmpty()) {
							for(OrderHardware c : beforeSet){
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								/*compareString("物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", "硬件", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								compareString("供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", c.getName(), alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						}
					}
				} catch (Exception e) {
					log.error("计算订单硬件变更记录错误"+orderNo, e);
				}
				try {
					// 订单包材物料
					if (orderInfoCommand.getOrderPackingCommands() != null && !orderInfoCommand.getOrderPackingCommands().isEmpty()) {
						Set<OrderPacking> beforeSet = orderInfoBefore.getOrderPackings();//
						Set<OrderPackingCommand> afterSet = orderInfoCommand.getOrderPackingCommands();
						if (beforeSet == null || beforeSet.isEmpty()) {// 原有数据为空，则全部是增加的
							for (OrderPackingCommand c : afterSet) {
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								/*compareString("", "物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "包材", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								compareString("", "供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, c.getName(), alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						} else {
							Map<Integer, OrderPacking> tempMap = new HashMap<>();
							for (OrderPacking before : beforeSet) {
								tempMap.put(before.getId(), before);
							}
							for (OrderPackingCommand c : afterSet) {
								if (c.getId() == null) {// 增加
									String isNew = "旧物料";
									if (c.getIsNew() == 2)
										isNew = "新物料";
									/*compareString("", "物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "包材", alt1,
											currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
									compareString("", "供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, c.getName(), alt1,
											currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
								} else {// 删除或者修改
									if (tempMap.get(c.getId()) != null) {// 修改操作
										OrderPacking h = tempMap.get(c.getId());
										Class<? extends OrderPackingCommand> cl = c.getClass();
										Method[] methods = cl.getMethods();
										for (Method m : methods) {
											for (Annotation a : m.getAnnotations()) {
												if (a instanceof Description) {
													// 物料名是不可能修改的，只有增、删
													if ("新旧物料".equals(((Description) a).value())) {
														// System.err.println("丝印1::::"+h.getClass().getMethod(m.getName(),new Class[0]).invoke(h,new Object[0]));
														String silkBe = "旧物料";
														if ("2".equals("" + h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]))) {
															silkBe = "新物料";
														}
														// System.err.println("丝印2::::"+m.invoke(c,new Object[0]));
														String silkAf = "旧物料";
														if ("2".equals("" + m.invoke(c, new Object[0]))) {
															silkAf = "新物料";
														}
														/*compareString(silkBe, silkAf, "包材-" + h.getName() + "-" + ((Description) a).value(), alt1, currUserId, sellerId,
																merchandiserId, orderNo, i, orderInfoCommand.getState());*/
														compareString(silkBe, silkAf, h.getName(), alt1, currUserId, sellerId,
																merchandiserId, orderNo, i, orderInfoCommand.getState());
													} else {
														/*compareString(h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]), m.invoke(c, new Object[0]), "包材-"
																+ h.getName() + "-" + ((Description) a).value(), alt1, currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
														compareString(h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]), m.invoke(c, new Object[0]), 
																 h.getName() , alt1, currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());

													}
												}
											}
										}
										tempMap.remove(c.getId());// 移除掉修改的，剩下的就应该删除操作
									} else {// 增加，多人操作可能会导致：A先查出，B删除，A再插入。
										String isNew = "旧物料";
										if (c.getIsNew() == 2)
											isNew = "新物料";
										/*compareString("", "物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "包材", alt1,
												currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
										compareString("", "供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, c.getName(), alt1,
												currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
									}
								}
							}
							for (OrderPacking c : tempMap.values()) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								/*compareString("物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", "包材", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								compareString("供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", c.getName(), alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						}
					}else{
						Set<OrderPacking> beforeSet = orderInfoBefore.getOrderPackings();//
						if (beforeSet != null && !beforeSet.isEmpty()) {
							for(OrderPacking c : beforeSet){
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								/*compareString("物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", "包材", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								compareString("供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", c.getName(), alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						}
					}
				} catch (Exception e) {
					log.error("计算订单包材变更记录错误"+orderNo, e);
				}
				try {
					// 定单配件物料
					if (orderInfoCommand.getOrderFittingCommands() != null && !orderInfoCommand.getOrderFittingCommands().isEmpty()) {
						Set<OrderFitting> beforeSet = orderInfoBefore.getOrderFittings();//
						Set<OrderFittingCommand> afterSet = orderInfoCommand.getOrderFittingCommands();
						if (beforeSet == null || beforeSet.isEmpty()) {// 原有数据为空，则全部是增加的
							for (OrderFittingCommand c : afterSet) {
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								/*compareString("", "物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "配件", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								compareString("", "供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, c.getName(), alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						} else {
							Map<Integer, OrderFitting> tempMap = new HashMap<>();
							for (OrderFitting before : beforeSet) {
								tempMap.put(before.getId(), before);
							}
							for (OrderFittingCommand c : afterSet) {
								if (c.getId() == null) {// 增加
									String isNew = "旧物料";
									if (c.getIsNew() == 2)
										isNew = "新物料";
									/*compareString("", "物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "配件", alt1,
											currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
									compareString("", "供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, c.getName(), alt1,
											currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
									
								} else {// 删除或者修改
									if (tempMap.get(c.getId()) != null) {// 修改操作
										OrderFitting h = tempMap.get(c.getId());
										Class<? extends OrderFittingCommand> cl = c.getClass();
										Method[] methods = cl.getMethods();
										for (Method m : methods) {
											for (Annotation a : m.getAnnotations()) {
												if (a instanceof Description) {
													// 物料名是不可能修改的，只有增、删
													if ("新旧物料".equals(((Description) a).value())) {
														// System.err.println("丝印1::::"+h.getClass().getMethod(m.getName(),new Class[0]).invoke(h,new Object[0]));
														String silkBe = "旧物料";
														if ("2".equals("" + h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]))) {
															silkBe = "新物料";
														}
														// System.err.println("丝印2::::"+m.invoke(c,new Object[0]));
														String silkAf = "旧物料";
														if ("2".equals("" + m.invoke(c, new Object[0]))) {
															silkAf = "新物料";
														}
														/*compareString(silkBe, silkAf, "配件-" + h.getName() + "-" + ((Description) a).value(), alt1, currUserId, sellerId,
																merchandiserId, orderNo, i, orderInfoCommand.getState());*/
														compareString(silkBe, silkAf, h.getName(), alt1, currUserId, sellerId,
																merchandiserId, orderNo, i, orderInfoCommand.getState());
													} else {
														/*compareString(h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]), m.invoke(c, new Object[0]), "配件-"
																+ h.getName() + "-" + ((Description) a).value(), alt1, currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
														compareString(h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]), m.invoke(c, new Object[0]),
																h.getName(), alt1, currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
													}
												}
											}
										}
										tempMap.remove(c.getId());// 移除掉修改的，剩下的就应该删除操作
									} else {// 增加，多人操作可能会导致：A先查出，B删除，A再插入。
										String isNew = "旧物料";
										if (c.getIsNew() == 2)
											isNew = "新物料";
										/*compareString("", "物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "配件", alt1,
												currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
										compareString("", "供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, c.getName(), alt1,
												currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
									}
								}
							}
							for (OrderFitting c : tempMap.values()) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								/*compareString("物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", "配件", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								compareString("供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", c.getName(), alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						}
					}else{
						Set<OrderFitting> beforeSet = orderInfoBefore.getOrderFittings();//
						if (beforeSet != null && !beforeSet.isEmpty()) {
							for(OrderFitting c : beforeSet){
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								/*compareString("物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", "配件", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								compareString("供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", c.getName(), alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						}
					}
					if (i.get() > 0 && orderInfoCommand.getState() >= OrderInfoConst.approved) {//修改相应的订单为未读,审核过后才修改（因为翻单未审核过也记录变更记录）
						int currUid = currUserId1 == null ? 0 : currUserId1;
						addOrderUnreadInfo(orderNo, currUid, orderId, 2);
						orderInfoService.updateUpdateTime(orderId);
					}
				} catch (Exception e) {
					log.error("计算订单配件变更记录错误"+orderNo, e);
				}
			}
		});
	}

	@Override
	public void addOrderUnreadInfo(String orderNo, int currUserId, int orderId, int choose) {// 1 审核通过，2 (审核通过后)普通订单修改
		// 页面点击审核通过后，生成用户未读订单记录
		ThreadPoolBiz.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				List<User> users = null;
				try {
					users = userService.listUsersWithOrderResource();
					// 批量添加未读信息
					orderUnreadService.addOrderUnreadBatch(currUserId, users, orderId);
				} catch (Exception e) {
					log.error("批量添加订单未读信息错误,订单id:" + orderId, e);
				}
				try {
					if (users != null && !users.isEmpty()) {
						if (StringUtils.isNotBlank(orderNo)) {
							if (choose == 1) {
								for (User u : users) {
									if (currUserId != u.getId()) {
										WebSocketSessionUtils.sendMessageToTarget(u.getId() + "", new WebSocketMsg("订单未读消息", "订单已经审核通过,订单号：" + orderNo + "。"));
									}
								}
							} else if (choose == 2) {
								for (User u : users) {
									if (currUserId != u.getId()) {
										WebSocketSessionUtils.sendMessageToTarget(u.getId() + "", new WebSocketMsg("订单未读消息", "订单有修改请查看订单号：" + orderNo + "。"));
									}
								}
							}
						}
					}
				} catch (Exception e) {
					log.error("发送通知未读信息错误"+orderNo, e);
				}
			}
		});
	}

	@Override
	public void addAddRecord(OrderInfo orderInfoBefore1, OrderInfoCommand orderInfoCommand, Integer currUserId1) {
		if (orderInfoBefore1 == null) {
			return;
		}
		// BeanUtils.copyProperties(orderInfo,orderInfoBefore);//这个是浅克隆，子类不new的话，是相同对象，则会后线程安全问题
		ThreadPoolBiz.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				OrderInfo orderInfoBefore = orderInfoBefore1;// orderInfoService.getOrderInfo(orderInfoCommand.getId());// 又查一次 效率变低
				OrderInfoAlt alt1 = null;

				String currUserId = currUserId1 + "";
				String sellerId = null;
				String merchandiserId = null;
				String orderNo = null;
				Integer orderId = null;
				AtomicInteger i = new AtomicInteger(0);// 统计变成项的多少，更新订单为未读
				try {
					orderId = orderInfoBefore.getId();
					String username = userService.get(currUserId1).getUsername();
					alt1 = new OrderInfoAlt(new OrderInfo(orderId), username);
					sellerId = orderInfoBefore.getSellerId() != null ? orderInfoBefore.getSellerId() + "" : null;
					merchandiserId = orderInfoBefore.getMerchandiserId() != null ? orderInfoBefore.getMerchandiserId() + "" : null;
					orderNo = orderInfoBefore.getOrderNo();
				} catch (Exception e) {
					log.error("", e);
					return;
				}
					//订单基本信息, 不计算变更记录

				try {
					// 软件信息
					if (orderInfoCommand.getOrderOSCommand() != null) {
						Set<OrderOS> orderOsSet = orderInfoBefore.getOrderOs();
						OrderOS orderOS = orderOsSet.iterator().next();
						OrderOSCommand orderOSCommand = orderInfoCommand.getOrderOSCommand();
						Class<? extends OrderOSCommand> cl = orderOSCommand.getClass();
						Method[] methods = cl.getMethods();
						for (Method m : methods) {
							for (Annotation a : m.getAnnotations()) {
								if (a instanceof Description) {
									/*
									 * if ("软件".equals(((Description) a).value())) {//这个是创建订单或翻单的时候程序就写好的，没必要判断 String isrepeatBe = "新软件"; if
									 * ("2".equals(""+orderOS.getClass().getMethod(m.getName(),new Class[0]).invoke(orderOS,new Object[0]))) { isrepeatBe = "返单软件"; } String
									 * isrepeatAf = "新软件"; if ("2".equals(""+m.invoke(orderOSCommand,new Object[0]))) { isrepeatAf = "返单软件"; } compareString(isrepeatBe, isrepeatAf,
									 * ((Description) a).value(), alt1, currUserId, userId, orderNo, i, orderInfoCommand.getState()); } else
									 */if ("是否定制".equals(((Description) a).value())) {
										String iscustomBe = "定制软件";
										if ("2".equals("" + orderOS.getClass().getMethod(m.getName(), new Class[0]).invoke(orderOS, new Object[0]))) {
											iscustomBe = "公版软件";
										}
										String iscustomAf = "定制软件";
										if ("2".equals("" + m.invoke(orderOSCommand, new Object[0]))) {
											iscustomAf = "公版软件";
										}
										compareString(iscustomBe, iscustomAf, ((Description) a).value(), alt1, currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
									} else {
										compareString(orderOS.getClass().getMethod(m.getName(), new Class[0]).invoke(orderOS, new Object[0]),
												m.invoke(orderOSCommand, new Object[0]), ((Description) a).value(), alt1, currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
									}
								}
							}
						}
					}
				} catch (Exception e) {
					log.error("计算订单os变更记录错误"+orderNo, e);
				}
				try {
					// 定单外壳
					if (orderInfoCommand.getOrderShellCommands() != null && !orderInfoCommand.getOrderShellCommands().isEmpty()) {
						Set<OrderShell> beforeSet = orderInfoBefore.getOrderShells();//
						Set<OrderShellCommand> afterSet = orderInfoCommand.getOrderShellCommands();
						if (beforeSet == null || beforeSet.isEmpty()) {// 原有数据为空，则全部是增加的
							for (OrderShellCommand c : afterSet) {
								String silk = "无";
								if (c.getSilkScreen() == 2)
									silk = "有";
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								/*compareString("", "部件：" + c.getName() + "；颜色：" + c.getColor() + "；工艺：" + c.getCraft() + "；丝印：" + silk + "；新旧物料：" + isNew, "外壳", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								compareString("", "颜色：" + c.getColor() + "；工艺：" + c.getCraft() + "；丝印：" + silk + "；新旧物料：" + isNew, c.getName(), alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						} else {
							Map<String, OrderShell> tempMap = new HashMap<>();
							for (OrderShell before : beforeSet) {
								tempMap.put(before.getName(), before);
							}
							for (OrderShellCommand c : afterSet) {
								// 删除或者修改
								if (tempMap.get(c.getName()) != null) {// 修改操作
									OrderShell h = tempMap.get(c.getName());
									Class<? extends OrderShellCommand> cl = c.getClass();
									Method[] methods = cl.getMethods();
									for (Method m : methods) {
										for (Annotation a : m.getAnnotations()) {
											if (a instanceof Description) {
												// 物料名是不可能修改的，只有增、删
												if ("丝印".equals(((Description) a).value())) {

													// System.err.println("丝印1::::"+h.getClass().getMethod(m.getName(),new Class[0]).invoke(h,new Object[0]));
													String silkBe = "丝印：无";
													if ("2".equals("" + h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]))) {
														silkBe = "丝印：有";
													}
													// System.err.println("丝印2::::"+m.invoke(c,new Object[0]));
													String silkAf = "丝印：无";
													if ("2".equals("" + m.invoke(c, new Object[0]))) {
														silkAf = "丝印：有";
													}
													/*compareString(silkBe, silkAf, "外壳-" + h.getName() + "-" + ((Description) a).value(), alt1, currUserId, sellerId,
															merchandiserId, orderNo, i, orderInfoCommand.getState());*/
													compareString(silkBe, silkAf, h.getName(), alt1, currUserId, sellerId,
															merchandiserId, orderNo, i, orderInfoCommand.getState());
												} else if ("新旧物料".equals(((Description) a).value())) {
													String silkBe = "旧物料";//创建翻单的时候默认就是旧的，所以要默认为旧
													String silkAf = "旧物料";
													if ("2".equals("" + m.invoke(c, new Object[0]))) {
														silkAf = "新物料";
													}
													/*compareString(silkBe, silkAf, "外壳-" + h.getName() + "-" + ((Description) a).value(), alt1, currUserId, sellerId,
															merchandiserId, orderNo, i, orderInfoCommand.getState());*/
													compareString(silkBe, silkAf, h.getName(), alt1, currUserId, sellerId,
															merchandiserId, orderNo, i, orderInfoCommand.getState());
												} else {
													/*compareString(h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]), m.invoke(c, new Object[0]), "外壳-"
															+ h.getName() + "-" + ((Description) a).value(), alt1, currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
													compareString(h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]), m.invoke(c, new Object[0]), 
															h.getName(), alt1, currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
												}
											}
										}
									}
									tempMap.remove(c.getName());// 移除掉修改的，剩下的就应该删除操作
								} else {// 增加，多人操作可能会导致：A先查出，B删除，A再插入。
									String silk = "无";
									if (c.getSilkScreen() == 2)
										silk = "有";
									String isNew = "旧物料";
									if (c.getIsNew() == 2)
										isNew = "新物料";
									/*compareString("", "部件：" + c.getName() + "；颜色：" + c.getColor() + "；工艺：" + c.getCraft() + "；丝印：" + silk + "；新旧物料：" + isNew, "外壳", alt1,
											currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
									compareString("", "颜色：" + c.getColor() + "；工艺：" + c.getCraft() + "；丝印：" + silk + "；新旧物料：" + isNew, c.getName(), alt1,
											currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
								}
							}
							for (OrderShell c : tempMap.values()) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
								String silk = "无";
								if (c.getSilkScreen() == 2)
									silk = "有";
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								/*compareString("部件：" + c.getName() + "；颜色：" + c.getColor() + "；工艺：" + c.getCraft() + "；丝印：" + silk + "；新旧物料：" + isNew, "", "外壳", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								compareString("颜色：" + c.getColor() + "；工艺：" + c.getCraft() + "；丝印：" + silk + "；新旧物料：" + isNew, "", c.getName(), alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						}
					}else{
						Set<OrderShell> beforeSet = orderInfoBefore.getOrderShells();//
						if (beforeSet != null && !beforeSet.isEmpty()) {
							for(OrderShell c : beforeSet){
								String silk = "无";
								if (c.getSilkScreen() == 2)
									silk = "有";
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								/*compareString("部件：" + c.getName() + "；颜色：" + c.getColor() + "；工艺：" + c.getCraft() + "；丝印：" + silk + "；新旧物料：" + isNew, "", "外壳", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								compareString("颜色：" + c.getColor() + "；工艺：" + c.getCraft() + "；丝印：" + silk + "；新旧物料：" + isNew, "", c.getName(), alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						}
					}
				} catch (Exception e) {
					log.error("计算订单外壳变更记录错误"+orderNo, e);
				}
				try {
					// 定单硬件物料
					if (orderInfoCommand.getOrderHardwareCommands() != null && !orderInfoCommand.getOrderHardwareCommands().isEmpty()) {
						Set<OrderHardware> beforeSet = orderInfoBefore.getOrderHardwares();//
						Set<OrderHardwareCommand> afterSet = orderInfoCommand.getOrderHardwareCommands();
						if (beforeSet == null || beforeSet.isEmpty()) {// 原有数据为空，则全部是增加的
							for (OrderHardwareCommand c : afterSet) {
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								if (c.getSupplier() == null) c.setSupplier("");if (c.getSpecification() == null) c.setSpecification("");
								/*compareString("", "物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "硬件", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								compareString("", "供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, c.getName(), alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						} else {
							Map<String, OrderHardware> tempMap = new HashMap<>();
							for (OrderHardware before : beforeSet) {
								tempMap.put(before.getName(), before);
							}
							for (OrderHardwareCommand c : afterSet) {
								// 删除或者修改
								if (tempMap.get(c.getName()) != null) {// 修改操作
									OrderHardware h = tempMap.get(c.getName());
									Class<? extends OrderHardwareCommand> cl = c.getClass();
									Method[] methods = cl.getMethods();
									for (Method m : methods) {
										for (Annotation a : m.getAnnotations()) {
											if (a instanceof Description) {
												// 物料名是不可能修改的，只有增、删
												if ("新旧物料".equals(((Description) a).value())) {
													String silkBe = "旧物料";//创建翻单的时候默认就是旧的，所以要默认为旧
													String silkAf = "旧物料";
													if ("2".equals("" + m.invoke(c, new Object[0]))) {
														silkAf = "新物料";
													}
													/*compareString(silkBe, silkAf, "硬件-" + h.getName() + "-" + ((Description) a).value(), alt1, currUserId, sellerId,
															merchandiserId, orderNo, i, orderInfoCommand.getState());*/
													compareString(silkBe, silkAf, h.getName(), alt1, currUserId, sellerId,
															merchandiserId, orderNo, i, orderInfoCommand.getState());
												} else {
													/*compareString(h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]), m.invoke(c, new Object[0]), "硬件-"
															+ h.getName() + "-" + ((Description) a).value(), alt1, currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
													compareString(h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]), m.invoke(c, new Object[0]), 
														 h.getName(), alt1, currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
												}
											}
										}
									}
									tempMap.remove(c.getName());// 移除掉修改的，剩下的就应该删除操作
								} else {// 增加，多人操作可能会导致：A先查出，B删除，A再插入。
									String isNew = "旧物料";
									if (c.getIsNew() == 2)
										isNew = "新物料";
									if (c.getSupplier() == null) c.setSupplier("");if (c.getSpecification() == null) c.setSpecification("");
									/*compareString("", "物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "硬件", alt1,
											currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
									compareString("", "物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, c.getName(), alt1,
											currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
								}
							}
							for (OrderHardware c : tempMap.values()) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								if (c.getSupplier() == null) c.setSupplier("");if (c.getSpecification() == null) c.setSpecification("");
								/*compareString("物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", "硬件", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								compareString("供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", c.getName(), alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						}
					}
					else{
						Set<OrderHardware> beforeSet = orderInfoBefore.getOrderHardwares();//
						if (beforeSet != null && !beforeSet.isEmpty()) {
							for(OrderHardware c : beforeSet){
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								if (c.getSupplier() == null) c.setSupplier("");if (c.getSpecification() == null) c.setSpecification("");
								/*compareString("物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", "硬件", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								compareString("供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", c.getName(), alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						}
					}
				} catch (Exception e) {
					log.error("计算订单硬件变更记录错误"+orderNo, e);
				}
				try {
					// 订单包材物料
					if (orderInfoCommand.getOrderPackingCommands() != null && !orderInfoCommand.getOrderPackingCommands().isEmpty()) {
						Set<OrderPacking> beforeSet = orderInfoBefore.getOrderPackings();//
						Set<OrderPackingCommand> afterSet = orderInfoCommand.getOrderPackingCommands();
						if (beforeSet == null || beforeSet.isEmpty()) {// 原有数据为空，则全部是增加的
							for (OrderPackingCommand c : afterSet) {
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								if (c.getSupplier() == null) c.setSupplier("");if (c.getSpecification() == null) c.setSpecification("");
								/*compareString("", "物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "包材", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								compareString("", "供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, c.getName(), alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						} else {
							Map<String, OrderPacking> tempMap = new HashMap<>();
							for (OrderPacking before : beforeSet) {
								tempMap.put(before.getName(), before);
							}
							for (OrderPackingCommand c : afterSet) {
								// 删除或者修改
								if (tempMap.get(c.getName()) != null) {// 修改操作
									OrderPacking h = tempMap.get(c.getName());
									Class<? extends OrderPackingCommand> cl = c.getClass();
									Method[] methods = cl.getMethods();
									for (Method m : methods) {
										for (Annotation a : m.getAnnotations()) {
											if (a instanceof Description) {
												// 物料名是不可能修改的，只有增、删
												if ("新旧物料".equals(((Description) a).value())) {
													String silkBe = "旧物料";//创建翻单的时候默认就是旧的，所以要默认为旧
													String silkAf = "旧物料";
													if ("2".equals("" + m.invoke(c, new Object[0]))) {
														silkAf = "新物料";
													}
													/*compareString(silkBe, silkAf, "包材-" + h.getName() + "-" + ((Description) a).value(), alt1, currUserId, sellerId,
															merchandiserId, orderNo, i, orderInfoCommand.getState());*/
													compareString(silkBe, silkAf, h.getName(), alt1, currUserId, sellerId,
															merchandiserId, orderNo, i, orderInfoCommand.getState());
												} else {
													/*compareString(h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]), m.invoke(c, new Object[0]), "包材-"
															+ h.getName() + "-" + ((Description) a).value(), alt1, currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
													compareString(h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]), m.invoke(c, new Object[0]), 
															h.getName(), alt1, currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());

												}
											}
										}
									}
									tempMap.remove(c.getName());// 移除掉修改的，剩下的就应该删除操作
								} else {// 增加，多人操作可能会导致：A先查出，B删除，A再插入。
									String isNew = "旧物料";
									if (c.getIsNew() == 2)
										isNew = "新物料";
									if (c.getSupplier() == null) c.setSupplier("");if (c.getSpecification() == null) c.setSpecification("");
									/*compareString("", "物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "包材", alt1,
											currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
									compareString("", "供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, c.getName(), alt1,
											currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
								}
							}
							for (OrderPacking c : tempMap.values()) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								if (c.getSupplier() == null) c.setSupplier("");if (c.getSpecification() == null) c.setSpecification("");
								/*compareString("物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", "包材", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								compareString("供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", c.getName() , alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						}
					}else{
						Set<OrderPacking> beforeSet = orderInfoBefore.getOrderPackings();//
						if (beforeSet != null && !beforeSet.isEmpty()) {
							for(OrderPacking c : beforeSet){
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								if (c.getSupplier() == null) c.setSupplier("");if (c.getSpecification() == null) c.setSpecification("");
								/*compareString("物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", "包材", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								compareString("供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", c.getName() , alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						}
					}
				} catch (Exception e) {
					log.error("计算订单包材变更记录错误"+orderNo, e);
				}
				try {
					// 定单配件物料
					if (orderInfoCommand.getOrderFittingCommands() != null && !orderInfoCommand.getOrderFittingCommands().isEmpty()) {
						Set<OrderFitting> beforeSet = orderInfoBefore.getOrderFittings();//
						Set<OrderFittingCommand> afterSet = orderInfoCommand.getOrderFittingCommands();
						if (beforeSet == null || beforeSet.isEmpty()) {// 原有数据为空，则全部是增加的
							for (OrderFittingCommand c : afterSet) {
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								if (c.getSupplier() == null) c.setSupplier("");if (c.getSpecification() == null) c.setSpecification("");
								/*compareString("", "物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "配件", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								compareString("", "供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew,  c.getName(), alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						} else {
							Map<String, OrderFitting> tempMap = new HashMap<>();
							for (OrderFitting before : beforeSet) {
								tempMap.put(before.getName(), before);
							}
							for (OrderFittingCommand c : afterSet) {
								// 删除或者修改
								if (tempMap.get(c.getName()) != null) {// 修改操作
									OrderFitting h = tempMap.get(c.getName());
									Class<? extends OrderFittingCommand> cl = c.getClass();
									Method[] methods = cl.getMethods();
									for (Method m : methods) {
										for (Annotation a : m.getAnnotations()) {
											if (a instanceof Description) {
												// 物料名是不可能修改的，只有增、删
												if ("新旧物料".equals(((Description) a).value())) {
													String silkBe = "旧物料";//创建翻单的时候默认就是旧的，所以要默认为旧
													String silkAf = "旧物料";
													if ("2".equals("" + m.invoke(c, new Object[0]))) {
														silkAf = "新物料";
													}
													/*compareString(silkBe, silkAf, "配件-" + h.getName() + "-" + ((Description) a).value(), alt1, currUserId, sellerId,
															merchandiserId, orderNo, i, orderInfoCommand.getState());*/
													compareString(silkBe, silkAf, h.getName(), alt1, currUserId, sellerId,
															merchandiserId, orderNo, i, orderInfoCommand.getState());
												} else {
													/*compareString(h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]), m.invoke(c, new Object[0]), "配件-"
															+ h.getName() + "-" + ((Description) a).value(), alt1, currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
													compareString(h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]), m.invoke(c, new Object[0]), 
															 h.getName(), alt1, currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
												}
											}
										}
									}
									tempMap.remove(c.getName());// 移除掉修改的，剩下的就应该删除操作
								} else {// 增加，多人操作可能会导致：A先查出，B删除，A再插入。
									String isNew = "旧物料";
									if (c.getIsNew() == 2)
										isNew = "新物料";
									if (c.getSupplier() == null) c.setSupplier("");if (c.getSpecification() == null) c.setSpecification("");
									/*compareString("", "物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "配件", alt1,
											currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
									compareString("", "供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, c.getName(), alt1,
											currUserId, sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
								}
							}
							for (OrderFitting c : tempMap.values()) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								if (c.getSupplier() == null) c.setSupplier("");if (c.getSpecification() == null) c.setSpecification("");
								/*compareString("物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", "配件", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								compareString("供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", c.getName(), alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						}
					}else{
						Set<OrderFitting> beforeSet = orderInfoBefore.getOrderFittings();//
						if (beforeSet != null && !beforeSet.isEmpty()) {
							for(OrderFitting c : beforeSet){
								String isNew = "旧物料";
								if (c.getIsNew() == 2)
									isNew = "新物料";
								if (c.getSupplier() == null) c.setSupplier("");if (c.getSpecification() == null) c.setSpecification("");
								/*compareString("物料名称：" + c.getName() + "；供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", "配件", alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());*/
								compareString("供应商：" + c.getSupplier() + "；规格型号：" + c.getSpecification() + "；新旧物料：" + isNew, "", c.getName(), alt1, currUserId,
										sellerId, merchandiserId, orderNo, i, orderInfoCommand.getState());
							}
						}
					}
					//增加调用的，所以肯定没有审核通过，所以没必要增加未读、通知信息（页面也不会显示出未审核的订单）
					
				} catch (Exception e) {
					log.error("计算订单配件变更记录错误"+orderNo, e);
				}
			}
		});
	}

}
