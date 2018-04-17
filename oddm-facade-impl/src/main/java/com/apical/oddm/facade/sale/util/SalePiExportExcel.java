package com.apical.oddm.facade.sale.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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

import com.apical.oddm.facade.sale.pi.SalePiDto;

/**
 * 导出销售汇总数据Excel
 * @author lgx
 * 2016-2-10
 */
public class SalePiExportExcel {
	
	public static HSSFWorkbook export(List<SalePiDto> salePiList, String orderMonth) throws IOException {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		String title = "";//标题 2016年1月-12月外销部订单汇总表
		if (StringUtils.isNotBlank(orderMonth)) {
			String[] month = orderMonth.split("-");
			if (month.length > 1) {
				String year = month[0];
				String mon = month[1]; mon = mon.replace("0", "");
				title = year+"年"+mon+"月";
			}
		}
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheetky
		HSSFSheet sheet = wb.createSheet(title);//多次create可以追加， 删除removeSheetAt

		sheet.setDefaultRowHeightInPoints(25.5f);//默认行高 ，默认列宽setDefaultColumnWidth  8.43
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
      
        //设置列宽， 设置行高setHeight
		double offset = 0.72;//0.72为偏移值（具体没深究，网上给的数据）
        sheet.setColumnWidth(0,(int) ((16+offset) * 256));//下单日期
        sheet.setColumnWidth(1,(int) ((13+offset) * 256));//PO编号
        sheet.setColumnWidth(2,(int) ((15+offset) * 256));//PI编号
        sheet.setColumnWidth(3,(int) ((12+offset) * 256));//业务员
        sheet.setColumnWidth(4,(int) ((20+offset) * 256));//客户名称
        sheet.setColumnWidth(5,(int) ((12+offset) * 256));//国家
        sheet.setColumnWidth(6,(int) ((30+offset) * 256));//摘要
        sheet.setColumnWidth(7,(int) ((12.71+offset) * 256));//总金额
        sheet.setColumnWidth(8,(int) ((8+offset) * 256));//币种
        sheet.setColumnWidth(9,(int) ((22+offset) * 256));//备注
        
		HSSFRow row = sheet.createRow(0); row.setHeightInPoints(25.5f); 

		/***********标题： 下单日期	PO编号	PI编号	业务员	客户名称	国家	摘要	总金额...**************/
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
		styleDate.setDataFormat(formatDate.getFormat("yyyy/M/d"));//yyyy年MM月dd日 自动补0
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
		fontDec.setFontHeightInPoints((short) 10);// 设置字体大小
		styleDec.setFont(fontDec);
		HSSFDataFormat formatDec2 = wb.createDataFormat();   
		styleDec.setDataFormat(formatDec2.getFormat("#,##0.00")); 
		
		/**************标题设置**************/
		HSSFCell 
		cell = row.createCell(0);cell.setCellValue("下单日期");cell.setCellStyle(styleTitle);
		cell = row.createCell(1);cell.setCellValue("PO编号");cell.setCellStyle(styleTitle);
		cell = row.createCell(2);cell.setCellValue("PI编号");cell.setCellStyle(styleTitle);
		cell = row.createCell(3);cell.setCellValue("业务员");cell.setCellStyle(styleTitle);
		cell = row.createCell(4);cell.setCellValue("客户名称");cell.setCellStyle(styleTitle);
		cell = row.createCell(5);cell.setCellValue("国家");cell.setCellStyle(styleTitle);
		cell = row.createCell(6);cell.setCellValue("摘要");cell.setCellStyle(styleTitle);
		cell = row.createCell(7);cell.setCellValue("总金额");cell.setCellStyle(styleTitle);
		cell = row.createCell(8);cell.setCellValue("币种");cell.setCellStyle(styleTitle);
		cell = row.createCell(9);cell.setCellValue("备注");cell.setCellStyle(styleTitle);

		sheet.createFreezePane(0, 1); // 冻结第1行
		CellRangeAddress c = CellRangeAddress.valueOf("A1:J1");//排序和刷选
		sheet.setAutoFilter(c);
		
		//数据为空，则退出
		if (salePiList == null || salePiList.isEmpty()) {
	        sheet.addMergedRegion(new CellRangeAddress(1,1,0,5));
			row = sheet.createRow(1); row.setHeightInPoints(35.5f); //第二行 
			cell = row.createCell(0);
			cell.setCellValue("数据记录为空！");
			cell.setCellStyle(styleBody);
			return wb;
		}
		/**************内容设置**************/
		int i = 0;
		for (SalePiDto salePiDto : salePiList) {
			i++;
			String poNo = salePiDto.getPoNo();
			float height = 25.5f;
			row = sheet.createRow(i); row.setHeightInPoints(height); //第二行,设置默认行高
			if (poNo != null) {
				String[] split = poNo.split("\\/");
				/*int h = split.length/2;
				float heightTemp = 18f;
				height = height+heightTemp*h;*/
				
				if (split.length > 2) {//
					row = sheet.createRow(i); //第二行，设置自动行高，得配合setWrapText()使用
				}
				poNo = poNo.replace("/", "\n");
			}
			cell = row.createCell(0);
			if (salePiDto.getOrderDate() != null)
			cell.setCellValue(salePiDto.getOrderDate());
			cell.setCellStyle(styleDate);
			
			cell = row.createCell(1);
			if (poNo != null)
			cell.setCellValue(poNo);
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(2);
			if (salePiDto.getPiNo() != null)
			cell.setCellValue(salePiDto.getPiNo());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(3);
			if (salePiDto.getSeller() != null)
			cell.setCellValue(salePiDto.getSeller());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(4);
			if (salePiDto.getClientName() != null)
			cell.setCellValue(salePiDto.getClientName());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(5);
			if (salePiDto.getDistrict() != null)
			cell.setCellValue(salePiDto.getDistrict());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(6);
			if (salePiDto.getSummary() != null)
			cell.setCellValue(salePiDto.getSummary());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(7);
			if (salePiDto.getAmount() != null)
			cell.setCellValue(salePiDto.getAmount()+"");
			cell.setCellStyle(styleDec);
			
			cell = row.createCell(8);
			if (salePiDto.getCurrency() != null)
			cell.setCellValue(salePiDto.getCurrency());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(9);
			if (salePiDto.getDescription() != null)
			cell.setCellValue(salePiDto.getDescription());
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
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		List<SalePiDto> list = new LinkedList<SalePiDto>();
		SalePiDto s = new SalePiDto();
		s.setOrderDate(formatDate.parse("2012-6-6"));
		//s.setAmount(121.00f);
		s.setClientName("apical");
		s.setCurrency("$");
		s.setDescription("RE5/RE7");
		s.setDistrict("美国");
		s.setPiNo("API16050002");
		s.setPoNo("WS16050046/WS16050046/WS16050046/WS16050046/WS16050046/WS16050046/WS16050046/WS16050046/WS16050046/WS16050046/WS16050046/WS16050046");
		s.setSeller("徐细梅");
		s.setSummary("FCC 认证费");
		list.add(s);
		
		s = new SalePiDto();
		s.setOrderDate(new Date());
		//s.setAmount(121.00f);
		s.setClientName("apical");
		s.setCurrency("$");
		s.setDescription("RE5/RE7");
		s.setDistrict("美国");
		s.setPiNo("API16050002");
		s.setPoNo("WS16050046/WS16050046/WS16050046/WS16050046/WS16050046/WS16050046/WS16050046/WS16050046");
		s.setSeller("徐细梅");
		s.setSummary("FCC 认证费");
		list.add(s);
		
		s = new SalePiDto();
		s.setOrderDate(new Date());
		//s.setAmount(121.00f);
		s.setClientName("apical");
		s.setCurrency("$");
		s.setDescription("RE5/RE7");
		s.setDistrict("西班牙");
		s.setPiNo("API16050002");
		s.setPoNo("WS16050046/WS16050046");
		s.setSeller("徐细梅");
		s.setSummary("FCC 认证费");
		list.add(s);
		
		s = new SalePiDto();
		s.setOrderDate(new Date());
		//s.setAmount(121.00f);
		s.setClientName("apical");
		s.setCurrency("$");
		s.setDescription("RE5/RE7");
		s.setDistrict("美国");
		s.setPiNo("API16050002");
		s.setPoNo("WS16050046/WS16050046/WS16050046");
		s.setSeller("徐细梅");
		s.setSummary("FCC 认证费");
		list.add(s);
		
		s = new SalePiDto();
		s.setOrderDate(new Date());
		//s.setAmount(121.00f);
		s.setClientName("apical");
		s.setCurrency("$");
		s.setDescription("RE5/RE7");
		s.setDistrict("美国");
		s.setPiNo("API16050002");
		s.setPoNo("WS16050046");
		s.setSeller("徐细梅");
		s.setSummary("FCC 认证费");
		list.add(s);
		try {
			SalePiExportExcel.export(list, "2016-01-01");
			System.out.println("成功");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
