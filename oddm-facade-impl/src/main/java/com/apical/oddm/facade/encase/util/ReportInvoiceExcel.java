package com.apical.oddm.facade.encase.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import com.apical.oddm.core.model.encase.InvoiceInfo;
import com.apical.oddm.core.model.encase.InvoiceList;
import com.apical.oddm.facade.util.TimeUtil;

public class ReportInvoiceExcel{

	public static HSSFWorkbook creatExcelInvoiceInfo(InvoiceInfo invoiceInfo,String imgPath) throws Exception  
    {  
		if(imgPath != null ){
			// 第一步，创建一个webbook，对应一个Excel文件
			HSSFWorkbook wb = new HSSFWorkbook();
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheetky
			HSSFSheet sheet = wb.createSheet("CI");//多次create可以追加， 删除removeSheetAt
			
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			////设置列宽
			double offset = 2;
			sheet.setColumnWidth(0,(int) ((13.38+offset) * 256));
	        sheet.setColumnWidth(1,(int) ((10.75+offset) * 256));
	        sheet.setColumnWidth(2,(int) ((35.13+offset) * 256));
	        sheet.setColumnWidth(3,(int) ((8.38+offset) * 256));
            sheet.setColumnWidth(4,(int) ((18+offset) * 256));
            sheet.setColumnWidth(5,(int) ((15.5+offset) * 256));
            sheet.setColumnWidth(6,(int) ((19.25+offset) * 256));
            sheet.setColumnWidth(7,(int) ((13.13+offset) * 256));
            
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));//起始行号，终止行号， 起始列号，终止列号     Shenzhen Apical Technology Co., Ltd
            sheet.addMergedRegion(new CellRangeAddress(1,1,0,5));//9F, Block B , Tsinghua Unis Infoport, LangShan Road, Hi-tech Industrial Park (North), Nanshan, Shenzhen, China
            sheet.addMergedRegion(new CellRangeAddress(2,2,0,2));//To:Stav Technologies Limited
            sheet.addMergedRegion(new CellRangeAddress(3,3,0,2));//Address:Rm 803, 8F, Yue Xiu Building, 160-174 Lockhart Road, Wan Chai, Hong Kong
            sheet.addMergedRegion(new CellRangeAddress(4,4,0,2));//Attn:Dmitry
            sheet.addMergedRegion(new CellRangeAddress(5,5,0,2));//Tel:+852-6888-6535  Fax:+852-2882-4450
            sheet.addMergedRegion(new CellRangeAddress(6,6,0,5));//Commercial Invoice
            sheet.addMergedRegion(new CellRangeAddress(7,7,0,2));//PRODUCT DESCRIPTION
            
            //设置行高
            HSSFRow row0 = sheet.createRow(0); row0.setHeightInPoints(22.5f);
            HSSFRow row1 = sheet.createRow(1); row1.setHeightInPoints(18f);
            HSSFRow row2 = sheet.createRow(2); row2.setHeightInPoints(15f);
            HSSFRow row3 = sheet.createRow(3); //row3.setHeightInPoints(30f);
            HSSFRow row4 = sheet.createRow(4); row4.setHeightInPoints(15f);
            HSSFRow row5 = sheet.createRow(5); row5.setHeightInPoints(15f);
            HSSFRow row6 = sheet.createRow(6); row6.setHeightInPoints(15f);
            HSSFRow row7 = sheet.createRow(7); row7.setHeightInPoints(21f);
            
            /***********大标题**************/
            HSSFCellStyle styleTitle = wb.createCellStyle();// CellStyle 
			styleTitle.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
			styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
			/*styleTitle.setBorderBottom(BorderStyle.NONE);
			styleTitle.setBorderTop(BorderStyle.NONE);
			styleTitle.setBorderLeft(BorderStyle.NONE);
			styleTitle.setBorderRight(BorderStyle.NONE);*/
			styleTitle.setWrapText(true);
			
			HSSFFont fontTitle = wb.createFont();
			fontTitle.setFontName("微软雅黑");
			fontTitle.setFontHeightInPoints((short) 16);// 设置字体大小
			fontTitle.setBold(false);
			styleTitle.setFont(fontTitle);
            
