package com.apical.oddm.facade.document.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.document.DocumentServiceI;
import com.apical.oddm.core.constant.DocumentConst;
import com.apical.oddm.core.model.document.Document;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.document.DocumentFacade;
import com.apical.oddm.facade.document.DocumentUnreadFacade;
import com.apical.oddm.facade.document.command.DocumentCommand;
import com.apical.oddm.facade.document.dto.DocumentDTO;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;
import com.apical.oddm.facade.util.TimeUtil;

/**
 * @description 类的描述：
 * @author 作者 : zhuzh
 * @date 创建时间：2016年11月6日 下午4:14:02
 * @version 1.0
 */
@Component("documentFacade")
public class DocumentFacadeImpl implements DocumentFacade {

	@Autowired
	private DocumentServiceI documentService;
	@Autowired
	private DocumentUnreadFacade documentUnreadFacade;

	@Override
	public BasePage<DocumentDTO> dataAuditByBizName(Integer state, Integer userId, PageCommand pageCommand) {
		// TODO Auto-generated method stub
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		Pager<Document> dataGrid = documentService.dataAuditByBizName(state, userId);
		BasePage<DocumentDTO> basePage = new BasePage<DocumentDTO>();
		List<DocumentDTO> list = new ArrayList<DocumentDTO>();
		if (dataGrid != null) {
			if (dataGrid.getTotal() > 0) {
				for (Document document : dataGrid.getRows()) {
					DocumentDTO documentDTO = new DocumentDTO();
					BeanUtils.copyProperties(document, documentDTO);
					documentDTO.setUploadtime(TimeUtil.dateToStringDetaile(document.getUploadtime()));
					if (document.getOrderInfo() != null) {
						OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
						BeanUtils.copyProperties(document.getOrderInfo(), orderInfoDTO);

						/*
						 * if(document.getOrderInfo().getUser() != null){ UserDTO userDTO = new UserDTO(); BeanUtils.copyProperties(document.getOrderInfo().getUser(), userDTO);
						 * orderInfoDTO.setUserDTO(userDTO); }
						 */
						documentDTO.setOrderInfoDTO(orderInfoDTO);
					}
					list.add(documentDTO);
				}
				basePage.setRows(list);
				basePage.setTotal(dataGrid.getTotal());
			}
		}
		return basePage;
	}

	@Override
	public BasePage<DocumentDTO> dataGrid(Set<Integer> states, PageCommand pageCommand) {
		// TODO Auto-generated method stub
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		Pager<Document> dataGrid = documentService.dataGrid(states);
		BasePage<DocumentDTO> basePage = new BasePage<DocumentDTO>();
		List<DocumentDTO> list = new ArrayList<DocumentDTO>();
		if (dataGrid != null) {
			if (dataGrid.getTotal() > 0) {
				for (Document document : dataGrid.getRows()) {
					DocumentDTO documentDTO = new DocumentDTO();
					BeanUtils.copyProperties(document, documentDTO);
					documentDTO.setUploadtime(TimeUtil.dateToStringDetaile(document.getUploadtime()));
					if (document.getOrderInfo() != null) {
						OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
						BeanUtils.copyProperties(document.getOrderInfo(), orderInfoDTO);
						/*
						 * if(document.getOrderInfo().getUser() != null){ UserDTO userDTO = new UserDTO(); BeanUtils.copyProperties(document.getOrderInfo().getUser(), userDTO);
						 * orderInfoDTO.setUserDTO(userDTO); }
						 */
						documentDTO.setOrderInfoDTO(orderInfoDTO);
					}
					list.add(documentDTO);
				}
				basePage.setRows(list);
				basePage.setTotal(dataGrid.getTotal());
			}
		}
		return basePage;
	}

