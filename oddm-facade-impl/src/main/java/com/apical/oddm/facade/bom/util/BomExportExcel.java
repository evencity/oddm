package com.apical.oddm.facade.bom.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import com.apical.oddm.facade.bom.dto.BomDTO;
import com.apical.oddm.facade.bom.dto.BomMaterialContactDTO;
import com.apical.oddm.facade.bom.dto.BomMaterialRefDTO;
import com.apical.oddm.facade.util.FractionalUtil;

/**
 * 导出Excel
 * @author zzh
 */
public class BomExportExcel {

	public static HSSFWorkbook export(BomDTO bomDTO) throws IOException {
		if (bomDTO != null) {
			// 第一步，创建一个webbook，对应一个Excel文件
			HSSFWorkbook wb = new HSSFWorkbook();
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheetky
			HSSFSheet sheet = wb.createSheet();//多次create可以追加， 删除removeSheetAt
			
			sheet.setDefaultRowHeightInPoints(21f);//默认行高 ，默认列宽setDefaultColumnWidth  8.43
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
          
            //设置列宽， 设置行高setHeight
			double offset = 0.72;
			//sheet.autoSizeColumn((short)0);//自动宽度（但是单元格自动换行的时候，只适应最长的那部分字）
            //sheet.setColumnWidth(0,(int) ((length+offset) * 256));//0.72为偏移值（具体没深究，网上给的数据）
            sheet.setColumnWidth(0,(int) ((5.13+offset) * 256));//序号
            sheet.setColumnWidth(1,(int) ((26.5+offset) * 256));//物料编码
            sheet.setColumnWidth(2,(int) ((24.25+offset) * 256));//品  名
            sheet.setColumnWidth(3,(int) ((44+offset) * 256));//规格型号及封装
            sheet.setColumnWidth(4,(int) ((6.88+offset) * 256));//品牌
            sheet.setColumnWidth(5,(int) ((11+offset) * 256));//用量
            sheet.setColumnWidth(6,(int) ((37+offset) * 256));//描述
            sheet.setColumnWidth(7,(int) ((13.75+offset) * 256));//厂家
            sheet.setColumnWidth(8,(int) ((7.13+offset) * 256));//联系人
            sheet.setColumnWidth(9,(int) ((14+offset) * 256));//电话
            sheet.setColumnWidth(10,(int) ((12.5+offset) * 256));//传真
            sheet.setColumnWidth(11,(int) ((12.63+offset) * 256));//手机
            
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,2));//APICAL 343J-5002A 物料清单
            sheet.addMergedRegion(new CellRangeAddress(1,1,0,2));//制表:TSH  日期:2015-9-1 版本号:01
            
            HSSFRow row0 = sheet.createRow(0); row0.setHeightInPoints(33f);
            HSSFRow row1 = sheet.createRow(1); row1.setHeightInPoints(24f);
            HSSFRow row2 = sheet.createRow(2); row2.setHeightInPoints(27.75f);
            HSSFRow row3 = sheet.createRow(3); row3.setHeightInPoints(30f);
            HSSFRow row4 = sheet.createRow(4); row4.setHeightInPoints(26.25f);
            HSSFRow row5 = sheet.createRow(5); row5.setHeightInPoints(26.25f);
            
            /***********大标题**************/
            HSSFCellStyle styleTitle = wb.createCellStyle();// CellStyle 
			styleTitle.setAlignment(HorizontalAlignment.LEFT);// 创建一个居中格式
			styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
			styleTitle.setBorderBottom(BorderStyle.THIN);
			styleTitle.setBorderTop(BorderStyle.THIN);
			styleTitle.setBorderLeft(BorderStyle.THIN);
			styleTitle.setBorderRight(BorderStyle.THIN);
			styleTitle.setWrapText(true);
			
			HSSFFont fontTitle = wb.createFont();
			fontTitle.setFontName("微软雅黑");
			fontTitle.setFontHeightInPoints((short) 14);// 设置字体大小
			fontTitle.setBold(true);
			styleTitle.setFont(fontTitle);
			
			
			/***********排序标题**************/
            HSSFCellStyle styleTitle1 = wb.createCellStyle();// CellStyle 
			styleTitle1.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
			styleTitle1.setVerticalAlignment(VerticalAlignment.CENTER);
			styleTitle1.setBorderBottom(BorderStyle.THIN);
			styleTitle1.setBorderTop(BorderStyle.THIN);
			styleTitle1.setBorderLeft(BorderStyle.THIN);
			styleTitle1.setBorderRight(BorderStyle.THIN);
			styleTitle1.setWrapText(true);
			
			HSSFFont fontTitle1 = wb.createFont();
			fontTitle1.setFontName("微软雅黑");
			fontTitle1.setFontHeightInPoints((short) 11);// 设置字体大小
			fontTitle1.setBold(true);
			styleTitle1.setFont(fontTitle1);
			
			HSSFCellStyle styleTitle1Left = wb.createCellStyle();// CellStyle 
			styleTitle1Left.setAlignment(HorizontalAlignment.LEFT);// 创建一个居中格式
			styleTitle1Left.setVerticalAlignment(VerticalAlignment.CENTER);
			styleTitle1Left.setBorderBottom(BorderStyle.THIN);
			styleTitle1Left.setBorderTop(BorderStyle.THIN);
			styleTitle1Left.setBorderLeft(BorderStyle.THIN);
			styleTitle1Left.setBorderRight(BorderStyle.THIN);
			styleTitle1Left.setWrapText(true);
			styleTitle1Left.setFont(fontTitle1);
			
			
			HSSFCellStyle styleTitleNoBorder = wb.createCellStyle();// CellStyle 
			styleTitleNoBorder.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
			styleTitleNoBorder.setVerticalAlignment(VerticalAlignment.CENTER);
			styleTitleNoBorder.setBorderBottom(BorderStyle.NONE);
			styleTitleNoBorder.setBorderTop(BorderStyle.NONE);
			styleTitleNoBorder.setBorderLeft(BorderStyle.NONE);
			styleTitleNoBorder.setBorderRight(BorderStyle.NONE);
			styleTitleNoBorder.setWrapText(true);
			styleTitleNoBorder.setFont(fontTitle1);
			
			/***********内容标题  -- 粉红 **************/
            HSSFCellStyle styleContent_pink = wb.createCellStyle();// CellStyle 
            styleContent_pink.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
            styleContent_pink.setVerticalAlignment(VerticalAlignment.CENTER);
            styleContent_pink.setBorderBottom(BorderStyle.THIN);
            styleContent_pink.setBorderTop(BorderStyle.THIN);
            styleContent_pink.setBorderLeft(BorderStyle.THIN);
            styleContent_pink.setBorderRight(BorderStyle.THIN);
            styleContent_pink.setFillForegroundColor(IndexedColors.ROSE.getIndex());
            styleContent_pink.setFillPattern(CellStyle.SOLID_FOREGROUND);
            styleContent_pink.setWrapText(true);
			
