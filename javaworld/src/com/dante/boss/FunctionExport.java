package com.dante.boss;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class FunctionExport {
	
	public final static String OCS = "ocs";		// OCS数据源
	public final static String CRM = "crm";		// CRM数据源
	
	private final static String CELL = "cell";		// 按单元格区分
	private final static String BLANK = "blank";	// 按空格区分
	
	/**
	 * 导出excel
	 * 
	 * @param funcs
	 */
	public void exportExcel(String fileName, List<FunctionVo> funcs, String type) {
		FileOutputStream fileOut = null;
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("系统功能权限");
			if(CELL.equals(type)) {
				this.buildExcelContentWithCellLevel(sheet, funcs);
			} else if(BLANK.equals(type)) {
				this.buildExcelContentWithBlank(sheet, funcs);
			}
		    fileOut = new FileOutputStream(fileName);    
            wb.write(fileOut);    
            wb.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fileOut != null){    
                try {    
                    fileOut.close();    
                } catch (IOException e) {    
                    e.printStackTrace();    
                }    
            }
		}
	}
	
	/**
	 * 按照空格分级
	 * 
	 * @param sheet
	 * @param funcs
	 */
	private void buildExcelContentWithBlank(HSSFSheet sheet, List<FunctionVo> funcs) {
		for (FunctionVo func : funcs) {
			this.convertFunctionVoToHSSFRowBlank(sheet, func);
			this.buildExcelContentWithBlank(sheet, func.getChilds());
		}
	}
	
	/**
	 * 按照单元格分级
	 * 
	 * @param sheet
	 * @param funcs
	 */
	private void buildExcelContentWithCellLevel(HSSFSheet sheet, List<FunctionVo> funcs) {
		for (FunctionVo func : funcs) {
			this.convertFunctionVoToHSSFRowCell(sheet, func);
			this.buildExcelContentWithCellLevel(sheet, func.getChilds());
		}
	}
	
	private HSSFRow convertFunctionVoToHSSFRowCell(HSSFSheet sheet, FunctionVo func) {
		return this.convertFunctionVoToHSSFRow(sheet, func, func.getLevel(), func.getFuncName());
	}
	
	private HSSFRow convertFunctionVoToHSSFRowBlank(HSSFSheet sheet, FunctionVo func) {
		return this.convertFunctionVoToHSSFRow(sheet, func, 0, func.getExcelFuncName());
	}
	
	private HSSFRow convertFunctionVoToHSSFRow(HSSFSheet sheet, FunctionVo func, int cellIndex, String cellValue) {
		HSSFRow row = sheet.createRow(func.getIndex());
		HSSFCell cell = row.createCell(cellIndex);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(cellValue);
		return row;
	}
	
	public static void main(String[] args) throws Exception {
		FunctionExport funcExpo = new FunctionExport();
		FunctionService funcService = new FunctionService();
		System.out.println("导出开始：" + new Date());
		
		List<FunctionVo> ocsfuncs = funcService.getFuncsByCode(OCS, "ocs");
		List<FunctionVo> crmfuncs = funcService.getFuncsByCode(CRM, "crm");
		funcExpo.exportExcel("/Users/dante/Documents/Project/javaworld/src/com/dante/boss/ocs系统功能权限1.xls", ocsfuncs, CELL);
		funcExpo.exportExcel("/Users/dante/Documents/Project/javaworld/src/com/dante/boss/ocs系统功能权限2.xls", ocsfuncs, BLANK);
		funcExpo.exportExcel("/Users/dante/Documents/Project/javaworld/src/com/dante/boss/crm系统功能权限1.xls", crmfuncs, CELL);
		funcExpo.exportExcel("/Users/dante/Documents/Project/javaworld/src/com/dante/boss/crm系统功能权限2.xls", crmfuncs, BLANK);
		
		System.out.println("导出结束：" + new Date());
		
	}
	
}
