package com.apical.oddm.web.controller.manufacture;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apical.oddm.facade.manufacture.ManufacturePackageTitleFacade;
import com.apical.oddm.facade.manufacture.dto.ManufacturePackageTitleDTO;


@Controller
@RequestMapping("/manufacturePackageTitle")
public class ManufacturePackageTitleController {

	@Autowired
	private ManufacturePackageTitleFacade manufacturePackageTitleFacade;
	
	/**
	 * 分页查询定单基础信息
	 */
	@RequestMapping("/listAll")
	@ResponseBody
	// jsp传过来的数据是通过setter方法映射到pojo对象里面的，相反传出去的json数据键必须和jsp的一致
	public List<ManufacturePackageTitleDTO> listAll() { 
		return manufacturePackageTitleFacade.getListAll();
	}
}
