package com.apical.oddm.web.controller.bom;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.bom.BomMaterialContactFacade;
import com.apical.oddm.facade.bom.dto.BomMaterialContactDTO;
import com.apical.oddm.facade.pageModel.DataGrid;
import com.apical.oddm.facade.pageModel.Json;

@Controller
@RequestMapping("/bomMaterialContact")
public class BomMaterialContactController {

	private static final Logger log = LoggerFactory.getLogger(BomMaterialContactController.class);
	@Autowired
	private BomMaterialContactFacade bomMaterialContactFacade;
	
	@RequestMapping("/manager")
	public String manager() {
		return "/bom/bomContace-list";
	}
	
	@RequestMapping("/addPage")
	public String addPage() {
		return "/bom/bomContact-add";
	}
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, int id) {
		System.out.println(id);
		BomMaterialContactDTO bomMaterialContact = bomMaterialContactFacade.getBomMaterialContactById(id);
		request.setAttribute("bomMaterialContact", bomMaterialContact);
		System.out.println(request.getAttribute("bomMaterialContact"));
		return "/bom/bomContact-edit";
	}
	@RequestMapping("/viewPage")
	public String viewPage(HttpServletRequest request, int id) {
		System.out.println(id);
		BomMaterialContactDTO bomMaterialContact = bomMaterialContactFacade.getBomMaterialContactById(id);
		request.setAttribute("bomMaterialContact", bomMaterialContact);
		System.out.println(request.getAttribute("bomMaterialContact"));
		return "/bom/bomContact-view";
	}
	
	@ResponseBody
	@RequestMapping(value="/getBomMaterialContactInPage")
	public DataGrid getBomMaterialContactInPage(
			BomMaterialContactDTO bomMaterialContact, PageCommand pageCommand) {
		return	bomMaterialContactFacade.getBomMaterialContactInPage(bomMaterialContact, pageCommand);
	}
	
	@ResponseBody
	@RequestMapping(value="/add")
	public Json addBomMaterialContact(@RequestBody BomMaterialContactDTO bomMaterialContact) {
		System.out.println("Into");
		Json json = new Json();
		try {
			int result = bomMaterialContactFacade.addBomMaterialContact(bomMaterialContact);
			if(result != 0) {
				System.out.println("success");
				json.setMsg("添加联系人成功！！");
				json.setSuccess(true);
				json.setObj(result);
			} else {
				System.out.println("fail");
				json.setMsg("添加联系人失败！！");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("添加Bom联系人出错",e);
		}
		
		return json;
	}

	@ResponseBody
	@RequestMapping(value="/update")
	public Json updateBomMaterialContact(@RequestBody BomMaterialContactDTO bomMaterialContact) {
		System.out.println("edit");
		bomMaterialContactFacade.updateBomMaterialContact(bomMaterialContact);
		int id = bomMaterialContact.getId();
		Json json = new Json();
		if(id != 0) {
			json.setMsg("修改联系人成功");
			json.setObj(id);
			json.setSuccess(true);
		} else {
			json.setMsg("修改联系人失败");
			json.setSuccess(false);
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value="/del")
	public Json delBomMaterialContact(int id,int mtcId) {
		boolean result = bomMaterialContactFacade.delBomMaterialContact(mtcId,id);
		Json json = new Json();
		if(result) {
			json.setMsg("删除成功");
			json.setSuccess(true);
		} else {
			json.setMsg("删除失败");
			json.setSuccess(false);
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value="/get")
	public BomMaterialContactDTO getBomMaterialContactById(int id) {
		return bomMaterialContactFacade.getBomMaterialContactById(id);
	}
	
	@ResponseBody
	@RequestMapping(value="/getBomMaterialContacts")
	public List<BomMaterialContactDTO> getBomMaterialContacts() {
		return bomMaterialContactFacade.getBomMaterialContacts();
	}
	
	
}
