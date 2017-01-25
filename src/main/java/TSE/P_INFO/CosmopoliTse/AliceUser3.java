package TSE.P_INFO.CosmopoliTse;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;

import javax.print.attribute.standard.RequestingUserName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import TSE.P_INFO.CosmopoliTse.UsefulMethods.Methods;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
public class AliceUser3 {
	
	static String APIclef = "knaK2OI6lGZq*Cx4H439cw((";
	private static Scanner input= new Scanner(System.in);
	
	public static void CompareBadges() throws JSONException, IOException{
		try{
			System.out.println("Entrer l'id d'un utilisateur");
			int userid= input.nextInt();
			if(userid == 0) return;
			ComparaisonBadge(userid);				
		}
		 catch (JSONException j) {
			System.out.println("Veuillez écrire un id existant");
		}
	}
	

	
	public static void ComparaisonBadge(int userid) throws JSONException, IOException{
		// Requete noms badges de l'utilisateur:
		String urlNomsBadges = "https://api.stackexchange.com/2.2/users/"+userid+"/badges?order=desc&sort=awarded&site=stackoverflow&key="+APIclef;
		String jsonString4 = Methods.sendGet(urlNomsBadges);
		JSONObject objNomsBadges= new JSONObject(jsonString4);
		JSONArray arrayNomsBadges = objNomsBadges.getJSONArray("items");
		System.out.println(" 3 meilleurs badges (or, argent, bronze) avec pour chaque 3 utilisateurs ayant les mêmes:");
		ListeBadgeClasse(sortByValue(ajouterDanstableauBadgesAwarded(arrayNomsBadges)),ajouterDanstableauBadgesRank(arrayNomsBadges), "gold");
		ListeBadgeClasse(sortByValue(ajouterDanstableauBadgesAwarded(arrayNomsBadges)),ajouterDanstableauBadgesRank(arrayNomsBadges), "silver");
		ListeBadgeClasse(sortByValue(ajouterDanstableauBadgesAwarded(arrayNomsBadges)),ajouterDanstableauBadgesRank(arrayNomsBadges), "bronze");
	}
	
	/**
	 * Permet de trier les meilleurs badges par awarded dans le sens ascendant
	 *  puis les remet dans le sens descendant. 
	 * @param arrayNomsBadgesUser
	 * @throws JSONException 
	 * @return
	 */
	public static Map<String, Integer> ajouterDanstableauBadgesAwarded(JSONArray arrayNomsBadgesUser) throws JSONException{
 
		//Tableau contenant les occurences des badges et leurs noms
		Map<String,Integer> MAPAwarded = new LinkedHashMap<String,Integer>();
		String nomBadges_id = "";
		int Occurence = 0;	
		for (int i = 0; i < arrayNomsBadgesUser.length(); i++) {
				nomBadges_id = arrayNomsBadgesUser.getJSONObject(i).getString("name");
				Occurence = arrayNomsBadgesUser.getJSONObject(i).getInt("award_count");
				MAPAwarded.put(nomBadges_id,Occurence);
				
		}
		return MAPAwarded;
		
	}
	
	/**
	 * Fonction : compare les valeurs des Hashmap et les trient dans l'odre decroissant
	 * @param map
	 * @return
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map )
	{
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>( map.entrySet() );
		Collections.sort( list, new Comparator<Map.Entry<K, V>>()
		{
		public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
		{
		    return (o2.getValue()).compareTo( o1.getValue() );
		}
			} );
		
		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list)
		{
			result.put( entry.getKey(), entry.getValue() );
		}
		return result;
	}
	
	/**
	 * Fonction Fabrication du tableau avec les noms des badges et leur classe (Gold, Silver, Bronze)
	 * @param arrayNomsBadgesUser
	 * @return
	 * @throws JSONException
	 */
	public static Map<String, String> ajouterDanstableauBadgesRank(JSONArray arrayNomsBadgesUser) throws JSONException{
		 Map<String,String> ascsortedMAPRank = new TreeMap<String,String>();//Tableau contenant les classe et les noms des badges
		 String nomBadges_id = "";
		 String rankBadges = "";
		 for (int i = 0; i < arrayNomsBadgesUser.length(); i++) {
			 rankBadges = arrayNomsBadgesUser.getJSONObject(i).getString("rank");
			 nomBadges_id = arrayNomsBadgesUser.getJSONObject(i).getString("name");
			 ascsortedMAPRank.put(nomBadges_id,rankBadges );
		 	}
		 return ascsortedMAPRank;
	 }
	
