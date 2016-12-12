package TSE.P_INFO.CosmopoliTse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Alice {
	private static Scanner input = new Scanner(System.in);
	
	/***
	 * Première Story d'Alice: Quelles sont les nouvelles questions dans mes compétences ?
	 * @author Bou-Zogheib Diane
	 * @throws IOException
	 */
	public static void firstStoryAlice() throws JSONException, IOException
	{
		System.out.println("Trouver les nouvelles questions dans mes compétences :");
		System.out.println("Veuillez saisir votre identifiant : ");
		input = new Scanner (System.in);
		int userid = input.nextInt();
		if (userid == 0) return;
		String url = "https://api.stackexchange.com//2.2/users/" + userid +"/top-tags?site=stackoverflow";
		String jsonString = DaveHttp.sendGet(url);
		JSONObject obj= new JSONObject(jsonString);
		JSONArray tab_tags = obj.getJSONArray("items");
    	
    	try
    	{
        	ArrayList<String> tags = new ArrayList<String>();
			for (int k = 0; k<6; k++)
			{
				tags.add(tab_tags.getJSONObject(k).getString("tag_name"));
        	
			}
			String tag = new String();
			int cpt = 0;
			tag = tags.get(0) + ";" + tags.get(1) + ";" + tags.get(2) + ";" + tags.get(3) + ";" + tags.get(4) + ";" + tags.get(5);
			int indice = 5;
			while (cpt < 10){
				String url1 = "https://api.stackexchange.com/2.2/questions/no-answers?pagesize=100&order=desc&sort=creation&tagged="+tag+"&site=stackoverflow";
				String jsonString1 = DaveHttp.sendGet(url1);
				JSONObject obj1= new JSONObject(jsonString1);
				JSONArray array = obj1.getJSONArray("items");
				if (array.length()<10){
					int i = 0;
					while(i<array.length() && cpt<10){
						String titre = array.getJSONObject(i).getString("title");
						String link = array.getJSONObject(i).getString("link");
						System.out.println((cpt+1) + " - " + titre);
						System.out.println("lien: " + link);
						i++;
						cpt++;
					}
					tag = tag.replace(";"+tags.get(indice), "");
					indice--;
				}else{
					int i = 0;
					while(cpt<10){
						String titre = array.getJSONObject(i).getString("title");
						String link = array.getJSONObject(i).getString("link");
						System.out.println((cpt+1) + " - " + titre);
						System.out.println("lien: " + link);
						i++;
						cpt++;
					}
				}
			}
    	}
    	catch(JSONException j)
    	{
    		System.out.println("Aucun tag n'a été touvé pour cet utilisateur");
  
    	}
    	System.out.println(userid);
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
    		List<Integer> tauxdesuccesList = new ArrayList<Integer>();
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
	    			tauxdesuccesList.add(obj.getJSONArray("items").getJSONObject(j).getInt("score"));
	    		}
		    	for(int questionid:questionidList){
		    		String url2 = "https://api.stackexchange.com/2.2/questions/"+ questionid +"?order=desc&sort=votes&site=stackoverflow";
		        	String jsonString2 = DaveHttp.sendGet(url2);
		        	JSONObject obj2= new JSONObject(jsonString2);  
		        	questionnameList.add(obj2.getJSONArray("items").getJSONObject(0).getString("title"));
		    	}
		    	System.out.println("Les Top 10 Question:");
		    	for(int m=0;m<questionidList.size();m++){
		    		System.out.println(questionnameList.get(m));
		    		System.out.println(tauxdesuccesList.get(m));
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
