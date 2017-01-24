package TSE.P_INFO.CosmopoliTse.UsersStories;

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
import TSE.P_INFO.CosmopoliTse.UsefulMethods.User;

public class BobTerminal extends User {
	
	BobTerminal(){
		setUsername("Bob");
	}
	
	/***
	 * Première Story de Bob: M’aider à trouver des question existantes qui correspondent à mon besoin.
	 * @author Bou-Zogheib Diane
	 * @throws IOException
	 ***/
	@Override
	public void firstStory() throws IOException
	{
		System.out.println("Veuillez saisir une question : ");
		input = new Scanner(System.in);
		String question = input.nextLine();
		String url = "https://api.stackexchange.com/2.2/search/advanced?order=desc&sort=relevance&q=" + URLEncoder.encode(question,"utf-8") + "&site=stackoverflow&key=TWJoclGjmJo5yUlPKN4TbQ((";
		JSONObject obj = Methods.generateJSONObject(url);
		String relatedQuestions;
		try
    	{
        	relatedQuestions = obj.getJSONArray("items").getJSONObject(0).getString("title");
    	}
		catch(JSONException j)
    	{
    		System.out.println("Aucune correspondance trouvée, veuillez resaisir une question");
    		question = input.nextLine();
    		url = "https://api.stackexchange.com/2.2/search/advanced?order=desc&sort=relevance&q=" + URLEncoder.encode(question,"utf-8") + "&site=stackoverflow&key=TWJoclGjmJo5yUlPKN4TbQ((";
    		obj = Methods.generateJSONObject(url);
    		relatedQuestions = obj.getJSONArray("items").getJSONObject(0).getString("title");
    	}
    	
    	url = "https://api.stackexchange.com/2.2/search/advanced?order=desc&sort=relevance&q=" + URLEncoder.encode(question,"utf-8") + "&site=stackoverflow&key=TWJoclGjmJo5yUlPKN4TbQ((";
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
	
	
	/***
	 * Deuxième Story de Bob: Me suggérer des mots clés à rajouter.
	 * @author Leang Sébastien
	 * @throws IOException
	 ***/
	@Override
	public void secondStory() throws IOException
	{
		System.out.println("Veuillez saisir votre recherche : ");
		try{
			input = new Scanner(System.in);
			String question = input.nextLine();
			System.out.println(Methods.Comparaison(Methods.RecupérationMotQuestion(question)));
		}
		 catch (JSONException j) {
			System.out.println("Erreur");
		}
	}
	/***
	 * Troisième Story de Bob: Me proposer des contributeurs à suivre.
	 * @author Li Shule
	 * @throws IOException
	 ***/
	@Override
	public void thirdStory() throws IOException{
		boolean continuer=false;
		do{
			System.out.println("Veuillez saisir un userid : ");
			input = new Scanner (System.in);
			int userid = input.nextInt();
			List<String> toptagList = new ArrayList<String>();
			Set<String> noms = new HashSet<String>();
			String url0 = Methods.generateTopTagsRequest(userid);
	    	JSONObject obj0= Methods.generateJSONObject(url0);
	    	if(obj0.getJSONArray("items").length()==0){
	    		System.out.println("Userid "+ userid +" pas trouver, veuillez refaire cette recherche ");
	    		userid = input.nextInt();
	    		url0 = Methods.generateTopTagsRequest(userid);
	        	obj0 = Methods.generateJSONObject(url0);
	    		continuer=true;
	    	}
	    	else{
	    		for(int j = 0;j<obj0.getJSONArray("items").length();j++){
	    			toptagList.add(obj0.getJSONArray("items").getJSONObject(j).getString("tag_name"));
	    		}
	    		for(String ss: toptagList){
	    			for(int i=0;i<3;i++){
	    				String url01 = Methods.generateTagRequest(ss);
	    		    	JSONObject obj01 = Methods.generateJSONObject(url01);
	    		    	noms.add(obj01.getJSONArray("items").getJSONObject(i).getJSONObject("user").getString("display_name"));
	    			}
	    		}
	    		System.out.println(noms);
	    	}
		}	
		while(continuer);
	}
	
	/***
	 * Quatrième Story de Bob :M’indiquer les nouvelles questions (avec déjà des réponses) dans mes champs d’intérêt.
	 * @author Li Shule
	 * @throws IOException
	 ***/
	public void fourthStory() throws IOException
	{
		boolean continuer=false;
    	do{
			System.out.println("Veuillez saisir un userid : ");
			input = new Scanner (System.in);
			int userid = input.nextInt();
			int flag = 10;
			int length = 6;
			List<String> toptagList = new ArrayList<String>();
			
	    	String url1 = Methods.generateTopTagsRequest(userid);
	    	String jsonString1 = Methods.sendGet(url1);
	    	JSONObject obj1= new JSONObject(jsonString1);
	    	System.out.println("Les les nouvelles 10 question: ");
	    	if(obj1.getJSONArray("items").length()==0){
	    		System.out.println("Userid "+ userid +" pas trouver, veuillez refaire cette recherche ");
	    		userid = input.nextInt();
	    		url1 = Methods.generateTopTagsRequest(userid);
	        	obj1 = Methods.generateJSONObject(url1);
	    		continuer=true;	
	    	}
	    	else{
	    		for(int j = 0;j<obj1.getJSONArray("items").length();j++){
	    			toptagList.add(obj1.getJSONArray("items").getJSONObject(j).getString("tag_name"));
	    		}
	    		while(flag>0){
		    		String url2 = "https://api.stackexchange.com/2.2/questions?order=desc&sort=activity&tagged="+ Methods.createTagString(toptagList,length)+"&site=stackoverflow&key=TWJoclGjmJo5yUlPKN4TbQ((";
			    	JSONObject obj2 = Methods.generateJSONObject(url2);
			    	if(obj2.getJSONArray("items").length()==0){
			    		length--;
			    		url2 = "https://api.stackexchange.com/2.2/questions?order=desc&sort=activity&tagged="+ Methods.createTagString(toptagList,length)+"&site=stackoverflow&key=TWJoclGjmJo5yUlPKN4TbQ((";
				    	obj2 = Methods.generateJSONObject(url2);
			    	}
			    	else{
			    		for(int n=0;n<obj2.getJSONArray("items").length();n++){
			    			if(obj2.getJSONArray("items").getJSONObject(n).getBoolean("is_answered")==true&&flag>0){
			    				flag--;
			    				System.out.println(Methods.afficheTag(toptagList,length));
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

		menu(new BobTerminal());

	}
	
	@Override
	public void setUsername(String name) {
		username = name;
		
	}

}
