package com.apical.oddm.facade.sale.util;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import com.apical.oddm.facade.sale.proto.SaleProtoDto;

/**
 * 导出销售汇总数据Excel
 * @author lgx
 * 2016-2-10
 */
public class SaleProtoExportExcel {
	
	public static HSSFWorkbook export(List<SaleProtoDto> saleProtoList) throws IOException {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheetky
		HSSFSheet sheet = wb.createSheet("样机记录");//多次create可以追加， 删除removeSheetAt

		sheet.setDefaultRowHeightInPoints(25.5f);//默认行高 ，默认列宽setDefaultColumnWidth  8.43
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
      
        //设置列宽， 设置行高setHeight
		double offset = 0.72;//0.72为偏移值（具体没深究，网上给的数据）
        sheet.setColumnWidth(0,(int) ((16+offset) * 256));//客户名称
        sheet.setColumnWidth(1,(int) ((16+offset) * 256));//国家区域
        sheet.setColumnWidth(2,(int) ((16+offset) * 256));//产品名称
        sheet.setColumnWidth(3,(int) ((8+offset) * 256));//尺寸
        sheet.setColumnWidth(4,(int) ((16+offset) * 256));//外壳型号
        sheet.setColumnWidth(5,(int) ((26+offset) * 256));//PCBA型号
        sheet.setColumnWidth(6,(int) ((18+offset) * 256));//外观/软件
        sheet.setColumnWidth(7,(int) ((8+offset) * 256));//数量
        sheet.setColumnWidth(8,(int) ((15+offset) * 256));//是否收款 
        sheet.setColumnWidth(9,(int) ((12+offset) * 256));//送样日期
        sheet.setColumnWidth(10,(int) ((12+offset) * 256));//收款日期
        sheet.setColumnWidth(11,(int) ((12+offset) * 256));//退还日期
        sheet.setColumnWidth(12,(int) ((25+offset) * 256));//备注
        
		/***********标题： 客户名称	国家/区域	产品名称	尺寸	外壳型号...**************/
		HSSFCellStyle styleTitle = wb.createCellStyle();// CellStyle 
		styleTitle.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
		styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
		styleTitle.setBorderBottom(BorderStyle.THIN);
		styleTitle.setBorderTop(BorderStyle.THIN);
		styleTitle.setBorderLeft(BorderStyle.THIN);
		styleTitle.setBorderRight(BorderStyle.THIN);
        
		HSSFFont fontTitle = wb.createFont();
		fontTitle.setFontName("微软雅黑");
		fontTitle.setFontHeightInPoints((short) 11);// 设置字体大小
		fontTitle.setBold(true);
		styleTitle.setFont(fontTitle);
		
		/**********正文内容**********/
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
		styleDate.setDataFormat(formatDate.getFormat("yyyy/MM/dd"));//yyyy年MM月dd日 自动补0
		/*********格式化2位小数****************/
		HSSFCellStyle styleDec1 = wb.createCellStyle();// CellStyle 
		styleDec1.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
		styleDec1.setVerticalAlignment(VerticalAlignment.CENTER);
		styleDec1.setBorderBottom(BorderStyle.THIN);
		styleDec1.setBorderTop(BorderStyle.THIN);
		styleDec1.setBorderLeft(BorderStyle.THIN);
		styleDec1.setBorderRight(BorderStyle.THIN);
		
		HSSFFont fontDec1 = wb.createFont();
		fontDec1.setFontName("微软雅黑");
		fontDec1.setFontHeightInPoints((short) 10);// 设置字体大小
		styleDec1.setFont(fontDec1);
		HSSFDataFormat formatDec2 = wb.createDataFormat();   
		styleDec1.setDataFormat(formatDec2.getFormat("###0.0")); 
		
		/**************标题设置**************/
		HSSFRow row = sheet.createRow(0); row.setHeightInPoints(25.5f); 
		HSSFCell 
		cell = row.createCell(0);cell.setCellValue("客户名称");cell.setCellStyle(styleTitle);
		cell = row.createCell(1);cell.setCellValue("国家区域");cell.setCellStyle(styleTitle);
		cell = row.createCell(2);cell.setCellValue("产品名称");cell.setCellStyle(styleTitle);
		cell = row.createCell(3);cell.setCellValue("尺寸");cell.setCellStyle(styleTitle);
		cell = row.createCell(4);cell.setCellValue("外壳型号");cell.setCellStyle(styleTitle);
		cell = row.createCell(5);cell.setCellValue("PCBA型号");cell.setCellStyle(styleTitle);
		cell = row.createCell(6);cell.setCellValue("外观/软件");cell.setCellStyle(styleTitle);
		cell = row.createCell(7);cell.setCellValue("数量");cell.setCellStyle(styleTitle);
		cell = row.createCell(8);cell.setCellValue("是否收款 ");cell.setCellStyle(styleTitle);
		cell = row.createCell(9);cell.setCellValue("送样日期");cell.setCellStyle(styleTitle);
		cell = row.createCell(10);cell.setCellValue("收款日期");cell.setCellStyle(styleTitle);
		cell = row.createCell(11);cell.setCellValue("退还日期");cell.setCellStyle(styleTitle);
		cell = row.createCell(12);cell.setCellValue("备注");cell.setCellStyle(styleTitle);

		sheet.createFreezePane(0, 1); // 冻结第1行
		CellRangeAddress c = CellRangeAddress.valueOf("A1:M1");//排序和刷选
		sheet.setAutoFilter(c);
		
		//数据为空，则退出
		if (saleProtoList == null || saleProtoList.isEmpty()) {
	        sheet.addMergedRegion(new CellRangeAddress(1,1,0,5));
			row = sheet.createRow(1); row.setHeightInPoints(35.5f); //第二行 
			cell = row.createCell(0);
			cell.setCellValue("数据记录为空！");
			cell.setCellStyle(styleBody);
			return wb;
		}
		int i=0;
		for (SaleProtoDto saleProtoDto : saleProtoList) {
			i++;
			row = sheet.createRow(i); row.setHeightInPoints(25.5f); //第二行,设置默认行高
			
			cell = row.createCell(0);
			if(saleProtoDto.getClientName() != null)
			cell.setCellValue(saleProtoDto.getClientName());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(1);
			if(saleProtoDto.getDistrict() != null)
			cell.setCellValue(saleProtoDto.getDistrict());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(2);
			if(saleProtoDto.getProductName() != null)
			cell.setCellValue(saleProtoDto.getProductName());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(3);
			if (saleProtoDto.getSize() != null)
			cell.setCellValue(saleProtoDto.getSize());
			cell.setCellStyle(styleDec1);
			
			cell = row.createCell(4);
			if(saleProtoDto.getShell() != null)
			cell.setCellValue(saleProtoDto.getShell());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(5);
			if(saleProtoDto.getPcba() != null)
			cell.setCellValue(saleProtoDto.getPcba());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(6);
			if(saleProtoDto.getFacade() != null)
			cell.setCellValue(saleProtoDto.getFacade());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(7);
			if (saleProtoDto.getQuantity() != null)
			cell.setCellValue(saleProtoDto.getQuantity());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(8);
			if(saleProtoDto.getPayed() != null)
			cell.setCellValue(saleProtoDto.getPayed());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(9);
			if (saleProtoDto.getDateSend() != null)
			cell.setCellValue(saleProtoDto.getDateSend());
			cell.setCellStyle(styleDate);
			
			cell = row.createCell(10);
			if (saleProtoDto.getDatePayed() != null)
			cell.setCellValue(saleProtoDto.getDatePayed());
			cell.setCellStyle(styleDate);
			
			cell = row.createCell(11);
			if (saleProtoDto.getDateReturn() != null)
			cell.setCellValue(saleProtoDto.getDateReturn());
			cell.setCellStyle(styleDate);
			
			cell = row.createCell(12);
			if(saleProtoDto.getDescription() != null)
			cell.setCellValue(saleProtoDto.getDescription());
			cell.setCellStyle(styleBody);
		}
		
		//最后一次判断合并
        /*try { 
			//写到本地做测试用
			FileOutputStream fout = new FileOutputStream("D://Desktop//test.xls");
		    wb.write(fout);
		    fout.close();
		} catch (Exception e) {  
		    e.printStackTrace();
		}*/
		return wb;
	}
	
