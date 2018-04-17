package com.apical.oddm.web.controller.bom;

import java.io.OutputStream;
import java.util.Date;

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

import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.bom.BomFacade;
import com.apical.oddm.facade.bom.dto.BomDTO;
import com.apical.oddm.facade.bom.dto.BomMaterialContactDTO;
import com.apical.oddm.facade.bom.dto.BomMaterialDTO;
import com.apical.oddm.facade.bom.util.BomExportExcel;
import com.apical.oddm.facade.order.OrderInfoFacade;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;
import com.apical.oddm.facade.util.TimeUtil;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.controller.order.OrderFollowupController;
import com.apical.oddm.web.framework.constant.GlobalConstant;
import com.apical.oddm.web.pageModel.base.Json;
import com.apical.oddm.web.pageModel.base.SessionInfo;

@Controller
@RequestMapping("/bom")
public class BomContorller extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(OrderFollowupController.class);
	
	@Autowired
	private BomFacade bomFacade;
	
	@Autowired
	private OrderInfoFacade orderInfoFacade;
	
	@RequestMapping("/manager")
	public String manager() {
		return "/bom/bom";
	}
	
	/**
	 * 分页查询
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public BasePage<BomDTO> dataGrid(BomDTO bomDTO, PageCommand pageCommand, HttpServletRequest request) { 
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		SystemContext.setCurrUserId(sessionInfo.getId());
		BasePage<BomDTO> dataGrid = bomFacade.dataGrid(bomDTO, null, pageCommand);
		return dataGrid;
	}
	  
	@RequestMapping("/getPage")
	public String getPage(HttpServletRequest request, Integer id) {
		if(id != null){
			request.setAttribute("bomId", id);
		}
		return "/bom/bomDetails";
	}
	
	/**
	 * 跳转查看bom界面
	 * 
	 * @return
	 */
	@RequestMapping(value="/findBomInfoByOrderNo")
	public String findBomInfoByOrderNo(HttpServletRequest request,Integer orderId) {
		request.setAttribute("orderId", orderId);
		return "/bom/bomDetailsForOrder";
	}
	@RequestMapping("/findBomInfo")
	@ResponseBody
	public BomDTO findBomInfo(Integer orderId, HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		return bomFacade.getBomByOrderId(orderId, sessionInfo.getId());
	}
	
	
	@RequestMapping("/get")
	@ResponseBody
	public BomDTO get(Integer id, HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		return bomFacade.getBomDetailById(id, sessionInfo.getId());
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		request.setAttribute("bomId", id);
		return "/bom/bomEdit";
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(@RequestBody BomDTO bomDTO, HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		try {
			bomFacade.edit(bomDTO,sessionInfo.getId(),sessionInfo.getName());
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		}catch(ConstraintViolationException e){
			j.setSuccess(false);
			j.setMsg("物料编码重复！");
			log.error("物料编码重复", e);
		}catch(DataIntegrityViolationException  e){
			j.setSuccess(false);
			j.setMsg("物料编码重复！");
			log.error("物料编码重复", e);
		}catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("系统出错：" + e.getMessage());
			log.error("Bom添加失败", e);
		}
		return j;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete( Integer id, HttpServletRequest request) {
		
		Json j = new Json();
		try {
			bomFacade.delete(id);
			j.setSuccess(true);
			j.setMsg("删除成功！");
		}  catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("系统出错：" + e.getMessage());
			log.error("Bom删除失败", e);
		}
		return j;
	}
	
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request,OrderInfoCommand orderInfoCommand) {
		if(orderInfoCommand.getOrderNo() != null && !"".equals(orderInfoCommand.getOrderNo())){
			 OrderInfoDTO orderInfoDTO = bomFacade.getByOrderDTO(orderInfoCommand);
			 request.setAttribute("orderInfoDTO", orderInfoDTO);
		}
		return "/bom/bomAdd";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Json add(@RequestBody BomDTO bomDTO, HttpServletRequest request) {
		//SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		try {
			Integer success = bomFacade.add(bomDTO);
			if (success != null) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！");
			}
		}catch(ConstraintViolationException e){
			j.setSuccess(false);
			j.setMsg("物料编码重复！");
			log.error("物料编码重复", e);
		}catch(DataIntegrityViolationException  e){
			j.setSuccess(false);
			j.setMsg("物料编码重复！");
			log.error("物料编码重复", e);
		}catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("系统出错：" + e.getMessage());
			log.error("Bom添加失败", e);
		}
		return j;
	}
	
	@RequestMapping("/checkOrderNoPage")
	public String checkOrderNoPage(HttpServletRequest request) {
		return "/bom/checkOrderNoPage";
	}

	// 判断订单号是否存在订单表中
	@RequestMapping("/checkOrderForOrder")
	@ResponseBody
	public Json checkOrderForOrder(OrderInfoCommand orderInfoCommand, HttpServletRequest request) {
		Json j = new Json();
		try {
			Boolean byOrderNo = bomFacade.getByOrder(orderInfoCommand);
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
	// 判断订单号是否存在任务书中
	@RequestMapping("/getBomByOrderNo")
	@ResponseBody
	public Json getBomByOrderNo(String orderNo, HttpServletRequest request) {
		Json j = new Json();
		try {
			BomDTO bomByOrderNo = bomFacade.getBomByOrderNo(orderNo);
			if (bomByOrderNo != null) {
				j.setSuccess(true);
				j.setMsg("您输入的订单号已绑定对应的Bom，请直接编辑！");
			} else {
				j.setSuccess(false);
				j.setMsg("您输入的订单号不存在！");
			}
		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	//判断主表中物料是否存在
	@RequestMapping("/isExistMaterialCode")
	@ResponseBody
	public Json isExistMaterialCode(String materialCode, HttpServletRequest request) {
		Json j = new Json();
		try {
			BomDTO bomDTO = bomFacade.getBomByMaterialCode(materialCode);
			if (bomDTO != null) {
				j.setSuccess(false);
				j.setMsg("您输入的物料编码已存在，请重新输入！！");
			} else {
				j.setSuccess(true);
				j.setMsg("您输入的物料编码不存在，请继续输入！！");
			}
		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	@RequestMapping("/chooseBomMaterial")
	public String chooseBomMaterial(HttpServletRequest request,String materialNames) {
		request.setAttribute("materialNames", materialNames);
		return "/bom/chooseMaterial";
	}
	
	@RequestMapping("/chooseBomMaterialTitle")
	public String chooseBomMaterialTitle(HttpServletRequest request,String materialNames) {
		request.setAttribute("materialNames", materialNames);
		return "/bom/chooseMaterialTitle";
	}
	
	@RequestMapping("/addBomContactPage")
	public String addBomContactPage(HttpServletRequest request,String materialNames) {
		return "/bom/bomContactAdd";
	}
	
	@RequestMapping("/editBomContactPage")
	public String editBomContactPage(HttpServletRequest request, BomMaterialContactDTO bomMaterialContact) {
		request.setAttribute("bomMaterialContact", bomMaterialContact);
		return "/bom/bomContactEdit";
	}
	
	@RequestMapping("/showBomContactPage")
	public String showBomContactPage(HttpServletRequest request, BomMaterialContactDTO bomMaterialContact) {
		request.setAttribute("bomMaterialContact", bomMaterialContact);
		return "/bom/bomContactDetails";
	}

	
	//导表
	@ResponseBody
	@RequestMapping(value="/excelExport")
	public void reportData(HttpServletResponse response,HttpServletRequest request,Integer id) {
		if(id != null){
		try {
			String fileName = "Bom物料清单"+TimeUtil.dateToStringForExcel(new Date()) ;
			HSSFWorkbook wb = BomExportExcel.export(bomFacade.getBomDetailById(id, null));
			fileName = new String(fileName.getBytes("gb2312"), "ISO-8859-1");
	        response.setContentType( "application/vnd.ms-excel" );    
	        response.setHeader( "Content-disposition" ,  "attachment;filename="+fileName+".xls" ); 
	        OutputStream ouputStream = response.getOutputStream();    
	        wb.write(ouputStream);    
	        ouputStream.flush();    
	        ouputStream.close(); 
		} catch (Exception e) {
			// TODO: handle exception
			log.error("导表出错：",e);
		}
		}
	}
	
	@RequestMapping("/review")
	@ResponseBody
	public Json review(Integer id, HttpServletRequest request,String orderNo) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		try {
			bomFacade.review(id,sessionInfo.getId(),orderNo);
			j.setSuccess(true);
			j.setMsg("审核成功！");
		} catch (ServiceException e) {
			j.setMsg("编辑失败：" + e.getMessage());
			log.error("编辑失败", e);
		}
		return j;
	}
	
	@ResponseBody
	@RequestMapping("/getByMaterialCode")
	public BomMaterialDTO getByMaterialCode(String materialCode) {
		return bomFacade.getByMaterialCode(materialCode);
	}
}
