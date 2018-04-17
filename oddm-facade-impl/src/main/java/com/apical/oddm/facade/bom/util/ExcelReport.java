package com.apical.oddm.facade.bom.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

import com.apical.oddm.facade.bom.dto.BomDTO;
import com.apical.oddm.facade.bom.dto.BomMaterialRefDTO;


public class ExcelReport  {

	@SuppressWarnings("deprecation")
	public HSSFWorkbook creatExcelBomInfo(BomDTO bomInfo) throws Exception  
    {
		if(bomInfo != null ){
	        // 第一步，创建一个webbook，对应一个Excel文件  
			HSSFWorkbook wb = new HSSFWorkbook();  
	        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	        HSSFSheet sheet = wb.createSheet("Bom清单：");  
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        HSSFRow row = sheet.createRow((int) 0);  
//	        HSSFCell cell = row.createCell((short) 0);  
	        // 第四步，创建单元格，并设置值表头 设置表头居中  
	        HSSFCellStyle style2 = wb.createCellStyle();  
	       
	        //居中
	     //   style2.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
	    //    style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        HSSFFont font2=wb.createFont();
	        //字体大小
	        font2.setFontName("宋体");
	        font2.setFontHeightInPoints((short)14);
	        //字体加粗
	        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   
	        style2.setFont(font2);
	        
	        sheet.setColumnWidth(0, 8 * 256);
	        sheet.setColumnWidth(1, 30 * 256);
	        sheet.setColumnWidth(2, 30 * 256);
	        sheet.setColumnWidth(3, 45 * 256);
	        sheet.setColumnWidth(4, 15 * 256);
	        sheet.setColumnWidth(5, 15 * 256);
	        sheet.setColumnWidth(6, 40 * 256);
	        
	        HSSFRow row0 = sheet.createRow((int) 0);
	        HSSFCell cell = row.createCell((short) 1);  
	        cell.setCellValue(bomInfo.getTitle());  
	        cell.setCellStyle(style2);  
	        row0.setHeight((short) 500);
	        
	        HSSFCellStyle style3 = wb.createCellStyle();  
	        HSSFFont font3=wb.createFont();
	        font3.setFontName("宋体");
	        font3.setFontHeightInPoints((short)13);
	       
	        style3.setFont(font3);
	        
	        HSSFCellStyle style33 = wb.createCellStyle();  
	        HSSFFont font33=wb.createFont();
	        font33.setFontName("宋体");
	        font33.setFontHeightInPoints((short)13);
	        font33.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   
	        style33.setFont(font33);
	        
	        HSSFRow row1 = sheet.createRow((int) 1);
	        HSSFCell cell1 = row1.createCell((short) 1) ;
	        cell1.setCellValue("制表人:"+bomInfo.getMaker());  
	        cell1.setCellStyle(style3); 
	        cell1 = row1.createCell((short) 2);  
	        if(bomInfo.getDateString() != null){
	        	 cell1.setCellValue("日期:"+bomInfo.getDateString()); 
	        }else{
	        	 cell1.setCellValue("日期:"); 
	        }
	        cell1.setCellStyle(style3); 
	        cell1 = row1.createCell((short) 3);  
	        cell1.setCellValue("版本:"+bomInfo.getVersion());  
	        cell1.setCellStyle(style3); 
	        cell1 = row1.createCell((short) 6);  
	        cell1.setCellValue("订单号:"+bomInfo.getOrderInfoDTO().getOrderNo()+"数量:"+bomInfo.getOrderInfoDTO().getQuantity()+"套");  
	        cell1.setCellStyle(style33); 
	        style3 = wb.createCellStyle();
	        row1.setHeight((short) 500);
			
	        
	        HSSFCellStyle style4 = wb.createCellStyle();  
	        style4.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
	        style4.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        HSSFFont font4=wb.createFont();
	        font4.setFontName("宋体");
	        font4.setFontHeightInPoints((short)11);
	        font4.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   
	        style4.setFont(font4);
	        style4.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
 	        style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
 	        style4.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
 	        style4.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框  
	        
	        HSSFRow row3 = sheet.createRow((int) 2);
	        HSSFCell cell3 = row3.createCell((short) 0);  
	        cell3.setCellValue("序号");  
	        cell3.setCellStyle(style4);  
	        cell3 = row3.createCell((short) 1);  
	        cell3.setCellValue("物料编码");  
	        cell3.setCellStyle(style4);  
	        cell3 = row3.createCell((short) 2);  
	        cell3.setCellValue("品名");  
	        cell3.setCellStyle(style4); 
	        cell3 = row3.createCell((short) 3);  
	        cell3.setCellValue("规格型号及封装");  
	        cell3.setCellStyle(style4); 
	        cell3 = row3.createCell((short) 4);  
	        cell3.setCellValue("品牌");  
	        cell3.setCellStyle(style4); 
	        cell3 = row3.createCell((short) 5);  
	        cell3.setCellValue("用量");  
	        cell3.setCellStyle(style4); 
	        cell3 = row3.createCell((short) 6);  
	        cell3.setCellValue("描述");  
	        cell3.setCellStyle(style4); 
	        row3.setHeight((short) 500);
	        int a = 0;
	        int b = 0;
	        int c = 0;
	        int d = 0;
	        int e = 0;
	        int f = 0;
	        int g = 0;
	        int h = 0;
	        int i = 0;
	        int j = 0;
	        int k = 0;
	        int l = 0;
	        int o = 0;
	        
	        List<BomMaterialRefDTO> btr = new ArrayList<BomMaterialRefDTO>();
	        btr.addAll(bomInfo.getBomMaterialRefDTOs());
	        if(btr != null && btr.size() > 0){
	        	 //累加bomMaterialRef的数，先判断type的类型，然后判断是否是标题，然后标题和副标题加一
	 	       
	 	       for(BomMaterialRefDTO bmt : btr){
	 	        	if(bmt.getType() == 1){
	 	        		if(bmt.getSeq() == 0){
	 	        			a += 1;
	 	        		}else{
	 	        			b += 1;
	 	        		}
	 	        	}
	 	        	if(bmt.getType() == 2){
	 	        		c += 1;
	 	        	}
	 	        	if(bmt.getType() == 3){
	 	        		d += 1;
	 	        	}
	 	        	if(bmt.getType() == 4){
	 	        		if(bmt.getSeq() == 0){
	 	        			e += 1;
	 	        		}else{
	 	        			f += 1;
	 	        		}
	 	        	}
	 	        	if(bmt.getType() == 5){
	 	        		if(bmt.getSeq() == 0){
	 	        			g += 1;
	 	        		}else{
	 	        			h += 1;
	 	        		}
	 	        	}
	 	        	if(bmt.getType() == 6){
	 	        		if(bmt.getSeq() == 0){
	 	        			i += 1;
	 	        		}else{
	 	        			j += 1;
	 	        		}
	 	        	}
	 	        	if(bmt.getType() == 7){
	 	        		if(bmt.getSeq() == 0){
	 	        			k += 1;
	 	        		}else{
	 	        			l += 1;
	 	        		}
	 	        	}
	 	        	if(bmt.getType() == 8){
	 	        		o += 1;
	 	        	}
	 	        }
	 	        
	 	        HSSFCellStyle style5 = wb.createCellStyle();  
	 	        style5.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中
	 	        style5.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//居中
	 	        HSSFFont font5=wb.createFont();
	 	        font5.setFontName("宋体");
	 	        font5.setFontHeightInPoints((short)12);
	 	        font5.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   
	 	        style5.setFont(font5);

	 	        HSSFCellStyle style6 = wb.createCellStyle();  
	 	        HSSFFont font6=wb.createFont();
	 	        font6.setFontName("宋体");
	 	        font6.setFontHeightInPoints((short)13);
	 	        font6.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  //字体加粗 
	 	        style6.setFont(font6);
	 	        
	 	        HSSFRow row4 = sheet.createRow((int) 4);
	 	        HSSFCell cell4 = row4.createCell((short) 0);  
	 	        cell4.setCellValue("第一部分：裸机包装模组");  
	 	        cell4.setCellStyle(style6);  
	 	        row4.setHeight((short) 500);
	 	       
	 	        HSSFCellStyle style7 = wb.createCellStyle();  
	 	        style7.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
	 	        style7.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	 	        HSSFFont font7=wb.createFont();
	 	        font7.setFontName("宋体");
	 	        font7.setFontHeightInPoints((short)10);
	 	        style7.setFont(font7);
	 	        style7.setWrapText(true);   
	 	        style7.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
	 	        style7.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
	 	        style7.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
	 	        style7.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框  
	 	        
	 	        HSSFCellStyle styleblue = wb.createCellStyle();  
	 	        styleblue.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
	 	        styleblue.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	 	        HSSFFont fontblue=wb.createFont();
	 	        fontblue.setFontHeightInPoints((short)10);
	 	        fontblue.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   
	 	        styleblue.setFont(fontblue);
	 	        styleblue.setFillForegroundColor(HSSFColor.PALE_BLUE.index); 
	 	        //背景颜色
	 	        styleblue.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	 	        styleblue.setWrapText(true);    
	 	        styleblue.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
	 	        styleblue.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
	 	        styleblue.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
	 	        styleblue.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框  
	 	        
	 	        
	 	        HSSFCellStyle stylepink = wb.createCellStyle();  
	 	        stylepink.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
	 	        stylepink.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	 	        HSSFFont fontpink=wb.createFont();
	 	        fontpink.setFontHeightInPoints((short)10);
	 	        fontpink.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   
	 	        stylepink.setFont(fontpink);
	 	        stylepink.setFillForegroundColor(HSSFColor.ROSE.index); 
	 	        //背景颜色
	 	        stylepink.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	 	        stylepink.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
	 	        stylepink.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
	 	        stylepink.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
	 	        stylepink.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框  

	 	        for(BomMaterialRefDTO m : btr){
	 	        	if(m.getType() == 0){
	 	    			if(m.getSeq() == 0){
	 					    HSSFRow rowz = sheet.createRow((int) 3);
	 				        HSSFCell cellz = rowz.createCell((short) 0);  
	 				        cellz.setCellStyle(style5);
	 				        rowz.setHeight((short) 500);
	 	    				cellz = rowz.createCell((short) 0);
	 	    				cellz.setCellValue("");  
	 	    				cellz = rowz.createCell((short) 1);
	 	    				cellz.setCellValue(m.getMaterialCode());  
	 	    				cellz.setCellStyle(stylepink); 
	 	    				cellz = rowz.createCell((short) 2);
	 	    				cellz.setCellValue(m.getProductName());  
	 	    				cellz.setCellStyle(stylepink); 
	 	    				cellz = rowz.createCell((short) 3);
	 	    			    cellz.setCellValue(m.getSpecification());  
	 	    			    cellz.setCellStyle(stylepink); 
	 	    			    cellz = rowz.createCell((short) 6);
	 	    			    cellz.setCellValue(m.getDescription());  
	 	    			    cellz.setCellStyle(style7); 
	 	    			    cellz = rowz.createCell((short) 7);
	 	    			    cellz.setCellValue(m.getCompany());  
	 	    			    cellz.setCellStyle(style7); 
	 	    			    cellz = rowz.createCell((short) 8);
	 	    			    cellz.setCellValue(m.getContacts());  
	 	    			    cellz.setCellStyle(style7); 
	 	    			    cellz = rowz.createCell((short) 9);
	 	    			    cellz.setCellValue(m.getEmail());  
	 	    			    cellz.setCellStyle(style7); 
	 	    			    cellz = rowz.createCell((short) 10);
	 	    			    cellz.setCellValue(m.getCellphone());  
	 	    			    cellz.setCellStyle(style7); 
	 	    			    cellz = rowz.createCell((short) 11);
	 	    			    cellz.setCellValue(m.getTelphone());  
	 	    			    cellz.setCellStyle(style7); 
	 	    			   
	 	            	 }
	 	        	}
	 	       }
	 	        
	 	        for(BomMaterialRefDTO m : btr){
	 	        	if(m.getType() == 1){
	 	    			if(m.getSeq() == 0){
	 					    HSSFRow rowz = sheet.createRow((int) 5);
	 				        HSSFCell cellz = rowz.createCell((short) 0);  
	 				        cellz.setCellStyle(style5);
	 				        rowz.setHeight((short) 500);
	 	    				cellz = rowz.createCell((short) 0);
	 	    				cellz.setCellValue("");  
	 	    				cellz = rowz.createCell((short) 1);
	 	    				cellz.setCellValue(m.getMaterialCode());  
	 	    				cellz.setCellStyle(styleblue); 
	 	    				cellz = rowz.createCell((short) 2);
	 	    				cellz.setCellValue(m.getProductName());  
	 	    				cellz.setCellStyle(styleblue); 
	 	    				cellz = rowz.createCell((short) 3);
	 	    			    cellz.setCellValue(m.getSpecification());  
	 	    			    cellz.setCellStyle(styleblue); 
	 	    			    cellz = rowz.createCell((short) 6);
	 	    			    cellz.setCellValue(m.getDescription());  
	 	    			    cellz.setCellStyle(style7); 
	 	    			    cellz = rowz.createCell((short) 7);
	 	    			    cellz.setCellValue(m.getCompany());  
	 	    			    cellz.setCellStyle(style7); 
	 	    			    cellz = rowz.createCell((short) 8);
	 	    			    cellz.setCellValue(m.getContacts());  
	 	    			    cellz.setCellStyle(style7); 
	 	    			    cellz = rowz.createCell((short) 9);
	 	    			    cellz.setCellValue(m.getEmail());  
	 	    			    cellz.setCellStyle(style7); 
	 	    			    cellz = rowz.createCell((short) 10);
	 	    			    cellz.setCellValue(m.getCellphone());  
	 	    			    cellz.setCellStyle(style7); 
	 	    			    cellz = rowz.createCell((short) 11);
	 	    			    cellz.setCellValue(m.getTelphone());  
	 	    			    cellz.setCellStyle(style7); 
	 	    			   
	 	            	 }
	 	        	}
	 	       }
	 	        
	 	        int n1 = 0;
	 	        for(BomMaterialRefDTO m : btr){
	 	        	if(m.getType() == 1){
	 	    			if(m.getSeq() == 1){
	 	    				 
	 	    				 HSSFRow rowz = sheet.createRow((int) 6 +  n1);
	 	    				 rowz.setHeight((short) 500);
	 		   			     HSSFCell cellz = rowz.createCell((short) 3);  
	 		   			     cellz = rowz.createCell((short) 0);
	 		   			     n1 = n1 + 1;
	 		   			     cellz.setCellValue(n1);  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 1);
	 		   			     cellz.setCellValue(m.getMaterialCode());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 2);
	 		   			     cellz.setCellValue(m.getProductName());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 3);
	 		   			     cellz.setCellValue(m.getSpecification());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 4);
	 		   			     cellz.setCellValue(m.getBrand());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 5);
	 		   			     cellz.setCellValue(m.getUsageAmount());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 6);
	 		   			     cellz.setCellValue(m.getDescription());  
	 	     			     cellz.setCellStyle(style7);
	 	     			     cellz = rowz.createCell((short) 7);
	 	    			     cellz.setCellValue(m.getCompany());  
	 	    			     cellz.setCellStyle(style7); 
	 	    			     cellz = rowz.createCell((short) 8);
	 	    			     cellz.setCellValue(m.getContacts());  
	 	    			     cellz.setCellStyle(style7); 
	 	    			     cellz = rowz.createCell((short) 9);
	 	    			     cellz.setCellValue(m.getEmail());  
	 	    			     cellz.setCellStyle(style7); 
	 	    			     cellz = rowz.createCell((short) 10);
	 	    			     cellz.setCellValue(m.getCellphone());  
	 	    			     cellz.setCellStyle(style7); 
	 	    			     cellz = rowz.createCell((short) 11);
	 	    			     cellz.setCellValue(m.getTelphone());  
	 	    			     cellz.setCellStyle(style7);   
	 	    			}
	 	        	}
	 	       }
	 	        HSSFRow row7 = sheet.createRow((int) 6+a+b);
	 	        row7.setHeight((short) 500);
	 	        HSSFCell cell7 = row7.createCell((short) 0);  
	 	        cell7.setCellValue("一、裸机成品");  
	 	        cell7.setCellStyle(style2);  
	 	        
	 	        for(BomMaterialRefDTO m : btr){
	 	        	if(m.getType() == 2){
	 	    			if(m.getSeq() == 0){
	 	    				HSSFRow rowz = sheet.createRow((int) 6+a+b+1);
	 	 			        HSSFCell cellz = rowz.createCell((short) 0);  
	 	 			        cellz.setCellStyle(style5);
	 	 			        rowz.setHeight((short) 500);
	 	    				cellz = rowz.createCell((short) 0);
	 	    				cellz.setCellValue("");  
	 	    				cellz.setCellStyle(style7); 
	 	    				cellz = rowz.createCell((short) 1);
	 	    				cellz.setCellValue(m.getMaterialCode());  
	 	    				cellz.setCellStyle(styleblue); 
	 	    				cellz = rowz.createCell((short) 2);
	 	    				cellz.setCellValue(m.getProductName());  
	 	    				cellz.setCellStyle(styleblue); 
	 	    				cellz = rowz.createCell((short) 3);
	 	    			    cellz.setCellValue(m.getSpecification());  
	 	    			    cellz.setCellStyle(styleblue); 
	 	    			    cellz = rowz.createCell((short) 6);
	 	    			    cellz.setCellValue(m.getDescription());  
	 	    			    cellz.setCellStyle(style7); 
	 	    			    cellz = rowz.createCell((short) 7);
	 		   			    cellz.setCellValue(m.getCompany());  
	 		   			    cellz.setCellStyle(style7); 
	 		   			    cellz = rowz.createCell((short) 8);
	 		   			    cellz.setCellValue(m.getContacts());  
	 		   			    cellz.setCellStyle(style7); 
	 		   			    cellz = rowz.createCell((short) 9);
	 		   			    cellz.setCellValue(m.getEmail());  
	 		   			    cellz.setCellStyle(style7); 
	 		   			    cellz = rowz.createCell((short) 10);
	 		   			    cellz.setCellValue(m.getCellphone());  
	 		   			    cellz.setCellStyle(style7); 
	 		   			    cellz = rowz.createCell((short) 11);
	 		   			    cellz.setCellValue(m.getTelphone());  
	 		   			    cellz.setCellStyle(style7); 
	 	            	 }
	 	        	}
	 	       }
	 	       
	 	        HSSFRow row9 = sheet.createRow((int)  6+a+b+1+c);
	 	        row9.setHeight((short) 500);
	 	        HSSFCell cell91 = row9.createCell((short) 0);  
	 	        cell91.setCellValue("a");  
	 	        HSSFCell cell92 = row9.createCell((short) 1);  
	 	        cell92.setCellValue("散料，无虚拟件料号");  
	 	        cell91.setCellStyle(stylepink);
	 	        cell92.setCellStyle(style4);		
	 	        int n2 = 0;
	 	        for(BomMaterialRefDTO m : btr){
	 	        	if(m.getType() == 3){
	 	    			if(m.getSeq() == 1){
	 	    				 
	 	    				 HSSFRow rowz = sheet.createRow((int) 6+a+b+1+c+1+n2);
	 		   			     HSSFCell cellz = rowz.createCell((short) 3);  
	 		   			     cellz = rowz.createCell((short) 0);
	 		   			     rowz.setHeight((short) 500);
	 		   			     n2 = n2 + 1;
	 		   			     cellz.setCellValue(n2);  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 1);
	 		   			     cellz.setCellValue(m.getMaterialCode());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 2);
	 		   			     cellz.setCellValue(m.getProductName());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 3);
	 		   			     cellz.setCellValue(m.getSpecification());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 4);
	 		   			  	 cellz.setCellValue(m.getBrand());  
	 		   			  	 cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 5);
	 		   			     cellz.setCellValue(m.getUsageAmount());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 6);
	 		   			     cellz.setCellValue(m.getDescription());  
	 	     			     cellz.setCellStyle(style7);
	 	     			     cellz = rowz.createCell((short) 7);
	 		   			     cellz.setCellValue(m.getCompany());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 8);
	 		   			     cellz.setCellValue(m.getContacts());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 9);
	 		   			     cellz.setCellValue(m.getEmail());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 10);
	 		   			     cellz.setCellValue(m.getCellphone());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 11);
	 		   			     cellz.setCellValue(m.getTelphone());  
	 		   			     cellz.setCellStyle(style7);
	 	            	 }
	 	        	}
	 	       }
	 	//