			HSSFFont fontContent_pink = wb.createFont();
			fontContent_pink.setFontName("微软雅黑");
			fontContent_pink.setFontHeightInPoints((short) 10);// 设置字体大小
			fontContent_pink.setBold(true);
			styleContent_pink.setFont(fontContent_pink);
			
			HSSFCellStyle styleContent_pinkLeft = wb.createCellStyle();// CellStyle 
			styleContent_pinkLeft.setAlignment(HorizontalAlignment.LEFT);// 创建一个居中格式
			styleContent_pinkLeft.setVerticalAlignment(VerticalAlignment.CENTER);
			styleContent_pinkLeft.setBorderBottom(BorderStyle.THIN);
			styleContent_pinkLeft.setBorderTop(BorderStyle.THIN);
			styleContent_pinkLeft.setBorderLeft(BorderStyle.THIN);
			styleContent_pinkLeft.setBorderRight(BorderStyle.THIN);
			styleContent_pinkLeft.setFillForegroundColor(IndexedColors.ROSE.getIndex());
			styleContent_pinkLeft.setFillPattern(CellStyle.SOLID_FOREGROUND);
			styleContent_pinkLeft.setWrapText(true);
			
            styleContent_pinkLeft.setFont(fontContent_pink);
			
			/***********内容标题  -- 蓝色 **************/
            HSSFCellStyle styleContent_blue = wb.createCellStyle();// CellStyle 
            styleContent_blue.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
            styleContent_blue.setVerticalAlignment(VerticalAlignment.CENTER);
            styleContent_blue.setBorderBottom(BorderStyle.THIN);
            styleContent_blue.setBorderTop(BorderStyle.THIN);
            styleContent_blue.setBorderLeft(BorderStyle.THIN);
            styleContent_blue.setBorderRight(BorderStyle.THIN);
            styleContent_blue.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
            styleContent_blue.setFillPattern(CellStyle.SOLID_FOREGROUND);
            styleContent_blue.setWrapText(true);
			
			HSSFFont fontContent_blue = wb.createFont();
			fontContent_blue.setFontName("微软雅黑");
			fontContent_blue.setFontHeightInPoints((short) 10);// 设置字体大小
			fontContent_blue.setBold(true);
			styleContent_blue.setFont(fontContent_blue);
			
			HSSFCellStyle styleContent_blueLeft = wb.createCellStyle();// CellStyle 
			styleContent_blueLeft.setAlignment(HorizontalAlignment.LEFT);// 创建一个居中格式
			styleContent_blueLeft.setVerticalAlignment(VerticalAlignment.CENTER);
			styleContent_blueLeft.setBorderBottom(BorderStyle.THIN);
			styleContent_blueLeft.setBorderTop(BorderStyle.THIN);
			styleContent_blueLeft.setBorderLeft(BorderStyle.THIN);
			styleContent_blueLeft.setBorderRight(BorderStyle.THIN);
			styleContent_blueLeft.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
			styleContent_blueLeft.setFillPattern(CellStyle.SOLID_FOREGROUND);
			styleContent_blueLeft.setWrapText(true);
			styleContent_blueLeft.setFont(fontContent_blue);
			
			HSSFPalette palette = wb.getCustomPalette();
			//这个是重点，具体的就是把之前的颜色 HSSFColor.LIME.index
			//替换为  RGB(51,204,204) 宝石蓝这种颜色
			//你可以改为 RGB(0,255,127)
			palette.setColorAtIndex(HSSFColor.SKY_BLUE.index, (byte) 153, (byte) 204, (byte) 255);
			
			/***********内容正文**************/
            HSSFCellStyle styleContent1 = wb.createCellStyle();// CellStyle 
            styleContent1.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
            styleContent1.setVerticalAlignment(VerticalAlignment.CENTER);
            styleContent1.setBorderBottom(BorderStyle.THIN);
            styleContent1.setBorderTop(BorderStyle.THIN);
            styleContent1.setBorderLeft(BorderStyle.THIN);
            styleContent1.setBorderRight(BorderStyle.THIN);
            styleContent1.setWrapText(true);
            
			HSSFFont fontContent1 = wb.createFont();
			fontContent1.setFontName("微软雅黑");
			fontContent1.setFontHeightInPoints((short) 10);// 设置字体大小
			styleContent1.setFont(fontContent1);
			
			HSSFCellStyle styleContent1_left = wb.createCellStyle();// CellStyle 
			styleContent1_left.setAlignment(HorizontalAlignment.LEFT);// 创建一个居中格式
			styleContent1_left.setVerticalAlignment(VerticalAlignment.CENTER);
			styleContent1_left.setBorderBottom(BorderStyle.THIN);
			styleContent1_left.setBorderTop(BorderStyle.THIN);
			styleContent1_left.setBorderLeft(BorderStyle.THIN);
			styleContent1_left.setBorderRight(BorderStyle.THIN);
			styleContent1_left.setWrapText(true);
			styleContent1_left.setFont(fontContent1);
			
			String orderNo = "";
			String count = "";
			String productFactory = "";
			if(bomDTO.getOrderInfoDTO() != null){
				orderNo = bomDTO.getOrderInfoDTO().getOrderNo();
				count = bomDTO.getOrderInfoDTO().getQuantity()+"";
				productFactory = bomDTO.getOrderInfoDTO().getProductFactory();
			}
			//开始写数据
			//标题：APICAL 343J-5002A 物料清单
			HSSFCell createCell0 = row0.createCell(0);
			createCell0.setCellValue("APICAL "+productFactory+" 物料清单");
			createCell0.setCellStyle(styleTitle);
            
			//制表:TSH       日期:2015-9-1           版本号:01
			HSSFCell createCell1_0 = row1.createCell(0);
			createCell1_0.setCellValue("制表:"+nullToString(bomDTO.getMaker())+"    "+"日期:"+nullToString(bomDTO.getDateString())+"    "+"版本号:"+nullToString(bomDTO.getVersion()));
			createCell1_0.setCellStyle(styleContent1_left);
			
			
			HSSFCell createCell1_6 = row1.createCell(6);
			createCell1_6.setCellValue(" 订单号:"+orderNo+"数量："+count+"套");
			createCell1_6.setCellStyle(styleTitleNoBorder);
			
			//排序标题
			HSSFCell createCell2_0 = row2.createCell(0);
			createCell2_0.setCellValue("序号");
			createCell2_0.setCellStyle(styleTitle1);

			HSSFCell createCell2_1 = row2.createCell(1);
			createCell2_1.setCellValue("物料编码");
			createCell2_1.setCellStyle(styleTitle1);

			HSSFCell createCell2_2 = row2.createCell(2);
			createCell2_2.setCellValue("品名");
			createCell2_2.setCellStyle(styleTitle1);

			HSSFCell createCell2_3 = row2.createCell(3);
			createCell2_3.setCellValue("规格型号及封装");
			createCell2_3.setCellStyle(styleTitle1);
			
