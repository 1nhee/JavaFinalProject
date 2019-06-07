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
				
				// entry�� ������ ���� ����
				if (entry.isDirectory()) {
					//file.mkdirs();
				} else {
					// �����̸� ���� �����
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
 * ���� ����� �޼ҵ�
 * 
 * @param file ����
 * @param zis  Zip��Ʈ��
 */
private static void createFile(File file, InputStream stream) throws Throwable {
	// ���丮 Ȯ��
	File parentDir = new File(file.getParent());
	// ���丮�� ������ ��������
	if (!parentDir.exists()) {
		parentDir.mkdirs();
	}
	// ���� ��Ʈ�� ����
	try (FileOutputStream fos = new FileOutputStream(file)) {
		byte[] buffer = new byte[256];
		int size = 0;
		// Zip��Ʈ�����κ��� byte�̾Ƴ���
		while ((size = stream.read(buffer)) > 0) {
			// byte�� ���� �����
			fos.write(buffer, 0, size);
		}
	} catch (Throwable e) {
		throw e;
	}
	}
}
