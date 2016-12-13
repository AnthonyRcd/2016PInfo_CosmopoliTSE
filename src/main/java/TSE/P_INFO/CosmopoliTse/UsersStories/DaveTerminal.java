package TSE.P_INFO.CosmopoliTse.UsersStories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;
import TSE.P_INFO.CosmopoliTse.UsefulMethods.User;
import TSE.P_INFO.CosmopoliTse.UsefulMethods.Methods;

public class DaveTerminal extends User {
	
	DaveTerminal(){
		setUsername("Dave");
	}
	/***
	 * Première Story de Dave: Trouver les personnes contribuant le plus à un sujet donné
	 * @author Li Shule, Leang Sebastien
	 * @throws IOException
	 ***/
	@Override
	public void firstStory() throws IOException{
		input = new Scanner(System.in);
		System.out.println("Sur quel sujet voulez-vous effectuer la recherche?");
		String tag = input.nextLine();
		System.out.println("Combien de contributeurs voulez-vous afficher? (20 max)");
		int count=input.nextInt();
		String url = "https://api.stackexchange.com/2.2/tags/" + tag + "/top-answerers/all_time?page=1&pagesize="+20+"&site=stackoverflow";
		String jsonString = Methods.sendGet(url);
		JSONObject obj= new JSONObject(jsonString);
		if(obj.getJSONArray("items").length()==0){
    		System.out.println("Tag "+tag+" pas trouver, veuillez refaire cette recherche");
    	}
		else{
			Map<String,Long> contributors = new HashMap<String,Long>();
			List<Long> contributorsPostCount = new ArrayList<Long>();
			
			Methods.fillContibutorsList(obj, contributors, contributorsPostCount);
			
			contributorsPostCount.sort(null);
		
			System.out.println("Les meilleurs contributeurs (Nom : Score) au sujet " + tag + " sont: ");
			Methods.printContributors(count, contributors, contributorsPostCount);
		}

	}
	
	/***
	 * Deuxième Story de Dave: Trouver la personne qui a le top tag dans un sujet donné
	 * @author Ricard Anthony, Bou-Zogheib Diane
	 * @version Console
	 * @throws IOException
	 ***/
	 @Override
    public void secondStory() throws IOException{
    	System.out.println("Pour quel sujet souhaitez-vous effectuer la recherche?");
    	String Tag = input.next();
    	String url = "https://api.stackexchange.com/2.2/tags/" + Tag + "/top-answerers/all_time?page=1&pagesize=1&site=stackoverflow";
    	String jsonString = Methods.sendGet(url);
    	JSONObject obj= new JSONObject(jsonString);  
    	if(obj.getJSONArray("items").length()==0){
    		System.out.println("Tag "+Tag+" pas trouver, veuillez refaire cette recherche");
    	}
    	else{
    		String tagTopAnswerer= Methods.getUserName(obj,0);
    		System.out.println("L'utilisateur ayant le Top Tag dans le sujet " + Tag + " est : " + tagTopAnswerer);
    	}
    }
    
    
    /***
     * Troisième Story de Dave: Trouver la personne qui contribue le plus à un ensemble de tags donné
     * @author Li Shule
     * @throws IOException
     * @version Console
    ***/
	 @Override
   public void thirdStory() throws IOException{
   	Scanner in = new Scanner(System.in);
   	Scanner in1 = new Scanner(System.in);
   	System.out.println("Commbien de tag vous voulez inserer?");
   	int nombre = in.nextInt();
   	List<String> tagList = new ArrayList<String>();
   	Map<String,Long> userScoreMap = new HashMap<String, Long>();
   	
   	for(int i=0;i<nombre;i++){
   		System.out.println("Inserer le tag "+(i+1));
   		String tags = in1.nextLine();
   		tagList.add(tags);
   	}
   	
   	for(String tt:tagList){
   		String url = "https://api.stackexchange.com/2.2/tags/" + tt + "/top-answerers/all_time?site=stackoverflow";
       	String jsonString = Methods.sendGet(url);
       	JSONObject obj= new JSONObject(jsonString); 
       	if(obj.getJSONArray("items").length()==0)
       	{
       		System.out.println("Tag "+tt+" pas trouver, veuillez refaire cette recherche");
       		tagList.remove(tt);
       	}
       	else
       	{
	    	for(int j=0;j<20;j++){
	    		int flag=0;
	    		String nom = Methods.getUserName(obj, j);
	    		Long post= obj.getJSONArray("items").getJSONObject(j).getLong("post_count");
	    		for (Map.Entry<String, Long> entry : userScoreMap.entrySet())
	    		{
	    			if(entry.getKey().equals(nom))
	    			{
	    				entry.setValue(post+entry.getValue());
	    				flag=1;
	    			}
	    			break;
	    		}
	    		if(flag==0)
	    			userScoreMap.put(nom,post);
	    	}
       	}
   	}
   	Long max =(long) 0; 
   	for (Long post : userScoreMap.values()) {  
   		  if(max <= post){
   			  max = post;
   		  }
   	}  
   	for (Map.Entry<String, Long> entry1 : userScoreMap.entrySet()){
	   		if(entry1.getValue()==max){
	   			System.out.print("Le personne qui contribue le plus sur les tags: ");
	   			for(String ttt:tagList){
	   				System.out.print("("+ttt+")");
	   			}
	   			System.out.print(" est "+ entry1.getKey());
	   		}
	   	}
   	in.close();
   	in1.close();
   }
	
   public static void main(String[] args) throws IOException {
	   menu(new DaveTerminal());
   }
   
	@Override
	public void setUsername(String name) {
		this.username=name;
	}


}
