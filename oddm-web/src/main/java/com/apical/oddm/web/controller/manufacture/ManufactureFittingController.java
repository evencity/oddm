package com.apical.oddm.web.controller.manufacture;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.facade.manufacture.ManufactureFittingFacade;
import com.apical.oddm.facade.manufacture.command.ManufactureFittingCommand;
import com.apical.oddm.facade.manufacture.dto.ManufactureFittingDTO;
import com.apical.oddm.web.framework.constant.GlobalConstant;
import com.apical.oddm.web.pageModel.base.Json;
import com.apical.oddm.web.pageModel.base.SessionInfo;


@Controller
@RequestMapping("/manufactureFitting")
public class ManufactureFittingController {
	private static final Logger log = LoggerFactory.getLogger(ManufactureFittingController.class);
	@Autowired
	private ManufactureFittingFacade manufactureFittingFacade;
	
	@RequestMapping("/add")
	@ResponseBody
	public Json add(@RequestBody ManufactureFittingCommand manufactureFittingCommand, HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		try {
			 ManufactureFittingDTO add = manufactureFittingFacade.add(manufactureFittingCommand,sessionInfo.getId(),sessionInfo.getName());
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
			log.error("任务书配件添加失败", e);
		}
		return j;
	}
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(@RequestBody ManufactureFittingCommand manufactureFittingCommand, HttpServletRequest request) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalConstant.SESSION_INFO);
		Json j = new Json();
		try {
			ManufactureFittingDTO add = manufactureFittingFacade.edit(manufactureFittingCommand,sessionInfo.getId(),sessionInfo.getName());
			if (add != null) {
				j.setSuccess(true);
				j.setMsg("编辑成功！");
				j.setObj(add);
			} else {
				j.setSuccess(false);
				j.setMsg("编辑失败！");
			}
		} catch (Exception e) {
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
			manufactureFittingFacade.delete(id,state,sessionInfo.getId(),sessionInfo.getName());
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
