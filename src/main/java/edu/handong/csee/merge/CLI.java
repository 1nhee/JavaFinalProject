package edu.handong.csee.merge;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class CLI {
	
	String Input_path;
	String Output_path;
	boolean Help;
	
	public CLI (String[] args) {
		
	}
	
	void run(String[] args) {
		Options options = createOptions();

		if (parseOptions(options, args)) {
			if (Help) {
				printHelp(options);
				return;
			}
		}

		// path is required (necessary) data so no need to have a branch.
		//System.out.println("This is result of 'i' option");
		//System.out.println("You put this path: " + Input_path +"\n");

		// TODO show the number of files in the path

		File dirFile = new File(Input_path);
		File[] fileList = dirFile.listFiles();
		ArrayList<String> fileNames = new ArrayList<String>();
		ArrayList<String> filePaths = new ArrayList<String>();
		
		for(File toGet: fileList) {
		  if(toGet.isFile()) {
		    String tempPath = toGet.getParent();
		    filePaths.add(tempPath);
		    
		    String tempFileName = toGet.getName();
		    fileNames.add(tempFileName);
		  }
		}
		
	}

	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			Input_path = cmd.getOptionValue("i");
			Output_path = cmd.getOptionValue("o");
			Help = cmd.hasOption("h");

		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}

	// Definition Stage
	private Options createOptions() {
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

	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "ls CLI";
		String footer = "";
		formatter.printHelp("CLIExample", header, options, footer, true);
	}

}
