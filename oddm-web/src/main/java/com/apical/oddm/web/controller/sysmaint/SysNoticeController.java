package com.apical.oddm.web.controller.sysmaint;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.application.sys.SysNoticeServiceI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.core.model.sys.SysNotice;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.web.controller.base.BaseController;
import com.apical.oddm.web.pageModel.base.Json;

@Controller
@RequestMapping("/sysNotice")
public class SysNoticeController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(SysNoticeController.class);

	@Autowired
	private SysNoticeServiceI sysNoticeService;

	@RequestMapping("/manager")
	public String manager() {
		return "/sysmaint/sysNotice";
	}
	
	@RequestMapping("/managerForAll")
	public String managerForAll() {
		return "/sysmaint/sysNoticeForAll";
	}
	@RequestMapping("/dataGrid")
	@ResponseBody  //jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public Pager<SysNotice> dataGrid(SysNotice sysNotice, Integer state, PageCommand pageCommand) {  //此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		try {
			SystemContext.setPageOffset(pageCommand.getPage());
			SystemContext.setPageSize(pageCommand.getRows());
			SystemContext.setOrder(pageCommand.getOrder());
			SystemContext.setSort("t."+pageCommand.getSort());
			Set<Integer> states = null;
			if (state != null) {
				states = new HashSet<Integer>();
				states.add(state);
			}
			return sysNoticeService.dataGrid(sysNotice, states);
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}
	
	@RequestMapping("/dataGridAll")
	@ResponseBody  //jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public Pager<SysNotice> dataGridAll(SysNotice sysNotice, Integer state, PageCommand pageCommand) {  //此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		try {
			SystemContext.setPageOffset(pageCommand.getPage());
			SystemContext.setPageSize(pageCommand.getRows());
			SystemContext.setOrder(pageCommand.getOrder());
			SystemContext.setSort("t."+pageCommand.getSort());
			Set<Integer> states = null;
			if (state != null) {
				states = new HashSet<Integer>();
				states.add(state);
			}
			return sysNoticeService.dataGrid(sysNotice, states);
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}
	
	@RequestMapping("/dataGrid2")
	@ResponseBody  //jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public Pager<SysNotice> dataGrid2(PageCommand pageCommand) {  //此处controler用了两个查询。孔浩的直接在业务层查完返回Page对象
		try {
			Set<Integer> states = new HashSet<Integer>();
			states.add(2);
			SystemContext.setPageOffset(pageCommand.getPage());
			SystemContext.setPageSize(pageCommand.getRows());
			return sysNoticeService.dataGrid(null, states);
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}
	
	@RequestMapping("/addPage")
	public String addPage() {
		return "/sysmaint/sysNoticeAdd";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Json add(SysNotice sysNotice) {
		Json j = new Json();
		try {
			//System.out.println(sysNotice);
			if (sysNotice.getId() != null) {//如果不为空，则跳到编辑方法
				return edit(sysNotice);
			}
			Serializable id = sysNoticeService.add(sysNotice);
			j.setSuccess(true);
			j.setObj((int)id);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			log.error("",e);
			j.setSuccess(false);
			j.setMsg("添加失败！"+e.getMessage());
		}
		return j;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		try {
			sysNoticeService.delete(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		if (id == null) return null;
		SysNotice r = sysNoticeService.get(id);
		request.setAttribute("sysNotice", r);
		return "/sysmaint/sysNoticeEdit";
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(SysNotice sysNotice) throws InterruptedException {
		Json j = new Json();
		try {
			sysNoticeService.edit(sysNotice);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			log.error("",e);
			j.setSuccess(false);
			j.setMsg("编辑失败！"+e.getMessage());
		}
		return j;
	}
	@RequestMapping("/get")
	@ResponseBody
	public SysNotice get(Integer id) {
		SysNotice notice = sysNoticeService.get(id);
		return notice;
	}
	
	@RequestMapping("/getPage")
	public String getPage(HttpServletRequest request, Integer id) {
		SysNotice r = sysNoticeService.get(id);
		request.setAttribute("sysNotice", r);
		return "/sysmaint/sysNoticeDetail";
	}
}
