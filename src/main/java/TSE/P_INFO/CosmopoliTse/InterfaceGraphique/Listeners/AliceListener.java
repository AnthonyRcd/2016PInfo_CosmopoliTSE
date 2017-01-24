package TSE.P_INFO.CosmopoliTse.InterfaceGraphique.Listeners;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JTextField;

import TSE.P_INFO.CosmopoliTse.InterfaceGraphique.Containers.InterfaceAlice;
import TSE.P_INFO.CosmopoliTse.UsersStories.AliceGUI;

public class AliceListener implements ActionListener, MouseListener, FocusListener{

	InterfaceAlice alice = new InterfaceAlice();
	Vector<URI> links = new Vector<URI>();
	
	
	public InterfaceAlice getAlice() {
		return alice;
	}

	public void setAlice(InterfaceAlice alice) {
		this.alice = alice;
	}

	public Vector<URI> getLinks() {
		return links;
	}

	public void setLinks(Vector<URI> links) {
		this.links = links;
	}

	public AliceListener() {
		actionsAlice();
	}

	private void actionsAlice() {
		alice.getSearch1().addActionListener(this);
		alice.getSearch2().addActionListener(this);
		alice.getSearch3().addActionListener(this);
		alice.getAnswerList().addMouseListener(this);
		alice.getSubject().addFocusListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton)
		{
			switch(((JButton) e.getSource()).getName())
			{
				case("story1"):
				{
					alice.getAnswerList().setVisible(true);
					alice.getErrorArea().setText("");alice.getErrorArea().setVisible(false); //links.clear();
					try{
						links=AliceGUI.firstStoryGUI(Integer.parseInt(alice.getSubject().getText()),alice.getAnswerList(), alice.getErrorArea());
					}catch(Exception ex){
						alice.getAnswerList().setVisible(false);
						System.err.println(ex);
						alice.getErrorArea().setVisible(true);
						if (ex instanceof NumberFormatException)
							alice.getErrorArea().setText("Veuillez insérer un nombre");
						else
							alice.getErrorArea().setText("Aucun tag trouvé pour cet utilisateur");
					}
					break;
				}
				case("story2"):
				{
					
					break;
				}
				case("story3"):
				{
					alice.getAnswerList().setVisible(true);
					alice.getErrorArea().setText("");alice.getErrorArea().setVisible(false); //links.clear();
					try{
						links = AliceGUI.thirdStoryGUI(Integer.parseInt(alice.getSubject().getText()),alice.getAnswerList(), alice.getErrorArea());
					}catch(Exception ex){
						alice.getAnswerList().setVisible(false);
						System.err.println(ex);
						alice.getErrorArea().setVisible(true);
						if(ex instanceof NumberFormatException)
							alice.getErrorArea().setText("Veuillez insérer un nombre");
						else
							alice.getErrorArea().setText("Userid "+ alice.getSubject().getText() +" introuvé, veuillez refaire cette recherche ");
					}
					break;
				}
				
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int user = alice.getAnswerList().getSelectedIndex();
		try {
			if(user!=0) Desktop.getDesktop().browse(links.get(user-1));
        } catch (IOException ex) {
            System.err.println("It looks like there's a problem");
        }
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		if(arg0.getSource() instanceof JTextField)
		{
			JTextField source = (JTextField) arg0.getSource();
			source.setText("");
		}
		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
