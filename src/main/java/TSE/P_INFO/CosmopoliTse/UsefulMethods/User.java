package TSE.P_INFO.CosmopoliTse.UsefulMethods;

import java.io.IOException;
import java.util.Scanner;

public abstract class User{

	public Scanner input;
	
	public String username;
	
	public abstract void setUsername(String name);
	
	public abstract void firstStory() throws IOException;
	
	public abstract void secondStory() throws IOException;
	
	public abstract void thirdStory() throws IOException;
	
	public abstract void fourthStory() throws IOException;
	
	public void questions(String username){
		switch(username)
		{
			case("Dave"):
				System.out.println("Que souhaitez-vous afficher?");
	        	System.out.println("1: Les personnes contribuant le plus à un sujet donné");
	        	System.out.println("2: La personne qui a le top tag dans un sujet donné");
	        	System.out.println("3: La personne qui contribue le plus à un ensemble de tags donnés");
	        	break;
			case("Alice"):
				System.out.println("Que souhaitez-vous afficher?");
	        	System.out.println("1: Les nouvelles questions dans mes compétences");
	        	System.out.println("2: Pour chacun de mes badges, les utilisateurs qui en ont plus ou autant que moi?");
	        	System.out.println("3: Les questions auxquelles j’ai répondu, triées en fonction de leur taux de succès");
	        	break;
			case("Bob"):
				System.out.println("Que souhaitez-vous afficher?");
	        	System.out.println("1: Des questions existantes qui correspondent à mon besoin");
	        	System.out.println("2: Des suggestions de mots-clés à rajouter");
	        	System.out.println("3: Des propositions de contributeurs à suivre");
	        	System.out.println("4: Les nouvelles questions (avec déjà des réponses) dans mes champs d’intérêt");
		}
	}
	
	
	public static void menu(User u) throws IOException{
    	
    	boolean continuer=false;
    	Scanner in = new Scanner(System.in);
    	do
    	{
    		u.questions(u.username);
    		u.input = new Scanner(System.in);
    		int choix = u.input.nextInt();
    		String reponse;
    		
	    	switch (choix)
	    	{
	    	  case 1:
	    		  u.firstStory();
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
	    		  u.secondStory();
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
	    		  u.thirdStory();
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
	    	  case 4:
	    		  u.fourthStory();
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

}