//	 			HSSFRow row13 = sheet.createRow((int) 6+a+b+1+c+1+d);
//	 			row13.setHeight((short) 500);
//	 			HSSFCell cell13 = row13.createCell((short) 0);  
//	 			cell13.setCellValue("b.外壳模组");  
//	 			cell13.setCellStyle(style4);	
	 			
	 	      
	 	        for(BomMaterialRefDTO m : btr){
	 	        	if(m.getType() == 4){
	 	    			if(m.getSeq() == 0){
	 					    HSSFRow rowz = sheet.createRow((int) 6+a+b+1+c+1+d);
	 				        rowz.setHeight((short) 500);
	 				        rowz.setRowStyle(style4);
	 				        HSSFCell cellz = rowz.createCell((short) 0);  
	 	    				cellz = rowz.createCell((short) 0);
	 	    				rowz.setHeight((short) 500);
	 	    				cellz.setCellValue("b");  
	 	    				cellz.setCellStyle(stylepink); 
	 	    				cellz = rowz.createCell((short) 1);
	 	    				cellz.setCellValue(m.getMaterialCode());  
	 	    				cellz.setCellStyle(styleblue); 
	 	    				cellz = rowz.createCell((short) 2);
	 	    				cellz.setCellValue(m.getProductName());  
	 	    				cellz.setCellStyle(styleblue); 
	 	    				cellz = rowz.createCell((short) 3);
	 	    			    cellz.setCellValue(m.getSpecification());  
	 	    			    cellz.setCellStyle(styleblue); 
	 	    			    cellz = rowz.createCell((short) 6);
	 	    			    cellz.setCellValue(m.getDescription());  
	 	    			    cellz.setCellStyle(style7); 
	 	    			    cellz = rowz.createCell((short) 7);
	 		   			    cellz.setCellValue(m.getCompany());  
	 		   			    cellz.setCellStyle(style7); 
	 		   			    cellz = rowz.createCell((short) 8);
	 		   			    cellz.setCellValue(m.getContacts());  
	 		   			    cellz.setCellStyle(style7); 
	 		   			    cellz= rowz.createCell((short) 9);
	 		   			    cellz.setCellValue(m.getEmail());  
	 		   			    cellz.setCellStyle(style7); 
	 		   			    cellz = rowz.createCell((short) 10);
	 		   			    cellz.setCellValue(m.getCellphone());  
	 		   			    cellz.setCellStyle(style7); 
	 		   			    cellz = rowz.createCell((short) 11);
	 		   			    cellz.setCellValue(m.getTelphone());  
	 		   			    cellz.setCellStyle(style7); 
	 	            	 }
	 	        	}
	 	       }

	 	        int n3 = 0;
	 	        for(BomMaterialRefDTO m : btr){
	 	        	if(m.getType() == 4){
	 	    			if(m.getSeq() == 1){
	 	    				 
	 	    				 HSSFRow rowz = sheet.createRow((int) 6+a+b+1+c+1+d+e+n3);
	 		   			     HSSFCell cellz = rowz.createCell((short) 3);  
	 		   			     cellz = rowz.createCell((short) 0);
	 		   			     rowz.setHeight((short) 500);
	 		   			     n3 = n3 + 1;
	 		   			     cellz.setCellValue(n3);  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 1);
	 		   			     cellz.setCellValue(m.getMaterialCode());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 2);
	 		   			     cellz.setCellValue(m.getProductName());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 3);
	 		   			     cellz.setCellValue(m.getSpecification());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 4);
	 		   			  	 cellz.setCellValue(m.getBrand());  
	 		   			  	 cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 5);
	 		   			     cellz.setCellValue(m.getUsageAmount());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 6);
	 		   			     cellz.setCellValue(m.getDescription());  
	 	     			     cellz.setCellStyle(style7);
	 	     			     cellz = rowz.createCell((short) 7);
	 		   			     cellz.setCellValue(m.getCompany());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 8);
	 		   			     cellz.setCellValue(m.getContacts());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 9);
	 		   			     cellz.setCellValue(m.getEmail());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 10);
	 		   			     cellz.setCellValue(m.getCellphone());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 11);
	 		   			     cellz.setCellValue(m.getTelphone());  
	 		   			     cellz.setCellStyle(style7);
	 	     			     
	 	            	 }
	 	        	}
	 	       }
	 	      
	 	        for(BomMaterialRefDTO m : btr){
	 	        	if(m.getType() == 5){
	 	    			if(m.getSeq() == 0){
	 					    HSSFRow rowz = sheet.createRow((int) 6+a+b+1+c+1+d+e+f);
	 				        rowz.setHeight((short) 500);
	 				        HSSFCell cellz = rowz.createCell((short) 0);  
	 				        rowz.setHeight((short) 500);
	 	    				cellz = rowz.createCell((short) 0);
	 	    				cellz.setCellValue("C");  
	 	    				cellz.setCellStyle(stylepink); 
	 	    				cellz = rowz.createCell((short) 1);
	 	    				cellz.setCellValue(m.getMaterialCode());  
	 	    				cellz.setCellStyle(styleblue); 
	 	    				cellz = rowz.createCell((short) 2);
	 	    				cellz.setCellValue(m.getProductName());  
	 	    				cellz.setCellStyle(styleblue); 
	 	    				cellz = rowz.createCell((short) 3);
	 	    			    cellz.setCellValue(m.getSpecification());  
	 	    			    cellz.setCellStyle(styleblue); 
	 	    			    cellz = rowz.createCell((short) 6);
	 	    			    cellz.setCellValue(m.getDescription());  
	 	    			    cellz.setCellStyle(style7); 
	 	    			    cellz = rowz.createCell((short) 7);
	 	   			     	cellz.setCellValue(m.getCompany());  
	 	   			     	cellz.setCellStyle(style7); 
	 	   			     	cellz = rowz.createCell((short) 8);
	 	   			     	cellz.setCellValue(m.getContacts());  
	 	   			     	cellz.setCellStyle(style7); 
	 	   			     	cellz = rowz.createCell((short) 9);
	 	   			     	cellz.setCellValue(m.getEmail());  
	 	   			     	cellz.setCellStyle(style7); 
	 	   			     	cellz = rowz.createCell((short) 10);
	 	   			     	cellz.setCellValue(m.getCellphone());  
	 	   			     	cellz.setCellStyle(style7); 
	 	   			     	cellz = rowz.createCell((short) 11);
	 	   			     	cellz.setCellValue(m.getTelphone());  
	 	   			     	cellz.setCellStyle(style7);
	 	            	 }
	 	        	}
	 	       }
	 	        int y = 0;
	 	        for(BomMaterialRefDTO m : btr){
	 	        	if(m.getType() == 5){
	 	    			if(m.getSeq() == 1){
	 	    				 HSSFRow rowz = sheet.createRow((int) 6+a+b+1+c+1+d+e+f+g+y);
	 	    			     HSSFCell cellz = rowz.createCell((short) 3);  
	 	    			     cellz = rowz.createCell((short) 0);
	 	    			     rowz.setHeight((short) 500);
	 	    			     y = y + 1;
	 	    			     cellz.setCellValue(y);  
	 	    			     cellz.setCellStyle(style7); 
	 	    			     cellz = rowz.createCell((short) 1);
	 	    			     cellz.setCellValue(m.getMaterialCode());  
	 	    			     cellz.setCellStyle(style7); 
	 	    			     cellz = rowz.createCell((short) 2);
	 	    			     cellz.setCellValue(m.getProductName());  
	 	    			     cellz.setCellStyle(style7); 
	 	    			     cellz = rowz.createCell((short) 3);
	 	    			     cellz.setCellValue(m.getSpecification());  
	 	    			     cellz.setCellStyle(style7); 
	 	    			     cellz = rowz.createCell((short) 4);
	 		   			  	 cellz.setCellValue(m.getBrand());  
	 		   			  	 cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 5);
	 		   			     cellz.setCellValue(m.getUsageAmount());  
	 		   			     cellz.setCellStyle(style7); 
	 	    			     cellz = rowz.createCell((short) 6);
	 	    			     cellz.setCellValue(m.getDescription());  
	 	      			     cellz.setCellStyle(style7);
	 	      			     cellz = rowz.createCell((short) 7);
	 		   			     cellz.setCellValue(m.getCompany());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 8);
	 		   			     cellz.setCellValue(m.getContacts());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 9);
	 		   			     cellz.setCellValue(m.getEmail());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 10);
	 		   			     cellz.setCellValue(m.getCellphone());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 11);
	 		   			     cellz.setCellValue(m.getTelphone());  
	 		   			     cellz.setCellStyle(style7);
	 	            	 }
	 	        	}
	 	       }
	 	        HSSFRow row18 = sheet.createRow((int) 6+a+b+1+c+1+d+e+f+g+h);
	 	        row18.setHeight((short) 500);
	 	 		HSSFCell cell18 = row18.createCell((short) 0);  
	 	 		cell18.setCellValue("第二部分：彩盒模组部分");  
	 	 		cell18.setCellStyle(style2);	
	 	 		
	 	       
	 	         for(BomMaterialRefDTO m : btr){
	 	         	if(m.getType() == 6){
	 	     			if(m.getSeq() == 0){
	 	     				HSSFRow rowz = sheet.createRow((int) 6+a+b+1+c+1+d+e+f+g+h+1);
	 	     		        rowz.setHeight((short) 500);
	 	     		        HSSFCell cellz = rowz.createCell((short) 0);  
	 	     		        cellz.setCellStyle(style5);
	 	     				cellz = rowz.createCell((short) 0);
	 	    				cellz.setCellValue("");  
	 	    				cellz.setCellStyle(style7); 
	 	    				cellz = rowz.createCell((short) 1);
	 	    				cellz.setCellValue(m.getMaterialCode());  
	 	    				cellz.setCellStyle(styleblue); 
	 	    				cellz = rowz.createCell((short) 2);
	 	    				cellz.setCellValue(m.getProductName());  
	 	    				cellz.setCellStyle(styleblue); 
	 	    				cellz = rowz.createCell((short) 3);
	 	    			    cellz.setCellValue(m.getSpecification());  
	 	    			    cellz.setCellStyle(styleblue); 
	 	    			    cellz = rowz.createCell((short) 6);
	 	    			    cellz.setCellValue(m.getDescription());  
	 	    			    cellz.setCellStyle(style7); 
	 	    			    cellz = rowz.createCell((short) 7);
	 		   			    cellz.setCellValue(m.getCompany());  
	 		   			    cellz.setCellStyle(style7); 
	 		   			    cellz = rowz.createCell((short) 8);
	 		   			    cellz.setCellValue(m.getContacts());  
	 		   			    cellz.setCellStyle(style7); 
	 		   			    cellz = rowz.createCell((short) 9);
	 		   			    cellz.setCellValue(m.getEmail());  
	 		   			    cellz.setCellStyle(style7); 
	 		   			    cellz = rowz.createCell((short) 10);
	 		   			    cellz.setCellValue(m.getCellphone());  
	 		   			    cellz.setCellStyle(style7); 
	 		   			    cellz = rowz.createCell((short) 11);
	 		   			    cellz.setCellValue(m.getTelphone());  
	 		   			    cellz.setCellStyle(style7);
	 	             	 }
	 	         	}
	 	        }
	 	         
	 	         int n = 0;
	 	         for(BomMaterialRefDTO m : btr){
	 	         	if(m.getType() == 6){
	 	     			if(m.getSeq() == 1){
	 	     				 HSSFRow rowz = sheet.createRow((int)  6+a+b+1+c+1+d+e+f+g+h+1+i+n);
	 	    			     HSSFCell cellz = rowz.createCell((short) 3);  
	 	    			     cellz = rowz.createCell((short) 0);
	 	    			     rowz.setHeight((short) 500);
	 	    			     n = n + 1;
	 	    			     cellz.setCellValue(n);  
	 	    			     cellz.setCellStyle(style7); 
	 	    			     cellz = rowz.createCell((short) 1);
	 	    			     cellz.setCellValue(m.getMaterialCode());  
	 	    			     cellz.setCellStyle(style7); 
	 	    			     cellz = rowz.createCell((short) 2);
	 	    			     cellz.setCellValue(m.getProductName());  
	 	    			     cellz.setCellStyle(style7); 
	 	    			     cellz = rowz.createCell((short) 3);
	 	    			     cellz.setCellValue(m.getSpecification());  
	 	    			     cellz.setCellStyle(style7); 
	 	    			     cellz = rowz.createCell((short) 4);
	 		   			  	 cellz.setCellValue(m.getBrand());  
	 		   			  	 cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 5);
	 		   			     cellz.setCellValue(m.getUsageAmount());  
	 		   			     cellz.setCellStyle(style7); 
	 	    			     cellz = rowz.createCell((short) 6);
	 	    			     cellz.setCellValue(m.getDescription());  
	 	      			     cellz.setCellStyle(style7);
	 	      			     cellz = rowz.createCell((short) 7);
	 		   			     cellz.setCellValue(m.getCompany());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 8);
	 		   			     cellz.setCellValue(m.getContacts());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 9);
	 		   			     cellz.setCellValue(m.getEmail());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 10);
	 		   			     cellz.setCellValue(m.getCellphone());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 11);
	 		   			     cellz.setCellValue(m.getTelphone());  
	 		   			     cellz.setCellStyle(style7);
	 	             	 }
	 	         	}
	 	        }
	 	        HSSFRow row21 = sheet.createRow((int) 6+a+b+1+c+1+d+e+f+g+h+1+i+j);
	 	        row21.setHeight((short) 500);
	 	  		HSSFCell cell21 = row21.createCell((short) 0);  
	 	  		cell21.setCellValue("第三部分：卡通箱模组部分");  
	 	  		cell21.setCellStyle(style2);	
	 	  		
	 	        
	 	          for(BomMaterialRefDTO m : btr){
	 	          	if(m.getType() == 7){
	 	      			if(m.getSeq() == 0){
	 		      			HSSFRow rowz = sheet.createRow((int) 6+a+b+1+c+1+d+e+f+g+h+1+i+j+1);
	 		      	        rowz.setHeight((short) 500);
	 		      	        HSSFCell cellz = rowz.createCell((short) 0);  
	 		      	        cellz.setCellStyle(style5);
	 	      				cellz = rowz.createCell((short) 0);
	 	    				cellz.setCellValue("");  
	 	    				cellz.setCellStyle(style7); 
	 	    				cellz = rowz.createCell((short) 1);
	 	    				cellz.setCellValue(m.getMaterialCode());  
	 	    				cellz.setCellStyle(styleblue); 
	 	    				cellz = rowz.createCell((short) 2);
	 	    				cellz.setCellValue(m.getProductName());  
	 	    				cellz.setCellStyle(styleblue); 
	 	    				cellz = rowz.createCell((short) 3);
	 	    			    cellz.setCellValue(m.getSpecification());  
	 	    			    cellz.setCellStyle(styleblue); 
	 	    			    cellz = rowz.createCell((short) 6);
	 	    			    cellz.setCellValue(m.getDescription());  
	 	    			    cellz.setCellStyle(style7); 
	 	    			    cellz = rowz.createCell((short) 7);
	 		   			    cellz.setCellValue(m.getCompany());  
	 		   			    cellz.setCellStyle(style7); 
	 		   			    cellz = rowz.createCell((short) 8);
	 		   			    cellz.setCellValue(m.getContacts());  
	 		   			    cellz.setCellStyle(style7); 
	 		   			    cellz = rowz.createCell((short) 9);
	 		   			    cellz.setCellValue(m.getEmail());  
	 		   			    cellz.setCellStyle(style7); 
	 		   			    cellz = rowz.createCell((short) 10);
	 		   			    cellz.setCellValue(m.getCellphone());  
	 		   			    cellz.setCellStyle(style7); 
	 		   			    cellz = rowz.createCell((short) 11);
	 		   			    cellz.setCellValue(m.getTelphone());  
	 		   			    cellz.setCellStyle(style7);
	 	              	 }
	 	          	}
	 	         }
	 	          
	 	          int n4 = 0;
	 	          for(BomMaterialRefDTO m : btr){
	 	          	if(m.getType() == 7){
	 	      			if(m.getSeq() == 1){
	 	    				 HSSFRow rowz = sheet.createRow((int)  6+a+b+1+c+1+d+e+f+g+h+1+i+j+1+k+n4);
	 		   			     HSSFCell cellz = rowz.createCell((short) 3);  
	 		   			     cellz = rowz.createCell((short) 0);
	 		   			     rowz.setHeight((short) 500);
	 		   			     n4 = n4 + 1;
	 		   			     cellz.setCellValue(n4);  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 1);
	 		   			     cellz.setCellValue(m.getMaterialCode());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 2);
	 		   			     cellz.setCellValue(m.getProductName());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 3);
	 		   			     cellz.setCellValue(m.getSpecification());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 4);
	 		   			  	 cellz.setCellValue(m.getBrand());  
	 		   			  	 cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 5);
	 		   			     cellz.setCellValue(m.getUsageAmount());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 6);
	 		   			     cellz.setCellValue(m.getDescription());  
	 	     			     cellz.setCellStyle(style7);
	 	     			     cellz = rowz.createCell((short) 7);
	 		   			     cellz.setCellValue(m.getCompany());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 8);
	 		   			     cellz.setCellValue(m.getContacts());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 9);
	 		   			     cellz.setCellValue(m.getEmail());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 10);
	 		   			     cellz.setCellValue(m.getCellphone());  
	 		   			     cellz.setCellStyle(style7); 
	 		   			     cellz = rowz.createCell((short) 11);
	 		   			     cellz.setCellValue(m.getTelphone());  
	 		   			     cellz.setCellStyle(style7);
	 	              	 }
	 	          	}
	 	         }
	 	          HSSFRow row24 = sheet.createRow((int) 6+a+b+1+c+1+d+e+f+g+h+1+i+j+1+k+l);
	 	          row24.setHeight((short) 500);
	 	    		HSSFCell cell24 = row24.createCell((short) 0);  
	 	    		cell24.setCellValue("第四部分：配件及其他包材类");  
	 	    		cell24.setCellStyle(style2);	
	 	            int n5 = 0;
	 	            for(BomMaterialRefDTO m : btr){
	 	            	if(m.getType() == 8){
	 	        			if(m.getSeq() == 1){
	 	        				 
	 	        				 HSSFRow rowz = sheet.createRow((int) 6+a+b+1+c+1+d+e+f+g+h+1+i+j+1+k+l+1+n5);
	 		       			     HSSFCell cellz = rowz.createCell((short) 3);  
	 		       			     cellz = rowz.createCell((short) 0);
	 		       			     rowz.setHeight((short) 500);
	 		       			     n5 = n5 + 1;
	 		       			     cellz.setCellValue(n5);  
	 		       			     cellz.setCellStyle(style7); 
	 		       			     cellz = rowz.createCell((short) 1);
	 		       			     cellz.setCellValue(m.getMaterialCode());  
	 		       			     cellz.setCellStyle(style7); 
	 		       			     cellz = rowz.createCell((short) 2);
	 		       			     cellz.setCellValue(m.getProductName());  
	 		       			     cellz.setCellStyle(style7); 
	 		       			     cellz = rowz.createCell((short) 3);
	 		       			     cellz.setCellValue(m.getSpecification());  
	 		       			     cellz.setCellStyle(style7); 
	 		       			     cellz = rowz.createCell((short) 4);
	 			   			  	 cellz.setCellValue(m.getBrand());  
	 			   			  	 cellz.setCellStyle(style7); 
	 			   			     cellz = rowz.createCell((short) 5);
	 			   			     cellz.setCellValue(m.getUsageAmount());  
	 			   			     cellz.setCellStyle(style7); 
	 		       			     cellz = rowz.createCell((short) 6);
	 		       			     cellz.setCellValue(m.getDescription());  
	 	         			     cellz.setCellStyle(style7);
	 	         			     cellz = rowz.createCell((short) 7);
	 		   	   			     cellz.setCellValue(m.getCompany());  
	 		   	   			     cellz.setCellStyle(style7); 
	 		   	   			     cellz = rowz.createCell((short) 8);
	 		   	   			     cellz.setCellValue(m.getContacts());  
	 		   	   			     cellz.setCellStyle(style7); 
	 		   	   			     cellz = rowz.createCell((short) 9);
	 		   	   			     cellz.setCellValue(m.getEmail());  
	 		   	   			     cellz.setCellStyle(style7); 
	 		   	   			     cellz = rowz.createCell((short) 10);
	 		   	   			     cellz.setCellValue(m.getCellphone());  
	 		   	   			     cellz.setCellStyle(style7); 
	 		   	   			     cellz = rowz.createCell((short) 11);
	 		   	   			     cellz.setCellValue(m.getTelphone());  
	 		   	   			     cellz.setCellStyle(style7);
	 	                	 }
	 	            	}
	 	           }
	 	            HSSFRow row26 = sheet.createRow((int) 6+a+b+1+c+1+d+e+f+g+h+1+i+j+1+k+l+1+o);
	 	            row26.setHeight((short) 500);
	 	    		HSSFCell cell26 = row26.createCell((short) 0);  
	 	    		cell26.setCellValue("第五部分：备品");  
	 	    		cell26.setCellStyle(style2);	
	 	    		
	 	    		int z = 0;
	 	            for(BomMaterialRefDTO m : btr){
	 	            	if(m.getType() == 9){
	 	        		   if(m.getSeq() == 1){
	 	        			   HSSFRow rowz = sheet.createRow((int) 6+a+b+1+c+1+d+e+f+g+h+1+i+j+1+k+l+1+o+1+z);
	 		       			   HSSFCell cellz = rowz.createCell((short) 3);  
	 	        			   cellz = rowz.createCell((short) 0);
	 	        				rowz.setHeight((short) 500);
	 	        				z = z + 1;
	 	        				cellz.setCellValue(z);  
	 	        				cellz.setCellStyle(style7); 
	 	        				cellz = rowz.createCell((short) 1);
	 	        				cellz.setCellStyle(style7); 
	 	        				cellz = rowz.createCell((short) 2);
	 	        				cellz.setCellValue(m.getProductName());  
	 	        				cellz.setCellStyle(style7); 
	 	        				cellz = rowz.createCell((short) 3);
	 	        				cellz.setCellValue(m.getSpecification());  
	 	        				cellz.setCellStyle(style7); 
	 	        				cellz = rowz.createCell((short) 4);
	 	        				cellz.setCellStyle(style7); 
	 	        				cellz = rowz.createCell((short) 5);
	 	        				cellz.setCellValue(m.getUsageAmount());  
	 	        				cellz.setCellStyle(style7);
	 	        				
	 	                	 }
	 	            	}
	 	           }
	        }
	      
