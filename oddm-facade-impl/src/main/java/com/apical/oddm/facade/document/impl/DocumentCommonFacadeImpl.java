package com.apical.oddm.facade.document.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.document.DocumentCommonServiceI;
import com.apical.oddm.core.model.document.DocumentCommon;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.document.DocumentCommonFacade;
import com.apical.oddm.facade.document.command.DocumentCommonCommand;
import com.apical.oddm.facade.document.dto.DocumentCommonDTO;
import com.apical.oddm.facade.util.TimeUtil;

/**
 * @description 类的描述：
 * @author 作者 : zhuzh
 * @date 创建时间：2016年11月6日 下午4:14:02
 * @version 1.0
 */
@Component("documentCommonFacade")
public class DocumentCommonFacadeImpl implements DocumentCommonFacade {

	@Autowired
	private DocumentCommonServiceI documentCommonService;

	@Override
	public BasePage<DocumentCommonDTO> dataGrid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasePage<DocumentCommonDTO> dataGridByMtName(String nameMaterial, PageCommand pageCommand) {
		// TODO Auto-generated method stub
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		BasePage<DocumentCommonDTO> basePage = new BasePage<DocumentCommonDTO>();
		if (nameMaterial == null) {
			nameMaterial = "";
		}
		Pager<DocumentCommon> dataGridByMtName = documentCommonService.dataGridByMtName(nameMaterial);
		if (dataGridByMtName != null && dataGridByMtName.getTotal() > 0) {
			List<DocumentCommonDTO> list = new ArrayList<DocumentCommonDTO>();
			for (DocumentCommon documentCommon : dataGridByMtName.getRows()) {
				DocumentCommonDTO documentCommonDTO = new DocumentCommonDTO();
				BeanUtils.copyProperties(documentCommon, documentCommonDTO);
				documentCommonDTO.setUploadtime(TimeUtil.dateToStringDetaile(documentCommon.getUploadtime()));
				list.add(documentCommonDTO);
			}
			basePage.setRows(list);
			basePage.setTotal(dataGridByMtName.getTotal());
		}
		return basePage;
	}

	@Override
	public void delete(Integer documentId) {
		// TODO Auto-generated method stub
		documentCommonService.delete(documentId);
	}

	@Override
	public Boolean add(DocumentCommonCommand documentCommonCommand) {
		// TODO Auto-generated method stub
		if (documentCommonCommand != null) {
			DocumentCommon documentCommon = new DocumentCommon();
			if (documentCommonCommand != null) {
				BeanUtils.copyProperties(documentCommonCommand, documentCommon);
				Serializable add = documentCommonService.add(documentCommon);
				if (add != null) {
					return true;
				} else {
					return false;
				}

			}
		}

		return false;
	}

	@Override
	public DocumentCommonDTO get(Integer documentId) {
		// TODO Auto-generated method stub
		if (documentId != null) {
			DocumentCommon documentCommon = documentCommonService.get(documentId);
			if (documentCommon != null) {
				DocumentCommonDTO documentCommonDTO = new DocumentCommonDTO();
				BeanUtils.copyProperties(documentCommon, documentCommonDTO);
				return documentCommonDTO;
			}
			return null;
		}
		return null;
	}

	@Override
	public void updatePath(DocumentCommonCommand documentCommonCommand) {
		// TODO Auto-generated method stub
		DocumentCommon documentCommon = new DocumentCommon();
		if (documentCommonCommand != null) {
			documentCommonCommand.setState(2);
			documentCommonCommand.setUploadtime(new Date());
			BeanUtils.copyProperties(documentCommonCommand, documentCommon);
		}
		documentCommonService.edit(documentCommon);
	}

	@Override
	public List<DocumentCommonDTO> getListByMtName(String nameMaterial) {
		// TODO Auto-generated method stub
		if (nameMaterial != null && !"".equals(nameMaterial)) {
			List<DocumentCommon> listByMtName = documentCommonService.getListByMtName(nameMaterial);
			if (listByMtName != null && listByMtName.size() > 0) {
				List<DocumentCommonDTO> list = new ArrayList<DocumentCommonDTO>();
				for (DocumentCommon documentCommon : listByMtName) {
					DocumentCommonDTO documentCommonDTO = new DocumentCommonDTO();
					BeanUtils.copyProperties(documentCommon, documentCommonDTO);
					list.add(documentCommonDTO);
				}
				return list;
			} else {
				return null;
			}
		}
		return null;
	}
}
