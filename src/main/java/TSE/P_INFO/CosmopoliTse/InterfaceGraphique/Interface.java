package TSE.P_INFO.CosmopoliTse.InterfaceGraphique;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Interface extends JPanel{

	public Interface(){
		
		super(new GridLayout(1, 1));
		
		JTabbedPane tabbedPane = new JTabbedPane();
		JScrollPane scrollPane = new JScrollPane(tabbedPane);
	
		DaveListener daveL = new DaveListener();
		JPanel dave = daveL.dave;
		tabbedPane.addTab("Dave", dave);
		tabbedPane.setToolTipTextAt(0, "Chasseur de têtes, cherche des profils intéressants");
		
		AliceListener aliceL = new AliceListener();
		JPanel alice = aliceL.alice;
		tabbedPane.addTab("Alice", alice);
		tabbedPane.setToolTipTextAt(1, "Développeuse confirmée, collectionneuse de badges");
		
		JPanel bob = new JPanel();
		tabbedPane.addTab("Bob", bob);
		tabbedPane.setToolTipTextAt(2, "Développeur débutant, cherche des réponses à ses problèmes");
		
		add(scrollPane);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
	}
			
	private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Projet Info CosmopoliTSE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Add content to the window.
        frame.add(new Interface(), BorderLayout.CENTER);
        
        //Display the window.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(3*screenSize.width/7, 3*screenSize.height/7);
        frame.setLocation(2*screenSize.width/7, 2*screenSize.height/7);
        frame.setVisible(true);
    }
	
	public static void main(String[] args)
	{
		createAndShowGUI();
		
	}
	
}

