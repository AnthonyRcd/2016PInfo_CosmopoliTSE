package TSE.P_INFO.CosmopoliTse;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

public class Interface extends JPanel{
	
	JButton search1 = new JButton("Recherche"); 
	JButton search2 = new JButton("Recherche");
	JButton search3 = new JButton("Recherche");
	JLabel subjectInstructions;
	JTextField subject;
	JTextField subject2;
	JTextField subject3;
	JLabel howMuchContributors;
	JTextField nbContributors;
	JTextField nbTags;
	JTextArea firstStoryAnswerLabel;
	JTextArea secondStoryAnswerLabel;
	JTextArea thirdStoryAnswerLabel;
	JTextArea errorLabel ;
	JLabel insertTag;
	JTextField tagInserted;
	
	public Interface(){
		
		super(new GridLayout(1, 1));
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		JPanel story1 = makeFirstStoryPanel();
		tabbedPane.addTab("Story 1", story1);
	
		JPanel story2 = makeSecondStoryPanel();
		tabbedPane.addTab("Story 2", story2);
	
		JPanel story3 = makeThirdStoryPanel();
		tabbedPane.addTab("Story 3", story3);
		
		add(tabbedPane);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		Ecouteur listener = new Ecouteur();
	}
	
	protected JPanel makeFirstStoryPanel(){
		subjectInstructions = new JLabel("Sur quel sujet voulez-vous effectuer la recherche?");
		subject = new JTextField(10);
		howMuchContributors = new JLabel("Combien de contributeurs voulez-vous afficher? (20 max)");
		nbContributors = new JTextField(10);
		search1.setName("search");
		
		JPanel firstStoryPanel = new JPanel();
		JPanel searchPanel = new JPanel();
		JPanel answerLabel = new JPanel();
		FlowLayout storyLayout = new FlowLayout(); firstStoryPanel.setLayout(storyLayout);

		GroupLayout searchLayout = new GroupLayout(searchPanel);
		searchPanel.setLayout(searchLayout);
		
		searchLayout.setAutoCreateGaps(true);
		searchLayout.setAutoCreateContainerGaps(true);
		
		searchLayout.setHorizontalGroup(
				searchLayout.createSequentialGroup()
				.addGroup(searchLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(subjectInstructions)
						.addComponent(howMuchContributors)
						.addComponent(search1))
				.addGroup(searchLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(subject)
						.addComponent(nbContributors))
		);
		
		searchLayout.setVerticalGroup(
				searchLayout.createSequentialGroup()
					.addGroup(searchLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(subjectInstructions)
							.addComponent(subject))
					.addGroup(searchLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(howMuchContributors)
							.addComponent(nbContributors))
							.addComponent(search1)
		);
		
		firstStoryAnswerLabel = new JTextArea(); firstStoryAnswerLabel.setSize(1000, 1000); firstStoryAnswerLabel.setEditable(false);
		answerLabel.add(firstStoryAnswerLabel);
		
		
		firstStoryPanel.add(searchPanel); firstStoryPanel.add(answerLabel);
		
		return firstStoryPanel;
	}
	
	protected JPanel makeSecondStoryPanel(){
		
		subjectInstructions = new JLabel("Sur quel sujet voulez-vous effectuer la recherche?");
		subject2 = new JTextField(10);
		search2.setName("search2");
		
		JPanel secondStoryPanel = new JPanel();
		JPanel searchPanel = new JPanel();
		JPanel answerLabel = new JPanel();
		FlowLayout storyLayout = new FlowLayout(); secondStoryPanel.setLayout(storyLayout);

		
		GroupLayout searchLayout = new GroupLayout(searchPanel);
		searchPanel.setLayout(searchLayout);
		
		searchLayout.setAutoCreateGaps(true);
		searchLayout.setAutoCreateContainerGaps(true);
		
		searchLayout.setHorizontalGroup(
				searchLayout.createSequentialGroup()
				.addGroup(searchLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(subjectInstructions)
						.addComponent(search2))
				.addGroup(searchLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(subject2))
		);
		
		searchLayout.setVerticalGroup(
				searchLayout.createSequentialGroup()
					.addGroup(searchLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(subjectInstructions)
							.addComponent(subject2))
					.addGroup(searchLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(search2))
		);
		
		secondStoryAnswerLabel = new JTextArea(); secondStoryAnswerLabel.setSize(1000, 1000); secondStoryAnswerLabel.setEditable(false);
		answerLabel.add(secondStoryAnswerLabel);
		
		
		secondStoryPanel.add(searchPanel); secondStoryPanel.add(answerLabel);
		
		return secondStoryPanel;
	}
	
