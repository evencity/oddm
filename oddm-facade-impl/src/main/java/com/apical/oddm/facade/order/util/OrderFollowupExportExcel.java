package com.apical.oddm.facade.order.util;

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
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

import com.apical.oddm.facade.order.dto.OrderFollowupDTO;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;

/**
 * @description 类的描述：
 * @author 作者 : zhuzh
 * @date 创建时间：2016年12月15日 下午5:05:49
 * @version 1.0
 */
public class OrderFollowupExportExcel {

	@SuppressWarnings("deprecation")
	public static HSSFWorkbook export(List<OrderFollowupDTO> orderFollowupDTOs,String picturePath) throws IOException {
		if ( picturePath != null) {
			// 第一步，创建一个webbook，对应一个Excel文件
			HSSFWorkbook wb = new HSSFWorkbook();
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet("订单跟进表");
			
			BufferedImage bufferImg = null;    
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();     
            bufferImg = ImageIO.read(new File(picturePath));     
            ImageIO.write(bufferImg, "png", byteArrayOut); 
            //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）  
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();     
            //anchor主要用于设置图片的属性  
            HSSFClientAnchor anchor = new HSSFClientAnchor(30, 30, 200, 200,(short) 0, 0, (short) 2, 0);     
            anchor.setAnchorType(3);     
            //插入图片    
            patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
            
            
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
            for(int i=0;i<29;i++){
            	sheet.addMergedRegion(new CellRangeAddress(1,2,i,i));
            }
            for(int i=32;i<36;i++){
            	sheet.addMergedRegion(new CellRangeAddress(1,2,i,i));
            }
            sheet.addMergedRegion(new CellRangeAddress(1,1,29,31)); 
			//头
			HSSFCellStyle styleTitle = wb.createCellStyle();
			styleTitle.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 创建一个居中格式
			styleTitle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			
			HSSFFont fontTitle = wb.createFont();
			fontTitle.setFontName("微软雅黑");
			fontTitle.setFontHeightInPoints((short) 13);// 设置字体大小
			fontTitle.setBold(true);
			styleTitle.setFont(fontTitle);
			
			HSSFRow rowTitle = sheet.createRow((int) 0);
			rowTitle.setHeight((short)(32*20));
			HSSFCell title_cellLogo = rowTitle.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,2)); 
			//title_cellLogo.setCellValue("APICAL");
			title_cellLogo.setCellStyle(styleTitle);
			
			HSSFCell title_cellTitle = rowTitle.createCell(3);
			sheet.addMergedRegion(new CellRangeAddress(0,0,3,35)); 
			//title_cellTitle.setCellValue("海外市场部订单跟进表");
			title_cellTitle.setCellStyle(styleTitle);

			//第一行
			HSSFCellStyle styleHead = wb.createCellStyle();
			styleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			styleHead.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			styleHead.setWrapText(true);
			styleHead.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
			styleHead.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			styleHead.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
			styleHead.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			styleHead.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
			styleHead.setTopBorderColor(IndexedColors.BLACK.getIndex());
			styleHead.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框    
			styleHead.setRightBorderColor(IndexedColors.BLACK.getIndex());
			HSSFFont fontHead = wb.createFont();
			fontHead.setFontName("微软雅黑");
			fontHead.setFontHeightInPoints((short) 9);// 设置字体大小
			fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			styleHead.setFont(fontHead);
		
			HSSFCellStyle styleHeadRed = wb.createCellStyle();
			styleHeadRed.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			styleHeadRed.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			styleHeadRed.setWrapText(true);
			styleHeadRed.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
			styleHeadRed.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			styleHeadRed.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
			styleHeadRed.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			styleHeadRed.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
			styleHeadRed.setTopBorderColor(IndexedColors.BLACK.getIndex());
			styleHeadRed.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框    
			styleHeadRed.setRightBorderColor(IndexedColors.BLACK.getIndex());
			HSSFFont fontHeadRed = wb.createFont();
			fontHeadRed.setFontName("微软雅黑");
			fontHeadRed.setFontHeightInPoints((short) 9);// 设置字体大小
			fontHeadRed.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			fontHeadRed.setColor(HSSFColor.RED.index); //字体颜色
			styleHeadRed.setFont(fontHeadRed);
			