	/**
	 * Fonction : permet d'afficher les  badges, leurs occurences, leurs rangs, et les badges suppérieurs. 
	 * @param hmAw
	 * @param hmRank
	 * @param rank
	 * @throws JSONException 
	 * @throws IOException 
	 */
	public static void ListeBadgeClasse(Map<String,Integer> hmAw ,Map<String,String> hmRank, String rank) throws JSONException, IOException{ 
		int i = 0;
		System.out.println("\nLa liste des badges " + rank +" classé par awarded : ");
		for (Map.Entry<String, Integer> elementAw : hmAw.entrySet()){
			for (Entry<String, String> elementRank : hmRank.entrySet()) {
				if (elementRank.getKey().equals(elementAw.getKey()) && elementRank.getValue().equals(rank) && i<3){	
					 System.out.println(elementAw.getKey()+ " obtenu " + elementAw.getValue() +" fois "+ tableauBadgesSupEtRecherche(elementAw.getKey()) + CompareAwardedUserUserx(elementAw.getValue(), elementAw.getKey()));
				i++;
				 }
			 }
		}
	}
	/**
	 * Cette fonction est un base de données des classement des badegs uniquement, c'est à dire que les badges sans badges supérieurs ne sont pas mentionnées.
	 * Deplus elle permet de récupérer les badges supérieur au badges de l'utilisateur si il y en a un ou plusieurs. 
	 * Elle doit etre mise à jour si des badges supérieurs arrive sur Stack overflow.
	 * @param badge
	 * @return
	 * @throws JSONException 
	 * @throws IOException 
	 */
	public static String tableauBadgesSupEtRecherche (String badge ) throws JSONException, IOException{
		int emplacement = 0;
		// Tableau de tous les badges possèdant un classement(Bronze, Argent ou/et Or).
		String AllBadges[] = {"Favorite Question","Stellar Question", "Nice Question","Good Question","Great Question", "Nice Answer","Good Answer","Great Answer","Popular Question","Notable Question", "Famous Question","Tenacious","Unsung Hero","Commentator","Enthusiast","Mortarboard","Epic", "Legendary","Precognitive","Beta","Quorum","Convention","Talkative","Outspoken","Citizen Patrol","Deputy","Marshal","Constable","Sheriff","Custodian","Reviewer","Steward","Editor","Strunk & White","Copy Editor","Excavator","Archaeologist","Tag Editor","Research Assistant","Announcer","Booster","Publicist"};
		//Tableau de la quesiton favorite
		String questionsFav[] = {"Favorite Question","Stellar Question"};
		//Tableau du classement des reponses au questions
		String questionsAnswer[] = {"Nice Question","Good Question","Great Question"};
		//Tableau du classement des reponses au questions
		String answersClassement[] = {"Nice Answer","Good Answer","Great Answer"};
		//Tableau du classement des questions populaires
		String questionsPop[] = {"Popular Question","Notable Question", "Famous Question"};
		//Tableau du classement du plus de question non répondue
		String answersZeroScore[] = {"Tenacious","Unsung Hero"};
		//Tableau du classement du plus de commentaires
		String particiaptionsComments[] = {"Commentator","Pundit"};
		//Tableau du classement du plus de visit sur le site
		String particiaptionsVisit[] = {"Enthusiast","Fanatic"}; 
		//Tableau du classement du plus de reputation 
		String particiaptionsGagneReput[] = {"Mortarboard","Epic", "Legendary"};
		//Tableau du classement du plus de votes
		String particiaptionsVote[] = {"Precognitive","Beta"};
		//Tableau du classement des posts avec un score de plus de 2
		String particiaptionsPosts[] = {"Quorum","Convention"};
		//Tableau du classement message avec une ou plus etoiles
		String particiaptionsMessages[] = {"Talkative","Outspoken"};
		//Tableau du classement du moderateur
		String moderationsClassement[] = {"Citizen Patrol","Deputy","Marshal"};	
		//Tableau du classement du plus longtemps en tant que modérateur
		String moderationsModerateur[] = {"Constable","Sheriff"};
		//Tableau du classement des examens des taches
		String moderationsTaches[] = {"Custodian","Reviewer","Steward"};
		//Tableau du classement des Editions
		String moderationsEditer[] = {"Editor","Strunk & White","Copy Editor"};
		//Tableau du classement des Editions des anciens posts
		String moderationsEditAncienPost[] = {"Excavator","Archaeologist"};
		//Tableau du classement des Edit wiki
		String moderationsEditWiki[] = {"Tag Editor","Research Assistant"};
		//Tableau du classement des autres Badges
		String moderationsOtherBadges[] = {"Announcer","Booster","Publicist"};
		
		// Verification que le badge à comparer possède un badges superieur ou est dans la listes puis choisit le tableaux correspondant a fin de récupérer les badges supérieurs.
		for (int j = 0; j < AllBadges.length; j++) {
			if (badge.equals(AllBadges[j])){
				emplacement = j;// designe le choix du tableau où il faut récuperer les badges.
				if (emplacement == 0 || emplacement == 1){
					return badgesSup(questionsFav, badge);
				}
				else if (emplacement == 2 || emplacement == 3 || emplacement == 4){
					return badgesSup(questionsAnswer, badge);
				}
				else if (emplacement == 5 || emplacement == 6 || emplacement == 7){
					return badgesSup(answersClassement, badge);
				}
				else if (emplacement == 8 || emplacement == 9|| emplacement == 10){
					return badgesSup(questionsPop, badge);
				}
				else if (emplacement == 11 || emplacement == 12){
					return badgesSup(answersZeroScore, badge);
				}
				else if (emplacement == 13 || emplacement == 14){
					return badgesSup(particiaptionsComments, badge);
				}
				else if (emplacement == 15 || emplacement == 16){
					return badgesSup(particiaptionsVisit, badge);
				}
				else if (emplacement == 17 || emplacement == 18 || emplacement == 19){
					return badgesSup(particiaptionsGagneReput, badge);
				}
				else if (emplacement == 20 || emplacement == 21){
					return badgesSup(particiaptionsVote, badge);
				}
				else if (emplacement == 22 || emplacement == 23){
					return badgesSup(particiaptionsMessages, badge);
				}
				else if (emplacement == 24 || emplacement == 25 || emplacement == 26){
					return badgesSup(moderationsClassement, badge);
				}
				else if (emplacement == 27 || emplacement == 28){
					return badgesSup(moderationsModerateur, badge);
				}
				else if (emplacement == 29 || emplacement == 30|| emplacement == 31){
					return badgesSup(moderationsTaches, badge);
				}
				else if (emplacement == 32 || emplacement == 33 || emplacement == 34){
					return badgesSup(moderationsEditer, badge);
				}
				else if (emplacement == 35 || emplacement == 36){
					return badgesSup(moderationsEditAncienPost, badge);
				}
				else if (emplacement == 37 || emplacement == 38){
					return badgesSup(moderationsEditWiki, badge);
				}
				else if (emplacement == 39 || emplacement == 40){
					return badgesSup(moderationsOtherBadges, badge);
				}
			}
		}
		return "";
		
	}
	/**
	 *  Fonction: Permet de recuperer les badges supérieurs aux badges de l'utilisateurs s'ils s'existent à l'aide des tableaux de la fonction tableauBadgesSup
	 *  Et permet de récupérer des utilisateurs qui possèdent le\les badge(s) supérieur(s).
	 * @param tab
	 * @param badge
	 * @return
	 * @throws JSONException 
	 * @throws IOException 
	 */
	public static  String badgesSup (String tab[], String badge) throws JSONException, IOException{
		String badgeSup = "";
		if (tab[0].equals(badge) && tab[2] !=null){
			badgeSup = tab[1] +" et "+tab[2];
			return "Les badges supérieur sont : " + badgeSup + ",\n L'Utilisateur ayant le badge sup argent : " + UserNameSup(tab[1]) + ",\n L'Utilisateur ayant le badge sup or : " + UserNameSup(tab[2]) ;
		} 
		else if (tab[0].equals(badge) && tab[2] == null){
			badgeSup = tab[1];
			//System.out.println(badgeSup);
			return "Le badge supérieur est : " + badgeSup+ ",\n L'utilisateur ayant le badge sup argent : "+ UserNameSup(badgeSup);
		}
		else if (tab[1].equals(badge) && tab.length>1){
			badgeSup = tab[2];
			//System.out.println(badgeSup);
			return "Le badge supérieur est : " + badgeSup + ",\n L'utilisateur ayant le badge sup or : "+ UserNameSup(badgeSup);
		}
		return "";
	}
	