			HSSFCell createCell2_4 = row2.createCell(4);
			createCell2_4.setCellValue("品牌");
			createCell2_4.setCellStyle(styleTitle1);

			HSSFCell createCell2_5 = row2.createCell(5);
			createCell2_5.setCellValue("用量");
			createCell2_5.setCellStyle(styleTitle1);
			
			HSSFCell createCell2_6 = row2.createCell(6);
			createCell2_6.setCellValue("描述");
			createCell2_6.setCellStyle(styleTitle1);
			
			HSSFCell createCell2_7 = row2.createCell(7);
			createCell2_7.setCellValue("厂家");
			createCell2_7.setCellStyle(styleTitle1);

			HSSFCell createCell2_8 = row2.createCell(8);
			createCell2_8.setCellValue("联系人");
			createCell2_8.setCellStyle(styleTitle1);

			HSSFCell createCell2_9 = row2.createCell(9);
			createCell2_9.setCellValue("电话");
			createCell2_9.setCellStyle(styleTitle1);
			
			HSSFCell createCell2_10 = row2.createCell(10);
			createCell2_10.setCellValue("传真");
			createCell2_10.setCellStyle(styleTitle1);

			HSSFCell createCell2_11 = row2.createCell(11);
			createCell2_11.setCellValue("手机");
			createCell2_11.setCellStyle(styleTitle1);
			
			//主标题（粉色）
			HSSFCell createCell3_0 = row3.createCell(0);
			createCell3_0.setCellValue("");
			createCell3_0.setCellStyle(styleContent1);
			HSSFCell createCell3_1 = row3.createCell(1);
			createCell3_1.setCellValue(nullToString(bomDTO.getMaterialCode()));
			createCell3_1.setCellStyle(styleContent_pink);
			HSSFCell createCell3_2 = row3.createCell(2);
			createCell3_2.setCellValue(nullToString(bomDTO.getProductName()));
			createCell3_2.setCellStyle(styleContent_pinkLeft);
			HSSFCell createCell3_3 = row3.createCell(3);
			createCell3_3.setCellValue(nullToString(bomDTO.getSpecification()));
			createCell3_3.setCellStyle(styleContent_pinkLeft);
			HSSFCell createCell3_4 = row3.createCell(4);
			//createCell3_4.setCellValue("");
			createCell3_4.setCellStyle(styleContent_pinkLeft);
			HSSFCell createCell3_5 = row3.createCell(5);
			//createCell3_5.setCellValue("");
			createCell3_5.setCellStyle(styleContent_pinkLeft);
			HSSFCell createCell3_6 = row3.createCell(6);
			createCell3_6.setCellValue(nullToString(bomDTO.getDescription()));
			createCell3_6.setCellStyle(styleContent1_left);
			
			List<BomMaterialRefDTO> list1 = new ArrayList<BomMaterialRefDTO>();
			List<BomMaterialRefDTO> list2 = new ArrayList<BomMaterialRefDTO>();
			List<BomMaterialRefDTO> list3 = new ArrayList<BomMaterialRefDTO>();
			List<BomMaterialRefDTO> list4 = new ArrayList<BomMaterialRefDTO>();
			List<BomMaterialRefDTO> list5 = new ArrayList<BomMaterialRefDTO>();
			List<BomMaterialRefDTO> list6 = new ArrayList<BomMaterialRefDTO>();
			List<BomMaterialRefDTO> list7 = new ArrayList<BomMaterialRefDTO>();
			List<BomMaterialRefDTO> list8 = new ArrayList<BomMaterialRefDTO>();
			List<BomMaterialRefDTO> list9 = new ArrayList<BomMaterialRefDTO>();
			
			List<BomMaterialRefDTO> bomMaterialRefDTOs = bomDTO.getBomMaterialRefDTOs();
			
			if(bomMaterialRefDTOs != null && bomMaterialRefDTOs.size() > 0){
				for(BomMaterialRefDTO bomMaterialRefDTO : bomMaterialRefDTOs){
					if(bomMaterialRefDTO.getType() == 1){//第一部分：裸机包装模组
						list1.add(bomMaterialRefDTO);
					}
					if(bomMaterialRefDTO.getType() == 2){//一、裸机成品
						list2.add(bomMaterialRefDTO);
					}
					if(bomMaterialRefDTO.getType() == 3){//a、散件，无虚拟件料号
						list3.add(bomMaterialRefDTO);
					}
					if(bomMaterialRefDTO.getType() == 4){//b
						list4.add(bomMaterialRefDTO);
					}
					if(bomMaterialRefDTO.getType() == 5){//c
						list5.add(bomMaterialRefDTO);
					}
					if(bomMaterialRefDTO.getType() == 6){//第二部分：彩盒模组部分
						list6.add(bomMaterialRefDTO);
					}
					if(bomMaterialRefDTO.getType() == 7){//第三部分：卡通箱模组部分
						list7.add(bomMaterialRefDTO);
					}
					if(bomMaterialRefDTO.getType() == 8){//第四部分：配件及其他包材类
						list8.add(bomMaterialRefDTO);
					}
					if(bomMaterialRefDTO.getType() == 9){//第五部分、备品
						list9.add(bomMaterialRefDTO);
					}
				}
			}
			/*System.out.println("list1大小："+list1.size());
			System.out.println("list2大小："+list2.size());
			System.out.println("list3大小："+list3.size());
			System.out.println("list4大小："+list4.size());
			System.out.println("list5大小："+list5.size());
			System.out.println("list6大小："+list6.size());
			System.out.println("list7大小："+list7.size());
			System.out.println("list8大小："+list8.size());
			System.out.println("list9大小："+list9.size());*/
			//第一部分：裸机包装模组
			HSSFCell createCell4_0 = row4.createCell(0);
			createCell4_0.setCellValue("第一部分：裸机包装模组");
			sheet.addMergedRegion(new CellRangeAddress(4,4,0,11));
			createCell4_0.setCellStyle(styleTitle1Left);
			
