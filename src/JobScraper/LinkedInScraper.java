package JobScraper;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkedInScraper {

    public static void singleCrawl(String position, String location, String site){

        BuildUrl buildUrlLinkedin = new BuildUrl(position, location, site);
        
        String url = buildUrlLinkedin.getUrl();

        //Map<String, JobWrapper> jobsMap = new HashMap<String,JobWrapper>();

        /*
        // Save original out stream.
        PrintStream originalOut = System.out;
        
        try {
            // Create a new file output stream.
            PrintStream fileOut = new PrintStream("./outLinkedIn.txt");

            // Redirect standard out to file.
            System.setOut(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

        org.jsoup.nodes.Document doc = RequestUrl.request(url, null);
        Elements elements = doc.getElementsByClass("base-search-card__info");

        for(Element element : elements){

            JobWrapper job = new JobWrapper();

            job.onLinkedin = true;
            job.onIndeed = false;
            job.position = getValueFromChild("base-search-card__title", element);
            job.location = getValueFromChild("job-search-card__location", element);
            job.companyName = getValueFromChild("hidden-nested-link", element);
            job.link = getLinkFromSibling("base-card__full-link", element);

            

            String jobKey = job.companyName + ":" + job.position;

            if(Scraper.jobsMap.containsKey(jobKey)){
                JobWrapper scrappedJob =  Scraper.jobsMap.get(jobKey);
                scrappedJob.onLinkedin = true;
            }else{
                Scraper.jobsMap.put(jobKey, job);
            }
        }

        /*
        for(JobWrapper job : Scraper.jobsMap.values()){
            System.out.println("\ncompany -----> " + job.companyName);
            System.out.println("position -----> " + job.position);
            System.out.println("location -----> " + job.location);
            System.out.println("link -----> " + job.link);
            System.out.println("onIndeed -----> " + job.onIndeed);
            System.out.println("onLinkedin -----> " + job.onLinkedin);
            

            originalOut.println("\ncompany -----> " + job.companyName);
            originalOut.println("position -----> " + job.position);
            originalOut.println("location -----> " + job.location);
            originalOut.println("link -----> " + job.link);
            originalOut.println("onIndeed -----> " + job.onIndeed);
            originalOut.println("onLinkedin -----> " + job.onLinkedin);

        }*/
        
        //originalOut.println("Program exit Linked in Scraper. ");

    }

    private static String getValueFromChild(String className, Element element){

        for(Element childElement : element.getElementsByClass(className)){

            String value = childElement.text();
            if(value != null && value != ""){
                return value;
            }

        }
        return null;
    }

    private static String getLinkFromSibling(String className, Element element){

        for(Element siblingElement : element.siblingElements()){

            if(siblingElement.className().equals(className)){
            String link = siblingElement.attr("href");
                if(link != null && link != ""){
                    return link;
                }
            }
            
        }
        return null;
    }

}
