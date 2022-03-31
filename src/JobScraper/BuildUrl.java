package JobScraper;

import java.util.Map;

public class BuildUrl {

    private static final String LINKEDIN_BASE_JOB_SEARCH_URL = "https://www.linkedin.com/jobs/search?";
    private static final String INDEED_BASE_JOB_SEARCH_URL = "https://ca.indeed.com/jobs?";

    private static String position; 
    private static String location; 
    private static String baseUrl;
    private static String baseUrlPosition;
    private static String baseUrlLocation;

    public BuildUrl(String position, String location, String site){

        BuildUrl.position = position;
        BuildUrl.location = location;
        
        if(site.contains(Scraper.INDEED)){
            baseUrl = INDEED_BASE_JOB_SEARCH_URL;
            baseUrlPosition = "q";
            baseUrlLocation = "l";
        }

        if(site.contains(Scraper.LINKEDIN)){
            baseUrl = LINKEDIN_BASE_JOB_SEARCH_URL;
            baseUrlPosition = "keywords";
            baseUrlLocation = "location";
        }
        
    }
    
    public String getUrl(){
        
        if(baseUrl == null || baseUrl == ""){
            return baseUrl;
        }

        String url = baseUrl;

        String[] positionKeywords = null;
        String[] locationKeywords = null;
        
        String positionBuild = "";

        if(position != null && position != ""){
            positionKeywords = position.split(" ");
            positionBuild = baseUrlPosition + "=";
        }// in else part add throw error "Position cannot be null or can it be? "

        

        for(String keyWord : positionKeywords){
            positionBuild += keyWord.trim() + "%20";
            //System.out.println("keyWord ---> " + keyWord);
        }

        if(positionBuild != null){
            positionBuild = positionBuild.substring(0, positionBuild.length() - 3);
           //System.out.println("positionBuild ---> " + positionBuild);

        }
        
        String locationBuild = "";

        if(location != null && location != ""){
            locationKeywords = location.split(" ");
            locationBuild = baseUrlLocation + "=";
            
        }// in else part add throw error "Position cannot be null or can it be? "
        
        for(String keyWord : locationKeywords){
            locationBuild += keyWord.trim() + "%2C%20";
            //System.out.println("keyWord ---> " + keyWord);
        }

        if(locationBuild != null){
            locationBuild = locationBuild.substring(0, locationBuild.length() - 6);
            //System.out.println("locationBuild ---> " + locationBuild);

        }

        url = positionBuild != null ? url + positionBuild : url;

        //System.out.println("url pos ---> " + url);

        url = positionBuild != null && locationBuild != null ?
                url + "&" + locationBuild : url + locationBuild;

        //System.out.println("url pos + loc ---> " + url);

        return url;

    }
}
