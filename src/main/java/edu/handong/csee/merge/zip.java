package edu.handong.csee.merge;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class zip {

	public ArrayList<String> unzipExample(String Input_path) {

		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		ZipInputStream zipInputStream = null;
		
		try {
			fileInputStream = new FileInputStream(Input_path);
			zipInputStream = new ZipInputStream(fileInputStream);
			ZipEntry zipEntry = null;

			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				fileOutputStream = new FileOutputStream(zipEntry.getName());

				int length = 0;
				while ((length = zipInputStream.read()) != -1) {
					fileOutputStream.write(length);
				}

				zipInputStream.closeEntry();
				fileOutputStream.flush();
				fileOutputStream.close();
			}
			zipInputStream.close();
		} catch (IOException e) {
			// Exception Handling
		} finally {
			try {
				zipInputStream.closeEntry();
				fileOutputStream.flush();
				fileOutputStream.close();
				zipInputStream.close();
			} catch (IOException e) {
				// Exception Handling
			}
		}
	}
}
