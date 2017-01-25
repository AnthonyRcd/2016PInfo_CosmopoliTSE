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
public class UserInterface extends JPanel{
	
	protected JButton search1 = new JButton("1ère Story");
	protected JButton search2 = new JButton("2ème Story");
	protected JButton search3 = new JButton("3ème Story");
	
	protected JLabel subjectInstructions;
	protected JTextField subject;
	
	protected JList<String> answerList;
	protected JTextArea errorArea;
	
	JPanel userPanel;
	JPanel searchPanel;
	JPanel buttonPanel;
	
	public JButton getSearch1() {
		return search1;
	}

	public void setSearch1(JButton search1) {
		this.search1 = search1;
	}

	public JButton getSearch2() {
		return search2;
	}

	public void setSearch2(JButton search2) {
		this.search2 = search2;
	}

	public JButton getSearch3() {
		return search3;
	}

	public void setSearch3(JButton search3) {
		this.search3 = search3;
	}

	public JLabel getSubjectInstructions() {
		return subjectInstructions;
	}

	public void setSubjectInstructions(JLabel subjectInstructions) {
		this.subjectInstructions = subjectInstructions;
	}

	public JTextField getSubject() {
		return subject;
	}

	public void setSubject(JTextField subject) {
		this.subject = subject;
	}

	public JList<String> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(JList<String> answerList) {
		this.answerList = answerList;
	}

	public JTextArea getErrorArea() {
		return errorArea;
	}

	public void setErrorArea(JTextArea errorArea) {
		this.errorArea = errorArea;
	}

	
	public UserInterface(){
		super();
		add(makePanel());
	}
	
	protected JPanel makePanel(){
		
		//Container of the whole frame
		BorderLayout globalLayout = new BorderLayout();
		globalLayout.setVgap(40); globalLayout.setHgap(25);
		userPanel = new JPanel(); userPanel.setLayout(globalLayout);
		
		//Container of the search field and the buttons
		searchPanel = new JPanel();
		subjectInstructions = new JLabel();
		subject = new JTextField(10);
		
		//Container of the buttons only
		buttonPanel = new JPanel();
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
