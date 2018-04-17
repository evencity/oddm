package com.apical.oddm.web.controller.bom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.apical.oddm.core.model.bom.BomMaterial;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.bom.BomMaterialFacade;
import com.apical.oddm.facade.bom.dto.BomMaterialDTO;
import com.apical.oddm.facade.bom.util.ReadExcel;
import com.apical.oddm.facade.pageModel.DataGrid;
import com.apical.oddm.web.controller.order.OrderFollowupController;
import com.apical.oddm.web.pageModel.base.Json;

@Controller
@RequestMapping("/bomMaterial")
public class BomMaterialController {

	private static final Logger log = LoggerFactory.getLogger(BomMaterialController.class);
	@Autowired
	private BomMaterialFacade bomMaterialFacade;
	
	@RequestMapping("/manager")
	public String manager() {
		return "/bom/bomMaterial-list";
	}
	

	@RequestMapping("/addPage")
	public String addPage() {
		return "/bom/bomMaterial-add";
	}
	
	@ResponseBody
	@RequestMapping(value="/getBomMaterialByNumber")
	public Json getBomMaterialByNumber(String code) {
		
		Json j = new Json();
	
			BomMaterialDTO bomMaterial = bomMaterialFacade.getBomMaterialByNumber(code);
			if(bomMaterial != null){
				j.setSuccess(true);
				j.setObj(bomMaterial);
				
			}else {
				j.setSuccess(false);
				j.setMsg("物料编号不存在");
			}
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value="/add")
	public Json addBomMaterial(BomMaterialDTO bomMaterial){
		Json j = new Json();
			try {
				System.out.println(bomMaterial);
				Boolean success = bomMaterialFacade.addBomMaterial(bomMaterial);
				if(success){
					j.setSuccess(true);
					j.setMsg("添加成功！");
				}else {
					j.setSuccess(false);
					j.setMsg("添加失败！");
				}
			} catch (ConstraintViolationException e) {
				j.setMsg("物料已存在！");
			}catch (Exception e) {
				j.setMsg(e.getMessage());
			}
		return j;
	}
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String mtlCode) {
		System.out.println(mtlCode);
		BomMaterialDTO bomMaterialDTO = bomMaterialFacade.getBomMaterialByNumber(mtlCode);
		
		request.setAttribute("bomMaterial", bomMaterialDTO);
		System.out.println(request.getAttribute("bomMaterial"));
		return "/bom/bomMaterial-edit";
	}
	
	@ResponseBody
	@RequestMapping(value="/update")
	public Json updateBomMaterial(BomMaterialDTO bomMaterial){
		Json j = new Json();
		try {
			bomMaterialFacade.updateBomMaterial(bomMaterial);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (ServiceException e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value="/del")
	public Json delBomMaterial(String materialCode) {
		System.out.println(materialCode);
		Json j = new Json();
		try {
			bomMaterialFacade.delBomMaterial(materialCode);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value="/getBomMaterialById")
	public BomMaterialDTO getBomMaterialById(int id){
		return bomMaterialFacade.getBomMaterialById(id);
	}
	
	@ResponseBody
	@RequestMapping(value="/dataGrid")
	public DataGrid getBomMaterialInPage(BomMaterialDTO bomMaterialDTO,
			PageCommand pageCommand) {
		return bomMaterialFacade.getBomMaterialInPage(bomMaterialDTO, pageCommand);
	}
	@ResponseBody
	@RequestMapping(value="/dataGridForBom")
	public DataGrid dataGridForBom(BomMaterialDTO bomMaterialDTO,
			PageCommand pageCommand) {
		return bomMaterialFacade.getBomMaterialInPage(bomMaterialDTO, pageCommand);
	}
	
	@ResponseBody
	@RequestMapping(value="/adds")
	public Json addBomMaterials(Map<Integer, Map<Integer,Object>> map){
		 Json j = new Json();
		 System.out.println("获得Excel表格的内容:");
		 
         for (int i = 0; i <= map.size(); i++) {
             System.out.println(map.get(i));
             System.out.println("========");
             System.out.println("===="+map.get(i).get(1));
             
             BomMaterialDTO bomMaterial = new BomMaterialDTO();
    		 bomMaterial.setMtlCode((String)map.get(i).get(1));
    		 bomMaterial.setMaterialName((String)map.get(i).get(2));
    		 bomMaterial.setSpecification((String)map.get(i).get(3));
    		 bomMaterial.setDescription((String)map.get(i).get(4));
    		 System.out.println("-----");
    		 try{
 				bomMaterialFacade.addBomMaterial(bomMaterial);
 			}catch(ConstraintViolationException e){
 				j.setMsg("物料编号"+(String)map.get(i).get(1)+"已存在");
 			}catch (Exception e) {
 				j.setMsg(e.getMessage());
 			}
         }
        
		return j;
	}
	
	String PATH = "";
	String filename = "";
	@ResponseBody
	@RequestMapping("/uploadz")
	 public boolean upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {  
		  
        System.out.println("开始");  
        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/views/bom/upload/");  
        String fileName = file.getOriginalFilename();  
        File targetFile = new File(path, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
        String filePath = path + "/" + fileName;
        PATH = filePath;
        System.out.println("filePath:"+filePath);
        //保存  
        try {  
            file.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return true;
	}
	@ResponseBody
	@RequestMapping("/importExcel")
	public Json importExcel(String path) {
		 Map<Integer, Map<Integer,Object>> maps = null;
		 Json j = new Json();
		 try {
	           ReadExcel excelReader = new ReadExcel(PATH);
	            // 对读取Excel表格标题测试
//	          String[] title = excelReader.readExcelTitle();
//	          System.out.println("获得Excel表格的标题:");
//	          for (String s : title) {
//	              System.out.print(s + " ");
//	          }
	            int count = 0;
	            maps = excelReader.readExcelContent();
	            List<BomMaterial> bomMaterials = new ArrayList<BomMaterial>();
	            System.out.println("......获得Excel表格的内容:");
	            if(maps != null && maps.size() > 0){
	            	for (int i = 1; i <= maps.size(); i++) {
		            	if(((String)maps.get(i).get(0)).length() == 14){
		            		 count++;
		            		 BomMaterial bomMaterial = new BomMaterial();
			          		 bomMaterial.setMtlCode((String)maps.get(i).get(0));
			          		 bomMaterial.setMaterialName((String)maps.get(i).get(1));
			          		 bomMaterial.setSpecification((String)maps.get(i).get(2));
			          		 bomMaterial.setDescription((String)maps.get(i).get(3));
			          		 bomMaterials.add(bomMaterial);
			          		 
			          		 try{
			          			Serializable id = bomMaterialFacade.addBomMaterials(bomMaterials);
			          			j.setMsg("共添加成功"+count+"条");
			        			j.setSuccess(true);

			       			}catch (Exception e) {
			       				log.error("添加数据失败，失败物料编码为："+(String)maps.get(i).get(0),e);
			       				j.setMsg(e.getMessage());
			       				j.setSuccess(false);
			       			}
		            	}
		            	
		          		
		            }
	            }else{
	            	j.setMsg("读取Excel内容失败");
        			j.setSuccess(false);
	            }
	            
	        } catch (FileNotFoundException e) {
	            log.error("未找到指定路径的文件!",e);
	            e.printStackTrace();
	        }catch (Exception e) {
	            e.printStackTrace();
	        }
		
		File files = new File(PATH);  
		 if (files.isFile() && files.exists()) {  
			 files.delete();  
		    }  
		return j;
		 
	}
}