			/***********公司名和公司地址**************/
            HSSFCellStyle styleCompanyName = wb.createCellStyle();// CellStyle 
			styleCompanyName.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
			styleCompanyName.setVerticalAlignment(VerticalAlignment.CENTER);
			/*styleCompanyName.setBorderBottom(BorderStyle.NONE);
			styleCompanyName.setBorderTop(BorderStyle.NONE);
			styleCompanyName.setBorderLeft(BorderStyle.NONE);
			styleCompanyName.setBorderRight(BorderStyle.NONE);*/
			styleCompanyName.setWrapText(true);
			
			HSSFFont fontCompanyName = wb.createFont();
			fontCompanyName.setFontName("微软雅黑");
			fontCompanyName.setFontHeightInPoints((short) 10);// 设置字体大小
			fontTitle.setBold(false);
			styleCompanyName.setFont(fontCompanyName);
			
			/***********基础信息**************/
            HSSFCellStyle styleBaseMsg = wb.createCellStyle();// CellStyle 
			styleBaseMsg.setAlignment(HorizontalAlignment.LEFT);// 创建一个居中格式
			styleBaseMsg.setVerticalAlignment(VerticalAlignment.CENTER);
			/*styleBaseMsg.setBorderBottom(BorderStyle.NONE);
			styleBaseMsg.setBorderTop(BorderStyle.NONE);
			styleBaseMsg.setBorderLeft(BorderStyle.NONE);
			styleBaseMsg.setBorderRight(BorderStyle.NONE);*/
			styleBaseMsg.setWrapText(true);
			
			HSSFFont fontBaseMsg = wb.createFont();
			fontBaseMsg.setFontName("Calibri");
			fontBaseMsg.setFontHeightInPoints((short) 12);// 设置字体大小
			fontBaseMsg.setBold(false);
			styleBaseMsg.setFont(fontBaseMsg);
			
			HSSFCellStyle styleBaseMsgRight = wb.createCellStyle();// CellStyle 
			styleBaseMsgRight.setAlignment(HorizontalAlignment.RIGHT);// 创建一个居中格式
			styleBaseMsgRight.setVerticalAlignment(VerticalAlignment.CENTER);
			styleBaseMsgRight.setBorderBottom(BorderStyle.NONE);
			styleBaseMsgRight.setBorderTop(BorderStyle.NONE);
			styleBaseMsgRight.setBorderLeft(BorderStyle.NONE);
			styleBaseMsgRight.setBorderRight(BorderStyle.NONE);
			styleBaseMsgRight.setWrapText(true);
			styleBaseMsgRight.setFont(fontBaseMsg);
			
			/***********Commercial Invoice**************/
            HSSFCellStyle styleInvoice = wb.createCellStyle();// CellStyle 
			styleInvoice.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
			styleInvoice.setVerticalAlignment(VerticalAlignment.CENTER);
			styleInvoice.setBorderBottom(BorderStyle.NONE);
			styleInvoice.setBorderTop(BorderStyle.NONE);
			styleInvoice.setBorderLeft(BorderStyle.NONE);
			styleInvoice.setBorderRight(BorderStyle.NONE);
			styleInvoice.setWrapText(true);
			
			HSSFFont fontInvoice = wb.createFont();
			fontInvoice.setFontName("微软雅黑");
			fontInvoice.setFontHeightInPoints((short) 12);// 设置字体大小
			fontInvoice.setBold(true);
			styleInvoice.setFont(fontInvoice);
			
			/***********内容标题**************/
            HSSFCellStyle styleContentTitle = wb.createCellStyle();// CellStyle 
            styleContentTitle.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
            styleContentTitle.setVerticalAlignment(VerticalAlignment.CENTER);
            styleContentTitle.setBorderBottom(BorderStyle.THIN);
            styleContentTitle.setBorderTop(BorderStyle.THIN);
            styleContentTitle.setBorderLeft(BorderStyle.THIN);
            styleContentTitle.setBorderRight(BorderStyle.THIN);
            styleContentTitle.setWrapText(true);
			
