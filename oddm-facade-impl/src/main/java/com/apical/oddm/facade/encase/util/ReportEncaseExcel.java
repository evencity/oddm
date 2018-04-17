package com.apical.oddm.facade.encase.util;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import com.apical.oddm.facade.encase.dto.EncaseInfoDTO;
import com.apical.oddm.facade.encase.dto.EncaseListDTO;

public class ReportEncaseExcel {

	@SuppressWarnings("deprecation")
	public HSSFWorkbook creatExcelEncaseInfo(EncaseInfoDTO encaseInfo) throws Exception  
    {  
        // 第一步，创建一个webbook，对应一个Excel文件  
		HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("订单包装任务书：");  
        
        // 第三步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        
        sheet.setColumnWidth(0, 30 * 100);
        sheet.setColumnWidth(1, 30 * 100);
        sheet.setColumnWidth(2, 30 * 100);
        
        //居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        HSSFFont font=wb.createFont();
        font.setFontHeightInPoints((short)13);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   
        style.setFont(font);
        
        HSSFCellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        HSSFFont font1=wb.createFont();
        font1.setFontHeightInPoints((short)10);
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   
        style1.setFont(font1);
        style1.setWrapText(true);
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框  
        
        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        HSSFFont font2=wb.createFont();
        font2.setFontHeightInPoints((short)10);
        style2.setFont(font2);
        style2.setWrapText(true);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框  
        
        HSSFRow row0 = sheet.createRow((int) 0);  
        HSSFCell cell0 = row0.createCell((short) 0);  
        cell0.setCellValue(new HSSFRichTextString("SHENZHEN APICAL TECHNOLOGY CO.,LTD"));  
        HSSFRow row1 = sheet.createRow((int) 2);  
        HSSFCell cell1 = row1.createCell((short) 2);  
        cell1.setCellValue(new HSSFRichTextString(encaseInfo.getAddress())); 
        HSSFRow row2 = sheet.createRow((int) 3);  
        HSSFCell cell2 = row2.createCell((short) 4);  
        cell2.setCellValue(new HSSFRichTextString(encaseInfo.getZipcode()+encaseInfo.getTelphone() 
        		+encaseInfo.getHomepage()));
        HSSFRow row3 = sheet.createRow((int) 6);  
        row3.setHeight((short) 400);
        HSSFCell cell3 = row3.createCell((short) 7);  
        cell3.setCellValue(new HSSFRichTextString("PACKING LIST"));
        cell3.setCellStyle(style);
        
        //合并单元格
//        Region region3 = new Region(0, (short)2, 0, (short)15);
//        Region region4 = new Region(0, (short)3, 0, (short)15);
//        Region region5 = new Region(0, (short)4, 0, (short)15);
//        Region region6 = new Region(0, (short)6, 0, (short)15);
        
        //表格主体
        HSSFRow row4 = sheet.createRow(7);  
        HSSFCell cell00_ = row4.createCell(6);
        HSSFCell cell01_ = row4.createCell(7);
        HSSFCell cell02_ = row4.createCell(8);
        HSSFCell cell03_ = row4.createCell(9);
        HSSFCell cell04_ = row4.createCell(10);
        HSSFCell cell05_ = row4.createCell(11);
        HSSFCell cell06_ = row4.createCell(12);
        HSSFCell cell07_ = row4.createCell(13);
        HSSFCell cell09_ = row4.createCell(14);
        HSSFCell cell08_ = row4.createCell(15);
        cell00_.setCellStyle(style1);
        cell01_.setCellStyle(style1);
        cell02_.setCellStyle(style1);
        cell03_.setCellStyle(style1);
        cell04_.setCellStyle(style1);
        cell05_.setCellStyle(style1);
        cell06_.setCellStyle(style1);
        cell07_.setCellStyle(style1);
        cell08_.setCellStyle(style1);
        cell09_.setCellStyle(style1);
        HSSFCell cell4 = row4.createCell(0);   
        cell4.setCellValue(new HSSFRichTextString("C/NO箱及编号"));  
        cell4.setCellStyle(style1);
        HSSFCell cell5 = row4.createCell(1);  
        cell5.setCellValue(new HSSFRichTextString("Item No.产品编号(客户型号)"));
        cell5.setCellStyle(style1);
        HSSFCell cell6 = row4.createCell(2);  
        cell6.setCellValue(new HSSFRichTextString("Description"));
        cell6.setCellStyle(style1);
        HSSFCell cell7 = row4.createCell(3);  
        cell7.setCellValue(new HSSFRichTextString("QTY数量"));
        cell7.setCellStyle(style1);
        HSSFCell cell8 = row4.createCell(4);  
        cell8.setCellValue(new HSSFRichTextString("UNIT单位"));
        cell8.setCellStyle(style1);
        HSSFCell cell9 = row4.createCell(5);  
        cell9.setCellValue(new HSSFRichTextString("IN ONE CARTON纸板箱"));
        cell9.setCellStyle(style1);
        HSSFCell cell19 = row4.createCell(11);  
        cell19.setCellValue(new HSSFRichTextString("TOTAL 总统计"));
        cell19.setCellStyle(style1);
        HSSFCell cell28 = row4.createCell(15);  
        cell28.setCellValue(new HSSFRichTextString("REMARK备注"));
        cell28.setCellStyle(style1);
        
        HSSFRow row5 = sheet.createRow(8);  
        HSSFCell cell10_ = row5.createCell(0);
        HSSFCell cell11_ = row5.createCell(1);
        HSSFCell cell12_ = row5.createCell(2);
        HSSFCell cell13_ = row5.createCell(3);
        HSSFCell cell14_ = row5.createCell(4);
        HSSFCell cell115_ = row5.createCell(15);
        cell10_.setCellStyle(style1);
        cell11_.setCellStyle(style1);
        cell12_.setCellStyle(style1);
        cell13_.setCellStyle(style1);
        cell14_.setCellStyle(style1);
        cell115_.setCellStyle(style1);
        HSSFCell cell10 = row5.createCell(5);
        cell10.setCellValue(new HSSFRichTextString("QTY/Ctn每箱的数量"));
        cell10.setCellStyle(style1);
        HSSFCell cell11 = row5.createCell(6);  
        cell11.setCellValue(new HSSFRichTextString("N.W.净重"));
        cell11.setCellStyle(style1);
        HSSFCell cell12 = row5.createCell(7);  
        cell12.setCellValue(new HSSFRichTextString("G.W.毛重"));
        cell12.setCellStyle(style1);
        HSSFCell cell13 = row5.createCell(8);  
        cell13.setCellValue(new HSSFRichTextString("Carton Size(MM)单个纸箱尺寸"));
        cell13.setCellStyle(style1);
        HSSFCell cell20 = row5.createCell(11);  
        cell20.setCellValue(new HSSFRichTextString("CTN装箱量"));
        cell20.setCellStyle(style1);
        HSSFCell cell21 = row5.createCell(12);  
        cell21.setCellValue(new HSSFRichTextString("Meas总尺寸(单箱的立方*总箱数)"));
        cell21.setCellStyle(style1);
        HSSFCell cell22 = row5.createCell(13);  
        cell22.setCellValue(new HSSFRichTextString("N.W."));
        cell22.setCellStyle(style1);
        HSSFCell cell23 = row5.createCell(14);  
        cell23.setCellValue(new HSSFRichTextString("G.W."));
        cell23.setCellStyle(style1);
        
        HSSFRow row6 = sheet.createRow(9); 
        HSSFCell cell20_ = row6.createCell(0);
        HSSFCell cell21_ = row6.createCell(1);
        HSSFCell cell22_ = row6.createCell(2);
        HSSFCell cell23_ = row6.createCell(3);
        HSSFCell cell24_ = row6.createCell(4);
        HSSFCell cell25_ = row6.createCell(5);
        HSSFCell cell26_ = row6.createCell(15);
        cell20_.setCellStyle(style1);
        cell21_.setCellStyle(style1);
        cell22_.setCellStyle(style1);
        cell23_.setCellStyle(style1);
        cell24_.setCellStyle(style1);
        cell25_.setCellStyle(style1);
        cell26_.setCellStyle(style1);
        HSSFCell cell14 = row6.createCell(6);  
        cell14.setCellValue(new HSSFRichTextString("(kg)"));
        cell14.setCellStyle(style1);
        HSSFCell cell15 = row6.createCell(7);  
        cell15.setCellValue(new HSSFRichTextString("(kg)"));
        cell15.setCellStyle(style1);
        HSSFCell cell16 = row6.createCell(8);  
        cell16.setCellValue(new HSSFRichTextString("Length"));
        cell16.setCellStyle(style1);
        HSSFCell cell17 = row6.createCell(9);  
        cell17.setCellValue(new HSSFRichTextString("Width"));
        cell17.setCellStyle(style1);
        HSSFCell cell18 = row6.createCell(10);  
        cell18.setCellValue(new HSSFRichTextString("Height"));
        cell18.setCellStyle(style1);
        HSSFCell cell24 = row6.createCell(11);  
        cell24.setCellValue(new HSSFRichTextString("QTY总箱数"));
        cell24.setCellStyle(style1);
        HSSFCell cell25 = row6.createCell(12);  
        cell25.setCellValue(new HSSFRichTextString("(M³)"));
        cell25.setCellStyle(style1);
        HSSFCell cell26 = row6.createCell(13);  
        cell26.setCellValue(new HSSFRichTextString("(kg)"));
        cell26.setCellStyle(style1);
        HSSFCell cell27 = row6.createCell(14);  
        cell27.setCellValue(new HSSFRichTextString("(kg)"));
        cell27.setCellStyle(style1);
        
        //表中数据填充
        HSSFSheet hssfSheet = wb.getSheetAt(0);
        
        List<EncaseListDTO> list = encaseInfo.getEncaseList();
        for(int i = 0; i < list.size(); i++){
        	 HSSFRow row10 = sheet.createRow(10+i); 
        	 HSSFCell cell_d_1 = row10.createCell(0);  
        	 cell_d_1.setCellValue(new HSSFRichTextString(list.get(i).getCNo()));
        	 cell_d_1.setCellStyle(style2);
        	 HSSFCell cell_d_2 = row10.createCell(1);  
        	 cell_d_2.setCellValue(new HSSFRichTextString(list.get(i).getItemNo()));
        	 cell_d_2.setCellStyle(style2);
        	 HSSFCell cell_d_3 = row10.createCell(2);  
        	 cell_d_3.setCellValue(new HSSFRichTextString(list.get(i).getDescription()));
        	 cell_d_3.setCellStyle(style2);
        	 HSSFCell cell_d_4 = row10.createCell(3);  
        	 cell_d_4.setCellValue(new HSSFRichTextString(list.get(i).getQty()+""));
        	 cell_d_4.setCellStyle(style2);
        	 HSSFCell cell_d_5 = row10.createCell(4);  
        	 cell_d_5.setCellValue(new HSSFRichTextString(list.get(i).getUnit()));
        	 cell_d_5.setCellStyle(style2);
        	 HSSFCell cell_d_6 = row10.createCell(5);  
        	 cell_d_6.setCellValue(new HSSFRichTextString(list.get(i).getQtyCtn()+""));
        	 cell_d_6.setCellStyle(style2);
        	 HSSFCell cell_d_7 = row10.createCell(6);  
        	 cell_d_7.setCellValue(new HSSFRichTextString(list.get(i).getnW()+""));
        	 cell_d_7.setCellStyle(style2);
        	 HSSFCell cell_d_8 = row10.createCell(7);  
        	 cell_d_8.setCellValue(new HSSFRichTextString(list.get(i).getgW()+""));
        	 cell_d_8.setCellStyle(style2);
        	 HSSFCell cell_d_9 = row10.createCell(8);  
        	 cell_d_9.setCellValue(new HSSFRichTextString(list.get(i).getLength()+""));
        	 cell_d_9.setCellStyle(style2);
        	 HSSFCell cell_d_10 = row10.createCell(9);  
        	 cell_d_10.setCellValue(new HSSFRichTextString(list.get(i).getWidth()+""));
        	 cell_d_10.setCellStyle(style2);
        	 HSSFCell cell_d_11 = row10.createCell(10);  
        	 cell_d_11.setCellValue(new HSSFRichTextString(list.get(i).getHight()+""));
        	 cell_d_11.setCellStyle(style2);
        	 HSSFCell cell_d_16 = row10.createCell(15);  
        	 cell_d_16.setCellValue(new HSSFRichTextString(list.get(i).getRemark()+""));
        	 cell_d_16.setCellStyle(style2);
        	 
        	 //计算总箱数
        	 //QTY 数量
        	 HSSFRow QTY = hssfSheet.getRow(10);
        	 HSSFCell QTY_ = QTY.getCell(3);
        	 //QTY/Ctn每箱的数量
        	 HSSFRow QTY_Ctn = hssfSheet.getRow(10);
             HSSFCell QTY_Ctn_ = QTY_Ctn.getCell(5);
             //总箱数
             int QTY_c = 0;
             HSSFCell cell_d_12 = row10.createCell(11);
             if(QTY_ != null && QTY_Ctn_ != null){
            	 QTY_c = Integer.valueOf(getValue(QTY_)) / Integer.valueOf(getValue(QTY_Ctn_));
            	 cell_d_12.setCellValue(new HSSFRichTextString(QTY_c+""));
            	 cell_d_12.setCellStyle(style2);
             }
        	//length
        	 HSSFRow length = hssfSheet.getRow(10);
        	 HSSFCell length_ = length.getCell(8);
        	 //width
        	 HSSFRow width = hssfSheet.getRow(10);
             HSSFCell width_ = width.getCell(9);
             //height
        	 HSSFRow height = hssfSheet.getRow(10);
             HSSFCell height_ = height.getCell(10);
        	 //M3
        	 HSSFCell cell_d_13 = row10.createCell(12);
             int M3 = Integer.valueOf(getValue(length_)) * Integer.valueOf(getValue(width_))
            		 * Integer.valueOf(getValue(height_)) / 1000000000 * QTY_c;
             cell_d_13.setCellValue(new HSSFRichTextString(M3+""));
             cell_d_13.setCellStyle(style2);
             //N.W.
             HSSFRow nW = hssfSheet.getRow(10);
             HSSFCell nW_ = nW.getCell(6);
             //NW
             HSSFCell cell_d_14 = row10.createCell(13);
             int NW = Integer.valueOf(getValue(nW_)) * QTY_c;
             cell_d_14.setCellValue(new HSSFRichTextString(NW+""));
             cell_d_14.setCellStyle(style2);
             //G.W.
             HSSFRow gW = hssfSheet.getRow(10);
             HSSFCell gW_ = gW.getCell(7);
             //GW
             HSSFCell cell_d_15 = row10.createCell(14);
             int GW = Integer.valueOf(getValue(gW_)) * QTY_c;
             cell_d_15.setCellValue(new HSSFRichTextString(GW+""));
             cell_d_15.setCellStyle(style2);
        }
        //合并描述的单元格
        
        
       //描述
        HSSFRow row7 = sheet.createRow(10 + list.size() + 1); 
        HSSFCell cell29 = row7.createCell(0);  
        for(int j = 0; j < 16; j++){
        	 row7.createCell(j).setCellStyle(style1);  
        }
        cell29.setCellValue(new HSSFRichTextString(encaseInfo.getDescription()));
        cell29.setCellStyle(style2);
//        Region region17 = new Region(10 + list.size() + 1, (short)0, 10 + list.size() + 1, (short)15);
        HSSFRow row8= sheet.createRow(10 + list.size() + 2); 
        HSSFCell cell30 = row8.createCell(2);  
        for(int j = 0; j < 16; j++){
        	row8.createCell(j).setCellStyle(style1);  
        }
        cell30.setCellValue(new HSSFRichTextString("Total"));
        cell30.setCellStyle(style2);  
        //总计
//        Region region18 = new Region(10 + list.size() + 3, (short)0, 10 + list.size() + 3, (short)4);
//        Region region19 = new Region(10 + list.size() + 4, (short)0, 10 + list.size() + 4, (short)4);
//        Region region20 = new Region(10 + list.size() + 3, (short)0, 10 + list.size() + 4, (short)0);
        
        HSSFRow row9= sheet.createRow(10 + list.size() + 3); 
        HSSFCell cell31 = row9.createCell(0);  
        for(int j = 0; j < 16; j++){
        	row9.createCell(j).setCellStyle(style2);  
        }
        cell31.setCellValue(new HSSFRichTextString("(总计)Total"));
        cell31.setCellStyle(style1);
        HSSFRow row10 = sheet.createRow(10 + list.size() + 4);
        for(int j = 0; j < 16; j++){
        	row10.createCell(j).setCellStyle(style1);  
        }
        HSSFCell cell32 = row10.createCell(11);  
        cell32.setCellValue(new HSSFRichTextString("CTN"));
        cell32.setCellStyle(style1);  
        HSSFCell cell33 = row10.createCell(12);  
        cell33.setCellValue(new HSSFRichTextString("M³"));
        cell33.setCellStyle(style1);  
        HSSFCell cell34 = row10.createCell(13);  
        cell34.setCellValue(new HSSFRichTextString("N.W.(kg)"));
        cell34.setCellStyle(style1);  
        HSSFCell cell35 = row10.createCell(14);  
        cell35.setCellValue(new HSSFRichTextString("G.W.(kg)"));
        cell35.setCellStyle(style1);  
        
//        Region region1 = new Region(0, (short)0, 0, (short)15);
//        Region region2 = new Region(1, (short)0, 1, (short)15);
//        Region region21 = new Region(0, (short)0, 1, (short)0);
//        
//        Region region7 = new Region(7, (short)0, 9, (short)0);
//        Region region8 = new Region(7, (short)1, 9, (short)1);
//        Region region9 = new Region(7, (short)2, 9, (short)2);
//        Region region10 = new Region(7, (short)3, 9, (short)3);
//        Region region11 = new Region(7, (short)4, 9, (short)4);
//        
//        Region region12 = new Region(7, (short)5, 7, (short)10);//IN ONE CARTON纸板箱
//        Region region13 = new Region(8, (short)5, 9, (short)5);
//        Region region14 = new Region(8, (short)8, 8, (short)10);
//        
//        Region region15 = new Region(7, (short)11, 7, (short)14);// TOTAL 总统计
//        Region region16 = new Region(7, (short)15, 9, (short)15);//REMARK备注
//        
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
 //       sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
  //      sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 15));
        sheet.addMergedRegion(new CellRangeAddress(7, 9, 0, 0));   
        sheet.addMergedRegion(new CellRangeAddress(7, 9, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(7, 9, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(7, 9, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(7, 9, 4, 4));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 5, 10));
        sheet.addMergedRegion(new CellRangeAddress(8, 9, 5, 5));     
        sheet.addMergedRegion(new CellRangeAddress(8, 8, 8, 10));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 11, 14)); 
        sheet.addMergedRegion(new CellRangeAddress(7, 9, 15, 15));
        sheet.addMergedRegion(new CellRangeAddress(10 + list.size() + 3, 10 + list.size() + 3, 0, 4));
        sheet.addMergedRegion(new CellRangeAddress(10 + list.size() + 4, 10 + list.size() + 4, 0, 4));
 //       sheet.addMergedRegion(new CellRangeAddress(10 + list.size() + 3, 10 + list.size() + 4, 0, 0));
 //       sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 15));
//        sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 15));
//        sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 15));
//        sheet.addMergedRegion(new CellRangeAddress(0, 0, 6, 15));
        sheet.addMergedRegion(new CellRangeAddress(10 + list.size() + 1, 10 + list.size() + 1, 0, 15));
        return wb;
    }
	
	 @SuppressWarnings("static-access")
	      private String getValue(HSSFCell hssfCell) {
	         if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
	              return String.valueOf(hssfCell.getBooleanCellValue());
	          } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
	             return String.valueOf(hssfCell.getNumericCellValue());
	          } else {
	             return String.valueOf(hssfCell.getStringCellValue());
	          }
	     }
}
