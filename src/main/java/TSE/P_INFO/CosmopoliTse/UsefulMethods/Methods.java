package TSE.P_INFO.CosmopoliTse.UsefulMethods;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Methods{

	/***
	 * Méthode permettant de décompresser les données récupérées sur le site
	 * @author Li Shule
	 * @param in - Objet contenant les données récupérées sur le site compressées au format GZIP
	 * @return une String contenant les données décompressées
	 * @throws IOException
	 * @see sendGet
	 */
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
	 * @return renvoie les données récupérées sur le site (compressées au format GZIP) sous la forme d'une String
	 * @see uncompress
	 ***/
	public static String sendGet(String url) throws IOException, ZipException{
        String result = "";
        try {
        	URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.connect();
            GZIPInputStream in = new GZIPInputStream(connection.getInputStream());    
   		    result = uncompress(in);
   		 	in.close();
   		 	return result;            
        } catch (Exception e) {
            System.err.println(e);
            throw e;
        }
    }
	
	
	/***
	 * Méthode permettant d'afficher une liste de contributeurs sur la sortie standard (DaveTerminal, First Story)
	 * @param count - Nombre de contributeurs à afficher
	 * @param contributors - Map contenant en Clés les noms des contributeurs et en Valeurs le nombre de réponses apportées
	 * @param contributorsPostCount - Liste contenant le nombre de réponses apportées pour chaque contributeur
	 */
	public static void printContributors(int count, Map<String,Long> contributors, List<Long> contributorsPostCount){
		for(int i=19;i>=19-(count-1);i--)
		{ /*La Map est créée après la récupération des noms et "post_count" des contributeurs qui elle se fait sous forme de liste. Cependant, celles-ci ne sont pas triées comme nous 
			le souhaitons (dans l'ordre décroissant des "post_count"). Nous avons donc trié indépendamment la liste des "post_count", ce qui la décorrèle de la liste des noms ; d'où 
			l'utilisation d'une double boucle pour parcourir simultanément la liste des "post_count" et la Map de correspondance Nom/"Post Count". */
			for(Map.Entry<String, Long> cont:contributors.entrySet())
				if(cont.getValue()==contributorsPostCount.get(i))
					System.out.println(cont.getKey() + ": " + cont.getValue());
		}
	}
	
	
	/***
	 * Méthode permettant de créer une Map de correspondance Nom/"Post Count" ainsi qu'une liste de "post_count" à partir d'un objet JSON (DaveTerminal, First Story)
	 * @param obj - Objet JSON contenant les données récupérées sur le site
	 * @param contributors - Map de correspondance Nom/"Post Count"
	 * @param contributorsPostCount - Liste contenant les "post_count" de tous le contributeurs
	 */
	public static void fillContibutorsList(JSONObject obj, Map<String,Long> contributors, List<Long> contributorsPostCount){
		for(int i=0;i<20;i++)
		{
			String nom = getUserProperty(obj,i,"display_name");
			Long score = obj.getJSONArray("items").getJSONObject(i).getLong("post_count");
			contributorsPostCount.add(score);
			contributors.put(nom, score);
		}
	}
	
	
	/**
	 * Méthode qui donne le nombre de postes d'un user pour d'un ensemble de tags donnés 
	 * @param obj - Objet JSON contenant les données récupérées sur le site
	 * @param userScoreMap - Map contenant le nom de l'utilisateur et le nombre de postes qu'il a fait
	 */
	public static void fillUserScoreMap(JSONObject obj, Map<String,Long> userScoreMap){
		for(int j=0;j<20;j++)
    	{
    		String nom = Methods.getUserProperty(obj, j,"display_name");
    		Long post = obj.getJSONArray("items").getJSONObject(j).getLong("post_count");
    		if(userScoreMap.containsKey(nom))
    		{
    			userScoreMap.replace(nom,post+userScoreMap.get(nom));
    		}
    		else
    		{
    			userScoreMap.put(nom, post);
    		}
    	}
	}
	
	
	/***
	 * Méthode permettant, à partir d'un objet JSON, de récupérer la propriété d'un utilisateur
	 * @param obj - Objet JSON contenant les données récupérées sur le site
	 * @param index - Index de l'utilisateur dont on souhaite le nom dans le tableau "items" contenu dans l'objet JSON
	 * @param prop - Propriété que l'on souhaite récupérer
	 * @return Une String contenant le nom de l'utilisateur souhaité
	 */
	public static String getUserProperty(JSONObject obj, int index, String prop){
		String property = "";
		try{
			property = obj.getJSONArray("items").getJSONObject(index).getJSONObject("user").getString(prop);
		}catch(JSONException e){
			System.err.println(e);
		}
		
		return property;
	}
	
	
	/***
	 * Méthode permettant, à partir d'un objet JSON, de récupérer l'identifiant d'une question
	 * @param obj - Objet JSON contenant les données récupérées sur le site
	 * @param index - Index de la question dont on souhaite l'identifiant dans le tableau "items" contenu dans l'objet JSON
	 * @return Un entier contenant l'identifiant de la question souhaitée
	 */
	public static Integer getQuestionId(JSONObject obj, int index){
		return obj.getJSONArray("items").getJSONObject(index).getInt("question_id");
	}
	
	
	/***
	 * Méthode permettant de générer un objet JSON à partir d'une String
	 * @param url - String à convertir en objet JSON
	 * @return Un objet JSON contenant les donées de "url"
	 * @see sendGet
	 */
	public static JSONObject generateJSONObject(String url){
		try {
			return new JSONObject(Methods.sendGet((url)));
		} catch (Exception e) {
			System.err.println(e);
		}
		return null;
	}
	
	
	/***
	 * Méthode permettant de récupérer une question et son lien à partir d'un tableau JSON
	 * @param array - Tableau JSON contenant des données sur différentes questions
	 * @param index - Index de la question que l'on souhaite récupérer dans "array"
	 * @param title - Titre de la question à récupérér
	 * @param link - Lien vers la question à récupérer
	 * @return Un tableau de deux Strings, l'une étant le titre d'une question et la seconde étant le lien vers cette question
	 */
	public static String[] generateQuestionAndLink(JSONArray array, int index, String title, String link){
		String[] answer = new String[2];
		title = StringEscapeUtils.unescapeHtml4(array.getJSONObject(index).getString("title"));
		answer[0]=title;
		link = array.getJSONObject(index).getString("link");
		answer[1]=link;
		return answer;
	}

	
	/***
	 * Méthode permettant de générer une url pour récupérer les réponses postées par un utilisateur
	 * @param userid - Identifiant de l'utilisateur ayant posté les réponses voulues
	 * @param page - Numéro de la page sur laquelle on récupère les réponses 
	 * @return Une String contenant l'url pour laquelle on effectuera la requête HTTP
	 */
	public static String generateAnswerRequest(int userid, int page)
	{
		return "https://api.stackexchange.com/2.2/users/" + userid + "/answers?page="+ page +"&pagesize=20&order=desc&sort=votes&site=stackoverflow";
	}
	
	
	/***
	 * Méthode permettant de générer une url pour récupérer les top tags d'un utilisateur
	 * @param userid - Identifiant de l'utilisateur
	 * @return Une String contenant l'url pour laquelle on effectuera la requête HTTP
	 */
	public static String generateTopTagsRequest(int userid) {
		return "https://api.stackexchange.com/2.2/users/"+ userid +"/top-tags?pagesize=6&site=stackoverflow&key=TWJoclGjmJo5yUlPKN4TbQ((";
	}

	
	/***
	 * Méthode permettant de générer une url pour récupérer les meilleurs "repondeurs" pour un tag donné
	 * @param tag - Tag pour lequel on souhaite effectuer la recherche
	 * @return Une String contenant l'url pour laquelle on effectuera la requête HTTP
	 */
	public static String generateTagRequest(String tag) {
		try{
			return "https://api.stackexchange.com/2.2/tags/" + URLEncoder.encode(tag, "utf-8") + "/top-answerers/all_time?page=1&site=stackoverflow&key=TWJoclGjmJo5yUlPKN4TbQ((";
		}catch(UnsupportedEncodingException uee){
			return "https://api.stackexchange.com/2.2/tags/" + tag + "/top-answerers/all_time?page=1&site=stackoverflow&key=TWJoclGjmJo5yUlPKN4TbQ((";
		}
	}
	
	
	/**
	 * Description : La méthode returne la valeur max de la Collection
	 * @author Ricard Anthony
	 * @param coll
	 * @return max - La valeur max
	 */
	
	public static Long getMax(Collection<Long> coll)
	{
		Long max = (long) 0;
		for (Long post : coll) 
		{  
  		  if(max <= post)
  			  max = post;
		}
		return max;
	}
	
	/**
	 * Méthode qui renvoie les liens vers le profile des utilisateurs
	 * @param obj - objet json contenant le resultat de la requête
	 * @param linkMap
	 */
	public static void fillLinkMap(JSONObject obj, Map<String,URI> linkMap)
	{
		for(int i=0;i<20;i++)
		{
			String link = getUserProperty(obj,i,"link");
			try {
				linkMap.put(Methods.getUserProperty(obj, i,"display_name"), new URI(link));
			} catch (URISyntaxException e) {
				System.err.println(e);
			}
		}
	}

	@SuppressWarnings("serial")
	public static class BadIdException extends Exception{
		
	}
	
	@SuppressWarnings("serial")
	public static class EmptyFieldException extends Exception{
		
	}
	
	public static String createTagString(List<String> ll, Integer length) throws UnsupportedEncodingException{
		String result = String.join(";", ll.subList(0, length));
		return URLEncoder.encode(result,"utf-8");
	}
	
	public static String afficheTag(List<String> ll, Integer length){
		String result = "Top tags: ";
		for(String l:ll){
			result += l+" et ";
		}
		result = result.substring(0,result.length()-3);
		return result;
	}
	/**
	 * Description : La méthode va prendre la question et mettre chaque mot de la question dans une map.
	 * @author Leang Sébastien 
	 * @param Question - Question que l'utilisateur pose
	 * @return Map - Map contenant chaque mot de la question 
	 * @throws JSONException
	 */
	public static HashMap <String, Integer> RecuperationMotQuestion(String Question) throws JSONException{
		
	    int firstChar = -1;
	    int length = 0;
	    HashMap <String, Integer> MapMot = new HashMap<String,Integer>();
	    for(int i=0 ; i<Question.length() ; i++) {
	        if(!Character.isLetter(Question.charAt(i))){
	            if(firstChar != -1)
	                MapMot.put((Question.substring(firstChar,firstChar + length)), i);
	            firstChar = -1;
	            length = 0;
	        } else {
	            if(firstChar == -1)
	                firstChar = i;
	            length++;
	        }
	        if(firstChar != -1 && i == Question.length()-1)
	        	MapMot.put(Question.substring(firstChar,firstChar + length),i);
	    }
		return MapMot;
	}
	/**
	 * Description : La méthode va parcourir la map et chercher si le mot a des relatedTags 
	 * @author Leang Sébastien 
	 * @param MapMot
	 * @return ListeMotClef - Liste de mots clef potentiellement à ajouter
	 * @throws IOException
	 */
	
	public static ArrayList<String> Comparaison( Map<String, Integer> MapMot) throws IOException{
		ArrayList<String> ListeMotClef = new ArrayList<String>();
	
		for (String e : MapMot.keySet()){
			String urlRelatedTag = "https://api.stackexchange.com/2.2/tags/"+ URLEncoder.encode(e, "utf-8") + "/related?site=stackoverflow&key=TWJoclGjmJo5yUlPKN4TbQ((";
			JSONObject RelatedTags = Methods.generateJSONObject(urlRelatedTag);
			JSONArray TagsArrayRelated = RelatedTags.getJSONArray("items");
			if(TagsArrayRelated.length()!=0)
				for (int i= 1; i<6; i++)
					ListeMotClef.add(TagsArrayRelated.getJSONObject(i).getString("name"));
		}
		
		return ListeMotClef;
	}
	
}
