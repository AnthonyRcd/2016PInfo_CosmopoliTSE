package TSE.P_INFO.CosmopoliTse.InterfaceGraphique;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class InterfaceAlice extends JPanel{
	JButton search1 = new JButton("1ère Story");
	JButton search2 = new JButton("2ème Story");
	JButton search3 = new JButton("3ème Story");
	
	JLabel subjectInstructions;
	JTextField subject;
	
	JTextArea answerArea;
		
	public InterfaceAlice(){
		add(makePanel());
	}

	private JPanel makePanel() {
		//Container of the whole frame
		BorderLayout globalLayout = new BorderLayout();
		globalLayout.setVgap(40); globalLayout.setHgap(25);
		JPanel alicePanel = new JPanel(); alicePanel.setLayout(globalLayout);
		
		//Container of the search field and the buttons
		JPanel searchPanel = new JPanel();
		subjectInstructions = new JLabel("Saisissez votre User Id: ");
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
		
		alicePanel.add(searchPanel,BorderLayout.PAGE_START); alicePanel.add(answerArea, BorderLayout.PAGE_END);
		
		return alicePanel;

	}

}