	protected JPanel makeThirdStoryPanel(){
		subjectInstructions = new JLabel("Combien de tags vous voulez inserer?");
		nbTags = new JTextField(2);
		insertTag = new JLabel("Insérez les tags séparés par des point-virgules (;)");
		tagInserted = new JTextField(20);
		search3.setName("search3");
		errorLabel = new JTextArea();
		
		JPanel thirdStoryPanel = new JPanel();
		JPanel searchPanel = new JPanel();
		JPanel answerLabel = new JPanel();
		FlowLayout storyLayout = new FlowLayout(); thirdStoryPanel.setLayout(storyLayout);

		GroupLayout searchLayout = new GroupLayout(searchPanel);
		searchPanel.setLayout(searchLayout);
		
		searchLayout.setAutoCreateGaps(true);
		searchLayout.setAutoCreateContainerGaps(true);
		
		searchLayout.setHorizontalGroup(
				searchLayout.createSequentialGroup()
				.addGroup(searchLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(subjectInstructions)
						.addComponent(insertTag)
						.addComponent(search3)
						.addComponent(errorLabel))
				.addGroup(searchLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(nbTags)
						.addComponent(tagInserted))
		);
		
		searchLayout.setVerticalGroup(
				searchLayout.createSequentialGroup()
					.addGroup(searchLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(subjectInstructions)
							.addComponent(nbTags))
					.addGroup(searchLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(insertTag)
							.addComponent(tagInserted))
							.addComponent(search3)
							.addComponent(errorLabel)
		);
		
		thirdStoryAnswerLabel = new JTextArea(); thirdStoryAnswerLabel.setSize(1000, 1000); thirdStoryAnswerLabel.setEditable(false); 
		errorLabel.setSize(1000, 1000); errorLabel.setEditable(false);
		answerLabel.add(thirdStoryAnswerLabel); answerLabel.add(errorLabel);
		
		
		thirdStoryPanel.add(searchPanel); thirdStoryPanel.add(answerLabel);
		
		return thirdStoryPanel;
	}	
	
	
	public class Ecouteur implements ActionListener{
		
		public Ecouteur(){
			actionsDave();
		}
		
		public void actionsDave(){
			search1.addActionListener(this);
			search2.addActionListener(this);
			search3.addActionListener(this);
		}
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() instanceof JButton)
			{
				switch(((JButton) e.getSource()).getName())
				{
					case("search"):
					{
						try {
							DaveHttp.firstStoryGUI(subject.getText(), Integer.parseInt(nbContributors.getText()),firstStoryAnswerLabel);
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							firstStoryAnswerLabel.setText("Veuillez saisir un nombre valide!");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							firstStoryAnswerLabel.setText("Veuillez saisir un sujet.");
						}
						break;
					}
					case("search2"):
					{
						try {
							DaveHttp.secondStoryGUI(subject2.getText(), secondStoryAnswerLabel);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							secondStoryAnswerLabel.setText("Veuillez saisir un sujet.");
						}
						break;
					}
					case("search3"):
					{
						try {
							DaveHttp.thirdStoryGUI(tagInserted.getText().split(";"),Integer.parseInt(nbTags.getText()), thirdStoryAnswerLabel,insertTag, errorLabel);
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							thirdStoryAnswerLabel.setText("Veuillez saisir un nombre valide!");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							thirdStoryAnswerLabel.setText("Veuillez saisir au moins un tag.");
						}
						break;
					}
					
				}
			}
			
		}
		
	}
	
	private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Dave's User Stories");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Add content to the window.
        frame.add(new Interface(), BorderLayout.CENTER);
        
        //Display the window.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height);
        frame.setVisible(true);
    }
	
	public static void main(String[] args)
	{
		createAndShowGUI();
		
	}
	
}

