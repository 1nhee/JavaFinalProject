package edu.handong.csee.merge;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

import edu.handong.csee.merge.ExcelReader;
import edu.handong.csee.merge.MyException;

public class ZipReader {

	public ArrayList<Object> readFileInZip(String path) {
		
		ZipFile zipFile;
		ArrayList<Object> fileContents = new ArrayList<Object>();
		
		try {
			zipFile = new ZipFile(path);
			Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries();

		    while(entries.hasMoreElements()){
		    	ZipArchiveEntry entry = entries.nextElement();
		    	//System.out.println(entry.getName());
		    	
		    	if(!entry.isDirectory()) {
		    		InputStream stream = zipFile.getInputStream(entry);
		    		
			        ExcelReader myReader = new ExcelReader();
			        
			        int num = 0;
			        
			        for(Object value:myReader.getData(stream)) {
			        	
			        	if(!value.equals(null)) {
			        		
			        		if(value.equals("4")) {
			        			num++;
			        			fileContents.add("5");
			        		}else {
			        			if(num == 1 && value.equals("제목(반드시 요약문 양식에 입력한 제목과 같아야 함.)")) {
				        			fileContents.add("Header");
				        			num = 0;
				        		}
			        			fileContents.add(value);
			        		}
			        	}
			        }
		    	}
		    }
		    zipFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			MyException ex = new MyException(); 
		}
		return fileContents;
	}

}
