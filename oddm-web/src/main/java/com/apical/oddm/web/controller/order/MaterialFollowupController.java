package com.apical.oddm.web.controller.order;

import java.io.File;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.core.constant.MaterialFollowupConst;
import com.apical.oddm.core.constant.OrderFollowupConst;
import com.apical.oddm.core.constant.OrderInfoConst;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.materialFollow.MaterialFollowupFacade;
import com.apical.oddm.facade.materialFollow.command.MaterialFollowupCommand;
import com.apical.oddm.facade.materialFollow.dto.MaterialFollowupDTO;
import com.apical.oddm.facade.materialFollow.util.MaterialFollowExportExcel;
import com.apical.oddm.facade.order.OrderInfoFacade;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;
import com.apical.oddm.facade.util.TimeUtil;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.framework.constant.GlobalConstant;
import com.apical.oddm.web.pageModel.base.Json;
import com.apical.oddm.web.pageModel.base.SessionInfo;

@Controller
@RequestMapping("/materialFollowup")
public class MaterialFollowupController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(MaterialFollowupController.class);
	@Autowired
	private MaterialFollowupFacade materialFollowupFacade;
	@Autowired
	private OrderInfoFacade orderInfoFacade;

	@RequestMapping("/manager")
	public String manager() {
		return "/order/materialFollowup";
	}

	@RequestMapping("/managerAll")
	public String managerAll() {
		return "/order/materialFollowupAll";
	}

	/*@RequestMapping("/dataGrid")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<MaterialFollowupDTO> dataGrid(OrderInfoCommand orderInfoCommand, PageCommand pageCommand, HttpServletRequest request) { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		orderInfoCommand.setMerchandiserId(sessionInfo.getId());
		orderInfoCommand.setSellerId(sessionInfo.getId());
		BasePage<MaterialFollowupDTO> pageList = materialFollowupFacade.dataGridByOrderInfo(orderInfoCommand, pageCommand);
		return pageList;
	}

	@RequestMapping("/dataGridAll")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<MaterialFollowupDTO> dataGridAll(OrderInfoCommand orderInfoCommand, PageCommand pageCommand) { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		BasePage<MaterialFollowupDTO> pageList = materialFollowupFacade.dataGridByOrderInfo(orderInfoCommand, pageCommand);
		return pageList;
	}*/
	@RequestMapping("/dataGrid")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<OrderInfoDTO> dataGrid(OrderInfoCommand orderInfoCommand, PageCommand pageCommand, HttpServletRequest request) { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		orderInfoCommand.setMerchandiserId(sessionInfo.getId());
		orderInfoCommand.setSellerId(sessionInfo.getId());
		Set<Integer> states = new HashSet<Integer>();
		states.add(OrderInfoConst.approved);
		states.add(OrderInfoConst.materialfinished);
		BasePage<OrderInfoDTO> pageList = materialFollowupFacade.dataGridForState(orderInfoCommand, pageCommand, states,sessionInfo.getHasRoles());
		return pageList;
	}

	@RequestMapping("/dataGridAll")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public BasePage<OrderInfoDTO> dataGridAll(OrderInfoCommand orderInfoCommand, PageCommand pageCommand,HttpServletRequest request) { // 此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Set<Integer> states = new HashSet<Integer>();
		states.add(OrderInfoConst.approved);
		states.add(OrderInfoConst.materialfinished);
		BasePage<OrderInfoDTO> pageList = materialFollowupFacade.dataGridForState(orderInfoCommand, pageCommand, states,sessionInfo.getHasRoles());
		return pageList;
	}
	@RequestMapping("/checkOrderNoPage")
	public String checkOrderNoPage(HttpServletRequest request) {
		return "/order/materialFollowup_checkOrderNoPage";
	}

	// 判断订单号是否存在订单表中
	@RequestMapping("/checkOrderForOrder")
	@ResponseBody
	public Json checkOrderForOrder(OrderInfoCommand orderInfoCommand, HttpServletRequest request) {
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		try {
			if (sessionInfo.getId() != null) {
				orderInfoCommand.setMerchandiserId(sessionInfo.getId());
				orderInfoCommand.setSellerId(sessionInfo.getId());
				Boolean byOrderNo = materialFollowupFacade.getByOrder(orderInfoCommand);
				if (byOrderNo) {
					j.setSuccess(true);
					j.setMsg("订单号存在！");
				} else {
					j.setSuccess(false);
					j.setMsg("您输入的订单号不存在！");
				}
			}
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	// 判断订单号是否存在订单表中
	@RequestMapping("/checkOrderForOrderAll")
	@ResponseBody
	public Json checkOrderForOrderAll(OrderInfoCommand orderInfoCommand, HttpServletRequest request) {
		Json j = new Json();
		try {
			Boolean byOrderNo = materialFollowupFacade.getByOrder(orderInfoCommand);
			if (byOrderNo) {
				j.setSuccess(true);
				j.setMsg("订单号存在！");
			} else {
				j.setSuccess(false);
				j.setMsg("您输入的订单号不存在！");
			}
		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	@RequestMapping("/checkExcitByOrderId")
	@ResponseBody
	public Json checkExcitByOrderId(OrderInfoCommand orderInfoCommand, HttpServletRequest request) {
		Json j = new Json();
		try {
			Boolean byOrderNo = materialFollowupFacade.checkExcitByOrder(orderInfoCommand,null);
			if (byOrderNo) {
				j.setSuccess(true);
				j.setMsg("所选订单号已经存在物料交期，请直接进行编辑！！");
			} else {
				j.setSuccess(false);
				j.setMsg("查询不到相关物料交期内容！！");
			}
		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	@RequestMapping("/checkExcitByOrderNo")
	@ResponseBody
	public Json checkExcitByOrderNo(OrderInfoCommand orderInfoCommand, HttpServletRequest request) {
		Json j = new Json();
		try {
			Boolean byOrderNo = materialFollowupFacade.checkExcitByOrder(orderInfoCommand,null);
			if (byOrderNo) {
				j.setSuccess(true);
				j.setMsg("所选订单号已经存在物料交期，请直接进行编辑！！");
			} else {
				j.setSuccess(false);
				j.setMsg("查询不到相关物料交期内容！！");
			}
		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	/*@RequestMapping("/addPage")
	public String addPage(OrderInfoCommand orderInfoCommand, HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		if (orderInfoCommand.getOrderNo() != null) {
			OrderInfoDTO byOrderDTO = materialFollowupFacade.getByOrderDTO(orderInfoCommand,sessionInfo.getHasRoles());
			request.setAttribute("orderInfoDTO", byOrderDTO);
		}
		return "/order/materialFollowupAdd";
	}*/
	@RequestMapping("/addPage")
	public String addPage(OrderInfoCommand orderInfoCommand, HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		if (orderInfoCommand.getOrderNo() != null) {
			OrderInfoDTO byOrderDTO = materialFollowupFacade.getByOrderDTO(orderInfoCommand,sessionInfo.getHasRoles());
			request.setAttribute("orderInfoDTO", byOrderDTO);
			request.setAttribute("orderId", byOrderDTO.getId());
		}
		return "/order/materialFollowupEdit";
	}

	@RequestMapping("/add")
	@ResponseBody
	public Json add(@RequestBody MaterialFollowupCommand materialFollowupCommand) {
		Json j = new Json();
		try {
			Integer add = materialFollowupFacade.add(materialFollowupCommand);
			if (add!=null) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
				j.setObj(add);
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！");
			}
		} catch (ConstraintViolationException e) {
			j.setMsg("物料已存在！");
			log.error("", e);
		} catch(DataIntegrityViolationException  e){
			j.setSuccess(false);
			j.setMsg("物料已存在！");
			log.error("物料已存在！", e);
		}catch (Exception e) {
			j.setMsg(e.getMessage());
		}

		return j;
	}

	@RequestMapping("/getPage")
	public String getPage(HttpServletRequest request, Integer id) {
		request.setAttribute("orderId", id);
		return "/order/materialFollowupDetails";
	}

	@RequestMapping("/get")
	@ResponseBody
	public  List<MaterialFollowupDTO>  get(OrderInfoCommand orderInfoCommand) {
		return materialFollowupFacade.listAllByOrderId(orderInfoCommand, null);
	}
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		request.setAttribute("orderId", id);
		return "/order/materialFollowupEdit";
	}

	@RequestMapping("/editForSubmit")
	@ResponseBody
	public Json editForSubmit(@RequestBody MaterialFollowupCommand materialFollowupCommand, HttpServletRequest request) {
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		try {
			materialFollowupCommand.setState(MaterialFollowupConst.save);// 正式提交
			materialFollowupFacade.edit(materialFollowupCommand, sessionInfo.getId());
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/editForFinish")
	@ResponseBody
	public Json editForFinish(@RequestBody MaterialFollowupCommand materialFollowupCommand, HttpServletRequest request) {
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		try {
			materialFollowupCommand.setState(MaterialFollowupConst.archive);// 完结
			materialFollowupFacade.edit(materialFollowupCommand, sessionInfo.getId());
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		try {
			materialFollowupFacade.delete(id);
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	@RequestMapping("/excelPage")
	public String excelPage() {
		return "/order/materialFollow_excelPage";
	}

	@RequestMapping("/excelExport")
	public void excelExport(Integer state, HttpServletResponse response, HttpServletRequest request, OrderInfoCommand orderInfoCommand) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		List<MaterialFollowupDTO> listAll = null;
		Set<Integer> set = new HashSet<Integer>();
		String fileName = "";
		// OrderInfoCommand orderInfoCommand = new OrderInfoCommand();
		orderInfoCommand.setMerchandiserId(sessionInfo.getId());
		orderInfoCommand.setSellerId(sessionInfo.getId());
		if (state == 1) {
			listAll = materialFollowupFacade.listAll(orderInfoCommand, set);
			fileName = "全部物料交期";
		}
		if (state == 2) {
			set.add(MaterialFollowupConst.archive);
			listAll = materialFollowupFacade.listAll(orderInfoCommand, set);
			fileName = "物料交期(集齐)";
		}
		if (state == 3) {
			set.add(OrderFollowupConst.save);
			set.add(OrderFollowupConst.temporarysave);
			listAll = materialFollowupFacade.listAll(orderInfoCommand, set);
			fileName = "物料交期(未集齐)";
		}
		try {
			String imagePath = File.separator + "style" + File.separator + "images" + File.separator + "materialFollowupLogo.png";

			String realPath = request.getSession().getServletContext().getRealPath(imagePath);
			HSSFWorkbook wb = MaterialFollowExportExcel.export(listAll, realPath);
			fileName = new String(fileName.getBytes("gb2312"), "ISO-8859-1");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + TimeUtil.dateToStringForExcel(new Date()) + ".xls");
			OutputStream ouputStream = response.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("导表出错：", e);
		}
	}

	@RequestMapping("/excelExportAll")
	public void excelExportAll(Integer state, HttpServletResponse response, HttpServletRequest request, OrderInfoCommand orderInfoCommand) {
		List<MaterialFollowupDTO> listAll = null;
		String fileName = "";
		Set<Integer> set = new HashSet<Integer>();
		// OrderInfoCommand orderInfoCommand = new OrderInfoCommand();
		if (state == 1) {
			listAll = materialFollowupFacade.listAll(orderInfoCommand, set);
			fileName = "全部物料交期";
		}
		if (state == 2) {
			set.add(MaterialFollowupConst.archive);
			listAll = materialFollowupFacade.listAll(orderInfoCommand, set);
			fileName = "物料交期(集齐)";
		}
		if (state == 3) {
			set.add(OrderFollowupConst.save);
			set.add(OrderFollowupConst.temporarysave);
			listAll = materialFollowupFacade.listAll(orderInfoCommand, set);
			fileName = "物料交期(未集齐)";
		}
		//System.out.println(">>>>>>>>"+orderInfoCommand.getDateOrderStart()+orderInfoCommand.getDateOrderEnd());
		try {
			String imagePath = File.separator + "style" + File.separator + "images" + File.separator + "materialFollowupLogo.png";
			String realPath = request.getSession().getServletContext().getRealPath(imagePath);
			HSSFWorkbook wb = MaterialFollowExportExcel.export(listAll, realPath);
			fileName = new String(fileName.getBytes("gb2312"), "ISO-8859-1");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + TimeUtil.dateToStringForExcel(new Date()) + ".xls");
			OutputStream ouputStream = response.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("导表出错：", e);
		}
	}
	@RequestMapping("/qualityTotal")
	@ResponseBody
	public  Integer  qualityTotal(Integer orderId) {
		Integer total = 0;
		if(orderId != null){
			total = materialFollowupFacade.qualityTotal(orderId);
			System.out.println("..................."+total);
		}
		return total;
	}
}
