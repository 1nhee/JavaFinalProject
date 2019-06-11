package edu.handong.csee.merge;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

import edu.handong.csee.merge.ExcelReader;
import edu.handong.csee.merge.MyException;
import edu.handong.csee.merge.queue;

public class ZipReader extends Thread{

	public Queue<String> readFileInZip(String path) {
		
		ZipFile zipFile;
		//ArrayList<String> fileContents = new ArrayList<String>();
		Queue<String> queue = new LinkedList<String>();
		
		try {
			zipFile = new ZipFile(path);
			Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries();

		    while(entries.hasMoreElements()){
		    	ZipArchiveEntry entry = entries.nextElement();
		    	//System.out.println(entry.getName());
		    	
		    	if(!entry.isDirectory()) {
		    		InputStream stream = zipFile.getInputStream(entry);
		    	
			        Thread myReader = new ExcelReader();

			        myReader.start();
			        
			        int num = 0;
			        
			        queue q = new queue();
			        q = ((ExcelReader) myReader).getData(stream);
			        
			        while (q.hasItems()) {
			        	String value = q.dequeue();
			        	
			        	if(!value.equals(null)) {
			        		
			        		if(value.equals("4")) {
			        			num++;
			        			queue.offer("5");
			        		}else {
			        			if(num == 1 && value.equals("����(�ݵ�� ��๮ ��Ŀ� �Է��� ����� ���ƾ� ��.)")) {
			        				queue.offer("Header");
				        			num = 0;
				        		}
			        			queue.offer(value);
			        		}
			        	}else {
			        		queue.offer("(blank)");
			        	}
			        }
		    	}
		    }
		    zipFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		//MyException ex = new MyException();
			
		}
		return queue;
	}

}
