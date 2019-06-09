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

import edu.handong.csee.merge.ZipReader;
import edu.handong.csee.merge.utils;

public class start {

	static String Input_path;
	static String Output_path;
	static boolean Help;

	public static void main(String[] args) throws Throwable {

		ArrayList<String> path = new ArrayList<String>();

		// UnzipSub unzip = new UnzipSub();
		ZipReader zipReader = new ZipReader();
		start toStart = new start();
		// toStart.run(args);

		Input_path = "C:\\Users\\Inhee Kwak\\git\\JavaFinalProject\\data";
		Output_path = "C:\\Users\\Inhee Kwak\\git\\JavaFinalProject\\data";

		ArrayList<Object> toGet = new ArrayList<Object>();
		ArrayList<Object> allFileContents = new ArrayList<Object>();
		ArrayList<Integer> allHeaderNum = new ArrayList<Integer>();

		for (int i = 1; i < 6; i++) {
			String toCheck = String.format("%04d", i);
			// System.out.println(toStart.Input_path + "\\" + toCheck + ".zip");
			ZipReader zipR = new ZipReader();
			toGet = zipR.readFileInZip(toStart.Input_path + "\\" + toCheck + ".zip");
			// toGet = unzip.subDecompress(toStart.Input_path + "\\" + toCheck + ".zip");

			for (Object toAdd : toGet) {

				allFileContents.add(toAdd);
				System.out.println(toAdd);
			}
		}

		ArrayList<Object> allHeader = new ArrayList<Object>();
		ArrayList<Object> allTableHeader = new ArrayList<Object>();

		ArrayList<Object> allTable = new ArrayList<Object>();
		ArrayList<Object> allStr = new ArrayList<Object>();

		ArrayList<Integer> table5Header = new ArrayList<Integer>();
		ArrayList<Integer> table7Header = new ArrayList<Integer>();
		ArrayList<Integer> table55Header = new ArrayList<Integer>();

		ArrayList<Integer> tableNum = new ArrayList<Integer>();
		ArrayList<Integer> strNum = new ArrayList<Integer>();

		for (int i = 0; i < allFileContents.size(); i++) {
			if (allFileContents.get(i).equals("5")) {
				table5Header.add(i + 1);
			} else if (allFileContents.get(i).equals("7")) {
				table7Header.add(i + 1);
				strNum.add(i + 10);
			} else if (allFileContents.get(i).equals("Header")) {
				table55Header.add(i + 1);
				tableNum.add(i + 6);
			}
		}

		for (int i = table7Header.get(0); i < table7Header.get(0) + 7; i++) {
			allHeader.add(allFileContents.get(i));
		}

		for (int i = table5Header.get(0); i < table5Header.get(0) + 1; i++) {
			allTableHeader.add(allFileContents.get(i));
		}

		for (int i = table55Header.get(0); i < table55Header.get(0) + 5; i++) {
			allTableHeader.add(allFileContents.get(i));
		}

		int studentTbl = 1;
		for (int count : table55Header) {

			String toCheck = String.format("%04d", studentTbl++);
			allTable.add("Student " + toCheck);

			if (count == 716) {
				for (int i = count + 5; i < 807; i++) {
					allTable.add(allFileContents.get(i));
				}
			} else {
				for (int i = count + 5;; i++) {
					allTable.add(allFileContents.get(i));

					if (allFileContents.get(i).equals("7")
							|| (allFileContents.get(i).equals("89.0") && allFileContents.get(i + 1).equals("5"))) {
						break;
					}
				}
			}
		}

		int studentStr = 1;
		for (int count : table7Header) {

			String toCheck = String.format("%04d", studentStr++);
			allStr.add("Student " + toCheck);

			for (int i = count + 8;; i++) {
				allStr.add(allFileContents.get(i));
				// System.out.println(i + " " + allFileContents.get(i));

				if (allFileContents.get(i + 1).equals("5") || (allFileContents.get(i).equals("³²ºÏ±³·ùÇù·ÂÁö¿øÇùÈ¸"))) {
					break;
				}
			}
		}
		
		ArrayList<Object> finishStr = new ArrayList<Object>();
		ArrayList<Object> finishTable = new ArrayList<Object>();
		
		int rowStr = 0;
		String strHead = allHeader.get(0).toString()+ "," + allHeader.get(1).toString()+ "," + allHeader.get(2).toString()+ "," + allHeader.get(3).toString()+ "," + allHeader.get(4).toString()+ "," + allHeader.get(5).toString()+ "," + allHeader.get(6).toString();
		//finishStr.add(strHead);

		utils write = new utils();
		
		//write string file
		write.writeAFile(finishStr, Output_path+"\\string.xlsx", allHeader);
		
		//write table file
		//write.writeAFile(allTable, Output_path+"\\table.xlsx");
	

	}// end of main

	public void run(String[] args) {
		Options options = createOptions();

		ArrayList<String> path = new ArrayList<String>();

		if (parseOptions(options, args)) {
			if (Help) {
				printHelp(options);
				return;
			}
		}

		// path is required (necessary) data so no need to have a branch.
		System.out.println("This is result of 'i' option");
		System.out.println("You put this path: " + Input_path + "\n");
	}

	public boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			start.Input_path = cmd.getOptionValue("i");
			System.out.println(Input_path);
			start.Output_path = cmd.getOptionValue("o");
			start.Help = cmd.hasOption("h");

		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}

	// Definition Stage
	public Options createOptions() {
		Options options = new Options();

		// input
		options.addOption(
				Option.builder("i").longOpt("Input_path").desc("Set a path of a directory or a file to display")
						.hasArg().argName("Path name to display").required().build());

		// input
		options.addOption(
				Option.builder("o").longOpt("Output_path").desc("Set a path of a directory or a file to display")
						.hasArg().argName("Path name to display").required().build());

		// add options by using OptionBuilder
		options.addOption(Option.builder("h").longOpt("Help").desc("Help").build());

		return options;
	}

	public void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "ls CLI";
		String footer = "";
		formatter.printHelp("CLIExample", header, options, footer, true);
	}

}// end of class