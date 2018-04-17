package com.apical.oddm.web.controller.encase;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.encase.dto.EncaseInfoDTO;
import com.apical.oddm.facade.encase.facade.EncaseFacade;
import com.apical.oddm.facade.encase.util.ReportEncaseExcel;
import com.apical.oddm.facade.pageModel.DataGrid;
import com.apical.oddm.facade.pageModel.Json;
import com.apical.oddm.web.controller.order.OrderFollowupController;

@Controller
@RequestMapping("/encase")
public class EncaseController {
	
	private static final Logger log = LoggerFactory.getLogger(OrderFollowupController.class);

	@Autowired
	private EncaseFacade encaseFacade;
	
	@Autowired  
	private HttpServletRequest request;  
	
	@RequestMapping("/manager")
	public String manager() {
		return "/encase/encase-list";
	}
	
	@ResponseBody
	@RequestMapping(value="/getEncaseInfoInPage")
	public DataGrid getEncaseInfoInPage(EncaseInfoDTO encaseDTO, PageCommand pageCommand){
		return encaseFacade.getEncaseInfoInPage(encaseDTO, pageCommand);
	}
	
	/**
	 * 跳转增加集装箱界面
	 * 
	 * @return
	 */
	@RequestMapping(value="/addEncasePage")
	public String addEncasePage() {
		return "/encase/addEncase";
	}
	
	/**
	 * 跳转查看集装箱界面
	 * 
	 * @return
	 */
	@RequestMapping(value="/findEncaseInfo")
	public String findEncaseInfo(int id) {
		 EncaseInfoDTO encaseInfo = encaseFacade.getEncaseInfoById(id);
		  request.setAttribute("encaseInfo", encaseInfo);
		return "/encase/editEncase";
	}
	
	@ResponseBody
	@RequestMapping(value="/add")
	public Json addEncaseInfo(@RequestBody EncaseInfoDTO encaseInfo){
		boolean flag = encaseFacade.addEncaseInfo(encaseInfo);
		Json j = new Json();
		if(flag){
			j.setSuccess(true);
			j.setMsg("添加成功");
		}else {
			j.setSuccess(false);
			j.setMsg("添加失败");
		}
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value="/edit")
	public Json updateEncaseInfo(@RequestBody EncaseInfoDTO encaseInfo){
		System.out.println("=========="+encaseInfo.getId());
		System.out.println("=========="+encaseInfo.getTimestamp());
		boolean flag = encaseFacade.updateEncaseInfo(encaseInfo);
		Json j = new Json();
		if(flag){
			j.setSuccess(true);
			j.setMsg("修改成功");
		}else {
			j.setSuccess(false);
			j.setMsg("修改失败");
		}
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value="/del")
	public Json delEncaseInfo(int id){
		return null;
	}
	
	@RequestMapping(value="/get")
	public String getEncaseInfoById(int id){
		 EncaseInfoDTO encaseInfo = encaseFacade.getEncaseInfoById(id);
		  request.setAttribute("encaseInfo", encaseInfo);
		  return "/encase/encase-view";
	}
	
	@ResponseBody
	@RequestMapping(value="/reportData")
	public void reportData(HttpServletResponse response,HttpServletRequest request,int id) {
		String fileName = encaseFacade.getEncaseInfoById(id).getName();
		try {
			HSSFWorkbook wb = new ReportEncaseExcel().creatExcelEncaseInfo(encaseFacade.getEncaseInfoById(id));
			fileName=new String(fileName.getBytes("utf-8"),"utf-8");
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
	@ResponseBody
	@RequestMapping(value="/delEncaseList")
	public Json delEncaseList(int id) {
	  boolean flag = encaseFacade.delEncaseList(id);
	  Json json = new Json();
	  if(flag){
		  json.setMsg("删除成功");
		  json.setSuccess(true);
	  }else{
		  json.setMsg("删除失败");
		  json.setSuccess(false);
	  }
	  return json;
	}
}
