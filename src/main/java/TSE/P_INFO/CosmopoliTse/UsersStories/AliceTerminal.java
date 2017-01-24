package TSE.P_INFO.CosmopoliTse.UsersStories;

import org.apache.commons.lang3.StringEscapeUtils;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import TSE.P_INFO.CosmopoliTse.UsefulMethods.Methods;
import TSE.P_INFO.CosmopoliTse.UsefulMethods.User;

public class AliceTerminal extends User{
	
	AliceTerminal(){
		setUsername("Alice");
	}
	
	/***
	 * Première Story d'Alice: Quelles sont les nouvelles questions dans mes compétences ?
	 * @author Bou-Zogheib Diane
	 * @throws IOException
	 ***/
	
	@Override
	public void firstStory() throws JSONException, IOException
	{
		System.out.println("Veuillez saisir votre identifiant : ");
		input = new Scanner (System.in);
		int userid;
		try{
			userid = input.nextInt();
		}catch(Exception e){
			System.err.println(e);
			return;
		}
		String url = Methods.generateTopTagsRequest(userid);
		JSONObject obj= Methods.generateJSONObject(url);
		String titre = new String();
		String link = new String();
		ArrayList<String> tags = new ArrayList<String>();
    	
    	try
    	{
    		//Retrieve the 5 first top tags of the user
			for (int k = 0; k<5; k++)
				tags.add(obj.getJSONArray("items").getJSONObject(k).getString("tag_name"));
        	
    	}catch(JSONException j){
	    		System.err.println("Aucun tag n'a été trouvé pour cet utilisateur");
	    }
    	
		//Create a signle string containing all the tags separated with semicolons (;)
		String tag = String.join(";",tags);
		int cpt = 0;
		String[] answer;
		while (cpt < 10)
		{
			try{
				url = "https://api.stackexchange.com/2.2/questions/no-answers?pagesize=10&order=desc&sort=creation&tagged="+URLEncoder.encode(tag, "utf-8")+"&site=stackoverflow";
				obj= Methods.generateJSONObject(url);
			}catch(IOException e){
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
					answer=Methods.generateQuestionAndLink(array, i, titre, link);
					System.out.println((cpt+1) + " - " + StringEscapeUtils.unescapeHtml4(answer[0]));
					System.out.println("lien: " + answer[1]);
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
					answer = Methods.generateQuestionAndLink(array, i, titre, link);
					System.out.println((cpt+1) + " - " + StringEscapeUtils.unescapeHtml4(answer[0]));
					System.out.println("lien: " + answer[1]);
					i++;
					cpt++;

				}
			}
		}
    	System.out.println(obj.get("quota_remaining"));
	}
	
	
	
	
	
	/***
	 * Troisième Story d'Alice: Trier les questions auxquelles j’ai répondu en fonction de leur taux de succès
	 * @author Li Shule
	 * @throws IOException
	 */
	@Override
	public void thirdStory() throws IOException{
		int page = 1;
		List<String> questionIdList = new ArrayList<String>();
		System.out.println("Veuiller saisir le user id que vous voulez chercher: ");
		input = new Scanner (System.in);
    	int userid = input.nextInt();
        JSONObject obj = Methods.generateJSONObject(Methods.generateAnswerRequest(userid, page));
        
        while(obj.getJSONArray("items").length()==0){
	    	System.err.println("Userid "+ userid +" introuvé, veuillez refaire cette recherche \n");
        	userid = input.nextInt();
        	obj = Methods.generateJSONObject(Methods.generateAnswerRequest(userid, page));
        }
        
		for(int j = 0;j<obj.getJSONArray("items").length();j++){
			questionIdList.add(Methods.getQuestionId(obj, j).toString());
		}
    	
    	String url = "https://api.stackexchange.com/2.2/questions/"+ String.join(";",questionIdList) +"?order=desc&sort=votes&site=stackoverflow";
    	obj = Methods.generateJSONObject(url); 
    	
    	System.out.println("Les Top 20 Question:");
    	
    	for(int i=0;i<obj.getJSONArray("items").length();i++)
    		System.out.println(StringEscapeUtils.unescapeHtml4(obj.getJSONArray("items").getJSONObject(i).getString("title")));
    	
    	System.out.println(obj.getInt("quota_remaining"));
    	
		}
	
	public static void main(String[] args) throws IOException {
		menu(new AliceTerminal());
		//firstStoryAlice();
		
	}

	@Override
	public void secondStory() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUsername(String name) {
		this.username=name;
		
	}

	@Override
	public void fourthStory() throws IOException {
		// TODO Auto-generated method stub
		
	}
}
