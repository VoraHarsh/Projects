package com.WebApp;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import Models.Entity;
import Models.LuisJsonModel;

public class Utilities {
	static String entities;
	
	public static LuisJsonModel CallLuisAsync(String query){
		 HttpClient httpclient = HttpClients.createDefault();
		 LuisJsonModel data = new LuisJsonModel();

	        try
	        {

	            // The ID of a public sample LUIS app that recognizes intents for turning on and off lights
	            String AppId = "a98499b9-7db6-4232-9c71-3a3dc546af7b";
	            
	            // Add your endpoint key 
	            // You can use the authoring key instead of the endpoint key. 
	            // The authoring key allows 1000 endpoint queries a month.
	            String EndpointKey = "47f9e6cee0d74f77a0641ff4aac3a8cf";

	            // Begin endpoint URL string building
	            URIBuilder endpointURLbuilder = new URIBuilder("https://westus.api.cognitive.microsoft.com/luis/v2.0/apps/a98499b9-7db6-4232-9c71-3a3dc546af7b?verbose=true&timezoneOffset=-360&subscription-key=47f9e6cee0d74f77a0641ff4aac3a8cf&q=");

	            // query text
	            endpointURLbuilder.setParameter("q", query);            

	            // create URL from string
	            URI endpointURL = endpointURLbuilder.build();

	            // create HTTP object from URL
	            HttpGet request = new HttpGet(endpointURL);

	            // set key to access LUIS endpoint
	            request.setHeader("Ocp-Apim-Subscription-Key", EndpointKey);

	            // access LUIS endpoint - analyze text
	            HttpResponse response = httpclient.execute(request);

	            // get response
	            HttpEntity entity = response.getEntity();


	            if (entity != null) 
	            {	//data = (LuisJsonModel)entity;
	            	entities = EntityUtils.toString(entity);
	            	data = new Gson().fromJson(entities, LuisJsonModel.class);
	            	//System.out.println(EntityUtils.toString(entity));
	            }
	        }
	    	
	        catch (Exception e)
	        {	
	            System.out.println(e.getMessage());
	        }
	     return data;	

	
}
	
	
public static String ExtractLuisData(LuisJsonModel luisJson) throws ParseException {
		
		int numberOfItems = 0;
       String genre = "";
       int year = 0;
       String exactDate = "";
      // int number = Integer.parseInt(i.resolution.value);
       
       
       for (Entity i : luisJson.getEntities()) {
          
       	switch(i.getType()) {
       	 case "builtin.number":
       		 	
             if (Integer.parseInt(i.resolution.value) < 1000)
                {
                    numberOfItems = Integer.parseInt(i.resolution.value);
                }
               
                break;

            case "genre":
                genre = i.entity;
                break;
                
            case "builtin.datetimeV2.daterange":
           	 int yearDateTime = 0;
  //         	 int entity = Integer.parseInt(i.entity); 
           	 Date exactDateTime;
           	 String strDateFormat = "yyyy-mm-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

                if ((i.entity).length() == 4)
                {
                    year = Integer.parseInt(i.entity);
                }
                else if(sdf.parse(i.entity) != null)
                {
               	 exactDateTime = sdf.parse(i.entity);
               	 exactDate = exactDateTime.toString();
                }
                break;
                
                
                    	
       	}
  
       	
       }
       
      return CreateSparqlQuery(numberOfItems, genre, year, exactDate);
		
	}

	private static String CreateSparqlQuery(int numberOfItems, String genre, int year, String exactDate) {
		// TODO Auto-generated method stub
		
		 String limit = numberOfItems > 0 ? String.format("LIMIT"+numberOfItems+"") : "";
        String genreMatch = (genre.trim().isEmpty()) ? "" : String.format("FILTER ( regex (str(?genre),'"+genre+"', 'i'))");
        String dateMatch = "";
        int yearinc = year+1;

        if (exactDate.equals(LocalDateTime.now()) && year == 0)
        {
            //Means that both haven't been assigned
        }
        else if (!(exactDate.trim().isEmpty()))
        {
            dateMatch = String.format("FILTER ( regex (str(?releaseDate),'"+exactDate+"', 'i'))");
        }
        else if (!(year == 0))
        {
            dateMatch = String.format("FILTER ((?releaseDate >= \""+year+"-01-01\"^^xsd:date) && (?releaseDate < \""+year+"-12-31\"^^xsd:date))");
        }
		
      String querypattern ="PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
        "PREFIX db: <http://dbpedia.org/ontology/> " +
        "PREFIX prop: <http://dbpedia.org/property/> " +
        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
        "PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
        "SELECT ?movieLink ?title ?genreLink ?genre ?releaseDate " +
        "WHERE {{ " +
            "?movieLink rdf:type db:Film; " +
                       "foaf:name ?title. " +
            "OPTIONAL {{ ?movieLink prop:genre ?genreLink. " +
                       "?genreLink rdfs:label ?genre. " +
                       "FILTER(lang(?genre) = 'en') }}. " +
            "OPTIONAL {{ ?movieLink <http://dbpedia.org/ontology/releaseDate> ?releaseDate }}. " +
            genreMatch + 
            dateMatch +
            "FILTER(lang(?title) = 'en') " +
        "}}" +
        "ORDER BY DESC(?releaseDate)" +
        limit;
				
		return String.format(querypattern, genreMatch, dateMatch, limit);

	}

}