			HSSFFont fontContentTitle = wb.createFont();
			fontContentTitle.setFontName("微软雅黑");
			fontContentTitle.setFontHeightInPoints((short) 10);// 设置字体大小
			fontContentTitle.setBold(true);
			styleContentTitle.setFont(fontContentTitle);
			
			HSSFCellStyle styleContentTitleLeft = wb.createCellStyle();// CellStyle 
			styleContentTitleLeft.setAlignment(HorizontalAlignment.LEFT);// 创建一个居中格式
			styleContentTitleLeft.setVerticalAlignment(VerticalAlignment.CENTER);
			styleContentTitleLeft.setBorderBottom(BorderStyle.THIN);
			styleContentTitleLeft.setBorderTop(BorderStyle.THIN);
			styleContentTitleLeft.setBorderLeft(BorderStyle.THIN);
			styleContentTitleLeft.setBorderRight(BorderStyle.THIN);
			styleContentTitleLeft.setWrapText(true);
			styleContentTitleLeft.setFont(fontContentTitle);
			
			HSSFCellStyle styleContentTitleNoBord = wb.createCellStyle();// CellStyle 
			styleContentTitleNoBord.setAlignment(HorizontalAlignment.LEFT);// 创建一个居中格式
			styleContentTitleNoBord.setVerticalAlignment(VerticalAlignment.CENTER);
			styleContentTitleNoBord.setBorderBottom(BorderStyle.NONE);
			styleContentTitleNoBord.setBorderTop(BorderStyle.NONE);
			styleContentTitleNoBord.setBorderLeft(BorderStyle.NONE);
			styleContentTitleNoBord.setBorderRight(BorderStyle.NONE);
			styleContentTitleNoBord.setWrapText(true);
			styleContentTitleNoBord.setFont(fontContentTitle);
			
			/***********内容**************/
            HSSFCellStyle styleContentMsg = wb.createCellStyle();// CellStyle 
            styleContentMsg.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
            styleContentMsg.setVerticalAlignment(VerticalAlignment.CENTER);
            styleContentMsg.setBorderLeft(BorderStyle.THIN);
            styleContentMsg.setBorderRight(BorderStyle.THIN);
            styleContentMsg.setWrapText(true);
			
			HSSFFont fontContentMsg = wb.createFont();
			fontContentMsg.setFontName("Calibri");
			fontContentMsg.setFontHeightInPoints((short) 11);// 设置字体大小
			fontContentMsg.setBold(false);
			styleContentMsg.setFont(fontContentMsg);
			
			HSSFCellStyle styleContentMsgBold = wb.createCellStyle();// CellStyle 
			styleContentMsgBold.setAlignment(HorizontalAlignment.LEFT);// 创建一个居中格式
			styleContentMsgBold.setVerticalAlignment(VerticalAlignment.CENTER);
			styleContentMsgBold.setBorderLeft(BorderStyle.THIN);
            styleContentMsgBold.setBorderRight(BorderStyle.THIN);
            styleContentMsgBold.setWrapText(true);
			
			HSSFFont fontContentMsgBold = wb.createFont();
			fontContentMsgBold.setFontName("Calibri");
			fontContentMsgBold.setFontHeightInPoints((short) 11);// 设置字体大小
			fontContentMsgBold.setBold(true);
			styleContentMsgBold.setFont(fontContentMsgBold);
			
			HSSFCellStyle styleContentMsgLeft = wb.createCellStyle();// CellStyle 
			styleContentMsgLeft.setAlignment(HorizontalAlignment.LEFT);// 创建一个居中格式
			styleContentMsgLeft.setVerticalAlignment(VerticalAlignment.CENTER);
			styleContentMsgLeft.setBorderLeft(BorderStyle.THIN);
			styleContentMsgLeft.setBorderRight(BorderStyle.THIN);
			styleContentMsgLeft.setWrapText(true);
			styleContentMsgLeft.setFont(fontContentMsg);
			
			HSSFCellStyle styleContentMsgRight = wb.createCellStyle();// CellStyle 
			styleContentMsgRight.setAlignment(HorizontalAlignment.RIGHT);// 创建一个居中格式
			styleContentMsgRight.setVerticalAlignment(VerticalAlignment.CENTER);
			styleContentMsgRight.setBorderLeft(BorderStyle.THIN);
			styleContentMsgRight.setBorderRight(BorderStyle.THIN);
			styleContentMsgRight.setWrapText(true);
			styleContentMsgRight.setFont(fontContentMsg);
			