			HSSFRow rowHead = sheet.createRow((int) 1);
			rowHead.setHeight((short) (29*20));
			
			HSSFCell cellHead = rowHead.createCell(0);
			cellHead.setCellValue("客户代码");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 5);  
			
			cellHead = rowHead.createCell(1);
			cellHead.setCellValue("订单号码");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 12);  
			
			cellHead = rowHead.createCell(2);
			cellHead.setCellValue("销售员");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 7);  
			
			cellHead = rowHead.createCell(3);
			cellHead.setCellValue("跟单员");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 7);  
			
			cellHead = rowHead.createCell(4);
			cellHead.setCellValue("产品型号");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 12);  
			
			cellHead = rowHead.createCell(5);
			cellHead.setCellValue("客户型号");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 12);  
			
			cellHead = rowHead.createCell(6);
			cellHead.setCellValue("方案");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 6);  
			
			cellHead = rowHead.createCell(7);
			cellHead.setCellValue("订单数量/pcs");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 7);  
			
			cellHead = rowHead.createCell(8);
			cellHead.setCellValue("开机画面");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 5);  
			
			cellHead = rowHead.createCell(9);
			cellHead.setCellValue("地图");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 5);  
			
			cellHead = rowHead.createCell(10);
			cellHead.setCellValue("升级软件");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 5);  
			
			cellHead = rowHead.createCell(11);
			cellHead.setCellValue("预存文件");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 5);  
			
			cellHead = rowHead.createCell(12);
			cellHead.setCellValue("UUID/其他");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 5);  
			
			cellHead = rowHead.createCell(13);
			cellHead.setCellValue("机身外壳");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 5);  
			
			cellHead = rowHead.createCell(14);
			cellHead.setCellValue("机身硬件");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 5);  
			
			cellHead = rowHead.createCell(15);
			cellHead.setCellValue("贴纸类");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 5);  
			
			cellHead = rowHead.createCell(16);
			cellHead.setCellValue("说明书类/卡类");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 5);  
			
			cellHead = rowHead.createCell(17);
			cellHead.setCellValue("贴膜类");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 5);  
			
			cellHead = rowHead.createCell(18);
			cellHead.setCellValue("彩盒");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 4);  
			
			cellHead = rowHead.createCell(19);
			cellHead.setCellValue("包装袋");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 5);  
			
			cellHead = rowHead.createCell(20);
			cellHead.setCellValue("卡通箱");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 5);  
			
			cellHead = rowHead.createCell(21);
			cellHead.setCellValue("配件");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 5);  
			
			cellHead = rowHead.createCell(22);
			cellHead.setCellValue("验货");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 5);  
			
			cellHead = rowHead.createCell(23);
			cellHead.setCellValue("货代");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 5);  
			
			cellHead = rowHead.createCell(24);
			cellHead.setCellValue("付款");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 5);  
			
			cellHead = rowHead.createCell(25);
			cellHead.setCellValue("客户交期");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 15);  
			
			cellHead = rowHead.createCell(26);
			cellHead.setCellValue("计划回复交期");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 15);  
			
			cellHead = rowHead.createCell(27);
			cellHead.setCellValue("正常状态更新");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 25);  
			
			cellHead = rowHead.createCell(28);
			cellHead.setCellValue("异常状态记录");
			cellHead.setCellStyle(styleHeadRed);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 26); 
			
			cellHead = rowHead.createCell(29);
			cellHead.setCellValue("异常等级区分");
			cellHead.setCellStyle(styleHeadRed);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 20);
			
			HSSFRow rowHead2 = sheet.createRow((int) 2);
			//设置边框
			for(int i=0;i<29;i++){
				cellHead = rowHead2.createCell(i);
				cellHead.setCellStyle(styleHeadRed);
            }
			//设置边框
            for(int i=32;i<36;i++){
            	cellHead = rowHead2.createCell(i);
				cellHead.setCellStyle(styleHeadRed);
            }
			cellHead = rowHead2.createCell(29);
			cellHead.setCellValue("非常严重");
			cellHead.setCellStyle(styleHeadRed);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 8);
			cellHead = rowHead2.createCell(30);
			cellHead.setCellValue("严重");
			cellHead.setCellStyle(styleHeadRed);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 6);
			cellHead = rowHead2.createCell(31);
			cellHead.setCellValue("一般");
			cellHead.setCellStyle(styleHeadRed);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 6);
			
			cellHead = rowHead.createCell(30);//设置边框
			cellHead.setCellStyle(styleHeadRed);
			cellHead = rowHead.createCell(31);
			cellHead.setCellStyle(styleHeadRed);
			
			cellHead = rowHead.createCell(32);
			cellHead.setCellValue("导常问题之责任部门或责任人");
			cellHead.setCellStyle(styleHeadRed);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 13);
			
			cellHead = rowHead.createCell(33);
			cellHead.setCellValue("是否定出解决方案");
			cellHead.setCellStyle(styleHeadRed);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 20);
			
			cellHead = rowHead.createCell(34);
			cellHead.setCellValue("出货日期和数量");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 16); 
			
			cellHead = rowHead.createCell(35);
			cellHead.setCellValue("备注");
			cellHead.setCellStyle(styleHead);
			sheet.setColumnWidth(cellHead.getColumnIndex(), 256 * 26);
			

			if(orderFollowupDTOs != null ){
				// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
				HSSFRow row = null ;
				
				HSSFCellStyle styleContent_Black = wb.createCellStyle();
				styleContent_Black.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
				styleContent_Black.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				styleContent_Black.setWrapText(true);
				styleContent_Black.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
				styleContent_Black.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				styleContent_Black.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
				styleContent_Black.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				styleContent_Black.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
				styleContent_Black.setTopBorderColor(IndexedColors.BLACK.getIndex());
				styleContent_Black.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框    
				styleContent_Black.setRightBorderColor(IndexedColors.BLACK.getIndex());
				
				
				HSSFCellStyle styleContent_Red = wb.createCellStyle();
				styleContent_Red.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
				styleContent_Red.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				styleContent_Red.setWrapText(true);
				styleContent_Red.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
				styleContent_Red.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				styleContent_Red.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
				styleContent_Red.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				styleContent_Red.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
				styleContent_Red.setTopBorderColor(IndexedColors.BLACK.getIndex());
				styleContent_Red.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框    
				styleContent_Red.setRightBorderColor(IndexedColors.BLACK.getIndex());
				
				HSSFCellStyle styleContent_Num = wb.createCellStyle();
				styleContent_Num.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));  
				styleContent_Num.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
				styleContent_Num.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				styleContent_Num.setWrapText(true);
				styleContent_Num.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
				styleContent_Num.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				styleContent_Num.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
				styleContent_Num.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				styleContent_Num.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
				styleContent_Num.setTopBorderColor(IndexedColors.BLACK.getIndex());
				styleContent_Num.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框    
				styleContent_Num.setRightBorderColor(IndexedColors.BLACK.getIndex());
				
				HSSFFont fontContent_Black = wb.createFont();
				fontContent_Black.setFontName("微软雅黑");
				fontContent_Black.setFontHeightInPoints((short) 9);// 设置字体大小
				fontContent_Black.setColor(HSSFColor.BLACK.index); //字体颜色
				//fontContent_Black.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
				styleContent_Black.setFont(fontContent_Black);
				
				HSSFFont fontContent_Red = wb.createFont();
				fontContent_Red.setFontName("微软雅黑");
				fontContent_Red.setFontHeightInPoints((short) 9);// 设置字体大小
				fontContent_Red.setColor(HSSFColor.RED.index); //字体颜色
				fontContent_Red.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
				styleContent_Red.setFont(fontContent_Red);
				styleContent_Num.setFont(fontContent_Black);
				
				for (int i = 0; i < orderFollowupDTOs.size(); i++) {
					
					row = sheet.createRow((int) i + 3);
					//row.setHeight((short) 400);
					OrderFollowupDTO orderFollowupDTO =  orderFollowupDTOs.get(i);
					// 第四步，创建单元格，并设置值
					//初始化数据
					String clientName = "";
					String orderNo = "";
					String seller = "";
					String merchandiser = "";
					String productClient = "";
					String productFactory = "";
					Integer quantity = 0;

					String agency = "";
					String bootLogo = "";
					String carton = "";
					String colorbox = "";
					String dateClient = "";
					String status = "";
					String fitting = "";
					String hardware = "";
					String inspection = "";
					String map = "";
					String membrane = "";
					String packing = "";
					String payment = "";
					String plan = "";
					String preFile = "";
					String shell = "";
					String sorfware = "";
					String specification = "";
					String tags = "";
					String uuid = "";
					String dateFactory = "";
					/*String dateShipment = "";*/
					String description = "";
					//String shipmentNo = "";
			/*		Integer shipmentTotal = null;*/
					
					if (orderFollowupDTO.getOrderInfoDTO() != null) {
						
						OrderInfoDTO orderInfoDTO = orderFollowupDTO.getOrderInfoDTO();
						if(orderInfoDTO.getClientNameCode() != null){
							clientName = orderInfoDTO.getClientNameCode();
						}
						HSSFCell cell_clientName = row.createCell(0);
						cell_clientName.setCellValue(clientName);
						cell_clientName.setCellStyle(styleContent_Black);
						
						if(orderInfoDTO.getOrderNo() != null){
							orderNo = orderInfoDTO.getOrderNo();
						}
						HSSFCell cell_orderNo = row.createCell(1);
						cell_orderNo.setCellValue(orderNo);
						cell_orderNo.setCellStyle(styleContent_Black);
						
						if(orderInfoDTO.getSeller() != null){
							seller = orderInfoDTO.getSeller();
						}
						HSSFCell cell_seller = row.createCell(2);
						cell_seller.setCellValue(seller);
						cell_seller.setCellStyle(styleContent_Black);
						
						if(orderInfoDTO.getMerchandiser() != null){
							merchandiser = orderInfoDTO.getMerchandiser();
						}
						HSSFCell cell_merchandiser = row.createCell(3);
						cell_merchandiser.setCellValue(merchandiser);
						cell_merchandiser.setCellStyle(styleContent_Black);
						
					
						if(orderInfoDTO.getProductFactory() != null){
							productFactory = orderInfoDTO.getProductFactory();
						}
						HSSFCell cell_productFactory = row.createCell(4);
						cell_productFactory.setCellValue(productFactory);
						cell_productFactory.setCellStyle(styleContent_Black);
						
						if(orderInfoDTO.getProductClient() != null){
							productClient = orderInfoDTO.getProductClient();
						}
						HSSFCell cell_productClient = row.createCell(5);
						cell_productClient.setCellValue(productClient);
						cell_productClient.setCellStyle(styleContent_Black);
						
						if(orderInfoDTO.getQuantity() != null)
							quantity = orderInfoDTO.getQuantity();
						HSSFCell cell_quantity = row.createCell(7);
						cell_quantity.setCellValue(quantity);
						cell_quantity.setCellStyle(styleContent_Num);
					}
					if(orderFollowupDTO.getPlan() != null){
						plan = orderFollowupDTO.getPlan();
					}
					HSSFCell cell_plan = row.createCell(6);
					cell_plan.setCellValue(plan);
					cell_plan.setCellStyle(styleContent_Black);
					
					HSSFCell cell_bootLogo = row.createCell(8);
					if(orderFollowupDTO.getBootLogo() != null){
						if(orderFollowupDTO.getBootLogo() == 1){
							bootLogo = "N/A";
							cell_bootLogo.setCellStyle(styleContent_Black);
						}
						if(orderFollowupDTO.getBootLogo() == 2){
							bootLogo = "NO";
							cell_bootLogo.setCellStyle(styleContent_Red);
						}
						if(orderFollowupDTO.getBootLogo() == 3){
							bootLogo = "OK";
							cell_bootLogo.setCellStyle(styleContent_Black);
						}
						cell_bootLogo.setCellValue(bootLogo);
					}else{
						cell_bootLogo.setCellStyle(styleContent_Black);
					}
					
					HSSFCell cell_map = row.createCell(9);
					if(orderFollowupDTO.getMap() != null){
						if(orderFollowupDTO.getMap() == 1){
							map = "N/A";
							cell_map.setCellStyle(styleContent_Black);
						}
						if(orderFollowupDTO.getMap() == 2){
							map = "NO";
							cell_map.setCellStyle(styleContent_Red);
						}
						if(orderFollowupDTO.getMap() == 3){
							map = "OK";
							cell_map.setCellStyle(styleContent_Black);
						}
						cell_map.setCellValue(map);
					}else{
						cell_map.setCellStyle(styleContent_Black);
					}
					HSSFCell cell_sorfware = row.createCell(10);
					if(orderFollowupDTO.getSorfware() != null){
						if(orderFollowupDTO.getSorfware() == 1){
							sorfware = "N/A";
							cell_sorfware.setCellStyle(styleContent_Black);
						}
						if(orderFollowupDTO.getSorfware() == 2){
							sorfware = "NO";
							cell_sorfware.setCellStyle(styleContent_Red);
						}
						if(orderFollowupDTO.getSorfware() == 3){
							sorfware = "OK";
							cell_sorfware.setCellStyle(styleContent_Black);
						}
						cell_sorfware.setCellValue(sorfware);
					}else{
						cell_sorfware.setCellStyle(styleContent_Black);
					}
					HSSFCell cell_preFile = row.createCell(11);
					if(orderFollowupDTO.getPreFile() != null){
						if(orderFollowupDTO.getPreFile() == 1){
							preFile = "N/A";
							cell_preFile.setCellStyle(styleContent_Black);
						}
						if(orderFollowupDTO.getPreFile() == 2){
							preFile = "NO";
							cell_preFile.setCellStyle(styleContent_Red);
						}
						if(orderFollowupDTO.getPreFile() == 3){
							preFile = "OK";
							cell_preFile.setCellStyle(styleContent_Black);
						}
						cell_preFile.setCellValue(preFile);
					}else{
						cell_preFile.setCellStyle(styleContent_Black);
					}
					HSSFCell cell_uuid = row.createCell(12);
					if(orderFollowupDTO.getUuid() != null){
						if(orderFollowupDTO.getUuid() == 1){
							uuid = "N/A";
							cell_uuid.setCellStyle(styleContent_Black);
						}
						if(orderFollowupDTO.getUuid() == 2){
							uuid = "NO";
							cell_uuid.setCellStyle(styleContent_Red);
						}
						if(orderFollowupDTO.getUuid() == 3){
							uuid = "OK";
							cell_uuid.setCellStyle(styleContent_Black);
						}
						cell_uuid.setCellValue(uuid);
					}else{
						cell_uuid.setCellStyle(styleContent_Black);
					}
					HSSFCell cell_shell = row.createCell(13);
					if(orderFollowupDTO.getShell() != null){
						if(orderFollowupDTO.getShell() == 1){
							shell = "N/A";
							cell_shell.setCellStyle(styleContent_Black);
						}
						if(orderFollowupDTO.getShell() == 2){
							shell = "NO";
							cell_shell.setCellStyle(styleContent_Red);
						}
						if(orderFollowupDTO.getShell() == 3){
							shell = "OK";
							cell_shell.setCellStyle(styleContent_Black);
						}
						cell_shell.setCellValue(shell);
					}else{
						cell_shell.setCellStyle(styleContent_Black);
					}
					HSSFCell cell_hardware = row.createCell(14);
					if(orderFollowupDTO.getHardware() != null){
						if(orderFollowupDTO.getHardware() == 1){
							hardware = "N/A";
							cell_hardware.setCellStyle(styleContent_Black);
						}
						if(orderFollowupDTO.getHardware() == 2){
							hardware = "NO";
							cell_hardware.setCellStyle(styleContent_Red);
						}
						if(orderFollowupDTO.getHardware() == 3){
							hardware = "OK";
							cell_hardware.setCellStyle(styleContent_Black);
						}
						cell_hardware.setCellValue(hardware);
					}else{
						cell_hardware.setCellStyle(styleContent_Black);
					}
					HSSFCell cell_tags = row.createCell(15);
					if(orderFollowupDTO.getTags() != null){
						if(orderFollowupDTO.getTags() == 1){
							tags = "N/A";
							cell_tags.setCellStyle(styleContent_Black);
						}
						if(orderFollowupDTO.getTags() == 2){
							tags = "NO";
							cell_tags.setCellStyle(styleContent_Red);
						}
						if(orderFollowupDTO.getTags() == 3){
							tags = "OK";
							cell_tags.setCellStyle(styleContent_Black);
						}
						cell_tags.setCellValue(tags);
					}else{
						cell_tags.setCellStyle(styleContent_Black);
					}
					HSSFCell cell_specification = row.createCell(16);
					if(orderFollowupDTO.getSpecification() != null){
						if(orderFollowupDTO.getSpecification() == 1){
							specification = "N/A";
							cell_specification.setCellStyle(styleContent_Black);
						}
						if(orderFollowupDTO.getSpecification() == 2){
							specification = "NO";
							cell_specification.setCellStyle(styleContent_Red);
						}
						if(orderFollowupDTO.getSpecification() == 3){
							specification = "OK";
							cell_specification.setCellStyle(styleContent_Black);
						}
						cell_specification.setCellValue(specification);
					}else{
						cell_specification.setCellStyle(styleContent_Black);
					}
					HSSFCell cell_membrane = row.createCell(17);
					if(orderFollowupDTO.getMembrane() != null){
						if(orderFollowupDTO.getMembrane() == 1){
							membrane = "N/A";
							cell_membrane.setCellStyle(styleContent_Black);
						}
						if(orderFollowupDTO.getMembrane() == 2){
							membrane = "NO";
							cell_membrane.setCellStyle(styleContent_Red);
						}
						if(orderFollowupDTO.getMembrane() == 3){
							membrane = "OK";
							cell_membrane.setCellStyle(styleContent_Black);
						}
						cell_membrane.setCellValue(membrane);
					}else{
						cell_membrane.setCellStyle(styleContent_Black);
					}
					HSSFCell cell_colorbox = row.createCell(18);
					if(orderFollowupDTO.getColorbox() != null){
						if(orderFollowupDTO.getColorbox() == 1){
							colorbox = "N/A";
							cell_colorbox.setCellStyle(styleContent_Black);
						}
						if(orderFollowupDTO.getColorbox() == 2){
							colorbox = "NO";
							cell_colorbox.setCellStyle(styleContent_Red);
						}
						if(orderFollowupDTO.getColorbox() == 3){
							colorbox = "OK";
							cell_colorbox.setCellStyle(styleContent_Black);
						}
						cell_colorbox.setCellValue(colorbox);
					}else{
						cell_colorbox.setCellStyle(styleContent_Black);
					}
					HSSFCell cell_packing = row.createCell(19);
					if(orderFollowupDTO.getPacking() != null){
						if(orderFollowupDTO.getPacking() == 1){
							packing = "N/A";
							cell_packing.setCellStyle(styleContent_Black);
						}
						if(orderFollowupDTO.getPacking() == 2){
							packing = "NO";
							cell_packing.setCellStyle(styleContent_Red);
						}
						if(orderFollowupDTO.getPacking() == 3){
							packing = "OK";
							cell_packing.setCellStyle(styleContent_Black);
						}
						cell_packing.setCellValue(packing);
					}else{
						cell_packing.setCellStyle(styleContent_Black);
					}
					HSSFCell cell_carton = row.createCell(20);
					if(orderFollowupDTO.getCarton() != null){
						if(orderFollowupDTO.getCarton() == 1){
							carton = "N/A";
							cell_carton.setCellStyle(styleContent_Black);
						}
						if(orderFollowupDTO.getCarton() == 2){
							carton = "NO";
							cell_carton.setCellStyle(styleContent_Red);
						}
						if(orderFollowupDTO.getCarton() == 3){
							carton = "OK";
							cell_carton.setCellStyle(styleContent_Black);
						}
						cell_carton.setCellValue(carton);
					}else{
						cell_carton.setCellStyle(styleContent_Black);
					}
					HSSFCell cell_fitting = row.createCell(21);
					if(orderFollowupDTO.getFitting() != null){
						if(orderFollowupDTO.getFitting() == 1){
							fitting = "N/A";
							cell_fitting.setCellStyle(styleContent_Black);
						}
						if(orderFollowupDTO.getFitting() == 2){
							fitting = "NO";
							cell_fitting.setCellStyle(styleContent_Red);
						}
						if(orderFollowupDTO.getFitting() == 3){
							fitting = "OK";
							cell_fitting.setCellStyle(styleContent_Black);
						}
						cell_fitting.setCellValue(fitting);
					}else{
						cell_fitting.setCellStyle(styleContent_Black);
					}
					HSSFCell cell_inspection = row.createCell(22);
					if(orderFollowupDTO.getInspection() != null){
						if(orderFollowupDTO.getInspection() == 1){
							inspection = "N/A";
							cell_inspection.setCellStyle(styleContent_Black);
						}
						if(orderFollowupDTO.getInspection() == 2){
							inspection = "NO";
							cell_inspection.setCellStyle(styleContent_Red);
						}
						if(orderFollowupDTO.getInspection() == 3){
							inspection = "OK";
							cell_inspection.setCellStyle(styleContent_Black);
						}
						cell_inspection.setCellValue(inspection);
					}else{
						cell_inspection.setCellStyle(styleContent_Black);
					}
					HSSFCell cell_agency = row.createCell(23);
					if(orderFollowupDTO.getAgency() != null){
						if(orderFollowupDTO.getAgency() == 1){
							agency = "N/A";
							cell_agency.setCellStyle(styleContent_Black);
						}
						if(orderFollowupDTO.getAgency() == 2){
							agency = "NO";
							cell_agency.setCellStyle(styleContent_Red);
						}
						if(orderFollowupDTO.getAgency() == 3){
							agency = "OK";
							cell_agency.setCellStyle(styleContent_Black);
						}
						cell_agency.setCellValue(agency);
					}else{
						cell_agency.setCellStyle(styleContent_Black);
					}
					HSSFCell cell_payment = row.createCell(24);
					if(orderFollowupDTO.getPayment() != null){
						if(orderFollowupDTO.getPayment() == 1){
							payment = "N/A";
							cell_payment.setCellStyle(styleContent_Black);
						}
						if(orderFollowupDTO.getPayment() == 2){
							payment = "NO";
							cell_payment.setCellStyle(styleContent_Red);
						}
						if(orderFollowupDTO.getPayment() == 3){
							payment = "OK";
							cell_payment.setCellStyle(styleContent_Black);
						}
						cell_payment.setCellValue(payment);
					}else{
						cell_payment.setCellStyle(styleContent_Black);
					}
					if(orderFollowupDTO.getDateClient() != null){
						dateClient = orderFollowupDTO.getDateClient();
					}
					HSSFCell cell_dateClient = row.createCell(25);
					cell_dateClient.setCellStyle(styleContent_Black);
					cell_dateClient.setCellValue(dateClient);
					
					if(orderFollowupDTO.getDateFactory() != null){
						dateFactory = orderFollowupDTO.getDateFactory();
					}
					HSSFCell cell_dateFactory = row.createCell(26);
					cell_dateFactory.setCellStyle(styleContent_Black);
					cell_dateFactory.setCellValue(dateFactory);
					
					if(orderFollowupDTO.getStatus() != null){
						status = orderFollowupDTO.getStatus();
					}
					HSSFCell cell_status = row.createCell(27);
					cell_status.setCellStyle(styleContent_Black);
					cell_status.setCellValue(status);
					
					String abnormal = "";
					if(orderFollowupDTO.getStatusAbnormal() != null){
						abnormal = orderFollowupDTO.getStatusAbnormal();
					}
					HSSFCell cell_abnormal = row.createCell(28);
					cell_abnormal.setCellStyle(styleContent_Red);
					cell_abnormal.setCellValue(abnormal);
					
					HSSFCell cell_level29 = row.createCell(29);
					HSSFCell cell_level30 = row.createCell(30);
					HSSFCell cell_level31 = row.createCell(31);
					if(orderFollowupDTO.getLevel() != null){
						if("3".equals(orderFollowupDTO.getLevel())){
							cell_level29.setCellValue("√");
						}
						if("2".equals(orderFollowupDTO.getLevel())){
							cell_level30.setCellValue("√");
						}
						if("1".equals(orderFollowupDTO.getLevel())){
							cell_level31.setCellValue("√");
						}
					}
					cell_level29.setCellStyle(styleContent_Black);
					cell_level30.setCellStyle(styleContent_Black);
					cell_level31.setCellStyle(styleContent_Black);
					
					
					HSSFCell cell_DutuOfficer = row.createCell(32);
					if(orderFollowupDTO.getDutyOfficer() != null){
						cell_DutuOfficer.setCellValue(orderFollowupDTO.getDutyOfficer());
					}
					cell_DutuOfficer.setCellStyle(styleContent_Red);
					
					HSSFCell cell_solution = row.createCell(33);
					if(orderFollowupDTO.getSolution() != null){
						cell_solution.setCellValue(orderFollowupDTO.getSolution());
					}
					cell_solution.setCellStyle(styleContent_Red);
					
					HSSFCell cell_shipment = row.createCell(34);
					if(orderFollowupDTO.getShipment() != null){
						cell_shipment.setCellValue(orderFollowupDTO.getShipment());
					}
					cell_shipment.setCellStyle(styleContent_Black);
					
					
					if(orderFollowupDTO.getDescription() != null){
						description = orderFollowupDTO.getDescription();
					}
					HSSFCell cell_description = row.createCell(35);
					cell_description.setCellStyle(styleContent_Black);
					cell_description.setCellValue(description);
					
				}
			}
			
			CellRangeAddress c = CellRangeAddress.valueOf("A3:AJ3");
		    sheet.setAutoFilter(c);
		    sheet.createFreezePane(13,3,13,3); //要冻结列数/行数  可见区域列数/行数
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
/*	public static void main(String[] args) {
		List<OrderFollowupDTO> orderFollowupDTOs = new ArrayList<OrderFollowupDTO>();
		for(int i = 0; i < 100; i++ ){
			OrderFollowupDTO orderFollowupDTO = new OrderFollowupDTO();
			orderFollowupDTO.setDateClient("xxxx"+i);
			OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
			orderInfoDTO.setClientNameCode("张三"+i);
			orderFollowupDTO.setOrderInfoDTO(orderInfoDTO);
			orderFollowupDTOs.add(orderFollowupDTO);
		}
		
		try {
			export(orderFollowupDTOs,"F://orderFollowLogo.png");
			System.out.println("success");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}



