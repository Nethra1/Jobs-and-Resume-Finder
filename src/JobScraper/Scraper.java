package JobScraper;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class Scraper {

    public static Map<String, JobWrapper> jobsMap = new HashMap<String, JobWrapper>();
    public static final String INDEED = "indeed";
    public static final String LINKEDIN = "linkedIn";

    public static void main(String[] args) {

        // String url =
        // "https://www.linkedin.com/jobs/search?keywords=Full%20Stack%20Engineer&location=Windsor%2C%20Ontario%2C%20Canada&geoId=101750980&trk=public_jobs_jobs-search-bar_search-submit&position=1&pageNum=0";
        // String url2 =
        // "https://www.linkedin.com/jobs/search?keywords=Full%20Stack%20Engineer";
        // ArrayList<String> visited = new ArrayList<>();
        String position = "administrative assistant";
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

        for (JobWrapper job : Scraper.jobsMap.values()) {
            System.out.println("\ncompany -----> " + job.companyName);
            System.out.println("position -----> " + job.position);
            System.out.println("location -----> " + job.location);
            System.out.println("link -----> " + job.link);
            System.out.println("onIndeed -----> " + job.onIndeed);
            System.out.println("onLinkedin -----> " + job.onLinkedin);

            if (job.onIndeed == true && job.onLinkedin == true) {
                bothJobs++;
            } else if (job.onIndeed == true) {
                indeedJobs++;
            } else if (job.onLinkedin == true) {
                linkedinJobs++;
            }

        }

        System.out.println("\nlinkedinJobs -----> " + linkedinJobs);
        System.out.println("indeedJobs -----> " + indeedJobs);
        System.out.println("bothJobs -----> " + bothJobs);

        originalOut.println("Program exit Scraper. ");

    }

}
