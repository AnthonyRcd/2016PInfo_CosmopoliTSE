import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import TSE.P_INFO.CosmopoliTse.UsefulMethods.Methods;

public class Alice2 {

	static String key = "TWJoclGjmJo5yUlPKN4TbQ((";
	//static Methods.QuickSort sorter = new Methods.QuickSort();
	
	public static Vector<String> getBadges(String rank){
		Vector<String> badges = new Vector<String>();
		String url = "http://api.stackexchange.com/2.2/users/1144035/badges?page=1&pagesize=100&order=asc&max=named&sort=type&site=stackoverflow&key=" + key;
		JSONObject obj = Methods.generateJSONObject(url);
		JSONArray arr = obj.getJSONArray("items");
		HashMap<String,Integer> ranked = new HashMap<String,Integer>();
		Vector<Integer> counts = new Vector<Integer>();
		for(int i = 0;i<arr.length();i++)
			if(arr.getJSONObject(i).getString("rank").equals(rank))
			{
				ranked.put(arr.getJSONObject(i).getString("name"), arr.getJSONObject(i).getInt("award_count"));
				counts.add(arr.getJSONObject(i).getInt("award_count"));
			}
		System.out.println(ranked);
		//sorter.sort(counts);
		Collections.reverse(counts);
		System.out.println(counts);
		for(int count = 0;count<3;count++){
			for(String name:ranked.keySet())
			{
				if(ranked.get(name).equals(counts.get(count)) && !badges.contains(name) && badges.size()<3)
					badges.add(name);	
			}
		}
		return badges;
	}
	
	public static void secondStory(){
		
		for(String s:getBadges("gold"))
			System.out.println(s);
	}
	
	public static void main(String[] args) {
		secondStory();

	}

}
