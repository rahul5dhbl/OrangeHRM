package com.orange.utils;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelActions 
{
	public static List<List<String>> ReadDataFromExcelInList(String filePath, String sheetName)
	{
		FileInputStream fis = null;
		List<List<String>> excelData = new ArrayList<List<String>>();
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			int lastRow = sheet.getLastRowNum();

			for(int i=1; i<lastRow ; i++)
			{
				List<String> rowData = new ArrayList<String>();
				int lastColumn = sheet.getRow(i).getLastCellNum();
				for(int j=0; j<lastColumn ; j++)
				{
					XSSFCell cell = sheet.getRow(i).getCell(j);
					int cellType = cell.getCellType();
					String strValue = null;
					if(cellType==Cell.CELL_TYPE_NUMERIC)
					{
						if(HSSFDateUtil.isCellDateFormatted(cell))
						{
							DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
							Date date = cell.getDateCellValue();
							strValue = df.format(date);
						}
						else
						{
							double numValue = cell.getNumericCellValue();
							strValue = String.valueOf(numValue);
						}
					}
					if(cellType==Cell.CELL_TYPE_STRING)
					{
						strValue = cell.getStringCellValue(); 
					}
					rowData.add(strValue);
				}
				excelData.add(rowData);				
			}
			return excelData;
		}catch (Exception e) {
			System.out.println("Exception in ExcelActions --> ReadDataFromExcelInList : "+e);
			return null;
		} finally {
			try {
				if(fis!=null ) {fis.close();}
			}catch (Exception e) {}
		}
	}
	
	public static Map<String, String[]> ReadDataFromExcelInMap(String filePath, String sheetName)
	{
		FileInputStream fis = null;
		XSSFWorkbook workbook = null;
		Map<String, String[]> excelData = new HashMap<String, String[]>();
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			int lastRow = sheet.getLastRowNum();

			for(int i=1; i<=lastRow ; i++)
			{
				int lastColumn = sheet.getRow(i).getLastCellNum();
				String[] arrKeywordData = new String[lastColumn];
				String keywordValue = sheet.getRow(i).getCell(0).getStringCellValue();
				for(int j=1; j<lastColumn ; j++)
				{
					XSSFCell cell = sheet.getRow(i).getCell(j);
					String strValue = null;
					strValue = ExcelActions.getStringValue(cell);
					/*int cellType = cell.getCellType();
					
					if(cellType==Cell.CELL_TYPE_NUMERIC)
					{
						if(HSSFDateUtil.isCellDateFormatted(cell))
						{
							DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
							Date date = cell.getDateCellValue();
							strValue = df.format(date);
						}
						else
						{
							double numValue = cell.getNumericCellValue();
							strValue = String.valueOf(numValue);
						}
					}
					if(cellType==Cell.CELL_TYPE_STRING)
					{
						strValue = cell.getStringCellValue(); 
					}*/
					arrKeywordData[j] = strValue;
				}
				excelData.put(keywordValue, arrKeywordData);
				
			}
			return excelData;
		}catch (Exception e) {
			System.out.println("Exception in ExcelActions --> ReadDataFromExcelInMap : "+e);
			return null;
		} finally {
			try {
				workbook = null;
				if(fis!=null ) {fis.close();}
			}catch (Exception e) {}
		}
	}
	
	public static String getStringValue(XSSFCell cell) {
		try {
			int cellType;
			try {
				cellType = cell.getCellType();
			}catch (NullPointerException e) {
				return "";
			}
			String data = null;
			switch (cellType) {
			case Cell.CELL_TYPE_STRING:
				data = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if(HSSFDateUtil.isCellDateFormatted(cell))
				{
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					Date date = cell.getDateCellValue();
					data = df.format(date);
				}
				else
				{
					double numValue = cell.getNumericCellValue();
					data = String.valueOf(numValue);
				}
				break;
			default:
				break;
			}
			return data;
		} catch (Exception e) {
			System.out.println("Exception in ExcelActions --> getStringValue : "+e);
			return null;
		}
	}
}