	@Override
	public BasePage<DocumentDTO> dataGrid(OrderInfoCommand orderInfoCommand, PageCommand pageCommand) {
		// TODO Auto-generated method stub
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		OrderInfo orderInfoquery = new OrderInfo();
		BeanUtils.copyProperties(orderInfoCommand, orderInfoquery);
		Pager<Document> dataGrid = documentService.dataGrid(orderInfoquery);
		BasePage<DocumentDTO> basePage = new BasePage<DocumentDTO>();
		List<DocumentDTO> list = new ArrayList<DocumentDTO>();
		if (dataGrid != null) {
			if (dataGrid.getTotal() > 0) {
				for (Document document : dataGrid.getRows()) {
					DocumentDTO documentDTO = new DocumentDTO();
					BeanUtils.copyProperties(document, documentDTO);
					documentDTO.setUploadtime(TimeUtil.dateToStringDetaile(document.getUploadtime()));
					if (document.getOrderInfo() != null) {
						OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
						BeanUtils.copyProperties(document.getOrderInfo(), orderInfoDTO);
						/*
						 * if(document.getOrderInfo().getUser() != null){ UserDTO userDTO = new UserDTO(); BeanUtils.copyProperties(document.getOrderInfo().getUser(), userDTO);
						 * orderInfoDTO.setUserDTO(userDTO); }
						 */
						documentDTO.setOrderInfoDTO(orderInfoDTO);
					}
					list.add(documentDTO);
				}
				basePage.setRows(list);
				basePage.setTotal(dataGrid.getTotal());
			}
		}
		return basePage;
	}

	@Override
	public BasePage<DocumentDTO> dataGridByUploadTime(Date startDate, Date endDate, PageCommand pageCommand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer documentId) {
		// TODO Auto-generated method stub
		documentService.delete(documentId);
	}

