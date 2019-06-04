package edu.handong.csee.merge;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class fileWriter {
	public static void main(String[] args) {
		File file=new File(Output_path);
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
        
		try {
			
			FileOutputStream out=new FileOutputStream(file, true);
			ConcurrentLinkedQueue<String> queue=new ConcurrentLinkedQueue<String>();
			
			/*
			 * for(int i=0;i<10;i++){ new Thread(new MyThread(queue,"Thread "
			 * +i+" ".Start)), (); // multi thread into the queue data }
			 * 
			 * new Thread(new DealFile(out,queue)).start();//The thread of monitoring,
			 * continuously from the queue read and write data to a file
			 */
			
			  int rowNum = 0;
		        System.out.println("Creating excel");

		        for (Object[] datatype : datatypes) {
		            Row row = sheet.createRow(rowNum++);
		            int colNum = 0;
		            for (Object field : datatype) {
		                Cell cell = row.createCell(colNum++);
		                if (field instanceof String) {
		                    cell.setCellValue((String) field);
		                } else if (field instanceof Integer) {
		                    cell.setCellValue((Integer) field);
		                }
		            }
		        }

		        try {
		            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
		            workbook.write(outputStream);
		            workbook.close();
		        } catch (FileNotFoundException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }

		        System.out.println("Done");
		    }
			
			try {
				Thread.sleep(3000);
			
				if(!Thread.currentThread().isAlive()){
					System.out.println("The thread has finished");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}

/**
 * Will be written to the file data into the queue
 * 
 * @author Administrator
 * 
 */
class MyThread implements Runnable {
	private ConcurrentLinkedQueue<String> queue;
	private String contents;

	public MyThread() {
	}

	public MyThread(ConcurrentLinkedQueue<String> queue, String contents) {
		this.queue = queue;
		this.contents = contents;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		queue.add(contents);
	}
}

/**
 * Write data to a file in the queue
 * 
 * @author Administrator
 *
 */
class DealFile implements Runnable {
	private FileOutputStream out;
	private ConcurrentLinkedQueue<String> queue;

	public DealFile() {
	}

	public DealFile(FileOutputStream out, ConcurrentLinkedQueue<String> queue) {
		this.out = out;
		this.queue = queue;
	}

	@Override
	public void run() {
		synchronized (queue) {
			while (true) {
				if (!queue.isEmpty()) {
					try {
						out.write(queue.poll().getBytes("UTF-8"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
