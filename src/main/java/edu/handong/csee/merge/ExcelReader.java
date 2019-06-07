package edu.handong.csee.merge;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {

	public ArrayList<String> getData(String path) {
		ArrayList<String> values = new ArrayList<String>();

		System.out.println(path);

		try (InputStream inp = new FileInputStream(path)) {
			// InputStream inp = new FileInputStream("workbook.xlsx");

			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);
			Row row = sheet.getRow(2);
			Cell cell = row.getCell(1);
			if (cell == null)
				cell = row.createCell(3);

			values.add(cell.getStringCellValue());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return values;
	}

	public ArrayList<Object> getData(InputStream is) {
		ArrayList<Object> values = new ArrayList<Object>();

		try (InputStream inp = is) {

			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);

			for (Row row : sheet) {
				if (row.getRowNum() == 1) {
					String toAdd = Integer.toString(-1);
					values.add(toAdd);
				}
				
				for (Cell cell : row) {
					if (cell != null) {
						Object cellValue = null;
						CellType cellType = cell.getCellType();// 
						
						if (cellType.equals(CellType.STRING)) {
							cellValue = cell.getStringCellValue());
							values.add(cellValue);
						} else if (cellType.equals(CellType.NUMERIC)) {
							if (DateUtil.isCellDateFormatted(cell)) {
								cellValue = cell.getDateCellValue());
								values.add(cellValue);
							} else {
								cellValue = cell.getNumericCellValue());
								values.add(cellValue);
							}
						} else if (cellType.equals(CellType.BOOLEAN)) {
							cellValue = cell.getBooleanCellValue();
							values.add(cellValue);
						} else if (cellType.equals(CellType.FORMULA)) {
							cellValue = cell.getCellFormula();
							values.add(cellValue);
						} else if (cellType.equals(CellType.BLANK)) {
							cellValue = "";
							values.add(cellValue);
						}
					}
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return values;
	}
}
