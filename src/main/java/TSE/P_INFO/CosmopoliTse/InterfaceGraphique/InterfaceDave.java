package TSE.P_INFO.CosmopoliTse.InterfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InterfaceDave extends JPanel{
	
	JButton search1 = new JButton("1ère Story");
	JButton search2 = new JButton("2ème Story");
	JButton search3 = new JButton("3ème Story");
	
	JLabel subjectInstructions;
	JTextField subject;
	
	JTextArea answerArea;
	JTextArea errorArea = new JTextArea();
	
	JTextArea explanations = new JTextArea();
	
	public InterfaceDave(){
		
		add(makePanel());
	}
	
	private JPanel makePanel(){
		
		//Container of the whole frame
		BorderLayout globalLayout = new BorderLayout();
		globalLayout.setVgap(40); globalLayout.setHgap(25);
		JPanel davePanel = new JPanel(); davePanel.setLayout(globalLayout);
		
		//Container of the search field and the buttons
		JPanel searchPanel = new JPanel();
		subjectInstructions = new JLabel("Saisissez l'objet de votre requête ici: ");
		subject = new JTextField(10);
		
		//Container of the buttons only
		JPanel buttonPanel = new JPanel();
		search1.setName("story1"); search2.setName("story2"); search3.setName("story3");
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(search1); buttonPanel.add(search2); buttonPanel.add(search3);
		

		GroupLayout searchLayout = new GroupLayout(searchPanel);
		searchPanel.setLayout(searchLayout);
		
		searchLayout.setAutoCreateGaps(true);
		searchLayout.setAutoCreateContainerGaps(true);
		
		searchLayout.setHorizontalGroup(
				searchLayout.createSequentialGroup()
				.addGroup(searchLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(subjectInstructions)
						.addComponent(buttonPanel))
				.addGroup(searchLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(subject))
		);
		
		searchLayout.setVerticalGroup(
				searchLayout.createSequentialGroup()
					.addGroup(searchLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(subjectInstructions)
							.addComponent(subject))
					.addGroup(searchLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(buttonPanel))
		);
		
		answerArea = new JTextArea(); answerArea.setSize(1000, 1000); answerArea.setEditable(false); answerArea.setText("Résultat de la requête");
		explanations.setText("Explications du fonctionnement des user stories:\n "
				+ "Story1: Trouver les personnes contribuant le plus à un sujet donné. Pour utiliser cette fonctionnalité, saisissez un mot dans le champ puis cliquez sur 1ère Story. \n "
				+ "Story2: Trouver la personne qui a le top tag dans un sujet donné. Pour utiliser cette fonctionnalité, saisissez un mot dans le champ puis cliquez sur 2ème Story.\n "
				+ "Story3: Trouver la personne qui contribue le plus à un ensemble de tags donné. Pour utiliser cette fonctionnalité, "
				+ "saisissez une série de mots séparés par des point-virgules (;) et cliquez sur 3ème Story.");
		
		davePanel.add(searchPanel,BorderLayout.PAGE_START); davePanel.add(explanations,BorderLayout.CENTER); davePanel.add(answerArea, BorderLayout.PAGE_END);
		
		return davePanel;
	}

}
