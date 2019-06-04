package edu.handong.csee.merge;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.RandomAccessFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import edu.handong.csee.merge.utils;

public class merge {

	private String Input_path;
	private String Output_path;
	boolean Help;

	public static void main(String[] args) {

		merge myRunner = new merge();
		myRunner.run(args);

	}

	private void run(String[] args) {
		
		Options options = createOptions();
		
		if(parseOptions(options, args)){
			if (Help){
				printHelp(options);
				return;
			}
		}
		
		ArrayList<String> fileString = readZipFile(this.Input_path);
		
		utils.(fileString, this.Output_path);
	}

	private ArrayList<String> readZipFile(String Input_path){
		 
		List<String> fileString = new ArrayList<String>();
	    StringBuffer sbf = new StringBuffer();

	    File file = new File(Input_path);
	    InputStream input;
	        
	    try {

	          input = new FileInputStream(file);
	          ZipInputStream zip = new ZipInputStream(input);
	          ZipEntry entry = zip.getNextEntry();

	          BodyContentHandler textHandler = new BodyContentHandler();
	          Metadata metadata = new Metadata();

	          Parser parser = new AutoDetectParser();

	          while (entry!= null){

	                if(entry.getName().endsWith(".txt") || 
	                           entry.getName().endsWith(".pdf")||
	                           entry.getName().endsWith(".docx")){
	                     parser.parse(input, textHandler, metadata, new ParseContext());
	                     fileString.add(textHandler.toString());
	                }
	           }
	           zip.close();
	           input.close();

	           return (ArrayList<String>) fileString;
	           
	        } catch (FileNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	}
	
	// CLI
		private boolean parseOptions(Options options, String[] args) {
			CommandLineParser parser = new DefaultParser();

			try {

				CommandLine cmd = parser.parse(options, args);

				Input_path = cmd.getOptionValue("i");
				//System.out.println("args is "+args);
				//System.out.println("Input is "+Input_path);
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

			// add options by using OptionBuilder
			options.addOption(Option.builder("i").longOpt("input").desc("Set an input file path").hasArg()
					.argName("Output_path").required().build());

			options.addOption(Option.builder("o").longOpt("output").desc("Set an output file path").hasArg()
					.argName("Output_path").required().build());

			// add options by using OptionBuilder
			options.addOption(Option.builder("h").longOpt("help").desc("Show a Help page").argName("Help").build());

			return options;
		}

		private void printHelp(Options HGUCourseCounter) {
			// automatically generate the help statement
			HelpFormatter formatter = new HelpFormatter();
			String header = "Java Final Project";
			String footer = ""; // Leave this empty.
			formatter.printHelp("CLIExample", header, options, footer, true);
		}

}
