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

			String path = "C:\\Users\\Inhee Kwak\\git\\JavaFinalProject\\unzip" + "\\" + toOut; // 폴더 경로
			File Folder = new File(path);

			// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
			if (!Folder.exists()) {
				try {
					Folder.mkdir(); // 폴더 생성합니다.
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
	 * // 파일이 있다면 파일 이름 출력
	 * 
	 * System.out.println("\t 파일 이름 = " + file.getName());
	 * 
	 * } else if (file.isDirectory()) {
	 * 
	 * System.out.println("디렉토리 이름 = " + file.getName());
	 * 
	 * // 서브디렉토리가 존재하면 재귀적 방법으로 다시 탐색
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
