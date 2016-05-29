package featureExtractor;
import java.io.BufferedReader;
import java.lang.*;
import java.io.FileReader;
import java.io.IOException;

import crawler.utils.CrawlerUtils;

public class Extractor {
	String[] brr = new String[100];
	String[] arr = new String[1000];
	static int[] ar = new int[1000];
	public static String OCTAVE_FILE_BASE_DIR= "octaveFiles/"; 
	/*public static void main(String ss[]) throws IOException {
		Extractor jApp = new Extractor();
		jApp.fileAttributes();
	}*/

	public void fileAttributes(String fileName) throws IOException {
		try {
			Extractor jApp = new Extractor();
			
			System.out.println("Number of lines"+"\t\t"+"Number of functions"+"\t"+"Number of variables"+"\t"+"Number of loops"+"\t\t"+"Number of conditional statement");
			String text = jApp
					.readFile(fileName);

			/*for (int i = 0; i < text.length(); i++){
			    char c = text.charAt(i);
			    if(c=='<') {
			    	break;
			    }
			    //Process char
			}*/
			brr = text.split("</pre>");
			int len = brr.length;
			int index=0;
			while(len>0) {
			int number_of_loops = 0;
			int number_of_conditional = 0;
			int number_of_function = 0;
			int number_of_variables = 0;
			arr = brr[index].split("\n");
			for (int i = 0; i < arr.length; i++) {
				if (arr[i].contains("for") || arr[i].contains("while")) {
					number_of_loops++;
				}
				if (arr[i].contains("if")) {
					number_of_conditional++;
				}
				if ((arr[i].contains("int") || arr[i].contains("double")
						|| arr[i].contains("float") || arr[i].contains("long")
						|| arr[i].contains("char") || arr[i].contains("void") || arr[i].contains("struct") ||arr[i]
							.contains("bool")) && (!arr[i].contains("//")) && !arr[i].contains("printf")) {
					if (arr[i].contains("(")) {
						number_of_function++;
					} else {
						number_of_variables = number_of_variables+1;
					}
				}
			}
			if(len>1) {
				System.out.println(ar[index]+"\t\t\t"+(number_of_function-1)+"\t\t\t "+number_of_variables+"\t\t\t "+number_of_loops+"\t\t\t "+number_of_conditional);
				CrawlerUtils.writeInAFile(OCTAVE_FILE_BASE_DIR+"features.m",ar[index]+"\t\t\t"+(number_of_function-1)+"\t\t\t "+number_of_variables+"\t\t\t "+number_of_loops+"\t\t\t "+number_of_conditional ,true);
			}
			index++;
			len--;
			}
		} catch (Exception e) {
			System.out.println("error...");
		}
		
	}

 public String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		int i=0;
		while (line != null) {
			ar[i]=ar[i]+1;
			if(line.contains("</pre>")) {
				i++;
			}
			sb.append(line);
			sb.append("\n");
			line = br.readLine();
		}
		br.close();
		return sb.toString();

	}

}