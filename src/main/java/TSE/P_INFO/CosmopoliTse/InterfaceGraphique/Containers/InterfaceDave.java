package TSE.P_INFO.CosmopoliTse.InterfaceGraphique.Containers;

import java.awt.Color;


@SuppressWarnings("serial")
public class InterfaceDave extends UserInterface{
			
	public InterfaceDave(){
		super();
		configureItems();
	}
	
	public void configureItems(){
		
		subject.setColumns(24);
		subject.setForeground(Color.GRAY); 
		subject.setText("ex: java (Story1) ou java;sql;c#;javascript (Story3)");
		
		subjectInstructions.setText("Saisissez l'objet de votre requête ici: ");
		search1.setToolTipText("Trouver les personnes contribuant le plus à un sujet donné");
		search2.setToolTipText("Trouver la personne qui a le top tag dans un sujet donné");
		search3.setToolTipText("Trouver la personne qui contribue le plus à un ensemble de tags donné");
		
	}

}
