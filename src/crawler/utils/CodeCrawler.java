package crawler.utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import crawler.utils.CrawlerUtils;
import java.util.*;
import featureExtractor.Extractor;

public class CodeCrawler {
	private String name;
	private static String BASE_URL_PLAINTEXT = "https://www.codechef.com/viewplaintext/";
	private static String BASE_URL_CONTENT	= "https://wwww.codechef.com/status/";
	private static String problemCode = "COINS";
	private static String problemParams = "?sort_by=Time&sorting_order=asc&language=11&status=15";
	private static String pager = "&page=";
	private static String FETCHED_FILE_DIR_BASE = "crawlerData/codechef/"+ problemCode+"/";
	private static final String NOT_CRAWLED_URLS = "crawlerData/codechef/NotCrawled.txt";

	public static void main(String[] args) throws SQLException, IOException, InterruptedException {
		CodeCrawler codeChefCrawler = new CodeCrawler();
		List<String> fetchedSolutionIds = new ArrayList<String>();
		//CrawlerUtils.writeInAFile(FETCHED_FILE_DIR_BASE +"/consolidatedCode" + ".txt", " ", false);
		int pagerCount = 2; //// Change to the no. of pages to lookup 
		/// Get the list of solution Ids for the given Problem Code
		for (int i =0;i<pagerCount;i++){
			String url = BASE_URL_CONTENT + problemCode + problemParams;
			if(i!=0) url = url+pager+i;
			System.out.println("Trying to connect "+ url);
			try {
				codeChefCrawler.processPage(url);
				String text = CrawlerUtils.readFile(getFileName(url));
				fetchedSolutionIds.addAll(codeChefCrawler.processPageFromText(text));
				System.out.println("Completed " + i);
				Thread.sleep(1000);
			} catch(Exception e) {
				e.printStackTrace();
				CrawlerUtils.writeInAFile(NOT_CRAWLED_URLS, url,true);
			}

		}
		/*System.out.println("Total Fetched System Ids are :");
		for (String solutionId : fetchedSolutionIds){
			System.out.println(solutionId);
		}*/
		
		/// Get and Save the plaintext solution with respective ids
		
		for (String solutionId : fetchedSolutionIds){
			String url = BASE_URL_PLAINTEXT + solutionId;
			if(solutionId == " ") continue;
			try {
				codeChefCrawler.fetchAndSavePlainText(url,solutionId);
				Thread.sleep(1000);
			} catch(Exception e) {
				e.printStackTrace();
				CrawlerUtils.writeInAFile(NOT_CRAWLED_URLS, url,true);
			}

		}
		Extractor extractor = new Extractor();
		extractor.fileAttributes(FETCHED_FILE_DIR_BASE +"/consolidatedCode" + ".txt");
	}

	private static String getFileName(String url) {
		return "crawler/codechef/" + url.replaceAll(BASE_URL_CONTENT, "") + ".txt";
	}

	public void processPage(String URL) throws SQLException, IOException {
		Connection.Response response = Jsoup
                .connect(URL)
                .userAgent(
                        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                .timeout(100000).ignoreHttpErrors(true).execute();

        int statusCode = response.statusCode();
        if (statusCode == 200) {
            Document doc = response.parse();
            String text = doc.select("body").toString();
            CrawlerUtils.writeInAFile(getFileName(URL), text, false);
        } else {
            System.out.println("received error code : " + statusCode);
        }
	}

	public void fetchAndSavePlainText(String URL,String solutionId) throws SQLException, IOException {
		System.out.println("Fetching code from URL : " + URL);
		Connection.Response response = Jsoup
                .connect(URL)
                .userAgent(
                        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                .timeout(100000).ignoreHttpErrors(true).execute();

        int statusCode = response.statusCode();
        if (statusCode == 200) {
        	Document doc = response.parse();
            String text = doc.select("pre").toString();
            CrawlerUtils.writeInAFile(FETCHED_FILE_DIR_BASE + solutionId + ".txt", text, false);
            CrawlerUtils.writeInAFile(FETCHED_FILE_DIR_BASE +"/consolidatedCode" + ".txt", text, true);
        } else {
            System.out.println("received error code : " + statusCode);
        }
	}
	
	public ArrayList<String> processPageFromText(String text) throws Exception {
		//System.out.println(text);
		name = CrawlerUtils.fetchDataContinuous(text, "<td width=\"60\"", ">", "</td>");
		String[] solIds = name.split(", ");
		ArrayList<String> solutionIds = new ArrayList<String>();
		for (String solId: solIds) {           
	        //Do your stuff here 
			if(solId.equals(" ")) continue;
			System.out.println(solId);
	        solutionIds.add(solId);
	    }
		return solutionIds;
	}
	

}
