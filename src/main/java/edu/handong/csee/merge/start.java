package edu.handong.csee.merge;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.zip.ZipInputStream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import edu.handong.csee.merge.UnzipFiles;

public class start {

	String Input_path;
	String Output_path;
	boolean Help;

	public static void main(String[] args) throws Throwable {

		start toStart = new start();
		UnzipFiles unzip = new UnzipFiles();
		UnzipSub unzipSub = new UnzipSub();

		ArrayList<String> toGet = new ArrayList<String>();
		ArrayList<String> allFileContents = new ArrayList<String>();

		toStart.Input_path = "C:\\Users\\Inhee Kwak\\git\\JavaFinalProject\\data.zip";
		toStart.Output_path = "C:\\Users\\Inhee Kwak\\git\\JavaFinalProject\\unzip";

		toGet = unzip.decompress(toStart.Input_path, toStart.Output_path);

		int i = 0;

		for (String toCheck : toGet) {
			String toOut = String.format("%04d", i + 1);
			i++;

			File toMake = new File(toOut);

			String path = "C:\\Users\\Inhee Kwak\\git\\JavaFinalProject\\unzip" + "\\" + toOut; // ���� ���
			File Folder = new File(path);

			// �ش� ���丮�� ������� ���丮�� �����մϴ�.
			if (!Folder.exists()) {
				try {
					Folder.mkdir(); // ���� �����մϴ�.
				} catch (Exception e) {
					e.getStackTrace();
				}
			}
			
			ZipReader subRead = new ZipReader();

			ArrayList<String> toAdd = new ArrayList<String>();
			toAdd = unzipSub.subDecompress(path+".zip", path);
			System.out.println("start" + path+".zip");
			
			for(String toAddSub : toAdd) {
				allFileContents.add(toAddSub);
				//System.out.println(toAddSub);
			}
			
			
			/*
			 * for (String toCheckTo : unzip.decompress(path+".zip", path)) {
			 * allFileNames.add(toCheckTo); System.out.println(toCheckTo); }
			 */
		}
	}

	/*
	 * LsRuuner myRunner = new LsRuuner(); myRunner.run(args);
	 * 
	 * File dir = new File();
	 * 
	 * File[] fileList = dir.listFiles();
	 * 
	 * try {
	 * 
	 * for (int i = 0; i < fileList.length; i++) {
	 * 
	 * File file = fileList[i];
	 * 
	 * if (file.isFile()) {
	 * 
	 * // ������ �ִٸ� ���� �̸� ���
	 * 
	 * System.out.println("\t ���� �̸� = " + file.getName());
	 * 
	 * } else if (file.isDirectory()) {
	 * 
	 * System.out.println("���丮 �̸� = " + file.getName());
	 * 
	 * // ������丮�� �����ϸ� ����� ������� �ٽ� Ž��
	 * 
	 * subDirList(file.getCanonicalPath().toString());
	 * 
	 * }
	 * 
	 * }
	 * 
	 * } catch (IOException e) {
	 * 
	 * }
	 * 
	 * }
	 * 
	 */

}
