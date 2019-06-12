package edu.handong.csee.merge;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import edu.handong.csee.merge.queue;

public class ExcelReader extends Thread{

	public queue getData(InputStream is) {
		//ArrayList<String> values = new ArrayList<String>();
		
		queue q = new queue();

		try (InputStream inp = is) {
			
			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);

			for (Row row : sheet) {
				
				if((row.getLastCellNum() == 0)) {
					throw new MyException();
				}
				
				if (row.getRowNum() == 0) {
					String toAdd = Integer.toString(row.getLastCellNum());
					q.enqueue(toAdd);
				}
				
				for (Cell cell : row) {
					if (cell != null) {
						String cellValue = null;
						CellType cellType = cell.getCellType();// 
						
						if (cellType.equals(CellType.STRING)) {
							cellValue = cell.getStringCellValue();
							//System.out.println(cellValue);
							q.enqueue(cellValue);
						} else if(cellType.equals(CellType.NUMERIC)) {
							cellValue = new String(Double.toString(cell.getNumericCellValue()));
							//System.out.println(cellValue);
							q.enqueue(cellValue);
						}else if(cellType.equals(CellType.BLANK)){
						q.enqueue("(blank)");
					}
					}else {
						
					}
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//MyException ex = new MyException();
			//e.printStackTrace();
		}catch(MyException e) {
			System.out.println(e.getMessage());
		}

		return q;
	}
}
