package com.apical.oddm.order;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apical.oddm.application.document.DocumentCommonServiceI;
import com.apical.oddm.application.material.MaterialBareServiceI;
import com.apical.oddm.application.material.MaterialPackingServiceI;
import com.apical.oddm.application.material.MaterialTypeServiceI;
import com.apical.oddm.application.material.ProductServiceI;
import com.apical.oddm.application.material.ProductTypeServiceI;
import com.apical.oddm.core.model.document.DocumentCommon;
import com.apical.oddm.core.model.material.MaterialBare;
import com.apical.oddm.core.model.material.MaterialPacking;
import com.apical.oddm.core.model.material.MaterialType;
import com.apical.oddm.core.model.material.Product;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.document.dto.DocumentCommonDTO;

@RunWith(SpringJUnit4ClassRunner.class)//让junit工作在spring环境中
@ContextConfiguration("/configs/spring.xml")//在classes中spring的配置文件
public class TestMaterial {
	@Autowired
	private MaterialTypeServiceI materialTypeService;
	@Autowired
	private MaterialBareServiceI materialBareService;
	@Autowired
	private MaterialPackingServiceI materialPackingService;
	@Autowired
	private ProductServiceI productService;
	@Autowired
	private ProductTypeServiceI productTypeService;
	@Autowired
	private DocumentCommonServiceI documentCommonService;
	
	@Test
	public void testProductTypeServiceI() {
		Set<Product> deleteAndEdit = productTypeService.deleteAndEdit(5);
		if (deleteAndEdit != null && !deleteAndEdit.isEmpty()) {
			for (Product p : deleteAndEdit) {
				System.out.println(p.getName());
			}
		}
	}
	
	@Test
	public void testdocumentCommonServiceI() {
		Pager<DocumentCommon> dataGridByMtName = documentCommonService.dataGridByMtName("");
		if(dataGridByMtName != null && dataGridByMtName.getSize() > 0){
			System.out.println(".............");
			List<DocumentCommonDTO> list = new ArrayList<DocumentCommonDTO>();
			for(DocumentCommon documentCommon : dataGridByMtName.getRows()){
				System.out.println("........xxxxxx.....");
				DocumentCommonDTO documentCommonDTO = new DocumentCommonDTO();
				BeanUtils.copyProperties(documentCommon, documentCommonDTO);
				list.add(documentCommonDTO);
			}
			for(DocumentCommonDTO documentCommonDTO : list){
				System.out.println(documentCommonDTO.getNameMt());
			}
		}
	}
	@Test
	public void testProducterviceI() {
		//测试命名查询
		/*List<ProductType> dataGrid = productTypeService.dataGrid();
		for (ProductType p : dataGrid) {
			System.out.println(p.getName());
		}*/
		//查
		/*SystemContext.setPageOffset(1);
		SystemContext.setPageSize(4);
		Pager<Product> dataGrid = productService.dataGrid("PND");
		for (Product p : dataGrid.getRows()) {
			System.out.println(p.getName()+" | | "+p.getProductType().getName());
		}
		System.out.println("dataGrid.getTotal() :"+dataGrid.getTotal());
		
		SystemContext.setPageOffset(2);
		SystemContext.setPageSize(4);
		dataGrid = productService.dataGrid("PND");
		for (Product p : dataGrid.getRows()) {
			System.out.println(p.getName()+" | | "+p.getProductType().getName());
		}
		System.out.println("dataGrid.getTotal() :"+dataGrid.getTotal());
		
		SystemContext.setPageOffset(3);
		SystemContext.setPageSize(4);
		dataGrid = productService.dataGrid("PND");
		for (Product p : dataGrid.getRows()) {
			System.out.println(p.getName()+" | | "+p.getProductType().getName());
		}
		System.out.println("dataGrid.getTotal() :"+dataGrid.getTotal());
		
		SystemContext.setPageOffset(4);
		SystemContext.setPageSize(4);
		dataGrid = productService.dataGrid("PND");
		for (Product p : dataGrid.getRows()) {
			System.out.println(p.getName()+" | | "+p.getProductType().getName());
		}
		System.out.println("dataGrid.getTotal() :"+dataGrid.getTotal());*/

		//查
		/*Product product = productService.get(1);
		System.out.println(product.getName());
		for (MaterialBare bare : product.getMaterialBares()) {
			System.out.println(bare.getName()+"\t\t"+bare.getMaterialType().getName());

		}
		for (MaterialPacking bare : product.getMaterialPackings()) {
			System.out.println(bare.getName());
		}*/
		
		//增加
		/*Product product = new Product();
		product.setName("test11");
		product.setProductType(new ProductType(1));
		product.setState(1);
		Set<MaterialBare> materialBares = new HashSet<MaterialBare>();
		materialBares.add(new MaterialBare(1));
		materialBares.add(new MaterialBare(2));
		product.setMaterialBares(materialBares);
		
		productService.add(product);*/
		//查询前10行
		SystemContext.setPageSize(9);
		//List<Product> listProduct = productService.listProduct("A");
		List<Product> listProduct = productService.listProduct("");
		for (Product p : listProduct) {
			System.out.println(p.getName());
		}
	}
	@Test
	public void testMaterialTypeServiceI() {
		//查1
		/*List<MaterialType> dataGrid = materialTypeService.dataGrid();
		for (MaterialType m : dataGrid) {
			System.out.println(m.getType()+" | "+m.getName());
		}*/
		//查2
		HashSet<Integer> types = new HashSet<Integer>();
		types.add(1);
		//types.add(3);
		
		List<MaterialType> dataGrid = materialTypeService.dataGrid(types);
		for (MaterialType m : dataGrid) {
			System.out.println(m.getType()+" | "+m.getName());
		}
	}
	
