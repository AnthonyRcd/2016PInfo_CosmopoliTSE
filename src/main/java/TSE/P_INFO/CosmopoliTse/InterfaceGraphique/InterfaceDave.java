package TSE.P_INFO.CosmopoliTse.InterfaceGraphique;

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
public class InterfaceDave extends JPanel{
	
	JButton search1 = new JButton("1ère Story");
	JButton search2 = new JButton("2ème Story");
	JButton search3 = new JButton("3ème Story");
	
	JLabel subjectInstructions;
	JTextField subject;
	
	JList<String> answerList;
	JTextArea errorArea;
	
		
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
		subject = new JTextField(24); subject.setForeground(Color.GRAY); subject.setText("ex: java (Story1) ou java;sql;c#;javascript (Story3)");
		
		//Container of the buttons only
		JPanel buttonPanel = new JPanel();
		search1.setName("story1"); search2.setName("story2"); search3.setName("story3");
		search1.setToolTipText("Trouver les personnes contribuant le plus à un sujet donné");
		search2.setToolTipText("Trouver la personne qui a le top tag dans un sujet donné");
		search3.setToolTipText("Trouver la personne qui contribue le plus à un ensemble de tags donné");
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
		
		
		errorArea = new JTextArea(); errorArea.setForeground(Color.red); errorArea.setBackground(davePanel.getBackground()); errorArea.setEditable(false);
		answerList = new JList<String>(); answerList.setBackground(davePanel.getBackground()); answerList.setForeground(Color.blue);
		davePanel.add(searchPanel,BorderLayout.PAGE_START); 
		davePanel.add(answerList,BorderLayout.CENTER);
		davePanel.add(errorArea, BorderLayout.PAGE_END); 
		
		return davePanel;
	}

}
