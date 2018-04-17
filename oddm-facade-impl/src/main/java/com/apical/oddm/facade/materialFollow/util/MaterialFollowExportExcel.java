package com.apical.oddm.facade.materialFollow.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

import com.apical.oddm.facade.materialFollow.dto.MaterialFollowupDTO;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;

/**
 * @description 类的描述：
 * @author 作者 : zhuzh
 * @date 创建时间：2016年12月15日 下午5:05:49
 * @version 1.0
 */

public class MaterialFollowExportExcel {

	public static HSSFWorkbook export(List<MaterialFollowupDTO> materialFollowupDTOs,String picturePath) throws IOException {
		if ( picturePath != null) {
			// 第一步，创建一个webbook，对应一个Excel文件
			HSSFWorkbook wb = new HSSFWorkbook();
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet("物料跟进表");
			
			sheet.setDefaultRowHeightInPoints(20); //默认行高
			
			BufferedImage bufferImg = null;    
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();     
            bufferImg = ImageIO.read(new File(picturePath));     
            ImageIO.write(bufferImg, "png", byteArrayOut); 
            //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）  
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();     
            //anchor主要用于设置图片的属性  
            HSSFClientAnchor anchor = new HSSFClientAnchor(200, 200, 255, 255,(short) 0, 0, (short) 3, 3);     
            anchor.setAnchorType(3);     
            //插入图片    
            patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
            
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
            sheet.addMergedRegion(new CellRangeAddress(1,1,5,10)); 
			sheet.addMergedRegion(new CellRangeAddress(3,3,5,8)); 
			sheet.addMergedRegion(new CellRangeAddress(4,4,5,8)); 
			sheet.addMergedRegion(new CellRangeAddress(5,5,5,8)); 
			
			sheet.addMergedRegion(new CellRangeAddress(3,3,10,11)); 
			sheet.addMergedRegion(new CellRangeAddress(4,4,10,11)); 
			
			sheet.addMergedRegion(new CellRangeAddress(7,7,1,13)); 
			//标题
			HSSFCellStyle styleTitle = wb.createCellStyle();
			styleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			styleTitle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			
			HSSFFont fontTitle = wb.createFont();
			fontTitle.setFontName("Cambria");
			fontTitle.setFontHeightInPoints((short) 15);// 设置字体大小
			fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			styleTitle.setFont(fontTitle);
			
			HSSFRow row1 = sheet.createRow((int) 1);
			row1.setHeight((short) 500);
			HSSFCell cell1 = row1.createCell(5);
			cell1.setCellValue("整机订单物料齐套状况跟踪表");
			cell1.setCellStyle(styleTitle);
			
			//标题下信息
			HSSFCellStyle styleTitle_msg = wb.createCellStyle();
			styleTitle_msg.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 创建一个居中格式
			styleTitle_msg.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			
			HSSFFont fontTitle_msg = wb.createFont();
			fontTitle_msg.setFontName("Cambria");
			fontTitle_msg.setFontHeightInPoints((short) 11);// 设置字体大小
			fontTitle_msg.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			styleTitle_msg.setFont(fontTitle_msg);
			
			HSSFRow row3 = sheet.createRow((int) 3);
			row3.setHeight((short) 350);
			HSSFCell row3_To = row3.createCell(5);
			row3_To.setCellValue("TO:");
			row3_To.setCellStyle(styleTitle_msg);
			HSSFCell row3_Tel = row3.createCell(10);
			row3_Tel.setCellValue("电话:");
			row3_Tel.setCellStyle(styleTitle_msg);
			
			HSSFRow row4 = sheet.createRow((int) 4);
			HSSFCell row4_From = row4.createCell(5);
			row4.setHeight((short) 350);
			row4_From.setCellValue("FROM:");
			row4_From.setCellStyle(styleTitle_msg);
			HSSFCell row4_Date = row4.createCell(10);
			row4_Date.setCellValue("DATE:");
			row4_Date.setCellStyle(styleTitle_msg);
			
			HSSFRow row5 = sheet.createRow((int) 5);
			HSSFCell row5_CC = row5.createCell(5);
			row5.setHeight((short) 350);
			row5_CC.setCellValue("CC:");
			row5_CC.setCellStyle(styleTitle_msg);
			
			HSSFCellStyle styleTitle_tip = wb.createCellStyle();
			//styleTitle_tip.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			styleTitle_tip.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			//styleTitle_tip.setFillBackgroundColor(HSSFColor.LAVENDER.index);
			styleTitle_tip.setFillForegroundColor(IndexedColors.LAVENDER.getIndex());
			styleTitle_tip.setFillPattern(CellStyle.SOLID_FOREGROUND);
			
			HSSFFont fontTitle_tip = wb.createFont();
			fontTitle_tip.setFontName("微软雅黑");
			fontTitle_tip.setFontHeightInPoints((short) 10);// 设置字体大小
			fontTitle_tip.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			styleTitle_tip.setFont(fontTitle_tip);
			
			HSSFRow row7 = sheet.createRow((int) 7);
			row7.setHeight((short) 400);
			HSSFCell row7_msg = row7.createCell(1);
			row7_msg.setCellValue("经营部负责填写和更新:");
			row7_msg.setCellStyle(styleTitle_tip);
			
			
			//正式内容数据
			HSSFCellStyle styleBold_BlackHead = wb.createCellStyle();
			styleBold_BlackHead.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			styleBold_BlackHead.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			styleBold_BlackHead.setWrapText(true);
			styleBold_BlackHead.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
			styleBold_BlackHead.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			styleBold_BlackHead.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
			styleBold_BlackHead.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			styleBold_BlackHead.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
			styleBold_BlackHead.setTopBorderColor(IndexedColors.BLACK.getIndex());
			styleBold_BlackHead.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框    
			styleBold_BlackHead.setRightBorderColor(IndexedColors.BLACK.getIndex());
			HSSFFont fontBold_BlackHead = wb.createFont();
			fontBold_BlackHead.setFontName("微软雅黑");
			fontBold_BlackHead.setFontHeightInPoints((short) 9);// 设置字体大小
			fontBold_BlackHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			styleBold_BlackHead.setFont(fontBold_BlackHead);
			
			
			
			HSSFRow row8 = sheet.createRow((int) 8);
			
			//row8.setHeight((short) 450);
			
			HSSFCell cellHead = row8.createCell(0);
			cellHead.setCellValue("生产顺位");
			cellHead.setCellStyle(styleBold_BlackHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 12); 
			
			cellHead = row8.createCell(1);
			cellHead.setCellValue("齐料日期");
			cellHead.setCellStyle(styleBold_BlackHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 12);  
			
			cellHead = row8.createCell(2);
			cellHead.setCellValue("外销订单下达日期");
			cellHead.setCellStyle(styleBold_BlackHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 12);  
			
			cellHead = row8.createCell(3);
			cellHead.setCellValue("料号确定日期");
			cellHead.setCellStyle(styleBold_BlackHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 12);  
			
			cellHead = row8.createCell(4);
			cellHead.setCellValue("到今天为止天数");
			cellHead.setCellStyle(styleBold_BlackHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 8);  
			
			cellHead = row8.createCell(5);
			cellHead.setCellValue("交货日期");
			cellHead.setCellStyle(styleBold_BlackHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 12);  
			
			cellHead = row8.createCell(6);
			cellHead.setCellValue("业务");
			cellHead.setCellStyle(styleBold_BlackHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 8);  
			
			cellHead = row8.createCell(7);
			cellHead.setCellValue("跟单");
			cellHead.setCellStyle(styleBold_BlackHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 8);  
			
			cellHead = row8.createCell(8);
			cellHead.setCellValue("客户");
			cellHead.setCellStyle(styleBold_BlackHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 15);  
			
			cellHead = row8.createCell(9);
			cellHead.setCellValue("整机订单");
			cellHead.setCellStyle(styleBold_BlackHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 15);  
			
			cellHead = row8.createCell(10);
			cellHead.setCellValue("物料编码");
			cellHead.setCellStyle(styleBold_BlackHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 18);  
			
			cellHead = row8.createCell(11);
			cellHead.setCellValue("规格");
			cellHead.setCellStyle(styleBold_BlackHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 40);  
			
			cellHead = row8.createCell(12);
			cellHead.setCellValue("批量");
			cellHead.setCellStyle(styleBold_BlackHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 8); 
			
			cellHead = row8.createCell(13);
			cellHead.setCellValue("整机物料齐套状况");
			cellHead.setCellStyle(styleBold_BlackHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 40); 
			
			cellHead = row8.createCell(14);
			cellHead.setCellValue("组装预计上线时间");
			cellHead.setCellStyle(styleBold_BlackHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 12); 
			
			if(materialFollowupDTOs != null ){
				HSSFCellStyle styleBold_Black = wb.createCellStyle();
				styleBold_Black.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
				styleBold_Black.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				styleBold_Black.setWrapText(true);
				styleBold_Black.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
				styleBold_Black.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				styleBold_Black.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
				styleBold_Black.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				styleBold_Black.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
				styleBold_Black.setTopBorderColor(IndexedColors.BLACK.getIndex());
				styleBold_Black.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框    
				styleBold_Black.setRightBorderColor(IndexedColors.BLACK.getIndex());
				HSSFFont fontBold_Black = wb.createFont();
				fontBold_Black.setFontName("Cambria");
				fontBold_Black.setFontHeightInPoints((short) 9);// 设置字体大小
				fontBold_Black.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				styleBold_Black.setFont(fontBold_Black);
				
				HSSFCellStyle style_Black = wb.createCellStyle();
				style_Black.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
				style_Black.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				style_Black.setWrapText(true);
				style_Black.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
				style_Black.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				style_Black.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
				style_Black.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				style_Black.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
				style_Black.setTopBorderColor(IndexedColors.BLACK.getIndex());
				style_Black.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框    
				style_Black.setRightBorderColor(IndexedColors.BLACK.getIndex());
				HSSFFont font_Black = wb.createFont();
				font_Black.setFontName("Cambria");
				font_Black.setFontHeightInPoints((short) 10);// 设置字体大小
				style_Black.setFont(font_Black);
				
				HSSFCellStyle style_Black_Left = wb.createCellStyle();
				style_Black_Left.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 创建一个居中格式
				style_Black_Left.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				style_Black_Left.setWrapText(true);
				style_Black_Left.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
				style_Black_Left.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				style_Black_Left.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
				style_Black_Left.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				style_Black_Left.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
				style_Black_Left.setTopBorderColor(IndexedColors.BLACK.getIndex());
				style_Black_Left.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框    
				style_Black_Left.setRightBorderColor(IndexedColors.BLACK.getIndex());
				style_Black_Left.setFont(font_Black);
				
				
				HSSFCellStyle style_Red = wb.createCellStyle();
				style_Red.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
				style_Red.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				style_Red.setWrapText(true);
				style_Red.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
				style_Red.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				style_Red.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
				style_Red.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				style_Red.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
				style_Red.setTopBorderColor(IndexedColors.BLACK.getIndex());
				style_Red.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框    
				style_Red.setRightBorderColor(IndexedColors.BLACK.getIndex());
				HSSFFont fontBold_Red = wb.createFont();
				fontBold_Red.setFontName("Cambria");
				fontBold_Red.setFontHeightInPoints((short) 10);// 设置字体大小
				fontBold_Red.setColor(HSSFColor.RED.index); //字体颜色
				//fontBold_Red.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
				style_Red.setFont(fontBold_Red);
				
				HSSFCellStyle style_Pink = wb.createCellStyle();
				style_Pink.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
				style_Pink.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				style_Pink.setWrapText(true);
				style_Pink.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
				style_Pink.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				style_Pink.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
				style_Pink.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				style_Pink.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
				style_Pink.setTopBorderColor(IndexedColors.BLACK.getIndex());
				style_Pink.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框    
				style_Pink.setRightBorderColor(IndexedColors.BLACK.getIndex());
				HSSFFont fontContent_Pink = wb.createFont();
				fontContent_Pink.setFontName("Cambria");
				fontContent_Pink.setFontHeightInPoints((short) 10);// 设置字体大小
				fontContent_Pink.setColor(HSSFColor.PINK.index); //字体颜色
				//fontContent_Pink.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
				style_Pink.setFont(fontContent_Pink);
				// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
				HSSFRow row = null ;
				
				
				for (int i = 0; i < materialFollowupDTOs.size(); i++) {
					row = sheet.createRow((int) i + 9);
					// 第四步，创建单元格，并设置值
					 MaterialFollowupDTO materialFollowupDTO = materialFollowupDTOs.get(i);
					 //初始化数据
					 String dateCommit = "";
					 String dateDeliver = "";
					 String dateFinish = "";
					 String dateOnline = "";
					 String dateSubmit = "";
					 Integer dateTo = null;
					 String mtCode = "";
					 String mtCondition = "";
					 String prodLine = "";
					 Integer quality = null;
					// String section = "";
					 String specification = "";
					 
					 String merchandiser = "";
					 String seller = "";
					 String orderNo = "";
					 String clientName = "";
					 
					if(materialFollowupDTO.getProdLine() != null){
						prodLine = materialFollowupDTO.getProdLine();
					}
					HSSFCell cell_prodLine = row.createCell(0);
					cell_prodLine.setCellValue(prodLine);
					cell_prodLine.setCellStyle(style_Black_Left);
					
					if(materialFollowupDTO.getDateFinish() != null){
						dateFinish = materialFollowupDTO.getDateFinish();
					}
					HSSFCell cell_dateFinish = row.createCell(1);
					cell_dateFinish.setCellValue(dateFinish);
					cell_dateFinish.setCellStyle(style_Black);
					
					if(materialFollowupDTO.getDateSubmit() != null){
						dateSubmit = materialFollowupDTO.getDateSubmit();
					}
					HSSFCell cell_dateSubmit = row.createCell(2);
					cell_dateSubmit.setCellValue(dateSubmit);
					cell_dateSubmit.setCellStyle(style_Red);
					
					if(materialFollowupDTO.getDateCommit() != null){
						dateCommit = materialFollowupDTO.getDateCommit();
					}
					HSSFCell cell_dateCommit = row.createCell(3);
					cell_dateCommit.setCellValue(dateCommit);
					cell_dateCommit.setCellStyle(style_Red);
					
					if(materialFollowupDTO.getDateTo() != null){
						dateTo = materialFollowupDTO.getDateTo();
						HSSFCell cell_dateTo = row.createCell(4);
						cell_dateTo.setCellValue(dateTo);
						cell_dateTo.setCellStyle(style_Black);
					}else{
						HSSFCell cell_dateTo = row.createCell(4);
						cell_dateTo.setCellValue("");
						cell_dateTo.setCellStyle(style_Black);
					}
					
					if(materialFollowupDTO.getDateDeliver()!= null){
						dateDeliver = materialFollowupDTO.getDateDeliver();
					}
					HSSFCell cell_dateDeliver = row.createCell(5);
					cell_dateDeliver.setCellValue(dateDeliver);
					cell_dateDeliver.setCellStyle(style_Pink);
					
					if(materialFollowupDTO.getMtCode()!= null){
						mtCode = materialFollowupDTO.getMtCode();
					}
					HSSFCell cell_mtCode = row.createCell(10);
					cell_mtCode.setCellValue(mtCode);
					cell_mtCode.setCellStyle(styleBold_Black);
					
					if(materialFollowupDTO.getSpecification() != null){
						specification = materialFollowupDTO.getSpecification();
					}
					HSSFCell cell_specification = row.createCell(11);
					cell_specification.setCellValue(specification);
					cell_specification.setCellStyle(style_Black_Left);
					
					if(materialFollowupDTO.getQuality() != null){
						quality = materialFollowupDTO.getQuality();
						HSSFCell cell_quality = row.createCell(12);
						cell_quality.setCellValue(quality);
						cell_quality.setCellStyle(style_Black);
					}else{
						HSSFCell cell_quality = row.createCell(12);
						cell_quality.setCellValue("");
						cell_quality.setCellStyle(style_Black);
					}
					
					if(materialFollowupDTO.getMtCondition() != null){
						mtCondition = materialFollowupDTO.getMtCondition();
					}
					HSSFCell cell_mtCondition = row.createCell(13);
					cell_mtCondition.setCellValue(mtCondition);
					cell_mtCondition.setCellStyle(style_Black_Left);
					
					if(materialFollowupDTO.getDateOnline() != null){
						dateOnline = materialFollowupDTO.getDateOnline();
					}
					HSSFCell cell_dateOnline = row.createCell(14);
					cell_dateOnline.setCellValue(dateOnline);
					cell_dateOnline.setCellStyle(style_Black);
						
					if (materialFollowupDTO.getOrderInfoDTO() != null) {
						OrderInfoDTO orderInfoDTO = materialFollowupDTO.getOrderInfoDTO();
						if(orderInfoDTO.getSeller() != null){
							seller = orderInfoDTO.getSeller();
						}
						HSSFCell cell_seller = row.createCell(6);
						cell_seller.setCellValue(seller);
						cell_seller.setCellStyle(style_Black);
						
						if(orderInfoDTO.getMerchandiser() != null){
							merchandiser = orderInfoDTO.getMerchandiser();
						}
						HSSFCell cell_merchandiser = row.createCell(7);
						cell_merchandiser.setCellValue(merchandiser);
						cell_merchandiser.setCellStyle(style_Black);
						
						if(orderInfoDTO.getClientName() != null){
							clientName = orderInfoDTO.getClientName();
						}
						HSSFCell cell_clientName = row.createCell(8);
						cell_clientName.setCellValue(clientName);
						cell_clientName.setCellStyle(styleBold_Black);
						
						if(orderInfoDTO.getOrderNo() != null){
							orderNo = orderInfoDTO.getOrderNo();
						}
						HSSFCell cell_orderNo = row.createCell(9);
						cell_orderNo.setCellValue(orderNo);
						cell_orderNo.setCellStyle(styleBold_Black);
					}
				}
			}
			
			CellRangeAddress c = CellRangeAddress.valueOf("A9:O9");
		    sheet.setAutoFilter(c);
		    sheet.createFreezePane(0,9,0,10); 
			/*try {  
				FileOutputStream fout = new FileOutputStream("F:/test.xls");  
			    wb.write(fout);  
			    fout.close();    
			} catch (Exception e) {  
			       e.printStackTrace();  
			}*/
			return wb;

		}
		return null;
	}
	
	public static void main(String[] args) throws IOException {
		List<MaterialFollowupDTO> materialFollowupDTOs = new ArrayList<MaterialFollowupDTO>();
		for (int i = 0; i < 100; i++) {
			MaterialFollowupDTO materialFollowupDTO =  new MaterialFollowupDTO();
			materialFollowupDTO.setProdLine("上线"+i);
			OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
			orderInfoDTO.setMerchandiser("张三"+i);
			materialFollowupDTO.setOrderInfoDTO(orderInfoDTO);
			materialFollowupDTOs.add(materialFollowupDTO);
		}
		
		
		export(materialFollowupDTOs,"F://materialFollowup.png");
		System.out.println("success");
	}
}