			int countRow = 5;//记录数据的数量
			if(list1 != null && list1.size() > 0){
				for(BomMaterialRefDTO bomMaterialRefDTO : list1){
					HSSFRow row = sheet.createRow(countRow); //row.setHeightInPoints(26.25f);
					if(bomMaterialRefDTO.getSeq() == 0){//标题
						HSSFCell row_0 = row.createCell(0);
						row_0.setCellValue("");
						row_0.setCellStyle(styleContent1);
						
						HSSFCell row_1 = row.createCell(1);
						row_1.setCellValue(nullToString(bomMaterialRefDTO.getMaterialCode())+"");
						row_1.setCellStyle(styleContent_blue);
						
						HSSFCell row_2 = row.createCell(2);
						row_2.setCellValue(nullToString(bomMaterialRefDTO.getProductName())+"");
						row_2.setCellStyle(styleContent_blueLeft);
						
						HSSFCell row_3 = row.createCell(3);
						row_3.setCellValue(nullToString(bomMaterialRefDTO.getSpecification())+"");
						row_3.setCellStyle(styleContent_blueLeft);
						
						HSSFCell row_4 = row.createCell(4);
						row_4.setCellValue("");
						row_4.setCellStyle(styleContent_blue);
						
						HSSFCell row_5 = row.createCell(5);
						row_5.setCellValue("");
						row_5.setCellStyle(styleContent_blue);
						
						HSSFCell row_6 = row.createCell(6);
						row_6.setCellValue(nullToString(bomMaterialRefDTO.getDescription())+"");
						row_6.setCellStyle(styleContent1_left);
						
					}else{//非标题
						HSSFCell row_0 = row.createCell(0);
						row_0.setCellValue(nullToString(bomMaterialRefDTO.getSeq()+"")+"");
						row_0.setCellStyle(styleContent1);
						
						HSSFCell row_1 = row.createCell(1);
						row_1.setCellValue(nullToString(bomMaterialRefDTO.getMaterialCode())+"");
						row_1.setCellStyle(styleContent1);
						
						HSSFCell row_2 = row.createCell(2);
						row_2.setCellValue(nullToString(bomMaterialRefDTO.getProductName())+"");
						row_2.setCellStyle(styleContent1_left);
						
						HSSFCell row_3 = row.createCell(3);
						row_3.setCellValue(nullToString(bomMaterialRefDTO.getSpecification())+"");
						row_3.setCellStyle(styleContent1_left);
						
						HSSFCell row_4 = row.createCell(4);
						row_4.setCellValue(nullToString(bomMaterialRefDTO.getBrand())+"");
						row_4.setCellStyle(styleContent1);
						
						String usageAmount = "";
						if(bomMaterialRefDTO.getUsageAmount1() != null && bomMaterialRefDTO.getUsageAmount2() != null){
							usageAmount = FractionalUtil.showToString(bomMaterialRefDTO.getUsageAmount1(), bomMaterialRefDTO.getUsageAmount2())+"";
						}
						HSSFCell row_5 = row.createCell(5);
						row_5.setCellValue(usageAmount);
						row_5.setCellStyle(styleContent1);
						
						HSSFCell row_6 = row.createCell(6);
						row_6.setCellValue(nullToString(bomMaterialRefDTO.getDescription())+"");
						row_6.setCellStyle(styleContent1_left);
						
						if(bomMaterialRefDTO.getMaterialContactDTO() != null){
							BomMaterialContactDTO contact = bomMaterialRefDTO.getMaterialContactDTO();
							
							HSSFCell row_7 = row.createCell(7);
							row_7.setCellValue(nullToString(contact.getCompany())+"");
							row_7.setCellStyle(styleContent1_left);
							
							HSSFCell row_8 = row.createCell(8);
							row_8.setCellValue(nullToString(contact.getContacts())+"");
							row_8.setCellStyle(styleContent1_left);
							
							HSSFCell row_9 = row.createCell(9);
							row_9.setCellValue(nullToString(contact.getTelphone())+"");
							row_9.setCellStyle(styleContent1_left);
							
							HSSFCell row_10 = row.createCell(10);
							row_10.setCellValue(nullToString(contact.getFax())+"");
							row_10.setCellStyle(styleContent1_left);
							
							HSSFCell row_11 = row.createCell(11);
							row_11.setCellValue(nullToString(contact.getCellphone())+"");
							row_11.setCellStyle(styleContent1_left);
						}
					}
					countRow ++ ;
				}
			}
			
