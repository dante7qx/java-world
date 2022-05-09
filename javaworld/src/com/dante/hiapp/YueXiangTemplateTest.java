package com.dante.hiapp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 读取悦享账单测试
 * 
 * @author dante
 *
 */
public class YueXiangTemplateTest {
	
	private static final String ORDER_CELL = "订单编号";
	private static final String HOTEL_TYPE_CELL = "类型";
	
	public static void main(String[] args) {
		XSSFWorkbook xwb = null;
		try {
			xwb = new XSSFWorkbook(
					new FileInputStream(
							"/Users/dante/Documents/Project/javaworld/src/com/dante/hiapp/悦享账单.xlsx"));
			XSSFSheet sheet = xwb.getSheet("预付酒店");
			XSSFRow row = null;
			XSSFCell orderCell = null;
			XSSFCell hotelTypeCell = null;
			int firstRow = sheet.getFirstRowNum();
			int rowSize = sheet.getPhysicalNumberOfRows();
			boolean readOrder = false;
			for (int i = firstRow; i < rowSize; i++) {
				row = sheet.getRow(i);
				orderCell = row.getCell(1);
				hotelTypeCell = row.getCell(2);
				if(orderCell != null && hotelTypeCell != null && ORDER_CELL.equals(orderCell.getStringCellValue()) && HOTEL_TYPE_CELL.equals(hotelTypeCell.getStringCellValue())) {
					System.out.println(orderCell.getStringCellValue() + " ------- " + hotelTypeCell.getStringCellValue());
					readOrder = true;
				} 
				if(readOrder && orderCell != null && orderCell.getStringCellValue().startsWith("H")) {
					System.out.println(orderCell.getStringCellValue() + " ------- " + hotelTypeCell.getStringCellValue());
				} else {
					continue;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(xwb != null) {
				try {
					xwb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
