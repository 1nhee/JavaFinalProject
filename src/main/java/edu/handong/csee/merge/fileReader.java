package edu.handong.csee.merge;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class fileReader { 
	
	public Class fileReader{
		
	}
	
	
	ArrayList<String> strTemp;
    String lock = "test" ;
    String strOutput;
    String strInput;
    Queue<String> ql = new LinkedList<String>();
    
    class TextFileReader extends Thread
    {
        public void run()
        {
            //System.out.println(new Date());
            
            try
            {
                 
                BufferedReader br = new BufferedReader
                      (new FileReader("test.txt"));
                 
                while(true)
                {
                    strTemp.add(br.readLine());
                    if (strTemp == null)
                    {
                        br.close();
                        ql.offer("eof");
                        break;
                    }
                    synchronized(lock)
                    {
                        ql.offer(strTemp);
                        lock.notifyAll();
                        // this is to prevent out of memory error
                        if (ql.size() > 1000)
                        {
                            try
                            {
                                lock.wait();
                            }
                            catch(InterruptedException ex)
                            {
                            }
                        }
                    }
                }
                 
            }//outer while end
            catch(IOException ex)
            {
            }
            catch(OutOfMemoryError eo)
            {
                System.out.println("queue size " + ql.size());
            }//outer try end
        }//run method end
    }//TextReader class end

	/*
	 * class MultiThread implements Runnable{ private static BufferedReader br =
	 * null; private List<String> list = new List<String>;
	 * 
	 * try { br = new BufferedReader(new FileReader("F://ThreadDemo.txt"),10);
	 * }catch (FileNotFoundException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * public void run() { String line = null; int count = 0;
	 * 
	 * while(true) { //System.out.println(Thread.currentThread().getName());
	 * this.list = new ArrayList<String>(); synchronized(br) { try {
	 * 
	 * FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
	 * Workbook workbook = new XSSFWorkbook(excelFile); Sheet datatypeSheet =
	 * workbook.getSheetAt(0); Iterator<Row> iterator = datatypeSheet.iterator();
	 * 
	 * while (iterator.hasNext()) {
	 * 
	 * Row currentRow = iterator.next(); Iterator<Cell> cellIterator =
	 * currentRow.iterator();
	 * 
	 * while (cellIterator.hasNext()) {
	 * 
	 * Cell currentCell = cellIterator.next(); //getCellTypeEnum shown as deprecated
	 * for version 3.15 //getCellTypeEnum ill be renamed to getCellType starting
	 * from version 4.0 if (currentCell.getCellTypeEnum() == CellType.STRING) {
	 * System.out.print(currentCell.getStringCellValue() + "--"); } else if
	 * (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
	 * System.out.print(currentCell.getNumericCellValue() + "--"); }
	 * 
	 * } System.out.println();
	 * 
	 * } } catch (FileNotFoundException e) { e.printStackTrace(); } catch
	 * (IOException e) { e.printStackTrace(); }
	 * 
	 * } } try { Thread.sleep(1); } catch (InterruptedException e) {
	 * e.printStackTrace(); } if(line == null) break; }
	 * 
	 * 
	 * }
	 */
	
}
