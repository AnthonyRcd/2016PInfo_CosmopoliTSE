package TSE.P_INFO.CosmopoliTse.InterfaceGraphique;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Interface extends JPanel{

	JButton search3 = new JButton("Recherche");
	JTextField subject3;
	JTextArea thirdStoryAnswerLabel;
	JTextArea errorLabel ;
	JLabel insertTag;
	JTextField tagInserted;
	
	public Interface(){
		
		super(new GridLayout(1, 1));
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		JPanel accueil = makeHomePage();
		tabbedPane.addTab("Accueil", accueil);
	
		DaveListener daveL = new DaveListener();
		JPanel dave = daveL.dave;
		tabbedPane.addTab("Dave", dave);
	
		AliceListener aliceL = new AliceListener();
		JPanel alice = aliceL.alice;
		tabbedPane.addTab("Alice", alice);
		
		JPanel bob = new JPanel();
		tabbedPane.addTab("Bob", bob);
		
		add(tabbedPane);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
	}
	
	private JPanel makeHomePage(){
		JPanel home = new JPanel(); home.setBackground(Color.lightGray); home.setLayout(new BorderLayout());
		JTextArea welcome = new JTextArea(); welcome.setEditable(false); 
		welcome.setText(
				"Bienvenue dans l'application des CosmopoliTSE! \n\n"
				+ "Dans cette application, vous aurez la possibilité d'utiliser les fonctionnalités liées à 4 types d'utilisateur: \n"
				+ "\t Alice, développeur confirmé \n"
				+ "\t Bob, développeur débutant \n"
				+ "\t Charlie, chercheur \n"
				+ "\t Dave, chasseur de têtes \n\n"
				+ "L'application récupère les données sur le site Stackoverflow et chaque fonctionnalité est décrite dans l'onglet correspondant à l'utilisateur choisi.\n");
		
		home.add(welcome,BorderLayout.CENTER);
		return home;
	}
	
	
	protected JPanel makeThirdStoryPanel(){
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
						.addComponent(insertTag)
						.addComponent(search3)
						.addComponent(errorLabel))
				.addGroup(searchLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(tagInserted))
		);
		
		searchLayout.setVerticalGroup(
				searchLayout.createSequentialGroup()
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
	
	private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Projet Info CosmopoliTSE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Add content to the window.
        frame.add(new Interface(), BorderLayout.CENTER);
        
        //Display the window.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(2*screenSize.width/3, 2*screenSize.height/3);
        frame.setLocation(screenSize.width/6, screenSize.height/6);
        frame.setVisible(true);
    }
	
	public static void main(String[] args)
	{
		createAndShowGUI();
		
	}
	
}

