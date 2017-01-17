package TSE.P_INFO.CosmopoliTse.InterfaceGraphique;

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

import TSE.P_INFO.CosmopoliTse.UsersStories.AliceGUI;

public class AliceListener implements ActionListener, MouseListener, FocusListener{

	InterfaceAlice alice = new InterfaceAlice();
	Vector<URI> links = new Vector<URI>();
	
	public AliceListener() {
		actionsAlice();
	}

	private void actionsAlice() {
		alice.search1.addActionListener(this);
		alice.search2.addActionListener(this);
		alice.search3.addActionListener(this);
		alice.answerList.addMouseListener(this);
		alice.subject.addFocusListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton)
		{
			switch(((JButton) e.getSource()).getName())
			{
				case("story1"):
				{
					alice.answerList.setVisible(true);
					alice.errorArea.setText("");alice.errorArea.setVisible(false); //links.clear();
					try{
						links=AliceGUI.firstStoryGUI(Integer.parseInt(alice.subject.getText()),alice.answerList, alice.errorArea);
					}catch(Exception ex){
						alice.answerList.setVisible(false);
						System.err.println(ex);
						alice.errorArea.setVisible(true);
						if (ex instanceof NumberFormatException)
							alice.errorArea.setText("Veuillez insérer un nombre");
						else
							alice.errorArea.setText("Aucun tag trouvé pour cet utilisateur");
					}
					break;
				}
				case("story2"):
				{
					
					break;
				}
				case("story3"):
				{
					alice.answerList.setVisible(true);
					alice.errorArea.setText("");alice.errorArea.setVisible(false); //links.clear();
					try{
						links = AliceGUI.thirdStoryGUI(Integer.parseInt(alice.subject.getText()),alice.answerList, alice.errorArea);
					}catch(Exception ex){
						alice.answerList.setVisible(false);
						System.err.println(ex);
						alice.errorArea.setVisible(true);
						if(ex instanceof NumberFormatException)
							alice.errorArea.setText("Veuillez insérer un nombre");
						else
							alice.errorArea.setText("Userid "+ alice.subject.getText() +" introuvé, veuillez refaire cette recherche ");
					}
					break;
				}
				
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int user = alice.answerList.getSelectedIndex();
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
