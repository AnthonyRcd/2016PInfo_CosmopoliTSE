package TSE.P_INFO.CosmopoliTse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

public class Alice {
	private static Scanner input;
	
	public static void firstStoryAlice() throws IOException
	{
		System.out.println("Trouver les nouvelles questions dans mes compétences :");
		System.out.println("Veuillez saisir votre nom : ");
		input = new Scanner (System.in);
		String inname = input.nextLine();
		inname = inname.replaceAll("\\s","%20");
		String url = "https://api.stackexchange.com/2.2/users?order=desc&sort=reputation&inname=" + inname +"&site=stackoverflow";
    	String jsonString = DaveHttp.sendGet(url);
    	JSONObject obj= new JSONObject(jsonString);
    	int userid = 0;
    	try
    	{
        	userid = obj.getJSONArray("items").getJSONObject(0).getInt("user_id");
        	
    	}
    	catch(JSONException j)
    	{
    		System.out.println("Ce nom n'existe pas veuillez resaisir votre nom");
    		input = new Scanner (System.in);
    		inname = input.nextLine();
    		inname = inname.replaceAll("\\s","%20");
    		url = "https://api.stackexchange.com/2.2/users?order=desc&sort=reputation&inname=" + inname +"&site=stackoverflow";
        	jsonString = DaveHttp.sendGet(url);
        	obj= new JSONObject(jsonString);  
        	userid = obj.getJSONArray("items").getJSONObject(0).getInt("user_id");
    	}
    	System.out.println(userid);
    	String url1 = "https://api.stackexchange.com/2.2/users/"+ userid +"/top-tags?site=stackoverflow";
    	String jsonString1 = DaveHttp.sendGet(url1);
    	JSONObject obj1= new JSONObject(jsonString1);
    	
    	for (int i = 0; i<6;i++)
    	{
    		String topTag = obj1.getJSONArray("items").getJSONObject(i).getString("tag_name");
    		System.out.println("Les nouvelles questions pour votre compétence " + topTag + " sont :");
    		String url2 = "https://api.stackexchange.com/2.2/questions/no-answers?order=desc&sort=creation&tagged=" + topTag + "&site=stackoverflow";
    		String jsonString2 = DaveHttp.sendGet(url2);
        	JSONObject obj2= new JSONObject(jsonString2);
        	
        	for (int k = 0; k < 10; k++)
        	{
        		String titreQuestions = obj2.getJSONArray("items").getJSONObject(k).getString("title");
        		String lienQuestions = obj2.getJSONArray("items").getJSONObject(k).getString("link");
        		System.out.println( (k+1) + "." + titreQuestions);
        		System.out.println(lienQuestions);
        	}
        	System.out.println("");
    	}
    	/*do 
    	{
    		if(obj.getJSONArray("items").length()==0)
    		{
	    		System.out.println("Nom "+ inname +" non trouvé, veuillez resaisir votre nom");
	    		inname = input.nextLine();
	    		page=1;
	    		url = "/2.2/users?order=desc&sort=name&inname=" + inname +"&site=stackoverflow";
	        	jsonString = DaveHttp.sendGet(url);
	        	obj = new JSONObject(jsonString);
	    		continuer=true;
    		}
    		else 
    		{
    			
    			
    		}
	    
    	}*/
    	
    	
		
	}
	public static void thirdStoryAlice() throws IOException{
		System.out.println("Trier les questions auxquelles j’ai répondu en fonction de leur taux de succès :");
		System.out.println("Veuiller saisir le user id que vous voulez chercher: ");
		input = new Scanner (System.in);
		int userid = input.nextInt();
		int page = 1;
		List<Integer> questionidList = new ArrayList<Integer>();
		List<String> questionnameList = new ArrayList<String>();
		String url = "https://api.stackexchange.com/2.2/users/" + userid + "/answers?page="+ page +"&pagesize=10&order=desc&sort=votes&site=stackoverflow";
    	String jsonString = DaveHttp.sendGet(url);
    	JSONObject obj= new JSONObject(jsonString);  
    	boolean continuer=false;
    	do{
	    	if(obj.getJSONArray("items").length()==0){
	    		System.out.println("Userid "+ userid +" pas trouver, veuillez refaire cette recherche");
	    		userid = input.nextInt();
	    		page=1;
	    		url = "https://api.stackexchange.com/2.2/users/" + userid + "/answers?page="+ page +"&pagesize=10&order=desc&sort=votes&site=stackoverflow";
	        	jsonString = DaveHttp.sendGet(url);
	        	obj = new JSONObject(jsonString);
	    		continuer=true;
	    	}
	    	else{
	    		for(int j = 0;j<obj.getJSONArray("items").length();j++){
	    			questionidList.add(obj.getJSONArray("items").getJSONObject(j).getInt("question_id"));
	    		}
		    	boolean has_more = obj.getBoolean("has_more");
		    	if(has_more){
		    		page++;
		    		String url1 = "https://api.stackexchange.com/2.2/users/" + userid + "/answers?page="+ page +"&pagesize=10&order=desc&sort=votes&site=stackoverflow";
		        	String jsonString1 = DaveHttp.sendGet(url1);
		        	JSONObject obj1 = new JSONObject(jsonString1);  
		        	int pageSize = obj1.getJSONArray("items").length();
		        	for(int i = 0 ; i<pageSize ; i++){
		        		questionidList.add(obj1.getJSONArray("items").getJSONObject(i).getInt("question_id"));
		        	}
		    	}
		    	
		    	for(int questionid:questionidList){
		    		String url2 = "https://api.stackexchange.com/2.2/questions/"+ questionid +"?order=desc&sort=votes&site=stackoverflow";
		        	String jsonString2 = DaveHttp.sendGet(url2);
		        	JSONObject obj2= new JSONObject(jsonString2);  
		        	questionnameList.add(obj2.getJSONArray("items").getJSONObject(0).getString("title"));
		    	}
		    	System.out.println("Les Top 20 Question:");
		    	for(String questionname: questionnameList){
		    		System.out.println(questionname);
		    	}
	    	}
	    	
    	}while(continuer);
	}
	public static void main(String[] args) throws IOException {
		firstStoryAlice();
	}
}
