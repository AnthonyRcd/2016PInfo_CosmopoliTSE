package TSE.P_INFO.CosmopoliTse.UsersStories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import org.json.JSONException;
import org.json.JSONObject;

import TSE.P_INFO.CosmopoliTse.UsefulMethods.Methods;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class DaveGUI{
	
	/***
	 * Première Story de Dave: Trouver les personnes contribuant le plus à un sujet donné
	 * @author Li SHule, Leang Sebastien, Ricard Anthony
	 * @param tag - le tag sur lequel l'utilisateur souhaite effectuer sa recherche
	 * @param count - le nombre de contributeurs que l'utilisateur souhaite afficher
	 * @param firstStoryAnswerLabel - zone de l'interface graphique assignée à l'affichage du résultat final
	 * @throws IOException
	 * @version GUI
	 ***/
	public static synchronized void firstStoryGUI(String tag, JTextPane answerArea, JTextArea errorArea){
		StringBuilder answer = new StringBuilder();
		String url = Methods.generateTagRequest(tag);
		try{
			String jsonString = Methods.sendGet(url);
			JSONObject obj = new JSONObject(jsonString);
			Map<String,Long> contributors = new HashMap<String,Long>();
			List<Long> contributorsPostCount = new ArrayList<Long>();
			Methods.fillContibutorsList(obj, contributors, contributorsPostCount);
			
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
			answerArea.setText(answer.toString());
		}catch(Exception e){
			errorArea.setVisible(true);
			errorArea.setText("Veuillez saisir un tag correct");
			if(e.getClass().equals(JSONException.class))
				System.err.println(e);
		}
		
	}
    
	
    /***
     * Deuxième Story de Dave: Trouver la personne qui a le top tag dans un sujet donné
     * @author Ricard Anthony, Bou-Zogheib Diane
     * @param Tag - le tag sur lequel l'utilisateur souhaite effectuer sa recherche
     * @version GUI
     * @param answerArea - zone de l'interface graphique assignée à l'affichage du résultat de la requête
     * @throws IOException
     ***/
    public static synchronized void secondStoryGUI(String Tag,JTextPane answerArea, JTextArea errorArea){
    	String url = Methods.generateTagRequest(Tag);
    	try{
    	String jsonString = Methods.sendGet(url);
    	JSONObject obj= new JSONObject(jsonString);  
		String tagTopAnswerer=Methods.getUserProperty(obj, 0,"display_name");
    	answerArea.setText("L'utilisateur ayant le Top Tag dans le sujet " + Tag + " est : " + tagTopAnswerer);
    	}catch(Exception e){
    		if(!e.getClass().equals(IOException.class))
    			System.err.println(e);
    		errorArea.setVisible(true);
    		errorArea.setText("Veuillez saisir un tag correct");
    	}
    }
     
    
    /***
     * Troisième Story de Dave: Trouver la personne qui contribue le plus à un ensemble de tags donné
     * @author Li Shule, Ricard Anthony
     * @param strings - tableau de chaînes de caractère permettant de récupérer les tags saisis par l'utilisateur
     * @param nombre - nombre de tags sur lequel l'utilisateur veut effectuer la requête
     * @param thirdStoryAnswerLabel - zone de l'interface graphique assignée à l'affichage du résultat final
     * @param insertTag - zone de l'interface graphique dans laquelle l'utilisateur saisit les tags
     * @param errorLabel - zone de l'interface graphique assignée à l'affichage de tags erronés le cas échéant
     * @throws IOException
     * @version GUI
     ***/
    public static synchronized void thirdStoryGUI(String[] strings, JTextPane answerArea, JTextArea errorArea){
    	StringBuilder answer = new StringBuilder();
    	List<String> toRemove = new ArrayList<String>();
    	List<String> tagList = new ArrayList<String>();
    	String jsonString = "";
    	JSONObject obj;
    	String url = "";
    	
    	for(String tt:strings)
    	{
    		tagList.add(tt);
    	}
    	
    	Map<String,Long> userScoreMap = new HashMap<String, Long>();
    	
    	for(String tt:tagList)
    	{
    		url = Methods.generateTagRequest(tt);
    		
			try {
				jsonString = Methods.sendGet(url);
				if(tt.contains("Story1"))
					throw new Exception("Error: no tag was entered by the user.");
			} catch (Exception e) {
				errorArea.setVisible(true);
				errorArea.setText("Veuillez saisir un tag correct");
				if (!e.getClass().equals(IOException.class)) System.err.println(e);
				return;
			}
			
			obj = new JSONObject(jsonString);
        	if(obj.getJSONArray("items").length()==0)
        	{
        		errorArea.setVisible(true);
        		errorArea.insert("Tag '"+ tt +"' inexistant, non pris en compte. \n", errorArea.getCaretPosition());
        		toRemove.add(tt);
        	}
        	else
	        	Methods.fillUserScoreMap(obj, userScoreMap);
    	}
    		
    	Long max = Methods.getMax(userScoreMap.values()); 
    	
    	for(String str:toRemove)
    	{
    		if(tagList.contains(str))
    			tagList.remove(str);
    	}
    	
    	for (Map.Entry<String, Long> entry : userScoreMap.entrySet())
    		if(entry.getValue() == max)
    		{
    			answer.append("La personne qui contribue le plus sur le(s) tag(s) " + tagList.toString() + " est " + entry.getKey());
    			break;
    		}
    			
    	answerArea.setText(answer.toString());
    }
    
    /*------Version JList------*/
    
    //TODO Javadoc
    public static synchronized Vector<URI> firstStoryGUI(String tag, JList<String> answerJList, JTextArea errorArea){
		Vector<String> answerList = new Vector<String>();
		Vector<URI> links = new Vector<URI>();
		String url = Methods.generateTagRequest(tag);
		try{
			String jsonString = Methods.sendGet(url);
			JSONObject obj = new JSONObject(jsonString);
			Map<String,Long> contributors = new HashMap<String,Long>();
			Map<String,URI> linkMap = new HashMap<String,URI>();
			List<Long> contributorsPostCount = new ArrayList<Long>();
			Methods.fillContibutorsList(obj, contributors, contributorsPostCount);
			
			contributorsPostCount.sort(null);
			
			Methods.fillLinkMap(obj, linkMap);
			
			answerList.add("Les meilleurs contributeurs (Nom : Score) au sujet " + tag + " sont: " + System.getProperty("line.separator"));
			
			for(int i=19;i>=0;i--)
			{
				for(Map.Entry<String, Long> cont:contributors.entrySet())
					if(cont.getValue()==contributorsPostCount.get(i))
					{
						answerList.add(cont.getKey() + ": " + cont.getValue() + System.getProperty("line.separator"));
						links.add(linkMap.get(cont.getKey()));
					}
			}
		}catch(Exception e){
			errorArea.setVisible(true);
			errorArea.setText("Veuillez saisir un tag correct");
			System.err.println(e);
		}
		
		answerJList.setListData(answerList); answerJList.setLayoutOrientation(JList.VERTICAL);
		return links;
	}
    
    
    //TODO Javadoc
    public static synchronized URI secondStoryGUI(String Tag, JList<String> answerList, JTextArea errorArea) throws URISyntaxException{
    	String[] answers = new String[2];
    	String url = Methods.generateTagRequest(Tag);
    	URI link = new URI("http;//www.stackoverflow.com");
    	try{
    	String jsonString = Methods.sendGet(url);
    	JSONObject obj= new JSONObject(jsonString);  
		String tagTopAnswerer=Methods.getUserProperty(obj, 0,"display_name");
		link = new URI(Methods.getUserProperty(obj, 0, "link"));
    	answers[0]= "L'utilisateur ayant le Top Tag dans le sujet " + Tag + " est : ";
    	answers[1] = tagTopAnswerer;
    	answerList.setListData(answers); answerList.setVisibleRowCount(1); answerList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
    	}catch(Exception e){
   			System.err.println(e);
    		errorArea.setVisible(true);
    		errorArea.setText("Veuillez saisir un tag correct");
    	}
    	return link;
    }
    
    
    //TODO Javadoc
    public static synchronized URI thirdStoryGUI(String[] strings, JList<String> answerJList, JTextArea errorArea) throws URISyntaxException{
    	String[] answer = new String[2];
    	URI link = new URI("http;//www.stackoverflow.com");
    	List<String> toRemove = new ArrayList<String>();
    	List<String> tagList = new ArrayList<String>();
    	String jsonString = "";
    	JSONObject obj;
    	String url = "";
    	
    	for(String tt:strings)
    	{
    		tagList.add(tt);
    	}
    	
    	Map<String,Long> userScoreMap = new HashMap<String, Long>();
    	Map<String,URI> linkMap = new HashMap<String,URI>();
    	for(String tt:tagList)
    	{
    		url = Methods.generateTagRequest(tt);
    		
			try {
				jsonString = Methods.sendGet(url);
				if(tt.contains("Story1"))
					throw new Exception("Error: no tag was entered by the user.");
			} catch (Exception e) {
				errorArea.setVisible(true);
				errorArea.setText("Veuillez saisir un tag correct");
				System.err.println(e);
				return null;
			}
			
			obj = new JSONObject(jsonString);
        	if(obj.getJSONArray("items").length()==0)
        	{
        		errorArea.setVisible(true);
        		errorArea.insert("Tag '"+ tt +"' inexistant, non pris en compte. \n", errorArea.getCaretPosition());
        		toRemove.add(tt);
        	}
        	else
        	{
	        	Methods.fillUserScoreMap(obj, userScoreMap);
	        	for(int i=0;i<20;i++)
	        	{
	        		String nom = Methods.getUserProperty(obj, i,"display_name");
	        		String slink = Methods.getUserProperty(obj, i, "link");
	        		if(linkMap.containsKey(nom))
	        			continue;
	        		else
	        			linkMap.put(nom, new URI(slink));
	        	}
        	}
    	}
    		
    	Long max = Methods.getMax(userScoreMap.values()); 
    	
    	for(String str:toRemove)
    	{
    		if(tagList.contains(str))
    			tagList.remove(str);
    	}
    	
    	for (Map.Entry<String, Long> entry : userScoreMap.entrySet())
    		if(entry.getValue() == max)
    		{
    			answer[0] = "La personne qui contribue le plus sur le(s) tag(s) " + tagList.toString() + " est ";
    			answer[1] = entry.getKey();
    			link = linkMap.get(entry.getKey());
    			break;
    		}
    	answerJList.setListData(answer);answerJList.setVisibleRowCount(1); answerJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
    	return link;
    	
    }
    
    
    
    
    
 }