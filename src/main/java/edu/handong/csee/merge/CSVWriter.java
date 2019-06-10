package edu.handong.csee.merge;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class CSVWriter {

	public static void writeAFile (ArrayList<Object> finishStr, String targetFileName, ArrayList<Object> allHeader) throws IOException {
        try {

            BufferedWriter writer = Files.newBufferedWriter(Paths.get(targetFileName));
        		
        	String head = new String();
        	for(Object header : allHeader) {
        		System.out.println("object "+header);
        		head.concat(header.toString());
        	}
        	System.out.println("head "+head);

            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("ID", "Name", "Designation", "Company")); 
            
            int count = 0;
            String input = new String();
            for(Object in : finishStr) {
            	if(count == 6) {
            		input.concat(in.toString());
            		System.out.println("input "+input);
            		csvPrinter.printRecord(input);
            		count = 0;
            		input = "";
            	}else {
            		input.concat(in.toString()+",");
                	count++;
            	}
            }
            System.out.println("File is written in " + targetFileName);
            csvPrinter.flush();            
        }finally{
        	
        }
}
}