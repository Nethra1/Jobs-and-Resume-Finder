package JobScraper;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class RequestUrl {

    public static org.jsoup.nodes.Document request(String url, ArrayList<String> visited){

        try{
            Connection con = Jsoup.connect(url); //Jsoup.connect(url);
            org.jsoup.nodes.Document doc =  con.get();

            if(con.response().statusCode() == 200){
                //System.out.println("\nLink : " + url);
                //System.out.println("Page Title---> " + doc.title());

                if(visited != null){
                    visited.add(url);
                }

                return doc;

            }
            return null;

        }catch(IOException e){

            return null;
        }
    }
    
}
