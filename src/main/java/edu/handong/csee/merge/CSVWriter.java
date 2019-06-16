package edu.handong.csee.merge;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class CSVWriter {

	public static void writeAFile(ArrayList<String> finishStr, String targetFileName, ArrayList<String> allHeader)
			throws IOException {
		try {

			FileOutputStream outputStream = new FileOutputStream(targetFileName);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
			writer.write("\uFEFF");

			// BufferedWriter writer = Files.newBufferedWriter(Paths.get(targetFileName));

			String strHead = new String();
			if (allHeader.size() == 7) {
				// System.out.println("head: "+ strHead);

				CSVPrinter csvPrinter = new CSVPrinter(writer,
						CSVFormat.DEFAULT.withHeader("Student ID", allHeader.get(0), allHeader.get(1), allHeader.get(2),
								allHeader.get(3), allHeader.get(4), allHeader.get(5), allHeader.get(6)));

				int count = 1;
				String input = new String();

				for (int i = 0; i < finishStr.size(); i++) {

					// System.out.println(i + ": " + finishStr.get(i));

					if (finishStr.get(i).equals("0001") || finishStr.get(i).equals("0002")
							|| finishStr.get(i).equals("0003") || finishStr.get(i).equals("0004")
							|| finishStr.get(i).equals("0005")) {
						if (finishStr.get(i).equals("0002") || finishStr.get(i).equals("0003")
								|| finishStr.get(i).equals("0004") || finishStr.get(i).equals("0005")) {
							count++;
						}

						String StudentNum = String.format("%04d", count);
						finishStr.remove(i);
						csvPrinter.printRecord(Arrays.asList(StudentNum, finishStr.get(i), finishStr.get(i + 1),
								finishStr.get(i + 2), finishStr.get(i + 3), finishStr.get(i + 4), finishStr.get(i + 5),
								finishStr.get(i + 6)));
					} else if ((i < 150 && i % 7 == 0)) {
						String StudentNum = String.format("%04d", count);
						csvPrinter.printRecord(Arrays.asList(StudentNum, finishStr.get(i), finishStr.get(i + 1),
								finishStr.get(i + 2), finishStr.get(i + 3), finishStr.get(i + 4), finishStr.get(i + 5),
								finishStr.get(i + 6)));

					} else if (i == 154) {
						// System.out.println
						String StudentNum = String.format("%04d", count);
						csvPrinter.printRecord(Arrays.asList(StudentNum, finishStr.get(i), finishStr.get(i + 1)));
					}
				}

				System.out.println("Your String file is written in " + targetFileName);
				csvPrinter.flush();

			} else if (allHeader.size() == 6) {

				// System.out.println("head: "+ strHead);

				CSVPrinter csvPrinter = new CSVPrinter(writer,
						CSVFormat.DEFAULT.withHeader(allHeader.get(0).substring(0, allHeader.get(0).indexOf("2.") - 1),
								allHeader.get(0).substring(allHeader.get(0).indexOf("2."))));

				csvPrinter.printRecord(Arrays.asList("Student ID", allHeader.get(4), allHeader.get(5)));

				int count = 1;
				String input = new String();

				// System.out.println(finishStr.size());
				for (int i = 0; i < finishStr.size(); i++) {

					if (finishStr.get(i).equals("0001") || finishStr.get(i).equals("0002")
							|| finishStr.get(i).equals("0003") || finishStr.get(i).equals("0004")
							|| finishStr.get(i).equals("0005")) {
						if (finishStr.get(i).equals("0002") || finishStr.get(i).equals("0003")
								|| finishStr.get(i).equals("0004") || finishStr.get(i).equals("0005")) {
							count++;
						}

						String StudentNum = String.format("%04d", count);

						finishStr.remove(i);
						csvPrinter.printRecord(
								Arrays.asList(StudentNum, finishStr.get(i), finishStr.get(i + 1), finishStr.get(i + 2),
										finishStr.get(i + 3), finishStr.get(i + 4), finishStr.get(i + 5)));
					} else if (i % 6 == 0) {
						if (i == 498) {
							break;
						}

						String StudentNum = String.format("%04d", count);
						// System.out.println(i + ":" + finishStr.get(i) + finishStr.get(i + 1)+
						// finishStr.get(i + 2)+finishStr.get(i + 3)+ finishStr.get(i + 4) +
						// finishStr.get(i + 5));
						csvPrinter.printRecord(
								Arrays.asList(StudentNum, finishStr.get(i), finishStr.get(i + 1), finishStr.get(i + 2),
										finishStr.get(i + 3), finishStr.get(i + 4), finishStr.get(i + 5)));
					}
				}

				System.out.println("Your Table file is written in " + targetFileName);
				csvPrinter.flush();
			}

		} finally {

		}
	}

}