			//一、裸机成品
			HSSFRow rowTitle1 = sheet.createRow(countRow); rowTitle1.setHeightInPoints(26.25f);
			HSSFCell rowTitle1_0 = rowTitle1.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(countRow,countRow,0,11));
			rowTitle1_0.setCellValue("一、裸机成品");
			rowTitle1_0.setCellStyle(styleTitle1Left);
			countRow ++ ;
			if(list2 != null && list2.size() > 0){
				for(BomMaterialRefDTO bomMaterialRefDTO : list2){
					HSSFRow row = sheet.createRow(countRow);// row.setHeightInPoints(26.25f);
					if(bomMaterialRefDTO.getSeq() == 0){//标题
						HSSFCell row_0 = row.createCell(0);
						row_0.setCellValue("");
						row_0.setCellStyle(styleContent1);
						HSSFCell row_1 = row.createCell(1);
						row_1.setCellValue(nullToString(bomMaterialRefDTO.getMaterialCode())+"");
						row_1.setCellStyle(styleContent_blue);
						
						HSSFCell row_2 = row.createCell(2);
						row_2.setCellValue(nullToString(bomMaterialRefDTO.getProductName())+"");
						row_2.setCellStyle(styleContent_blueLeft);
						
						HSSFCell row_3 = row.createCell(3);
						row_3.setCellValue(nullToString(bomMaterialRefDTO.getSpecification())+"");
						row_3.setCellStyle(styleContent_blueLeft);
						
						HSSFCell row_4 = row.createCell(4);
						row_4.setCellValue("");
						row_4.setCellStyle(styleContent_blue);
						
						HSSFCell row_5 = row.createCell(5);
						row_5.setCellValue("");
						row_5.setCellStyle(styleContent_blue);
						
						HSSFCell row_6 = row.createCell(6);
						row_6.setCellValue(nullToString(bomMaterialRefDTO.getDescription())+"");
						row_6.setCellStyle(styleContent1_left);
						
					}
					countRow ++ ;
				}
			}
			
			
			//a、散件，无虚拟料号
			HSSFRow rowTitle2 = sheet.createRow(countRow); rowTitle2.setHeightInPoints(26.25f);
			HSSFCell rowTitle2_0 = rowTitle2.createCell(0);
			rowTitle2_0.setCellValue("a");
			rowTitle2_0.setCellStyle(styleContent_pink);
			HSSFCell rowTitle2_1 = rowTitle2.createCell(1);
			rowTitle2_1.setCellValue("散件，无虚拟料号");
			rowTitle2_1.setCellStyle(styleTitle1Left);
			
			sheet.addMergedRegion(new CellRangeAddress(countRow,countRow,1,11));
			countRow ++ ;
			if(list3 != null && list3.size() > 0){
				for(BomMaterialRefDTO bomMaterialRefDTO : list3){
					HSSFRow row = sheet.createRow(countRow); //row.setHeightInPoints(26.25f);
					//非标题
					HSSFCell row_0 = row.createCell(0);
					row_0.setCellValue(nullToString(bomMaterialRefDTO.getSeq()+"")+"");
					row_0.setCellStyle(styleContent1);
					
					HSSFCell row_1 = row.createCell(1);
					row_1.setCellValue(nullToString(bomMaterialRefDTO.getMaterialCode())+"");
					row_1.setCellStyle(styleContent1);
					
					HSSFCell row_2 = row.createCell(2);
					row_2.setCellValue(nullToString(bomMaterialRefDTO.getProductName())+"");
					row_2.setCellStyle(styleContent1_left);
					
					HSSFCell row_3 = row.createCell(3);
					row_3.setCellValue(nullToString(bomMaterialRefDTO.getSpecification())+"");
					row_3.setCellStyle(styleContent1_left);
					
					HSSFCell row_4 = row.createCell(4);
					row_4.setCellValue(nullToString(bomMaterialRefDTO.getBrand())+"");
					row_4.setCellStyle(styleContent1);
					
					String usageAmount = "";
					if(bomMaterialRefDTO.getUsageAmount1() != null && bomMaterialRefDTO.getUsageAmount2() != null){
						usageAmount = FractionalUtil.showToString(bomMaterialRefDTO.getUsageAmount1(), bomMaterialRefDTO.getUsageAmount2())+"";
					}
					HSSFCell row_5 = row.createCell(5);
					row_5.setCellValue(usageAmount);
					row_5.setCellStyle(styleContent1);
					
					HSSFCell row_6 = row.createCell(6);
					row_6.setCellValue(nullToString(bomMaterialRefDTO.getDescription())+"");
					row_6.setCellStyle(styleContent1_left);
					
					if(bomMaterialRefDTO.getMaterialContactDTO() != null){
						BomMaterialContactDTO contact = bomMaterialRefDTO.getMaterialContactDTO();
						
						HSSFCell row_7 = row.createCell(7);
						row_7.setCellValue(nullToString(contact.getCompany())+"");
						row_7.setCellStyle(styleContent1_left);
						
						HSSFCell row_8 = row.createCell(8);
						row_8.setCellValue(nullToString(contact.getContacts())+"");
						row_8.setCellStyle(styleContent1_left);
						
						HSSFCell row_9 = row.createCell(9);
						row_9.setCellValue(nullToString(contact.getTelphone())+"");
						row_9.setCellStyle(styleContent1_left);
						
						HSSFCell row_10 = row.createCell(10);
						row_10.setCellValue(nullToString(contact.getFax())+"");
						row_10.setCellStyle(styleContent1_left);
						
						HSSFCell row_11 = row.createCell(11);
						row_11.setCellValue(nullToString(contact.getCellphone())+"");
						row_11.setCellStyle(styleContent1_left);
					}
					countRow ++ ;
				}
			}
			
			
			if(list4 != null && list4.size() > 0){
				for(BomMaterialRefDTO bomMaterialRefDTO : list4){
					//b
					HSSFRow row = sheet.createRow(countRow); //row.setHeightInPoints(26.25f);
					if(bomMaterialRefDTO.getSeq() == 0){//标题
						HSSFCell row_0 = row.createCell(0);
						row_0.setCellValue("b");
						row_0.setCellStyle(styleContent_pink);
						
						HSSFCell row_1 = row.createCell(1);
						row_1.setCellValue(nullToString(bomMaterialRefDTO.getMaterialCode())+"");
						row_1.setCellStyle(styleContent_blue);
						
						HSSFCell row_2 = row.createCell(2);
						row_2.setCellValue(nullToString(bomMaterialRefDTO.getProductName())+"");
						row_2.setCellStyle(styleContent_blueLeft);
						
						HSSFCell row_3 = row.createCell(3);
						row_3.setCellValue(nullToString(bomMaterialRefDTO.getSpecification())+"");
						row_3.setCellStyle(styleContent_blueLeft);
						
						HSSFCell row_4 = row.createCell(4);
						row_4.setCellValue("");
						row_4.setCellStyle(styleContent_blue);
						
						HSSFCell row_5 = row.createCell(5);
						row_5.setCellValue("");
						row_5.setCellStyle(styleContent_blue);
						
						HSSFCell row_6 = row.createCell(6);
						row_6.setCellValue(nullToString(bomMaterialRefDTO.getDescription())+"");
						row_6.setCellStyle(styleContent1_left);
					}else{
						//非标题
						HSSFCell row_0 = row.createCell(0);
						row_0.setCellValue(nullToString(bomMaterialRefDTO.getSeq()+"")+"");
						row_0.setCellStyle(styleContent1);
						
						HSSFCell row_1 = row.createCell(1);
						row_1.setCellValue(nullToString(bomMaterialRefDTO.getMaterialCode())+"");
						row_1.setCellStyle(styleContent1);
						
						HSSFCell row_2 = row.createCell(2);
						row_2.setCellValue(nullToString(bomMaterialRefDTO.getProductName())+"");
						row_2.setCellStyle(styleContent1_left);
						
						HSSFCell row_3 = row.createCell(3);
						row_3.setCellValue(nullToString(bomMaterialRefDTO.getSpecification())+"");
						row_3.setCellStyle(styleContent1_left);
						
						HSSFCell row_4 = row.createCell(4);
						row_4.setCellValue(nullToString(bomMaterialRefDTO.getBrand())+"");
						row_4.setCellStyle(styleContent1);
						
						String usageAmount = "";
						if(bomMaterialRefDTO.getUsageAmount1() != null && bomMaterialRefDTO.getUsageAmount2() != null){
							usageAmount = FractionalUtil.showToString(bomMaterialRefDTO.getUsageAmount1(), bomMaterialRefDTO.getUsageAmount2())+"";
						}
						HSSFCell row_5 = row.createCell(5);
						row_5.setCellValue(usageAmount);
						row_5.setCellStyle(styleContent1);
						
						HSSFCell row_6 = row.createCell(6);
						row_6.setCellValue(nullToString(bomMaterialRefDTO.getDescription())+"");
						row_6.setCellStyle(styleContent1_left);
						
						if(bomMaterialRefDTO.getMaterialContactDTO() != null){
							BomMaterialContactDTO contact = bomMaterialRefDTO.getMaterialContactDTO();
							
							HSSFCell row_7 = row.createCell(7);
							row_7.setCellValue(nullToString(contact.getCompany())+"");
							row_7.setCellStyle(styleContent1_left);
							
							HSSFCell row_8 = row.createCell(8);
							row_8.setCellValue(nullToString(contact.getContacts())+"");
							row_8.setCellStyle(styleContent1_left);
							
							HSSFCell row_9 = row.createCell(9);
							row_9.setCellValue(nullToString(contact.getTelphone())+"");
							row_9.setCellStyle(styleContent1_left);
							
							HSSFCell row_10 = row.createCell(10);
							row_10.setCellValue(nullToString(contact.getFax())+"");
							row_10.setCellStyle(styleContent1_left);
							
							HSSFCell row_11 = row.createCell(11);
							row_11.setCellValue(nullToString(contact.getCellphone())+"");
							row_11.setCellStyle(styleContent1_left);
						}
					
					}
					countRow ++ ;
				}
			}
			
			
			
			if(list5 != null && list5.size() > 0){
				for(BomMaterialRefDTO bomMaterialRefDTO : list5){
					//c
					HSSFRow row = sheet.createRow(countRow);// row.setHeightInPoints(26.25f);
					if(bomMaterialRefDTO.getSeq() == 0){//标题
						HSSFCell row_0 = row.createCell(0);
						row_0.setCellValue("c");
						row_0.setCellStyle(styleContent_pink);
						
						HSSFCell row_1 = row.createCell(1);
						row_1.setCellValue(nullToString(bomMaterialRefDTO.getMaterialCode())+"");
						row_1.setCellStyle(styleContent_blue);
						
						HSSFCell row_2 = row.createCell(2);
						row_2.setCellValue(nullToString(bomMaterialRefDTO.getProductName())+"");
						row_2.setCellStyle(styleContent_blueLeft);
						
						HSSFCell row_3 = row.createCell(3);
						row_3.setCellValue(nullToString(bomMaterialRefDTO.getSpecification())+"");
						row_3.setCellStyle(styleContent_blueLeft);
						
						HSSFCell row_4 = row.createCell(4);
						row_4.setCellValue("");
						row_4.setCellStyle(styleContent_blue);
						
						HSSFCell row_5 = row.createCell(5);
						row_5.setCellValue("");
						row_5.setCellStyle(styleContent_blue);
						
						HSSFCell row_6 = row.createCell(6);
						row_6.setCellValue(nullToString(bomMaterialRefDTO.getDescription())+"");
						row_6.setCellStyle(styleContent1_left);
						
					}else{
						//非标题
						HSSFCell row_0 = row.createCell(0);
						row_0.setCellValue(nullToString(bomMaterialRefDTO.getSeq()+"")+"");
						row_0.setCellStyle(styleContent1);
						
						HSSFCell row_1 = row.createCell(1);
						row_1.setCellValue(nullToString(bomMaterialRefDTO.getMaterialCode())+"");
						row_1.setCellStyle(styleContent1);
						
						HSSFCell row_2 = row.createCell(2);
						row_2.setCellValue(nullToString(bomMaterialRefDTO.getProductName())+"");
						row_2.setCellStyle(styleContent1_left);
						
						HSSFCell row_3 = row.createCell(3);
						row_3.setCellValue(nullToString(bomMaterialRefDTO.getSpecification())+"");
						row_3.setCellStyle(styleContent1_left);
						
						HSSFCell row_4 = row.createCell(4);
						row_4.setCellValue(nullToString(bomMaterialRefDTO.getBrand())+"");
						row_4.setCellStyle(styleContent1);
						
						String usageAmount = "";
						if(bomMaterialRefDTO.getUsageAmount1() != null && bomMaterialRefDTO.getUsageAmount2() != null){
							usageAmount = FractionalUtil.showToString(bomMaterialRefDTO.getUsageAmount1(), bomMaterialRefDTO.getUsageAmount2())+"";
						}
						HSSFCell row_5 = row.createCell(5);
						row_5.setCellValue(usageAmount);
						row_5.setCellStyle(styleContent1);
						
						HSSFCell row_6 = row.createCell(6);
						row_6.setCellValue(nullToString(bomMaterialRefDTO.getDescription())+"");
						row_6.setCellStyle(styleContent1_left);
						
						if(bomMaterialRefDTO.getMaterialContactDTO() != null){
							BomMaterialContactDTO contact = bomMaterialRefDTO.getMaterialContactDTO();
							
							HSSFCell row_7 = row.createCell(7);
							row_7.setCellValue(nullToString(contact.getCompany())+"");
							row_7.setCellStyle(styleContent1_left);
							
							HSSFCell row_8 = row.createCell(8);
							row_8.setCellValue(nullToString(contact.getContacts())+"");
							row_8.setCellStyle(styleContent1_left);
							
							HSSFCell row_9 = row.createCell(9);
							row_9.setCellValue(nullToString(contact.getTelphone())+"");
							row_9.setCellStyle(styleContent1_left);
							
							HSSFCell row_10 = row.createCell(10);
							row_10.setCellValue(nullToString(contact.getFax())+"");
							row_10.setCellStyle(styleContent1_left);
							
							HSSFCell row_11 = row.createCell(11);
							row_11.setCellValue(nullToString(contact.getCellphone())+"");
							row_11.setCellStyle(styleContent1_left);
						}
					}
					countRow ++ ;
				}
			}
			
			
			//二、彩盒模组部分
			HSSFRow rowTitle3 = sheet.createRow(countRow); rowTitle3.setHeightInPoints(26.25f);
			HSSFCell rowTitle3_0 = rowTitle3.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(countRow,countRow,0,11));
			rowTitle3_0.setCellValue("二、彩盒模组部分");
			rowTitle3_0.setCellStyle(styleTitle1Left);
			countRow ++ ;
			if(list6 != null && list6.size() > 0){
				for(BomMaterialRefDTO bomMaterialRefDTO : list6){
					//二、彩盒模组部分
					HSSFRow row = sheet.createRow(countRow); //row.setHeightInPoints(26.25f);
					if(bomMaterialRefDTO.getSeq() == 0){//标题
						HSSFCell row_0 = row.createCell(0);
						row_0.setCellValue("");
						row_0.setCellStyle(styleContent1);
						
						HSSFCell row_1 = row.createCell(1);
						row_1.setCellValue(nullToString(bomMaterialRefDTO.getMaterialCode())+"");
						row_1.setCellStyle(styleContent_blue);
						
						HSSFCell row_2 = row.createCell(2);
						row_2.setCellValue(nullToString(bomMaterialRefDTO.getProductName())+"");
						row_2.setCellStyle(styleContent_blueLeft);
						
						HSSFCell row_3 = row.createCell(3);
						row_3.setCellValue(nullToString(bomMaterialRefDTO.getSpecification())+"");
						row_3.setCellStyle(styleContent_blueLeft);
						
						HSSFCell row_4 = row.createCell(4);
						row_4.setCellValue("");
						row_4.setCellStyle(styleContent_blue);
						
						HSSFCell row_5 = row.createCell(5);
						row_5.setCellValue("");
						row_5.setCellStyle(styleContent_blue);
						
						HSSFCell row_6 = row.createCell(6);
						row_6.setCellValue(nullToString(bomMaterialRefDTO.getDescription())+"");
						row_6.setCellStyle(styleContent1_left);
						
					}else{//非标题
						HSSFCell row_0 = row.createCell(0);
						row_0.setCellValue(nullToString(bomMaterialRefDTO.getSeq()+"")+"");
						row_0.setCellStyle(styleContent1);
						
						HSSFCell row_1 = row.createCell(1);
						row_1.setCellValue(nullToString(bomMaterialRefDTO.getMaterialCode())+"");
						row_1.setCellStyle(styleContent1);
						
						HSSFCell row_2 = row.createCell(2);
						row_2.setCellValue(nullToString(bomMaterialRefDTO.getProductName())+"");
						row_2.setCellStyle(styleContent1_left);
						
						HSSFCell row_3 = row.createCell(3);
						row_3.setCellValue(nullToString(bomMaterialRefDTO.getSpecification())+"");
						row_3.setCellStyle(styleContent1_left);
						
						HSSFCell row_4 = row.createCell(4);
						row_4.setCellValue(nullToString(bomMaterialRefDTO.getBrand())+"");
						row_4.setCellStyle(styleContent1);
						
						String usageAmount = "";
						if(bomMaterialRefDTO.getUsageAmount1() != null && bomMaterialRefDTO.getUsageAmount2() != null){
							usageAmount = FractionalUtil.showToString(bomMaterialRefDTO.getUsageAmount1(), bomMaterialRefDTO.getUsageAmount2())+"";
						}
						HSSFCell row_5 = row.createCell(5);
						row_5.setCellValue(usageAmount);
						row_5.setCellStyle(styleContent1);
						
						HSSFCell row_6 = row.createCell(6);
						row_6.setCellValue(nullToString(bomMaterialRefDTO.getDescription())+"");
						row_6.setCellStyle(styleContent1_left);
						
						if(bomMaterialRefDTO.getMaterialContactDTO() != null){
							BomMaterialContactDTO contact = bomMaterialRefDTO.getMaterialContactDTO();
							
							HSSFCell row_7 = row.createCell(7);
							row_7.setCellValue(nullToString(contact.getCompany())+"");
							row_7.setCellStyle(styleContent1_left);
							
							HSSFCell row_8 = row.createCell(8);
							row_8.setCellValue(nullToString(contact.getContacts())+"");
							row_8.setCellStyle(styleContent1_left);
							
							HSSFCell row_9 = row.createCell(9);
							row_9.setCellValue(nullToString(contact.getTelphone())+"");
							row_9.setCellStyle(styleContent1_left);
							
							HSSFCell row_10 = row.createCell(10);
							row_10.setCellValue(nullToString(contact.getFax())+"");
							row_10.setCellStyle(styleContent1_left);
							
							HSSFCell row_11 = row.createCell(11);
							row_11.setCellValue(nullToString(contact.getCellphone())+"");
							row_11.setCellStyle(styleContent1_left);
						}
					}
					countRow ++ ;
				}
			}
			
			//System.out.println("countRow::::"+countRow);
			//第三部分：卡通箱模组部分
			HSSFRow rowTitle4 = sheet.createRow(countRow); rowTitle4.setHeightInPoints(26.25f);
			HSSFCell rowTitle4_0 = rowTitle4.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(countRow,countRow,0,11));
			rowTitle4_0.setCellValue("第三部分：卡通箱模组部分");
			rowTitle4_0.setCellStyle(styleTitle1Left);
			countRow ++ ;
			if(list7 != null && list7.size() > 0){
				for(BomMaterialRefDTO bomMaterialRefDTO : list7){
					//二、彩盒模组部分
					HSSFRow row = sheet.createRow(countRow); //row.setHeightInPoints(26.25f);
					if(bomMaterialRefDTO.getSeq() == 0){//标题
						HSSFCell row_0 = row.createCell(0);
						row_0.setCellValue("");
						row_0.setCellStyle(styleContent1);
						
						HSSFCell row_1 = row.createCell(1);
						row_1.setCellValue(nullToString(bomMaterialRefDTO.getMaterialCode())+"");
						row_1.setCellStyle(styleContent_blue);
						
						HSSFCell row_2 = row.createCell(2);
						row_2.setCellValue(nullToString(bomMaterialRefDTO.getProductName())+"");
						row_2.setCellStyle(styleContent_blueLeft);
						
						HSSFCell row_3 = row.createCell(3);
						row_3.setCellValue(nullToString(bomMaterialRefDTO.getSpecification())+"");
						row_3.setCellStyle(styleContent_blueLeft);
						
						HSSFCell row_4 = row.createCell(4);
						row_4.setCellValue("");
						row_4.setCellStyle(styleContent_blue);
						
						HSSFCell row_5 = row.createCell(5);
						row_5.setCellValue("");
						row_5.setCellStyle(styleContent_blue);
						
						HSSFCell row_6 = row.createCell(6);
						row_6.setCellValue(nullToString(bomMaterialRefDTO.getDescription())+"");
						row_6.setCellStyle(styleContent1_left);
						
					}else{//非标题
						HSSFCell row_0 = row.createCell(0);
						row_0.setCellValue(nullToString(bomMaterialRefDTO.getSeq()+"")+"");
						row_0.setCellStyle(styleContent1);
						
						HSSFCell row_1 = row.createCell(1);
						row_1.setCellValue(nullToString(bomMaterialRefDTO.getMaterialCode())+"");
						row_1.setCellStyle(styleContent1);
						
						HSSFCell row_2 = row.createCell(2);
						row_2.setCellValue(nullToString(bomMaterialRefDTO.getProductName())+"");
						row_2.setCellStyle(styleContent1_left);
						
						HSSFCell row_3 = row.createCell(3);
						row_3.setCellValue(nullToString(bomMaterialRefDTO.getSpecification())+"");
						row_3.setCellStyle(styleContent1_left);
						
						HSSFCell row_4 = row.createCell(4);
						row_4.setCellValue(nullToString(bomMaterialRefDTO.getBrand())+"");
						row_4.setCellStyle(styleContent1);
						
						String usageAmount = "";
						if(bomMaterialRefDTO.getUsageAmount1() != null && bomMaterialRefDTO.getUsageAmount2() != null){
							usageAmount = FractionalUtil.showToString(bomMaterialRefDTO.getUsageAmount1(), bomMaterialRefDTO.getUsageAmount2())+"";
						}
						HSSFCell row_5 = row.createCell(5);
						row_5.setCellValue(usageAmount);
						row_5.setCellStyle(styleContent1);
						
						HSSFCell row_6 = row.createCell(6);
						row_6.setCellValue(nullToString(bomMaterialRefDTO.getDescription())+"");
						row_6.setCellStyle(styleContent1_left);
						
						if(bomMaterialRefDTO.getMaterialContactDTO() != null){
							BomMaterialContactDTO contact = bomMaterialRefDTO.getMaterialContactDTO();
							
							HSSFCell row_7 = row.createCell(7);
							row_7.setCellValue(nullToString(contact.getCompany())+"");
							row_7.setCellStyle(styleContent1_left);
							
							HSSFCell row_8 = row.createCell(8);
							row_8.setCellValue(nullToString(contact.getContacts())+"");
							row_8.setCellStyle(styleContent1_left);
							
							HSSFCell row_9 = row.createCell(9);
							row_9.setCellValue(nullToString(contact.getTelphone())+"");
							row_9.setCellStyle(styleContent1_left);
							
							HSSFCell row_10 = row.createCell(10);
							row_10.setCellValue(nullToString(contact.getFax())+"");
							row_10.setCellStyle(styleContent1_left);
							
							HSSFCell row_11 = row.createCell(11);
							row_11.setCellValue(nullToString(contact.getCellphone())+"");
							row_11.setCellStyle(styleContent1_left);
						}
					}
					countRow ++ ;
				}
			}
			//System.out.println("countRow::::"+countRow);
			
			

			//第四部分：配件及其他包材类
			HSSFRow rowTitle5 = sheet.createRow(countRow); rowTitle5.setHeightInPoints(26.25f);
			HSSFCell rowTitle5_0 = rowTitle5.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(countRow,countRow,0,11));
			rowTitle5_0.setCellValue("第四部分：配件及其他包材类");
			rowTitle5_0.setCellStyle(styleTitle1Left);
			countRow ++ ;
			if(list8 != null && list8.size() > 0){
				for(BomMaterialRefDTO bomMaterialRefDTO : list8){
					//第四部分：配件及其他包材类
					HSSFRow row = sheet.createRow(countRow); //row.setHeightInPoints(26.25f);
					HSSFCell row_0 = row.createCell(0);
					row_0.setCellValue(nullToString(bomMaterialRefDTO.getSeq()+"")+"");
					row_0.setCellStyle(styleContent1);
					
					HSSFCell row_1 = row.createCell(1);
					row_1.setCellValue(nullToString(bomMaterialRefDTO.getMaterialCode())+"");
					row_1.setCellStyle(styleContent1);
					
					HSSFCell row_2 = row.createCell(2);
					row_2.setCellValue(nullToString(bomMaterialRefDTO.getProductName())+"");
					row_2.setCellStyle(styleContent1_left);
					
					HSSFCell row_3 = row.createCell(3);
					row_3.setCellValue(nullToString(bomMaterialRefDTO.getSpecification())+"");
					row_3.setCellStyle(styleContent1_left);
					
					HSSFCell row_4 = row.createCell(4);
					row_4.setCellValue(nullToString(bomMaterialRefDTO.getBrand())+"");
					row_4.setCellStyle(styleContent1);
					
					String usageAmount = "";
					if(bomMaterialRefDTO.getUsageAmount1() != null && bomMaterialRefDTO.getUsageAmount2() != null){
						usageAmount = FractionalUtil.showToString(bomMaterialRefDTO.getUsageAmount1(), bomMaterialRefDTO.getUsageAmount2())+"";
					}
					HSSFCell row_5 = row.createCell(5);
					row_5.setCellValue(usageAmount);
					row_5.setCellStyle(styleContent1);
					
					HSSFCell row_6 = row.createCell(6);
					row_6.setCellValue(nullToString(bomMaterialRefDTO.getDescription())+"");
					row_6.setCellStyle(styleContent1_left);
					
					if(bomMaterialRefDTO.getMaterialContactDTO() != null){
						BomMaterialContactDTO contact = bomMaterialRefDTO.getMaterialContactDTO();
						
						HSSFCell row_7 = row.createCell(7);
						row_7.setCellValue(nullToString(contact.getCompany())+"");
						row_7.setCellStyle(styleContent1_left);
						
						HSSFCell row_8 = row.createCell(8);
						row_8.setCellValue(nullToString(contact.getContacts())+"");
						row_8.setCellStyle(styleContent1_left);
						
						HSSFCell row_9 = row.createCell(9);
						row_9.setCellValue(nullToString(contact.getTelphone())+"");
						row_9.setCellStyle(styleContent1_left);
						
						HSSFCell row_10 = row.createCell(10);
						row_10.setCellValue(nullToString(contact.getFax())+"");
						row_10.setCellStyle(styleContent1_left);
						
						HSSFCell row_11 = row.createCell(11);
						row_11.setCellValue(nullToString(contact.getCellphone())+"");
						row_11.setCellStyle(styleContent1_left);
					}
					countRow ++ ;
				}
			}
			
			

			//第五部分、备品
			HSSFRow rowTitle6 = sheet.createRow(countRow); rowTitle6.setHeightInPoints(26.25f);
			HSSFCell rowTitle6_0 = rowTitle6.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(countRow,countRow,0,11));
			rowTitle6_0.setCellValue("第五部分、备品");
			rowTitle6_0.setCellStyle(styleTitle1Left);
			countRow ++ ;
			if(list9 != null && list9.size() > 0){
				for(BomMaterialRefDTO bomMaterialRefDTO : list9){
					//第五部分、备品
					HSSFRow row = sheet.createRow(countRow);// row.setHeightInPoints(26.25f);
					HSSFCell row_0 = row.createCell(0);
					row_0.setCellValue(nullToString(bomMaterialRefDTO.getSeq()+"")+"");
					row_0.setCellStyle(styleContent1);
					
					HSSFCell row_1 = row.createCell(1);
					row_1.setCellValue(nullToString(bomMaterialRefDTO.getMaterialCode())+"");
					row_1.setCellStyle(styleContent1);
					
					HSSFCell row_2 = row.createCell(2);
					row_2.setCellValue(nullToString(bomMaterialRefDTO.getProductName())+"");
					row_2.setCellStyle(styleContent1_left);
					
					HSSFCell row_3 = row.createCell(3);
					row_3.setCellValue(nullToString(bomMaterialRefDTO.getSpecification())+"");
					row_3.setCellStyle(styleContent1_left);
					
					HSSFCell row_4 = row.createCell(4);
					row_4.setCellValue(nullToString(bomMaterialRefDTO.getBrand())+"");
					row_4.setCellStyle(styleContent1);
					
					String usageAmount = "";
					if(bomMaterialRefDTO.getUsageAmount1() != null && bomMaterialRefDTO.getUsageAmount2() != null){
						usageAmount = FractionalUtil.showToString(bomMaterialRefDTO.getUsageAmount1(), bomMaterialRefDTO.getUsageAmount2())+"";
					}
					HSSFCell row_5 = row.createCell(5);
					row_5.setCellValue(usageAmount);
					row_5.setCellStyle(styleContent1);
					
					HSSFCell row_6 = row.createCell(6);
					row_6.setCellValue(nullToString(bomMaterialRefDTO.getDescription())+"");
					row_6.setCellStyle(styleContent1_left);
					
					countRow ++ ;
				}
			}
			
			
			
            CellRangeAddress c = CellRangeAddress.valueOf("A3:L3");
		    sheet.setAutoFilter(c);
			/*try { 
				//写到本地做测试用
				FileOutputStream fout = new FileOutputStream("F://test.xls");
			    wb.write(fout);
			    fout.close();
			} catch (Exception e) {  
			    e.printStackTrace();
			}*/
			return wb;
		}
		return null;
	}
	
	private static String nullToString(String src) {
		if(src == null || "null".equals(src)){
			src = "";
		}
		return src;
	}

	
	public static void main(String[] args) throws IOException {
		Boolean flag = -1 % 2 == 0;
		System.out.println(-5 % 2 );
	}
}
