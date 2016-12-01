package TSE.P_INFO.CosmopoliTse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

public class Alice {
	private static Scanner input = new Scanner(System.in);
	
	/***
	 * Première Story d'Alice: Quelles sont les nouvelles questions dans mes compétences ?
	 * @author Bou-Zogheib Diane
	 * @throws IOException
	 */
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
    		String tagUrl = topTag;
    		System.out.println("Les nouvelles questions pour votre compétence '" + topTag + "' sont :");
    		if(topTag.equals("c#"))
    			tagUrl="%23";
    		String url2 = "https://api.stackexchange.com/2.2/questions/no-answers?order=desc&sort=creation&tagged=" + tagUrl + "&site=stackoverflow";
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
	}
	
	/***
	 * Troisième Story d'Alice: Trier les questions auxquelles j’ai répondu en fonction de leur taux de succès
	 * @author Li Shule
	 * @throws IOException
	 */
	public static void thirdStoryAlice() throws IOException{
		
    	boolean continuer=false;
    	do{
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
	    	if(obj.getJSONArray("items").length()==0){
	    		System.out.println("Userid "+ userid +" pas trouver, veuillez refaire cette recherche \n");
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
	
	 public static void menu() throws IOException{
	    	
	    	boolean continuer=false;
	    	Scanner in = new Scanner(System.in);
	    	do
	    	{
	    		System.out.println("Que souhaitez-vous afficher?");
	        	System.out.println("1: Les nouvelles questions dans mes compétences");
	        	System.out.println("2: Les questions auxquelles j’ai répondu, triées en fonction de leur taux de succès");
	        	
	    		int choix=input.nextInt();
	    		String reponse;
	    		
		    	switch (choix)
		    	{
		    	  case 1:
		    		  firstStoryAlice();
		    		  System.out.println();
		    		  do
		    		  {
		    			  System.out.println("Souhaitez-vous effectuer une autre recherche? y/n");
			    		  reponse=in.next();
		    			  if(reponse.equals("y"))
		    				  continuer=true;
		    			  else if (reponse.equals("n"))
		    			  {
		    				  System.out.println("Arrêt de l'application...");
		    				  continuer=false;  
		    			  }
		    			  else
		    				  System.out.println("Choix incorrect.");
		    		  }while(!(reponse.equals("y") || reponse.equals("n")));
		    		  break;  
		    	  case 2:
		    		  thirdStoryAlice();
		    		  System.out.println();
		    		  do
		    		  {
		    			  System.out.println("Souhaitez-vous effectuer une autre recherche? y/n");
			    		  reponse=in.next();
		    			  if(reponse.equals("y"))
		    				  continuer=true;
		    			  else if (reponse.equals("n"))
		    			  {
		    				  System.out.println("Arrêt de l'application...");
		    				  continuer=false;  
		    			  }
		    			  else
		    				  System.out.println("Choix incorrect.");
		    		  }while(!(reponse.equals("y") || reponse.equals("n")));
		    		  break;
		    	  default:
		    		  System.out.println("Erreur de choix, veuillez recommencer.");
		    		  System.out.println();
		    		  continuer=true;
		    		  break;
		    	}
		    	
	    	}while(continuer);
	    	in.close();
	    }
	public static void main(String[] args) throws IOException {
		menu();
	}
}
