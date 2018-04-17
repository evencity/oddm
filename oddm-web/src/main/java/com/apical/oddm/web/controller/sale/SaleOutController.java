package com.apical.oddm.web.controller.sale;

import java.io.OutputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.core.constant.OrderInfoConst;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;
import com.apical.oddm.facade.sale.out.SaleOutDTO;
import com.apical.oddm.facade.sale.out.SaleOutFacadeI;
import com.apical.oddm.facade.util.TimeUtil;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.framework.constant.GlobalConstant;
import com.apical.oddm.web.pageModel.base.SessionInfo;

@Controller
@RequestMapping("/saleOut")
public class SaleOutController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(SaleOutController.class);

	@Autowired
	private SaleOutFacadeI saleOutFacade;
	
	/*@RequestMapping("/manager")
	public String manager() {
		return "/sale/saleOut/saleOutList";
	}*/
	@RequestMapping("/manager")
	public String manager() {
		return "/sale/saleOut/saleOut";
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody  //jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<OrderInfoDTO> dataGrid(HttpServletRequest request, OrderInfoCommand orderInfoCommand, PageCommand pageCommand) {  //此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		try {
			/*SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
			Integer userId = sessionInfo.getId();
			if (userId == null) return null;
			SystemContext.setCurrUserId(userId);*/
			SystemContext.setPageOffset(pageCommand.getPage());
			SystemContext.setPageSize(pageCommand.getRows());
			SystemContext.setOrder(pageCommand.getOrder());
			SystemContext.setSort(pageCommand.getSort());
			SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
			Set<Integer> states = new HashSet<Integer>();
			states.add(OrderInfoConst.approved);
			states.add(OrderInfoConst.materialfinished);
			BasePage<OrderInfoDTO> pageList = saleOutFacade.dataGridForState(orderInfoCommand, states,sessionInfo.getHasRoles());
			return pageList;
			//System.err.println(dataGrid.getRows());
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}
	@RequestMapping("/getMonthPage")
	public String getMonthPage(SaleOutDTO saleOutDTO, HttpServletRequest request) {
		request.setAttribute("saleOutDto", saleOutDTO);
		return "/sale/saleOut/saleOutMonList";
	}
	
	@RequestMapping("/excelExport")
	public void excelExport(SaleOutDTO saleOutDTO, HttpServletResponse response, HttpServletRequest request) {
		//SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		try {
			String fileName = "订单外销统计出库  ";
			//HSSFWorkbook wb = SaleOutExportExcel.export(list, saleOutDto.getShipmentMonth());
			fileName = new String(fileName.getBytes("gb2312"), "ISO-8859-1");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + TimeUtil.dateToStringForExcel(new Date()) + ".xls");
			OutputStream ouputStream = response.getOutputStream();
			//wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			log.error("导表出错：", e);
		}
	}
}
