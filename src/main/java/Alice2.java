import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import TSE.P_INFO.CosmopoliTse.UsefulMethods.Methods;

public class Alice2 {

	static String key = "TWJoclGjmJo5yUlPKN4TbQ((";
	
	public static HashMap<Integer,Integer> getBadges(String rank, int userId){
		
		String url = "http://api.stackexchange.com/2.2/users/" + userId + "/badges?pagesize=100&order=asc&max=named&sort=type&site=stackoverflow&key=" + key;
		JSONObject obj = Methods.generateJSONObject(url);
		JSONArray arr = obj.getJSONArray("items");
		
		HashMap<Integer,Integer> id_awdcnt = new HashMap<Integer,Integer>();
		HashMap<Integer,Integer> toKeep = new HashMap<Integer,Integer>();

		Vector<Integer> counts = new Vector<Integer>();
		
		for(int i = 0;i<arr.length();i++)
			if(arr.getJSONObject(i).getString("rank").equals(rank))
			{
				id_awdcnt.put(arr.getJSONObject(i).getInt("badge_id"),arr.getJSONObject(i).getInt("award_count"));
				counts.add(arr.getJSONObject(i).getInt("award_count"));
			}
		
		Collections.sort(counts);
		Collections.reverse(counts);
		for(int count = 0;count<3;count++){
			for(int id:id_awdcnt.keySet())
			{
				if(id_awdcnt.get(id).equals(counts.get(count)) && !toKeep.containsKey(id) && toKeep.size()<3)
					toKeep.put(id,id_awdcnt.get(id));	
			}
		}
		return toKeep;
	}
	
	public static JSONArray getRecipients(int badgeId){
		
		String url = "http://api.stackexchange.com/2.2/badges/" + badgeId + "/recipients?pagesize=100&order=asc&max=named&sort=type&site=stackoverflow&key=" + key;
		JSONArray arr = Methods.generateJSONObject(url).getJSONArray("items");
		return arr;
	}
	
	public static String[] goldCompare(int userId,int awardCount,int badgeId){
		String url = "http://api.stackexchange.com/2.2/users/" + userId + "/badges?pagesize=100&order=asc&max=gold&sort=rank&site=stackoverflow&key=" + key;
		JSONArray arr = Methods.generateJSONObject(url).getJSONArray("items");
		
		String[] users = new String[2];
		int i=0; boolean failure = true;
		do{
			if(arr.getJSONObject(i).getInt("badge_id")==badgeId && arr.getJSONObject(i).getInt("award_count")>=awardCount)
			{
				users[0] = arr.getJSONObject(i).getJSONObject("user").getString("display_name");
				users[1] = arr.getJSONObject(i).getJSONObject("user").getString("link");
				failure=false;
			}
			i++;
		}while(failure);
		
		return users;
	}
	
	public static void secondStory(){
		HashMap<Integer,Integer> badges = getBadges("gold",1144035);
		int userId;
		Vector<String> badgeNames = new Vector<String>();
		Vector<String> userNames = new Vector<String>();
		Vector<String> userLinks = new Vector<String>();
		int i=0;
		for(int id:badges.keySet())
		{
			
			JSONArray rec = getRecipients(id);
			badgeNames.add(rec.getJSONObject(0).getString("name"));
			int l=0;
			do{
				userId=rec.getJSONObject(l).getJSONObject("user").getInt("user_id");
				userNames.add(goldCompare(userId,badges.get(id),id)[0]);
				userLinks.add(goldCompare(userId,badges.get(id),id)[1]);
				l++;
			}while(userNames.size()<3);
			
			System.out.println(badgeNames.get(i));
			
			for(int j=0;j<userNames.size();j++)
			{
				System.out.println(userNames.get(j));
				System.out.println(userLinks.get(j) + "\n");
			}
			userNames.clear(); userLinks.clear();
			i++;
		}
		
		//System.out.println(getBadges("gold",1144035));
	}
	
	
	
	public static void main(String[] args) {
		secondStory();

	}

}
