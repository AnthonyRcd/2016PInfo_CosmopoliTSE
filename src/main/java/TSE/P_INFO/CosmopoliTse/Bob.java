package TSE.P_INFO.CosmopoliTse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import TSE.P_INFO.CosmopoliTse.UsefulMethods.Methods;

public class Bob {
	
	private static Scanner input;
	
	public static String createTagString(List<String> ll, Integer length) throws UnsupportedEncodingException{
		String result = String.join(";", ll.subList(0, length));
		return URLEncoder.encode(result,"utf-8");
	}
	public static String createAfficheTag(List<String> ll, Integer length){
		String result = "Top tags: ";
		for(String l:ll){
			result += l+" et ";
		}
		result = result.substring(0,result.length()-3);
		return result;
	}
	public static void firstStory() throws IOException
	{
		System.out.println("M’aider à trouver des questions existantes qui correspondent à mon besoin");
		System.out.println("Veuillez saisir une question : ");
		input= new Scanner(System.in);
		String question = input.nextLine();
		question = question.replaceAll("\\s","%20");
		String url = "https://api.stackexchange.com/2.2/search?order=desc&sort=relevance&intitle=" + question + "&site=stackoverflow";
		JSONObject obj = Methods.generateJSONObject(url);
		String relatedQuestions ;
		try
    	{
        	relatedQuestions = obj.getJSONArray("items").getJSONObject(0).getString("title");
    	}
		catch(JSONException j)
    	{
    		System.out.println("Aucune correspondance trouvée, veuillez resaisir une question");
    		input = new Scanner (System.in);
    		question = input.nextLine();
    		url = "https://api.stackexchange.com/2.2/search?order=desc&sort=relevance&intitle=" + question + "&site=stackoverflow";
    		obj = Methods.generateJSONObject(url);
    		relatedQuestions = obj.getJSONArray("items").getJSONObject(0).getString("title");
    	}
    	
    	url = "https://api.stackexchange.com/2.2/search?order=desc&sort=relevance&intitle=" + question + "&site=stackoverflow";
        obj= Methods.generateJSONObject(url);
        relatedQuestions = obj.getJSONArray("items").getJSONObject(0).getString("title");
    	System.out.println("Les questions correspondants à votre besoin sont :");
        	
        	for (int k = 0; k < 10; k++)
        	{
        		String titreQuestions = obj.getJSONArray("items").getJSONObject(k).getString("title");
        		String lienQuestions = obj.getJSONArray("items").getJSONObject(k).getString("link");
        		System.out.println((k+1) + "." + StringEscapeUtils.unescapeHtml4(titreQuestions));
        		System.out.println(lienQuestions);
        	}
        	System.out.println("");
    	}
	public static void secondStory() throws IOException
	{
		System.out.println("Me suggérer des mots-clés à rajouter");
		System.out.println("Veuillez saisir votre recherche : ");
		
	}
	
	public static void thirdStory() throws IOException{
		boolean continuer=false;
		do{
			System.out.println("Me proposer des contributeurs à suivre");
			System.out.println("Veuillez saisir un userid : ");
			input = new Scanner (System.in);
			int userid = input.nextInt();
			List<String> toptagList = new ArrayList<String>();
			Set<String> noms = new HashSet<String>();
			String url0 = Methods.generateUserRequest(userid);
	    	String jsonString0 = Methods.sendGet(url0);
	    	JSONObject obj0= new JSONObject(jsonString0);
	    	if(obj0.getJSONArray("items").length()==0){
	    		System.out.println("Userid "+ userid +" pas trouver, veuillez refaire cette recherche ");
	    		userid = input.nextInt();
	    		url0 = Methods.generateUserRequest(userid);
	        	jsonString0 = Methods.sendGet(url0);
	        	obj0 = new JSONObject(jsonString0);
	    		continuer=true;
	    	}
	    	else{
	    		for(int j = 0;j<obj0.getJSONArray("items").length();j++){
	    			toptagList.add(obj0.getJSONArray("items").getJSONObject(j).getString("tag_name"));
	    		}
	    		for(String ss: toptagList){
	    			for(int i=0;i<3;i++){
	    				String url01 = Methods.generateTagRequest(ss);
	    		    	String jsonString01 = Methods.sendGet(url01);
	    		    	JSONObject obj01 = new JSONObject(jsonString01);
	    				//System.out.println(obj01.getJSONArray("items").getJSONObject(i).getJSONObject("user").getString("display_name"));
	    		    	noms.add(obj01.getJSONArray("items").getJSONObject(i).getJSONObject("user").getString("display_name"));
	    			}
	    		}
	    		System.out.println(noms);
	    	}
		}	
		while(continuer);
	}
	public static void fourthStory() throws IOException
	{
		boolean continuer=false;
    	do{
			System.out.println("M’indiquer les nouvelles questions (avec déjà des réponses) dans mes champs d’intérêt");
			System.out.println("Veuillez saisir un userid : ");
			input = new Scanner (System.in);
			int userid = input.nextInt();
			int flag = 10;
			int length = 6;
			List<String> toptagList = new ArrayList<String>();
			
	    	String url1 = Methods.generateUserRequest(userid);
	    	String jsonString1 = Methods.sendGet(url1);
	    	JSONObject obj1= new JSONObject(jsonString1);
	    	System.out.println("Les les nouvelles 10 question: ");
	    	if(obj1.getJSONArray("items").length()==0){
	    		System.out.println("Userid "+ userid +" pas trouver, veuillez refaire cette recherche ");
	    		userid = input.nextInt();
	    		url1 = Methods.generateUserRequest(userid);
	        	jsonString1 = Methods.sendGet(url1);
	        	obj1 = new JSONObject(jsonString1);
	    		continuer=true;	
	    	}
	    	else{
	    		for(int j = 0;j<obj1.getJSONArray("items").length();j++){
	    			toptagList.add(obj1.getJSONArray("items").getJSONObject(j).getString("tag_name"));
	    		}
	    		while(flag>0){
		    		String url2 = "https://api.stackexchange.com/2.2/questions?order=desc&sort=activity&tagged="+createTagString(toptagList,length)+"&site=stackoverflow";
			    	String jsonString2 = Methods.sendGet(url2);
			    	JSONObject obj2= new JSONObject(jsonString2);
			    	if(obj2.getJSONArray("items").length()==0){
			    		length--;
			    		url2 = "https://api.stackexchange.com/2.2/questions?order=desc&sort=activity&tagged="+createTagString(toptagList,length)+"&site=stackoverflow";
				    	jsonString2 = Methods.sendGet(url2);
				    	obj2 = new JSONObject(jsonString2);
			    	}
			    	else{
			    		for(int n=0;n<obj2.getJSONArray("items").length();n++){
			    			if(obj2.getJSONArray("items").getJSONObject(n).getBoolean("is_answered")==true&&flag>0){
			    				flag--;
			    				System.out.println(createAfficheTag(toptagList,length));
			    				String title = obj2.getJSONArray("items").getJSONObject(n).getString("title");
			    				String link = obj2.getJSONArray("items").getJSONObject(n).getString("link");
			    				System.out.println(StringEscapeUtils.unescapeHtml4(title));
			    				System.out.println(link);
			    			}
			    		}
			    		length--;
			    	}
	    		}
	    	}
		}
    	while(continuer);
	}
	public static void main(String[] args) throws IOException {

		//fourthStory();
		firstStory();
		//thirdStory();

	}

}
