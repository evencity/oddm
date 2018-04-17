package com.apical.oddm.web.controller.manufacture;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.facade.manufacture.ManufacturePackageFacade;
import com.apical.oddm.facade.manufacture.command.ManufacturePackageCommand;
import com.apical.oddm.facade.manufacture.dto.ManufacturePackageDTO;
import com.apical.oddm.web.framework.constant.GlobalConstant;
import com.apical.oddm.web.pageModel.base.Json;
import com.apical.oddm.web.pageModel.base.SessionInfo;


@Controller
@RequestMapping("/manufacturePackage")
public class ManufacturePackageController {
	private static final Logger log = LoggerFactory.getLogger(ManufacturePackageController.class);
	@Autowired
	private ManufacturePackageFacade manufacturePackageFacade;
	
	@RequestMapping("/add")
	@ResponseBody
	public Json add(@RequestBody ManufacturePackageCommand manufacturePackageCommand, HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		try {
			ManufacturePackageDTO add = manufacturePackageFacade.add(manufacturePackageCommand,sessionInfo.getId(),sessionInfo.getName());
			if (add != null) {
				j.setSuccess(true);
				j.setMsg("添加成功！");
				j.setObj(add);
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！");
			}
		}  catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("系统出错：" + e.getMessage());
			log.error("任务书包材添加失败", e);
		}
		return j;
	}
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(@RequestBody ManufacturePackageCommand manufacturePackageCommand, HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		try {
			ManufacturePackageDTO add = manufacturePackageFacade.edit(manufacturePackageCommand,sessionInfo.getId(),sessionInfo.getName());
			if (add != null) {
				j.setSuccess(true);
				j.setMsg("编辑成功！");
				j.setObj(add);
			} else {
				j.setSuccess(false);
				j.setMsg("添加失败！");
			}
		}  catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("系统出错：" + e.getMessage());
			log.error("任务书包材添加失败", e);
		}
		return j;
	}
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id, Integer state,HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		try {
			manufacturePackageFacade.delete(id,state,sessionInfo.getId(),sessionInfo.getName());
			j.setSuccess(true);
			j.setMsg("删除成功！");
		}  catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("系统出错：" + e.getMessage());
			log.error("任务书包材删除失败", e);
		}
		return j;
	}
}