	/**
	 * Fonction : Récupere l'utilisateur d'un badge à l'aide du nom et de l'id du badge.
	 * @param nameBadge
	 * @return
	 * @throws JSONException
	 * @throws IOException 
	 */
	public static ArrayList<String> UserNameSup (String nameBadge) throws JSONException, IOException{
		ArrayList <String> ListUserNameSup= new ArrayList<String>();
		nameBadge = nameBadge.replaceAll("\\s","%20");
		String urlIDBadges = "https://api.stackexchange.com/2.2/badges/name?pagesize=100&order=desc&sort=rank&inname="+nameBadge+"&site=stackoverflow&key="+APIclef;
		String jsonString5 = Methods.sendGet(urlIDBadges);
		JSONObject objIDBadges= new JSONObject(jsonString5);
		JSONArray arrayIDBadges = objIDBadges.getJSONArray("items");
		int id = arrayIDBadges.getJSONObject(0).getInt("badge_id");
		String urlNomUserBadges = "https://api.stackexchange.com/2.2/badges/"+id+"/recipients?site=stackoverflow&key="+APIclef;
		String jsonString6 = Methods.sendGet(urlNomUserBadges);
		JSONObject objNomUserBadges= new JSONObject(jsonString6);
		JSONArray arrayNomUserBadges = objNomUserBadges.getJSONArray("items");
		for (int i = 0; i<3; i++){
			String NomUserBadges = arrayNomUserBadges.getJSONObject(i).getJSONObject("user").getString("display_name");
			ListUserNameSup.add(NomUserBadges);
		}
		return ListUserNameSup;
	}
	