//	        HSSFRow row4 = sheet.createRow((int) 2);
//	        HSSFCell cell4 = row2.createCell((short) 3);  
//	        cell2.setCellValue("第一部分：裸机包装模组");  
//	        cell2.setCellStyle(style);  
	        
//	        HSSFRow row5 = sheet.createRow((int) 5);
//	        HSSFCell cell5 = row5.createCell((short) 0);  
//	        cell5.setCellValue("物料编码");  
//	        cell5.setCellStyle(style);  
//	        cell5 = row4.createCell((short) 1);  
//	        cell5.setCellValue("制表人");  
//	        cell5.setCellStyle(style); 
//	        cell5 = row4.createCell((short) 2);  
//	        cell5.setCellValue("日期");  
//	        cell5.setCellStyle(style); 
//	        cell5 = row4.createCell((short) 3);  
//	        cell5.setCellValue("版本");  
//	        cell5.setCellStyle(style); 
	//
//	        
//	        while(it.hasNext()){
//	        	BomMaterialRef b = (BomMaterialRef)it.next();
//	        	if(b.getType() == 2){
//	    			if(b.getSeq() == 1){
//	            		row5.createCell((short) 0).setCellValue(b.getMaterialCode());  
//	         	        row5.createCell((short) 1).setCellValue(b.getProductName());  
//	         	        row5.createCell((short) 2).setCellValue(b.getSpecification());  
//	         	        row5.createCell((short) 3).setCellValue(b.getDescription()); 
//	            	 }
//	        	}
//	       }
	      /* String filePath = path + "/" + bomInfo.getTitle().replace(" ", "") + ".xls";
	        // 第六步，将文件存到指定位置  
	        try  
	        {  
	            FileOutputStream fout = new FileOutputStream(filePath);  
	            wb.write(fout);  
	            fout.close();  
	        }  
	        catch (Exception e1)  
	        {  
	            e1.printStackTrace();  
	        }  */
	        return wb;
		}
		return null;
    }  
}