			HSSFCellStyle styleContentMsgForAmount = wb.createCellStyle();// CellStyle 
			styleContentMsgForAmount.setAlignment(HorizontalAlignment.RIGHT);// 创建一个居中格式
			styleContentMsgForAmount.setVerticalAlignment(VerticalAlignment.CENTER);
			styleContentMsgForAmount.setBorderBottom(BorderStyle.THIN);
			styleContentMsgForAmount.setBorderTop(BorderStyle.THIN);
			styleContentMsgForAmount.setBorderLeft(BorderStyle.THIN);
			styleContentMsgForAmount.setBorderRight(BorderStyle.THIN);
			styleContentMsgForAmount.setWrapText(true);
			styleContentMsgForAmount.setFont(fontContentMsg);
			
			HSSFCellStyle styleContentMsgNoborder = wb.createCellStyle();// CellStyle 
			styleContentMsgNoborder.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
			styleContentMsgNoborder.setVerticalAlignment(VerticalAlignment.CENTER);
			styleContentMsgNoborder.setWrapText(true);
			styleContentMsgNoborder.setFont(fontContentMsg);
			
			if(invoiceInfo != null){
				
				HSSFCell cell0_0 = row0.createCell(0);	
				cell0_0.setCellValue(nullToString(invoiceInfo.getCompanyName()));
				cell0_0.setCellStyle(styleTitle);
				
				HSSFCell cell1_0 = row1.createCell(0);
				cell1_0.setCellValue(nullToString(invoiceInfo.getCompanyAddr()));
				cell1_0.setCellStyle(styleCompanyName);
				
				HSSFCell cell2_0 = row2.createCell(0);
				cell2_0.setCellValue(nullToString("To:"+invoiceInfo.getTo_()));
				cell2_0.setCellStyle(styleBaseMsg);
				HSSFCell cell2_4 = row2.createCell(4);
				cell2_4.setCellValue("DATE:");
				cell2_4.setCellStyle(styleBaseMsgRight);
				HSSFCell cell2_5 = row2.createCell(5);
				cell2_5.setCellValue(nullToString(TimeUtil.dateToString(invoiceInfo.getDateInvoice())));
				cell2_5.setCellStyle(styleBaseMsg);
				
				HSSFCell cell3_0 = row3.createCell(0);
				cell3_0.setCellValue("Address:"+nullToString(invoiceInfo.getAddress()));
				cell3_0.setCellStyle(styleBaseMsg);
				HSSFCell cell3_4 = row3.createCell(4);
				cell3_4.setCellValue("P/I NO.:");
				cell3_4.setCellStyle(styleBaseMsgRight);
				HSSFCell cell3_5 = row3.createCell(5);
				cell3_5.setCellValue(nullToString(invoiceInfo.getPiNo()));
				cell3_5.setCellStyle(styleBaseMsg);
				
				HSSFCell cell4_0 = row4.createCell(0);
				cell4_0.setCellValue("Attn:"+nullToString(invoiceInfo.getPiNo()));
				cell4_0.setCellStyle(styleBaseMsg);
				
				HSSFCell cell5_0 = row5.createCell(0);
				cell5_0.setCellValue("Tel:"+nullToString(invoiceInfo.getTel())+"  Fax:"+nullToString(invoiceInfo.getFax()));
				cell5_0.setCellStyle(styleBaseMsg);
				
				HSSFCell cell6_0 = row6.createCell(0);
				cell6_0.setCellValue("Commercial Invoice");
				cell6_0.setCellStyle(styleInvoice);
				
				HSSFCell cell7_0 = row7.createCell(0);
				cell7_0.setCellValue("PRODUCT DESCRIPTION");
				cell7_0.setCellStyle(styleContentTitleLeft);
				//设置边框
				HSSFCell row7_1 = row7.createCell(1);
				row7_1.setCellStyle(styleContentTitleLeft);
				HSSFCell row7_2 = row7.createCell(2);
				row7_2.setCellStyle(styleContentTitleLeft);

				HSSFCell cell7_3 = row7.createCell(3);
				cell7_3.setCellValue("QTY");
				cell7_3.setCellStyle(styleContentTitle);
				HSSFCell cell7_4 = row7.createCell(4);
				cell7_4.setCellValue("UNIT PRICE("+nullToString(invoiceInfo.getCurrency())+")");
				cell7_4.setCellStyle(styleContentTitle);
				HSSFCell cell7_5 = row7.createCell(5);
				cell7_5.setCellValue("AMOUNT("+nullToString(invoiceInfo.getCurrency())+")");
				cell7_5.setCellStyle(styleContentTitle);
				
				int countRow = 8;//统计当前为多少行
				HSSFRow row = null;
				if(invoiceInfo.getInvoiceLists() != null && invoiceInfo.getInvoiceLists().size() > 0){
					Set<InvoiceList> invoiceLists = invoiceInfo.getInvoiceLists();
					for(InvoiceList invoiceList : invoiceLists){
						row = sheet.createRow(countRow); 
						HSSFRow createRow = sheet.createRow(countRow); 
						
						int size = 6;
			            String description = invoiceList.getDescription();
			            String[] split = null;
			            if (description != null) {
			            	split = description.split("\n");
			            	if (split.length > size) size = split.length;
			            }
						
			            for(int i = 0; i<size+1 ;i++){
			            	sheet.addMergedRegion(new CellRangeAddress(countRow+i,countRow+i,0,2));//起始行号，终止行号， 起始列号，终止列号
			            	row = sheet.createRow(countRow+i); 
			            	HSSFCell cell = row.createCell(0);
			            	if (split != null && i<split.length) cell.setCellValue(nullToString(split[i]));
			                else cell.setCellValue("");
			            	if(i == 0){
			            		cell.setCellStyle(styleContentMsgBold);
			            	}else{
				            	cell.setCellStyle(styleContentMsgLeft);
			            	}
			            	
			            	//设置边框
							HSSFCell row_1 = row.createCell(1);
							row_1.setCellStyle(styleContentMsg);
							HSSFCell row_2 = row.createCell(2);
							row_2.setCellStyle(styleContentMsg);
							HSSFCell row_31 = row.createCell(3);
							row_31.setCellStyle(styleContentMsg);
							HSSFCell row_41 = row.createCell(4);
							row_41.setCellStyle(styleContentMsg);
							HSSFCell row_51 = row.createCell(5);
							row_51.setCellStyle(styleContentMsg);
			            }
						
			            HSSFCell row_3 = createRow.createCell(3);
						if(invoiceList.getQty() != null){
							row_3.setCellValue(invoiceList.getQty());
						}else {
							row_3.setCellValue(0);
						}
						row_3.setCellStyle(styleContentMsg);
						
						HSSFCell row_4 = createRow.createCell(4);
						row_4.setCellValue(invoiceList.getUnitPrice().doubleValue());
						row_4.setCellStyle(styleContentMsg);
						
						HSSFCell row_5 = createRow.createCell(5);
						if(invoiceList.getQty() != null && invoiceList.getUnitPrice() != null){
							int next = countRow + 1;
							row_5.setCellFormula("PRODUCT(D"+next+",E"+next+")");
						}
						else row_5.setCellValue(0.00);
						row_5.setCellStyle(styleContentMsg);
						
						HSSFCell row_6 = createRow.createCell(6);
						row_6.setCellValue(nullToString(invoiceList.getOrderNo())+"-"+nullToString(invoiceList.getModel())/*+"-"+invoiceList.getQty()+"台"*/);
						row_6.setCellStyle(styleContentMsgNoborder);
						
						countRow += size+1;
					}
				}
				HSSFCell row_0 = null;
				HSSFCell row_1 = null;
				HSSFCell row_2 = null;
				HSSFCell row_3 = null;
				HSSFCell row_4 = null;
				HSSFCell row_5 = null;
				//int next = countRow + 1;
				row = sheet.createRow(countRow); row.setHeightInPoints(15f);
				sheet.addMergedRegion(new CellRangeAddress(countRow,countRow,0,2));//起始行号，终止行号， 起始列号，终止列号
				row_0 = row.createCell(0);
				row_0.setCellValue("Amount:");
				row_0.setCellStyle(styleContentMsgForAmount);
				row_1 = row.createCell(1);//设置边框
				row_1.setCellStyle(styleContentMsgForAmount);
				row_2 = row.createCell(2);//设置边框
				row_2.setCellStyle(styleContentMsgForAmount);
				row_3 = row.createCell(3);
				row_3.setCellFormula("SUM(D9:D"+countRow+")");
				row_3.setCellStyle(styleContentMsgForAmount);
				row_4 = row.createCell(4);//设置边框
				row_4.setCellStyle(styleContentMsgForAmount);
				row_5 = row.createCell(5);
				row_5.setCellFormula(/*toChar(nullToString(invoiceInfo.getCurrency()))+*/"SUM(F9:F"+countRow+")");
				row_5.setCellStyle(styleContentMsgForAmount);
				
				countRow ++ ;
				//末尾
				row = sheet.createRow(countRow); row.setHeightInPoints(15f);
				sheet.addMergedRegion(new CellRangeAddress(countRow,countRow,0,2));//起始行号，终止行号， 起始列号，终止列号
				row_0 = row.createCell(0);
				row_0.setCellValue("Shipping Mark /brand:"+nullToString(invoiceInfo.getBrand()));
				row_0.setCellStyle(styleContentTitleNoBord);
				countRow ++ ;
				
				row = sheet.createRow(countRow); row.setHeightInPoints(15f);
				sheet.addMergedRegion(new CellRangeAddress(countRow,countRow,0,2));//起始行号，终止行号， 起始列号，终止列号
				row_0 = row.createCell(0);
				row_0.setCellValue("Shipping Method:"+nullToString(invoiceInfo.getShippingMethod()));
				row_0.setCellStyle(styleContentTitleNoBord);
				countRow ++ ;
				
				row = sheet.createRow(countRow); row.setHeightInPoints(15f);
				sheet.addMergedRegion(new CellRangeAddress(countRow,countRow,0,2));//起始行号，终止行号， 起始列号，终止列号
				row_0 = row.createCell(0);
				row_0.setCellValue("Incoterms:"+nullToString(invoiceInfo.getIncoterms()));
				row_0.setCellStyle(styleContentTitleNoBord);
				countRow ++ ;
				
				row = sheet.createRow(countRow); row.setHeightInPoints(15f);
				sheet.addMergedRegion(new CellRangeAddress(countRow,countRow,0,2));//起始行号，终止行号， 起始列号，终止列号
				row_0 = row.createCell(0);
				row_0.setCellValue("Origion:"+nullToString(invoiceInfo.getOrigion()));
				row_0.setCellStyle(styleContentTitleNoBord);
				countRow ++ ;
				
				row = sheet.createRow(countRow); row.setHeightInPoints(15f);
				sheet.addMergedRegion(new CellRangeAddress(countRow,countRow,0,2));//起始行号，终止行号， 起始列号，终止列号
				row_0 = row.createCell(0);
				row_0.setCellValue("Payment:"+nullToString(invoiceInfo.getPayment()));
				row_0.setCellStyle(styleContentTitleNoBord);
				countRow ++ ;
				
			}
			
			BufferedImage bufferImg = null;    
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();     
            bufferImg = ImageIO.read(new File(imgPath));     
            ImageIO.write(bufferImg, "png", byteArrayOut); 
            //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）  
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();     
            //anchor主要用于设置图片的属性  
            HSSFClientAnchor anchor = new HSSFClientAnchor(500, 0, 500, 255,(short) 0, 0, (short) 1, 0);     
            anchor.setAnchorType(3);     
            //插入图片    
            patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
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
	/*private static String toChar(String key) {
		switch (key) {
			case "USD":
				return "$";
			case "RMB":
				return "￥";
			case "EUR":
				return "€";
			case "GBP":
				return "￡";
			default:
				return "$";
		}
	}*/
	public static void main(String[] args) throws Exception {
		

		System.out.println("success");
	}

}