	public static void main(String[] args) throws ParseException {
		//SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		List<SaleProtoDto> list = new LinkedList<SaleProtoDto>();
		SaleProtoDto s = new SaleProtoDto();
		s.setClientName("apical");
		s.setDatePayed(new Date());
		s.setDateReturn(new Date());
		s.setDateSend(new Date());
		s.setDescription("csfdsfsfs f发的萨芬的是范德萨");
		s.setDistrict("美国");
		s.setFacade("黑色，公模");
		s.setPayed("尚未-1230");
		s.setPcba("1004W-128-4G-FA");
		s.setProductName("MID");
		s.setQuantity(15);
		s.setShell("美味又");
		s.setSize(5.2f);
		list.add(s);
		
		s = new SaleProtoDto();
		s.setClientName("apical");
		s.setDatePayed(new Date());
		s.setDateReturn(new Date());
		s.setDateSend(new Date());
		s.setDescription("csfdsfsfs f发的萨芬的是范德萨");
		s.setDistrict("美国");
		s.setFacade("黑色，公模");
		s.setPayed("尚未-1230");
		s.setPcba("1004W-128-4G-FA");
		s.setProductName("MID");
		//s.setQuantity(15);
		s.setShell("美味又");
	//	s.setSize(5.2f);
		list.add(s);
		
		s = new SaleProtoDto();
		s.setClientName("apical");
		s.setDatePayed(new Date());
		s.setDateReturn(new Date());
		s.setDateSend(new Date());
		s.setDescription("csfdsfsfs f发的萨芬的是范德萨");
		s.setDistrict("美国");
		s.setFacade("黑色，公模");
		s.setPayed("尚未-1230");
		s.setPcba("1004W-128-4G-FA");
		s.setProductName("MID");
		s.setQuantity(15);
		s.setShell("美味又");
		s.setSize(5.2f);
		list.add(s);
		
		s = new SaleProtoDto();
		s.setClientName("apical");
		s.setDatePayed(new Date());
		s.setDateReturn(new Date());
		s.setDateSend(new Date());
		s.setDescription("csfdsfsfs f发的萨芬的是范德萨");
		s.setDistrict("美国");
		s.setFacade("黑色，公模");
		s.setPayed("尚未-1230");
		s.setPcba("1004W-128-4G-FA");
		s.setProductName("MID");
		s.setQuantity(15);
		s.setShell("美味又");
		s.setSize(5.2f);
		list.add(s);
		try {
			SaleProtoExportExcel.export(list);
			System.out.println("成功");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
