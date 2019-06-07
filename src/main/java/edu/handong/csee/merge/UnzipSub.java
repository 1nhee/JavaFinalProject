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

	public static ArrayList<String> subDecompress(String Input_path) throws Throwable {

		byte[] buffer = new byte[1024];

		ArrayList<String> fileNames = new ArrayList<String>();

		try {

			// create output directory is not exists
			// File folder = new File(Output_path);

			ZipFile zipFile = new ZipFile(Input_path);
			Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries();

			while (entries.hasMoreElements()) {
				ZipArchiveEntry entry = entries.nextElement();
				InputStream stream = zipFile.getInputStream(entry);

				String toPut = entry.getName();
				fileNames.add(toPut);

				ExcelReader myReader = new ExcelReader();

				for (String value : myReader.getData(stream)) {
					System.out.println(value);
					fileNames.add(value);
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fileNames;

	}

}