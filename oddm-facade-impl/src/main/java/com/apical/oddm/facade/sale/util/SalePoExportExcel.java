package com.apical.oddm.facade.sale.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;

import com.apical.oddm.facade.sale.po.dto.SalePoDto;
import com.apical.oddm.facade.sale.po.dto.SalePoListDto;

/**
 * 内部订单导出Excel
 * @author lgx
 * 2016-2-10
 */
public class SalePoExportExcel {

	public static HSSFWorkbook export(SalePoDto salePoDto,String picturePath) throws IOException {
		if (picturePath != null) {
			// 第一步，创建一个webbook，对应一个Excel文件
			HSSFWorkbook wb = new HSSFWorkbook();
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheetky
			HSSFSheet sheet = wb.createSheet(salePoDto.getOrderNo());//多次create可以追加， 删除removeSheetAt
			
			sheet.setDefaultRowHeightInPoints(21f);//默认行高 ，默认列宽setDefaultColumnWidth  8.43
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
          
            //设置列宽， 设置行高setHeight
			double offset = 2;
			sheet.autoSizeColumn((short)0);//自动宽度（但是单元格自动换行的时候，只适应最长的那部分字）
            //sheet.setColumnWidth(0,(int) ((length+offset) * 256));//0.72为偏移值（具体没深究，网上给的数据）
            sheet.setColumnWidth(1,(int) ((10.5+offset) * 256));
            sheet.setColumnWidth(2,(int) ((27.75+offset) * 256));
            sheet.setColumnWidth(3,(int) ((7.25+offset) * 256));
            sheet.setColumnWidth(4,(int) ((8+offset) * 256));
            sheet.setColumnWidth(5,(int) ((12.88+offset) * 256));
            sheet.setColumnWidth(6,(int) ((10.5+offset) * 256));
            sheet.setColumnWidth(7,(int) ((19.25+offset) * 256));
            //sheet.autoSizeColumn((short)3);//调整第四列宽度
            //合并单元格
            //表头 
            sheet.addMergedRegion(new CellRangeAddress(1,1,0,7));
            sheet.addMergedRegion(new CellRangeAddress(2,2,0,7));
            sheet.addMergedRegion(new CellRangeAddress(3,3,0,7));
            sheet.addMergedRegion(new CellRangeAddress(4,4,0,7));
            //内容
            //客户名称一行
            sheet.addMergedRegion(new CellRangeAddress(5,5,0,1));
            sheet.addMergedRegion(new CellRangeAddress(5,5,2,3));
            //业务划分一行
            sheet.addMergedRegion(new CellRangeAddress(6,7,0,1));
            sheet.addMergedRegion(new CellRangeAddress(6,6,2,5));
            sheet.addMergedRegion(new CellRangeAddress(7,7,3,5));
            sheet.addMergedRegion(new CellRangeAddress(6,7,6,6));
            sheet.addMergedRegion(new CellRangeAddress(6,7,7,7));
            //预计验货日期
            sheet.addMergedRegion(new CellRangeAddress(8,8,0,1));
            sheet.addMergedRegion(new CellRangeAddress(8,8,2,5));
            //加工工厂
            sheet.addMergedRegion(new CellRangeAddress(9,9,0,1));
            sheet.addMergedRegion(new CellRangeAddress(9,9,2,7));
            
            /*********统一创建行：************/
			HSSFRow row2 = sheet.createRow(1); row2.setHeightInPoints(24f); //第二行 
			HSSFRow row3 = sheet.createRow(2); row3.setHeightInPoints(21f);
			HSSFRow row4 = sheet.createRow(3); row4.setHeightInPoints(21f);
			HSSFRow row5 = sheet.createRow(4); row5.setHeightInPoints(34f);
			HSSFRow row6 = sheet.createRow(5); row6.setHeightInPoints(32f);
			HSSFRow row7 = sheet.createRow(6); row7.setHeightInPoints(32f);
			HSSFRow row8 = sheet.createRow(7); row8.setHeightInPoints(32f);
			HSSFRow row9 = sheet.createRow(8); row9.setHeightInPoints(32f);
			HSSFRow row10 = sheet.createRow(9); row10.setHeightInPoints(24f);
			HSSFRow row11 = sheet.createRow(10); row11.setHeightInPoints(24f);

			/***********客户名称 等标题**************/
			HSSFCellStyle styleTitle2 = wb.createCellStyle();// CellStyle 
			styleTitle2.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
			styleTitle2.setVerticalAlignment(VerticalAlignment.CENTER);
			styleTitle2.setBorderBottom(BorderStyle.THIN);
			styleTitle2.setBorderTop(BorderStyle.THIN);
			styleTitle2.setBorderLeft(BorderStyle.THIN);
			styleTitle2.setBorderRight(BorderStyle.THIN);
            
			HSSFFont fontTitle2 = wb.createFont();
			fontTitle2.setFontName("微软雅黑");
			fontTitle2.setFontHeightInPoints((short) 10);// 设置字体大小
			fontTitle2.setBold(true);
			styleTitle2.setFont(fontTitle2);
				
			/***********备注描述 换行且加粗**************/
			HSSFCellStyle styleDescription = wb.createCellStyle();// CellStyle 
			styleDescription.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
			styleDescription.setVerticalAlignment(VerticalAlignment.CENTER);
			styleDescription.setBorderBottom(BorderStyle.THIN);
			styleDescription.setBorderTop(BorderStyle.THIN);
			styleDescription.setBorderLeft(BorderStyle.THIN);
			styleDescription.setBorderRight(BorderStyle.THIN);
            
			HSSFFont fontstyleDescription = wb.createFont();
			fontstyleDescription.setFontName("微软雅黑");
			fontstyleDescription.setFontHeightInPoints((short) 10);// 设置字体大小
			fontstyleDescription.setBold(true);
			styleDescription.setFont(fontstyleDescription);
			styleDescription.setWrapText(true);
			
			/**********正文内容，部分标题和正文内容一致**********/
            HSSFCellStyle styleBody = wb.createCellStyle();// CellStyle 
            styleBody.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
			styleBody.setVerticalAlignment(VerticalAlignment.CENTER);
			styleBody.setBorderBottom(BorderStyle.THIN);
			styleBody.setBorderTop(BorderStyle.THIN);
			styleBody.setBorderLeft(BorderStyle.THIN);
			styleBody.setBorderRight(BorderStyle.THIN);
			
			HSSFFont fontBody = wb.createFont();
			fontBody.setFontName("微软雅黑");
			fontBody.setFontHeightInPoints((short) 10);// 设置字体大小
			styleBody.setFont(fontBody);
			styleBody.setWrapText(true);//换行

			/*********日期格式化****************/
			HSSFCellStyle styleDate = wb.createCellStyle();// CellStyle 
			styleDate.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
			styleDate.setVerticalAlignment(VerticalAlignment.CENTER);
			styleDate.setBorderBottom(BorderStyle.THIN);
			styleDate.setBorderTop(BorderStyle.THIN);
			styleDate.setBorderLeft(BorderStyle.THIN);
			styleDate.setBorderRight(BorderStyle.THIN);
            
			HSSFFont fontDate = wb.createFont();
			fontDate.setFontName("微软雅黑");
			fontDate.setFontHeightInPoints((short) 10);// 设置字体大小
			styleDate.setFont(fontDate);
			DataFormat formatDate = wb.createDataFormat();
			styleDate.setDataFormat(formatDate.getFormat("yyyy年M月d日"));//yyyy年MM月dd日 自动补0
			
			/*********格式化2位小数****************/
			HSSFCellStyle styleDec2 = wb.createCellStyle();// CellStyle 
			styleDec2.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
			styleDec2.setVerticalAlignment(VerticalAlignment.CENTER);
			styleDec2.setBorderBottom(BorderStyle.THIN);
			styleDec2.setBorderTop(BorderStyle.THIN);
			styleDec2.setBorderLeft(BorderStyle.THIN);
			styleDec2.setBorderRight(BorderStyle.THIN);
			
			HSSFFont fontDec2 = wb.createFont();
			fontDec2.setFontName("微软雅黑");
			fontDec2.setFontHeightInPoints((short) 10);// 设置字体大小
			styleDec2.setFont(fontDec2);
			HSSFDataFormat formatDec2 = wb.createDataFormat();   
			styleDec2.setDataFormat(formatDec2.getFormat("#,##0.00")); 
			
			
            //格式化 设置文本的值  HSSFCellStyle不同风格则另外createCellStyle一个
			/*********深圳市爱培科技术股份有限公司*********/
            HSSFCellStyle styleCompany = wb.createCellStyle();// CellStyle 
            styleCompany.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
            styleCompany.setVerticalAlignment(VerticalAlignment.CENTER);
			HSSFFont fontCompany = wb.createFont();
			fontCompany.setFontName("微软雅黑");
			fontCompany.setFontHeightInPoints((short) 16);// 设置字体大小
			fontCompany.setBold(true);;
			styleCompany.setFont(fontCompany);
			HSSFCell cellCompany = row2.createCell(0);
			if (salePoDto.getCompany() != null)
			cellCompany.setCellValue(salePoDto.getCompany());
			cellCompany.setCellStyle(styleCompany);
            
			/*****SHENZHEN APICAL TECHNOLOGY CO., LTD*****/
            HSSFCellStyle styleAddr = wb.createCellStyle();// CellStyle 
            styleAddr.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
			styleAddr.setVerticalAlignment(VerticalAlignment.CENTER);
			HSSFFont fontAddr = wb.createFont();
			fontAddr.setFontName("微软雅黑");
			fontAddr.setFontHeightInPoints((short) 11);// 设置字体大小
			styleAddr.setFont(fontAddr);
			HSSFCell cellAddr = row3.createCell(0);
			if (salePoDto.getAddress() != null)
			cellAddr.setCellValue(salePoDto.getAddress());
			cellAddr.setCellStyle(styleAddr);
			
			/*****电话:0755-61613205   传真：0755-61613210      网址:www.apical-hk.com*****/
			HSSFCell cellTel = row4.createCell(0);
			cellTel.setCellValue("电话:"+salePoDto.getTel()+"   传真:"+salePoDto.getFax()+"   网址:"+salePoDto.getHomepage());
			cellTel.setCellStyle(styleAddr);
			
			/**********Purchase Order*************/
			HSSFCellStyle styleTitle = wb.createCellStyle();// CellStyle 
			styleTitle.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
            styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
			HSSFFont fontTitle = wb.createFont();
			fontTitle.setFontName("微软雅黑");
			fontTitle.setFontHeightInPoints((short) 16);// 设置字体大小
			fontTitle.setBold(true);;
			styleTitle.setFont(fontTitle);
			HSSFCell cellTitle = row5.createCell(0);
			cellTitle.setCellValue("Purchase Order");
			cellTitle.setCellStyle(styleTitle);
			
			/***********第6行*************/
			HSSFCell cell6 = row6.createCell(0);
			cell6.setCellValue("客户名称");
			cell6.setCellStyle(styleTitle2);
			
			cell6 = row6.createCell(1); //仅仅是设置边框
			cell6.setCellStyle(styleTitle2);
			
			cell6 = row6.createCell(2);
			if (salePoDto.getClientName() != null)
			cell6.setCellValue(salePoDto.getClientName());
			cell6.setCellStyle(styleBody);
			
			cell6 = row6.createCell(3); //仅仅是设置边框
			cell6.setCellStyle(styleBody);
			
			cell6 = row6.createCell(4);
			cell6.setCellValue("客户代码");
			cell6.setCellStyle(styleBody);
			
			cell6 = row6.createCell(5);
			if (salePoDto.getClientNameCode() != null)
			cell6.setCellValue(salePoDto.getClientNameCode());
			cell6.setCellStyle(styleBody);
			
			cell6 = row6.createCell(6);
			cell6.setCellValue("订单编号");
			cell6.setCellStyle(styleTitle2);
		
			cell6 = row6.createCell(7);
			if (salePoDto.getPiNo() != null) {
				cell6.setCellValue(salePoDto.getOrderNo()+"\n("+salePoDto.getPiNo()+")");
			} else {
				if (salePoDto.getOrderNo() != null) {
					cell6.setCellValue(salePoDto.getOrderNo());
				}
			}
			cell6.setCellStyle(styleBody);
			/***********第7、8行*************/
			HSSFCell cell7 = row7.createCell(0);
			cell7.setCellValue("业务划分");
			cell7.setCellStyle(styleTitle2);
			
			cell7 = row7.createCell(1); //仅仅是设置边框
			cell7.setCellStyle(styleTitle2);
			
			cell7 = row7.createCell(2);
			String seller = salePoDto.getSeller()==null?"":salePoDto.getSeller();
			cell7.setCellValue("销售经理："+seller);
			cell7.setCellStyle(styleBody);
			
			HSSFCell cell8 = row8.createCell(0); //仅仅是设置边框
			cell8.setCellStyle(styleTitle2);
			
			cell8 = row8.createCell(2);
			String pm = salePoDto.getPm()==null?"":salePoDto.getPm();
			cell8.setCellValue("产品经理："+pm);
			cell8.setCellStyle(styleBody);
			cell8 = row8.createCell(3);
			
			String merchandiser = salePoDto.getMerchandiser()==null?"":salePoDto.getMerchandiser();
			cell8.setCellValue("跟单："+merchandiser);
			cell8.setCellStyle(styleBody);
			
			cell8 = row8.createCell(4); //仅仅是设置边框
			cell8.setCellStyle(styleBody);
			cell8 = row8.createCell(5); //仅仅是设置边框
			cell8.setCellStyle(styleBody);
			cell8 = row8.createCell(6); //仅仅是设置边框
			cell8.setCellStyle(styleBody);
			cell8 = row8.createCell(7); //仅仅是设置边框
			cell8.setCellStyle(styleBody);
			
			cell7 = row7.createCell(6);
			cell7.setCellValue("下单日期");
			cell7.setCellStyle(styleTitle2);
			
			cell7 = row7.createCell(7);
			cell7.setCellValue(salePoDto.getDateOrder());
			cell7.setCellStyle(styleDate);
			
			/***********第9、10行*************/
			HSSFCell cell9 = row9.createCell(0);
			cell9.setCellValue("预计验货日期");
			cell9.setCellStyle(styleTitle2);
			
			cell9 = row9.createCell(1); //仅仅是设置边框
			cell9.setCellStyle(styleTitle2);
			
			cell9 = row9.createCell(2);
			if (salePoDto.getDateExaminePre() != null)
			cell9.setCellValue(salePoDto.getDateExaminePre());
			cell9.setCellStyle(styleDate);
			
			cell9 = row9.createCell(3); //仅仅是设置边框
			cell9.setCellStyle(styleDate);
			
			cell9 = row9.createCell(4); //仅仅是设置边框
			cell9.setCellStyle(styleDate);
			
			cell9 = row9.createCell(5); //仅仅是设置边框
			cell9.setCellStyle(styleDate);
			
			cell9 = row9.createCell(6);
			cell9.setCellValue("交货日期");
			cell9.setCellStyle(styleTitle2);
			
			cell9 = row9.createCell(7);
			if (salePoDto.getDateDelivery() != null)
			cell9.setCellValue(salePoDto.getDateDelivery());
			cell9.setCellStyle(styleDate);
			
			HSSFCell cell10 = row10.createCell(0);
			cell10.setCellValue("加工工厂");
			cell10.setCellStyle(styleTitle2);
			
			cell10 = row10.createCell(1); //仅仅是设置边框
			cell10.setCellStyle(styleTitle2);
			cell10 = row10.createCell(7); //仅仅是设置边框
			cell10.setCellStyle(styleTitle2);
			
			/***********第11行*************/
			HSSFCell cell11 = row11.createCell(0);
			cell11.setCellValue("NO.");
			cell11.setCellStyle(styleTitle2);
			
			cell11 = row11.createCell(1);
			cell11.setCellValue("产品名称");
			cell11.setCellStyle(styleTitle2);
			
			cell11 = row11.createCell(2);
			cell11.setCellValue("型号规格");
			cell11.setCellStyle(styleTitle2);
			
			cell11 = row11.createCell(3);
			cell11.setCellValue("数量");
			cell11.setCellStyle(styleTitle2);
			
			cell11 = row11.createCell(4);
			cell11.setCellValue("币种");
			cell11.setCellStyle(styleTitle2);
			
			cell11 = row11.createCell(5);
			if (salePoDto.getCurrency() != null) {
				cell11.setCellValue("单价（"+salePoDto.getCurrency()+"）");
			} else {
				cell11.setCellValue("单价");
			}
			cell11.setCellStyle(styleTitle2);
			
			cell11 = row11.createCell(6);
			cell11.setCellValue("金额");
			cell11.setCellStyle(styleTitle2);
			
			cell11 = row11.createCell(7);
			cell11.setCellValue("备注");
			cell11.setCellStyle(styleTitle2);
			/***********第12~行 数据填充*************/
			Set<SalePoListDto> salePoListsDto = salePoDto.getSalePoListsDto();
			HSSFRow row = null;
			int i = 0;
			if (salePoListsDto != null && !salePoListsDto.isEmpty()) {
				for (SalePoListDto list : salePoListsDto) {
					i ++;
					row = sheet.createRow(10+i); row.setHeightInPoints(52f);
					HSSFCell cell = row.createCell(0);
					if (list.getNumber() != null)
					cell.setCellValue(list.getNumber());
					cell.setCellStyle(styleBody);
					
					cell = row.createCell(1);
					if (list.getProductName() != null)
					cell.setCellValue(list.getProductName());
					cell.setCellStyle(styleBody);
					
					cell = row.createCell(2);
					if (list.getSpecification() != null)
					cell.setCellValue(list.getSpecification());
					cell.setCellStyle(styleBody);
					
					cell = row.createCell(3);
					if (list.getQuality() != null)
					cell.setCellValue(list.getQuality());
					cell.setCellStyle(styleBody);
					
					cell = row.createCell(4);
					if (salePoDto.getCurrency() != null)
					cell.setCellValue(salePoDto.getCurrency());
					cell.setCellStyle(styleBody);
					
					cell = row.createCell(5);
					if (list.getUnitPrice() != null)
					cell.setCellValue(list.getUnitPrice()+"");
					cell.setCellStyle(styleDec2);
					
					cell = row.createCell(6);
					cell.setCellFormula("D"+(11+i)+"*F"+(11+i));//公式
					cell.setCellStyle(styleDec2);
					
					cell = row.createCell(7);
					if (list.getDescription() != null)
					cell.setCellValue(list.getDescription());
					cell.setCellStyle(styleDescription);
				}
			} else {//至少保留1行
				for (int x = 0; x<1; x++ ) {
					i ++;
					row = sheet.createRow(10+i); row.setHeightInPoints(52f);
					HSSFCell cell = row.createCell(0);
					cell.setCellStyle(styleBody);
					
					cell = row.createCell(1);
					cell.setCellStyle(styleBody);
					
					cell = row.createCell(2);
					cell.setCellStyle(styleBody);
					
					cell = row.createCell(3);
					cell.setCellValue(0);
					cell.setCellStyle(styleBody);
					
					cell = row.createCell(4);
					if (salePoDto.getCurrency() != null)
					cell.setCellValue(salePoDto.getCurrency());
					cell.setCellStyle(styleBody);
					
					cell = row.createCell(5);
					cell.setCellValue(0);
					cell.setCellStyle(styleDec2);
					
					cell = row.createCell(6);
					cell.setCellFormula("D"+(11+i)+"*F"+(11+i));//公式
					cell.setCellStyle(styleDec2);
					
					cell = row.createCell(7);
					cell.setCellStyle(styleDescription);
				}
			}
			/**********合计(美金大写)：************/
            sheet.addMergedRegion(new CellRangeAddress(11+i,11+i,0,1));//合并单元格
            sheet.addMergedRegion(new CellRangeAddress(11+i,11+i,2,4));
			row = sheet.createRow(11+i); row.setHeightInPoints(24f);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue("合计(美金大写)：");
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(1); //仅仅是设置边框
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(5);
			cell.setCellValue("(小写)");
			cell.setCellStyle(styleTitle2);
			
			cell = row.createCell(6);
			int rowTotalIndex = cell.getRowIndex()+1;
			//System.err.println(cell.getRowIndex());
			String formula = "";
			for (int j=0; j<i;j++) {
				if(j == 0) {//第一个
					formula += "SUM(G"+(12+j);
				} else {
					formula += ":G"+(12+j);
				}
				if ((j+1) == i) {//最后一个
					formula += ")";
				}
			}
			if (!"".equals(formula)) cell.setCellFormula(formula);//公式
			cell.setCellStyle(styleDec2);
				
			cell = row.createCell(2);
			cell.setCellFormula("SUBSTITUTE(SUBSTITUTE(IF(G"+rowTotalIndex+"<0,\"负\",\"\")&TEXT(TRUNC(ABS(ROUND(G"+rowTotalIndex+",2))),\"[DBNum2]\")&\"元\"&IF(ISERR(FIND(\".\",ROUND(G"+rowTotalIndex+",2))),\"\",TEXT(RIGHT(TRUNC(ROUND(G"+rowTotalIndex+",2)*10)),\"[DBNum2]\"))&IF(ISERR(FIND(\".0\",TEXT(G"+rowTotalIndex+",\"0.00\"))),\"角\",\"\")&IF(LEFT(RIGHT(ROUND(G"+rowTotalIndex+",2),3))=\".\",TEXT(RIGHT(ROUND(G"+rowTotalIndex+",2)),\"[DBNum2]\")&\"分\",IF(ROUND(G"+rowTotalIndex+",2)=0,\"\",\"整\")),\"零元零\",\"\"),\"零元\",\"\")");//公式
			cell.setCellStyle(styleTitle2);
			
			cell = row.createCell(3); //仅仅是设置边框
			cell.setCellStyle(styleBody);
			cell = row.createCell(4); //仅仅是设置边框
			cell.setCellStyle(styleBody);
			cell = row.createCell(7); //仅仅是设置边框
			cell.setCellStyle(styleBody);
			
			/**********备注************/
			HSSFCellStyle styleDescription2 = wb.createCellStyle();// CellStyle 
			styleDescription2.setAlignment(HorizontalAlignment.LEFT);// 创建一个居中格式

			styleDescription2.setBorderLeft(BorderStyle.THIN);
			styleDescription2.setBorderRight(BorderStyle.THIN);

			HSSFFont fontDescription2 = wb.createFont();
			fontDescription2.setFontName("微软雅黑");
			fontDescription2.setFontHeightInPoints((short) 10);// 设置字体大小
			styleDescription2.setFont(fontDescription2);
			styleDescription2.setWrapText(true);//换行
			
            int size = 6;
            String description = salePoDto.getDescription();
            String[] split = null;
            if (description != null) {
            	split = description.split("\n");
            	if (split.length > size) size = split.length;
            }
            int m = 0;
            for (m=0; m<size; m++) {
                sheet.addMergedRegion(new CellRangeAddress(12+i+m,12+i+m,0,7));//合并单元格
                row = sheet.createRow(12+i+m);row.setHeightInPoints(20f);
            	cell = row.createCell(0);
                if (split != null && m<split.length) {
        			cell.setCellValue(split[m]);
                }
    			cell.setCellStyle(styleDescription2);
    			cell = row.createCell(7); //仅仅是设置边框
    			cell.setCellStyle(styleDescription2);
            }
			/**********拟制************/
            sheet.addMergedRegion(new CellRangeAddress(12+i+m,12+i+m,0,1));//合并单元格
            sheet.addMergedRegion(new CellRangeAddress(12+i+m,12+i+m,3,5));//合并单元格
            sheet.addMergedRegion(new CellRangeAddress(12+i+m,12+i+m,6,7));//合并单元格
            
            row = sheet.createRow(12+i+m); row.setHeightInPoints(24f);
            cell = row.createCell(0);
			cell.setCellValue("拟制");
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(1); //仅仅是设置边框
			cell.setCellStyle(styleBody);
			cell = row.createCell(2); //仅仅是设置边框
			cell.setCellStyle(styleBody);
			cell = row.createCell(4); //仅仅是设置边框
			cell.setCellStyle(styleBody);
			cell = row.createCell(5); //仅仅是设置边框
			cell.setCellStyle(styleBody);
			cell = row.createCell(7); //仅仅是设置边框
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(3);
			cell.setCellValue("批准");
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(6);
			cell.setCellValue("需方签章");
			cell.setCellStyle(styleBody);
			
            sheet.addMergedRegion(new CellRangeAddress(13+i+m,13+i+m,0,1));//合并单元格
            sheet.addMergedRegion(new CellRangeAddress(13+i+m,13+i+m,3,5));//合并单元格
            sheet.addMergedRegion(new CellRangeAddress(13+i+m,13+i+m,6,7));//合并单元格

            row = sheet.createRow(13+i+m); row.setHeightInPoints(27f);
            cell = row.createCell(0);
			cell.setCellValue(salePoDto.getMaker());
			cell.setCellStyle(styleTitle2);
			
			cell = row.createCell(1); //仅仅是设置边框
			cell.setCellStyle(styleBody);
			cell = row.createCell(2); //仅仅是设置边框
			cell.setCellStyle(styleBody);
			cell = row.createCell(4); //仅仅是设置边框
			cell.setCellStyle(styleBody);
			cell = row.createCell(5); //仅仅是设置边框
			cell.setCellStyle(styleBody);
			cell = row.createCell(6); //仅仅是设置边框
			cell.setCellStyle(styleBody);
			cell = row.createCell(7); //仅仅是设置边框
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(3);
			cell.setCellValue(salePoDto.getApprover());
			cell.setCellStyle(styleTitle2);
			
		/**********************最后一步插入图片，否则也会变形*******************/
		/*	//方法 1 插入图片至坐标
			BufferedImage bufferImg = null;
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            bufferImg = ImageIO.read(new File(picturePath));
            ImageIO.write(bufferImg, "jpg", byteArrayOut);
            //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）  
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            //anchor主要用于设置图片的属性  ，dx1、dy1定义了该图片在开始cell的起始位置，dx2、dy2定义了在终cell的结束位置。col1、row1定义了开始cell、col2、row2定义了结束cell。
            //单元格从0开始
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 201, 59,(short) 0, 0, (short) 3, 1);
            anchor.setAnchorType(3);
            //插入图片    
            patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
            //问题： 图片变形*/
			/**********************方法 2 插入图片至单元格，以及另一种图片载入方法，其实本质也一样*******************/
	        InputStream is = new FileInputStream(picturePath);  
	        byte[] bytes = IOUtils.toByteArray(is);  
	        int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);  
	        CreationHelper helper = wb.getCreationHelper();  
	        Drawing drawing = sheet.createDrawingPatriarch();  
	        ClientAnchor anchor2 = helper.createClientAnchor();  
	        // 图片插入坐标  
	        anchor2.setCol1(0);  
	        anchor2.setRow1(0);  
	        // 插入图片  
	        Picture pict = drawing.createPicture(anchor2, pictureIdx);  
	        pict.resize();  
			/*****************************************/
			/*try { 
				//写到本地做测试用
				FileOutputStream fout = new FileOutputStream("E://java//test.xls");
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
		SalePoDto s = new SalePoDto();
		s.setOrderNo("WS16100006");
		s.setCompany("深圳市爱培科技术股份有限公司");
		s.setAddress("SHENZHEN APICAL TECHNOLOGY CO., LTD");
		s.setTel("0755-61613205");
		s.setFax("0755-61613210");
		s.setHomepage("www.apical-hk.com");
		s.setClientName("MMS JOINT STOCK CO.(SHT Group AG)");
		s.setClientNameCode("0411");
		s.setOrderNo("WS16110004");
		s.setPiNo("API16100003");
		s.setSeller("李晓磊");
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		try {
			System.err.println(formatDate.parse("2016-12-22"));
			System.err.println(formatDate.parse("2016-12-7"));
			s.setDateOrder(formatDate.parse("2016-2-2"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		s.setDateDelivery(new Date());
		s.setPm("李晓磊");
		s.setMerchandiser("李莉");
		s.setDateExaminePre(new Date());
		s.setCurrency("USD");
		s.setDescription("备注：\n"							
			+"1，	交货方式：	FOB HK\n"
			+"2，	付款方式：	20%定金，余款TT\n"
			+"3,	配件：	车充，背夹，支架，USB，保修卡，说明书，WinCE贴纸\n"					
			+"4，	备份：	1%备品，已列在上面\n"
			+"5，	特殊要求：	彩盒需热塑封\n"
			+"3,	配件：	车充，背夹，支架，USB，保修卡，说明书，WinCE贴纸\n"					
			+"4，	备份：	1%备品，已列在上面\n"
			+"5，	特殊要求：	彩盒需热塑封\n"
			+"6，	其他：");
		s.setApprover("李晓磊");
		s.setMaker("陈金艳");
		
		HashSet<SalePoListDto> set = new HashSet<SalePoListDto>();
		s.setSalePoListsDto(set);
		SalePoListDto l = new SalePoListDto();
		l.setNumber("1318001-0094-0");
		l.setProductName("A7082");
		l.setSpecification("343J-7.0-GPS-4G-128M DDR");
		l.setQuality(3000);
		//l.setUnitPrice(22.29f);
		l.setDescription("800*480,电池950mAh");
		set.add(l);
		
		l = new SalePoListDto();
		l.setNumber("1318001-0094-0");
		l.setProductName("A7082");
		l.setSpecification("343J-7.0-GPS-4G-128M DDR");
		l.setQuality(31);
		//l.setUnitPrice(220.92f);
		l.setDescription("30台备品, 1台船头板（返单号WS16090068）");
		set.add(l);
		
		l = new SalePoListDto();
		l.setNumber("1");
		l.setProductName("A7082");
		l.setSpecification("343J-7.0-GPS-4G-128M DDR");
		l.setQuality(31);
		//l.setUnitPrice(220.92f);
		l.setDescription("30台备品, 1台船头板（返单号WS16090068）");
		set.add(l);
		
		export(s,"E://java//salepo-apical-logo.jpg");
		System.out.println("success");
	}
}
