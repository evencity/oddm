package com.apical.oddm.facade.order.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.order.OrderFollowupAlterServiceI;
import com.apical.oddm.application.order.OrderFollowupServiceI;
import com.apical.oddm.application.order.OrderInfoServiceI;
import com.apical.oddm.application.sys.SysConfigServiceI;
import com.apical.oddm.application.sys.UserServiceI;
import com.apical.oddm.core.constant.CrudConst;
import com.apical.oddm.core.model.order.OrderFollowup;
import com.apical.oddm.core.model.order.OrderFollowupAlter;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.order.OrderFollowupAlterFacade;
import com.apical.oddm.facade.order.command.OrderFollowupAlterCommand;
import com.apical.oddm.facade.order.command.OrderFollowupCommand;
import com.apical.oddm.facade.order.dto.OrderFollowupAlterDTO;
import com.apical.oddm.facade.pool.ThreadPoolBiz;
import com.apical.oddm.facade.util.TimeUtil;

/**
 * @description 类的描述：
 * @author 作者 : zhuzh
 * @date 创建时间：2016年10月31日 下午5:51:25
 * @version 1.0
 */
@Component("orderFollowupAlterFacade")
public class OrderFollowupAlterFacadeImpl implements OrderFollowupAlterFacade {

	private static final Logger log = LoggerFactory.getLogger(OrderFollowupAlterFacadeImpl.class);

	private static FastDateFormat dayFmat = FastDateFormat.getInstance("yyyy-MM-dd");

	@Autowired
	private UserServiceI userService;

	@Autowired
	private OrderFollowupServiceI orderFollowupService;

	@Autowired
	private SysConfigServiceI sysConfigService;

	@Autowired
	private OrderFollowupAlterServiceI orderFollowupAlterService;

	@Autowired
	private OrderInfoServiceI orderInfoService;

	@Override
	public BasePage<OrderFollowupAlterDTO> dataGrid(Integer followUpId, PageCommand pageCommand) {
		// TODO Auto-generated method stub

		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		SystemContext.setSort("t." + pageCommand.getSort());
		Pager<OrderFollowupAlter> dataGrid = orderFollowupAlterService.dataGrid(followUpId);
		BasePage<OrderFollowupAlterDTO> basePage = new BasePage<OrderFollowupAlterDTO>();
		if (dataGrid != null && dataGrid.getRows().size() > 0) {
			List<OrderFollowupAlterDTO> list = new ArrayList<OrderFollowupAlterDTO>();
			for (OrderFollowupAlter orderFollowupAlter : dataGrid.getRows()) {
				OrderFollowupAlterDTO orderFollowupAlterDTO = new OrderFollowupAlterDTO();
				BeanUtils.copyProperties(orderFollowupAlter, orderFollowupAlterDTO);
				if (orderFollowupAlter.getTimestamp() != null) {
					orderFollowupAlterDTO.setTimestamp(TimeUtil.timestampToStrings(orderFollowupAlter.getTimestamp()));
				}
				list.add(orderFollowupAlterDTO);
			}
			basePage.setRows(list);
			basePage.setTotal(dataGrid.getTotal());
		}
		return basePage;
	}

	@Override
	public Boolean add(OrderFollowupAlterCommand orderFollowupAlterCommand) {
		// TODO Auto-generated method stub
		OrderFollowupAlter orderFollowupAlter = new OrderFollowupAlter();
		BeanUtils.copyProperties(orderFollowupAlterCommand, orderFollowupAlter);

		Serializable add = orderFollowupAlterService.add(orderFollowupAlter);
		if (add != null) {
			return true;
		}
		return false;
	}

