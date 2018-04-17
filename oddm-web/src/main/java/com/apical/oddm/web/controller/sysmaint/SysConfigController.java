package com.apical.oddm.web.controller.sysmaint;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.application.sys.SysConfigServiceI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.core.model.sys.SysConfig;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.pageModel.base.Json;

@Controller
@RequestMapping("/sysConfig")
public class SysConfigController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(SysConfigController.class);

	@Autowired
	private SysConfigServiceI sysConfigService;

	@RequestMapping("/manager")
	public String manager() {
		return "/sysmaint/sysConfig";
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody  //jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public Pager<SysConfig> dataGrid(PageCommand pageCommand) {  //此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		try {
			SystemContext.setPageOffset(pageCommand.getPage());
			SystemContext.setPageSize(pageCommand.getRows());
			SystemContext.setOrder(pageCommand.getOrder());
			SystemContext.setSort("t."+pageCommand.getSort());
			return sysConfigService.dataGrid();
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}
	@RequestMapping("/addPage")
	public String addPage() {
		return "/sysmaint/sysConfigAdd";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Json add(SysConfig sysConfig) {
		Json j = new Json();
		try {
			//System.out.println(sysConfig);
			sysConfigService.add(sysConfig);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		}catch (DataIntegrityViolationException e) {
			log.error(e.getMessage());
			j.setMsg("名称已存在！");
		} catch (Exception e) {
			log.error(e.getMessage());
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		try {
			sysConfigService.delete(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		SysConfig r = sysConfigService.get(id);
		request.setAttribute("sysconfig", r);
		return "/sysmaint/sysConfigEdit";
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(SysConfig sysConfig) throws InterruptedException {
		Json j = new Json();
		try {
			sysConfigService.edit(sysConfig);//
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		/*} catch (DataIntegrityViolationException e) {//如果重复，则会编辑重复的那个，所以禁止编辑最好
			System.out.println("..........................................................................");
			log.error("系统报错",e);
			j.setMsg("名称已存在！");
		*/}catch (Exception e) {
			j.setMsg(e.getMessage());
			log.error("系统报错",e);
		}
		return j;
	}

}
