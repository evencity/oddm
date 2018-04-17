package com.apical.oddm.facade.sale.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;


/**
 * 导出销售汇总数据Excel
 * @author lgx
 * 2016-2-10
 */
public class SaleOutExportExcel {
	
	/*public static HSSFWorkbook export(List<SaleOutDTO> saleOutList, String shipmentMonth) throws IOException {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		String title = "";//标题 2016年1月-12月外销部订单汇总表
		String[] month = shipmentMonth.split("-");
		String year = month[0];
		String mon = month[1]; mon = mon.replace("0", "");
		title = year+"年"+mon+"月";
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheetky
		HSSFSheet sheet = wb.createSheet(title+"订单外销统计出库");//多次create可以追加， 删除removeSheetAt

		sheet.setDefaultRowHeightInPoints(25.5f);//默认行高 ，默认列宽setDefaultColumnWidth  8.43
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
      
        //设置列宽， 设置行高setHeight
		double offset = 0.72;//0.72为偏移值（具体没深究，网上给的数据）
		int colIndex = -1;
		colIndex++; sheet.setColumnWidth(colIndex,(int) ((10+offset) * 256));//
        colIndex++; sheet.setColumnWidth(colIndex,(int) ((12+offset) * 256));//下单日期
        colIndex++; sheet.setColumnWidth(colIndex,(int) ((14+offset) * 256));//订单编号
        colIndex++; sheet.setColumnWidth(colIndex,(int) ((13+offset) * 256));//业务员
        colIndex++; sheet.setColumnWidth(colIndex,(int) ((24+offset) * 256));//客户名称
        colIndex++; sheet.setColumnWidth(colIndex,(int) ((14+offset) * 256));//国家
        colIndex++; sheet.setColumnWidth(colIndex,(int) ((14+offset) * 256));//产品名称
        colIndex++; sheet.setColumnWidth(colIndex,(int) ((14+offset) * 256));//订单数量
        colIndex++; sheet.setColumnWidth(colIndex,(int) ((14+offset) * 256));//出货数量
        colIndex++; sheet.setColumnWidth(colIndex,(int) ((14+offset) * 256));//总出货数量
        colIndex++; sheet.setColumnWidth(colIndex,(int) ((13+offset) * 256));//备注
        
		HSSFRow row = sheet.createRow(0); row.setHeightInPoints(25.5f); 

		*//*********头部：9月份出货汇总*********//*
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,8));
        HSSFCellStyle head = wb.createCellStyle();// CellStyle 
        head.setAlignment(HorizontalAlignment.LEFT);// 创建一个居中格式
        head.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFFont fontHead = wb.createFont();
		fontHead.setFontName("微软雅黑");
		fontHead.setFontHeightInPoints((short) 12);// 设置字体大小
		//fontHead.setBold(true);;
		head.setFont(fontHead);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(title+"份出货汇总");
		cell.setCellStyle(head);
		
		*//***********标题： 订单编号	业务员	客户名称	国家	产品名称	订单数量	出货数量	备注...**************//*
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
		
		*//**********正文内容**********//*
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
		*//*********日期格式化****************//*
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
		
		*//**************标题设置**************//*
		int cellIndex = -1;
		row = sheet.createRow(1); row.setHeightInPoints(25.5f); 
		cellIndex++; cell = row.createCell(cellIndex);cell.setCellValue("");cell.setCellStyle(styleTitle);
		cellIndex++; cell = row.createCell(cellIndex);cell.setCellValue("出货日期");cell.setCellStyle(styleTitle);
		cellIndex++; cell = row.createCell(cellIndex);cell.setCellValue("订单编号");cell.setCellStyle(styleTitle);
		cellIndex++; cell = row.createCell(cellIndex);cell.setCellValue("业务员");cell.setCellStyle(styleTitle);
		cellIndex++; cell = row.createCell(cellIndex);cell.setCellValue("客户名称");cell.setCellStyle(styleTitle);
		cellIndex++; cell = row.createCell(cellIndex);cell.setCellValue("国家");cell.setCellStyle(styleTitle);
		cellIndex++; cell = row.createCell(cellIndex);cell.setCellValue("产品名称");cell.setCellStyle(styleTitle);
		cellIndex++; cell = row.createCell(cellIndex);cell.setCellValue("订单数量");cell.setCellStyle(styleTitle);
		cellIndex++; cell = row.createCell(cellIndex);cell.setCellValue("出货数量");cell.setCellStyle(styleTitle);
		cellIndex++; cell = row.createCell(cellIndex);cell.setCellValue("总出货数量");cell.setCellStyle(styleTitle);
		cellIndex++; cell = row.createCell(cellIndex);cell.setCellValue("备注");cell.setCellStyle(styleTitle);
		
		sheet.createFreezePane(0, 2); // 冻结第1行
		CellRangeAddress c = CellRangeAddress.valueOf("A2:I2");//排序和刷选
		sheet.setAutoFilter(c);
		
		//数据为空，则退出
		if (saleOutList == null || saleOutList.isEmpty()) {
	        sheet.addMergedRegion(new CellRangeAddress(2,2,0,5));
			row = sheet.createRow(2); row.setHeightInPoints(35.5f); //第二行 
			cell = row.createCell(0);
			cell.setCellValue("数据记录为空！");
			cell.setCellStyle(styleBody);
			return wb;
		}
		
		*//**************内容设置**************//*
		String week = "";//总计使用变量
		String weekTemp = "";//总计使用变量
		int beginIndex = 3;//总计使用变量
		int i = 1;
		for (SaleOutDto saleOutDto : saleOutList) {
			i++;
			if (i == 2) {
				week = saleOutDto.getShipmentWeek();
			}
			weekTemp = saleOutDto.getShipmentWeek();
			if (!week.equals(weekTemp)) {
				row = sheet.createRow(i); row.setHeightInPoints(30f); //第二行,设置默认行高
				cell = row.createCell(0);
				cell.setCellValue(week); cell.setCellStyle(styleBody);
				cell = row.createCell(1); cell.setCellStyle(styleDate);
				cell = row.createCell(2); cell.setCellStyle(styleBody);
				cell = row.createCell(3); cell.setCellStyle(styleBody);
				cell = row.createCell(4); cell.setCellStyle(styleBody);
				cell = row.createCell(5); cell.setCellStyle(styleBody);
				cell = row.createCell(6); cell.setCellValue("总计"); cell.setCellStyle(styleBody);
				cell = row.createCell(7); cell.setCellFormula("SUM(H"+(beginIndex)+":H"+(i)+")"); cell.setCellStyle(styleBody);
				cell = row.createCell(8); cell.setCellFormula("SUM(I"+(beginIndex)+":I"+(i)+")"); cell.setCellStyle(styleBody);
				cell = row.createCell(9); cell.setCellFormula("SUM(J"+(beginIndex)+":J"+(i)+")"); cell.setCellStyle(styleBody);
				cell = row.createCell(10); cell.setCellStyle(styleBody);
		        i++;
		        beginIndex = i+1;
		        week = saleOutDto.getShipmentWeek();
			}
			row = sheet.createRow(i); row.setHeightInPoints(30f); //第二行,设置默认行高
			cell = row.createCell(0);
			if (saleOutDto.getShipmentWeek() != null)
			cell.setCellValue(saleOutDto.getShipmentWeek());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(1);
			if (saleOutDto.getShipmentDate() != null)
			cell.setCellValue(saleOutDto.getShipmentDate());
			cell.setCellStyle(styleDate);
			
			cell = row.createCell(2);
			if (saleOutDto.getOrderNo() != null)
			cell.setCellValue(saleOutDto.getOrderNo());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(3);
			if (saleOutDto.getSeller() != null)
			cell.setCellValue(saleOutDto.getSeller());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(4);
			if (saleOutDto.getClientName() != null)
			cell.setCellValue(saleOutDto.getClientName());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(5);
			if (saleOutDto.getDistrict() != null)
			cell.setCellValue(saleOutDto.getDistrict());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(6);
			if (saleOutDto.getProductType() != null)
			cell.setCellValue(saleOutDto.getProductType());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(7);
			if (saleOutDto.getQuantity() != null)
			cell.setCellValue(saleOutDto.getQuantity());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(8);
			if (saleOutDto.getShipmentNo() != null)
			cell.setCellValue(saleOutDto.getShipmentNo());
			cell.setCellStyle(styleBody);
			
			cell = row.createCell(9);
			if (saleOutDto.getShipmentTotal() != null)
			cell.setCellValue(saleOutDto.getShipmentTotal());
			cell.setCellStyle(styleBody);
			
			
			cell = row.createCell(10);
			cell.setCellStyle(styleBody);
			if(saleOutDto.getShipmentTotal() != null && saleOutDto.getQuantity() != null) {
				if ((saleOutDto.getShipmentTotal() - saleOutDto.getQuantity()) >= 0) {
					cell.setCellValue("已出完");
				} else {
					cell.setCellValue("未出完");
				}
			}
		}
		i= i+1;
		row = sheet.createRow(i); row.setHeightInPoints(30f); //第二行,设置默认行高
		cell = row.createCell(0);
		cell.setCellValue(week); cell.setCellStyle(styleBody);
		cell = row.createCell(1); cell.setCellStyle(styleDate);
		cell = row.createCell(2); cell.setCellStyle(styleBody);
		cell = row.createCell(3); cell.setCellStyle(styleBody);
		cell = row.createCell(4); cell.setCellStyle(styleBody);
		cell = row.createCell(5); cell.setCellStyle(styleBody);
		cell = row.createCell(6); cell.setCellValue("总计"); cell.setCellStyle(styleBody);
		cell = row.createCell(7); cell.setCellFormula("SUM(H"+(beginIndex)+":H"+(i)+")"); cell.setCellStyle(styleBody);
		cell = row.createCell(8); cell.setCellFormula("SUM(I"+(beginIndex)+":I"+(i)+")"); cell.setCellStyle(styleBody);
		cell = row.createCell(9); cell.setCellFormula("SUM(J"+(beginIndex)+":J"+(i)+")"); cell.setCellStyle(styleBody);
		cell = row.createCell(10); cell.setCellStyle(styleBody);
		
		String cellValue = "";
		String cellValueTemp = "";
		int firstIndex = 1;
		int n = 2;
		for (n=2; n<1000000; n++) {//合并单元格
			row = sheet.getRow(n);
			if (row == null) {
				break;
			}
			HSSFCell cell2 = row.getCell(0);
			if (n == 2) {
				cellValue = cell2.getStringCellValue();
			}
			cellValueTemp = cell2.getStringCellValue();
			if (!cellValue.equals(cellValueTemp)) {
				if ((firstIndex+1) != (n-1))
		        sheet.addMergedRegion(new CellRangeAddress((firstIndex+1),(n-1),0,0));
		        firstIndex = n-1;
		        cellValue = cell2.getStringCellValue();
			}
			//System.out.println(cell2.getStringCellValue());
		}
		if ((firstIndex+1) != (n-1))
        sheet.addMergedRegion(new CellRangeAddress((firstIndex+1),(n-1),0,0));
		
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
		//SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		List<SaleOutDto> list = new LinkedList<SaleOutDto>();
		SaleOutDto s = new SaleOutDto();
		s.setClientName("apical");
		s.setSeller("凌国鑫");
		s.setDistrict("美国");
		s.setOrderNo("WS16020003");
		s.setProductType("PND");
		s.setQuantity(10200);
		s.setShipmentNo(1222);
		s.setShipmentTotal(10000);
		s.setShipmentWeek("第一周");
		s.setShipmentDate(new Date());
		list.add(s);
		
		s = new SaleOutDto();
		s.setClientName("apical");
		s.setSeller("凌国鑫");
		s.setDistrict("美国");
		s.setOrderNo("WS16020003");
		s.setProductType("PND");
		s.setQuantity(10200);
		s.setShipmentNo(1222);
		s.setShipmentTotal(10200);
		s.setShipmentWeek("第三周");
		s.setShipmentDate(new Date());
		list.add(s);
		
		s = new SaleOutDto();
		s.setClientName("apical");
		s.setSeller("凌国鑫");
		s.setDistrict("美国");
		s.setOrderNo("WS16020003");
		s.setProductType("PND");
		s.setQuantity(10200);
		s.setShipmentNo(1222);
		s.setShipmentTotal(10000);
		s.setShipmentWeek("第三周");
		list.add(s);
		
		s = new SaleOutDto();
		s.setClientName("apical");
		s.setSeller("凌国鑫");
		s.setDistrict("美国");
		s.setOrderNo("WS16020003");
		s.setProductType("PND");
		s.setQuantity(10200);
		s.setShipmentNo(1222);
		s.setShipmentTotal(10000);
		s.setShipmentWeek("第三周");
		list.add(s);
		s = new SaleOutDto();
		s.setClientName("apical");
		s.setSeller("凌国鑫");
		s.setDistrict("美国");
		s.setOrderNo("WS16020003");
		s.setProductType("PND");
		s.setQuantity(10200);
		s.setShipmentNo(1222);
		s.setShipmentTotal(10000);
		s.setShipmentWeek("第四周");
		list.add(s);
		try {
			SaleOutExportExcel.export(list, "2016-01-01");
			System.out.println("成功");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	*/
}
