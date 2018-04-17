package com.apical.oddm.web.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.bom.BomUnreadServiceI;
import com.apical.oddm.application.document.DocumentUnreadServiceI;
import com.apical.oddm.application.order.OrderUnreadServiceI;
import com.apical.oddm.application.produce.ManufactureUnreadServiceI;
import com.apical.oddm.application.sale.SalePoUnreadServiceI;

@Component("myClearUnreadBean") //得在这里加注解，否则下面的 @Autowired无效
public class MyClearUnreadJob {  
	private static final Logger log = LoggerFactory.getLogger(MyWebsocketJob.class);

	@Autowired
	private OrderUnreadServiceI orderUnreadService;
	
	@Autowired
	private DocumentUnreadServiceI documentUnreadService;
	
	@Autowired
	private ManufactureUnreadServiceI manufactureUnreadService;
	
	@Autowired
	private BomUnreadServiceI bomUnreadService;
	
	@Autowired
	private SalePoUnreadServiceI salePoUnreadService;
	
	/**
	 * 删除几个月前的未读记录，避免越来越多
	 */
	private int month = 2;
	
	public void execute() {
		try {
			orderUnreadService.deleteMonth(month);
			documentUnreadService.deleteMonth(month);
			manufactureUnreadService.deleteMonth(month);
			bomUnreadService.deleteMonth(month);
			salePoUnreadService.deleteMonth(month);
			log.info("定时任务删除"+month+"个月未读记录");
		} catch (Exception e) {
			log.error("定时任务删除"+month+"个月未读记录", e);
		}
	}
}
