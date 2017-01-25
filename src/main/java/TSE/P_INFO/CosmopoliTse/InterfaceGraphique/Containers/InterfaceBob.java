package TSE.P_INFO.CosmopoliTse.InterfaceGraphique.Containers;

import java.awt.Color;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class InterfaceBob extends UserInterface{

	protected JButton search4 = new JButton("4ème Story");
	
	public JButton getSearch4() {
		return search4;
	}

	public void setSearch4(JButton search4) {
		this.search4 = search4;
	}

	public InterfaceBob() {
		super();
		configureItems();
	}

	private void configureItems() {

		subject.setColumns(24);
		subject.setForeground(Color.GRAY);
		subject.setText("ex: How to use regex in java?");
		subjectInstructions.setText("Saisissez votre User Id ou une question: ");
		
		search1.setToolTipText("Trouver des questions existantes qui correspondent à mon besoin");
		search2.setToolTipText("Me suggérer des mots-clés à rajouter");
		search3.setToolTipText("Me proposer des contributeurs à suivre");
		search4.setToolTipText("M’indiquer les nouvelles questions (avec déjà des réponses) dans mes champs d’intérêt");
		
		search4.setName("story4");
		buttonPanel.add(search4);
	}

}
