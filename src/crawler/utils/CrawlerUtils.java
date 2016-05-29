package crawler.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CrawlerUtils {

	public static String fetchData(String textToParse, String contentDiv, String startString, String endString) {
		String data = new String();
		String arr[] = textToParse.split(contentDiv);
		for (int i = 1; i < arr.length; i++) {
			String str = arr[i];
			int start = str.indexOf(startString);

			if (start != -1) {
				int end = str.substring(start).indexOf(endString);
				if (end != -1) {
					end = end + start;
					int end1 = (end >= str.length()) ? str.length() : (end);
					try {
					data = str.substring(Math.min(start + startString.length(), str.length()), end1);
					} catch(Exception e) {
						e.printStackTrace();
						continue;
					}
				}
			}
		}
		return data;
	}

	public static String fetchDataContinuous(String textToParse, String contentDiv, String startString, String endString) {
		String data = new String();
		String arr[] = textToParse.split(contentDiv);
		for (int i = 1; i < arr.length; i++) {
			String str = arr[i];
			int start = str.indexOf(startString);

			if (start != -1) {
				int end = str.substring(start).indexOf(endString);
				if (end != -1) {
					end = end + start;
					int end1 = (end >= str.length()) ? str.length() : (end);
					data = data + ", " + str.substring(start + startString.length(), end1);
				}
			}
		}
		return data;
	}
	
	public static String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();

		while (line != null) {
			sb.append(line);
			sb.append("\n");
			line = br.readLine();
		}
		br.close();
		return sb.toString();

	}

	public static void writeInAFile(String fileName, String content, boolean isAppend) throws IOException {
		File file = new File(fileName);

		if (!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsolutePath(), isAppend);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.append(content);
		bw.newLine();
		bw.close();
	}


}
