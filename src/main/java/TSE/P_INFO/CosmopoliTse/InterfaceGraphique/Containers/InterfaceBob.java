package TSE.P_INFO.CosmopoliTse.InterfaceGraphique.Containers;

import java.awt.Color;

import javax.swing.JButton;

import TSE.P_INFO.CosmopoliTse.InterfaceGraphique.Containers.UserInterface;

@SuppressWarnings("serial")
public class InterfaceBob extends UserInterface{

	protected JButton search4 = new JButton("4ème Story");
	
	public InterfaceBob() {
		super();
		configureItems();
	}

	private void configureItems() {

		subject.setColumns(24);
		subject.setForeground(Color.GRAY); 
		subjectInstructions.setText("Saisissez votre User Id ou une question: ");
		
		search1.setToolTipText("Trouver des questions existantes qui correspondent à mon besoin");
		search2.setToolTipText("Me suggérer des mots-clés à rajouter");
		search3.setToolTipText("Me proposer des contributeurs à suivre");
		search4.setToolTipText("M’indiquer les nouvelles questions (avec déjà des réponses) dans mes champs d’intérêt");
		
		search4.setName("story4");
		//panels[2].add(search4);
	}

}
