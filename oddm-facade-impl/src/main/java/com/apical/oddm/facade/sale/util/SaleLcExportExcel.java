package com.apical.oddm.facade.sale.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import com.apical.oddm.facade.sale.lc.SaleLcDto;

/**
 * 导出销售汇总数据Excel
 * @author lgx
 * 2016-2-10
 */
public class SaleLcExportExcel {
	
	public static HSSFWorkbook export(List<SaleLcDto> saleLcList) throws IOException {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheetky
		HSSFSheet sheet = wb.createSheet();//多次create可以追加， 删除removeSheetAt

		sheet.setDefaultRowHeightInPoints(16.5f);//默认行高 ，默认列宽setDefaultColumnWidth  8.43
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
      
        //设置列宽， 设置行高setHeight
		double offset = 3.2;//0.72为偏移值（具体没深究，网上给的数据）
        sheet.setColumnWidth(0,(int) ((14+offset) * 256));//订单号
        sheet.setColumnWidth(1,(int) ((15+offset) * 256));//客户
        sheet.setColumnWidth(2,(int) ((10+offset) * 256));//即期/远期
        sheet.setColumnWidth(3,(int) ((17+offset) * 256));//信用证号
        sheet.setColumnWidth(4,(int) ((14+offset) * 256));//信用证总金额
        sheet.setColumnWidth(5,(int) ((5+offset) * 256));//币种
        sheet.setColumnWidth(6,(int) ((15+offset) * 256));//发票号
        sheet.setColumnWidth(7,(int) ((14+offset) * 256));//发票金额
        sheet.setColumnWidth(8,(int) ((10+offset) * 256));//交单日期
        sheet.setColumnWidth(9,(int) ((10+offset) * 256));//到账日期
        sheet.setColumnWidth(10,(int) ((22+offset) * 256));//备注
        
		HSSFRow row = sheet.createRow(0); row.setHeightInPoints(25.5f); 

		/*********头部：LC收款台账*********/
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,10));
        HSSFCellStyle head = wb.createCellStyle();// CellStyle 
        head.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
        head.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFFont fontHead = wb.createFont();
		fontHead.setFontName("微软雅黑");
		fontHead.setFontHeightInPoints((short) 16);// 设置字体大小
		fontHead.setBold(true);;
		head.setFont(fontHead);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("LC收款台账");
		cell.setCellStyle(head);
		
		/***********标题： 订单号	客户	即期/远期	信用证号	信用证总金额(USD)	发票号	发票金额	交单日期	到账日期	备注**************/
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
		HSSFCellStyle styleDec = wb.createCellStyle();// CellStyle 
		styleDec.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
		styleDec.setVerticalAlignment(VerticalAlignment.CENTER);
		styleDec.setBorderBottom(BorderStyle.THIN);
		styleDec.setBorderTop(BorderStyle.THIN);
		styleDec.setBorderLeft(BorderStyle.THIN);
		styleDec.setBorderRight(BorderStyle.THIN);
		
		HSSFFont fontDec = wb.createFont();
		fontDec.setFontName("微软雅黑");
		fontDec.setColor(Font.COLOR_RED);
		fontDec.setFontHeightInPoints((short) 10);// 设置字体大小
		styleDec.setFont(fontDec);
		HSSFDataFormat formatDec2 = wb.createDataFormat();   
		styleDec.setDataFormat(formatDec2.getFormat("#,##0.00")); 
		
		/**************标题设置**************/
		row = sheet.createRow(1); row.setHeightInPoints(25.5f); 
		cell = row.createCell(0);cell.setCellValue("订单号");cell.setCellStyle(styleTitle);
		cell = row.createCell(1);cell.setCellValue("客户");cell.setCellStyle(styleTitle);
		cell = row.createCell(2);cell.setCellValue("即期/远期");cell.setCellStyle(styleTitle);
		cell = row.createCell(3);cell.setCellValue("信用证号");cell.setCellStyle(styleTitle);
		cell = row.createCell(4);cell.setCellValue("信用证总金额");cell.setCellStyle(styleTitle);
		cell = row.createCell(5);cell.setCellValue("币种");cell.setCellStyle(styleTitle);
		cell = row.createCell(6);cell.setCellValue("发票号");cell.setCellStyle(styleTitle);
		cell = row.createCell(7);cell.setCellValue("发票金额");cell.setCellStyle(styleTitle);
		cell = row.createCell(8);cell.setCellValue("交单日期");cell.setCellStyle(styleTitle);
		cell = row.createCell(9);cell.setCellValue("到账日期");cell.setCellStyle(styleTitle);
		cell = row.createCell(10);cell.setCellValue("备注");cell.setCellStyle(styleTitle);

		sheet.createFreezePane(0, 2); // 冻结第1行
		CellRangeAddress c = CellRangeAddress.valueOf("A2:K2");//排序和刷选
		sheet.setAutoFilter(c);
		
		//数据为空，则退出
		if (saleLcList == null || saleLcList.isEmpty()) {
	        sheet.addMergedRegion(new CellRangeAddress(2,2,0,5));
			row = sheet.createRow(2); row.setHeightInPoints(35.5f); //第二行 
			cell = row.createCell(0);
			cell.setCellValue("数据记录为空！");
			cell.setCellStyle(styleBody);
			return wb;
		}
		/**************内容设置**************/
		int i = 1;
		for (SaleLcDto saleLcDto : saleLcList) {
			i++;
			String orderNo = saleLcDto.getOrderNo();
			float height = 25.5f;
			row = sheet.createRow(i); row.setHeightInPoints(height); //第二行,设置默认行高
			if (orderNo != null) {
				String[] split = orderNo.split("\\/");
				if (split.length > 2) {//
					row = sheet.createRow(i); //第二行，设置自动行高，得配合setWrapText()使用
				}
				orderNo = orderNo.replace("/", "\n");
			}
			cell = row.createCell(0);
			if (orderNo != null)
			cell.setCellValue(orderNo);
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(1);
			if (saleLcDto.getClientName() != null)
			cell.setCellValue(saleLcDto.getClientName());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(2);
			if (saleLcDto.getSpotForward() != null)
			cell.setCellValue(saleLcDto.getSpotForward());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(3);
			if (saleLcDto.getCreditNo() != null)
			cell.setCellValue(saleLcDto.getCreditNo());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(4);
			if (saleLcDto.getCreditAmount() != null)
			cell.setCellValue(saleLcDto.getCreditAmount()+"");
			cell.setCellStyle(styleDec);
			
			cell = row.createCell(5);
			if (saleLcDto.getCurrency() != null)
			cell.setCellValue(saleLcDto.getCurrency());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(6);
			if (saleLcDto.getInvoiceNo() != null)
			cell.setCellValue(saleLcDto.getInvoiceNo());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(7);
			if (saleLcDto.getInvoiceAmount() != null)
			cell.setCellValue(saleLcDto.getInvoiceAmount()+"");
			cell.setCellStyle(styleDec);
			
			cell = row.createCell(8);
			if (saleLcDto.getDateDelivery() != null)
			cell.setCellValue(saleLcDto.getDateDelivery());
			cell.setCellStyle(styleDate);
			
			cell = row.createCell(9);
			if (saleLcDto.getDateGet() != null)
			cell.setCellValue(saleLcDto.getDateGet());
			cell.setCellStyle(styleDate);
			
			cell = row.createCell(10);
			if (saleLcDto.getDescription() != null)
			cell.setCellValue(saleLcDto.getDescription());
			cell.setCellStyle(styleBody);
			
		}
		
		//最后一次判断合并
        try { 
			//写到本地做测试用
			FileOutputStream fout = new FileOutputStream("D://Desktop//test.xls");
		    wb.write(fout);
		    fout.close();
		} catch (Exception e) {  
		    e.printStackTrace();
		}
		return wb;
	}
	
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		List<SaleLcDto> list = new LinkedList<SaleLcDto>();
		SaleLcDto s = new SaleLcDto();
		s.setClientName("PROSTIGIO");
		//s.setCreditAmount(191700f);
		s.setCreditNo("6037FLCDP160005");
		s.setCurrency("USD");
		s.setDateDelivery(formatDate.parse("2016-6-6"));
		s.setDateGet(new Date());
		s.setDescription("备注描述、备注描述");
		//s.setInvoiceAmount(265650f);
		s.setInvoiceNo("WS15110048AE");
		s.setOrderNo("WS16040012/WS16050016/WS16050017");
		s.setSpotForward("45DAYS");
		list.add(s);
		
		s = new SaleLcDto();
		s.setClientName("PROSTIGIO");
		//s.setCreditAmount(191700f);
		s.setCreditNo("6037FLCDP1605");
		s.setCurrency("USD");
		s.setDateDelivery(new Date());
		s.setDateGet(new Date());
		s.setDescription("备注描述、备注描述");
		//s.setInvoiceAmount(265650f);
		s.setInvoiceNo("WS15110048AE");
		s.setOrderNo("WS16040012");
		s.setSpotForward("45DAYS");
		list.add(s);
		
		s = new SaleLcDto();
		s.setClientName("PROSTI");
		//s.setCreditAmount(191700f);
		s.setCreditNo("6037FLCDP160005");
		s.setCurrency("USD");
		s.setDateDelivery(new Date());
		s.setDateGet(new Date());
		s.setDescription("备注描述、备注描述");
		//s.setInvoiceAmount(265650f);
		s.setInvoiceNo("WS15110048AE");
		s.setOrderNo("WS16040012/WS16050016");
		s.setSpotForward("45DAYS");
		list.add(s);
		
		s = new SaleLcDto();
		s.setClientName("PROSTIGIO");
		//s.setCreditAmount(191700f);
		s.setCreditNo("Y05L16085I000020");
		s.setCurrency("USD");
		s.setDateDelivery(new Date());
		s.setDateGet(new Date());
		s.setDescription("备注描述、备注描述");
		//s.setInvoiceAmount(265650f);
		s.setInvoiceNo("WS15110048AE");
		s.setOrderNo("WS16040012/WS16050016/WS16050016/WS16050016/WS16050016/WS16050016/WS16050016");
		s.setSpotForward("45DAYS");
		list.add(s);
		
		s = new SaleLcDto();
		s.setClientName("PROSTIGIO");
		//s.setCreditAmount(191700f);
		s.setCreditNo("6037FLCDP160005");
		s.setCurrency("USD");
		s.setDateDelivery(new Date());
		s.setDateGet(new Date());
		s.setDescription("备注描述");
		//s.setInvoiceAmount(265650f);
		s.setInvoiceNo("WS15110048");
		s.setOrderNo("WS16050016/WS16050016/WS16050016/WS16050016");
		s.setSpotForward("45DAYS");
		list.add(s);
		try {
			SaleLcExportExcel.export(list);
			System.out.println("成功");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
