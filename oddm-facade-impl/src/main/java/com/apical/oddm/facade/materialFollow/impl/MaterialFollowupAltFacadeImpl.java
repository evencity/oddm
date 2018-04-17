package com.apical.oddm.facade.materialFollow.impl;



import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.order.MaterialFollowupAlterServiceI;
import com.apical.oddm.application.order.MaterialFollowupServiceI;
import com.apical.oddm.application.order.OrderInfoServiceI;
import com.apical.oddm.application.sys.UserServiceI;
import com.apical.oddm.core.constant.CrudConst;
import com.apical.oddm.core.model.order.MaterialFollowup;
import com.apical.oddm.core.model.order.MaterialFollowupAlter;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.materialFollow.MaterialFollowupAltFacade;
import com.apical.oddm.facade.materialFollow.command.MaterialFollowupAlterCommand;
import com.apical.oddm.facade.materialFollow.command.MaterialFollowupCommand;
import com.apical.oddm.facade.materialFollow.dto.MaterialFollowupAlterDTO;
import com.apical.oddm.facade.pool.ThreadPoolBiz;
import com.apical.oddm.facade.util.TimeUtil;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年11月6日 下午2:12:42 
 * @version 1.0 
 */
@Component("materialFollowupAltFacade")
public class MaterialFollowupAltFacadeImpl implements MaterialFollowupAltFacade{

	private static final Logger log = LoggerFactory.getLogger(MaterialFollowupAltFacadeImpl.class);

	private static FastDateFormat dayFmat = FastDateFormat.getInstance("yyyy-MM-dd");

	@Autowired
	private UserServiceI userService;
	
	@Autowired
	private OrderInfoServiceI orderInfoService;
	
	@Autowired
	private MaterialFollowupAlterServiceI materialFollowupAlterService;
	@Autowired
	private MaterialFollowupServiceI materialFollowupService;
	
	@Override
	public BasePage<MaterialFollowupAlterDTO> dataGrid(Integer materialFollowupId, PageCommand pageCommand) {
		// TODO Auto-generated method stub
		if(materialFollowupId != null){
			SystemContext.setPageOffset(pageCommand.getPage());
			SystemContext.setPageSize(pageCommand.getRows());
			SystemContext.setOrder(pageCommand.getOrder());
			SystemContext.setSort("t."+pageCommand.getSort());
			Pager<MaterialFollowupAlter> dataGrid = materialFollowupAlterService.dataGrid(materialFollowupId);
			BasePage<MaterialFollowupAlterDTO> basePage = new BasePage<MaterialFollowupAlterDTO>();
			SystemContext.setPageOffset(null);
			SystemContext.setPageSize(null);
			if(dataGrid != null){
				if(dataGrid.getRows().size() > 0){
					List<MaterialFollowupAlterDTO> list = new ArrayList<MaterialFollowupAlterDTO>();
					for(MaterialFollowupAlter materialFollowupAlter : dataGrid.getRows()){
						MaterialFollowupAlterDTO materialFollowupAlterDTO = new MaterialFollowupAlterDTO();
						BeanUtils.copyProperties(materialFollowupAlter, materialFollowupAlterDTO);
						if(materialFollowupAlter.getTimestamp() != null){
							materialFollowupAlterDTO.setTimestamp(TimeUtil.timestampToStringDetaile(materialFollowupAlter.getTimestamp()));
						}
						list.add(materialFollowupAlterDTO);
					}
					basePage.setRows(list);
					basePage.setTotal(dataGrid.getTotal());
				}
			}
			return basePage;
		}
		return null;
	}

	@Override
	public Boolean add(MaterialFollowupAlterCommand materialFollowupAlterCommand) {
		// TODO Auto-generated method stub
		if(materialFollowupAlterCommand != null){
			if(materialFollowupAlterCommand.getMaterialFollowupId() != null){
				MaterialFollowup materialFollowup = materialFollowupService.get(materialFollowupAlterCommand.getMaterialFollowupId());
				MaterialFollowupAlter materialFollowupAlter = new MaterialFollowupAlter();
				BeanUtils.copyProperties(materialFollowupAlterCommand, materialFollowupAlter);
				materialFollowupAlter.setMaterialFollowup(materialFollowup);
				
				Serializable add = materialFollowupAlterService.add(materialFollowupAlter);
				if(add != null){
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		}
		return false;
	}

	@Override
	public void addEditRecord(MaterialFollowup materialFollowu, MaterialFollowupCommand materialFollowupCommand, Integer currUserId1) {
		if (materialFollowu == null) return;
		MaterialFollowup materialFollowubefore = materialFollowu;//orderFollowupService.get(orderFollowupCommand.getId());
		ThreadPoolBiz.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				MaterialFollowupAlter alt1 = null;
				String currUserId = currUserId1+"";
				//String userId = null;;
				String orderNo = null;;
				try {
					String username = userService.get(currUserId1).getUsername();
					OrderInfo orderInfo = orderInfoService.get(materialFollowu.getOrderId());
					//userId = orderInfo.getSellerId()!=null?orderInfo.getSellerId()+"":null;
					orderNo = orderInfo.getOrderNo();
					alt1 = new MaterialFollowupAlter(new MaterialFollowup(materialFollowubefore.getId()), username);
				} catch (Exception e) {
					log.error("", e);
					return;
				}
				// 物料交期基本信息
				try {
					Class<? extends MaterialFollowupCommand> cl = materialFollowupCommand.getClass();
					Method[] methods = cl.getMethods();
					for (Method m : methods) {
						for (Annotation a : m.getAnnotations()) {
							if (a instanceof Description) {
								compareString(materialFollowubefore.getClass().getMethod(m.getName(), new Class[0]).invoke(materialFollowubefore,new Object[0]),
										m.invoke(materialFollowupCommand,new Object[0]), ((Description) a).value(), alt1, currUserId, orderNo);
							}
						}
					}
				} catch (Exception e) {
					log.error("计算跟物料交期变更记录错误", e);
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
	 */
	private void compareString(Object before, Object after, String item, MaterialFollowupAlter alt, String currUserId, String orderNo) {
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
		alt = new MaterialFollowupAlter(alt.getMaterialFollowup(), alt.getOperator());
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
			materialFollowupAlterService.add(alt);
		} catch (Exception e) {
			log.error("变更记录错误："+orderNo, e);
		}
		/*if (!StringUtils.equals(currUserId, sellerId)) {
			WebSocketSessionUtils.sendMessageToTarget(sellerId, new WebSocketMsg("跟单"+orderNo+"修改通知", alt.toString()));
		}*/
	}
}
