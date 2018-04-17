package com.apical.oddm.facade.sale.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.apical.oddm.facade.sale.saleInfo.dto.SaleInfoDto;

/**
 * 导出销售汇总数据Excel
 * @author lgx
 * 2016-2-10
 */
public class SaleInfoExportExcel {
	
	@SuppressWarnings("deprecation")
	public static HSSFWorkbook export(List<SaleInfoDto> saleInfoList, String dateStart, String dateEnd) throws IOException {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		String title = "";//标题 2016年1月-12月外销部订单汇总表
		String[] start = dateStart.split("-");
		String[] end = dateEnd.split("-");
		String year1 = start[0]; String year2 = end[0];
		if (year1.equals(year2)) {
			year2 = "";
		} else {
			year2 +="年";
		}
		String mon1 = start[1]; mon1 = mon1.replace("0", "");
		String mon2 = end[1]; mon2 = mon2.replace("0", "");
		title = year1+"年"+mon1+"月-"+year2+mon2+"月海外销部订单汇总表";
		
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheetky
		HSSFSheet sheet = wb.createSheet();//多次create可以追加， 删除removeSheetAt
		
		sheet.setDefaultRowHeightInPoints(35.5f);//默认行高 ，默认列宽setDefaultColumnWidth  8.43
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
      
        //设置列宽， 设置行高setHeight
		double offset = 0.72;//0.72为偏移值（具体没深究，网上给的数据）
        sheet.setColumnWidth(0,(int) ((4.71+offset) * 256));//序号
        sheet.setColumnWidth(1,(int) ((9.14+offset) * 256));//月份
        sheet.setColumnWidth(2,(int) ((10+offset) * 256));//下单日期
        sheet.setColumnWidth(3,(int) ((15+offset) * 256));//订单编号
        sheet.setColumnWidth(4,(int) ((10.57+offset) * 256));//业务员
        sheet.setColumnWidth(5,(int) ((24.14+offset) * 256));//客户名称
        sheet.setColumnWidth(6,(int) ((16.57+offset) * 256));//国家
        sheet.setColumnWidth(7,(int) ((12.71+offset) * 256));//产品名称
        sheet.setColumnWidth(8,(int) ((12.71+offset) * 256));//产品机型
        sheet.setColumnWidth(9,(int) ((27.86+offset) * 256));//型号规格
        sheet.setColumnWidth(10,(int) ((7.57+offset) * 256));//数量
        sheet.setColumnWidth(11,(int) ((5.86+offset) * 256));//币种
        sheet.setColumnWidth(12,(int) ((12.71+offset) * 256));//单价
        sheet.setColumnWidth(13,(int) ((12.71+offset) * 256));//金额
        
        sheet.setColumnWidth(14,(int) ((7.57+offset) * 256));//备品
        sheet.setColumnWidth(15,(int) ((25+offset) * 256));//备注
        
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,15));
		HSSFRow row = sheet.createRow(0); row.setHeightInPoints(35.5f); //第二行 

		/*********2016年1月-12月外销部订单汇总表*********/
        HSSFCellStyle styleTitle = wb.createCellStyle();// CellStyle 
        styleTitle.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
        styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFFont fontTitle = wb.createFont();
		fontTitle.setFontName("黑体");
		fontTitle.setFontHeightInPoints((short) 18);// 设置字体大小
		fontTitle.setBold(true);;
		styleTitle.setFont(fontTitle);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(title);
		cell.setCellStyle(styleTitle);
        
		/***********标题： 序号	月份	下单日期	订单编号	业务员	客户名称	国家...**************/
		HSSFCellStyle styleTitle2 = wb.createCellStyle();// CellStyle 
		styleTitle2.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
		styleTitle2.setVerticalAlignment(VerticalAlignment.CENTER);
		styleTitle2.setBorderBottom(BorderStyle.THIN);
		styleTitle2.setBorderTop(BorderStyle.THIN);
		styleTitle2.setBorderLeft(BorderStyle.THIN);
		styleTitle2.setBorderRight(BorderStyle.THIN);
        
		HSSFFont fontTitle2 = wb.createFont();
		fontTitle2.setFontName("黑体");
		fontTitle2.setFontHeightInPoints((short) 12);// 设置字体大小
		fontTitle2.setBold(true);
		styleTitle2.setFont(fontTitle2);
		
		/**********正文内容**********/
        HSSFCellStyle styleBody = wb.createCellStyle();// CellStyle 
        styleBody.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
		styleBody.setVerticalAlignment(VerticalAlignment.CENTER);
		styleBody.setBorderBottom(BorderStyle.THIN);
		styleBody.setBorderTop(BorderStyle.THIN);
		styleBody.setBorderLeft(BorderStyle.THIN);
		styleBody.setBorderRight(BorderStyle.THIN);
		
		HSSFFont fontBody = wb.createFont();
		fontBody.setFontName("宋体");
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
		fontDate.setFontName("宋体");
		fontDate.setFontHeightInPoints((short) 10);// 设置字体大小
		styleDate.setFont(fontDate);
		DataFormat formatDate = wb.createDataFormat();
		styleDate.setDataFormat(formatDate.getFormat("yyyyMMdd"));//yyyy年MM月dd日 自动补0
		/*********格式化2位小数****************/
		HSSFCellStyle styleDec2 = wb.createCellStyle();// CellStyle 
		styleDec2.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
		styleDec2.setVerticalAlignment(VerticalAlignment.CENTER);
		styleDec2.setBorderBottom(BorderStyle.THIN);
		styleDec2.setBorderTop(BorderStyle.THIN);
		styleDec2.setBorderLeft(BorderStyle.THIN);
		styleDec2.setBorderRight(BorderStyle.THIN);
		
		HSSFFont fontDec2 = wb.createFont();
		fontDec2.setFontName("宋体");
		fontDec2.setFontHeightInPoints((short) 10);// 设置字体大小
		styleDec2.setFont(fontDec2);
		HSSFDataFormat formatDec2 = wb.createDataFormat();   
		styleDec2.setDataFormat(formatDec2.getFormat("#,##0.00")); 
		
		
		//数据为空，则退出
		if (saleInfoList == null || saleInfoList.isEmpty()) {
	        sheet.addMergedRegion(new CellRangeAddress(1,1,0,5));
			row = sheet.createRow(1); row.setHeightInPoints(35.5f); //第二行 
			cell = row.createCell(0);
			cell.setCellValue("数据记录为空！");
			cell.setCellStyle(styleBody);
			return wb;
		}
		row = sheet.createRow(1); row.setHeightInPoints(35.5f); //第二行 
		sheet.createFreezePane(0, 2); // 冻结第二行
		CellRangeAddress c = CellRangeAddress.valueOf("A2:O2");//排序和刷选
	    sheet.setAutoFilter(c);
	    
 		cell = row.createCell(0); cell.setCellValue("序号"); cell.setCellStyle(styleTitle2);
		cell = row.createCell(1); cell.setCellValue("月份"); cell.setCellStyle(styleTitle2);
		cell = row.createCell(2); cell.setCellValue("下单日期"); cell.setCellStyle(styleTitle2);
		cell = row.createCell(3); cell.setCellValue("订单编号"); cell.setCellStyle(styleTitle2);
		cell = row.createCell(4); cell.setCellValue("业务员"); cell.setCellStyle(styleTitle2);
		cell = row.createCell(5); cell.setCellValue("客户名称"); cell.setCellStyle(styleTitle2);
		cell = row.createCell(6); cell.setCellValue("国家"); cell.setCellStyle(styleTitle2);
		cell = row.createCell(7); cell.setCellValue("产品名称"); cell.setCellStyle(styleTitle2);
		cell = row.createCell(8); cell.setCellValue("产品机型"); cell.setCellStyle(styleTitle2);
		cell = row.createCell(9); cell.setCellValue("型号规格"); cell.setCellStyle(styleTitle2);
		cell = row.createCell(10); cell.setCellValue("数量"); cell.setCellStyle(styleTitle2);
		cell = row.createCell(11); cell.setCellValue("币种"); cell.setCellStyle(styleTitle2);
		cell = row.createCell(12); cell.setCellValue("单价"); cell.setCellStyle(styleTitle2);
		cell = row.createCell(13); cell.setCellValue("金额"); cell.setCellStyle(styleTitle2);
		cell = row.createCell(14); cell.setCellValue("备品"); cell.setCellStyle(styleTitle2);
		cell = row.createCell(15); cell.setCellValue("备注"); cell.setCellStyle(styleTitle2);
		
		int i = 0;
		int lastMonth = 0;//saleInfoList.get(0).getDateOrder().getMonth()+1;
		int startIndex = 2;//其实索引
		int currentIndex = 0; // 当前索引
		int temp = 0;//间隔多少个相同
		for (SaleInfoDto saleInfo : saleInfoList) {
			i++;
			currentIndex = i+1; //必须放前面， 2
			row = sheet.createRow(currentIndex); row.setHeightInPoints(35.5f); // 
			cell = row.createCell(0); cell.setCellValue(i); cell.setCellStyle(styleBody);
	 		if ((saleInfo.getDateOrder().getMonth()+1) == lastMonth) {
	 			temp ++;//间隔多少个相同
	 			//System.out.println("\t ssss"+temp+" m"+(saleInfo.getDateOrder().getMonth()+1));
	 		} else {//合并
	 			//System.out.println(startIndex+"\t"+temp+" m"+(saleInfo.getDateOrder().getMonth()+1));
	 			if (temp >= 2) sheet.addMergedRegion(new CellRangeAddress(startIndex, startIndex+(temp-1), 1, 1));
	 			startIndex = currentIndex;
	 	    	temp = 1;
		 		lastMonth = saleInfo.getDateOrder().getMonth()+1;
	 		}
			cell = row.createCell(1);
			if (saleInfo.getDateOrder() != null)
			cell.setCellValue(saleInfo.getDateOrder().getMonth() + 1 + "月");
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(2);
			if (saleInfo.getDateOrder() != null)
			cell.setCellValue(saleInfo.getDateOrder());
			cell.setCellStyle(styleDate);
			
			cell = row.createCell(3);
			if (saleInfo.getOrderNo() != null)
			cell.setCellValue(saleInfo.getOrderNo());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(4);
			if (saleInfo.getSeller() != null)
			cell.setCellValue(saleInfo.getSeller());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(5);
			if (saleInfo.getClientName() != null)
			cell.setCellValue(saleInfo.getClientName());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(6);
			if (saleInfo.getDistrict() != null)
			cell.setCellValue(saleInfo.getDistrict());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(7);
			if (saleInfo.getProductType() != null)
			cell.setCellValue(saleInfo.getProductType());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(8);
			if (saleInfo.getProductFactory() != null)
			cell.setCellValue(saleInfo.getProductFactory());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(9);
			if (saleInfo.getSpecification() != null)
			cell.setCellValue(saleInfo.getSpecification());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(10);
			if (saleInfo.getQuantitySale() != null)
			cell.setCellValue(saleInfo.getQuantitySale());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(11);
			if (saleInfo.getCurrency() != null)
			cell.setCellValue(saleInfo.getCurrency());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(12);
			if (saleInfo.getUnitPrice() != null)
			cell.setCellValue(saleInfo.getUnitPrice()+"");
			cell.setCellStyle(styleDec2);
			
			cell = row.createCell(13);
			cell.setCellFormula("K" + (2 + i) + "*M" + (2 + i));
			cell.setCellStyle(styleDec2);
			
			cell = row.createCell(14);
			if (saleInfo.getSpare() != null)
			cell.setCellValue(saleInfo.getSpare());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(15);
			if (saleInfo.getDescription() != null)
			cell.setCellValue(saleInfo.getDescription());
			cell.setCellStyle(styleBody);
		}
		//最后一次判断合并
		if (temp >= 2) sheet.addMergedRegion(new CellRangeAddress(startIndex, startIndex+(temp-1), 1, 1));
     /*   try { 
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
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		List<SaleInfoDto> list = new LinkedList<SaleInfoDto>();
		SaleInfoDto s = new SaleInfoDto();
		s.setDateOrder(formatDate.parse("2016-03-22"));
		s.setOrderNo("WS16010003");
		s.setSeller("徐细梅");
		s.setClientName("WSAM Trading");
		s.setDistrict("深圳");
		s.setProductType("PND");
		s.setProductFactory("A4378");
		s.setSpecification("615-8GB-128MB DDR -GPS +FM+蓝牙BLE 4.0 版本，支持外挂TPMS ");
		s.setQuantitySale(11);
		s.setCurrency("USD");
		s.setUnitPrice(new BigDecimal(11.11f));//网页导出Excel没很长的单价问题
		s.setSpare(120);
		s.setDescription("615-8GB-128MB DDR -GPS +FM+蓝牙BLE 4.0 版本，支持外挂TPMS ");
		list.add(s);
		
		s = new SaleInfoDto();
		s.setDateOrder(formatDate.parse("2016-09-2"));
		s.setOrderNo("WS16010002");
		s.setSeller("徐细梅");
		s.setClientName("WSAM Trading");
		s.setDistrict("深圳");
		s.setProductType("PND");
		s.setProductFactory("A4378");
		s.setSpecification("615-8GB-128MB DDR -GPS +FM+蓝牙BLE 4.0 版本，支持外挂TPMS ");
		s.setQuantitySale(11);
		s.setCurrency("USD");
		s.setUnitPrice(new BigDecimal(11.11f));
		s.setSpare(120);
		s.setDescription("615-8GB-128MB DDR -GPS +FM+蓝牙BLE 4.0 版本，支持外挂TPMS ");
		list.add(s);
		
		s = new SaleInfoDto();
		s.setDateOrder(formatDate.parse("2016-09-2"));
		s.setOrderNo("WS16010002");
		s.setSeller("徐细梅");
		s.setClientName("WSAM Trading");
		s.setDistrict("深圳");
		s.setProductType("PND");
		s.setProductFactory("A4378");
		s.setSpecification("615-8GB-128MB DDR -GPS +FM+蓝牙BLE 4.0 版本，支持外挂TPMS ");
		s.setQuantitySale(11);
		s.setCurrency("USD");
		s.setUnitPrice(new BigDecimal(11.11f));
		s.setSpare(120);
		s.setDescription("615-8GB-128MB DDR -GPS +FM+蓝牙BLE 4.0 版本，支持外挂TPMS ");
		list.add(s);
		
		s = new SaleInfoDto();
		s.setDateOrder(formatDate.parse("2017-09-2"));
		s.setOrderNo("WS16010012");
		s.setSeller("徐细梅");
		s.setClientName("WSAM Trading");
		s.setDistrict("深圳");
		s.setProductType("PND");
		s.setProductFactory("A4378");
		s.setSpecification("615-8GB-128MB DDR -GPS +FM+蓝牙BLE 4.0 版本，支持外挂TPMS ");
		s.setQuantitySale(11);
		s.setCurrency("USD");
		s.setUnitPrice(new BigDecimal(11.11f));
		s.setSpare(120);
		s.setDescription("615-8GB-128MB DDR -GPS +FM+蓝牙BLE 4.0 版本，支持外挂TPMS ");
		list.add(s);
		try {
			SaleInfoExportExcel.export(list, "2016-01-01", "2017-11-01");
			System.out.println("成功");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
