package TSE.P_INFO.CosmopoliTse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

public class Bob {
	
	private static Scanner input;
	public static String createTagString(List<String> ll, Integer length){
		String result = "";
		for(int i = 0;i<length;i++){
			result += ll.get(i);
			result += "%3B";
		}
		result = result.substring(0,result.length()-3);
		return result;
	}
	public static void fourthStoryBob() throws IOException
	{
		boolean continuer=false;
    	do{
			System.out.println("M’indiquer les nouvelles questions (avec déjà des réponses) dans mes champs d’intérêt");
			System.out.println("Veuillez saisir un userid : ");
			input = new Scanner (System.in);
			int userid = input.nextInt();
			int flag = 10;
			int length = 6;
			List<String> toptagList = new ArrayList<String>();
			
	    	String url1 = "https://api.stackexchange.com/2.2/users/"+ userid +"/top-tags?pagesize=6&site=stackoverflow";
	    	String jsonString1 = DaveHttp.sendGet(url1);
	    	JSONObject obj1= new JSONObject(jsonString1);
	    	System.out.println("Les les nouvelles 10 question: ");
	    	if(obj1.getJSONArray("items").length()==0){
	    		System.out.println("Userid "+ userid +" pas trouver, veuillez refaire cette recherche \n");
	    		userid = input.nextInt();
	    		url1 = "https://api.stackexchange.com/2.2/users/"+ userid +"/top-tags?pagesize=6&site=stackoverflow";
	        	jsonString1 = DaveHttp.sendGet(url1);
	        	obj1 = new JSONObject(jsonString1);
	    		continuer=true;
	    		
	    	}
	    	else{
	    		for(int j = 0;j<obj1.getJSONArray("items").length();j++){
	    			toptagList.add(obj1.getJSONArray("items").getJSONObject(j).getString("tag_name"));
	    		}
	    		while(flag>0){
		    		String url2 = "https://api.stackexchange.com/2.2/questions?order=desc&sort=activity&tagged="+createTagString(toptagList,length)+"&site=stackoverflow";
			    	String jsonString2 = DaveHttp.sendGet(url2);
			    	JSONObject obj2= new JSONObject(jsonString2);
			    	if(obj2.getJSONArray("items").length()==0){
			    		length--;
			    		url2 = "https://api.stackexchange.com/2.2/questions?order=desc&sort=activity&tagged="+createTagString(toptagList,length)+"&site=stackoverflow";
				    	jsonString2 = DaveHttp.sendGet(url2);
				    	obj2 = new JSONObject(jsonString2);
			    	}
			    	else{
			    		length--;
			    		for(int n=0;n<obj2.getJSONArray("items").length();n++){
			    			if(obj2.getJSONArray("items").getJSONObject(n).getBoolean("is_answered")==true&&flag>0){
			    				flag--;
			    				//System.out.println(createTagString(toptagList,length));
			    				String title = obj2.getJSONArray("items").getJSONObject(n).getString("title");
			    				String link = obj2.getJSONArray("items").getJSONObject(n).getString("link");
			    				System.out.println(title);
			    				System.out.println(link);
			    			}
			    		}
			    	}
	    		}
	    	}
		}
    	while(continuer);
	}
	public static void main(String[] args) throws IOException {
		fourthStoryBob();
	}

}
