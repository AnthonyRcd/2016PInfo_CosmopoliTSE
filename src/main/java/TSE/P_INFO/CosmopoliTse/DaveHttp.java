package TSE.P_INFO.CosmopoliTse;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DaveHttp {

	private static Scanner input= new Scanner(System.in);
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

	/**
	 * Methode permettant l'envoi d'une requête HTTP get vers le site dont l'url est passée en paramètre
	 * @author Li Shule
	 * @throws IOException
	 * @param url - String contenant l'url pour laquelle on souhaite établir une connexion HTTP
	 * @return renvoie la donnée décompressée récupérée sur le site au format String
	 **/
	
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
	
	
	/***
	 * Première Story de Dave: Trouver les personnes contribuant le plus à un sujet donné
	 * @author Li Shule, Leang Sebastien
	 * @throws IOException
	 * @version Console
	 */
	public static void firstStory() throws IOException{
		input = new Scanner(System.in);
		System.out.println("Sur quel sujet voulez-vous effectuer la recherche?");
		String tag = input.nextLine();
		System.out.println("Combien de contributeurs voulez-vous afficher? (20 max)");
		int count=input.nextInt();
		String url = "https://api.stackexchange.com/2.2/tags/" + tag + "/top-answerers/all_time?page=1&pagesize="+20+"&site=stackoverflow";
		String jsonString = DaveHttp.sendGet(url);
		JSONObject obj= new JSONObject(jsonString);
		if(obj.getJSONArray("items").length()==0){
    		System.out.println("Tag "+tag+" pas trouver, veuillez refaire cette recherche");
    	}
		else{
			Map<String,Long> contributors = new HashMap<String,Long>();
			List<Long> contributorsPostCount = new ArrayList<Long>();
			for(int i=0;i<20;i++)
			{
				String nom = obj.getJSONArray("items").getJSONObject(i).getJSONObject("user").getString("display_name");
				Long score = obj.getJSONArray("items").getJSONObject(i).getLong("post_count");
				contributorsPostCount.add(score);
				contributors.put(nom, score);
			}
			
			contributorsPostCount.sort(null);
		
			
			System.out.println("Les meilleurs contributeurs (Nom : Score) au sujet " + tag + " sont: ");
			for(int i=19;i>=19-(count-1);i--)
			{
				for(Map.Entry<String, Long> cont:contributors.entrySet())
					if(cont.getValue()==contributorsPostCount.get(i))
					{
						System.out.println(cont.getKey() + ": " + cont.getValue());
					}
			}
		}

	}
	
	/***
	 * Première Story de Dave: Trouver les personnes contribuant le plus à un sujet donné
	 * @author Ricard Anthony
	 * @param tag - le tag sur lequel l'utilisateur souhaite effectuer sa recherche
	 * @param count - le nombre de contributeurs que l'utilisateur souhaite afficher
	 * @param firstStoryAnswerLabel - zone de l'interface graphique assignée à l'affichage du résultat final
	 * @throws IOException
	 * @version GUI
	 */
	public static synchronized void firstStoryGUI(String tag, int count,JTextArea firstStoryAnswerLabel) throws IOException{
		StringBuilder answer = new StringBuilder();
		String url = "https://api.stackexchange.com/2.2/tags/" + tag + "/top-answerers/all_time?page=1&pagesize="+20+"&site=stackoverflow";
		String jsonString = DaveHttp.sendGet(url);
		JSONObject obj= new JSONObject(jsonString);
		if(obj.getJSONArray("items").length()==0){
    		answer.append("Tag '"+tag+"' inexistant, veuillez ré-effectuer la recherche.");
    	}
		else{
			Map<String,Long> contributors = new HashMap<String,Long>();
			List<Long> contributorsPostCount = new ArrayList<Long>();
			for(int i=0;i<20;i++)
			{
				String nom = obj.getJSONArray("items").getJSONObject(i).getJSONObject("user").getString("display_name");
				Long score = obj.getJSONArray("items").getJSONObject(i).getLong("post_count");
				contributorsPostCount.add(score);
				contributors.put(nom, score);
			}
			
			contributorsPostCount.sort(null);
			
			answer.append("Les meilleurs contributeurs (Nom : Score) au sujet " + tag + " sont: " + System.getProperty("line.separator"));
			for(int i=19;i>=19-(count-1);i--)
			{
				for(Map.Entry<String, Long> cont:contributors.entrySet())
					if(cont.getValue()==contributorsPostCount.get(i))
					{
						answer.append(cont.getKey() + ": " + cont.getValue() + System.getProperty("line.separator"));
					}
			}
		}
		firstStoryAnswerLabel.setText(answer.toString());
	}
	
	/***
	 * Deuxième Story de Dave: Trouver la personne qui a le top tag dans un sujet donné
	 * @author Ricard Anthony, Bou-Zogheib Diane
	 * @version Console
	 * @throws IOException
	 */
    public static void secondStoryDave() throws IOException{
    	System.out.println("Pour quel sujet souhaitez-vous effectuer la recherche?");
    	String Tag = input.next();
    	String url = "https://api.stackexchange.com/2.2/tags/" + Tag + "/top-answerers/all_time?page=1&pagesize=1&site=stackoverflow";
    	String jsonString = DaveHttp.sendGet(url);
    	JSONObject obj= new JSONObject(jsonString);  
    	if(obj.getJSONArray("items").length()==0){
    		System.out.println("Tag "+Tag+" pas trouver, veuillez refaire cette recherche");
    	}
    	else{
    		String tagTopAnswerer=obj.getJSONArray("items").getJSONObject(0).getJSONObject("user").getString("display_name");
    		System.out.println("L'utilisateur ayant le Top Tag dans le sujet " + Tag + " est : " + tagTopAnswerer);
    	}
    }
    
    /***
     * Deuxième Story de Dave: Trouver la personne qui a le top tag dans un sujet donné
     * @author Ricard Anthony
     * @param Tag - le tag sur lequel l'utilisateur souhaite effectuer sa recherche
     * @version GUI
     * @param area - zone de l'interface graphique assignée à l'affichage du résultat de la requête
     * @throws IOException
     */
    public static synchronized void secondStoryGUI(String Tag,JTextArea area) throws IOException{
    	String url = "https://api.stackexchange.com/2.2/tags/" + Tag + "/top-answerers/all_time?page=1&pagesize=1&site=stackoverflow";
    	String jsonString = DaveHttp.sendGet(url);
    	JSONObject obj= new JSONObject(jsonString);  
    	if(obj.getJSONArray("items").length()==0){
    		area.setText("Tag '"+Tag+"' inexistant, veuillez ré-effectuer la recherche.");
    	}
    	else{
    		String tagTopAnswerer=obj.getJSONArray("items").getJSONObject(0).getJSONObject("user").getString("display_name");
    		area.setText("L'utilisateur ayant le Top Tag dans le sujet " + Tag + " est : " + tagTopAnswerer);
    	}
    }
     /***
      * Troisième Story de Dave: Trouver la personne qui contribue le plus à un ensemble de tags donné
      * @author Li Shule
      * @throws IOException
      * @version Console
      */
    public static void thirdStoryDave() throws IOException{
    	Scanner in = new Scanner(System.in);
    	Scanner in1 = new Scanner(System.in);
    	System.out.println("Commbien de tag vous voulez inserer?");
    	int nombre = in.nextInt();
    	List<String> tagList = new ArrayList<String>();
    	Map<String,Long> userScoreMap = new HashMap<String, Long>();
    	for(int i=0;i<nombre;i++){
    		System.out.println("Inserer le tag "+(i+1));
    		String tags = in1.nextLine();
    		tagList.add(tags);
    	}
    	for(String tt:tagList){
    		String url = "https://api.stackexchange.com/2.2/tags/" + tt + "/top-answerers/all_time?site=stackoverflow";
        	String jsonString = DaveHttp.sendGet(url);
        	JSONObject obj= new JSONObject(jsonString); 
        	if(obj.getJSONArray("items").length()==0){
        		System.out.println("Tag "+tt+" pas trouver, veuillez refaire cette recherche");
        		tagList.remove(tt);
        	}
        	else{
	        	for(int j=0;j<20;j++){
	        		int flag=0;
	        		String nom = obj.getJSONArray("items").getJSONObject(j).getJSONObject("user").getString("display_name");
	        		Long post= obj.getJSONArray("items").getJSONObject(j).getLong("post_count");
	        		for (Map.Entry<String, Long> entry : userScoreMap.entrySet()){
	        			if(entry.getKey().equals(nom)){
	        				entry.setValue(post+entry.getValue());
	        				flag=1;
	        			}break;
	        		}
	        		if(flag==0){
	        			userScoreMap.put(nom,post);
	        		}
	        	}
        	}
    	}
    	Long max =(long) 0; 
    	for (Long post : userScoreMap.values()) {  
    		  if(max <= post){
    			  max = post;
    		  }
    	}  
    	for (Map.Entry<String, Long> entry1 : userScoreMap.entrySet()){
    		if(entry1.getValue()==max){
    			System.out.print("Le personne qui contribue le plus sur les tags: ");
    			for(String ttt:tagList){
    				System.out.print("("+ttt+")");
    			}
    			System.out.print(" est "+ entry1.getKey());
    		}
    	}
    }
    
    /***
     * Troisième Story de Dave: Trouver la personne qui contribue le plus à un ensemble de tags donné
     * @author Ricard Anthony
     * @param strings - tableau de chaînes de caractère permettant de récupérer les tags saisis par l'utilisateur
     * @param nombre - nombre de tags sur lequel l'utilisateur veut effectuer la requête
     * @param thirdStoryAnswerLabel - zone de l'interface graphique assignée à l'affichage du résultat final
     * @param insertTag - zone de l'interface graphique dans laquelle l'utilisateur saisit les tags
     * @param errorLabel - zone de l'interface graphique assignée à l'affichage de tags erronés le cas échéant
     * @throws IOException
     * @version GUI
     */
    public static synchronized void thirdStoryGUI(String[] strings, int nombre,JTextArea thirdStoryAnswerLabel,JLabel insertTag,JTextArea errorLabel) throws IOException{
    	StringBuilder answer = new StringBuilder();
    	StringBuilder errors = new StringBuilder();
    	List<String> toRemove = new ArrayList<String>();
    	List<String> tagList = new ArrayList<String>();
    	for(String tt:strings)
    	{
    		tagList.add(tt);
    	}
    	Map<String,Long> userScoreMap = new HashMap<String, Long>();
    	for(String tt:tagList){
    		String url = "https://api.stackexchange.com/2.2/tags/" + tt + "/top-answerers/all_time?site=stackoverflow";
        	String jsonString = DaveHttp.sendGet(url);
        	JSONObject obj= new JSONObject(jsonString); 
        	if(obj.getJSONArray("items").length()==0){
        		errors.append("Tag '"+ tt +"' inexistant, non pris en compte. \n");
        		toRemove.add(tt);
        	}
        	else{
	        	for(int j=0;j<20;j++){
	        		int flag=0;
	        		String nom = obj.getJSONArray("items").getJSONObject(j).getJSONObject("user").getString("display_name");
	        		Long post= obj.getJSONArray("items").getJSONObject(j).getLong("post_count");
	        		for (Map.Entry<String, Long> entry : userScoreMap.entrySet()){
	        			if(entry.getKey().equals(nom)){
	        				entry.setValue(post+entry.getValue());
	        				flag=1;
	        			}break;
	        		}
	        		if(flag==0){
	        			userScoreMap.put(nom,post);
	        		}
	        	}
        	}
    	}
    	Long max =(long) 0; 
    	for (Long post : userScoreMap.values()) {  
    		  if(max <= post){
    			  max = post;
    		  }
    	}  
    	for(String str:toRemove)
    	{
    		if(tagList.contains(str))
    			tagList.remove(str);
    	}
    	for (Map.Entry<String, Long> entry1 : userScoreMap.entrySet()){
    		if(entry1.getValue()==max){
    			answer.append("La personne qui contribue le plus sur les tags: ");
    			for(String ttt:tagList){
    				answer.append("("+ttt+")");
    			}
    			answer.append(" est "+ entry1.getKey());
    		}
    	}
    	errorLabel.setText(errors.toString());
    	thirdStoryAnswerLabel.setText(answer.toString());
    }
    
    /***
     * Menu créé servant pour l'utilisation de l'application en mode console
     * @throws IOException
     */
    
    public static void menu() throws IOException{
    	
    	boolean continuer=false;
    	Scanner in = new Scanner(System.in);
    	do
    	{
    		System.out.println("Que souhaitez-vous afficher?");
        	System.out.println("1: Les personnes contribuant le plus à un sujet donné");
        	System.out.println("2: La personne qui a le top tag dans un sujet donné");
        	System.out.println("3: La personne qui contribue le plus à un ensemble de tags donnés");
        	
    		int choix=input.nextInt();
    		String reponse;
    		
	    	switch (choix)
	    	{
	    	  case 1:
	    		  firstStory();
	    		  System.out.println();
	    		  do
	    		  {
	    			  System.out.println("Souhaitez-vous effectuer une autre recherche? y/n");
		    		  reponse=in.next();
	    			  if(reponse.equals("y"))
	    				  continuer=true;
	    			  else if (reponse.equals("n"))
	    			  {  
	    				  System.out.println("Arrêt de l'application...");
	    				  continuer=false;
	    			  }
	    			  else
	    				  System.out.println("Choix incorrect.");
	    		  }while(!(reponse.equals("y") || reponse.equals("n")));
	    		  break;  
	    	  case 2:
	    		  secondStoryDave();
	    		  System.out.println();
	    		  do
	    		  {
	    			  System.out.println("Souhaitez-vous effectuer une autre recherche? y/n");
		    		  reponse=in.next();
	    			  if(reponse.equals("y"))
	    				  continuer=true;
	    			  else if (reponse.equals("n"))
	    			  {
	    				  System.out.println("Arrêt de l'application...");
	    				  continuer=false;
	    			  }  
	    			  else
	    				  System.out.println("Choix incorrect.");
	    		  }while(!(reponse.equals("y") || reponse.equals("n")));
	    		  break;
	    	  case 3:
	    		  thirdStoryDave();
	    		  System.out.println();
	    		  do
	    		  {
	    			  System.out.println("Souhaitez-vous effectuer une autre recherche? y/n");
		    		  reponse=in.next();
	    			  if(reponse.equals("y"))
	    				  continuer=true;
	    			  else if (reponse.equals("n"))
	    			  {
	    				  System.out.println("Arrêt de l'application...");
	    				  continuer=false;  
	    			  }
	    			  else
	    				  System.out.println("Choix incorrect.");
	    		  }while(!(reponse.equals("y") || reponse.equals("n")));
	    		  break;
	    	  default:
	    		  System.out.println("Erreur de choix, veuillez recommencer.");
	    		  System.out.println();
	    		  continuer=true;
	    		  break;
	    	}
	    	
    	}while(continuer);
    	in.close();
    }
    
    public static void main(String[] args) throws IOException {
    	menu();
    }
}