	@Test
	public void testMaterialBareServiceI() {
		//org.hibernate.exception.ConstraintViolationException
		/*MaterialBare materialBare = new MaterialBare();
		//增
	/*	materialBare.setName("test物料2");
		materialBare.setDescription("test物料");
//		materialBare.setType(2); //不行
		//materialBare.setMaterialType(materialTypeService.get(2));
		materialBare.setMaterialType(new MaterialType(2));
		materialBareService.add(materialBare);*/
		//改
		/*materialBare.setDescription("test物料2");
		materialBare.setMaterialType(materialTypeService.get(2));
		materialBareService.edit(materialBare);*/
		//查
/*		materialBare = materialBareService.get(1);
		System.out.println(materialBare.getType());
		System.out.println(materialBare.getMaterialType());*/

		//查全部
	/*	Pager<MaterialBare> dataGrid = materialBareService.dataGrid();
		for (MaterialBare m : dataGrid.getRows()) {
			System.out.println(m.getName()+"| "+m.getMaterialType().getName());
		}*/
		/*Set<Product> materialBareProduct = materialBareService.getMaterialBareProduct(1);
		for (Product p : materialBareProduct) {
			System.out.println(p.getName());
		}*/
/*<<<<<<< HEAD
		Set<Integer> types = new HashSet<Integer>();
		types.add(1);
		
		Pager<MaterialBare> dataGrid = materialBareService.dataGrid(types);
		for (MaterialBare m : dataGrid.getRows()) {
			System.out.println(m.getName()+"| "+m.getMaterialType().getName());
=======
		//查
		Set<Integer> set = new HashSet<Integer>();
		set.add(1);
		Pager<MaterialBare> page = materialBareService.dataGridByType(set);
		for (MaterialBare m : page.getRows()) {
			System.out.println(m.getName()+" | "+m.getMaterialType().getName());
>>>>>>> temp
		}*/
		Set<Integer> set = new HashSet<Integer>();
		set.add(1);
		List<MaterialBare> listGridByType = materialBareService.listGridByType(set, 1);
		
		for(MaterialBare materialBare:listGridByType){
			System.out.println(materialBare.getName());
		}
	}
	@Test
	public void testMaterialPackingServiceI() {
		//查全部
		Pager<MaterialPacking> dataGrid = materialPackingService.dataGrid();
		for (MaterialPacking m : dataGrid.getRows()) {
			System.out.println(m.getName()+"| "+m.getMaterialType().getName());
		}
	}
}
