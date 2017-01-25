package TSE.P_INFO.CosmopoliTse.InterfaceGraphique;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import TSE.P_INFO.CosmopoliTse.InterfaceGraphique.Listeners.*;

@SuppressWarnings("serial")
public class GlobalInterface extends JPanel{

	static JFrame frame ;
	static JTabbedPane tabbedPane;
	public GlobalInterface(){
		
		super(new GridLayout(1, 1));
		
		tabbedPane = new JTabbedPane();
		JScrollPane scrollPane = new JScrollPane(tabbedPane);
	
		DaveListener daveL = new DaveListener();
		JPanel dave = daveL.getDave();
		tabbedPane.addTab("Dave", dave);
		tabbedPane.setToolTipTextAt(0, "Chasseur de têtes, cherche des profils intéressants");
		
		AliceListener aliceL = new AliceListener();
		JPanel alice = aliceL.getAlice();
		aliceL.getAlice();
		tabbedPane.addTab("Alice", alice);
		tabbedPane.setToolTipTextAt(1, "Développeuse confirmée, collectionneuse de badges");
		
		BobListener bobL = new BobListener();
		JPanel bob = bobL.getBob();
		tabbedPane.addTab("Bob", bob);
		tabbedPane.setToolTipTextAt(2, "Développeur débutant, cherche des réponses à ses problèmes");
		
		add(scrollPane);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		tabbedPane.addChangeListener(new Listener());
		
	}
		
	public class Listener implements ChangeListener{
		@Override
		public void stateChanged(ChangeEvent arg0) {
			frame.setTitle("Projet Info CosmopoliTSE - " + tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
		}
		
	}
	
	private static void createAndShowGUI() {
        //Create and set up the window.

		BufferedImage icone = null;
		try {
		    icone = ImageIO.read(GlobalInterface.class.getResource("stackoverflow.png"));
		} catch (IOException e) {
			System.err.println(e);
		}
		
		frame = new JFrame("Projet Info CosmopoliTSE - Dave");
        frame.setIconImage(icone);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Add content to the window.
        frame.add(new GlobalInterface(), BorderLayout.CENTER);
        
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

