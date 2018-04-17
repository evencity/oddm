package com.apical.oddm.facade.material.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.material.MaterialBareServiceI;
import com.apical.oddm.application.material.MaterialTypeServiceI;
import com.apical.oddm.application.material.ProductServiceI;
import com.apical.oddm.application.material.ProductTypeServiceI;
import com.apical.oddm.core.model.material.MaterialBare;
import com.apical.oddm.core.model.material.MaterialType;
import com.apical.oddm.core.model.material.Product;
import com.apical.oddm.core.model.material.ProductType;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.material.ProductFacade;
import com.apical.oddm.facade.material.command.ProductCommand;
import com.apical.oddm.facade.material.dto.MaterialBareDTO;
import com.apical.oddm.facade.material.dto.ProductDTO;

/**
 * @description 类的描述：
 * @author 作者 : zhuzh
 * @date 创建时间：2016年10月28日 上午9:52:24
 * @version 1.0
 */
@Component("productFacade")
public class ProductFacadeImpl implements ProductFacade {

	@Autowired
	private ProductServiceI productService;

	@Autowired
	private ProductTypeServiceI productTypeService;

	@Autowired
	private MaterialBareServiceI materialBareService;

	@Autowired
	private MaterialTypeServiceI materialTypeService;

	@Override
	public BasePage<ProductDTO> pageList(ProductCommand productCommand, PageCommand pageCommand) {
		// TODO Auto-generated method stub
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		/*
		 * if(pageCommand.getSort().equals("typeName")){ SystemContext.setSort("t.type"); }else { SystemContext.setSort("t."+pageCommand.getSort()); }
		 */
		Pager<Product> dataGrid = null;
		if (productCommand.getName() == null || "".equals(productCommand.getName())) {
			dataGrid = productService.dataGrid();
		} else {
			dataGrid = productService.dataGrid(productCommand.getType());
		}
		BasePage<ProductDTO> basePage = new BasePage<ProductDTO>();
		if (dataGrid != null && dataGrid.getRows().size() > 0) {
			List<ProductDTO> list = new ArrayList<ProductDTO>();
			for (Product product : dataGrid.getRows()) {
				ProductDTO productDTO = new ProductDTO();
				BeanUtils.copyProperties(product, productDTO);
				if (product.getProductType() != null) {
					ProductType productType = productTypeService.get(product.getProductType().getId());
					productDTO.setTypeId(productType.getId());
					productDTO.setTypeName(productType.getName());
				}
				list.add(productDTO);
			}
			basePage.setTotal(dataGrid.getTotal());
			basePage.setRows(list);
		}
		return basePage;
	}

	@Override
	public ProductDTO getProductByName(String productName) {
		// TODO Auto-generated method stub
		Product product = productService.getProductByName(productName);
		ProductDTO productDTO = new ProductDTO();
		if (product != null) {
			BeanUtils.copyProperties(product, productDTO);
			if (product.getMaterialBares() != null && product.getMaterialBares().size() > 0) {
				Set<MaterialBareDTO> set = new TreeSet<MaterialBareDTO>((Comparator<? super MaterialBareDTO>) new Comparator<MaterialBareDTO>() {
					@Override
					public int compare(MaterialBareDTO o1, MaterialBareDTO o2) {
						return o1.getId().compareTo(o2.getId());
					}
				});
				for (MaterialBare materialBare : product.getMaterialBares()) {
					MaterialBareDTO materialBareDTO = new MaterialBareDTO();
					BeanUtils.copyProperties(materialBare, materialBareDTO);
					set.add(materialBareDTO);
				}
				productDTO.setMaterialBareDTOs(set);
			}
		}
		return productDTO;
	}

	@Override
	public Boolean add(ProductCommand productCommand) {
		// TODO Auto-generated method stub
		if (productCommand != null) {
			Product product = new Product();
			BeanUtils.copyProperties(productCommand, product);
			if (productCommand.getType() != null) {
				ProductType productType = productTypeService.get(Integer.parseInt(productCommand.getType()));
				product.setProductType(productType);
			}
			if (productCommand.getMaterialBareIds() != null) {

				String[] materialBareIds = productCommand.getMaterialBareIds().split(",");
				// String[] materialpackingIds = productCommand.getMaterialPackingIds().split(",");
				// String[] materialFittingIds = productCommand.getMaterialFittingIds().split(",");
				Set<MaterialBare> materialBareSet = new HashSet<MaterialBare>();
				// Set<MaterialPacking> materialPackingSet = new TreeSet<MaterialPacking>();
				for (String materialBareId : materialBareIds) {
					if (!"".equals(materialBareId)) {
						MaterialBare materialBare = new MaterialBare();
						materialBare.setId(Integer.parseInt(materialBareId));
						materialBareSet.add(materialBare);
					}

				}
				/*
				 * for(String materialFittingId :materialFittingIds){ if(!"".equals(materialFittingId) ){ MaterialBare materialBare = new MaterialBare();
				 * materialBare.setId(Integer.parseInt(materialFittingId)); materialBareSet.add(materialBare); } } for(String materialpackingId :materialpackingIds){
				 * if(!"".equals(materialpackingId) ){ MaterialPacking materialPacking = new MaterialPacking(); materialPacking.setId(Integer.parseInt(materialpackingId));
				 * materialPackingSet.add(materialPacking); } }
				 */

				product.setMaterialBares(materialBareSet);
				// product.setMaterialPackings(materialPackingSet);

				Serializable id = productService.add(product);
				if (id != null) {
					return true;
				}
				return false;
			}
		}

		return false;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		if (id != null) {
			productService.delete(id);
		}
	}

