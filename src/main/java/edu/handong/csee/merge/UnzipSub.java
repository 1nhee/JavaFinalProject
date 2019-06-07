package edu.handong.csee.merge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import edu.handong.csee.merge.ExcelReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

public class UnzipSub {
	
	public static ArrayList<String> subDecompress(String Input_path, String Output_path) throws Throwable {
		
		byte[] buffer = new byte[1024];
		
		ArrayList<String> fileNames = new ArrayList<String>(); 
	    	
	     try{
	    		
	    	//create output directory is not exists
	    	File folder = new File(Output_path);
			
	    	ZipFile zipFile = new ZipFile(Input_path);
			Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries();

		    while(entries.hasMoreElements()){
		    	ZipArchiveEntry entry = entries.nextElement();
		        InputStream stream = zipFile.getInputStream(entry);
		        
		        String toPut = entry.getName();
		        fileNames.add(toPut);
		        System.out.println("subzip "+toPut);
				String a = Output_path+".zip\\"+toPut;
				File file = new File(a);
				
				// entry가 폴더면 폴더 생성
				if (entry.isDirectory()) {
					//file.mkdirs();
				} else {
					// 파일이면 파일 만들기
					createFile(file, stream);
				}
		        
				/*
				 * ExcelReader myReader = new ExcelReader();
				 * 
				 * for(String value:myReader.getData(stream)) { //System.out.println(value);
				 * fileNames.add(value); }
				 */
		    }
		    }catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     
	     return fileNames;

}

/**
 * 파일 만들기 메소드
 * 
 * @param file 파일
 * @param zis  Zip스트림
 */
private static void createFile(File file, InputStream stream) throws Throwable {
	// 디렉토리 확인
	File parentDir = new File(file.getParent());
	// 디렉토리가 없으면 생성하자
	if (!parentDir.exists()) {
		parentDir.mkdirs();
	}
	// 파일 스트림 선언
	try (FileOutputStream fos = new FileOutputStream(file)) {
		byte[] buffer = new byte[256];
		int size = 0;
		// Zip스트림으로부터 byte뽑아내기
		while ((size = stream.read(buffer)) > 0) {
			// byte로 파일 만들기
			fos.write(buffer, 0, size);
		}
	} catch (Throwable e) {
		throw e;
	}
	}
}
