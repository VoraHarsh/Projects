package com.WebApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.client.HttpClient;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

import Models.LuisJsonModel;

/*@WebServlet(
        name = "SearchServlet",
        urlPatterns = "/"
)*/


public class SearchServlet extends HttpServlet
{
	 String entitystore;
	 Map<String, HashMap<String,String>> movies = new HashMap<String,HashMap<String,String>>();
	 TreeMap<String,HashMap<String,String>> sortedKey = new TreeMap<>();
	 public SearchServlet(){
		
	}
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException,ServletException
	{
		String search;
		search = req.getParameter("search");
		System.out.print(search);
		
		if (search.isEmpty() || search.length()<7) {
			res.sendRedirect("index.jsp");
		}
		else {
		LuisJsonModel luisJson = Utilities.CallLuisAsync(search);
		 System.out.println(luisJson);
		 
		 PrintWriter out = res.getWriter();
		 out.println(luisJson);
		 

	
		try {
			entitystore = Utilities.ExtractLuisData(luisJson);
			System.out.println(entitystore);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 this.getMoviepage(req, res, entitystore);
		
		}
	}
	 private void getMoviepage(HttpServletRequest request, HttpServletResponse response, String entitystore)
	            throws ServletException, IOException{
		 
		 	movies.clear();
		 	sortedKey.clear();
			
			Query sparqlquery = QueryFactory.create(entitystore);
			String endpointDb = "http://dbpedia.org/sparql";

	        // Lager Query Execution Factory 
	        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpointDb, sparqlquery);

	        // Setter Timeout 
	        ((QueryEngineHTTP)qexec).addParam("timeout", "10000");

	        int counter = 0;
	        ResultSet resultSet = qexec.execSelect();
	        
	        List<String> em;
	        em = resultSet.getResultVars();
	        System.out.println(em.toString());
	       
	       int i=0;

	       while (resultSet.hasNext()) {

	       	i=movies.size();
	       	QuerySolution bindings=resultSet.next();

	       	HashMap<String,String> moviedata = new HashMap<String,String>();
	       	String movieLink = bindings.get("movieLink").toString();
	       	System.out.println("movieLink: "+movieLink);
	       	moviedata.put("movieLink", movieLink);
	       	
	       	String title=bindings.get("title").toString();
	       	title=Remove3Characters(title).toString();
	       	System.out.println("Title: "+title);
	       	moviedata.put("title", title);
	       	
	       	
	        if (bindings.contains("genreLink"))	
	      	{
	       	
	       	String genreLink=bindings.get("genreLink").toString();
	       	if (genreLink.isEmpty()) {
	       		genreLink = null;
	       		System.out.println("genreLink: "+genreLink);
	       		moviedata.put("genreLink", genreLink);
	       	}
	        System.out.println("genreLink: "+genreLink);
	       	moviedata.put("genreLink", genreLink);

	       	
	       	String genre = bindings.get("genre").toString();
	       	if (genre.isEmpty()) {
	       		genre = null;
	       		System.out.println("genre: "+genre);
	       		moviedata.put("genre", genre);
	       	}
	       	genre = Remove3Characters(genre).toString();
	       	System.out.println("genre: "+genre);
	       	moviedata.put("genre", genre);
	       
	       	
	      	
	    }
	        else if(!(bindings.contains("genrelink") )) {
	      		 String genreLink = "";
	      		System.out.println("genreLink: "+genreLink);
	      		moviedata.put("genreLink", genreLink);
	      		
	      		String genre = "";
	      		System.out.println("genre: "+genre);
	      		moviedata.put("genre", genre);
	      	}
	      	
	    	if(bindings.contains("releaseDate")){
	           	String Releasedate=bindings.get("releaseDate").toString();
	           	Releasedate = DateCreator(Releasedate).toString();
	           	moviedata.put("ReleaseDate",Releasedate);
	            System.out.println("ReleaseDate:"+Releasedate);
	          	}
	          	else if(!(bindings.contains("releaseDate"))){
	          		String Releasedate="";
	          	 	moviedata.put("ReleaseDate",Releasedate);
	                System.out.println("ReleaseDate:"+Releasedate);
	          		
	          	}
	        
	        movies.put(Integer.toString(i), moviedata);
	   
	   }
	       sortedKey.putAll(movies);
	       System.out.println(movies.size());
	 
	       if(movies.size()==0){
	        	request.setAttribute("movieData", this.sortedKey);
	
	        }else{
	        
	        //System.out.println("result as text: "+ResultSetFormatter.asText(results));
	    	//System.out.println("inside search movie: length of arr "+arr.length);
	        	request.setAttribute("movieData", this.sortedKey);
	        	
	        	System.out.println("done");
	        
	        }
	    	
	    	
	    			request.getRequestDispatcher("result.jsp")
	    					.forward(request, response);
		 
		 
		 
	}
	 
	 public static String Remove3Characters(String word) {
			String result = null;
			
		if (word.length() > 3) {
				
			result = word.substring(0, word.length()-3);
		}
			
		return result;
		}

		public static String DateCreator(String word) {
			
			String date = null;
			if(!(word.isEmpty())){
				int index = word.indexOf("^");
				date = word.substring(0,index);
			}
			
			
			
			return date;

		}
	 
		
}
