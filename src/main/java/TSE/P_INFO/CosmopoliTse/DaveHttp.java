package TSE.P_INFO.CosmopoliTse;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DaveHttp {

	//Uncompress the received dates
	public static String uncompress(GZIPInputStream in) throws IOException {   
		 if (in == null) {   
			 return "";   
		 }   
		 ByteArrayOutputStream out = new ByteArrayOutputStream();
         byte[] buffer = new byte[256];
		 int n;   
		 while ((n = in.read(buffer))>= 0) {   
		 	out.write(buffer, 0, n);   
		 }   
		    // ToString () using the platform default encoding, can also be explicitly specified as toString ()   
		 return out.toString("utf-8");
	}   
	
    // A request to send a GET method to the specified URL
    // realUrl: The specified URL
	public static String sendGet(String url) throws IOException {
        String result = "";
        GZIPInputStream in = null;
        
        try {
        	URL realUrl = new URL(url);
            // Open the connection to servers with one URL
            URLConnection connection = realUrl.openConnection();
            // Set the generic request properties
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            // Establish the actual connection
            connection.connect();
            // Define GZIPInputStream and uncompress responsive dates from servers
            in = new GZIPInputStream(connection.getInputStream());    
   		    result = uncompress(in);
   		    
   		    // Return
   		 	return result;
            
        } catch (Exception e) {
            System.out.println("error in http get!" + e);
            e.printStackTrace();
        }
        // Use the 'finally' block to close the input stream
        finally {
            in.close();
        }
        // Return
        return result;
    }
	
	public static void firstStory() throws IOException{
		Scanner in = new Scanner(System.in);
		System.out.println("Sur quel sujet voulez-vous effectuer la recherche?");
		String tag = in.nextLine();
		System.out.println("Combien de contributeurs souhaitez-vous afficher?");
		int count=in.nextInt();
		String url = "https://api.stackexchange.com/2.2/tags/" + tag + "/top-answerers/all_time?page=1&pagesize="+count+"&site=stackoverflow";
		String jsonString = DaveHttp.sendGet(url);
		JSONObject obj= new JSONObject(jsonString);
		List<String> contributorsNames = new ArrayList<String>();
		List<Long> contributorsScores = new ArrayList<Long>();
		for(int i=0;i<count;i++)
		{
			String nom = obj.getJSONArray("items").getJSONObject(i).getJSONObject("user").getString("display_name");
			contributorsNames.add(nom);
			Long score = obj.getJSONArray("items").getJSONObject(i).getLong("score");
			contributorsScores.add(score);
		}
		
		
		System.out.println("Les meilleurs contributeurs (Nom : Score) au sujet " + tag + " sont: ");
		for(int i=0;i<contributorsNames.size();i++)
		{
			System.out.println(contributorsNames.get(i) + ": " + contributorsScores.get(i));
		}
	}
	
    public static String secondStoryDave(String Tag) throws IOException{
    	String url = "https://api.stackexchange.com/2.2/tags/" + Tag + "/top-answerers/all_time?page=1&pagesize=1&site=stackoverflow";
    	String jsonString = DaveHttp.sendGet(url);
    	JSONObject obj= new JSONObject(jsonString);  
    	String tagTopAnswerer=obj.getJSONArray("items").getJSONObject(0).getJSONObject("user").getString("display_name");
    	return tagTopAnswerer;
    }
    
    public static String thirdStoryDave() throws IOException{
    	Scanner in = new Scanner(System.in);
    	Scanner in1 = new Scanner(System.in);
    	System.out.println("Commbien de tag vous voulez inserer?");
    	int nombre = in.nextInt();
    	List<String> tagList = new ArrayList<String>();
    	Map<String,Long> userScoreMap = new HashMap<String, Long>();
    	List <Map<String,Long>> listUserScoreMap = new ArrayList<Map<String,Long>>();
    	for(int i=0;i<nombre;i++){
    		System.out.println("Inserer le "+(i+1)+" tag");
    		String tags = in1.nextLine();
    		tagList.add(tags);
    	}
    	for(String tt:tagList){
    		String url = "https://api.stackexchange.com/2.2/tags/" + tt + "/top-answerers/all_time?site=stackoverflow";
        	String jsonString = DaveHttp.sendGet(url);
        	JSONObject obj= new JSONObject(jsonString); 
        	for(int j=0;j<20;j++){
        		String nom = obj.getJSONArray("items").getJSONObject(j).getJSONObject("user").getString("display_name");
        		Long score = obj.getJSONArray("items").getJSONObject(j).getLong("score");
        		userScoreMap.put(nom,score);
        	}
        	listUserScoreMap.add(userScoreMap);
    	}
    	for(Map<String,Long> map: listUserScoreMap){
    		for (String s : map.keySet()) {
    			System.out.println("nom:" + s);
    			System.out.println("score:" + map.get(s));
    		}
    		System.out.println("---------------");
    	}
    	
    	
    	return "";
    }
    
    public static void menu() throws IOException{
    	System.out.println("Que souhaitez-vous afficher?");
    	System.out.println("1: Les personnes contribuant le plus à un sujet donné");
    	System.out.println("2: La personne qui a le top tag dans un sujet donné");
    	System.out.println("3: La personne qui contribue le plus à un ensemble de tags donnés");
    	Scanner in = new Scanner(System.in);
    	
    	boolean continuer=false;
    	
    	do
    	{
    		int choix=in.nextInt();
	    	switch (choix)
	    	{
	    	  case 1:
	    		  firstStory();
	    		  continuer=false;
	    	    break;  
	    	  case 2:
	    		  secondStoryDave("java");
	    		  continuer=false;
	    		  break;
	    	  case 3:
	    		  thirdStoryDave();
	    		  continuer=false;
	    		  break;
	    	  default:
	    		  System.out.println("Erreur de choix, veuillez recommencer.");
	    		  continuer=true;
	    		  break;
	    	}
	    	
    	}while(continuer);
    }
    
    public static void main(String[] args) throws IOException {
    	menu();
    	//firstStory();
       //System.out.println(DaveHttp.secondStoryDave("javascript"));
    	//thirdStoryDave();
    	
    }
}