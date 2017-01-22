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
public class UserInterface extends JPanel{
	
	protected JButton search1 = new JButton("1ère Story");
	protected JButton search2 = new JButton("2ème Story");
	protected JButton search3 = new JButton("3ème Story");
	
	protected JLabel subjectInstructions;
	protected JTextField subject;
	
	protected JList<String> answerList;
	protected JTextArea errorArea;
	
		
	public UserInterface(){
		super();
		add(makePanel());
	}
	
	protected JPanel makePanel(){
		
		//Container of the whole frame
		BorderLayout globalLayout = new BorderLayout();
		globalLayout.setVgap(40); globalLayout.setHgap(25);
		JPanel userPanel = new JPanel(); userPanel.setLayout(globalLayout);
		
		//Container of the search field and the buttons
		JPanel searchPanel = new JPanel();
		subjectInstructions = new JLabel();
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
		
		this.setFocusable(true);
		
		errorArea = new JTextArea(); errorArea.setForeground(Color.red); errorArea.setBackground(userPanel.getBackground()); errorArea.setEditable(false);
		answerList = new JList<String>(); answerList.setBackground(userPanel.getBackground()); answerList.setForeground(Color.blue);
		userPanel.add(searchPanel,BorderLayout.PAGE_START); 
		userPanel.add(answerList,BorderLayout.CENTER);
		userPanel.add(errorArea, BorderLayout.PAGE_END); 
		
		return userPanel;
	}

}