	@Override
	public void edit(ProductCommand productCommand) {
		// TODO Auto-generated method stub
		/*
		 * Product product = new Product(); BeanUtils.copyProperties(productCommand, product); if(productCommand.getType() != null){ ProductType productType =
		 * productTypeService.get(Integer.parseInt(productCommand.getType())); product.setProductType(productType); }
		 */
		if (productCommand != null) {
			Product product = productService.get(productCommand.getId());
			product.setDescription(productCommand.getDescription());
			product.setName(productCommand.getName());
			if (productCommand.getType() != null) {
				ProductType productType = productTypeService.get(Integer.parseInt(productCommand.getType()));
				product.setProductType(productType);
			}
			if (productCommand.getMaterialBareIds() != null) {

				String[] materialBareIds = productCommand.getMaterialBareIds().split(",");
				/*
				 * String[] materialpackingIds = productCommand.getMaterialPackingIds().split(","); String[] materialFittingIds = productCommand.getMaterialFittingIds().split(",");
				 */
				Set<MaterialBare> materialBareSet = new HashSet<MaterialBare>();
				// Set<MaterialPacking> materialPackingSet = new TreeSet<MaterialPacking>();

				if (materialBareIds != null && materialBareIds.length > 0) {
					for (String materialBareId : materialBareIds) {
						MaterialBare materialBare = new MaterialBare();
						materialBare.setId(Integer.parseInt(materialBareId));
						materialBareSet.add(materialBare);
					}
				}
				/*
				 * if(materialFittingIds != null && materialFittingIds.length > 0){ for(String materialFittingId :materialFittingIds){ MaterialBare materialBare = new
				 * MaterialBare(); materialBare.setId(Integer.parseInt(materialFittingId)); materialBareSet.add(materialBare); } } if(materialpackingIds != null &&
				 * materialpackingIds.length > 0){ for(String materialpackingId :materialpackingIds){ MaterialPacking materialPacking = new MaterialPacking();
				 * materialPacking.setId(Integer.parseInt(materialpackingId)); materialPackingSet.add(materialPacking); } }
				 */

				product.setMaterialBares(materialBareSet);
				// product.setMaterialPackings(materialPackingSet);
			}
			productService.edit(product);
		}

	}

	@Override
	public ProductDTO get(Integer id) {
		// TODO Auto-generated method stub
		if (id != null) {
			ProductDTO productDTO = new ProductDTO();
			Product product = productService.get(id);
			/* BeanUtils.copyProperties(product, productDTO); */
			if (product != null) {
				productDTO.setId(product.getId());
				productDTO.setDescription(product.getDescription());
				productDTO.setName(product.getName());
				if (product.getProductType() != null) {
					ProductType productType = productTypeService.get(product.getProductType().getId());
					productDTO.setTypeId(productType.getId());
					productDTO.setTypeName(productType.getName());
				}
				Set<MaterialBare> materialBares = product.getMaterialBares();
				/* Set<MaterialPacking> materialPackings = product.getMaterialPackings(); */

				Set<MaterialBareDTO> materialBareDTOs = productDTO.getMaterialBareDTOs();
				/*
				 * Set<MaterialFittingDTO> materialFittingDTOs = productDTO.getMaterialFittingDTOs(); Set<MaterialPackingDTO> materialPackingsDTOs =
				 * productDTO.getMaterialPackingDTOs();
				 */

				if (materialBares != null && materialBares.size() > 0) {
					for (MaterialBare materialBare : materialBares) {
						if (materialBare.getMaterialType().getType() == 1) {
							// 裸机物料
							MaterialBareDTO materialBareDTO = new MaterialBareDTO();
							BeanUtils.copyProperties(materialBare, materialBareDTO);
							if (materialBare.getMaterialType() != null) {
								MaterialType materialType = materialTypeService.get(materialBare.getType());
								materialBareDTO.setTypeId(materialBare.getType());
								materialBareDTO.setTypeName(materialType.getName());
							}
							materialBareDTOs.add(materialBareDTO);
						}
						/*
						 * if(materialBare.getMaterialType().getType() == 3){ //配件物料 MaterialFittingDTO materialFittingDTO = new MaterialFittingDTO();
						 * BeanUtils.copyProperties(materialBare, materialFittingDTO); if(materialBare.getMaterialType() != null){ MaterialType materialType =
						 * materialTypeService.get(materialBare.getType()); materialFittingDTO.setTypeId(materialBare.getType());
						 * materialFittingDTO.setTypeName(materialType.getName()); } materialFittingDTOs.add(materialFittingDTO); }
						 */
					}
				}
				/*
				 * if(materialPackings != null && materialPackings.size() > 0){ for(MaterialPacking materialPacking : materialPackings){ //包材 MaterialPackingDTO materialPackingDTO
				 * = new MaterialPackingDTO(); BeanUtils.copyProperties(materialPacking, materialPackingDTO); if(materialPacking.getMaterialType() != null){ MaterialType
				 * materialType = materialTypeService.get(materialPacking.getType()); materialPackingDTO.setTypeId(materialPacking.getType());
				 * materialPackingDTO.setTypeName(materialType.getName()); } materialPackingsDTOs.add(materialPackingDTO); } }
				 */
			}

			return productDTO;
		}
		return null;
	}

