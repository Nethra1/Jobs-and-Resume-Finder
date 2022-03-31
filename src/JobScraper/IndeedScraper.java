package JobScraper;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class IndeedScraper {

    public static void singleCrawl(String position, String location, String site) {

        BuildUrl buildUrlLinkedin = new BuildUrl(position, location, site);

        String url = buildUrlLinkedin.getUrl();

        //System.out.println("url--->" + url);

        /*
        // Save original out stream.
        PrintStream originalOut = System.out;
        
        try {
            // Create a new file output stream.
            PrintStream fileOut = new PrintStream("./outIndeed.txt");

            // Redirect standard out to file.
            System.setOut(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

        org.jsoup.nodes.Document doc = RequestUrl.request(url, null);
        Elements elements = doc.getElementsByClass("slider_container");

        int count = 0;

        for(Element mainDiv : elements){

            count++;

            //System.out.println( "\ncount ---> " + count);

            
            JobWrapper job = new JobWrapper();

            job.onLinkedin = false;
            job.onIndeed = true;
            job.position = getPosition(mainDiv);
            job.companyName = getValueByClassName(mainDiv, "companyOverviewLink");
            job.location = getValueByClassName(mainDiv, "companyLocation");
            job.link = getLink(mainDiv);

            /*
            System.out.println( "position ---> " + job.position);
            System.out.println( "companyName ---> " + job.companyName);
            System.out.println( "location ---> " + job.location);
            System.out.println( "link ---> " + job.link);
            System.out.println( "onLinkedin ---> " + job.onLinkedin);
            System.out.println( "onIndeed ---> " + job.onIndeed);
            */

            String jobKey = job.companyName + ":" + job.position;

            
            if(nullCheckJob(job)){
                if(Scraper.jobsMap.containsKey(jobKey)){
                    JobWrapper scrappedJob =  Scraper.jobsMap.get(jobKey);
                    scrappedJob.onIndeed = true;
                }else{
                    Scraper.jobsMap.put(jobKey, job);
                }
            }
        }

        //originalOut.println("Program exit Indeed in Scraper. ");


    }

    private static String getLink(Element mainDiv) {

        String link = null;

        Element parent = mainDiv.parent();
        
        String url = parent.attr("href");

        if(url != null && url != ""){
            link = "https://ca.indeed.com" + parent.attr("href");
        }
        
        return link;
    }

    private static String getValueByClassName(Element mainTable, String className) {

        String value = null;

        for(Element element : mainTable.getElementsByClass(className)){
            value = element.text();
        }

        return value;
    }

    private static String getPosition(Element mainTable) {

        String position = null;

        for(Element heading2 : mainTable.getElementsByClass("jobTitle")){

            for(Element spanForTitle : heading2.getElementsByTag("span")){

                if(spanForTitle.attr("title") != "" && spanForTitle.attr("title") != null){
                    position = spanForTitle.attr("title");
                }
        
            }
        }

        return position;
    }

    private static boolean nullCheckJob(JobWrapper job){

        return job.position != null && job.companyName != null && job.location != null;
    }

}
