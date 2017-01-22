package TSE.P_INFO.CosmopoliTse.UsersStories;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JTextArea;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import TSE.P_INFO.CosmopoliTse.UsefulMethods.Methods;
import TSE.P_INFO.CosmopoliTse.UsefulMethods.Methods.BadIdException;

public class AliceGUI {
	
	public static synchronized Vector<URI> firstStoryGUI(int userid, JList<String> answerJList, JTextArea errorArea) throws JSONException
	{
		Vector<String> answerList = new Vector<String>();
		Vector<URI> links = new Vector<URI>();
		String url = Methods.generateTopTagsRequest(userid);
		JSONObject obj= Methods.generateJSONObject(url);
		String titre = new String();
		String link = new String();
		ArrayList<String> tags = new ArrayList<String>();
    	//Retrieve the 5 first top tags of the user
		for (int k = 0; k<5; k++)
			tags.add(obj.getJSONArray("items").getJSONObject(k).getString("tag_name"));
    	
    	answerList.add("Les nouvelles questions dans vos compétences sont: " );
		//Create a signle string containing all the tags separated with semicolons (;)
		String tag = String.join(";",tags);
		int cpt = 0;
		while (cpt < 10)
		{
			try{
				url = "https://api.stackexchange.com/2.2/questions/no-answers?order=desc&sort=creation&tagged="+URLEncoder.encode(tag, "utf-8")+"&site=stackoverflow";
				obj= Methods.generateJSONObject(url);
			}catch(Exception e){
				System.err.println(e);
				tag.replace(tag.subSequence(tag.lastIndexOf(";"), tag.length()-1), "");
				continue;
			}
			JSONArray array = obj.getJSONArray("items");
			if (array.length()<10)
			{
				int i = 0;
				while(i<array.length() && cpt<10)
				{
					answerList.add(Methods.generateQuestionAndLink(array, i, titre, link)[0]);
					try {
						links.add(new URI(Methods.generateQuestionAndLink(array, i, titre, link)[1]));
					} catch (URISyntaxException e) {
						System.err.println(e);
					}
					i++;
					cpt++;
				}
				tag = tag.replace(tag.substring(tag.lastIndexOf(";")), "");
			}
			else
			{
				int i = 0;
				while(cpt<10)
				{
					answerList.add(Methods.generateQuestionAndLink(array, i, titre, link)[0]);
					try {
						links.add(new URI(Methods.generateQuestionAndLink(array, i, titre, link)[1]));
					} catch (URISyntaxException e) {
						System.err.println(e);					
					}
					i++;
					cpt++;
				}
			}
		}
		System.out.println(obj.get("quota_remaining"));
		answerJList.setListData(answerList); answerJList.setLayoutOrientation(JList.VERTICAL);
		return links;
	}
	
	public static synchronized Vector<URI> thirdStoryGUI(int userid, JList<String> answerJList, JTextArea errorArea) throws BadIdException{
		int page = 1;
		Vector<String> answerList = new Vector<String>();
		Vector<URI> links = new Vector<URI>();
		List<String> questionIdList = new ArrayList<String>();
        JSONObject obj = Methods.generateJSONObject(Methods.generateAnswerRequest(userid, page));
        
        if(obj.getJSONArray("items").length()==0){
	    	throw new BadIdException();
        }
        
		for(int j = 0;j<obj.getJSONArray("items").length();j++){
			questionIdList.add(Methods.getQuestionId(obj, j).toString());
		}
    	
    	String url = "https://api.stackexchange.com/2.2/questions/"+ String.join(";",questionIdList) +"?order=desc&sort=votes&site=stackoverflow";
    	obj = Methods.generateJSONObject(url); 
    	
    	answerList.add("Les meilleurs questions auxquelles vous avez répondu sont: ");
    	
    	for(int i=0;i<obj.getJSONArray("items").length();i++)
    	{
    		answerList.add(StringEscapeUtils.unescapeHtml4(obj.getJSONArray("items").getJSONObject(i).getString("title")));
    		try{
    		links.add(new URI(obj.getJSONArray("items").getJSONObject(i).getString("link")));
    		}catch(URISyntaxException e){
    			System.err.println(e);
    		}
    	}
    	
    	System.out.println(obj.getInt("quota_remaining"));
    	answerJList.setListData(answerList); answerJList.setLayoutOrientation(JList.VERTICAL);
    	return links;
	}
}
