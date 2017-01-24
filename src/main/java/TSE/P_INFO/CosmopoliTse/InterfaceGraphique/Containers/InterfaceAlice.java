package TSE.P_INFO.CosmopoliTse.InterfaceGraphique.Containers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class InterfaceAlice extends UserInterface{
	
	public InterfaceAlice(){
		super();
		configureItems();
	}

	public void configureItems(){
		subjectInstructions.setText("Saisissez votre User Id: ");
		
		search1.setToolTipText("Trouver les nouvelles questions dans mes compétences");
		search2.setToolTipText("Trouver, pour chacun de mes badges, les utilisateurs qui en ont plus ou autant que moi");
		search3.setToolTipText("Trouver les questions auxquelles j’ai répondu, triées en fonction de leur taux de succès");
		
	}

}