	@Override
	public List<ProductDTO> listProduct(String productTypeName) {
		// TODO Auto-generated method stub
		List<ProductDTO> list = new ArrayList<ProductDTO>();
		if (productTypeName != null) {
			List<Product> listProduct = productService.listProduct(productTypeName);
			if (listProduct != null && listProduct.size() > 0) {
				for (Product product : listProduct) {
					ProductDTO productDTO = new ProductDTO();
					BeanUtils.copyProperties(product, productDTO);
					// Set<MaterialBareDTO> materialBareDTOs = new TreeSet<MaterialBareDTO>();
					/*
					 * if(product.getMaterialBares() != null && product.getMaterialBares().size() > 0){ Set<MaterialBare> materialBares = product.getMaterialBares();
					 * 
					 * for(MaterialBare materialBare : materialBares){ MaterialBareDTO materialBareDTO = new MaterialBareDTO(); BeanUtils.copyProperties(materialBare,
					 * materialBareDTO); materialBareDTOs.add(materialBareDTO); } } if(materialBareDTOs != null && materialBareDTOs.size() > 0){
					 * productDTO.setMaterialBareDTOs(materialBareDTOs); }
					 */
					list.add(productDTO);
				}
			}
		}
		return list;
	}

	@Override
	public BasePage<ProductDTO> dataGrid(ProductCommand productCommand, PageCommand pageCommand) {
		// TODO Auto-generated method stub
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		if (pageCommand.getSort().equals("typeName")) {
			SystemContext.setSort("p.name");
		} else {
			SystemContext.setSort("t." + pageCommand.getSort());
		}
		Pager<Product> dataGrid = null;

		BasePage<ProductDTO> basePage = new BasePage<ProductDTO>();
		if (productCommand != null) {
			Product productQuery = new Product();
			if (productCommand.getName() != null) {
				productQuery.setName(productCommand.getName());
			}
			if (productCommand.getTypeId() != null) {
				productQuery.setProductType(new ProductType(productCommand.getTypeId()));
			}
			dataGrid = productService.dataGrid(productQuery);
			if (dataGrid.getRows().size() > 0) {
				List<ProductDTO> list = new ArrayList<ProductDTO>();
				for (Product product : dataGrid.getRows()) {
					ProductDTO productDTO = new ProductDTO();
					BeanUtils.copyProperties(product, productDTO);
					if (product.getProductType() != null) {
						// ProductType productType = productTypeService.get(product.getProductType().getId());
						productDTO.setTypeId(product.getProductType().getId());
						productDTO.setTypeName(product.getProductType().getName());
					}
					list.add(productDTO);
				}
				basePage.setTotal(dataGrid.getTotal());
				basePage.setRows(list);
			}
		}

		return basePage;
	}

	@Override
	public Boolean isExistProduct(String productName) {
		if (!StringUtils.isEmpty(productName)) {
			Product existProduct = productService.isExistProduct(productName);
			if (existProduct != null) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public Boolean freeze(ProductCommand productCommand) {
		// TODO Auto-generated method stub
		if(productCommand.getId() != null){
			Product product = productService.get(productCommand.getId());
			if(product != null){
				if(product.getState() == 1){
					product.setState(2);
				}else {
					product.setState(1);
				}
				
				productService.edit(product);
				return true;
			}
			return false;
		}
		return false;
	}

}
