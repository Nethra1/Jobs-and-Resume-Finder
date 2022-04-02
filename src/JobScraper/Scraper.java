package JobScraper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Scraper {

    public static Map<String, JobWrapper> jobsMap = new HashMap<String, JobWrapper>();
    public static final String INDEED = "indeed";
    public static final String LINKEDIN = "linkedIn";
    
    public void scrapeLinkedInAndIndded(String position) throws IOException {
//    	String position = "administrative assistant";
        String location = "Canada";

        // crawl(1, url, visited, 1);..
        IndeedScraper.singleCrawl(position, location, INDEED);
        LinkedInScraper.singleCrawl(position, location, LINKEDIN);

        // Save original out stream.
        PrintStream originalOut = System.out;

        try {
            // Create a new file output stream.
            PrintStream fileOut = new PrintStream("./out.txt");

            // Redirect standard out to file.
            System.setOut(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int linkedinJobs = 0;
        int indeedJobs = 0;
        int bothJobs = 0;

        Map<Integer, Object[]> jobData = new TreeMap<Integer, Object[]>();
        jobData.put(1, new Object[] { "ID", "Company", "Position", "Location", "Job Link", "From Indeed", "From LinkedIn" });
        Integer i = 1;
        for (JobWrapper job : Scraper.jobsMap.values()) {
            System.out.println("\ncompany -----> " + job.companyName);
            System.out.println("position -----> " + job.position);
            System.out.println("location -----> " + job.location);
            System.out.println("link -----> " + job.link);
            System.out.println("onIndeed -----> " + job.onIndeed);
            System.out.println("onLinkedin -----> " + job.onLinkedin);
            jobData.put(i++, new Object[] {i.toString(), job.companyName, job.position, job.location, job.link, job.onIndeed.toString(), job.onLinkedin.toString()});
            if (job.onIndeed == true && job.onLinkedin == true) {
                bothJobs++;
            } else if (job.onIndeed == true) {
                indeedJobs++;
            } else if (job.onLinkedin == true) {
                linkedinJobs++;
            }

        }
        
        addToExcel(jobData, position);
        System.out.println("\nlinkedinJobs -----> " + linkedinJobs);
        System.out.println("indeedJobs -----> " + indeedJobs);
        System.out.println("bothJobs -----> " + bothJobs);

        originalOut.println("Program exit Scraper. ");

    }

    private void addToExcel(Map<Integer, Object[]> jobData, String position) throws IOException {
    	XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("/Users/nethravijayadas/Study/ACC/ResumeData/" + position + ".xlsx"));
		XSSFSheet spreadsheet = workbook.createSheet(position+" Jobs");

		// creating a row object
		XSSFRow row;
		Set<Integer> keyid = jobData.keySet();

		int rowid = 0;

		// writing the data into the sheets...

		for (Integer key : keyid) {

			row = spreadsheet.createRow(rowid++);
			Object[] objectArr = jobData.get(key);
			int cellid = 0;

			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}
		}
		FileOutputStream out = new FileOutputStream(
				new File("/Users/nethravijayadas/Study/ACC/ResumeData/" + position + ".xlsx"));

		workbook.write(out);
		out.close();
		
	}

	public static void main(String[] args) {

    	//scrapeLinkedInAndIndded();

    }

}
