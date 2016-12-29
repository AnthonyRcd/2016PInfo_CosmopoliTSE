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
	private static Scanner input = new Scanner(System.in);
	
	AliceTerminal(){
		setUsername("Alice");
	}
	
	/***
	 * Première Story d'Alice: Quelles sont les nouvelles questions dans mes compétences ?
	 * @author Bou-Zogheib Diane
	 * @throws IOException
	 */
	
	/*
	@Override
	public void firstStory() throws IOException
	{
		System.out.println("Trouver les nouvelles questions dans mes compétences :");
		System.out.println("Veuillez saisir votre nom : ");
		input = new Scanner (System.in);
		String inname = input.nextLine();
		inname = inname.replaceAll("\\s","%20");
		String url = "https://api.stackexchange.com/2.2/users?order=desc&sort=reputation&inname=" + inname +"&site=stackoverflow";
    	JSONObject obj = Methods.generateJSONObject(url);
    	int userid = 0;
    	String topTag ;
    	String tagUrl ;
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
        	obj= Methods.generateJSONObject(url);
        	userid = obj.getJSONArray("items").getJSONObject(0).getInt("user_id");
    	}
    	System.out.println(userid);
    	
    	for (int i = 0; i<6;i++)
    	{
    		url = "https://api.stackexchange.com/2.2/users/"+ userid +"/top-tags?site=stackoverflow";
        	obj= Methods.generateJSONObject(url);
    		topTag = obj.getJSONArray("items").getJSONObject(i).getString("tag_name");
    		tagUrl = topTag;
    		System.out.println("Les nouvelles questions pour votre compétence '" + topTag + "' sont :");
    		if(topTag.equals("c#"))
    			tagUrl="%23";
    		url = "https://api.stackexchange.com/2.2/questions/no-answers?order=desc&sort=creation&tagged=" + tagUrl + "&site=stackoverflow";
        	obj= Methods.generateJSONObject(url);
        	
        	for (int k = 0; k < 10; k++)
        	{
        		String titreQuestions = obj.getJSONArray("items").getJSONObject(k).getString("title");
        		String lienQuestions = obj.getJSONArray("items").getJSONObject(k).getString("link");
        		System.out.println( (k+1) + "." + titreQuestions);
        		System.out.println(lienQuestions);
        	}
        	System.out.println("");
    	}	
	}*/
	
	
	@Override
	public void firstStory() throws JSONException, IOException
	{
		System.out.println("Trouver les nouvelles questions dans mes compétences :");
		System.out.println("Veuillez saisir votre identifiant : ");
		input = new Scanner (System.in);
		int userid = input.nextInt();
		if (userid == 0) return;
		String url = Methods.generateUserRequest(userid);
		JSONObject obj= Methods.generateJSONObject(url);
		JSONArray tab_tags = obj.getJSONArray("items");
		String titre = new String();
		String link = new String();
    	
    	try
    	{
        	ArrayList<String> tags = new ArrayList<String>();
			for (int k = 0; k<6; k++)
			{
				tags.add(tab_tags.getJSONObject(k).getString("tag_name"));
        	
			}
			String tag = String.join(";",tags.subList(0, 5));
			int cpt = 0;
			int indice = 5;
			String[] answer;
			while (cpt < 10){
				url = "https://api.stackexchange.com/2.2/questions/no-answers?pagesize=100&order=desc&sort=creation&tagged="+URLEncoder.encode(tag, "utf-8")+"&site=stackoverflow";
				obj= Methods.generateJSONObject(url);
				JSONArray array = obj.getJSONArray("items");
				if (array.length()<10){
					int i = 0;
					while(i<array.length() && cpt<10){
						answer=Methods.generateQuestionAndLink(array, i, titre, link);
						System.out.println((cpt+1) + " - " + StringEscapeUtils.unescapeHtml4(answer[0]));
						System.out.println("lien: " + answer[1]);
						i++;
						cpt++;
					}
					tag = tag.replace(";"+tags.get(indice), "");
					indice--;
				}else{
					int i = 0;
					while(cpt<10){
						answer = Methods.generateQuestionAndLink(array, i, titre, link);
						System.out.println((cpt+1) + " - " + StringEscapeUtils.unescapeHtml4(answer[0]));
						System.out.println("lien: " + answer[1]);
						i++;
						cpt++;

					}
				}
			}
    	}
    	catch(JSONException j)
    	{
    		System.out.println("Aucun tag n'a été trouvé pour cet utilisateur");
  
    	}
    	System.out.println(userid);
	}
	
	
	
	
	
	/***
	 * Troisième Story d'Alice: Trier les questions auxquelles j’ai répondu en fonction de leur taux de succès
	 * @author Li Shule
	 * @throws IOException
	 */
	@Override
	public void thirdStory() throws IOException{
		
		int userid;
		int page;
		List<Integer> questionIdList = new ArrayList<Integer>();
		List<String> questionNameList = new ArrayList<String>();
		JSONObject obj;
		String url;
    	boolean continuer=false;
    	do{
    		System.out.println("Trier les questions auxquelles j’ai répondu en fonction de leur taux de succès :");
    		System.out.println("Veuiller saisir le user id que vous voulez chercher: ");
    		input = new Scanner (System.in);
    		
    		userid = input.nextInt();
    		page=1;
        	obj = Methods.generateJSONObject(Methods.generateAnswerRequest(userid, page));
        	
	    	if(obj.getJSONArray("items").length()==0){
	    		System.out.println("Userid "+ userid +" pas trouver, veuillez refaire cette recherche \n");
	    		userid = input.nextInt();
	    		page=1;
	        	obj = Methods.generateJSONObject(Methods.generateAnswerRequest(userid, page));
	    		continuer=true;
	    	}
	    	else{
	    		for(int j = 0;j<obj.getJSONArray("items").length();j++){
	    			questionIdList.add(Methods.getQuestionId(obj, j));
	    		}
		    	boolean has_more = obj.getBoolean("has_more");
		    	if(has_more){
		    		page++;
		        	String jsonString1 = Methods.sendGet(Methods.generateAnswerRequest(userid, page));
		        	obj = new JSONObject(jsonString1);  
		        	
		        	int pageSize = obj.getJSONArray("items").length();
		        	for(int i = 0 ; i<pageSize ; i++){
		        		questionIdList.add(Methods.getQuestionId(obj, i));
		        	}
		    	}
		    	
		    	for(int questionid:questionIdList){
		    		url = "https://api.stackexchange.com/2.2/questions/"+ questionid +"?order=desc&sort=votes&site=stackoverflow";
		        	obj= Methods.generateJSONObject(url);  
		        	questionNameList.add(obj.getJSONArray("items").getJSONObject(0).getString("title"));
		    	}
		    	System.out.println("Les Top 20 Question:");
		    	for(String questionname: questionNameList){
		    		System.out.println(StringEscapeUtils.unescapeHtml4(questionname));
		    	}
	    	}
	    	questionIdList.clear();
    		questionNameList.clear();
    	}while(continuer);
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
}
