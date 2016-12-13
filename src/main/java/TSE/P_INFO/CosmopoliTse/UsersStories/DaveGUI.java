package TSE.P_INFO.CosmopoliTse.UsersStories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

import org.json.JSONObject;

import TSE.P_INFO.CosmopoliTse.UsefulMethods.Methods;

import java.io.IOException;

public class DaveGUI{
	
	/***
	 * Première Story de Dave: Trouver les personnes contribuant le plus à un sujet donné
	 * @author Ricard Anthony
	 * @param tag - le tag sur lequel l'utilisateur souhaite effectuer sa recherche
	 * @param count - le nombre de contributeurs que l'utilisateur souhaite afficher
	 * @param firstStoryAnswerLabel - zone de l'interface graphique assignée à l'affichage du résultat final
	 * @throws IOException
	 * @version GUI
	 ***/
	public static synchronized void firstStoryGUI(String tag, JTextArea answerArea) throws IOException{
		StringBuilder answer = new StringBuilder();
		String url = "https://api.stackexchange.com/2.2/tags/" + tag + "/top-answerers/all_time?page=1&pagesize="+20+"&site=stackoverflow";
		String jsonString = Methods.sendGet(url);
		JSONObject obj= new JSONObject(jsonString);
		if(obj.getJSONArray("items").length()==0){
    		answer.append("Tag '"+tag+"' inexistant, veuillez ré-effectuer la recherche.");
    	}
		else{
			Map<String,Long> contributors = new HashMap<String,Long>();
			List<Long> contributorsPostCount = new ArrayList<Long>();
			for(int i=0;i<20;i++)
			{
				String nom = obj.getJSONArray("items").getJSONObject(i).getJSONObject("user").getString("display_name");
				Long score = obj.getJSONArray("items").getJSONObject(i).getLong("post_count");
				contributorsPostCount.add(score);
				contributors.put(nom, score);
			}
			
			contributorsPostCount.sort(null);
			
			answer.append("Les meilleurs contributeurs (Nom : Score) au sujet " + tag + " sont: " + System.getProperty("line.separator"));
			for(int i=19;i>=0;i--)
			{
				for(Map.Entry<String, Long> cont:contributors.entrySet())
					if(cont.getValue()==contributorsPostCount.get(i))
					{
						answer.append(cont.getKey() + ": " + cont.getValue() + System.getProperty("line.separator"));
					}
			}
		}
		answerArea.setText(answer.toString());
	}
    
    /***
     * Deuxième Story de Dave: Trouver la personne qui a le top tag dans un sujet donné
     * @author Ricard Anthony
     * @param Tag - le tag sur lequel l'utilisateur souhaite effectuer sa recherche
     * @version GUI
     * @param area - zone de l'interface graphique assignée à l'affichage du résultat de la requête
     * @throws IOException
     ***/
    public static synchronized void secondStoryGUI(String Tag,JTextArea area) throws IOException{
    	String url = "https://api.stackexchange.com/2.2/tags/" + Tag + "/top-answerers/all_time?page=1&pagesize=1&site=stackoverflow";
    	String jsonString = Methods.sendGet(url);
    	JSONObject obj= new JSONObject(jsonString);  
    	if(obj.getJSONArray("items").length()==0){
    		area.setText("Tag '"+Tag+"' inexistant, veuillez ré-effectuer la recherche.");
    	}
    	else{
    		String tagTopAnswerer=obj.getJSONArray("items").getJSONObject(0).getJSONObject("user").getString("display_name");
    		area.setText("L'utilisateur ayant le Top Tag dans le sujet " + Tag + " est : " + tagTopAnswerer);
    	}
    }
     
    
    /***
     * Troisième Story de Dave: Trouver la personne qui contribue le plus à un ensemble de tags donné
     * @author Ricard Anthony
     * @param strings - tableau de chaînes de caractère permettant de récupérer les tags saisis par l'utilisateur
     * @param nombre - nombre de tags sur lequel l'utilisateur veut effectuer la requête
     * @param thirdStoryAnswerLabel - zone de l'interface graphique assignée à l'affichage du résultat final
     * @param insertTag - zone de l'interface graphique dans laquelle l'utilisateur saisit les tags
     * @param errorLabel - zone de l'interface graphique assignée à l'affichage de tags erronés le cas échéant
     * @throws IOException
     * @version GUI
     ***/
    public static synchronized StringBuilder thirdStoryGUI(String[] strings, JTextArea answerArea) throws IOException{
    	StringBuilder answer = new StringBuilder();
    	StringBuilder errors = new StringBuilder();
    	List<String> toRemove = new ArrayList<String>();
    	List<String> tagList = new ArrayList<String>();
    	for(String tt:strings)
    	{
    		tagList.add(tt);
    	}
    	Map<String,Long> userScoreMap = new HashMap<String, Long>();
    	for(String tt:tagList){
    		String url = "https://api.stackexchange.com/2.2/tags/" + tt + "/top-answerers/all_time?site=stackoverflow";
        	String jsonString = Methods.sendGet(url);
        	JSONObject obj= new JSONObject(jsonString); 
        	if(obj.getJSONArray("items").length()==0){
        		errors.append("Tag '"+ tt +"' inexistant, non pris en compte. \n");
        		toRemove.add(tt);
        	}
        	else{
	        	for(int j=0;j<20;j++){
	        		int flag=0;
	        		String nom = obj.getJSONArray("items").getJSONObject(j).getJSONObject("user").getString("display_name");
	        		Long post= obj.getJSONArray("items").getJSONObject(j).getLong("post_count");
	        		for (Map.Entry<String, Long> entry : userScoreMap.entrySet()){
	        			if(entry.getKey().equals(nom)){
	        				entry.setValue(post+entry.getValue());
	        				flag=1;
	        			}break;
	        		}
	        		if(flag==0){
	        			userScoreMap.put(nom,post);
	        		}
	        	}
        	}
    	}
    	Long max =(long) 0; 
    	for (Long post : userScoreMap.values()) {  
    		  if(max <= post){
    			  max = post;
    		  }
    	}  
    	for(String str:toRemove)
    	{
    		if(tagList.contains(str))
    			tagList.remove(str);
    	}
    	for (Map.Entry<String, Long> entry1 : userScoreMap.entrySet()){
    		if(entry1.getValue()==max){
    			answer.append("La personne qui contribue le plus sur les tags: ");
    			for(String ttt:tagList){
    				answer.append("("+ttt+")");
    			}
    			answer.append(" est "+ entry1.getKey());
    		}
    	}
    	
    	answerArea.setText(answer.toString());
    	
    	return errors;
    }
    
 }