	/**
	 * Cette fonction permet d'afficher les utilisateurs qui ont plus en Awkward sur un badge que l'utilisateur possède.
	 * @param aw
	 * @param nameBadge
	 * @return
	 * @throws JSONException
	 * @throws IOException 
	 */
	public static String CompareAwardedUserUserx( int aw, String nameBadge) throws JSONException, IOException{
		int awardedUsrX = 0;
		String NameUser = "";
		String NameBadgeUser = "";
		int ID_utilisateur =0;
		//pour tous les utilisateurs
		String urlalluser = "https://api.stackexchange.com/2.2/users?pagesize=100&order=desc&sort=reputation&site=stackoverflow&key="+APIclef;
		String jsonString7 = Methods.sendGet(urlalluser);
		JSONObject objAllUser = new JSONObject(jsonString7);
		JSONArray arrayAllUser = objAllUser.getJSONArray("items");
		
		
		for (int i = 0; i < arrayAllUser.length(); i++) {
			NameUser = arrayAllUser.getJSONObject(i).getString("display_name");// Nom de l'utilisateur 
			ID_utilisateur = arrayAllUser.getJSONObject(i).getInt("user_id");
			// Reccupération de tous les badges de l'utilisateur
			String urlNameBadgeUser = "https://api.stackexchange.com/2.2/users/"+ID_utilisateur+"/badges?order=desc&sort=awarded&site=stackoverflow&key="+APIclef;
			String jsonString8 = Methods.sendGet(urlNameBadgeUser);
			JSONObject objNameBadgeUser = new JSONObject(jsonString8);
			JSONArray arrayNameBadgeUser = objNameBadgeUser.getJSONArray("items");
			for (int j = 0; j < arrayNameBadgeUser.length(); j++) {
				NameBadgeUser = arrayNameBadgeUser.getJSONObject(j).getString("name");
				if (nameBadge.equals(NameBadgeUser) ){
					awardedUsrX = arrayNameBadgeUser.getJSONObject(j).getInt("award_count");
					if (aw < awardedUsrX){
						return "\nL'utilisateur qui possède plus de fois ce badge est : "+ NameUser;
					}
				}
			}
		}
		
		
		return "";
	}
	
	public static void main(String[] args) throws IOException {
		
	AliceUser3.CompareBadges();
}

}
