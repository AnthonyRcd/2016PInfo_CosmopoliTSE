package TSE.P_INFO.CosmopoliTse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BobUser2 {
	static String APIclef = "knaK2OI6lGZq*Cx4H439cw((";
	private static Scanner input= new Scanner(System.in);
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
		 return out.toString("utf-8");
	}   

	public static String sendGet(String url) throws IOException {
       String result = "";
       GZIPInputStream in = null;
       
       try {
       	URL realUrl = new URL(url);
           URLConnection connection = realUrl.openConnection();
           connection.setRequestProperty("accept", "*/*");
           connection.setRequestProperty("connection", "Keep-Alive");
           connection.connect();
           in = new GZIPInputStream(connection.getInputStream());    
  		    result = uncompress(in);
  		 	return result;
           
       } 
       catch (Exception e) {
           System.out.println("error in http get!" + e);
           e.printStackTrace();
       }
       finally {
           in.close();
       }
       return result;
       }
	public static JSONObject requete (String mot) throws IOException{
		String urlTag= "https://api.stackexchange.com/2.2/tags?order=desc&sort=popular&inname="+mot+"&site=stackoverflow";
		String jsonString = BobUser2.sendGet(urlTag);
		JSONObject Tags = new JSONObject(jsonString);
		
		return Tags;
	}
	public static HashMap <String, Integer> RecupérationMotQuestion(String Question) throws JSONException{
		
	    int firstChar = -1;
	    int length = 0;
	    HashMap <String, Integer> MapMot = new HashMap<String,Integer>();
	    for(int i=0 ; i<Question.length() ; i++) {
	        if(!Character.isLetter(Question.charAt(i))){
	            if(firstChar != -1)
	                MapMot.put((Question.substring(firstChar,firstChar + length)), i);
	            firstChar = -1;
	            length = 0;
	        } else {
	            if(firstChar == -1)
	                firstChar = i;
	            length++;
	        }
	        if(firstChar != -1 && i == Question.length()-1)
	        	MapMot.put(Question.substring(firstChar,firstChar + length),i);
	    }
		return MapMot;
	}
	public static ArrayList<String> Comparaison( Map<String, Integer> MapMot) throws IOException{
		ArrayList<String> ListeMotClef = new ArrayList<String>();
		String valeur = null;
	
		for (Map.Entry<String, Integer> e : MapMot.entrySet())
			
			
			if (requete(e.getKey()).getBoolean("has_more")==true) {
				
				String urlRelatedTag = "https://api.stackexchange.com/2.2/tags/"+ valeur+"/related?site=stackoverflow";
				String jsonString2 = BobUser2.sendGet(urlRelatedTag);
				JSONObject RelatedTags = new JSONObject(jsonString2);
				JSONArray TagsArrayRelated = RelatedTags.getJSONArray("items");
				for (int i= 1; i<4; i++){
					ListeMotClef.add(TagsArrayRelated.getJSONObject(i).getString("name"));
				}
			}
			else {
				
			}
		if (ListeMotClef.isEmpty()){
			System.out.println("Pas de mot clef à ajouter");
		}
		return ListeMotClef;
	}
	
	public static void main(String[] args) throws IOException{
		try{
			System.out.println("Entrez une question");
			String question = input.nextLine();
			System.out.println(BobUser2.Comparaison(BobUser2.RecupérationMotQuestion(question)));
		}
		 catch (JSONException j) {
			System.out.println("Erreur");
		}
	}
}
