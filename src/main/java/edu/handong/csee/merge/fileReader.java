package edu.handong.csee.merge;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.LinkedList;

public class fileReader { 
	
	public static void readFiles(String Input_path) {
		
		ArrayList<String> strList;
		ArrayList<String> 
		//String strTemp; 
		Queue<String> ql = new LinkedList<String>();
		
	try { 
        FileInputStream file = new FileInputStream(new File(Input_path)); 

        // Create Workbook instance holding reference to .xlsx file 
        HSSFWorkbook workbook = new HSSFWorkbook(file); 

        // Get first/desired sheet from the workbook 
        HSSFSheet sheet = workbook.getSheetAt(0); 

        // Iterate through each rows one by one 
        Iterator<Row> rowIterator = sheet.iterator(); 
        while (rowIterator.hasNext()) { 
            Row row = rowIterator.next(); 
            // For each row, iterate through all the columns 
            Iterator<Cell> cellIterator = row.cellIterator(); 

            while (cellIterator.hasNext()) { 
                Cell cell = cellIterator.next(); 
                
                // Check the cell type and format accordingly 
                switch (cell.getCellType()) { 
	                case NUMERIC: 
	                    System.out.print(cell.getNumericCellValue() + "t"); 
	                    break; 
	                case STRING: 
	                	strList.add(cell.getStringCellValue()); 
	                    break;
					default:
						break; 
                } 
            } 
        } 
        file.close(); 
    } 
    catch (Exception e) { 
        e.printStackTrace(); 
    } 
	
	}
} 
	
	
	
	
	/*
	 * ArrayList<String> strList; String strTemp; String lock = "test" ;
	 * Queue<String> ql = new LinkedList<String>();
	 * 
	 * 
	 * 
	 * class TextFileReader extends Thread { public void run() { try {
	 * 
	 * BufferedReader br = new BufferedReader (new FileReader("test.txt"));
	 * 
	 * while(true) {
	 *  strTemp = br.readLine(); 
	 *  strList.add(strTemp); 
	 *  if (strTemp == null) { 
	 *  br.close(); ql.offer("eof"); break; } synchronized(lock) {
	 * ql.offer(strTemp); lock.notifyAll(); // this is to prevent out of memory
	 * error if (ql.size() > 1000) { try { lock.wait(); } catch(InterruptedException
	 * ex) { } } } }
	 * 
	 * }//outer while end catch(IOException ex) { } catch(OutOfMemoryError eo) {
	 * System.out.println("queue size " + ql.size()); }//outer try end }//run method
	 * end }//TextReader class end
	 */
	
}
