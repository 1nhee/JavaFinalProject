package edu.handong.csee.merge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class merge {

	private Input_path;
	private Output_path;
	boolean help;
	
	Options options = createOptions();
	
	public static void main(String[] args) throws Exception{
		
		if(parseOptions(options, args)){
			if (help){
				printHelp(options);
				return;
			}
			
		  //Unzip
		  ZipInputStream zipinputstream = new ZipInputStream(new FileInputStream(Input_path));
		   ZipEntry zipentry = zipinputstream.getNextEntry();
		    while (zipentry != null) {
		      String entryName = zipentry.getName();
		      File newFile = new File(entryName);
		      String directory = newFile.getParent();
		      if (directory == null) {
		        if (newFile.isDirectory())
		          break;
		      }
		      RandomAccessFile  rf = new RandomAccessFile(entryName, "r");
		      String line;
		      if ((line = rf.readLine()) != null) {
		        System.out.println(line);
		      }
		      rf.close();
		      zipinputstream.closeEntry();
		      zipentry = zipinputstream.getNextEntry();
		    }
		    zipinputstream.close();
		  
		    
		  //merge files
		  try {
	        mergeExcelFiles(new File(Output_path));
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }//end of catch
		  }// end of main

	public static void mergeExcelFiles(File file) throws IOException {
		HSSFWorkbook book = new HSSFWorkbook();
		System.out.println(file.getName());
		String directoryName = Input_path;
		File directory = new File(directoryName);
		
		// get all the files from a directory
		File[] fList = directory.listFiles();
		
		for (File file1 : fList) {
			if (file1.isFile()) {
				String ParticularFile = file1.getName();
				FileInputStream fin = new FileInputStream(new File(directoryName + "\\" + ParticularFile));
				HSSFWorkbook b = new HSSFWorkbook(fin);
				for (int i = 0; i < b.getNumberOfSheets(); i++) {
					HSSFSheet sheet = book.createSheet(b.getSheetName(i));
					copySheets(book, sheet, b.getSheetAt(i));
					System.out.println("Copying..");
				}
			}
			try {
				writeFile(book, file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected static void writeFile(HSSFWorkbook book, File file) throws Exception {
		FileOutputStream out = new FileOutputStream(file);
		book.write(out);
		out.close();
	}

	private static void copySheets(HSSFWorkbook newWorkbook, HSSFSheet newSheet, HSSFSheet sheet) {
		copySheets(newWorkbook, newSheet, sheet, true);
	}

	private static void copySheets(HSSFWorkbook newWorkbook, HSSFSheet newSheet, HSSFSheet sheet, boolean copyStyle) {
		int newRownumber = newSheet.getLastRowNum();
		int maxColumnNum = 0;
		Map<Integer, HSSFCellStyle> styleMap = (copyStyle) ? new HashMap<Integer, HSSFCellStyle>() : null;

		for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
			HSSFRow srcRow = sheet.getRow(i);
			HSSFRow destRow = newSheet.createRow(i + newRownumber);
			if (srcRow != null) {
				copyRow(newWorkbook, sheet, newSheet, srcRow, destRow, styleMap);
				if (srcRow.getLastCellNum() > maxColumnNum) {
					maxColumnNum = srcRow.getLastCellNum();
				}
			}
		}
		for (int i = 0; i <= maxColumnNum; i++) {
			newSheet.setColumnWidth(i, sheet.getColumnWidth(i));
		}
	}

	public static void copyRow(HSSFWorkbook newWorkbook, HSSFSheet srcSheet, HSSFSheet destSheet, HSSFRow srcRow,
			HSSFRow destRow, Map<Integer, HSSFCellStyle> styleMap) {
		destRow.setHeight(srcRow.getHeight());
		for (int j = srcRow.getFirstCellNum(); j <= srcRow.getLastCellNum(); j++) {
			HSSFCell oldCell = srcRow.getCell(j);
			HSSFCell newCell = destRow.getCell(j);
			if (oldCell != null) {
				if (newCell == null) {
					newCell = destRow.createCell(j);
				}
				copyCell(newWorkbook, oldCell, newCell, styleMap);
			}
		}
	}

	public static void copyCell(HSSFWorkbook newWorkbook, HSSFCell oldCell, HSSFCell newCell,
			Map<Integer, HSSFCellStyle> styleMap) {
		if (styleMap != null) {
			int stHashCode = oldCell.getCellStyle().hashCode();
			HSSFCellStyle newCellStyle = styleMap.get(stHashCode);
			if (newCellStyle == null) {
				newCellStyle = newWorkbook.createCellStyle();
				newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
				styleMap.put(stHashCode, newCellStyle);
			}
			newCell.setCellStyle(newCellStyle);
		}
		switch (oldCell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			newCell.setCellValue(oldCell.getRichStringCellValue());
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			newCell.setCellValue(oldCell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			newCell.setCellType(HSSFCell.CELL_TYPE_BLANK);
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			newCell.setCellValue(oldCell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			newCell.setCellErrorValue(oldCell.getErrorCellValue());
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			newCell.setCellFormula(oldCell.getCellFormula());
			break;
		default:
			break;
		}
	}
	
	// CLI
		public boolean parseOptions(Options options, String[] args) {
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
		public Options createOptions() {
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


}