	@Override
	public Boolean add(DocumentCommand documentCommand) {
		// TODO Auto-generated method stub
		Document document = new Document();
		if (documentCommand != null) {
			BeanUtils.copyProperties(documentCommand, document);

			if (documentCommand.getOrderId() != null) {
				OrderInfo orderInfo = new OrderInfo(documentCommand.getOrderId());
				document.setOrderInfo(orderInfo);
				Serializable add = documentService.add(document);
				if (add != null) {
					return true;
				}
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public DocumentDTO get(Integer documentId) {
		// TODO Auto-generated method stub
		if (documentId != null) {
			Document document = documentService.get(documentId);
			if (document != null) {
				DocumentDTO documentDTO = new DocumentDTO();
				BeanUtils.copyProperties(document, documentDTO);
				return documentDTO;
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	public void reviewed(Integer id, Integer pass, Integer orderId, Integer currUserId) {
		// TODO Auto-generated method stub
		if (id != null && pass != null && orderId != null && currUserId != null) {
			Document document = documentService.get(id);
			if (pass == 3) {
				//document.setState(3);
				document.setState(DocumentConst.approved);
			} else if (pass == 4) {
				//document.setState(4);
				document.setState(DocumentConst.rejected);
			}
			/*
			 * OrderInfo orderInfo = new OrderInfo(orderId); document.setOrderInfo(orderInfo);
			 */
			documentService.edit(document);
			if (pass == 3) {// 文档审核通过则生成未读信息
				documentUnreadFacade.addDocUnreadBatch(currUserId, id);
			}
		} else {
			throw new RuntimeException("审核异常！");
		}

	}

	@Override
	public void editDocCount(DocumentCommand documentCommand) {
		// TODO Auto-generated method stub
		if (documentCommand != null) {
			if (documentCommand.getId() != null) {
				Document document = documentService.get(documentCommand.getId());
				if (document != null) {
					Integer count = documentCommand.getDownloadCount();
					document.setDownloadCount(count++);
					if (documentCommand.getOrderId() != null) {
						OrderInfo orderInfo = new OrderInfo(documentCommand.getOrderId());
						document.setOrderInfo(orderInfo);
						documentService.edit(document);
					}
				}
			}
		}
	}

	@Override
	public void editDocPath(DocumentCommand documentCommand) {
		// TODO Auto-generated method stub
		if (documentCommand != null) {
			if (documentCommand.getId() != null) {
				Document document = documentService.get(documentCommand.getId());
				//document.setState(2);
				document.setState(DocumentConst.uploaded);
				document.setVersion(documentCommand.getVersion());
				//document.setUserId(documentCommand.getUserId());
				//document.setUploadtime(new Date());
				document.setPath(documentCommand.getPath());
				if (documentCommand.getOrderId() != null) {
					OrderInfo orderInfo = new OrderInfo(documentCommand.getOrderId());
					document.setOrderInfo(orderInfo);
				}
				documentService.edit(document);
			}
		}

	}

	@Override
	public BasePage<DocumentDTO> dataGridOrderInfo(DocumentCommand documentCommand, Set<Integer> states, PageCommand pageCommand) {
		// TODO Auto-generated method stub
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		Document documentQuery = new Document();
		BasePage<DocumentDTO> basePage = new BasePage<DocumentDTO>();
		List<DocumentDTO> list = new ArrayList<DocumentDTO>();
		BeanUtils.copyProperties(documentCommand, documentQuery);
		if(documentCommand.getOrderNo() != null){
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrderNo(documentCommand.getOrderNo());
			documentQuery.setOrderInfo(orderInfo);
		}
		Pager<Document> dataGrid = documentService.dataGridOrderInfo(documentQuery, states);

		if (dataGrid != null) {
			if (dataGrid.getTotal() > 0) {
				for (Document document : dataGrid.getRows()) {
					DocumentDTO documentDTO = new DocumentDTO();
					BeanUtils.copyProperties(document, documentDTO);
					documentDTO.setUploadtime(TimeUtil.dateToStringDetaile(document.getUploadtime()));
					if (document.getOrderInfo() != null) {
						OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
						BeanUtils.copyProperties(document.getOrderInfo(), orderInfoDTO);
						orderInfoDTO.setTimestamp(TimeUtil.timestampToString(document.getOrderInfo().getTimestamp()));
						
						 
						documentDTO.setOrderInfoDTO(orderInfoDTO);
					}
					list.add(documentDTO);
				}
				basePage.setRows(list);
				basePage.setTotal(dataGrid.getTotal());
			}
		}
		return basePage;
	}

	@Override
	public void uploadFile(DocumentCommand documentCommand) {
		// TODO Auto-generated method stub
		if (documentCommand != null) {
			if (documentCommand.getId() != null) {
				Document document = documentService.get(documentCommand.getId());
				//document.setState(2);
				document.setState(DocumentConst.uploaded);
				document.setVersion(documentCommand.getVersion());
				document.setUploadtime(new Date());
				document.setPath(documentCommand.getPath());
				if (documentCommand.getOrderId() != null) {
					OrderInfo orderInfo = new OrderInfo(documentCommand.getOrderId());
					document.setOrderInfo(orderInfo);
					documentService.edit(document);
				}

			}
		}

	}

	@Override
	public BasePage<OrderInfoDTO> dataOrderInfoGrid(OrderInfoCommand orderInfoCommand, Set<Integer> set, PageCommand pageCommand, Boolean hasRoles) {
		// TODO Auto-generated method stub
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		SystemContext.setSort("t." + pageCommand.getSort());
		Pager<OrderInfo> dataGrid = null;
		OrderInfo orderInfoquery = new OrderInfo();
		BeanUtils.copyProperties(orderInfoCommand, orderInfoquery);
		dataGrid = documentService.dataOrderInfoGrid(orderInfoquery, set);
		BasePage<OrderInfoDTO> basePage = new BasePage<OrderInfoDTO>();
		if (dataGrid != null && dataGrid.getTotal() != 0) {
			if (dataGrid.getRows().size() > 0) {
				List<OrderInfoDTO> list = new ArrayList<OrderInfoDTO>();
				if (hasRoles != null && hasRoles) {
					for (OrderInfo orderInfo : dataGrid.getRows()) {
						OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
						BeanUtils.copyProperties(orderInfo, orderInfoDTO);
						orderInfoDTO.setClientNameCode(orderInfo.getClientName());
						orderInfoDTO.setClientName("");
						if (orderInfo.getDateDelivery() != null) {
							orderInfoDTO.setDateDelivery(TimeUtil.dateToString(orderInfo.getDateDelivery()));
						}
						if (orderInfo.getDateExamine() != null) {
							orderInfoDTO.setDateExamine(TimeUtil.dateToString(orderInfo.getDateExamine()));
						}
						if (orderInfo.getDateOrder() != null) {
							orderInfoDTO.setDateOrder(TimeUtil.dateToString(orderInfo.getDateOrder()));
						}
						if (orderInfo.getTimestamp() != null) {
							orderInfoDTO.setTimestamp(TimeUtil.timestampToStringDetaile(orderInfo.getTimestamp()));
						}
						if (orderInfo.getUpdatetime() != null) {
							orderInfoDTO.setUpdatetime(TimeUtil.timestampToStringDetaile(orderInfo.getUpdatetime()));
						}
						list.add(orderInfoDTO);
					}
				} else {
					for (OrderInfo orderInfo : dataGrid.getRows()) {
						OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
						BeanUtils.copyProperties(orderInfo, orderInfoDTO);
						orderInfoDTO.setClientName("");
						if (orderInfo.getDateDelivery() != null) {
							orderInfoDTO.setDateDelivery(TimeUtil.dateToString(orderInfo.getDateDelivery()));
						}
						if (orderInfo.getDateExamine() != null) {
							orderInfoDTO.setDateExamine(TimeUtil.dateToString(orderInfo.getDateExamine()));
						}
						if (orderInfo.getDateOrder() != null) {
							orderInfoDTO.setDateOrder(TimeUtil.dateToString(orderInfo.getDateOrder()));
						}
						if (orderInfo.getTimestamp() != null) {
							orderInfoDTO.setTimestamp(TimeUtil.timestampToStringDetaile(orderInfo.getTimestamp()));
						}
						if (orderInfo.getUpdatetime() != null) {
							orderInfoDTO.setUpdatetime(TimeUtil.timestampToStringDetaile(orderInfo.getUpdatetime()));
						}
						list.add(orderInfoDTO);
					}
				}

				basePage.setTotal(dataGrid.getTotal());
				basePage.setRows(list);
			}
		}
		return basePage;
	}

	@Override
	public List<DocumentDTO> isExistPath(String path) {
		// TODO Auto-generated method stub
		List<DocumentDTO> list = new ArrayList<DocumentDTO>();
		if (path != null && !"".equals(path)) {
			SystemContext.setPageOffset(1);
			SystemContext.setPageSize(10);
			List<Document> existPath = documentService.isExistPath(path);
			if (existPath != null && existPath.size() > 0) {
				for (Document document : existPath) {
					DocumentDTO documentDTO = new DocumentDTO();
					BeanUtils.copyProperties(document, documentDTO);
					if (document.getOrderInfo() != null) {
						OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
						orderInfoDTO.setOrderNo(document.getOrderInfo().getOrderNo());
						orderInfoDTO.setId(document.getOrderInfo().getId());
						documentDTO.setOrderInfoDTO(orderInfoDTO);
					}
					list.add(documentDTO);
				}
				return list;
			}
		}
		return list;
	}

	@Override
	public Boolean isExistDocument(Integer orderId, String mtName) {
		// TODO Auto-generated method stub
		if (orderId != null && mtName != null && !"".equals(mtName)) {
			Document existDocument = documentService.isExistDocument(orderId, mtName);
			if (existDocument != null) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public BasePage<DocumentDTO> dataGrid(DocumentCommand documentCommand, Set<Integer> states, PageCommand pageCommand) {
		// TODO Auto-generated method stub
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		BasePage<DocumentDTO> basePage = new BasePage<DocumentDTO>();
		if (documentCommand != null) {
			Document documentQuery = new Document();
			if (documentCommand.getOrderNo() != null) {
				documentQuery.setOrderNo(documentCommand.getOrderNo());
			}
			/*if (documentCommand.getOrderNo() != null) {
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderNo(documentCommand.getOrderNo());
				documentQuery.setOrderInfo(orderInfo);
			}*/
			if (documentCommand.getUnread() != null) {
				documentQuery.setUnread(documentCommand.getUnread());
			}
			if(documentCommand.getUserId() != null){
				documentQuery.setUserId(documentCommand.getUserId());
			}
			if(documentCommand.getType() != null){
				documentQuery.setType(documentCommand.getType());
			}
			Pager<Document> dataGrid = documentService.dataGrid(documentQuery, states);
			List<DocumentDTO> list = new ArrayList<DocumentDTO>();
			if (dataGrid != null) {
				if (dataGrid.getTotal() > 0) {
					for (Document document : dataGrid.getRows()) {
						DocumentDTO documentDTO = new DocumentDTO();
						BeanUtils.copyProperties(document, documentDTO);

						if (document.getUnread() != null) {
							documentDTO.setUnread(document.getUnread());
						}
						if (document.getOrderNo() != null) {
							documentDTO.setOrderNo(document.getOrderNo());
						}
						if (document.getProductClient() != null) {
							documentDTO.setProductClient(document.getProductClient());
						}
						if (document.getProductFactory() != null) {
							documentDTO.setProductFactory(document.getProductFactory());
						}
						if (document.getUploadtime() != null) {
							documentDTO.setUploadtime(TimeUtil.dateToStringDetaile(document.getUploadtime()));
						}
						list.add(documentDTO);
					}
					basePage.setRows(list);
					basePage.setTotal(dataGrid.getTotal());
				}
			}
		}

		return basePage;
	}

	

}
