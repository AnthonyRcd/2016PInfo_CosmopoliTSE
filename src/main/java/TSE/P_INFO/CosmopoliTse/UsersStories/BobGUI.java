package TSE.P_INFO.CosmopoliTse.UsersStories;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JTextArea;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import TSE.P_INFO.CosmopoliTse.UsefulMethods.Methods;
import TSE.P_INFO.CosmopoliTse.UsefulMethods.Methods.BadIdException;

public class BobGUI {

	public static Vector<URI> firstStoryGUI(String question, JList<String> answerJList, JTextArea errorArea) throws IOException, JSONException, URISyntaxException{
		Vector<String> answerList = new Vector<String>();
		Vector<URI> links = new Vector<URI>();
		
		String url = "https://api.stackexchange.com/2.2/search/advanced?order=desc&sort=relevance&q=" + URLEncoder.encode(question,"utf-8") + "&site=stackoverflow&key=TWJoclGjmJo5yUlPKN4TbQ((";
		JSONObject obj = Methods.generateJSONObject(url);
		answerList.add("Les questions correspondant à votre besoin sont :");
		for (int k = 0; k < 10; k++)
    	{
    		answerList.add(StringEscapeUtils.unescapeHtml4(obj.getJSONArray("items").getJSONObject(k).getString("title")));
    		links.add(new URI(obj.getJSONArray("items").getJSONObject(k).getString("link")));
    	}
		answerJList.setListData(answerList); answerJList.setLayoutOrientation(JList.VERTICAL);
		return links;
	}
	
	public static boolean secondStoryGUI(String question, JList<String> answerJList, JTextArea errorArea) throws JSONException, IOException{
		Vector<String> answerList = new Vector<String>();
		ArrayList<String> list = Methods.Comparaison(Methods.RecuperationMotQuestion(question));
		if(!list.isEmpty())
		{
			answerList.add("Voici une suggestion de tags à ajouter à votre question :");
			answerList.addAll(list);
			answerJList.setListData(answerList); answerJList.setLayoutOrientation(JList.VERTICAL);
			return true;
		}
		return false;
		
	}
	
	public static Vector<URI> thirdStoryGUI(int userId, JList<String> answerJList, JTextArea errorArea) throws JSONException, URISyntaxException, BadIdException{
		Vector<String> answerList = new Vector<String>();
		Vector<URI> links = new Vector<URI>();
		List<String> toptagList = new ArrayList<String>();
		
		String url0 = Methods.generateTopTagsRequest(userId);
    	JSONObject obj0= Methods.generateJSONObject(url0);
    	if(obj0.getJSONArray("items").length()==0)
    		throw new Methods.BadIdException();
    	
    	for(int j = 0;j<obj0.getJSONArray("items").length();j++){
			toptagList.add(obj0.getJSONArray("items").getJSONObject(j).getString("tag_name"));
		}
    	
    	answerList.add("Voici une suggestion de contributeurs à suivre :");
    	for(String ss: toptagList){
			for(int i=0;i<3;i++){
				String url01 = Methods.generateTagRequest(ss);
		    	JSONObject obj01 = Methods.generateJSONObject(url01);
		    	answerList.add(obj01.getJSONArray("items").getJSONObject(i).getJSONObject("user").getString("display_name"));
		    	links.add(new URI(obj01.getJSONArray("items").getJSONObject(i).getJSONObject("user").getString("link")));
			}
		}
    	answerJList.setListData(answerList); answerJList.setLayoutOrientation(JList.VERTICAL);
    	return links;
	}
	
	public static Vector<URI> fourthStoryGUI(int userId, JList<String> answerJList, JTextArea errorArea) throws BadIdException, UnsupportedEncodingException, JSONException, URISyntaxException{
		Vector<String> answerList = new Vector<String>();
		Vector<URI> links = new Vector<URI>();
		List<String> toptagList = new ArrayList<String>();
		
		int flag = 10;
		int length = 6;
		
		String url1 = Methods.generateTopTagsRequest(userId);
		JSONObject obj1= Methods.generateJSONObject(url1);
		if(obj1.getJSONArray("items").length()==0)
    		throw new Methods.BadIdException();
		
		for(int j = 0;j<obj1.getJSONArray("items").length();j++)
			toptagList.add(obj1.getJSONArray("items").getJSONObject(j).getString("tag_name"));
		
		answerList.addElement("Voici 10 questions déjà répondues dans vos centres d'intérêt " + toptagList.toString() + ":");
		while(flag>0){
    		String url2 = "https://api.stackexchange.com/2.2/questions?order=desc&sort=activity&tagged="+ Methods.createTagString(toptagList,length)+"&site=stackoverflow&key=TWJoclGjmJo5yUlPKN4TbQ((";
	    	JSONObject obj2 = Methods.generateJSONObject(url2);
	    	if(obj2.getJSONArray("items").length()==0){
	    		length--;
	    		url2 = "https://api.stackexchange.com/2.2/questions?order=desc&sort=activity&tagged="+ Methods.createTagString(toptagList,length)+"&site=stackoverflow&key=TWJoclGjmJo5yUlPKN4TbQ((";
		    	obj2 = Methods.generateJSONObject(url2);
	    	}
	    	else{
	    		for(int n=0;n<obj2.getJSONArray("items").length();n++)
	    			if(obj2.getJSONArray("items").getJSONObject(n).getBoolean("is_answered")==true&&flag>0)
	    			{
	    				flag--;
	    				answerList.add(StringEscapeUtils.unescapeHtml4(obj2.getJSONArray("items").getJSONObject(n).getString("title")));
	    				links.add(new URI(obj2.getJSONArray("items").getJSONObject(n).getString("link")));
	    			}
	    		length--;
	    	}
		}
		answerJList.setListData(answerList); answerJList.setLayoutOrientation(JList.VERTICAL);
    	return links;
		
		
	}

}