	/**
	 * @param orderFollowupbefore
	 *            变更前跟单对象
	 * @param orderFollowupCommand
	 *            变更后跟单对象
	 * @param currUserId
	 *            当前用户id
	 */
	@Override
	public void addEditRecord(OrderFollowup orderFollowup, OrderFollowupCommand orderFollowupCommand, Integer currUserId1) {
		if (orderFollowup == null) return;
		OrderFollowup orderFollowupbefore = orderFollowup;// orderFollowupService.get(orderFollowupCommand.getId());
		ThreadPoolBiz.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				OrderFollowupAlter alt1 = null;
				String currUserId = currUserId1 + "";
				// String userId = null;
				String orderNo = null;
				AtomicInteger i = new AtomicInteger(0);// 统计变成项的多少，更新订单为未读
				try {
					String username = userService.get(currUserId1).getUsername();
					OrderInfo orderInfo = orderInfoService.get(orderFollowupbefore.getOrderId());
					// userId = orderInfo.getSellerId()!=null?orderInfo.getSellerId()+"":null;
					orderNo = orderInfo.getOrderNo();
					alt1 = new OrderFollowupAlter(new OrderFollowup(orderFollowupbefore.getId()), username);
				} catch (Exception e) {
					log.error(""+orderNo, e);
					return;
				}
				// 跟单基本信息
				try {
					Class<? extends OrderFollowupCommand> cl = orderFollowupCommand.getClass();
					Method[] methods = cl.getMethods();
					for (Method m : methods) {
						for (Annotation a : m.getAnnotations()) {
							if (a instanceof Description) {
								compareString(orderFollowupbefore.getClass().getMethod(m.getName(), new Class[0]).invoke(orderFollowupbefore, new Object[0]),
										m.invoke(orderFollowupCommand, new Object[0]), ((Description) a).value(), alt1, currUserId, orderNo, i);
							}
						}
					}
					//单个表可以不用
					/*if (i.get() > 0) {// 修改相应的订单为未读、更新时间
						//int currUid = currUserId1 == null ? 0 : currUserId1;
						orderFollowupService.updateUpdateTime(orderFollowupbefore.getId());
					}*/
				} catch (Exception e) {
					log.error("计算跟单信息变更记录错误"+orderNo, e);
				}
				
			}
		});
	}

	/**
	 * 比较量字符串
	 * 
	 * @param before
	 *            变更前
	 * @param after
	 *            变更后
	 * @param item
	 *            变更项
	 * @param alt
	 * @param currUserId
	 *            当前登录用户
	 * @param sellerId
	 *            要发消息给对应的用户
	 */
	private void compareString(Object before, Object after, String item, OrderFollowupAlter alt, String currUserId, String orderNo, AtomicInteger i) {
		// System.err.println("beforebeforebefore: " + before+"\t ,after:"+after);
		String changeBe = null;
		String changeAf = null;
		// bug注意：1、null
		if (before instanceof Date) {// 格式化日期后再判断
			changeBe = dayFmat.format(before);
		} else if (before != null) {// null+"" 会变成 "null",小心
			changeBe = StringUtils.stripToNull(before + "");// 会转成null
			if (changeBe != null)
				changeBe = changeBe.replaceAll("\r|\n", "");
		}
		if (after instanceof Date) {// 格式化日期后再判断
			changeAf = dayFmat.format(after);
		} else if (after != null) {
			changeAf = StringUtils.stripToNull(after + ""); // 会转成null
			if (changeAf != null)
				changeAf = changeAf.replaceAll("\r|\n", "");
		}
		// bug注意：2、换行符
		if (StringUtils.equalsIgnoreCase(changeBe, changeAf)) {
			// x do nothing
			// System.err.println("do nothing " + item);
			return;
		}
		alt = new OrderFollowupAlter(alt.getOrderFollowup(), alt.getOperator());
		alt.setAlteritem(item);
		if (!"状态更新".equals(item) && !"备注".equals(item) && !"工厂交期".equals(item) && !"客户交期".equals(item) && !"方案".equals(item)) {
			if ("3".equals(changeBe)) {
				changeBe = "OK";
			} else if ("2".equals(changeBe)) {
				changeBe = "NO";
			} else if ("1".equals(changeBe)) {
				changeBe = "N/A";
			}
			if ("3".equals(changeAf)) {
				changeAf = "OK";
			} else if ("2".equals(changeAf)) {
				changeAf = "NO";
			} else if ("1".equals(changeAf)) {
				changeAf = "N/A";
			}
		}
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
			orderFollowupAlterService.add(alt);
		} catch (Exception e) {
			log.error("变更记录错误："+orderNo, e);
		}
		/*
		 * if (!StringUtils.equals(currUserId, sellerId)) { WebSocketSessionUtils.sendMessageToTarget(sellerId, new WebSocketMsg("跟单"+orderNo+"修改通知", alt.toString())); }
		 */
	}
/*	//出货记录计算用到
	private void compareStringFolloupOut(Object before, Object after, String item, OrderFollowupAlter alt, String currUserId, String orderNo, AtomicInteger i) {
		// System.err.println("beforebeforebefore: " + before+"\t ,after:"+after);
		String changeBe = null;
		String changeAf = null;
		// bug注意：1、null
		if (before instanceof Date) {// 格式化日期后再判断
			changeBe = dayFmat.format(before);
		} else if (before != null) {// null+"" 会变成 "null",小心
			changeBe = StringUtils.stripToNull(before + "");// 会转成null
			if (changeBe != null)
				changeBe = changeBe.replaceAll("\r|\n", "");
		}
		if (after instanceof Date) {// 格式化日期后再判断
			changeAf = dayFmat.format(after);
		} else if (after != null) {
			changeAf = StringUtils.stripToNull(after + ""); // 会转成null
			if (changeAf != null)
				changeAf = changeAf.replaceAll("\r|\n", "");
		}
		// bug注意：2、换行符
		if (StringUtils.equalsIgnoreCase(changeBe, changeAf)) {
			// x do nothing
			// System.err.println("do nothing " + item);
			return;
		}
		alt = new OrderFollowupAlter(alt.getOrderFollowup(), alt.getOperator());
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
			orderFollowupAlterService.add(alt);
		} catch (Exception e) {
			log.error("变更记录错误："+orderNo, e);
		}
	}*/
	// 封装变更记录通过websocket消息发送至页面
	/*
	 * private String altInfoToString(int operateType, String beforeInfo, String afterInfo, String operator, String alteritem) { String operateTy = ""; if (operateType == 1) {
	 * operateTy = "增加"; } else if (operateType == 2) { operateTy = "删除"; } else if (operateType == 3) { operateTy = "修改"; } else { return "变更消息异常，请联系开发人员！"; } beforeInfo =
	 * beforeInfo==null?"":beforeInfo; afterInfo = afterInfo==null?"":afterInfo; String result = "用户‘"+operator+"’执行‘"+operateTy+"’操作;" + "<br/>变更项 ["+ alteritem + "];" +
	 * "<br/>变更前 [" + beforeInfo +"];" + "<br/>变更后 [" + afterInfo + "];" + "<br/>操作时间 ["+ OddmDateUtil.dateFmat.format(System.currentTimeMillis()) + "]";
	 * //System.err.println(result); return result; }
	 */
}
