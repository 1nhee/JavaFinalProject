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
import edu.handong.csee.merge.utils;

public class start {

	String Input_path;
	String Output_path;
	boolean Help;

	public static void main(String[] args) throws Throwable {

		ArrayList<String> path = new ArrayList<String>();

		UnzipSub unzip = new UnzipSub();
		start toStart = new start();
		// toStart.run(args);

		toStart.Input_path = "C:\\Users\\Inhee Kwak\\git\\JavaFinalProject\\data";
		toStart.Output_path = "C:\\Users\\Inhee Kwak\\git\\JavaFinalProject\\data";

		ArrayList<String> toGet = new ArrayList<String>();
		ArrayList<String> allFileContents = new ArrayList<String>();

		for (int i = 1; i < 6; i++) {
			String toCheck = String.format("%04d", i);
			System.out.println(toStart.Input_path + "\\" + toCheck + ".zip");
			toGet = unzip.subDecompress(toStart.Input_path + "\\" + toCheck + ".zip");
			
			for(String toAdd : toGet) {
				allFileContents.add(toAdd);
			}
		}
		
		utils write = new utils();
		write.writeAFile(lines, targetFileName);

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