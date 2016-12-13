package TSE.P_INFO.CosmopoliTse.UsefulMethods;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.json.JSONArray;
import org.json.JSONObject;

public class Methods{

	public static String uncompress(GZIPInputStream in) throws IOException {   
		 if (in == null) {   
			 return "";   
		 }   
		 ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[256];
		 int n;   
		 while ((n = in.read(buffer))>= 0) {   
		 	out.write(buffer, 0, n);   
		 }   
		 return out.toString("utf-8");
	}   
	
	/***
	 * Methode permettant l'envoi d'une requête HTTP get vers le site dont l'url est passée en paramètre
	 * @author Li Shule
	 * @throws IOException
	 * @param url - String contenant l'url pour laquelle on souhaite établir une connexion HTTP
	 * @return renvoie la donnée décompressée récupérée sur le site au format String
	 ***/
	
	public static String sendGet(String url) throws IOException {
        String result = "";
        GZIPInputStream in = null;
        
        try {
        	URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.connect();
            in = new GZIPInputStream(connection.getInputStream());    
   		    result = uncompress(in);
   		 	return result;
            
        } catch (Exception e) {
            System.out.println("error in http get!" + e);
            e.printStackTrace();
        }
        finally {
            in.close();
        }
        return result;
    }
	
	public static void printContributors(int count, Map<String,Long> contributors, List<Long> contributorsPostCount){
		for(int i=19;i>=19-(count-1);i--)
		{
			for(Map.Entry<String, Long> cont:contributors.entrySet())
				if(cont.getValue()==contributorsPostCount.get(i))
				{
					System.out.println(cont.getKey() + ": " + cont.getValue());
				}
		}
	}
	
	public static void fillContibutorsList(JSONObject obj, Map<String,Long> contributors, List<Long> contributorsPostCount){
		for(int i=0;i<20;i++)
		{
			String nom = getUserName(obj,i);
			Long score = obj.getJSONArray("items").getJSONObject(i).getLong("post_count");
			contributorsPostCount.add(score);
			contributors.put(nom, score);
		}
	}
	
	public static String getUserName(JSONObject obj, int index){
		return obj.getJSONArray("items").getJSONObject(index).getJSONObject("user").getString("display_name");
	}
	
	public static int getQuestionId(JSONObject obj, int index){
		return obj.getJSONArray("items").getJSONObject(index).getInt("question_id");
	}
	
	public static String answersRequestURL(int userid, int page)
	{
		return "https://api.stackexchange.com/2.2/users/" + userid + "/answers?page="+ page +"&pagesize=10&order=desc&sort=votes&site=stackoverflow";
	}
	
	public static JSONObject generateJSONObject(String url) throws IOException{
		return new JSONObject(Methods.sendGet((url)));
	}
	
	public static void generateQuestionAndLink(JSONArray array, int cpt, int index, String title, String link){
		title = array.getJSONObject(index).getString("title");
		link = array.getJSONObject(index).getString("link");
		System.out.println((cpt+1) + " - " + title);
		System.out.println("lien: " + link);
	}
}
