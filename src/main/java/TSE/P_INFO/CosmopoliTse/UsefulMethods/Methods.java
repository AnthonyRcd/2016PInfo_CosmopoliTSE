package TSE.P_INFO.CosmopoliTse.UsefulMethods;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.json.JSONArray;
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
	public static String sendGet(String url) throws IOException{
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
            System.err.println(e);//System.out.println(result);
            throw e;
        }
    }
	
	
	/***
	 * Méthode permettant d'afficher une liste de contributeurs sur la sortie standard (DaveTerminal, First Story)
	 * @param count - Nombre de contributeurs à afficher
	 * @param contributors - Map contenant en Clés les noms des contributeurs et en Valeurs le nombre de réponses apportées
	 * @param contributorsPostCount - Liste contenant le nombre de réponses apportées pour chaque contributeur
	 * @see fillContributorsList
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
	 * @see printContributors, getUserName
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
	
	
	//TODO Javadoc
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
		return obj.getJSONArray("items").getJSONObject(index).getJSONObject("user").getString(prop);
	}
	
	
	/***
	 * Méthode permettant, à partir d'un objet JSON, de récupérer l'identifiant d'une question
	 * @param obj - Objet JSON contenant les données récupérées sur le site
	 * @param index - Index de la question dont on souhaite l'identifiant dans le tableau "items" contenu dans l'objet JSON
	 * @return Un entier contenant l'identifiant de la question souhaitée
	 */
	public static int getQuestionId(JSONObject obj, int index){
		return obj.getJSONArray("items").getJSONObject(index).getInt("question_id");
	}
	
	
	/***
	 * Méthode permettant de générer un objet JSON à partir d'une String
	 * @param url - String à convertir en objet JSON
	 * @return Un objet JSON contenant les donées de "url"
	 * @throws IOException
	 * @see sendGet
	 */
	public static JSONObject generateJSONObject(String url) throws IOException{
		return new JSONObject(Methods.sendGet((url)));
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
		title = array.getJSONObject(index).getString("title");
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
		return "https://api.stackexchange.com/2.2/users/" + userid + "/answers?page="+ page +"&pagesize=10&order=desc&sort=votes&site=stackoverflow";
	}
	
	
	/***
	 * Méthode permettant de générer une url pour récupérer les top tags d'un utilisateur
	 * @param userid - Identifiant de l'utilisateur
	 * @return Une String contenant l'url pour laquelle on effectuera la requête HTTP
	 */
	public static String generateUserRequest(int userid) {
		return "https://api.stackexchange.com/2.2/users/"+ userid +"/top-tags?pagesize=6&site=stackoverflow";
	}

	
	/***
	 * Méthode permettant de générer une url pour récupérer les meilleurs "repondeurs" pour un tag donné
	 * @param tag - Tag pour lequel on souhaite effectuer la recherche
	 * @return Une String contenant l'url pour laquelle on effectuera la requête HTTP
	 * @throws UnsupportedEncodingException
	 */
	public static String generateTagRequest(String tag) {
		try{
			return "https://api.stackexchange.com/2.2/tags/" + URLEncoder.encode(tag, "utf-8") + "/top-answerers/all_time?page=1&site=stackoverflow";
		}catch(UnsupportedEncodingException uee){
			return "https://api.stackexchange.com/2.2/tags/" + tag + "/top-answerers/all_time?page=1&site=stackoverflow";
		}
	}
	
	
	//TODO Javadoc
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
	
	//TODO Javadoc
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

	
}
