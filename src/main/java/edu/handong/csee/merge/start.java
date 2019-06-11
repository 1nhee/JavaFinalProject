package edu.handong.csee.merge;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import edu.handong.csee.merge.ZipReader;
import edu.handong.csee.merge.CSVWriter;

public class start {

	static String Input_path;
	static String Output_path;
	static boolean Help;

	public static void main(String[] args) throws Throwable {
		
		Options options = createOptions();

		if (parseOptions(options, args)) {
			if (Help) {
				printHelp(options);
				return;
			}
		}

		
		// path is required (necessary) data so no need to have a branch.
		if(!Input_path.isEmpty()) {
			System.out.println("This is result of 'i' option");
			System.out.println("You put this Input path: " + Input_path + "\n");
		}
		
		if(!Output_path.isEmpty()) {
			System.out.println("This is result of 'o' option");
			System.out.println("You put this Output path: " + Output_path + "\n");
		}
		

		// UnzipSub unzip = new UnzipSub();
		ZipReader zipReader = new ZipReader();
		start toStart = new start();
		// toStart.run(args);

		//Input_path = "C:\\Users\\Inhee Kwak\\git\\JavaFinalProject\\data";
		//Output_path = "C:\\Users\\Inhee Kwak\\git\\JavaFinalProject\\data";

		Queue<String> toGet = new LinkedList<String>();
		ArrayList<String> allFileContents = new ArrayList<String>();
		ArrayList<Integer> allHeaderNum = new ArrayList<Integer>();

		for (int i = 1; i < 6; i++) {
		
			String toCheck = String.format("%04d", i);
			// System.out.println(toStart.Input_path + "\\" + toCheck + ".zip");
			ZipReader zipR = new ZipReader();
			toGet = zipR.readFileInZip(toStart.Input_path + "\\" + toCheck + ".zip");
			// toGet = unzip.subDecompress(toStart.Input_path + "\\" + toCheck + ".zip");

			for (String toAdd : toGet) {

				allFileContents.add(toAdd);
				//System.out.println(toAdd);
			}
		}

		ArrayList<String> allHeader = new ArrayList<String>();
		ArrayList<String> allTableHeader = new ArrayList<String>();

		ArrayList<String> allTable = new ArrayList<String>();
		ArrayList<String> allStr = new ArrayList<String>();

		ArrayList<Integer> table5Header = new ArrayList<Integer>();
		ArrayList<Integer> table7Header = new ArrayList<Integer>();

		ArrayList<Integer> tableNum = new ArrayList<Integer>();
		ArrayList<Integer> strNum = new ArrayList<Integer>();

		for (int i = 0; i < allFileContents.size(); i++) {
			if (allFileContents.get(i).equals("5")) {
				table5Header.add(i + 1);
			} else if (allFileContents.get(i).equals("7")) {
				table7Header.add(i + 1);
				strNum.add(i + 10);
			}
			/*
			 * else if (allFileContents.get(i).equals("Header")) { table55Header.add(i + 1);
			 * tableNum.add(i + 6); }
			 */
		}

		for (int i = table7Header.get(0); i < table7Header.get(0) + 7; i++) {
			allHeader.add(allFileContents.get(i));
		}

		for (int i = table5Header.get(0); i < table5Header.get(0) + 6; i++) {
			allTableHeader.add(allFileContents.get(i));
			//System.out.println(i + ": " + allFileContents.get(i));
		}

		int studentTbl = 1;
		for (int count : table5Header) {
			//System.out.println("count " + count);
			String toCheck = String.format("%04d", studentTbl++);
			allTable.add(toCheck);

				for (int i = count + 6;; i++) {
					allTable.add(allFileContents.get(i));
					//System.out.println(i + ": " + allFileContents.get(i));
					if(i == 801) {
						break;
					}
					
					if (allFileContents.get(i + 1).equals("7")) {
						break;
					}
				}
			//}
		}

		int studentStr = 1;
		for (int count : table7Header) {
			// System.out.println("count " + count);
			String toCheck = String.format("%04d", studentStr++);
			allStr.add(toCheck);

			for (int i = count + 7;; i++) {
				allStr.add(allFileContents.get(i));
				// System.out.println(i + " " + allFileContents.get(i));
				
				//if(allFileContents.get(i).equals("북한의 농업ㆍ식량 사정과 대응 방향") || allFileContents.get(i).equals("세계보건기구, 대북 보건의료 정책, WHO 중점 지원사업") || allFileContents.get(i).equals("세계보건기구, 대북 보건의료 정책, WHO 중점 지원사업")) {
					//allStr.add(" ");
				//}

				if (allFileContents.get(i + 1).equals("5") || (allFileContents.get(i).equals("���ϱ�������������ȸ"))) {
					break;
				}
			}
		}
		
		// write string file
		CSVWriter.writeAFile(allStr, Output_path + "\\String.csv", allHeader);
		//System.out.println(allTableHeader.size());
		CSVWriter.writeAFile(allTable, Output_path + "\\Table.csv", allTableHeader);


	}// end of main

	public static boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			start.Input_path = cmd.getOptionValue("i");
			//System.out.println("CLI Input: "+Input_path);
			start.Output_path = cmd.getOptionValue("o");
			//System.out.println("CLI Input: "+Output_path);
			start.Help = cmd.hasOption("h");

		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}

	// Definition Stage
	public static Options createOptions() {
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

	public static void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "ls CLI";
		String footer = "";
		formatter.printHelp("CLIExample", header, options, footer, true);
	}

}// end of class